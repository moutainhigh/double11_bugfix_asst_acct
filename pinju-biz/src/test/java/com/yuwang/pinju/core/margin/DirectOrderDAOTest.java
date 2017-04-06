package com.yuwang.pinju.core.margin;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.trade.dao.DirectDAO;
import com.yuwang.pinju.domain.trade.DirectOrderDO;

/**  
 * @Project: pinju-biz
 * @Discription: [即时到账订单测试DAO]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-12 上午09:47:46
 * @update 2011-8-12 上午09:47:46
 * @version V1.0  
 */
public class DirectOrderDAOTest extends BaseTestCase{
	
	Log log = LogFactory.getLog(this.getClass().getName());

	@SpringBean("directDAO")
	private DirectDAO directDAO;
	
	@Test
	public final void testGetOrderId(){
		assertNotNull(directDAO);
		Long orderId;
		try {
			orderId = directDAO.getOrderId();
			assertTrue("序列值小于20",orderId > 20);
			System.out.println(orderId);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.yuwang.pinju.core.margin.dao.DirectOrderDAO#InsertDirectOrderRecord(com.yuwang.pinju.domain.trade.DirectOrderDO)}.
	 */
	@Test
	public final void testInsertDirectOrderRecord() {
		assertNotNull(directDAO);
		DirectOrderDO directOrderDO = new DirectOrderDO();
		directOrderDO.setId(1111L);
		directOrderDO.setPayOrderId(1111L);
		directOrderDO.setBuyerId(1111L);
		directOrderDO.setBuyerNick("jiebing");
		directOrderDO.setSellerId(1111L);
		directOrderDO.setSellerNick("lingjt");
		directOrderDO.setOrderState(0);
		directOrderDO.setOutPayId("outId1111");
		directOrderDO.setOrderAmount(1111L);
		directOrderDO.setBizType(1);
		directOrderDO.setItemId(1111L);
		directOrderDO.setItemTitle("测试商品标题");
		directOrderDO.setItemPrice(1111L);
		directOrderDO.setBuyAmount(1111);
		directOrderDO.setBuyIp(1111L);
		directOrderDO.setEndTime(new Date());
		try {
			assertNotNull(directOrderDO);
			directDAO.insertDirectOrderRecord(directOrderDO);
			assertNotNull(directOrderDO.getId());
		} catch (DaoException e) {
			log.debug("插入即时到账订单失败："+e.getMessage());
		}
	}	
	
	/**
	 * Test method for {@link com.yuwang.pinju.core.margin.dao.DirectOrderDAO#getDirectOrderDOById(java.lang.Long)}.
	 */
	@Test
	public final void testGetDirectOrderDOById() {
		assertNotNull(directDAO);
		DirectOrderDO directOrderDO = new DirectOrderDO();
		try {
			assertNotNull(directOrderDO);
			directOrderDO = directDAO.getDirectOrderDOById(6L);
			assertNotNull(directOrderDO.getId());
			log.info("业务ID："+directOrderDO.getId());
		} catch (DaoException e) {
			log.debug("插入即时到账订单失败："+e.getMessage());
		}
	}	
	
}
