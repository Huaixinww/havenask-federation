/*
*Copyright (c) 2021, Alibaba Group;
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at

*   http://www.apache.org/licenses/LICENSE-2.0

*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
*/

/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * Modifications Copyright Havenask Contributors. See
 * GitHub history for details.
 */

package org.havenask.action.admin.cluster.storedscripts;

import org.havenask.common.io.stream.BytesStreamOutput;
import org.havenask.common.io.stream.StreamInput;
import org.havenask.test.HavenaskTestCase;
import org.havenask.action.admin.cluster.storedscripts.GetStoredScriptRequest;

import java.io.IOException;

import static org.havenask.test.VersionUtils.randomVersion;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetStoredScriptRequestTests extends HavenaskTestCase {
    public void testGetIndexedScriptRequestSerialization() throws IOException {
        GetStoredScriptRequest request = new GetStoredScriptRequest("id");

        BytesStreamOutput out = new BytesStreamOutput();
        out.setVersion(randomVersion(random()));
        request.writeTo(out);

        StreamInput in = out.bytes().streamInput();
        in.setVersion(out.getVersion());
        GetStoredScriptRequest request2 = new GetStoredScriptRequest(in);

        assertThat(request2.id(), equalTo(request.id()));
    }
}