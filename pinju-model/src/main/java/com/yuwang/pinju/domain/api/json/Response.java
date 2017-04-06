package com.yuwang.pinju.domain.api.json;

public class Response {

	public final static String SUCCESS = "success";

	private String result;

	public Response(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
