package com.yuwang.pinju.core.trade.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;

/**
 * Created on 2011-8-10
 * @see
 * <p>Discription: 盛付通非直连支付</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface SdoDirectPayAO extends DirectPayAO{
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>Discription: 盛付通非直连付款
	 * 	1.生成即时到帐订单
	 *  2.生成即时到账支付订单
	 *  3.生成即时到账发送日志
	 *  4.生成盛付通签名加密串
	 * </p>
	 * @param directPayParamDO
	 * @return 处理成功Result.isSuccess为true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result sdoDirectPay(DirectPayParamDO directPayParamDO);
	
	/**
	 * 
	 * Created on 2011-8-11
	 * <p>Discription: 盛付通付款回调
	 * 	1.修改即时到帐订单,即时到帐支付订单状态
	 *  2.插入支付回调日志
	 * </p>
	 * @param directPayReceiveParamDO
     * @return 处理成功Result.isSuccess为true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result sdoDirectPayNotify(DirectPayReceiveParamDO directPayReceiveParamDO,boolean payState);
	
	/**
	 * 
	 * Created on 2011-8-15
	 * <p>Discription: 得到发送报文详情</p>
	 * @param amount
	 * @param orderId
	 * @param dateString
	 * @param productNo
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getSendDetail(String amount, Long orderId,
			String dateString, String productNo);
	/**
	 * 
	 * Created on 2011-8-12
	 * <p>Discription: 盛付通回调签名验证</p>
	 * @param amount
	 * @param payAmount
	 * @param orderNo
	 * @param serialNo
	 * @param status
	 * @param merchantNo
	 * @param payChannel
	 * @param discount
	 * @param signType
	 * @param payTime
	 * @param currencyType
	 * @param productNo
	 * @param productDesc
	 * @param remark1
	 * @param remark2
	 * @param exInfo
	 * @param mac
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result verifySign(String amount , String payAmount ,String orderNo
	    		,String serialNo,String status,String merchantNo , String payChannel
	    		,String discount,String signType ,String payTime,String currencyType
	    		,String productNo,String productDesc,String remark1,String remark2
	    		,String exInfo,String mac);

	
}

