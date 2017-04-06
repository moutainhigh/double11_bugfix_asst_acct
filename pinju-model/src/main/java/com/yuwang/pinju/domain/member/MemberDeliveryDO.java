package com.yuwang.pinju.domain.member;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.RelationEntity;

/**
 * <p>会员收货地址实体</p>
 *
 * @author gaobaowen
 * 2011-6-3 下午04:16:56
 */
public class MemberDeliveryDO implements RelationEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 收货地址最大数量（10）
	 */
	public final static int MAX_DELIVERY_COUNT = 10;

	/**
	 * 非默认收货地址（0）
	 */
	public final static Integer STATUS_UNDEFAULT = 0;

	/**
	 * 默认收货地址（0）
	 */
	public final static Integer STATUS_DEFAULT = 1;

	private Long id;
	private Long memberId;
	private String receiverName;
	private String province;
	private String city;
	private String district;
	private String pcdCode;
	private String address;
	private String zipCode;
	private String phone;
	private String mobile;
	private Integer status = STATUS_UNDEFAULT;
	private Date updateTime;

	private String hash;
	private String idCode;
	private String memberIdCode;

	public MemberDeliveryDO() {
	}

	public MemberDeliveryDO(Long id, Long memberId) {
		this.id = id;
		this.memberId = memberId;
	}

	public boolean isDefault() {
		return STATUS_DEFAULT.equals(status);
	}

	@Override
	public boolean isNew() {
		return id == null;
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

	@NotEmpty(message = "{memberDelivery.receiverName.notempty}")
	@Length(min = 2, max = 20, message = "{memberDelivery.receiverName.length}")
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@NotEmpty(message = "{memberDelivery.province.notempty}")
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	@NotEmpty(message = "{memberDelivery.city.notempty}")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	@NotEmpty(message = "{memberDelivery.pcdCode.notnull}")
	public String getPcdCode() {
		return pcdCode;
	}
	public void setPcdCode(String pcdCode) {
		this.pcdCode = pcdCode;
	}

	@NotEmpty(message = "{memberDelivery.address.notempty}")
	@Length(min = 1, max = 200, message = "{memberDelivery.address.length}")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@NotEmpty(message = "{memberDelivery.zipCode.notempty}")
	@Pattern(regexp = "(?:(?:0[1-7]|[1-8][0-9])[0-9]{4})?", message = "{memberDelivery.zipCode.pattern}")
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Pattern(regexp = "(?:1[3458][0-9]{9})?", message = "{memberDelivery.mobile.pattern}")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getMemberIdCode() {
		return memberIdCode;
	}
	public void setMemberIdCode(String memberIdCode) {
		this.memberIdCode = memberIdCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCode == null) ? 0 : idCode.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((memberIdCode == null) ? 0 : memberIdCode.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((pcdCode == null) ? 0 : pcdCode.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((receiverName == null) ? 0 : receiverName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		MemberDeliveryDO other = (MemberDeliveryDO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCode == null) {
			if (other.idCode != null)
				return false;
		} else if (!idCode.equals(other.idCode))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (memberIdCode == null) {
			if (other.memberIdCode != null)
				return false;
		} else if (!memberIdCode.equals(other.memberIdCode))
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
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (receiverName == null) {
			if (other.receiverName != null)
				return false;
		} else if (!receiverName.equals(other.receiverName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
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
		return "MemberDeliveryDO [id=" + id + ", memberId=" + memberId + ", receiverName=" + receiverName
				+ ", province=" + province + ", city=" + city + ", district=" + district + ", pcdCode=" + pcdCode
				+ ", address=" + address + ", zipCode=" + zipCode + ", phone=" + phone + ", mobile=" + mobile
				+ ", status=" + status + ", updateTime=" + updateTime + ", hash=" + hash + ", idCode=" + idCode
				+ ", memberIdCode=" + memberIdCode + "]";
	}
}
