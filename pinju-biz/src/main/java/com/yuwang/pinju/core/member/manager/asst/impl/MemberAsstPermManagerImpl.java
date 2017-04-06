package com.yuwang.pinju.core.member.manager.asst.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.member.dao.MemberAsstDAO;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstPermManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionJsonVO;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionTreeVO;

public class MemberAsstPermManagerImpl implements MemberAsstPermManager {

	private final static Log log = LogFactory.getLog(MemberAsstPermManagerImpl.class);

	/**
	 * 权限数据。key 为 target:action；value 为 MemberAsstPermissionDO 对象
	 */
	private Map<String, MemberAsstPermissionDO> permissions;

	/**
	 * JSON 格式的权限数据，用于 zTree 显示
	 */
	private String jsonPermissions;
	
	private List<MemberAsstPermissionTreeVO> treeVoList;

	private MemberAsstDAO memberAsstDAO;

	public MemberAsstPermManagerImpl() {
	}

	public void setMemberAsstDAO(MemberAsstDAO memberAsstDAO) {
		this.memberAsstDAO = memberAsstDAO;
	}

	public void init() {
		if (permissions != null) {
			return;
		}
		try {
			List<MemberAsstPermissionDO> perms = memberAsstDAO.getValidAsstPermissions();
			if (EmptyUtil.isBlank(perms)) {
				throw new IllegalStateException("initialize assistant account permissions, but permission data was empty, please check MySQL table MEMBER_ASST_PERMISSION");
			}
			permissions = new HashMap<String, MemberAsstPermissionDO>();
			for (MemberAsstPermissionDO perm : perms) {
				String key = wrapKey( perm.getTarget(), perm.getAction() );
				permissions.put(key, perm);
				log.warn("[init] initialize assistant account permissions, " + perm);
			}
			jsonPermissions = initJsonPermisson( perms );
		} catch (Exception e) {
			permissions = null;
			jsonPermissions = null;
			log.error("[init] initialize assistant account permissions failed", e);
		}
	}

	@Override
	public MemberAsstPermissionDO getAsstPermission(String target, String action) {
		if (StringUtils.isBlank(target) || StringUtils.isBlank(action)) {
			log.warn("[getAsstPermission] parameter error, target: [" + target + "], action: [" + action + "]");
			return null;
		}
		init();
		if (permissions == null) {
			log.warn("[getAsstPermission] assistant account permissions can not correct initialization, target: [" + target + "], action: [" + action + "]");
			return null;
		}
		return permissions.get( wrapKey(target, action) );
	}

	@Override
	public String getJsonPermission() {
		init();
		if (permissions == null) {
			log.warn("[getJsonPermission] assistant account permissions can not correct initialization");
			return null;
		}
		return jsonPermissions;
	}

	/**
	 * <p>初始化 JSON 格式的权限数据</p>
	 *
	 * @param memberAsstPermissionList
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-26 09:33:16
	 */
	private String initJsonPermisson(Collection<MemberAsstPermissionDO> memberAsstPermissionList) {
	    List<MemberAsstPermissionJsonVO> permissionJsonList = new ArrayList<MemberAsstPermissionJsonVO>();
	    treeVoList = new ArrayList<MemberAsstPermissionTreeVO>();
	    String target = null;
	    for (MemberAsstPermissionDO memberAsstPermission : memberAsstPermissionList) {
	        if (memberAsstPermission.getTarget() != null && !memberAsstPermission.getTarget().equals(target)) {
	            MemberAsstPermissionJsonVO jsonVo = memberAsstPermission.createMemberAsstPermissionTypeJsonVO();
	            MemberAsstPermissionTreeVO treeVo = memberAsstPermission.createMemberAsstPermissionTreeVO(true);
	            permissionJsonList.add(jsonVo);
	            treeVoList.add(treeVo);
	            target = memberAsstPermission.getTarget();
	        }
	        MemberAsstPermissionJsonVO jsonVo = memberAsstPermission.createMemberAsstPermissionJsonVO();
	        MemberAsstPermissionTreeVO treeVo = memberAsstPermission.createMemberAsstPermissionTreeVO(false);
	        treeVoList.add(treeVo);
	        permissionJsonList.add(jsonVo);
	    }
	    JSONArray jsonArray = new JSONArray();
	    for (MemberAsstPermissionJsonVO jsonVo : permissionJsonList) {
	        jsonArray.add(jsonVo);
	    }
	    String json = jsonArray.toString();
	    log.warn("init json permissions finished, " + json);
	    return json;
	}
	
	private String wrapKey(String target, String action) {
		return target + ':' + action;
	}

	@Override
	public List<MemberAsstPermissionTreeVO> getMemberAsstPermission() {
		return treeVoList;
	}
}
