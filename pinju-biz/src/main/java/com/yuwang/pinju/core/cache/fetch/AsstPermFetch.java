package com.yuwang.pinju.core.cache.fetch;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.cache.CacheFetcher;
import com.yuwang.pinju.core.member.manager.MemberAsstManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.asst.MemberAsstMemberRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;

/**
 * <p>子账号权限数据抓取</p>
 *
 * @author gaobaowen
 * @since 2011-12-22 10:08:04
 */
public class AsstPermFetch implements CacheFetcher {

	public final static String NO_PERMISSION = "NO_PERMISSION";

	private final static Log log = LogFactory.getLog(AsstPermFetch.class);

	private MemberAsstManager memberAsstManager;

	public void setMemberAsstManager(MemberAsstManager memberAsstManager) {
		this.memberAsstManager = memberAsstManager;
	}

	@Override
	public Object fetch(Object condition) {
		if (condition == null) {
			log.warn("condition is null, cannot fetch data");
			return null;
		}
		if (!(condition instanceof Long)) {
			log.warn("condition isnot Long type, cannot fetch data, condition: [" + condition + "], class: [" + condition.getClass().getName() + "]");
			return null;
		}
		try {
			Long memberId = (Long)condition;

			List<MemberAsstMemberRoleDO> memberRoles = memberAsstManager.getAsstRoles(memberId);
			if (EmptyUtil.isBlank(memberRoles)) {
				log.warn("according to assistant member id [" + condition + "] cannot find any MemberAsstMemberRole data");
				return NO_PERMISSION;
			}
			Set<String> permissions = new TreeSet<String>();
			for (MemberAsstMemberRoleDO memberRole : memberRoles) {
				MemberAsstRoleDO role = memberAsstManager.findMemberAsstRoleById(memberRole.getAsstRoleId());
				if (role == null) {
					log.warn("according to assistant ");
					continue;
				}
				addPermissions(permissions, role);
			}
			MemberAsstRelationDO relation = memberAsstManager.getMemberAsstRelationByAsst(memberId);
			return wrap(relation, permissions);
		} catch (Exception e) {
			log.error("fetch cache data, assistant member id: [" + condition + "]", e);
			return null;
		}
	}

	private void addPermissions(Set<String> permissions, MemberAsstRoleDO role) {
		String[] perms = role.getPermissions().split(";");
		for (String perm : perms) {
			if (StringUtils.isBlank(perm)) {
				continue;
			}
			permissions.add(perm.trim().toLowerCase());
		}
	}

	private String wrap(MemberAsstRelationDO relation, Set<String> permissions) {
		StringBuilder builder = new StringBuilder(permissions.size() * 22);
		if (relation != null) {
			builder.append(relation.getMasterMemberId());
			builder.append(",").append(relation.getMasterLoginName());
		}
		builder.append("|;");
		for (String permission : permissions) {
			builder.append(permission).append(';');
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		String str = ";1:2;3:4;;";
		System.out.println(Arrays.toString(str.split("\\s*;\\s*")));
	}
}
