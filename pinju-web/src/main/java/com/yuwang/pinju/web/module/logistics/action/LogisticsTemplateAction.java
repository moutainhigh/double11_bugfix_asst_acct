package com.yuwang.pinju.web.module.logistics.action;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.logistics.ao.LogisticsAreaCarriageAO;
import com.yuwang.pinju.core.logistics.ao.LogisticsTemplateAO;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateQuery;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;


/**
 * <p>创建，修改物流运费模板</p>
 * 
 * @author shihongbo
 * @since 2011-07-11
 */          
public class LogisticsTemplateAction implements Action,ServletResponseAware{
	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(LogisticsTemplateAction.class);
	
	//物流模板Id
	private Long templateId = null;

	//物流类型--平邮
	private Integer logisticsTypePost = null;
	
	//物流类型--快递
	private Integer logisticsTypeCourier = null;
	
	//物流类型--EMS
	private Integer logisticsTypeEMS = null;
	
	//默认物流费用--平邮
	private String postFee;
	
	//默认物流费用--快递
	private String courierFee;

	//默认物流费用--EMS
	private String emsFee;
	
	//增加物流费用--平邮
	private String postFeeIncrease;
	
	//增加物流费用--快递
	private String courierFeeIncrease;
	
	//增加物流费用--EMS
	private String emsFeeIncrease;
	
	//模板名称
	private String templateName;

	//模板说明
	private String templateMemo;
	
	//为指定地区设置运费
    private String tempinfor;
    
    //加载地区运费
    private Map<String, List<LogisticsTemplateVO>> tempDetailMap;

    //重复模板数
    Integer templateCount = 0;
    
    //判断是新增还是修改
    private String isTempName;
    
	private LogisticsTemplateAO logisticsTemplateAO;
	
    private LogisticsAreaCarriageAO logisticsAreaCarriageAO;
    
    private HttpServletResponse response;
    
    private MemberAsstLog memberAsstLog;

	@AssistantPermission(target = "trade", action = "logistics")
	public String execute() {
		//给费用赋值，用于页面显示
		postFee = "0.01";
		courierFee = "0.01";
		emsFee = "0.01";
		postFeeIncrease="0.00";
		courierFeeIncrease="0.00";
		emsFeeIncrease="0.00";
		return INPUT;
	}
	
	/**
	 * 保存物流模板信息
	 */
    @AssistantPermission(target = "trade", action = "logistics")
	public String editTemplate(){
		loadTemplateById();
		return SUCCESS;
	}
	
