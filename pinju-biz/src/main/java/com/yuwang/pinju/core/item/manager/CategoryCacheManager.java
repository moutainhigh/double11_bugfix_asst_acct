/**
 * 
 */
package com.yuwang.pinju.core.item.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.BrandDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CpValueRelationDO;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SpuDO;

/**
 * @Project: pinju-biz
 * @Title: CategoryCacheManager.java
 * @Package com.yuwang.pinju.core.item.manager
 * @Description: 默认的类目相关缓存
 * @author liuboen liuboen@zba.com
 * @date 2011-6-24 上午10:57:03
 * @version V1.0
 */

public interface CategoryCacheManager {

	/**
	 * 根据编号 获得缓存类目(local)(树形类目,大数据)
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public CategoryDO getForestCategoryDOById(long id) throws ManagerException;
	
	/**
	 * 根据编号 获得缓存类目(local)(simple 类目对象)
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public CategoryDO getCategoryDOById(long id) throws ManagerException;
	
	/**
	 * 通过父类目编号 获得类目列表
	 * @param parentId
	 * @return
	 * @throws ManagerException
	 */
	public List<CategoryDO> getItemCategoryListByParentId(long parentId) throws ManagerException;
	/**
	 * 获取当前类目层级(树形类目,大数据)
	 * @param parentId
	 * @return
	 * @throws ManagerException
	 */
	public List<CategoryDO> getCategoryParentLevel(long parentId) throws ManagerException;

	/**
	 * 获取根类目列表
	 * @param parentId
	 * @return
	 * @throws ManagerException
	 */
	public List<CategoryDO> getRootCategoryList() throws ManagerException;
	
	
	/**
	 * 加载当天所有的类目信息
	 * @return
	 * @throws ManagerException
	 */
	public boolean loadFullCategory()throws ManagerException;
	/**
	 * 重置所有的类目信息
	 * @return
	 * @throws ManagerException
	 */
	public boolean resetFullCategory()throws ManagerException;
	/**
	 * 增量更新
	 * @return
	 * @throws ManagerException
	 */
	public boolean incrLoadCategory()throws ManagerException;
	/**
	 * 根据类目编号 获得缓存类目属性列表
	 * 
	 * @param categoryId
	 * @return
	 * @throws ManagerException
	 */
	public List<CategoryPropertyDO> getItemCategoryProperty(long categoryId) throws ManagerException;

	/**
	 * 根据类目编号 获得缓存类目属性
	 * 
	 * @param categoryId
	 * @return
	 * @throws ManagerException
	 */
	public CategoryPropertyDO getItemCategoryPropertyById(long id) throws ManagerException;

	/**
	 * 根据类目属性 获得缓存属性值列表
	 * 
	 * @param categoryPropertyId
	 * @return
	 * @throws ManagerException
	 */
	public List<CategoryPropertyValueDO> getItemCategoryPropertyValue(long categoryPropertyId) throws ManagerException;

	/**
	 * 通过值编号 获得缓存值对象
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public BaseValueDO getBaseValueById(long id) throws ManagerException;

	/**
	 * 根据父子关系编号 获得关系集合
	 * 
	 * @param cpId
	 * @param sonCpId
	 * @return
	 * @throws ManagerException
	 */
	public List<CpValueRelationDO> getItemCpValueRelation(long cpId, long sonCpId) throws ManagerException;

	/**
	 * 根据类目属性编号 获得管理集合
	 * 
	 * @param cpId
	 * @return
	 * @throws DaoException
	 */
	public CpValueRelationDO getItemCpValueRelationByCpIdAndCpvId(long cpId, long cpvId) throws ManagerException;

	/**
	 * 根据类目属性编号 获得管理集合
	 * 
	 * @param cpId
	 * @return
	 * @throws DaoException
	 */
	public List<CpValueRelationDO> getItemCpValueRelationByCpId(long cpId) throws ManagerException;

	/**
	 * 根据编号 获得品牌对象
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public BrandDO getItemBrandById(long id) throws ManagerException;
		

	/**
	 * 获取spu 的信息
	 * @param spuid
	 * @return
	 * @throws ManagerException 
	 */
	SpuDO getItemCategorySpubyId(long spuid) throws ManagerException;

	
	/**
	 * 当缓存存在时覆盖该缓存,没有时不做任何操作
	 * @param itemDO
	 * @return
	 */
	public boolean resetItemDOCache(ItemDO itemDO)throws ManagerException;
	
	/**
	 * 获取商品信息
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public  ItemDO getItemDOById(long id)throws ManagerException;
	
	/**
	 * 获取商品限时折扣活动信息
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public  ActivityDiscountDO getActivityDiscountDOId(long id)throws ManagerException;

	/**
	 * 删除限时折扣缓存
	 * @param activityDiscountDO
	 * @return
	 * @throws ManagerException
	 */
	boolean deleteActivityDiscountDOCache(long actId)
			throws ManagerException;

	/**
	 * 删除商品缓存
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	boolean deleteItemDOCache(long id) throws ManagerException;

	/**
	 * 获取自定义属性缓存
	 * @param itemId
	 * @param cateProID
	 * @param valueID
	 * @return
	 * @throws ManagerException
	 */
	public CustomProValueDO getCustomProValueDO(long itemId,long cateProID,long valueID)throws ManagerException;
	/**
	 * 删除自定义属性缓存
	 * @param itemId
	 * @return
	 * @throws ManagerException
	 */
	public boolean deleteCustomProValueDO(long itemId)throws ManagerException;
}
