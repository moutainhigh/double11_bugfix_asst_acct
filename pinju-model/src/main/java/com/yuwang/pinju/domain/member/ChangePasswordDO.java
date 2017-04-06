package com.yuwang.pinju.domain.member;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.domain.annotation.SecurityTransmission;
import com.yuwang.pinju.validation.annotation.FieldMatch;

/**
 * <p>
 * 修改密码
 * </p>
 *
 * @author gaobaowen
 *
 * @since 2011-8-1 18:17:24
 */
@FieldMatch.List({ @FieldMatch(first = "newPassword", second = "confirm", message = "{chagePassword.confirm.match}") })
public class ChangePasswordDO {

	/**
	 * 会员编号
	 */
	private long memberId;

	/**
	 * 昵称（会员名）
	 */
	private String nickname;

	/**
	 * 旧密码
	 */
	private String oldPassword;

	/**
	 * 新密码
	 */
	private String newPassword;

	/**
	 * 新密码确认
	 */
	private String confirm;

	/**
	 * 安全数据输入客户端 ID
	 */
	private String tid;	

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@SecurityTransmission
	@NotEmpty(message = "{chagePassword.oldPassword.notempty}")
	@Length(min = 6, max = 16, message = "{chagePassword.oldPassword.length}")
	@Pattern(regexp = "[\\x21-\\x7e]+", message = "{chagePassword.oldPassword.pattern}")
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@SecurityTransmission
	@NotEmpty(message = "{chagePassword.newPassword.notempty}")
	@Length(min = 6, max = 16, message = "{chagePassword.newPassword.length}")
	@Pattern.List({
		@Pattern(regexp = "[\\x21-\\x7e]+", message = "{chagePassword.newPassword.pattern}"),
		@Pattern(regexp = "(?!^[0-9]+$).*", message = "{memberRegister.password.number}"),
		@Pattern(regexp = "(?!^[a-zA-Z]+$).*", message = "{memberRegister.password.letter}")
	})
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@SecurityTransmission
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirm == null) ? 0 : confirm.hashCode());
		result = prime * result + (int) (memberId ^ (memberId >>> 32));
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((oldPassword == null) ? 0 : oldPassword.hashCode());
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
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
		ChangePasswordDO other = (ChangePasswordDO) obj;
		if (confirm == null) {
			if (other.confirm != null)
				return false;
		} else if (!confirm.equals(other.confirm))
			return false;
		if (memberId != other.memberId)
			return false;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (oldPassword == null) {
			if (other.oldPassword != null)
				return false;
		} else if (!oldPassword.equals(other.oldPassword))
			return false;
		if (tid == null) {
			if (other.tid != null)
				return false;
		} else if (!tid.equals(other.tid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChangePasswordDO [memberId=" + memberId + ", nickname=" + nickname + ", oldPassword=" + StringUtil.asterisk(oldPassword)
				+ ", newPassword=" + StringUtil.asterisk(newPassword) + ", confirm=" + StringUtil.asterisk(confirm) + ", tid=" + tid + "]";
	}
}
