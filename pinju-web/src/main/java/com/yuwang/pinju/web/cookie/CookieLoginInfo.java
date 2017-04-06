package com.yuwang.pinju.web.cookie;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.common.Base61Code;
import com.yuwang.pinju.common.NumberUtil;
import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.core.member.manager.ticket.MemberOrigin;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.domain.member.asst.HasAsstPermVO;

/**
 * <p>
 * 存放于 Cookie 中的登录用户信息
 * </p>
 *
 * @author gaobaowen 2011-5-31 13:57:20
 */
public final class CookieLoginInfo {

	public final static String COOKIE_LOGIN_INFO = CookieLoginInfo.class.getName();

	public final static String ACTION_CONTEXT_KEY = CookieLoginInfo.class.getName() + ".actionContext";

	public final static Integer MEMBER_ORIGIN_UNKNOW = 9;
	public final static Integer AGREE_AGREEMENT_DEFAULT = MemberDO.AGREE_AGREEMENT_NO;

	private final static char HASH_MID_SEPERATOR = ':';

	private final static Log log = LogFactory.getLog(CookieLoginInfo.class);

	private String sessionId;
	private long sessionCreateTime;
	private Long memberId;
	private String nickname;
	private long loginTime;
	private int memberOrign = MEMBER_ORIGIN_UNKNOW;
	private int agreeAgreement = AGREE_AGREEMENT_DEFAULT;
	private int accountType = MemberDO.ACCOUNT_TYPE_COMMON;
	private int unreadMsgCount;
	private int buyerGrade;
	private int sellerGrade;
	private long infoVersion;
	private MemberDO member;
	private boolean fetchMember;
	private Integer userAgent;
	private String tokenHash;
	private long ip;

	private String asstPerms;

	CookieLoginInfo() {
	}

	/**
	 * <p>
	 * 从用户 Cookie 中获取登录信息，需要调用 {@link #isLogin()} 方法以确定是否登录
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static CookieLoginInfo getCookieLoginInfo() {
		return PinjuCookieManager.getCookieLoginInfo();
	}

	/**
	 * <p>
	 * 获取当前会员的 MemberDO 对象。若没有登录，或者无法获取会员数据时则返回 null
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public MemberDO getMember() {
		if (!isLogin()) {
			return null;
		}
		if (!fetchMember && member == null) {
			fetchMember = true;
			MemberAO memberAO = SpringBeanFactory.getBean(MemberAO.class);
			member = memberAO.findMember(memberId);
		}
		return member;
	}

	/**
	 * <p>
	 * 创建会员登录历史记录对象。设置了会话ID、会话时间、登录时间，以及登录 IP 地址等信息。其余信息需要使用 MemberLoginHisDO
	 * set 方法另行设置
	 * </p>
	 *
	 * @param loginIp
	 * @return
	 *
	 * @author gaobaowen
	 */
	public MemberLoginHisDO newMemberLoginHis(String loginIp) {
		MemberLoginHisDO loginHis = new MemberLoginHisDO();
		loginHis.setSessionId(sessionId == null ? PinjuCookieManager.getSessionId() : sessionId);
		loginHis.setSessionTime(new Date(sessionCreateTime));
		loginHis.setLoginTime(new Date());
		loginHis.setLoginIp(loginIp);
		return loginHis;
	}

	/**
	 * <p>
	 * 用户是否已经登录
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public boolean isLogin() {
		return (sessionId != null) && (memberId != null);
	}

	/**
	 *
	 * <p>
	 * 从平台登出，同时清理登录的 Cookie 信息
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public boolean logout() {
		PinjuCookieManager.clearSession();
		PinjuCookieManager.clearLogin();
		return true;
	}

	/**
	 *
	 * <p>
	 * 当前登录用户是否是盛大 CAS 用户
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public boolean isSdoMember() {
		return (MemberOrigin.SDO.getOrigin() == memberOrign);
	}

	/**
	 * <p>
	 * 获取会话ID
	 * </p>
	 *
	 * @return
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * <p>
	 * 获取会话创建的 Unix 纪元时间
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public long getSessionCreateTime() {
		return sessionCreateTime;
	}

	/**
	 * <p>
	 * 获取会员编号
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public Long getMemberId() {
		return memberId;
	}

	/**
	 * <p>
	 * 获取登录用户昵称
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * <p>
	 * 获取登录时间
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public long getLoginTime() {
		return loginTime;
	}

	/**
	 * <p>
	 * 获取会员来源 {@link MemberOrigin}
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public int getMemberOrign() {
		return memberOrign;
	}

	/**
	 * <p>用户是否同意过用户协议 {@link MemberDO#agreeAgreement}</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public int getAgreeAgreement() {
		return agreeAgreement;
	}

	/**
	 * <p>用户是否同意了用户协议</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-18 下午04:26:12
	 */
	public boolean isAgreeAgreement() {
		return MemberDO.AGREE_AGREEMENT_YES.equals(agreeAgreement);
	}

