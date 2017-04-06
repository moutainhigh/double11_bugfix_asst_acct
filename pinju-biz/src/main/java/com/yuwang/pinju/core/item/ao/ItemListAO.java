package com.yuwang.pinju.core.item.ao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;

/**
 * 
 * 商品列表AO
 * 
 * @author liming
 *@since 2011-5-30
 */
public interface ItemListAO {

	/**
	 * 获得商品列表
	 * 
	 * @param itemQuery
	 */
	public List<ItemDO> getItemList(ItemQuery itemQuery);
	
	/**
	 * 获得商品列表FORAPI
	 * 
	 * @param itemQuery
	 */
	public List<ItemDO> getItemListForAPI(ItemQuery itemQuery);

	/**
	 * 用户上架商品
	 * 
	 * @param ids
	 * @return
	 */
	public List<String> userAddShelfItem(Long ids[]);

	/**
	 * 用户删除商品
	 * 
	 * @param ids
	 * @return
	 */
	public int userDelItem(Long ids[]);

	/**
	 * 用户下架商品
	 * 
	 * @param ids
	 * @return
	 */
	public int userDelShelfItem(Long ids[]);

	/**
	 * 验证商品状态是否可进行操作
	 * @param ids 商品ID
	 * @param operation 操作名称
	 * @param sellerId 用户ID
	 * @return
	 */
	public Map<Long, String> itemStatusValidatorForAPI(Long[] ids, String operation, Long sellerId);

	/**
	 * 获取商品标题记录日志
	 */
	public String getItemTitles(Long[] ids);
	
}
