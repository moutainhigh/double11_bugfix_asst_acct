package com.yuwang.pinju.core.member.ao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.cache.CacheUtil;
import com.yuwang.pinju.core.cache.MemcachedManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.common.tenpay.BaseSplitRequestHandler;
import com.yuwang.pinju.core.common.tenpay.ScriptClientResponseHandler;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.member.manager.MemberAsstManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.member.manager.SndaManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.DsrStatDO;
import com.yuwang.pinju.domain.member.MemberAgreementDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;
import com.yuwang.pinju.domain.member.SellerQualityInfoDO;
import com.yuwang.pinju.domain.member.SndaAccountDO;
import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;

public class MemberAOImpl implements MemberAO, MemberResultConstant, MemberKeyConstant {

	private final static Log log = LogFactory.getLog(MemberAOImpl.class);

	private MemberManager memberManager;
	private FileStorageManager fileStorageManager;
	private SndaManager sndaManager;
	private MemcachedManager qualitityMemcachedManager;
	private MemberSecurityManager memberSecurityManager;
	private MemberAsstManager memberAsstManager;

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

	public void setSndaManager(SndaManager sndaManager) {
		this.sndaManager = sndaManager;
	}

	public void setQualitityMemcachedManager(MemcachedManager qualitityMemcachedManager) {
		this.qualitityMemcachedManager = qualitityMemcachedManager;
	}