	/**
	 * 删除物流模板信息
	 */
    @AssistantPermission(target = "trade", action = "logistics")
	public String deleteTemplate(){
		if(templateId == null){
			log.error("Event=[LogisticsTemplateAction#deleteTemplate] 删除模板id为空");
			return ERROR;
		}
		
		//删除物流模板前先判断物流模板是否被引用
		if(!logisticsTemplateAO.checkLogisticsTemplate(templateId)){
			LogisticsTemplateDO  logisticsTemplateDO = logisticsTemplateAO.loadLogisticsTemplate(templateId);
			Long sellerId = CookieLoginInfo.getCookieLoginInfo().getMasterMemberId();
			logisticsTemplateAO.deleteLogisticsTemplate(templateId, sellerId);
			
			//记录日志
			String message = "删除物流模板'" + logisticsTemplateDO.getTemplateName() + "'";
			memberAsstLog.log(message);
		}else{
			log.error("Event=[LogisticsTemplateAction#deleteTemplate] 删除物流模板前先判断物流模板是否被引用失败");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除物流模板前检测当前物流模板是否被引用
	 */
	public void checkLogisticsTemplate() throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/htmt;charset=utf-8");
		
		if(logisticsTemplateAO.checkLogisticsTemplate(templateId)){
			response.getWriter().print("ok");
		}
	}
	
	/**
	 * 添加运费模板之前检测模板名称是否存在(防止同名的运费模板,Ajax异步调用)
	 * @throws IOException 
	 */
	public void checkTempName() throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/htmt;charset=utf-8");
		//检测用户名的长度是否小于25个字符
		int count = StringUtil.getStringLength(templateName);
		if(count>50){
			response.getWriter().print("运费模板名称不能超过25个字");
			return;
		}
		
		//检测模板名称重复
		LogisticsTemplateQuery logisticsTemplateQuery = new LogisticsTemplateQuery();
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		logisticsTemplateQuery.setTemplateName(templateName);
		logisticsTemplateQuery.setSellerId(login.getMasterMemberId());
		templateCount = logisticsTemplateAO.getLogisTicsTemplateCount(logisticsTemplateQuery);
		
		if(templateCount > 0){
			response.getWriter().print("运费模板名称不可重复");
		}
	}
	
	
	/**
	 * 保存物流模板信息
	*/
	@AssistantPermission(target = "trade", action = "logistics")
	public String saveTemplate(){
		//保存模板信息
		LogisticsTemplateDO template = new LogisticsTemplateDO();
		
		Date now = new Date();
		
		if(isTempName.equals("") || isTempName==""){
			template.setGmtCreate(now);
			template.setGmtModified(now);
		}else{
			template.setGmtModified(now);
		}
	
		template.setGmtModified(now);
		
		
		template.setMemo(templateMemo);
		
		template.setState(1);
		
		template.setTemplateName(templateName);
		
		Long masterMemberId = CookieLoginInfo.getCookieLoginInfo().getMasterMemberId();
		template.setSellerId(masterMemberId);
		
		template.setId(templateId);
		
		//验证
		ActionInvokeResult invoke = new ActionInvokeResult(template);
		if(!invoke.validate()) {
			log.warn("bean validation failed, data: " + template);
			return INPUT;
		}
		
		
		Long newTemplateId = logisticsTemplateAO.saveLogisticsTemplate(template);
		
		
		//默认运费和地区运费--先删除后添加
		logisticsTemplateAO.deleteCarriageByTemplate(newTemplateId, masterMemberId);
		
		
		//保存默认运费信息
		if(logisticsTypePost != null){
			addDefaultCarriage(newTemplateId, logisticsTypePost, postFee, postFeeIncrease);
		}
		
		if(logisticsTypeCourier != null){
			addDefaultCarriage(newTemplateId, logisticsTypeCourier, courierFee, courierFeeIncrease);	
		}
		
		if(logisticsTypeEMS != null){
			addDefaultCarriage(newTemplateId, logisticsTypeEMS, emsFee, emsFeeIncrease);
		}
		
		//添加 为指定地区设置运费信息
		addLogisticsAreaCarriage(newTemplateId);
		
		//记录日志
		String message = "";
		if(templateId == null){
			message = "新增物流模板'" + templateName + "'";
		}else {
			message = "修改物流模板'" + templateName + "'";
		}
		
		memberAsstLog.log(message);
		
		return SUCCESS;
	}
	

	/**
	 * 添加默认运费
	 * 
	 * @param templateId 模板编号
	 * @param logisticsType 物流方式
	 * @param fee 默认运费
	 * @param feeIncrease 增加运费
	 * 
	 * @return 
	 */
	private void addDefaultCarriage(Long templateId, Integer logisticsType, String fee, String feeIncrease){
		LogisticsCarriageDO logisticsCarriageDO = new LogisticsCarriageDO();
		logisticsCarriageDO.setTemplateId(templateId);
		logisticsCarriageDO.setCarriageIncrease(new Money(feeIncrease).getCent());
		logisticsCarriageDO.setDefaultCarriage(new Money(fee).getCent());
		logisticsCarriageDO.setLogisticsTypeId(logisticsType);
		
		logisticsCarriageDO.setLogisticsTypeName(LogisticsCarriageDO.getLogisticsTypeDisplay(logisticsType));
		
		Date now = new Date();
		logisticsCarriageDO.setGmtCreate(now);
		logisticsCarriageDO.setGmtModified(now);
		logisticsCarriageDO.setGmtModified(now);

		logisticsTemplateAO.addDefaultCarriage(logisticsCarriageDO);
		
	}
	
