/**
 * 
 */
package com.yuwang.pinju.web.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.xml.DOMConfigurator;

import com.yuwang.pinju.core.config.ConfigHelper;

/**  
 * @Project: pinju-web
 * @Title: LogConfigListener.java
 * @Package com.yuwang.pinju.web.system
 * @Description: 构建一些系统配置信息
 * @author liuboen liuboen@zba.com
 * @date 2011-6-30 下午01:54:00
 * @version V1.0  
 */

public class LogConfigListener implements ServletContextListener{

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		String p = System.clearProperty("log.root.path");
		sce.getServletContext().log("remove log4j placeholder system property [log.root.path] before value [" + p + "]");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.setProperty("log.root.path",ConfigHelper.getDefault().getString("log.root.path") );
		sce.getServletContext().log("log4j placeholder system property [log.root.path] value [" + System.getProperty("log.root.path") + "]");

		DOMConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource("log4j.xml"));
		sce.getServletContext().log("load log4j configuration file log4j.xml");
		
	}

}
