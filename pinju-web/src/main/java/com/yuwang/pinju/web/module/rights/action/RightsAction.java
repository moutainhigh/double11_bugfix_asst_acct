package com.yuwang.pinju.web.module.rights.action;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.coupon.ao.OrderCouponAO;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.rights.ao.RightsAO;
import com.yuwang.pinju.core.rights.manager.RightsManager;
import com.yuwang.pinju.core.storage.ao.FileUploadAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsQuery;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.message.RightsMessageName;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * @Project: pinju-web
 * @Description: 消保维权Action
 * @author 石兴 shixing@zba.com
 * @date 2011-7-1 上午09:56:14
 * @update 2011-7-1 上午09:56:14
 * @version V1.0
 */
public class RightsAction extends BaseAction {
	
	Log log = LogFactory.getLog(this.getClass().getName());
	
	private static final long serialVersionUID = 1L;

	private RightsAO rightsAO;
	
	private OrderCouponAO orderCouponAO;
	
	private RightsManager rightsManager;
	
	private OrderQueryManager orderQueryManager;
	
	private RefundManager refundManager;
	
	private OrderBusinessManager orderBusinessManager;

	private RightsQuery query;
	
	private FileUploadAO fileUploadAO;
	
	private File[] voucherPic;
	
    private String[] voucherPicFileName;
    
    private String[] voucherPicContentType;
    
    private String buyerReason;
    
    private String buyerApplyPrice;

	private Integer buyerRequire;

	private Integer voucherType; 
	
	private String voucherTypeHidden;
	
	private String errorMsg;
	
	private Long orderItemId;
	
	/**
	 * 买家账户名
	 */
	private String buyerAccountName;
	
	/**
	 * 买家卡号（即买家银行卡号）
	 */
	private String buyerAccount;
	
	/**
	 * 开户行（即买家卡号开户行）
	 */
	private String buyOpenAccount;	

	/**总订单优惠费**/
	private String youhuifei;
	/**子订单优惠费**/
	private String itemYouhuifei;
	
	/**
	 * 运费
	 */
	private String freight;
	
	/**
	 * 实付款 = 商品总价 + 运费
	 */
	private String payment;
	
	/**
	 * 小计 = 购买商品数量 * 优惠后的商品单价
	 */
	private String itemAmount;
	
