/**
 * 
 */
package com.yuwang.pinju.core.shop.ao;

import java.util.List;

import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * @author liyouguo
 * 
 */
public interface ShopPagePrototypeAO {

	/**
	 * 获取所有待编辑的页面
	 * 
	 * @return
	 */
	List<ShopPagePrototypeDO> getAllDesignerPage();

	/**
	 * 获取指定类型的页面
	 * 
	 * @param pageId
	 * @return
	 * @throws Exception
	 */
	ShopPagePrototypeDO queryPageProtoByPageid(Integer pageId) throws Exception;
}
