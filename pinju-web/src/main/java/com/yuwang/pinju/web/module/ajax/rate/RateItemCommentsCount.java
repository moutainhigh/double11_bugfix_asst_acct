package com.yuwang.pinju.web.module.ajax.rate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.NumberUtil;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.rate.ao.RateReadCommentsAO;
import com.yuwang.pinju.domain.rate.read.CommentsItemCountVO;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>针对于商品的评价数量</p>
 * @author gaobaowen
 * @since 2011-12-3 10:34:47
 */
public class RateItemCommentsCount implements PinjuAction {

	private final static Log log = LogFactory.getLog(RateItemCommentsCount.class);

	private RateReadCommentsAO rateReadCommentsAO;

	/**
	 * 商品编号
	 */
	private String item;

	/**
	 * 商品评价数量
	 */
	private CommentsItemCountVO count;

	public RateItemCommentsCount() {
	}

	@Override
	public String execute() throws Exception {

		// validate HTTP Referer header
		if (!PinjuConstant.isDevelopment && !ServletUtil.isPinjuReferer()) {
			log.warn("request is not pinju referer, request ip: " + ServletUtil.getRemoteIp() + ", development mode: " + PinjuConstant.isDevelopment + ", referer: " + ServletUtil.getHttpReferer());
			return INVALID_REQUEST;
		}

		// validate item id whether or not number
		long itemId = NumberUtil.parseLong(item, 0);
		if (itemId < 0) {
			log.warn("item id is invalid, item id: [" + itemId + "]");
			return toResult(0);
		}
		long itemCount = rateReadCommentsAO.countItemComments(itemId);
		return toResult(itemCount);
	}

	public CommentsItemCountVO getCount() {
		return count;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setRateReadCommentsAO(RateReadCommentsAO rateReadCommentsAO) {
		this.rateReadCommentsAO = rateReadCommentsAO;
	}

	private String toResult(long itemCount) {
		count = new CommentsItemCountVO(item, itemCount);
		return SUCCESS;
	}
}