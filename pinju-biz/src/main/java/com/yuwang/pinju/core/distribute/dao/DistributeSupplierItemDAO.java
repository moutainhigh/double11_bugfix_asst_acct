package com.yuwang.pinju.core.distribute.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemParamDO;
import com.yuwang.pinju.domain.distribute.DistributorItemQuery;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public interface DistributeSupplierItemDAO {

	/**
	 * 取得分页分销商品ID
	 * 
	 * @param distrbuteSupplierItemDO
	 * @return
	 * @throws DaoException
	 */
	public List<Long> queryDistributeGoodIds(DistrbuteSupplierItemParamDO distrbuteSupplierItemDO)
			throws DaoException;
	

	/**
	 * 取得分页分销商品数据
	 * 
	 * @param distrbuteSupplierItemDO
	 * @param start
	 * @param end
	 * @throws DaoException
	 */
	public List<DistrbuteSupplierItemDO> queryDistributeGoods(DistrbuteSupplierItemParamDO distrbuteSupplierItemDO)
			throws DaoException;


	/**
	 * 保存分销商品
	 * 
	 * @param distrbuteSupplierItemDO
	 * @throws DaoException
	 */
	public DistrbuteSupplierItemDO saveSupplierItem(DistrbuteSupplierItemDO distrbuteSupplierItemDO)
			throws DaoException;

	/**
	 * 更新分销商品
	 * 
	 * @param distrbuteSupplierItemDO
	 * @throws DaoException
	 */
	public int updateSupplierItem(DistrbuteSupplierItemDO distrbuteSupplierItemDO) throws DaoException;

	/**
	 * 查询供应商商品列表
	 * 
	 * @param param
	 * 			查询参数[分页]
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	List<DistrbuteSupplierItemDO> selectAllDistrbuteSupplierItem(DistrbuteSupplierItemParamDO param) throws DaoException;
	
	/**
	 * 供应商商品列表行数
	 * 
	 * @param param
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	Integer getCount(DistrbuteSupplierItemParamDO param) throws DaoException;
	
	/**
	 * 查询分销商未分销的商品列表（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	List<DistrbuteSupplierItemDO> findDistributorUnsoldItem(DistributorItemQuery query) throws DaoException;
	
	/**
	 * 查询分销商未分销的商品数量（支持分页）
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	Integer findDistributorUnsoldItemCount(DistributorItemQuery query) throws DaoException;
	
	/**
	 * 查询分销商已分销的商品列表（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	List<DistrbuteSupplierItemDO> findDistributorSoldItem(DistributorItemQuery query) throws DaoException;
	
	/**
	 * 查询分销商已分销的商品列表id（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	List<Long> findDistributorSoldItemIdList(DistributorItemQuery query) throws DaoException;

	/**
	 * 查询分销商已分销的商品数量（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	Integer findDistributorSoldItemCount(DistributorItemQuery query) throws DaoException;
	
	/**
	 * 根据用户ID和商品ID查找商品返点率信息[交易检测接口]
	 * 
	 * @param param
	 * 		用户ID和商品信息
	 * @return
	 * @throws DaoException
	 */
	List<DistrbuteSupplierItemDO> selectDistributorSoldItemByCondition(DistributorItemQuery param) throws DaoException;

	/**
	 * 供应商停止分销商品
	 * 
	 * @param parameterList
	 * 		参数
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	abstract Integer discardDistributorItemBySupplierIdAndItemId(
		List<DistrbuteSupplierItemDO> parameterList) throws DaoException;
}