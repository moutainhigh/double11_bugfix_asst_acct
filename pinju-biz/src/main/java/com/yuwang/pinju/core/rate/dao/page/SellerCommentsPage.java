package com.yuwang.pinju.core.rate.dao.page;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.domain.page.MySQLPagingQuery;

public class SellerCommentsPage extends MySQLPagingQuery {

	private Long sellerId;
	private Date conditionTime;

	public SellerCommentsPage(Long sellerId, int page, int pageSize) {
		super(page, pageSize);
		this.sellerId = sellerId;
		this.conditionTime = DateUtil.decrementDay(new Date(), PinjuConstant.COMMENTS_SELLER_DAYS);
	}

	public Long getSellerId() {
		return sellerId;
	}

	public Date getConditionTime() {
		return conditionTime;
	}
}
