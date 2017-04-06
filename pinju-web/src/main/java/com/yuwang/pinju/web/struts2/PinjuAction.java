package com.yuwang.pinju.web.struts2;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>{@link Action} 接口的扩展。添加了一些常用的全局返回值，建议 Action 实现该接口</p>
 *
 * @author gaobaowen
 * 2011-6-22 上午10:32:46
 */
public interface PinjuAction extends Action {

	/**
	 * 品聚需要登录页面未登录时的全局跳转。若需要在登录后跳回至原页面，需要在 Action
	 * 中执行 {@link ServletUtil#loginCurrentResultUrl() ServletUtil.setStrutsCurrentReturnUrl()} 方法
	 */
	String PINJU_LOGIN = "pinju-login";

	/**
	 * 不被允许的 HTTP 访问时的结果
	 */
	String NOT_ALLOWED_METHOD = "not-allowed-method";

	/**
	 * 全局返回之前的页面
	 */
	String RETURN_URL = "returnUrl";

	/**
	 * 全局用户协议页面
	 */
	String AGREEMENT = "agreement";

	/**
	 * 重复提交
	 */
	String REPEAT_SUBMIT = "repeat-submit";

	/**
	 * 全局 HTTP 404 页面
	 */
	String PAGE_404 = "page404";

	/**
	 * 全局 HTTP 500 页面
	 */
	String PAGE_500 = "page500";

	/**
	 * 非法参数提交
	 */
	String FORBIDDEN_PARAM_SUMBIT = "forbidden_param_submit";

	/**
	 * 跳回首页
	 */
	String MAIN_PAGE = "main-page";

	/**
	 * get请求方式
	 */
	String REQUEST_METHOD_GET = "GET";

	/**
	 * 无效的请求
	 */
	String INVALID_REQUEST = "invalid-request";

	/**
	 * 无权限进行操作
	 */
	String ACCESS_DENIED = "access-denied";
}
