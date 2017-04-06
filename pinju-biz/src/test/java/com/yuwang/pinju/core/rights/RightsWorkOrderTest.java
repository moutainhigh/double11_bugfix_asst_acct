package com.yuwang.pinju.core.rights;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.rights.manager.RightsWorkOrderManager;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderQuery;

public class RightsWorkOrderTest extends BaseTestCase{

	@SpringBean("rightsWorkOrderManager")
	private RightsWorkOrderManager rightsWorkOrderManager;
	
	@Test
	public void testGetRightsWorkOrderDOByBizType(){
		FinanceWorkOrderQuery financeWorkOrderQuery = new FinanceWorkOrderQuery();
		financeWorkOrderQuery.setBizId(10061);
		financeWorkOrderQuery.setBizType(100);
		FinanceWorkOrderDO rightsWorkOrderDO;
		try {
			rightsWorkOrderDO = rightsWorkOrderManager.getRightsWorkOrderDOByBizType(financeWorkOrderQuery);
			assertTrue(rightsWorkOrderDO.getId().longValue()==43);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertRightsWorkOrderDO(){
		FinanceWorkOrderDO financeWorkOrderDO = new FinanceWorkOrderDO();
		financeWorkOrderDO.setOperId(666L);
		financeWorkOrderDO.setOperPerson("crmAdmin");
		financeWorkOrderDO.setOperType(100);
		financeWorkOrderDO.setOrderId(000022L);
		financeWorkOrderDO.setRightsReason("假一赔三");
		financeWorkOrderDO.setSellerId(888L);
		financeWorkOrderDO.setSellerNick("我试大卖家");
		financeWorkOrderDO.setShopId(8888L);
		financeWorkOrderDO.setShopName("中华旗舰店");
		financeWorkOrderDO.setDeMargin(500L);
		financeWorkOrderDO.setBuyerId(999L);
		financeWorkOrderDO.setBuyerNick("我是中华大买家");
		financeWorkOrderDO.setBuyerBankAccount("ccbc");
		financeWorkOrderDO.setBuyerBankCode("111 222 333 444 555");
		financeWorkOrderDO.setBuyerBankOpen("北京八大胡同");
		financeWorkOrderDO.setStatus(100);
		financeWorkOrderDO.setWarnTime(new Date());
		financeWorkOrderDO.setGmtCreate(new Date());
		financeWorkOrderDO.setGmtModified(new Date());
		try {
			rightsWorkOrderManager.insertRightsWorkOrderDO(financeWorkOrderDO);
			System.out.println(financeWorkOrderDO.getId());
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateRightsWorkOrderDO(){
		FinanceWorkOrderDO financeWorkOrderDO = new FinanceWorkOrderDO();
		financeWorkOrderDO.setId(3L);
		financeWorkOrderDO.setStatus(200);
		try {
			boolean flag = rightsWorkOrderManager.updateRightsWorkOrderDO(financeWorkOrderDO);
			System.out.println(flag);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetRightsWorkOrderDOs(){
		FinanceWorkOrderQuery financeWorkOrderQuery = new FinanceWorkOrderQuery();
		financeWorkOrderQuery.setBuyerNick("我是中华大买家");
		List list = new ArrayList();
		try {
			list = rightsWorkOrderManager.getRightsWorkOrderDOs(financeWorkOrderQuery);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
	}
	
}
