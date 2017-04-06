package com.yuwang.pinju.domain.member.security;

import java.util.Date;

import com.yuwang.pinju.common.RelationEntity;

/**
 * <p>会员安全邮箱信息历史记录</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 09:38:17
 */
public class MemberSecurityEmailHisDO implements java.io.Serializable, RelationEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 绑定状态 -- 绑定（1）
     */
    public final static Integer STATUS_BOUND = 1;

    /**
     * 绑定状态 -- 解绑（2）
     */
    public final static Integer STATUS_UNBOUND = 2;

    public static MemberSecurityEmailHisDO createInstance(MemberSecurityEmailDO email, int status) {
    	MemberSecurityEmailHisDO his = new MemberSecurityEmailHisDO();
    	his.memberId = email.getMemberId();
    	his.loginName = email.getLoginName();
    	his.emailAddr = email.getEmailAddr();
    	his.messageId = email.getMessageId();
    	his.validationIp = email.getValidationIp();
    	his.status = status;
    	return his;
    }

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

    /**
     * 验证邮件的消息编号（MEMBER_SECURITY_EMAIL_LINK.MESSAGE_ID）
     */
    private String messageId;

    /**
     * 验证邮件确认客户端IP
     */
    private String validationIp;

    /**
     * 历史状态（1：绑定；2：解绑）
     */
    private Integer status = STATUS_BOUND;

    private Date gmtCreate;

    private Date gmtModified;

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

    public String getEmailAddr(){
        return emailAddr;
    }

    public String getMessageId(){
        return messageId;
    }

    public String getValidationIp(){
        return validationIp;
    }

    public Integer getStatus(){
        return status;
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

    public void setMessageId(String messageId){
        this.messageId = messageId;
    }

    public void setValidationIp(String validationIp){
        this.validationIp = validationIp;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailAddr == null) ? 0 : emailAddr.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((validationIp == null) ? 0 : validationIp.hashCode());
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
		MemberSecurityEmailHisDO other = (MemberSecurityEmailHisDO) obj;
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
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (validationIp == null) {
			if (other.validationIp != null)
				return false;
		} else if (!validationIp.equals(other.validationIp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberSecurityEmailHis [id=" + id + ", memberId=" + memberId + ", loginName=" + loginName
				+ ", emailAddr=" + emailAddr + ", messageId=" + messageId + ", validationIp=" + validationIp
				+ ", status=" + status + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}

