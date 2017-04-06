package com.yuwang.pinju.core.margin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.margin.dao.MarginSellerOrderDAO;
import com.yuwang.pinju.domain.margin.MarginSellerOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginSellerOrderQuery;

public class MarginSellerOrderDAOTest extends BaseTestCase{
	
	Log log = LogFactory.getLog(this.getClass().getName());
	
	@SpringBean("marginSellerOrderDAO")
	private MarginSellerOrderDAO marginSellerOrderDAO;
	
	@Test
	public final void testInsertMarginOrderRecord() {
		try {
			assertNotNull(marginSellerOrderDAO);
			MarginSellerOrderDO marginSellerOrderDO = new MarginSellerOrderDO();
			marginSellerOrderDO.setMemberId(10000006L);
			marginSellerOrderDO.setMemberId(100000L);
			marginSellerOrderDO.setMemberNick("jieDAO");
			marginSellerOrderDO.setInvMemberId(200001L);
			marginSellerOrderDO.setInvMemberNick("hello");
			marginSellerOrderDO.setInvMemberPayment("20110802143232");
			marginSellerOrderDO.setRightsId(2L);
			marginSellerOrderDO.setRefundId(201L);
			marginSellerOrderDO.setAmount(260000L);
			marginSellerOrderDO.setOperateType(0);
			marginSellerOrderDAO.insertMarginSellerOrderRecord(marginSellerOrderDO);
			assertNotNull(marginSellerOrderDO.getId());
			log.debug("保证金交易ID："+marginSellerOrderDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}	
	
	@Test
	public final void testUpdateMarginOrderRecord() {
		try {
			assertNotNull(marginSellerOrderDAO);
			MarginSellerOrderDO marginSellerOrderDO = new MarginSellerOrderDO();
			marginSellerOrderDO.setId(10000L);
			marginSellerOrderDO.setMemberNick("州立");
			marginSellerOrderDO.setInvMemberNick("how are you ");
			marginSellerOrderDO.setAmount(250L);
			marginSellerOrderDO.setOperateType(1);
			marginSellerOrderDAO.updateMarginSellerOrderRecord(marginSellerOrderDO);
			assertNotNull(marginSellerOrderDO.getId());
			log.debug("保证金交易ID："+marginSellerOrderDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testGetMarginDOById() {
		try {
			assertNotNull(marginSellerOrderDAO);
			MarginSellerOrderDO marginSellerOrderDO = marginSellerOrderDAO.getMarginSellerOrderDOById(10000L);
			assertNotNull(marginSellerOrderDO.getId());
			log.debug("nick："+marginSellerOrderDO.getInvMemberNick());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}	
	
	@Test
	public final void testGetMarginDOs() {
		try {
			MarginSellerOrderQuery marginSellerOrderQuery = new MarginSellerOrderQuery();
			marginSellerOrderQuery.setMemberId(100000L);
			int count=0;
			try {
				count = marginSellerOrderDAO.getMarginSellerOrderDOsCount(marginSellerOrderQuery);
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.debug("记录数："+count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
