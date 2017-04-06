package com.yuwang.pinju.web.module.my.screen;

import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.tenpay.BaseSplitRequestHandler;
import com.yuwang.pinju.core.common.tenpay.MD5Util;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;
import com.yuwang.pinju.core.common.tenpay.XMLClientResponseHandler;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.InputPayAccountDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.MemberCheckAction;
import com.yuwang.pinju.web.module.member.action.MemberSignAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>
 * 财付通账号管理
 * </p>
 *
 * @author zwm
 */
public class TenpayAction extends MemberCheckAction implements PinjuAction, PaymentAction {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(TenpayAction.class);

	private MemberAO memberAO;
	private MemberPaymentDO payment;
	
	private InputPayAccountDO pa;
	
	public TenpayAction() {
		pa = new InputPayAccountDO();
	}
	
	/**
	 * 财付通参数Add by ShiXing@2011.09.15
	 */
	private SortedMap parameters = new TreeMap();

	@Override
	public String execute() throws Exception {
		// 获取登录数据
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		// 未登录时跳至登录页面
		if (!login.isLogin()) {
			log.warn("execute, current member not logged, member id: " + login);
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		Result result = memberAO.getPaymentAccount(login.getMemberId());

		if (log.isDebugEnabled()) {
			log.debug("execute, invoke AO result is: " + result.getResultCode());
		}

		if (RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("cannot find member information, member id: " + login.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		if (!result.isSuccess()) {
			log.warn("execute, invoked AO result is not success, return to page 500, result: " + result.getResultCode());
			return PAGE_500;
		}
		payment = result.getModel(MemberKeyConstant.KEY_MEMBER_PAYMENT, MemberPaymentDO.class);
		if (payment == null ||  MemberPaymentDO.BOUND_STATUS_UNBOUND.equals(payment.getBondStatus())) {
			return UNBOUND;
		}
		return BOUND;
	}
	
	public String boundPayAccount() throws Exception {
		
		String method = ServletActionContext.getRequest().getMethod();
		if (REQUEST_METHOD_GET.equals(method)) {
			log.warn("GET request is not allowed");
			return INPUT;
		}
		
		// 获取登录数据
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		// 未登录时跳至登录页面
		if (!login.isLogin()) {
			log.warn("execute, current member not logged, member id: " + login);
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		
		if (!super.isSameMember(login)) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_MEMBER_NOT_MATCH, login.getNickname());
			return INPUT;
		}

		if (pa.getAccountType() == null || (pa.getAccountType() != 0 && pa.getAccountType() != 1)) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_ACCOUNTTYPE_NO);
		}
		
		ActionInvokeResult invoke = new ActionInvokeResult(pa);
		if(!invoke.validate()) {
			log.warn("bean validation failed, data: " + pa);
			return INPUT;
		}

		MemberDO member = memberAO.findMember(login.getMemberId());
		
		if (member == null) {
			log.warn("bound, cannot find member information, member id: " + login.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		payment = new MemberPaymentDO();
		payment.setMemberId(member.getMemberId());
		payment.setAccountNO(pa.getAccount());
		payment.setPersonName(pa.getUsername());
		payment.setAccountType(pa.getAccountType());
		payment.setActiveStatus(MemberPaymentDO.ACTIVE_STATUS_ACTIVE);
		payment.setBondStatus(MemberPaymentDO.BOUND_STATUS_BOUND);
		payment.setInstitution(MemberPaymentDO.INSTITUTION_TENPAY);
		
		Result result = memberAO.findBoundMemberPaymented(payment);
		if (!result.isSuccess()) {
			if (MemberResultConstant.PAY_ACCOUNT_ALREADY_BOUND_ERROR.equals(result.getResultCode())) {
				ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_ALREADY_BOUND_ERROR);
			} else {
				ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_BOUND_ERROR);
			}
			return INPUT;
		}
		return userInfoCheck(payment);
	}
	
