package com.yuwang.pinju.core.common;

public class ResultSupportExt extends ResultSupport {

	private static final long serialVersionUID = 1L;

	public final static String SUCCESS = "SUCCESS";

	private ResultSupportExt(String resultCode) {
		setResultCode(resultCode);
	}

	public static Result createSuccess() {
		return new ResultSupportExt(SUCCESS);
	}

	public static Result createSuccess(Object returnObject) {
		Result result = new ResultSupportExt(SUCCESS);
		result.setModel(returnObject);
		return result;
	}

	public static Result createSuccess(String key, Object returnObject) {
		Result result = new ResultSupportExt(SUCCESS);
		result.setModel(key, returnObject);
		return result;
	}

	public static Result createError(String resultCode) {
		return new ResultSupportExt(resultCode);
	}

	@Override
	public boolean isSuccess() {
		return SUCCESS.equals(getResultCode());
	}
}
