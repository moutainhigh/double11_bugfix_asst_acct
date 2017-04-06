package com.yuwang.pinju.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>用于过滤访问hessian服务的客户端ip，只有品聚内部ip才可以访问</p>
 *
 * @author shihongbo
 */
public class HessianRemoteFilter implements Filter {
	private final static Log log = LogFactory.getLog(HessianRemoteFilter.class);
	
    private HttpServletRequest request;
    private HttpServletResponse response;
    public void destroy() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {
    	
        this.request = (HttpServletRequest) servletRequest;
        this.response = ((HttpServletResponse) servletResponse);
        
        String remoteIp = ServletUtil.getRemoteIp();
        Boolean validIp = isFromPinju(remoteIp);

        if(validIp){
        	chain.doFilter(request, response);
        }else{
        	log.info("invalid ip " + remoteIp + " access hessian service, just ignore it.");
			return;
		}
        
    }

    //查看是否来源于品聚内部
    private boolean isFromPinju(String remoteIp){
    	String cips[] = PinjuConstant.PINJU_COMPANY_IPS;
    	if (cips!=null) {
    		for (String cip : cips) {
    			
    			if (cip.equalsIgnoreCase(remoteIp)) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }   
}