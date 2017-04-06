package com.yuwang.pinju.core.active.ao;

import java.io.File;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.active.ActiveDescDO;
import com.yuwang.pinju.domain.active.ActiveInfoDO;
import com.yuwang.pinju.domain.active.ActiveInfoQuery;
import com.yuwang.pinju.domain.active.ActiveRegtDO;

public interface ActiveAO {
	/**
	 * 查询活动列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	Result queryActiveList(ActiveInfoQuery query);
	
	/**
	 * 查询活动信息总数
	 * @param query
	 * @return
	 * @throws Exception
	 */
	int queryActiveCount(ActiveInfoQuery query);

	/**
	 * 查询活动
	 * @param request
	 * @param activeInfo
	 * @return
	 */
	ActiveInfoDO queryActiveInfo(Long activeInfoId);

	/**
	 * 查询活动描述信息
	 * @param request
	 * @param activeInfo
	 * @return
	 * @throws Exception
	 */
	ActiveDescDO queryActiveDesc(ActiveInfoDO activeInfo);

	/**
	 * 添加活动报名信息
	 * @param request
	 * @param acticeRegt
	 * @param logo
	 * @param logoFileName
	 * @param logoContentType
	 */
	Result addActiveRegistInfo(ActiveRegtDO acticeRegt, File logo, String logoFileName,
			String logoContentType);

	/**
	 * 查询卖家已报名和已通过的活动信息
	 * @param request
	 * @param query
	 * @return
	 */
	Result queryActivePageList(ActiveInfoQuery query);

	/**
	 * 查询卖家已报名和已通过的活动信息总数
	 * @param query
	 * @return
	 * @throws Exception
	 */
	int queryActivePageCount(ActiveInfoQuery query);

	/**
	 * 查询卖家报名信息
	 * @param request
	 * @param activeInfo
	 * @return
	 */
	Result queryActiveRegtList(ActiveInfoQuery query) ;

	/**
	 * 撤销活动报名
	 * @param request
	 * @param activeRegt
	 */
	Result cancelActiveRegist(ActiveRegtDO activeRegt);

	/**
	 * 验证卖家是否可报名此活动
	 * @param activeInfo 活动信息（1-活动ID，2-活动类型）
	 * @param memberId 
	 * @return
	 */
	Result verifyActiveInfo(ActiveInfoDO activeInfo, Long memberId);

	/**
	 * 根据卖家id查询店铺资质，验证卖家限制
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	ActiveInfoQuery getActiveInfoQuery(Long memberId);
	
}
