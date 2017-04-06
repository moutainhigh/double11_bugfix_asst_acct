package com.yuwang.pinju.web.module.ajax.member;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.Tools;
import com.yuwang.pinju.domain.ajax.TipsInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.system.SysConfig;

public class LoginValidator implements PinjuAction {
	private final static Log log = LogFactory.getLog(LoginValidator.class);
	
	private final static Pattern RETURN_URL_PATTERN = Pattern.compile("https?://[a-z0-9-]{1,63}" + SysConfig.PINJU_ROOT_DOMAIN.replace(".", "\\.") + "(?:[/?].*)?");
	/**
	 * 返回信息
	 
	private TipsInfoDO tipsInfo = new TipsInfoDO();*/
	
	private String callBack;
	
	private InputStream inputStream;
	
	@Override
	public String execute() throws Exception {
		String referer = ServletUtil.getHttpReferer();
		log.debug("HTTP Referer : " + referer);
		
		TipsInfoDO tipsInfo = new TipsInfoDO();
		if (Tools.isBlank(referer) || !RETURN_URL_PATTERN.matcher(referer.toLowerCase()).matches()) {
			tipsInfo.setLogin(false);
		}else{
			long memberId = 0;
			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			if (login.isLogin()) {
				memberId = login.getMemberId();
				if(log.isDebugEnabled()){
					log.debug("Member has Login : " + memberId);
				}
				tipsInfo.setLogin(true);
			} else {
				tipsInfo.setLogin(false);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("login", tipsInfo.isLogin());
		String result = callBack + "(" + JSONObject.fromObject(map).toString() + ")";
		return response(result);
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	private String response(String responseData) {
		try {
			inputStream = new ByteArrayInputStream(responseData
					.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return SUCCESS;
	}
}