	/**
	 * 商品总价：订单总额 - 运费
	 */
	private String orderItemAmount;
	private Long rightsId;
	private String rightsIdString;
	private String orderId;
	private Date beginDate;
	private Date endDate;
	private Integer status=-1;
	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[获取买家维权列表]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	public String getRightsList(){
		query = new RightsQuery();
		query.setItemsPerPage(20);
		query.setPage(getInteger("currentPage"));
		CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
		if (!loginInfo.isLogin()) {
			setErrorMsg(Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
			return SUCCESS;
		}
		query.setBuyerId(loginInfo.getMemberId());
		try {
			Result result = rightsAO.getRightsDOs(query);
			if (result.isSuccess()){
				List<RightsDO> rightsDOs = (List<RightsDO>)result.getModel("rightsDO");
				if (rightsDOs != null) {
					if (rightsDOs.size()>0) {						
						RightsDO[] list= rightsDOs.toArray(new RightsDO[rightsDOs.size()]);
						Arrays.sort(list, new Comparator() {
							public int compare(Object left, Object right) {
								RightsDO a = (RightsDO) left;
								RightsDO b = (RightsDO) right;
								long val1 = a.getId();
								long val2 = b.getId();
								return val1 > val2 ? -1 : (val1 == val2 ? 0 : 1);
							}
						});
						rightsDOs = Arrays.asList(list);
					}
					query.setRightsDOs(rightsDOs);
				}
			}
			query.setAction("/rights/rightsList.htm");
		} catch (Exception e) {
			setErrorMsg(Message.getMessage(RightsMessageName.RIGHTS_RECORD_GET_EXIST));
			log.error("Event=[RightsAction#getRightsList] 获取买家维权记录失败",e);
		}
		return SUCCESS; 
	}
	
	/**
	 * Created on 2011-7-6 
	 * <p>Discription:[获取卖家维权列表]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	public String getSellerRightsList(){
		query = new RightsQuery();
		query.setItemsPerPage(20);
		query.setPage(getInteger("currentPage"));
		CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
		if (!loginInfo.isLogin()) {
			setErrorMsg(Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
			return SUCCESS;
		}
		query.setSellerId(loginInfo.getMemberId());
		if (isNotEmpty(orderId)) {
			try {
				query.setOrderId(Long.valueOf(orderId));			
			} catch (Exception e) {
				query.setItems(0);
				errorMessage="订单编号类型错误，请重新输入!";
				return ERROR;
			}
		}
		if (isNotEmpty(rightsIdString)) {
			try {
				query.setRightsId(Long.valueOf(rightsIdString));				
			} catch (Exception e) {
				query.setItems(0);
				errorMessage="维权编号类型错误，请重新输入!";
				return ERROR;
			}
		}
		query.setBeginDate(beginDate);
		query.setEndDate(endDate);
		if (status!=-1) {
			query.setStatus(status);			
		}
		try {
			Result result = rightsAO.getRightsDOs(query);
			if (result.isSuccess()){
				List<RightsDO> rightsDOs = (List<RightsDO>)result.getModel("rightsDO");
				if (rightsDOs != null) {
					if (rightsDOs.size()>0) {						
						RightsDO[] list= rightsDOs.toArray(new RightsDO[rightsDOs.size()]);
						Arrays.sort(list, new Comparator() {
							public int compare(Object left, Object right) {
								RightsDO a = (RightsDO) left;
								RightsDO b = (RightsDO) right;
								long val1 = a.getId();
								long val2 = b.getId();
								return val1 > val2 ? -1 : (val1 == val2 ? 0 : 1);
							}
						});
						rightsDOs = Arrays.asList(list);
					}
					query.setRightsDOs(rightsDOs);
				}
			}
			query.setAction("/rights/sellerRightsList.htm");
		} catch (Exception e) {
			query.setItems(0);
			errorMessage="获取卖家维权记录失败!";
			setErrorMsg(Message.getMessage(RightsMessageName.RIGHTS_RECORD_GET_EXIST));
			log.error("Event=[RightsAction#getSellerRightsList] 获取卖家维权记录失败",e);
		}
		return SUCCESS; 
	}
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[维权申请校验]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String checkRightsRecode(){
		JSONObject jsonObject = new JSONObject();
		try {
			Long orderItemId = getLong("orderItemId");
			RightsDO rightsDO = new RightsDO();
			rightsDO.setSubOrderId(orderItemId);
			rightsDO = rightsManager.getApplyRightsDO(rightsDO);
			ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
			if (rightsDO != null) { // 即该订单已发起过维权
				jsonObject.put("already",Message.getMessage(RightsMessageName.OPERATE_RIGHTS_ALREADY));
				//jsonObject.put("rightsId",rightsDO.getId());
				return null;
			}
			OrderItemDO orderItemDO = orderQueryManager.getOrderItemDOById(orderItemId);
			if(orderItemDO == null || orderItemDO.getOrderItemState() != 5){
				jsonObject.put(ERROR, "该商品不存在或交易未成功，无法申请维权");
				return null;
			}
			//检查是否超过维权时间限制：如果该订单交易成功超过15天
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date());   //当前时间
			Calendar c2 = Calendar.getInstance();
			c2.setTime(orderItemDO.getStateModifyTime()); //交易成功时间
			c2.add(Calendar.DAY_OF_MONTH, RightsConstant.OUT_OF_DATE);
			if (c2.before(c1)) {
				jsonObject.put("outofdate", Message.getMessage(RightsMessageName.TRADE_RIGHTS_OUTOFDATE));
				return null;
			}		
			RefundDO refundDO = refundManager.loadRefundByOrderItem(orderItemId);
			if(refundDO == null){//该子订单未进行过退款申请
				jsonObject.put(SUCCESS, SUCCESS);
				return null;
			}
			// 如果该子订单是需要退货(即refundDO.getNeedSalesReturn()==1)，
			//并且退款成功(refundDO.getRefundState()==5)，则不能发起维权；反之，则可以
			if(refundDO.getNeedSalesReturn() == 1 && refundDO.getRefundState() == 5){
				jsonObject.put(ERROR,"该商品您已退货并退款成功，无法申请维权");
				return null;
			}
			jsonObject.put(SUCCESS, SUCCESS);
		} catch (Exception e) {
			jsonObject.put(ERROR, "网络繁忙，请稍后再试");
			log.error("Event=[RightsAction#showApplyRights] 查询子订单详情失败",e);
		} finally{
			try {
				ServletActionContext.getResponse().getWriter().println(jsonObject);
				ServletActionContext.getResponse().getWriter().flush();
				ServletActionContext.getResponse().getWriter().close();
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[显示维权申请表单]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String showApplyRights(){
		try {
			Long orderItemId = getLong("orderItemId");
			query = new RightsQuery();
			OrderItemDO orderItemDO = orderQueryManager.getOrderItemDOById(orderItemId);
			if(orderItemDO == null){
				log.debug("编号为["+orderItemId+"]的子订单信息不存在！");
				errorMsg = "查询子订单信息失败！";
				return SUCCESS;
			}
			query.setOrderItemDO(orderItemDO);					
			OrderDO orderDO = orderQueryManager.getOrderDOById(orderItemDO.getOrderId());
			if(orderDO == null){
				log.debug("编号为["+orderItemDO.getOrderId()+"]的订单信息不存在！");
				errorMsg = "查询订单信息失败！";
				return SUCCESS;
			}
			query.setOrderDO(orderDO); 
			//优惠费 = 商品原来的价格 - 优惠后的价格
			youhuifei = orderCouponAO.getCouponMoneyByYuan(orderDO.getOrderId());//总订单优惠
			itemYouhuifei = orderItemDO.getTotalAmountByYuan();//子订单优惠
			//小计 = 购买商品的数量 * 优惠后的价格
			Money itemAmoutMoney = new Money(orderItemDO.getOrderItemPrice()).multiply(orderItemDO.getBuyNum());
			itemAmount = itemAmoutMoney.getAmount().toString();
			OrderLogisticsDO orderLogisticsDO = orderBusinessManager.queryOrderLogisticsByOrderId(orderItemDO.getOrderId());
			if(orderLogisticsDO!=null){
				//运费
				Money transPayMoney = new Money(orderLogisticsDO.getPostPrice());
				freight = transPayMoney.getAmount().toString();
				//商品总价
				Money orderItemAmountMoney = new Money(orderDO.getPriceAmount()).subtract(transPayMoney);
				orderItemAmount = orderItemAmountMoney.getAmount().toString();
				//实付款 = 商品总价 + 运费
				Money shifukuanMoney = new Money(freight).add(orderItemAmountMoney);
				payment = shifukuanMoney.getAmount().toString();
			}
		} catch (ManagerException e) {
			errorMsg = "网络繁忙，请稍后再试";
			log.error("Event=[RightsAction#showApplyRights] 查询子订单详情失败",e);
		} catch (Exception e) {
			errorMsg = Message.getMessage(RightsMessageName.RIGHTS_SHOW_APPLY_FAIL);
			log.error("Event=[RightsAction#showApplyRights] 查询子订单详情失败",e);
		}
		return SUCCESS;
	}	
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[发起维权申请]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String applyRights(){
		try {
			request.setAttribute("orderItemId",orderItemId);
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			if(!loginInfo.isLogin()) {
				showApplyRights();
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
				return ERROR;
			}
			OrderItemDO orderItemDO = orderQueryManager.getOrderItemDOById(orderItemId);
			if(orderItemDO == null || orderItemDO.getOrderItemState() != 5){
				showApplyRights();
				errorMsg = "该维权子订单不存在或交易未成功，无法申请维权";
				return ERROR;
			}	
			RefundDO refundDO = refundManager.loadRefundByOrderItem(orderItemId);
			if(refundDO == null){
				log.debug("编号为："+orderItemId+"的退款信息不存在");
			}else {
				// 如果该子订单是需要退货(即refundDO.getNeedSalesReturn()==1)，
				//并且退款成功(refundDO.getRefundState()==5)，则不能发起维权；反之，则可以
				if(refundDO.getNeedSalesReturn() == 1 && refundDO.getRefundState() == 5){
					showApplyRights();
					errorMsg = "该商品您已退款退货成功，无法申请维权";
					return ERROR;
				}
			}
			voucherType = Integer.valueOf(voucherTypeHidden);
			if(voucherType == 0){//维权原因是否是七天无理由退货， 检查交易成功是否超过7天
				Calendar c1 = Calendar.getInstance();
				c1.setTime(new Date());   //当前时间
				Calendar c2 = Calendar.getInstance();
				c2.setTime(orderItemDO.getStateModifyTime()); //交易成功时间
				c2.add(Calendar.DAY_OF_MONTH, RightsConstant.OUT_SEVEN_DATE);
				if (c2.before(c1)) {
					showApplyRights();
					errorMsg = "您已过了七天无理由退货期限，无法申请无理由退货。";
					return ERROR;
				}
			}
			OrderDO orderDO = orderQueryManager.getOrderDOById(orderItemDO.getOrderId());
			if(orderDO == null){
				showApplyRights();
				log.debug("订单不存在");
				errorMsg = "查询订单失败！";
				return ERROR;
			}
			query = new RightsQuery();
			if (!orderDO.getBuyerId().equals(Long.valueOf(loginInfo.getMemberId()))) {
				showApplyRights();
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_IDENTITY);
				return ERROR;
			}
			RightsDO rightsDO = new RightsDO();
//			OrderLogisticsDO orderLogisticsDO = orderBusinessManager.queryOrderLogisticsByOrderId(orderDO.getOrderId());
//			if(orderLogisticsDO != null){
//				rightsDO.setLogisticsId(orderLogisticsDO.getOrderLogisticsId());
//			}
			rightsDO.setOrderId(orderItemDO.getOrderId());
			rightsDO.setSubOrderId(orderItemDO.getOrderItemId());
			rightsDO.setBuyerId(loginInfo.getMemberId());
			rightsDO.setBuyerNick(loginInfo.getNickname());
			rightsDO.setIsReturnGoods(buyerRequire);
			rightsDO.setPrice(new Money(orderItemDO.getTotalAmountByYuan()).getCent());//子订单的交易金额
			rightsDO.setVoucherType(voucherType);
			rightsDO.setBuyerRequire(buyerRequire);  //维权要求  0-要求退款  1-要求退货退款
			if(StringUtil.isNotEmpty(filterMsg(buyerReason))){
				if(buyerReason.length() > 300){
					showApplyRights();
					log.debug("请勿超出150个汉字300字符长度，请尽量描述简洁，以方便卖家查看。");
					errorMsg = "请勿超出150个汉字300字符长度，请尽量描述简洁，以方便卖家查看。";
					return ERROR;
				}
			}
			rightsDO.setBuyerReason(buyerReason);
			String tradePrice = orderItemDO.getTotalAmountByYuan();
			if(StringUtil.isNotEmpty(buyerApplyPrice)&&StringUtil.isNotEmpty(tradePrice)){
				Long buyerApplyPriceLong = new Money(buyerApplyPrice).getCent();// 单位：元
				Money money = new Money(tradePrice);
				if(voucherType == 2){//如果维权类型是2-假一赔三时，最多 （商品实际付款的金额*3 + 100）
					money = money.multiply(3).add(new Money(100.00));
					if (buyerApplyPriceLong > (money.getCent())) {
						log.debug("买家申请退款金额不能大于（商品价格*3+100）");
						showApplyRights();
						errorMsg = "买家申请退款金额超限";
						return ERROR;
					}
				}else if(voucherType == 1){//有质量问题求退款上限为该商品实际付款的金额加100元可能的运费
					money = money.add(new Money(100.00));
					if (buyerApplyPriceLong > (money.getCent())) {
						log.debug("买家申请退款金额不能大于（该商品实际付款的金额+100）");
						showApplyRights();
						errorMsg = "买家申请退款金额超限";
						return ERROR;
					}
				}else {//voucherType=0七天无理由退货要求退款上限为买家为该商品实际付款的金额
					if (buyerApplyPriceLong > (money.getCent())) {
						log.debug("买家申请退款金额不能大于（该商品实际付款的金额）");
						showApplyRights();
						errorMsg = "买家申请退款金额超限";
						return ERROR;
					}
				}			
			}			
			rightsDO.setBuyerApplyPrice(new Money(buyerApplyPrice).getCent());  //单位：分
			rightsDO.setState(RightsConstant.WAIT_SELLER_HANDLE); 
			rightsDO.setSellerId(orderDO.getSellerId());
			rightsDO.setSellerNick(orderDO.getSellerNick());
			rightsDO.setBuyerBankAccountName(buyerAccountName);
			rightsDO.setBuyerBankAccountNo(buyerAccount);
			rightsDO.setBuyerOpenBankName(buyOpenAccount);
			rightsDO.setStateUpdateTime(new Date());
			if(voucherPic != null && voucherPic.length > 0) {
				for (File file : voucherPic) {
					if (file.length()/PinjuConstant.FILE_SIZE_K > 500) {
						showApplyRights();
						errorMsg = Message.getMessage(MessageName.FILE_SIZE_TO_LARGE);
						return ERROR;
					}
				}
				for (File file : voucherPic) {
					if (!FileSecurityUtils.isImageValid(file)) {
						showApplyRights();
						errorMsg = Message.getMessage(MessageName.FILE_TYPE_INVALID);
						return ERROR;
					}
				}
			    Result result = fileUploadAO.saveUserPics(voucherPic, voucherPicFileName, loginInfo.getMemberId(), loginInfo.getMember().getNickname());
			    if (result.isSuccess()) {
			    	String[] retFileName = (String[])result.getModel("fileNames");
			    	if (retFileName.length==1) {
			    		rightsDO.setVoucherPic1(retFileName[0]);
					}else if(retFileName.length==2){
						rightsDO.setVoucherPic1(retFileName[0]);
						rightsDO.setVoucherPic2(retFileName[1]);
					}else if(retFileName.length==3){
						rightsDO.setVoucherPic1(retFileName[0]);
						rightsDO.setVoucherPic2(retFileName[1]);
						rightsDO.setVoucherPic3(retFileName[2]);
					}
				}
			}
			Result result = rightsAO.insertRightsRecord(rightsDO);
			if (!result.isSuccess()){
				showApplyRights();
				errorMsg = Message.getMessage(result.getResultCode());
				return ERROR;					
			}else {
				errorMsg = Message.getMessage(result.getResultCode());
				query.setRightsDO(rightsDO);
				rightsId = rightsDO.getId();
				request.setAttribute("rightsId",rightsId);
			}
		} catch (ManagerException e) {
			showApplyRights();
			errorMsg = "网络繁忙，请稍后再试";
			log.error("Event=[RightsAction#applyRights] 发起维权失败！" + e);
			return ERROR;
		}catch (Exception e) {
			showApplyRights();
			errorMsg = "网络繁忙，请稍后再试";
			log.error("Event=[RightsAction#applyRights] 发起维权失败！" + e);
			return ERROR;
		}
		return SUCCESS; 
	}
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[维权记录动作处理]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	public String rightsRecordProcess(){
		query = new RightsQuery();
		query.setPage(getInteger("currentPage"));
		CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
		if(!loginInfo.isLogin()) {
			errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
			return SUCCESS;
		}
		query.setBuyerId(loginInfo.getMemberId());
		try {
			Result result = rightsAO.getRightsDOs(query);
			if (result.isSuccess()){
				List<RightsDO> rightsDOs = (List<RightsDO>)result.getModel("rightsDO");
				if (rightsDOs != null) {
					query.setRightsDOs(rightsDOs);
				}
			}
			query.setAction("/rights/rightsList.htm");
		} catch (Exception e) {
			errorMsg = "网络繁忙，请稍后再试";
			log.error("Event=[RightsAction#getRightsList] 获取维权记录失败",e);
		}
		return SUCCESS; 
	}
	
	
	/**
	 * 防止JS代码执行
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-22
	 * @update:2011-8-22
	 * @param content
	 * @return
	 */
	private static String filterMsg(String content) {
		content = content.trim();
		content = content.replace("<", "&lt;");
		content = content.replace(">", "&gt;");
		return content;
	}	
	public void setRightsAO(RightsAO rightsAO) {
		this.rightsAO = rightsAO;
	}

	public void setQuery(RightsQuery query) {
		this.query = query;
	}

	public RightsQuery getQuery() {
		return query;
	}

	public File[] getVoucherPic() {
		return voucherPic;
	}

	public void setVoucherPic(File[] voucherPic) {
		this.voucherPic = voucherPic;
	}

	public String[] getVoucherPicFileName() {
		return voucherPicFileName;
	}

	public void setVoucherPicFileName(String[] voucherPicFileName) {
		this.voucherPicFileName = voucherPicFileName;
	}

	public String[] getVoucherPicContentType() {
		return voucherPicContentType;
	}

	public void setVoucherPicContentType(String[] voucherPicContentType) {
		this.voucherPicContentType = voucherPicContentType;
	}

	public RightsAO getRightsAO() {
		return rightsAO;
	}

	public void setFileUploadAO(FileUploadAO fileUploadAO) {
		this.fileUploadAO = fileUploadAO;
	}

	public FileUploadAO getFileUploadAO() {
		return fileUploadAO;
	}

	public void setBuyerReason(String buyerReason) {
		this.buyerReason = buyerReason;
	}

	public String getBuyerReason() {
		return buyerReason;
	}
    
    public Integer getBuyerRequire() {
		return buyerRequire;
	}

	public void setBuyerRequire(Integer buyerRequire) {
		this.buyerRequire = buyerRequire;
	}
	
	public Integer getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}
	
