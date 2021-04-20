package com.platon.protocol.core;

import com.platon.protocol.Web3j;
import com.platon.protocol.Web3jService;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

import static org.mockito.Mockito.*;

public class JsonRpc2_0Web3jTest {

    private ScheduledExecutorService scheduledExecutorService
            = mock(ScheduledExecutorService.class);
    private Web3jService service = mock(Web3jService.class);

    private Web3j web3j = Web3j.build(service, 10, scheduledExecutorService);

    @Test
    public void testStopExecutorOnShutdown() throws Exception {
        web3j.shutdown();

        verify(scheduledExecutorService).shutdown();
        verify(service).close();
    }

    @Test(expected = RuntimeException.class)
    public void testThrowsRuntimeExceptionIfFailedToCloseService() throws Exception {
        doThrow(new IOException("Failed to close"))
                .when(service).close();

        web3j.shutdown();
    }
}