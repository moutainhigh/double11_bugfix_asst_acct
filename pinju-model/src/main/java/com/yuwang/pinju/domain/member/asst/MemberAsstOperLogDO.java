package com.yuwang.pinju.domain.member.asst;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * <p>会员子账号操作日志</p>
 * @author gaobaowen
 * @since 2011-12-16 13:22:26
 */
public class MemberAsstOperLogDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 主账号会员编号（MEMBER_MEMBER.MEMBER_ID）
     */
    private Long masterMemberId;

    /**
     * 主账号会员名
     */
    private String masterLoginName;

    /**
     * 子账号会员编号
     */
    private Long asstMemberId;

    /**
     * 子账号登录名
     */
    private String asstLoginName;

    /**
     * 操作目标（MEMBER_ASST_PERMISSION.TARGET）
     */
    private String target;

    /**
     * 操作功能（MEMBER_ASST_PERMISSION.ACTION）
     */
    private String action;

    /**
     * 权限目标描述
     */
    private String targetDesc;

    /**
     * 目标行为描述
     */
    private String actionDesc;

    /**
     * 操作日志
     */
    private String operationLog;

    /**
     * 操作IP
     */
    private String operationIp;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * <p>检查操作日志的数据项是否有空</p>
     *
     * @return
     *
     * @author gaobaowen
     * @since 2011-12-23 14:32:16
     */
    public boolean hasEmpty() {
    	if (masterMemberId == null) {
    		return true;
    	}
    	if (asstMemberId == null) {
    		return true;
    	}
    	if (StringUtils.isBlank(asstLoginName)) {
    		return true;
    	}
    	if (StringUtils.isBlank(target)) {
    		return true;
    	}
    	if (StringUtils.isBlank(action)) {
    		return true;
    	}
    	if (StringUtils.isBlank(targetDesc)) {
    		return true;
    	}
    	if (StringUtils.isBlank(actionDesc)) {
    		return true;
    	}
    	if (StringUtils.isBlank(operationLog)) {
    		return true;
    	}
    	return false;
    }

    public Long getId(){
        return id;
    }

    public Long getMasterMemberId(){
        return masterMemberId;
    }

    public Long getAsstMemberId(){
        return asstMemberId;
    }

    public String getAsstLoginName(){
        return asstLoginName;
    }

    public String getTarget(){
        return target;
    }

    public String getAction(){
        return action;
    }

    public String getOperationLog(){
        return operationLog;
    }

    public String getOperationIp(){
        return operationIp;
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

    public void setMasterMemberId(Long masterMemberId){
        this.masterMemberId = masterMemberId;
    }

    public void setAsstMemberId(Long asstMemberId){
        this.asstMemberId = asstMemberId;
    }

    public void setAsstLoginName(String asstLoginName){
        this.asstLoginName = asstLoginName;
    }

    public void setTarget(String target){
        this.target = target;
    }

    public void setAction(String action){
        this.action = action;
    }

    public void setOperationLog(String operationLog){
    	String log = StringUtils.trim(operationLog);
    	if (log != null && log.length() > 500) {
    		log = log.substring(0, 495) + "...";
    	}
        this.operationLog = log;
    }

    public void setOperationIp(String operationIp){
        this.operationIp = operationIp;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

	public String getTargetDesc() {
		return targetDesc;
	}

	public void setTargetDesc(String targetDesc) {
		this.targetDesc = targetDesc;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public String getMasterLoginName() {
		return masterLoginName;
	}

	public void setMasterLoginName(String masterLoginName) {
		this.masterLoginName = masterLoginName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((actionDesc == null) ? 0 : actionDesc.hashCode());
		result = prime * result + ((asstLoginName == null) ? 0 : asstLoginName.hashCode());
		result = prime * result + ((asstMemberId == null) ? 0 : asstMemberId.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((masterMemberId == null) ? 0 : masterMemberId.hashCode());
		result = prime * result + ((masterLoginName == null) ? 0 : masterLoginName.hashCode());
		result = prime * result + ((operationIp == null) ? 0 : operationIp.hashCode());
		result = prime * result + ((operationLog == null) ? 0 : operationLog.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((targetDesc == null) ? 0 : targetDesc.hashCode());
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
		MemberAsstOperLogDO other = (MemberAsstOperLogDO) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (actionDesc == null) {
			if (other.actionDesc != null)
				return false;
		} else if (!actionDesc.equals(other.actionDesc))
			return false;
		if (asstLoginName == null) {
			if (other.asstLoginName != null)
				return false;
		} else if (!asstLoginName.equals(other.asstLoginName))
			return false;
		if (asstMemberId == null) {
			if (other.asstMemberId != null)
				return false;
		} else if (!asstMemberId.equals(other.asstMemberId))
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
		if (masterMemberId == null) {
			if (other.masterMemberId != null)
				return false;
		} else if (!masterMemberId.equals(other.masterMemberId))
			return false;
		if (masterLoginName == null) {
			if (other.masterLoginName != null)
				return false;
		} else if (!masterLoginName.equals(other.masterLoginName))
			return false;
		if (operationIp == null) {
			if (other.operationIp != null)
				return false;
		} else if (!operationIp.equals(other.operationIp))
			return false;
		if (operationLog == null) {
			if (other.operationLog != null)
				return false;
		} else if (!operationLog.equals(other.operationLog))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (targetDesc == null) {
			if (other.targetDesc != null)
				return false;
		} else if (!targetDesc.equals(other.targetDesc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberAsstOperLogDO [id=" + id + ", masterMemberId=" + masterMemberId + ", masterMemberLoginName="
				+ masterLoginName + ", asstMemberId=" + asstMemberId + ", asstLoginName=" + asstLoginName
				+ ", target=" + target + ", action=" + action + ", targetDesc=" + targetDesc + ", actionDesc="
				+ actionDesc + ", operationLog=" + operationLog + ", operationIp=" + operationIp + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}

