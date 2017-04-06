package com.yuwang.pinju.core.margin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.margin.dao.MarginPinJuDAO;
import com.yuwang.pinju.domain.margin.MarginPinJuDO;

public class MarginPinJuDAOTest extends BaseTestCase{
	
	Log log = LogFactory.getLog(this.getClass().getName());
	
	@SpringBean("marginPinJuDAO")
	private MarginPinJuDAO marginPinJuDAO;
	
	@Test
	public final void testAddMarginPinJuDO(){
		MarginPinJuDO marginPinJuDO = new MarginPinJuDO();
		marginPinJuDO.setCurBalAmount(100L);
		marginPinJuDO.setVersion(1);
		try {
			marginPinJuDAO.addMarginPinJuDO(marginPinJuDO);
		} catch (DaoException e) {
			log.error("添加账户失败", e);
		}
	}
	
	@Test
	public final void testUpdateMarginPinJuDO(){
		MarginPinJuDO marginPinJuDO = new MarginPinJuDO();
		marginPinJuDO.setId(10008L);
		marginPinJuDO.setCurBalAmount(500L);
		marginPinJuDO.setVersion(2);
		int a = 0;
		try {
			a = marginPinJuDAO.updateMarginPinJuDO(marginPinJuDO);
		} catch (DaoException e) {
			log.error("更新账户失败", e);
		}
		log.debug("a="+a);
	}
	
	@Test
	public final void testGetMarginPinJuDOById(){
		MarginPinJuDO marginPinJuDO = new MarginPinJuDO();
		try {
			marginPinJuDO=marginPinJuDAO.getMarginPinJuDOById(10001L);
		} catch (DaoException e) {
			log.error("获取保证金信息失败", e);
		}
		System.out.println("CurBalAmount:"+marginPinJuDO.getCurBalAmount());
		System.out.println("Version:"+marginPinJuDO.getVersion());
		System.out.println("Id:"+marginPinJuDO.getId());
		
	}
	
	@Test
	public final void testGetMarginPinJuDO(){
		MarginPinJuDO marginPinJuDO = new MarginPinJuDO();
		List<MarginPinJuDO> margins = null;
		try {
			margins=marginPinJuDAO.getMarginPinJuDO();
		} catch (DaoException e) {
			log.error("获取保证金信息失败", e);
		}
		marginPinJuDO = margins.get(0);
		System.out.println("CurBalAmount:"+marginPinJuDO.getCurBalAmount());
		System.out.println("Version:"+marginPinJuDO.getVersion());
		System.out.println("Id:"+marginPinJuDO.getId());
		
	}
}
