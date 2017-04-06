package com.yuwang.pinju.web.module.shop.screen;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.shop.ao.ShopDomainAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.util.ObjectUtil;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * 域名设置action 
 * @author XueQi
 *
 * @since 2011-10-10
 */
public class ShopDomainSettingAction {
	
	/**
	 * log
	 */
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * 域名设置AO
	 */
	private ShopDomainAO shopDomainAO;
	
	/**
	 * 店铺基本信息
	 */
	private ShopInfoDO shopInfoDO;
	
	/**
	 * 设置类型 0: 绑定, 1: 解绑, 2:更改
	 */
	private Integer settingType;
	
	/**
	 * 域名
	 */
	private String domain;
	
	/**
	 * 错误信息
	 */
	private String errorMessage;
	
	/**
	 * 错误信息
	 */
	private String errorMessage2;
	
	/**
	 * JSON字符串
	 */
	private String jsonResult;
	
	/**
	 * 店铺id
	 */
	private Integer shopId;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	/**
	 * 子账户日志
	 */
	private MemberAsstLog memberAsstLog;

	/**
	 * 显示域名设置页
	 * @return
	 */	
	@AssistantPermission(target = "shop", action = "domain")
	public String showDomainSettingPage() throws Exception{
		long userId = queryUserId();
		//验证用户是否已有绑定域名
		shopInfoDO = shopDomainAO.getShopDomainObject(userId, null);
		//验证是否开店
		boolean b = validateShopIsOpen(shopInfoDO);
		if(!b){
			return "NOT_OPEN";
		}
		//验证是否被合规
		b = validateShopIsHeGui(shopInfoDO);
		if(b){
			errorMessage = "店铺已被关闭";
			return "IS_CLOSE";
		}
		
		//如果有域名, 则显示解绑页面
		shopId = shopInfoDO.getShopId();
		if(shopInfoDO.getDomain() != null && !shopInfoDO.getDomain().trim().equals("")){
			domain = shopInfoDO.getDomain().trim();
			return "shopDomainIsBind";
		}else{
			return "shopDomainBind";
		}
	}
	
	/**
	 * 进入同意协议页面
	 * @return
	 */
	public String showDomainBindAgreePage(){
		long userId = queryUserId();
		//验证用户是否已有绑定域名
		shopInfoDO = shopDomainAO.getShopDomainObject(userId, null);
		//验证是否开店
		boolean b = validateShopIsOpen(shopInfoDO);
		if(!b){
			return "NOT_OPEN";
		}
		//验证是否被合规
		b = validateShopIsHeGui(shopInfoDO);
		if(b){
			errorMessage = "店铺已被关闭";
			return "IS_CLOSE";
		}
		if(domain != null){
			return "success";
		}
		errorMessage = "显示绑定域名协议页面出错！";
		errorMessage2 = "<a href='/shopDomain/showDomainSettingPageAction.htm'>请重新设置！</a>";
		return "error";
	}
	
	/**
	 * 点击更改域名按钮操作
	 * @return
	 */
	public String showChangeDomainPage(){
		long userId = queryUserId();
		//验证用户是否已有绑定域名
		shopInfoDO = shopDomainAO.getShopDomainObject(userId, null);
		//验证是否开店
		boolean b = validateShopIsOpen(shopInfoDO);
		if(!b){
			return "NOT_OPEN";
		}
		//验证是否被合规
		b = validateShopIsHeGui(shopInfoDO);
		if(b){
			errorMessage = "店铺已被关闭";
			return "IS_CLOSE";
		}
		errorMessage2 = "<a href='/shopDomain/showDomainSettingPageAction.htm'>请重新设置！</a>";
		//出错
		if(shopInfoDO == null){
			errorMessage = "显示更改域名页面出错！";
			return "error";
		}
		if(!checkCanBindAndUnBind(shopInfoDO)){
			errorMessage = "您更改域名的次数过于频繁，暂时无法再次更改！";
			return "error";
		}
		domain = shopInfoDO.getDomain().trim();
		domain = domain.substring(0,domain.indexOf("."));
		return "changeDomainPage";
	}
	
