package com.alaya.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.alaya.protocol.core.methods.response.PlatonFilter;
import com.alaya.protocol.core.methods.response.PlatonLog;
import com.alaya.protocol.Web3j;
import com.alaya.protocol.core.Request;

/**
 * Handler for working with transaction filter requests.
 */
public class PendingTransactionFilter extends Filter<String> {

    public PendingTransactionFilter(Web3j web3j, Callback<String> callback) {
        super(web3j, callback);
    }

    @Override
    PlatonFilter sendRequest() throws IOException {
        return web3j.platonNewPendingTransactionFilter().send();
    }

    @Override
    void process(List<PlatonLog.LogResult> logResults) {
        for (PlatonLog.LogResult logResult : logResults) {
            if (logResult instanceof PlatonLog.Hash) {
                String transactionHash = ((PlatonLog.Hash) logResult).get();
                callback.onEvent(transactionHash);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + ", required Hash");
            }
        }
    }

    /**
     * Since the pending transaction filter does not support historic filters,
     * the filterId is ignored and an empty optional is returned
     * @param filterId
     * Id of the filter for which the historic log should be retrieved
     * @return
     * Optional.empty()
     */
    @Override
    protected Optional<Request<?, PlatonLog>> getFilterLogs(BigInteger filterId) {
        return Optional.empty();
    }
}

