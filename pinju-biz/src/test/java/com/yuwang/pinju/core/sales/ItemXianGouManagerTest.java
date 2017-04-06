package com.yuwang.pinju.core.sales;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.sales.manager.ItemXianGouManager;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;

public class ItemXianGouManagerTest extends BaseTestCase{
	
	@SpringBean("itemXianGouManager")
	private ItemXianGouManager itemXianGouManager;
	
	@Test
	public void testAddItemXianGouDO() {
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		Date date = new Date();
		itemXianGouDO.setItemId(5555L);
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
			itemXianGouManager.addItemXianGouDO(itemXianGouDO);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testGetItemXianGouDOById() {
		Long id = 4L;
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		try {
			itemXianGouDO = itemXianGouManager.getItemXianGouDOById(id);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
		System.out.println(itemXianGouDO.getItemTitle());
		
	}
	
	@Test
	public void testGetItemXianGouDOByItemId() {
		Long itemId = 555L;
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		try {
			itemXianGouDO = itemXianGouManager.getItemXianGouDOByItemId(itemId);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
		System.out.println(itemXianGouDO.getItemTitle());
		
	}
	
	@Test
	public void testUpdateItemXianGouDO() {
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		itemXianGouDO.setId(4L);
		itemXianGouDO.setBuyCount(5);
		itemXianGouDO.setCount(888880000L);
		itemXianGouDO.setStatus(1);
		boolean flag = false;
		try {
			flag = itemXianGouManager.updateItemXianGouDO(itemXianGouDO);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
		System.out.println(flag);
	}
	
	
	
	
}
