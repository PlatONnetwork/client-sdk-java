package com.platon.protocol.core.methods.response;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.platon.protocol.ObjectMapperFactory;
import com.platon.protocol.core.Response;


/** admin_peers. */
public class AdminPeers extends Response<List<AdminPeers.Peer>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = AdminPeers.ResponseDeserializer.class)
    public void setResult(List<Peer> result) {
        super.setResult(result);
    }

    public static class Peer {
        public Peer() {}

        public Peer(String id, String name, String enode, PeerNetwork network) {
            this.id = id;
            this.name = name;
            this.network = network;
            this.enode = enode;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEnode() {
            return enode;
        }

        private String id;
        private String name;
        private PeerNetwork network;
        private String enode;

        public PeerNetwork getNetwork() {
            return network;
        }
    }

    public static class PeerNetwork {

        public PeerNetwork() {}

        private String localAddress;
        private String remoteAddress;

        public PeerNetwork(String localAddress, String remoteAddress) {
            this.localAddress = localAddress;
            this.remoteAddress = remoteAddress;
        }

        public String getLocalAddress() {
            return localAddress;
        }

        public String getRemoteAddress() {
            return remoteAddress;
        }
    }

    public static class ResponseDeserializer extends JsonDeserializer<List<Peer>> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public List<Peer> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, new TypeReference<List<Peer>>() {});
            } else {
                return null; // null is wrapped by Optional in above getter
            }
        }
    }
}