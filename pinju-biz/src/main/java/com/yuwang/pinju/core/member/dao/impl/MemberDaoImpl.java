package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberDao;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;

/**
 * <p>会员相关的基本数据库操作</p>
 *
 * @author gaobaowen
 * 2011-6-9 下午12:26:22
 */
public class MemberDaoImpl extends BaseDAO implements MemberDao {

	/**
	 * MEMBER_MEMBER 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX_MEMBER = "Member.";

	/**
	 * MEMBER_MEMBER_INFO 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX_MEMBER_INFO = "MemberInfo.";

	/**
	 * MEMBER_MEMBER_DELIVERY 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX_MEMBER_DELIVERY = "MemberDelivery.";

	/**
	 * MEMBER_MEMBER_PAYMENT 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX_MEMBER_PAYMENT = "MemberPayment.";


	@Override
	public MemberDO persist(MemberDO member) throws DaoException {
		Long id = (Long)super.executeInsert(NAMESPACE_PREFIX_MEMBER + "persistMember", member);
		member.setId(id);
		return member;
	}

	@Override
	public int updateMember(MemberDO member) throws DaoException {

		Long version = member.getInfoVersion();
		if(version == null) {
			version = 0L;
		}
		version += 1;
		member.setInfoVersion(version);

		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER + "updateMember", member);
	}

	@Override
	public MemberDO findMember(String account, int origin) throws DaoException {
		return (MemberDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER + "findMemberByAccount", new MemberDO(account, origin));
	}

	@Override
	public MemberDO findMemberByMid(long mid) throws DaoException {
		return (MemberDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER + "findMemberByMid", mid);
	}

	@Override
	public MemberDO findMemberByNickname(String nickname) throws DaoException {
		return (MemberDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER + "findMemberByNickname", nickname);
	}

	@Override
	public MemberInfoDO findMemberInfoByMid(long memberId) throws DaoException {
		return (MemberInfoDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER_INFO + "findMemberInfoByMid", memberId);
	}

	@Override
	public MemberInfoDO persistMemberInfo(MemberInfoDO memberInfo) throws DaoException {
		Long id = (Long)super.executeInsert(NAMESPACE_PREFIX_MEMBER_INFO + "persistMemberInfo", memberInfo);
		memberInfo.setId(id);
		return memberInfo;
	}

	@Override
	public MemberInfoDO getMemberInfoByEmail(String email) throws DaoException {
		return (MemberInfoDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER_INFO + "getMemberInfoByEmail", email);
	}

	@Override
	public int updateMemberInfo(MemberInfoDO memberInfo) throws DaoException {
		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER_INFO + "updateMemberInfo", memberInfo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MemberDeliveryDO> findMemberDeliveryByMid(long memberId) throws DaoException {
		return (List<MemberDeliveryDO>)super.executeQueryForList(NAMESPACE_PREFIX_MEMBER_DELIVERY + "findMemberDeliveryByMid", memberId);
	}

	@Override
	public MemberDeliveryDO getMemberDeliveryById(long id) throws DaoException {
		return (MemberDeliveryDO) super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER_DELIVERY + "getMemberDeliveryById", id);
	}

	@Override
	public MemberDeliveryDO persistMemberDelivery(MemberDeliveryDO memberDelivery) throws DaoException {
		memberDelivery.setUpdateTime(new Date());
		Long id = (Long)super.executeInsert(NAMESPACE_PREFIX_MEMBER_DELIVERY + "persistMemberDelivery", memberDelivery);
		memberDelivery.setId(id);
		return memberDelivery;
	}

	@Override
	public int updateMemberDelivery(MemberDeliveryDO memberDelivery) throws DaoException {
		memberDelivery.setUpdateTime(new Date());
		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER_DELIVERY + "updateMemberDelivery", memberDelivery);
	}

	@Override
	public Number countMemberDeliveryByMid(long memberId) throws DaoException {
		return (Number)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER_DELIVERY + "countMemberDeliveryByMid", memberId);
	}

	@Override
	public int removeMemberDelivery(long deliveryId, long memberId) throws DaoException {
		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER_DELIVERY + "removeMemberDelivery", new MemberDeliveryDO(deliveryId, memberId));
	}

	@Override
	public int setDefaultDelivery(long deliveryId, long memberId) throws DaoException {
		MemberDeliveryDO delivery = new MemberDeliveryDO(deliveryId, memberId);
		delivery.setUpdateTime(new Date());
		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER_DELIVERY + "setDefaultDelivery", delivery);
	}

	@Override
	public int clearDeliveryStatus(long memberId) throws DaoException {
		MemberDeliveryDO delivery = new MemberDeliveryDO();
		delivery.setMemberId(memberId);
		delivery.setUpdateTime(new Date());
		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER_DELIVERY + "clearDeliveryStatus", delivery);
	}

	@Override
	public MemberPaymentDO persistMemberPayment(MemberPaymentDO memberPayment) throws DaoException {
		Long id = (Long)super.executeInsert(NAMESPACE_PREFIX_MEMBER_PAYMENT + "persistMemberPayment", memberPayment);
		memberPayment.setId(id);
		return memberPayment;
	}

	@Override
	public int updateMemberPayment(MemberPaymentDO memberPayment) throws DaoException {
		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER_PAYMENT + "updateMemberPayment", memberPayment);
	}

	@Override
	public MemberPaymentDO findBoundMemberPayment(MemberPaymentDO paymentQuery) throws DaoException {
		return (MemberPaymentDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER_PAYMENT + "findBoundMemberPayment", paymentQuery);
	}

	@Override
	public int updateMemberPaymentByAccount(MemberPaymentDO memberPayment)
			throws DaoException {
		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER_PAYMENT + "updateMemberPaymentByAccountNo", memberPayment);
	}

	@Override
	public MemberPaymentDO getPaymentDOByAccountNo(String accountNo)
			throws DaoException {
		return (MemberPaymentDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER_PAYMENT + "getPaymentDOByAccountNo", accountNo);
	}
	
	@Override
	public MemberPaymentDO findMemberPaymentDOByMemberAndInst(MemberPaymentDO paymentQuery)
			throws DaoException {
		return (MemberPaymentDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER_PAYMENT + "findTenPayBoundMemberPayment", paymentQuery);
	}

	@Override
	public MemberPaymentDO findMemberPaymentForValidate(
			MemberPaymentDO paymentQuery) throws DaoException {
		return (MemberPaymentDO)super.executeQueryForObject(NAMESPACE_PREFIX_MEMBER_PAYMENT + "validateBoundMemberPayment", paymentQuery);
	}

	@Override
	public Integer findMemberMyPageTypeByMemberId(long memberId)
			throws DaoException {
		return (Integer)executeQueryForObject(NAMESPACE_PREFIX_MEMBER + "findMemberMyPageTypeByMemberId", memberId);
	}

	@Override
	public void updateMemberMyPageTypeByMemberId(MemberDO memberDO)
			throws DaoException {
		executeUpdate(NAMESPACE_PREFIX_MEMBER + "updateMemberMyPageTypeByMemberId", memberDO);
	}

	@Override
	public int updateMemberStatus(MemberDO member) throws DaoException {
		return super.executeUpdate(NAMESPACE_PREFIX_MEMBER + "updateMemberStatus", member);
	}

	@Override
	public int updateMemberAccountType(long memberId, Integer accountType) throws DaoException {
		MemberDO member = new MemberDO();
		member.setMemberId(memberId);
		member.setAccountType(accountType == null ? MemberDO.ACCOUNT_TYPE_COMMON :accountType);
		return executeUpdate(NAMESPACE_PREFIX_MEMBER + "updateMemberAccountType", member);
	}
}
