/**
 * 
 */
package com.yuwang.pinju.core.item.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;

/**  
 * @Project: pinju-biz
 * @Title: CategoryProManager.java
 * @Package com.yuwang.pinju.core.item.manager
 * @Description: 类目属性和属性值manager
 * @author liuboen liuboen@zba.com
 * @date 2011-6-8 下午04:01:27
 * @version V1.0  
 */

public interface CategoryProManager {
	/**
	 * 通过商品属性对获取商品属性和属性值(直接查询数据库)
	 * @return
	 * @param cateProMap 属性1:属性值1,属性值2; 属性:属性值1,属性值2 形式
	 * @author liuboen
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> getItemCateProAndValueByIdMap(String cateProMap)throws ManagerException;

	/**
	 * 通过类目属性ID 获取类目属性值
	 * @param cateProId
	 * @return
	 * @throws ManagerException
	 */
	public CategoryPropertyDO getCategoryPropertyByCateProId(long cateProId)throws ManagerException;
	
	public BaseValueDO getBaseValueDOByBaseValueId(long baseValueId)throws ManagerException;
}
