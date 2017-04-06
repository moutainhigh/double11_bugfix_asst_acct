package com.yuwang.pinju.core.shop.ao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;

/**
 * 店铺商品分类封装
 * 
 * @author mike
 *
 * @since 2011-6-7
 */
public interface ShopCategoryAO {
	/**
	 * 通过店铺id查询对应的商品分类列表
	 * 
	 * @param shopId 店铺id
	 * 
	 * @return  店铺分类列表
	 */
	public  Map<String, ShopCategoryListDO>  queryShopCategoryList(Integer shopId)throws Exception;
	
	public List<ItemDO> queryCategoryGoods(String key, Integer shopId) throws Exception;
	
	
}
