package com.yuwang.pinju.core.executor.impl;

import com.yuwang.pinju.core.executor.PinjuExecutor;

public class PinjuEveryThreadExecutor implements PinjuExecutor {

	@Override
	public void execute(Runnable command) {
		new Thread(command).start();
	}

	@Override
	public void shutdown() {
		
	}
}
