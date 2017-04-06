/**
 * 
 */
package com.yuwang.pinju.core.shop.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * 页面原型
 * @version 1.0
 * @author liyouguo
 * @created 17-六月-2011 10:39:44
 */
public interface ShopPagePrototypeDAO {
	
	/**
	 * 获取所有待编辑的页面
	 * @return
	 */
	List<ShopPagePrototypeDO> getAllDesignerPage() throws DaoException;
	
	/**
	 * 获取指定类型的页面
	 * @param pageId
	 * @return
	 * @throws DaoException
	 */
	ShopPagePrototypeDO queryPageProtoByPageid(Integer pageId) throws DaoException; 
}
