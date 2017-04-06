package com.yuwang.pinju.core.member.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.member.dao.MemberLoginPageImgDAO;
import com.yuwang.pinju.domain.member.login.MemberLoginPageImgDO;

public class MemberLoginPageImgDAOImpl extends BaseDAO implements MemberLoginPageImgDAO {

	/**
	 * MEMBER_LOGIN_PAGE_IMG 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE = "member_login_page_img.";
	
	private ReadBaseDAO readBaseDAOForMySql;

	@Override
	@SuppressWarnings("unchecked")
	public List<MemberLoginPageImgDO> getValidPageImgs() throws DaoException {
		return (List<MemberLoginPageImgDO>)executeQueryForList(NAMESPACE + "getValidPageImgs", null);
	}

	@Override
	@SuppressWarnings("unchecked")	
	public List<MemberLoginPageImgDO> getValidPageImgsFromRead() throws DaoException {
		return (List<MemberLoginPageImgDO>)readBaseDAOForMySql.executeQueryForList(NAMESPACE + "getValidPageImgs", null);
	}
}
