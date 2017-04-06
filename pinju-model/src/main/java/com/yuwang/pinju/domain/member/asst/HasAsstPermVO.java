package com.yuwang.pinju.domain.member.asst;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>子账号权限判断数据对象</p>
 * @author gaobaowen
 * @since 2011-12-22 14:14:37
 */
public class HasAsstPermVO {

	private final static Log log = LogFactory.getLog(HasAsstPermVO.class);

	/**
	 * 会员编号
	 */
	private Long memberId;

	/**
	 * 权限目标
	 */
	private String target;

	/**
	 * 权限行为
	 */
	private String action;

	public HasAsstPermVO(Long memberId, String target, String action) {
		this.memberId = memberId;
		this.target = target;
		this.action = action;
	}

	public String wrap() {
		return ';' + target + ':' + action + ';';
	}

	public boolean hasEmpty() {
		return StringUtils.isBlank(target) || StringUtils.isBlank(action);
	}

	public boolean hasAsstPerm(String asstPerms, Long memberId) {
		if (StringUtils.isBlank(asstPerms)) {
			log.warn("[hasAsstPerm] assistant permissions data is empty, member id: [" + memberId + "]");
			return false;
		}
		int offset = asstPerms.indexOf(wrap());
		if (offset < 0) {
			return false;
		}
		return true;
	}

	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = StringUtils.trim(target);
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = StringUtils.trim(action);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		HasAsstPermVO other = (HasAsstPermVO) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HasAsstPermVO [memberId=" + memberId + ", target=" + target + ", action=" + action + "]";
	}
}