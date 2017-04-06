/**
 * 
 */
package com.yuwang.pinju.web.module.item.action;

import java.util.Map;
import java.util.Map.Entry;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.web.module.BaseAction;

/**  
 * @Project: pinju-web
 * @Title: GetLogisticJson.java
 * @Package com.yuwang.pinju.web.module.item.action
 * @Description: 异步获取运费模板信息,保证商品详情先出来
 * @author liuboen liuboen@zba.com
 * @date 2011-8-16 下午06:42:31
 * @version V1.0  
 */

public class GetLogisticJson extends BaseAction {
	private ItemDetailAO itemDetailAO;
	String cityName;
	String logisticsStr;
	Boolean sucess;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3961750864502418813L;

	public String execute(){
		sucess=false;
		long freeTemplateId=getLong("templateId");
		Map<String, String>  logisticsMap=null;
		//获取-[地区物流信息]
		String cityIp=this.ipAddr();
		//填充式ip获取
		String getIp=getString("ip");
		if (getIp!=null) {
			cityIp=getIp;
		}
		logisticsMap=itemDetailAO.getLogisticsCityByIp(freeTemplateId, cityIp);
		if (logisticsMap!=null) {
			sucess=true;
			cityName=logisticsMap.get("cityName");
			logisticsStr=mapToString(logisticsMap);
		}
		return SUCCESS;
	}

public  String ipAddr() {
    String ip = request.getHeader("x-forwarded-for");
    /*Enumeration test=request.getHeaderNames();
    while (test.hasMoreElements()) {
		Object object = (Object) test.nextElement();
		String headName=object.toString();
		System.out.println(object.toString()+":"+request.getHeader(headName));
	}*/
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
    }
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
    }
    return ip;
}

/**
 * 转换物流信息到输出
 * @param logisticsMap
 * @return
 */
public String mapToString(Map<String, String>  logisticsMap){
	String tempStr="";
	try {
		for (Entry<String, String> entry: logisticsMap.entrySet()) {
			String innerTemp="";
			if (entry.getKey().equals("1")) {
				innerTemp="平邮:";
			}else if(entry.getKey().equals("2")){
				innerTemp="快递:";
			}else if(entry.getKey().equals("3")){
				innerTemp="EMS:";
			}
			if (!entry.getKey().equals("cityName")&&entry.getValue()!=null&&!entry.getValue().equals("0")) {
				tempStr=tempStr+innerTemp+MoneyUtil.getCentToDollar(Long.parseLong(entry.getValue()))+"元　";
			}
		}
		
	} catch (Exception e) {
		System.out.println(e);
	}
	
	return tempStr;
}

/**
 * @param itemDetailAO the itemDetailAO to set
 */
public void setItemDetailAO(ItemDetailAO itemDetailAO) {
	this.itemDetailAO = itemDetailAO;
}

/**
 * @return the cityName
 */
public String getCityName() {
	return cityName;
}

/**
 * @return the logisticsStr
 */
public String getLogisticsStr() {
	return logisticsStr;
}

/**
 * @return the sucess
 */
public Boolean getSucess() {
	return sucess;
}



}
