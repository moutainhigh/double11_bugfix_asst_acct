package com.yuwang.pinju.core.order.manager.check.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.Constant.ItemXianGouConstant;
import com.yuwang.pinju.common.ItemXianGouUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.active.manager.ActivityDiscountManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.common.resultcode.XianGouResultCode;
import com.yuwang.pinju.core.constant.activity.ActivityConstant;
import com.yuwang.pinju.core.coupon.manager.CouponManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.member.dao.MemberAsstDAO;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.order.manager.helper.OrderDiscountManager;
import com.yuwang.pinju.core.order.manager.helper.impl.OrderUtilMothed;
import com.yuwang.pinju.core.sales.manager.ItemXianGouManager;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * Created on 2011-7-19
 * 
 * @see <p>
 *      Discription:
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderCheckManagerImpl extends BaseManager implements
		OrderCheckManager {

	private OrderQueryDAO orderQueryDAO;

	private ItemXianGouManager itemXianGouManager;
	
	private OrderDiscountManager orderDiscountManager;
	
	private ItemManager itemManager;
	
	private ActivityDiscountManager activityDiscountManager;
	
	private OrderQueryManager orderQueryManager;
	
	private CouponManager couponManager;
	
	private MemberAsstDAO memberAsstDAO;
	
	@Override
	public boolean isBuyerOrder(Long orderId, Long buyerId)
			throws ManagerException {
		try {
			OrderDO orderDO = orderQueryDAO.queryWriteOrder(orderId);
			if(orderDO != null){
				return orderDO.getBuyerId().compareTo(
						buyerId) == 0 ? true : false;
			}
			return false;
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public boolean isSellerOrder(Long orderId, Long sellerId)
			throws ManagerException {
		try {
			boolean flag = false ;
			OrderDO orderDO = orderQueryDAO.queryOrder(orderId);
			if(orderDO != null){
				flag =  orderDO.getSellerId().compareTo(
						sellerId) == 0 ? true : false;
				if(!flag){
					//根据子账号  获取父账号信息
					MemberAsstRelationDO memberAsstRelationDO = memberAsstDAO.getMemberAsstRalationByAsst(sellerId);
					if(memberAsstRelationDO!=null){
						flag = memberAsstRelationDO.getMasterMemberId().compareTo(orderDO.getSellerId()) == 0 ? true : false;
					}
				}
			}
			return flag;
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public boolean buyerDelivery(MemberDeliveryDO memberDeliveryDO, Long buyerId)throws ManagerException{
		return memberDeliveryDO.getMemberId().compareTo(buyerId) == 0 ? true
				: false;
	}
	/**
	 *判断登录账号是否为子账号 
	 *Add By LiXin 2012-01-04
	 * @throws DaoException 
	 */
	public boolean isAssMember(Long memberId) throws DaoException{
		MemberAsstRelationDO memberAsstRelationDO = memberAsstDAO.getMemberAsstRalationByAsst(memberId);
		if(memberAsstRelationDO != null){
			 return true;
		}
		return false;
	}
	@Override
	public Result checkCreateOrder(MemberDO buyerMemberDO,
			MemberDO sellerMemberDO, ShopInfoDO shopInfoDO, ItemDO itemDO,
			SkuDO skuDO, Long buyNum) throws ManagerException {
		Result result = null;
		
		try {
			if(this.isAssMember(buyerMemberDO.getMemberId())){
				result = this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_ACCOUNT_FAIL);
				return result;
			}
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
		// 效验卖家与买家是否同一个用户
		if (this.isBuySameSell(buyerMemberDO, sellerMemberDO)) {
			return this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_BUYER_SAME_SELLER_FAIL);
		} 
		// 买家账户有效性
		if (!this.isAvailabilityBuyerMember(buyerMemberDO)) {
			return this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_BUYERMEMBER_FAIL);
		} 
		// 卖家账户有效性
		if (!this.isAvailabilitySellerMember(sellerMemberDO)) {
			return this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_SELLERMEMBER_FAIL);
		}
		// 店铺有效性
		if (shopInfoDO != null && shopInfoDO.getApproveStatus() != null) {
			if (!this.isAvailabilityShop(shopInfoDO))
				return this
						.setErrorReturnCode(TradeResultCode.ORDER_CHECK_SHOP_FAIL);
		}
		// 商品有效性
		if (!this.isAvailabilityItem(itemDO)) {
			return this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_ITEMSTATE_FAIL);
		} 
		// 商品购买数量
		if (skuDO != null) {
			//sku有效性
			if (!this.isAvailabilitySku(skuDO, itemDO.getId()))
				return this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_SKUTOITEM_FAIL);
			// sku购买数量
			if (!this.isAvailabilitySkuItemNum(skuDO, buyNum))
				return this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_BUYNUM_FAIL);
		} else {
			//效验无sku商品库存
			if(!this.isAvailabilityItemNum(itemDO, buyNum)) 
				return this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_BUYNUM_FAIL);
		}
		
		
		return result != null ? result : new ResultSupport();
	}
	/**
	 * 
	 * Created on 2011-8-31
	 * <p>Discription: 活动效验</p>
	 * @param orderCreation 生成订单相关对象
	 * @return
	 * @author:[贺泳]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result activityCheck(OrderCreationVO orderCreation) throws ManagerException{
		Result checkResult = new ResultSupport();
		//效验限购
		checkResult = checkXianGuo(orderCreation);
		if(!checkResult.isSuccess())
			return checkResult;
		//限时折扣效验
		checkResult = checkLastTimeBuy(orderCreation);
		
		return checkResult;
	}
	
	
	
	@Override
	public Result checkLastTimeBuy(OrderCreationVO orderCreation) throws ManagerException{
		Result checkResult = new ResultSupport();
		int size = 0;
		for(Long itemId : orderCreation.getItemId()){
			ItemDO itemDO = itemManager.getItemDOById(itemId);
			int bussinessType = orderCreation.getBussinessType(size);
			if(OrderUtilMothed.isDicountRate(bussinessType)||OrderUtilMothed.isGroup(bussinessType))
			{

				String activeInfoById = itemDO
						.getFeatureByEnum(ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT);
			
				if (activeInfoById != null) {
					// 得到活动信息
					Map<String, Object> maps = activityDiscountManager
							.getActivityDiscountInfo(Long
									.valueOf(activeInfoById), itemDO.getId());
					if (maps != null) {
						 Long status = (Long) maps.get(ActivityConstant.STATUS);
						 if(status!=1){
							 checkResult = setErrorReturnCode(TradeResultCode.ORDER_CHECK_LASTTIME_BUYTIME_FAIL);
						 }
							
							Date endDate = (Date) maps.get(ActivityConstant.END_TIME);
							Date startDate = (Date) maps.get(ActivityConstant.START_TIME);
							//可购数量
							String limitNum = (String) maps.get(ActivityConstant.LIMIT_COUNT);
							if(!StringUtil.isNumeric(limitNum)){
								 checkResult = setErrorReturnCode(TradeResultCode.ORDER_CHECK_LASTTIME_BUYTIME_FAIL);
								 return checkResult;
							}
								
							//已买数量
							long buyNum = this.queryBuyNum(orderCreation.getBuyerMemberId(), itemId, startDate, endDate);
							
							checkResult = checkLastTimeBuy(startDate, endDate, Long.valueOf(limitNum), buyNum);
							if(!checkResult.isSuccess()){
								checkResult.setModel("errorItemId", itemId);
							}
					}
				}
			}
			size++;	
		}
		return checkResult;
	}
	
	
	
	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription: 封装返回值
	 * </p>
	 * 
	 * @param result
	 * @param flag
	 * @param code
	 * @param itemId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Result setErrorReturnCode(String code) {
		Result result = new ResultSupport();
		result.setSuccess(false);
		result.setResultCode(code);
		return result;
	}


	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription: 效验商品所属店铺有效性 ApproveStatus -1 无效
	 * </p>
	 * 
	 * @param shopId
	 * @return 有效返回true
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isAvailabilityShop(ShopInfoDO shopInfoDO)
			throws ManagerException {
		return shopInfoDO.getApproveStatus().compareTo(-1) == 0 ? false : true;
	}

	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription: 效验买家账户有效性
	 * </p>
	 * 
	 * @param memberId
	 * @return 有效返回true
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isAvailabilityBuyerMember(MemberDO buyerMemberDO)
			throws ManagerException {
		if (buyerMemberDO == null)
			return false;
		/**
		 * TODO
		 * <p>
		 * 会员账户有效性目前没有实现,请耐心等待后续。。。 杜成 2011-07-19
		 * </p>
		 */
		return true;
	}

	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription: 效验卖家账户有效性
	 * </p>
	 * 
	 * @param sellerMemberId
	 * @return 有效返回true
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isAvailabilitySellerMember(MemberDO sellerMemberDO)
			throws ManagerException {
		if (sellerMemberDO == null)
			return false;
		/**
		 * TODO
		 * <p>
		 * 会员账户有效性目前没有实现,请耐心等待后续。。。 杜成 2011-07-19
		 * </p>
		 */
		return true;
	}

	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription:效验商品状态有效性 Status 0会员上架,1运营上架
	 * </p>
	 * 
	 * @param itemId
	 * @return 有效返回true
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isAvailabilityItem(ItemDO itemDO) throws ManagerException {
		Long flag = itemDO.getStatus();
		return itemDO != null
				&& (flag.compareTo(1L) == 0 || flag.compareTo(0L) == 0);
	}

	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription: 效验商品sku库存数量
	 * </p>
	 * 
	 * @param itemDO
	 * @return 有效返回true
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isAvailabilitySkuItemNum(SkuDO skuDO, Long buyNum)
			throws ManagerException {
		return buyNum>0 && skuDO.getCurrentStock().compareTo(buyNum)>= 0 ;
	}

	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription: 效验商品库存数量
	 * </p>
	 * 
	 * @param skuDO
	 * @param buyNum
	 * @return 有效返回true
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isAvailabilityItemNum(ItemDO itemDO, Long buyNum)
			throws ManagerException {
		return  buyNum>0 && itemDO.getCurStock().compareTo(buyNum)>= 0 ;
	}

	/**
	 * 
	 * Created on 2011-8-8
	 * <p>
	 * Discription: 效验卖家与买家是否同一个ID
	 * </p>
	 * 
	 * @param buyer
	 * @param seller
	 * @return 同一个会员返回true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isBuySameSell(MemberDO buyer, MemberDO seller) {
		return buyer.getMemberId().compareTo(seller.getMemberId()) == 0;
	}

	/**
	 * 
	 * Created on 2011-7-19
	 * <p>
	 * Discription: sku有效性
	 * </p>
	 * 
	 * @param itemDO
	 * @param skuId
	 * @return 有效返回true
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isAvailabilitySku(SkuDO skuDO, long itemId)
			throws ManagerException {
		return skuDO != null && skuDO.getItemId().compareTo(itemId) == 0 ;
	}

	@Override
	public Result checkLastTimeBuy(Date startTime, Date endTime,
			Long lastTimeNum, Long buyNum) throws ManagerException{
		Result result = null;
		/** 添加限时折扣活动,限购数量为不限购默认传值为0的判断  ducheng@zba.com 2011-10-10 */
		if (lastTimeNum.compareTo(0L)!=0 && buyNum.compareTo(lastTimeNum)>0)
			result = this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_LASTTIME_BUYNUM_FAIL);
		if(System.currentTimeMillis()>endTime.getTime())
			result = this.setErrorReturnCode(TradeResultCode.ORDER_CHECK_LASTTIME_BUYTIME_FAIL);
		return result != null ? result : new ResultSupport();
	}
	
	public void setOrderQueryDAO(OrderQueryDAO orderQueryDAO) {
		this.orderQueryDAO = orderQueryDAO;
	}

	@Override
	public Result checkXianGuo(OrderCreationVO orderCreation) {
		Result checkResult = new ResultSupport();
		try {
			//特供码
			if(StringUtil.isNotEmpty(orderCreation.getXianGuoCode())){
				/******用户领取的限购码是否可以买该商品Add By ShiXing@2011.09.16 ******/
				Long code;
				try {
					code = ItemXianGouUtil.decode(orderCreation.getXianGuoCode());
				} catch (Exception e) {
					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_CHECKCODE_FAIL);
					return checkResult;
				}
				ItemXianGouUseDO itemXianGouUseDO = itemXianGouManager.getItemXianGouUseDOByCode(code);
				if (itemXianGouUseDO==null) {
					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_CHECKCODE_FAIL);
					return checkResult;
				}
				if (itemXianGouUseDO.getCodeSource()==null || !itemXianGouUseDO.getCodeSource().equals(orderCreation.getXianGuoCode())) {
					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_CHECKCODE_ILLEGAL);
					return checkResult;
				}
				
