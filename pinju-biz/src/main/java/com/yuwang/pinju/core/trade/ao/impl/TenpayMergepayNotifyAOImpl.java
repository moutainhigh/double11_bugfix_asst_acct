package com.yuwang.pinju.core.trade.ao.impl;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ReflectionUtils;
import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.tenpay.MD5Util;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.order.manager.cache.ItemBuyNumCacheManager;
import com.yuwang.pinju.core.trade.ao.TenpayMergepayNotifyAO;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.trade.TenpayMergepayRecvParamDO;
import com.yuwang.pinju.domain.trade.TenpaySinglepayRecvParamDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

public class TenpayMergepayNotifyAOImpl extends TenPayAbstractBaseAO implements TenpayMergepayNotifyAO{
	private  final   Log log = LogFactory.getLog(this.getClass().getName());
	protected final Log PAYLOG = LogFactory.getLog("tenpay-pay");
	private VouchCreateManager vouchCreateManager;
	private VouchQueryManager vouchQueryManager;
	private OrderUpDateManager orderUpDateManager;
	private OrderQueryManager orderQueryManager;
	private ItemManager itemManager;
	private ItemBuyNumCacheManager itemBuyNumCacheManager;
	private DataSourceTransactionManager zizuTransactionManager;
	/**
	 * <p>
	 * 合并支付页面回调通知,不处理业务
	 * </p>
	 * 
	 * @param param
	 * @return 
	 */
	public Result mergePayNotify(TenpayMergepayRecvParamDO param){
		Result result = new ResultSupport();

		
		//验证财付通签名
		Boolean verifySignSuccess = verifyNotifySign(param);
		result.setModel("verifySignSuccess", verifySignSuccess);
		if(!verifySignSuccess){
			result.setSuccess(false);
			log.error("mergePayNotify, verify sign from tenpay error");
			return result;
		}
		
		//解析参数
		List<TenpaySinglepayRecvParamDO> recvParamList = parseRequest(param);
		
		
		//设置页面显示参数
		setDisplayData(result, recvParamList);

		//创建支付回调通知日志
		insertTradePayBackLog(recvParamList);

		//对于返回的每笔订单处理
        result.setSuccess(processOrderDetail(recvParamList));


		
		return result;
	}
	
	
	
	
	/**
	 * <p>R
	 * 对于返回的每笔订单处理
	 * </p>
	 * 
	 * @param result
	 * @param recvParamList
	 * @return 
	 */
	private void setDisplayData(Result result, List<TenpaySinglepayRecvParamDO> recvParamList){
		result.setModel("recvParamList", recvParamList);
		Money totalFee = new Money(0) ;
		for(TenpaySinglepayRecvParamDO tenpaySinglepayRecvParamDO:recvParamList){
			//System.out.println(tenpaySinglepayRecvParamDO.getTotalFee());
			totalFee.addTo(new Money(Long.parseLong(tenpaySinglepayRecvParamDO.getTotalFee())));
		}
		
		result.setModel("totalFee", MoneyUtil.getCentToDollar(totalFee.getCent()));
	}
	
