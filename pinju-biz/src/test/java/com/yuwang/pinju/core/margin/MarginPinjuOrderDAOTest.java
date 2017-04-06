package com.yuwang.pinju.core.margin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.margin.dao.MarginPinjuOrderDAO;
import com.yuwang.pinju.domain.margin.MarginPinjuOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginPinJuOrderQuery;

/**  
 * @Project: pinju-biz
 * @Discription: [品聚保证金交易流水DAO测试类]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-9 下午05:27:34
 * @update 2011-8-9 下午05:27:34
 * @version V1.0  
 */
public class MarginPinjuOrderDAOTest extends BaseTestCase {
	
	Log log = LogFactory.getLog(this.getClass().getName());

	@SpringBean("marginPinjuOrderDAO")
	private MarginPinjuOrderDAO marginPinjuOrderDAO;

	/**
	 * Test method for {@link com.yuwang.pinju.core.marign.dao.MarginPinjuOrderDAO#insertMarginPinjuOrderRecord(com.yuwang.pinju.domain.margin.MarginPinjuOrderDO)}.
	 */
	@Test
	public final void testInsertMarginPJOrderRecord() {
		try {
			assertNotNull(marginPinjuOrderDAO);
			MarginPinjuOrderDO marginPinjuOrderDO = new MarginPinjuOrderDO();
			marginPinjuOrderDO.setInvMemberId(100000350009000L);
			marginPinjuOrderDO.setInvMemberNick("jiejiejie");
			marginPinjuOrderDO.setInvMemberPayment("62251234567890");
			marginPinjuOrderDO.setOperateType(0);
			marginPinjuOrderDO.setAmount(99999999L);
			marginPinjuOrderDO.setRefundId(1L);
			marginPinjuOrderDO.setRefundId(4444L);
			marginPinjuOrderDAO.insertMarginPinjuOrderRecord(marginPinjuOrderDO);
			assertNotNull(marginPinjuOrderDO.getId());
			log.debug("品聚保证金交易ID："+marginPinjuOrderDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.yuwang.pinju.core.marign.dao.MarginPinjuOrderDAO#updateMarginPinjuOrderRecord(com.yuwang.pinju.domain.margin.MarginPinjuOrderDO)}.
	 */
	@Test
	public final void testUpdateMarginPJOrderRecord() {
		try {
			assertNotNull(marginPinjuOrderDAO);
			MarginPinjuOrderDO marginPinjuOrderDO = new MarginPinjuOrderDO();
			marginPinjuOrderDO.setId(10005L);
			marginPinjuOrderDO.setInvMemberId(100000350009000L);
			marginPinjuOrderDO.setInvMemberNick("jiebingljt");
			marginPinjuOrderDO.setInvMemberPayment("62250987654321");
			marginPinjuOrderDO.setOperateType(0);
			marginPinjuOrderDO.setAmount(6666666L);
			marginPinjuOrderDO.setRefundId(1L);
			marginPinjuOrderDO.setRefundId(4444L);
			marginPinjuOrderDAO.updateMarginPinjuOrderRecord(marginPinjuOrderDO);
			assertNotNull(marginPinjuOrderDO.getId());
			log.debug("品聚保证金交易ID："+marginPinjuOrderDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testGetMarginPJOrderDOById() {
		try {
			assertNotNull(marginPinjuOrderDAO);
			MarginPinjuOrderDO marginPinjuOrderDO = new MarginPinjuOrderDO();
			marginPinjuOrderDO = marginPinjuOrderDAO.getMarginPinjuOrderDOById(100000350009000L);
			assertNotNull(marginPinjuOrderDO.getId());
			log.debug("品聚保证金交易ID："+marginPinjuOrderDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testGetMarginPJOrderDOs() {
		try {
			assertNotNull(marginPinjuOrderDAO);
			MarginPinJuOrderQuery marginPinJuOrderQuery = new MarginPinJuOrderQuery();
			List<MarginPinjuOrderDO> marginPinjuOrderDOs = marginPinjuOrderDAO.getPinjuOrderDOs(marginPinJuOrderQuery);
			assertNotNull(marginPinjuOrderDOs);
			log.debug("品聚保证金交易记录数："+marginPinjuOrderDOs.size());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

}
