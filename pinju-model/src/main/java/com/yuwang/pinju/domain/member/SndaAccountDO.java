package com.yuwang.pinju.domain.member;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>盛大通行证账号信息</p>
 *
 * @author gaobaowen
 * @since 2011-7-11 下午01:51:45
 */
public class SndaAccountDO {

	/**
	 * 盛大通行证数字账号
	 */
	private String sndaId;

	/**
	 * 盛大通行证数字账号所对应的账号
	 */
	private Map<String, String> sndaAccounts;

	public SndaAccountDO(String sndaId) {
		this.sndaId = sndaId;
		this.sndaAccounts = new HashMap<String, String>();
	}

	public void addSndaAccount(String accountType, String account) {
		sndaAccounts.put(accountType, account);
	}

	/**
	 * <p>根据盛大通行证账号类型获取该账号</p>
	 *
	 * @param accountType <p>账号类型，允许的类型有</p>
	 * <ul>
	 * 	<li>{@link MemberDO#SNDA_ACCOUNT_KEY_PT MemberDO.SNDA_ACCOUNT_KEY_PT} -- 盛大通行证 PT 账号</li>
	 * 	<li>{@link MemberDO#SNDA_ACCOUNT_KEY_MOBILE MemberDO.SNDA_ACCOUNT_KEY_MOBILE} -- 盛大通行证手机账号</li>
	 * 	<li>{@link MemberDO#SNDA_ACCOUNT_KEY_EMAIL MemberDO.SNDA_ACCOUNT_KEY_EMAIL} -- 盛大通行证邮箱账号</li>
	 * 	<li>{@link MemberDO#SNDA_ACCOUNT_KEY_SNDAMAIL MemberDO.SNDA_ACCOUNT_KEY_SNDAMAIL} -- 盛大通行证 SNDA 邮箱账号</li>
	 * </ul>
	 *
	 * @return 账号名，没有指定类型的账号时返回 null 值
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 下午01:52:24
	 */
	public String getSndaAccount(String accountType) {
		return sndaAccounts.get(accountType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sndaAccounts == null) ? 0 : sndaAccounts.hashCode());
		result = prime * result + ((sndaId == null) ? 0 : sndaId.hashCode());
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
		SndaAccountDO other = (SndaAccountDO) obj;
		if (sndaAccounts == null) {
			if (other.sndaAccounts != null)
				return false;
		} else if (!sndaAccounts.equals(other.sndaAccounts))
			return false;
		if (sndaId == null) {
			if (other.sndaId != null)
				return false;
		} else if (!sndaId.equals(other.sndaId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "SndaMemberInfoDO [sndaId=" + sndaId + ", sndaAccounts="
				+ (sndaAccounts != null ? toString(sndaAccounts.entrySet(), maxLen) : null) + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}
}
