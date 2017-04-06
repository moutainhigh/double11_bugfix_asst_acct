package com.yuwang.pinju.core.member.manager;

import com.yuwang.pinju.domain.member.SndaAccountDO;

/**
 * <p>盛大通行证管理</p>
 *
 * @author gaobaowen
 * @since 2011-7-11 14:07:35
 */
public interface SndaManager {

	/**
	 * <p>根据盛大通行证数字账号获取盛大通行证的账号信息</p>
	 *
	 * @param sndaId  盛大通行证数字账号
	 * @return  盛大通行证账号信息，若无法获取时返回 null 值
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 14:07:44
	 */
	SndaAccountDO getSndaAccount(String sndaId);
}
