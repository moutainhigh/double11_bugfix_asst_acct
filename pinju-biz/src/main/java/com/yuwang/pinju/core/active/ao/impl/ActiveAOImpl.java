package com.yuwang.pinju.core.active.ao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.active.ao.ActiveAO;
import com.yuwang.pinju.core.active.manager.ActiveManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.active.ActiveDescDO;
import com.yuwang.pinju.domain.active.ActiveInfoDO;
import com.yuwang.pinju.domain.active.ActiveInfoQuery;
import com.yuwang.pinju.domain.active.ActiveRegtDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;

public class ActiveAOImpl extends BaseAO implements ActiveAO {
	private static final Log log = LogFactory.getLog(ActiveAOImpl.class);

	private ActiveManager activeManager;

	private MemberManager memberManager;
	
	private FileStorageManager fileStorageManager;

	@Override
	public Result queryActiveList(ActiveInfoQuery query) {
		Result result = new ResultSupport();
		result.setSuccess(true);
		List<ActiveInfoDO> activeList = new ArrayList<ActiveInfoDO>();
		try {

			List<ActiveInfoDO> activeInfoList = activeManager
					.queryActiveList(query);

			// 验证 活动参与总数量未到上限、卖家报名数量未到上限的活动
			List<Boolean> flags = new ArrayList<Boolean>();
			if (activeInfoList != null && activeInfoList.size() > 0) {
				for (ActiveInfoDO info : activeInfoList) {
					query.setActivityId(info.getId());
					int res = activeManager.queryCanRegistActiveNum(query);
					if (res < 1) {
						flags.add(Boolean.FALSE);
					} else {
						flags.add(Boolean.TRUE);
						activeList.add(info);
					}
				}
			}
			result.setModel("flags", flags);
		} catch (ManagerException e) {
			log.error("ActiveAO.queryActiveList Exception : ", e);
		}
		result.setModel("activeInfoList", activeList);
		
		return result;
	}

	@Override
	public int queryActiveCount(ActiveInfoQuery query) {
		int totalPage = 0;
		try {
			totalPage = activeManager.queryActiveCount(query);
		} catch (ManagerException e) {
			log.error("ActiveAO.queryActivePageCount() Exception : ", e);
		}
		return totalPage;
	}

	@Override
	public Result queryActiveRegtList(ActiveInfoQuery query) {
		Result result = new ResultSupport();
		result.setSuccess(true);
		List<ActiveRegtDO> activeRegtList = null;
		try {

			activeRegtList = activeManager.queryActiveRegtList(query);

			if (activeRegtList == null || activeRegtList.size() == 0) {
				log.debug("卖家未报名此次活动！");
				result.setModel("isRegist", Boolean.FALSE);
			} else {
				log.debug("卖家已报名此次活动，报名次数：" + activeRegtList.size());
				result.setModel("isRegist", Boolean.TRUE);
				// 区分报名类型：商品、店铺
				int registed = 0;
				int passed = 0;
				for (ActiveRegtDO regist : activeRegtList) {
					registed ++;
					if (regist.getCheckStatus().intValue() == 1) {
						passed ++;
					}
				}
				log.debug("此次活动该卖家已报名商品数：" + registed + "，已通过商品数：" + passed);
				result.setModel("registed", registed);
				result.setModel("passed", passed);
				ActiveInfoDO activeInfo = queryActiveInfo(query.getActivityId());
				Date registEndDate = activeInfo.getRegistEndTime();
				Date now = new Date();
				if (now.compareTo(registEndDate) > 0) {
					result.setModel("canRegist", 0);
				} else {
					int canRegist = activeManager.queryCanRegistActiveNum(query);
					log.debug("此次活动卖家还可参加商品数：" + canRegist);
					result.setModel("canRegist", canRegist);
				}
			}
		} catch (ManagerException e) {
			log.error("ActiveAO.queryActiveRegtList Exception : ", e);
		}
		if(activeRegtList == null){
			result.setSuccess(false);
		}
		result.setModel("activeRegtList", activeRegtList);
		return result;
		
	}

