package com.yuwang.pinju.core.member.service;

import com.yuwang.pinju.domain.api.MemberReaderResponseDO;

public interface MemberService {

	/**
	 * <p>参数数据签名校验</p>
	 *
	 * @param sign
	 * @param params
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-19 14:33:27
	 */
	boolean checkSign(String sign, String... params);

	/**
	 * <p>通过会员编号获取社区需要的会员信息</p>
	 *
	 * @param memberId  会员编号
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-19 14:03:51
	 */
	MemberReaderResponseDO snsRequestMemberInfo(long memberId);
}
