package com.yuwang.pinju.core.margin;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.trade.dao.DirectDAO;
import com.yuwang.pinju.domain.trade.DirectPayDO;

/**  
 * @Project: pinju-biz
 * @Discription: [即时到账支付订单测试DAO]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-12 上午09:47:46
 * @update 2011-8-12 上午09:47:46
 * @version V1.0  
 */
public class DirectPayDAOTest extends BaseTestCase{
	
	Log log = LogFactory.getLog(this.getClass().getName());

	@SpringBean("directDAO")
	private DirectDAO directDAO;
	
	/**
	 * Test method for {@link com.yuwang.pinju.core.margin.dao.DirectOrderDAO#InsertDirectOrderRecord(com.yuwang.pinju.domain.margin.DirectOrderDO)}.
	 */
	@Test
	public final void testInsertDirectOrderRecord() {
		assertNotNull(directDAO);
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
		try {
			assertNotNull(directPayDO);
			directDAO.insertDirectPayRecord(directPayDO);
			assertNotNull(directPayDO.getId());
			log.info(""+directPayDO.getAcceptAccount());
		} catch (DaoException e) {
			log.debug("插入即时到账支付订单失败："+e.getMessage());
		}
	}		
	
}
