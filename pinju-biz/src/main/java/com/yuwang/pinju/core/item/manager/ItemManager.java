/**
 * 
 */
package com.yuwang.pinju.core.item.manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemAndOtherVO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemPicDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.item.SkuDO;

/**
 * @author yejingfeng
 * @since 2011-5-30 商品管理类，管理前台商品发布、上下架、删除，以及仓库中商品查询。
 */
public interface ItemManager {

	/**
	 * 根据编号上架商品
	 * 
	 * @author liming
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public String addShelfById(long id, long type) throws ManagerException;

	/**
	 * 创建商品SKU
	 * 
	 * @author liming
	 * @param skuAttr
	 *            sku字符串
	 * @param isVerify
	 *            是否验证
	 * @return
	 * @throws ManagerException
	 */
	public void createItemSku(List<SkuDO> skuList, long itemId, long sellId, boolean isVerify) throws ManagerException;

	/**
	 * 创建商品自定义SKU
	 * 
	 * @author caijunjie
	 * @param skuAttr
	 *            sku字符串
	 * @param isVerify
	 *            是否验证
	 * @return
	 * @throws ManagerException
	 */
	public void createItemCustomSku(List<CustomProValueDO> customSkuList, long itemId, long sellId, boolean isVerify) throws ManagerException;

	/**
	 * 创建商品自定义SKU
	 * 
	 * @author caijunjie
	 * @param skuDO
	 * @return
	 * @throws ManagerException
	 */
	public long createItemCustomSku(CustomProValueDO customProValueDO) throws ManagerException;
	
	/**
	 * 创建商品SKU
	 * 
	 * @author liming
	 * @param skuDO
	 * @return
	 * @throws ManagerException
	 */
	public long createItemSku(SkuDO skuDO) throws ManagerException;

	/**
	 * 创建商品快照
	 * 
	 * @author liming
	 * @param itemSnapshotDO
	 * @return
	 * @throws ManagerException
	 */
	public long createItemSnapshot(ItemDO itemDO) throws ManagerException;

	/**
	 * 删除商品店铺分类
	 * 
	 * @param sellerId
	 *            卖家编号
	 * @param ids
	 *            需要删除的类型编号集合
	 * @return
	 * @throws ManagerException
	 */
	public int deleteItemStoreCategories(Long sellerId, List<String> ids) throws ManagerException;

	/**
	 * 根据编号删除商品
	 * 
	 * @author liming
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public int delItemById(long id, long type) throws ManagerException;

	/**
	 * 根据编号下架商品
	 * 
	 * @author liming
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public int delShelfById(long id, long type) throws ManagerException;

	/**
	 * 通过商品ID获取单条商品信息(未判断状态状态,为空返回null)
	 * 
	 * @param id
	 * @author liuboen
	 * @return
	 * @throws ManagerException
	 */
	public ItemDO getItemDOById(long id) throws ManagerException;

	/**
	 * 获得商品列表
	 * 
	 * @author liming
	 * @param m
	 *            过滤参数
	 * @param pageInt
	 *            页码
	 * @return
	 * @throws ManagerException
	 */
	public List<ItemDO> getItemList(ItemQuery itemQuery) throws ManagerException;

	/**
	 * 获得商品列表
	 * 
	 * @author liming
	 * @param userId
	 *            用户编号
	 * @param type
	 *            类型
	 * @return
	 * @throws ManagerException
	 */
	public List<ItemDO> getItemList(long sellerId) throws ManagerException;

	/**
	 * 根据多个商品编号获取商品列表，最多20个商品编号
	 * 
	 * @author yejingfeng
	 * @param ids
	 * @return List<ItemDO>
	 * @throws ManagerException
	 */
	public List<ItemDO> getItemListByIds(List<Long> ids) throws ManagerException;
	/**
	 * 根据多个商品编号获取商品列表，最多20个商品编号(逗号隔开)
	 * <p>读库</p>
	 * @author liuboen
	 * @param ids
	 * @return List<ItemDO>
	 * @throws ManagerException
	 */
	public List<ItemDO> getReadSimpleItemListByIds(String ids,long sellerId) throws ManagerException;

	/**
	 * 获得商品数量
	 * 
	 * @author liming
	 * @param itemQuery
	 * @return
	 * @throws ManagerException
	 */
	public int getItemListCount(ItemQuery itemQuery) throws ManagerException;

