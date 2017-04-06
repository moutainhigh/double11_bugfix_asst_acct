package com.yuwang.pinju.core.shop.manager.impl;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;

/**
 * 店铺商品分类管理接口测试
 * 
 * @author mike
 *
 * @since 2011-6-1
 */
public class ShopCategoryManagerTest extends BaseTestCase {
		@SpringBean("shopCategoryManager")
		private ShopCategoryManagerImpl shopCategoryManager;
		private ShopCategoryDO shopCategoryDO;
		
		
		@Test
	    /**
	     * 测试增加店铺分类
	     */
	    public void InsertShopCategory()throws Exception{
	    	shopCategoryDO=constructShopCategory();
	    	shopCategoryManager.insertShopCategroy(shopCategoryDO);
	    }
		
		
		@Test
	    /**
	     * 测试更新店铺分类
	     */
	    public void UpdateShopCategory()throws Exception{
	    	shopCategoryDO=constructShopCategory();
	    	shopCategoryManager.updateShopCategroy(shopCategoryDO);
	    }
		
		
		
	    /**
	     * 构建一个商品分类
	     * 
	     * @return 商品分类
	     */
	    public ShopCategoryDO constructShopCategory(){
	    	ShopCategoryDO  shopDO=new ShopCategoryDO();
	    	shopDO.setId(17);
	    	shopDO.setFirstCategory("衣服,鞋子");
	    	//shopDO.setSecondCategory("衣服=短袖,裤子;鞋子=皮鞋,休闲鞋;");
	    	shopDO.setConfiguration("衣服=短袖,裤子" + "\n"+"鞋子=皮鞋,休闲鞋");
	    	shopDO.setShopId(1);
	    	shopDO.setGmtCreate(new Date());
	    	shopDO.setGmtModified(new Date());
	    	return shopDO;
	    }
}
