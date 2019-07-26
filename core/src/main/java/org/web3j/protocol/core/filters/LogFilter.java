package org.web3j.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.PlatonFilter;
import org.web3j.protocol.core.methods.response.PlatonLog;
import org.web3j.protocol.core.methods.response.Log;

/**
 * Log filter handler.
 */
public class LogFilter extends Filter<Log> {

    private final org.web3j.protocol.core.methods.request.PlatonFilter ethFilter;

    public LogFilter(
            Web3j web3j, Callback<Log> callback,
            org.web3j.protocol.core.methods.request.PlatonFilter ethFilter) {
        super(web3j, callback);
        this.ethFilter = ethFilter;
    }


    @Override
    PlatonFilter sendRequest() throws IOException {
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
