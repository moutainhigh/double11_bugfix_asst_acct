package com.yuwang.pinju.core.distribute.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public interface DistributeSupplierDao {

	/**
	 * 根据ID取得供应商
	 * 
	 * @param id
	 * @return DistributeSupplierDO
	 * @throws DaoException
	 */
	public DistributeSupplierDO getDistributeSupplierById(long id) throws DaoException;
	
	/**
	 * 根据条件取得供应商
	 * 
	 * @param id
	 * @return DistributeSupplierDO
	 * @throws DaoException 
	 * @author caiwei
	 */
	public DistributeSupplierDO getDistributeSupplier(DistributeSupplierParamDO distributeSupplierDO) throws DaoException;

	/**
	 * 保存供应商
	 * 
	 * @param distribureChannelDO
	 * @throws DaoException
	 */
	public Integer saveDistributeSupplier(DistributeSupplierDO distributeSupplierDO) throws DaoException;

	/**
	 * 更新供应商
	 * 
	 * @param distribureChannelDO
	 * @throws DaoException
	 */
	public int updateDistributeSupplier(DistributeSupplierDO distributeSupplierDO) throws DaoException;

	/**
	 * 查询供应商信息列表
	 * 
	 * @param param
	 * 			查询参数[分页]
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	List<DistributeSupplierDO> selectAllDistributeSupplierInfo(DistributeSupplierParamDO param) throws DaoException;
	
	/**
	 * 供应商列表行数
	 * 
	 * @param param
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	Integer getCount(DistributeSupplierParamDO param) throws DaoException;
	
	/**
	 * 根据id查询供应商信息列表
	 * @param ids
	 * 			id序列
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	List<DistributeSupplierDO> selectDistributeSupplierByIds(List<Integer> ids) throws DaoException;
	
	/**
	 * 查询供应商列表
	 * 
	 * @param param
	 * 			查询参数[分页]
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	List<DistributeSupplierDO> findAllDistributeSupplierByCondition(DistributeSupplierParamDO param) throws DaoException;

	/**
	 * 根据id查询供应商信息列表
	 * @param ids
	 * 			会员id序列
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	abstract List<DistributeSupplierDO> selectSupplierShopIdByIds(List<Long> memberIds)
			throws DaoException;

	/**
	 * 根据条件取得供应商
	 * 
	 * @param itemId
	 * @return DistributeSupplierDO
	 * @throws DaoException
	 * @author caiwei
	 */
	abstract DistributeSupplierDO selectDistributeSupplierByItemId(Long itemId)
			throws DaoException;
}