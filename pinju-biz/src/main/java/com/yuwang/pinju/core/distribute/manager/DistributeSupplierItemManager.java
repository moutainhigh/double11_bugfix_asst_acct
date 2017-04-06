package com.yuwang.pinju.core.distribute.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemParamDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public interface DistributeSupplierItemManager {

	/**
	 * 取得分页分销商品数据
	 * 
	 * @param distrbuteSupplierItemDO
	 * @return List<DistrbuteSupplierItemDO>
	 * @throws ManagerException
	 */
	public List<DistrbuteSupplierItemDO> getDistributeGoods(DistrbuteSupplierItemParamDO distrbuteSupplierItemDO)
			throws ManagerException;

	/**
	 * 保存分销商品列表
	 * 
	 * @param distributeGoods
	 * @throws ManagerException
	 */
	public void saveSupplierItems(List<DistrbuteSupplierItemDO> distributeGoods) throws ManagerException;

	/**
	 * 更新分销商品
	 * 
	 * @param distributeGoods
	 * @throws ManagerException
	 */
	public void updateSupplierItem(List<DistrbuteSupplierItemDO> distributeGoods) throws ManagerException;

	/**
	 * 根据供应商和商品取得分销商品
	 * 
	 * @throws ManagerException
	 */
	public DistrbuteSupplierItemDO getSupplierItemBySupplierIdAndItemId(
			DistrbuteSupplierItemParamDO distrbuteSupplierItemDO) throws ManagerException;
	
	/**
	 * 取得已分销商品分页列表
	 * 
	 * @throws ManagerException
	 * 
	 */
	public List<Long> getDistributeGoodIds(Integer supplierId, Integer curPage, Integer items) throws ManagerException;

	/**
	 * 取得商铺已分销商品数
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException 
	 */
	public int getDistributeGoodsCount(Integer supplierId) throws ManagerException;

}