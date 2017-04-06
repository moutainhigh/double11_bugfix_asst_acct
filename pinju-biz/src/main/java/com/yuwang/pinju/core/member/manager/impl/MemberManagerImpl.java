package com.yuwang.pinju.core.member.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.cache.CacheUtil;
import com.yuwang.pinju.core.cache.MemcachedManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.member.dao.DsrStatDAO;
import com.yuwang.pinju.core.member.dao.MemberDao;
import com.yuwang.pinju.core.member.dao.MemberExtDAO;
import com.yuwang.pinju.core.member.dao.MemberLogDao;
import com.yuwang.pinju.core.member.dao.MemberSnsInfoDAO;
import com.yuwang.pinju.core.member.dao.SellerQualityDAO;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.member.manager.sequence.MemberIdGenerator;
import com.yuwang.pinju.core.member.manager.ticket.MemberOrigin;
import com.yuwang.pinju.core.member.manager.ticket.TicketValidator;
import com.yuwang.pinju.core.rate.manager.RateManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.DsrStatDO;
import com.yuwang.pinju.domain.member.MemberAllDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.MemberExtDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;
import com.yuwang.pinju.domain.member.SellerQualityInfoDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailDO;
import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;
import com.yuwang.pinju.domain.member.ticket.TicketValidatorResultDO;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;

public class MemberManagerImpl extends TransactionMemberManager implements MemberManager {

	private final static Log log = LogFactory.getLog(MemberManagerImpl.class);

	private MemberDao memberDAO;
	private MemberLogDao memberLogDAO;
	private MemberIdGenerator memberIdGenerator;
	private SellerQualityDAO sellerQualityDAO;
	private DsrStatDAO dsrStatDAO;
	private MemberSnsInfoDAO memberSnsInfoDAO;
	private MemberExtDAO memberExtDAO;
	private MemberSecurityManager memberSecurityManager;

	private MemcachedManager qualitityMemcachedManager;
	private RateManager rateManager;

	public void setMemberExtDAO(MemberExtDAO memberExtDAO) {
		this.memberExtDAO = memberExtDAO;
	}

