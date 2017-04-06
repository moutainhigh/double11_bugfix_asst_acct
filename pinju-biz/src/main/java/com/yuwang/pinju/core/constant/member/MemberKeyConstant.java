package com.yuwang.pinju.core.constant.member;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.service.MemberService;
import com.yuwang.pinju.domain.member.DsrStatDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;

public interface MemberKeyConstant {

	/**
	 * 会员 -- 卖家资质信息（{@link SellerQualityDO}）
	 */
	String KEY_MEMBER_SHOPINFO_SELLER_QUALITY = "KEY_MEMBER_SHOPINFO_SELLER_QUALITY";

	/**
	 * 会员 -- 卖家 DSR 统计信息（List<{@link DsrStatDO}>）
	 */
	String KEY_MEMBER_SHOPINFO_DSR_STAT = "KEY_MEMBER_SHOPINFO_DSR_STAT";

	/**
	 * 会员 -- 会员社区信息（{@link MemberSnsInfoDO}）
	 */
	String KEY_MEMBER_SNS_INFO = "KEY_MEMBER_SNS_INFO";

	/**
	 * 会员 -- 更新受影响的数量
	 */
	String KEY_MEMBER_UPDATE_COUNT = "KEY_UPDATE_COUNT";

	String KEY_MEMBER_INFO_VERSION = "KEY_MEMBER_INFO_VERSION";

	/**
	 * {@link MemberService#getMemberSnsInfo(long)} 方法 {@link Result} 响应的数据键名
	 */
	String KEY_SERVICE_MEMBER_READER_RESPONSE = "KEY_SERVICE_MEMBER_READER_RESPONSE";

	/**
	 * 会员 -- 会员支付账户（{@link MemberPaymentDO}）
	 */
	String KEY_MEMBER_PAYMENT = "KEY_MEMBER_PAYMENT";

	/**
	 * 品聚会员登录 -- 登录次数超过限制是否需要显示验证码
	 */
	String KEY_LOGIN_OVER_ERROR_COUNT = "KEY_LOGIN_OVER_ERROR_COUNT";

	/**
	 * 布尔值结果
	 */
	String KEY_BOOLEAN = "KEY_BOOLEAN";
}
