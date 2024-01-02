package com.platon.protocol.core.methods.response;

import com.platon.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * Transaction object used by both {@link PlatonTransaction} and {@link PlatonBlock}.
 */
public class Transaction {
    private static final int CHAIN_ID_INC = 35;
    private static final int LOWER_REAL_V = 27;

    private String hash;
    private String nonce;
    private String blockHash;
    private String blockNumber;
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    private String gasPrice;    // platon中，等于maxFeePerGas
    private String gas;
    private String input;
    private String creates;
    private String publicKey;
    private String raw;
    private String r;
    private String s;
    private long v;  // see https://github.com/web3j/web3j/issues/44
    private String  chainId;    //"0x1"
    private List<String> accessList; //地址列表
    private String type;   // "0x2" 0 - 传统交易（旧） 1 - AccessList交易 2 - DynamicFee交易
    private String maxFeePerGas;            // "0x247da5b94"
    private String maxPriorityFeePerGas;    // "0x0"， platon固定是0

    public Transaction() {
    }

    public Transaction(String hash, String nonce, String blockHash, String blockNumber,
                       String transactionIndex, String from, String to, String value,
                       String gas, String gasPrice, String input, String creates,
                       String publicKey, String raw, String r, String s, long v,
                       String chainId, List<String> accessList, String type, String maxFeePerGas, String maxPriorityFeePerGas) {
        this.hash = hash;
        this.nonce = nonce;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.transactionIndex = transactionIndex;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gasPrice = gasPrice;
        this.gas = gas;
        this.input = input;
        this.creates = creates;
        this.publicKey = publicKey;
        this.raw = raw;
        this.r = r;
        this.s = s;
        this.v = v;

        this.chainId = chainId;
        this.accessList = accessList;
        this.type = type;
        this.maxFeePerGas = maxFeePerGas;
        this.maxPriorityFeePerGas = maxPriorityFeePerGas;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BigInteger getNonce() {
        return Numeric.decodeQuantity(nonce);
    }

    public String getNonceRaw() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(blockNumber);
    }

    public String getBlockNumberRaw() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigInteger getTransactionIndex() {
        return Numeric.decodeQuantity(transactionIndex);
    }

    public String getTransactionIndexRaw() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigInteger getValue() {
        return Numeric.decodeQuantity(value);
    }

    public String getValueRaw() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigInteger getGasPrice() {
        return Numeric.decodeQuantity(gasPrice);
    }

    public String getGasPriceRaw() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getGas() {
        return Numeric.decodeQuantity(gas);
    }

    public String getGasRaw() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getCreates() {
        return creates;
    }

    public void setCreates(String creates) {
        this.creates = creates;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public long getV() {
        return v;
    }

    public long getChainId() {
        /*if (v == LOWER_REAL_V || v == (LOWER_REAL_V + 1)) {
            return null;
        }
        Long chainId = (v - CHAIN_ID_INC) / 2;
        return chainId;*/
        return Numeric.decodeQuantity(type).longValue();
    }
    public String getChainIdRaw() {
        return this.chainId;
    }
    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    // public void setV(byte v) {
    //     this.v = v;
    // }

    // Workaround until Geth & Parity return consistent values. At present
    // Parity returns a byte value, Geth returns a hex-encoded string
    // https://github.com/ethereum/go-ethereum/issues/3339
    public void setV(Object v) {
        if (v instanceof String) {
            this.v = Numeric.toBigInt((String) v).longValueExact();
        } else if (v instanceof Integer) {
            this.v = ((Integer) v).longValue();
        } else {
            this.v = (Long) v;
        }
    }

    public List<String> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<String> accessList) {
        this.accessList = accessList;
    }

    public int getType() {
        return Numeric.decodeQuantity(type).intValue();
    }

    public String getTypeRaw() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigInteger getMaxFeePerGas() {
        return Numeric.decodeQuantity(maxFeePerGas);
    }

    public String getMaxFeePerGasRaw() {
        return maxFeePerGas;
    }

    public void setMaxFeePerGas(String maxFeePerGas) {
        this.maxFeePerGas = maxFeePerGas;
    }


    public BigInteger getMaxPriorityFeePerGas() {
        return Numeric.decodeQuantity(maxPriorityFeePerGas);
    }

    public String getMaxPriorityFeePerGasRaw() {
        return maxPriorityFeePerGas;
    }

    public void setMaxPriorityFeePerGas(String maxPriorityFeePerGas) {
        this.maxPriorityFeePerGas = maxPriorityFeePerGas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (v != that.v) return false;
        if (!Objects.equals(hash, that.hash)) return false;
        if (!Objects.equals(nonce, that.nonce)) return false;
        if (!Objects.equals(blockHash, that.blockHash)) return false;
        if (!Objects.equals(blockNumber, that.blockNumber)) return false;
        if (!Objects.equals(transactionIndex, that.transactionIndex))
            return false;
        if (!Objects.equals(from, that.from)) return false;
        if (!Objects.equals(to, that.to)) return false;
        if (!Objects.equals(value, that.value)) return false;
        if (!Objects.equals(gasPrice, that.gasPrice)) return false;
        if (!Objects.equals(gas, that.gas)) return false;
        if (!Objects.equals(input, that.input)) return false;
        if (!Objects.equals(creates, that.creates)) return false;
        if (!Objects.equals(publicKey, that.publicKey)) return false;
        if (!Objects.equals(raw, that.raw)) return false;
        if (!Objects.equals(r, that.r)) return false;
        if (!Objects.equals(s, that.s)) return false;
        if (!Objects.equals(chainId, that.chainId)) return false;
        if (!Objects.equals(accessList, that.accessList)) return false;
        if (!Objects.equals(type, that.type)) return false;
        if (!Objects.equals(maxFeePerGas, that.maxFeePerGas)) return false;
        return Objects.equals(maxPriorityFeePerGas, that.maxPriorityFeePerGas);
    }

    @Override
    public int hashCode() {
        int result = hash != null ? hash.hashCode() : 0;
        result = 31 * result + (nonce != null ? nonce.hashCode() : 0);
        result = 31 * result + (blockHash != null ? blockHash.hashCode() : 0);
        result = 31 * result + (blockNumber != null ? blockNumber.hashCode() : 0);
        result = 31 * result + (transactionIndex != null ? transactionIndex.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (gasPrice != null ? gasPrice.hashCode() : 0);
        result = 31 * result + (gas != null ? gas.hashCode() : 0);
        result = 31 * result + (input != null ? input.hashCode() : 0);
        result = 31 * result + (creates != null ? creates.hashCode() : 0);
        result = 31 * result + (publicKey != null ? publicKey.hashCode() : 0);
        result = 31 * result + (raw != null ? raw.hashCode() : 0);
        result = 31 * result + (r != null ? r.hashCode() : 0);
        result = 31 * result + (s != null ? s.hashCode() : 0);
        result = 31 * result + (int) (v ^ (v >>> 32));
        result = 31 * result + (chainId != null ? chainId.hashCode() : 0);
        result = 31 * result + (accessList != null ? accessList.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (maxFeePerGas != null ? maxFeePerGas.hashCode() : 0);
        result = 31 * result + (maxPriorityFeePerGas != null ? maxPriorityFeePerGas.hashCode() : 0);
        return result;
    }
}
