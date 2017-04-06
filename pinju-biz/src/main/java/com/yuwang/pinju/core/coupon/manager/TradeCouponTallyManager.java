package com.yuwang.pinju.core.coupon.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.coupon.TradeCouponTallyDO;

public interface TradeCouponTallyManager {
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 新增买家领取优惠券计数表</p>
	 * @param tctd
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long addTradeCouponTally(TradeCouponTallyDO tctd)throws ManagerException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription:修改  优惠券领取记录 </p>
	 * @param tctd
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateTradeCouponTally(TradeCouponTallyDO tctd)throws ManagerException;
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription:查询买家领取优惠券数量 </p>
	 * @param batchId 批次id
	 * @param buyerId 买家id
	 * @return
	 * @throws ManagerException
	 * @author:[石洪波]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public TradeCouponTallyDO loadCouponTally(Long batchId, Long buyerId)throws ManagerException;
}
