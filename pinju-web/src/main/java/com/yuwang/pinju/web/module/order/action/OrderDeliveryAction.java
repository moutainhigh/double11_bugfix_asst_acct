package com.yuwang.pinju.web.module.order.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.annotations.JSON;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.core.order.ao.OrderBusinessAO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.PhoneDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.OrderMessage;
import com.yuwang.pinju.web.message.OrderMessageName;
import com.yuwang.pinju.web.module.my.screen.UrlToken;
import com.yuwang.pinju.web.valitation.ValidationResult;

/**
 * 
 * @author 杜成
 * @date 2011-6-8
 * @version 1.0
 */
public class OrderDeliveryAction extends ValidationResult implements
		OrderMessageName {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6657404809616724610L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private UrlToken urlToken = new UrlToken();

	private MemberDeliveryDO memberDelivery;

	private OrderBusinessAO orderBusinessAO;
	
	private PhoneDO phone;

	/**
	 * 异步json消息返回码
	 */
	private String resultCode;
	/**
	 * 异步json消息返回
	 */

	private String resultMessage;

	/**
	 * 
	 * Created on 2011-6-12
	 * 
	 * @see <p>
	 *      Discription: 更新或新添加收货地址
	 *      </p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update: [2011-7-16] [杜成] 把原有的ajax.xml返回方式改成struts.json返回方式
	 */
	public String addDelivery() {

		try {
			boolean flag = this.validate(memberDelivery);
			// 效验参数
			if (!flag) {
				this.setValidatorErrorMessage();
				this.setResultCode(OrderConstant.PARAMETERRROR);
				return ERROR;
			}
			// 添加
			memberDelivery.setMemberId(this.getUserId());

			//memberDelivery.setStatus(MemberDeliveryDO.STATUS_DEFAULT);
			
			memberDelivery.setPhone(phone.combine());

			Result result = orderBusinessAO.saveMemberDelivery(memberDelivery);

			this.setMemberDelivery((MemberDeliveryDO) result.getModels().get(
					"memberDelivery"));
			this.setResultMessage("！");
			if (result.isSuccess()) {
				this.setResultMessage(OrderMessage.getMessage(
						"operate.success", null));
				this.setResultCode(OrderConstant.OPERATESUCCED);
			} else {
				this.setResultCode(OrderConstant.PARAMETERRROR);
				this.setResultMessage(OrderMessage.getMessage(
						"operate.success", 10));
			}
		} catch (Exception e) {
			log.error("Event=[SettlementAction#settlement] 生成结算页面失败:", e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String setDefDelivery() {
		Result result = orderBusinessAO.setDufDelivery(this.getUserId(),
				memberDelivery.getId());
		if (result.isSuccess())
			return null;
		return null;
	}

	/**
	 * 
	 * Created on 2011-7-16
	 * 
	 * @see <p>
	 *      Discription: 参数错误封装返回信息
	 *      </p>
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void setValidatorErrorMessage() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("参数错误--");
		for (String key : this.getValidator().keySet()) {
			sBuffer.append(key);
			sBuffer.append(":");
			sBuffer.append(this.getValidator().get(key));
		}
		this.setResultMessage(sBuffer.toString());
	}

	private long getUserId() {
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

	@JSON(serialize = true)
	public MemberDeliveryDO getMemberDelivery() {
		return memberDelivery;
	}

	public void setMemberDelivery(MemberDeliveryDO memberDelivery) {
		this.memberDelivery = memberDelivery;
	}

	public UrlToken getUrlToken() {
		return urlToken;
	}

	public void setUrlToken(UrlToken urlToken) {
		this.urlToken = urlToken;
	}

	public OrderBusinessAO getOrderBusinessAO() {
		return orderBusinessAO;
	}

	public void setOrderBusinessAO(OrderBusinessAO orderBusinessAO) {
		this.orderBusinessAO = orderBusinessAO;
	}

	@JSON(serialize = true)
	public String getResultCode() {
		return resultCode;
	}

	@JSON(serialize = true)
	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public PhoneDO getPhone() {
		return phone;
	}

	public void setPhone(PhoneDO phone) {
		this.phone = phone;
	}
	
}