//				if (!itemXianGouUseDO.getItemId().equals(orderCreation.getItemId()[0])) {
//					//用户领取的限购码是否可以买该商品
//					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_CODE_IS_NOT_APPLY_THIS_ITEM);
//					checkResult.setModel("itemid", itemXianGouUseDO.getItemId());
//					return checkResult;
//				}
				/****** ShiXing End! ******/
				// 参数校验
				ItemXianGouDO xianGouDO = itemXianGouManager.getItemXianGouDOByItemId(orderCreation.getItemId()[0]);
				
				//第一次活动的限购码第二次不能使用
//				int version = xianGouDO.getVersion();
//				long ver = itemXianGouUseDO.getVersion();
//				if (version>ver) {
//					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_OUT_OF_DATE);
//					return checkResult;
//				}
				
				//校验数量
				itemXianGouUseDO.setItemId(xianGouDO.getItemId());
				itemXianGouUseDO.setBatchNum(200);
				itemXianGouUseDO.setIsUse(ItemXianGouConstant.XIANGOU_CODE_HAD_USE);
				Long count = itemXianGouManager.getItemXianGouUseCount(itemXianGouUseDO);
				Long total = xianGouDO.getCount();
				Long free = total - count;
				if (free<=0)
                {
				    OrderUtilMothed.setResult(false, checkResult, TradeResultCode.ORDER_CHECK_LASTTIME_BUYNUM_FAIL);
                    return checkResult;
                }
				
				// 取得限购活动的状态：0-已开始 1-已结束
				if (xianGouDO == null || xianGouDO.getStatus() == 1) {
					OrderUtilMothed.setResult(false, checkResult, TradeResultCode.ORDER_CHECK_DISCOUNTCODE_FAIL);
					return checkResult;
				}
				// 活动开始时间
				Date startDate = xianGouDO.getExpiryStart();
				// 活动结束时间
				Date endDate = xianGouDO.getExpiryEnd();

				if (!OrderUtilMothed.compareDate(startDate, endDate)) {
					OrderUtilMothed.setResult(false, checkResult,TradeResultCode.ORDER_CHECK_DISCOUNTCODE_END);
				} else {
					checkResult = orderDiscountManager.compareCodeIsNull(orderCreation.getXianGuoCode());
				}
			}
		} catch (ManagerException e) {
			OrderUtilMothed.setResult(false, checkResult,TradeResultCode.ORDER_CHECK_DISCOUNTCODE_FAIL);
		}
		return checkResult;
	}
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * Discription: 获取某个商品在某个时间段的购买数量
	 * </p>
	 * 
	 * @param buyerId
	 * @param itemId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Long queryBuyNum(Long buyerId, Long itemId, Date startDate,
			Date endDate) throws ManagerException {
		QueryOrderItem queryOrderItem = new QueryOrderItem(buyerId, itemId,
				new int[] { OrderItemDO.ORDER_ITEM_TYPE_3,
						OrderItemDO.ORDER_ITEM_TYPE_4 }, new int[] {
						OrderItemDO.ORDER_ITEM_STATE_1,
						OrderItemDO.ORDER_ITEM_STATE_2,
						OrderItemDO.ORDER_ITEM_STATE_3,
						OrderItemDO.ORDER_ITEM_STATE_5 }, startDate, endDate);
	
		List<OrderItemDO> list =orderQueryManager.queryOrderItemDOList(queryOrderItem);
		Long buyNum = 0L;
		for(OrderItemDO orderItemDO : list){
			buyNum += orderItemDO.getBuyNum();
		}
		return buyNum;
	}

	public void setItemXianGouManager(ItemXianGouManager itemXianGouManager) {
		this.itemXianGouManager = itemXianGouManager;
	}

	public void setOrderDiscountManager(OrderDiscountManager orderDiscountManager) {
		this.orderDiscountManager = orderDiscountManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setActivityDiscountManager(
			ActivityDiscountManager activityDiscountManager) {
		this.activityDiscountManager = activityDiscountManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	@Override
	public Result couponCheck(OrderCreationVO orderCreation)
			throws ManagerException {
		Result checkResult = new ResultSupport();
		checkResult.setSuccess(true);
		if(orderCreation.getCouponId()==null||orderCreation.getCouponId().length==0){
			return checkResult;
		}
		TradeCouponDO tradeCouponDO = null;
		for(Long couponId:orderCreation.getCouponId()){
			if(couponId.compareTo(0l)==0){
				continue;
			}
			tradeCouponDO = couponManager.getTradeCouponDOById(couponId);
			if(tradeCouponDO==null){
				OrderUtilMothed.setResult(false, checkResult, TradeResultCode.ORDER_CHECK_COUPON_ATTRIBUTION_FAIL);
			}else if(tradeCouponDO.getUseStatus().compareTo(TradeCouponDO.COUPON_USED)==0){
				OrderUtilMothed.setResult(false, checkResult, TradeResultCode.ORDER_CHECK_COUPON_USED_FAIL);
			}else if(tradeCouponDO.getCouponStatus().compareTo(10)!=0){
				OrderUtilMothed.setResult(false, checkResult, TradeResultCode.ORDER_CHECK_COUPON_STATE_FAIL);
			}else if(tradeCouponDO.getBuyerId().compareTo(orderCreation.getBuyerMemberId())!=0){
				OrderUtilMothed.setResult(false, checkResult, TradeResultCode.ORDER_CHECK_COUPON_ATTRIBUTION_FAIL);
			}
		}
		return checkResult;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

	public void setMemberAsstDAO(MemberAsstDAO memberAsstDAO) {
		this.memberAsstDAO = memberAsstDAO;
	}
	
	
	
}
