package com.yuwang.pinju.web.module.order.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.order.ao.OrderCreationAO;
import com.yuwang.pinju.domain.order.query.CartSellCreation;
import com.yuwang.pinju.domain.order.query.ImmediatelySellCreation;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.module.BaseAction;


/**
 * 
 * @author 杜成
 * @date 2011-6-8
 * @version 1.0
 */
public class SettlementAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6657404809616724610L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private List<Map<String, Object>> itemList;
	/**
	 * 传出购物车结算
	 */
	private CartSellCreation sellCreation;
	/**
	 * 传出立即购买结算
	 */
	private ImmediatelySellCreation immediatelySellCreation;

	/**
	 * 当前订单中的商品总价,在生成订单时使用
	 */
	private Long sumPrice;

	private OrderCreationAO orderCreationAO;
	
	/**
	 * 购物车结算参数效验错误
	 */
	private final static String PARAMETERRROR = "paramet-error";

	/**
	 * 订单生成参数
	 */
	private OrderCreationVO orderCreation;
	/**
	 * 错误消息提示
	 */
	private String errorMessage;

	/**
	 * 验证码
	 */
	private String sid;
	
	
	private long errorItemId = 0;
	
	/**
	 * 
	 * Created on 2011-8-17
	 * <p>Discription: 购物车结算页生成</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String settlement() {
		try {
	
			//无参数返回购物车
			if(orderCreation==null)
				return PARAMETERRROR;
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			orderCreation.setBuyerMemberId(loginInfo.getMember().getMemberId());
			//获取购物车提交结算数据
			Result result = orderCreationAO.creationSettling(orderCreation);
			//参数为空或异常
			if(!result.isSuccess()){
				//结算参数效验错误
				this.setErrorMessage(ResultCodeMsg.getResultMessage(result.getResultCode(), new String()));
				return ERROR;
			}
			CartSellCreation sellCreation =(CartSellCreation)result.getModels().get("sellCreation");
			//封装返回数据
			this.setSellCreation(sellCreation);
		} catch (Exception e) {
			log.error("Event=[SettlementAction#settlement]", e);
			this.setErrorMessage(ResultCodeMsg.getResultMessage(TradeResultCode.ORDER_CHECK_EXCEPTION,new Object()));
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * Created on 2011-7-26
	 * <p>Discription:立即结算
	 * 立即购买传入参数方式与购物车有区别在这里自行封装OrderCreationVO对象
	 * </p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String lastTimeBuySell() {
		try {
			//页面取得验证码
			sid = PinjuCookieManager.getHashSessionId(SettlementAction.class);
			//无登陆
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			if (!loginInfo.isLogin()) {
				ActionContext.getContext().put("returnUrl","orderBuyer/lastTimeBuySell.htm?"+this.getLastTimeBuySellParmString());
				return ERROR;
			}
			OrderCreationVO orderCreation = this.getOrderCreationVO(loginInfo.getMemberId());
			Result result = orderCreationAO.creationLastTimeSettling(orderCreation);
			//参数为空或异常
			if(!result.isSuccess()){
				//结算参数效验错误
				if(orderCreation.getItemId()!=null && orderCreation.getItemId().length > 0)
					this.errorItemId = orderCreation.getItemId()[0];
				this.setErrorMessage(ResultCodeMsg.getResultMessage(result.getResultCode(), new String()));
				return ERROR;
			}
			//限时折扣返回代码
			final String GroupOrDicountRateCode = result.getSubResultCode();
			if(GroupOrDicountRateCode != null)
					errorMessage = ResultCodeMsg.getResultMessage(GroupOrDicountRateCode, new Object());
			//放入结算需要对象
			ImmediatelySellCreation immediatelySellCreation = (ImmediatelySellCreation)result.getModels().get("immediatelySellCreation");
			this.setImmediatelySellCreation(immediatelySellCreation);
			request.setAttribute("SelectedPrice", orderCreation.getPrice()[0]);
		} catch (Exception e) {
			log.error("Event=[SettlementAction#settlement] ", e);
			this.setErrorMessage(ResultCodeMsg.getResultMessage(TradeResultCode.ORDER_CHECK_EXCEPTION,new Object()));
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>Discription: 封装立即购买传入参数</p>
	 * @param loginMemberId 登录会员编号
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private OrderCreationVO getOrderCreationVO(Long loginMemberId){
		OrderCreationVO orderCreation = new OrderCreationVO();
		//购买类型
		orderCreation.setBussinessType(getIntegers("bussinessType"));
		//商品ID
		orderCreation.setItemId(getLongs("itemId"));
		//商品SKU
		orderCreation.setItemSkuAttributes(getStrings("SelectedSKU"));
		//商品SKU ID
		orderCreation.setItemSkuId(getLongs("SelectedSkuId"));
		//购买数量
		orderCreation.setNum(getLongs("SelectedNum"));
		//商品价格
		String priceStr=getString("SelectedPrice");
		Long price =MoneyUtil.getDollarToCent(priceStr);
		orderCreation.setPrice(new Long[]{price});
		//分销商编号
		orderCreation.setChannelId(getStrings("channelId"));
		//登录会员编号
		orderCreation.setBuyerMemberId(loginMemberId);
		//特供码
		orderCreation.setDiscountCode(getStrings("limitBuyCode"));
		//广告码
		orderCreation.setAd(getStrings("p"));
		
		return orderCreation;
	}
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>Discription: 封装立即购买参数字符串,
	 * 	在用户无登陆情况下使用
	 * </p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private String getLastTimeBuySellParmString(){
		StringBuffer sb = new StringBuffer();
		sb.append("bussinessType="+getString("bussinessType"));
		sb.append("&itemId="+getString("itemId"));
		sb.append("&SelectedSKU="+getString("SelectedSKU"));
		sb.append("&SelectedSkuId="+getString("SelectedSkuId"));
		sb.append("&SelectedNum="+getString("SelectedNum"));
		sb.append("&SelectedPrice="+getString("SelectedPrice"));
		sb.append("&channelId="+getString("channelId"));
		return sb.toString();
	}
	
	
	
	public List<Map<String, Object>> getItemList() {
		return itemList;
	}

	public void setItemList(List<Map<String, Object>> itemList) {
		this.itemList = itemList;
	}

	public void setOrderCreationAO(OrderCreationAO orderCreationAO) {
		this.orderCreationAO = orderCreationAO;
	}

	public Long getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Long sumPrice) {
		this.sumPrice = sumPrice;
	}

	public OrderCreationVO getOrderCreation() {
		return orderCreation;
	}

	public void setOrderCreation(OrderCreationVO orderCreation) {
		this.orderCreation = orderCreation;
	}

	public CartSellCreation getSellCreation() {
		return sellCreation;
	}

	public void setSellCreation(CartSellCreation sellCreation) {
		this.sellCreation = sellCreation;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ImmediatelySellCreation getImmediatelySellCreation() {
		return immediatelySellCreation;
	}

	public void setImmediatelySellCreation(
			ImmediatelySellCreation immediatelySellCreation) {
		this.immediatelySellCreation = immediatelySellCreation;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public long getErrorItemId() {
		return errorItemId;
	}

	public void setErrorItemId(long errorItemId) {
		this.errorItemId = errorItemId;
	}
	
}
