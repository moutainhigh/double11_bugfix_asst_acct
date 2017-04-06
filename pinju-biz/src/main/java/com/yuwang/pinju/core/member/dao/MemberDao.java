package com.yuwang.pinju.core.member.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;

/**
 * <p>会员数据库操作</p>
 *
 * <p>该接口中对于所有的持久化保存的操作，在数据返回会带有数据库自动产生的 ID 填充对象的 id 属性</p>
 *
 * @author gaobaowen
 * 2011-6-3 下午04:29:31
 */
public interface MemberDao {

	/**
	 * <p>保存用户信息</p>
	 *
	 * @param member
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberDO persist(MemberDO member) throws DaoException;

	/**
	 * <p>更新用户基本信息</p>
	 *
	 * @param member
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	int updateMember(MemberDO member) throws DaoException;

	/**
	 * <p>根据第三方账号及第三方来源查找用户</p>
	 *
	 * @param account
	 * @param orign
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberDO findMember(String account, int origin) throws DaoException;

	/**
	 * <p>根据 MID 查找会员</p>
	 *
	 * @param mid
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberDO findMemberByMid(long mid) throws DaoException;

	/**
	 * <p>根据用户昵称查找会员，用户昵称在平台中应该唯一</p>
	 *
	 * @param nickname
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberDO findMemberByNickname(String nickname) throws DaoException;

	/**
	 * <p>根据 MID 查找会员个人信息</p>
	 *
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberInfoDO findMemberInfoByMid(long memberId) throws DaoException;

	/**
	 * <p>根据 email 查找会员个人信息</p>
	 *
	 * @param email
	 * @return 未找到时返回 null 值，找到时对象中仅有 {@link MemberInfoDO#id} 和 {@link MemberInfoDO#memberId} 两项值
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-7-29 13:02:15
	 */
	MemberInfoDO getMemberInfoByEmail(String email) throws DaoException;

	/**
	 * <p>保存会员的个人信息</p>
	 *
	 * @param memberInfo
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberInfoDO persistMemberInfo(MemberInfoDO memberInfo) throws DaoException;

	/**
	 * <p>更新会员的个人信息</p>
	 *
	 * @param memberInfo
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	int updateMemberInfo(MemberInfoDO memberInfo) throws DaoException;

	/**
	 * <p>根据会员编号（MID）查找该会员的收货地址数据</p>
	 *
	 * @param memberId
	 * @return	该会员已保存的收货地址数据，默认地址排在最前面，非默认地址按更新的时间倒序排序
	 *
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	List<MemberDeliveryDO> findMemberDeliveryByMid(long memberId) throws DaoException;

	/**
	 *
	 * <p>根据会员收货地址 ID 获取收货地址</p>
	 *
	 * @param id
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberDeliveryDO getMemberDeliveryById(long id) throws DaoException;

	/**
	 * <p>保存会员的收货地址信息</p>
	 *
	 * @param memberDelivery
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberDeliveryDO persistMemberDelivery(MemberDeliveryDO memberDelivery) throws DaoException;

	/**
	 * <p>更新会员的收货地址信息</p>
	 *
	 * @param memberDelivery
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	int updateMemberDelivery(MemberDeliveryDO memberDelivery) throws DaoException;

	/**
	 * <p>统计指定会员当前有多少条收货地址数据</p>
	 *
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	Number countMemberDeliveryByMid(long memberId) throws DaoException;

	/**
	 * <p>删除会员收货地址数据</p>
	 *
	 * @param deliveryId
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	int removeMemberDelivery(long deliveryId, long memberId) throws DaoException;

	/**
	 * <p><b>MEMBER_MEMBER_DELIVERY</b> 数据表操作</p>
	 * <p>将用户的收货地址更改为默认地址</p>
	 *
	 * @param id		收货地址（MEMBER_MEMBER_DELIVERY） ID
	 * @param memberId	会员编号
	 * @return			更新的记录数量
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	int setDefaultDelivery(long deliveryId, long memberId) throws DaoException;

	/**
	 * <p><b>MEMBER_MEMBER_DELIVERY</b> 数据表操作</p>
	 * <p>清除某一会员已设置为默认收货地址的记录</p>
	 *
	 * @param memberId		会员编号
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	int clearDeliveryStatus(long memberId) throws DaoException;

	/**
	 * <p>保存会员支付账户信息</p>
	 *
	 * @param memberPayment
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberPaymentDO persistMemberPayment(MemberPaymentDO memberPayment) throws DaoException;

	/**
	 * <p>根据会员编号查找会员绑定的支付账户记录</p>
	 *
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberPaymentDO findBoundMemberPayment(MemberPaymentDO paymentQuery) throws DaoException;

	/**
	 * <p>更新会员支付账户信息，该更新需要有版本号的支持，需要在更新前填充对象中的 {@link MemberPaymentDO#version} 属性值</p>
	 *
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	int updateMemberPayment(MemberPaymentDO memberPayment) throws DaoException;

	/**
	 * <p>更新会员支付账户信息，该更新需要有版本号的支持，需要在更新前填充对象中的 {@link MemberPaymentDO#version} 属性值</p>
	 *
	 * @param memberPayment
	 * @return
	 * @throws DaoException
	 */
	int updateMemberPaymentByAccount(MemberPaymentDO memberPayment) throws DaoException;

	/**
	 * <p>根据会员编号查找会员绑定的支付账户记录</p>
	 *
	 * @param paymentQuery
	 * @return
	 * @throws DaoException
	 */
	MemberPaymentDO findMemberPaymentDOByMemberAndInst(MemberPaymentDO paymentQuery) throws DaoException;

	/**
	 * 验证该账号是否已被绑定
	 * @param paymentQuery
	 * @return
	 * @throws DaoException
	 */
	MemberPaymentDO findMemberPaymentForValidate(MemberPaymentDO paymentQuery) throws DaoException;
	/**
	 * Created on 2011-9-14
	 * @desc <p>Discription:[Method Discription]</p>
	 * @param
	 * @return void
	 * @author:[石兴]
	 * @update:[2011-9-14] [更改人姓名]
	 */
	MemberPaymentDO getPaymentDOByAccountNo(String accountNo)throws DaoException;

	/**
	 * <p>根据会员编号查询会员的首页类型</p>
	 *
	 * @param memberId 会员编号
	 * @return
	 * @throws DaoException
	 */
	Integer findMemberMyPageTypeByMemberId(long memberId) throws DaoException;

	/**
	 * <p>根据会员编号更新会员的首页类型</p>
	 * @param memberDO
	 * @throws DaoException
	 */
	void updateMemberMyPageTypeByMemberId(MemberDO memberDO) throws DaoException;
	/**
	 * <p>更新会员状态(status)</p>
	 * @param member
	 * @return
	 * @throws DaoException
	 */
	int updateMemberStatus(MemberDO member) throws DaoException;

	/**
	 * <p>更新会员的账号类型</p>
	 *
	 * @param memberId  会员编号
	 * @param accountType  账号类型
	 * @return  更新所涉及的行数
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-12-21 13:14:52
	 */
	int updateMemberAccountType(long memberId, Integer accountType) throws DaoException;
}
