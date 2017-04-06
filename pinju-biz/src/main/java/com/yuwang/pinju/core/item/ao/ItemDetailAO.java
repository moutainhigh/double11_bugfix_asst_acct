/**
 * 
 */
package com.yuwang.pinju.core.item.ao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemDetailResult;
import com.yuwang.pinju.domain.item.ItemTagMetaInfo;
import com.yuwang.pinju.domain.item.SpuDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;


/**  
 * @Project: pinju-biz
 * @Title: ItemDetailAo.java
 * @Package com.yuwang.pinju.core.item.ao
 * @Description: 商品详情AO
 * @author liuboen liuboen@zba.com
 * @date 2011-6-4 下午03:34:46
 * @version V1.0  
 */

public interface ItemDetailAO {

	/**
	 * 通过商品ID获取商品详情
	 * @param id
	 * @return
	 */
	public ItemDO getItemDOById(Long id);
	
	/**
	 * 获取商品类目信息
	 * @param cateId
	 * @return
	 */
	public CategoryDO getCategoryDOForestByCateId(Long cateId);
	/**
	 * 获取类目属性
	 * @param cateProMap
	 * @return
	 */
	public List <Map<String, Object>> getItemCategoryPro(String cateProMap);
	
	/**
	 * @param spuid
	 * @return
	 */
	SpuDO getItemCategorySpubyId(long spuid);
	
	/**
	 * 获取商品SKU
	 * @param itemId
	 * @param detailQuery
	 * @return
	 */
	ItemDetailResult getSkuByItemId(ItemDO itemDO,ItemDetailResult detailQuery);

	/**
	 * 获取店铺相关
	 * @param userId
	 * @param detailQuery
	 * @return
	 */
	ItemDetailResult getShopInfoByUserId(long userId,ItemDetailResult detailQuery);
	
	/**
	 *  重置本地类目库
	 * @return
	 */
	public boolean resetFullCategory();
	
	/**
	 * 获取区域信息
	 * @param cityIp
	 * @return
	 */
	public  Map<String, String>   getLogisticsCityByIp(Long logisticsTemplateId,String cityIp);
	
	/**
	 * 获取限时折扣信息
	 * @param disid
	 * @return
	 */
	public ActivityDiscountDO getActivityDiscountDOById(String disid);
	
	/**
	 * 获取购买记录
	 * @param disid
	 * @return
	 */
	public List<OrderItemDO> getBuyOrderItemDOListById(QueryOrderItem queryOrderItemDO);
	
	/**
	 * 通过商品ID获取简单商品对象list
	 * 如果没有数据则取商品库几条
	 * <p>ids以逗号隔开</p>
	 * @param ids
	 * @return
	 */
	public List<Map<String, Object>> getSimpleItemDOsByids(String ids,long sellerId);
	
	/**
	 * 通过商品ID获取标签及meta信息
	 * @param itemDO
	 * @return
	 */
	public ItemTagMetaInfo getItemTagMetaInfo(ItemDO itemDO);
}
