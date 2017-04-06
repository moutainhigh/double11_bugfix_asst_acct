package com.yuwang.pinju.biz.item;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.ItemSnapshotDAO;
import com.yuwang.pinju.domain.item.ItemSnapshotDO;

public class ItemSnapshotTest extends BaseTestCase {

	protected Log log = LogFactory.getLog(this.getClass());

	@SpringBean("itemSnapshotDao")
	private ItemSnapshotDAO itemSnapshotDao;

	public void itemSnapshotDao() {

		ItemSnapshotDO itemSnapshotDO = new ItemSnapshotDO();
		itemSnapshotDO.setItemId(1l);
		itemSnapshotDO.setCatetoryId(2l);
		itemSnapshotDO.setSpuId(3l);
		itemSnapshotDO.setTitle("Title");
		itemSnapshotDO.setStoreCategories("4");
		itemSnapshotDO.setDescription("DiscriptionUrl");
		itemSnapshotDO.setPropertyValuePair("aaa:10;bbb:10,20;");
		itemSnapshotDO.setItemType(5);
		itemSnapshotDO.setSellerId(100000055000000l);
		itemSnapshotDO.setPicUrl("PicUrl");
		itemSnapshotDO.setPicColor(6);
		itemSnapshotDO.setPrice(7l);
		itemSnapshotDO.setProvinces("Provinces");
		itemSnapshotDO.setCity("City");
		itemSnapshotDO.setDeliveryCosts(8l);

		itemSnapshotDO.setMailCosts(9l);
		itemSnapshotDO.setEmsCosts(10l);
		//itemSnapshotDO.setFreeTemplates("FreeTemplates");

		Date d = new Date();
		itemSnapshotDO.setStartTime(d);
		itemSnapshotDO.setEndTime(d);

		itemSnapshotDO.setItemDegree(11);
		itemSnapshotDO.setRecommendedStatus(12);
		itemSnapshotDO.setCollectionNumber(13l);

		itemSnapshotDO.setFeatures("Features");

		itemSnapshotDO.setExpiredDate(7l);
		itemSnapshotDO.setLastModified(d);
		itemSnapshotDO.setGmtCreate(d);
		itemSnapshotDO.setGmtModified(d);

		itemSnapshotDO.setCode("Code");

		try {

			long id = itemSnapshotDao.insertItemItemSnapshot(itemSnapshotDO);
			log.debug("结果：" + id);

		} catch (DaoException e) {
			e.printStackTrace();
		}

	}

	public void testItemSnapshot() {
		// itemSnapshotDao(); //dao测试
	}

}
