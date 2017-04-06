package com.yuwang.pinju.core.executor;

import java.util.concurrent.Executor;

public interface PinjuExecutor extends Executor {

	void shutdown();
}
