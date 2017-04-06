package com.yuwang.pinju.web.listener;

import org.apache.commons.logging.Log;

/**
 * <p>事件处理</p>
 *
 * @author gaobaowen
 * @since 2011-7-29 13:23:04
 */
public interface EventProcess {
	
	/**
	 * <p>处理事件</p>
	 *
	 * @param log 日志记录器
	 * @return 处理事件的结果
	 *
	 * @author gaobaowen
	 * @since 2011-7-29 13:23:15
	 */
	boolean process(Log log);
}
