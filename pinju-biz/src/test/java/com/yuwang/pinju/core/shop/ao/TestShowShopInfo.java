
package com.yuwang.pinju.core.shop.ao;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class TestShowShopInfo extends Assert {
	/**
	 * 根据userid获取店铺基本信息
	 * @throws DaoException
	 */
	@Test public void testSaveShopInfo() throws DaoException{
		FileSystemXmlApplicationContext factory = new FileSystemXmlApplicationContext("/src/test/resources/applicationContext.xml");
		ShopShowInfoManager shopShowInfoManager = (ShopShowInfoManager)factory.getBean("shopShowInfoManager");
		long userId = 5001;
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId,null);

		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
}


