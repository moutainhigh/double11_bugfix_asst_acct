package com.yuwang.pinju.core.shop.dao.impl;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;


import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;

/**
 * 店铺商品分类数据接口测试
 * 
 * @author mike
 *
 * @since 2011-5-31
 */
public class ShopCategoryTest  extends BaseTestCase
{
	
    @SpringBean("shopCategoryDAO")
    private ShopCategoryDAOImpl shopCategoryDAO;
    private ShopCategoryDO shopCategoryDO;
    
    /**
     * 测试增加店铺分类
     */
    public void InsertShopCategory()throws Exception{
    	shopCategoryDO=constructShopCategory();
    	shopCategoryDAO.insertShopCategroy(shopCategoryDO);
    }
    
    
    /**
     * 测试通过店铺id查询店铺商品分类
     * 
     * @throws Exception
     */
    public void QueryCategory()throws Exception{
    	ShopCategoryDO shopDO=shopCategoryDAO.queryShopCategory(1,null);
    	System.out.println(shopDO.getSecondCategory());
    	System.out.println(shopDO.getCategoryConfig("衣服"));
    }
    
    /**
     * 构建一个商品分类
     * 
     * @return 商品分类
     */
    public ShopCategoryDO constructShopCategory(){
    	ShopCategoryDO  shopDO=new ShopCategoryDO();
    	shopDO.setUserId(1L);
    	shopDO.setFirstCategory("衣服,鞋子");
    	shopDO.setSecondCategory("衣服=短袖,裤子" + "\n"+"鞋子=皮鞋,休闲鞋");
    	shopDO.setShopId(1);
    	shopDO.setGmtCreate(new Date());
    	shopDO.setGmtModified(new Date());
    	return shopDO;
    }
}
