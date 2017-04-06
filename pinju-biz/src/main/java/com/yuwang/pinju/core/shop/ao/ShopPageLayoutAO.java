package com.yuwang.pinju.core.shop.ao;

import java.util.List;

import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;

public interface ShopPageLayoutAO {

	/**
	 * 保存页面布局
	 * 
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return
	 */
	public boolean savePageLayout(Long userId, Integer shopId, Integer pageId,
			String layoutXml);

	/**
	 * 查询用户页面布局
	 * 
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return
	 */
	public List<ShopPageLayoutDO> queryPageLayout(Long userPageId, Long userId,
			Integer shopId, Integer pageId, boolean isRelease);
}
