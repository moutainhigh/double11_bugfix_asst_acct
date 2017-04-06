package com.yuwang.pinju.domain.member.security;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;

/**
 * <p>会员安全邮箱地址</p>
 *
 * @author gaobaowen
 * @since 2011-9-1 10:27:54
 */
public class MemberSecurityEmailDO implements java.io.Serializable {

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
     * 会员安全邮箱地址
     */
    private String emailAddr;

    private String messageId;
    
    private String validationIp;
    
    private Integer version;
    
    private Date gmtCreate;


    private Date gmtModified;


    public Long getId(){
        return id;
    }

    public Long getMemberId(){
        return memberId;
    }

    public String getLoginName(){
        return loginName;
    }

    public String getEmailAddr(){
        return emailAddr;
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

    public void setEmailAddr(String emailAddr){
        this.emailAddr = emailAddr;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }


	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getValidationIp() {
		return validationIp;
	}

	public void setValidationIp(String validationIp) {
		this.validationIp = validationIp;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailAddr == null) ? 0 : emailAddr.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result
				+ ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result
				+ ((validationIp == null) ? 0 : validationIp.hashCode());
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
		MemberSecurityEmailDO other = (MemberSecurityEmailDO) obj;
		if (emailAddr == null) {
			if (other.emailAddr != null)
				return false;
		} else if (!emailAddr.equals(other.emailAddr))
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
		return "MemberSecurityEmailDO [id=" + id + ", memberId=" + memberId + ", loginName=" + loginName + ", emailAddr="
				+ emailAddr + ",messageId= " + messageId + ", validationIp=" + validationIp + ",version= " + version 
				+ ", gmtCreate=" + DateUtil.formatDatetime(gmtCreate) + ", gmtModified=" + DateUtil.formatDatetime(gmtModified) + "]";
	}
}

