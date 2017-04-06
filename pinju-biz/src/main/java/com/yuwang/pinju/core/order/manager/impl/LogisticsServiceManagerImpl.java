package com.yuwang.pinju.core.order.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.order.manager.LogisticsServiceManager;
import com.yuwang.pinju.core.order.manager.impl.httpLogsisticsServce.LogisticsServiceHundDO;
import com.yuwang.pinju.core.order.manager.impl.httpLogsisticsServce.ServiceHttpLogistics;
import com.yuwang.pinju.domain.order.LogisticsServiceDO;
public class LogisticsServiceManagerImpl implements LogisticsServiceManager {

	private final static Log log = LogFactory.getLog(LogisticsServiceManagerImpl.class);
	private final static int resulttype = 1;
	private ServiceHttpLogistics serviceSoapLogistics;
	private ServiceHttpLogistics serviceHundredHttpProxy;
	
	/**
	 * 查询物流信息  （通过WebService）
	 * @param billcode 订单号
	 * @param express  快递公司代码
	 * @return 物流信息实体
	 */
	public LogisticsServiceDO getTrackBybillcode(String billcode,String express){
		LogisticsServiceDO ldo = new LogisticsServiceDO();
		String xml = "";
		try {
			xml = serviceSoapLogistics.getTrackBybillcode(billcode, express, resulttype);
			if(StringUtils.isNotBlank(xml)){
				ldo = serviceSoapLogistics.xmlToBean(xml,LogisticsServiceDO.class);
			}else{
				ldo.setResult("false");
				ldo.setResultInfo("调用物流接口超时!");
			}
		} catch (Exception e) {
			log.error("LogisticsServiceManagerImpl error, " + xml +  e);
			ldo.setResult("false");
			ldo.setResultInfo("调用物流接口错误!");
		}
		return ldo;
	}

	public void setServiceSoapLogistics(ServiceHttpLogistics serviceSoapLogistics) {
		this.serviceSoapLogistics = serviceSoapLogistics;
	}
	
	public void setServiceHundredHttpProxy(
			ServiceHttpLogistics serviceHundredHttpProxy) {
		this.serviceHundredHttpProxy = serviceHundredHttpProxy;
	}

	@Override
	public LogisticsServiceDO getTrackBybillcode(String billcode,
			String express, String express100) {
		LogisticsServiceDO ldo = new LogisticsServiceDO();
		String xml = "";
		try {
			xml = serviceSoapLogistics.getTrackBybillcode(billcode, express, resulttype);
			if(StringUtils.isNotBlank(xml)){
				ldo = serviceSoapLogistics.xmlToBean(xml,LogisticsServiceDO.class);
				if(ldo.isSuccess()){
					return ldo;
				}else{
					return this.getTrackBybillcodeForHund(ldo, billcode, express100);
				}
			}else{
				return this.getTrackBybillcodeForHund(ldo, billcode, express100);
			}
		} catch (Exception e) {
			log.error("LogisticsServiceManagerImpl.getTrackBybillcode() error, " + xml +  e);
			ldo.setResult("false");
			ldo.setResultInfo("系统繁忙，请稍后再试，或使用物流公司官网查询");
		}
		return ldo; 
	}
	
	/**
	 * <p>Description: 快递100 查询物流</p>
	 * @param ldo
	 * @param billcode
	 * @param express100
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-2
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private LogisticsServiceDO getTrackBybillcodeForHund(LogisticsServiceDO ldo,String billcode,String express100){
		String xml = "";
		try {
			xml = serviceHundredHttpProxy.getTrackBybillcode(billcode,express100, resulttype);
			if (StringUtils.isNotBlank(xml)) {
				LogisticsServiceHundDO ldo100 = new LogisticsServiceHundDO();
				ldo100 = serviceHundredHttpProxy.xmlToBean(xml,LogisticsServiceHundDO.class);
				//把ldo100 转化成 LogisticsServiceDO
				ldo = this.chanagehundDoToLogDO(ldo, ldo100);
				return ldo;
			} else {
				ldo.setResult("false");
				ldo.setResultInfo("系统繁忙，请稍后再试，或使用物流公司官网查询");
				return ldo;
			}
		} catch (Exception e) {
			log.error("LogisticsServiceManagerImpl.getTrackBybillcodeForHund() error, " + xml +  e);
			ldo.setResult("false");
			ldo.setResultInfo("系统繁忙，请稍后再试，或使用物流公司官网查询");
		}
		return ldo;
	}
	
	/**
	 * <p>Description: 物流实体对象转化</p>
	 * @param ldo		快递宝 物流实体
	 * @param ldo100	快递100 物流实体
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-2
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private LogisticsServiceDO chanagehundDoToLogDO(LogisticsServiceDO ldo,LogisticsServiceHundDO ldo100){
		ldo.setResultInfo(ldo100.getMessage());
		List<HashMap<String, String>> _step = new ArrayList<HashMap<String, String>>();
		if(ldo100.getStep() != null && ldo100.getStep().size() > 0){
			for(HashMap<String, String> step: ldo100.getStep() ){
				HashMap<String, String> steps = new HashMap<String, String>();
				steps.put("datetime", step.get("time"));
				steps.put("remark", step.get("context"));
				_step.add(steps);
			}
			ldo.setStep(_step);
		}
		
		if(StringUtils.isNotBlank(ldo100.getStatus())){
			if(ldo100.getStatus().compareTo("1")!=0){
				ldo.setResult("false");
			}else{
				ldo.setResult("true");
			}
		}else{
			ldo.setResult("false");
		}
		return ldo;
	}
	
}
