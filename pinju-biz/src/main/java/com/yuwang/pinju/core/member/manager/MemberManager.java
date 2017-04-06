package com.yuwang.pinju.core.member.manager;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.cache.CacheUtil;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.member.DsrStatDO;
import com.yuwang.pinju.domain.member.MemberAllDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;
import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;

/**
 * <p>
 * 会员基本信息、会员个人资料、会员收货地址等信息管理
 * </p>
 *
 * @author gaobaowen 2011-6-8 上午10:39:57
 */
public interface MemberManager {

	/**
	 * <p>
	 * 简单地检查一下会员编号（MID）是否有效。只检查该编号是否在会员编码的指定范围之内， 并不表示该会员编码是否存在。
	 * </p>
	 *
	 * @param memberId
	 * @return
	 *
	 * @author gaobaowen
	 */
	boolean isCorrectMemberId(Long memberId);

	/**
	 * <p>根据第三方唯一账号与会员来源查询会员信息</p>
	 *
	 * @param account  第三方唯一账号，即 {@link MemberDO#accountNamer}
	 * @param orign    会员来源（见 {@link MemberDO} 中以 MEMBER_ORIGINAL 开头的常量）
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-7-12 上午09:08:42
	 */
	MemberDO getMemberByAccount(String account, int origin) throws ManagerException;

	/**
	 * <p>保存会员信息数据</p>
	 *
	 * @param member
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-7-12 上午10:19:20
	 */
	MemberDO saveMember(final MemberDO member) throws ManagerException;

	/**
	 * <p>
	 * 会员登录管理。主要操作有：
	 * </p>
	 * <ul>
	 * <li>若会员在平台第一次登录，则创建该用户的基本信息资料</li>
	 * <li>记录用户的登录信息</li>
	 * </ul>
	 *
	 * @param ticket 第三方认证回传的会员登录 ticket 数据
	 *
	 * @return 若 ticket 验证不通过将返回 null 值
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	MemberDO login(MemberTicketDO ticket) throws ManagerException;

	/**
	 * <p>登录日志处理</p>
	 *
	 * @param memberLoginHis
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	MemberLoginHisDO logLogin(MemberLoginHisDO memberLoginHis) throws ManagerException;

	/**
	 * <p>更新会员基本信息</p>
	 *
	 * @param member
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 下午02:21:36
	 */
	int updateMember(MemberDO member) throws ManagerException;

	/**
	 * <p>会员注销操作，主要操作有：</p>
	 *
	 * <ul>
	 * <li>记录会员注销</li>
	 * </ul>
	 * @param sessionId    用户会话编号
	 * @param memberId     会员ID
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	int logout(String sessionId, long memberId, Date logoutTime) throws ManagerException;

	/**
	 * <p>
	 * 根据会员编号（MID）查找会员基本信息
	 * </p>
	 *
	 * @param memberId
	 * @return 没有找到时返回 null 值
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	MemberDO findMember(long memberId) throws ManagerException;

	/**
	 * <p>
	 * 根据会员昵称查找会员基本信息
	 * </p>
	 *
	 * @param nickname
	 *            需要查找的会员昵称
	 * @return 没有找到或者参数为空时返回 null 值
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	MemberDO findMemberByNickname(String nickname) throws ManagerException;

	/**
	 * <p>
	 * 根据会员编号（MID）查找会员的个人信息资料
	 * </p>
	 *
	 * @param memberId
	 * @return 没有找到时返回 null 值
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	MemberInfoDO findMemberInfo(long memberId) throws ManagerException;

	/**
	 * <p>根据邮箱查找会员信息</p>
	 *
	 * @param email 邮箱
	 * @return 未找到时返回 null 值，找到时对象中仅有 {@link MemberInfoDO#id} 和 {@link MemberInfoDO#memberId} 两项值
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-7-29 13:00:14
	 */
	MemberInfoDO getMemberInfoByEmail(String email) throws ManagerException;

