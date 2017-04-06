package com.yuwang.pinju.domain.member;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.domain.annotation.SecurityTransmission;
import com.yuwang.pinju.domain.member.login.MemberSinaAccountDO;
import com.yuwang.pinju.validation.annotation.MemberName;

public class MemberSinaRegisterDO {

	private Long id;
	
	private String registerIp;
	
	/**
	 * 会员名
	 */
	private String loginName;
	/**
	 * 确认密码
	 */
	private String confirm;
	/**
	 * 密码
	 */
	private String password;

	/**
     * 会员编号
     */
    private Long memberId;

    /**
     * 新浪微博会员ID
     */
    private String sinaUid;

    /**
     * 新浪会员微博昵称
     */
    private String sinaNickname;

    /**
     * 省份编码（参考省份编码表）
     */
    private Integer province;

    /**
     * 城市编码（参考城市编码表）
     */
    private Integer city;

    /**
     * 新浪微博会员居住地址
     */
    private String location;

    /**
     * 新浪微博的URL地址
     */
    private String weiboUrl;

    /**
     * 新浪会员个人描述
     */
    private String description;

    /**
     * 大头像地址
     */
    private String avatarLarge;

    /**
     * 用户个性化URL
     */
    private String userDomain;

    /**
     * 自定义图像地址
     */
    private String profileImage;

    /**
     * 会员性别（0：女；1：男；9：未知）
     */
    private Integer gender;

    /**
     * 版本号
     */
    private Integer version;

    private Date gmtCreate;

    private Date gmtModified;
    
    /**
     * base64 sina微博会员信息
     */
    private String aj0;
    /**
     * 加密 sina微博会员信息
     */
    private String aj1;
    
    public MemberDO createMember() {
		MemberDO member = new MemberDO();
		member.setMemberId(memberId);
		member.setAccountName(sinaUid);
		member.setMemberOrigin(MemberDO.MEMBER_ORIGIN_SINA);
		member.setNickname(loginName);
		member.setAgreeAgreement(MemberDO.AGREE_AGREEMENT_YES);
		return member;
	}
    
    public MemberSinaAccountDO createMemberSinaAccount() {
    	MemberSinaAccountDO memberSinaAccount = new MemberSinaAccountDO();
    	memberSinaAccount.setMemberId(memberId);
    	memberSinaAccount.setAvatarLarge(avatarLarge);
    	memberSinaAccount.setCity(city);
    	memberSinaAccount.setDescription(description);
    	memberSinaAccount.setGender(gender);
    	memberSinaAccount.setLocation(location);
    	memberSinaAccount.setProfileImage(profileImage);
    	memberSinaAccount.setProvince(province);
    	memberSinaAccount.setSinaNickname(sinaNickname);
    	memberSinaAccount.setSinaUid(sinaUid);
    	memberSinaAccount.setUserDomain(userDomain);
    	memberSinaAccount.setWeiboUrl(weiboUrl);
    	return memberSinaAccount;
    }
    
    public MemberRegisterDO createMemberRegister() {
    	MemberRegisterDO resgister = MemberRegisterDO.newSinaRegister();
    	resgister.setLoginName(loginName);
    	resgister.setPassword(password);
    	resgister.setConfirm(confirm);
    	resgister.setRegisterIp(registerIp);
    	return resgister;
    }

    public Long getId(){
        return id;
    }

    public Long getMemberId(){
        return memberId;
    }

    public String getSinaUid(){
        return sinaUid;
    }
    
    @MemberName
    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = StringUtil.trim(loginName);
	}

    public String getSinaNickname(){
        return sinaNickname;
    }
    
    @SecurityTransmission
	@NotEmpty(message = "{memberRegister.confirm.notempty}")
    public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
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

    public Integer getProvince(){
        return province;
    }

    public Integer getCity(){
        return city;
    }

    public String getLocation(){
        return location;
    }

    public String getWeiboUrl(){
        return weiboUrl;
    }

    public String getDescription(){
        return description;
    }

    public String getAvatarLarge(){
        return avatarLarge;
    }

    public String getUserDomain(){
        return userDomain;
    }

    public String getProfileImage(){
        return profileImage;
    }

    public Integer getGender(){
        return gender;
    }

    public Integer getVersion(){
        return version;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public void setSinaUid(String sinaUid){
        this.sinaUid = StringUtil.trim(sinaUid);
    }

    public void setSinaNickname(String sinaNickname){
        this.sinaNickname = StringUtil.trim(sinaNickname);
    }

    public void setProvince(Integer province){
        this.province = province;
    }

    public void setCity(Integer city){
        this.city = city;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setWeiboUrl(String weiboUrl){
        this.weiboUrl = weiboUrl;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAvatarLarge(String avatarLarge){
        this.avatarLarge = avatarLarge;
    }

    public void setUserDomain(String userDomain){
        this.userDomain = userDomain;
    }

    public void setProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

    public void setGender(Integer gender){
        this.gender = gender;
    }

    public void setVersion(Integer version){
        this.version = version;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }
    
    
    public String getAj0() {
		return aj0;
	}

	public void setAj0(String aj0) {
		this.aj0 = aj0;
	}

	public String getAj1() {
		return aj1;
	}

	public void setAj1(String aj1) {
		this.aj1 = aj1;
	}
	
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aj0 == null) ? 0 : aj0.hashCode());
		result = prime * result + ((aj1 == null) ? 0 : aj1.hashCode());
		result = prime * result
				+ ((avatarLarge == null) ? 0 : avatarLarge.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result
				+ ((profileImage == null) ? 0 : profileImage.hashCode());
		result = prime * result
				+ ((province == null) ? 0 : province.hashCode());
		result = prime * result
				+ ((sinaNickname == null) ? 0 : sinaNickname.hashCode());
		result = prime * result + ((sinaUid == null) ? 0 : sinaUid.hashCode());
		result = prime * result
				+ ((userDomain == null) ? 0 : userDomain.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result
				+ ((weiboUrl == null) ? 0 : weiboUrl.hashCode());
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
		MemberSinaRegisterDO other = (MemberSinaRegisterDO) obj;
		if (aj0 == null) {
			if (other.aj0 != null)
				return false;
		} else if (!aj0.equals(other.aj0))
			return false;
		if (aj1 == null) {
			if (other.aj1 != null)
				return false;
		} else if (!aj1.equals(other.aj1))
			return false;
		if (avatarLarge == null) {
			if (other.avatarLarge != null)
				return false;
		} else if (!avatarLarge.equals(other.avatarLarge))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (profileImage == null) {
			if (other.profileImage != null)
				return false;
		} else if (!profileImage.equals(other.profileImage))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (sinaNickname == null) {
			if (other.sinaNickname != null)
				return false;
		} else if (!sinaNickname.equals(other.sinaNickname))
			return false;
		if (sinaUid == null) {
			if (other.sinaUid != null)
				return false;
		} else if (!sinaUid.equals(other.sinaUid))
			return false;
		if (userDomain == null) {
			if (other.userDomain != null)
				return false;
		} else if (!userDomain.equals(other.userDomain))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (weiboUrl == null) {
			if (other.weiboUrl != null)
				return false;
		} else if (!weiboUrl.equals(other.weiboUrl))
			return false;
		return true;
	}
}
