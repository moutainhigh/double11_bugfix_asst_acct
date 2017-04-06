/**
 * 
 */
package com.yuwang.pinju.core.shop.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;

/**
 * 页面原型
 * @version 1.0
 * @author liyouguo
 * @created 17-六月-2011 10:39:44
 */
public interface ShopModulePrototypeDAO {
	
	/**
	 * 获取待编辑的页面的指定部分可添加的模块
	 * 
	 * @param 
	 * 			pageId 		--页面编号与表SHOP_PAGE_PROTOTYPE关联 
	 * 			type 		--页面的哪个部分（上，左，右，下）
	 * @return
	 */
	List<ShopModulePrototypeDO> getDesignerModuleByPage(ShopModulePrototypeDO shopModulePrototypeDO) throws DaoException;
}
