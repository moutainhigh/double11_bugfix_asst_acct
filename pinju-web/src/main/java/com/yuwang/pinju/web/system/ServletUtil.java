package com.yuwang.pinju.web.system;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.cookie.util.Tools;
import com.yuwang.pinju.core.constant.system.PinjuConstant;

/**
 * <p>
 * Servlet, Struts2 相关的工具类
 * </p>
 *
 * @author gaobaowen 2011-6-22 上午10:33:52
 */
public class ServletUtil {

	private final static Log log = LogFactory.getLog(ServletUtil.class);

	private volatile static int port = -1;

	private static final ThreadLocal<DateFormat> HTTP_HEADER_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            return df;
        }
    };

	private ServletUtil() {
	}

	/**
	 * <p>
	 * Web 服务器反向代理中用于存放客户端原始 IP 地址的 Http header 名字，若新增其他的需要修改其中的值。
	 * </p>
	 */
	private final static String[] PROXY_REMOTE_IP_ADDRESS = { "X-Forwarded-For", "X-Real-IP" };

	/**
	 * <p>
	 * 允许注册及登录后跳转 URL 的模式
	 * </p>
	 */
//	private final static Pattern RETURN_URL_PATTERN = Pattern.compile("https?://[a-z0-9-]{1,63}" + SysConfig.PINJU_ROOT_DOMAIN.replace(".", "\\.") + "(?::[0-9]{2,5})?(?:[/?].*)?");

	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * <p>
	 * 获取请求的客户端的 IP 地址。若应用服务器前端配有反向代理的 Web 服务器， 需要在 Web 服务器中将客户端原始请求的 IP 地址加入到
	 * HTTP header 中。 详见 {@link ServletUtil#PROXY_REMOTE_IP_ADDRESS}
	 * </p>
	 *
	 * @param request
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		for (int i = 0; i < PROXY_REMOTE_IP_ADDRESS.length; i++) {
			String ip = request.getHeader(PROXY_REMOTE_IP_ADDRESS[i]);
			if (!Tools.isBlank(ip)) {
				return getRemoteIpFromForward(ip);
			}
		}
		return request.getRemoteHost();
	}

	/**
	 * <p>获取浏览器 User-Agent hash 值</p>
	 *
	 * @return 浏览器 User-Agent hash 值，若 User-Agent 不存在或者为空值时返回 0
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 11:24:08
	 */
	public static Integer getUserAgentHash() {
		String userAgent = getHttpHeader("User-Agent");
		if (userAgent == null) {
			return 0;
		}

		userAgent = fixedUserAgent(userAgent);

//		for (Enumeration e = ServletActionContext.getRequest().getHeaderNames(); e.hasMoreElements(); ) {
//			String name = (String)e.nextElement();
//			System.out.printf("%-15s: %s%n", name, ServletActionContext.getRequest().getHeader(name));
//		}
		return userAgent.hashCode() & Integer.MAX_VALUE;
	}

	private static String fixedUserAgent(String userAgent) {
		if (userAgent.indexOf("; 360SE") > 0) {
			String fixed = userAgent.replaceAll("; MSIE [0-9]+.[0-9]+", "; MSIE 0.0");
			if (log.isInfoEnabled()) {
				log.info("current user agent is 360 browser, fixed user agent information.\n" +
						"before: [" + userAgent + "]\n" +
						" fixed: [" + fixed + "]");
			}
			return fixed;
		}
		return userAgent;
	}

	/**
	 * <p>获取 HTTP 请求 Header 数据</p>
	 *
	 * @param name header 名称
	 * @return http header 数据，若传入参数为空时返回 null
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 11:25:26
	 */
	public static String getHttpHeader(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		return getRequest().getHeader(name);
	}

	/**
	 * <p>
	 * 在 Struts 2 环境中获取远程 IP
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 18:00:28
	 */
	public static String getRemoteIp() {
		return getRemoteIp( getRequest() );
	}

	/**
	 * <p>禁用页面缓存，点击后退按钮重新产生请求</p>
	 *
	 * @author gaobaowen
	 * @since 2011-9-5 13:47:30
	 */
	public static void forbidBrowserCache() {
		HttpServletResponse response = getResponse();
		response.addHeader("Cache-Control", "no-store, no-cache");
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Expires", "-1");
	}

	/**
	 * <p>响应页面不允许被放于 frame, iframe 中。以下浏览器支持：IE8+, Firefox 3.6.9+,
	 * Chrome 4.1.249.1042+, Opera 10.50+, Safari 4.0+</p>
	 *
	 * @author gaobaowen
	 * @since 2011-12-19 18:16:53
	 */
	public static void forbidFrame() {
		HttpServletResponse response = getResponse();
		response.addHeader("X-Frame-Options", "DENY");
	}

	/**
	 * <p>响应页面除同源之外，不允许被放于 frame, iframe 中。以下浏览器支持：IE 8.0+, Firefox 3.6.9+,
	 * Chrome 4.1.249.1042+, Opera 10.50+, Safari 4.0+</p>
	 *
	 * @author gaobaowen
	 * @since 2011-12-19 18:19:07
	 */
	public static void forbidFrameUsingSameorigin() {
		HttpServletResponse response = getResponse();
		response.addHeader("X-Frame-Options", "SAMEORIGIN");
	}

	/**
	 * <p>设置 XSS Filter 头。</p>
	 *
	 * @author gaobaowen
	 * @since 2011-12-21 11:46:42
	 */
	public static void setXssFilterHeader() {
		getResponse().addHeader("X-XSS-Protection", "1; mode=block");
	}

	/**
	 * <p>设置客户端浏览器的缓存。使用 HTTP/1.1 协议中的 Cache-Control:max-age
	 * 头和 HTTP/1.0 中的 Expires 头。</p>
	 *
	 * @param maxageSeconds 缓存的秒数，值小于 1 时忽略
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 13:51:44
	 */
	public static void setHeaderMaxAge(int maxageSeconds) {
		if (maxageSeconds < 1) {
			return;
		}
		HttpServletResponse response = getResponse();
		response.addHeader("Cache-Control", "max-age=" + maxageSeconds);
		response.addHeader("Expires", formatHeaderDate(new Date(System.currentTimeMillis() + 1000L * maxageSeconds)));
	}

	/**
	 * <p>
	 * 获取数字格式的 IP 地址。转换算法：将 IP 地址看作是 256 进制数
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-8-15 16:50:37
	 */
	public static long getRemoteNumberIp() {
		String ip = getRemoteIp();
		if (Tools.isBlank(ip)) {
			return 0L;
		}
		return parseIp(ip);
	}

	/**
	 * <p>DEBUG 日志 cookie 对象数据</p>
	 *
	 * @param cookie cookie 对象
	 * @param log 日志记录器
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 11:26:33
	 */
	public static void logDebugCookie(Cookie cookie, Log log) {
		if (!log.isDebugEnabled()) {
			return;
		}
		log.debug("cookie name: [" + cookie.getName() + "], value: [" + cookie.getValue() + "], domain: ["
				+ cookie.getDomain() + "], path: [" + cookie.getPath() + "], maxAge: [" + cookie.getMaxAge()
				+ "], secure: [" + cookie.getSecure() + "], comment: [" + cookie.getComment() + "], version: ["
				+ cookie.getVersion() + "]");
	}

	public static void logDebugCookies(Cookie[] cookies, Log log) {
		if (log == null || !log.isDebugEnabled()) {
			return;
		}
		if (cookies == null || cookies.length == 0) {
			return;
		}
		StringBuilder sb = new StringBuilder("current has ").append(cookies.length).append("cookie(s)");
		for (int i = 0; i < cookies.length; i++) {
			sb.append('\n');
			sb.append("name: [").append(cookies[i].getName());
			sb.append("], value: [").append(cookies[i].getValue());
			sb.append("], domain: [").append(cookies[i].getDomain());
			sb.append("], path: [").append(cookies[i].getPath());
			sb.append("], maxAge: [").append(cookies[i].getMaxAge());
			sb.append("], secure: [").append(cookies[i].getSecure());
			sb.append("], comment: [").append(cookies[i].getComment());
			sb.append("], version: [").append(cookies[i].getVersion());
			sb.append(']');
		}
	}

	/**
	 * <p>检查指定 URL 是否属于 *.pinju.com 域</p>
	 *
	 * @param returnUrl 属于 *.pinju.com 域原值返回，否则返回品聚主站 URL
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 上午11:27:25
	 */
	public static String checkDomainReturnUrl(String returnUrl) {
		return checkDomainReturnUrl(returnUrl, PinjuConstant.PINJU_SERVER);
	}

	public static String checkDomainReturnUrl(String returnUrl, String defaultUrl) {
		return checkReturnUrl(returnUrl, defaultUrl);
	}

	public static String checkReturnUrl(String returnUrl, String defaultValue) {
		return isPinjuUrl(returnUrl) ? returnUrl : defaultValue;
	}

	private static boolean isPinjuUrl(String pinjuUrl) {
		if (StringUtils.isBlank(pinjuUrl)) {
			log.debug("[isPinjuUrl], parameter pinjuUrl is null or empty");
			return false;
		}
		try {
			boolean result = new URL(pinjuUrl).getHost().toLowerCase().endsWith(SysConfig.PINJU_ROOT_DOMAIN);
			if (!result) {
				log.warn("[isPinjuUrl], parameter: [" + pinjuUrl + "] is not pinju domain url");
			}
			return result;
		} catch (Exception e) {
			log.warn("[isPinjuUrl], parse parameter: [" + pinjuUrl + "] cause exception, so the url is not pinju domain url");
			return false;
		}
	}

	/**
	 * <p>
	 * 处理返回 URL
	 * </p>
	 *
	 * @param returnUrl
	 *            需要返回的 URL
	 * @param encode
	 *            是否需要采用 URL 编码返回
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-8-3 上午11:30:52
	 */
	public static String processReturnUrl(String returnUrl) {
		returnUrl = processReturnUrl(returnUrl, PinjuConstant.PINJU_SERVER);
		return checkDomainReturnUrl(returnUrl);
	}

	public static String processReturnUrl(String returnUrl, String defaultUrl) {
		returnUrl = checkReturnUrl(returnUrl, defaultUrl);
		return checkDomainReturnUrl(returnUrl, defaultUrl);
	}

	/**
	 * <p>
	 * 获取应用服务器的 HTTP 监听端口号
	 * </p>
	 *
	 * @param request
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static int getServerPort(HttpServletRequest request) {
		if (port == -1) {
			port = request.getServerPort();
		}
		return port;
	}

	public static int getServerPort() {
		return getServerPort( getRequest() );
	}

	/**
	 * <p>获取 HTTP 请求头中的 Referer 信息</p>
	 *
	 * @return  HTTP header Referer 信息，无法获取时返回 null 值
	 *
	 * @author gaobaowen
	 * @since 2011-11-23 16:48:49
	 */
	public static String getHttpReferer() {
		return getRequest().getHeader("Referer");
	}

	/**
	 * <p>设置浏览器的缓存时间。设置 HTTP 头中的 Cache-Control 的 max-age 值。</p>
	 *
	 * @param seconds 缓存的秒数，参数值小于 1 时会被忽略
	 *
	 * @author gaobaowen
	 * @since 2011-12-1 10:05:47
	 */
	public static void maxAge(int seconds) {
		if (seconds < 1) {
			return;
		}
		getResponse().addHeader("Cache-control", "max-age=" + seconds);
	}

	/**
	 * <p>HTTP 请求的 Referer 是否属于 pinju 域下的 Referer</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-11-23 16:49:31
	 */
	public static boolean isPinjuReferer() {
		return isPinjuUrl( getHttpReferer() );
	}

	/**
	 * <p>
	 * 获取当前请求的 URL 地址
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static String getRequestUrl() {
		HttpServletRequest request = getRequest();
		StringBuffer buffer = request.getRequestURL();
		if (!Tools.isBlank(request.getQueryString())) {
			buffer.append('?').append(request.getQueryString());
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * 设置当前页面的 URL，用于参数中的 returnUrl 值
	 * </p>
	 *
	 * @author gaobaowen
	 */
	public static void loginCurrentResultUrl() {
		setReturnUrl( getRequestUrl() );
	}

	/**
	 * <p>
	 * 设置当前页面的 URL，用于参数中的 returnUrl 值
	 * </p>
	 *
	 * @author gaobaowen
	 */
	public static void setReturnUrl(String returnUrl) {
		ActionContext.getContext().getValueStack().set("returnUrl", returnUrl);
	}

	public static void loginReturnUrl(String returnUrl) {
		setReturnUrl(returnUrl);
	}

	public static void loginReturnBaseUrl(String returnUrl) {
		setReturnUrl(PinjuConstant.PINJU_SERVER + returnUrl);
	}

	public static void logActionContext(Log log, boolean output) {
		if (!output) {
			return;
		}
		try {
			for (Map.Entry<String, Object> entry : ActionContext.getContext().getContextMap().entrySet()) {
				log.debug(new Formatter().format("%-55s : %-60s : %s", entry.getKey(), entry.getValue().getClass()
						.getName(), entry.getValue()));
			}
		} catch (Exception e) {
			log.error("logActionContext error", e);
		}
	}

	/**
	 * <p>
	 * 从 HTTP Header 的 X-Forward-IP 头中截取客户端连接 IP 地址。如果经过多次反向代理， 在 X-Forward-IP
	 * 中获得的是以“,&lt;SP&gt;”分隔 IP 地址链，第一段为客户端 IP 地址。
	 * </p>
	 *
	 * @param xforwardIp
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-8-11 09:20:13
	 */
	private static String getRemoteIpFromForward(String xforwardIp) {
		int commaOffset = xforwardIp.indexOf(',');
		if (commaOffset < 0) {
			return xforwardIp;
		}
		return xforwardIp.substring(0, commaOffset);
	}

	public static String getActionContextContent() {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, Object> entry : ActionContext.getContext().getContextMap().entrySet()) {
			Object value = entry.getValue();
			builder.append(new Formatter().format("%-55s : %-60s : %s", entry.getKey(), value.getClass().getName(), value)).append("\n");
		}
		return builder.toString();
	}

	/**
	 * <p>
	 * 将 IP 地址转换成为 long 类型数字
	 * </p>
	 *
	 * @param ip
	 *            以“.”分隔 4 段的 IPv4 地址
	 * @return 表示 IP 地址的 long 类型数值
	 *
	 * @author gaobaowen
	 * @since 2011-8-17 15:26:07
	 */
	public static long parseIp(String ip) {
		if (ip == null || ip.length() == 0) {
			return -1;
		}
		char[] chs = ip.toCharArray();
		long t = 0;
		int n = 0;
		for (int i = 0, k = 0; i < chs.length; i++) {
			if (chs[i] == '.') {
				if (k++ > 2) {
					break;
				}
				t = (t << 8) | (n & 0xff);
				n = 0;
				continue;
			}
			if (chs[i] >= '0' && chs[i] <= '9') {
				n = n * 10 + (chs[i] - '0');
				continue;
			}
			break;
		}
		t = (t << 8) | (n & 0xff);
		return t & 0xffffffffL;
	}

	/**
	 * <p>将数值的 IPv4 地址转换成为四段的 IPv4 地址</p>
	 *
	 * @param numberIp IPv4 地址的数值形式
	 * @return 四段格式的 IPv4 地址。若参数中的数值小于 0 或者大于 0xffffffffL 时将返回 null 值
	 *
	 * @author gaobaowen
	 * @since 2011-10-27 13:48:55
	 */
	public static String toIPv4(long numberIp) {
		if (numberIp < 0 || numberIp > 0xffffffffL) {
			return null;
		}
		return ((numberIp >>> 24) & 0xff) + "." +
			   ((numberIp >>> 16) & 0xff) + "." +
			   ((numberIp >>>  8) & 0xff) + "." +
			   (numberIp & 0xff);
	}

	private static String formatHeaderDate(Date date) {
		if (date == null) {
			return null;
		}
		return HTTP_HEADER_DATE_FORMAT.get().format(date);
	}
}
