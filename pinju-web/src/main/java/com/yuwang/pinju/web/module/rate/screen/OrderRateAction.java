package com.yuwang.pinju.web.module.rate.screen;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.constant.rate.RateKeyConstant;
import com.yuwang.pinju.core.rate.ao.RateBuyerAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.rate.comment.RateOrderModel;
import com.yuwang.pinju.domain.rate.comment.RateOrderModel.DsrComments;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

public class OrderRateAction implements PinjuAction, ModelDriven<RateOrderModel> {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(OrderRateAction.class);

	private RateBuyerAO rateBuyerAO;

	private RateOrderModel model = new RateOrderModel();

	private String invokeMessage;
	private String[] errors;
	private Long mid;

	@Override
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if(!login.isLogin()) {
			log.warn("current hasnot login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		ServletUtil.forbidBrowserCache();
		mid = login.getMemberId();
		return load(login);
	}

	public String doComment() {
		String method = ServletActionContext.getRequest().getMethod();
		if ("GET".equals(method)) {
			return MAIN_PAGE;
		}
		ServletUtil.forbidBrowserCache();
		log.debug(model);
		return doComment0();
	}

	private String load(CookieLoginInfo login) {
		long orderId = model.getOrderId();
		if (orderId < 1) {
			log.error("order id is invalid, order id: " + model.getOid());
			invokeMessage = Message.getMessage("result.rate.order.access.denied");

			return ERROR;
		}
		Result result = rateBuyerAO.queryOrderForRate(login.getMemberId(), orderId);
		if (!result.isSuccess()) {
			invokeMessage = Message.getMessage(result.getResultCode(), OrderDO.ORDER_RATE_TIME_LIMIT);
			
			return ERROR;
		}
		putValue(result);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void putValue(Result result) {
		model.setSellerMember( result.getModel(RateKeyConstant.KEY_RATE_SELLER_MEMBER, MemberDO.class) );
		model.setOrder( result.getModel(RateKeyConstant.KEY_RATE_ORDER, OrderDO.class) );
		model.setOrderItems( result.getModel(RateKeyConstant.KEY_RATE_ORDER_ITEMS, List.class) );
		model.setItemDsr( result.getModel(RateKeyConstant.KEY_RATE_ITEM_DSRS, List.class) );
		model.setShopDsr( result.getModel(RateKeyConstant.KEY_RATE_SHOP_DSRS, List.class) );
	}

	private String doComment0() {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		// 没有登录
		if(!login.isLogin()) {
			log.warn("current hasnot login, " + login);
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		if (!validate()) {
			load(login);
			log.debug("validate parameter: " + invokeMessage);
			return INPUT;
		}

		mid = login.getMemberId();

		Result result = rateBuyerAO.rateOrder(login.getMemberId(), model);

		return processCommentsResult(result);
	}

	private String processCommentsResult(Result result) {
		String code = result.getResultCode();
		if (log.isInfoEnabled()) {
			log.info("process comment order id: [" + model.getOid() + "], result code: [" + code + "]");
		}
		if (ResultSupportExt.SUCCESS.equals(code)) {
			return SUCCESS;
		}
		invokeMessage = Message.getMessage(code, OrderDO.ORDER_RATE_TIME_LIMIT);
		return ERROR;
	}

	private boolean validate() {
		List<DsrComments> comments = model.getComment();
		if (EmptyUtil.isBlank(comments)) {
			invokeMessage = Message.getMessage(MessageName.RATE_ORDER_COMMENT_EMPTY);
			return false;
		}
		for (int i = 0, k = comments.size(); i < k; i++) {
			String comment = comments.get(i).getComment();
			if (EmptyUtil.isBlank(comment)) {
				addError(i, Message.getMessage(MessageName.RATE_ORDER_COMMENT_EMPTY), k);
				continue;
			}
			if (comment.length() > 300) {
				addError(i, Message.getMessage(MessageName.RATE_ORDER_COMMENT_LONG, 300), k);
				continue;
			}
			if (WordFilterFacade.scan(comment, RateBuyerAO.WORD_FILTER_TYPE)) {
				addError(i, Message.getMessage(MessageName.RATE_ORDER_COMMENT_ILLEGAL), k);
				continue;
			}
		}
		if (errors != null) {
			invokeMessage = Message.getMessage(MessageName.RATE_ORDER_COMMENT_ERROR);
			return false;
		}
		return true;
	}

	private void addError(int index, String message, int max) {
		if (errors == null) {
			errors = new String[max];
		}
		errors[index] = message;
	}

	@Override
	public RateOrderModel getModel() {
		return model;
	}
	public void setRateBuyerAO(RateBuyerAO rateBuyerAO) {
		this.rateBuyerAO = rateBuyerAO;
	}
	public Long getMid() {
		return mid;
	}
	public String getInvokeMessage() {
		return invokeMessage;
	}
	public String[] getErrors() {
		return errors;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
		System.out.println(MemberIdPuzzle.encode(100000085000000L));
	}
}
