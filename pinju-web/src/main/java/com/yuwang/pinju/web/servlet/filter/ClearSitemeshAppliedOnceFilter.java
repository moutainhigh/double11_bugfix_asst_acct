package com.yuwang.pinju.web.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Sitemesh 装饰清空过滤器。用于错误页面时也能使用 Sitemesh 装饰</p>
 *
 * @author gaobaowen
 * 2011-6-22 上午09:57:53
 */
public class ClearSitemeshAppliedOnceFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		request.removeAttribute("com.opensymphony.sitemesh.APPLIED_ONCE");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
