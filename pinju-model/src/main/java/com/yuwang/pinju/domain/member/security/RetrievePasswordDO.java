package com.yuwang.pinju.domain.member.security;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.validation.annotation.FieldMatch;

/**
 * <p>
 * 忘记密码
 * </p>
 *
 * @author gaobaowen
 *
 * @since 2011-9-5 16:14:00
 */
@FieldMatch.List({ @FieldMatch(first = "newPassword", second = "confirm", message = "{chagePassword.confirm.match}") })
public class RetrievePasswordDO {

	/**
	 * MAC 摘要计算时原始数据拼接符
	 */
	public final static String DATA_SEPERATOR = "|";

	private String newPassword;
	private String confirm;

	/**
	 * 邮件链接 TOKEN
	 */
	private String param3;

	/**
	 * 邮件链接消息编号
	 */
	private String param4;

	/**
	 * TOKEN+消息编号 HASH 值
	 */
	private String param5;

	/**
	 * 找回密码的类型
	 */
	private String param6;

	private Integer userAgent;
	private String clientIp;

	/**
	 * <p>获取 MAC 摘要原始数据</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 15:32:37
	 */
	public String getMacData() {
		return param3 + DATA_SEPERATOR + param4 + DATA_SEPERATOR + param6 + DATA_SEPERATOR + userAgent + DATA_SEPERATOR + clientIp;
	}

	@NotEmpty(message = "{chagePassword.newPassword.notempty}")
	@Length(min = 6, max = 16, message = "{chagePassword.newPassword.length}")
	@Pattern(regexp = "[\\x21-\\x7e]+", message = "{chagePassword.newPassword.pattern}")
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	/**
	 * <p>
	 * 邮件链接 TOKEN 值（param3）
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 14:43:54
	 */
	public String getParam3() {
		return param3;
	}

	/**
	 * <p>
	 * 设置邮件链接 TOKEN 值（param3）
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 14:43:54
	 */
	public void setParam3(String param3) {
		this.param3 = param3;
	}

	/**
	 * <p>
	 * 邮件链接消息编号值（param4）
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 14:43:54
	 */
	public String getParam4() {
		return param4;
	}

	/**
	 * <p>
	 * 设置邮件链接消息编号值（param4）
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 14:43:54
	 */
	public void setParam4(String param4) {
		this.param4 = param4;
	}

	/**
	 * <p>
	 * TOKEN+消息编号数据摘要（param5）
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 14:43:54
	 */
	public String getParam5() {
		return param5;
	}

	/**
	 * <p>
	 * 设置 TOKEN+消息编号数据摘要（param5）
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 14:43:54
	 */
	public void setParam5(String param5) {
		this.param5 = param5;
	}

	/**
	 * <p>获取安全类型。见（{@link MemberSecurityParam}）</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-8 下午01:36:34
	 */
	public String getParam6() {
		return param6;
	}

	public void setParam6(String param6) {
		this.param6 = param6;
	}

	public Integer getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(Integer userAgent) {
		this.userAgent = userAgent;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientIp == null) ? 0 : clientIp.hashCode());
		result = prime * result + ((confirm == null) ? 0 : confirm.hashCode());
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((param3 == null) ? 0 : param3.hashCode());
		result = prime * result + ((param4 == null) ? 0 : param4.hashCode());
		result = prime * result + ((param5 == null) ? 0 : param5.hashCode());
		result = prime * result + ((userAgent == null) ? 0 : userAgent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetrievePasswordDO other = (RetrievePasswordDO) obj;
		if (clientIp == null) {
			if (other.clientIp != null)
				return false;
		} else if (!clientIp.equals(other.clientIp))
			return false;
		if (confirm == null) {
			if (other.confirm != null)
				return false;
		} else if (!confirm.equals(other.confirm))
			return false;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword))
			return false;
		if (param3 == null) {
			if (other.param3 != null)
				return false;
		} else if (!param3.equals(other.param3))
			return false;
		if (param4 == null) {
			if (other.param4 != null)
				return false;
		} else if (!param4.equals(other.param4))
			return false;
		if (param5 == null) {
			if (other.param5 != null)
				return false;
		} else if (!param5.equals(other.param5))
			return false;
		if (userAgent == null) {
			if (other.userAgent != null)
				return false;
		} else if (!userAgent.equals(other.userAgent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetrievePasswordDO [newPassword=" + StringUtil.asterisk(newPassword) + ", confirm="
				+ StringUtil.asterisk(confirm) + ", userAgent=" + userAgent + ", clientIp=" + clientIp + ", param3(token)=" + param3
				+ ", param4(message id)=" + param4 + ", param5(mac)=" + param5 + "]";
	}

}
