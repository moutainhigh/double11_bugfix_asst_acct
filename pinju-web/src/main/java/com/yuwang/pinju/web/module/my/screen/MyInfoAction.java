package com.yuwang.pinju.web.module.my.screen;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.cookie.PinjuCookieManager.LoginRelationCookie;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>用户完善个人信息</p>
 *
 * @author gaobaowen
 * 2011-6-3 下午01:19:10
 */
public class MyInfoAction implements PinjuAction, MessageName {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(MyInfoAction.class);

	private MemberAO memberAO;

	private MemberDO member;
	private MemberInfoDO memberInfo;
	private String hash;
	private String returnUrl;
	private boolean nickname;
	private boolean realName;

	public MyInfoAction() {
	}

	/**
	 * <p>将用户资料查询出来 ，供用户修改</p>
	 */
	public String execute() throws Exception {

		ServletUtil.forbidBrowserCache();

		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		member = memberAO.findMember(login.getMemberId());
		if(member == null || !member.getNickname().equals(login.getNickname())) {
			log.warn("cannot find member, member id: " + login.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		processShow(login);

		if(!EmptyUtil.isBlank(member.getNickname())) {
			nickname = true;
		}
		if (!EmptyUtil.isBlank(memberInfo.getRealName())) {
			realName = true;
		}

		hash = digest(member, memberInfo);

		return SUCCESS;
	}

	/**
	 * <p>更新会员个人基本资料</p>
	 *
	 * @return
	 * @throws Exception
	 *
	 * @author gaobaowen
	 */
	public String update() throws Exception {

		ServletUtil.forbidBrowserCache();

		// 获取会员基本信息
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();

		String method = ServletActionContext.getRequest().getMethod();

		// 不允许除 POST 方法的访问！
		if(!"POST".equalsIgnoreCase(method)) {
			log.warn("user request this update using HTTP/GET, cookie info: " + login);
			ActionInvokeResult.setInvokeMessageKey(OPERATE_INVALID);
			return ERROR;
		}

		MemberDO loginMember = memberAO.findMember(login.getMemberId());
		if(loginMember == null || !loginMember.getNickname().equals(login.getNickname())) {
			log.warn("cannot find member, member id: " + login.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		boolean isChangeNickname = EmptyUtil.isBlank(loginMember.getNickname());

		// 生成更新数据的消息摘要
		String realHash = digest(loginMember, memberInfo);

		// 判断消息摘要与提交过来的消息摘要是否一致，若不一致时为非法操作
		if(EmptyUtil.isBlank(hash) || !hash.equals(realHash)) {
			log.warn("user submit url hash incorrect, hash: " + hash + ", real: " + realHash);
			ActionInvokeResult.setInvokeMessageKey(OPERATE_INVALID);
			return SUCCESS;
		}

		// 若当前用户没有设置为昵称，则设置成会员填写的昵称
		if(EmptyUtil.isBlank(loginMember.getNickname())) {
			loginMember.setNickname(member.getNickname());
			log.debug("update member nickname, current user has not nickname, then using user fill the nickname");
		} else {
			nickname = true;
		}

		MemberInfoDO info = memberAO.findMemberInfo(login.getMemberId());
		if (info != null && !EmptyUtil.isBlank(info.getRealName())) {
			memberInfo.setRealName(info.getRealName());
			realName = true;
		}

		// 设置会员填写的性别
		loginMember.setSex(member.getSex());

		memberInfo.setMemberId(loginMember.getMemberId());

		ActionInvokeResult air = new ActionInvokeResult(loginMember, memberInfo);

		// 验证用户填写的数据
		if(!air.validate()) {
			member = loginMember;
			ActionInvokeResult.setInvokeMessageKey(OPERATE_SUBMIT_PARAMETER_ERROR);
			log.info("validate loginMember and memberInfo has error(s), message: [ OPERATE_SUBMIT_PARAMETER_ERROR ]");
			return SUCCESS;
		}

		int[] updates = memberAO.updateMemberInfo(loginMember, memberInfo);

		hash = digest(loginMember, memberInfo);

		if(log.isInfoEnabled()) {
			log.info("update status, member update count: " + updates[0] + ", member update count: " + updates[1]);
		}

		if(!EmptyUtil.isBlank(member.getNickname())) {
			nickname = true;
		}

		if (!EmptyUtil.isBlank(memberInfo.getRealName())) {
			realName = true;
		}

		member = loginMember;

		resetLoginCookie(member, login, isChangeNickname);

		if(EmptyUtil.isBlank(returnUrl)) {
			ActionInvokeResult.setInvokeMessageKey(OPERATE_SUCCESS);
			return SUCCESS;
		} else {
			log.info("need redirect to returnUrl after update success, return: [" + returnUrl + "]");
			return RETURN_URL;
		}
	}

	private String processShow(CookieLoginInfo login) {
		memberInfo = memberAO.findMemberInfo(login.getMemberId());
		if(memberInfo == null) {
			memberInfo = new MemberInfoDO();
			if(log.isInfoEnabled()) {
				log.info("cannot find member info, member id: " + login.getMemberId());
			}
		}
		return SUCCESS;
	}

	private void resetLoginCookie(MemberDO member, CookieLoginInfo login, boolean isChangeNickname) {
		log.debug("reset login cookie");
		LoginRelationCookie relation = new LoginRelationCookie(login);
		relation.setInfoVersion(member.getInfoVersion());
		if (log.isDebugEnabled()) {
			log.debug("reset login cookies, member information version, write version: " + member.getInfoVersion());
		}
		if(isChangeNickname) {
			relation.setNickname(member.getNickname());
			if (log.isDebugEnabled()) {
				log.debug("reset login cookies, nickname has been changed, new nickname: " + member.getNickname());
			}
		}
		PinjuCookieManager.writeLogin(relation);
	}

	private static String digest(MemberDO member, MemberInfoDO memberInfo) {
		return DigestUtils.shaHex(member.getId() + "|" + member.getMemberId() + "|" + memberInfo.getId());
	}

	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public MemberDO getMember() {
		return member;
	}
	public void setMember(MemberDO member) {
		this.member = member;
	}
	public MemberInfoDO getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(MemberInfoDO memberInfo) {
		this.memberInfo = memberInfo;
	}
	public MemberAO getMemberAO() {
		return memberAO;
	}
	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}
	public boolean isNickname() {
		return nickname;
	}
	public boolean isRealName() {
		return realName;
	}
}
