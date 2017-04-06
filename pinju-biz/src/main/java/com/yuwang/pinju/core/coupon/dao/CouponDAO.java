package com.yuwang.pinju.core.coupon.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.coupon.TradeCouponQueryDO;

public interface CouponDAO {
	/**
	 * 保存优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新或者保存的优惠券id
	 */
	public Long saveTradeCoupon(TradeCouponDO tradeCouponDO)throws DaoException;
	
	/**
	 * 更新优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新的记录条数
	 */
	public Integer updateTradeCoupon(TradeCouponDO tradeCouponDO)throws DaoException;
	
	/**
	 * 更新优惠券关联的订单信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新的记录条数
	 */
	public Integer updateCouponOrder(TradeCouponDO tradeCouponDO)throws DaoException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription:获取优惠券信息记录 </p>
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<TradeCouponDO> getTradeCouponDOs(TradeCouponQueryDO queryDO) throws DaoException;
	
	/**
	 * Created on 2011-11-25
	 * <p>Discription: 根据买家id和店铺id,获取买家能使用的优惠券</p>
	 * @param buyerId
	 * @param shopId
	 * @return
	 * @throws ManagerException
	 * @author:[石洪波]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<TradeCouponDO> getValidCoupon(TradeCouponDO tradeCouponDO) throws DaoException;
	
	/**
	 * Created on 2011-11-25
	 * <p>Discription: 获取超时的优惠券</p>
	 * @return
	 * @throws ManagerException
	 * @author:[石洪波]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<TradeCouponDO> getTimeoutCoupon() throws DaoException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription:获取优惠券信息记录数 </p>
	 * @param queryDO
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int getTradeCouponDOsNum(TradeCouponQueryDO queryDO) throws DaoException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 根据优惠券ID获取优惠券信息</p>
	 * @param Id
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public TradeCouponDO getTradeCouponDOByID(Long Id)throws DaoException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 根据订单ID获取优惠券信息</p>
	 * @param orderId
	 * @return
	 * @throws ManagerException
	 * @author:[石洪波]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public TradeCouponDO getTradeCouponDOByOrderId(Long orderId)throws DaoException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 买家“删除”已使用或已过期的优惠券</p>
	 * @param couponDO
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int buyerDeleteCoupon(TradeCouponDO couponDO) throws DaoException;
	
	/**
	 * Created on 2011-11-25
	 * <p>Discription: 获取优惠券信息</p>
	 * @param couponDO
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public TradeCouponDO getTradeCouponDO(TradeCouponDO couponDO)throws DaoException;
}