	/**
	 * 根据商品编号 获得最新商品快照编号
	 * 
	 * @param itemId
	 * @return
	 * @throws ManagerException
	 */
	public long getItemSnapshotIdByItemId(Long itemId) throws ManagerException;

	/**
	 * 商品发布
	 * 
	 * @author liming
	 * @param itemItemDO
	 * @throws SQLException
	 */

	public long itemReleased(ItemDO itemDO, List<SkuDO> skuList, List<ItemPicDO> itemPicList, List<CustomProValueDO> customSkuList) throws ManagerException;

	/**
	 * 
	 * 商品更新
	 * 
	 * @author liming
	 * @param itemDO
	 * @throws ManagerException
	 */
	public void itemUpdate(ItemDO itemDO, List<SkuDO> skuList, List<ItemPicDO> itemPicList, List<CustomProValueDO> customSkuList) throws ManagerException;

	/**
	 * 获取商品列表（提供店铺、分销[分销商]调用）
	 * 
	 * @author liyouguo
	 * @param itemQuery
	 * @return
	 * @throws ManagerException
	 */
	public List<ItemDO> queryItemListEx(ItemQueryEx itemQuery) throws ManagerException;

	/**
	 * 更新商品
	 * 
	 * @author liming
	 * @param itemVO
	 * @return
	 * @throws ManagerException
	 */
	public int updateItem(ItemDO itemDO) throws ManagerException;

	/**
	 * 更新商品SKU
	 * 
	 * @param skuList
	 * @throws ManagerException
	 */
	public void updateItemSku(List<SkuDO> skuList) throws ManagerException;

	/**
	 * 更新商品SKU
	 * 
	 * @param skuDO
	 * @return
	 * @throws ManagerException
	 */
	public long updateItemSku(SkuDO skuDO) throws ManagerException;

	/**
	 * 更新商品店铺分类
	 * 
	 * @param temps
	 *            分类Map<商品编号,分类字符串(各分类编号逗号(,)分隔)>
	 * @return
	 * @throws ManagerException
	 */
	public int updateItemStoreCategories(Map<Long, String> temps) throws ManagerException;

	/**
	 * 通过运费模板下架商品
	 * 
	 * @param freightId
	 * @return
	 * @throws ManagerException
	 */
	public int shelvesItemByFreight(long freightId) throws ManagerException;

	/**
	 * 通过不同业务修改features
	 * @author liuboen
	 * @param itemId
	 * @param ItemFeaturesEnum
	 * @param toValue
	 *            (为空或为""时为将该Feature删除)
	 * @return Result(error获取:errorMessage)
	 * @throws ManagerException
	 */
	public Result setFeatures(long itemId, ItemFeaturesEnum featureEnum, String toValue) throws ManagerException;

	/**
	 * 是否存在店铺类型
	 * 
	 * @param sellerId
	 *            卖家编号
	 * @param storeCategoriesId
	 *            店铺分类编号（单一）
	 * @param status
	 *            商品状态
	 * @return
	 * @throws ManagerException
	 */
	public boolean hasStoreCategories(long sellerId, String storeCategoriesId, List<Long> status)
			throws ManagerException;
	
	/**
	 * 扣库存
	 * 
	 * @author liuboen
	 * @param itemId
	 * @param skuId
	 * @param num
	 *            数量
	 * @return Result(error获取:errorMessage)
	 * @throws ManagerException
	 */
	public Result cutStock(long itemId, Long skuId, long num)
			throws ManagerException;
	
	/**
	 * 该运费模版是否存在商品
	 * 
	 * @param freeTemplateId
	 *            运费模版编号
	 * @return
	 * @throws ManagerException
	 */
	public boolean getItemCountByfreeTemplateId(long freeTemplateId)throws ManagerException;

	/**
	 * 获取读库的商品
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	ItemDO getReadItemDOById(long id) throws ManagerException;

	@Deprecated
	List<ItemAndOtherVO> queryItemListExAndOtherInfo(ItemQueryEx itemQuery)
			throws ManagerException;

	List<Map<String, Object>> getSimpleItemMaps(List<ItemDO> itemList)
			throws ManagerException;
	
	/**
	 * 通过ID取得单条商品信息
	 * 
	 * @author 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public ItemDO selectItemDObyId(long id) throws ManagerException;
	
	/**
	 * 获取商品标题记录日志
	 */
	public String getItemTitles(ItemQueryEx itemQuery) throws ManagerException;
}
