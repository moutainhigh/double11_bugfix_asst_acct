package com.yuwang.pinju.core.constant.trade;

import com.yuwang.pinju.core.constant.system.PinjuConstant;

/**
 * <p>Description:  平台退款常量类 </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-15
 */
public class RefundConstant {
	
	/**
	 * 请求的最大次数
	 */
	public final static int MAX_REQUEST_NUMBER = 5 ;
	
	/**
	 *  CA证书名
	 */
	public final static String CACERT_NAME = PinjuConstant.TENPAY_CACERT_NAME;
	/**
	 * 个人(商户)证书
	 */
	public final static String CERTIFICATE_NAME = PinjuConstant.TENPAY_PAY_PARTNER.concat(".pfx");
	
}


