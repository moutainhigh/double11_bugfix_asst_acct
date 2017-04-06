package com.yuwang.pinju.core.member.manager.asst;

import java.util.List;

import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionTreeVO;

/**
 * <p>子账号权限数据管理器</p>
 *
 * @author gaobaowen
 * @since 2011-12-23 10:18:54
 */
public interface MemberAsstPermManager {

	/**
	 * <p>根据权限目标和权限行为查询子账号权限信息</p>
	 *
	 * @param target  权限目标
	 * @param action  权限行为
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-23 10:19:08
	 */
	MemberAsstPermissionDO getAsstPermission(String target, String action);

	/**
	 * <p>获取 JSON 格式的权限数据</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-26 09:35:17
	 */
	String getJsonPermission();
	
	/**
	 * <p>获取 JSON 格式的权限数据(action、target、actionDesc、targetDesc)</p> 
	 * 
	 * @return
	 */
	List<MemberAsstPermissionTreeVO> getMemberAsstPermission();
}
