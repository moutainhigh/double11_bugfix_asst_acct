/**
 * 
 */
package com.yuwang.pinju.core.distribute.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.distribute.dao.DistributeChannelItemDAO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemDO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemParamDO;
import com.yuwang.pinju.domain.distribute.ShowCaseQueryDO;

/**
 * 分销商商品DAO
 * 
 * @author caiwei
 *
 */
@SuppressWarnings("unchecked")
public class DistributeChannelItemDAOImpl extends BaseDAO implements DistributeChannelItemDAO {

	/**
	 * 批量保存分销商品
	 * 
	 * @param param
	 * 			分销商品
	 * @throws DaoException
	 */
	@Override
	public void save(List<DistributeChannelItemDO> paramList) throws DaoException {
		super.executeBatchInsert("distribute_channel_item.insertDistributeChannelItem", paramList, 100);
	}
	
	/**
	 * 保存一个分销商品
	 * 
	 * @param param
	 * 			分销商品
	 * @throws DaoException
	 */
	@Override
	public void save(DistributeChannelItemDO param) throws DaoException {
		DistributeChannelItemDO[] paramArray = {param};
		save(CollectionUtils.arrayToList(paramArray));
	}
	
	/**
	 * 批量更新分销商品
	 * 
	 * @param param
	 * 			分销商品
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Boolean update(List<DistributeChannelItemDO> paramList) throws DaoException {
		Integer row = super.executeBatchUpdate("distribute_channel_item.updateDistributeChannelItem", paramList, 100);
		if (row != null && row > 0) {
			return Boolean.TRUE;
		}else {
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 更新一个分销商品
	 * 
	 * @param param
	 * 			分销商品
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Boolean update(DistributeChannelItemDO param) throws DaoException {
		DistributeChannelItemDO[] paramArray = {param};
		return update(CollectionUtils.arrayToList(paramArray));
	}
	
	/**
	 * 根据条件查找所有的分销商品信息
	 * 
	 * @param param
	 * 			分销商品查找条件
	 * @return
	 * @throws DaoException
	 */
	@Override
	public List<DistributeChannelItemDO> findDistributorChannelItemDOByCondition(DistributeChannelItemParamDO param) throws DaoException{
		List<DistributeChannelItemDO> result = super.executeQueryForList("distribute_channel_item.selectDistributeChannelItem", param);
		if (!CollectionUtils.isEmpty(result)) {
			return result;
		}else {
			return new ArrayList<DistributeChannelItemDO>();
		}
	}
	
	/**
	 * 根据条件查找分销商品总数
	 * 
	 * @param param
	 * 			分销商品查找条件
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Integer getCount(DistributeChannelItemParamDO param) throws DaoException{
		Integer pageCount = (Integer) super.executeQueryForObject("distribute_channel_item.selectDistributeChannelItemCount", param);
		if (pageCount != null) {
			return pageCount;
		}else {
			return new Integer(0);
		}
	}
	
	/**
	 * 根据条件查找商品ID
	 * 
	 * @param param
	 * 			分销商商品查找条件
	 * @return
	 * @throws DaoException
	 */
	@Override
	public List<Long> findItemIdsByCondition(DistributeChannelItemParamDO param) throws DaoException{
		List<Long> ids = super.executeQueryForList("distribute_channel_item.selectDistributeChannelItemId", param);
		if (!CollectionUtils.isEmpty(ids)) {
			return ids;
		}else {
			return new ArrayList<Long>();
		}
	}
	
	/**
	 * 根据条件查找分销商品信息[分享购社区]
	 * 
	 * @param param
	 * 			分销商商品查找条件
	 * @return
	 * @throws DaoException
	 */
	@Override
	public List<DistributeChannelItemDO> findShowCaseItemByCondition(ShowCaseQueryDO param) throws DaoException{
		List<DistributeChannelItemDO> result = super.executeQueryForList("distribute_channel_item.findShowCaseItemByCondition", param);
		if (!CollectionUtils.isEmpty(result)) {
			return result;
		}else {
			return new ArrayList<DistributeChannelItemDO>();
		}
	}
	
	/**
	 * 根据条件查找分销商品总数[分享购社区]
	 * 
	 * @param param
	 * 			分销商商品查找条件
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Integer findShowCaseItemCountByCondition(ShowCaseQueryDO param) throws DaoException{
		Integer count = (Integer)super.executeQueryForObject("distribute_channel_item.findShowCaseItemCountByCondition", param);
		if (count != null) {
			return count;
		}else {
			return new Integer(0);
		}
	}
}
