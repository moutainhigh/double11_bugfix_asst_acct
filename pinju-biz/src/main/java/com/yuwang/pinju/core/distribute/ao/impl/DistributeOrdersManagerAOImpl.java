package com.yuwang.pinju.core.distribute.ao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.distribute.DistributeConstant;
import com.yuwang.pinju.core.distribute.ao.DistributeOrdersManagerAO;
import com.yuwang.pinju.core.distribute.manager.DistributeChannelItemManager;
import com.yuwang.pinju.core.distribute.manager.DistributeChannelManager;
import com.yuwang.pinju.core.distribute.manager.DistributeSupplierManager;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.query.QueryDistributeOrder;

/**
 * 分销订单查询
 * 
 * @author xiazhenyu
 * 
 */
public class DistributeOrdersManagerAOImpl extends BaseAO implements DistributeOrdersManagerAO {

	/**
	 * 供应商Manager
	 */
	private DistributeSupplierManager distributeSupplierManager;

	/**
	 * 分销商Manager
	 */
	private DistributeChannelManager distributeChannelManager;

	/**
	 * 订单Manager
	 */
	private OrderQueryManager orderQueryManager;

	/**
	 * 分销商Manager
	 */
	private DistributorManager distributorManager;
	
	@Autowired
	private DistributeChannelItemManager distributeChannelItemManager;

	/**
	 * 取得分销商ID
	 */
	public DistributeDistributorDO getDistributorId(long userId) {
		try {
			DistributeDistributorDO dddo = distributeChannelManager.getDistributorByUserId(userId);
			if (null != dddo) {
				return dddo;
			}
		} catch (ManagerException e) {
			log.error("取得供应商ID出错", e);
		}
		return null;
	}

	/**
	 * 取得供应商ID
	 */
	public Long getSupplierId(long userId) {
		DistributeSupplierParamDO distributeSupplierDO = new DistributeSupplierParamDO();
		distributeSupplierDO.setMemberId(userId);
		try {
			return Long.valueOf(distributeSupplierManager.getDistributeSupplier(distributeSupplierDO).getId());
		} catch (ManagerException e) {
			log.error("取得供应商ID出错", e);
		}
		return null;
	}

	/**
	 * 取得分销商已分销商品总数
	 * 
	 * @param distributeId
	 * @return
	 */
	@Override
	public Integer getDistributeItemCount(long distributorId) {//caiwei 已经分销商品数改为统计记录条数，不再拼接字符串        ---->  用分销商ID去DAO查
//		StringBuffer goodIdString = new StringBuffer();
//		// 获取分销商分销的所有商品等信息
//		List<DistribureChannelDO> distribureChannelDOList = distributorManager
//				.getItemByCondition(new DistributorItemQuery(distributorId));
//		// 组装可以分销商品的ID
//		for (DistribureChannelDO distribureChannelDO : distribureChannelDOList) {
//			goodIdString.append(distribureChannelDO.getGoodsList());
//		}
//		List<Long> itemList = distributorManager.getItemIdList(goodIdString.toString());
//		if (-1L == itemList.get(0)) {
//			return 0;
//		} else {
//			return itemList.size();
//		}
		return distributeChannelItemManager.getCount(new DistributeChannelItemParamDO(distributorId, DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_SELLING));
	}

	/**
	 * 取得分销商合作的供应商总数
	 * 
	 * @param distributeId
	 * @return
	 */
	@Override
	public Integer getDistributeSupplierCount(DistribureChannelParamDO param) {
		return distributorManager.findDistributeSuppliersCountByChannel(param);
	}

	/**
	 * 取得分销商合作的供应商列表
	 * 
	 * @param distributeId
	 * @return
	 */
	@Override
	public List<DistributeSupplierDO> getDistributeSuppliers(DistribureChannelParamDO param) {
		return distributorManager.findDistributeSuppliersByCondition(param);
	}

	/**
	 * 分销订单分页查询
	 * 
	 * @param queryDistributeOrder
	 * @return OrderChannelDO List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Result queryDistributeOrderList(QueryDistributeOrder queryDistributeOrder) {
		List<Long> ids = new ArrayList<Long>();
		try {
			Result result = orderQueryManager.queryDistributeOrderList(queryDistributeOrder);
			Object object = result.getModel("orderChannelList");
			if (object != null) {
				for (OrderChannelDO orderChannelDO : (List<OrderChannelDO>)object) {
					ids.add(orderChannelDO.getSupplierId());
				}
				List<DistributeSupplierDO> distributeSupplierDOList = distributorManager.selectSupplierShopIdByIds(ids);
				result.setModel("shopIds", getShopIdList(distributeSupplierDOList));
				result.setModel("orderChannelList", addShopId((List<OrderChannelDO>)object, getShopIdMap(distributeSupplierDOList)));
			}
			return result;
		} catch (ManagerException e) {
			log.error("分销订单分页查询出错", e);
		}
		return null;
	}

	private List<OrderChannelDO> addShopId(List<OrderChannelDO> param, Map<Long, Integer> shopIdMap){
		for (OrderChannelDO orderChannelDO : param) {
			orderChannelDO.setShopId(shopIdMap.get(orderChannelDO.getSupplierId()));
		}
		return param;
	}
	
	private Map<Long, Integer> getShopIdMap(List<DistributeSupplierDO> distributeSupplierDOList){
		Map<Long, Integer> resultMap = new HashMap<Long, Integer>();
		for (DistributeSupplierDO distributeSupplierDO : distributeSupplierDOList) {
			resultMap.put(distributeSupplierDO.getMemberId(), distributeSupplierDO.getShopId());
		}
		return resultMap;
	}
	
	private List<Integer> getShopIdList(List<DistributeSupplierDO> param) {
		List<Integer> ids = new ArrayList<Integer>();
		for (DistributeSupplierDO distributeSupplierDO : param) {
			ids.add(distributeSupplierDO.getShopId());
		}
		return ids;
	}
	
	public DistributeSupplierManager getDistributeSupplierManager() {
		return distributeSupplierManager;
	}

	public void setDistributeSupplierManager(DistributeSupplierManager distributeSupplierManager) {
		this.distributeSupplierManager = distributeSupplierManager;
	}

	public DistributeChannelManager getDistributeChannelManager() {
		return distributeChannelManager;
	}

	public void setDistributeChannelManager(DistributeChannelManager distributeChannelManager) {
		this.distributeChannelManager = distributeChannelManager;
	}

	public OrderQueryManager getOrderQueryManager() {
		return orderQueryManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public DistributorManager getDistributorManager() {
		return distributorManager;
	}

	public void setDistributorManager(DistributorManager distributorManager) {
		this.distributorManager = distributorManager;
	}

}
