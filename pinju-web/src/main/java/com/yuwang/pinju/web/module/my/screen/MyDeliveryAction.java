package com.yuwang.pinju.web.module.my.screen;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.yuwang.cookie.util.CodeUtil;
import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.PhoneDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.valitation.ValidationResult;

/**
 * <p>会员收货地址管理</p>
 *
 * @author gaobaowen
 * 2011-6-3 下午05:03:57
 */
public class MyDeliveryAction extends ValidationResult implements Action, MessageName {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(MyDeliveryAction.class);
	private final static String HASH_SALT = MyDeliveryAction.class.getName();

	private MemberAO memberAO;
	private List<MemberDeliveryDO> deliveries;
	private MemberDeliveryDO delivery;
	private PhoneDO phone;

	private String invokeMessage;
	private String _token_;

	private String encodeMemberId;

	/**
	 * 进入地址管理的查询，用于显示页面上的数据
	 */
	@Override
	public String execute() throws Exception {
		long memberId = getMemberId();
		String result = selectDeliveries(memberId, true);
		logDebug("execute delivery infomation list finished", result);
		invokeMessage = null;
		ServletActionContext.getResponse().addHeader("Cache-Control", "no-cache, no-store");
		return result;
	}

	/**
	 * <p>新增或者保存</p>
	 *
	 * @return
	 * @throws Exception
	 *
	 * @author gaobaowen
	 */
	public String saveOrUpdate() throws Exception {
		long memberId = getMemberId();

		// 不允许除 POST 方法的访问！
		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			log.warn("ACCESS DENIED, user request this update using HTTP/" + method);
			selectDeliveries(memberId, true);
			invokeMessage = Message.getMessage(OPERATE_INVALID);
			return ERROR;
		}