	@Override
	public ActiveInfoDO queryActiveInfo(
			Long activeInfoId) {
		ActiveInfoDO active = null;
		try {
			active = activeManager.queryActiveInfoById(activeInfoId);
			if (active == null) {
				active = new ActiveInfoDO();
				active.setId(activeInfoId);
			}
		} catch (ManagerException e) {
			log.error("ActiveAO.queryActiveInfo Exception : ", e);
		}
		return active;
	}

	@Override
	public ActiveDescDO queryActiveDesc(ActiveInfoDO activeInfo) {
		ActiveDescDO activeDesc = null;
		try {
			activeDesc = activeManager.queryActiveDescByActiveInfo(activeInfo);
		} catch (ManagerException e) {
			log.error("ActiveAO.queryActiveDesc Exception : ", e);
		}
		return (activeDesc != null ? activeDesc : new ActiveDescDO());
	}

	@Override
	public Result addActiveRegistInfo(ActiveRegtDO activeRegt, File logo, String fileName,
			String logoContentType){
		Result result = new ResultSupport();
		result.setSuccess(false);
		try {
			
			if (fileName != null) {
				String[] paths = fileStorageManager.saveImage(new File[] { logo },
						new String[] { fileName }, activeRegt.getMemberId(), activeRegt.getNickName(), true);
				if (paths == null || paths.length == 0) {
					log.debug("保存活动报名图片失败。。。");
				} else {
					if(log.isDebugEnabled()){
						log.debug("活动报名图片保存成功，文件名：" + paths[0]);
					}
					if (activeRegt.getRegistType().intValue() == 2) {
						activeRegt.setShopPic(paths[0]);
					} else {
						activeRegt.setSalePic(paths[0]);
					}
				}
			}

			// 如果是店铺报名，设置店铺报名数量
			if (activeRegt.getRegistType().intValue() == 2) {
				activeRegt.setAuctionNum(1L);
			}else{
				int agio = (int) Math.round(activeRegt.getSalePrice()*100.0/activeRegt.getOriPrice());
				activeRegt.setAgio(agio * 10);
			}

			activeRegt.setGmtCreate(new Date());
			activeRegt.setGmtModified(new Date());

			result = activeManager.addActiveRegistInfo(activeRegt);
			
			// 修改活动信息，活动总报名数增加
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", activeRegt.getActivityId());
			map.put("num", new Integer(1));
			int flag = activeManager.updateActiveInfoRegistNum(map);
			log.debug("更新活动报名数量结果：" + flag);
			if(flag == 1){
				result.setSuccess(true);
			}else{
				result.setResultCode("更新活动报名数量失败");
			}
		} catch (ManagerException e) {
			log.error("ActiveAO.addActiveRegistInfo Exception : ", e);
		}
		return result;
	}

	/**
	 * 验证卖家是否符合报名条件（根据活动报名类型：商店、店铺） 1、商品验证：折扣限制 - 数据来源：数据库、卖家自定义？
	 * 2、店铺限制（卖家限制）：店铺类型、店铺等级、消保类型、店铺类目、（店铺状态） -
	 * 根据会员编号查询卖家资质信息(MEMBER_SELLER_QUALITY) -
	 * SELLER_TYPE、LEVEL（卖家级别？）、CP_TYPE、CATEGORY_ID（所属一级类目？）、（APPROVE_STATUS）
	 */
	@Override
	public Result verifyActiveInfo(ActiveInfoDO activeInfo, Long memberId) {
		// 验证卖家是否符合报名条件（根据活动报名类型：商店、店铺）
		// 1、商品验证：折扣限制
		// 2、店铺限制（卖家限制）：店铺类型、店铺等级、消保类型、店铺类目、（店铺状态）
		// 根据会员编号查询卖家资质信息(MEMBER_SELLER_QUALITY)
		Result result = new ResultSupport();
		result.setSuccess(false);
		try {
			ActiveInfoQuery query = getActiveInfoQuery(memberId);
			if(query == null){
				log.debug("卖家尚未开办店铺，无法参加此次活动：Member Id : " + memberId);
				result.setModel("errorMessage", "您尚未开办店铺，无法参加此次活动！");
				return result;
			}

			query.setActivityId(activeInfo.getId());
			query.setMemberId(memberId);
			
			int res = activeManager.queryActiveCount(query);
			if(res != 1){
				log.debug("卖家限制条件不符，无法参加此次活动：Member Id : " + memberId);
				result.setModel("errorMessage", "您的条件不符合活动限制，无法参加此次活动！");
				return result;
			}
			
			int canRegist = activeManager.queryCanRegistActiveNum(query);
			if (canRegist < 1) {
				log.debug("卖家报名数量已达上限，无法继续参加此次活动：Member Id : " + memberId);
				result.setModel("errorMessage", "您的报名数量已达上限，无法继续参加此次活动！");
				return result;
			} else {
				// 验证通过的话，直接将剩余报名量放入request
				result.setModel("canRegist", canRegist);
				result.setSuccess(true);
			}
		} catch (ManagerException e) {
			log.error("验证卖家报名条件异常：", e);
			result.setModel("errorMessage", "系统繁忙，请稍后再报名此次活动！");
		}
		return result;
	}

