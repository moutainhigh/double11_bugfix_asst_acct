package com.yuwang.pinju.biz.item;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.BrandDAO;
import com.yuwang.pinju.domain.item.BrandDO;

public class BrandTest extends BaseTestCase {

	@SpringBean("brandDAO")
	private BrandDAO brandDAO;

	public void daoTest(){
		try {
			BrandDO brandDO = brandDAO.getItemBrandById(3l);
			log.debug("查询结果："+brandDO.getName());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testBrand() {
		daoTest();
	}

}
