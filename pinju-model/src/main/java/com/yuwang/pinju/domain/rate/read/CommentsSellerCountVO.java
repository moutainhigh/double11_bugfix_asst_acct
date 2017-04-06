package com.yuwang.pinju.domain.rate.read;

import java.io.Serializable;

public class CommentsSellerCountVO implements Serializable {

	private static final long serialVersionUID = -1191673155444031805L;

	private String seller;
	private long count;

	public CommentsSellerCountVO(String seller, long count) {
		this.seller = seller;
		this.count = count;
	}

	public String getSeller() {
		return seller;
	}

	public long getCount() {
		return count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (count ^ (count >>> 32));
		result = prime * result + ((seller == null) ? 0 : seller.hashCode());
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
		CommentsSellerCountVO other = (CommentsSellerCountVO) obj;
		if (count != other.count)
			return false;
		if (seller == null) {
			if (other.seller != null)
				return false;
		} else if (!seller.equals(other.seller))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentsSellerCountVO [seller=" + seller + ", count=" + count + "]";
	}
}
