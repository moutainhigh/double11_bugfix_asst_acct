package com.yuwang.pinju.web.module.ajax.rate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.rate.ao.RateReadCommentsAO;
import com.yuwang.pinju.core.util.CharsetUtil;
import com.yuwang.pinju.domain.rate.read.CommentsSellerDO;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

public class RateSellerCommentReader implements PinjuAction {

	private final static Log log = LogFactory.getLog(RateSellerCommentReader.class);

	private RateReadCommentsAO rateReadCommentsAO;

	/**
	 * 商品编号
	 */
	private String seller;

	/**
	 * 页码
	 */
	private String page;

	/**
	 * 每页大小
	 */
	private String pageSize;

	private InputStream inputStream;

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
			return INVALID_REQUEST;
		}

//		SellerQualityDO sellerQuality = memberManager.getSellerQualityByMemberId(sellerId);
//		if (sellerQuality == null) {
//			log.warn("request seller quality, seller is not exists, seller member id: [" + sellerId + "(" + seller + ")]");
//			return INVALID_REQUEST;
//		}

		// get current page
		int pageNum = getInt(page, 1);

		int pageSizeNum = getInt(pageSize, PinjuConstant.COMMENTS_SELLER_PAGE_SIZE);

		if (log.isDebugEnabled()) {
			log.debug("request item comments data, seller member id: [" + sellerId + "(" + seller + ")], page: [" + page + "], pageSize: [" + pageSize + "]");
		}

		try {
			Result result = rateReadCommentsAO.getSellerComments(sellerId, pageNum, pageSizeNum);
			if (!result.isSuccess()) {
				log.warn("execute getSellerComments result is not success, result code: [" + result.getResultCode() + "]");
				return response(null);
			}
			CommentsSellerDO comments = result.getModel(CommentsSellerDO.class);
			comments.setSeller(seller);
			return response(new JSONObject(comments).toString());
		} catch (Exception e) {
			log.error("execute getSellerComments cause exception, seller member id: [" + sellerId + "(" + seller + ")], page: [" + page + "], ip: [" + ServletUtil.getRemoteIp() + "], action context:");
			ServletUtil.logActionContext(log, true);
			return response(null);
		}
	}

	public void setRateReadCommentsAO(RateReadCommentsAO rateReadCommentsAO) {
		this.rateReadCommentsAO = rateReadCommentsAO;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	private String response(String str) {
		if (log.isDebugEnabled()) {
			log.debug("response data: " + str);
		}
		byte[] bys = CharsetUtil.toBytes(str == null ? "{}" : str);
		inputStream = new ByteArrayInputStream(bys);
		return SUCCESS;
	}

	private static int getInt(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
