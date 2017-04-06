package com.yuwang.pinju.biz.item;

import java.util.Calendar;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.item.ao.ItemListAO;
import com.yuwang.pinju.core.item.ao.ItemAO;
import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.core.item.manager.impl.ItemManagerImpl;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemInput;

public class ItemReleaseTest extends BaseTestCase {

	@SpringBean("itemManager")
	private ItemManagerImpl itemManager;
	@SpringBean("categoryManager")
	private CategoryManager categoryManager;
	@SpringBean("itemReleasedAO")
	private ItemAO itemReleasedAO;
	@SpringBean("itemListAO")
	private ItemListAO itemListAO;

	/**
	 * 插入商品测试
	 */
	public void itemReleaseTest() throws Exception {

		ItemInput itemVO = new ItemInput();

		Calendar c = Calendar.getInstance();

		itemVO.setCatetoryId(1l);
		itemVO.setSellerId(100000055000000l);

		itemVO.setItemDegree(ItemConstant.DEGREE_TYPE_1);
		itemVO.setPropertyValuePair("1:10;");
		
		itemVO.setTitle("test" + c.getTime());
		itemVO.setPrice("22222.22");
		itemVO.setCode("dfdfdd");
		itemVO.setNumber("1");
		itemVO.setDescription("<div>111</div>");
		itemVO.setProvinces("上海");
		itemVO.setCity("上海");

		itemVO.setFreightType(ItemConstant.FREIGHT_TYPE_2);
		itemVO.setMailCosts("12345678");

		itemVO.setExpiryType(ItemConstant.EXPIRY_TYPE_1);
		itemVO.setReleasedType(1);

		//itemReleasedAO.itemReleased(itemVO);

	}

	/**
	 * 删除商品
	 * 
	 * @throws Exception
	 */
	public void userDelItemTest() throws Exception {
		Long ids[] = { 17l, 18l, 19l };
		itemListAO.userDelItem(ids);
		itemManager.delItemById(20l, ItemConstant.STATUS_TYPE_5);
	}

	public void testTest() {
		try {

			itemReleaseTest();
			// userDelItemTest();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获得类目列表
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testGetItemCategoryList() throws DaoException {

		// try {
		// categoryManager.getItemCategoryList(27);
		// } catch (ManagerException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
