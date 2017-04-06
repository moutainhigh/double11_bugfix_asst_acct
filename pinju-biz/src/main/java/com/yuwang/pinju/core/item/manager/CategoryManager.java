/**
 * 
 */
package com.yuwang.pinju.core.item.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CpValueRelationDO;
import com.yuwang.pinju.domain.item.SpuDO;

/**
 * @author liming
 * @since 2011-6-7 类目管理，包括类目列表查询、类目属性查询等功能。
 */
public interface CategoryManager {

	/**
	 * 获得类目名称列表
	 * 
	 * @param parentId
	 *            父类目编号
	 * @return 该父类目下所有类目名集合
	 * @throws ManagerException
	 */
	public List<CategoryDO> getItemCategoryList(long parentId) throws ManagerException;

	/**
	 * 根据编号获得类目对象
	 * 
	 * @param id
	 *            编号
	 * @return 类目对象
	 * @throws ManagerException
	 */
	public CategoryDO getItemCategory(long id) throws ManagerException;

	/**
	 * 获得类目属性
	 * 
	 * @param id
	 *            类目属性编号
	 * @return
	 * @throws ManagerException
	 */
	public List<CategoryPropertyDO> getItemCategoryProperty(long categoryId) throws ManagerException;

	/**
	 * 根据编号获得类目属性对象
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public CategoryPropertyDO getItemCategoryPropertyById(long id) throws ManagerException;

	/**
	 * 获得类目属性
	 * 
	 * @param categoryPropertyId
	 * @return
	 * @throws ManagerException
	 */
	public List<CategoryPropertyValueDO> getItemCategoryPropertyValue(long categoryPropertyId) throws ManagerException;

	/**
	 * 
	 * 获得类目基本编号
	 * 
	 * @param baseValueDO
	 * @return
	 * @throws ManagerException
	 */
	public Long getItemBaseValueByValue(String value) throws ManagerException;

	/**
	 * 获得SPU 根据关键字（属性编号，属性值编号）
	 * 
	 * @param keyPropertyId
	 * @param keyPropertyValue
	 * @return
	 * @throws ManagerException
	 */
	public Map getItemSpuByKey(Long keyPropertyId, Long keyPropertyValue) throws ManagerException;

	/**
	 * 获得SPU 根据编号
	 * 
	 * @param keyPropertyId
	 * @param keyPropertyValue
	 * @return
	 * @throws ManagerException
	 */
	public SpuDO getItemSpuById(Long spuId) throws ManagerException;

	/**
	 * 根据编号获得 基本值
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public BaseValueDO getBaseValueById(Long id) throws ManagerException;

	/**
	 * 根据值id列表获得基本值列表
	 * 
	 * @param propertyValueList
	 * @return
	 * @throws ManagerException
	 */
	public List getBaseValueNameByIds(List propertyValueList) throws ManagerException;

	/**
	 * 根据值id列表获得基本值MAP
	 * 
	 * @param propertyValueList
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, String> getBaseValueNameMapByIds(List propertyValueList) throws ManagerException;

	/**
	 * 根据父子编号 获得关系对象
	 * 
	 * @param cpId
	 * @return
	 * @throws DaoException
	 */
	public List<CpValueRelationDO> getItemCpValueRelation(long cpId, long sonCpId) throws ManagerException;
	
	/**
	 * 根据类目属性及属性值编号 获得關係集合
	 * @param cpId
	 * @param cpvId
	 * @return
	 * @throws ManagerException
	 */
	public CpValueRelationDO getItemCpValueRelationByCpIdAndCpvId(long cpId, long cpvId) throws ManagerException;

	/**
	 * 根据类书属性编号 获得管理集合
	 * 
	 * @param cpId
	 * @return
	 * @throws DaoException
	 */
	public List<CpValueRelationDO> getItemCpValueRelationByCpId(long cpId) throws ManagerException;

}
