package com.yuwang.pinju.web.cookie.convert;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.domain.member.MemberDO;

/**
 * <p>会员编号转换器</p>
 *
 * @author gaobaowen
 * @since 2011-7-14 上午11:30:24
 */
public class MemeberIdConvert extends BasicConvert<Long> {

	@Override
	public String encode(Long obj) {
		return MemberIdPuzzle.encode(obj == null ? 0 : obj);
	}

	@Override
	public Long decode(String value) {
		Long memberId = MemberIdPuzzle.decode(value);
		if (memberId == null || memberId < MemberDO.MIN_MEMBER_ID || memberId > MemberDO.MAX_MEMBER_ID) {
			log.warn("decode member id error, value: " + value + ", decode value: " + memberId);
			return null;
		}
		return memberId;
	}
}
