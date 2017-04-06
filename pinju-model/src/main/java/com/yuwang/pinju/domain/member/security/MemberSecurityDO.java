package com.yuwang.pinju.domain.member.security;

import java.util.Date;

import com.yuwang.pinju.common.RelationEntity;

/**
 * <p>会员安全信息</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 09:35:23
 */
public class MemberSecurityDO implements java.io.Serializable, RelationEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱掩码
     */
    public final static int MASK_EMAIL = 1 << 0;

    /**
     * 手机掩码
     */
    public final static int MASK_MOBILE = 1 << 1;

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
     * 安全验证掩码（1<<0：邮箱；1<<1：手机）
     */
    private Integer securityAuthMask = 0;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次登录IP
     */
    private String lastLoginIp;

    /**
     * 当前登录时间
     */
    private Date currentLoginTime;

    /**
     * 当前登录IP
     */
    private String currentLoginIp;

    /**
     * 数据版本号
     */
    private Integer version = 0;


    private Date gmtCreate;


    private Date gmtModified;

    @Override
	public boolean isNew() {
		return (id == null);
	}
    
    /**
     * 获取会员安全级别
     * @return
     */
    public int getLevel() {
    	int level = 0;
		if (hasSecurityMobile()) {
			level ++;
		}
		if (hasSecurityEmail()) {
			level ++ ;
		}
		return level;
    }

    /**
     * <p>会员是否已验证过邮箱</p>
     *
     * @return
     *
     * @author gaobaowen
     * @since 2011-11-21 09:43:48
     */
    public boolean hasSecurityEmail() {
    	return mask(MASK_EMAIL);
    }

    /**
     * <p>会员是否已验证过手机号码</p>
     *
     * @return
     *
     * @author gaobaowen
     * @since 2011-11-21 09:44:06
     */
    public boolean hasSecurityMobile() {
    	return mask(MASK_MOBILE);
    }

    /**
     * <p>给会员添加上邮箱验证的标记</p>
     *
     * @return 当前会员所拥有的安全值
     *
     * @author gaobaowen
     * @since 2011-11-21 09:44:22
     */
    public int addSecurityEmailMask() {
    	return maskValue(MASK_EMAIL);
    }
    
    /**
     * <p>给会员去除邮箱验证的标记</p>
     * 
     * @return 当前会员所拥有的安全值
     */
    public int delSecurityEmailMask() {
    	return delMaskValue(MASK_EMAIL);
    }

    /**
     * <p>给会员添加上手机号码验证的标记</p>
     *
     * @return 当前会员所拥有的安全值
     *
     * @author gaobaowen
     * @since 2011-11-21 09:45:08
     */
    public int addSecurityMobileMask() {
    	return maskValue(MASK_MOBILE);
    }
    
    /**
     * <p>给会员去除手机验证的标记</p>
     * 
     * @return 当前会员所拥有的安全值
     */
    public int delSecurityMobileMask() {
    	return delMaskValue(MASK_MOBILE);
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

    public Integer getSecurityAuthMask(){
        return securityAuthMask;
    }

    public Date getLastLoginTime(){
        return lastLoginTime;
    }

    public String getLastLoginIp(){
        return lastLoginIp;
    }

    public Date getCurrentLoginTime(){
        return currentLoginTime;
    }

    public String getCurrentLoginIp(){
        return currentLoginIp;
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

    public void setSecurityAuthMask(Integer securityAuthMask){
        this.securityAuthMask = securityAuthMask;
    }

    public void setLastLoginTime(Date lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }

    public void setLastLoginIp(String lastLoginIp){
        this.lastLoginIp = lastLoginIp;
    }

    public void setCurrentLoginTime(Date currentLoginTime){
        this.currentLoginTime = currentLoginTime;
    }

    public void setCurrentLoginIp(String currentLoginIp){
        this.currentLoginIp = currentLoginIp;
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

    private boolean mask(int maskValue) {
    	return (securityAuthMask & maskValue) != 0;
    }

    private int maskValue(int maskValue) {
    	securityAuthMask |= maskValue;
    	return securityAuthMask;
    }
    
    private int delMaskValue(int maskValue) {
    	securityAuthMask = (~maskValue) & securityAuthMask;
    	return securityAuthMask;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentLoginIp == null) ? 0 : currentLoginIp.hashCode());
		result = prime * result + ((currentLoginTime == null) ? 0 : currentLoginTime.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLoginIp == null) ? 0 : lastLoginIp.hashCode());
		result = prime * result + ((lastLoginTime == null) ? 0 : lastLoginTime.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((securityAuthMask == null) ? 0 : securityAuthMask.hashCode());
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
		MemberSecurityDO other = (MemberSecurityDO) obj;
		if (currentLoginIp == null) {
			if (other.currentLoginIp != null)
				return false;
		} else if (!currentLoginIp.equals(other.currentLoginIp))
			return false;
		if (currentLoginTime == null) {
			if (other.currentLoginTime != null)
				return false;
		} else if (!currentLoginTime.equals(other.currentLoginTime))
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
		if (lastLoginIp == null) {
			if (other.lastLoginIp != null)
				return false;
		} else if (!lastLoginIp.equals(other.lastLoginIp))
			return false;
		if (lastLoginTime == null) {
			if (other.lastLoginTime != null)
				return false;
		} else if (!lastLoginTime.equals(other.lastLoginTime))
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
		if (securityAuthMask == null) {
			if (other.securityAuthMask != null)
				return false;
		} else if (!securityAuthMask.equals(other.securityAuthMask))
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
		return "MemberSecurity [id=" + id + ", memberId=" + memberId + ", loginName=" + loginName
				+ ", securityAuthMask=" + securityAuthMask + ", lastLoginTime=" + lastLoginTime + ", lastLoginIp="
				+ lastLoginIp + ", currentLoginTime=" + currentLoginTime + ", currentLoginIp=" + currentLoginIp
				+ ", version=" + version + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}

