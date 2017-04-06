package com.yuwang.pinju.domain.order.query;

import java.util.List;
import java.util.Map;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;


/**
 * Created on 2011-7-15
 * <p>
 * Discription: 购物车结算页面生成
 * </p>
 * 
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class CartSellCreation {
	/**
	 * 买家收货地址列表
	 */
	private List<MemberDeliveryDO> buyMemberDeliveryList;
	/**
	 * 买家
	 */
	private MemberDO buyMemberDO;
	/**
	 * 商品效验错误信息集合
	 */
	private Map<Long, String> errorItemMap;
	
	
	/**
	 * 商品所属卖家集合
	 */
	private List<MemberDO> sellerMemberList;
	/**
	 * 要下单的商品集合
	 */
	private Map<ItemDO, OrderItemDO> itemMap;
	

	/**
	 * 拆单后的订单集合
	 */
	private Map<OrderDO,List<OrderItemDO>> orderMap;
	/**
	 * 当前订单中所有商品的集合
	 */
	private List<ItemDO> itemDOList;
	
	/**
	 * 订单总价
	 */
	private Long sumPrice;
	
	public List<MemberDeliveryDO> getBuyMemberDeliveryList() {
		return buyMemberDeliveryList;
	}

	public void setBuyMemberDeliveryList(
			List<MemberDeliveryDO> buyMemberDeliveryList) {
		this.buyMemberDeliveryList = buyMemberDeliveryList;
	}

	public MemberDO getBuyMemberDO() {
		return buyMemberDO;
	}

	public void setBuyMemberDO(MemberDO buyMemberDO) {
		this.buyMemberDO = buyMemberDO;
	}

	public Map<Long, String> getErrorItemMap() {
		return errorItemMap;
	}

	public void setErrorItemMap(Map<Long, String> errorItemMap) {
		this.errorItemMap = errorItemMap;
	}

	public List<MemberDO> getSellerMemberList() {
		return sellerMemberList;
	}

	public void setSellerMemberList(List<MemberDO> sellerMemberList) {
		this.sellerMemberList = sellerMemberList;
	}

	public Map<ItemDO, OrderItemDO> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<ItemDO, OrderItemDO> itemMap) {
		this.itemMap = itemMap;
	}

	public Long getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Long sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Map<OrderDO, List<OrderItemDO>> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<OrderDO, List<OrderItemDO>> orderMap) {
		this.orderMap = orderMap;
	}

	public List<ItemDO> getItemDOList() {
		return itemDOList;
	}

	public void setItemDOList(List<ItemDO> itemDOList) {
		this.itemDOList = itemDOList;
	}

}
