package com.yuwang.pinju.domain.page;

/**
 * <p>MySQL 分页查询参数</p>
 *
 * @author gaobaowen
 * @since 2011-10-13 09:17:57
 */
public abstract class MySQLPagingQuery {

	/**
	 * 页码数
	 */
	private int page;
	
	/**
	 * 每页数据数
	 */
	private int pageSize;

	public MySQLPagingQuery(int page, int pageSize) {
		setPage(page);
		setPageSize(pageSize);
	}

	public int getPage() {
		return page;
	}
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * <p>获取分页起始的条数，即 MySQL 中 LIMIT 的第一个参数。</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-10-13 09:18:28
	 */
	public int getStartOffset() {
		return (page - 1) * pageSize;
	}

	private void setPageSize(int pageSize) {
		this.pageSize = pageSize < 1 ? 1 : pageSize;
	}

	private void setPage(int page) {
		this.page = page < 1 ? 1 : page;
	}
}
