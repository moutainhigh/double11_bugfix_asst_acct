package com.yuwang.pinju.core.bi.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.bi.dao.BiShopDAO;
import com.yuwang.pinju.core.bi.manager.BiShopManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.bi.QueryShopSalesRankDO;
import com.yuwang.pinju.domain.bi.ShopSalesRankDO;

/**
 * 
 * @author 杜成
 * @date 2011-6-20
 * @version 1.0
 * @update 2011-11-11 By MaYuanChao
 */
public class BiShopManagerImpl extends BaseManager implements BiShopManager {
	
	private BiShopDAO biShopDAO;

	@Override
	public List<ShopSalesRankDO> queryShopSalesRank(
			QueryShopSalesRankDO queryShopSalesRankDO) throws ManagerException {
		try {
			if (queryShopSalesRankDO.getMemberId() == null|| queryShopSalesRankDO.getMemberId().compareTo(0L) == 0){
				return null;
			}
			if (queryShopSalesRankDO.getShopId() == null|| queryShopSalesRankDO.getShopId().compareTo(0) == 0){
				return null;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", queryShopSalesRankDO.getMemberId());
			map.put("shopId", queryShopSalesRankDO.getShopId());
			map.put("rankType", queryShopSalesRankDO.getRankType());
			map.put("rowNum", queryShopSalesRankDO.getNum());
			return biShopDAO.querySalesRank(map);
		} catch (DaoException e) {
			log.error("BiShopManagerImpl.queryShopSalesRank error", e);
			throw new ManagerException(
					"BiShopManagerImpl.queryShopSalesRank error", e);
		} catch (NullPointerException e) {
			log.error("BiShopManagerImpl.queryShopSalesRank error not null", e);
			throw new ManagerException(
					"BiShopManagerImpl.queryShopSalesRank error not null", e);
		}
	}

	public void setBiShopDAO(BiShopDAO biShopDAO) {
		this.biShopDAO = biShopDAO;
	}

}
