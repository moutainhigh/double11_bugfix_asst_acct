package com.yuwang.pinju.core.trade.ao;

import java.util.SortedMap;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;

/**
 * Created on 2011-8-31
 * 
 * @see <p>
 *      Discription: 腾讯财付通即时到账AO
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface TenDirectPayAO{
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * 消保即时到账
	 * Discription: 财付通即时到账付款 1.生成即时到帐订单 2.生成即时到账支付订单 3.生成即时到账发送日志 4.生成盛付通签名加密串
	 * </p>
	 * 
	 * @param directPayParamDO
	 * @return 处理成功Result.isSuccess为true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result tenDirectPay(DirectPayParamDO directPayParamDO);

	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * 团购即时到账
	 * Discription: 财付通即时到账付款 1.生成即时到账支付订单 2.生成即时到账发送日志 3.生成盛付通签名加密串
	 * </p>
	 * 
	 * @param directPayParamDO
	 * @return 处理成功Result.isSuccess为true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result groupTenDirectPay(DirectPayParamDO directPayParamDO);
	
	
	/**
	 * 
	 * Created on 2011-8-11
	 * <p>
	 * Discription: 财付通付款回调 1.修改即时到帐订单,即时到帐支付订单状态 2.插入支付回调日志
	 * </p>
	 * 
	 * @param directPayReceiveParamDO
	 * @return 处理成功Result.isSuccess为true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result tenDirectPayNotify(DirectPayReceiveParamDO directPayReceiveParamDO,
			boolean payState);
	
	/**
	 * 
	 * Created on 2011-9-9
	 * <p>Discription: 担保订单即时到账。财付通付款回调 1.修改担保交易订单,即时到帐支付订单状态 2.插入支付回调日志</p>
	 * @param directPayReceiveParamDO
	 * @param payState
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result vouchTenDirectPayNotify(
			DirectPayReceiveParamDO directPayReceiveParamDO, boolean payState);
	
	
	
	/**
	 * 
	 * Created on 2011-8-15
	 * <p>
	 * Discription: 得到发送报文详情
	 * </p>
	 * 
	 * @param amount
	 * @param orderId
	 * @param dateString
	 * @param productNo
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	String getSendDetail(String amount, Long orderId, String dateString,
			String productNo);
	
	/**
	 * 
	 * Created on 2011-9-5
	 * <p>Discription: 验证回调签名</p>
	 * @param parameters
	 * @param signString
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	boolean verifySign(SortedMap<String, String> parameters,String signString);
	
	/**
	 * 
	 * Created on 2011-9-24
	 * <p>Discription: 回掉页面通知通过支付编号显示网站内部订单编号</p>
	 * @param orderPayId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result queryOrderId(Long orderPayId);
	
}
