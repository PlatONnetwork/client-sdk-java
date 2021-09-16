package com.platon.protocol.admin.methods.response;

import com.platon.protocol.core.Response;

import java.util.List;

/** personal_listWallets **/
public class PersonalListWallets extends Response<List<PersonalListWallets.Wallet>> {


    public static class Wallet {
        private List<Account> accounts;
        private String status;
        private String url;

        public List<Account> getAccounts() {
            return accounts;
        }

        public void setAccounts(List<Account> accounts) {
            this.accounts = accounts;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Account {
        private String url;
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


    }
}