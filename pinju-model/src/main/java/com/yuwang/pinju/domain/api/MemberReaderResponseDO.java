package com.yuwang.pinju.domain.api;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.domain.api.json.Response;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;

public class MemberReaderResponseDO extends Response {

	/**
	 * 接口调用状态 -- 会员编号无效（member.code.invalid）
	 */
	public final static String MEMBER_CODE_INVALID = "member.code.invalid";

	/**
	 * 接口调用状态 -- 签名验证失败（sign.error）
	 */
	public final static String SIGN_ERROR = "sign.error";

	/**
	 * 接口调用状态 -- 会员不存在（member.not.exists）
	 */
	public final static String MEMBER_NOT_EXISTS = "member.not.exists";

	/**
	 * 接口调用状态 -- 服务不可用（service.unavailable）
	 */
	public final static String SERVICE_UNAVAILABLE = "service.unavailable";

	private Long memberId;
	private Long infoVersion;
	private String nickname;
	private String fileServer;
	private MemberBasic basicInfo = new MemberBasic();
	private MemberSns snsInfo = new MemberSns();

	public MemberReaderResponseDO(String result) {
		super(result);
	}

	public static MemberReaderResponseDO createSuccess() {
		return create(SUCCESS);
	}

	public static MemberReaderResponseDO create(String result) {
		return new MemberReaderResponseDO(result);
	}

	public void addMember(MemberDO member) {
		if (member == null) {
			return;
		}
		memberId = member.getMemberId();
		infoVersion = member.getInfoVersion();
		nickname = member.getNickname();
		basicInfo.sex = member.getSex();
	}

	public void addMemberInfo(MemberInfoDO memberInfo) {
		if (memberInfo == null) {
			return;
		}
		basicInfo.realName = memberInfo.getRealName();
		basicInfo.birthday = DateUtil.formatDate(memberInfo.getBirthday());
		basicInfo.constellation = memberInfo.getConstellation();
		basicInfo.province = memberInfo.getProvince();
		basicInfo.city = memberInfo.getCity();
		basicInfo.district = memberInfo.getDistrict();
		basicInfo.pcdCode = memberInfo.getPcdCode();
		basicInfo.address = memberInfo.getAddress();
		basicInfo.zipCode = memberInfo.getZipCode();
		basicInfo.phone = memberInfo.getPhone();
		basicInfo.mobile = memberInfo.getMobile();
		basicInfo.email = memberInfo.getEmail();
	}

	public void addMemberSnsInfo(MemberSnsInfoDO memberSnsInfo) {
		if (memberSnsInfo == null) {
			return;
		}
		snsInfo.avatarsBasePath = memberSnsInfo.getAvatarsBasePath();
		snsInfo.biography = memberSnsInfo.getBiography();
	}

	public String getFileServer() {
		return fileServer;
	}

	public void setFileServer(String fileServer) {
		this.fileServer = fileServer;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getInfoVersion() {
		return infoVersion;
	}

	public void setInfoVersion(Long infoVersion) {
		this.infoVersion = infoVersion;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public MemberBasic getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(MemberBasic basicInfo) {
		this.basicInfo = basicInfo;
	}

	public MemberSns getSnsInfo() {
		return snsInfo;
	}

	public void setSnsInfo(MemberSns snsInfo) {
		this.snsInfo = snsInfo;
	}

	public static class MemberBasic {
		private Integer sex;
		private String realName;
		private String birthday;
		private String constellation;
		private String province;
		private String city;
		private String district;
		private String pcdCode;
		private String address;
		private String zipCode;
		private String phone;
		private String mobile;
		private String email;

		public Integer getSex() {
			return sex;
		}

		public String getRealName() {
			return realName;
		}

		public String getBirthday() {
			return birthday;
		}

		public String getConstellation() {
			return constellation;
		}

		public String getProvince() {
			return province;
		}

		public String getCity() {
			return city;
		}

		public String getDistrict() {
			return district;
		}

		public String getPcdCode() {
			return pcdCode;
		}

		public String getAddress() {
			return address;
		}

		public String getZipCode() {
			return zipCode;
		}

		public String getPhone() {
			return phone;
		}

		public String getMobile() {
			return mobile;
		}

		public String getEmail() {
			return email;
		}

		@Override
		public String toString() {
			return "MemberBasic [sex=" + sex + ", realName=" + realName + ", birthday=" + birthday + ", constellation="
					+ constellation + ", province=" + province + ", city=" + city + ", district=" + district
					+ ", pcdCode=" + pcdCode + ", address=" + address + ", zipCode=" + zipCode + ", phone=" + phone
					+ ", mobile=" + mobile + ", email=" + email + "]";
		}
	}

	public static class MemberSns {
		private String avatarsBasePath;
		private String biography;

		public String getAvatarsBasePath() {
			return avatarsBasePath;
		}

		public void setAvatarsBasePath(String avatarsBasePath) {
			this.avatarsBasePath = avatarsBasePath;
		}

		public String getBiography() {
			return biography;
		}

		@Override
		public String toString() {
			return "MemberSns [avatarsBasePath=" + avatarsBasePath + ", biography=" + biography + "]";
		}
	}

	@Override
	public String toString() {
		return "MemberReaderResponseDO [memberId=" + memberId + ", infoVersion=" + infoVersion + ", nickname="
				+ nickname + ", basicInfo=" + basicInfo + ", snsInfo=" + snsInfo + "]";
	}
}