	@Override
	public Result queryActivePageList(ActiveInfoQuery query) {
		List<ActiveInfoDO> activeInfoList = null;
		Result result = new ResultSupport();
		result.setSuccess(true);
		try {
			
			activeInfoList = activeManager.queryActivePageList(query);
			List<Boolean> flags = new ArrayList<Boolean>();
			if (activeInfoList != null && activeInfoList.size() > 0) {
				for (ActiveInfoDO info : activeInfoList) {
					query.setActivityId(info.getId());
					Date registEndDate = info.getRegistEndTime();
					Date now = new Date();
					int res = activeManager.queryCanRegistActiveNum(query);
					if (res < 1 || now.compareTo(registEndDate) > 0) {
						flags.add(Boolean.FALSE);
					} else {
						flags.add(Boolean.TRUE);
					}
				}
			}
			result.setModel("flags", flags);
		} catch (ManagerException e) {
			log.error("ActiveAO.queryActivePageList Exception : ", e);
		}
		if(activeInfoList == null){
			result.setSuccess(false);
		}
		result.setModel("activeInfoList", activeInfoList);
		return result;
	}

	@Override
	public int queryActivePageCount(ActiveInfoQuery query) {
		int totalPage = 0;
		try {
			totalPage = activeManager.queryActivePageCount(query);
		} catch (ManagerException e) {
			log.error("ActiveAO.queryActivePageCount() Exception : ", e);
		}
		return totalPage;
	}

	@Override
	public Result cancelActiveRegist(ActiveRegtDO activeRegt) {
		Result result = null;
		try {
			result = activeManager.cancelActiveRegist(activeRegt);
			// 修改活动信息，活动总报名数增加
			if(result.isSuccess()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", activeRegt.getActivityId());
				map.put("num", new Integer(-1));
				int flag = activeManager.updateActiveInfoRegistNum(map);
				log.debug("更新活动报名数量结果：" + flag);
				if(flag != 1){
					result.setSuccess(false);
					log.debug(result.getModels().get("errorMessage"));
					result.setModel("errorMessage", "更新活动报名数量失败！");
				}
			}
		} catch (ManagerException e) {
			log.error("ActiveAO.cancelActiveRegist() Exception : ", e);
		}
		return result;
	}

	@Override
	public ActiveInfoQuery getActiveInfoQuery(Long memberId) {
		ActiveInfoQuery query = null;
		try {
			SellerQualityDO sellerQuality = memberManager
					.getSellerQualityByMemberId(memberId);
			if (sellerQuality == null) {
				log.debug("Seller quality is null, member id:" + memberId);
				return null;
			}
			query = new ActiveInfoQuery();
			// 卖家等级
			query.setShopLevel("#" + sellerQuality.getLevel() + "#");
			// 消保类型
			query.setCpType("#" + sellerQuality.getCpType() + "#");
			// 店铺类型
			query.setShopType("#" + sellerQuality.getSellerType() + "#");
			// 所属类目
			query.setCategoryId("#" + sellerQuality.getCategoryId() + "#");
		} catch (ManagerException e) {
			log.error("ActiveAO.getActiveInfoQuery Exception : ", e);
		}
		return query;
	}

	public ActiveManager getActiveManager() {
		return activeManager;
	}

	public void setActiveManager(ActiveManager activeManager) {
		this.activeManager = activeManager;
	}

	public MemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public FileStorageManager getFileStorageManager() {
		return fileStorageManager;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

}
