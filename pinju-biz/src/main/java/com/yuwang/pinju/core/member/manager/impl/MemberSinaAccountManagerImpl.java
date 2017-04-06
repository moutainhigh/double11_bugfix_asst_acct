package com.yuwang.pinju.core.member.manager.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.member.dao.MemberDao;
import com.yuwang.pinju.core.member.dao.MemberSinaAccountDAO;
import com.yuwang.pinju.core.member.manager.MemberSinaAccountManager;
import com.yuwang.pinju.core.member.manager.sequence.MemberIdGenerator;
import com.yuwang.pinju.core.member.manager.ticket.MemberOrigin;
import com.yuwang.pinju.core.weibo.json.JSONException;
import com.yuwang.pinju.core.weibo.json.JSONObject;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberSinaRegisterDO;
import com.yuwang.pinju.domain.member.login.MemberSinaAccountDO;

public class MemberSinaAccountManagerImpl extends TransactionMemberManager implements MemberSinaAccountManager {

	private final static Log log = LogFactory.getLog(MemberSinaAccountManagerImpl.class);

	private MemberSinaAccountDAO memberSinaAccountDAO;
	private MemberDao memberDAO;
	private MemberIdGenerator memberIdGenerator;

	public static final String[] removeKeys = new String[] { "verified_type",
			"verified_reason", "statuses_count", "lang", "following",
			"favourites_count", "online_status", "bi_followers_count",
			"geo_enabled", "allow_all_comment", "allow_all_act_msg", "idstr",
			"verified", "created_at", "friends_count", "follow_me" };

	public void setMemberDAO(MemberDao memberDAO) {
		this.memberDAO = memberDAO;
	}
	public void setMemberIdGenerator(MemberIdGenerator memberIdGenerator) {
		this.memberIdGenerator = memberIdGenerator;
	}

	public void setMemberSinaAccountDAO(MemberSinaAccountDAO memberSinaAccountDAO) {
		this.memberSinaAccountDAO = memberSinaAccountDAO;
	}

	@Override
	public MemberSinaAccountDO getSinaAccountBySinaUid(String sinaUid)
			throws ManagerException {
		if (StringUtil.isBlank(sinaUid)) {
			return null;
		}
		try {
			return memberSinaAccountDAO.getSinaAccountBySinaUid(sinaUid);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public MemberDO registerSinaMember(MemberSinaRegisterDO register)
			throws ManagerException {
		long memberId = memberIdGenerator.nextMemberId(MemberOrigin.SINA);
		register.setMemberId(memberId);

		final MemberDO member = register.createMember();
		final MemberSinaAccountDO memberSinaAccount = register.createMemberSinaAccount();

		return executeInTransaction(new TransactionExecutor<MemberDO>() {
			public MemberDO execute() throws DaoException {
				Date currentDate = new Date();
				member.setCreateTime(currentDate);
				member.setAgreeAgreementTime(currentDate);
				MemberDO m = memberDAO.persist(member);
				memberSinaAccountDAO.addSinaAccount(memberSinaAccount);
				if (log.isDebugEnabled()) {
					log.debug("addMemberPinjuLogin, pinju login id: , member id: " + m.getId());
				}
				return m;
			}
		}, "addMemberSinaAccount");
	}

	@Override
	public String getFilterSinaUser(String sinaUser) throws ManagerException {
		try {
			JSONObject jsonObject = new JSONObject(sinaUser);
			for (int i = 0; i < removeKeys.length; i++) {
				jsonObject.remove(removeKeys[i]);
			}
			return jsonObject.toString();
		} catch (JSONException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void addSinaMember(MemberSinaAccountDO memberSinaAccount)
			throws DaoException {
		try {
			memberSinaAccountDAO.addSinaAccount(memberSinaAccount);
		} catch (Exception e) {
			throw new DaoException("[addSinaMember] " + memberSinaAccount, e);
		}		
	}
}
