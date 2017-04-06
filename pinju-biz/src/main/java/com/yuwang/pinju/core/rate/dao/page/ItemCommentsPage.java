package com.yuwang.pinju.core.rate.dao.page;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.domain.page.MySQLPagingQuery;

public class ItemCommentsPage extends MySQLPagingQuery {

	private Long itemId;
	private Date conditionTime;

	public ItemCommentsPage(Long itemId, int page, int pageSize) {
		super(page, pageSize);
		this.itemId = itemId;
		this.conditionTime = DateUtil.decrementDay(new Date(), PinjuConstant.COMMENTS_ITEM_DAYS);
	}

	public Long getItemId() {
		return itemId;
	}

	public Date getConditionTime() {
		return conditionTime;
	}
}
