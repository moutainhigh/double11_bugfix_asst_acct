package com.yuwang.pinju.domain.rate.read;

public class CommentsItemCountVO {

	private String item;
	private long count;

	public CommentsItemCountVO() {
	}

	public CommentsItemCountVO(String item, long count) {
		this.item = item;
		this.count = count;
	}

	public String getItem() {
		return item;
	}
	public long getCount() {
		return count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (count ^ (count >>> 32));
		result = prime * result + ((item == null) ? 0 : item.hashCode());
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
		CommentsItemCountVO other = (CommentsItemCountVO) obj;
		if (count != other.count)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentsItemCountVO [item=" + item + ", count=" + count + "]";
	}
}
