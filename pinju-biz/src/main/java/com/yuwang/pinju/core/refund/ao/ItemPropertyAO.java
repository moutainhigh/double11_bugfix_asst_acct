package com.yuwang.pinju.core.refund.ao;

import java.util.List;

import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;

/**
 * 取得商品属性
 * @author shihongbo
 * @since   2011-8-11
 */
public interface ItemPropertyAO {
	/**
	 * 取得商品属性
	 * 
	 * @param sku
	 * @return
	 */
	public List<ItemPropertyVO> getItemPropertyBySku(SkuDO sku);
}
