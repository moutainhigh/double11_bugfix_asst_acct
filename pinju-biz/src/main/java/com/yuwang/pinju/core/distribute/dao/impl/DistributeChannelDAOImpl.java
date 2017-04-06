package com.yuwang.pinju.core.distribute.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.distribute.dao.DistributeChannelDao;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributorItemQuery;
import com.yuwang.pinju.domain.distribute.ShowCaseQueryDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
@SuppressWarnings("unchecked")
public class DistributeChannelDAOImpl extends BaseDAO implements DistributeChannelDao {

	public DistributeChannelDAOImpl() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 查询分销商的供应商数量
	 * 
	 * @param param
	 *            查询参数
	 * @throws DaoException
	 * @author caiwei
	 */
	public Integer getDistributeCount(DistribureChannelParamDO param) throws DaoException {
		Integer result = (Integer) super.executeQueryForObject("distribute_channel.selectDistributeChannelInfoCount",
				param);
		if (result != null) {
			return (Integer) result;
		} else {
			return new Integer(0);
		}
	}

	/**
	 * 查询分销商的所有供应商[有分页]
	 * 
	 * @param param
	 *            参数
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	public List<DistributeSupplierDO> findAllDistribureSupplierByCondition(DistribureChannelParamDO param)
			throws DaoException {
		List<DistributeSupplierDO> result = super.executeQueryForList("distribute_channel.selectDistributeChannelInfo",
				param);
		if (result != null) {
			return (List<DistributeSupplierDO>) result;
		} else {
			return new ArrayList<DistributeSupplierDO>();
		}
	}
	
	/**
	 * 查询分销商的所有供应商[无分页]
	 * 
	 * @param param
	 *            参数
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<DistributeSupplierDO> findDistribureSupplierByCondition(DistribureChannelParamDO param) throws DaoException {
		List<DistributeSupplierDO> result = super.executeQueryForList("distribute_channel.findDistributeChannelInfo",
				param);
		if (result != null) {
			return (List<DistributeSupplierDO>) result;
		} else {
			return new ArrayList<DistributeSupplierDO>();
		}
	}

	/**
	 * 根据条件查找指定分销商关系
	 * 
	 * @param param
	 *            条件
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public DistribureChannelDO findDistributeChannelByCondition(DistribureChannelParamDO param) throws DaoException {
		List<DistribureChannelDO> resultList = super.executeQueryForList("distribute_channel.selectDistributeChannel",
				param);
		if (resultList != null && resultList.size() > 1) {
			throw new DaoException(
					"[DistributeChannelDAOImpl#findDistributeChannelByCondition->returned too many results]");
		} else if (resultList != null && resultList.size() == 1) {
			return (DistribureChannelDO) resultList.get(0);
		} else {
			return new DistribureChannelDO();
		}
	}

	/**
	 * 
	 * @param distribureChannelDO
	 * @param start
	 * @param end
	 * @throws DaoException
	 */
	public List<DistribureChannelDO> queryDistributeList(DistribureChannelParamDO param) throws DaoException {
		List<DistribureChannelDO> result = super.executeQueryForList("distribute_channel.selectDistributeChannel",
				param);
		if (result != null) {
			return (List<DistribureChannelDO>) result;
		} else {
			return new ArrayList<DistribureChannelDO>();
		}
	}
	
	/**
	 * 
	 * @param distribureChannelDO
	 * @param start
	 * @param end
	 * @throws DaoException
	 */
	@Override
	public List<DistributeDistributorDO> findDistributeList(DistribureChannelParamDO param) throws DaoException {
		List<DistributeDistributorDO> result = super.executeQueryForList("distribute_channel.findDistributeChannel",
				param);
		if (result != null) {
			return (List<DistributeDistributorDO>) result;
		} else {
			return new ArrayList<DistributeDistributorDO>();
		}
	}

	/**
	 * 保存供应商的分销商信息
	 * 
	 * @param distribureChannelDO
	 * @throws DaoException
	 * @author caiwei
	 */
	public void saveDistribureChannel(DistribureChannelDO distribureChannelDO) throws DaoException {
		super.executeInsert("distribute_channel.insertDistributeChannel", distribureChannelDO);
	}

	/**
	 * 更新供应商分销商的关系信息
	 * 
	 * @param distribureChannelDO
	 *            关系信息
	 * @throws DaoException
	 * @author caiwei
	 */
	public void updateDistribute(DistribureChannelDO param) throws DaoException {
		super.executeUpdate("distribute_channel.updateDistributeChannelStatus", param);
	}

	public void updateDistributeAddGoods(DistribureChannelDO param) throws DaoException {
		Integer updateRow = super.executeUpdate("distribute_channel.updateDistributeChannelGoods", param);
		if (updateRow == null || updateRow == 0) {
			throw new DaoException("event[DistributeChannelDAO#updateDistributeAddGoods->数据版本不一致]");
		}
	}

	/**
	 * 查询指定分销商的所有在合作期间内的供应商
	 * 
	 * @param param
	 * @throws DaoException
	 * @author caiwei
	 */
	public List<DistribureChannelDO> selectAllowedDistributeChannelGoodList(DistributorItemQuery param)
			throws DaoException {
		List<DistribureChannelDO> result = super.executeQueryForList(
				"distribute_channel.selectAllowedDistributeChannelGoodList", param);
		if (result != null) {
			return result;
		} else {
			return new ArrayList<DistribureChannelDO>();
		}
	}

	/**
	 * 查询指定分销商的所有在合作期间内的供应商
	 * 
	 * @param param
	 * @throws DaoException
	 * @author caiwei
	 */
	public List<DistribureChannelDO> findShowCaseByCondition(ShowCaseQueryDO param) throws DaoException {
		List<DistribureChannelDO> result = super.executeQueryForList("distribute_channel.findShowCaseByCondition",
				param);
		if (result != null) {
			return result;
		} else {
			return new ArrayList<DistribureChannelDO>();
		}
	}

	/**
	 * 取得分销商信息
	 * 
	 * @param userId
	 * @throws DaoException
	 */
	public DistributeDistributorDO getDistributorByUserId(long userId) throws DaoException {
		DistributeDistributorDO result = (DistributeDistributorDO) super.executeQueryForObject(
				"distribute_channel.getDistributorByMemberId", userId);
		if (result != null) {
			return (DistributeDistributorDO) result;
		}
		return null;
	}
	
	/**
	 * 分销商停止分销供应商的商品
	 * 
	 * @param paramList
	 * 		参数
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public Integer discardDistributeChannelGoodBySupplierId(List<DistribureChannelDO> paramList) throws DaoException{
	    Integer updateRow = super.executeBatchUpdate("distribute_channel.discardDistributeChannelGood", paramList, 100);
	    if (updateRow != null) {
		return updateRow;
	    }else {
		return new Integer(0);
	    }
	}
}