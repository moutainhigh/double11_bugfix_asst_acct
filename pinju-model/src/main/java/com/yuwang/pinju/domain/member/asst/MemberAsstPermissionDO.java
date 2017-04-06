package com.yuwang.pinju.domain.member.asst;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;

/**
 * <p>会员子账号权限</p>
 * @author gaobaowen
 * @since 2011-12-16 14:27:08
 */
public class MemberAsstPermissionDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限状态 -- 有效（0）
     */
    public final static Integer STATUS_VALID = 0;

    /**
     * 权限状态 -- 无效（1）
     */
    public final static Integer STATUS_INVALID = 1;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限目标
     */
    private String target;

    /**
     * 在权限目标上的行为
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
     * 权限详细说明
     */
    private String permissionDesc;

    /**
     * 权限状态（0：有效；1：无效）
     */
    private Integer status = STATUS_VALID;
    
	private Date gmtCreate;

    private Date gmtModified;
    
    public MemberAsstPermissionJsonVO createMemberAsstPermissionTypeJsonVO() {
    	MemberAsstPermissionJsonVO jsonVo = new MemberAsstPermissionJsonVO();
    	jsonVo.setId(target);
    	jsonVo.setpId("0");
    	jsonVo.setName(targetDesc);
    	jsonVo.setAction("");
    	jsonVo.setTarget("");
    	jsonVo.setOpen(true);
    	return jsonVo;
    }
    
    public MemberAsstPermissionTreeVO createMemberAsstPermissionTreeVO(boolean islabel) {
    	MemberAsstPermissionTreeVO treeVo = new MemberAsstPermissionTreeVO();
    	treeVo.setAction(action);
    	treeVo.setActionDesc(actionDesc);
    	treeVo.setTarget(target);
    	treeVo.setTargetDesc(targetDesc);
    	treeVo.setLabel(islabel);
    	return treeVo;
    }
    
    public MemberAsstPermissionJsonVO createMemberAsstPermissionJsonVO() {
    	MemberAsstPermissionJsonVO jsonVo = new MemberAsstPermissionJsonVO();
    	jsonVo.setId(String.valueOf(id));
    	jsonVo.setpId(target);
    	jsonVo.setName(actionDesc);
    	jsonVo.setAction(action);
    	jsonVo.setTarget(target);
    	return jsonVo;
    }

    public Integer getId(){
        return id;
    }

    public String getTarget(){
        return target;
    }

    public String getAction(){
        return action;
    }

    public String getTargetDesc(){
        return targetDesc;
    }

    public String getActionDesc(){
        return actionDesc;
    }

    public String getPermissionDesc(){
        return permissionDesc;
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

    public void setId(Integer id){
        this.id = id;
    }

    public void setTarget(String target){
        this.target = target;
    }

    public void setAction(String action){
        this.action = action;
    }

    public void setTargetDesc(String targetDesc){
        this.targetDesc = targetDesc;
    }

    public void setActionDesc(String actionDesc){
        this.actionDesc = actionDesc;
    }

    public void setPermissionDesc(String permissionDesc){
        this.permissionDesc = permissionDesc;
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
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((actionDesc == null) ? 0 : actionDesc.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((permissionDesc == null) ? 0 : permissionDesc.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		MemberAsstPermissionDO other = (MemberAsstPermissionDO) obj;
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
		if (permissionDesc == null) {
			if (other.permissionDesc != null)
				return false;
		} else if (!permissionDesc.equals(other.permissionDesc))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		return "MemberAsstPermissionDO [id=" + id + ", target=" + target + ", action=" + action + ", targetDesc="
				+ targetDesc + ", actionDesc=" + actionDesc + ", permissionDesc=" + permissionDesc + ", status="
				+ status + ", gmtCreate=" + DateUtil.formatDatetime(gmtCreate) + ", gmtModified="
				+ DateUtil.formatDatetime(gmtModified) + "]";
	}
}

