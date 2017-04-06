/**
 * 
 */
package com.yuwang.pinju.core.shop.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * @author liyouguo
 * 
 */
public interface ShopPagePrototypeManager {
	/**
	 * 获取所有待编辑的页面
	 * 
	 * @return
	 */
	List<ShopPagePrototypeDO> getAllDesignerPage() throws ManagerException;

	/**
	 * 获取指定类型的页面
	 * 
	 * @param pageId
	 * @return
	 * @throws ManagerException
	 */
	ShopPagePrototypeDO queryPageProtoByPageid(Integer pageId)
			throws ManagerException;
}
