package com.yuwang.pinju.core.coupon.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchVO;

public interface TradeCouponBatchDAO {

	/**
	 * 新增一个批次优惠券
	 * @author caoxiao
	 * @date 2011-11-23
	 * @param tcb
	 */
	public long addTradeCouponBatch(TradeCouponBatchDO tcb) throws DaoException ;
	
	/**
	 * 根据批次id查询
	 * @param batchId
	 * @return TradeCouponBatch
	 * @throws DaoException
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public TradeCouponBatchDO getTradeCouponBatchById(long batchId) throws DaoException;
	
	/**
	 * 根据批次id更新
	 * @param tcb
	 * @return
	 * @throws DaoException
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public long updateTradeCouponBatchById(TradeCouponBatchDO tcb) throws DaoException;
	
	/**
	 * 买家领取一张优惠券，更新批次信息
	 * @param tcb
	 * @return
	 * @throws DaoException
	 * @author shihongbo
	 * @date 2011-11-23
	 */
	public Integer buyerGetCoupon(TradeCouponBatchDO tcb) throws DaoException;
	
	/**
	 * 查询全部优惠券批次总数
	 * @return
	 * @throws DaoException
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public long queryTradeCouponBatchAllCount(TradeCouponBatchDO tcb) throws DaoException;
	
	/**
	 * 查询全部优惠券批次
	 * @return
	 * @throws DaoException
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public List<TradeCouponBatchDO> queryTradeCouponBatchAll(TradeCouponBatchDO tcb) throws DaoException;
	
	
	/**
	 * 根据卖家id和店铺id 批量关闭已失效优惠券批次
	 * @return
	 * @throws DaoException
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public long updateTradeCouponBatchByInvalid(TradeCouponBatchDO tcb) throws DaoException;
	
	/**
	 * Created on 2011-11-25
	 * <p>Description: 卖家统计优惠券</p>
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<TradeCouponBatchVO> getCouponBatchVO(TradeCouponBatchDO couponBatchDO)throws DaoException;
	
	/**
	 * Created on 2011-11-25
	 * <p>Description: 卖家统计优惠券数量</p>
	 * @param couponBatchDO
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int getCouponBatchVONum(TradeCouponBatchDO couponBatchDO)throws DaoException;
	
	/**
	 * 卖家首页显示最新的5条优惠券
	 * @param tcb
	 * @return
	 * @throws DaoException
	 */
	public List<TradeCouponBatchDO> queryTradeCouponBatchByTop(TradeCouponBatchDO tcb) throws DaoException;
}
