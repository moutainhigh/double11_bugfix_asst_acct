package com.yuwang.pinju.domain.member.security;

import java.util.Date;

import com.yuwang.pinju.common.RelationEntity;
import com.yuwang.pinju.common.StringUtil;

/**
 * <p>会员安全手机信息</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 09:36:26
 */
public class MemberSecurityMobileDO implements java.io.Serializable, RelationEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 会员编号（MEMBER_MEMBER.MEMBER_ID）
     */
    private Long memberId;

    /**
     * 会员登录名
     */
    private String loginName;

    /**
     * 会员验证手机号码
     */
    private String mobile;

    /**
     * 验证手机号的消息编号（MEMBER_SECURITY_MOBILE_CODE.MESSAGE_ID）
     */
    private String messageId;

    /**
     * 验证手机号确认客户端IP
     */
    private String validationIp;

    /**
     * 版本号
     */
    private Integer version = 0;

    private Date gmtCreate;

    private Date gmtModified;
    
    public MemberSecurityMobileDO() {
    }

	public static MemberSecurityMobileDO createTemp(String mobile) {
		MemberSecurityMobileDO securityMobile = new MemberSecurityMobileDO();
		securityMobile.setMemberId(999999999999999L);
		securityMobile.setLoginName("[TEMP]");
		securityMobile.setMobile(mobile);
		return securityMobile;
	}

	@Override
	public boolean isNew() {
		return (id == null);
	}

    public Long getId(){
        return id;
    }

    public Long getMemberId(){
        return memberId;
    }

    public String getLoginName(){
        return loginName;
    }

    public String getMobile(){
        return mobile;
    }

    public String getMessageId(){
        return messageId;
    }

    public String getValidationIp(){
        return validationIp;
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

    public void setLoginName(String loginName){
        this.loginName = loginName;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public void setMessageId(String messageId){
        this.messageId = messageId;
    }

    public void setValidationIp(String validationIp){
        this.validationIp = validationIp;
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
    
    public String getHideMobile() {
    	return StringUtil.hideMobile(mobile);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((validationIp == null) ? 0 : validationIp.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		MemberSecurityMobileDO other = (MemberSecurityMobileDO) obj;
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
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (validationIp == null) {
			if (other.validationIp != null)
				return false;
		} else if (!validationIp.equals(other.validationIp))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberSecurityMobile [id=" + id + ", memberId=" + memberId + ", loginName=" + loginName + ", mobile="
				+ mobile + ", messageId=" + messageId + ", validationIp=" + validationIp + ", version=" + version
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}

