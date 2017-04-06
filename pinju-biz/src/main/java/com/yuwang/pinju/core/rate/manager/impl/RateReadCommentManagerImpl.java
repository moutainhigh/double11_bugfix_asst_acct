package com.yuwang.pinju.core.rate.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.rate.dao.DsrCommentDAO;
import com.yuwang.pinju.core.rate.dao.page.ItemCommentsPage;
import com.yuwang.pinju.core.rate.dao.page.SellerCommentsPage;
import com.yuwang.pinju.core.rate.manager.RateReadCommentsManager;
import com.yuwang.pinju.domain.rate.DsrCommentDO;
import com.yuwang.pinju.domain.rate.read.CommentsItemDO;
import com.yuwang.pinju.domain.rate.read.CommentsSellerDO;

public class RateReadCommentManagerImpl implements RateReadCommentsManager {

	private final static Log log = LogFactory.getLog(RateReadCommentManagerImpl.class);

	private DsrCommentDAO dsrCommentDAO;
	private MemberManager memberManager;

	public void setDsrCommentDAO(DsrCommentDAO dsrCommentDAO) {
		this.dsrCommentDAO = dsrCommentDAO;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	@Override
	public CommentsItemDO getItemComments(long itemId, int page, int pageSize) throws ManagerException {
		if (pageSize < 1 || pageSize > 100) {
			log.warn("getItemComments, item id: [" + itemId + "], page: [" + page + "], page size: [" + pageSize + "] to using default value"); 
			pageSize = PinjuConstant.COMMENTS_ITEM_PAGE_SIZE;
		}
		ItemCommentsPage itemPage = new ItemCommentsPage(itemId, page, pageSize);
		CommentsItemDO itemComments = new CommentsItemDO(itemId, page, itemPage.getPageSize());
		try {
			long count = dsrCommentDAO.countDsrCommentByItemRealtime(itemId);
			itemComments.setCount(count);
			if (count < 1) {
				return itemComments;
			}
			List<DsrCommentDO> dsrComments = dsrCommentDAO.pagingDsrCommentByItem(itemPage);
			itemComments.addComments(dsrComments);
			return itemComments;
		} catch (Exception e) {
			String message = "getItemComments cause exception, itemId: [" + itemId + "], page: [" + page + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public CommentsSellerDO getSellerComments(long memberId, int page, int pageSize) throws ManagerException {
		if (pageSize < 1 || pageSize > 100) {
			log.warn("getSellerComments, seller member id: [" + memberId + "], page: [" + page + "], page size: [" + pageSize + "] to using default value"); 
			pageSize = PinjuConstant.COMMENTS_ITEM_PAGE_SIZE;
		}
		SellerCommentsPage sellerPage = new SellerCommentsPage(memberId, page, pageSize);
		CommentsSellerDO comments = new CommentsSellerDO(page, sellerPage.getPageSize());
		try {
			long count = dsrCommentDAO.countDsrCommentBySellerIdRealtime(memberId);
			comments.setCount(count);
			if (count < 1) {
				return comments;
			}
			List<DsrCommentDO> dsrComments = dsrCommentDAO.pagingDsrCommentBySellerId(sellerPage);
			comments.addComments(dsrComments);
			return comments;
		} catch (Exception e) {
			String message = "getSellerComments cause exception, seller member id: [" + memberId + "], page: [" + page + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public long countItemComments(long itemId) throws ManagerException {
		if (itemId < 1) {
			log.warn("[countItemComments] item id [" + itemId + "] is invalid");
			return 0;
		}
		try {
			return dsrCommentDAO.countDsrCommentByItem(itemId);
		} catch (DaoException e) {
			String message = "[countItemComments] cause exception, item id: [" + itemId + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public long countSellerComments(long sellerId) throws ManagerException {
		if (!memberManager.isCorrectMemberId(sellerId)) {
			log.warn("[countSellerComments] seller id [" + sellerId + "] is invalid");
			return 0;
		}
		try {
			return dsrCommentDAO.countDsrCommentBySellerId(sellerId);
		} catch (DaoException e) {
			String message = "[countSellerComments] cause exception, seller id: [" + sellerId + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	/**
	private MemcachedManager dsrCommentsMemcachedManager;

	public void setDsrCommentsMemcachedManager(MemcachedManager dsrCommentsMemcachedManager) {
		this.dsrCommentsMemcachedManager = dsrCommentsMemcachedManager;
	}

	private String generateItemKey(long itemId, int page) {
		return "I." + PinjuConstant.COMMENTS_ITEM_PAGE_SIZE + "." + itemId + "." + page;
	}

	private String generateSellerKey(long memberId, int page) {
		return "M." + PinjuConstant.COMMENTS_SELLER_PAGE_SIZE + "." + memberId + "." + page;
	}
	*/
}
