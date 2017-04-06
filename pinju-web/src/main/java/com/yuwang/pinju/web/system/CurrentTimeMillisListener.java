package com.yuwang.pinju.web.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CurrentTimeMillisListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletcontextevent) {
		SystemCurrent.getInstance().shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletcontextevent) {		
		SystemCurrent.getInstance().start();
	}
}
