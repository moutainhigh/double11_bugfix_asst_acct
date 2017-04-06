package com.yuwang.pinju.web.cookie.manager;

import static com.yuwang.pinju.web.cookie.PinjuCookieName.PJ_AD_POP;
import static com.yuwang.pinju.web.cookie.PinjuCookieName.PJ_AD_POP_INFO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.cookie.CookieManager;
import com.yuwang.cookie.CookieMapping;
import com.yuwang.cookie.rw.CookieReader;
import com.yuwang.cookie.rw.CookieWriter;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;

public class AdvertiseCookieManager {
	private final static Log log = LogFactory
			.getLog(AdvertiseCookieManager.class);

	private AdvertiseCookieManager() {
	}

	public static void writeAdvertise(String info) {
		CookieWriter writer = CookieManager
				.newCookieWriter(PJ_AD_POP);
		writer.addCookie(PJ_AD_POP_INFO, info);
		/*if(info.contains("-1"))
			writer.addCookie(PJ_AD_POP_TIME, new Date().getTime());*/
		writer.write(ServletActionContext.getResponse());
	}
	
	public static String getAdvertiseCookie(){
		CookieReader reader = CookieManager.newCookieReader(PJ_AD_POP);
		CookieMapping mapping = PinjuCookieManager.getCookieMapping();
		if (!reader.readCookies(mapping, ServletActionContext.getResponse())) {
			log.warn("read advertise cause error, cannot read advertise cookies value");
			return "";
		}else{
			return (String) reader.getOriginalValue(PJ_AD_POP_INFO);
		}
	}
	
	/*public static Long getAdvertiseCookieTime(){
		CookieReader reader = CookieManager.newCookieReader(PJ_AD_POP);
		CookieMapping mapping = PinjuCookieManager.getCookieMapping();
		if (!reader.readCookies(mapping, ServletActionContext.getResponse())) {
			log.warn("read advertise cause error, cannot read advertise cookies value");
			return 0L;
		}else{
			return (Long) reader.getOriginalValue(PJ_AD_POP_TIME);
		}
	}*/
}
