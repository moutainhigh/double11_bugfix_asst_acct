package com.yuwang.pinju.web.module.ajax.member;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.Tools;
import com.yuwang.pinju.core.citystation.ao.CityStationAO;
import com.yuwang.pinju.core.util.PingYinUtil;
import com.yuwang.pinju.domain.citystation.CityStationDO;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.system.SysConfig;

public class CityStationValidator implements PinjuAction {
	private static Log log = LogFactory.getLog(CityStationValidator.class);

	private final static Pattern RETURN_URL_PATTERN = Pattern
			.compile("https?://[a-z0-9-]{1,63}"
					+ SysConfig.PINJU_ROOT_DOMAIN.replace(".", "\\.")
					+ "(?:[/?].*)?");
	private CityStationAO cityStationAO;
	
	private String callBack;
	
	private InputStream inputStream;

	/**
	 * 点击城市分站，弹出选择对话框
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAllCityStations() throws Exception {
		if (checkHttpReferer()) {
			return response(callBack + "([])");
		}
		List<CityStationDO> cityStationList = cityStationAO
				.getAllCityStations();
		String json = "";
		if (cityStationList != null && cityStationList.size() > 0) {
			json = parseCityStationList(cityStationList);
		}
		String result = callBack + "(" + json + ")";
		return response(result);
	}
	
	private boolean checkHttpReferer(){
		String referer = ServletUtil.getHttpReferer();
		if (log.isDebugEnabled()) {
			log.debug("HTTP Referer : " + referer);
		}
		return Tools.isBlank(referer)
				|| !RETURN_URL_PATTERN.matcher(referer.toLowerCase()).matches();
	}

	private String parseCityStationList(List<CityStationDO> cityStationList) {
		JSONArray array = new JSONArray();
		Map<String, String> cityStationMap = new HashMap<String, String>();
		for (CityStationDO cityStation : cityStationList) {
			cityStationMap.put("status", cityStation.getStatus());
			cityStationMap.put("cityName", cityStation.getCityName());
			cityStationMap.put("cityUrl", cityStation.getCityUrl());
			array.add(cityStationMap);
		}
		return array.toString();
	}

	/**
	 * 进入首页后，根据IP异步判断分站
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getCityStationInfo() throws Exception {
		if (checkHttpReferer()) {
			return response(callBack + "([])");
		}

		String remoteIp = ServletUtil.getRemoteIp();
		if (log.isDebugEnabled()) {
			log.debug("Customer IP Address : " + remoteIp);
		}
		CityStationDO cityStationDO = cityStationAO
				.getCityStationInfo(remoteIp);
		// 存在城市分站信息，并且已开通
		String json = "";
		if (cityStationDO != null && "1".equals(cityStationDO.getStatus())) {
			if (log.isDebugEnabled()) {
				log.debug("Customer mapped City Substation : " + cityStationDO.getCityName());
			}
			Map<String, String> cityStationMap = new HashMap<String, String>();
			cityStationMap.put("cityName", cityStationDO.getCityName());
			if(cityStationDO.getCityName() != null || !"".equals(cityStationDO.getCityName())){
				cityStationMap.put("cityImage", PingYinUtil.getPinYinHeadChar(cityStationDO.getCityName()));
			}
			cityStationMap.put("cityUrl", cityStationDO.getCityUrl());
			cityStationMap.put("status", cityStationDO.getStatus());
			json = JSONObject.fromObject(cityStationMap).toString();
		}
		String result = callBack + "(" + json + ")";
		return response(result);
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

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCityStationAO(CityStationAO cityStationAO) {
		this.cityStationAO = cityStationAO;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

}
