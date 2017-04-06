package com.yuwang.pinju.core.distribute.manager.impl;

import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.distribute.dao.DistributeSupplierItemDAO;
import com.yuwang.pinju.core.distribute.manager.DistributeSupplierItemManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemParamDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public class DistributeSupplierItemManagerImpl extends BaseManager implements DistributeSupplierItemManager {

	private DistributeSupplierItemDAO distributeSupplierItemDAO;

	private ItemManager itemManager;

	/**
	 * 保存分销商品列表
	 * 
	 * @param distributeGoods
	 * @throws ManagerException
	 */
	@Override
	public void saveSupplierItems(List<DistrbuteSupplierItemDO> distributeGoods) throws ManagerException {

		for (final DistrbuteSupplierItemDO dsi : distributeGoods) {
			if (!dsi.isChecked()) {
				continue;
			}
			    executeMysqlTransaction(Boolean.class, new TransactionCallback<Boolean>() {
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					DistrbuteSupplierItemParamDO paramDo = new DistrbuteSupplierItemParamDO();
					paramDo.setSupplierId(dsi.getSupplierId());
					paramDo.setItemId(dsi.getItemId());
					try {
						List<DistrbuteSupplierItemDO> list = distributeSupplierItemDAO.queryDistributeGoods(paramDo);
						if (0 < list.size()) {
							dsi.setId(list.get(0).getId());
							distributeSupplierItemDAO.updateSupplierItem(dsi);
						} else {
							distributeSupplierItemDAO.saveSupplierItem(dsi);
						}
						if (!itemManager.setFeatures(dsi.getItemId(), ItemFeaturesEnum.SHOP_DISTRIBUTION,
								String.valueOf(dsi.getStatus())).isSuccess()) {
						    throw new RuntimeException();
						}
					} catch (Exception e) {
					    throw new RuntimeException("保存分销商品出错");
					}
				    return true;
				}
			    });
		}
	}

	/**
	 * 更新分销商品
	 * 
	 * @param distributeGoods
	 * @throws ManagerException
	 */
	@Override
	public void updateSupplierItem(List<DistrbuteSupplierItemDO> distributeGoods) throws ManagerException {
		for (DistrbuteSupplierItemDO dsi : distributeGoods) {
			try {
				distributeSupplierItemDAO.updateSupplierItem(dsi);
			} catch (DaoException e) {
				throw new ManagerException("更新分销商品出错", e);
			}
		}
	}

	/**
	 * 取得分页分销商品数据
	 * 
	 * @param distrbuteSupplierItemDO
	 * @return List<DistrbuteSupplierItemDO>
	 * @throws ManagerException
	 */
	@Override
	public List<DistrbuteSupplierItemDO> getDistributeGoods(DistrbuteSupplierItemParamDO distrbuteSupplierItemDO)
			throws ManagerException {
		try {
			return distributeSupplierItemDAO.queryDistributeGoods(distrbuteSupplierItemDO);
		} catch (DaoException e) {
			throw new ManagerException("分页查询分销商品出错", e);
		}
	}

	/**
	 * 根据供应商和商品取得分销商品
	 * 
	 * @param DistrbuteSupplierItemParamDO
	 */
	@Override
	public DistrbuteSupplierItemDO getSupplierItemBySupplierIdAndItemId(
			DistrbuteSupplierItemParamDO distrbuteSupplierItemDO) throws ManagerException {
		try {
			List<DistrbuteSupplierItemDO> list = distributeSupplierItemDAO
					.queryDistributeGoods(distrbuteSupplierItemDO);
			if (0 < list.size()) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (DaoException e) {
			throw new ManagerException("根据供应商和商品取得分销商品", e);
		}
	}

	/**
	 * 取得已分销商品分页列表
	 * 
	 * @throws ManagerException
	 * 
	 */
	@Override
	public List<Long> getDistributeGoodIds(Integer supplierId, Integer curPage, Integer items) throws ManagerException {
		DistrbuteSupplierItemParamDO param = new DistrbuteSupplierItemParamDO();
		param.setSupplierId(supplierId);
		param.setItems(items);
		param.setPage(curPage);
		param.setStatus((short) 0);
		try {
			return distributeSupplierItemDAO.queryDistributeGoodIds(param);
		} catch (DaoException e) {
			throw new ManagerException("取得分页分销商品ID", e);
		}
	}

	/**
	 * 取得商铺已分销商品数
	 * 
	 * @long supplier
	 */
	@Override
	public int getDistributeGoodsCount(Integer supplierId) throws ManagerException {
		DistrbuteSupplierItemParamDO distrbuteSupplierItemDO = new DistrbuteSupplierItemParamDO();
		distrbuteSupplierItemDO.setSupplierId(supplierId);
		distrbuteSupplierItemDO.setStatus((short) 0);
		try {
			return distributeSupplierItemDAO.getCount(distrbuteSupplierItemDO);
		} catch (DaoException e) {
			throw new ManagerException("根据供应商和商品取得分销商品", e);
		}
	}

	public DistributeSupplierItemDAO getDistributeSupplierItemDAO() {
		return distributeSupplierItemDAO;
	}

	public void setDistributeSupplierItemDAO(DistributeSupplierItemDAO distributeSupplierItemDAO) {
		this.distributeSupplierItemDAO = distributeSupplierItemDAO;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

}