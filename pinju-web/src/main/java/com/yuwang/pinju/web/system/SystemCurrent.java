package com.yuwang.pinju.web.system;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemCurrent implements Runnable {

	private final static Log log = LogFactory.getLog(SystemCurrent.class);

	private volatile long currentTimeMillis = System.currentTimeMillis();

	private final static SystemCurrent instance = new SystemCurrent();

	private ScheduledExecutorService executor;
	private long period;

	private SystemCurrent() {
		period = 500L;
	}

	@Override
	public void run() {
		currentTimeMillis = System.currentTimeMillis();
//		
//		if(log.isDebugEnabled()) {
//			log.debug("execute SystemCurrent " + currentTimeMillis);
//		}
	}

	public static long currentTimeMillis() {
		return instance.currentTimeMillis;
	}

	static SystemCurrent getInstance() {
		return instance;
	}

	void shutdown() {
		if(executor != null) {
			try {
				executor.shutdown();
				log.info("shutdown SystemCurrent schedule executor finished");
			} catch (Exception e) {
				log.warn("shutdown SystemCurrent schedule executor failed", e);
			}
		}
	}

	void start() {
		executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleAtFixedRate(this, 0, period, TimeUnit.MILLISECONDS);
		log.info("SystemCurrent schedule executor start, period: " + period + "ms");
	}
}
