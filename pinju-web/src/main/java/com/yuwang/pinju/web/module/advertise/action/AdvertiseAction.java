package com.yuwang.pinju.web.module.advertise.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.web.cookie.manager.AdvertiseCookieManager;
import com.yuwang.pinju.web.module.BaseAction;

public class AdvertiseAction extends BaseAction {
	private static Log log = LogFactory.getLog(AdvertiseAction.class);

	/**
	 * 品聚主页
	 */
	private static final String HOME_PAGE = "http://www.pinju.com/";

	/**
	 * 每天时长
	 */
	private static final long SECOND_PER_DAY = 24 * 60 * 60 * 1000;

	/**
	 * 推广商信息
	 */
	private String p;

	/**
	 * 推广链接URL
	 */
	private String i;

	private String redirectUrl;

	/**
	 * 1、首先取出cookie，判断是否变动，有则不动cookie，没有新增cookie 2、推广商信息丢失，仍继续跳转，但不种cookie
	 * 3、跳转链接无效，仍进行跳转 4、没有跳转链接，转向首页
	 */
	@Override
	public String execute() throws Exception {
		log.debug("推广商信息: " + p + ", 推广链接URL: " + i);
		// 1、没有跳转链接，转向首页 ；2、 推广商信息丢失，仍继续跳转，但不种cookie
		if (p == null) {
			redirectUrl = (i == null) ? HOME_PAGE : i;
			return SUCCESS;
		}
		// 读取cookie.PJ_AD_POP
		String pCookie = AdvertiseCookieManager.getAdvertiseCookie();
		log.debug("原Cookie中信息：" + pCookie);
		String info = "";
		// 推广商信息存在且和当前的信息相似并有效，判断cookie有效期（15天）
		if (pCookie != null && pCookie.startsWith(p)) {
			if (pCookie.startsWith(p.concat("-1"))) {
				long ptCookie = getAdvertiseCookieTime(pCookie);
				long now = new Date().getTime();
				Long period = (now - ptCookie) / SECOND_PER_DAY;
				// 如果超过15天，修改cookie状态（计算不精确）
				if (period.intValue() > 15) {
					AdvertiseCookieManager.writeAdvertise(pCookie.replace("-1",
							"-0"));
					info = p.concat("-0");
				} else {
					info = p.concat("-1");
				}
			} else {
				info = p.concat("-0");
			}
		} else {
			// 推广商信息不存在，写入cookie(状态为1、时间为系统当前时间)
			StringBuffer cookie = new StringBuffer().append(p).append("-1")
					.append("-").append(new Date().getTime());
			AdvertiseCookieManager.writeAdvertise(cookie.toString());
			info = p.concat("-1");
		}
		// 处理推广链接URL
		redirectUrl = processReturnUrl(i, info);
		return SUCCESS;
	}

	private long getAdvertiseCookieTime(String cookie) {
		String time = cookie.substring(cookie.lastIndexOf("-") + 1);
		if ("".equals(time)) {
			log.warn("原Cookie中无时间值");
		} else {
			try {
				return Long.valueOf(time);
			} catch (NumberFormatException e) {
				log.error("转换Cookie时间值异常：", e);
			}
		}
		return 0;
	}

	private String processReturnUrl(String url, String info) {
		// 如果地址为空，跳转到首页
		if (url == null) {
			return HOME_PAGE;
		}
		String result = "";
		if (url.contains("?")) {
			result = url.trim().concat("&p=").concat(info);
		} else {
			result = url.trim().concat("?p=").concat(info);
		}
		return result;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
