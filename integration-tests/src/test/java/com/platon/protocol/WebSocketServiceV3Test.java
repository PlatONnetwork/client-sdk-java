package com.platon.protocol;

import com.platon.protocol.websocket.WebSocketService;
import org.junit.Test;

import java.net.ConnectException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class WebSocketServiceV3Test {

	private WebSocketService webSocketService = new WebSocketService("ws://192.168.120.146:7789", false);
	private Web3j web3j = Web3j.build(webSocketService);

	private Consumer<String> onMessage = message -> {
		System.out.println("----------------------------------   on message = " + message);
	};
	private Consumer<Throwable> onError = error -> {
		System.out.println("----------------------------------   on error = " + error);
	};
	private Runnable onClose = () -> {
		System.out.println("----------------------------------   on close");
	};

	@Test
    public void connect() throws Exception {

		webSocketService.autoReconnect();

		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 1; i++) {
		   executorService.execute(() -> {
			   while (true) {
				   try {
					   TimeUnit.SECONDS.sleep(1);
					   System.out.println(Thread.currentThread().getName() + " cur block number" + web3j.platonBlockNumber().send().getBlockNumber());
				   } catch (Exception e) {
						e.printStackTrace();
				   }
			   }
		   });
		}

		executorService.awaitTermination(1000, TimeUnit.HOURS);
    }
}
