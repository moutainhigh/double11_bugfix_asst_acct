package com.yuwang.pinju.web.system;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yuwang.pinju.core.constant.system.PinjuConstant;

/**
 * <p>系统常量配置</p>
 *
 * @author gaobaowen
 * 2011-6-1 下午04:45:41
 */
public interface SysConfig {

	/**
	 * Struts 2 后缀
	 */
	final String ACTION_SUFFIX = ".htm";

	/**
	 * 盛大 SDO 退出请求 URL
	 */
	final String SDO_LOGOUT_URL = "https://cas.sdo.com/cas/logout?url=";

	/**
	 * pinju 网 Cookie 根域名
	 */
	final String PINJU_ROOT_DOMAIN = Util.getBaseDomain(PinjuConstant.PINJU_SERVER);

	/**
	 * pinju www 服务根路径
	 */
	final String PINJU_WWW_MAIN_PAGE = "/";

	/**
	 * pinju www 主页
	 */
	final String PINJU_WWW_MAIN = PinjuConstant.PINJU_SERVER + PINJU_WWW_MAIN_PAGE;

	/**
	 * pinju 登录 URL
	 */
	final String PINJU_LOGIN_URL = PinjuConstant.PINJU_SERVER + "/member/login" + ACTION_SUFFIX;

	public static class Util {
		private static String getBaseDomain(String domain) {
			Pattern p = Pattern.compile("https?://[^./]+(\\.[^./]+\\.[^./]+)");
			Matcher matcher = p.matcher(domain);
			if (matcher.find()) {
				return matcher.group(1).toLowerCase();
			}
			return ".pinju.com";
		}
	}
}
