package com.yuwang.pinju.domain.member;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.domain.annotation.SecurityTransmission;
import com.yuwang.pinju.validation.annotation.FieldMatch;
import com.yuwang.pinju.validation.annotation.MemberName;

/**
 * <p>
 * 会员注册信息
 * </p>
 *
 * @author gaobaowen
 * @since 2011-7-28 15:56:19
 */
@FieldMatch.List({ @FieldMatch(first = "password", second = "confirm", message = "{chagePassword.confirm.match}") })
public class MemberRegisterDO {

	private final static java.util.regex.Pattern AD_CODE_PATTERN = java.util.regex.Pattern.compile("(?i:PJ)_[0-9]{1,15}_[0-9]{1,15}_[0-9]{1,15}(?:-[0-9]+)?");

	private String sid;
	private String loginName;
	private String password;
	private String confirm;
	private String email;
	private String captcha;
	private String referer;
	private String code;
	private Integer accoutType = MemberDO.ACCOUNT_TYPE_COMMON;

	/**
	 * 安全数据输入客户端 ID
	 */
	private String tid;

	private String registerIp;
	private String agreement;

	private Integer original;

	private MemberRegisterDO(Integer original) {
		this.original = original;
	}

	public static MemberRegisterDO newPinjuRegister() {
		return new MemberRegisterDO(MemberDO.MEMBER_ORIGIN_PINJU);
	}

	public static MemberRegisterDO newSinaRegister() {
		return new MemberRegisterDO(MemberDO.MEMBER_ORIGIN_SINA);
	}

	public boolean isPinjuRegister() {
		return MemberDO.MEMBER_ORIGIN_PINJU.equals(original);
	}

	public boolean isAdCode() {
		if (StringUtil.isBlank(code)) {
			return false;
		}
		return AD_CODE_PATTERN.matcher(code).matches();
	}
	
	public Integer getAccoutType() {
		return accoutType;
	}

	public void setAccoutType(Integer accoutType) {
		this.accoutType = accoutType;
	}

	public MemberPinjuLoginDO createPinjuLogin() {
		MemberPinjuLoginDO pinjuLogin = new MemberPinjuLoginDO();
		pinjuLogin.setLoginName(loginName);
		pinjuLogin.setPassword(password);
		pinjuLogin.setRegisterIp(registerIp);
		if (referer != null) {
			if (referer.length() > 512) {
				referer = referer.substring(0, 512);
			}
			pinjuLogin.setReferer(referer);
		}
		if (isAdCode()) {
			pinjuLogin.setAdCode(code);
		}
		return pinjuLogin;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@MemberName
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@SecurityTransmission
	@NotEmpty(message = "{memberRegister.password.notempty}")
	@Length(min = 6, max = 16, message = "{memberRegister.password.length}")
	@Pattern.List({
		@Pattern(regexp = "[\\x21-\\x7e]+", message = "{memberRegister.password.pattern}"),
		@Pattern(regexp = "(?!^[0-9]+$).*", message = "{memberRegister.password.number}"),
		@Pattern(regexp = "(?!^[a-zA-Z]+$).*", message = "{memberRegister.password.letter}")
	})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@SecurityTransmission
	@NotEmpty(message = "{memberRegister.confirm.notempty}")
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	// @NotEmpty(message = "{memberRegister.email.notempty}")
	// @Email(message = "{memberRegister.email.email}")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "{memberRegister.captcha.notempty}")
	@Pattern(regexp = "[0-9A-Za-z]{4}", message = "{memberRegister.captcha.pattern}")
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	@NotEmpty(message = "{memberRegister.agreement.notempty}")
	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = StringUtil.trim(code);
	}

	public Integer getOriginal() {
		return original;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agreement == null) ? 0 : agreement.hashCode());
		result = prime * result + ((captcha == null) ? 0 : captcha.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((confirm == null) ? 0 : confirm.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((original == null) ? 0 : original.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((referer == null) ? 0 : referer.hashCode());
		result = prime * result + ((registerIp == null) ? 0 : registerIp.hashCode());
		result = prime * result + ((sid == null) ? 0 : sid.hashCode());
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
		MemberRegisterDO other = (MemberRegisterDO) obj;
		if (agreement == null) {
			if (other.agreement != null)
				return false;
		} else if (!agreement.equals(other.agreement))
			return false;
		if (captcha == null) {
			if (other.captcha != null)
				return false;
		} else if (!captcha.equals(other.captcha))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (confirm == null) {
			if (other.confirm != null)
				return false;
		} else if (!confirm.equals(other.confirm))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (original == null) {
			if (other.original != null)
				return false;
		} else if (!original.equals(other.original))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (referer == null) {
			if (other.referer != null)
				return false;
		} else if (!referer.equals(other.referer))
			return false;
		if (registerIp == null) {
			if (other.registerIp != null)
				return false;
		} else if (!registerIp.equals(other.registerIp))
			return false;
		if (sid == null) {
			if (other.sid != null)
				return false;
		} else if (!sid.equals(other.sid))
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
		return "MemberRegisterDO [sid=" + sid + ", loginName=" + loginName + ", password=" + StringUtil.asterisk(password) + ", confirm="
				+ StringUtil.asterisk(confirm) + ", email=" + email + ", captcha=" + captcha + ", referer=" + referer + ", code=" + code
				+ ", tid=" + tid + ", registerIp=" + registerIp + ", agreement=" + agreement + ", original=" + original + "]";
	}
}
