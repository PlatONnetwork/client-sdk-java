package com.platon.protocol.core.filters;

import com.platon.protocol.Web3j;
import com.platon.protocol.core.Request;
import com.platon.protocol.core.methods.request.PlatonFilter;
import com.platon.protocol.core.methods.response.Log;
import com.platon.protocol.core.methods.response.PlatonLog;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Log filter handler.
 */
public class LogFilter extends Filter<Log> {

    private final PlatonFilter ethFilter;

    public LogFilter(
            Web3j web3j, Callback<Log> callback,
            PlatonFilter ethFilter) {
        super(web3j, callback);
        this.ethFilter = ethFilter;
    }


    @Override
    com.platon.protocol.core.methods.response.PlatonFilter sendRequest() throws IOException {
        return web3j.platonNewFilter(ethFilter).send();
    }

    @Override
    void process(List<PlatonLog.LogResult> logResults) {
        for (PlatonLog.LogResult logResult : logResults) {
            if (logResult instanceof PlatonLog.LogObject) {
                Log log = ((PlatonLog.LogObject) logResult).get();
                callback.onEvent(log);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + " required LogObject");
            }
        }
    }

    @Override
    protected Optional<Request<?, PlatonLog>> getFilterLogs(BigInteger filterId) {
        return Optional.of(web3j.platonGetFilterLogs(filterId));
    }
}
