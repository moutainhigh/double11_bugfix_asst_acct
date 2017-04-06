package com.yuwang.pinju.web.module.ajax.rate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.yuwang.pinju.common.NumberUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.rate.ao.RateReadCommentsAO;
import com.yuwang.pinju.core.util.CharsetUtil;
import com.yuwang.pinju.domain.rate.read.CommentsItemDO;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>基于商品的购买记录评价数据查询</p>
 *
 * @author gaobaowen
 * @since 2011-10-13 15:51:16
 */
public class RateItemCommentsReader implements PinjuAction {

	private final static Log log = LogFactory.getLog(RateItemCommentsReader.class);

	private RateReadCommentsAO rateReadCommentsAO;

	/**
	 * 商品编号
	 */
	private String item;

	/**
	 * 页码
	 */
	private String page;

	/**
	 * 每页大小
	 */
	private String pageSize;

	private InputStream inputStream;

	public void setRateReadCommentsAO(RateReadCommentsAO rateReadCommentsAO) {
		this.rateReadCommentsAO = rateReadCommentsAO;
	}

	@Override
	public String execute() throws Exception {

		// validate HTTP Referer header
		if (!PinjuConstant.isDevelopment && !ServletUtil.isPinjuReferer()) {
			log.warn("request is not pinju referer, request ip: " + ServletUtil.getRemoteIp() + ", development mode: " + PinjuConstant.isDevelopment + ", referer: " + ServletUtil.getHttpReferer());
			return INVALID_REQUEST;
		}

		// validate item id where or nor a number
		long itemId = NumberUtil.parseLong(item, 0L);
		if (itemId < 1) {
			log.warn("request item id is invalid, request parameter item: " + item);
			return INVALID_REQUEST;
		}

		// get current page
		int pageNum = NumberUtil.parseInt(page, 1);
		int pageSizeNum = NumberUtil.parseInt(pageSize, PinjuConstant.COMMENTS_SELLER_PAGE_SIZE);

		if (log.isDebugEnabled()) {
			log.debug("request item comments data, item id: [" + itemId + "], page: [" + page + "]");
		}

		try {
			Result result = rateReadCommentsAO.getItemComments(itemId, pageNum, pageSizeNum);
			if (!result.isSuccess()) {
				log.warn("execute getItemComments result is not success, result code: [" + result.getResultCode() + "]");
				return response(null);
			}
			CommentsItemDO comments = result.getModel(CommentsItemDO.class);
			return response(new JSONObject(comments).toString());
		} catch (Exception e) {
			log.error("execute getItemComments cause exception, item id: [" + item + "], page: [" + page + "], ip: [" + ServletUtil.getRemoteIp() + "], action context:");
			ServletUtil.logActionContext(log, true);
			return response(null);
		}
	}

	public void setItem(String item) {
		this.item = item;
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
}
