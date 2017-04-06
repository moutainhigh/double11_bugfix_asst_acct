package com.yuwang.pinju.core.executor.impl;

import com.yuwang.pinju.core.executor.PinjuExecutor;

public class PinjuSyncExecutor implements PinjuExecutor {

	@Override
	public void execute(Runnable command) {
		command.run();
	}

	@Override
	public void shutdown() {
	}
}
