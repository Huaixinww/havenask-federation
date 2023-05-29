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

package org.havenask.engine.rpc.http;

import java.io.IOException;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakFilters;
import org.havenask.OkHttpThreadLeakFilter;
import org.havenask.engine.HavenaskITTestCase;
import org.havenask.engine.rpc.HeartbeatTargetResponse;
import org.havenask.engine.rpc.SearcherClient;
import org.havenask.engine.rpc.TargetInfo;
import org.havenask.engine.rpc.UpdateHeartbeatTargetRequest;

@ThreadLeakFilters(filters = { OkHttpThreadLeakFilter.class })
public class SearcherHttpClientIT extends HavenaskITTestCase {
    public void testGetHeartbeatTarget() throws IOException {
        SearcherClient client = new SearcherHttpClient(39200);
        HeartbeatTargetResponse response = client.getHeartbeatTarget();
        String expectCustomInfo = "{\n"
            + "\t\"app_info\":{\n"
            + "\t\t\"config_path\":\"\",\n"
            + "\t\t\"keep_count\":20\n"
            + "\t},\n"
            + "\t\"biz_info\":{\n"
            + "\t\t\"default\":{\n"
            + "\t\t\t\"config_path\":\"/usr/share/havenask/data_havenask/config/bizs/0\",\n"
            + "\t\t\t\"custom_biz_info\":{},\n"
            + "\t\t\t\"keep_count\":5\n"
            + "\t\t}\n"
            + "\t},\n"
            + "\t\"custom_app_info\":{},\n"
            + "\t\"service_info\":{\n"
            + "\t\t\"cm2\":{\n"
            + "\t\t\t\"topo_info\":\"general.default:1:0:553898268:100:2400309353:-1:true|general"
            + ".default_agg:1:0:553898268:100:2400309353:-1:true|general"
            + ".default_sql:1:0:553898268:100:2400309353:-1:true|general"
            + ".para_search_2:1:0:553898268:100:2400309353:-1:true|general"
            + ".para_search_4:1:0:553898268:100:2400309353:-1:true|\"\n"
            + "\t\t}\n"
            + "\t},\n"
            + "\t\"table_info\":{\n"
            + "\t\t\"test_ha\":{\n"
            + "\t\t\t\"0\":{\n"
            + "\t\t\t\t\"config_path\":\"/usr/share/havenask/data_havenask/config/table/0\",\n"
            + "\t\t\t\t\"force_online\":false,\n"
            + "\t\t\t\t\"group_name\":\"default_group_name\",\n"
            + "\t\t\t\t\"index_root\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0/runtimedata\",\n"
            + "\t\t\t\t\"partitions\":{\n"
            + "\t\t\t\t\t\"0_65535\":{\n"
            + "\t\t\t\t\t\t\"check_index_path\":\"\",\n"
            + "\t\t\t\t\t\t\"deploy_status\":2,\n"
            + "\t\t\t\t\t\t\"deploy_status_map\":[\n"
            + "\t\t\t\t\t\t\t[\n"
            + "\t\t\t\t\t\t\t\t0,\n"
            + "\t\t\t\t\t\t\t\t{\n"
            + "\t\t\t\t\t\t\t\t\t\"local_config_path\":\"/usr/share/havenask/data_havenask/local_search_12000"
            + "/general_0/zone_config/table/test_ha/0/\",\n"
            + "\t\t\t\t\t\t\t\t\t\"deploy_status\":2\n"
            + "\t\t\t\t\t\t\t\t}\n"
            + "\t\t\t\t\t\t\t]\n"
            + "\t\t\t\t\t\t],\n"
            + "\t\t\t\t\t\t\"inc_version\":0,\n"
            + "\t\t\t\t\t\t\"keep_count\":1,\n"
            + "\t\t\t\t\t\t\"loaded_config_path\":\"/usr/share/havenask/data_havenask/config/table/0\",\n"
            + "\t\t\t\t\t\t\"loaded_index_root\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0"
            + "/runtimedata\",\n"
            + "\t\t\t\t\t\t\"local_index_path\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0"
            + "/runtimedata/test_ha/generation_0/partition_0_65535\",\n"
            + "\t\t\t\t\t\t\"rt_status\":0,\n"
            + "\t\t\t\t\t\t\"schema_version\":0,\n"
            + "\t\t\t\t\t\t\"table_load_type\":0,\n"
            + "\t\t\t\t\t\t\"table_status\":6,\n"
            + "\t\t\t\t\t\t\"table_type\":0\n"
            + "\t\t\t\t\t}\n"
            + "\t\t\t\t},\n"
            + "\t\t\t\t\"raw_index_root\":\"\",\n"
            + "\t\t\t\t\"rt_status\":0,\n"
            + "\t\t\t\t\"timestamp_to_skip\":-1\n"
            + "\t\t\t}\n"
            + "\t\t},\n"
            + "\t\t\"in0\":{\n"
            + "\t\t\t\"0\":{\n"
            + "\t\t\t\t\"config_path\":\"/usr/share/havenask/data_havenask/config/table/0\",\n"
            + "\t\t\t\t\"force_online\":false,\n"
            + "\t\t\t\t\"group_name\":\"default_group_name\",\n"
            + "\t\t\t\t\"index_root\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0/runtimedata\",\n"
            + "\t\t\t\t\"partitions\":{\n"
            + "\t\t\t\t\t\"0_65535\":{\n"
            + "\t\t\t\t\t\t\"check_index_path\":\"\",\n"
            + "\t\t\t\t\t\t\"deploy_status\":2,\n"
            + "\t\t\t\t\t\t\"deploy_status_map\":[\n"
            + "\t\t\t\t\t\t\t[\n"
            + "\t\t\t\t\t\t\t\t1,\n"
            + "\t\t\t\t\t\t\t\t{\n"
            + "\t\t\t\t\t\t\t\t\t\"local_config_path\":\"/usr/share/havenask/data_havenask/local_search_12000"
            + "/general_0/zone_config/table/in0/0/\",\n"
            + "\t\t\t\t\t\t\t\t\t\"deploy_status\":2\n"
            + "\t\t\t\t\t\t\t\t}\n"
            + "\t\t\t\t\t\t\t]\n"
            + "\t\t\t\t\t\t],\n"
            + "\t\t\t\t\t\t\"inc_version\":1,\n"
            + "\t\t\t\t\t\t\"keep_count\":1,\n"
            + "\t\t\t\t\t\t\"loaded_config_path\":\"/usr/share/havenask/data_havenask/config/table/0\",\n"
            + "\t\t\t\t\t\t\"loaded_index_root\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0"
            + "/runtimedata\",\n"
            + "\t\t\t\t\t\t\"local_index_path\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0"
            + "/runtimedata/in0/generation_0/partition_0_65535\",\n"
            + "\t\t\t\t\t\t\"rt_status\":0,\n"
            + "\t\t\t\t\t\t\"schema_version\":0,\n"
            + "\t\t\t\t\t\t\"table_load_type\":0,\n"
            + "\t\t\t\t\t\t\"table_status\":6,\n"
            + "\t\t\t\t\t\t\"table_type\":0\n"
            + "\t\t\t\t\t}\n"
            + "\t\t\t\t},\n"
            + "\t\t\t\t\"raw_index_root\":\"\",\n"
            + "\t\t\t\t\"rt_status\":0,\n"
            + "\t\t\t\t\"timestamp_to_skip\":-1\n"
            + "\t\t\t}\n"
            + "\t\t}\n"
            + "\t}\n"
            + "}";
        String expectServiceInfo = "{\n"
            + "\"cm2\":\n"
            + "  {\n"
            + "  \"topo_info\":\n"
            + "    \"general.default:1:0:553898268:100:2400309353:-1:true|general"
            + ".default_agg:1:0:553898268:100:2400309353:-1:true|general"
            + ".default_sql:1:0:553898268:100:2400309353:-1:true|general"
            + ".para_search_2:1:0:553898268:100:2400309353:-1:true|general"
            + ".para_search_4:1:0:553898268:100:2400309353:-1:true|\"\n"
            + "  }\n"
            + "}";
        String expectSignature = "{\"table_info\": {\"in0\": {\"0\": {\"index_root\": "
            + "\"/usr/share/havenask/data_havenask/local_search_12000/general_0/runtimedata\", \"partitions\": "
            + "{\"0_65535\": {\"inc_version\": 1}}, \"config_path\": "
            + "\"/usr/share/havenask/data_havenask/config/table/0\"}}, \"test_ha\": {\"0\": {\"index_root\": "
            + "\"/usr/share/havenask/data_havenask/local_search_12000/general_0/runtimedata\", \"partitions\": "
            + "{\"0_65535\": {\"inc_version\": 0}}, \"config_path\": "
            + "\"/usr/share/havenask/data_havenask/config/table/0\"}}}, \"biz_info\": {\"default\": {\"config_path\":"
            + " \"/usr/share/havenask/data_havenask/config/bizs/0\"}}, \"service_info\": {\"zone_name\": \"general\","
            + " \"version\": 0, \"part_count\": 1, \"part_id\": 0}, \"clean_disk\": false}";
        assertEquals(expectCustomInfo, response.getCustomInfo().toString());
        assertEquals(expectServiceInfo, response.getServiceInfo());
        assertEquals(expectSignature, response.getSignature());
    }

