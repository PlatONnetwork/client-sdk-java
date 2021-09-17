package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;
import com.platon.protocol.core.methods.response.bean.WaitSlashingNode;
import com.platon.utils.JSONUtil;

import java.util.List;

/**
 * debug_getWaitSlashingNodeList
 */
public class DebugWaitSlashingNodeList extends Response<String> {

    public List<WaitSlashingNode> get() {
        return JSONUtil.parseArray(getResult(), WaitSlashingNode.class);
    }
}