	/**
	 * 根据模板id加载数据
	 * 
	 * @param 
	 * 
	 * @return 
	 */
	private void loadTemplateById(){

		if(templateId == null)
			return;
		
		//加载模板
		LogisticsTemplateDO template = logisticsTemplateAO.loadLogisticsTemplate(templateId);
		
		templateName = template.getTemplateName();
		templateMemo = template.getMemo();

		
		//加载默认运费
		List<LogisticsCarriageDO> carriageList = logisticsTemplateAO.loadLogisticsCarriage(templateId);

		for(LogisticsCarriageDO carriage:carriageList){

			//平邮
			if(carriage.getLogisticsTypeId().equals(LogisticsCarriageDO.LOGISTICS_TYPE_POST)){
				logisticsTypePost = carriage.getLogisticsTypeId();
				postFee = MoneyUtil.getCentToDollar(carriage.getDefaultCarriage());
				postFeeIncrease = MoneyUtil.getCentToDollar(carriage.getCarriageIncrease());
				
			//快递
			}else if(carriage.getLogisticsTypeId().equals(LogisticsCarriageDO.LOGISTICS_TYPE_COURIER)){
				logisticsTypeCourier = carriage.getLogisticsTypeId();
				courierFee = MoneyUtil.getCentToDollar(carriage.getDefaultCarriage());
				courierFeeIncrease = MoneyUtil.getCentToDollar(carriage.getCarriageIncrease());
				
			//EMS
			}else if(carriage.getLogisticsTypeId().equals(LogisticsCarriageDO.LOGISTICS_TYPE_EMS)){
				logisticsTypeEMS = carriage.getLogisticsTypeId();
				emsFee = MoneyUtil.getCentToDollar(carriage.getDefaultCarriage());
				emsFeeIncrease = MoneyUtil.getCentToDollar(carriage.getCarriageIncrease());
			}
		}
		
		//根据模板ID加载所有指定地区运费
		getTempByTemplateId();
		
	}
	