	public void setRightsManager(RightsManager rightsManager) {
		this.rightsManager = rightsManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setRefundManager(RefundManager refundManager){
		this.refundManager = refundManager;
	}

	public RefundManager getRefundManager(){
		return refundManager;
	}

	public void setBuyerAccountName(String buyerAccountName){
		this.buyerAccountName = buyerAccountName;
	}

	public String getBuyerAccountName(){
		return buyerAccountName;
	}

	public void setBuyerAccount(String buyerAccount){
		this.buyerAccount = buyerAccount;
	}

	public String getBuyerAccount(){
		return buyerAccount;
	}

	public void setBuyOpenAccount(String buyOpenAccount){
		this.buyOpenAccount = buyOpenAccount;
	}

	public String getBuyOpenAccount(){
		return buyOpenAccount;
	}

	public void setYouhuifei(String youhuifei){
		this.youhuifei = youhuifei;
	}

	public String getYouhuifei(){
		return youhuifei;
	}

	public void setFreight(String freight){
		this.freight = freight;
	}

	public String getFreight(){
		return freight;
	}

	public void setOrderBusinessManager(OrderBusinessManager orderBusinessManager){
		this.orderBusinessManager = orderBusinessManager;
	}

	public void setItemAmount(String itemAmount){
		this.itemAmount = itemAmount;
	}

	public String getItemAmount(){
		return itemAmount;
	}

	
	public void setPayment(String payment){
		this.payment = payment;
	}

	public String getPayment(){
		return payment;
	}

	public void setOrderItemAmount(String orderItemAmount){
		this.orderItemAmount = orderItemAmount;
	}

	public String getOrderItemAmount(){
		return orderItemAmount;
	}
    
    public String getBuyerApplyPrice(){
		return buyerApplyPrice;
	}

	public void setBuyerApplyPrice(String buyerApplyPrice){
		this.buyerApplyPrice = buyerApplyPrice;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setVoucherTypeHidden(String voucherTypeHidden){
		this.voucherTypeHidden = voucherTypeHidden;
	}

	public String getVoucherTypeHidden(){
		return voucherTypeHidden;
	}

	public String getRightsIdString() {
		return rightsIdString;
	}

	public void setRightsIdString(String rightsIdString) {
		this.rightsIdString = rightsIdString;
	}

	public void setRightsId(Long rightsId){
		this.rightsId = rightsId;
	}

	public Long getRightsId(){
		return rightsId;
	}

	public void setOrderCouponAO(OrderCouponAO orderCouponAO) {
		this.orderCouponAO = orderCouponAO;
	}

	public void setItemYouhuifei(String itemYouhuifei) {
		this.itemYouhuifei = itemYouhuifei;
	}

	public String getItemYouhuifei() {
		return itemYouhuifei;
	}

}
