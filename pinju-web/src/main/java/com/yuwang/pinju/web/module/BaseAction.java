/**
 * 
 */
package com.yuwang.pinju.web.module;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.yuwang.pinju.common.DateUtil;

/**  
 * @Project: pinju-web
 * @Title: BaseAction.java
 * @Package com.yuwang.pinju.web.module
 * @Description: BaseAction,提供页面操作基本数据
 * @author liuboen liuboen@zba.com
 * @date 2011-6-16 下午03:09:22
 * @version V1.0  
 */

public class BaseAction extends TokenAction implements SessionAware,
ServletResponseAware, ServletRequestAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6552764196600021567L;
	protected  final   Log log =LogFactory.getLog(this.getClass().getName());
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	/**
	 * 全局 HTTP 404 页面
	 */
	public static final String PAGE_404 = "page404";
	
	/**
	 * 一天表示多少毫秒
	 */
	public final static  long ONE_DAY=1000*60*60*24;
	/**
	 * 一小时表示多少毫秒
	 */
	public final static  long ONE_HOUR=1000*60*60;
	/**
	 * 一分钟表示多少毫秒
	 */
	public final static  long ONE_MINUTE=1000*60;

	/**
	 * 获取页面参数,非数字类型时返回0
	 * @author liuboen
	 * @param name
	 * @return
	 */
	protected long getLong(String name) {
		String longStr=request.getParameter(name);
		try {
			return Long.parseLong(longStr);
		} catch (NumberFormatException e) {
				return 0;
		}
	}
	/**
	 * 获取页面参数,非数字类型时返回0
	 * @author liuboen
	 * @param name
	 * @return
	 */
	protected Long[] getLongs(String name) {
		try {
			String str[]=request.getParameterValues(name);
			if (str!=null) {
				Long[] l = new Long[str.length];
				  
				  for( int i = 0; i < l.length; i++){
				   l[i] = new Long(str[i]);
				  }
				return l;
			}
			return null;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	/**
	 * 获取页面参数,返回string
	 * @author liuboen
	 * @param name
	 * @return
	 */
	protected String getString(String name) {
		String str=request.getParameter(name);
		return str;
	}
	/**
	 * 获取页面参数,返回string[]
	 * @author liuboen
	 * @param name
	 * @return
	 */
	protected String[] getStrings(String name) {
		String str[]=request.getParameterValues(name);
		return str;
	}
	/**
	 * 获取页面参数,非数字类型时返回0
	 * @author liuboen
	 * @param name
	 * @return
	 */
	protected int getInteger(String name) {
		String longStr=request.getParameter(name);
		try {
			return Integer.parseInt(longStr);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	/**
	 * 获取页面参数,非数字类型时返回0
	 * @author liuboen
	 * @param name
	 * @return
	 */
	protected Integer[] getIntegers(String name) {
		try {
			String str[]=request.getParameterValues(name);
			if (str!=null) {
				Integer[] l = new Integer[str.length];
				  
				  for( int i = 0; i < l.length; i++){
					  if (str[i]!=null&&!str[i].equals("")) {
						  l[i] = new Integer(str[i]);
					}
				  }
				return l;
			}
			return null;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	/**
	 * 获取时间
	 * @author liuboen
	 * @param name
	 * @return
	 */
	protected Date getDate(String name){
		String dateStr=request.getParameter(name);
		Date date=DateUtil.parse("yyyy-MM-dd HH:mm:ss", dateStr);
		return date;
	}
	/**
	 * 获取指定格式时间
	 * @author liuboen
	 * @param name
	 * @return
	 */
	protected Date getDate(String name,String patten){
		String dateStr=request.getParameter(name);
		Date date=DateUtil.parse(patten, dateStr);
		return date;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		
		
	}
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
}