	public void setMemberSecurityManager(MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	public void setMemberAsstManager(MemberAsstManager memberAsstManager) {
		this.memberAsstManager = memberAsstManager;
	}

	public Result acceptAgreement(MemberAgreementDO agreement) {
		if (agreement == null || agreement.getMemberId() == null || agreement.getNickname() == null) {
			log.warn("agreement parameter error, " + agreement);
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			MemberDO member = findMember(agreement.getMemberId());
			if (member == null) {
				log.warn("cannot find member, member agreement infomation: " + agreement);
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}
			if (MemberDO.AGREE_AGREEMENT_YES.equals(member.getAgreeAgreement())) {
				log.info("member has been accepted user agreement, member agreement infomation: " + agreement + ", accept time: "
						+ DateUtil.formatDatetime(member.getAgreeAgreementTime()));
				return ResultSupportExt.createError(RESULT_MEMBER_ACCEPT_AGREEMENT);
			}
			member.setNickname(agreement.getNickname());
			member.setAgreeAgreement(MemberDO.AGREE_AGREEMENT_YES);
			member.setAgreeAgreementTime(DateUtil.current());
			int count = memberManager.updateMember(member);
			if (count < 1) {
				log.warn("process accept agreement, but update count is lesser than 1, member: " + member);
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}
			return ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("process accept agreement cause exception, parameter: " + agreement, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public MemberDO login(MemberTicketDO ticket) {
		MemberDO member = null;
		try {
			member = memberManager.login(ticket);
			if (log.isInfoEnabled()) {
				log.info("login success, " + member);
			}
		} catch (Exception e) {
			log.error("member login failed, ticket: " + ticket, e);
		}
		return member;
	}

	@Override
	public boolean logLogin(MemberLoginHisDO memberLoginHis) {
		try {
			MemberLoginHisDO his = memberManager.logLogin(memberLoginHis);
			if (log.isDebugEnabled()) {
				log.debug("log login, " + his);
			}

			int count = memberSecurityManager.updateSecurityLoginTime(his.getMemberId(), his.getLoginTime(), his.getLoginIp());
			if (log.isDebugEnabled()) {
				log.debug("[logLogin] update security information count: [" + count + "], " + his);
			}
			if (count < 1) {
				log.warn("[logLogin] update security information count is incorrect, count: [" + count + "], " + his);
			}
			return true;
		} catch (Exception e) {
			log.error("logLogin failed, " + memberLoginHis, e);
			return false;
		}
	}

	@Override
	public boolean setMemberSndaAccount(MemberDO member) {
		if (member == null) {
			log.info("setMemberSndaAccount, member parameter is null, cannot set snda account");
			return true;
		}
		if (!MemberDO.MEMBER_ORIGIN_SDO.equals(member.getMemberOrigin())) {
			log.info("setMemberSndaAccount, member: " + member.getMemberId()
					+ " is not SNDA account member, need not fetch sdo account");
			return true;
		}
		if (!EmptyUtil.isBlank(member.getSndaPtAccount())) {
			log.info("setMemberSndaAccount, current member has PT account, need not fetch sdo account, member id: "
					+ member.getMemberId() + ", pt: " + member.getSndaPtAccount());
			return true;
		}

		SndaAccountDO sndaAccount = sndaManager.getSndaAccount(member.getAccountName());
		if (sndaAccount == null) {
			log.warn("setMemberSndaAccount, cannot get SndaAccount object, the fetch failed, try again util member login again");
			return false;
		}

		member.setSndaAccount(sndaAccount);

		try {
			Integer count = memberManager.updateMember(member);
			log.debug("setMemberSndaAccount, update member count: " + count);
			return (count != null && count > 0);
		} catch (Exception e) {
			log.error("setMemberSndaAccount, update member info error, " + member, e);
			return false;
		}
	}

	@Override
	public boolean logout(String sessionId, long memberId, Date logoutTime) {
		try {
			int n = memberManager.logout(sessionId, memberId, logoutTime);
			if (log.isDebugEnabled()) {
				log.debug("logout, update logout, sessionId: " + sessionId + ", memberId: " + memberId + ", logoutTime: " +
					DateUtil.formatDatetime(logoutTime) + ", update table row count = [" + n + "]");
			}
			return true;
		} catch (Exception e) {
			log.error("execute logout failed, sessionId: " + sessionId + ", memberId: " + memberId + ", logoutTime: " +
					DateUtil.formatDatetime(logoutTime), e);
			return false;
		}
	}

	@Override
	public MemberDO findMember(long memberId) {
		try {
			return memberManager.findMember(memberId);
		} catch (Exception e) {
			log.error("findMember error, memberId: " + memberId, e);
			return null;
		}
	}

	@Override
	public MemberDO findMemberByNickname(String nickname) {
		try {
			return memberManager.findMemberByNickname(nickname);
		} catch (Exception e) {
			log.error("findMember error, nickname: " + nickname, e);
			return null;
		}
	}

	@Override
	public MemberInfoDO findMemberInfo(long memberId) {
		try {
			return memberManager.findMemberInfo(memberId);
		} catch (Exception e) {
			log.error("Exception error, memberId: " + memberId, e);
			return null;
		}
	}

	@Override
	public Result checkEmail(String email, long memberId) {
		try {
			if (EmptyUtil.isBlank(email)) {
				log.warn("check email, parameter email is null or empty");
				return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
			}
			MemberInfoDO memberInfo = memberManager.getMemberInfoByEmail(email);

			if (memberInfo != null) {
				if (memberId == 0 || memberInfo.getMemberId() != memberId) {
					log.info("check email, email [" + email + "] has been used by member id: " + memberInfo.getMemberId());
					return ResultSupportExt.createError(RESULT_MEMBER_EMAIL_HAS_EXIST);
				}
			}
			return ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("check email failed, email: " + email + ", memberId: " + memberId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public int[] updateMemberInfo(MemberDO member, MemberInfoDO memberInfo) {
		try {
			return memberManager.updateMemberInfo(member, memberInfo);
		} catch (Exception e) {
			log.error("updateMemberInfo error, member: " + member + ", memberInfo: " + memberInfo, e);
			return null;
		}
	}

	@Override
	public List<MemberDeliveryDO> findMemberDeliveries(long memberId) {
		try {
			return memberManager.findMemberDeliveries(memberId);
		} catch (Exception e) {
			log.error("findMemberDeliveries error, member id: " + memberId, e);
			return null;
		}
	}

	@Override
	public int updateMemberDelivery(MemberDeliveryDO memberDelivery) {
		try {
			return memberManager.updateMemberDelivery(memberDelivery);
		} catch (Exception e) {
			log.error("updateMemberDelivery error, member delivery: " + memberDelivery, e);
			return 0;
		}
	}

	@Override
	public MemberDeliveryDO saveMemberDelivery(MemberDeliveryDO memberDelivery) {
		try {
			return memberManager.saveMemberDelivery(memberDelivery);
		} catch (Exception e) {
			log.error("persistMemberDelivery error, member delivery: " + memberDelivery, e);
			return null;
		}
	}

	@Override
	public int countMemberDeliveries(long memberId) {
		try {
			return memberManager.countMemberDeliveries(memberId);
		} catch (Exception e) {
			log.error("countMemberDeliveries error, member id: " + memberId, e);
			return 0;
		}
	}

	@Override
	public int removeMemberDelivery(long deliveryId, long memberId) {
		try {
			return memberManager.removeMemberDelivery(deliveryId, memberId);
		} catch (Exception e) {
			log.error("removeMemberDelivery error, delivery id: " + deliveryId + ", mid: " + memberId, e);
			return 0;
		}
	}

	@Override
	public int setDefaultDelivery(final long deliveryId, final long memberId) {
		try {
			return memberManager.setDefaultDelivery(deliveryId, memberId);
		} catch (Exception e) {
			log.error("setDefaultDelivery error, delivery id: " + deliveryId + ", mid: " + memberId, e);
			return 0;
		}
	}

	@Override
	public Result getMemberShopQuality(long memberId) {
		return getMemberShopQuality(memberId, false);
	}

	@Override
	public Result getMemberShopQuality(long memberId, boolean isCacheDebug) {
		if (log.isInfoEnabled()) {
			log.info("getMemberShopQuality, member id: [" + memberId + "], isDevelopment: [" + PinjuConstant.isDevelopment +
					"], isCacheDebug: [" + isCacheDebug + "], need cache? [" + CacheUtil.isReadCache(isCacheDebug) + "]");
		}

		// member id is incorrect!!!
		if (!memberManager.isCorrectMemberId(memberId)) {
			log.error("getMemberShopQuality, member id is incorrect, member id: [" + memberId + "]");
			return ResultSupportExt.createError(RESULT_MEMBER_ID_INVALID);
		}

		try {

			// read SellerQualityInfoDO object from Memcached
			SellerQualityInfoDO sqi = qualitityMemcachedManager.getCacheObject(memberId, SellerQualityInfoDO.class);

			if (CacheUtil.isReadCache(isCacheDebug) && sqi != null) {
				log.info("getMemberShopQuality, get SellerQualityInfoDO object from cache, member id: " + memberId);
				return ResultSupportExt.createSuccess(sqi);
			}

			if (log.isDebugEnabled()) {
				log.info("getMemberShopQuality, can not get SellerQualityInfoDO object from cache, member id: " + memberId);
			}

			// find MemberDO object
			MemberDO member = findMember(memberId);
			if (member == null) {
				log.warn("getMemberShopQuality, cannot find Member, member id: " + memberId);
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}

			// find SellerQualityDO object
			SellerQualityDO sellerQuality = memberManager.getSellerQualityByMemberId(memberId);
			if (sellerQuality == null) {
				log.warn("getMemberShopQuality, cannot find SellerQuality, member id: " + memberId);
				return ResultSupportExt.createError(RESULT_MEMBER_SELLER_QUALITY_NOT_FOUND);
			}

			// get current seller DSR statistics data
			List<DsrStatDO> dsrStats = memberManager.getIntactDsrStatsByMemberId(memberId, isCacheDebug);
			if (dsrStats == null) {
				log.warn("getMemberShopQuality, cannot find List<DsrStatDO>, member id: " + memberId);
				dsrStats = new ArrayList<DsrStatDO>(0);
			}

			sqi = new SellerQualityInfoDO(sellerQuality, dsrStats);

			// write SellerQualityInfoDO object to Memcached
			boolean result = qualitityMemcachedManager.setCacheObject(memberId, sqi);

			if (log.isInfoEnabled()) {
				log.info("getMemberShopQuality, cache set result: " + result + ", member id: " + memberId);
			}

			return ResultSupportExt.createSuccess(sqi);
		} catch (Exception e) {
			log.error("getMemberShopQuality error, member id: " + memberId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result getMemberSnsInfoByMemberId(long memberId) {
		if (log.isDebugEnabled()) {
			log.debug("getMemberSnsInfoByMemberId, member id: " + memberId);
		}
		MemberDO member = findMember(memberId);
		if (member == null) {
			log.warn("getMemberSnsInfoByMemberId, cannot find Member, member id: " + memberId);
			return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
		}
		try {
			MemberSnsInfoDO memberSnsInfo = memberManager.getMemberSnsInfo(memberId);
			Result result = ResultSupportExt.createSuccess();
			result.setModel(KEY_MEMBER_SNS_INFO, memberSnsInfo);
			return result;
		} catch (Exception e) {
			log.error("getMemberSnsInfoByMemberId error, member id: " + memberId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result saveOrUpdateMemberSnsInfo(MemberSnsInfoDO memberSnsInfo) {
		if (log.isDebugEnabled()) {
			log.debug("saveOrUpdateMemberSnsInfo, " + memberSnsInfo);
		}
		MemberDO member = findMember(memberSnsInfo.getMemberId());
		if (member == null) {
			log.warn("saveOrUpdateMemberSnsInfo, cannot find Member, member id: " + memberSnsInfo.getMemberId());
			return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
		}
		try {
			MemberSnsInfoDO snsInfo = memberManager.getMemberSnsInfo(memberSnsInfo.getMemberId());
			int updateCount = 0;
			if (snsInfo == null) {
				snsInfo = memberManager.insertMemberSnsInfo(member, memberSnsInfo);
				updateCount = 1;
			} else {
				memberSnsInfo.setId(snsInfo.getId());
				memberSnsInfo.setGmtModified(new Date());
				updateCount = memberManager.updateMemberSnsInfo(member, memberSnsInfo);
				if (!EmptyUtil.isBlank(memberSnsInfo.getAvatarsBasePath())) {
					snsInfo.setAvatarsBasePath(memberSnsInfo.getAvatarsBasePath());
				}
				snsInfo.setBiography(memberSnsInfo.getBiography());
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(KEY_MEMBER_SNS_INFO, snsInfo);
			result.setModel(KEY_MEMBER_UPDATE_COUNT, updateCount);
			result.setModel(KEY_MEMBER_INFO_VERSION, member.getInfoVersion());
			return result;
		} catch (Exception e) {
			log.error("saveOrUpdateMemberSnsInfo error, " + memberSnsInfo, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result saveAvatars(File file, String filename, long memberId, String nickname) {
		try {

			String[] filenames = fileStorageManager.saveUserIcon(new File[] { file }, new String[] { filename }, memberId,
					nickname);
			if (filenames == null || filenames.length == 0 || filenames[0] == null) {
				log.error("saveAvatars error, member id: " + memberId + ", nickname: " + nickname + "file: "
						+ file.getAbsolutePath() + "file length: " + file.length() + ", original filename: " + filename);
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel("avatars", filenames);
			return result;
		} catch (Exception e) {
			log.error("saveAvatars error, member id: " + memberId + ", nickname: " + nickname + "file: "
					+ file.getAbsolutePath() + "file length: " + file.length() + ", original filename: " + filename);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result getPaymentAccount(long memberId) {
		if (log.isDebugEnabled()) {
			log.debug("getPaymentAccount, member id: [" + memberId + "]");
		}
		if (!memberManager.isCorrectMemberId(memberId)) {
			log.warn("getPaymentAccount, member id is invalid");
			return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
		}
		MemberDO member = findMember(memberId);
		if (member == null) {
			log.warn("getPaymentAccount, cannot find member info of member id: [" + memberId + "]");
			return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
		}
		try {
			MemberPaymentDO mp = memberManager.findBoundMemberPayment(new MemberPaymentDO(memberId, MemberPaymentDO.INSTITUTION_TENPAY));
			if (log.isDebugEnabled()) {
				log.debug("getPaymentAccount, member id: [" + memberId + "], payment id: ["
						+ (mp == null ? null : mp.getId()) + "]");
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(KEY_MEMBER_PAYMENT, mp);
			return result;
		} catch (Exception e) {
			log.error("getPaymentAccount error, member id: " + memberId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result boundPaymentAccount(MemberPaymentDO payment) {
		if (log.isDebugEnabled()) {
			log.debug("boundPaymentAccount, payment: " + payment);
		}
		if (payment == null) {
			log.warn("boundPaymentAccount, payment parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		if (!memberManager.isCorrectMemberId(payment.getMemberId())) {
			log.warn("boundPaymentAccount, member id is invalid");
			return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
		}

		try {
			MemberPaymentDO retMemberPaymetDO = memberManager.findMemberPaymentDOByMemberAndInst(payment);
			if (retMemberPaymetDO != null) {
				int count = memberManager.updateMemberPaymentByAccount(payment);
				if (count != 1) {
					log.error("boundPaymentAccount update error, payment: " + payment + ",count=" + count);
					return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
				}
			} else {
				memberManager.boundMemberPayment(payment);
			}
			return ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("boundPaymentAccount error, payment: " + payment, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result unboundPaymentAccount(long memberId) {
		if (log.isDebugEnabled()) {
			log.debug("unboundPaymentAccount, memberId: " + memberId);
		}
		if (!memberManager.isCorrectMemberId(memberId)) {
			log.warn("unboundPaymentAccount, member id is invalid");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		MemberDO member = findMember(memberId);
		if (member == null) {
			log.warn("unboundPaymentAccount, cannot find member info of member id: [" + memberId + "]");
			return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
		}
		MemberPaymentDO payment = null;
		try {
			payment = memberManager.findBoundMemberPayment(new MemberPaymentDO(memberId, MemberPaymentDO.INSTITUTION_TENPAY));
			if (payment == null) {
				log.warn("unboundPaymentAccount, current user has not bound pay account, need not unbound");
				return ResultSupportExt.createError(RESULT_PAYMENT_NOT_EXISTS);
			}
			int count = memberManager.unboundMemberPayment(payment);
			if (log.isDebugEnabled()) {
				log.debug("unboundPaymentAccount, member id: [" + payment.getMemberId() + "], update count: [" + count
						+ "]");
			}
			if (count < 1) {
				return ResultSupportExt.createError(RESULT_UPDATE_COUNT_ERROR);
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(KEY_MEMBER_UPDATE_COUNT, count);
			return result;
		} catch (Exception e) {
			log.error("unboundPaymentAccount error, memberId: [" + memberId + "], bounded payment account: " + payment, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result signPayAgreement(MemberPaymentDO memberPaymentDO) {
		Result result = new ResultSupport();
		try {
			//已绑定且未签订协议
			if (memberPaymentDO != null && MemberPaymentDO.SIGN_AGREEMENT_NO.equals(memberPaymentDO.getSignAgreement())) {
				memberPaymentDO.setSignAgreement(MemberPaymentDO.SIGN_AGREEMENT_YES); //已签订
				memberPaymentDO.setSignAgreementTime(new Date()); //签订时间
				memberManager.signPayAgreement(memberPaymentDO);
			}
		} catch (Exception e) {
			log.error("signPayAgreement error, memberId: [" + memberPaymentDO.getMemberId() + "]", e);
			result.setSuccess(false);
			result.setResultCode(MemberResultConstant.RESULT_SIGN_PAY_AGREEMENT_FAIL);
		}
		return result;
	}

	@Override
	public Result isSignPayAgreement(long memberId) {
		Result result = new ResultSupport();
		try {
			MemberPaymentDO memberPaymentDO = memberManager.findBoundMemberPayment(new MemberPaymentDO(memberId, MemberPaymentDO.INSTITUTION_TENPAY));
			if (memberPaymentDO == null) { //未点击绑定账号按钮
				result.setSuccess(false);
				result.setResultCode(MemberResultConstant.RESULT_MEMBERID_FAIL);
				return result;
			}
			if (MemberPaymentDO.BOUND_STATUS_UNBOUND.equals(memberPaymentDO.getBondStatus())) { //未绑定
				result.setSuccess(false);
				result.setResultCode(MemberResultConstant.RESULT_SIGN_PAY_BIND_NOT);
			}else if (MemberPaymentDO.SIGN_AGREEMENT_NO.equals(memberPaymentDO.getSignAgreement())) { //查本地库未签协议
				Result resultRel = getInquireRelation(memberPaymentDO.getAccountNO()); //调财付通接口远程查询委托协议签约状态
				if (resultRel.isSuccess()) { //远程查询成功则更新本地库的签约状态为签约成功
					signPayAgreement(memberPaymentDO);
				}else {
					result.setSuccess(false);
					result.setResultCode(MemberResultConstant.RESULT_SIGN_PAY_AGREEMENT_NOT);
				}
			}
			result.setModel("memberPaymentDO",memberPaymentDO);
		} catch (Exception e) {
			log.error("isSignPayAgreement error, memberId: [" + memberId + "]", e);
			result.setSuccess(false);
			result.setResultCode(MemberResultConstant.RESULT_GET_PAY_AGREEMENT_FAIL);
		}
		return result;
	}

	@Override
	public Result updateMemberPaymentByAccount(MemberPaymentDO memberPayment) {
		Result result = null;
		try {
			memberManager.updateMemberPaymentByAccount(memberPayment);
			result = ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("updateMemberPaymentByAccount error, memberPayment: [" + memberPayment.toString() + "]", e);
			result = ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
		return result;
	}

	@Override
	public MemberPaymentDO getPaymentDOByAccountNo(String accountNo) {
		try {
			return memberManager.getPaymentDOByAccountNo(accountNo);
		} catch (ManagerException e) {
			log.error("getPaymentDOByAccountNo error, accountNo: [" + accountNo + "]", e);
		}
		return null;
	}

	@Override
	public Result findBoundMemberPaymented(MemberPaymentDO paymentQuery) {
		try {
			if (memberManager.findMemberPaymentForValidate(paymentQuery) != null) {
				return ResultSupportExt.createError(PAY_ACCOUNT_ALREADY_BOUND_ERROR);
			}
			return ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("findBoundMemberPaymented error, payment: " + paymentQuery, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	/**
	 * Created on 2011-9-23
	 * @desc <p>Discription:[委托退款关系查询接口]</p>
	 * @param payAccountNo 用户的财付通账号
	 * @return String
	 * @author:[石兴]
	 * @update:[2011-9-23] [更改人姓名]
	 */
	@Override
	public Result getInquireRelation(String payAccountNo) {
		Result result = new ResultSupport();
		result.setSuccess(false);
		try {
			BaseSplitRequestHandler reqHandler = new BaseSplitRequestHandler(null, null);
			TenpayHttpClient httpClient = new TenpayHttpClient();
			ScriptClientResponseHandler resHandler = new ScriptClientResponseHandler();

			reqHandler.init();
			reqHandler.setKey(PinjuConstant.TENPAY_PAY_MD5KEY); // 设置密钥
			//reqHandler.setKey("82d2f8b9fd7695aec51415ab2900a755"); // 设置密钥测试用
			reqHandler.setGateUrl(PinjuConstant.TENPAY_INQUIRE_RELATION_GATEURL); // 委托关系查询接口URL
			reqHandler.setParameter("version", "4"); // 版本号
			reqHandler.setParameter("bargainor_id", PinjuConstant.TENPAY_PAY_PARTNER); // 商户号
			//reqHandler.setParameter("bargainor_id", "1900000107"); // 商户号测试用
			reqHandler.setParameter("purchaser_id", payAccountNo); // 用户的财付通账号(QQ或EMAIL)
			reqHandler.setParameter("type", "1"); // 类型：1、委托退款，2、委托代扣
			reqHandler.setParameter("return_url", "http://127.0.0.1/"); // 固定值,必须为http://127.0.0.1/
			reqHandler.setParameter("cmdno", "99"); // 命令字,必须为99

			String caURL = PinjuConstant.TENPAY_CERTIFICATE_PATH.concat("cacert.pem");
			String certInfoURL = PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(PinjuConstant.TENPAY_PAY_PARTNER.concat(".pfx"));
			//String certInfoURL = PinjuConstant.TENPAY_CERTIFICATE_PATH.concat("1900000107.pfx");//测试用
			// 设置CA证书
			httpClient.setCaInfo(new File(caURL));
			// 设置个人(商户)证书
			httpClient.setCertInfo(new File(certInfoURL), PinjuConstant.TENPAY_PAY_PARTNER);
			//httpClient.setCertInfo(new File(certInfoURL), "1900000107"); //测试用

			// 请求的url
			String requestUrl = reqHandler.getRequestURL();
			// 设置请求内容
			httpClient.setReqContent(requestUrl);
			String rescontent = "null";
			// 后台调用
			if (httpClient.call()) {
				rescontent = httpClient.getResContent();
				// 设置结果参数
				resHandler.setContent(rescontent);
				resHandler.setKey(PinjuConstant.TENPAY_PAY_MD5KEY);
				//resHandler.setKey("82d2f8b9fd7695aec51415ab2900a755");//测试用
				// 获取返回参数
				String status = resHandler.getParameter("status");

				// 判断签名及结果
				if (resHandler.isTenpaySign() && "1".equals(status)) {
					result.setSuccess(true);
				} else {
					result.setSuccess(false);
					result.setResultCode(MemberResultConstant.RESULT_SIGN_NOT_EXIST);
					log.error("验证签名失败或委托退款关系不存在");
				}
				log.error("purchaser_id:"+ resHandler.getParameter("purchaser_id") + " status:" + resHandler.getParameter("status"));
			} else {
				result.setSuccess(false);
				log.error("有可能因为网络原因，请求已经处理，但未收到应答,err_code:"+ httpClient.getResponseCode() + "errmsg:" + httpClient.getErrInfo());
			}
			log.error("debug info " + reqHandler.getDebugInfo());
		} catch (Exception e) {
			log.error("Event#MemberCheckAction#getInquireRelation fail");
		}
		return result;
	}

	@Override
	public boolean asstLogin(MemberLoginHisDO his) {
		if (his == null) {
			log.warn("[asstLogin] parameter is null");
			return false;
		}
		if (!memberManager.isCorrectMemberId(his.getMemberId())) {
			log.warn("[asstLogin] parameter member id is invalid, " + his);
			return false;
		}
		try {
			int count = memberAsstManager.logMemberAsstLogin(his);
			if (count < 1) {
				log.warn("[asstLogin] record assistant member login time and ip update row count error, " + his);
			}
			boolean result = memberAsstManager.initAsstPermCache(his.getMemberId());
			if (!result) {
				log.warn("[asstLogin] init assistant member permission cache data failed, " + his);
			}
			if (log.isInfoEnabled()) {
				log.info("[asstLogin] init assistant member permission cache data, result: [" + result + "], " +
						"record login time and ip update count: [" + count + "], " + his);
			}
			return true;
		} catch (Exception e) {
			log.error("[asstLogin] cause exception, " + his);
			return false;
		}
	}
}
