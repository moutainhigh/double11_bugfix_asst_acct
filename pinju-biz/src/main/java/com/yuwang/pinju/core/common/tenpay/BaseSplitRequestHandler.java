package com.yuwang.pinju.core.common.tenpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseSplitRequestHandler extends RequestHandler {

	public BaseSplitRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}
	
	protected void createSign() {
		super.createSign();
		
		this.setParameter("sign", this.getParameter("sign").toUpperCase());
	}
}
