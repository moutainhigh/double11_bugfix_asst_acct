package com.yuwang.pinju.domain.api;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>盛大通行证定制注册后通知接口传输的数据</p>
 *
 * @author gaobaowen
 * @since 2011-7-12 10:52:35
 */
public class SndaRegisterNotifyDO {

	/**
	 * 盛大通行证数字账号
	 */
	private String sdid;

	/**
	 * 盛大通行证 PT 账号
	 */
	private String sndapt;

	/**
	 * 盛大通行证注册时用户填写的昵称
	 */
	private String nickname;

	/**
	 * 通知数据签名
	 */
	private String sign;

	/**
	 * <p>获取数据的签名</p>
	 *
	 * @param signKey
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-12 11:16:05
	 */
	public String getDataSign(String signKey, String charset) {
		String signString = sdid + nickname + signKey;
		try {
			return DigestUtils.md5Hex(signString.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			return "UnsupportedEncodingException";
		}
	}

	public String getSdid() {
		return sdid;
	}
	public void setSdid(String sdid) {
		this.sdid = sdid;
	}
	public String getSndapt() {
		return sndapt;
	}
	public void setSndapt(String sndapt) {
		this.sndapt = sndapt;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		result = prime * result + ((sdid == null) ? 0 : sdid.hashCode());
		result = prime * result + ((sndapt == null) ? 0 : sndapt.hashCode());
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
		SndaRegisterNotifyDO other = (SndaRegisterNotifyDO) obj;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (sign == null) {
			if (other.sign != null)
				return false;
		} else if (!sign.equals(other.sign))
			return false;
		if (sdid == null) {
			if (other.sdid != null)
				return false;
		} else if (!sdid.equals(other.sdid))
			return false;
		if (sndapt == null) {
			if (other.sndapt != null)
				return false;
		} else if (!sndapt.equals(other.sndapt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SndaRegisterNotifyDO [sdid=" + sdid + ", sndapt=" + sndapt + ", nickname=" + nickname + ", sign=" + sign
				+ "]";
	}
}
