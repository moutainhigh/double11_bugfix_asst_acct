package com.yuwang.pinju.core.coupon.ao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;

public interface TradeCouponBatchAO {
	
	/**
	 * 新增一个批次优惠券
	 * @author caoxiao
	 * @date 2011-11-23
	 * @param tcb
	 */
	public long addTradeCouponBatch(TradeCouponBatchDO tcb);
	
	/**
	 * 根据批次id查询
	 * @param batchId
	 * @return TradeCouponBatch
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public TradeCouponBatchDO getTradeCouponBatchById(long batchId);
	
	/**
	 * 根据批次id更新
	 * @param tcb
	 * @return
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public long updateTradeCouponBatchById(TradeCouponBatchDO tcb);
	
	/**
	 * 查询全部优惠券批次总数
	 * @return
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public Result queryTradeCouponBatchAll(TradeCouponBatchDO tcb);
	
	/**
	 * 批量关闭已失效优惠券批次
	 * @return
	 * @throws DaoException
	 * @author caoxiao
	 * @date 2011-11-23
	 */
	public long updateTradeCouponBatchByInvalid(TradeCouponBatchDO tcb);
	
	/**
	 * Created on 2011-11-25
	 * <p>Description: 获取卖家优惠券记录</p>
	 * @param couponBatchDO
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result sellerQueryTradeCouponBatch(TradeCouponBatchDO couponBatchDO);
	
	/**
	 * Created on 2011-11-25
	 * <p>Description: 获取卖家优惠券记录数</p>
	 * @param couponBatchDO
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int sellerQueryTradeCouponBatchNum(TradeCouponBatchDO couponBatchDO);
}