    public void testUpdateHeartbeatTarget() throws IOException {
        String targetStr = "{\"table_info\": {\"in0\": {\"0\": {\"index_root\": "
            + "\"/usr/share/havenask/data_havenask/local_search_12000/general_0/runtimedata\", \"partitions\": "
            + "{\"0_65535\": {\"inc_version\": 1}}, \"config_path\": "
            + "\"/usr/share/havenask/data_havenask/config/table/0\"}}}, \"biz_info\": {\"default\": {\"config_path\":"
            + " \"/usr/share/havenask/data_havenask/config/bizs/0\"}}, \"service_info\": {\"zone_name\": \"general\","
            + " \"version\": 0, \"part_count\": 1, \"part_id\": 0}, \"clean_disk\": false}";
        TargetInfo targetInfo = TargetInfo.parse(targetStr);
        UpdateHeartbeatTargetRequest request = new UpdateHeartbeatTargetRequest(targetInfo);
        SearcherClient client = new SearcherHttpClient(39200);
        HeartbeatTargetResponse response = client.updateHeartbeatTarget(request);
        String responseTargetStr = "{\n"
            + "\t\"app_info\":{\n"
            + "\t\t\"config_path\":\"\",\n"
            + "\t\t\"keep_count\":20\n"
            + "\t},\n"
            + "\t\"biz_info\":{\n"
            + "\t\t\"default\":{\n"
            + "\t\t\t\"config_path\":\"/usr/share/havenask/data_havenask/config/bizs/0\",\n"
            + "\t\t\t\"custom_biz_info\":{},\n"
            + "\t\t\t\"keep_count\":5\n"
            + "\t\t}\n"
            + "\t},\n"
            + "\t\"custom_app_info\":{},\n"
            + "\t\"service_info\":{\n"
            + "\t\t\"cm2\":{\n"
            + "\t\t\t\"topo_info\":\"general.default:1:0:1976225101:100:2400309353:-1:true|general"
            + ".default_agg:1:0:1976225101:100:2400309353:-1:true|general"
            + ".default_sql:1:0:1976225101:100:2400309353:-1:true|general"
            + ".para_search_2:1:0:1976225101:100:2400309353:-1:true|general"
            + ".para_search_4:1:0:1976225101:100:2400309353:-1:true|\"\n"
            + "\t\t}\n"
            + "\t},\n"
            + "\t\"table_info\":{\n"
            + "\t\t\"in0\":{\n"
            + "\t\t\t\"0\":{\n"
            + "\t\t\t\t\"config_path\":\"/usr/share/havenask/data_havenask/config/table/0\",\n"
            + "\t\t\t\t\"force_online\":false,\n"
            + "\t\t\t\t\"group_name\":\"default_group_name\",\n"
            + "\t\t\t\t\"index_root\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0/runtimedata\",\n"
            + "\t\t\t\t\"partitions\":{\n"
            + "\t\t\t\t\t\"0_65535\":{\n"
            + "\t\t\t\t\t\t\"check_index_path\":\"\",\n"
            + "\t\t\t\t\t\t\"deploy_status\":2,\n"
            + "\t\t\t\t\t\t\"deploy_status_map\":[\n"
            + "\t\t\t\t\t\t\t[\n"
            + "\t\t\t\t\t\t\t\t1,\n"
            + "\t\t\t\t\t\t\t\t{\n"
            + "\t\t\t\t\t\t\t\t\t\"local_config_path\":\"/usr/share/havenask/data_havenask/local_search_12000"
            + "/general_0/zone_config/table/in0/0/\",\n"
            + "\t\t\t\t\t\t\t\t\t\"deploy_status\":2\n"
            + "\t\t\t\t\t\t\t\t}\n"
            + "\t\t\t\t\t\t\t]\n"
            + "\t\t\t\t\t\t],\n"
            + "\t\t\t\t\t\t\"inc_version\":1,\n"
            + "\t\t\t\t\t\t\"keep_count\":1,\n"
            + "\t\t\t\t\t\t\"loaded_config_path\":\"/usr/share/havenask/data_havenask/config/table/0\",\n"
            + "\t\t\t\t\t\t\"loaded_index_root\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0"
            + "/runtimedata\",\n"
            + "\t\t\t\t\t\t\"local_index_path\":\"/usr/share/havenask/data_havenask/local_search_12000/general_0"
            + "/runtimedata/in0/generation_0/partition_0_65535\",\n"
            + "\t\t\t\t\t\t\"rt_status\":0,\n"
            + "\t\t\t\t\t\t\"schema_version\":0,\n"
            + "\t\t\t\t\t\t\"table_load_type\":0,\n"
            + "\t\t\t\t\t\t\"table_status\":6,\n"
            + "\t\t\t\t\t\t\"table_type\":0\n"
            + "\t\t\t\t\t}\n"
            + "\t\t\t\t},\n"
            + "\t\t\t\t\"raw_index_root\":\"\",\n"
            + "\t\t\t\t\"rt_status\":0,\n"
            + "\t\t\t\t\"timestamp_to_skip\":-1\n"
            + "\t\t\t}\n"
            + "\t\t}\n"
            + "\t}\n"
            + "}";
        String serviceInfoStr = "{\n"
            + "\"cm2\":\n"
            + "  {\n"
            + "  \"topo_info\":\n"
            + "    \"general.default:1:0:1976225101:100:2400309353:-1:true|general"
            + ".default_agg:1:0:1976225101:100:2400309353:-1:true|general"
            + ".default_sql:1:0:1976225101:100:2400309353:-1:true|general"
            + ".para_search_2:1:0:1976225101:100:2400309353:-1:true|general"
            + ".para_search_4:1:0:1976225101:100:2400309353:-1:true|\"\n"
            + "  }\n"
            + "}";
        assertEquals(targetStr, response.getSignature());
        assertEquals(responseTargetStr, response.getCustomInfo().toString());
        assertEquals(serviceInfoStr, response.getServiceInfo());
    }
}
