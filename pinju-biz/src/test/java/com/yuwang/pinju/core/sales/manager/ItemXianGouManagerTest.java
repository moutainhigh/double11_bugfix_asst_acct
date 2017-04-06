/**
 * ItemXianGouUseDAOTest
 */
package com.yuwang.pinju.core.sales.manager;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;

/**  
 * @Project: crm-biz
 * @Discription: [class discription]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-30 下午07:21:21
 * @update 2011-8-30 下午07:21:21
 * @version V1.0  
 */
public class ItemXianGouManagerTest extends BaseTestCase{
	
	@SpringBean("itemXianGouManager")
	private ItemXianGouManager itemXianGouManager;
	
	@Test
	public void testUpdateItemXianGouUseRecord(){
		ItemXianGouUseDO itemXianGouUseDO = new ItemXianGouUseDO();
		itemXianGouUseDO.setCode(2222L);
		itemXianGouUseDO.setIsUse(1);
		itemXianGouUseDO.setVersion(1234L);
		try{
			assertNotNull(itemXianGouUseDO);
			itemXianGouManager.updateItemXianGouUse(itemXianGouUseDO);
			System.out.println("修改后活动版本号:"+itemXianGouUseDO.getVersion());
		}catch(ManagerException e){
			System.out.println(e.getStackTrace());
		}
	}

}
