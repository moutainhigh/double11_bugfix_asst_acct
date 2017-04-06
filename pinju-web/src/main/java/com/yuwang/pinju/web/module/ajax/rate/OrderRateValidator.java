package com.yuwang.pinju.web.module.ajax.rate;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.rate.ao.RateBuyerAO;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

public class OrderRateValidator implements PinjuAction {
	
	private final static Log log = LogFactory.getLog(OrderRateValidator.class);

	private final static int RESULT_SUCCESS = 0;
	private final static int RESULT_EMPTY = 1;
	private final static int RESULT_TOO_LONG = 2;
	private final static int RESULT_ILLEGAL = 3;

	private String[] comments;
	private int[] result;

	@Override
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("current user unlogin, request ip: " + ServletUtil.getRemoteIp());
			return "unlogin";
		}
		if (comments == null || comments.length == 0) {
			result = new int[0];
			log.warn("comments is empty or null");
			return SUCCESS;
		}
		result = new int[comments.length];
		for (int i = 0; i < comments.length; i++) {
			result[i] = validate(comments[i]);
			log.debug("comments [" + i + "], result: " + result[i] + ", " + comments[i]);
		}
		return SUCCESS;
	}

	private int validate(String str) {
		if (StringUtils.isBlank(str)) {
			return RESULT_EMPTY;
		}
		if (str.length() > 300) {
			return RESULT_TOO_LONG;
		}
		if (WordFilterFacade.scan(str, RateBuyerAO.WORD_FILTER_TYPE)) {
			return RESULT_ILLEGAL;
		}
		return RESULT_SUCCESS;
	}

	public int[] getResult() {
		return result;
	}

	public void setComments(String[] comments) {
		this.comments = comments;
	}
	
	public static void main(String[] args) {
		System.out.println(MemberIdPuzzle.encode(100000285009000L));
	}
}
