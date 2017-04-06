package com.yuwang.pinju.core.sales;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.sales.dao.ItemXianGouDAO;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;

public class ItemXianGouDAOTest extends BaseTestCase {
	
	@SpringBean("itemXianGouDAO")
	private ItemXianGouDAO itemXianGouDAO;
	
	@Test
	public void testInsertItemXianGouDO() {
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		Date date = new Date();
		itemXianGouDO.setItemId(555L);
		itemXianGouDO.setItemTitle("大白菜");
		itemXianGouDO.setSellerId(333L);
		itemXianGouDO.setSellerNick("seller");
		itemXianGouDO.setCount(7000L);
		itemXianGouDO.setStatus(0);
		itemXianGouDO.setBuyCount(2);
		itemXianGouDO.setVersion(1);
		itemXianGouDO.setExpiryEnd(date);
		itemXianGouDO.setExpiryStart(date);
		try {
			itemXianGouDAO.insertItemXianGouDO(itemXianGouDO);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testGetItemXianGouDOById() {
		Long id = 4L;
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		try {
			itemXianGouDO = itemXianGouDAO.getItemXianGouDOById(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		System.out.println(itemXianGouDO.getItemTitle());
		
	}
	
	@Test
	public void testGetItemXianGouDOByItemId() {
		Long itemId = 555L;
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		try {
			itemXianGouDO = itemXianGouDAO.getItemXianGouDOByItemId(itemId);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		System.out.println(itemXianGouDO.getItemTitle());
		
	}
	
	@Test
	public void testUpdateItemXianGouDO() {
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		itemXianGouDO.setId(4L);
		itemXianGouDO.setBuyCount(5);
		itemXianGouDO.setCount(20000L);
		itemXianGouDO.setStatus(1);
		boolean flag = false;
		try {
			flag = itemXianGouDAO.updateItemXianGouDO(itemXianGouDO);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		System.out.println(flag);
	}
	
	


}
