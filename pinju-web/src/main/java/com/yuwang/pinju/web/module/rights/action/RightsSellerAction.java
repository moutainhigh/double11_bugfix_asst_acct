package com.yuwang.pinju.web.module.rights.action;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.coupon.ao.OrderCouponAO;
import com.yuwang.pinju.core.order.service.OpenQueryOrderService;
import com.yuwang.pinju.core.rights.ao.RightsAO;
import com.yuwang.pinju.core.rights.ao.RightsSellerAO;
import com.yuwang.pinju.core.storage.ao.FileUploadAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsSellerQuery;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.message.RightsMessageName;
import com.yuwang.pinju.web.module.BaseAction;

public class RightsSellerAction extends BaseAction{
	
	private static final long serialVersionUID = 2909462736771104190L;
	
	private String outOfDate = "true";
	private RightsAO rightsAO;
	private RightsSellerAO rightsSellerAO;
	private RightsSellerQuery query;
	private String errorMsg;
	private String orderErrorMsg;
	private String msgErrorMsg;
	private String content; // 留言内容
	private File[] voucherPic;
	private String[] voucherPicFileName;
	private String[] voucherPicContentType;
	private FileUploadAO fileUploadAO;
	private Long rightsId;
	private String errCaiWuMsg;
	private String updateMsg;
	private String errorMessage;//错误页面提示信息
	private Long rightsTime=0L;//倒计时用
	private String refuseReason;
	private String sendMsg;
	private Long nowTime; //此刻时间
	private String lotcFlag="false"; //判断是否有物流信息
	private String isSeller="true";
	private Integer status;
	private Integer isGoods;
	private String totalPrice;
	
	/**
	 * 主订单优惠
	 */
	private String couponMoneyByYuan;
	
	private OrderCouponAO orderCouponAO;
	
	public void setOrderCouponAO(OrderCouponAO orderCouponAO)
    {
        this.orderCouponAO = orderCouponAO;
    }
    public String getCouponMoneyByYuan()
    {
        return couponMoneyByYuan;
    }
    public void setCouponMoneyByYuan(String couponMoneyByYuan)
    {
        this.couponMoneyByYuan = couponMoneyByYuan;
    }
    public Integer getIsGoods() {
		return isGoods;
	}
	public void setIsGoods(Integer isGoods) {
		this.isGoods = isGoods;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getIsSeller() {
		return isSeller;
	}
	public void setIsSeller(String isSeller) {
		this.isSeller = isSeller;
	}
	public String getLotcFlag() {
		return lotcFlag;
	}
	public void setLotcFlag(String lotcFlag) {
		this.lotcFlag = lotcFlag;
	}
	public Long getNowTime() {
		return nowTime;
	}
	public void setNowTime(Long nowTime) {
		this.nowTime = nowTime;
	}
	public String getSendMsg() {
		return sendMsg;
	}
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}

	private OpenQueryOrderService openQueryOrderService;
	
