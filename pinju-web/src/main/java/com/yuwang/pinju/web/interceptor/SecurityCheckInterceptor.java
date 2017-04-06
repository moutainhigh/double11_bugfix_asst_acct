/**
 * 品聚生活，创造奇迹
 */
package com.yuwang.pinju.web.interceptor;

import java.util.*;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.core.util.CharsetUtil;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.struts2.PinjuAction;

/**
 * 关键字过滤的拦截器，如果有对应的非法关键字，则返回对应的错误页面
 * 
 * @author liyouguo
 * 
 * @since 2011-7-25
 */
public class SecurityCheckInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4886230632099736184L;

	/**
	 * 需要过滤的正则表达式
	 */
	private String expression;

	/**
	 * 指定的参数列表 （如果为空，则对提交的所有参数进行过滤）
	 */
	private List<String> paramNameList = new ArrayList<String>();

	/**
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub

		if (expression == null || "".equals(expression.trim()))// 正则表达式为空，则无需过滤
			invocation.invoke();

		Pattern pattern = Pattern.compile(expression);
		HttpServletRequest request = ServletActionContext.getRequest();
		if (paramNameList != null && paramNameList.size() > 0) {// 对指定参数进行过滤（如果为空，则对提交的所有参数进行过滤）
			for (String key : paramNameList) {
				String value = request.getParameter(key);
				String decodeValue = CharsetUtil.decodeURL(value);
				if (value != null
						&& (pattern.matcher(value).matches() || pattern
								.matcher(decodeValue).matches()))
					return PinjuAction.FORBIDDEN_PARAM_SUMBIT;
			}
		} else {
			Enumeration keys = request.getAttributeNames();
			while (keys.hasMoreElements()) {
				String value = request
						.getParameter((String) keys.nextElement());
				String decodeValue = CharsetUtil.decodeURL(value);
				if (value != null
						&& (pattern.matcher(value).matches() || pattern
								.matcher(decodeValue).matches()))
					return PinjuAction.FORBIDDEN_PARAM_SUMBIT;
			}
		}
		return invocation.invoke();
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public List<String> getParamNameList() {
		return paramNameList;
	}

	public void setParamNameList(String paramNameStr) {
		if (EmptyUtil.isBlank(paramNameStr))
			return;
		String[] paramNames = paramNameStr.split(",");
		for (int i = 0, j = paramNames.length; i < j; i++) {
			if (paramNames[i] != null && !"".equals(paramNames[i].trim()))
				paramNameList.add(paramNames[i].trim());
		}
	}
}
