package com.yuwang.pinju.web.module.order.screen;




import com.yuwang.pinju.core.order.ao.HessianOrderAO;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * 自动确认收货手工触发
 * @author 杜成
 * @date 2011-7-13
 * @version 1.0
 */
public class AoutConfDeliveryAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HessianOrderAO hessianOrderAO;
	
	private String message;
	/**
	 * 手工确认收货
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String aoutConfDelivery(){
		try{
		log.debug("orderConfirm 手工执行 start");
		hessianOrderAO.confirmReceipt();
		log.debug("orderConfirm 手工执行 end");
		message = "run SUCCESS";
		}catch (Exception e) {
			message = "run error";
			log.error("orderConfirm 手工执行 error");
		}
		return SUCCESS;
	}
	public void setHessianOrderAO(HessianOrderAO hessianOrderAO) {
		this.hessianOrderAO = hessianOrderAO;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
