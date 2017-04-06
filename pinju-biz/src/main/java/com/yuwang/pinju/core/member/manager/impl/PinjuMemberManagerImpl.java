package com.yuwang.pinju.core.member.manager.impl;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.cache.PinjuCacheManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstantUtil;
import com.yuwang.pinju.core.member.dao.MemberDao;
import com.yuwang.pinju.core.member.dao.MemberPinjuLoginDAO;
import com.yuwang.pinju.core.member.dao.MemberSecurityDAO;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.member.manager.PinjuMemberManager;
import com.yuwang.pinju.core.member.manager.register.RegisterCallback;
import com.yuwang.pinju.core.member.manager.sequence.MemberIdGenerator;
import com.yuwang.pinju.core.member.manager.ticket.MemberOrigin;
import com.yuwang.pinju.core.transmission.manager.SecurityTransManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginDO;
import com.yuwang.pinju.domain.member.MemberPinjuLoginDO;
import com.yuwang.pinju.domain.member.MemberRegisterDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileDO;
import com.yuwang.pinju.domain.member.security.PasswordValidatorVO;
import com.yuwang.pinju.validation.validator.LoginNameValidator;

public class PinjuMemberManagerImpl extends TransactionMemberManager implements PinjuMemberManager {

	private final static Log log = LogFactory.getLog(PinjuMemberManagerImpl.class);

	private MemberDao memberDAO;
	private MemberPinjuLoginDAO memberPinjuLoginDAO;
	private MemberIdGenerator memberIdGenerator;
	private PinjuCacheManager pinjuMemcachedManager;
	private MemberSecurityManager memberSecurityManager;
	private SecurityTransManager securityTransManager;
	private MemberSecurityDAO memberSecurityDAO;

