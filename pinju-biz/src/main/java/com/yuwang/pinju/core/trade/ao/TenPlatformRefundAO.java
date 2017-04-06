package com.yuwang.pinju.core.trade.ao;

import java.util.SortedMap;

import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.tenpay.ScriptClientResponseHandler;
/**
 * <p>Description: 平台退款AO 接口</p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-8
 */
public interface TenPlatformRefundAO {

	/***
	 * <p>Description: 1.生成平台退款发送日志 4.生成盛付通签名加密串</p>
	 * @param refundParamDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-9
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result createPlatformRefundParam(PlatformRefundParamDO refundParamDO);
	
	
	/**
	 * <p>Description:验证签名 </p>
	 * @param parameters
	 * @param signString
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-14
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	boolean verifySign(SortedMap<String, String> parameters,String signString);
	
	/**
	 * <p>Description: 平台退款回调接口</p>
	 * @param refundParamDO
	 * @param refundState
	 * @return Result
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-14
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result platformRefundNotifySuccess(PlatformRefundParamDO refundParamDO);
	
	
	/**
	 * <p>Description:财付通返回失败，不是规定的失败code，直接手工插单</p>
	 * @param refundParamDO
	 * @param status
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result platformRefundNotifyIsError(PlatformRefundParamDO refundParamDO);
	
	/**
	 * <p>Description: 组装接受参数</p>
	 * @param resHandler
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-1
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	 public PlatformRefundParamDO getReceiveParamDO(ScriptClientResponseHandler resHandler);
	 
	 /**
	  * <p>Description: 平台退款AO（全额和部分）</p>
	  * @param paramDO
	  * @return
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-11-1
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	 public Result platformRefund(PlatformRefundParamDO paramDO);
	 
	 /**
	  * <p>Description: 发送平台退款请求， 处理返回的接口</p>
	  * @param postUrl
	  * @param paramDO
	  * @return
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-11-1
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	 public Result platformRefundSendRequest(String postUrl,PlatformRefundParamDO paramDO);
	 
	 
	 /**
	  * <p>Description:平台退款 手工单定时   处理业务接口 </p>
	  * @param refundParamDO
	  * @return
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-11-1
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	 public  Result platformRefundNotifyForTiming(PlatformRefundParamDO refundParamDO);
	 
	 
	 /**
	  * <p>Description: 退款定时调用接口</p>
	  * @param orderId
	  * @return
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-11-1
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	public Result platformRefundForOneOrder(Long orderId);
		
}


