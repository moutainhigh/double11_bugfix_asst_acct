package com.yuwang.pinju.core.order.manager.check;

import java.util.Date;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * Created on 2011-7-19
 * @see
 * <p>Discription: 结算生成,订单生成等相关效验方法</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderCheckManager {
	
	/**
	 * Created on 2011-6-18
	 * <p>效验订单是否属于该买家</p>
	 * @param orderId 订单编号
	 * @param buyerId 买家编号
	 * @return 是返回ture
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean isBuyerOrder(Long orderId,Long buyerId)throws ManagerException;
	
	/**
	 * Created on 2011-8-24
	 * <p>Discription: 效验订单是否属于该卖家</p>
	 * @param orderId 订单编号
	 * @param sellerId  卖家编号
	 * @return  是返回true
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean isSellerOrder(Long orderId,Long sellerId)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-7-21
	 * <p>Discription: 效验买家与送货地址的关系
	 * </p>
	 * @param memberDeliveryDO
	 * @param buyerId
	 * @return 成功返回true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean buyerDelivery(MemberDeliveryDO memberDeliveryDO,Long buyerId)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-7-27
	 * <p>Discription: 
	 * 1.效验当前时间是否属于活动
	 * 2.效验购买数量
	 * </p>
	 * @param startTime 开始时间
	 * @param endTime 活动结束时间
	 * @param lastTimeNum 限时折扣可购数量
	 * @param buyNum 已够数量
	 * @return 
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result checkLastTimeBuy(Date startTime,Date endTime,Long lastTimeNum,Long buyNum)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-9-23
	 * <p>Discription: [团购限时折扣效验]</p>
	 * @param orderCreation
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result checkLastTimeBuy(OrderCreationVO orderCreation) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription:结算生成效验 
	 * 1.效验会员状态有效性
	 * 2.效验商品id
	 * 3.效验商品所属店铺有效性
	 * 4.效验商品有效性
	 * 5.有skuId,效验商品skuId有效性
	 * 7.商品库存数量(有skuId的话,效验skuDO对象中的数量,没有效验ItemDO对象中的数量)
	 * </p>
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result checkCreateOrder(MemberDO buyerMemberDO,MemberDO sellerMemberDO,ShopInfoDO shopInfoDO,ItemDO itemDO,SkuDO skuDO,Long buyNum)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-9-23
	 * <p>Discription: [限购效验]</p>
	 * @param orderCreation
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result checkXianGuo(OrderCreationVO orderCreation);
	
	/**
	 * 
	 * Created on 2011-9-23
	 * <p>Discription: [活动效验]</p>
	 * @param orderCreation
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result activityCheck(OrderCreationVO orderCreation) throws ManagerException;
	
	
	
	
	/**
	 * 
	 * Created on 2011-9-23
	 * <p>Discription: [优惠券效验]</p>
	 * @param orderCreation
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result couponCheck(OrderCreationVO orderCreation) throws ManagerException;
	
	
}

