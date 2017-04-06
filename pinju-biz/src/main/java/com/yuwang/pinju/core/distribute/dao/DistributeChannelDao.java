package com.yuwang.pinju.core.distribute.dao;
import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
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
public interface DistributeChannelDao {

	/**
	 * 查询分销商的供应商数量
	 * 
	 * @param param
	 * 			查询参数
	 * @throws DaoException
	 * @author caiwei 
	 */
	Integer getDistributeCount(DistribureChannelParamDO param) throws DaoException;

	/**
	 * 根据条件查找指定分销商关系
	 * 
	 * @param param
	 * 			条件
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	DistribureChannelDO findDistributeChannelByCondition(DistribureChannelParamDO param) throws DaoException;
	
	/**
	 * 查询分销商的所有供应商[有分页]
	 * 
	 * @param param
	 * 			参数 
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	List<DistributeSupplierDO> findAllDistribureSupplierByCondition(DistribureChannelParamDO param) throws DaoException;
	
	/**
	 * 
	 * @param distribureChannelDO
	 * @param start
	 * @param end
	 * @throws DaoException 
	 */
	List<DistribureChannelDO> queryDistributeList(DistribureChannelParamDO param) throws DaoException;

	/**
	 * 保存供应商的分销商信息
	 * 
	 * @param distribureChannelDO
	 * @throws DaoException 
	 * @author caiwei
	 */
	void saveDistribureChannel(DistribureChannelDO distribureChannelDO) throws DaoException;

	/**
	 * 更新供应商分销商的关系信息
	 * 
	 * @param distribureChannelDO
	 * 			关系信息
	 * @throws DaoException 
	 * @author caiwei
	 */
	void updateDistribute(DistribureChannelDO param) throws DaoException;
	void updateDistributeAddGoods(DistribureChannelDO param) throws DaoException;

	/**
	 * 查询指定分销商的所有在合作期间内的供应商
	 * 
	 * @param param
	 * @throws DaoException 
	 * @author caiwei
	 */
	List<DistribureChannelDO> selectAllowedDistributeChannelGoodList(DistributorItemQuery param) throws DaoException;
	/**
	 * 查询指定分销商的所有在合作期间内的供应商
	 * 
	 * @param param
	 * @throws DaoException 
	 * @author caiwei
	 */
	List<DistribureChannelDO> findShowCaseByCondition(ShowCaseQueryDO param) throws DaoException;
	
	/**
	 * 取得分销商信息
	 * 
	 * @return
	 * @throws DaoException 
	 * @throws DaoException
	 */
	public DistributeDistributorDO getDistributorByUserId(long userId) throws DaoException;

	/**
	 * 分销商停止分销供应商的商品
	 * 
	 * @param paramList
	 * 		参数
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	abstract Integer discardDistributeChannelGoodBySupplierId(
		List<DistribureChannelDO> paramList) throws DaoException;

	/**
	 * 分享购订单页面分销商下拉列表
	 * 
	 * @param distribureChannelDO
	 * @param start
	 * @param end
	 * @throws DaoException
	 */
	abstract List<DistributeDistributorDO> findDistributeList(DistribureChannelParamDO param) throws DaoException;

	/**
	 * 查询分销商的所有供应商[无分页]
	 * 
	 * @param param
	 *            参数
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	public abstract List<DistributeSupplierDO> findDistribureSupplierByCondition(DistribureChannelParamDO param)
			throws DaoException;
}