		String result = selectDeliveries(memberId, innerSaveOrUpdate(memberId));
		logDebug("execute saveOrUpdate finished", result);
		return result;
	}

	/**
	 *
	 * <p>删除用户收货地址</p>
	 *
	 * @return
	 * @throws Exception
	 *
	 * @author gaobaowen
	 */
	public String remove() throws Exception {
		long memberId = getMemberId();
		String result = selectDeliveries(memberId, innerRemove(memberId));
		logDebug("execute remove finished", result);
		return result;
	}

	/**
	 *
	 * <p>设为默认收货地址</p>
	 *
	 * @return
	 * @throws Exception
	 *
	 * @author gaobaowen
	 */
	public String defaultDelivery() throws Exception {
		long memberId = getMemberId();
		String result = selectDeliveries(memberId, innerDefaultDelivery(memberId));
		logDebug("execute defaultDelivery finished", result);
		return result;
	}

	public MemberAO getMemberAO() {
		return memberAO;
	}
	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}
	public List<MemberDeliveryDO> getDeliveries() {
		return deliveries;
	}
	public void setDeliveries(List<MemberDeliveryDO> deliveries) {
		this.deliveries = deliveries;
	}
	public MemberDeliveryDO getDelivery() {
		return delivery;
	}
	public void setDelivery(MemberDeliveryDO delivery) {
		this.delivery = delivery;
	}
	public String getInvokeMessage() {
		return invokeMessage;
	}
	public void setInvokeMessage(String invokeMessage) {
		this.invokeMessage = invokeMessage;
	}
	public PhoneDO getPhone() {
		return phone;
	}
	public void setPhone(PhoneDO phone) {
		this.phone = phone;
	}
	public String get_token_() {
		return _token_;
	}
	public void set_token_(String _token_) {
		this._token_ = _token_;
	}
	public String getEncodeMemberId() {
		return encodeMemberId;
	}
	public void setEncodeMemberId(String encodeMemberId) {
		this.encodeMemberId = encodeMemberId;
		if(delivery != null) {
			delivery.setMemberId(MemberIdPuzzle.decode(encodeMemberId));
		}
	}

	/**
	 * <p>计算摘要</p>
	 *
	 * @param deliveries
	 *
	 * @author gaobaowen
	 */
	private static void hashList(List<MemberDeliveryDO> deliveries) {
		if(EmptyUtil.isBlank(deliveries)) {
			return;
		}
		for(int i = 0, k = deliveries.size(); i < k; i++) {
			hashDelivery(deliveries.get(i));
		}
	}

	/**
	 * <p>计算一条地址数据的摘要</p>
	 *
	 * @param delivery
	 *
	 * @author gaobaowen
	 */
	private static void hashDelivery(MemberDeliveryDO delivery) {
		String h = hash(delivery.getId(), delivery.getMemberId());
		delivery.setHash(h);
		delivery.setIdCode(CodeUtil.toBase62(delivery.getId()));
		delivery.setMemberIdCode(CodeUtil.toBase62(delivery.getMemberId()));
	}

	/**
	 * <p>检查摘要是否正确</p>
	 *
	 * @param delivery
	 * @param memberId   当前会员的会员编号
	 * @return
	 *
	 * @author gaobaowen
	 */
	private static boolean checkMemberIdAndHash(MemberDeliveryDO delivery, Long memberId) {
		String hash = hash(delivery.getId(), delivery.getMemberId());
		if(log.isDebugEnabled()) {
			log.debug("check hash, parameter hash: " + delivery.getHash() + ", calculate hash: " + hash);
		}
		return hash.equals(delivery.getHash()) && memberId.equals(delivery.getMemberId());
	}

	private static String hash(Long id, Long memberId) {
		char[] chs = (id + HASH_SALT + memberId).toCharArray();
		long h = 0;
		for(int i = 0; i < chs.length; i++) {
			h = h * 117 + chs[i];
		}
		return CodeUtil.toBase62(Math.abs(h));
	}

	private long getMemberId() {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		return login.getMemberId();
	}

	/**
	 * <p>修改或者新增，根据 ID 和 MID 是否为空判断</p>
	 *
	 * @param memberId
	 * @return
	 *
	 * @author gaobaowen
	 */
	private boolean innerSaveOrUpdate(long memberId) {
//		if(!urlToken.validateToken()) {
//			log.debug("saveOrUpdate, token validating failed");
//			invokeMessage = Message.getMessage(OPERATE_SUBMIT_DUPLICATE);
//			return false;
//		}

		if( delivery == null ) {
			invokeMessage = Message.getMessage(OPERATE_INVALID);
			return false;
		}
		if(log.isDebugEnabled()) {
			log.debug("saveOrUpdate, current member id: " + memberId + ", delivery id: " + delivery.getId() + ", delivery mid: " + delivery.getMemberId());
		}

		if(!validate(delivery, phone)) {
			invokeMessage = Message.getMessage(OPERATE_SUBMIT_PARAMETER_ERROR);
			log.warn("inner save or update validate failed");
			return false;
		}

		delivery.setPhone(phone.combine());

		// 电话和手机号不能同时为空
		if(EmptyUtil.isBlank(delivery.getMobile()) && EmptyUtil.isBlank(delivery.getPhone())) {
			invokeMessage = Message.getMessage(OPERATE_SUBMIT_PARAMETER_ERROR);
			log.warn("mobile and telphone are all empty, mobile: [" + delivery.getMobile() + "], telphone: [" + delivery.getPhone() + "]");
			return false;
		}

		// ID 和 MEMBER_ID 都不为空时，执行修改
		if(delivery.getId() != null && delivery.getMemberId() != null) {
			log.debug("id and mid are not null, execute update");
			return executeUpdate(memberId, false);
		}

		// ID、MEMBER_ID 为空时，执行新增

		if(log.isDebugEnabled()) {
			log.debug("execute insert operate, " + delivery);
		}

		// 检查会员当前的地址数量
		int count = memberAO.countMemberDeliveries(memberId);
		if(count + 1 > MemberDeliveryDO.MAX_DELIVERY_COUNT) {
			log.warn("current member delivery amount (" + count + ") is over max delivery count (" + MemberDeliveryDO.MAX_DELIVERY_COUNT + ")");
			invokeMessage = Message.getMessage(MEMBER_DELIVERY_OVER_MAX, MemberDeliveryDO.MAX_DELIVERY_COUNT);
			return false;
		}

		delivery.setMemberId(memberId);
		delivery = memberAO.saveMemberDelivery(delivery);

		// id 没得到的话，插入就失败了
		if(delivery.getId() == null) {
			invokeMessage = Message.getMessage(OPERATE_FAILED);
			log.error("insert delivery error, " + delivery);
			return false;
		}

		invokeMessage = Message.getMessage(OPERATE_INSERT_SUCCESS);
		clearDataObject();
		return true;
	}

	private boolean innerRemove(long memberId) {
//		if(!urlToken.validateToken()) {
//			log.debug("innerRemove, token validating failed");
//			invokeMessage = Message.getMessage(OPERATE_SUBMIT_DUPLICATE);
//			return false;
//		}
		if(delivery == null) {
			invokeMessage = Message.getMessage(OPERATE_INVALID);
			return false;
		}
		if(log.isDebugEnabled()) {
			log.debug("remove, current member id: " + memberId + ", delivery id: " + delivery.getId() + ", delivery mid: " + delivery.getMemberId());
		}
		return executeUpdate(memberId, true);
	}

	/**
	 * <p>执行修改或者删除</p>
	 *
	 * @param memberId   会员编号
	 * @param isRemove   是否删除
	 * @return
	 *
	 * @author gaobaowen
	 */
	private boolean executeUpdate(long memberId, boolean isRemove) {
		String operation = (isRemove ? "delete" : "update");
		if(!checkMemberIdAndHash(delivery, memberId)) {
			log.warn("checkMemberIdAndHash check failed, " + delivery + ", operation: " + operation);
			invokeMessage = Message.getMessage(OPERATE_INVALID);
			return false;
		}
		int u = -1;
		if(isRemove) {
			u = memberAO.removeMemberDelivery(delivery.getId(), delivery.getMemberId());
		} else {
			u = memberAO.updateMemberDelivery(delivery);
		}
		if(log.isDebugEnabled()) {
			log.debug("execute " + operation + " delivery, count: " + u);
		}
		if(u < 1) {
			invokeMessage = Message.getMessage(OPERATE_FAILED);
			return false;
		}
		invokeMessage = Message.getMessage(isRemove ? OPERATE_REMOVE_SUCCESS : OPERATE_UPDATE_SUCCESS);
		clearDataObject();
		return true;
	}

	/**
	 * <p>设置默认收货地址</p>
	 *
	 * @param memberId
	 * @return
	 *
	 * @author gaobaowen
	 */
	private boolean innerDefaultDelivery(long memberId) {
//		if(!urlToken.validateToken()) {
//			log.debug("innerDefaultDelivery, token validating failed");
//			invokeMessage = Message.getMessage(OPERATE_SUBMIT_DUPLICATE);
//			return false;
//		}
		if(log.isDebugEnabled()) {
			log.debug("innerDefaultDelivery, current member id: " + memberId + ", delivery id: " + delivery.getId() + ", delivery mid: " + delivery.getMemberId());
		}
		if(delivery.getId() == null || delivery.getMemberId() == null) {
			invokeMessage = Message.getMessage(OPERATE_INVALID);
			log.debug("id and mid has null, cannot set default delivery");
			return false;
		}
		if(!checkMemberIdAndHash(delivery, memberId)) {
			log.warn("innerDefaultDelivery checkMemberIdAndHash check failed, " + delivery);
			invokeMessage = Message.getMessage(OPERATE_INVALID);
			return false;
		}
		int defaultCount = memberAO.setDefaultDelivery(delivery.getId(), memberId);
		if(defaultCount < 1) {
			invokeMessage = Message.getMessage(OPERATE_FAILED);
		}
		invokeMessage = Message.getMessage(OPERATE_UPDATE_SUCCESS);
		return (defaultCount > 0);
	}

	/**
	 * <p>选取当前用户所有的收货地址</p>
	 *
	 * @param memberId     当前会员编号
	 * @param lastResult   上一步执行的结果是正确还是失败
	 * @return
	 *
	 * @author gaobaowen
	 */
	private String selectDeliveries(long memberId, boolean lastResult) {
		deliveries = memberAO.findMemberDeliveries(memberId);
		if(!EmptyUtil.isBlank(deliveries)) {
			hashList(deliveries);
		}
		if(lastResult && EmptyUtil.isBlank(invokeMessage)) {
			invokeMessage = Message.getMessage(OPERATE_SUCCESS);
		}
		if(EmptyUtil.isBlank(invokeMessage) || OPERATE_SUCCESS.equals(invokeMessage) || delivery == null) {
			delivery = new MemberDeliveryDO();
			hashDelivery(delivery);
		}
		return lastResult ? SUCCESS : ERROR;
	}

	private void logDebug(String info, String result) {
		if(log.isDebugEnabled()) {
			log.debug(info + ", result: " + result);
		}
	}

	private void clearDataObject() {
		delivery = null;
		phone = null;
	}
}
