package com.yuwang.pinju.domain.order.query;

import com.yuwang.pinju.common.StringUtil;




/**
 * <p>
 * 生成订单相关
 * </p>
 * 
 * @author 杜成
 * @date 2011-7-9
 * @version 1.0
 */
public class OrderCreationVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -904731887160723294L;
	/**
	 * 商品编号
	 */
	private Long[] itemId;
	/**
	 * 数量
	 */
	private Long[] num;
	/**
	 * 价格
	 */
	private Long[] price;
	/**
	 * 商品Sku编号
	 */
	private Long[] itemSkuId;
	/**
	 * 商品Sku描述
	 */
	private String[] itemSkuAttributes;
	/**
	 * 买家会员编号
	 */
	private Long buyerMemberId;

	/**
	 * 买家备注
	 */
	private String[] buyerMemo;

	/**
	 * 卖家编号
	 */
	private Long[] sellerId;

	/**
	 * 送货地址
	 */
	private Long memberDeliveryId;

	/**
	 * 交易类型
	 */
	private Integer[] bussinessType;
	/**
	 * 物流价格
	 */
	private Long[] logisticsPrice;
	/**
	 * 分销商编号
	 */
	private String[] channelId;
	
	/**
	 * 用来做物流价格与订单备注定位
	 */
	private String[] orderKey;
	/**
	 * 物流类型 1平邮 2快递 3EMS 4卖家包邮
	 */
	private Integer[] logisticsMode;
	
	/**
	 * 匿名购买
	 */
	private Long[] anonymousBuy;
	
	/**
	 * 限购码
	 */
	private String[] discountCode;
	
	/**
	 * 广告编号
	 */
	private String[] ad;
	
	/**
	 * 优惠券
	 */
	private Long[] couponId;
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到匿名购买</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long isAnonymousBuy(long itemIdkey){
		if (anonymousBuy!=null) {
			for(Long anonymous_id :anonymousBuy){
				if(anonymous_id.compareTo(itemIdkey)==0){
					return 2L;
				}
			}
		}
		return 1L;
	}
	
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到商品编号对应的优惠券ID</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long getCouponId(long itemIdkey) {
		if (this.couponId != null && this.orderKey != null) {
			for (int i = 0; i < this.orderKey.length; i++) {
				if (orderKey[i].indexOf(String.valueOf(itemIdkey)) > -1){
					return couponId[i];
				}
			}
		}
		return 0l;
	}
	
	
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到商品编号对应的买家备注</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getMemo(long itemIdkey) {
		if (this.buyerMemo != null && this.orderKey != null) {

			for (int i = 0; i < this.orderKey.length; i++) {
				if (orderKey[i].indexOf(String.valueOf(itemIdkey)) > -1){
					return buyerMemo[i];
				}
			}
		}
		return "";
	}
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到商品编号对应的物流类型</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Integer getLogisticsMode(long itemIdkey) {
		if (this.logisticsMode != null && this.orderKey != null) {

			for (int i = 0; i < this.orderKey.length; i++) {
				if (orderKey[i].indexOf(String.valueOf(itemIdkey)) > -1){
					return logisticsMode[i];
				}
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到商品编号对应的物流价格</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long getLogisticsPrice(long itemIdkey) {
		if (this.logisticsPrice != null && this.orderKey != null) {
			for (int i = 0; i < this.orderKey.length; i++) {
				if (orderKey[i].indexOf(String.valueOf(itemIdkey)) > -1){
					return logisticsPrice[i];
				}
			}
		}
		return 0L;
	}

	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到广告编号</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getAd(int size){
		if (itemId != null && ad!=null) {
				if(ad.length>size){
					return ad[size];
				}
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到分销商编号</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getChannelId(int size){
		if (itemId != null && channelId!=null) {
				if(channelId.length>size){
					return channelId[size];
				}
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到商品对应的购买数量</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long getItemBuyNum(int size){
		if (itemId != null && num!=null) {
				if(num.length>size){
					return num[size];
				}
		}
		return null;
	}
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到商品对应的购买类型</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Integer getBussinessType(int size){
		if (itemId != null && bussinessType!=null) {
				if(bussinessType.length>size){
					return bussinessType[size];
				}
		}
		return null;
	}
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到商品对应的SKUID</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long getItemSkuId(int size){
		if (itemId != null && itemSkuId!=null) {
				if(itemSkuId.length>size){
					return itemSkuId[size];
				}
		}
		return null;
	}
	
	/**
	 * 
	 * Created on 2011-09-02
	 * <p>Discription: 得到限购码</p>
	 * @return
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getXianGuoCode(){
		if(discountCode != null ){
			if(StringUtil.isNotEmpty(discountCode[0])){
				return discountCode[0];
			}
		}
		return "";
	}
	
	

	
	/**
	 * 效验基本参数
	 */
	public boolean check() {
		return this != null && this.itemId != null && this.num != null && this.memberDeliveryId != null ? true: false;                   
	}

	public Long[] getItemId() {
		return itemId;
	}

	public void setItemId(Long[] itemId) {
		this.itemId = itemId;
	}

	public Long[] getNum() {
		return num;
	}

	public void setNum(Long[] num) {
		this.num = num;
	}

	public Long[] getPrice() {
		return price;
	}

	public void setPrice(Long[] price) {
		this.price = price;
	}

	public Long[] getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long[] itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public String[] getItemSkuAttributes() {
		return itemSkuAttributes;
	}

	public void setItemSkuAttributes(String[] itemSkuAttributes) {
		this.itemSkuAttributes = itemSkuAttributes;
	}

	public String[] getBuyerMemo() {
		return buyerMemo;
	}

	public void setBuyerMemo(String[] buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public Long[] getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long[] sellerId) {
		this.sellerId = sellerId;
	}

	public Integer[] getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(Integer[] bussinessType) {
		this.bussinessType = bussinessType;
	}

	public Long getMemberDeliveryId() {
		return memberDeliveryId;
	}

	public void setMemberDeliveryId(Long memberDeliveryId) {
		this.memberDeliveryId = memberDeliveryId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getBuyerMemberId() {
		return buyerMemberId;
	}

	public void setBuyerMemberId(Long buyerMemberId) {
		this.buyerMemberId = buyerMemberId;
	}

	public Long[] getLogisticsPrice() {
		return logisticsPrice;
	}

	public void setLogisticsPrice(Long[] logisticsPrice) {
		this.logisticsPrice = logisticsPrice;
	}

	public String[] getChannelId() {
		return channelId;
	}

	public void setChannelId(String[] channelId) {
		this.channelId = channelId;
	}

	public String[] getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String[] orderKey) {
		this.orderKey = orderKey;
	}

	public Integer[] getLogisticsMode() {
		return logisticsMode;
	}

	public void setLogisticsMode(Integer[] logisticsMode) {
		this.logisticsMode = logisticsMode;
	}

	public Long[] getAnonymousBuy() {
		return anonymousBuy;
	}

	public void setAnonymousBuy(Long[] anonymousBuy) {
		this.anonymousBuy = anonymousBuy;
	}

	public String[] getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String[] discountCode) {
		this.discountCode = discountCode;
	}

	public String[] getAd() {
		return ad;
	}

	public void setAd(String[] ad) {
		this.ad = ad;
	}

	public Long[] getCouponId() {
		return couponId;
	}

	public void setCouponId(Long[] couponId) {
		this.couponId = couponId;
	}
	
	
}
