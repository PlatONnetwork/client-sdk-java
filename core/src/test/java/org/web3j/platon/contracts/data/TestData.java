package org.web3j.platon.contracts.data;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.platon.contracts.bean.Node;
import org.web3j.platon.contracts.util.FakeNodeGenerator;

import java.io.*;
import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: Chendongming
 * @Date: 2019/4/16 15:16
 * @Description: 测试数据统一配置
 */
public class TestData {
    private static Logger logger = LoggerFactory.getLogger(TestData.class);

    public static final String web3jTestAddress = "http://192.168.9.76:6789";
    public static final String web3jDockerAddress = "http://192.168.99.100:6789";
    public static final String adminWalletPassword = "88888888";
    public static final String adminTestWalletAddress = "0x493301712671ada506ba6ca7891f436d29185821";
    public static final String adminTestWalletRelativePath = "wallet/admin-test.json";
    public static final String adminDockerWalletAddress = "0xf462b59140246f648f3573b4a38e861161304e79";
    public static final String adminDockerWalletRelativePath = "wallet/admin-docker.json";
    public static final String ownerWalletAddress = "0xf8f3978c14f585c920718c27853e2380d6f5db36";
    public static final String ownerWalletPassword = "88888888";
    public static final String ownerWalletRelativePath = "wallet/owner.json";
    public static final String multisigContractRelativePath = "platon/contracts/multisig.wasm";
    public static final String randomNodeJsonFilePath = System.getProperty("user.home") + File.separator + "RandomNode.json";
    public static final BigInteger depositAmount = new BigInteger("1000000000000000000000000");

    public static Node initData() {
        try {
            File file = new File(TestData.randomNodeJsonFilePath);
            if(file.exists()) file.delete();
            // 生成虚拟节点
            List<Node> nodes = FakeNodeGenerator.getFakeNodes("http://","192.168.9",12789, 13250, 1);
            Node randomNode = nodes.get(0);
            String json = JSON.toJSONString(randomNode);
            BufferedWriter bf  = new BufferedWriter(new FileWriter(file));
            bf.write(json);
            bf.flush();
            bf.close();
            logger.info("Random Node: {}",json);
            return randomNode;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    public static Node getRandomNode() {
        Node node;
        try {
            BufferedReader br = new BufferedReader(new FileReader(TestData.randomNodeJsonFilePath));
            StringBuilder sb = new StringBuilder();
            br.lines().forEach(line->sb.append(line));
            logger.info("Loading Node Config: {}",sb.toString());
            node = JSON.parseObject(sb.toString(),Node.class);
            return node;
        }catch (Exception e){
            node = initData();
            logger.error(e.getMessage());
        }
        return node;
    }

    public static void main(String[] args) {
        initData();
        getRandomNode();
    }
}
