package com.yuwang.pinju.core.distribute.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.distribute.dao.DistributeSupplierItemDAO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemParamDO;
import com.yuwang.pinju.domain.distribute.DistributorItemQuery;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
@SuppressWarnings("unchecked")
public class DistributeSupplierItemDAOImpl extends BaseDAO implements
		DistributeSupplierItemDAO {

	public DistributeSupplierItemDAOImpl() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param distrbuteSupplierItemDO
	 */
	public int getDistributeGoodsCount(DistrbuteSupplierItemDO distrbuteSupplierItemDO) {
		return 0;
	}

	/**
	 * 保存分销商品
	 * 
	 * @param distrbuteSupplierItemDO
	 * @throws DaoException
	 */
	public DistrbuteSupplierItemDO saveSupplierItem(DistrbuteSupplierItemDO distrbuteSupplierItemDO)
			throws DaoException {
		return (DistrbuteSupplierItemDO) super.executeInsert("distribute_supplier_goods.insertDistributeSupplierGoods",
				distrbuteSupplierItemDO);
	}

	/**
	 * 更新分销商品
	 * 
	 * @param distrbuteSupplierItemDO
	 * @throws DaoException
	 */
	public int updateSupplierItem(DistrbuteSupplierItemDO distrbuteSupplierItemDO) throws DaoException {
		return super.executeUpdate("distribute_supplier_goods.updateDistributeSupplierGoods", distrbuteSupplierItemDO);
	}

	/**
	 * 供应商商品列表行数
	 * 
	 * @param param
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public Integer getCount(DistrbuteSupplierItemParamDO param) throws DaoException {
		Integer result = (Integer) super.executeQueryForObject("distribute_supplier_goods.count", param);
		if (result != null) {
			return (Integer) result;
		} else {
			return new Integer(0);
		}
	}

	/**
	 * 查询供应商商品列表
	 * 
	 * @param param
	 *            查询参数[分页]
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<DistrbuteSupplierItemDO> selectAllDistrbuteSupplierItem(DistrbuteSupplierItemParamDO param)
			throws DaoException {
		List<DistrbuteSupplierItemDO> result = super.executeQueryForList(
				"distribute_supplier_goods.selectDistributeSupplierGoods", param);
		if (result != null) {
			return (List<DistrbuteSupplierItemDO>) result;
		} else {
			return new ArrayList<DistrbuteSupplierItemDO>();
		}
	}

	/**
	 * 查询分销商已分销的商品列表（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<DistrbuteSupplierItemDO> findDistributorSoldItem(
		DistributorItemQuery query) throws DaoException {
	    List<DistrbuteSupplierItemDO> result = super.executeQueryForList("distribute_supplier_goods.selectDistributorSoldItem", query);
	    if (result != null) {
		return result;
	    } else {
		return new ArrayList<DistrbuteSupplierItemDO>();
	    }
	}
	/**
	 * 查询分销商已分销的商品列表（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<Long> findDistributorSoldItemIdList(
		DistributorItemQuery query) throws DaoException {
	    List<Long> result = super.executeQueryForList("distribute_supplier_goods.selectDistributorSoldItemIdList", query);
	    if (result != null) {
		return result;
	    } else {
		return new ArrayList<Long>();
	    }
	}


	/**
	 * 查询分销商未分销的商品列表（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<DistrbuteSupplierItemDO> findDistributorUnsoldItem(
		DistributorItemQuery query) throws DaoException {
	    List<DistrbuteSupplierItemDO> result = super.executeQueryForList("distribute_supplier_goods.selectDistributorUnsoldItem", query);
	    if (result != null) {
		return result;
	    } else {
		return new ArrayList<DistrbuteSupplierItemDO>();
	    }
	}

	/**
	 * 查询分销商已分销的商品数量（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public Integer findDistributorSoldItemCount(DistributorItemQuery query)
		throws DaoException {
	    Integer count = (Integer)super.executeQueryForObject("distribute_supplier_goods.selectDistributorSoldItemCount", query);
	    if (count != null) {
		return count;
	    } else {
		return 0;
	    }
	}

	/**
	 * 查询分销商未分销的商品数量（支持分页）
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public Integer findDistributorUnsoldItemCount(
		DistributorItemQuery query) throws DaoException {
	    Integer count = (Integer)super.executeQueryForObject("distribute_supplier_goods.selectDistributorUnsoldItemCount", query);
	    if (count != null) {
		return count;
	    } else {
		return 0;
	    }
	}


	/**
	 * 取得分页分销商品ID
	 * 
	 * @param 查询参数
	 *            [分页]
	 * @return IDs
	 */
	@Override
	public List<Long> queryDistributeGoodIds(DistrbuteSupplierItemParamDO param) throws DaoException {
		List<Long> result = super.executeQueryForList("distribute_supplier_goods.selectDistributeSupplierGoodIds",
				param);
		if (result != null) {
			return result;
		} else {
			return new ArrayList<Long>();
		}
	}

	/**
	 * 取得分页分销商品数据
	 * 
	 * @param distrbuteSupplierItemDO
	 * @param start
	 * @param end
	 * @throws DaoException
	 */
	@Override
	public List<DistrbuteSupplierItemDO> queryDistributeGoods(DistrbuteSupplierItemParamDO distrbuteSupplierItemDO)
			throws DaoException {
		List<DistrbuteSupplierItemDO> result = super.executeQueryForList(
				"distribute_supplier_goods.selectDistributeSupplierGoods", distrbuteSupplierItemDO);
		if (result != null) {
			return result;
		} else {
			return new ArrayList<DistrbuteSupplierItemDO>();
		}
	}

	/**
	 * 根据用户ID和商品ID查找商品返点率信息[交易检测接口]
	 * 
	 * @param param
	 * 		用户ID和商品信息
	 * @return
	 * @throws DaoException
	 */
	public List<DistrbuteSupplierItemDO> selectDistributorSoldItemByCondition(DistributorItemQuery param) throws DaoException {
	    List<DistrbuteSupplierItemDO> resultList = super.executeQueryForList("distribute_supplier_goods.selectDistributorSoldItemByCondition", param);
	    if (CollectionUtils.isEmpty(resultList)) {
		return new ArrayList<DistrbuteSupplierItemDO>();
	    }else {
		return resultList;
	    }
	}
	
	/**
	 * 供应商停止分销商品
	 * 
	 * @param parameterList
	 * 		参数
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public Integer discardDistributorItemBySupplierIdAndItemId(List<DistrbuteSupplierItemDO> parameterList) throws DaoException{
	    Integer updateRow = super.executeBatchUpdate("distribute_supplier_goods.discardDistributeSupplierGood", parameterList, 100);
	    if (updateRow != null) {
		return updateRow;
	    }else {
		return new Integer(0);
	    }
	}
}