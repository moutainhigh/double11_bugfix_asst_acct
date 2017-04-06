package com.yuwang.pinju.domain.rate.comment;

import java.util.List;

import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.rate.DsrCommentDO;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;

public class RateOrderModel {

	/**
	 * 订单信息
	 */
	private String oid;
	private MemberDO sellerMember;
	private OrderDO order;
	private List<OrderItemDO> orderItems;
	private List<DsrDimensionDO> itemDsr;
	private List<DsrDimensionDO> shopDsr;

	/**
	 * 购买商品评价
	 */
	private List<DsrComments> comment;

	/**
	 * 商品 DSR 评分
	 */
	private List<Dimension> idsr;

	/**
	 * 店铺 DSR 评分
	 */
	private List<Dimension> sdsr;

	public long getOrderId() {
		if (oid == null) {
			return -1;
		}
		long orderId = -1;
		try {
			orderId = Long.parseLong(oid);
		} catch (Exception e) {
		}
		return orderId;
	}

	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public MemberDO getSellerMember() {
		return sellerMember;
	}
	public void setSellerMember(MemberDO sellerMember) {
		this.sellerMember = sellerMember;
	}
	public OrderDO getOrder() {
		return order;
	}
	public void setOrder(OrderDO order) {
		this.order = order;
	}
	public List<OrderItemDO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDO> orderItems) {
		this.orderItems = orderItems;
	}
	public List<DsrDimensionDO> getItemDsr() {
		return itemDsr;
	}
	public void setItemDsr(List<DsrDimensionDO> itemDsr) {
		this.itemDsr = itemDsr;
	}
	public List<DsrDimensionDO> getShopDsr() {
		return shopDsr;
	}
	public void setShopDsr(List<DsrDimensionDO> shopDsr) {
		this.shopDsr = shopDsr;
	}

	public List<DsrComments> getComment() {
		return comment;
	}

	public void setComment(List<DsrComments> comment) {
		this.comment = comment;
	}

	public List<Dimension> getIdsr() {
		return idsr;
	}

	public void setIdsr(List<Dimension> idsr) {
		this.idsr = idsr;
	}

	public List<Dimension> getSdsr() {
		return sdsr;
	}

	public void setSdsr(List<Dimension> sdsr) {
		this.sdsr = sdsr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderCommentsDO, order id = [").append(oid).append("]");
		output(builder, "Comment", comment);
		output(builder, "ItemDSR", idsr);
		output(builder, "ShopDSR", sdsr);
		return builder.toString();
	}

	private void output(StringBuilder builder, String name, List<?> list) {
		if (list == null) {
			builder.append("\n   ").append(name).append(" is null");
			return;
		}
		for (Object obj : list) {
			builder.append("\n   ").append(name).append(": ").append(obj);
		}
	}

	/**
	 * <p>DSR 评分</p>
	 * @author gaobaowen
	 * @since 2011-10-9 15:36:12
	 */
	public static class Dimension {

		private final static String RATE_DEFAULT = "0";
		private final static String[] RATE_ACCEPTABLE = { "1", "2", "3", "4", "5" };

		private String item;

		/**
		 * Demension ID
		 */
		private String dimen;

		/**
		 * 评分
		 */
		private String rate = RATE_DEFAULT;

		public String getItem() {
			return item;
		}
		public void setItem(String item) {
			this.item = item;
		}
		public String getDimen() {
			return dimen;
		}
		public void setDimen(String dimen) {
			this.dimen = dimen;
		}
		public String getRate() {
			return rate;
		}
		public void setRate(String rate) {
			for (int i = 0; i < RATE_ACCEPTABLE.length; i++) {
				if (RATE_ACCEPTABLE[i].equals(rate)) {
					this.rate = rate;
					return;
				}
			}
		}
		public int getRateScore() {
			return num(rate, 0);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((dimen == null) ? 0 : dimen.hashCode());
			result = prime * result + ((item == null) ? 0 : item.hashCode());
			result = prime * result + ((rate == null) ? 0 : rate.hashCode());
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
			Dimension other = (Dimension) obj;
			if (dimen == null) {
				if (other.dimen != null)
					return false;
			} else if (!dimen.equals(other.dimen))
				return false;
			if (item == null) {
				if (other.item != null)
					return false;
			} else if (!item.equals(other.item))
				return false;
			if (rate == null) {
				if (other.rate != null)
					return false;
			} else if (!rate.equals(other.rate))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Dimension [item=" + item + ", dimen=" + dimen + ", rate=" + rate + "]";
		}
	}

	/**
	 * <p>DSR 评价内容</p>
	 *
	 * @author gaobaowen
	 * @since 2011-10-9 15:36:26
	 */
	public static class DsrComments {

		/**
		 * 是否匿名评价 -- 否（0），默认值
		 */
		public final static String ANONYMOUS_NO = String.valueOf(DsrCommentDO.ANONYMOUS_NO);

		/**
		 * 是否匿名评价 -- 是（1）
		 */
		public final static String ANONYMOUS_YES = String.valueOf(DsrCommentDO.ANONYMOUS_YES);

		/**
		 * 是否分享到 SNS -- 是（0），默认值
		 */
		public final static String SHARE_SNS_YES = String.valueOf(DsrCommentDO.SHARE_SNS_YES);

		/**
		 * 是否分享到 SNS -- 否（1）
		 */
		public final static String SHARE_SNS_NO = String.valueOf(DsrCommentDO.SHARE_SNS_NO);

		/**
		 * 商品编号
		 */
		private String item;

		/**
		 * 评价内容
		 */
		private String comment;

		/**
		 * 是否匿名
		 */
		private String anonymous = ANONYMOUS_NO;

		/**
		 * 是否分享到 SNS
		 */
		private String shareSns = SHARE_SNS_YES;

		public String getItem() {
			return item;
		}
		public void setItem(String item) {
			this.item = item;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = (comment == null) ? null : comment.trim();
		}
		public String getAnonymous() {
			return anonymous;
		}
		public void setAnonymous(String anonymous) {
			this.anonymous = ANONYMOUS_YES.equals(anonymous) ? ANONYMOUS_YES : ANONYMOUS_NO;
		}
		public int getAnonymousStatus() {
			return num(anonymous, DsrCommentDO.ANONYMOUS_NO);
		}
		public String getShareSns() {
			return shareSns;
		}
		public void setShareSns(String shareSns) {
			this.shareSns = SHARE_SNS_NO.equals(shareSns) ? SHARE_SNS_NO : SHARE_SNS_YES;
		}
		public int getShareSnsStatus() {
			return num(shareSns, DsrCommentDO.SHARE_SNS_YES);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((anonymous == null) ? 0 : anonymous.hashCode());
			result = prime * result + ((comment == null) ? 0 : comment.hashCode());
			result = prime * result + ((item == null) ? 0 : item.hashCode());
			result = prime * result + ((shareSns == null) ? 0 : shareSns.hashCode());
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
			DsrComments other = (DsrComments) obj;
			if (anonymous == null) {
				if (other.anonymous != null)
					return false;
			} else if (!anonymous.equals(other.anonymous))
				return false;
			if (comment == null) {
				if (other.comment != null)
					return false;
			} else if (!comment.equals(other.comment))
				return false;
			if (item == null) {
				if (other.item != null)
					return false;
			} else if (!item.equals(other.item))
				return false;
			if (shareSns == null) {
				if (other.shareSns != null)
					return false;
			} else if (!shareSns.equals(other.shareSns))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "DsrComments [item=" + item + ", comment=" + comment + ", anonymous=" + anonymous + ", shareSns="
					+ shareSns + "]";
		}
	}

	private static int num(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
