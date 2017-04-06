package com.yuwang.pinju.core.rate.ao.impl;

import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_INNER_ERROR;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_PARAMETERS_ERROR;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.rate.ao.RateReadCommentsAO;
import com.yuwang.pinju.core.rate.manager.RateReadCommentsManager;
import com.yuwang.pinju.domain.rate.read.CommentsItemDO;
import com.yuwang.pinju.domain.rate.read.CommentsSellerDO;

public class RateReadCommentsAOImpl implements RateReadCommentsAO {

	private final static Log log = LogFactory.getLog(RateReadCommentsAOImpl.class);

	private RateReadCommentsManager rateReadCommentsManager;
	private MemberManager memberManager;

	public void setRateReadCommentsManager(RateReadCommentsManager rateReadCommentsManager) {
		this.rateReadCommentsManager = rateReadCommentsManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	@Override
	public Result getItemComments(long itemId, int page, int pageSize) {
		if (itemId < 1) {
			log.error("getItemComments, itemId: [" + itemId + "] is invalid");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			CommentsItemDO comments = rateReadCommentsManager.getItemComments(itemId, page, pageSize);
			if (comments == null) {
				log.error("getItemComments result is null, itemId: [" + itemId + "], page: [" + page + "], page size: [" + pageSize + "]");
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			return ResultSupportExt.createSuccess(comments);
		} catch (Exception e) {
			log.error("getItemComments cause exception, itemId: [" + itemId + "], page: [" + page + "], page size: [" + pageSize + "]");
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result getSellerComments(long memberId, int page, int pageSize) {
		if (memberId < 1) {
			log.error("getSellerComments, seller member id: [" + memberId + "] is invalid");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			CommentsSellerDO comments = rateReadCommentsManager.getSellerComments(memberId, page, pageSize);
			if (comments == null) {
				log.error("getSellerComments result is null, seller member id: [" + memberId + "], page: [" + page + "], page size: [" + pageSize + "]");
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			return ResultSupportExt.createSuccess(comments);
		} catch (Exception e) {
			log.error("getItemComments cause exception, seller member id: [" + memberId + "], page: [" + page + "], page size: [" + pageSize + "]");
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public long countItemComments(long itemId) {
		if (itemId < 1) {
			log.warn("[countItemComments] item id [" + itemId + "] is invalid");
			return 0;
		}
		try {
			return rateReadCommentsManager.countItemComments(itemId);
		} catch (Exception e) {
			log.error("[countItemComments] cause exception, item id: [" + itemId + "]", e);
			return 0;
		}
	}

	@Override
	public long countSellerComments(long sellerId) {
		if (!memberManager.isCorrectMemberId(sellerId)) {
			log.warn("[countSellerComments] seller id [" + sellerId + "] is invalid");
			return 0;
		}
		try {
			return rateReadCommentsManager.countSellerComments(sellerId);
		} catch (Exception e) {
			log.error("[countSellerComments] cause exception, seller id: [" + sellerId + "]", e);
			return 0;
		}
	}
}
