package com.yuwang.pinju.core.member.manager.impl;

import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_INVALID;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_SECURITY_LINK_NOT_FOUND;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_SECURITY_MOBILE_NOT_FOUND;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MOBILE_DAILY_OVER;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MOBILE_INVALID;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MOBILE_NOT_EXISTS;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_PARAMETERS_ERROR;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.Cipher;
import javax.crypto.Mac;

import org.apache.axis.encoding.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.message.constants.MessageConstants;
import com.yuwang.message.domain.message.SenderModel;
import com.yuwang.message.jms.manager.MessageJmsManager;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.cache.MemcachedManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstantUtil;
import com.yuwang.pinju.core.member.dao.MemberDao;
import com.yuwang.pinju.core.member.dao.MemberSecurityDAO;
import com.yuwang.pinju.core.member.dao.MemberSecurityEmailDAO;
import com.yuwang.pinju.core.member.dao.MemberSecurityEmailLinkDAO;
import com.yuwang.pinju.core.member.dao.MemberSecurityHisDAO;
import com.yuwang.pinju.core.member.dao.MemberSecurityMobileCodeDAO;
import com.yuwang.pinju.core.member.dao.MemberSecurityMobileDAO;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.message.manager.sms.SmsMessageManager;
import com.yuwang.pinju.core.util.CharsetUtil;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.RandomUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.security.MemberSecurity;
import com.yuwang.pinju.domain.member.security.MemberSecurityDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailHisDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO.LinkType;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileHisDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityParam.DefaultSecurityParam;
import com.yuwang.pinju.domain.member.security.SecurityLinkDO;
import com.yuwang.pinju.domain.member.security.SmsCodeValidatorVO;
import com.yuwang.pinju.domain.member.security.SmsSenderVO;
import com.yuwang.pinju.domain.message.SmsMessage;
import com.yuwang.pinju.domain.message.SmsMessageType;

/**
 * <p>implements {@link MemberSecurityManager}</p>
 *
 * @author gaobaowen
 * @since 2011-9-1 17:35:26
 */
public class MemberSecurityManagerImpl extends TransactionMemberManager implements MemberSecurityManager {

	private final static Log log = LogFactory.getLog(MemberSecurityManagerImpl.class);

	private final static long EXPIRES = 24L * 3600L * 1000L * 1; // PinjuConstant.EMAIL_LINK_EXPIRE_MILLIS; //24L * 3600L * 1000L * 1;

	private MemberManager memberManager;
	private MemberSecurityEmailLinkDAO memberSecurityEmailLinkDAO;
	private MemberDao memberDAO;
	private MemberSecurityEmailDAO memberSecurityEmailDAO;
	private MemberSecurityMobileDAO memberSecurityMobileDAO;
	private MessageJmsManager messageJmsManager;
	private MemberSecurityHisDAO memberSecurityHisDAO;
	private MemberSecurityDAO memberSecurityDAO;
	private MemberSecurityMobileCodeDAO memberSecurityMobileCodeDAO;
	private MemcachedManager mobileDailyMemcachedManager;
	private SmsMessageManager smsMessageManager;

	public void setMessageJmsManager(MessageJmsManager messageJmsManager) {
		this.messageJmsManager = messageJmsManager;
	}

	public void setMemberSecurityEmailDAO(MemberSecurityEmailDAO memberSecurityEmailDAO) {
		this.memberSecurityEmailDAO = memberSecurityEmailDAO;
	}

	public void setMemberSecurityEmailLinkDAO(MemberSecurityEmailLinkDAO memberSecurityEmailLinkDAO) {
		this.memberSecurityEmailLinkDAO = memberSecurityEmailLinkDAO;
	}

