/*
 * Copyright (c) 2021, Alibaba Group;
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.havenask.engine.search.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.havenask.action.ActionListener;
import org.havenask.action.ingest.IngestActionForwarder;
import org.havenask.action.search.SearchRequest;
import org.havenask.action.search.SearchResponse;
import org.havenask.action.search.ShardSearchFailure;
import org.havenask.action.support.ActionFilters;
import org.havenask.action.support.HandledTransportAction;
import org.havenask.client.ha.SqlResponse;
import org.havenask.cluster.ClusterState;
import org.havenask.cluster.service.ClusterService;
import org.havenask.common.inject.Inject;
import org.havenask.engine.NativeProcessControlService;
import org.havenask.engine.search.HavenaskSearchQueryProcessor;
import org.havenask.engine.rpc.QrsClient;
import org.havenask.engine.rpc.http.QrsHttpClient;
import org.havenask.engine.search.HavenaskSearchFetchProcessor;
import org.havenask.search.internal.InternalSearchResponse;
import org.havenask.tasks.Task;
import org.havenask.threadpool.ThreadPool;
import org.havenask.transport.TransportService;

import java.util.Map;

public class TransportHavenaskSearchAction extends HandledTransportAction<SearchRequest, SearchResponse> {
    private static final Logger logger = LogManager.getLogger(TransportHavenaskSearchAction.class);
    private ClusterService clusterService;
    private final IngestActionForwarder ingestForwarder;
    private QrsClient qrsClient;
    private HavenaskSearchQueryProcessor havenaskSearchQueryProcessor;
    private HavenaskSearchFetchProcessor havenaskSearchFetchProcessor;

    @Inject
    public TransportHavenaskSearchAction(
        ClusterService clusterService,
        TransportService transportService,
        NativeProcessControlService nativeProcessControlService,
        ActionFilters actionFilters
    ) {
        super(HavenaskSearchAction.NAME, transportService, actionFilters, SearchRequest::new, ThreadPool.Names.SEARCH);
        this.clusterService = clusterService;
        this.ingestForwarder = new IngestActionForwarder(transportService);
        this.qrsClient = new QrsHttpClient(nativeProcessControlService.getQrsHttpPort());
        havenaskSearchQueryProcessor = new HavenaskSearchQueryProcessor(qrsClient);
        havenaskSearchFetchProcessor = new HavenaskSearchFetchProcessor(qrsClient);
    }

    @Override
    protected void doExecute(Task task, SearchRequest request, ActionListener<SearchResponse> listener) {
        if (false == clusterService.localNode().isIngestNode()) {
            ingestForwarder.forwardIngestRequest(HavenaskSearchAction.INSTANCE, request, listener);
            return;
        }

        try {
            // TODO: 目前的逻辑只有单havenask索引的查询会走到这里，后续如果有多索引的查询，这里需要做相应的修改
            if (request.indices().length != 1) {
                throw new IllegalArgumentException("illegal index count! only support search single havenask index.");
            }
            String tableName = request.indices()[0];

            long startTime = System.nanoTime();

            ClusterState clusterState = clusterService.state();

            Map<String, Object> indexMapping = clusterState.metadata().index(tableName).mapping() != null
                ? clusterState.metadata().index(tableName).mapping().getSourceAsMap()
                : null;
            SqlResponse havenaskSearchQueryPhaseSqlResponse = havenaskSearchQueryProcessor.executeQuery(request, tableName, indexMapping);

            InternalSearchResponse internalSearchResponse = havenaskSearchFetchProcessor.executeFetch(
                havenaskSearchQueryPhaseSqlResponse,
                tableName,
                request.source()
            );

            SearchResponse searchResponse = buildSearchResponse(
                tableName,
                internalSearchResponse,
                havenaskSearchQueryPhaseSqlResponse,
                startTime
            );
            listener.onResponse(searchResponse);
        } catch (Exception e) {
            logger.error("Failed to execute havenask search, ", e);
            listener.onFailure(e);
        }
    }

    private SearchResponse buildSearchResponse(
        String indexName,
        InternalSearchResponse internalSearchResponse,
        SqlResponse havenaskSearchQueryPhaseSqlResponse,
        long startTime
    ) {
        ClusterState clusterState = clusterService.state();
        int totalShards = clusterState.metadata().index(indexName).getNumberOfShards();
        double coveredPercent = havenaskSearchQueryPhaseSqlResponse.getCoveredPercent();
        int successfulShards = (int) Math.round(totalShards * coveredPercent);

        long endTime = System.nanoTime();
        return new SearchResponse(
            internalSearchResponse,
            null,
            totalShards,
            successfulShards,
            0,
            (endTime - startTime) / 1000000,
            ShardSearchFailure.EMPTY_ARRAY,
            SearchResponse.Clusters.EMPTY
        );
    }
}