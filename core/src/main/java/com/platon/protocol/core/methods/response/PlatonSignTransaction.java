package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * @Author liushuyu
 * @Date 2021/9/16 11:21
 * @Version
 * @Desc
 */
public class PlatonSignTransaction extends Response<PlatonSignTransaction.SignTransaction> {

    public static class SignTransaction {
        private String raw;
        private Transaction tx;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public Transaction getTx() {
            return tx;
        }

        public void setTx(Transaction tx) {
            this.tx = tx;
        }
    }
}
