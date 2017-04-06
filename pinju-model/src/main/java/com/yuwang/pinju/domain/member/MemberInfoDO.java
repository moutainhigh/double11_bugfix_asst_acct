package com.yuwang.pinju.domain.member;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.RelationEntity;

/**
 * <p>会员个人资料</p>
 *
 * @author gaobaowen
 * 2011-6-2 下午12:08:31
 */
public class MemberInfoDO implements RelationEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 将电话作为交易联系方式（0）
	 */
	public final static Integer PHONE_TRANS_YES = 0;

	/**
	 * 不将电话作为交易联系方式（默认）（1）
	 */
	public final static Integer PHONE_TRANS_NO = 1;

	private Long id;
	private Long memberId;
	private String realName;
	private String idCard;
	private Date birthday;
	private String constellation;
	private String province;
	private String city;
	private String district;
	private String pcdCode;
	private String address;
	private String zipCode;
	private String phone;
	private String mobile;
	private Integer phoneTrans = PHONE_TRANS_NO;
	private String email;

	public MemberInfoDO() {
	}

	public boolean isNew() {
		return (id == null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@NotEmpty(message = "{memberInfoDO.realName.notempty}")
	@Length(min = 2, max = 20, message = "{memberInfoDO.realName.length}")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = StringUtils.trim(realName);
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = StringUtils.trim(idCard);
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Length(max = 10, message = "{memberInfoDO.constellation.length}")
	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = StringUtils.trim(constellation);
	}

	@NotEmpty(message = "{memberInfoDO.province.notempty}")
	@Length(max = 20, message = "{memberInfoDO.province.length}")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = StringUtils.trim(province);
	}

	@NotEmpty(message = "{memberInfoDO.city.notempty}")
	@Length(max = 20, message = "{memberInfoDO.city.length}")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = StringUtils.trim(city);
	}

	@Length(max = 20, message = "{memberInfoDO.district.length}")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = StringUtils.trim(district);
	}

	@NotEmpty(message = "{memberInfoDO.pcdCode.notempty}")
	@Length(max = 20, message = "{memberInfoDO.pcdCode.length}")
	public String getPcdCode() {
		return pcdCode;
	}

	public void setPcdCode(String pcdCode) {
		this.pcdCode = StringUtils.trim(pcdCode);
	}

	@Length(max = 200, message = "{memberInfoDO.address.length}")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if(address == null || address.trim().length() == 0) {
			return;
		}
		this.address = StringUtils.trim(address);
	}

	@Pattern(regexp = "(?:(?:0[1-7]|[1-8][0-9])[0-9]{4})?", message = "{memberInfoDO.zipCode.pattern}")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = StringUtils.trim(zipCode);
	}

	@Pattern(regexp = "(?:1[3458][0-9]{9}|[0-9-]+)?", message = "{memberInfoDO.phone.pattern}")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = StringUtils.trim(phone);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = StringUtils.trim(mobile);
	}

	@NotNull(message = "{memberInfoDO.phoneTrans.notnull}")
	public Integer getPhoneTrans() {
		return phoneTrans;
	}

	public void setPhoneTrans(Integer phoneTrans) {
		this.phoneTrans = phoneTrans;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = StringUtils.trim(email);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((constellation == null) ? 0 : constellation.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCard == null) ? 0 : idCard.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((pcdCode == null) ? 0 : pcdCode.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((phoneTrans == null) ? 0 : phoneTrans.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((realName == null) ? 0 : realName.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		MemberInfoDO other = (MemberInfoDO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (constellation == null) {
			if (other.constellation != null)
				return false;
		} else if (!constellation.equals(other.constellation))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCard == null) {
			if (other.idCard != null)
				return false;
		} else if (!idCard.equals(other.idCard))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (pcdCode == null) {
			if (other.pcdCode != null)
				return false;
		} else if (!pcdCode.equals(other.pcdCode))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (phoneTrans == null) {
			if (other.phoneTrans != null)
				return false;
		} else if (!phoneTrans.equals(other.phoneTrans))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (realName == null) {
			if (other.realName != null)
				return false;
		} else if (!realName.equals(other.realName))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberInfoDO [id=" + id + ", memberId=" + memberId + ", realName=" + realName + ", idCard=" + idCard
				+ ", birthday=" + DateUtil.formatDate(birthday) + ", constellation=" + constellation + ", province=" + province + ", city="
				+ city + ", district=" + district + ", pcdCode=" + pcdCode + ", address=" + address + ", zipCode="
				+ zipCode + ", phone=" + phone + ", mobile=" + mobile + ", phoneTrans=" + phoneTrans + ", email="
				+ email + "]";
	}
}
