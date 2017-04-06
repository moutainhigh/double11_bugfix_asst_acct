package com.yuwang.pinju.core.margin;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.trade.dao.DirectDAO;
import com.yuwang.pinju.domain.trade.DirectPayRevLogDO;

/**  
 * @Project: pinju-biz
 * @Discription: [保证金报文接收记录DAOTest]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-2 下午04:43:11
 * @update 2011-8-2 下午04:43:11
 * @version V1.0  
 */
public class MarginRevLogDAOTest extends BaseTestCase{

	@SpringBean("directDAO")
	private DirectDAO directDAO;
	
	@Test
	public final void testInsertMarginRevLogRecord(){
		try{
			assertNotNull(directDAO);
			DirectPayRevLogDO directPayRevLogDO = new DirectPayRevLogDO();
			directPayRevLogDO.setDetail("保证金报文接受记录详情，已接收");
			directDAO.insertdirectPayRevLogRecord(directPayRevLogDO);
			assertNotNull(directPayRevLogDO.getId());
			assertFalse("插入失败",directPayRevLogDO.getId()==null);
			assertTrue("插入成功",directPayRevLogDO.getId()!=null);
		}catch(DaoException e){
			e.getStackTrace();
		}
	}
	
	@Test
	public final void testGetMarginRevLogDOById(){
		try{
			assertNotNull(directDAO);
			DirectPayRevLogDO marginRevLogDO = directDAO.getDirectPayRevLogDOById(1L);
			assertNotNull(marginRevLogDO);
			assertNotNull(marginRevLogDO.getId());
			System.out.println("保证金报文接受记录ID："+marginRevLogDO.getId());
		}catch(DaoException e){
			e.getStackTrace();
		}
	}
		
}
