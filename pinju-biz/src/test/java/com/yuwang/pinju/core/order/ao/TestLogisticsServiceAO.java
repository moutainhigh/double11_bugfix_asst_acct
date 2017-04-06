package com.yuwang.pinju.core.order.ao;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.domain.order.LogisticsServiceDO;

public class TestLogisticsServiceAO extends BaseTestCase {

	@SpringBean("logisticsServiceAO")
	private LogisticsServiceAO logisticsServiceAO;
	
	/**
	 * 查询物流信息  （通过WebService）
	 * @param billcode 订单号
	 * @param express  快递公司代码
	 * @return 物流信息实体
	 */
	@Test
	public void testGetTrackBybillcode(){
		LogisticsServiceDO lod = logisticsServiceAO.getTrackBybillcode("368811452176", "sto");
		//System.out.println(lod.getStatus()+" - "+lod.getMsg()+" - "+lod.getStep());
	}
}