	/**
	 * Created on 2011-9-15 
	 * @desc <p>Discription:[调财付通绑定接口并接收响应信息]</p>
	 * @param 
	 * @return String
	 * @author:[石兴]
	 * @update:[2011-9-15] [更改人姓名]
	 */
	public String userInfoCheck(MemberPaymentDO payment) {
		
		BaseSplitRequestHandler reqHandler = new BaseSplitRequestHandler(null, null);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		XMLClientResponseHandler resHandler = new XMLClientResponseHandler();
		try {
			reqHandler.init();
			reqHandler.setKey(PinjuConstant.TENPAY_PAY_MD5KEY); //与财付通约定的md5key
			//reqHandler.setKey("82d2f8b9fd7695aec51415ab2900a755"); //测试与财付通约定的md5key
			reqHandler.setGateUrl(USER_INFO_CHECK_CGI);
			
			reqHandler.setParameter("cmdno", MemberSignAction.BIZ_TYPE_BIND);
			reqHandler.setParameter("spid", PinjuConstant.TENPAY_PAY_PARTNER); //商户号   	
			//reqHandler.setParameter("spid", "1900000107"); //测试商户号   	
			reqHandler.setParameter("uin", payment.getAccountNO());    	//用户财付通账号
			reqHandler.setParameter("name", payment.getPersonName());    	//用户姓名    

			//设置请求内容
			httpClient.setReqContent(reqHandler.getRequestURL());
			//后台调用
			if(httpClient.call(1)) {
				//设置结果参数
				resHandler.setContent(httpClient.getResContent());
				resHandler.setKey(PinjuConstant.TENPAY_PAY_MD5KEY);
				//resHandler.setKey("82d2f8b9fd7695aec51415ab2900a755"); //与财付通约定的md5key
				//获取返回参数
				String retcd = resHandler.getParameter("retcd");
				//判断签名及结果
				if (!resHandler.isTenpaySign()) {
					ActionInvokeResult.setInvokeMessageKey(MessageName.TEN_PAY_SIGN_ERROR);
					return INPUT;
				}else if("0".equals(retcd)) { //0通过
					Result result = memberAO.boundPaymentAccount(payment);
					if (!result.isSuccess()) {
						ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_BOUND_ERROR);
						return INPUT;
					}
				} else {
					log.error("财付通系统返回错误,错误码retcd：" + resHandler.getParameter("retcd")+" 错误消息retmsg:" + resHandler.getParameter("retmsg"));
					ActionInvokeResult.setInvokeMessageKey(MessageName.TEN_PAY_ACCOUNT_IS_INVALID);
					return INPUT;
				}	
			} else {
				log.error("网络异常,请稍候重试！");
				ActionInvokeResult.setInvokeMessageKey(MessageName.TEN_PAY_NET_ERROR);
				return INPUT;
			}
		} catch (Exception e) {
			log.error("TenpayAction#userInfoCheck#Exception xml=" + resHandler.getContent(), e);
			ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_BOUND_ERROR);
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	 * Created on 2011-9-15 
	 * @desc <p>Discription:[是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名]</p>
	 * @param 
	 * @return boolean
	 * @author:[石兴]
	 * @update:[2011-9-15] [更改人姓名]
	 */
	public boolean isTenpaySign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + PinjuConstant.TENPAY_DIRECTPAY_MD5KEY);
		//算出摘要
		String sign = MD5Util.MD5Encode(sb.toString(), PinjuConstant.TENPAY_DIRECTPAY_INPUT_CHARSET).toLowerCase();
		String tenpaySign = ServletActionContext.getRequest().getParameter("sign").toLowerCase();
		//debug信息
		log.error("TenpayAction#isTenpaySign"+sb.toString() + " => sign:" + sign + " tenpaySign:" + tenpaySign);
		return tenpaySign.equals(sign);
	}
	
	public InputPayAccountDO getPa() {
		return pa;
	}

	public void setPa(InputPayAccountDO pa) {
		this.pa = pa;
	}

	public MemberPaymentDO getPayment() {
		return payment;
	}

	public void setPayment(MemberPaymentDO payment) {
		this.payment = payment;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}
}
