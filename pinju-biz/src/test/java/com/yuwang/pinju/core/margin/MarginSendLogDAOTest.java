package com.yuwang.pinju.core.margin;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.trade.dao.DirectDAO;
import com.yuwang.pinju.domain.trade.DirectPaySendLogDO;

/**  
 * @Project: pinju-biz
 * @Discription: [保证金报文发送记录DAOTest]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-2 下午04:18:15
 * @update 2011-8-2 下午04:18:15
 * @version V1.0  
 */
public class MarginSendLogDAOTest extends BaseTestCase{

	@SpringBean("directDAO")
	private DirectDAO directDAO;
	
	/**
	 * Test method for {@link com.yuwang.pinju.core.rights.dao.RightsDAO#insertRightsRecord(com.yuwang.pinju.domain.rights.RightsDO)}.
	 */
	@Test
	public final void testInsertMarginOrderRecord() {
		try {
			assertNotNull(directDAO);
			DirectPaySendLogDO directPaySendLogDO = new DirectPaySendLogDO();
			directPaySendLogDO.setFromMemberId(100000060000000L);
			directPaySendLogDO.setFromMemberPayment("1234567890");
			directPaySendLogDO.setToMemberId(100000050000000L);
			directPaySendLogDO.setToMemberPayment("0987654321");
			directPaySendLogDO.setDetail("保证金发送报文详情");
			directDAO.insertDirectPaySendLogRecord(directPaySendLogDO);
			assertNotNull(directPaySendLogDO.getId());
			log.debug("保证金报文发送记录ID："+directPaySendLogDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}	
	
	@Test
	public final void testGetMarginOrderDO() {
		try {
			assertNotNull(directDAO);
			DirectPaySendLogDO marginSendLogDO = directDAO.getDirectPaySendLogDOById(1L);
			assertNotNull(marginSendLogDO.getId());
			log.debug("保证金报文发送记录ID："+marginSendLogDO.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}	
		
	
}
