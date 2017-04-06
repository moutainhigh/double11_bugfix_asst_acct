package com.yuwang.pinju.core.distribute.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemDO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemParamDO;
import com.yuwang.pinju.domain.distribute.ShowCaseQueryDO;

public interface DistributeChannelItemDAO {

	/**
	 * 批量保存分销商品
	 * 
	 * @param param
	 * 			分销商品
	 * @throws DaoException
	 */
	abstract void save(List<DistributeChannelItemDO> paramList) throws DaoException;

	/**
	 * 批量更新分销商品
	 * 
	 * @param param
	 * 			分销商品
	 * @return
	 * @throws DaoException
	 */
	abstract Boolean update(List<DistributeChannelItemDO> paramList) throws DaoException;

	/**
	 * 根据条件查找所有的分销商品信息
	 * 
	 * @param param
	 * 			分销商品查找条件
	 * @return
	 * @throws DaoException
	 */
	abstract List<DistributeChannelItemDO> findDistributorChannelItemDOByCondition(
			DistributeChannelItemParamDO param) throws DaoException;

	/**
	 * 根据条件查找分销商品总数
	 * 
	 * @param param
	 * 			分销商品查找条件
	 * @return
	 * @throws DaoException
	 */
	abstract Integer getCount(DistributeChannelItemParamDO param)
			throws DaoException;

	/**
	 * 更新一个分销商品
	 * 
	 * @param param
	 * 			分销商品
	 * @return
	 * @throws DaoException
	 */
	abstract Boolean update(DistributeChannelItemDO param) throws DaoException;

	/**
	 * 保存一个分销商品
	 * 
	 * @param param
	 * 			分销商品
	 * @throws DaoException
	 */
	abstract void save(DistributeChannelItemDO param) throws DaoException;

	/**
	 * 根据条件查找商品ID
	 * 
	 * @param param
	 * 			分销商商品查找条件
	 * @return
	 * @throws DaoException
	 */
	abstract List<Long> findItemIdsByCondition(DistributeChannelItemParamDO param)
			throws DaoException;

	/** 根据条件查找分销商品信息[分享购社区]
   	 * 
   	 * @param param
   	 * 			分销商商品查找条件
   	 * @return
   	 * @throws DaoException
   	 */
	abstract List<DistributeChannelItemDO> findShowCaseItemByCondition(ShowCaseQueryDO param)
			throws DaoException;

	/**
	 * 根据条件查找分销商品总数[分享购社区]
	 * 
	 * @param param
	 * 			分销商商品查找条件
	 * @return
	 * @throws DaoException
	 */
	abstract Integer findShowCaseItemCountByCondition(ShowCaseQueryDO param)
			throws DaoException;

}