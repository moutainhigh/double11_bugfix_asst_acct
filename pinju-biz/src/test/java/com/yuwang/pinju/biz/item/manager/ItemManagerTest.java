/**
 * 
 */
package com.yuwang.pinju.biz.item.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.domain.item.ItemDO;

/**
 * @Project: pinju-biz
 * @Title: ItemManagerTest.java
 * @Package com.yuwang.pinju.biz.item.manager
 * @Description: itemManager Test
 * @author liuboen liuboen@zba.com
 * @date 2011-6-9 上午10:48:16
 * @version V1.0
 */

public class ItemManagerTest extends BaseTestCase {
	@SpringBean("itemManager")
	private ItemManager itemManager;
	
	public void testItemManager(){
		updateItemStoreCategories();
	}

	public void updateItemStoreCategories() {
		Map<Long, String> m = new HashMap<Long, String>();
		m.put(4185l, "11,22");
		m.put(4169l, "33,44");
		try {
			itemManager.updateItemStoreCategories(m);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}

	public void getItemListByList() {
		List<Long> ids = new ArrayList<Long>();
		// ids.add(13l);
		ids.add(17l);
		ids.add(18l);
		ids.add(19l);
		ids.add(20l);
		ids.add(21l);
		try {
			List<ItemDO> itemList = itemManager.getItemListByIds(ids);
			for (ItemDO itemDO : itemList) {
				System.out.println(itemDO.getTitle());
			}
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
}
