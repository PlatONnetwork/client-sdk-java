package org.web3j.platon.contracts.util;


import org.web3j.platon.contracts.bean.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FakeNodeGenerator {
    public static List<Node> getFakeNodes(String protocol, String ipPrefix, int startPort, int endPort, int count){
        List<IPGenerator.IpPort> fakeIpPorts = IPGenerator.randomIps(protocol,ipPrefix,startPort,endPort,count);
        List<ECKeyPairGenerator.KeyPair> keyPairs = ECKeyPairGenerator.randomKeyPairs(count);

        List<Node> fakeNodes = new ArrayList<>();
        for (int i=0;i<count;i++){
            IPGenerator.IpPort ipPort = fakeIpPorts.get(i);
            ECKeyPairGenerator.KeyPair keyPair = keyPairs.get(i);
            Node node = new Node(keyPair.getPublicKey(),randomName()+1,ipPort.getIp(),ipPort.getPort().toString());
            fakeNodes.add(node);
        }
        return fakeNodes;
    }

    private static final String [] namePrefix = {"Xuni-","Fake-","Jia-","Virtual-"};

    private static String randomName(){
        Random random = new Random();
        int index = random.nextInt(namePrefix.length);
        String prefix = namePrefix[index];
        String name = prefix+ UUID.randomUUID().toString().substring(0,6);
        return name;
    }
}
