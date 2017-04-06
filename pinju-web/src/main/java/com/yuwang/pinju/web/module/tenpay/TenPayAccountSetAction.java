package com.yuwang.pinju.web.module.tenpay;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * 财付通支付账号设定页面
 * @author XueQi
 *
 * @since 2011-9-28
 */
public class TenPayAccountSetAction {
	
	/**
	 * log
	 */
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * member AO
	 */
	private MemberAO memberAO;
	
	/**
	 * 返回到页面的提示信息
	 */
	private String tenPayError;
	
	/**
	 * 成功绑定和签约后点击下一步跳转的地方
	 */
	private String returnModule;
	
	/**
	 * 财付通商户号
	 */
	private String merchantno;
	
	
	/**
	 * 页面显示
	 * @return
	 */
	public String tenPayAccountSetPage(){
		return "success";
	}
	
	/**
	 * 店铺类型
	 */
	public String sellerType;
	
	/**
	 * 点击下一步验证
	 * @return
	 */
//	public String accountNext(){
//		long userId = queryUserId();
//		//验证是否已绑定和已签约
//		Result result = memberAO.isSignPayAgreement(userId);
//		//设置返回的页面,如果returnModule为空,则获取由前一个action传过来的值,否则就是页面hidden的值
//		if(returnModule == null){
//			returnModule = ActionContext.getContext().getParameters().get("returnModule").toString();
//		}
//		//获取财付通商户号
//		if(merchantno == null){
//			merchantno = ActionContext.getContext().getParameters().get("merchantno").toString();
//		}
//		//成功则返回前一个应用
//		//result.setSuccess(true);
//		if(result.isSuccess()){
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("sellerType", sellerType);
//			ActionContext.getContext().setParameters(parameters);
//			return returnModule;
//		}
//		//返回财付通设置页面
//		else{
//			tenPayError = ResultCodeMsg.getResultMessage(result.getResultCode());
//			return "error";
//		}
//	}
	
	/**
	 * 点击下一步验证------2.0新
	 * @return
	 */
	public String accountNext(){
		long userId = queryUserId();
		//验证是否已绑定和已签约
		Result result = memberAO.isSignPayAgreement(userId);
		//获取财付通商户号
		merchantno = PinjuConstant.TENPAY_PAY_PARTNER;
		//成功则返回前一个应用
		//result.setSuccess(true);
		if(result.isSuccess()){
			tenPayError = "";
			return "success";
		}
		//返回财付通设置页面
		else{
			if(result.getResultCode().equals(MemberResultConstant.RESULT_MEMBERID_FAIL)){
				tenPayError = "1";
			}
			if(result.getResultCode().equals(MemberResultConstant.RESULT_SIGN_PAY_BIND_NOT)){
				tenPayError = "2";
			}
			if(result.getResultCode().equals(MemberResultConstant.RESULT_SIGN_PAY_AGREEMENT_NOT)){
				tenPayError = "3";
			}
			return "error";
		}
	}
	
	/**
	 * 从cookie获取userId
	 * @return
	 */
	protected long queryUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMemberId();
		} else {

		}
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public String getTenPayError() {
		return tenPayError;
	}

	public void setTenPayError(String tenPayError) {
		this.tenPayError = tenPayError;
	}

	public String getReturnModule() {
		return returnModule;
	}

	public void setReturnModule(String returnModule) {
		this.returnModule = returnModule;
	}

	public String getMerchantno() {
		return merchantno;
	}

	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

}
