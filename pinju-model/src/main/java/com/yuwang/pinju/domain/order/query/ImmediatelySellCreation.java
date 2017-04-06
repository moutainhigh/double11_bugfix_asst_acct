package com.yuwang.pinju.domain.order.query;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;

/**
 * Created on 2011-7-15
 * <p>
 * Discription: 立即结算页面生成
 * </p>
 * 
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class ImmediatelySellCreation implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6173075950008785394L;
	/**
	 * 买家收货地址列表
	 */
	private List<MemberDeliveryDO> buyMemberDeliveryList;
	/**
	 * 买家
	 */
	private MemberDO buyMemberDO;
	/**
	 * 商品提示信息
	 */
	private String message;
	/**
	 * 商品所属卖家
	 */
	private MemberDO sellerMember;
	/**
	 * 要下单的商品
	 */
	private ItemDO itemDO;

	/**
	 * 可购买数量
	 */
	private Long lastTimeNum = 0L;
	/**
	 * 已买数量
	 */
	private Long buyNum = 0L;
	/**
	 * 折扣率
	 */
	private Long dicountRate = 0L;

	/**
	 * 优惠列表
	 */
	private List<Map<Long, String>> rateList;

	private Long skuId;

	private String skuAttributes;
	/**
	 * 购买类型
	 */
	private Integer bussinessType;
	/**
	 * 分销商编号
	 */
	private String channelId = "";
	/**
	 * 是否特供码商品
	 */
	private String discountCode;
	
	/**
	 * 购买数量
	 */
	private Long buyCount;
	
	/**
	 * 得到广告编号
	 */
	private String ad;
	
	private Integer shopId;
	
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MemberDO getSellerMember() {
		return sellerMember;
	}

	public void setSellerMember(MemberDO sellerMember) {
		this.sellerMember = sellerMember;
	}

	public ItemDO getItemDO() {
		return itemDO;
	}

	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
	}

	public List<Map<Long, String>> getRateList() {
		return rateList;
	}

	public void setRateList(List<Map<Long, String>> rateList) {
		this.rateList = rateList;
	}

	public Long getLastTimeNum() {
		return lastTimeNum;
	}

	public void setLastTimeNum(Long lastTimeNum) {
		this.lastTimeNum = lastTimeNum;
	}

	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}

	public Long getDicountRate() {
		return dicountRate;
	}

	public void setDicountRate(Long dicountRate) {
		this.dicountRate = dicountRate;
	}

	
	/**
	 * 折扣后价格
	 */
	public String getRatePrice() {
		return MoneyUtil.getCentToDollar(itemDO.getPrice());
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getSkuAttributes() {
		return skuAttributes;
	}

	public void setSkuAttributes(String skuAttributes) {
		this.skuAttributes = skuAttributes;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(Integer bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public Long getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Long buyCount) {
		this.buyCount = buyCount;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}



}
