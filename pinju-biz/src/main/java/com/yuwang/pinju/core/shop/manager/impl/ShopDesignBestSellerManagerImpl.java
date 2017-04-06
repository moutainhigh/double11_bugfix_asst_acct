package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;
import java.util.Properties;

import com.yuwang.pinju.core.bi.manager.BiShopManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.report.manager.SellReportManager;
import com.yuwang.pinju.core.shop.manager.ShopBaseDesignerManager;
import com.yuwang.pinju.domain.bi.QueryShopSalesRankDO;
import com.yuwang.pinju.domain.bi.ShopSalesRankDO;
import com.yuwang.pinju.domain.report.SellItemsReportDO;
import com.yuwang.pinju.domain.report.SellReportQuery;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

/**
 * 商品排行榜（店铺装修）
 * @author liyouguo
 *
 * @since 2011-7-4
 */
public class ShopDesignBestSellerManagerImpl extends
		ShopBaseDesignerManagerImpl implements ShopBaseDesignerManager {
	private SellReportManager sellReportManager;
	/**
	 * BI接口管理器 
	 */
	private BiShopManager biShopManager;

	public BiShopManager getBiShopManager() {
		return biShopManager;
	}

	public void setBiShopManager(BiShopManager biShopManager) {
		this.biShopManager = biShopManager;
	}
	
	/**
	 * 获取店铺商品排行榜
	 * 包括销售排行榜【rankType=0】，收藏排行榜【rankType=1】
	 * 
	 * @param queryShopSalesRankDO
	 * @return
	 * List<ShopSalesRankDO>
	 *
	 */
	/*public List<SellItemsReportDO> getBestSeller(
			QueryShopSalesRankDO queryShopSalesRankDO) {
		try {
			SellReportQuery sellReportQuery = new SellReportQuery();
			sellReportQuery.setBegDate("4");
			sellReportQuery.setSellerId(queryShopSalesRankDO.getMemberId());
			sellReportQuery.setItemsPerPage(queryShopSalesRankDO.getNum());
			sellReportQuery.setPage(1);
			return sellReportManager.querySellItemsReportByDate(sellReportQuery);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return null;
	}*/
	
	public List<ShopSalesRankDO> getBestSeller(
			QueryShopSalesRankDO queryShopSalesRankDO) {
		try {
			return biShopManager.queryShopSalesRank(queryShopSalesRankDO);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return null;
	}
	/**
	 * 展现排行榜（获取接口数据）
	 * 
	 * @param shopUserModuleParamDO
	 * @param properties
	 *
	 */
	protected void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		// TODO Auto-generated method stub
		QueryShopSalesRankDO queryShopSalesRankDO = new QueryShopSalesRankDO();
		queryShopSalesRankDO.setMemberId(shopUserModuleParamDO.getUserId());
		queryShopSalesRankDO.setShopId(shopUserModuleParamDO.getShopId());
		try {
			queryShopSalesRankDO.setNum(new Integer(properties
					.getProperty("DISPLAYNUM")));
		} catch (Exception e) {

		}
		properties.put("SELLER_DATA", getBestSeller(queryShopSalesRankDO));// 获取销售排行榜
		//queryShopSalesRankDO.setRankType(1);
		//properties.put("COLLECT_DATA", getBestSeller(queryShopSalesRankDO));// 获取热门收藏排行榜
	}

	/**
	 * 编辑商品排行榜，获取已编辑的相关数据
	 * 
	 * @param shopUserModuleParamDO
	 * @param properties
	 *
	 */
	protected void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		// TODO Auto-generated method stub
		return;
	}

	public SellReportManager getSellReportManager() {
		return sellReportManager;
	}

	public void setSellReportManager(SellReportManager sellReportManager) {
		this.sellReportManager = sellReportManager;
	}
	
	
}