	/**
	 * <p>
	 * 对于返回的每笔订单处理
	 * </p>
	 * 
	 * @param recvParamList
	 * @return 
	 */
	private boolean processOrderDetail(final List<TenpaySinglepayRecvParamDO> recvParamList){
		boolean flag = true;
		for(TenpaySinglepayRecvParamDO singlepayRecvParam:recvParamList){
			try{
				//支付结果(0为支付成功,其它为失败)
				if(!"0".equalsIgnoreCase(singlepayRecvParam.getPayResult())){
					continue;
				}
				
				//取得订单号
				String orderIdString = singlepayRecvParam.getSpBillno();
				Long orderId = Long.parseLong(orderIdString);
				log.info("order id：" + singlepayRecvParam.getSpBillno());
				
				
				//取得交易号
				String orderPayId = singlepayRecvParam.getTransactionId();
				log.info("orderPayId：" + orderPayId);
				
				//查询支付订单
				VouchPayDO  vouchPayDO = vouchQueryManager.selectOrderPayByOrderPayId(orderPayId);
				if(vouchPayDO == null){
					log.error("order id：" + singlepayRecvParam.getSpBillno() + ", no VouchPayDO exist!");
					flag = false;
					continue;
				}

				
				//是否处理过
				Boolean isProcessed = 
					vouchPayDO != null && 
					vouchPayDO.getPayState().compareTo(VouchPayConstant.VOUCH_PAY_STATE_UNPAID) != 0;
				
				if(isProcessed){
					log.info("order id：" + singlepayRecvParam.getSpBillno() + " already processed!");
					continue;
					
				//该笔订单未处理
				}else{	
					//更新订单状态,更新支付订单
					if(!updateData(vouchPayDO.getBuyerId(), orderId, orderPayId, singlepayRecvParam)){
						flag = false;
					}
				}
				
			}catch (Exception e) {
				log.error("Event=[TenpayMergepayNotifyAOImpl#processOrderDetail] 对于返回的每笔订单处理", e);
				flag = false;
				continue;
			}
			
		}
		return flag;
		
	}
	
	private Boolean updateData(final long memberId, final Long orderId,
			final String orderPayId, final  TenpaySinglepayRecvParamDO singlepayRecvParam){
			PAYLOG.debug("start order updateData!");
			//显示折扣商品是否已关闭换库存
			boolean is_ready_stock = false;
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = zizuTransactionManager.getTransaction(def);
			boolean flag = false;
			try {
				OrderDO orderDO = orderQueryManager.getOrderDOById(orderId);
				if(orderDO!=null && orderDO.getOrderState().compareTo(OrderDO.ORDER_STATE_6)==0){
					is_ready_stock = true;
				}
				//更新订单状态
				flag = orderUpDateManager.upOrderState(memberId, orderId, OrderDO.ORDER_STATE_2, "已付款");
				if(flag){
				//更新支付订单
					flag = updateOrderPay(orderId, orderPayId, singlepayRecvParam);
				}
				if(!flag){
					zizuTransactionManager.rollback(status);
					throw new ManagerException("Event=[TenpayMergepayAOImpl#updateData] error 更新订单状态失败 orderId".concat(String.valueOf(orderId)));
				}
				PAYLOG.debug("start order updateData commit!".concat(String.valueOf(orderId)));
				zizuTransactionManager.commit(status);
				PAYLOG.debug("end order updateData commit!".concat(String.valueOf(orderId)));
			} catch (ManagerException e) {
				PAYLOG.debug("start order updateData rollback!".concat(String.valueOf(orderId)));
				zizuTransactionManager.rollback(status);
				PAYLOG.debug("end order updateData rollback!".concat(String.valueOf(orderId)));
				log.error("Event=[TenpayMergepayAOImpl#updateData] upOrderState, updateOrderPay, stockDown", e);
				return flag;
			}
			try{
				//支付成功扣库存 
				stockDown(orderId,is_ready_stock);
				List<OrderItemDO> list = orderQueryManager.queryOrderItemList(orderId);
				Iterator<OrderItemDO> ator = list.iterator();
				while(ator.hasNext()){
					OrderItemDO  orderItemDO = ator.next();
					itemBuyNumCacheManager.setCacheItemBuyNum(orderItemDO.getItemId(), orderItemDO.getBuyNum());
				}
			}catch(Exception e){
				log.error("pay stockDown error".concat(String.valueOf(orderId)));
			}

	return flag;
}