	/**
	 * 设置域名(绑定,解绑,更改)
	 * @return
	 */
	@AssistantPermission(target = "shop", action = "domain")
	public String settingDomain(){
		errorMessage2 = "<a href='/shopDomain/showDomainSettingPageAction.htm'>请重新设置！</a>";
		if(settingType == null){
			errorMessage = "绑定域名出错！";
			return "error";
		}
		shopInfoDO = new ShopInfoDO();
		Long userId = queryUserId();
		shopInfoDO.setUserId(userId);
		String returnString = "";
		
		ShopInfoDO shopInfoDO2 = shopDomainAO.getShopDomainObject(userId, null);
		//验证是否开店
		boolean b = validateShopIsOpen(shopInfoDO2);
		if(!b){
			return "NOT_OPEN";
		}
		//验证是否被合规
		b = validateShopIsHeGui(shopInfoDO2);
		if(b){
			errorMessage = "店铺已被关闭";
			return "IS_CLOSE";
		}
		
		if(shopInfoDO2!=null){
			shopId = shopInfoDO2.getShopId();
		}
		
		if(!checkCanBindAndUnBind(shopInfoDO2)){
			errorMessage = "您更改域名的次数过于频繁，暂时无法再次更改！";
			return "error";
		}
		
		//绑定/更改
		if(settingType.equals(ShopConstant.SETTING_DOMAIN_BIND) || settingType.equals(ShopConstant.SETTING_DOMAIN_CHANGE)){
			//域名转换成小写
			domain = domain.toLowerCase();
			//验证输入规则
			if(!checkDomain(domain) || domain.length() > 32 || domain.length()<5){
				errorMessage = "域名前缀可以输入长度为5~32个字符的英文、数字或者\"-\"，且\"-\"不能出现在最前面和最后面！";
				return "error";
			}

			//敏感词过滤
			if(WordFilterFacade.scan(domain, 6)){
				errorMessage = "对不起，您的域名中含有敏感词，请重新输入域名！";
				return "error";
			}
			//精确匹配敏感词过滤
			if(WordFilterFacade.accurateScan(domain, 8, false, true)){
				errorMessage = "对不起，您的域名中含有敏感词，请重新输入域名！";
				return "error";
			}
			//是否是默认域名
			if(domain.indexOf("shop") == 0){
				String num = domain.substring(4, domain.length());
				if(ObjectUtil.isNum(num)){
					errorMessage = "您的域名是保留域名，";
					return "error";
				}
			}
			domain = domain + ShopConstant.PINJU_COM;
			if(checkDomainIsBind(domain)){
				errorMessage = "您晚了一步！此域名已被使用，";
				return "error";
			}
			shopInfoDO.setDomain(domain);
			shopInfoDO.setDomainCreate(new Date());
			
			returnString = "shopDomainIsBind";
			//子账户日志
			memberAsstLog.log("店铺域名由 " + shopInfoDO2.getDomain() +" 设置为 " + domain);
		}
		//解绑
		else if(settingType.equals(ShopConstant.SETTING_DOMAIN_UNBIND)){
			shopInfoDO.setDomain(" ");
			returnString = "shopDomainBind";
			//子账户日志
			memberAsstLog.log("店铺域名由 " + shopInfoDO2.getDomain() +" 设置为 " + "");
		}
		if(shopDomainAO.updateShopDomain(shopInfoDO)){
			return returnString;
		}else{
			errorMessage = "绑定域名出错！";
			return "error";
		}
		
	}
	
	/**
	 * 是否可以绑定(一小时内无法重复绑定/解绑) 
	 * @param shopInfo
	 * @return true : 可以绑定/解绑, false:不可以绑定/解绑
	 */
	private boolean checkCanBindAndUnBind(ShopInfoDO shopInfo){
		Date domainCreateDate = shopInfo.getDomainCreate();
		if(domainCreateDate == null) return true;
		Date nowDate = new Date();
		if((nowDate.getTime() - domainCreateDate.getTime()) < 60 * 60 * 1000){
			return false;
		}
		return true;
	}
	
	/**
	 * 验证domain输入是否合法
	 * @param str
	 * @return true 合法 , false 不合法
	 */
	private boolean checkDomain(String str){
		Pattern p = Pattern.compile("^([a-z0-9]{1,2}|[a-z0-9][a-z0-9\\-]+[a-z0-9])$", Pattern.CASE_INSENSITIVE); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();	
		
	}
	
	/**
	 * 验证域名是否被绑定 
	 * @param domainStr
	 * @return true : 已绑定 ; false 未绑定
	 */
	private boolean checkDomainIsBind(String domainStr){
		ShopInfoDO shopInfoDO = shopDomainAO.getShopDomainObject(null, domainStr);
		if(shopInfoDO != null && shopInfoDO.getDomain() != null && !shopInfoDO.getDomain().trim().equals("")){
			if(shopInfoDO.getUserId().longValue() == queryUserId()){
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 异步验证域名已被绑定
	 * @return
	 */
	public String checkDomainIsBindAjax() {
		//检查敏感词
		if(WordFilterFacade.scan(domain, 6)){
			jsonResult = "wordFilter";
			return "success";
		}
		
		//精确匹配敏感词过滤
		if(WordFilterFacade.accurateScan(domain, 8, false, true)){
			jsonResult = "wordFilter";
			return "success";
		}
		
		
		//是否是默认域名
		if(domain.indexOf("shop") == 0){
			String num = domain.substring(4, domain.length());
			if(ObjectUtil.isNum(num)){
				jsonResult = "defaultName";
				return "success";
			}
		}
		String domainStr = domain + ShopConstant.PINJU_COM;
		if(checkDomainIsBind(domainStr)){
			jsonResult = "bind";
		}else{
			jsonResult = "unbind";
		}
		
		return "success";
	}
	
	/**
	 * 验证是否开店
	 * @param shopInfo
	 * @return true: 已开店 , false: 未开店
	 */
	private boolean validateShopIsOpen(ShopInfoDO shopInfo){
		//为空--为开店
		if(shopInfo == null){
			return false;
		}
		//不为空 但是没有店铺id为空或店铺id没有值--未开店
		if(shopInfo != null && (shopInfo.getApproveStatus()==null || shopInfo.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO) ){
			return false;
		}
		return true;
	}
	
	/**
	 * 验证是否被合规
	 * @param shopInfo
	 * @return true: 已合规 , false: 未合规
	 */
	private boolean validateShopIsHeGui(ShopInfoDO shopInfo){
		//不为空 并且 开店状态为合规状态(-2) 放回true
		if(shopInfo != null && (shopInfo.getApproveStatus()==null || shopInfo.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI )){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获取userId
	 * @return
	 */
	private long queryUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		} else {

		}
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}
	

	public void setShopDomainAO(ShopDomainAO shopDomainAO) {
		this.shopDomainAO = shopDomainAO;
	}

	public ShopInfoDO getShopInfoDO() {
		return shopInfoDO;
	}

	public void setShopInfoDO(ShopInfoDO shopInfoDO) {
		this.shopInfoDO = shopInfoDO;
	}

	public Integer getSettingType() {
		return settingType;
	}

	public void setSettingType(Integer settingType) {
		this.settingType = settingType;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	public String getErrorMessage2() {
		return errorMessage2;
	}

	public void setErrorMessage2(String errorMessage2) {
		this.errorMessage2 = errorMessage2;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
	
}
