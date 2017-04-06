package com.yuwang.pinju.domain.page;

/**
 * <p>分页数据结果</p>
 *
 * @author gaobaowen
 * @since 2011-10-13 09:19:54
 */
public class PagingResult {

	/**
	 * 数据总条数
	 */
	private long count;

	/**
	 * 当前页码数
	 */
	private int page;

	/**
	 * 每页数据量
	 */
	private int pageSize;

	/**
	 * 最大页码数
	 */
	private long maxPage;

	public PagingResult(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize < 1 ? 10 : pageSize;
	}

	public void setCount(long count) {
		this.count = count;
		resetMaxPage();
	}

	public long getCount() {
		return count;
	}

	public int getPage() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getMaxPage() {
		return maxPage;
	}
	private void resetMaxPage() {
		this.maxPage = (count + pageSize - 1) / pageSize;
	}

	public StringBuilder toPagingString() {
		StringBuilder builder = new StringBuilder("Paging params:");
		builder.append(" count=[").append(count).append("]");
		builder.append(", page=[").append(page).append("]");
		builder.append(", pageSize=[").append(pageSize).append("]");
		builder.append(", maxPage=[").append(maxPage).append("]");
		return builder;
	}
}