	public void setOpenQueryOrderService(OpenQueryOrderService openQueryOrderService) {
		this.openQueryOrderService = openQueryOrderService;
	}
	public  RightsSellerAction() {
		super();
		query = new RightsSellerQuery();
	}
	@SuppressWarnings("unchecked")
	public String getSellerRightsDetail() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
 			if (!loginInfo.isLogin()) {
				errorMessage = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
  			query.setBuyerId(loginInfo.getMemberId());
			//查询rightsDO
			Result result = rightsSellerAO.getRightsDOById(rightsId);
			if (!result.isSuccess()) {
				errorMessage = "系统繁忙，请稍候再试！";
				return ERROR;
			}
			RightsDO rightsDO = (RightsDO) result.getModel("rightsDO");
			if (rightsDO==null) {
				errorMessage ="该维权不存在！";
				return ERROR;
			}
						
 			query.setRightsDO(rightsDO);
			if (loginInfo.getMemberId() != rightsDO.getSellerId()) {
				errorMessage = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_IDENTITY));
				return ERROR;
			}
			
			// 查询右侧订单信息 OrderDO OrderItemDO,VouchPayDO
			Result orderResult = rightsSellerAO.getOrderDOAndOrderItemDOAndVouchPayDOById(rightsDO.getOrderId(), rightsDO.getSubOrderId()); 
			if (!orderResult.isSuccess()) {
				errorMessage = "系统繁忙，请稍候再试！";
				return ERROR;
			}
			OrderDO orderDO = (OrderDO) orderResult.getModel("orderDO");
			if (orderDO==null) {
				errorMessage = "查询订单失败！";
				return ERROR;
			}
			VouchPayDO vouchPayDO = (VouchPayDO) orderResult.getModel("vouchPayDO");
			if (vouchPayDO==null) {
				errorMessage = "查询实付款失败！";
				return ERROR;
			}
			OrderItemDO orderItemDO = (OrderItemDO) orderResult.getModel("orderItemDO");
			if (orderItemDO==null) {
				errorMessage ="该维权子订单不存在！";
				return ERROR;
			}
			//计算子订单优惠金额
			String discPrice = openQueryOrderService.queryOrderItemDiscountPrice(orderItemDO);
			query.setSubOrderDiscPrice(discPrice);
			query.setOrderItemDO(orderItemDO);
			
			//主订单优惠信息
			couponMoneyByYuan = orderCouponAO.getCouponMoneyByYuan(orderDO.getOrderId());
			
			
			//查询订单运费
			Result ordLgtisResult= rightsSellerAO.queryOrderLogisticsByOrderId(orderItemDO.getOrderId());
			if (!ordLgtisResult.isSuccess()) {
				errorMessage = "系统繁忙，请稍候再试！";
				return ERROR;
			}
			query.setOrderDO(orderDO);
			query.setOrderTotalPrice(orderDO.getPriceAmountByYuan());
			if (vouchPayDO.getRealPayMentamount()==null ) {
				query.setRealPayMoney(MoneyUtil.getCentToDollar(0L));
			}else {
				query.setRealPayMoney(MoneyUtil.getCentToDollar(vouchPayDO.getRealPayMentamount()));				
			}
			
			OrderLogisticsDO orderLogisticsDO = (OrderLogisticsDO) ordLgtisResult.getModel("orderLogisticsDO");
			if (orderLogisticsDO!=null) {
				query.setOrderPostPrice(orderLogisticsDO.getPostPriceByYuan());
				
				query.setItemTotalPrice(MoneyUtil.getCentToDollar(orderDO.getPriceAmount()-orderLogisticsDO.getPostPrice()));
			}else {				
				errorMessage="查询订单信息失败！";
				return ERROR;
			}
			
			//订单总价
            Long orderPriceAmount = orderDO.getPriceAmount() != null ? orderDO.getPriceAmount() : 0L;
            Long postPrice = orderLogisticsDO.getPostPrice() != null ? orderLogisticsDO.getPostPrice() : 0L;
            totalPrice = MoneyUtil.getCentToDollar(orderPriceAmount - postPrice);
			
			//查询留言信息列表
			query.setItemsPerPage(20); // 可以不设，每页条数，默认10条
			query.setPage(getInteger("currentPage"));
			query.setVoucherId(rightsId);
			Result msgResult = rightsAO.getRightsMsgDOs(query);
			if (msgResult.isSuccess()) {
				List<RightsMessageDO> rightsMessageDOs = (List<RightsMessageDO>) msgResult.getModel("rightsMsgDOs");
				if (rightsMessageDOs!=null) {
					query.setRightsMessageDOs(rightsMessageDOs);
				}
			}else {
				msgErrorMsg = "查询留言信息失败，请稍候再试";				
			}
			//当维权状态为卖家处理时，检查时间是否超时,超时，更新状态为：维权成功或者等待买家退货
			if (rightsDO.getState()==RightsConstant.WAIT_SELLER_HANDLE) {
				Long gmtCreate = rightsDO.getStateUpdateTime().getTime() + 5L * 24 * 60 * 60*1000;
				nowTime = (new Date()).getTime();
				Long gmtTime = gmtCreate - nowTime;
				if (gmtTime > 0){
					outOfDate = "false";
					rightsTime = gmtCreate - new Date().getTime();;
				}else{//超时
					outOfDate = "true";
					//检查是否需要退货
					if (rightsDO.getBuyerRequire()==RightsConstant.REQUIRE_GOODS) {
						//需要退货时，状态更改为等待买家发货
						rightsDO.setState(RightsConstant.SELLER_AGREE);
						result = rightsAO.updateRightsRecord(rightsDO);
						if (!result.isSuccess()) {
							log.error("Event=[RightsDetailAction#getSellerRightsDetail] 更新维权状态为等待买家发货失败");
						}
					}else if (rightsDO.getBuyerRequire()==RightsConstant.REQUIRE_MONEY) {
						// 要求退款 1,更新状态为维权成功；2，生成一条财务处理工单；
						rightsDO.setState(RightsConstant.SELLER_AGREE);
						Result res = rightsSellerAO.updateRightsRecordAndAddRightsWorkOrder(rightsDO);
						if (!res.isSuccess()) {
							log.error("Event=[RightsDetailAction#getSellerRightsDetail] 生成财务处理工单失败");
							errCaiWuMsg = "生成财务处理工单失败";
						}
					}
				}
			}
			//等待卖家确认收获 ,1，查詢物流信息，2，超过时间，自动进入等待客服处理
			if (rightsDO.getState() == RightsConstant.WAIT_SELLER_RECEIVED
					|| rightsDO.getState() == RightsConstant.WAIT_CUSTOMER_HANDLE
					|| (rightsDO.getState() == RightsConstant.RIGHTS_SUCCESS_SELLER && rightsDO
							.getBuyerRequire() == RightsConstant.REQUIRE_GOODS)
					|| (rightsDO.getState() == RightsConstant.RIGHTS_SUCCESS_CUSTOMER && rightsDO
							.getBuyerRequire() == RightsConstant.REQUIRE_GOODS)
					|| (rightsDO.getState() == RightsConstant.RIGHTS_CLOSE_CUSTOMER && rightsDO
							.getBuyerRequire() == RightsConstant.REQUIRE_GOODS)
					|| rightsDO.getState()== RightsConstant.RIGHTS_CONFIRM_GOODS_ALREADY
					|| rightsDO.getState() == RightsConstant.RIGHTS_CUSTOMER_AGREE
							&& rightsDO.getBuyerRequire() == RightsConstant.REQUIRE_GOODS
					|| rightsDO.getState() == RightsConstant.WAIT_SELLER_RECEIVED_BY_CUSTOMER) {
				//查询退货物流信息		
				Result rightsLogtisResult  = rightsSellerAO.getRightsLogisticsDO(rightsId, rightsDO.getBuyerId());
				if (rightsLogtisResult.isSuccess()) {
					RightsLogisticsDO rightsLogisticsDO = (RightsLogisticsDO) rightsLogtisResult.getModel("rightsLogisticsDO");
					if (rightsLogisticsDO!=null) {
						lotcFlag="true";
						query.setRightsLogisticsDO(rightsLogisticsDO);
						if (rightsDO.getState() == RightsConstant.WAIT_SELLER_RECEIVED) {
//							Calendar sendTime = Calendar.getInstance();
//							Calendar nowTime = Calendar.getInstance();
//							nowTime.setTime(new Date());
//							sendTime.setTime(rightsDO.getStateUpdateTime());
//							// 查询物流信息，平邮30天，快递10天
//							Long postType = rightsLogisticsDO.getLogisticsId(); //快递，平邮
//							if (postType==100) {
//								sendTime.add(Calendar.DAY_OF_MONTH, RightsConstant.OUT_THIRTY_DATE);
//							}
//							if (postType==200) {
//								sendTime.add(Calendar.DAY_OF_MONTH, RightsConstant.OUT_SEVEN_DATE);
//							}
							Long postType = rightsLogisticsDO.getLogisticsId(); //快递，平邮
							Long sendTime = rightsDO.getStateUpdateTime().getTime();
							if (postType==RightsLogisticsDO.PING_YOU_POST) {
								sendTime = sendTime+ 30L * 24 * 60 * 60*1000;
							}else if (postType==RightsLogisticsDO.KUAI_DI_YOU_POST || postType== RightsLogisticsDO.EMS_POST) {
								sendTime = sendTime+ 10L * 24 * 60 * 60*1000;
							}else {
								sendTime = sendTime+ 10L * 24 * 60 * 60*1000;
							}
							nowTime = (new Date()).getTime();
							Long gmtTime = sendTime - nowTime;
							
							//超过时间，自动进入等待客服处理
							if (gmtTime<=0) {
								rightsDO.setState(RightsConstant.WAIT_CUSTOMER_HANDLE);
								rightsDO.setStateUpdateTime(new Date());
								Result res = rightsAO.updateRightsRecord(rightsDO);
								if (!res.isSuccess()) {
									log.error("Event=[RightsDetailAction#getSellerRightsDetail] 卖家确认收货超时，更新维权状态失败");
								}
							}else {
								outOfDate="false";
								rightsTime = sendTime-new Date().getTime();
							}
						}
					}
				}else {
					errorMsg = "查询退货运单信息失败！";
				}
			}
			
			query.setAction("/rights/sellerRightsDetail.htm?rightsId="+rightsId);
		} catch (Exception e) {
			errorMessage = (Message.getMessage(MessageName.OPERATE_FAILED));
			log.error("Event=[RightsDetailAction#getSellerRightsDetail] 获取维权记录失败",e);
			return ERROR;
		}
		return SUCCESS;
	}	
	
	
	/**
	 * 卖家发表留言
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-22
	 * @update:2011-9-22
	 * @return
	 * @throws Exception
	 */
	public String sellerSendMessage() throws Exception {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				sendMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
				return SUCCESS;
			}
			int length = getStringLength(content);
			if (length>600) {
				sendMsg = "拒绝维权失败，字数超过限制！";
				return ERROR;
			}
			// ----------发送留言----------//
			RightsMessageDO rightsMessageDO = new RightsMessageDO();
			rightsMessageDO.setVoucherId(rightsId);// 维权id
			rightsMessageDO.setUserId(loginInfo.getMemberId());// 用户ID
			rightsMessageDO.setUserNick(loginInfo.getMember().getNickname());// 留言用户昵称
			rightsMessageDO.setContent(StringUtils.trimToEmpty(filterMsg(content)));// 记录留言内容
			// -------上传凭证-------//
			if (voucherPic != null && voucherPic.length >0 ) {
				//图片大小校验,图片大小不能超过500k
				for(File file:voucherPic){
					if((file.length()/PinjuConstant.FILE_SIZE_K)>500){
						sendMsg = Message.getMessage(MessageName.FILE_SIZE_TO_LARGE);
						return ERROR;
					}
				}
				//取后缀校验图片类型是否为jpg,gif,png
//				for (int i = 0; i < voucherPicFileName.length; i++) {
//					String fileName=voucherPicFileName[i];
//					if (fileName.lastIndexOf(".")!=-1) {
//						String suffix=fileName.substring(fileName.indexOf(".")+1);
//						if (!FileSecurityUtils.isImageSuffixValid(suffix)) {
//							sendMsg="第"+i+"个图片类型错误，上传失败，文件名："+fileName;
//							return ERROR;
//						}
//					}else {
//						sendMsg="第"+i+"个图片类型错误，上传失败，文件名："+fileName;
//						return ERROR;
//					}
//					
//				}
				//图片安全校验,如图片类型是否有效
				for(File file:voucherPic){
					if(!FileSecurityUtils.isImageValid(file)){
						sendMsg = Message.getMessage(MessageName.FILE_TYPE_INVALID);
						return ERROR;
					}
				}
				Result resultFile = fileUploadAO.saveUserPics(voucherPic, voucherPicFileName, 
						loginInfo.getMemberId(), loginInfo.getMember().getNickname());
				if (resultFile.isSuccess()) {
					String[] retFileName = (String[]) resultFile.getModel("fileNames");
					// 留言凭证
					if (retFileName.length == 1) {
						rightsMessageDO.setVoucherPic1(retFileName[0]);
					} else if (retFileName.length == 2) {
						rightsMessageDO.setVoucherPic1(retFileName[0]);
						rightsMessageDO.setVoucherPic2(retFileName[1]);
					} else if (retFileName.length == 3) {
						rightsMessageDO.setVoucherPic1(retFileName[0]);
						rightsMessageDO.setVoucherPic2(retFileName[1]);
						rightsMessageDO.setVoucherPic3(retFileName[2]);
					}
				}
			}
			Result result = rightsAO.sendMessage(rightsMessageDO);
			if (result.isSuccess()) {
				sendMsg = "发表留言成功";
			} else {
				sendMsg = Message.getMessage(result.getResultCode());
				return ERROR;
			}
		} catch (Exception e) {
			sendMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			log.error("Event=[RightsDetailAction#sellerSendMessage] 消保维权留言失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 卖家同意维权
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-26
	 * @update:2011-9-26
	 * @return
	 */
	public String sellerAgreeRights() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				updateMsg = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
			Long rightsId = getLong("rightsId");
			Result result = rightsSellerAO.getRightsDOById(rightsId);
			if (!result.isSuccess()) {
				errorMessage = "系统繁忙，请稍后再试！";
				return ERROR;
			}
			RightsDO rightsDO = (RightsDO) result.getModel("rightsDO");
			if (rightsDO==null) {
				errorMessage = "该记录不存在，请重试！";
				return ERROR;
			}
			rightsDO.setSellerProcDate(new Date());
			if (rightsDO.getState() == RightsConstant.WAIT_SELLER_HANDLE) {
				if (rightsDO.getBuyerRequire() ==RightsConstant.REQUIRE_MONEY) {
					//退款，1，维权成功；2，生成财务处理工单 （事务操作）
					rightsDO.setState(RightsConstant.SELLER_AGREE);
					Result res = rightsSellerAO.updateRightsRecordAndAddRightsWorkOrder(rightsDO);
					if (!res.isSuccess()) {
						errorMessage = "更新维权状态失败，请重试！";
						return ERROR;
					}
				}else if (rightsDO.getBuyerRequire() == RightsConstant.REQUIRE_GOODS) {
					//退货退款 ，1，等待买家发货；
					rightsDO.setState(RightsConstant.SELLER_AGREE);
					rightsDO.setStateUpdateTime(new Date());
					Result res = rightsAO.updateRightsRecord(rightsDO);
					if (!res.isSuccess()) {
						errorMessage = "更新维权状态失败，请重试！";
						return ERROR;
					}
				}
				
			}else {
				status = rightsDO.getState();
				isSeller = "true";
				isGoods = rightsDO.getBuyerRequire();
				return ERROR;
			}
		} catch (Exception e) {
			errorMessage = (Message.getMessage(MessageName.OPERATE_FAILED));
			log.error("Event=[RightsDetailAction#sellerAgreeRights] 卖家同意维权失败！");
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 维权申请被卖家拒绝
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-27
	 * @update:2011-9-27
	 * @return
	 */
	public String sellerRefuseRights() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				updateMsg = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return SUCCESS;
			}
			// 卖家填写拒绝理由字数校验
			refuseReason = StringUtils.trim(refuseReason);
			if (StringUtils.isEmpty(refuseReason)) {
				errorMessage = "拒绝维权申请失败，拒绝理由不能为空！";
				return ERROR;
			}
			int length = getStringLength(refuseReason);
			if (length>300) {
				errorMessage="拒绝维权失败，拒绝理由字数超限！";
				return ERROR;
			}
			
			// 首先查询rightsDO,插入rightsMessageDO ,更新维权状态为卖家拒绝该维权
			Result result = rightsSellerAO.sellerRefuseRights(rightsId,filterMsg(refuseReason));
			if (!result.isSuccess()) {
				isSeller = "true";
				isGoods = (Integer) result.getModel("isGoods");
				errorMessage = "更新维权状态失败，请重试！";
				status = (Integer) result.getModel("status");
				return ERROR;
			}
		} catch (Exception e) {
			updateMsg = (Message.getMessage(MessageName.OPERATE_FAILED));
			log.error("Event=[RightsDetailAction#sellerRefuseRights] 卖家拒绝维权失败！");
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 卖家确认收货  1，更新维权状态，2，生成财务处理工单
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-27
	 * @update:2011-9-27
	 * @return
	 */
	public String sellerConfirmReceipt() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMessage = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
			Result result = rightsSellerAO.getRightsDOById(rightsId);
			if (!result.isSuccess()) {
				errorMessage = "系统繁忙，请稍后再试！";
				return ERROR;
			}
			RightsDO rightsDO = (RightsDO) result.getModel("rightsDO");
			if (rightsDO==null) {
				errorMessage = "该记录不存在，请重试！";
				return ERROR;
			}
			if (rightsDO.getState()==RightsConstant.WAIT_SELLER_RECEIVED || rightsDO.getState() == RightsConstant.WAIT_SELLER_RECEIVED_BY_CUSTOMER) {
				if (rightsDO.getBuyerRequire() == RightsConstant.REQUIRE_GOODS) {
					//更新确认收货时间
					Result upLogticsResult = rightsSellerAO.updateRightsLogisticsRecord(rightsDO);
					if (upLogticsResult.isSuccess()) {
						//退款，1，维权成功；2，生成财务处理工单 （事务操作）
						if (rightsDO.getState()== RightsConstant.WAIT_SELLER_RECEIVED) {
							rightsDO.setState(RightsConstant.RIGHTS_CONFIRM_GOODS_ALREADY);						
						}else if (rightsDO.getState() == RightsConstant.WAIT_SELLER_RECEIVED_BY_CUSTOMER) {						
							rightsDO.setState(RightsConstant.RIGHTS_CUSTOMER_AGREE);						
						}
						Result res = rightsSellerAO.updateRightsRecordAndAddRightsWorkOrder(rightsDO);
						if (!res.isSuccess()) {
							errorMessage = "更新维权状态失败，请重试！";
							return ERROR;
						}					
					}else {
						errorMessage = "更物流表确认收获时间失败，请重试！";
						return ERROR;
					}
				}else {
					errorMessage = "更新维权状态失败，此维权非要求退货的维权！";
					return ERROR;
				}
				
			}else {
				isSeller = "true";
				errorMessage = "更新维权状态失败！";
				status = rightsDO.getState();
				return ERROR;
			}
		} catch (Exception e) {
			errorMessage = (Message.getMessage(MessageName.OPERATE_FAILED));
			log.error("Event=[RightsDetailAction#sellerAgreeRights] 卖家确认收获失败！");
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 卖家申请客服介入处理
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-28
	 * @update:2011-9-28
	 * @return
	 */
	public String sellerApplyCustomer(){
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMessage = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
			Result result = rightsSellerAO.getRightsDOById(rightsId);
			if (!result.isSuccess()) {
				errorMessage = "系统繁忙，请重试！";
				return ERROR;
			}
			RightsDO rightsDO = (RightsDO) result.getModel("rightsDO");
			if (rightsDO==null) {
				errorMessage = "不存在该维权记录！";
				return ERROR;
			}
			if (rightsDO.getState()==RightsConstant.WAIT_SELLER_RECEIVED || rightsDO.getState() == RightsConstant.WAIT_SELLER_RECEIVED_BY_CUSTOMER) {
				rightsDO.setState(RightsConstant.WAIT_CUSTOMER_HANDLE);
				rightsDO.setStateUpdateTime(new Date());
				Result res  = rightsAO.updateRightsRecord(rightsDO);
				if (!res.isSuccess()) {
					errorMessage = "更新失败，请重试！";
					return ERROR;
				}
				
			}else {
				isSeller = "true";
				errorMessage = "更新维权状态失败!";
				status = rightsDO.getState();
				return ERROR;
			}
			
		} catch (Exception e) {
			errorMessage = "更新失败，请重试！";
			log.error("Event=[RightsDetailAction#sellerApply] 卖家申请客服介入处理失败！");
			return ERROR;
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
		content = content.replace("<", "&lt;");
		content = content.replace(">", "&gt;");
		return content;
	}
	
	public String getOutOfDate() {
		return outOfDate;
	}
	public void setOutOfDate(String outOfDate) {
		this.outOfDate = outOfDate;
	}

	public RightsSellerQuery getQuery() {
		return query;
	}

	public void setQuery(RightsSellerQuery query) {
		this.query = query;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public void setRightsAO(RightsAO rightsAO) {
		this.rightsAO = rightsAO;
	}
	public void setRightsSellerAO(RightsSellerAO rightsSellerAO) {
		this.rightsSellerAO = rightsSellerAO;
	}
	public String getOrderErrorMsg() {
		return orderErrorMsg;
	}
	public void setOrderErrorMsg(String orderErrorMsg) {
		this.orderErrorMsg = orderErrorMsg;
	}
	public String getMsgErrorMsg() {
		return msgErrorMsg;
	}
	public void setMsgErrorMsg(String msgErrorMsg) {
		this.msgErrorMsg = msgErrorMsg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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

	public void setFileUploadAO(FileUploadAO fileUploadAO) {
		this.fileUploadAO = fileUploadAO;
	}

	public Long getRightsId() {
		return rightsId;
	}
	public void setRightsId(Long rightsId) {
		this.rightsId = rightsId;
	}

	public String getErrCaiWuMsg() {
		return errCaiWuMsg;
	}
	public void setErrCaiWuMsg(String errCaiWuMsg) {
		this.errCaiWuMsg = errCaiWuMsg;
	}
	public String getUpdateMsg() {
		return updateMsg;
	}
	public void setUpdateMsg(String updateMsg) {
		this.updateMsg = updateMsg;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Long getRightsTime() {
		return rightsTime;
	}
	public void setRightsTime(Long rightsTime) {
		this.rightsTime = rightsTime;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	
    /**
     * <p>Discription:[getStringLength,获取中英文混输字符串长度，字母按一个字节计算，中文按两字节计算]</p>
     * @return length,计算后的字符串长度
     */
	private static int getStringLength(String inputString) {
		if (isEmpty(inputString)) {
			return 0;
		}
		int length = 0;
		char[] inputchar = inputString.toCharArray();
		int inputcharlength = inputchar.length;
		for (int i = 0; i < inputcharlength; i++) {
			length = isChinese(inputchar[i]) ? length + 2 : ++length;
		}
		return length;
	}

	private static boolean isChinese(char c) {
		return c > '\377';
	}
    public String getTotalPrice()
    {
        return totalPrice;
    }
    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }
	
	
	
}
