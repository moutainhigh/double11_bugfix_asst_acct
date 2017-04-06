package com.yuwang.pinju.core.member.manager;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberSinaRegisterDO;
import com.yuwang.pinju.domain.member.login.MemberSinaAccountDO;

public interface MemberSinaAccountManager {

	/**
	 * <p>根据新浪微博会员编号获取新浪账号信息</p>
	 *
	 * @param sinaUid
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:28:54
	 */
	MemberSinaAccountDO getSinaAccountBySinaUid(String sinaUid) throws ManagerException;
	
	/**
	 * <p>新浪微博在pinju注册会员名</p>
	 * 
	 * @return
	 * @throws ManagerException
	 */
	MemberDO registerSinaMember(MemberSinaRegisterDO register) throws ManagerException;
	
	void addSinaMember(MemberSinaAccountDO memberSinaAccount) throws DaoException;
	
	/**
	 * <p>过滤新浪用户不需要的基本信息</p>
	 * 
	 * @param sinaUser
	 * @return
	 * @throws ManagerException
	 */
	String getFilterSinaUser(String sinaUser) throws ManagerException;
}
