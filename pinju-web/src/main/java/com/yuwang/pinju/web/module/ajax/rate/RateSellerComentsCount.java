package com.yuwang.pinju.web.module.ajax.rate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.rate.ao.RateReadCommentsAO;
import com.yuwang.pinju.domain.rate.read.CommentsSellerCountVO;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>针对于卖家的评价数量</p>
 *
 * @author gaobaowen
 * @since 2011-12-3 10:34:47
 */
public class RateSellerComentsCount implements PinjuAction {

	private final static Log log = LogFactory.getLog(RateSellerComentsCount.class);

	private RateReadCommentsAO rateReadCommentsAO;

	/**
	 * 卖家会员编号
	 */
	private String seller;

	/**
	 * 针对于卖家的评价数量
	 */
	private CommentsSellerCountVO count;

	public RateSellerComentsCount() {
	}

	@Override
	public String execute() throws Exception {

		// validate HTTP Referer header
		if (!PinjuConstant.isDevelopment && !ServletUtil.isPinjuReferer()) {
			log.warn("request is not pinju referer, request ip: " + ServletUtil.getRemoteIp() + ", development mode: " + PinjuConstant.isDevelopment + ", referer: " + ServletUtil.getHttpReferer());
			return INVALID_REQUEST;
		}

		// validate seller id where or nor a number
		long sellerId = MemberIdPuzzle.decode(seller);
		if (sellerId < 1) {
			log.warn("request seller id is invalid, request parameter seller: " + seller);
			return toResult(0);
		}

		long sellerCount = rateReadCommentsAO.countSellerComments(sellerId);
		return toResult(sellerCount);
	}

	public CommentsSellerCountVO getCount() {
		return count;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public void setRateReadCommentsAO(RateReadCommentsAO rateReadCommentsAO) {
		this.rateReadCommentsAO = rateReadCommentsAO;
	}

	private String toResult(long sellerCount) {
		count = new CommentsSellerCountVO(seller, sellerCount);
		return SUCCESS;
	}
}
