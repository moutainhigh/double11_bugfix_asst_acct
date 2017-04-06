/**
 * 
 */
package com.yuwang.pinju.core.rights.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsMsgQuery;
import com.yuwang.pinju.domain.rights.RightsQuery;

/**  
 * @Project: pinju-biz
 * @Description: 消保维权DAO测试类
 * @author 石兴 shixing@zba.com
 * @date 2011-6-29 下午02:06:25
 * @update 2011-6-29 下午02:06:25
 * @version V1.0  
 */
public class RightsDAOTest extends BaseTestCase {

	@SpringBean("rightsDAO")
	private RightsDAO rightsDAO;
	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#insertRightsRecord(com.yuwang.pinju.domain.rights.RightsDO)}.
	 */
	@Test
	public final void testInsertRightsRecord() {
		assertNotNull(rightsDAO);
		RightsDO rightsDO = new RightsDO();
		rightsDO.setOrderId(11112L);
		rightsDO.setSubOrderId(11113L);
		rightsDO.setBuyerId(100000395009000L);
		rightsDO.setBuyerNick("shixing");
		rightsDO.setSellerId(11115L);
		rightsDO.setSellerNick("柠檬绿茶");
		rightsDO.setApplyDate(new Date());
		rightsDO.setSellerProcDate(new Date());
		rightsDO.setPrice(100L);
		rightsDO.setBuyerApplyPrice(90L);
		rightsDO.setSellerReturnPrice(90L);
		rightsDO.setIsReturnGoods(1);
		rightsDO.setVoucherType(0);
		rightsDO.setBuyerRequire(0);
		rightsDO.setBuyerReason("绿茶不好喝，过期了！");
		rightsDO.setLogisticsId(11117L);
		rightsDO.setState(0);
		rightsDO.setVoucherPic1("http://www.pingju.com/pic1.jpg");
		rightsDO.setVoucherPic2("http://www.pingju.com/pic2.jpg");
		rightsDO.setVoucherPic3("http://www.pingju.com/pic3.jpg");
		try {
			rightsDAO.insertRightsRecord(rightsDO);
			assertNotNull(rightsDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#insertMessageRecord(com.yuwang.pinju.domain.rights.RightsMessageDO)}.
	 */
	@Test
	public final void testInsertMessageRecord() {
		assertNotNull(rightsDAO);
		RightsMessageDO rightsMessageDO = new RightsMessageDO();
		rightsMessageDO.setUserId(11114L);
		rightsMessageDO.setUserNick("shixing");
		rightsMessageDO.setContent("老板，你这什么东西，必须给我退掉，难喝死啦");
		rightsMessageDO.setVoucherId(1L);
		rightsMessageDO.setVoucherPic1("http://www.pingju.com/pic4.jpg");
		rightsMessageDO.setVoucherPic2("http://www.pingju.com/pic5.jpg");
		rightsMessageDO.setVoucherPic3("http://www.pingju.com/pic6.jpg");
		try {
			rightsDAO.insertMessageRecord(rightsMessageDO);
			assertNotNull(rightsMessageDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#insertLogisticsRecord(com.yuwang.pinju.domain.rights.RightsLogisticsDO)}.
	 */
	@Test
	public final void testInsertLogisticsRecord() {
		assertNotNull(rightsDAO);
		RightsLogisticsDO rightsLogisticsDO = new RightsLogisticsDO();
		rightsLogisticsDO.setVoucherId(1L);
		rightsLogisticsDO.setLogisticsId(1L);
		rightsLogisticsDO.setLogisticsName("申通快递");
		rightsLogisticsDO.setBillNo("4444444444");
		rightsLogisticsDO.setBuyerId(11114L);
		rightsLogisticsDO.setComments("东西我给你寄过来啦");
		rightsLogisticsDO.setBuySendDate(new Date());
		rightsLogisticsDO.setSellerId(11115L);
		rightsLogisticsDO.setSellerConfirmDate(new Date());
		rightsLogisticsDO.setBuyerReturnState(0);
		try {
			rightsDAO.insertLogisticsRecord(rightsLogisticsDO);
			assertNotNull(rightsLogisticsDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#getApplyRightsDO(com.yuwang.pinju.domain.rights.RightsDO)}.
	 */
	@Test
	public final void testGetApplyRightsDO() {
		
		RightsDO rightsDO = new RightsDO();
		try {
			rightsDO.setId(1L);
			rightsDO = rightsDAO.getApplyRightsDO(rightsDO);
			assertNotNull(rightsDO);
			System.out.println(rightsDO.getBuyerId());
			System.out.println(rightsDO.getBuyerNick());
			System.out.println(rightsDO.getSellerId());
			System.out.println(rightsDO.getSellerNick());
			System.out.println(rightsDO.getSellerReturnPrice());
			System.out.println(rightsDO.getState());
			System.out.println(rightsDO.getBuyerReason());
			System.out.println(rightsDO.getBuyerRequire());
			System.out.println(rightsDO.getIsReturnGoods());
			System.out.println(rightsDO.getLogisticsId());
			System.out.println(rightsDO.getOrderId());
			System.out.println(rightsDO.getSubOrderId());
			System.out.println(rightsDO.getVoucherPic1());
			System.out.println(rightsDO.getVoucherPic2());
			System.out.println(rightsDO.getVoucherPic3());
			System.out.println(rightsDO.getVoucherType());
			System.out.println(rightsDO.getApplyDate());
			assertEquals(rightsDO.getBuyerNick(), "shixing");
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#getRightsDOs(com.yuwang.pinju.domain.rights.RightsDO)}.
	 */
	@Test
	public final void testGetRightsDOs() {
		
		try {
			RightsQuery query = new RightsQuery();
			query.setBuyerId(100000060000000L);
			List<RightsDO> rightsDOs = rightsDAO.getRightsDOs(query);
			assertNotNull(rightsDOs);
			for (RightsDO rightsDO : rightsDOs) {
				System.out.println(rightsDO.getBuyerId());
				System.out.println(rightsDO.getBuyerNick());
				System.out.println(rightsDO.getSellerId());
				System.out.println(rightsDO.getSellerNick());
				System.out.println(rightsDO.getSellerReturnPrice());
				System.out.println(rightsDO.getState());
				System.out.println(rightsDO.getBuyerReason());
				System.out.println(rightsDO.getBuyerRequire());
				System.out.println(rightsDO.getIsReturnGoods());
				System.out.println(rightsDO.getLogisticsId());
				System.out.println(rightsDO.getOrderId());
				System.out.println(rightsDO.getSubOrderId());
				System.out.println(rightsDO.getVoucherPic1());
				System.out.println(rightsDO.getVoucherPic2());
				System.out.println(rightsDO.getVoucherPic3());
				System.out.println(rightsDO.getVoucherType());
				System.out.println(rightsDO.getApplyDate());
			}
			assertTrue(rightsDOs.size()>1);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#getRightsMessageDOs(long)}.
	 */
	@Test
	public final void testGetRightsMessageDOs() {
		RightsMsgQuery rightsMsgQuery = new RightsMsgQuery();
		rightsMsgQuery.setVoucherId(1L);
		List<RightsMessageDO> rightsMessageDOs;
		try {
			rightsMessageDOs = rightsDAO.getRightsMessageDOs(rightsMsgQuery);
			for (RightsMessageDO rightsMessageDO2 : rightsMessageDOs) {
				System.out.println(rightsMessageDO2.getId());
				System.out.println(rightsMessageDO2.getUserId());
				System.out.println(rightsMessageDO2.getUserNick());
				System.out.println(rightsMessageDO2.getContent());
				System.out.println(rightsMessageDO2.getVoucherId());
				System.out.println(rightsMessageDO2.getVoucherPic1());
				System.out.println(rightsMessageDO2.getVoucherPic2());
				System.out.println(rightsMessageDO2.getVoucherPic3());
				assertEquals(rightsMessageDO2.getUserNick(), "shixing");
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#getRightsLogisticsDO(long)}.
	 */
	@Test
	public final void testGetRightsLogisticsDO() {
		RightsLogisticsDO rightsLogisticsDO = new RightsLogisticsDO();
		rightsLogisticsDO.setId(1L);
		try {
			rightsLogisticsDO = rightsDAO.getRightsLogisticsDO(rightsLogisticsDO);
			System.out.println(rightsLogisticsDO.getComments());
			System.out.println(rightsLogisticsDO.getLogisticsId());
			System.out.println(rightsLogisticsDO.getLogisticsName());
			System.out.println(rightsLogisticsDO.getBuySendDate());
			System.out.println(rightsLogisticsDO.getBuyerId());
			System.out.println(rightsLogisticsDO.getBuyerReturnState());
			System.out.println(rightsLogisticsDO.getSellerConfirmDate());
			assertTrue("物流公司：申通快递", rightsLogisticsDO.getLogisticsName().equals("申通快递"));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#updateRightsRecord(com.yuwang.pinju.domain.rights.RightsDO)}.
	 */
	@Test
	public final void testUpdateRightsRecord() {
		RightsDO rightsDO = new RightsDO();
		rightsDO.setId(1L);
		rightsDO.setState(1);
		try {
			rightsDO = rightsDAO.getApplyRightsDO(rightsDO);
			rightsDO.setState(3);
			rightsDAO.updateRightsRecord(rightsDO);
			rightsDO = rightsDAO.getApplyRightsDO(rightsDO);
			assertEquals(rightsDO.getState(), new Integer(3));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#updateRightsLogisticsRecord(long)}.
	 */
	@Test
	public final void testUpdateRightsLogisticsRecord() {
		RightsLogisticsDO rightsLogisticsDO = new RightsLogisticsDO();
		rightsLogisticsDO.setId(1L);
		try {
			rightsLogisticsDO = rightsDAO.getRightsLogisticsDO(rightsLogisticsDO);
			rightsLogisticsDO.setBuyerReturnState(1);
			rightsDAO.updateRightsLogisticsRecord(rightsLogisticsDO);
			rightsLogisticsDO = rightsDAO.getRightsLogisticsDO(rightsLogisticsDO);
			assertEquals(rightsLogisticsDO.getBuyerReturnState(), 1);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

}
