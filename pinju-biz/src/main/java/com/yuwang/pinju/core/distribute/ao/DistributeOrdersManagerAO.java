package com.yuwang.pinju.core.distribute.ao;

import java.util.List;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.query.QueryDistributeOrder;

/**
 * 分销订单查询
 * 
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public interface DistributeOrdersManagerAO {

	/**
	 * 取得供应商ID
	 * 
	 * @param userId
	 * @return
	 */
	public Long getSupplierId(long userId);

	/**
	 * 取得分销商ID
	 * 
	 * @param userId
	 * @return
	 */
	public DistributeDistributorDO getDistributorId(long userId);

	/**
	 * 取得分销商已分销商品总数
	 * 
	 * @param distributeId
	 * @return
	 */
	public Integer getDistributeItemCount(long distributorId);

	/**
	 * 取得分销商合作的供应商总数
	 * 
	 * @param distributeId
	 * @return
	 */
	public Integer getDistributeSupplierCount(DistribureChannelParamDO param);

	/**
	 * 取得分销商合作的供应商列表
	 * 
	 * @param distributeId
	 * @return
	 */
	public List<DistributeSupplierDO> getDistributeSuppliers(DistribureChannelParamDO param);
	
	/**
	 * 分销订单分页查询
	 * 
	 * @param queryDistributeOrder
	 * @return OrderChannelDO List
	 */
	public Result queryDistributeOrderList(QueryDistributeOrder queryDistributeOrder);
}