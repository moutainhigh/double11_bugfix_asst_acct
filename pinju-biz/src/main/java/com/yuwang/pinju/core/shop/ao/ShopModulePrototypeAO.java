/**
 * 
 */
package com.yuwang.pinju.core.shop.ao;

import java.util.List;

import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;

/**
 * @author liyouguo
 *
 */
public interface ShopModulePrototypeAO {
	/**
	 * 获取待编辑的页面的指定部分可添加的模块
	 * @param 	
	 * 			pageId 	--页面编号与表SHOP_PAGE_PROTOTYPE关联
	 * 			type 	--页面的哪个部分（上，左，右，下）
	 * @return
	 */
	List<ShopModulePrototypeDO> getDesignerModuleByPage(Integer pageId, Integer type);

}
