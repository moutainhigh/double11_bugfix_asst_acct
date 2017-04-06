package com.yuwang.pinju.core.bi.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;
import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.bi.ShopSalesRankDO;


/**
 *
 * @author 杜成
 * @date   2011-6-8
 * @version 1.0
 */
public class TestShopSalesRankDAO extends BaseTestCase {
	
	@SpringBean("biShopDAO")
	private BiShopDAO biShopDAO;

	@Test
	public void testquerySalesRank() throws DaoException, ManagerException{
		/**
		 * 店铺销售排行榜查询
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("categoryId", 10);
		map.put("cateparentId", 1001);
		map.put("memberId", 100000055000000L);
		map.put("shopId", 100001);
		map.put("rowNum", 5);	//可为null 默认 5
		map.put("rankType", 0); //可为null 默认 0 排行榜类别
		List<ShopSalesRankDO> list = biShopDAO.querySalesRank(map);
		assertNotNull(list);
		assertEquals(list.size()>0, true);
		assertEquals(list.size(), 5);
	}
}
