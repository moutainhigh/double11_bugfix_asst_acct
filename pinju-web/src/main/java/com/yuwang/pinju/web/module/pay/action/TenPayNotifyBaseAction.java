package com.yuwang.pinju.web.module.pay.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * Created on 2011-9-13
 * @see
 * <p>Discription: 财富通接口调用基类</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public  abstract class TenPayNotifyBaseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1944210945392624548L;

	/**
	 * 财富通参数集合
	 */
	private SortedMap<String, String> parameters;
	
	/**
	 * 业务类型
	 */
	protected Integer bizType;

	/**
	 * 错误提示
	 */
	protected String errorMessage;
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: [封装接受参数]</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected abstract SortedMap<String, String> setParameters(SortedMap<String, String> parameters);
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: [设置业务类型] </p>
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected abstract Integer getBizType();
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: [效验签名]</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected abstract boolean verifySign();
	
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: [获取财富通处理状态]</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected abstract boolean isTenState();
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: [发货处理]</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected abstract boolean notifyDelivery();

	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: 获取封装参数后的集合</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected SortedMap<String, String> getParameters() {
		if (this.parameters != null && !this.parameters.isEmpty()) {
			return this.parameters;
		}
		this.parameters = new TreeMap<String, String>();
		setParameters(this.parameters);
		return this.parameters;
	}

	/**
	 * 
	 * Created on 2011-10-11
	 * <p>Discription: 组装财付通回传报文</p>
	 * @param getParameters
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected String getRevDetail(){
		SortedMap<String, String> parameters = getParameters();
		String revDetail = request.getRequestURI().concat("?");
		int i = 0;
		for(Map.Entry<String, String> entry : parameters.entrySet()){
			i++;
			revDetail += entry.getKey().concat("=").concat(String.valueOf(entry.getValue()));
			if(i!=1){
				revDetail += "&";
			}
		}
		revDetail = StringUtil.chop(revDetail);
		return revDetail;
	}
	/**
	 * Created on 2011-8-11
	 * <p>
	 * Discription: 财付通支付返回服务端接口
	 * </p>
	 * 
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String tenNotify() {
		boolean flag = false;
		try {
			// 效验签名
			if (!verifySign()) {
				response.getOutputStream().print("签名失败");
			}else{
				// 调用处理 (无论财付通支付是否成功)
				flag = notifyDelivery();
			}
			if(flag){
				//业务处理成功时通知财付通,否则让财付通补单
			   response.getOutputStream().print(PinjuConstant.TENPAY_DIRECTPAY_SUCCESS);
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Event=[".concat(this.getClass().toString()).concat("#tenNotify] UnsupportedEncodingException:"),e);
			log.error("direct pay debug 打印当前第3方回调参数报文--->".concat(getRevDetail()));
		} catch (IOException e) {
			log.error("Event=[".concat(this.getClass().toString()).concat("#tenNotify] IOException:"),e);
			log.error("direct pay debug 打印当前第3方回调参数报文--->".concat(getRevDetail()));
		} catch (Exception e) {
			log.error("Event=[".concat(this.getClass().toString()).concat("#tenNotify] Exception:"),e);
			log.error("direct pay debug 打印当前第3方回调参数报文--->".concat(getRevDetail()));
		}
		return null;
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * Created on 2011-9-9
	 * <p>Discription: 页面回调</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String tenPostBack() {
		//this.tenNotify();  //前台调后台供王凯测试用
		// 效验签名
		if (!verifySign()) {
			errorMessage = "返回参数签名异常！";
			return ERROR;
		}
		return notifyJump();
	}
	
	
	
	
	/**
	 * 
	 * Created on 2011-9-6
	 * <p>
	 * Discription: 获取支付后页面跳转
	 * </p>
	 * 
	 * @param attach
	 * @param payState
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected String notifyJump() {
		String returnFlag = ERROR;
		if (getBizType().compareTo(0)==0)
			return returnFlag;
		switch (getBizType()) {
		case DirectPayConstant.BIZ_TYPE_MARGIN:
			Map<String, Object> parametersMap = new HashMap<String, Object>();
			parametersMap.put("out_trade_no", parameters.get("out_trade_no"));
			ActionContext.getContext().setParameters(parametersMap);
			returnFlag = DirectPayConstant.MARGIN_BACK_GO_ACTION; //保证金跳转action
			break;
		case DirectPayConstant.BIZ_TYPE_GROUP:
			returnFlag = SUCCESS;
			break;
		case DirectPayConstant.BIZ_TYPE_LIMITDISCOUNT:
			returnFlag = SUCCESS;
			break;
		case DirectPayConstant.BIZ_TYPE_PRICE:
			returnFlag = SUCCESS;
			
			break;
		default:
			/**
			 * 没有匹配项处理
			 */
			returnFlag = ERROR;
			break;
		}
		return returnFlag;
	}


	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: [发货失败处理]</p>
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected void deliveryFail(Result result){
		
	}
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: [业务失败处理]</p>
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected void upPayFail(Result result){
		if(result == null){
			log.error("direct pay debug 即时到账支付回调业务处理失败,返回result为null!无法理解情况");
			log.error("direct pay debug 打印当前第3方回调参数报文--->".concat(getRevDetail()));
			return ;
		}
		log.warn("direct pay debug 即时到账支付回调业务处理失败,失败描述信息----->".concat(ResultCodeMsg.getResultMessage(result.getResultCode())));
		log.warn("direct pay debug 打印当前第3方回调参数报文--->".concat(getRevDetail()));
	}
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: [无业务类型处理]</p>
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected void no_productNo(){
		
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}

