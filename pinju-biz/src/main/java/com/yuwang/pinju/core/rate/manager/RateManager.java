package com.yuwang.pinju.core.rate.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.rate.DsrCommentDO;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;
import com.yuwang.pinju.domain.rate.DsrResultDO;

/**
 * <p>动态评分管理</p>
 *
 * @author gaobaowen
 * 2011-6-15 上午11:03:17
 */
public interface RateManager {

	/**
	 * <p>允许使用缓存中的数据读取有效的 DSR 维度</p>
	 *
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-10-20 09:38:02
	 */
	List<DsrDimensionDO> getValidDsrDimension() throws ManagerException;

	/**
	 * <p>获取所有有效的 DSR 维度</p>
	 *
	 * @param isCacheDebug 是否处于调试模式（若为非开发模式时忽略该值），调试模式时数据不经过缓存
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	List<DsrDimensionDO> getValidDsrDimension(boolean isCacheDebug) throws ManagerException;

	/**
	 * <p>通过所有的 DSR 过滤出所有商品的 DSR</p>
	 *
	 * @param list  所有有效的 DSR
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	List<DsrDimensionDO> filterItemDsrDimension(List<DsrDimensionDO> list) throws ManagerException;

	/**
	 * <p>通过所有的 DSR 过滤出所有的店铺动态 DSR</p>
	 *
	 * @param list  所有有效的 DSR
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	List<DsrDimensionDO> filterRateDsrDimension(List<DsrDimensionDO> list) throws ManagerException;

	/**
	 * <p>新增订单的评价</p>
	 *
	 * @param dsrResults
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	void saveDsrResults(List<DsrResultDO> dsrResults) throws ManagerException;

	/**
	 * <p>保存购买后评价</p>
	 *
	 * @param comments  购买后的评论
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-10-12 09:41:04
	 */
	void saveDsrComments(List<DsrCommentDO> comments) throws ManagerException;
}