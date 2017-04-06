package com.yuwang.pinju.core.executor.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yuwang.pinju.core.executor.PinjuExecutor;

public class PinjuThreadPoolExecutor implements PinjuExecutor {

	private ExecutorService executor;

	public PinjuThreadPoolExecutor() {
		executor = Executors.newFixedThreadPool(10);
	}

	@Override
	public void execute(Runnable command) {
		executor.execute(command);
	}

	@Override
	public void shutdown() {
		if (executor != null) {
			executor.shutdown();
		}
	}
}
