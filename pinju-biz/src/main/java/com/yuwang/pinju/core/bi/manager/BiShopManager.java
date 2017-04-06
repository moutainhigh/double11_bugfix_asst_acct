package com.yuwang.pinju.core.bi.manager;

import java.util.List;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.bi.QueryShopSalesRankDO;
import com.yuwang.pinju.domain.bi.ShopSalesRankDO;

/**
 * @see 商铺相关数据挖掘管理
 * @author 杜成
 * @date   2011-6-20
 * @version 1.0
 */
public interface BiShopManager {
	
	/**
	 * @see 查询店铺商品销售排行
	 * @param queryShopSalesRankDO 查询实体 
	 * queryShopSalesRankDO 中的
	 * memberId,shopId 不能为null或0,否则的话抛出ManagerException异常
	 * mun 默认查询条数为5
	 * @return 没有结果值的话 返回O长度的List集合
	 * @throws ManagerException
	 * @author 杜成
	 */
	List<ShopSalesRankDO> queryShopSalesRank(QueryShopSalesRankDO queryShopSalesRankDO)throws ManagerException;
	
}