	/**
	 * <p>获取账号类型</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-19 15:55:37
	 */
	public int getAccountType() {
		return accountType;
	}

	/**
	 * <p>
	 * 获取会员未读消息数量
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}

	/**
	 * <p>
	 * 获取买家等级
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public int getBuyerGrade() {
		return buyerGrade;
	}

	/**
	 * <p>
	 * 获取卖家等级
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public int getSellerGrade() {
		return sellerGrade;
	}

	/**
	 * <p>个人资料版本号</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-19 16:20:05
	 */
	public long getInfoVersion() {
		return infoVersion;
	}

	/**
	 * <p>会员浏览器 User-Agent 摘要值</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-19 下午04:22:11
	 */
	public Integer getUserAgent() {
		return userAgent;
	}

	/**
	 * <p>会话 ID 摘要数据</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-19 16:25:27
	 */
	public String getTokenHash() {
		return tokenHash;
	}

	/**
	 * <p>登录IP</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-19 16:25:43
	 */
	public long getIp() {
		return ip;
	}

	/**
	 * <p>获取编码后的会员编号</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-17 17:14:20
	 */
	public String hashSessionMId() {
		if (memberId == null) {
			return null;
		}
		return hashMid(MemberIdPuzzle.encode(memberId));
	}

	/**
	 * <p>是否是同一个用户</p>
	 *
	 * @param pj0
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-19 下午02:13:18
	 */
	public boolean isSameMember(String pj0) {

		if (log.isInfoEnabled()) {
			log.info("isSameMember, current member id: [" + memberId + "], session id: [" + sessionId + "], pj0=[" + pj0 + "]");
		}

		// 没有登录
		if (!isLogin()) {
			log.warn("isSameMember, current member is un login");
			return false;
		}

		// 参数为空
		if (StringUtils.isBlank(pj0)) {
			log.warn("isSameMember, member session hash value (pj0) is empty, pj0=[" + pj0 + "]");
			return false;
		}

		// 没有分隔符
		int index = pj0.indexOf(HASH_MID_SEPERATOR);
		if (index < 0) {
			log.warn("isSameMember, member session hash value is invalid that lack seperator, pj0=[" + pj0 + "]");
			return false;
		}

		// 截取编码的会员编号
		String encMid = pj0.substring(0, index);
		long mid = MemberIdPuzzle.decode(encMid);
		if (mid < 0) {
			log.warn("isSameMember, member session hash value is invalid, can not get member id or member id is invalid, pj0=[" + pj0 + "], decode mid: [" + mid + "]");
			return false;
		}

		// 比对 hash 参数值
		if (!pj0.equals(hashMid(encMid))) {
			log.warn("isSameMember, member session hash is incorrect, pj0=[" + pj0 + "], calculate hash: [" + hashMid(encMid) + "]");
			return false;
		}

		// 比较会员编号是否相同
		if (!memberId.equals(mid)) {
			log.warn("isSameMember, member id is not same with login member id, pj0=[" + pj0 + "], before member id: [" + mid + "], current member id: [" + memberId + "]");
			return false;
		}
		return true;
	}

	void setBuyerGrade(int buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	void setSellerGrade(int sellerGrade) {
		this.sellerGrade = sellerGrade;
	}

	void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}

	void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	void setSessionCreateTime(long sessionCreateTime) {
		this.sessionCreateTime = sessionCreateTime;
	}

	void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	void setNickname(String nickname) {
		this.nickname = nickname;
	}

	void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	void setMemberOrign(int memberOrign) {
		this.memberOrign = memberOrign;
	}

	void setAgreeAgreement(int agreeAgreement) {
		this.agreeAgreement = agreeAgreement;
	}

	void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	void setInfoVersion(long infoVersion) {
		this.infoVersion = infoVersion;
	}

	void setUserAgent(Integer userAgent) {
		this.userAgent = userAgent;
	}

	void setTokenHash(String tokenHash) {
		this.tokenHash = tokenHash;
	}

	void setIp(long ip) {
		this.ip = ip;
	}

	/**
	 * <p>根据 SID 和会员编号生成消息摘要值</p>
	 *
	 * @param encodedMid
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 上午09:52:59
	 */
	private String hashMid(String encodedMid) {
		return encodedMid + HASH_MID_SEPERATOR + Base61Code.encode(DigestUtils.sha(sessionId + encodedMid + CookieLoginInfo.class.getName()));
	}