	/**
	 * <p>
	 * 更新会员的个人资料。由于个人资料中的昵称、性别存放于会员基本信息中， 其他数据存放于会员个人资料中，因此更新会员个人资料时需要同时进行更新。
	 * </p>
	 *
	 * <p>
	 * 若会员个人信息数据不存在时，则为该会员将建一条数据
	 * </p>
	 *
	 * @param member
	 *            会员基本信息
	 * @param memberInfo
	 *            会员个人信息
	 * @return int[]：int[0] 会员基本信息更新的数据条数；int[1] 会员个人信息更新的数据条数（若为新增则为 1）
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	int[] updateMemberInfo(MemberDO member, MemberInfoDO memberInfo) throws ManagerException;

	/**
	 * <p>
	 * 根据会员编号（MID）查找该会员的收货地址信息。返回的数据按默认地址放在最前面， 非默认地址按收货地址的更新时间倒序进行排序。
	 * </p>
	 *
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	List<MemberDeliveryDO> findMemberDeliveries(long memberId) throws ManagerException;

	/**
	 * <p>根据会员收货地址 ID 获取收货地址</p>
	 *
	 * @param deliveryId
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	MemberDeliveryDO getMemberDeliveryById(long deliveryId) throws ManagerException;

	/**
	 * <p>
	 * 更新会员的收货地址
	 * </p>
	 *
	 * <p>
	 * NOTE: 若更新的会员收货地址设为默认地址时，在更新时会先将该用户已有的收货地址全部更新为非默认， 然后再更新该条数据
	 * </p>
	 *
	 * @param memberDelivery
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	int updateMemberDelivery(MemberDeliveryDO memberDelivery) throws ManagerException;

	/**
	 * <p>
	 * 保存会员的收货地址。保存会员资料时并不会检查会员已经拥有多少条地址数据了，若需要对收货地址进行数量限制， 需要在调用该方法之前调用 (@link
	 * MemberManager#countMemberDeliveries) 方法。
	 * </p>
	 *
	 * <p>
	 * NOTE: 详见 {@link MemberManager#updateMemberDelivery} 的 NOTE
	 * </p>
	 *
	 * @param memberDelivery
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	MemberDeliveryDO saveMemberDelivery(MemberDeliveryDO memberDelivery) throws ManagerException;

	/**
	 * <p>
	 * 根据会员编号（MID）统计会员目前有多少条收货地址数据
	 * </p>
	 *
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	int countMemberDeliveries(long memberId) throws ManagerException;

	/**
	 * <p>
	 * 根据会员编号（MID）删除该会员的一条收货地址数据。若删除的收货地址为默认地址时， 删除后该会员则不再有默认收货地址。
	 * </p>
	 *
	 * @param deliveryId
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	int removeMemberDelivery(long deliveryId, long memberId) throws ManagerException;

	/**
	 * <p>
	 * 根据收货地址ID和会员编号（MID）将会员的一条收货地址置为默认地址。由于每一位会员只允许有一个默认地址，
	 * 因此在设置时会将该用户基本的默认地址置为非默认，然后再将该地址更改为默认地址
	 * </p>
	 *
	 * @param deliveryId
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	int setDefaultDelivery(long deliveryId, long memberId) throws ManagerException;

	/**
	 * <p>根据会员编号查询会员绑定的支付账户信息</p>
	 *
	 * @param memberId 会员编号
	 * @return MemberPaymentDO 会员绑定并激活的支付账户。值为 null 时表示当前会员没有绑定支付账户
	 * @throws ManagerException
	 */
	MemberPaymentDO findBoundMemberPayment(MemberPaymentDO paymentQuery) throws ManagerException;

	/**
	 * <p>将会员的支付账户解绑</p>
	 *
	 * @param memberId 会员编号
	 * @param institution 支付机构名称
	 * @param paymentAccount 支付账号名
	 * @return 解绑的数量
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-7-20 下午01:10:31
	 */
	int unboundMemberPayment(MemberPaymentDO payment) throws ManagerException;

	/**
	 * <p>绑定会员的支付账号</p>
	 *
	 * @param payment
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-7-20 13:07:52
	 */
	MemberPaymentDO boundMemberPayment(MemberPaymentDO payment) throws ManagerException;

	/**
	 * <p>根据会员编号查询会员所有的相关信息</p>
	 *
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	MemberAllDO findMemberAll(long memberId) throws ManagerException;

	/**
	 * <p>初始化店铺资质信息</p>
	 *
	 * <p>若对象中的会员编号无效，或者该会员已有卖家资质信息时抛出异常。</p>
	 *
	 * @param sellerQuality
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	SellerQualityDO initSellerQuality(SellerQualityDO sellerQuality) throws ManagerException;

	/**
	 * <p>根据用户编号查找卖家资质信息</p>
	 *
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	SellerQualityDO getSellerQualityByMemberId(long memberId) throws ManagerException;

	/**
	 * <p>更新卖家资质信息</p>
	 *
	 * @param memberId  卖家会员编号
	 * @param sellerQuality 需要更新的数据。其中 id, memberId, nickname, shopId, gmtCreate, gmtModified 设置的值将被忽略
	 *
	 * @return 更新的数据行数，仅大于 0 时表示更新成功
	 *
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 15:35:46
	 */
	int updateSellerQuality(final long memberId, final SellerQualityDO sellerQuality) throws ManagerException;

