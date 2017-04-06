package com.yuwang.pinju.core.margin.mananger;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.trade.manager.DirectManager;
import com.yuwang.pinju.domain.trade.DirectOrderDO;
import com.yuwang.pinju.domain.trade.DirectPayDO;
import com.yuwang.pinju.domain.trade.DirectPaySendLogDO;

/**  
 * @Project: pinju-biz
 * @Discription: [即时到账ManagerTest]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-12 下午07:08:14
 * @update 2011-8-12 下午07:08:14
 * @version V1.0  
 */
public class DirectManagerTest extends BaseTestCase{

	@SpringBean("directManager")
	private DirectManager directManager;
	
	@Test
	public final void testAddDirectRecord(){
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
		
		DirectPayDO directPayDO = new DirectPayDO();
		directPayDO.setPayOrderId(2222L);
		directPayDO.setOutPayId("3333L");
		directPayDO.setTotalFee(4444L);
		directPayDO.setRealPayAmount(5555L);
		directPayDO.setSellerId(6666L);
		directPayDO.setAcceptAccount("卖家收款账号54321");
		directPayDO.setBuyerId(9999L);
		directPayDO.setPayAccount("买家支付帐号12345");
		directPayDO.setCommisionFee(7777L);
		directPayDO.setDiscountFee(1111L);
		directPayDO.setPayState(0);
		directPayDO.setPayTime(new Date());
		directPayDO.setCreateTime(new Date());
		directPayDO.setEndTime(new Date());
		directPayDO.setCloser("交易被维权关闭");
		
		DirectPaySendLogDO marginSendLogDO = new DirectPaySendLogDO();
		marginSendLogDO.setFromMemberId(100000060000000L);
		marginSendLogDO.setFromMemberPayment("1234567890");
		marginSendLogDO.setToMemberId(100000050000000L);
		marginSendLogDO.setToMemberPayment("0987654321");
		marginSendLogDO.setDetail("保证金发送报文详情");		
		try{
			assertNotNull(directOrderDO);
			assertNotNull(directPayDO);
			assertNotNull(marginSendLogDO);
			directManager.insertDirectOrderRecord(directOrderDO,directPayDO,marginSendLogDO);
			assertTrue("即时到帐订单ID："+directOrderDO.getId(),directOrderDO.getId() != null);
			assertTrue("即时到帐支付订单ID："+directPayDO.getId(),directPayDO.getId() != null);
			//assertTrue("保证金报文发送日志ID："+marginSendLogDO.getId(),marginSendLogDO.getId() != null);
		}catch(Exception e){
			log.debug("插入即时到账失败："+e.getMessage());
		}
	}
}