	/**
	 * <p>登录会员是否是普通账号</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-19 15:56:58
	 */
	public boolean isCommonAccount() {
		return isAccountType(MemberDO.ACCOUNT_TYPE_COMMON);
	}

	/**
	 * <p>登录会员是否是卖家主账号</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-19 15:58:39
	 */
	public boolean isMasterAccount() {
		return isAccountType(MemberDO.ACCOUNT_TYPE_SELLER);
	}

	/**
	 * <p>登录会员是否是卖家子账号</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-19 15:58:51
	 */
	public boolean isAssistantAccount() {
		return isAccountType(MemberDO.ACCOUNT_TYPE_ASSISTANT);
	}

	/**
	 * <p>登录会员是否是卖家相关的账号（卖家主账号或者是卖家子账号）</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-20 16:32:46
	 */
	public boolean isSellerAccount() {
		return isMasterAccount() || isAssistantAccount();
	}

	/**
	 * <p>判断子账号登录会员是否具有子账号的权限。会员为主账号时永恒返回 true 值。
	 * 若会员未登录或者除主账号、子账号之外的会员永远返回 false 值。</p>
	 *
	 * @param target 权限目标串
	 * @param action 权限行为串
	 * @return  是否具有该子账号功能的限制
	 *
	 * @author gaobaowen
	 * @since 2011-12-19 16:08:16
	 */
	public boolean hasAsstPerm(String target, String action) {
		if (!isLogin()) {
			return false;
		}
		if (isCommonAccount()) {
			return false;
		}
		if (isMasterAccount()) {
			return true;
		}
		initAsstPerms();
		if (StringUtils.isBlank(asstPerms)) {
			return false;
		}
		HasAsstPermVO data = new HasAsstPermVO(memberId, target, action);
		return data.hasAsstPerm(asstPerms, memberId);
	}

	/**
	 * <p>获取会员的主账号。若当前会员没有主账号，则该数据与 {@link #memberId} 一致。</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 下午04:02:19
	 */
	public Long getMasterMemberId() {
		if (!isLogin()) {
			return null;
		}
		if (!isAssistantAccount()) {
			return memberId;
		}
		initAsstPerms();
		if (StringUtils.isBlank(asstPerms)) {
			log.warn("[getMasterMemberId] assistant permissions data is empty, member id: [" + memberId + "]");
			return memberId;
		}
		int offset = asstPerms.indexOf('|');
		if (offset < 0) {
			return memberId;
		}
		String prefix = asstPerms.substring(0, offset);
		offset = prefix.indexOf(',');
		if (offset < 0) {
			return memberId;
		}
		return NumberUtil.parseLong(prefix.substring(0, offset), memberId);
	}

	/**
	 * <p>获取会员的主账号会员名。若当前会员没有主账号，则该数据与 {@link #nickname} 一致。</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 16:02:19
	 */
	public String getMasterMemberName() {
		if (!isLogin()) {
			return null;
		}
		if (!isAssistantAccount()) {
			return nickname;
		}
		initAsstPerms();
		if (StringUtils.isBlank(asstPerms)) {
			log.warn("[getMasterMemberName] assistant permissions data is empty, member id: [" + memberId + "]");
			return nickname;
		}
		int offset = asstPerms.indexOf('|');
		if (offset < 0) {
			return nickname;
		}
		String prefix = asstPerms.substring(0, offset);
		offset = prefix.indexOf(',');
		if (offset < 0) {
			return nickname;
		}
		return prefix.substring(offset + 1);
	}

	/**
	 * <p>初始化子账号权限字符串</p>
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 16:49:24
	 */
	private void initAsstPerms() {
		if (StringUtils.isBlank(asstPerms)) {
			MemberAsstAO memberAsstAO = SpringBeanFactory.getBean(MemberAsstAO.class);
			asstPerms = memberAsstAO.getAsstPerm(memberId);
		}
	}

	private boolean isAccountType(Integer acctType) {
		if (!isLogin()) {
			return false;
		}
		return acctType.equals(accountType);
	}

	@Override
	public String toString() {
		return "CookieLoginInfo [sessionId=" + sessionId + ", sessionCreateTime=" + sessionCreateTime + ", memberId="
				+ memberId + ", nickname=" + nickname + ", loginTime=" + loginTime + ", memberOrign=" + memberOrign
				+ ", agreeAgreement=" + agreeAgreement + ", accountType=" + accountType + ", unreadMsgCount="
				+ unreadMsgCount + ", buyerGrade=" + buyerGrade + ", sellerGrade=" + sellerGrade + ", infoVersion="
				+ infoVersion + ", member=" + member + ", fetchMember=" + fetchMember + ", userAgent=" + userAgent
				+ ", tokenHash=" + tokenHash + ", ip=" + ip + "]";
	}
}