	/**
	 * <p>根据会员编号获取该会员的 DSR 统计信息。若需要根据可用的 DSR 维度获取卖家的 DSR 统计数据需要使用 {@link #getIntactDsrStatsByMemberId} 方法。</p>
	 *
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	List<DsrStatDO> getDsrStatsByMemberId(long memberId) throws ManagerException;

	/**
	 * <p>根据会员编号获取完整的 DSR 统计数据。如果某一项 DSR 维度在该统计数据中不存在，则使用一个默认的 DSR 统计数据代替。</p>
	 *
	 * @param memberId  卖家会员编号
	 * @param isCacheDebug  是否使用缓存中读取的数据，详见 {@link CacheUtil#isReadCache(boolean)} 说明。 
	 *
	 * @return  根据 DSR 维度获取的卖家 DSR 统计数据
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-10-19 10:38:26
	 */
	List<DsrStatDO> getIntactDsrStatsByMemberId(long memberId, boolean isCacheDebug) throws ManagerException;

	/**
	 * <p>新插入一条会员社区信息。同时将会员资料信息版本号更新</p>
	 *
	 * @param member
	 * @param memberSnsInfo
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 下午03:47:32
	 */
	MemberSnsInfoDO insertMemberSnsInfo(MemberDO member, MemberSnsInfoDO memberSnsInfo) throws ManagerException;

	/**
	 * <p>更新会员社区信息。同时将会员资料信息版本号更新</p>
	 *
	 * @param member
	 * @param memberSnsInfo
	 * @return  更新的记录数（一般更新成功将会返回 1，否则会返回 0）
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 下午03:47:55
	 */
	int updateMemberSnsInfo(MemberDO member, MemberSnsInfoDO memberSnsInfo) throws ManagerException;

	/**
	 * <p>根据会员编号查询会员社区信息</p>
	 *
	 * @param memberId  会员编号
	 * @return  查询到的会员社区信息
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 下午03:48:29
	 */
	MemberSnsInfoDO getMemberSnsInfo(long memberId) throws ManagerException;

	/**
	 * Created on 2011-9-13
	 * @desc <p>Discription:[签订财付通代扣协议]</p>
	 * @param memberPaymentDO 会员编号
	 * @return void
	 * @author:[石兴]
	 * @update:[2011-9-13] [更改人姓名]
	 */
	int signPayAgreement(MemberPaymentDO memberPaymentDO) throws ManagerException;

	/**
	 * <p>更新会员支付账户信息，该更新需要有版本号的支持，需要在更新前填充对象中的 {@link MemberPaymentDO#version} 属性值</p>
	 *
	 * @param memberPayment
	 * @return
	 * @throws ManagerException
	 */
	int updateMemberPaymentByAccount(MemberPaymentDO memberPayment) throws ManagerException;
	/**
	 * <p>根据会员编号查找会员绑定的支付账户记录</p>
	 *
	 * @param paymentQuery
	 * @return
	 * @throws DaoException
	 */
	MemberPaymentDO findMemberPaymentDOByMemberAndInst(MemberPaymentDO paymentQuery) throws ManagerException;

	/**
	 * Created on 2011-9-14
	 * @desc <p>Discription:[根据]</p>
	 * @param
	 * @return void
	 * @author:[石兴]
	 * @update:[2011-9-14] [更改人姓名]
	 */
	MemberPaymentDO getPaymentDOByAccountNo(String accountNo) throws ManagerException;

	/**
	 * 验证该账号是否已被绑定
	 * @param paymentQuery
	 * @return
	 * @throws DaoException
	 */
	MemberPaymentDO findMemberPaymentForValidate(MemberPaymentDO paymentQuery) throws ManagerException;

	/**
	 * <p>根据会员编号查询会员的首页类型</p>
	 *
	 * @param memberId 会员编号
	 * @return
	 * @throws DaoException
	 */
	Integer findMemberMyPageTypeByMemberId(long memberId) throws ManagerException;

	/**
	 * <p>根据会员编号更新会员的首页类型</p>
	 *
	 * @param memberId 会员编号
	 * @param myPageType 会员的首页类型
	 * @throws DaoException
	 */
	void updateMemberMyPageTypeByMemberId(long memberId, int myPageType) throws ManagerException;

	/**
	 * 查询用户的扩展信息
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	boolean findBuyerExt(long memberId) throws ManagerException;
}
