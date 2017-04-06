/**
 * ItemXianGouUseDAOTest
 */
package com.yuwang.pinju.core.sales;

import java.util.List;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.sales.dao.ItemXianGouUseDAO;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;
import com.yuwang.pinju.domain.sales.query.ItemXianGouUseQuery;

/**  
 * @Project: crm-biz
 * @Discription: [class discription]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-30 下午07:21:21
 * @update 2011-8-30 下午07:21:21
 * @version V1.0  
 */
public class ItemXianGouUseDAOTest extends BaseTestCase{
	
	@SpringBean("itemXianGouUseDAO")
	private ItemXianGouUseDAO itemXianGouUseDAO;
	
	@Test
	public void testInsertItemXianGouUseRecord(){
		ItemXianGouUseDO itemXianGouUseDO = new ItemXianGouUseDO();
		itemXianGouUseDO.setId(1111L);
		itemXianGouUseDO.setXianGouId(2222L);
		itemXianGouUseDO.setCode(2222L);
		itemXianGouUseDO.setBuyerId(3333L);
		itemXianGouUseDO.setBuyerNick("3333Nick");
		itemXianGouUseDO.setSellerId(4444L);
		itemXianGouUseDO.setSellerNick("4444Nick");
		itemXianGouUseDO.setItemId(5555L);
		itemXianGouUseDO.setItemTitle("5555Name");
		itemXianGouUseDO.setIsUse(0);
		itemXianGouUseDO.setVersion(0000L);
		try{
			assertNotNull(itemXianGouUseDO);
			itemXianGouUseDAO.insertItemXianGouUseRecord(itemXianGouUseDO);
			assertNotNull(itemXianGouUseDO.getId());
			System.out.println("ID:"+itemXianGouUseDO.getId());
		}catch(DaoException e){
			System.out.println(e.getStackTrace());
		}
	}
	
	@Test
	public void testUpdateItemXianGouUseRecord(){
		ItemXianGouUseDO itemXianGouUseDO = new ItemXianGouUseDO();
		itemXianGouUseDO.setCode(4156060194500L);
		itemXianGouUseDO.setIsUse(1);
		itemXianGouUseDO.setVersion(9999L);
		itemXianGouUseDO.setBuyerId(11111L);
		itemXianGouUseDO.setBuyerNick("11111nick");
		itemXianGouUseDO.setSellerId(4444L);
		itemXianGouUseDO.setSellerNick("4444nick");
		itemXianGouUseDO.setItemId(6666L);
		itemXianGouUseDO.setItemTitle("6666nick");
		try{
			assertNotNull(itemXianGouUseDO);
			itemXianGouUseDAO.updateItemXianGouUseRecord(itemXianGouUseDO);
			System.out.println("修改后活动版本号:"+itemXianGouUseDO.getVersion());
			System.out.println("修改后卖家昵称:"+itemXianGouUseDO.getSellerNick());
			System.out.println("修改后买家昵称:"+itemXianGouUseDO.getBuyerNick());
		}catch(DaoException e){
			System.out.println(e.getStackTrace());
		}
	}
	
	@Test
	public void testGetItemXianGouUseByCode(){
		try{
			ItemXianGouUseDO itemXianGouUseDO = itemXianGouUseDAO.getItemXianGouUseDOByCode(415L);
			System.out.println("是否使用:"+itemXianGouUseDO.getIsUse());
			System.out.println("买家昵称:"+itemXianGouUseDO.getBuyerNick());
			System.out.println("卖家昵称:"+itemXianGouUseDO.getSellerNick());
			System.out.println("商品名称:"+itemXianGouUseDO.getItemTitle());
		}catch(DaoException e){
			System.out.println(e.getStackTrace());
		}
	}
	
	@Test
	public void testGetItemXianGouUseDOs(){
//		ItemXianGouUseDO itemXianGouUseDO = new ItemXianGouUseDO();
//		itemXianGouUseDO.setBuyerNick("3333昵称");
		ItemXianGouUseQuery itemXianGouUseQuery = new ItemXianGouUseQuery();
//		itemXianGouUseQuery.setItemXianGouUseDO(itemXianGouUseDO);
		itemXianGouUseQuery.setBuyerNick("3333Nick");
		try{
			assertNotNull(itemXianGouUseQuery);
			List<ItemXianGouUseDO> itemXianGouUseDOs = (List<ItemXianGouUseDO>)itemXianGouUseDAO.getItemXianGouUseDOs(itemXianGouUseQuery);
			assertNotNull(itemXianGouUseDOs);
			System.out.println("限购码领用记录数："+itemXianGouUseDOs.size());
		}catch(DaoException e){
			System.out.println(e.getStackTrace());
		}
	}

}
