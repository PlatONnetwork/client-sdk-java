/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.platon.protocol.admin.methods.response;


import com.platon.protocol.core.Response;
import com.platon.utils.Numeric;

import java.util.Map;

/** txpool_status. */
public class TxPoolStatus extends Response<Map<String, String>> {
    public Integer getPending() {
        return Numeric.decodeQuantity((String) getResult().get("pending")).intValue();
    }

    public Integer getQueued() {
        return Numeric.decodeQuantity((String) getResult().get("queued")).intValue();
    }
}
