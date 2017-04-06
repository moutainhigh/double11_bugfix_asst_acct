package com.yuwang.pinju.domain.rate;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.rate.comment.RateOrderModel.DsrComments;

/**
 * <p>买家购买后评价</p>
 *
 * @author gaobaowen
 * @since 2011-10-12 09:22:39
 */
public class DsrCommentDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否匿名 -- 不匿名（0）（默认）
     */
    public final static Integer ANONYMOUS_NO = 0;

    /**
     * 是否匿名 -- 匿名（1）
     */
    public final static Integer ANONYMOUS_YES = 1;

    /**
     * 是否分享到 SNS -- 分享（0）（默认）
     */
    public final static Integer SHARE_SNS_YES = 0;

    /**
     * 是否分享到 SNS -- 不分享（1）
     */
    public final static Integer SHARE_SNS_NO = 1;

    /**
     * 评论状态 -- 有效（0）（默认）
     */
    public final static Integer STATUS_VALID = 0;

    /**
     * 评论状态 -- 无效（1）
     */
    public final static Integer STATUS_INVALID = 1;


    /**
     * 编号
     */
    private Long id;

    /**
     * 买家会员编号
     */
    private Long buyerId;

    /**
     * 买家会员名
     */
    private String buyerNick;

    /**
     * 卖家会员编号
     */
    private Long sellerId;

    /**
     * 卖家会员名
     */
    private String sellerNick;

    /**
     * 订单编号
     */
    private Long orderId;

    /**
     * 商品编号
     */
    private Long itemId;

    /**
     * 商品标题
     */
    private String itemTitle;

    /**
     * 商品图片 URL
     */
    private String itemImgUrl;

    /**
     * 商品SKU ID
     */
    private Long itemSkuId;

    /**
     * 商品SKU描述
     */
    private String itemSkuAttributes;

    /**
     * 实际结算价格
     */
    private Long itemOrignalPrice;

    /**
     * 订单价格
     */
    private Long itemOrderPrice;

    /**
     * 买家评论内容
     */
    private String buyerComment;

    /**
     * DSR 评分（DSR_ID\:RATE;）
     */
    private String dsrRate;

    /**
     * 交易时间
     */
    private Date tradeSuccessTime;

    /**
     * 评论时间
     */
    private Date commentTime;

    /**
     * 是否匿名评价（0：否；1：是）
     */
    private Integer anonymous = ANONYMOUS_NO;

    /**
     * 是否分享到SNS（0：分享；1：不分享）
     */
    private Integer shareSns = SHARE_SNS_YES;

    /**
     * 状态（0：无效；1：有效）
     */
    private Integer status = STATUS_VALID;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    public DsrCommentDO() {
    }

    public DsrCommentDO(OrderDO order, OrderItemDO item, DsrComments comment, Date rateTime, List<DsrResultDO> dsrResults) {
    	this.buyerId = order.getBuyerId();
    	this.buyerNick = order.getBuyerNick();
    	this.sellerId = order.getSellerId();
    	this.sellerNick = order.getSellerNick();
    	this.orderId = order.getOrderId();
    	this.itemId = item.getItemId();
    	this.itemTitle = item.getItemTitle();
    	this.itemImgUrl = item.getItemPicUrl();
    	this.itemSkuId = item.getItemSkuId();
    	this.itemSkuAttributes = item.getItemSkuAttributes();
    	this.itemOrignalPrice = item.getOriginalPrice();
    	this.itemOrderPrice = item.getOrderItemPrice();
    	this.buyerComment = comment.getComment();
    	this.dsrRate = generateDsrRates(itemId, dsrResults);
    	this.tradeSuccessTime = order.getStateModifyTime();
    	this.commentTime = rateTime;
    	this.anonymous = comment.getAnonymousStatus();
    	this.shareSns = comment.getShareSnsStatus();
    }

    private String generateDsrRates(Long itemId, List<DsrResultDO> dsrResults) {
    	if (itemId == null || dsrResults == null || dsrResults.size() == 0) {
    		return null;
    	}
    	StringBuilder builder = new StringBuilder(50);
    	int k = 0;
    	for (DsrResultDO dsrResult : dsrResults) {
    		if (itemId.equals(dsrResult.getItemId())) {
    			if (k++ > 0) {
    				builder.append(';');
    			}
    			builder.append(dsrResult.getDimensionId()).append(':').append(dsrResult.getRate());
    		}
    	}
    	return builder.toString();
    }

    public boolean isAnonymousComment() {
    	return ANONYMOUS_YES.equals(anonymous);
    }

    public boolean isShareSnsComment() {
    	return SHARE_SNS_YES.equals(shareSns);
    }

    public String getHideBuyerNick() {
    	return StringUtil.hideMemberName(buyerNick);
    }

    public String getYuanOrignalPrice() {
    	return MoneyUtil.getCentToDollar(itemOrignalPrice == null ? 0 : itemOrignalPrice);
    }

    public Long getId(){
        return id;
    }

    public Long getBuyerId(){
        return buyerId;
    }

    public String getBuyerNick(){
        return buyerNick;
    }

    public Long getSellerId(){
        return sellerId;
    }

    public String getSellerNick(){
        return sellerNick;
    }

    public Long getOrderId(){
        return orderId;
    }

    public Long getItemId(){
        return itemId;
    }

    public String getItemTitle(){
        return itemTitle;
    }

    public String getItemImgUrl(){
        return itemImgUrl;
    }

    public Long getItemSkuId(){
        return itemSkuId;
    }

    public String getItemSkuAttributes(){
        return itemSkuAttributes;
    }

    public Long getItemOrignalPrice(){
        return itemOrignalPrice;
    }

    public Long getItemOrderPrice(){
        return itemOrderPrice;
    }

    public String getBuyerComment(){
        return buyerComment;
    }

    public String getDsrRate(){
        return dsrRate;
    }

    public Date getTradeSuccessTime(){
        return tradeSuccessTime;
    }

    public Date getCommentTime(){
        return commentTime;
    }

    public Integer getAnonymous(){
        return anonymous;
    }

    public Integer getShareSns(){
        return shareSns;
    }

    public Integer getStatus(){
        return status;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setBuyerId(Long buyerId){
        this.buyerId = buyerId;
    }

    public void setBuyerNick(String buyerNick){
        this.buyerNick = buyerNick;
    }

    public void setSellerId(Long sellerId){
        this.sellerId = sellerId;
    }

    public void setSellerNick(String sellerNick){
        this.sellerNick = sellerNick;
    }

    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }

    public void setItemTitle(String itemTitle){
        this.itemTitle = itemTitle;
    }

    public void setItemImgUrl(String itemImgUrl){
        this.itemImgUrl = itemImgUrl;
    }

    public void setItemSkuId(Long itemSkuId){
        this.itemSkuId = itemSkuId;
    }

    public void setItemSkuAttributes(String itemSkuAttributes){
        this.itemSkuAttributes = itemSkuAttributes;
    }

    public void setItemOrignalPrice(Long itemOrignalPrice){
        this.itemOrignalPrice = itemOrignalPrice;
    }

    public void setItemOrderPrice(Long itemOrderPrice){
        this.itemOrderPrice = itemOrderPrice;
    }

    public void setBuyerComment(String buyerComment){
        this.buyerComment = buyerComment;
    }

    public void setDsrRate(String dsrRate){
        this.dsrRate = dsrRate;
    }

    public void setTradeSuccessTime(Date tradeSuccessTime){
        this.tradeSuccessTime = tradeSuccessTime;
    }

    public void setCommentTime(Date commentTime){
        this.commentTime = commentTime;
    }

    public void setAnonymous(Integer anonymous){
        this.anonymous = anonymous;
    }

    public void setShareSns(Integer shareSns){
        this.shareSns = shareSns;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anonymous == null) ? 0 : anonymous.hashCode());
		result = prime * result + ((buyerComment == null) ? 0 : buyerComment.hashCode());
		result = prime * result + ((buyerId == null) ? 0 : buyerId.hashCode());
		result = prime * result + ((buyerNick == null) ? 0 : buyerNick.hashCode());
		result = prime * result + ((commentTime == null) ? 0 : commentTime.hashCode());
		result = prime * result + ((dsrRate == null) ? 0 : dsrRate.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((itemImgUrl == null) ? 0 : itemImgUrl.hashCode());
		result = prime * result + ((itemOrderPrice == null) ? 0 : itemOrderPrice.hashCode());
		result = prime * result + ((itemOrignalPrice == null) ? 0 : itemOrignalPrice.hashCode());
		result = prime * result + ((itemSkuAttributes == null) ? 0 : itemSkuAttributes.hashCode());
		result = prime * result + ((itemSkuId == null) ? 0 : itemSkuId.hashCode());
		result = prime * result + ((itemTitle == null) ? 0 : itemTitle.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((sellerId == null) ? 0 : sellerId.hashCode());
		result = prime * result + ((sellerNick == null) ? 0 : sellerNick.hashCode());
		result = prime * result + ((shareSns == null) ? 0 : shareSns.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tradeSuccessTime == null) ? 0 : tradeSuccessTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DsrCommentDO other = (DsrCommentDO) obj;
		if (anonymous == null) {
			if (other.anonymous != null)
				return false;
		} else if (!anonymous.equals(other.anonymous))
			return false;
		if (buyerComment == null) {
			if (other.buyerComment != null)
				return false;
		} else if (!buyerComment.equals(other.buyerComment))
			return false;
		if (buyerId == null) {
			if (other.buyerId != null)
				return false;
		} else if (!buyerId.equals(other.buyerId))
			return false;
		if (buyerNick == null) {
			if (other.buyerNick != null)
				return false;
		} else if (!buyerNick.equals(other.buyerNick))
			return false;
		if (commentTime == null) {
			if (other.commentTime != null)
				return false;
		} else if (!commentTime.equals(other.commentTime))
			return false;
		if (dsrRate == null) {
			if (other.dsrRate != null)
				return false;
		} else if (!dsrRate.equals(other.dsrRate))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (itemImgUrl == null) {
			if (other.itemImgUrl != null)
				return false;
		} else if (!itemImgUrl.equals(other.itemImgUrl))
			return false;
		if (itemOrderPrice == null) {
			if (other.itemOrderPrice != null)
				return false;
		} else if (!itemOrderPrice.equals(other.itemOrderPrice))
			return false;
		if (itemOrignalPrice == null) {
			if (other.itemOrignalPrice != null)
				return false;
		} else if (!itemOrignalPrice.equals(other.itemOrignalPrice))
			return false;
		if (itemSkuAttributes == null) {
			if (other.itemSkuAttributes != null)
				return false;
		} else if (!itemSkuAttributes.equals(other.itemSkuAttributes))
			return false;
		if (itemSkuId == null) {
			if (other.itemSkuId != null)
				return false;
		} else if (!itemSkuId.equals(other.itemSkuId))
			return false;
		if (itemTitle == null) {
			if (other.itemTitle != null)
				return false;
		} else if (!itemTitle.equals(other.itemTitle))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (sellerId == null) {
			if (other.sellerId != null)
				return false;
		} else if (!sellerId.equals(other.sellerId))
			return false;
		if (sellerNick == null) {
			if (other.sellerNick != null)
				return false;
		} else if (!sellerNick.equals(other.sellerNick))
			return false;
		if (shareSns == null) {
			if (other.shareSns != null)
				return false;
		} else if (!shareSns.equals(other.shareSns))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tradeSuccessTime == null) {
			if (other.tradeSuccessTime != null)
				return false;
		} else if (!tradeSuccessTime.equals(other.tradeSuccessTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DsrCommentDO [id=" + id + ", buyerId=" + buyerId + ", buyerNick=" + buyerNick + ", sellerId="
				+ sellerId + ", sellerNick=" + sellerNick + ", orderId=" + orderId + ", itemId=" + itemId
				+ ", itemTitle=" + itemTitle + ", itemImgUrl=" + itemImgUrl + ", itemSkuId=" + itemSkuId
				+ ", itemSkuAttributes=" + itemSkuAttributes + ", itemOrignalPrice=" + itemOrignalPrice
				+ ", itemOrderPrice=" + itemOrderPrice + ", buyerComment=" + buyerComment + ", dsrRate=" + dsrRate
				+ ", tradeSuccessTime=" + tradeSuccessTime + ", commentTime=" + commentTime + ", anonymous="
				+ anonymous + ", shareSns=" + shareSns + ", status=" + status + ", gmtCreate=" + gmtCreate
				+ ", gmtModified=" + gmtModified + "]";
	}
}

