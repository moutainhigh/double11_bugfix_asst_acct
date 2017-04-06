package com.yuwang.pinju.core.margin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.margin.dao.MarginSellerDAO;
import com.yuwang.pinju.domain.margin.MarginSellerDO;

/**  
 *	卖家保障金测试DAO
 */
public class MarginSellerDAOTest extends BaseTestCase{
	
	Log log = LogFactory.getLog(this.getClass().getName());
	
	@SpringBean("marginSellerDAO")
	private MarginSellerDAO marginSellerDAO;
	
	@Test
	public final void testInsertSellerMarginRecord() {
		assertNotNull(marginSellerDAO);
		try {
			MarginSellerDO sellerMarginDO = new MarginSellerDO();
//			sellerMarginDO.setId(113L);
			sellerMarginDO.setMemberId(100000L);
			sellerMarginDO.setMemberNick("jies");
			sellerMarginDO.setCpType(0);
			sellerMarginDO.setCategoryId(111L);
			sellerMarginDO.setCategoryName("手机");
			sellerMarginDO.setPinjuMargin(111000L);
			sellerMarginDO.setCurrentMargin(260111L);
			sellerMarginDO.setVersion(0);// 默认是0
			marginSellerDAO.insertMarginSellerDORecord(sellerMarginDO);
			assertNotNull(sellerMarginDO.getId());
			log.debug("卖家保证金ID："+sellerMarginDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}	
	
	@Test
	public final void testUpdateSellerMarginRecord() {
		try {
			assertNotNull(marginSellerDAO);
  			MarginSellerDO marginSellerDO = new MarginSellerDO();
			marginSellerDO.setId(10004L);
			marginSellerDO.setCategoryName("football");
			marginSellerDO.setVersion(3);
			int count = marginSellerDAO.updateMarginSellerDORecord(marginSellerDO);
			log.debug("count："+count);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}	
	
	@Test
	public final void testGetSellerMarginDOById() {
		try {
			assertNotNull(marginSellerDAO);
			MarginSellerDO sellerMarginDO = marginSellerDAO.getMarginSellerDOBySellerId(10004L);
			assertNotNull(sellerMarginDO);
			assertNotNull(sellerMarginDO.getId());
			log.debug("所属类目名称："+sellerMarginDO.getCategoryName());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}	

}
