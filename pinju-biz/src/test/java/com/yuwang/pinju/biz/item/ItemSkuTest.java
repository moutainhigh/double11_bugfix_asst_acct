package com.yuwang.pinju.biz.item;

import java.util.List;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.SkuDAO;
import com.yuwang.pinju.domain.item.SkuDO;

public class ItemSkuTest extends BaseTestCase {

	@SpringBean("skuDAO")
	private SkuDAO skuDAO;

	public void itemSkuDaoTest() {
		try {

			SkuDO skuDO = new SkuDO();

			skuDO.setSellerId(100000055000000l);
			skuDO.setItemId(10l);
			skuDO.setSalePv1("1:1;");
			skuDO.setSalePv2("2:2;");
			skuDO.setSalePv3("3:3;");
			skuDO.setSalePv4("4:4;");
			skuDO.setPrice(new Money("11.11").getCent());
			skuDO.setOriStock(10l);
			skuDO.setCurrentStock(10l);

			skuDO.setGmtCreate(DateUtil.current());
			skuDO.setGmtModified(DateUtil.current());

			long id = skuDAO.createItemSku(skuDO);
			log.debug("createItemSku：" + id);

			List<SkuDO> ls = skuDAO.getItemSkuByItemId(10l);
			log.debug("getItemSkuByItemId[size]:" + ls.size());

			skuDO.setPrice(20l);
			int flag = skuDAO.updateItemSku(skuDO);
			log.debug("updateItemSku[result]:" + flag);
			
			skuDO = new SkuDO();
			skuDO.setPrice(20l);
			ls = skuDAO.getItemSku(skuDO);
			log.debug("getItemSku[result]:" + ls.size());


		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testItemSku() {
		itemSkuDaoTest();
	}

}