	/**
	 * 根据模板ID加载所有指定地区运费
	 */
	private void getTempByTemplateId() {
		//加载地区运费(根据模板ID)
		List<LogisticsAreaCarriageDO> areaCarriagelist = logisticsTemplateAO.loadAreaCarriageByTemplate(templateId);
		
		List<LogisticsTemplateVO> list = new LinkedList<LogisticsTemplateVO>();
		
		tempDetailMap = new HashMap<String, List<LogisticsTemplateVO>>();
		
		if(areaCarriagelist != null){
			//合并相同地区运费
			Map<AreaCarriageVO, String> map = new TreeMap<AreaCarriageVO, String>();
			
			for(LogisticsAreaCarriageDO areaCarriage:areaCarriagelist){
				AreaCarriageVO areaCarriageVO = new AreaCarriageVO();
				areaCarriageVO.setAreaName(areaCarriage.getAreaName());			             //地区名称
				areaCarriageVO.setDefaultCarriage(areaCarriage.getAreaCarriage());			 //地区运费
				areaCarriageVO.setCarriageIncrease(areaCarriage.getAreaCarriageIncrease());  //增加商品运费
				areaCarriageVO.setLogisticsTypeId(areaCarriage.getLogisticsTypeId());        //物流方式id
				areaCarriageVO.setLogisticsTypeName(LogisticsCarriageDO.getLogisticsTypeDisplay(areaCarriage.getLogisticsTypeId())); //物流方式名称
				areaCarriageVO.setAreaId(areaCarriage.getAreaId());                          //地区id
				
				String areaname = map.get(areaCarriageVO);
				if(areaname != null){
					areaname = areaname + "," + areaCarriageVO.getAreaId()+ "|" + areaCarriageVO.getAreaName();
				}else{
					areaname = areaCarriageVO.getAreaId()+ "|" +areaCarriageVO.getAreaName();
				}
				map.put(areaCarriageVO, areaname);
			}
			
			//地区运费 加入显示列表
			Iterator<Map.Entry<AreaCarriageVO,String>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry<AreaCarriageVO, String> entry = (Map.Entry<AreaCarriageVO, String>)iterator.next();
				AreaCarriageVO key = (AreaCarriageVO)entry.getKey();
				String value = (String)entry.getValue();
				
				LogisticsTemplateVO item = new LogisticsTemplateVO();
				item.setAreaName(value);
				item.setCarriageIncrease(MoneyUtil.getCentToDollar(key.getCarriageIncrease()));
				item.setDefaultCarriage(MoneyUtil.getCentToDollar(key.getDefaultCarriage()));
				item.setLogisticsTypeName(key.getLogisticsTypeName());
				item.setLogisticsTypeId(key.getLogisticsTypeId());
				item.setAreaId(key.getAreaId());
				
				list.add(item);
			}
		}
		tempDetailMap.put("key" + templateId, list);
	}
	
	/**
	 * 添加 为指定地区设定运费
	 */
	private void addLogisticsAreaCarriage(Long tempId){
		
		if(tempinfor.equals("")||tempinfor=="")
			return;
		
		//List<LogisticsAreaCarriageDO> list=new ArrayList<LogisticsAreaCarriageDO>();
		
		Map<AreaFee, LogisticsAreaCarriageDO> areaFeeMap = new HashMap<AreaFee, LogisticsAreaCarriageDO>();
		
		String[] temp = tempinfor.split(";");
		for (int i = 0; i < temp.length; i++) {
			String [] comminuteStr        = temp[i].split(":");
			String logisticsTypeId        = comminuteStr[0];           //物流方式id:	1：平邮  2：快递  3：EMS
			String areaName               = comminuteStr[1];		   //地区
			String areaCarriage           = comminuteStr[2];           //运费
			String areaCarriageIncrease   = comminuteStr[3];		   //每多加一件商品的运费
			
			String [] addres = areaName.split(",");                    //北京,天津,河北                     11|北京,12|天津,13|河北
			 
			//LogisticsAreaCarriageDO logisticsAreaCarriageDO = null;
			
			
			for (int j = 0; j < addres.length; j++) {
				String[] tmpCode = StringUtil.split(addres[j], "|");
				
				AreaFee areaFee = new AreaFee();
				areaFee.setAreaId(Integer.parseInt(tmpCode[0]));
				areaFee.setLogisticsTypeId(Integer.parseInt(logisticsTypeId));
				areaFee.setTemplateId(tempId);
				
				LogisticsAreaCarriageDO logisticsAreaCarriageDO = new LogisticsAreaCarriageDO();
				Date now = new Date();   //系统当前时间
				logisticsAreaCarriageDO.setLogisticsTypeId(Integer.parseInt(logisticsTypeId));	//物流方式id:	1：平邮  2：快递  3：EMS
				logisticsAreaCarriageDO.setAreaId(Integer.parseInt(tmpCode[0])); 				     //地区ID
				logisticsAreaCarriageDO.setTemplateId(tempId);           //模板ID
				logisticsAreaCarriageDO.setAreaCarriage(new Money(areaCarriage).getCent());                  //地区运费
				logisticsAreaCarriageDO.setAreaCarriageIncrease(new Money(areaCarriageIncrease).getCent());  //每多加一件商品的运费
				logisticsAreaCarriageDO.setGmtCreate(now);			     //记录创建时间
				logisticsAreaCarriageDO.setGmtModified(now);		     //记录修改时间
				logisticsAreaCarriageDO.setAreaName(tmpCode[1]);          //地区名称
				logisticsAreaCarriageDO.setType(Long.parseLong("1"));	 //1选择，2显示


				areaFeeMap.put(areaFee, logisticsAreaCarriageDO);
				//list.add(logisticsAreaCarriageDO);
				/*
				AreaFee areafee = new AreaFee();
				areafee.setLogisticsTypeId(Integer.parseInt(logisticsTypeId));
				areafee.setAreaId(1);
				areaFeeMap.put(areafee, logisticsAreaCarriageDO);
				*/
			}
		}
		
		try {
			List<LogisticsAreaCarriageDO> list = new LinkedList<LogisticsAreaCarriageDO>();
			
			Iterator<Map.Entry<AreaFee, LogisticsAreaCarriageDO>> iterator = areaFeeMap.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry<AreaFee, LogisticsAreaCarriageDO> entry = (Map.Entry<AreaFee, LogisticsAreaCarriageDO>)iterator.next();
				LogisticsAreaCarriageDO logisticsAreaCarriageDO = (LogisticsAreaCarriageDO)entry.getValue();
				System.out.println(logisticsAreaCarriageDO.getAreaName() + logisticsAreaCarriageDO.getAreaCarriage());
				list.add(logisticsAreaCarriageDO);
			}
			
			logisticsAreaCarriageAO.insertLogisticsAreaCarriageList(list);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Event=[LogisticsTemplateAction#addLogisticsAreaCarriage] 为指定地区设置运费失败");
		}
		
		/*
		//打印结果
		for (int k = 0; k < list.size(); k++) {
			System.out.println("地区："+list.get(k).getAreaName()+"***************************");
			System.out.println("地区运费："+list.get(k).getAreaCarriage());
			System.out.println("增加运费："+list.get(k).getAreaCarriageIncrease());
			System.out.println("物流方式："+list.get(k).getLogisticsTypeId());
			System.out.println("地区ID："+list.get(k).getAreaId());
			System.out.println("模板ID："+list.get(k).getTemplateId());
			System.out.println("记录创建时间："+list.get(k).getGmtCreate());
			System.out.println("记录修改时间："+list.get(k).getGmtModified());
			System.out.println("type："+list.get(k).getType());
		}
		*/
		
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateMemo() {
		return templateMemo;
	}

	public void setTemplateMemo(String templateMemo) {
		this.templateMemo = templateMemo;
	}
				   
	public void setLogisticsTemplateAO(LogisticsTemplateAO logisticsTemplateAO) {
		this.logisticsTemplateAO = logisticsTemplateAO;
	}

	public Integer getLogisticsTypePost() {
		return logisticsTypePost;
	}

	public void setLogisticsTypePost(Integer logisticsTypePost) {
		this.logisticsTypePost = logisticsTypePost;
	}

	public Integer getLogisticsTypeCourier() {
		return logisticsTypeCourier;
	}

	public void setLogisticsTypeCourier(Integer logisticsTypeCourier) {
		this.logisticsTypeCourier = logisticsTypeCourier;
	}

	public Integer getLogisticsTypeEMS() {
		return logisticsTypeEMS;
	}

	public void setLogisticsTypeEMS(Integer logisticsTypeEMS) {
		this.logisticsTypeEMS = logisticsTypeEMS;
	}
	
	public String getPostFee() {
		return postFee;
	}

	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}

	public String getCourierFee() {
		return courierFee;
	}

	public void setCourierFee(String courierFee) {
		this.courierFee = courierFee;
	}

	public String getEmsFee() {
		return emsFee;
	}

	public void setEmsFee(String emsFee) {
		this.emsFee = emsFee;
	}

	public String getPostFeeIncrease() {
		return postFeeIncrease;
	}

	public void setPostFeeIncrease(String postFeeIncrease) {
		this.postFeeIncrease = postFeeIncrease;
	}

	public String getCourierFeeIncrease() {
		return courierFeeIncrease;
	}

	public void setCourierFeeIncrease(String courierFeeIncrease) {
		this.courierFeeIncrease = courierFeeIncrease;
	}

	public String getEmsFeeIncrease() {
		return emsFeeIncrease;
	}

	public void setEmsFeeIncrease(String emsFeeIncrease) {
		this.emsFeeIncrease = emsFeeIncrease;
	}

	public String getTemplateName() {
		return templateName;
	}
		
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	
	public String getTempinfor() {
		return tempinfor;
	}

	public void setTempinfor(String tempinfor) {
		this.tempinfor = tempinfor;
	}
	
	public Map<String, List<LogisticsTemplateVO>> getTempDetailMap() {
		return tempDetailMap;
	}

	public void setTempDetailMap(
			Map<String, List<LogisticsTemplateVO>> tempDetailMap) {
		this.tempDetailMap = tempDetailMap;
	}
	
	public void setLogisticsAreaCarriageAO(
			LogisticsAreaCarriageAO logisticsAreaCarriageAO) {
		this.logisticsAreaCarriageAO = logisticsAreaCarriageAO;
	}
    
    public LogisticsAreaCarriageAO getLogisticsAreaCarriageAO() {
		return logisticsAreaCarriageAO;
	}

	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response=response;
	}
	
	public Integer getTemplateCount() {
		return templateCount;
	}
	
	public void setIsTempName(String isTempName) {
		this.isTempName = isTempName;
	}
    
    public String getIsTempName() {
		return isTempName;
	}

    public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
}
