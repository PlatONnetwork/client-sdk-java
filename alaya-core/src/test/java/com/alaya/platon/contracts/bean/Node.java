package com.alaya.platon.contracts.bean;

import lombok.Data;

@Data
public class Node {
    // 节点ID
    private String id;
    // 节点ID
    private String name;
    // 节点IP
    private String ip;
    // 节点端口
    private String p2pPort;

    public Node(String id, String name, String ip, String p2pPort) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.p2pPort = p2pPort;
    }
}