	/**
	 * 扣库存
	 * @param orderId 订单id
	 * @return true 支付成功
	 */
	private Boolean stockDown(Long orderId,boolean is_ready_stock){
		try {
			List<OrderItemDO> orderItemList = orderQueryManager.queryOrderItemList(orderId);
			if(orderItemList == null)
				return false;

			for(OrderItemDO orderItemDO:orderItemList){
				
				boolean isCutStock = orderItemDO.getBussinessType().equals(DirectPayConstant.BIZ_TYPE_PRICE);
				if(isCutStock || is_ready_stock){
					log.debug("stock down, orderItemId=" + orderItemDO.getItemId() + ", itemskuid = " + orderItemDO.getItemSkuId() + ", buynum = " + orderItemDO.getBuyNum());
					itemManager.cutStock(orderItemDO.getItemId(), orderItemDO.getItemSkuId(), orderItemDO.getBuyNum());
				}
			}
		}catch (Exception e) {
			log.error("Event=[TenpayMergepayAOImpl#stockDown] stockDown error", e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 更新支付订单
	 * @param orderId 订单id
	 * @param orderPayId 支付订单id
	 * @param singlepayRecvParam 单笔参数
	 * @return 
	 */
	private Boolean updateOrderPay (
			final Long orderId,
			final String orderPayId, 
			final  TenpaySinglepayRecvParamDO singlepayRecvParam)throws ManagerException{
		
		VouchPayDO vouchPayDO = new VouchPayDO();
		
		//订单id
		vouchPayDO.setOrderId(orderId);
		
		//支付状态
		vouchPayDO.setPayState(VouchPayConstant.VOUCH_PAY_STATE_PAID);
		
		//内部支付编号
		vouchPayDO.setOrderPayId(orderPayId);
		
		//第三方支付编号，即财付通交易单号
		vouchPayDO.setOutPayId(singlepayRecvParam.getTransactionId());
		
		//支付时间
		Long payTime = Long.parseLong(singlepayRecvParam.getPayTime()) * 1000;
		vouchPayDO.setDealTime(new Date(payTime));
		
		//实际支付金额
		vouchPayDO.setRealPayMentamount(Long.parseLong(singlepayRecvParam.getFee1()));
		
		Boolean success = vouchCreateManager.updateOrderPay(vouchPayDO);
		
		return success;
	}
	
	/**
	 * 合并支付回调通知验证前台签名
	 * @param param
	 * @return 
	 */
	private Boolean verifyNotifySign(TenpayMergepayRecvParamDO param){
		
		StringBuffer sb = new StringBuffer();
		sb.append("return_no=" + param.getReturnNo());
		sb.append("&spid=" + param.getSpid());

		int i = 0;
		for(String s:param.getRequest()){
			if(i == 0){
				sb.append("&request=" + s);
			}else{
				sb.append("&request" + i + "=" + s);
			}
			
			i++;
		}
		
		sb.append("&key=" + getMD5Key());

		log.debug("test more verifyNotifySign parms：" + sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), CHARSET);
		log.debug("test more verifyNotifySign sign：" + sign);
		log.debug("sign：" + sign);
		
		Boolean verifySignSuccess = sign.equalsIgnoreCase(param.getSign());
		
		return verifySignSuccess;
	}

	

	
	
	
	
	
	
	
	
	
	/**
	 * <p>
	 * 创建支付回调通知日志
	 * </p>
	 * 
	 * @param param
	 * @return 
	 */
	private void insertTradePayBackLog(final List<TenpaySinglepayRecvParamDO> recvParamList){
		
		try{
			for(TenpaySinglepayRecvParamDO param:recvParamList){
				
				Date now = new Date();
				
				//插入接收日志（测试数据）
				PayBackLogDO payBackLogDO = new PayBackLogDO();

				payBackLogDO.setSendType(PinjuConstant.TENPAY_SINGLE_PAY_CMDNO);
				payBackLogDO.setOrderPayId(param.getTransactionId());
				payBackLogDO.setOutPayId(param.getTransactionId());
				
				// 接受内容, 合并支付超过4000时只记录4000个
				String backInfoString = param.getBackInfo();
				if(backInfoString.length() > 4000)
					backInfoString = backInfoString.substring(0, 3999);
				
				payBackLogDO.setBackInfo(backInfoString);
				
				payBackLogDO.setCreationTime(now);
				payBackLogDO.setPayType(PinjuConstant.TENPAY_SINGLE_PAY_CMDNO);
				//payBackLogDO.setPayBackAttributes(payBackAttributes);
				payBackLogDO.setGmtCreate(now);
				payBackLogDO.setGmtModified(now);
				
				
				vouchCreateManager.insertTradePayBackLog(payBackLogDO);

			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 解析合并支付回调request
	 * 
	 * @param param
	 * @return 
	 */
	private List<TenpaySinglepayRecvParamDO> parseRequest(TenpayMergepayRecvParamDO param){
		List<TenpaySinglepayRecvParamDO> recvParamList = new LinkedList<TenpaySinglepayRecvParamDO>();
		
		for(String request:param.getRequest()){
			
			try{
				request = URLDecoder.decode(request, CHARSET);
				log.debug("request=" + request);
			}catch (Exception e) {
				e.printStackTrace();
			}
			TenpaySinglepayRecvParamDO recvParam = getRecvParamByRequest(request);
			
			recvParam.setBackInfo(param.getRecvContent());
			
			recvParamList.add(recvParam);
		}
		
		return recvParamList;
	}
	
	/**
	 * 解析单笔request
	 * 
	 * @param param
	 * @return 
	 */
	private TenpaySinglepayRecvParamDO getRecvParamByRequest(String request){
		TenpaySinglepayRecvParamDO recvParam = new TenpaySinglepayRecvParamDO();
		
		String[] p = request.split("&", -1);
		for(String param:p){
			int i = param.indexOf("=");
			if(i == p.length - 1)
				continue;
			
			String name = param.substring(0, i);
			String valueString = param.substring(i + 1, param.length());
			String methodName = recvNameMap.get(name);
			if(methodName != null){
				Method method = ReflectionUtils.findMethod(TenpaySinglepayRecvParamDO.class, methodName, String.class);
				ReflectionUtils.invokeMethod(method, recvParam, valueString);
			}
		}

		return recvParam;
	}
	
	private static Map<String, String> recvNameMap = new HashMap<String, String>();
	
	static{
		recvNameMap.put("cmdno", "setCmdno");
		recvNameMap.put("pay_result", "setPayResult");
		recvNameMap.put("pay_info", "setPayInfo");
		recvNameMap.put("bargainor_id", "setBargainorId");
		recvNameMap.put("transaction_id", "setTransactionId");
		recvNameMap.put("sp_billno", "setSpBillno");
		recvNameMap.put("total_fee", "setTotalFee");
		recvNameMap.put("fee_type", "setFeeType");
		recvNameMap.put("attach", "setAttach");
		recvNameMap.put("pay_time", "setPayTime");
		recvNameMap.put("fee1", "setFee1");
		recvNameMap.put("fee2", "setFee2");
		recvNameMap.put("fee3", "setFee3");
		recvNameMap.put("vfee", "setVfee");
	}
	
	@Override
	protected String getMD5Key() {
		return PinjuConstant.TENPAY_PAY_MD5KEY;
	}

	public void setVouchCreateManager(VouchCreateManager vouchCreateManager) {
		this.vouchCreateManager = vouchCreateManager;
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}
	
	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	/**
	 * <code>2011-11-11 添加财富通单笔支付回调</code>
	 */
	
	


	/**
	 * 
	 * Created on 2011-11-11
	 * <p>Discription: <code>财富通后台单笔回调 </code></p>
	 * @param param
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result backGroundMergePayNotify(Map<String, String> parameters){
		Result result = new ResultSupport();
		//验证财付通签名
		if(!verifySign(parameters,parameters.get("sign"))){
			result.setSuccess(false);
			PAYLOG.debug("verifySign error");
			return result;
		}
		TenpaySinglepayRecvParamDO tenpaySinglepayRecvParamDO = getTenpaySinglepayRecvParamDO(parameters);
		//创建支付回调通知日志
		List<TenpaySinglepayRecvParamDO> recvParamList = new LinkedList<TenpaySinglepayRecvParamDO>();
		recvParamList.add(tenpaySinglepayRecvParamDO);
		PAYLOG.debug("start insert backlog");
		insertTradePayBackLog(recvParamList);
		PAYLOG.debug("end insert backlog");
		//对于返回的每笔订单处理
		result = processOrderDetail(tenpaySinglepayRecvParamDO);
		return result;
	}
	

	
	
	
	
	/**
	 * 
	 * Created on 2011-9-1
	 * <p>Discription: 财付通签名 使用财付通MD5加密 Md5(原字符串&key=商户密钥).toLowerCase</p>
	 * @param parameters
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Map<String, String> createSign(Map<String, String> parameters) {
		StringBuffer sb = new StringBuffer();

		if(StringUtil.isNotEmpty(parameters.get("cmdno")))
			sb.append("cmdno=").append(parameters.get("cmdno"));
		if(StringUtil.isNotEmpty(parameters.get("pay_result")))
			sb.append("&pay_result=").append(parameters.get("pay_result"));
		if(StringUtil.isNotEmpty(parameters.get("date")))
			sb.append("&date=").append( parameters.get("date"));
		if(StringUtil.isNotEmpty(parameters.get("transaction_id")))
			sb.append("&transaction_id=").append(parameters.get("transaction_id"));
		if(StringUtil.isNotEmpty(parameters.get("sp_billno")))
			sb.append("&sp_billno=").append( parameters.get("sp_billno"));
		if(StringUtil.isNotEmpty(parameters.get("total_fee")))
			sb.append("&total_fee=").append(parameters.get("total_fee"));
		if(StringUtil.isNotEmpty(parameters.get("fee_type")))
			sb.append("&fee_type=").append( parameters.get("fee_type"));
		if(StringUtil.isNotEmpty(parameters.get("attach")))
			sb.append("&attach=").append( parameters.get("attach"));
		
		sb.append("&key=" + getMD5Key());
		String sign = MD5Util.MD5Encode(sb.toString(), CHARSET).toLowerCase();
		PAYLOG.debug("createSign start:".concat(sb.toString()));
		PAYLOG.debug("sign:".concat(sign));
		parameters.put("sign", sign);
		parameters.put("backInfo", sb.toString());
		return parameters;
	}
	
	
	
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: 验证签名</p>
	 * @param parameters
	 * @param signString
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean verifySign(Map<String, String> parameters,String signString){
		createSign(parameters);
		String _signString = parameters.get("sign");
		return StringUtil.equalsIgnoreCase(_signString, signString);
	
	}
	/**
	 * 解析合并支付回调
	 * 
	 * @param param
	 * @return 
	 */
	private TenpaySinglepayRecvParamDO getTenpaySinglepayRecvParamDO(Map<String, String> parameters){

		TenpaySinglepayRecvParamDO tenpaySinglepayRecvParamDO = new TenpaySinglepayRecvParamDO();
		tenpaySinglepayRecvParamDO.setCmdno(parameters.get("cmdno"));
		tenpaySinglepayRecvParamDO.setPayResult(parameters.get("pay_result"));
		tenpaySinglepayRecvParamDO.setPayInfo(parameters.get("pay_info"));
		tenpaySinglepayRecvParamDO.setBargainorId(parameters.get("bargainor_id"));
		tenpaySinglepayRecvParamDO.setTransactionId(parameters.get("transaction_id"));
		tenpaySinglepayRecvParamDO.setSpBillno(parameters.get("sp_billno"));
		tenpaySinglepayRecvParamDO.setTotalFee(parameters.get("total_fee"));
		tenpaySinglepayRecvParamDO.setFeeType(parameters.get("fee_type"));
		tenpaySinglepayRecvParamDO.setAttach(parameters.get("attach"));
		tenpaySinglepayRecvParamDO.setPayTime(parameters.get("pay_time"));
		tenpaySinglepayRecvParamDO.setFee1(parameters.get("total_fee"));
		tenpaySinglepayRecvParamDO.setBackInfo(parameters.get("backInfo"));
		return tenpaySinglepayRecvParamDO;
	}
	
	
	

	/**
	 * <p>
	 * 对于返回的每笔订单处理
	 * </p>
	 * 
	 * @param recvParamList
	 * @return 
	 */
	private Result processOrderDetail(TenpaySinglepayRecvParamDO tenpaySinglepayRecvParamDO){
		PAYLOG.debug("start processOrderDetail".concat(tenpaySinglepayRecvParamDO.getSpBillno()));
		Result result = new ResultSupport();
		result.setSuccess(false);
			try{
				//支付结果(0为支付成功,其它为失败)
				if(!"0".equalsIgnoreCase(tenpaySinglepayRecvParamDO.getPayResult())){
					result.setResultCode("1000001");
					PAYLOG.debug("tenpay pay fail".concat(tenpaySinglepayRecvParamDO.getSpBillno()));
					return result;
				}
				
				//取得订单号
				String orderIdString = tenpaySinglepayRecvParamDO.getSpBillno();
				Long orderId = Long.parseLong(orderIdString);
				PAYLOG.debug("orderId：" + tenpaySinglepayRecvParamDO.getSpBillno());
				//取得交易号
				String orderPayId = tenpaySinglepayRecvParamDO.getTransactionId();
				PAYLOG.debug("orderPayId：" + orderPayId);
				//查询支付订单
				VouchPayDO  vouchPayDO = vouchQueryManager.selectOrderPayByOrderPayId(orderPayId);
				if(vouchPayDO == null){
					log.error("order id：" + tenpaySinglepayRecvParamDO.getSpBillno() + ", no VouchPayDO exist!");
					PAYLOG.debug("vouchPayDO is null!".concat(tenpaySinglepayRecvParamDO.getSpBillno()));
					result.setResultCode("1000002");
					return result;
				}

				
				//是否处理过
				Boolean isProcessed = 
					vouchPayDO != null && 
					vouchPayDO.getPayState().compareTo(VouchPayConstant.VOUCH_PAY_STATE_UNPAID) != 0;
				
				if(isProcessed){
					PAYLOG.debug("vouchPayDO order is already execute!".concat(tenpaySinglepayRecvParamDO.getSpBillno()));
					result.setResultCode("1000003");
					return result;
					
				//该笔订单未处理
				}else{	
					//更新订单状态,更新支付订单
					if(updateData(vouchPayDO.getBuyerId(), orderId, orderPayId, tenpaySinglepayRecvParamDO)){
						result.setSuccess(true);
						return result;
					}
				}
			}catch (Exception e) {
				log.error("Event=[TenpayMergepayNotifyAOImpl#processOrderDetail(TenpaySinglepayRecvParamDO tenpaySinglepayRecvParamDO)] 对于返回的每笔订单处理", e);
				PAYLOG.debug("processOrderDetail error".concat(tenpaySinglepayRecvParamDO.getSpBillno()));
				return result;
			}
			
		
			return result;
		
	}


	public void setZizuTransactionManager(
			DataSourceTransactionManager zizuTransactionManager) {
		this.zizuTransactionManager = zizuTransactionManager;
	}


	public void setItemBuyNumCacheManager(
			ItemBuyNumCacheManager itemBuyNumCacheManager) {
		this.itemBuyNumCacheManager = itemBuyNumCacheManager;
	}
	
	
	
}