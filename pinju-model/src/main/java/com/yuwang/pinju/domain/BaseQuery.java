/**
 * 
 */
package com.yuwang.pinju.domain;

import com.yuwang.pinju.common.Money;

/**  
 * @Project: pinju-model
 * @Title: BaseQuery.java
 * @Package com.yuwang.pinju.domain
 * @Description: 基本query类型,用于页面显示用基础类,集成分页、错误提示信息
 * @author liuboen liuboen@zba.com
 * @date 2011-6-8 下午01:43:47
 * @version V1.0  
 */

public class BaseQuery extends Paginator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5505670565942976620L;

	/**
	 * 基本页面提示错误信息
	 */
	private String errorMessage;
	
	/**
	 * 将数据库价格(分)显示为元
	 * @param priceCent
	 * @return
	 */
	public static String getCentToDollar(long priceCent){
		return new Money(priceCent).toString();
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

}
