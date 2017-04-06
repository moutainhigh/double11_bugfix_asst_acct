package com.yuwang.pinju.core.distribute.ao;

import java.util.List;

import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public interface DistributeGoodsManagerAO {

	/**
	 * 取得已分销商品分页列表
	 * 
	 * @param distrbuteSupplierItemDO
	 * @param start
	 * @param end
	 */
	public List<DistrbuteSupplierItemDO> getDistributeGoods(Integer supplierId, ItemQueryEx itemQuery);

	/**
	 * 取得商铺所有可分销商品数
	 * 
	 * @param userId
	 * @return
	 */
	public int getAllGoodsCount(long userId);

	/**
	 * 取得商铺已分销商品数
	 * 
	 * @param userId
	 * @return
	 */
	public int getDistributeGoodsCount(Integer supplierId);

	/**
	 * 取得商铺所有商品分页列表
	 * 
	 * @param userId
	 * @param page
	 * @param allcount
	 */
	public List<DistrbuteSupplierItemDO> getAllGoods(Integer supplierId, ItemQueryEx itemQuery);

	/**
	 * 保存未分销商品至分销商品
	 * 
	 * @param distributeGoods
	 */
	public void saveDistributeGoods(List<DistrbuteSupplierItemDO> distributeGoods);

	/**
	 * 更新已分销商品
	 * 
	 * @param unDistributeGoods
	 */
	public void updateDistributeGoods(List<DistrbuteSupplierItemDO> unDistributeGoods);

}