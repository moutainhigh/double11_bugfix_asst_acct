package com.yuwang.pinju.web.struts2;

import java.util.EventListener;

public interface HashLink extends EventListener {
	
	String VALIDATE_SUCCESS = "VALIDATE_SUCCESS";
	
	void onHash(long memberId);
	String onValidate(long memberId);
	String onError(String message);
	void onConvertHash();
}