	public void setMemberDAO(MemberDao memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setMemberPinjuLoginDAO(MemberPinjuLoginDAO memberPinjuLoginDAO) {
		this.memberPinjuLoginDAO = memberPinjuLoginDAO;
	}

	public void setMemberIdGenerator(MemberIdGenerator memberIdGenerator) {
		this.memberIdGenerator = memberIdGenerator;
	}

	public void setPinjuMemcachedManager(PinjuCacheManager pinjuMemcachedManager) {
		this.pinjuMemcachedManager = pinjuMemcachedManager;
	}

	public void setMemberSecurityManager(MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	public void setSecurityTransManager(SecurityTransManager securityTransManager) {
		this.securityTransManager = securityTransManager;
	}

	public void setMemberSecurityDAO(MemberSecurityDAO memberSecurityDAO) {
		this.memberSecurityDAO = memberSecurityDAO;
	}

	@Override
	public Result registerPinjuMember(MemberRegisterDO register, final RegisterCallback callback) throws ManagerException {
		final MemberPinjuLoginDO pinjuLogin = register.createPinjuLogin();
		long memberId = memberIdGenerator.nextMemberId(MemberOrigin.valueOf(register.getOriginal()));
		pinjuLogin.setMemberId(memberId);
		pinjuLogin.setPassword(hash(pinjuLogin.getLoginName(), pinjuLogin.getPassword()));

		final MemberDO member = pinjuLogin.createMember(register.getOriginal(), register.getAccoutType());

		if (callback != null) {
			callback.setMemberProperty(member);
		}
		
		final MemberSecurityDO security = member.createMemberSecurity();

//		final MemberInfoDO memberInfo = new MemberInfoDO();
//		memberInfo.setMemberId(memberId);
//		memberInfo.setProvince("");
//		memberInfo.setCity("");
//		memberInfo.setDistrict("");
//		memberInfo.setPcdCode("000000");
//		memberInfo.setEmail(register.getEmail());

		return executeInTransaction(new TransactionExecutor<Result>() {
			public Result execute() throws DaoException {
				MemberPinjuLoginDO login = memberPinjuLoginDAO.addMemberPinjuLogin(pinjuLogin);
				member.setCreateTime(login.getGmtCreate());
				member.setAgreeAgreementTime(login.getGmtCreate());
				MemberDO m = memberDAO.persist(member);
				MemberSecurityDO ms = memberSecurityDAO.addMemberSecurity(security);
				if (log.isDebugEnabled()) {
					log.debug("addMemberPinjuLogin, pinju login id: " + login.getId() + ", member id: " + m.getId());
				}
				if (callback != null) {
					callback.doCallback();
				}
				Result result = ResultSupportExt.createSuccess();
				result.setModel(login);
				result.setModel(m);
				result.setModel(ms);
				return result;
			}
		}, "addMemberPinjuLogin");
	}

	public MemberPinjuLoginDO getMemberPinjuLoginByLoginName(String loginName) throws ManagerException {
		if (EmptyUtil.isBlank(loginName)) {
			log.warn("getMemberPinjuLoginByLoginName, parameter login name is empty");
			return null;
		}
		try {
			// 登录账号为邮箱
			if (LoginNameValidator.isEmail(loginName)) {
				if (log.isInfoEnabled()) {
					log.info("getMemberPinjuLoginByLoginName, loginName [" + loginName + "] is email account");
				}
				MemberSecurityEmailDO email = memberSecurityManager.findMemberSecurityEmailDOByEmail(loginName);
				if (email == null) {
					log.warn("getMemberPinjuLoginByLoginName, email [" + loginName + "] is not exists");
					return null;
				}
				return memberPinjuLoginDAO.getMemberPinjuLoginByMemberId(email.getMemberId());
			}

			// 登录账号为手机号码
			if (PinjuConstantUtil.isMobile(loginName)) {
				if (log.isInfoEnabled()) {
					log.info("getMemberPinjuLoginByLoginName, loginName [" + loginName + "] is mobile account");
				}
				MemberSecurityMobileDO mobile = memberSecurityManager.findMemberSecurityMobileByMobile(loginName);
				if (mobile == null) {
					log.warn("getMemberPinjuLoginByLoginName, mobile [" + loginName + "] is not exists");
					return null;
				}
				return memberPinjuLoginDAO.getMemberPinjuLoginByMemberId(mobile.getMemberId());
			}

			// 普通账号
			return memberPinjuLoginDAO.getMemberPinjuLoginByLoginName(loginName);
		} catch (Exception e) {
			String message = "getMemberPinjuLoginByLoginName execute failed, loginName: " + loginName;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	public boolean validatePassowrd(PasswordValidatorVO vp) throws ManagerException {
		if (vp == null) {
			throw new ManagerException("[validate password], parameter vp is null");
		}
		if (!vp.checkParameter()) {
			throw new ManagerException("[validate password], parameter has null value or empty value, " + vp);
		}
		String[] plains = securityTransManager.decryptTransData(vp.getTid(), vp.getPassword());
		if (plains == null || plains.length != 1 || StringUtil.hasBlank(plains)) {
			log.warn("[validate password] decrypt password error, " + vp);
			return false;
		}
		vp.setPassword(plains[0]);
		if (vp.getPassword().length() < 6 || vp.getPassword().length() > 16) {
			log.warn("[validate password], password length is incorrect, " + vp);
			return false;
		}
		try {
			MemberPinjuLoginDO login = getMemberPinjuLoginByLoginName(vp.getLoginName());
			if (login == null) {
				log.warn("[validate password], can not find Login data, " + vp);
				return false;
			}
			String hash = hash(login.getLoginName(), vp.getPassword());
			boolean result = login.getPassword().equals(hash);
			log.info("[validate password], result = [" + result + "], " + vp);
			return result;
		} catch (Exception e) {
			String message = "[validate password] cause exception, " + vp;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	public MemberPinjuLoginDO getMemberPinjuLoginByMemberId(long memberId) throws ManagerException {
		try {
			return memberPinjuLoginDAO.getMemberPinjuLoginByMemberId(memberId);
		} catch (DaoException e) {
			String message = "getMemberPinjuLoginByMemberId execute failed, memberId: " + memberId;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	public int updatePinjuLoginPassowrd(final MemberPinjuLoginDO pinjuLogin, final String password)
			throws ManagerException {
		if (log.isDebugEnabled()) {
			log.debug("updatePinjuLoginPassowrd, " + pinjuLogin + ", password: " + StringUtil.asterisk(password));
		}
		if (pinjuLogin == null || EmptyUtil.isBlank(password)) {
			log.warn("updatePinjuLoginPassowrd, parameter pinjuLogin or new password is null");
			return 0;
		}
		if (pinjuLogin.getId() == null || EmptyUtil.isBlank(pinjuLogin.getLoginName())
				|| pinjuLogin.getMemberId() == null) {
			log.warn("updatePinjuLoginPassowrd, pinju login id or login name or member id is empty or null");
			return 0;
		}
		return executeInTransaction(new TransactionExecutor<Integer>() {
			public Integer execute() throws DaoException {
				pinjuLogin.setPassword(hash(pinjuLogin.getLoginName(), password));
				return memberPinjuLoginDAO.updatePinjuLoginPassowrd(pinjuLogin);
			}
		}, "updatePinjuLoginPassowrd");
	}

	@Override
	public String hash(String loginName, String originPassword) {
		String hash = PasswordHash.base64Hash(originPassword, loginName);
		if (log.isDebugEnabled()) {
			log.debug("hash password, login name: " + loginName + ", password: " + StringUtil.asterisk(originPassword)
					+ ", hash: " + hash);
		}
		return hash;
	}

	@Override
	public int incrementLoginErrorCount(MemberLoginDO login) throws ManagerException {
		int count = getLoginErrorCount(login);
		count++;
		boolean result = pinjuMemcachedManager.setLoginErrorCount(login, count, PinjuConstant.PINJU_LOGIN_ERROR_COUNT_TIMEOUT);
		if (log.isDebugEnabled()) {
			log.debug("increment login error count, login name: " + login.getLoginName() + ", after increment count: " + count
					+ ", put cache result: " + result);
		}
		return count;
	}

	@Override
	public boolean isCheckCaptcha(MemberLoginDO login) throws ManagerException {
		if (!LoginNameValidator.isLoginName(login.getLoginName())) {
			log.warn("isCheckCaptcha, check value is not loginName, " + login);
			return false;
		}
		int count = getLoginErrorCount(login);
		if (log.isDebugEnabled()) {
			log.debug("is check captcha, current count: " + count + ", show captcha limit count: " + PinjuConstant.PINJU_LOGIN_ERROR_COUNT_CAPTCHA);
		}
		return count >= PinjuConstant.PINJU_LOGIN_ERROR_COUNT_CAPTCHA;
	}

	@Override
	public boolean clearLoginErrorCount(MemberLoginDO login) throws ManagerException {
		boolean result = pinjuMemcachedManager.clearLoginErrorCount(login);
		if (log.isDebugEnabled()) {
			log.debug("clear login error count, login name: " + login.getLoginName());
		}
		return result;
	}

	private int getLoginErrorCount(MemberLoginDO login) throws ManagerException {
		int count = pinjuMemcachedManager.getLoginErrorCount(login);
		if (log.isDebugEnabled()) {
			log.debug("get login error count, login name: " + login.getLoginName() + ", current count: " + count);
		}
		return count;
	}

	private static class PasswordHash {

		private final static String CHARSET = PinjuConstant.DEFAULT_CHARSET;

		private PasswordHash() {
		}

		public static String base64Hash(String password, String salt) {
			return base64Hash(password, salt, "SHA-256");
		}

		private static String base64Hash(String password, String salt, String algorithm) {
			byte[] bys = byteHash(password, salt, algorithm);
			return new String(Base64.encodeBase64(bys));
		}

		public static byte[] byteHash(String password, String salt, String algorithm) {
			if (password == null || algorithm == null) {
				return null;
			}
			try {
				MessageDigest md = MessageDigest.getInstance(algorithm.trim());
				if (salt != null) {
					md.update((salt.toLowerCase() + ":" + password).getBytes(CHARSET));
					byte[] bys = md.digest();
					md.reset();
					md.update(password.getBytes(CHARSET));
					md.update(bys);
				} else {
					md.update(password.getBytes(CHARSET));
				}
				return md.digest();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		String[][] names = { { "1031", "firefox" }, { "1032", "msie" }, { "1033", "opera" }, { "1034", "小熊宝宝" }, { "1035", "gongjiayun" }, { "1036", "shihongbo" }, { "1037", "liming" }, { "1038", "xqwin" }, { "1039", "xqwin11" }, { "1040", "heyong" }, { "1041", "frankiegao002" }, { "1042", "boengood" }, { "1043", "qhm1227" }, { "1044", "lingjt" }, { "1045", "lixin" }, { "1046", "colley" }, { "1047", "liuweiguo" }, { "1048", "summer" }, { "1049", "ducheng" }, { "1050", "firefox3" }, { "1051", "firefox4" }, { "1052", "grass" }, { "1053", "firefox2" }, { "1054", "firefox1" }, { "1055", "shixing" }, { "1056", "kscn_com" }, { "1057", "firefox8" }, { "1058", "firefox9" }, { "1059", "xiazhenyu" }, { "1060", "lintao" }, { "1061", "caijunjie" }, { "1062", "caiwei" }, { "1063", "一二三四五六七八九十" }, { "1064", "firefox011" }, { "1065", "firefox012" }, { "1066", "firefox013" }, { "1067", "火狐" }, { "1068", "密码" }, { "1069", "yejingfeng" }, { "1070", "小狐狸" }, { "1073", "frankiegao123" }, { "1074", "火狐狐狸" }, { "1075", "火狐TEST" }, { "1076", "firefox003" }, { "1077", "firefox004" }, { "1078", "firefox005" }, { "1083", "hbshi" }, { "1084", "firefox009" }, { "1085", "firefox010" }, { "1086", "firefox014" }, { "1087", "xqwin22" }, { "1088", "colley1" }, { "1089", "uioioerio" }, { "1090", "qiuhongming" }, { "1091", "火狐fox" }, { "1092", "火狐2" }, { "1093", "xqwin33" }, { "1094", "中ab" }, { "1095", "中abc" }, { "1096", "zhaozhao" }, { "1097", "colleyMa" }, { "1098", "ling" }, { "1099", "firefox125" }, { "1100", "firefox126" }, { "1101", "firefox127" }, { "1102", "firefox128" }, { "1103", "firefox129" }, { "1104", "firefox130" }, { "1105", "firefox131" }, { "1106", "firefox132" }, { "1107", "firefox133" }, { "1108", "firefox134" }, { "1109", "firefox135" }, { "1110", "firefox136" }, { "1111", "firefox137" }, { "1112", "firefox138" }, { "1113", "firefox139" }, { "1114", "xqwin44" }, { "1115", "andy" } };
		for (int i = 0; i < names.length; i++) {
			String password = PasswordHash.base64Hash("123456", names[i][1], "SHA-256");
			System.out.printf("UPDATE MEMBER_PINJU_LOGIN SET PASSWORD = '%s', GMT_MODIFIED = now() WHERE ID = %s AND LOGIN_NAME = '%s';%n",
					password, names[i][0], names[i][1]);
		}
	}
}