	public void setMemberDAO(MemberDao memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setMemberSecurityHisDAO(MemberSecurityHisDAO memberSecurityHisDAO) {
		this.memberSecurityHisDAO = memberSecurityHisDAO;
	}

	public void setMemberSecurityDAO(MemberSecurityDAO memberSecurityDAO) {
		this.memberSecurityDAO = memberSecurityDAO;
	}

	public void setMobileDailyMemcachedManager(MemcachedManager mobileDailyMemcachedManager) {
		this.mobileDailyMemcachedManager = mobileDailyMemcachedManager;
	}

	public void setMemberSecurityMobileCodeDAO(MemberSecurityMobileCodeDAO memberSecurityMobileCodeDAO) {
		this.memberSecurityMobileCodeDAO = memberSecurityMobileCodeDAO;
	}

	public void setSmsMessageManager(SmsMessageManager smsMessageManager) {
		this.smsMessageManager = smsMessageManager;
	}

	public void setMemberSecurityMobileDAO(MemberSecurityMobileDAO memberSecurityMobileDAO) {
		this.memberSecurityMobileDAO = memberSecurityMobileDAO;
	}

	public boolean isMobile(String mobile) {
		if (mobile == null) {
			return false;
		}
		return PinjuConstant.MOBILE_NUMBER_PATTERN.matcher(mobile).matches();
	}

	@Override
	public int updateSecurityLoginTime(final long memberId, final Date currentLoginTime, final String currentLoginIp) {
		if (log.isDebugEnabled()) {
			log.debug("[updateSecurityLoginTime] parameter list, member id: [" + memberId + "], " +
					"login time: [" + DateUtil.formatDatetime(currentLoginTime) + "], login ip: [" + currentLoginIp + "]");
		}

		if (!memberManager.isCorrectMemberId(memberId)) {
			log.warn("[updateSecurityLoginTime] parameter member id is invalid, member id: [" + memberId + "], " +
					"login time: [" + DateUtil.formatDatetime(currentLoginTime) + "], login ip: [" + currentLoginIp + "]");
			return 0;
		}
		try {

			int count = memberSecurityDAO.updateLastLoginTime(memberId, currentLoginTime, currentLoginIp);

			// 更新数量大于 0，表示更新成功
			if (count > 0) {
				if (log.isInfoEnabled()) {
					log.info("[updateSecurityLoginTime] update success, count: [" + count + "], member id: [" + memberId + "], " +
					"login time: [" + DateUtil.formatDatetime(currentLoginTime) + "], login ip: [" + currentLoginIp + "]");
				}
				return count;
			}

			log.warn("[updateSecurityLoginTime] update count [" + count + "] is incorrect, execute retry policy, member id: [" +
							memberId + "], login time: [" + DateUtil.formatDatetime(currentLoginTime) + "], " +
							"login ip: [" + currentLoginIp + "]");

			// 没有更新到？可能是 MemberSecurity 没有数据，不过一般来说不会发生这种情况
			final MemberSecurityDO security = memberSecurityDAO.getSecurityByMid(memberId);
			if (security == null) {

				// 会员安全信息不存在？
				log.warn("[updateSecurityLoginTime] according member id can not find security information, start rebuild it, " +
							"member id: [" + memberId + "], login time: [" + DateUtil.formatDatetime(currentLoginTime) + "], " +
							"login ip: [" + currentLoginIp + "]");

				MemberDO member = memberManager.findMember(memberId);
				if (member == null) {
					// 会员都没找到，啥都不用再做了
					log.warn("[updateSecurityLoginTime] security rebuild it, member was not found!!!, member id: [" + memberId + "], " +
							"login time: [" + DateUtil.formatDatetime(currentLoginTime) + "], login ip: [" + currentLoginIp + "]");
					return 0;
				}

				final MemberSecurityDO newSecurity = member.createMemberSecurity();
				newSecurity.setCurrentLoginTime(currentLoginTime);
				newSecurity.setCurrentLoginIp(currentLoginIp);

				executeInTransaction(new TransactionExecutor<MemberSecurityDO>() {
					public MemberSecurityDO execute() throws DaoException {
						return memberSecurityDAO.addMemberSecurity(newSecurity);
					}
				}, "updateSecurityLoginTime", newSecurity);
				log.warn("[updateSecurityLoginTime] security rebuild it success, " + newSecurity);
				return 1;
			}

			log.warn("[updateSecurityLoginTime] security has existed, update it again, member id: [" + memberId + "], " +
							"login time: [" + DateUtil.formatDatetime(currentLoginTime) + "], login ip: [" + currentLoginIp + "]");

			// 若已经存在，再更新一次！！！
			count = executeInTransaction(new TransactionExecutor<Integer>() {
				@Override
				public Integer execute() throws DaoException {
					return memberSecurityDAO.updateLastLoginTime(memberId, currentLoginTime, currentLoginIp);
				}
			}, "updateSecurityLoginTime");

			log.warn("[updateSecurityLoginTime] update it again, count: [" + count + "]");

			return count;
		} catch (Exception e) {
			log.error("[updateSecurityLoginTime] cause exception, member id: [" + memberId + "], login time: [" +
					DateUtil.formatDatetime(currentLoginTime) + "], login ip: [" + currentLoginIp + "]", e);
			return 0;
		}
	}

	@Override
	public Result sendSmsCodeToLogin(SmsSenderVO smsSender) throws ManagerException {
		if (!memberManager.isCorrectMemberId(smsSender.getMemberId())) {
			log.warn("[sendSmsCodeToLogin] member id is invalid, " + smsSender);
			return ResultSupportExt.createError(RESULT_MEMBER_INVALID);
		}
		if (StringUtils.isBlank(smsSender.getLoginName())) {
			log.warn("[sendSmsCodeToLogin] loginName is null or empty, " + smsSender);
			return ResultSupportExt.createError(RESULT_MEMBER_INVALID);
		}
		try {
			MemberSecurityMobileDO securityMobile = getSendSmsSecurityMobile(smsSender);
			if (securityMobile == null) {
				log.warn("[sendSmsCodeToLogin] according to memberId: [" + smsSender.getMemberId() + "] can not find MemberSecurityMobile data, " + smsSender);
				return ResultSupportExt.createError(RESULT_MOBILE_NOT_EXISTS);
			}
			if (!securityMobile.getLoginName().equals(smsSender.getLoginName())) {
				log.warn("[sendSmsCodeToLogin] according to member id: [" + smsSender.getMemberId() + "] retrieve loginName (" + securityMobile.getLoginName() + ") in MemberSecurityMobile is not same with current loginName: [" + smsSender.getLoginName() + "]");
				return ResultSupportExt.createError(RESULT_MEMBER_INVALID);
			}
			return sendSms(securityMobile, smsSender.getSmsType(), smsSender.getClientIp());
		} catch (DaoException e) {
			throw new ManagerException("[sendSmsCodeToLogin] error, " + smsSender, e);
		}
	}

	private MemberSecurityMobileDO getSendSmsSecurityMobile(SmsSenderVO smsSender) throws DaoException {

		// 如果是解绑，从安全手机中获取信息
		if (smsSender.getSmsType() == SmsMessageType.UNBOUNDING_CODE) {
			if (log.isInfoEnabled()) {
				log.info("[getSendSmsSecurityMobile] sms send type is UNBOUNDING_CODE, get it from member id, " + smsSender);
			}
			return memberSecurityMobileDAO.getSecurityMobileByMid(smsSender.getMemberId());
		}

		// 如果是绑定，从发送信息中获取
		if (smsSender.getSmsType() == SmsMessageType.BOUNDING_CODE) {
			if (log.isInfoEnabled()) {
				log.info("[getSendSmsSecurityMobile] sms send type is BOUNDING_CODE, wrap MemberSecurityMobileDO object from SmsSenderVO, " + smsSender);
			}
			if (!isMobile( smsSender.getMobile() )) {
				log.warn("[getSendSmsSecurityMobile] sms send type is BOUNDING_CODE, mobile is invalid, " + smsSender);
				return null;
			}
			MemberSecurityMobileDO securityMobile = new MemberSecurityMobileDO();
			securityMobile.setMemberId(smsSender.getMemberId());
			securityMobile.setLoginName(smsSender.getLoginName());
			securityMobile.setMobile(smsSender.getMobile());
			return securityMobile;
		}
		return null;
	}

	@Override
	public Result sendSmsCodeToUnlogin(SmsSenderVO smsSender) throws ManagerException {
		if(!isMobile(smsSender.getMobile())) {
			log.warn("[sendSmsCodeToUnlogin] mobile is invalid, " + smsSender);
			return ResultSupportExt.createError(RESULT_MOBILE_INVALID);
		}
		MemberSecurityMobileDO securityMobile = MemberSecurityMobileDO.createTemp(smsSender.getMobile());
		return sendSms(securityMobile, smsSender.getSmsType(), smsSender.getClientIp());
	}

	private Result sendSms(MemberSecurityMobileDO securityMobile, SmsMessageType type, String clientIp) throws ManagerException {
		if (securityMobile == null) {
			throw new ManagerException("send SMS, parameter MemberSecurityMobileDO is null, clientIp: [" + clientIp + "], SmsMessageType: [" + type + "]");
		}
		if (type == null) {
			throw new ManagerException("send SMS, parameter SmsMessageType is null, clientIp: [" + clientIp + "], MemberSecurityMobileDO: [" + securityMobile + "]");
		}
		if (log.isDebugEnabled()) {
			log.debug("send SMS, securityMobile: " + securityMobile + ", type: [" + type.getCodeType() + "]");
		}

		try {

			// 发送频率检查（暂时去除，使用前端控制）
			// revision: 19702

			// 当天发送次数检查
			String dailyKey = securityMobile.getMobile() + '.' + type.getCodeType() + '.' + DateUtil.getCurrentYYYYMMDD();
			Integer dailyCount = mobileDailyMemcachedManager.getCacheObject(dailyKey, Integer.class);
			if (log.isDebugEnabled()) {
				log.debug("send SMS, current daily count: [" + dailyCount + "]");
			}
			if (dailyCount != null && dailyCount >= PinjuConstant.MOBILE_CODE_DAILY_MAX_COUNT) {
				log.warn("send SMS, mobile send count over daily limit. mobile: [" + securityMobile.getMobile() + "], type: [" + type.getCodeType() + "]");
				return ResultSupportExt.createError(RESULT_MOBILE_DAILY_OVER);
			}

			// 生成校验码
			String code = RandomUtil.randomNumber(PinjuConstant.MOBILE_CODE_LENGTH);
			MemberSecurityMobileCodeDO mobileCode = new MemberSecurityMobileCodeDO(securityMobile, type.getCodeType(), code, PinjuConstant.MOBILE_CODE_EXPIRE_SECONDS, clientIp);
			mobileCode = memberSecurityMobileCodeDAO.addMobileCode(mobileCode);
			sendSms(mobileCode);

			mobileDailyMemcachedManager.setCacheObject(dailyKey, dailyCount == null ? 1 : dailyCount + 1);

			return ResultSupportExt.createSuccess(mobileCode);
		} catch (DaoException e) {
			String message = "sendSMS failed, codeType: [" + type.getCodeType() + "], clientIp: [" + clientIp + "], securityMobile: " + securityMobile;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	private void sendSms(MemberSecurityMobileCodeDO mobileCode) {
		SmsMessage sms = new SmsMessage(mobileCode);
		sms.addParameter("code", mobileCode.getCode());
		smsMessageManager.sendSms(sms);
	}

	public MemberSecurityMobileCodeDO validateMobileCode(SmsCodeValidatorVO validator) throws ManagerException {

		// check parameter validity
		checkSmsCodeValidator(validator);
		try {

			// 根据参数的数据获取 MemberSecurityMobileCodeDO 对象
			final MemberSecurityMobileCodeDO mobileCode = memberSecurityMobileCodeDAO.getUnconfirmMobileCode(validator.getMobile(), validator.getMessageId(), validator.getType().getCodeType());

			// 没有找到？表示参数中的查询条件是无效的，或者是已被其他线程确认过了
			if (mobileCode == null) {
				log.warn("[validateMobileCode] mobileCode is null, " + validator);
				return null;
			}

			mobileCode.setToken(MemberSecurity.generateMessageToken());
			mobileCode.setCodeStatus(MemberSecurityMobileCodeDO.CODE_STATUS_VALIDATED);
			mobileCode.setValidateIp(validator.getClientIp());

			// 更新该条数据的状态
			int updateCount = executeInTransaction(new TransactionExecutor<Integer>() {
				@Override
				public Integer execute() throws DaoException {
					return memberSecurityMobileCodeDAO.confirmMobileCode(mobileCode);
				}
			}, "validateMobileCode", validator);

			if (log.isDebugEnabled()) {
				log.debug("[validateMobileCode] confirm mobile code update count [" + updateCount + "], parameters: " + validator);
			}

			// 如果更新数量为 0，那表示被其他线程或者服务器提前更新了，因此该次的校验结果视为不正确
			if (updateCount == 0) {
				log.warn("[validateMobileCode] confirm mobile code update count is 0. current message id mobile code " + "has been used other member, parameter: " + validator);
				return null;
			}

			if (!mobileCode.getCode().equals(validator.getCode())) {
				log.warn("[validateMobileCode] code inputed by member is not same with current message id code: [" + mobileCode.getCode() + "], parameters: " + validator);
				return null;
			}
			return mobileCode;
		} catch (DaoException e) {

			throw new ManagerException("[validateMobileCode] cause exception, parameter: [" + validator + "]", e);

		}
	}

	private void checkSmsCodeValidator(SmsCodeValidatorVO validator) throws ManagerException {
		if (validator == null) {
			throw new ManagerException("parameter is null");
		}
		if (StringUtils.isBlank(validator.getMessageId()) || validator.getMessageId().length() != MemberSecurityMobileCodeDO.MESSAGE_ID_LENGTH
				|| StringUtils.isBlank(validator.getCode()) || validator.getCode().length() != PinjuConstant.MOBILE_CODE_LENGTH
				|| validator.getType() == null) {
			throw new ManagerException("parameter is invalid, " + validator);
		}
	}

	public MemberSecurityEmailDO findMemberSecurityEmailByMemberId(long memberId) throws ManagerException {
		try {
			return memberSecurityEmailDAO.findMemberSecurityEmailByMemberId(memberId);
		} catch (DaoException e) {
			String message = "findMemberSecurityEmailByMemberId execute failed, memberId: [" + memberId + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberSecurityMobileDO findMemberSecurityMobileByMemberId(
			long memberId) throws ManagerException {
		try {
			return memberSecurityMobileDAO.getSecurityMobileByMid(memberId);
		} catch (DaoException e) {
			String message = "findMemberSecurityMobileByMemberId execute failed, memberId: [" + memberId + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberSecurityMobileDO getMemberSecurityMobileDOByMobile(
			String mobile) throws ManagerException {
		try {
			if (EmptyUtil.isBlank(mobile)) {
				return null;
			}
			return memberSecurityMobileDAO.getSecurityMobileByMobile(mobile);
		} catch (DaoException e) {
			String message = "getMemberSecurityMobileDOByMobile execute failed, mobile: " + mobile;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberSecurityMobileDO addMemberSecurityMobile(final MemberSecurityMobileDO memberSecurityMobile)
			throws ManagerException {
		try {
			final int status = 1;
			final MemberSecurityDO security = memberSecurityDAO.getSecurityByMid(memberSecurityMobile.getMemberId());
			return executeInTransaction(new TransactionExecutor<MemberSecurityMobileDO>(){
				@Override
				public MemberSecurityMobileDO execute() throws DaoException{
					if(security == null){
						MemberSecurityDO memberSecurity = new MemberSecurityDO();
						memberSecurity.setMemberId(memberSecurityMobile.getMemberId());
						memberSecurity.addSecurityMobileMask();
						memberSecurity.setLoginName(memberSecurityMobile.getLoginName());
						memberSecurity.setCurrentLoginIp(memberSecurityMobile.getValidationIp());
						memberSecurityDAO.addMemberSecurity(memberSecurity);
					}else{
						security.addSecurityMobileMask();
						int k = memberSecurityDAO.updateSecurityMask(security);
						if(k == 0){
							return null;
						}
					}
					MemberSecurityMobileHisDO mobileHis = MemberSecurityMobileHisDO.createInstance(memberSecurityMobile, status);
					memberSecurityHisDAO.addSecurityMobileHis(mobileHis);
					return memberSecurityMobileDAO.addSecurityMobile(memberSecurityMobile);
				}
			}, "addMemberSecurityMobile", memberSecurityMobile);
		} catch (Exception e) {
			log.warn("addMemberSecurityMobile error" + memberSecurityMobile.toString(), e);
			return null;
		}
	}


	@Override
	public int unblandMemberSecurityMobile(final MemberSecurityMobileDO memberSecurityMobile) throws ManagerException {
		try {
			final int status = 2;
			final MemberSecurityDO security = memberSecurityDAO.getSecurityByMid(memberSecurityMobile.getMemberId());
			return executeInTransaction(new TransactionExecutor<Integer>(){
				@Override
				public Integer execute() throws DaoException {
					if(security == null){
						log.warn("searche MemberSecurityDO error memberID ["+memberSecurityMobile.getMemberId()+"]");
						return 0;
					}
					security.delSecurityMobileMask();
					int k = memberSecurityDAO.updateSecurityMask(security);
					if(k == 0){
						log.warn("update memberSecurity error memberID ["+memberSecurityMobile.getMemberId()+"]");
						return 0;
					}
					MemberSecurityMobileHisDO mobileHis = MemberSecurityMobileHisDO.createInstance(memberSecurityMobile, status);
					memberSecurityHisDAO.addSecurityMobileHis(mobileHis);
					return memberSecurityMobileDAO.deleteMemberSecurityMobile(memberSecurityMobile);
				}
			}, "unblandaddMemberSecurityMobile");
		} catch (Exception e) {
			log.warn("unblandMemberSecurityMobile error " + memberSecurityMobile.toString(), e);
			return 0;
		}
	}


	@Override
	public MemberSecurityEmailDO addMemberSecurityEmail(final MemberSecurityEmailDO memberSecurityEmail) throws ManagerException {
		try {
			final MemberSecurityDO memberSecurity = getMemberSecurity(memberSecurityEmail.getMemberId());
			final MemberSecurityEmailHisDO securityEmailHis = MemberSecurityEmailHisDO.createInstance(memberSecurityEmail, MemberSecurityEmailHisDO.STATUS_BOUND);
			return executeInTransaction(new TransactionExecutor<MemberSecurityEmailDO>() {
				@Override
				public MemberSecurityEmailDO execute() throws DaoException {
					memberSecurity.addSecurityEmailMask();
					int update = memberSecurityDAO.updateSecurityMask(memberSecurity);
					if (update < 1) {
						if (log.isDebugEnabled()) {
							log.debug("addMemberSecurityEmail, update security mask count: [" + update + "], member id: [" + memberSecurityEmail.getMemberId() + "]");
						}
						return null;
					}
					MemberSecurityEmailHisDO his = memberSecurityHisDAO.addSecurityEmailHis(securityEmailHis);
					if (log.isDebugEnabled()) {
						log.debug("addMemberSecurityEmail, add email his, member id: [" + memberSecurityEmail.getMessageId() + "], id: [" + his.getId() + "]");
					}
					return memberSecurityEmailDAO.addMemberSecurityEmail(memberSecurityEmail);
				}
			}, "addMemberSecurityEmail", memberSecurityEmail);
		} catch (ManagerException e) {
			String message = "addMemberSecurityEmail execute failed:";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberSecurityDO getMemberSecurity(long memberId) throws ManagerException {
		try {
			MemberSecurityDO memberSecurity = memberSecurityDAO.getSecurityByMid(memberId);
			if (memberSecurity != null) {
				return memberSecurity;
			}
			log.warn("getMemberSecurity, can not find MemberSecurity, member id: [" + memberId + "], need add it");
			final MemberDO member = memberManager.findMember(memberId);
			if (member == null) {
				throw new ManagerException("getMemberSecurity, can not find member id: [" + memberId + "] in Member data");
			}
			final MemberSecurityDO security = new MemberSecurityDO();
			security.setMemberId(memberId);
			security.setLoginName(member.getNickname());
			return executeInTransaction(new TransactionExecutor<MemberSecurityDO>() {
				@Override
				public MemberSecurityDO execute() throws DaoException {
					return memberSecurityDAO.addMemberSecurity(security);
				}
			}, "getMemberSecurity", memberId);
		} catch (DaoException e) {
			String message = "getMemberSecurity execute failed, member id: [" + memberId + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberSecurityEmailDO findMemberSecurityEmailDOByEmail(String email)
			throws ManagerException {
		if (EmptyUtil.isBlank(email)) {
			return null;
		}
		try {
			return memberSecurityEmailDAO.getMemberSecurityEmailByEmail(email);
		} catch (DaoException e) {
			String message = "findMemberSecurityEmailDOByEmail execute failed, email: " + email;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberSecurityMobileDO findMemberSecurityMobileByMobile(String mobile) throws ManagerException {
		if (!PinjuConstantUtil.isMobile(mobile)) {
			log.warn("findMemberSecurityMobileByMobile, parameter [" + mobile + "] is not mobile");
			return null;
		}
		try {
			return memberSecurityMobileDAO.getSecurityMobileByMobile(mobile);
		} catch (DaoException e) {
			String message = "findMemberSecurityMobileByMobile execute failed, mobile: " + mobile;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberSecurityEmailLinkDO generateEmailLink(long memberId, String email, String ip, LinkType linkType) throws ManagerException {
		MemberDO member = null;
		try {
			member = memberDAO.findMemberByMid(memberId);
		} catch (Exception e) {
			throw new ManagerException("generateEmailLink error, memberId: " + memberId + ", email: " + email + ", linkType: " + linkType, e);
		}
		if (member == null) {
			throw new ManagerException("generateEmailLink member id [" + memberId + "] not exists");
		}
		EmailLink link = EmailLink.newInstance(memberId);
		final MemberSecurityEmailLinkDO ek = createSecurityEmailLink(link, email, member, ip, linkType);
		return executeInTransaction(new TransactionExecutor<MemberSecurityEmailLinkDO>() {
			public MemberSecurityEmailLinkDO execute() throws DaoException {
				MemberSecurityEmailLinkDO saved = memberSecurityEmailLinkDAO.addMemberSecurityEmailLink(ek);
				if (log.isDebugEnabled()) {
					log.debug("generate email link info: " + saved);
				}
				return saved;
			}
		}, "generateEmailLink");
	}

	@Override
	public MemberSecurityEmailLinkDO confirmEmailLink(SecurityLinkDO link) throws ManagerException {
		EmailLink ek = EmailLink.parse(link, memberManager);
		if (ek == null) {
			log.warn("confirmEmailLink, link[" + link + "] parameter is error");
			return null;
		}
		try {
			MemberDO member = memberDAO.findMemberByMid(ek.getMemberId());
			if (member == null) {
				log.warn("confirmEmailLink, member is null by member id: [" + ek.getMemberId() + "]");
				return null;
			}

			MemberSecurityEmailLinkDO sk = ek.getEmailLinkQueryDO();
			final MemberSecurityEmailLinkDO sek = memberSecurityEmailLinkDAO.getUnconfirmEmailLink(sk);
			if (sek == null) {
				log.warn("confirmEmailLink, email link was not found, link param: " + link.getParam() + ", query parameter: " + sk);
				return null;
			}
			sek.setToken(MemberSecurity.generateMessageToken());
			sek.setConfirmStatus(MemberSecurityEmailLinkDO.CONFIRM_STATUS_YES);
			sek.setConfirmIp(link.getIp());
			return executeInTransaction(new TransactionExecutor<MemberSecurityEmailLinkDO>() {
				@Override
				public MemberSecurityEmailLinkDO execute() throws DaoException {
					int updateCount = memberSecurityEmailLinkDAO.confirmMemberSecurityEmailLink(sek);
					if (updateCount < 1) {
						log.warn("confirmEmailLink, update confirm email link count is [" + updateCount + "]");
						return null;
					}
					return sek;
				}
			}, "confirmEmailLink");
		} catch (DaoException e) {
			throw new ManagerException("confirmEmailLink, execute query failed, method parameter link [" + link + "]", e);
		}
	}

	@Override
	public Result confirmToken(DefaultSecurityParam param) throws ManagerException {
		if (param == null) {
			log.warn("confirmToken, param is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		if (!MemberSecurity.isMessageId(param.getMessageId()) || !MemberSecurity.isMessageId(param.getToken()) || StringUtils.isBlank(param.getSecurityType())) {
			log.warn("confirmToken, parameters is null or empty, " + param);
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}

		try {
			if (param.isEmail()) {
				MemberSecurityEmailLinkDO ek = memberSecurityEmailLinkDAO.getSecurityEmailLinkByToken(param.getMessageId(), param.getToken());
				if (ek == null) {
					log.warn("confirmToken, security type is email, parameter: " + param);
					return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_LINK_NOT_FOUND);
				}
				// change token status to used
				int updateCount = memberSecurityEmailLinkDAO.confirmToken(ek);
				if (log.isDebugEnabled()) {
					log.debug("confirmToken, confirm token update count: [" + updateCount + "], email link: " + ek);
				}
				if (updateCount < 1) {
					log.warn("confirmToken, confirm token update count is incorrect, [" + updateCount + "], email link: " + ek);
					return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_LINK_NOT_FOUND);
				}
				return ResultSupportExt.createSuccess(ek.getMemberId());
			}

			if (param.isMobile()) {
				MemberSecurityMobileCodeDO ek = memberSecurityMobileCodeDAO.getSecurityMobileByToken(param.getMessageId(), param.getToken());
				if (ek == null) {
					log.warn("confirmToken, security type is mobile, parameter: " + param);
					return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_MOBILE_NOT_FOUND);
				}
				// change token status to used
				int updateCount = memberSecurityMobileCodeDAO.confirmToken(ek);
				if (log.isDebugEnabled()) {
					log.debug("confirmToken, confirm token update count: [" + updateCount + "], mobile code: " + ek);
				}
				if (updateCount < 1) {
					log.warn("confirmToken, confirm token update count is incorrect, [" + updateCount + "], mobile code: " + ek);
					return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_MOBILE_NOT_FOUND);
				}
				MemberSecurityMobileDO securityMobile = memberSecurityMobileDAO.getSecurityMobileByMobile(ek.getMobile());
				if (securityMobile == null) {
					log.warn("confirmToken, can not find MemberSecurityMobileDO info according to mobile [" + ek.getMobile() + "], mobile code: " + ek);
					return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_MOBILE_NOT_FOUND);
				}

				return ResultSupportExt.createSuccess(securityMobile.getMemberId());
			}
			throw new ManagerException("confirmToken, securityType is invalid, " + param);
		} catch (DaoException e) {
			throw new ManagerException("confirmToken, cause DaoException, param: " + param, e);
		}
	}

	@Override
	public void sendRetrievePasswordEmail(MemberSecurityEmailDO email, MemberSecurityEmailLinkDO link)
			throws ManagerException {
		SenderModel sender = new SenderModel();
		try {
			sender.setMessageId(link.getMessageId());
			sender.setFromAddress(PinjuConstant.EMAIL_SYSTEM_ADDRESS);
			sender.setFromName(PinjuConstant.EMAIL_SYSTEM_FROM_NAME);
			sender.setSendType(MessageConstants.CHANNEL_SENDTYPE_MAIL);
			sender.addDest(email.getEmailAddr(), email.getEmailAddr());
			sender.setTitleTemplateName(EMAIL_TEMPLATE_RETRIEVE_PASSWORD_SUBJECT);
			sender.setContentTemplateName(EMAIL_TEMPLATE_RETRIEVE_PASSWORD_CONTENT);
			sender.addContentParameters("loginName", email.getLoginName());
			sender.addContentParameters("securityEmail", email.getEmailAddr());
			sender.addContentParameters("param", link.getLinkParam());
			sender.setChannel(MessageConstants.CHANNEL_MAIL_RETRIEVE_PASSWORD);
			sender.setSendTime(link.getSendTime());
			if (log.isInfoEnabled()) {
				log.info("sending retrieve password email, message id: [" + sender.getMessageId() + "]");
			}
			messageJmsManager.sendEmailSend(sender);
			if (log.isInfoEnabled()) {
				log.info("send retrieve password email finished, message id: [" + sender.getMessageId());
			}
		} catch (Exception e) {
			throw new ManagerException( builder("retrieve password email send failed", sender), e);
		}
	}

	public MemberSecurityEmailDO unboundEmail(final long memberId) throws ManagerException {
		try {
			final MemberSecurityEmailDO memberSecurityEmail = memberSecurityEmailDAO.findMemberSecurityEmailByMemberId(memberId);
			final MemberSecurityDO memberSecurity = getMemberSecurity(memberId);
			if (memberSecurityEmail == null || memberSecurity == null) {
				return null;
			}
			final MemberSecurityEmailHisDO securityEmailHis = MemberSecurityEmailHisDO.createInstance(memberSecurityEmail, MemberSecurityEmailHisDO.STATUS_UNBOUND);
			return executeInTransaction(new TransactionExecutor<MemberSecurityEmailDO>() {
				@Override
				public MemberSecurityEmailDO execute() throws DaoException {
					memberSecurity.delSecurityEmailMask();
					int update = memberSecurityDAO.updateSecurityMask(memberSecurity);
					if (update < 1) {
						if (log.isDebugEnabled()) {
							log.debug("addMemberSecurityEmail, update security mask count: [" + update + "], member id: [" + memberSecurityEmail.getMemberId() + "]");
						}
						return null;
					}
					MemberSecurityEmailHisDO his = memberSecurityHisDAO.addSecurityEmailHis(securityEmailHis);
					if (log.isDebugEnabled()) {
						log.debug("addMemberSecurityEmail, add email his, member id: [" + memberSecurityEmail.getMessageId() + "], id: [" + his.getId() + "]");
					}
					memberSecurityEmailDAO.deleteMemberSecurityEmail(memberId);
					return memberSecurityEmail;
				}
			}, "addMemberSecurityEmail", memberSecurityEmail);
		} catch (Exception e) {
			String message = "addMemberSecurityEmail execute failed:";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	private String builder(String prefix, SenderModel sender) {
		StringBuilder builder = (prefix == null ? new StringBuilder() : new StringBuilder(prefix));
		builder.append("\nsender information: \n");
		builder.append("  from.address   : [").append(sender.getFromAddress()).append("]\n");
		builder.append("  from.name      : [").append(sender.getFromName()).append("]\n");
		builder.append("  send.type      : [").append(sender.getSendType()).append("]\n");
		builder.append("  subj.templ.name: [").append(sender.getTitleTemplateName()).append("]\n");
        builder.append("  cont.templ.name: [").append(sender.getContentTemplateName()).append("]\n");
        builder.append("  message.id     : [").append(sender.getMessageId()).append("]\n");
        builder.append("  send.time      : [").append(sender.getSendTime()).append("]\n");
		builder.append("  dest           : [");
		int i = 0;
		for (String[] dest : sender.getDest()) {
			if (i++ > 0) {
				builder.append(", ");
			}
			builder.append(dest[0]).append(":").append(dest[1]);
		}
		builder.append("]\n");
		builder.append("  parameters     : [");
		i = 0;
		for (Map.Entry<String, String> entry : sender.getContentParameters().entrySet()) {
			if (i++ > 0) {
				builder.append(", ");
			}
			builder.append(entry.getKey()).append(":").append(entry.getValue());
		}
		builder.append("]");
		return builder.toString();
	}

	private MemberSecurityEmailLinkDO createSecurityEmailLink(EmailLink link, String email, MemberDO member, String ip, LinkType linkType) {
		MemberSecurityEmailLinkDO ek = new MemberSecurityEmailLinkDO();
		ek.setMemberId(member.getMemberId());
		ek.setLoginName(member.getNickname());
		ek.setEmailAddr(email);
		ek.setLinkParam(link.toLink());
		ek.setLinkType(linkType.getType());
		ek.setSendTime(new Date());
		ek.setExpireTime(new Date(ek.getSendTime().getTime() + EXPIRES));
		ek.setMessageId(link.getMessageId());
		ek.setLinkIp(ip);
		return ek;
	}

	/**
	 * <p>Email 链接参数对象</p>
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 15:44:41
	 */
	private static class EmailLink {

		private long memberId;
		private String messageId;
		private long expireTime;

		private final static AtomicInteger seq = new AtomicInteger(0);

		private EmailLink(long memberId) {
			this.memberId = memberId;;
			this.messageId = MemberSecurity.generateMessageId();
			this.expireTime = (System.currentTimeMillis() + EXPIRES) / 1000;
		}

		private EmailLink(long memberId, String messageId, long expireTime) {
			this.memberId = memberId;
			this.messageId = messageId;
			this.expireTime = expireTime / 1000;
		}

		public static EmailLink newInstance(long memberId) {
			return new EmailLink(memberId);
		}

		public long getMemberId() {
			return memberId;
		}

		public String getMessageId() {
			return messageId;
		}

		@SuppressWarnings("unused")
		public long getTime() {
			return expireTime;
		}

		public String toLink() {
			String encodeMid = MemberSecurity.encodeMemberId(memberId);
			String encodeTime = MemberSecurity.toBase61(((seq.incrementAndGet() & Integer.MAX_VALUE) % 310) * 10000000000L + expireTime, 7);
			String hash = MemberSecurity.hash(EmailLink.class.getName(), encodeMid, messageId, encodeTime);
			return encodeMid + "-" + messageId + "-" + encodeTime + "-" + hash;
		}

		public MemberSecurityEmailLinkDO getEmailLinkQueryDO() {
			MemberSecurityEmailLinkDO query = new MemberSecurityEmailLinkDO();
			query.setMemberId(memberId);
			query.setExpireTime(new Date());
			query.setMessageId(messageId);
			return query;
		}

		public static EmailLink parse(SecurityLinkDO securityLink, MemberManager memberManager) {
			if (securityLink == null) {
				log.warn("EmailLink.parse, securityLink is null");
				return null;
			}
			String link = securityLink.getParam();
			if (EmptyUtil.isBlank(link)) {
				log.warn("EmailLink.parse, link is empty");
				return null;
			}
			if (link.indexOf('-') < 0) {
				log.warn("EmailLink.parse, link [" + link + "] can not find \'-\' character");
				return null;
			}
			String[] sp = link.split("-");
			if (sp.length != 4) {
				log.warn("EmailLink.parse, link [" + link + "] node length is not 4");
				return null;
			}
			String hash = MemberSecurity.hash(EmailLink.class.getName(), sp[0], sp[1], sp[2]);
			if (!hash.equals(sp[3])) {
				log.warn("EmailLink.parse, link [" + link + "] hash is incorrect, calculate hash [" + hash + "], link hash [" + sp[3] + "]");
				return null;
			}
			long mid = MemberSecurity.decode(sp[0]);
			if (!memberManager.isCorrectMemberId(mid)) {
				log.warn("EmailLink.parse, link [" + link + "] memberId [" + mid +"]is incorrect");
				return null;
			}
			long expireTime = (MemberSecurity.base61ToLong(sp[2]) % 10000000000L) * 1000L;
			if (expireTime < 0) {
				log.warn("EmailLink.parse, link [" + link + "] time is incorrect");
				return null;
			}
			if (expireTime < System.currentTimeMillis()) {
				log.warn("EmailLink.parse, link [" + link + "] time has benn expired, expire time: [" + String.format("%tF %<tT", expireTime) + "]");
				return null;
			}
			return new EmailLink(mid, sp[1], expireTime);
		}

		@Override
		public String toString() {
			return String.format("EmailLink [memberId=%d, messageId=%s, expireTime=%tF %<tT", memberId, messageId, expireTime * 1000);
		}
	}

	public static void main(String... args) {
		EmailLink link = new EmailLink(100000445009000L);
		System.out.println(link.toLink());
	}

	@Override
	public byte[] digestMessage(String data, String key) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			return digest.digest(CharsetUtil.toBytes(key == null ? data : data + key));
		} catch (NoSuchAlgorithmException e) {
			log.error("ignore exception", e);
			return null;
		}
	}

	@Override
	public String digestMessageHex(String data, String key) {
		byte[] bys = digestMessage(data, key);
		if (bys == null) {
			return null;
		}
		return new String(Hex.encodeHex(bys));
	}

	@Override
	public byte[] macMessage(String data) {
		if (data == null) {
			return null;
		}
		try {
			Mac mac = createMac();
			return mac.doFinal(CharsetUtil.toBytes(data));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String macMessageBase64(String data) {
		byte[] bys = macMessage(data);
		if (bys == null) {
			return null;
		}
		return Base64.encode( bys );
	}

	@Override
	public String decryptMessage(String base64) {
		if (base64 == null) {
			return null;
		}
		try {
			byte[] bys = Base64.decode(base64);
			Cipher cipher = createCipher(Cipher.DECRYPT_MODE);
			return CharsetUtil.toString(cipher.doFinal(bys));
		} catch (Exception e) {
			return null;
		}
	}

	private static Mac createMac() throws Exception {
		Mac mac = Mac.getInstance(PinjuConstant.AUTH_SIGN_MAC_ALGORITHM);
		mac.init(PinjuConstant.AUTH_SIGN_MAC_KEY);
		return mac;
	}

	private static Cipher createCipher(int mode) throws Exception {
		Cipher cipher = Cipher.getInstance(PinjuConstant.AUTH_ENCRYPT_ALGORITHM + "/" + PinjuConstant.AUTH_ENCRYPT_MODE + "/" + PinjuConstant.AUTH_ENCRYPT_PADDING);
		cipher.init(mode, PinjuConstant.AUTH_ENCRYPT_KEY);
		return cipher;
	}

	@Override
	public byte[] encryptMessage(String data) {
		if (data == null) {
			return null;
		}
		try {
			Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);
			return cipher.doFinal(CharsetUtil.toBytes(data));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String encryptMessageBase64(String data) {
		byte[] bys = encryptMessage(data);
		if (bys == null) {
			return null;
		}
		return Base64.encode( bys );
	}

}