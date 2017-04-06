package com.yuwang.pinju.core.images.jsonTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.image.ao.ImagesCategoryAO;
import com.yuwang.pinju.core.image.manager.StorageFileInfoManager;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;

public class JsonTest extends BaseTestCase{
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	@SpringBean("imagesCategoryAO")
	private ImagesCategoryAO imagesCategoryAO;
	
	@SpringBean("storageFileInfoManager")
	private StorageFileInfoManager storageFileInfoManager;
	@Test
	public void testgetImagesCategoryToJsonTest(){
		
		try {
			
			String jsonString = imagesCategoryAO.getImagesCategoryToJson(100000325009000L);
			log.info("jsonString = " + jsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testgetStorageFileInfoListByConToJsonTest(){
		
		try {
			StorageFileInfoDO storageFileInfoDO = new StorageFileInfoDO();
			storageFileInfoDO.setStartRow(0);
			storageFileInfoDO.setPageCount(20);
			String jsonString = storageFileInfoManager.getStorageFileInfoListByConToJson(storageFileInfoDO);
			log.info("jsonString = " + jsonString);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
