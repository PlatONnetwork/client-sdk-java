package com.alaya.platon.contracts.util;

import lombok.Data;

import java.util.*;

public class IPGenerator {
    @Data
    static class IpPort{
        private String protocol;
        private String ip;
        private Long port;
        public IpPort(String protocol,String ip,Long port){
            this.protocol=protocol;
            this.ip=ip;
            this.port=port;
        }
    }
    public static List<IpPort> randomIps(String protocol,String ipPrefix, int startPort,int endPort, int count){
        String [] ipParts = ipPrefix.split("\\.");
        int needPartCount = 4-ipParts.length;
        Set<IpPort> ipSet = new HashSet<>();
        while (ipSet.size()<count){
            StringBuilder sb = new StringBuilder(ipPrefix);
            for (int j=0;j<needPartCount;j++) sb.append(".").append(randomIpPart());
            IpPort ipPort = new IpPort(protocol,sb.toString(),randomPort(startPort,endPort));
            ipSet.add(ipPort);
        }
        return new ArrayList<>(ipSet);
    }

    private static String randomIpPart() {
        Random random = new Random();
        int part = 0;
        while (part==0) part = random.nextInt(254);
        return String.valueOf(part);
    }

    private static Long randomPort(int startPort,int endPort) {
        Random random = new Random();
        int port = 0;
        while (port==0||port<startPort) port = random.nextInt(endPort);
        return Long.valueOf(port);
    }
}
