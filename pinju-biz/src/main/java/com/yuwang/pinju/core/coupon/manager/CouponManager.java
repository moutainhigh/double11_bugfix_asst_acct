package com.yuwang.pinju.core.coupon.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.coupon.TradeCouponQueryDO;

public interface CouponManager {
	/**
	 * 保存优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 保存的优惠券id
	 */
	public Long saveTradeCoupon(TradeCouponDO tradeCouponDO)throws ManagerException;
	
	
	/**
	 * 更新优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新的信息条数
	 */
	public Integer updateTradeCoupon(TradeCouponDO tradeCouponDO)throws ManagerException;
	
	/**
	 * 更新优惠券关联的订单信息
	 * 
	 * @param couponId
	 * @param orderId
	 * 
	 * @return true表示更新成功
	 */
	public Boolean updateCouponOrder(Long couponId, Long orderId)throws ManagerException;
	

	/**
	 * 买家领取优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return true表示领取成功
	 */
	public Boolean buyerGetCoupon(final TradeCouponDO tradeCouponDO)throws ManagerException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 获取优惠券记录</p>
	 * @param queryDO
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<TradeCouponDO> getTradeCouponDOList(TradeCouponQueryDO queryDO) throws ManagerException;
	
	/**
	 * Created on 2011-11-25
	 * <p>Discription: 根据买家id和店铺id,获取买家能使用的优惠券</p>
	 * @param buyerId
	 * @param shopId
	 * @param couponMoney
	 * @return
	 * @throws ManagerException
	 * @author:[石洪波]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<TradeCouponDO> getValidCoupon(Long buyerId, Long shopId) throws ManagerException;

	
	/**
	 * Created on 2011-11-24
	 * <p>Discription:获取优惠券记录数 </p>
	 * @param queryDO
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int getTradeCouponDOListNum(TradeCouponQueryDO queryDO) throws ManagerException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 根据优惠券ID获取优惠券信息</p>
	 * @param id
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public TradeCouponDO getTradeCouponDOById(Long id)throws ManagerException;
	
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
	public TradeCouponDO getTradeCouponDOByOrderId(Long orderId)throws ManagerException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 买家“删除”已使用或已过期的优惠券</p>
	 * @param couponDO
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int buyerDeleteCoupon(TradeCouponDO couponDO)throws ManagerException;
	
	/**
	 * Created on 2011-11-25
	 * <p>Discription: 获取优惠券详情</p>
	 * @param couponDO
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public TradeCouponDO getTradeCouponDO(TradeCouponDO couponDO)throws ManagerException;
}
