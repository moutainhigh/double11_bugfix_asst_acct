package com.yuwang.pinju.core.member.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.login.MemberLoginPageImgDO;

/**
 * <p>登录页面图片相关数据操作</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 11:32:58
 */
public interface MemberLoginPageImgDAO {

	/**
	 * <p>获取有效的登录页面图片信息</p>
	 *
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 11:33:12
	 */
	List<MemberLoginPageImgDO> getValidPageImgs() throws DaoException;
	
	/**
	 * <p>从读库中获取有效的登录页面图片信息</p>
	 *
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 11:33:12
	 */
	List<MemberLoginPageImgDO> getValidPageImgsFromRead() throws DaoException;
}
