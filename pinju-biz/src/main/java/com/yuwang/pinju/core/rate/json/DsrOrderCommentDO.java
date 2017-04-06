package com.yuwang.pinju.core.rate.json;

import java.util.LinkedList;
import java.util.List;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.rate.ao.impl.RateBuyerAOImpl.DsrOrderInfo;
import com.yuwang.pinju.domain.rate.DsrCommentDO;

/**
 * <p>DSR 评价数据</p>
 *
 * @author gaobaowen
 * @since 2011-7-12 15:07:42
 */
public class DsrOrderCommentDO {

	public final static Integer AFTER_BUY_YES = 1;
	public final static Integer AFTER_BUY_NO = 0;

	private Long orderId;
	private Long sellerId;
	private String sellerNickname;
	private Long buyerId;
	private String buyerNickname;
	private String tradeTime;
	private String rateTime;
	private Integer afterBuy = AFTER_BUY_YES;
	private List<DsrItemCommentDO> comments;

	public DsrOrderCommentDO() {
	}

	public DsrOrderCommentDO(DsrOrderInfo info) {
		this.orderId = info.getOrder().getOrderId();
		this.sellerId = info.getOrder().getSellerId();
		this.sellerNickname = info.getOrder().getSellerNick();
		this.buyerId = info.getOrder().getBuyerId();
		this.buyerNickname = info.getOrder().getBuyerNick();
		this.tradeTime = DateUtil.formatDatetime(info.getOrder().getStateModifyTime());
		this.rateTime = DateUtil.formatDatetime(info.getCurrent());
	}

	public Integer getAfterBuy() {
		return afterBuy;
	}
	public void setAfterBuy(Integer afterBuy) {
		this.afterBuy = afterBuy;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerNickname() {
		return sellerNickname;
	}
	public void setSellerNickname(String sellerNickname) {
		this.sellerNickname = sellerNickname;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerNickname() {
		return buyerNickname;
	}
	public void setBuyerNickname(String buyerNickname) {
		this.buyerNickname = buyerNickname;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getRateTime() {
		return rateTime;
	}
	public void setRateTime(String rateTime) {
		this.rateTime = rateTime;
	}
	public List<DsrItemCommentDO> getComments() {
		return comments;
	}
	public void setComments(List<DsrItemCommentDO> comments) {
		this.comments = comments;
	}
	public void addDsrItemComment(DsrCommentDO dsrComment) {
		if(comments == null) {
			comments = new LinkedList<DsrItemCommentDO>();
		}
		comments.add(new DsrItemCommentDO(dsrComment));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((afterBuy == null) ? 0 : afterBuy.hashCode());
		result = prime * result + ((buyerId == null) ? 0 : buyerId.hashCode());
		result = prime * result + ((buyerNickname == null) ? 0 : buyerNickname.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((rateTime == null) ? 0 : rateTime.hashCode());
		result = prime * result + ((sellerId == null) ? 0 : sellerId.hashCode());
		result = prime * result + ((sellerNickname == null) ? 0 : sellerNickname.hashCode());
		result = prime * result + ((tradeTime == null) ? 0 : tradeTime.hashCode());
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
		DsrOrderCommentDO other = (DsrOrderCommentDO) obj;
		if (afterBuy == null) {
			if (other.afterBuy != null)
				return false;
		} else if (!afterBuy.equals(other.afterBuy))
			return false;
		if (buyerId == null) {
			if (other.buyerId != null)
				return false;
		} else if (!buyerId.equals(other.buyerId))
			return false;
		if (buyerNickname == null) {
			if (other.buyerNickname != null)
				return false;
		} else if (!buyerNickname.equals(other.buyerNickname))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (rateTime == null) {
			if (other.rateTime != null)
				return false;
		} else if (!rateTime.equals(other.rateTime))
			return false;
		if (sellerId == null) {
			if (other.sellerId != null)
				return false;
		} else if (!sellerId.equals(other.sellerId))
			return false;
		if (sellerNickname == null) {
			if (other.sellerNickname != null)
				return false;
		} else if (!sellerNickname.equals(other.sellerNickname))
			return false;
		if (tradeTime == null) {
			if (other.tradeTime != null)
				return false;
		} else if (!tradeTime.equals(other.tradeTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("DsrOrderCommentDO");
		builder.append(": orderId=[").append(orderId).append("]");
		builder.append(", sellerId=[").append(sellerId).append("]");
		builder.append(", sellerNickname=[").append(sellerNickname).append("]");

		builder.append(", buyerId=[").append(buyerId).append("]");
		builder.append(", buyerNickname=[").append(buyerNickname).append("]");
		builder.append(", tradeTime=[").append(tradeTime).append("]");
		builder.append(", rateTime=[").append(rateTime).append("]");
		builder.append(", afterBuy=[").append(afterBuy).append("]");
		builder.append(", comments size=[").append(comments == null ? "null" : comments.size()).append("]");

		if (comments != null && comments.size() > 0) {
			for (int i = 0, k = comments.size(); i < k; i++) {
				builder.append("\n   ");
				comments.get(i).toStringBuilder(i, builder);
			}
		}
		return builder.toString();
	}
}
