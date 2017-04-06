package com.yuwang.pinju.biz.servicefee;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.servicefee.manager.ServiceFeeManager;

public class ServiceFeeManagerTest extends BaseTestCase {
	@SpringBean("serviceFeeManager")
	private ServiceFeeManager serviceFeeManager;

	/**
	 * 
	 * @author liuweiguo liuweiguo@zba.com
	 * @throws ManagerException
	 * @return void
	 * @date 2011-7-20上午10:47:58
	 */
	@Test
	public void testPlatServiceFee() {
		System.out.println("");
	}

}
