package com.yuwang.pinju.core.item.ao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.SearchCategoryResult;

public interface CategoryAO {

	/**
	 * 获得子类目属性
	 * 
	 * @param cpId
	 * @param cpvId
	 * @return
	 */
	public Map getSonPro(String cpId, String cpvId);

	/**
	 * 获得商品SKU
	 * 
	 * @param itemId
	 * @return
	 */
	public Map getItemSku(String itemId);

	/**
	 * 通过类目ID获得类目层级
	 * cateLevel1--
	 * 				cateLevel
	 * @return
	 */
	public SearchCategoryResult getCategoryLevelByCateId(long cateId);
	
	/**
	 * 通过类目Id查找类目
	 * @param cateId
	 * @return
	 */
	public Result sercheCategoryDOInfo(String cateIdStr);
	
	/**
	 * 通过类目ID获得所有父节点信息 第n+1个元素 是第n个元素的父节点信息
	 * @param cateId 类目id
	 * @return
	 */
	public List<CategoryDO> getCategoryParentsByCateId(long cateId);
}
