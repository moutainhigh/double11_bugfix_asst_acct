package com.yuwang.pinju.domain.member.asst;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.domain.Paginator;

/**
 * <p>会员子账号操作日志</p>
 * @author gaobaowen
 * @since 2011-12-16 13:22:26
 */
public class MemberAsstOperLogQuery extends Paginator {

    private static final long serialVersionUID = 1L;

    /**
     * 主账号会员编号（MEMBER_MEMBER.MEMBER_ID）
     */
    private Long masterMemberId;

    private String inputAsstMemberId;

    private Long asstMemberId;

	/**
     * 子账号登录名
     */
    private String asstLoginName;
    
    private String inputStartTime;
    
	private String inputEndTime;
	
	private String paction;
	
	private String target;

	private Date startTime;

    private Date endTime;
    
    public boolean validateParam(long dAsstMemberId, String first) {
    	if (dAsstMemberId == -1) {
    		return false;
    	}
    	asstMemberId = dAsstMemberId;
    	
    	if ("first".equals(first)) {
    		Date current = new Date();
    		inputStartTime = DateUtil.formatDate("yyyy-MM-dd", current);
    		inputEndTime = inputStartTime;
    	}
    	
    	if (StringUtil.isNotBlank(inputStartTime)) {
    		startTime = DateUtil.parse("yyyy-MM-dd", inputStartTime);
    		if (startTime == null) {
    			return false;
    		}
    	}
    	if (StringUtil.isNotBlank(inputEndTime)) {
    		endTime = DateUtil.parse("yyyy-MM-dd", inputEndTime);
    		if (endTime == null) {
    			return false;
    		}
    	}
    	return true;
    }

	public Long getMasterMemberId() {
		return masterMemberId;
	}

	public void setMasterMemberId(Long masterMemberId) {
		this.masterMemberId = masterMemberId;
	}

	public String getAsstLoginName() {
		return asstLoginName;
	}

	public void setAsstLoginName(String asstLoginName) {
		this.asstLoginName = asstLoginName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getInputAsstMemberId() {
		return inputAsstMemberId;
	}
	public void setInputAsstMemberId(String inputAsstMemberId) {
		this.inputAsstMemberId = inputAsstMemberId;
	}
	public Long getAsstMemberId() {
		return asstMemberId;
	}
	public void setAsstMemberId(Long asstMemberId) {
		this.asstMemberId = asstMemberId;
	}
	
	public String getInputStartTime() {
		return inputStartTime;
	}

	public void setInputStartTime(String inputStartTime) {
		this.inputStartTime = inputStartTime;
	}

	public String getInputEndTime() {
		return inputEndTime;
	}

	public void setInputEndTime(String inputEndTime) {
		this.inputEndTime = inputEndTime;
	}
	
	public String getPaction() {
		return paction;
	}

	public void setPaction(String paction) {
		this.paction = paction;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}