	public void setMemberDAO(MemberDao memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setMemberIdGenerator(MemberIdGenerator memberIdGenerator) {
		this.memberIdGenerator = memberIdGenerator;
	}

	public void setMemberLogDAO(MemberLogDao memberLogDAO) {
		this.memberLogDAO = memberLogDAO;
	}

	public void setSellerQualityDAO(SellerQualityDAO sellerQualityDAO) {
		this.sellerQualityDAO = sellerQualityDAO;
	}

	public void setDsrStatDAO(DsrStatDAO dsrStatDAO) {
		this.dsrStatDAO = dsrStatDAO;
	}

	public void setMemberSnsInfoDAO(MemberSnsInfoDAO memberSnsInfoDAO) {
		this.memberSnsInfoDAO = memberSnsInfoDAO;
	}

	public void setQualitityMemcachedManager(MemcachedManager qualitityMemcachedManager) {
		this.qualitityMemcachedManager = qualitityMemcachedManager;
	}

	public void setRateManager(RateManager rateManager) {
		this.rateManager = rateManager;
	}

	public void setMemberSecurityManager(MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	@Override
	public boolean isCorrectMemberId(Long memberId) {
		if (memberId == null) {
			log.warn("isCorrectMemberId, memberId is null");
			return false;
		}
		if (memberId < MemberDO.MIN_MEMBER_ID || memberId > MemberDO.MAX_MEMBER_ID) {
			log.warn("isCorrectMemberId, memberId is incorrect, memberId = [" + memberId + "]");
			return false;
		}
		return true;
	}

	public MemberDO getMemberByAccount(String account, int origin) throws ManagerException {
		if (EmptyUtil.isBlank(account)) {
			log.warn("getMemberByAccount, account is empty or null, cannot find any member information");
			return null;
		}
		try {
			return memberDAO.findMember(account, origin);
		} catch (DaoException e) {
			log.error("getMemberByAccount, access database cause error, account: " + account + ", origin: " + origin);
			return null;
		}
	}

	public MemberDO saveMember(final MemberDO member) throws ManagerException {
		return executeInTransaction(new TransactionExecutor<MemberDO>() {
			public MemberDO execute() throws DaoException {
				return memberDAO.persist(member);
			}
		}, "saveMember");
	}

	public int updateMember(final MemberDO member) throws ManagerException {
		return executeInTransaction(new TransactionExecutor<Integer>() {
			public Integer execute() throws DaoException {
				return memberDAO.updateMember(member);
			}
		}, "updateMember");
	}

	@Override
	public int logout(final String sessionId, final long memberId, final Date logoutTime) throws ManagerException {
		if (EmptyUtil.isBlank(sessionId)) {
			throw new ManagerException("logout, sessionId is invalid, sessionId: " + sessionId + ", memberId: " + memberId);
		}
		if (logoutTime == null) {
			throw new ManagerException("logout, logoutTime is invalid, sessionId: " + sessionId + ", memberId: " + memberId);
		}
		if (!isCorrectMemberId(memberId)) {
			throw new ManagerException("logout, mid invalid, member id: " + memberId + ", session id: " + sessionId);
		}
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("sessionId", sessionId);
		parameters.put("memberId", memberId);
		parameters.put("logoutTime", logoutTime);
		return executeInTransaction(new TransactionExecutor<Integer>() {
			public Integer execute() throws DaoException {
				int logoutCount = memberLogDAO.updateMemberLoginHis(parameters);
				if (log.isDebugEnabled()) {
					log.debug("session id: " + sessionId + ", member id: " + memberId + ", logout count: "
							+ logoutCount);
				}
				return logoutCount;
			}
		}, "logout");
	}

	public MemberDO findMember(long memberId) throws ManagerException {
		if (log.isDebugEnabled()) {
			log.debug("execute findMember, member id: " + memberId);
		}
		if (!isCorrectMemberId(memberId)) {
			return null;
		}
		try {
			return memberDAO.findMemberByMid(memberId);
		} catch (DaoException e) {
			log.error("find member error, memberId = " + memberId, e);
			throw new ManagerException("find member error, memberId = " + memberId, e);
		}
	}

	public MemberDO findMemberByNickname(String nickname) throws ManagerException {
		log.debug("execute findMemberByNickname");
		if (EmptyUtil.isBlank(nickname)) {
			log.warn("execute findMemberByNickname, nickname is null or empty, nickname = " + nickname);
			return null;
		}
		try {
			return memberDAO.findMemberByNickname(nickname);
		} catch (DaoException e) {
			log.error("find member error, nickname = " + nickname, e);
			throw new ManagerException("find member error, nickname = " + nickname, e);
		}
	}

	public MemberInfoDO findMemberInfo(long memberId) throws ManagerException {
		log.debug("execute findMemberInfo");
		if (!isCorrectMemberId(memberId)) {
			return null;
		}
		try {
			return memberDAO.findMemberInfoByMid(memberId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	public MemberInfoDO getMemberInfoByEmail(String email) throws ManagerException {
		if (log.isDebugEnabled()) {
			log.debug("execute getMemberInfoByEmail, email: " + email);
		}
		if (EmptyUtil.isBlank(email)) {
			throw new ManagerException("execute getMemberInfoByEmail, parameter email value is null or empty");
		}
		try {
			return memberDAO.getMemberInfoByEmail(email);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	public int[] updateMemberInfo(final MemberDO member, final MemberInfoDO memberInfo) throws ManagerException {
		if (member == null || memberInfo == null) {
			throw new ManagerException("updateMemberInfo, member or memberInfo is null");
		}
		if (member.getMemberId() == null || memberInfo.getMemberId() == null
				|| !member.getMemberId().equals(memberInfo.getMemberId())) {
			throw new ManagerException("updateMemberInfo, mid in member or in memberInfo is empty or arenot equal,"
					+ "member.mid = " + member.getMemberId() + ", memberInfo.mid = " + memberInfo.getMemberId());
		}
		if (log.isInfoEnabled()) {
			log.info("process member basic info: " + member + ", memberInfo: " + memberInfo);
		}

		try {
			MemberSecurityEmailDO email = memberSecurityManager.findMemberSecurityEmailByMemberId(member.getMemberId());
			if (email != null) {
				if (log.isInfoEnabled()) {
					log.info("updateMemberInfo, MemberSecurityEmailDO was found, member id: [" + member.getMemberId() + "], email: [" + email.getEmailAddr() + "]");
				}
				memberInfo.setEmail(email.getEmailAddr());
			} else if (log.isDebugEnabled()) {
				log.debug("updateMemberInfo, MemberSecurityEmailDO was not found, member id: [" + member.getMemberId() + "]");
			}
		} catch (Exception e) {
			log.warn("updateMemberInfo, get member id: [" + member.getMemberId() + "] MemberSecurityEmailDO cause exception," +
					"reset MemberInfo email failed, but the case can be ignored", e);
		}

		if (memberInfo.isNew()) {
			// 如果会员资料是全新的，再次查询该会员是否已经填写过资料（防止会员多标签提交）
			MemberInfoDO info = findMemberInfo(member.getMemberId());
			if (info != null) {
				// 若已经存在会员资料，将 ID 置为已经存在的 ID 值
				memberInfo.setId(info.getId());
				if (log.isInfoEnabled()) {
					log.info("updateMemberInfo, member id [" + member.getMemberId() + "], find member info id: [" + info.getId() + "], execute update");
				}
			} else if (log.isInfoEnabled()){
				log.info("updateMemberInfo, member id [" + member.getMemberId() + "], can not find member info, need execute insert");
			}
		}

		return executeInTransaction(new TransactionExecutor<int[]>() {
			public int[] execute() throws DaoException {

				int[] updateCount = new int[2];
				updateCount[0] = memberDAO.updateMember(member);
				if (log.isDebugEnabled()) {
					log.debug("updateMemberInfo, update member success, update count: " + updateCount[0]);
				}
				if (memberInfo.isNew()) {
					memberDAO.persistMemberInfo(memberInfo);
					updateCount[1] = 1;
					if (log.isDebugEnabled()) {
						log.debug("updateMemberInfo, memberInfo is new, persist it, new id: " + memberInfo.getId());
					}
				} else {
					updateCount[1] = memberDAO.updateMemberInfo(memberInfo);
					if (log.isDebugEnabled()) {
						log.debug("updateMemberInfo, update memberinfo success, update count: " + updateCount[0]);
					}
				}
				return updateCount;
			}
		}, "updateMemberInfo");
	}

	@Override
	public MemberDO login(MemberTicketDO ticket) throws ManagerException {
		if (ticket == null) {
			throw new ManagerException("parameter ticket is null");
		}

		TicketValidatorResultDO result = validate(ticket);

		if (!result.isSuccess()) {
			log.warn("ticket validate failed, result: " + result);
			return null;
		}
		return login(ticket, result);
	}

	public MemberLoginHisDO logLogin(final MemberLoginHisDO memberLoginHis) throws ManagerException {
		log.info("process logLogin, " + memberLoginHis);
		return executeInTransaction(new TransactionExecutor<MemberLoginHisDO>() {
			public MemberLoginHisDO execute() throws DaoException {
				return memberLogDAO.persistMemberLoginHis(memberLoginHis);
			}
		}, "logLogin");
	}

	private TicketValidatorResultDO validate(MemberTicketDO ticket) throws ManagerException {
		TicketValidator validator = createMemberOrigin(ticket);
		if (log.isDebugEnabled()) {
			log.debug("using " + validator + " to validate ticket");
		}
		return validator.validate(ticket);
	}

	private TicketValidator createMemberOrigin(MemberTicketDO ticket) throws ManagerException {
		MemberOrigin memberOrign = MemberOrigin.valueOf(ticket.getOrigin());
		if (memberOrign == null) {
			throw new ManagerException("origin value (" + ticket.getOrigin() + ") in ticket is invalid");
		}
		if (log.isDebugEnabled()) {
			log.debug("find: " + memberOrign);
		}
		return memberOrign.getTicketValidator();
	}

	private MemberDO login(MemberTicketDO ticket, TicketValidatorResultDO result) throws ManagerException {
		MemberDO member = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("prepare to find member from account: " + result.getLoginAccount() + ", origin: "
						+ ticket.getOrigin());
			}
			member = memberDAO.findMember(result.getLoginAccount(), ticket.getOrigin());
			if (log.isDebugEnabled() && member != null) {
				log.debug("find member: " + member);
			}
		} catch (DaoException e) {
			throw new ManagerException("find member error, account name: " + result.getLoginAccount() + ", origin: "
					+ ticket.getOrigin(), e);
		}
		if (member == null) {
			long memberId = memberIdGenerator.nextMemberId(MemberOrigin.valueOf(ticket.getOrigin()));
			log.info("cannot find member, account name: " + result.getLoginAccount() + ", origin: "
					+ ticket.getOrigin() + ", generated member id: " + memberId);
			member = MemberDO.createMember(memberId, ticket, result);
		}

		return processLoginMember(member);
	}

	private MemberDO processLoginMember(final MemberDO member) throws ManagerException {
		log.info("process login member: " + member);
		return executeInTransaction(new TransactionExecutor<MemberDO>() {
			public MemberDO execute() throws DaoException {
				MemberDO innerMember = member;
				if (innerMember.isNew()) {
					innerMember = memberDAO.persist(innerMember);
					if (log.isDebugEnabled()) {
						log.debug("create member success, member: " + member);
					}
				}
				return innerMember;
			}
		}, "processLoginMember");
	}

	@Override
	public List<MemberDeliveryDO> findMemberDeliveries(long memberId) throws ManagerException {
		log.debug("execute findMemberDeliveries");
		if (!isCorrectMemberId(memberId)) {
			return null;
		}
		try {
			List<MemberDeliveryDO> deliveries = memberDAO.findMemberDeliveryByMid(memberId);
			if (EmptyUtil.isBlank(deliveries)) {
				log.warn("findMemberDeliveries, result is null, memberId = " + memberId);
				return null;
			}
			if (log.isInfoEnabled()) {
				log.info("findMemberDeliveries, result size: " + deliveries.size() + ", memberId = " + memberId);
			}
			return deliveries;
		} catch (DaoException e) {
			String message = "find member delivery error, memberId = " + memberId;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberDeliveryDO getMemberDeliveryById(long deliveryId) throws ManagerException {
		try {
			return memberDAO.getMemberDeliveryById(deliveryId);
		} catch (DaoException e) {
			log.error("getMemberDeliveryById error", e);
			throw new ManagerException("getMemberDeliveryById error", e);
		}
	}

	@Override
	public int updateMemberDelivery(final MemberDeliveryDO memberDelivery) throws ManagerException {
		if (memberDelivery == null) {
			throw new ManagerException("updateMemberDelivery, memberDelivery parameter is null");
		}
		if (memberDelivery.getId() == null || !isCorrectMemberId(memberDelivery.getMemberId())) {
			throw new ManagerException("updateMemberDelivery, id or mid is null, id = " + memberDelivery.getId()
					+ ", member id: " + memberDelivery.getMemberId());
		}
		if (log.isDebugEnabled()) {
			log.info("update member delivery info: " + memberDelivery);
		}
		return executeInTransaction(new TransactionExecutor<Integer>() {
			public Integer execute() throws DaoException {
				if (memberDelivery.isDefault()) {
					int clearCount = memberDAO.clearDeliveryStatus(memberDelivery.getMemberId());
					log.debug("update member delivery the status is " + memberDelivery.getStatus()
							+ ", and need clear old default delivery, clearCount: " + clearCount);
				}
				int updateCount = memberDAO.updateMemberDelivery(memberDelivery);
				if (log.isDebugEnabled()) {
					log.debug("update member delivery success, update count: " + updateCount + ", member delivery: "
							+ memberDelivery);
				}
				return updateCount;
			}
		}, "updateMemberDelivery");
	}

	@Override
	public MemberDeliveryDO saveMemberDelivery(final MemberDeliveryDO memberDelivery) throws ManagerException {
		if (memberDelivery == null) {
			throw new ManagerException("saveMemberDelivery, memberDelivery parameter is null");
		}
		if (!isCorrectMemberId(memberDelivery.getMemberId())) {
			throw new ManagerException("saveMemberDelivery, mid invalid, memberDelivery info: " + memberDelivery);
		}
		if (log.isDebugEnabled()) {
			log.info("persist member delivery info: " + memberDelivery);
		}
		return executeInTransaction(new TransactionExecutor<MemberDeliveryDO>() {
			public MemberDeliveryDO execute() throws DaoException {
				if (memberDelivery.isDefault()) {
					int clearCount = memberDAO.clearDeliveryStatus(memberDelivery.getMemberId());
					log.debug("save member delivery the status is " + memberDelivery.getStatus()
							+ ", and need clear old default delivery, clearCount: " + clearCount);
				}
				MemberDeliveryDO delivery = memberDAO.persistMemberDelivery(memberDelivery);
				if (log.isDebugEnabled()) {
					log.debug("save member delivery success, persist member delivery info: " + memberDelivery);
				}
				return delivery;
			}
		}, "saveMemberDelivery");
	}

	@Override
	public int countMemberDeliveries(long memberId) throws ManagerException {
		log.debug("execute countMemberDeliveries");
		if (!isCorrectMemberId(memberId)) {
			return 0;
		}
		try {
			Number count = memberDAO.countMemberDeliveryByMid(memberId);
			if (count == null) {
				log.warn("countMemberDeliveries, count result is null, memberId = " + memberId);
				return 0;
			}
			if (log.isInfoEnabled()) {
				log.info("countMemberDeliveries, count: " + count + ", memberId = " + memberId);
			}
			return count.intValue();
		} catch (DaoException e) {
			String message = "count member delivery error, memberId = " + memberId;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public int removeMemberDelivery(final long deliveryId, final long memberId) throws ManagerException {
		log.debug("execute removeMemberDelivery");
		if (!isCorrectMemberId(memberId)) {
			throw new ManagerException("removeMemberDelivery, mid invalid, deliveryId: " + deliveryId + ", mid: "
					+ memberId);
		}
		if (log.isDebugEnabled()) {
			log.info("remove member delivery, deliveryId: " + deliveryId + ", mid: " + memberId);
		}
		return executeInTransaction(new TransactionExecutor<Integer>() {
			public Integer execute() throws DaoException {
				int removeCount = memberDAO.removeMemberDelivery(deliveryId, memberId);
				if (log.isDebugEnabled()) {
					log.debug("remove member delivery success, remove count: " + removeCount);
				}
				return removeCount;
			}
		}, "removeMemberDelivery");
	}

	public int setDefaultDelivery(final long deliveryId, final long memberId) throws ManagerException {
		log.debug("execute setDefaultDelivery");
		if (!isCorrectMemberId(memberId)) {
			throw new ManagerException("setDefaultDelivery, mid invalid, deliveryId: " + deliveryId + ", mid: "
					+ memberId);
		}
		if (log.isDebugEnabled()) {
			log.info("set default delivery, deliveryId: " + deliveryId + ", mid: " + memberId);
		}
		return executeInTransaction(new TransactionExecutor<Integer>() {
			public Integer execute() throws DaoException {
				// 清除用户已设置的默认收货地址
				int clearCount = memberDAO.clearDeliveryStatus(memberId);
				// 设置默认收货地址
				int defaultCount = memberDAO.setDefaultDelivery(deliveryId, memberId);
				if (log.isDebugEnabled()) {
					log.debug("set default delivery, clear count: " + clearCount + ", set default count: "
							+ defaultCount);
				}
				return defaultCount;
			}
		}, "setDefaultDelivery");
	}

	@Override
	public MemberPaymentDO findBoundMemberPayment(MemberPaymentDO paymentQuery) throws ManagerException {
		log.debug("execute findBoundMemberPayment");
		if (!isCorrectMemberId(paymentQuery.getMemberId())) {
			throw new ManagerException("findBoundMemberPayment, mid invalid: " + paymentQuery.getMemberId());
		}
		if (log.isDebugEnabled()) {
			log.info("find member payment info, according to member id, member id: " + paymentQuery.getMemberId());
		}
		try {
			return memberDAO.findBoundMemberPayment(paymentQuery);
		} catch (DaoException e) {
			String message = "find member payment info failed, memberId = " + paymentQuery.getMemberId();
			throw new ManagerException(message, e);
		}
	}

	@Override
	public int unboundMemberPayment(final MemberPaymentDO payment) throws ManagerException {
		if (payment == null) {
			throw new ManagerException("unboundMemberPayment, parameter payment is null");
		}
		if (payment.getId() == null || payment.getMemberId() == null || payment.getVersion() == null) {
			throw new ManagerException(
					"unboundMemberPayment, member payment object id/member id/version is null, id: [" + payment.getId()
							+ ", member id: [" + payment.getMemberId() + ", version: [" + payment.getVersion() + "]");
		}
		return executeInTransaction(new TransactionExecutor<Integer>() {
			@Override
			public Integer execute() throws DaoException {
				payment.setBondStatus(MemberPaymentDO.BOUND_STATUS_UNBOUND);
				return memberDAO.updateMemberPayment(payment);
			}
		}, "unboundMemberPayment");
	}

	@Override
	public MemberPaymentDO boundMemberPayment(final MemberPaymentDO payment) throws ManagerException {
		if (payment == null) {
			throw new ManagerException("boundMemberPayment, parameter payment is null");
		}
		if (!isCorrectMemberId(payment.getMemberId())) {
			throw new ManagerException(
					"boundMemberPayment, member payment object member id is null or invalid, id: ["
							+ payment.getId() + ", member id: [" + payment.getMemberId());
		}
		return executeInTransaction(new TransactionExecutor<MemberPaymentDO>() {
			@Override
			public MemberPaymentDO execute() throws DaoException {
				return memberDAO.persistMemberPayment(payment);
			}
		}, "boundMemberPayment");
	}

	@Override
	public MemberAllDO findMemberAll(long memberId) throws ManagerException {
		log.debug("execute findMemberAll");
		if (!isCorrectMemberId(memberId)) {
			throw new ManagerException("findMemberAll, mid invalid: " + memberId);
		}
		if (log.isDebugEnabled()) {
			log.info("find all member info, according to member id, member id: " + memberId);
		}
		MemberAllDO memberAll = new MemberAllDO(memberId);
		try {
			memberAll.setMember(memberDAO.findMemberByMid(memberId));
			memberAll.setMemberInfo(memberDAO.findMemberInfoByMid(memberId));
			memberAll.setMemberDeliveries(memberDAO.findMemberDeliveryByMid(memberId));
			memberAll.setMemberPayment(memberDAO.findBoundMemberPayment(new MemberPaymentDO(memberId, MemberPaymentDO.INSTITUTION_TENPAY)));
		} catch (DaoException e) {
			String message = "find all member info failed, memberId = " + memberId;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
		return memberAll;
	}

	@Override
	public SellerQualityDO initSellerQuality(final SellerQualityDO sellerQuality) throws ManagerException {
		if (!isCorrectMemberId(sellerQuality.getMemberId())) {
			log.error("createSellerQuality, member " + sellerQuality.getMemberId() + " id is null or invalid");
			throw new ManagerException("member id " + sellerQuality.getMemberId() + " is null or invalid");
		}
		try {
			SellerQualityDO oldSellerQuality = sellerQualityDAO.getSellerQualityByMemberId(sellerQuality.getMemberId());
			if (oldSellerQuality != null) {
				String info = "createSellerQuality, seller quality has exist, member id: "
						+ sellerQuality.getMemberId() + ", seller quality id: " + oldSellerQuality.getId();
				log.error(info);
				throw new ManagerException(info);
			}

			// update 2011.12.21 gaobaowen, change seller member account type to MemberDO.ACCOUNT_TYPE_SELLER
			return executeInTransaction(new TransactionExecutor<SellerQualityDO>() {
				public SellerQualityDO execute() throws DaoException {
					SellerQualityDO newSellerQuality = sellerQualityDAO.insertSellerQuality(sellerQuality);
					int updateCount = memberDAO.updateMemberAccountType(sellerQuality.getMemberId(), MemberDO.ACCOUNT_TYPE_SELLER);
					if (updateCount < 1) {
						log.warn("[initSellerQuality] update member account type row count is [" + updateCount + "], " + sellerQuality);
					}
					return newSellerQuality;
				}
			}, "initSellerQuality", sellerQuality);
		} catch (DaoException e) {
			log.error("create seller quality error, " + sellerQuality, e);
			throw new ManagerException("create seller quality error, " + sellerQuality, e);
		}
	}

	@Override
	public SellerQualityDO getSellerQualityByMemberId(long memberId) throws ManagerException {
		if (!isCorrectMemberId(memberId)) {
			log.error("createSellerQuality, member id " + memberId + " is null or invalid");
			throw new ManagerException("member id " + memberId + " is null or invalid");
		}
		try {
			SellerQualityDO sellerQuality = sellerQualityDAO.getSellerQualityByMemberId(memberId);
			if (sellerQuality == null) {
				log.warn("getSellerQualityByMemberId seller quality is null, member id: " + memberId);
				return null;
			}
			if (log.isInfoEnabled()) {
				log.info("getSellerQualityByMemberId, find seller quality, member id: " + memberId
						+ ", seller quality id: " + sellerQuality.getId());
			}
			return sellerQuality;
		} catch (DaoException e) {
			String message = "getSellerQualityByMemberId error, member id: " + memberId;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	public int updateSellerQuality(final long memberId, final SellerQualityDO sellerQuality) throws ManagerException {
		if (!isCorrectMemberId(memberId)) {
			throw new ManagerException("updateSellerQuality, member id [" + memberId + "] is null or invalid");
		}
		if (sellerQuality == null) {
			throw new ManagerException("updateSellerQuality, member id [" + memberId + "], parameter sellerQuality is null");
		}
		sellerQuality.setMemberId(memberId);
		int updateCount = executeInTransaction(new TransactionExecutor<Integer>() {
			@Override
			public Integer execute() throws DaoException {
				int update = sellerQualityDAO.updateSellerQuality(sellerQuality);
				if (log.isDebugEnabled()) {
					log.debug("updateSellerQuality, update seller quality information version, update count: " + update
							+ ", update data: " + sellerQuality);
				}
				return update;
			}
		}, "updateSellerQuality");

		if (updateCount < 1) {
			log.warn("updateSellerQuality, update count is " + updateCount + ", update data: " + sellerQuality);
			return updateCount;
		}

		try {
			// 更新缓存
			SellerQualityInfoDO sqi = qualitityMemcachedManager.getCacheObject(memberId, SellerQualityInfoDO.class);
			if (sqi == null) {
				log.info("updateSellerQuality, refresh cache object, cache can not find object, member id: [" + memberId + "], need not refresh cache");
				return updateCount;
			}

			SellerQualityDO updatedObject = getSellerQualityByMemberId(memberId);
			if (updatedObject == null) {
				log.warn("updateSellerQuality, can not find seller quality, the case can be ignored, never happen, info: " + sellerQuality);
				return updateCount;
			}

			sqi.setSellerQuality(updatedObject);
			boolean result = qualitityMemcachedManager.setCacheObject(memberId, sqi);
			log.info("updateSellerQuality, get SellerQualityInfoDO object from cache, need refresh cache member id: " + memberId + ", cache set result: " + result);
		} catch (Exception e) {
			log.warn("updateSellerQuality, refresh seller quality failed, the exception can not be ignored, memberid: [" + memberId + "]", e);
		}
		return updateCount;
	}

	@Override
	public List<DsrStatDO> getDsrStatsByMemberId(long memberId) throws ManagerException {
		if (!isCorrectMemberId(memberId)) {
			log.error("getDsrStatsByMemberId, member id " + memberId + " is null or invalid");
			throw new ManagerException("member id " + memberId + " is null or invalid");
		}
		try {
			List<DsrStatDO> dsrStats = dsrStatDAO.getDsrStatsByMemberId(memberId);
			if (log.isInfoEnabled()) {
				log.info("getDsrStatsByMemberId, find dsr stats data, member id: " + memberId + ", dsr data count: "
						+ (dsrStats == null ? "null" : dsrStats.size()));
			}
			return dsrStats;
		} catch (DaoException e) {
			String message = "getDsrStatsByMemberId error, member id: " + memberId;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public List<DsrStatDO> getIntactDsrStatsByMemberId(long memberId, boolean isCacheDebug) throws ManagerException {
		if (log.isInfoEnabled()) {
			log.info("getIntactDsrStatsByMemberId, member id: [" + memberId + "], isCacheDebug: [" + isCacheDebug +
					"], isReadCache? [" + CacheUtil.isReadCache(isCacheDebug) + "]");
		}
		try {
			List<DsrDimensionDO> dimensions = rateManager.getValidDsrDimension(isCacheDebug);
			if (EmptyUtil.isBlank(dimensions)) {
				log.error("getValidDsrStatsByMemberId, can not foud any DsrDimensionDO data, ignored merge DSR statistics data");
				return new ArrayList<DsrStatDO>(0);
			}
			List<DsrStatDO> dsrStats = getDsrStatsByMemberId(memberId);

			if (log.isDebugEnabled()) {
				log.debug("getIntactDsrStatsByMemberId, member id: [" + memberId + "], dimensions size: [" +
						dimensions.size() + "], dsr stats size: [" + dsrStats.size() + "]");
			}

			List<DsrStatDO> list = new ArrayList<DsrStatDO>(dimensions.size());
			for (DsrDimensionDO dimension : dimensions) {
				DsrStatDO dsrStat = findDimensionDsrStat(memberId, dimension, dsrStats);
				list.add(dsrStat);
			}
			return list;
		} catch (Exception e) {
			String message = "getValidDsrStatsByMemberId error, member id: " + memberId;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberSnsInfoDO insertMemberSnsInfo(final MemberDO member, final MemberSnsInfoDO memberSnsInfo)
			throws ManagerException {
		if (memberSnsInfo == null) {
			throw new ManagerException("execute insertMemberSnsInfo, parameter MemberSnsInfoDO object is null");
		}
		if (!isCorrectMemberId(memberSnsInfo.getMemberId())) {
			throw new ManagerException("execute insertMemberSnsInfo, member id is null, empty or invalid, member id: ["
					+ memberSnsInfo.getMemberId() + "]");
		}
		return executeInTransaction(new TransactionExecutor<MemberSnsInfoDO>() {
			@Override
			public MemberSnsInfoDO execute() throws DaoException {
				int update = memberDAO.updateMember(member);
				if (log.isDebugEnabled()) {
					log.debug("insertMemberSnsInfo, update member information version, update count: " + update
							+ ", lasted version: " + member.getInfoVersion());
				}
				return memberSnsInfoDAO.insertMemberSnsInfo(memberSnsInfo);
			}
		}, "insertMemberSnsInfo");
	}

	@Override
	public int updateMemberSnsInfo(final MemberDO member, final MemberSnsInfoDO memberSnsInfo) throws ManagerException {
		if (memberSnsInfo == null) {
			throw new ManagerException("execute updateMemberSnsInfo, parameter MemberSnsInfoDO object is null");
		}
		if (!isCorrectMemberId(memberSnsInfo.getMemberId()) || memberSnsInfo.getId() == null) {
			throw new ManagerException("execute updateMemberSnsInfo, member id is null, empty or invalid, member id: ["
					+ memberSnsInfo.getMemberId() + "], id: [" + memberSnsInfo.getId() + "]");
		}
		return executeInTransaction(new TransactionExecutor<Integer>() {
			@Override
			public Integer execute() throws DaoException {
				int update = memberDAO.updateMember(member);
				if (log.isDebugEnabled()) {
					log.debug("insertMemberSnsInfo, update member information version, update count: " + update
							+ ", lasted version: " + member.getInfoVersion());
				}
				return memberSnsInfoDAO.updateMemberSnsInfo(memberSnsInfo);
			}
		}, "updateMemberSnsInfo");
	}

	@Override
	public MemberSnsInfoDO getMemberSnsInfo(long memberId) throws ManagerException {
		if (!isCorrectMemberId(memberId)) {
			throw new ManagerException("execute updateMemberSnsInfo, member id is null, empty or invalid, member id: ["
					+ memberId + "]");
		}
		try {
			return memberSnsInfoDAO.getMemberSnsInfoByMemberId(memberId);
		} catch (DaoException e) {
			log.error("getMemberSnsInfoByMemberId failed");
			throw new ManagerException("getMemberSnsInfoByMemberId failed", e);
		}
	}

	@Override
	public int signPayAgreement(MemberPaymentDO memberPaymentDO) throws ManagerException {
		try {
			return memberDAO.updateMemberPayment(memberPaymentDO);
		} catch (DaoException e) {
			log.error("signPayAgreement failed");
			throw new ManagerException("signPayAgreement failed", e);
		}

	}

	@Override
	public int updateMemberPaymentByAccount(MemberPaymentDO memberPayment)
			throws ManagerException {
		try {
			return memberDAO.updateMemberPaymentByAccount(memberPayment);
		} catch (DaoException e) {
			log.error("updateMemberPaymentByAccount failed");
			throw new ManagerException("updateMemberPaymentByAccount failed", e);
		}
	}

	@Override
	public MemberPaymentDO getPaymentDOByAccountNo(String accountNo) throws ManagerException{
		try {
			return memberDAO.getPaymentDOByAccountNo(accountNo);
		} catch (DaoException e) {
			log.error("updateMemberPaymentByAccount failed");
			throw new ManagerException("getPaymentDOByAccountNo failed", e);
		}

	}

	@Override
	public MemberPaymentDO findMemberPaymentDOByMemberAndInst(MemberPaymentDO paymentQuery)
			throws ManagerException {
		try {
			return memberDAO.findMemberPaymentDOByMemberAndInst(paymentQuery);
		} catch (DaoException e) {
			log.error("findMemberPaymentDO failed");
			throw new ManagerException("getPaymentDOByAccountNo failed", e);
		}
	}

	@Override
	public MemberPaymentDO findMemberPaymentForValidate(
			MemberPaymentDO paymentQuery) throws ManagerException {
		try {
			return memberDAO.findMemberPaymentForValidate(paymentQuery);
		} catch (DaoException e) {
			log.error("findMemberPaymentForValidate failed");
			throw new ManagerException(e);
		}
	}

	@Override
	public Integer findMemberMyPageTypeByMemberId(long memberId)
			throws ManagerException {
		try {
			return memberDAO.findMemberMyPageTypeByMemberId(memberId);
		} catch (DaoException e) {
			log.error("findMemberMyPageTypeByMemberId failed");
			throw new ManagerException("findMemberMyPageTypeByMemberId failed", e);
		}
	}

	@Override
	public void updateMemberMyPageTypeByMemberId(long memberId, int myPageType)
			throws ManagerException {
		try {
			MemberDO memberDO = new MemberDO();
			memberDO.setMemberId(memberId);
			memberDO.setMyPageType(myPageType);
			memberDAO.updateMemberMyPageTypeByMemberId(memberDO);
		} catch (DaoException e) {
			log.error("updateMemberMyPageTypeByMemberId failed");
			throw new ManagerException("updateMemberMyPageTypeByMemberId failed", e);
		}
	}

	@Override
	public boolean findBuyerExt(long memberId) throws ManagerException {
		boolean isBuyerExt = true;
		try {
			MemberExtDO memberExtDO = memberExtDAO.findMemberExtDOByMemberId(memberId);
			if (memberExtDO == null || memberExtDO.getBuyerExt() == null ||
					(memberExtDO.getBuyerExt().indexOf(MemberExtDO.BUYER_CITY) == -1)) {
				isBuyerExt = false;
			}
		} catch (DaoException e) {
			log.error("findBuyerExt failed");
			throw new ManagerException("findBuyerExt failed", e);
		}
		return isBuyerExt;
	}

	/**
	 * <p>根据 DSR 维度编号查找 DSR 统计数据。找到时直接返回，未找到时生成默认的 DSR 统计数据</p>
	 *
	 * @param memberId  卖家会员编号
	 * @param dimension DSR 维度信息
	 * @param dsrStats 卖家既有的 DSR 统计数据
	 * @return 卖家既有或者默认的 DSR 统计数据
	 *
	 * @author gaobaowen
	 * @since 2011-10-19 10:40:03
	 */
	private DsrStatDO findDimensionDsrStat(long memberId, DsrDimensionDO dimension, List<DsrStatDO> dsrStats) {
		for (DsrStatDO dsrStat : dsrStats) {
			if (dimension.getId().equals(dsrStat.getDimensionId())) {
				dsrStat.setDimensionName(dimension.getName());
				if (log.isDebugEnabled()) {
					log.debug("findDimensionDsrStat, member id: [" + memberId + "], DSR dimension: [" +
							dimension.getId() + "], DSR dimension name after reset: [" + dsrStat.getDimensionName() + "]");
				}
				return dsrStat;
			}
		}
		log.info("findDimensionDsrStat, member id: [" + memberId + "], DSR dimension: [" + dimension.getId() + "|" +
				dimension.getName() + "] statistics data was not found, generate an empty DSR statistics data");
		return DsrStatDO.createEmpty(memberId, dimension);
	}
}
