package com.yuwang.pinju.core.distribute.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.constant.distribute.DistributeConstant;
import com.yuwang.pinju.core.distribute.dao.DistributeSupplierDao;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
@SuppressWarnings("unchecked")
public class DistributeSupplierDAOImpl extends BaseDAO implements DistributeSupplierDao {

	/**
	 * 保存供应商
	 * 
	 * @param distribureChannelDO
	 * @throws DaoException
	 */
	@Override
	public Integer saveDistributeSupplier(DistributeSupplierDO distributeSupplierDO) throws DaoException {
		return (Integer) super.executeInsert("distribute_supplier.insertDistributeSupplier",
				distributeSupplierDO);
	}

	/**
	 * 更新供应商
	 * 
	 * @param distribureChannelDO
	 * @throws DaoException
	 */
	@Override
	public int updateDistributeSupplier(DistributeSupplierDO distributeSupplierDO) throws DaoException {
		return super.executeUpdate("distribute_supplier.updateDistributeSupplier", distributeSupplierDO);

	}

	/**
	 * 根据ID取得供应商
	 * 
	 * @param id
	 * @return DistributeSupplierDO
	 * @throws DaoException
	 */
	@Override
	public DistributeSupplierDO getDistributeSupplierById(long id) throws DaoException {
		return (DistributeSupplierDO) super.executeQueryForObject("distribute_supplier.selectDistributeSupplierByid",
				id);
	}

	/**
	 * 根据条件取得供应商
	 * 
	 * @param id
	 * @return DistributeSupplierDO
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public DistributeSupplierDO getDistributeSupplier(DistributeSupplierParamDO distributeSupplierDO) throws DaoException {
		List<DistributeSupplierDO> resultList = super.executeQueryForList("distribute_supplier.selectDistributeSupplier",distributeSupplierDO);
		if (resultList!=null && resultList.size() > 1) {
			throw new DaoException("[DistributeSupplierDAOImpl#getDistributeSupplier->returned too many results]");
		}else if (resultList!=null && resultList.size() == 1) {
			return (DistributeSupplierDO)resultList.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 * 根据条件取得供应商
	 * 
	 * @param itemId
	 * @return DistributeSupplierDO
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public DistributeSupplierDO selectDistributeSupplierByItemId(Long itemId) throws DaoException {
		List<DistributeSupplierDO> resultList = super.executeQueryForList("distribute_supplier.selectDistributeSupplierByItemId",new DistributeSupplierParamDO(itemId, DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_SELLING.shortValue()));
		if (resultList!=null && resultList.size() > 1) {
			throw new DaoException("[DistributeSupplierDAOImpl#getDistributeSupplier->returned too many results]");
		}else if (resultList!=null && resultList.size() == 1) {
			return (DistributeSupplierDO)resultList.get(0);
		}else {
			return new DistributeSupplierDO();
		}
	}
	
	/**
	 * 查询供应商信息列表
	 * 
	 * @param param
	 * 			查询参数[分页]
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<DistributeSupplierDO> selectAllDistributeSupplierInfo(
			DistributeSupplierParamDO param) throws DaoException {
		List<DistributeSupplierDO> result = super.executeQueryForList("distribute_supplier.selectAllDistributeSupplierInfo", param);
		if (result != null) {
			return (List<DistributeSupplierDO>)result;
		}else {
			return new ArrayList<DistributeSupplierDO>(0);
		}
	}
	
	/**
	 * 查询供应商列表
	 * 
	 * @param param
	 * 			查询参数[分页]
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<DistributeSupplierDO> findAllDistributeSupplierByCondition(DistributeSupplierParamDO param) throws DaoException {
		List<DistributeSupplierDO> result = super.executeQueryForList("distribute_supplier.selectDistributeSupplier", param);
		if (result != null) {
			return (List<DistributeSupplierDO>)result;
		}else {
			return new ArrayList<DistributeSupplierDO>(0);
		}
	}
	
	/**
	 * 根据shopId查询供应商信息列表
	 * @param ids
	 * 			shopId序列
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<DistributeSupplierDO> selectDistributeSupplierByIds(List<Integer> ids) throws DaoException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		paramMap.put("status", "0");
		List<DistributeSupplierDO> result = super.executeQueryForList("distribute_supplier.selectDistributeSupplierByIds", paramMap);
		if (result != null) {
			return (List<DistributeSupplierDO>)result;
		}else {
			return new ArrayList<DistributeSupplierDO>(0);
		}
	}
	
	/**
	 * 根据id查询供应商信息列表
	 * @param ids
	 * 			会员id序列
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public List<DistributeSupplierDO> selectSupplierShopIdByIds(List<Long> memberIds) throws DaoException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", memberIds);
		paramMap.put("status", "0");
		List<DistributeSupplierDO> result = super.executeQueryForList("distribute_supplier.selectSupplierShopIdByIds", paramMap);
		if (result != null) {
			return (List<DistributeSupplierDO>)result;
		}else {
			return new ArrayList<DistributeSupplierDO>(0);
		}
	}
	
	/**
	 * 供应商列表行数
	 * 
	 * @param param
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public Integer getCount(DistributeSupplierParamDO param) throws DaoException{
		Integer result = (Integer)super.executeQueryForObject("distribute_supplier.count", param);
		if (result != null) {
			return (Integer)result;
		}else {
			return new Integer(0);
		}
	}
}