package com.yuwang.pinju.domain.rate.read;

import java.util.Arrays;
import java.util.List;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.domain.page.PagingResult;
import com.yuwang.pinju.domain.rate.DsrCommentDO;

/**
 * <p>按卖家的评论数据</p>
 *
 * @author gaobaowen
 * @since 2011-10-17 11:34:14
 */
public class CommentsSellerDO extends PagingResult {

	public final static int DEFAULT_PAGE_SIZE = 10;

	private String seller;
	private Comments[] comments = new Comments[0];

	public CommentsSellerDO(int page, int pageSize) {
		super(page, pageSize);
	}

	public void addComments(List<DsrCommentDO> dsrComments) {
		if (dsrComments == null || dsrComments.size() == 0) {
			return;
		}
		comments = new Comments[dsrComments.size()];
		for (int i = 0; i < comments.length; i++) {
			comments[i] = new Comments(dsrComments.get(i));
		}
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getSeller() {
		return seller;
	}

	public Comments[] getComments() {
		return comments;
	}

	public static class Comments {
	    private Long bid;     		// 买家会员编号
	    private String name;  		// 买家会员名
	    private Long sid;     		// SKU ID
	    private String sat;   		// SKU 购买属性
	    private String price;       // 订单价格
	    private String comment;     // 评论内容
	    private String time;   		// 评论时间
	    private boolean anonymous;  // 是否匿名
	    private boolean sns;        // 是否分享
	    private Long itemId;        // 商品编号
	    private String title;       // 商品名

	    public Comments() {
	    }

	    public Comments(DsrCommentDO dsrComment) {
	    	this.anonymous = dsrComment.isAnonymousComment();
	    	this.sns = dsrComment.isShareSnsComment();
	    	if (!anonymous) {
	    		this.bid = dsrComment.getBuyerId();
	    		this.name = dsrComment.getBuyerNick();
	    	} else {
	    		this.name = dsrComment.getHideBuyerNick();
	    	}
	    	this.sid = dsrComment.getItemSkuId();
	    	this.sat = dsrComment.getItemSkuAttributes();
	    	this.price = dsrComment.getYuanOrignalPrice();
	    	this.comment = dsrComment.getBuyerComment();
	    	this.time = DateUtil.formatDate(dsrComment.getCommentTime());
	    	this.itemId = dsrComment.getItemId();
	    	this.title = dsrComment.getItemTitle();
	    }

		public Long getBid() {
			return bid;
		}

		public String getName() {
			return name;
		}

		public Long getSid() {
			return sid;
		}

		public String getSat() {
			return sat;
		}

		public String getPrice() {
			return price;
		}

		public String getComment() {
			return comment;
		}

		public String getTime() {
			return time;
		}

		public boolean isAnonymous() {
			return anonymous;
		}

		public boolean isSns() {
			return sns;
		}

		public Long getItemId() {
			return itemId;
		}

		public String getTitle() {
			return title;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (anonymous ? 1231 : 1237);
			result = prime * result + ((bid == null) ? 0 : bid.hashCode());
			result = prime * result + ((comment == null) ? 0 : comment.hashCode());
			result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((price == null) ? 0 : price.hashCode());
			result = prime * result + ((sat == null) ? 0 : sat.hashCode());
			result = prime * result + ((sid == null) ? 0 : sid.hashCode());
			result = prime * result + (sns ? 1231 : 1237);
			result = prime * result + ((time == null) ? 0 : time.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
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
			Comments other = (Comments) obj;
			if (anonymous != other.anonymous)
				return false;
			if (bid == null) {
				if (other.bid != null)
					return false;
			} else if (!bid.equals(other.bid))
				return false;
			if (comment == null) {
				if (other.comment != null)
					return false;
			} else if (!comment.equals(other.comment))
				return false;
			if (itemId == null) {
				if (other.itemId != null)
					return false;
			} else if (!itemId.equals(other.itemId))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (price == null) {
				if (other.price != null)
					return false;
			} else if (!price.equals(other.price))
				return false;
			if (sat == null) {
				if (other.sat != null)
					return false;
			} else if (!sat.equals(other.sat))
				return false;
			if (sid == null) {
				if (other.sid != null)
					return false;
			} else if (!sid.equals(other.sid))
				return false;
			if (sns != other.sns)
				return false;
			if (time == null) {
				if (other.time != null)
					return false;
			} else if (!time.equals(other.time))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			return true;
		}
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "CommentsSellerDO [seller=" + seller + ", comments="
				+ (comments != null ? Arrays.asList(comments).subList(0, Math.min(comments.length, maxLen)) : null)
				+ "]";
	}
}
