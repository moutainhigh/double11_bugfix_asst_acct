package com.yuwang.pinju.web.module.rights.action;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.coupon.ao.OrderCouponAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsCorpManager;
import com.yuwang.pinju.core.margin.manager.MarginManager;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.rights.ao.RightsAO;
import com.yuwang.pinju.core.rights.manager.RightsManager;
import com.yuwang.pinju.core.storage.ao.FileUploadAO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsMsgQuery;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.message.RightsMessageName;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * @Project: pinju-web
 * @Description: 消保维权详情Action
 * @author 石兴 shixing@zba.com
 * @date 2011-7-1 上午09:56:14
 * @update 2011-7-1 上午09:56:14
 * @version V1.0
 */
public class RightsDetailAction extends BaseAction {

	Log log = LogFactory.getLog(this.getClass().getName());

	private static final long serialVersionUID = 1L;

	private RightsAO rightsAO;
	private FileUploadAO fileUploadAO;
	private OrderCouponAO orderCouponAO;
	private RightsMsgQuery query;
	private List<RightsMessageDO> rightsMessageDOs;
	private List<LogisticsCorpDO> logisticsCorpDOs;
	private RightsLogisticsDO rightsLogisticsDO;
	private RightsManager rightsManager;
	private MarginManager marginManager;
	private OrderBusinessManager orderBusinessManager;
	private OrderQueryManager orderQueryManager;
	private LogisticsCorpManager logisticsCorpManager; 

	private File[] voucherPic;
	private String[] voucherPicFileName;
	private String[] voucherPicContentType;

	private String buyerReason;// 买家维权详细原因
	private Long buyerApplyPrice;// 买家申请退款金额
	private String globalMoney; //页面传回的买家申请退款金额
	private String orderItemAmountStr; //传给界面的子订单总额（子订单价格*数量）
	private Integer buyerRequire; // 买家维权要求
	private Integer voucherType; // 维权类型
	private String content; // 留言内容
	private String errorMsg;
	private Integer status;
	private Integer isGoods;
	private String updateMsg; //更新信息
	/**发表留言错误信息**/
	private String sendMsg;
	private Long rightsId;
	private String buyerApplyPriceStr; //买家申请退款金额
	private String tradePrice;// 交易金额
	private String logisticsName;//物流名称
	/**维权时间+5天**/
	private Long rightsTime;
	/**服务器当前时间**/
	private Long nowTime;
	/**维权截止日期**/
	private Long rightsDateTime;
	/**
	 * 运单号
	 */
	private String billNo;
	private boolean edit;
	private String freight;//运费
	/**总订单优惠费**/
	private String youhuifei;
	/**子订单优惠费**/
	private String itemYouhuifei;
	private String payment;// 实付款
	/**
	 * 小计 = 购买商品数量 * 优惠后的商品单价
	 */
	private String itemAmount;	
	/**
	 * 商品总价 = 订单总额 - 运费
	 */
	private String orderItemAmount;
	/**
	 * 卖家拒绝维权时的理由
	 */
	private String refusedReason;
	/**
	 * 买家备注说明-买家填写的退货说明
	 */
	private String conments;
	
	private String outOfDate = "true";

	/**
	 * 维权消费详细信息 Created on 2011-7-4
	 * <p> Discription:[Discription] </p>
	 * @return
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	public String getRightsDetail() {
		try {
			query = new RightsMsgQuery();
			rightsId = getLong("rightsId");// 从界面得到维权ID(rightId)
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
			query.setBuyerId(loginInfo.getMemberId());
			RightsDO rightsDO = rightsManager.getRightsDOById(rightsId);
			if (rightsDO == null) {
				errorMsg = (Message.getMessage(MessageName.OPERATE_FAILED));
				return ERROR;
			}
			query.setRightsDO(rightsDO);
			// 得到买家申请退款金额（元）
			buyerApplyPriceStr = new Money(rightsDO.getBuyerApplyPrice()).getAmount().toString();
			// 查询右侧订单信息 OrderDO OrderItemDO,VouchPayDO
			Result orderResult = rightsAO.getOrderDOAndOrderItemDOAndVouchPayDOAndOrderLogisticsDOById(rightsDO); 
			if (!orderResult.isSuccess()) {
				status = rightsDO.getState();
				isGoods = rightsDO.getBuyerRequire();
				errorMsg = orderResult.getResultCode();
				return ERROR;
			}
			OrderDO orderDO = (OrderDO) orderResult.getModel("orderDO");
			query.setOrderDO(orderDO);
			if (!orderDO.getBuyerId().equals(Long.valueOf(loginInfo.getMemberId()))) {
				status = rightsDO.getState();
				isGoods = rightsDO.getBuyerRequire();
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_IDENTITY);
				return ERROR;
			}
			
			VouchPayDO vouchPayDO = (VouchPayDO) orderResult.getModel("vouchPayDO");
			
			OrderItemDO orderItemDO = (OrderItemDO) orderResult.getModel("orderItemDO");
			query.setOrderItemDO(orderItemDO);	
			
			Money transPayMoney = null ;//运输费
			OrderLogisticsDO orderLogisticsDO = (OrderLogisticsDO)orderResult.getModel("orderLogisticsDO");
			if(orderLogisticsDO != null){
				transPayMoney = new Money(orderLogisticsDO.getPostPrice());
				freight = transPayMoney.getAmount().toString();		
			}else {
				transPayMoney = new Money(0);
				freight = transPayMoney.getAmount().toString();	
			}
			//商品总价 = 订单总额 - 运费
			Money orderItemAmountMoney = new Money(orderDO.getPriceAmount()).subtract(transPayMoney);
			orderItemAmount = orderItemAmountMoney.getAmount().toString();
			//实付款 = 商品总价 + 运费
			payment = new Money(vouchPayDO.getRealPayMentamount() == null ? 0 : vouchPayDO.getRealPayMentamount()).getAmount().toString();
			if(vouchPayDO.getRealPayMentamount() == null){
				log.error("获取支付订单ID为："+vouchPayDO.getId()+"的实付款金额为null");
			}

			//优惠费--计算子订单优惠金额
			youhuifei = orderCouponAO.getCouponMoneyByYuan(orderDO.getOrderId());//总订单优惠
			itemYouhuifei = orderItemDO.getTotalAmountByYuan();//子订单优惠
			//卖家同意卖家退款退货，买家需发货给卖家
			if(rightsDO.getBuyerRequire() == 1 && (rightsDO.getState() == 1 || rightsDO.getState() == 3 || rightsDO.getState() == 11 || rightsDO.getState() == 12)){
				rightsLogisticsDO = new RightsLogisticsDO();
				rightsLogisticsDO.setVoucherId(rightsDO.getId());
				rightsLogisticsDO = rightsManager.getRightsLogisticsDO(rightsLogisticsDO);
				//显示物流信息
				if(rightsLogisticsDO == null){
					//获取货流方式列表信息
					LogisticsCorpDO logisticsCorpDO = new LogisticsCorpDO();
					//物流状态：1:正常 2:注销 3:冻结 4:其他
					logisticsCorpDO.setLogisticsStatus(1L);
					logisticsCorpDOs = logisticsCorpManager.getLogisticsCorpByStatus(logisticsCorpDO);
				}else {
					logisticsName = rightsLogisticsDO.getLogisticsName();
					billNo = rightsLogisticsDO.getBillNo();
					conments = rightsLogisticsDO.getComments();
				}
			}
	
			// ---------获取留言列表--------//
			query.setItemsPerPage(20); // 可以不设，每页条数，默认10条
			query.setPage(getInteger("currentPage"));
			query.setVoucherId(rightsId);
			Result result = rightsAO.getRightsMsgDOs(query);
			if (result.isSuccess()) {
				rightsMessageDOs = (List<RightsMessageDO>) result.getModel("rightsMsgDOs");
				if (rightsMessageDOs != null) {
					query.setRightsMessageDOs(rightsMessageDOs);
				}
			}else {
				errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			}
			query.setAction("/rights/rightsDetail.htm?rightsId="+rightsId);
			//买家超时处理
			//当维权状态为0.等待卖家处理;1.卖家已同意时;2.维权申请被卖家拒绝;显示维权剩余时间
			if (rightsDO.getState() == RightsConstant.WAIT_SELLER_HANDLE || rightsDO.getState() == RightsConstant.SELLER_AGREE || rightsDO.getState() == RightsConstant.SELLER_REFUSE || rightsDO.getState() == RightsConstant.SELLER_AGREE_BY_CUSTOMER) {
				rightsTime = rightsDO.getStateUpdateTime().getTime() + 5 * 24 * 60 * 60*1000;
				//----------------------------------//
				Calendar c1 = Calendar.getInstance();
				c1.setTime(rightsDO.getStateUpdateTime()); //维权创建时间
				c1.add(Calendar.DAY_OF_MONTH, 5);	
				//rightsDateTime = (c1.getTimeInMillis() - (new Date()).getTime())/1000;
				//----------------------------------//
				nowTime = (new Date()).getTime();
				Long gmtTime = rightsTime - nowTime;
				rightsDateTime = gmtTime/1000;
				if (gmtTime > 0){
					outOfDate = "false";
				}else{//买家处理超时
					outOfDate = "true";
					//默认为买家撤销维权（无论是退款还是退款退货）即更新维权状态为维权关闭
					if(rightsDO.getBuyerRequire() == RightsConstant.REQUIRE_GOODS){//退款退货时
						//维权状态为：1.卖家同意,2.卖家拒绝,3.客服同意
						if(rightsDO.getState() == RightsConstant.SELLER_AGREE || rightsDO.getState() == RightsConstant.SELLER_REFUSE || rightsDO.getState() == RightsConstant.SELLER_AGREE_BY_CUSTOMER){
							rightsDO.setState(RightsConstant.RIGHTS_CLOSE);
							rightsDO.setStateUpdateTime(new Date());
							result = rightsAO.updateRightsRecord(rightsDO);
							if (!result.isSuccess()) {
								log.error("Event=[RightsDetailAction#getRightsDetail] 更新维权状态为维权关闭失败");
							}
						}
					}else if (rightsDO.getBuyerRequire() == RightsConstant.REQUIRE_MONEY) {//退款时
						//维权状态为：1.卖家同意,2.卖家拒绝
						if(rightsDO.getState() == RightsConstant.SELLER_AGREE || rightsDO.getState() == RightsConstant.SELLER_REFUSE){
							rightsDO.setState(RightsConstant.RIGHTS_CLOSE);
							rightsDO.setStateUpdateTime(new Date());
							result = rightsAO.updateRightsRecord(rightsDO);
							if (!result.isSuccess()) {
								log.error("Event=[RightsDetailAction#getRightsDetail] 更新维权状态为维权关闭失败");
							}
						}
					} 			
				}
			}	
		} catch (ManagerException e) {
			errorMsg = "系统繁忙，请稍后再试";
			log.error("Event=[RightsAction#getRightsDetail] 显示消保维权详情失败",e);
			return ERROR;
		} catch (Exception e) {
			errorMsg = "系统繁忙，请稍后再试";
			log.error("Event=[RightsDetailAction#getRightsDetail] 显示消保维权详情失败",e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * @Description: [显示消保维权留言页.买家]
	 * @author 凌建涛
	 * @create 2011-7-7 下午02:33:02
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @version V1.0
	 */
	public String showSendMessage() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
			rightsId = getLong("rightsId");
		} catch (Exception e) {
			errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			log.error("Event=[RightsDetailAction#showSendMessage] 显示消保维权留言失败");
		}
		return SUCCESS;
	}
	

	/**
	 * Created on 2011-7-6
	 * <p>Discription:[维权留言记录处理.买家]</p>
	 * @param
	 * @return
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String sendMessage() throws Exception {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
				return ERROR;
			}
			// ----------发送留言----------//
			RightsMessageDO rightsMessageDO = new RightsMessageDO();
			rightsMessageDO.setVoucherId(rightsId);// 维权id
			rightsMessageDO.setUserId(loginInfo.getMemberId());// 用户ID
			rightsMessageDO.setUserNick(loginInfo.getMember().getNickname());// 留言用户昵称
			if(getStringLength(content) > 600){
				getRightsDetail();
				sendMsg = "请勿超出300个汉字600个字符长度，请尽量描述简洁，以方便查看。";
				return SUCCESS;
			}
			rightsMessageDO.setContent(StringUtils.trimToEmpty(filterMsg(content)));// 记录留言内容
			// -------上传凭证-------//
			if (voucherPic != null && voucherPic.length > 0 ) {
				//图片大小校验,图片大小不能超过500k
				for(File file:voucherPic){
					if(file != null && ((file.length()/PinjuConstant.FILE_SIZE_K) > 500)){
						getRightsDetail();
						sendMsg = Message.getMessage(MessageName.FILE_SIZE_TO_LARGE);
						return SUCCESS;
					}
				}
				//图片安全校验,如图片类型是否有效
				for(File file:voucherPic){
					if(file != null && !FileSecurityUtils.isImageValid(file)){
						getRightsDetail();
						sendMsg = Message.getMessage(MessageName.FILE_TYPE_INVALID);
						return SUCCESS;
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
			Result resultRightsMessageDO = rightsAO.sendMessage(rightsMessageDO);
			if (resultRightsMessageDO.isSuccess()) {
				sendMsg = "发表留言成功";
			} else {
				sendMsg = "发表留言失败";
			}
			getRightsDetail();
		} catch (Exception e) {
			errorMsg = "系统繁忙，请稍后再试";
			log.error("Event=[RightsDetailAction#sendMessage] 消保维权留言失败",e);
			return ERROR;
		}
		return SUCCESS;
	}	

	/**
	 * @Description: [显示消保维权留言页.卖家]
	 * @author 凌建涛
	 * @create 2011-7-7 下午02:33:02
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @version V1.0
	 */
	public String showSellerSendMessage() {
		try {
			rightsId = getLong("rightsId");
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
		} catch (Exception e) {
			errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			log.error("Event=[RightsDetailAction#showSellerSendMessage] 显示消保维权留言失败",e);
		}
		return SUCCESS;
	}

	/**
	 * Created on 2011-7-6
	 * <p>Discription:[到维权记录修改页.买家]</p>
	 * @param
	 * @return
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String showModifyRightsPage() {
		try {
			query = new RightsMsgQuery();
			if(!edit){
				rightsId = getLong("rightsId");
			}
			//获取登录用户信息
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			if (!loginInfo.isLogin()) {
				errorMsg = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN));
				return ERROR;
			}
			RightsDO rightsDO = rightsManager.getRightsDOById(rightsId);
			if(rightsDO == null){
				log.debug("编号为["+rightsId+"]的维权不存在");
				errorMsg = "该维权不存在！";
				return ERROR;
			}
			query.setRightsDO(rightsDO);
			//买家申请退款金额（元）
			globalMoney = new Money(rightsDO.getBuyerApplyPrice()).getAmount().toString();
			// 查询右侧订单信息 OrderDO OrderItemDO,VouchPayDO
			Result orderResult = rightsAO.getOrderDOAndOrderItemDOAndVouchPayDOAndOrderLogisticsDOById(rightsDO); 
			if (!orderResult.isSuccess()) {
				errorMsg = orderResult.getResultCode();
				return ERROR;
			}
			OrderDO orderDO = (OrderDO) orderResult.getModel("orderDO");
			query.setOrderDO(orderDO);
			if (!orderDO.getBuyerId().equals(Long.valueOf(loginInfo.getMemberId()))) {
				errorMsg = (Message.getMessage(RightsMessageName.OPERATE_RIGHTS_IDENTITY));
				return ERROR;
			}
			
			VouchPayDO vouchPayDO = (VouchPayDO) orderResult.getModel("vouchPayDO");

			OrderItemDO orderItemDO = (OrderItemDO) orderResult.getModel("orderItemDO");
			query.setOrderItemDO(orderItemDO);	
			
			Money transPayMoney = null ;//运输费
			OrderLogisticsDO orderLogisticsDO = (OrderLogisticsDO)orderResult.getModel("orderLogisticsDO");
			if(orderLogisticsDO!=null){
				transPayMoney = new Money(orderLogisticsDO.getPostPrice());
				freight = transPayMoney.getAmount().toString();
			}else {
				transPayMoney = new Money(0);
				freight = transPayMoney.getAmount().toString();
			}
			//优惠费
			youhuifei = orderCouponAO.getCouponMoneyByYuan(orderDO.getOrderId());//总订单优惠
			itemYouhuifei = orderItemDO.getTotalAmountByYuan();//子订单优惠
			//商品总价  = 订单总额 - 运费
			Money orderItemAmountMoney = new Money(orderDO.getPriceAmount()).subtract(transPayMoney);
			orderItemAmount = orderItemAmountMoney.getAmount().toString();
			//实付款 = 商品总价 + 运费
			payment = new Money(vouchPayDO.getRealPayMentamount() == null ? 0 : vouchPayDO.getRealPayMentamount()).getAmount().toString();
			if(vouchPayDO.getRealPayMentamount() == null){
				log.error("获取支付订单ID为："+vouchPayDO.getId()+"的实付款金额为null");
			}
		} catch (Exception e) {
			errorMsg = "系统繁忙，请稍后再试";
			log.error("Event=[RightsDetialAction#showModifyRightsPage] 发起维权至修改页失败！",e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Created on 2011-7-6
	 * <p>Discription:[维权记录修改处理操作.买家]</p>
	 * @param
	 * @return String
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String modifyRights() {
		try {
			edit = true;
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
				return ERROR;
			}
			RightsDO rightsDO = rightsManager.getRightsDOById(rightsId);
			if(rightsDO == null){
				errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
				return ERROR;
			}
			if(rightsDO.getState() != 0){
				status = rightsDO.getState();
				isGoods = rightsDO.getBuyerRequire();
				errorMsg = "您的维权状态不能进行此操作";
				return ERROR;
			}
			// 查询右侧订单信息 OrderDO OrderItemDO,VouchPayDO
			Result orderResult = rightsAO.getOrderDOAndOrderItemDOAndVouchPayDOAndOrderLogisticsDOById(rightsDO); 
			if (!orderResult.isSuccess()) {
				status = rightsDO.getState();
				isGoods = rightsDO.getBuyerRequire();
				errorMsg = orderResult.getResultCode();
				return ERROR;
			}
			//获取子订单信息
			OrderItemDO orderItemDO = (OrderItemDO) orderResult.getModel("orderItemDO");
			query = new RightsMsgQuery();
			query.setOrderItemDO(orderItemDO);				
			RightsDO  newRightsDO =new RightsDO();
			newRightsDO.setId(rightsId);
			if(StringUtil.isNotEmpty(filterMsg(buyerReason))){
				if(getStringLength(buyerReason) > 300){
					showModifyRightsPage();
					log.debug("请勿超出150个汉字300字符长度，请尽量描述简洁，以方便卖家查看。");
					errorMsg = "请勿超出150个汉字300字符长度，请尽量描述简洁，以方便卖家查看。";
					return SUCCESS;
				}
			}			
			newRightsDO.setBuyerReason(buyerReason);
			tradePrice = orderItemDO.getTotalAmountByYuan();
			if(StringUtil.isNotEmpty(globalMoney)&&StringUtil.isNotEmpty(tradePrice)){
				buyerApplyPrice = new Money(globalMoney).getCent();// 单位：分
				Money money = new Money(tradePrice);
				if(voucherType == 2){//如果维权类型是2-假一赔三时，最多 （商品实际付款的金额*3 + 100）
					money = money.multiply(3).add(new Money(100.00));
					if (buyerApplyPrice > (money.getCent())) {
						log.debug("买家申请退款金额不能大于（商品实际付款的金额*3+100）");
						showModifyRightsPage();
						errorMsg = "买家申请金额超限！";
						return SUCCESS;
					}
				}else if(voucherType == 1){//有质量问题求退款上限为该商品实际付款的金额加100元可能的运费
					money = money.add(new Money(100.00));
					if (buyerApplyPrice > (money.getCent())) {
						log.debug("买家申请退款金额不能大于（商品实际付款的金额+100）");
						showModifyRightsPage();
						errorMsg = "买家申请金额超限！";
						return SUCCESS;
					}
				}else {//voucherType=0七天无理由退货要求退款上限为买家为该商品实际付款的金额
					if (buyerApplyPrice > (money.getCent())) {
						log.debug("买家申请退款金额不能大于（商品实际付款的金额）");
						showModifyRightsPage();
						errorMsg = "买家申请金额超限！";
						return SUCCESS;
					}
				}
				newRightsDO.setBuyerApplyPrice(buyerApplyPrice); 			
			}
			// -------上传凭证-------//
			if (voucherPic != null && voucherPic.length > 0) {
				//图片大小校验,图片大小不能超过500k
				for(File file:voucherPic){
					if((file.length()/PinjuConstant.FILE_SIZE_K) > 500){
						showModifyRightsPage();
						errorMsg = Message.getMessage(MessageName.FILE_SIZE_TO_LARGE);
						return SUCCESS;
					}
				}
				//图片安全校验,如图片类型是否有效
				for(File file:voucherPic){
					if(!FileSecurityUtils.isImageValid(file)){
						showModifyRightsPage(); 
						errorMsg = Message.getMessage(MessageName.FILE_TYPE_INVALID);
						return SUCCESS;
					}
				}
				Result resultFile = fileUploadAO.saveUserPics(voucherPic, voucherPicFileName, 
					loginInfo.getMemberId(), loginInfo.getMember().getNickname());
				if (resultFile.isSuccess()) {
					String[] retFileName = (String[])resultFile.getModel("fileNames");
					if (retFileName.length==1) {
						newRightsDO.setVoucherPic1(retFileName[0]);
					}else if(retFileName.length==2){
						 newRightsDO.setVoucherPic1(retFileName[0]);
						 newRightsDO.setVoucherPic2(retFileName[1]);
					}else if(retFileName.length==3){
						 newRightsDO.setVoucherPic1(retFileName[0]);
						 newRightsDO.setVoucherPic2(retFileName[1]);
						 newRightsDO.setVoucherPic3(retFileName[2]);
					}
				}
			}
			Result resultRightsDO = rightsAO.updateRightsRecord(newRightsDO);
			if (!resultRightsDO.isSuccess()) {
				errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			} else {
				errorMsg = Message.getMessage(MessageName.OPERATE_UPDATE_SUCCESS);
			}
			showModifyRightsPage();
		} catch (Exception e) {
			errorMsg = "系统繁忙，请稍后再试！";
			log.error("Event=[RightsDetailAction#modifyRights] 发起维权修改失败！",e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Created on 2011-7-6
	 * <p>Discription:[到维权记录修改页.卖家]</p>
	 * @param
	 * @return
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String showModifySellerRightsPage() {
		try {
			query = new RightsMsgQuery();
			if(!edit){
				rightsId = getLong("rightsId");
			}
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				getRightsDetail();
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
				return ERROR;
			}
			RightsDO rightsDO = rightsManager.getRightsDOById(rightsId);
			if (rightsDO != null) {
				query.setRightsDO(rightsDO);
				// 买家申请退款金额（元）
				globalMoney = new Money(rightsDO.getBuyerApplyPrice()).getAmount().toString();
				if (loginInfo.getMemberId() != rightsDO.getBuyerId()) {
					getRightsDetail();
					errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_IDENTITY);
					return ERROR;
				}
				Money itemAmoutMoney = null ;//运输费
				Money transPayMoney = null ;//商品总价
				OrderLogisticsDO orderLogisticsDO = orderBusinessManager.queryOrderLogisticsByOrderId(rightsDO.getOrderId());
				if(orderLogisticsDO!=null){
					//运费
					transPayMoney = new Money(orderLogisticsDO.getPostPrice());
					freight = transPayMoney.getAmount().toString();
				}
				OrderItemDO orderItemDO = orderQueryManager.getOrderItemDOById(rightsDO.getSubOrderId());//获取子订单信息
				if(orderItemDO!=null){
					query.setOrderItemDO(orderItemDO);
					//优惠费
//					Money youhuifeuMoney = new Money(orderItemDO.getOriginalPrice()).subtract(new Money(orderItemDO.getOrderItemPrice()));
//					youhuifei = youhuifeuMoney.getAmount().toString();
					youhuifei = orderCouponAO.getCouponMoneyByYuan(rightsDO.getOrderId());//总订单优惠
					itemYouhuifei = orderItemDO.getTotalAmountByYuan();//子订单优惠					
					// 商品总价=子订单商品数量*优惠否的单价
					itemAmoutMoney = new Money(orderItemDO.getOrderItemPrice()).multiply(orderItemDO.getBuyNum());
					orderItemAmountStr = itemAmoutMoney.getAmount().toString();
				}
				//实付款(即商品总价+运费)
				if(itemAmoutMoney != null && transPayMoney != null){
					payment = itemAmoutMoney.add(transPayMoney).toString();
				}
			} else {
				errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			}
		} catch (Exception e) {
//			getSellerRightsDetail();
			errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			log.error("Event=[RightsDetialAction#showModifySellerRightsPage] 发起维权修改页失败！",e);
			return ERROR;
		}
		return SUCCESS;
	}	
	
	/**
	 * Created on 2011-7-6
	 * <p>Discription:[维权记录修改处理操作.卖家]</p>
	 * @param
	 * @return
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String modifySellerRights() {
		try {
			edit = true;
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
				return SUCCESS;
			}
			RightsDO  newRightsDO =new RightsDO();
			newRightsDO.setId(rightsId);
			newRightsDO.setVoucherType(voucherType);
			// 跟着消保走,此处还需要个查询
			newRightsDO.setBuyerRequire(buyerRequire); // 维权要求 0-要求退款
			// 1-要求退货退款
			newRightsDO.setBuyerReason(buyerReason);
			if(StringUtil.isNotEmpty(globalMoney)&&StringUtil.isNotEmpty(tradePrice)){
				buyerApplyPrice = new Money(globalMoney).getCent();// 单位：分
				if (buyerApplyPrice>(new Money(tradePrice).getCent())) {
					log.debug("买家申请退款金额不能大于交易金额");
					errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
					showModifySellerRightsPage();
					return SUCCESS;
				}
			}
			newRightsDO.setBuyerApplyPrice(buyerApplyPrice); 			
			// -------上传凭证-------//
			if (voucherPic != null && voucherPic.length > 0) {
				//图片大小校验,图片大小不能超过100k
				for(File file:voucherPic){
					if((file.length()/PinjuConstant.FILE_SIZE_K)>100){
						errorMsg = Message.getMessage(MessageName.FILE_SIZE_TO_LARGE);
						showModifySellerRightsPage();
						return ERROR;
					}
				}
				//图片安全校验,如图片类型是否有效
				for(File file:voucherPic){
					if(!FileSecurityUtils.isImageValid(file)){
						errorMsg = Message.getMessage(MessageName.FILE_TYPE_INVALID);
						showModifySellerRightsPage();
						return ERROR;
					}
				}
				Result resultFile = fileUploadAO.saveUserPics(voucherPic, voucherPicFileName, 
					 loginInfo.getMemberId(), loginInfo.getMember().getNickname());
				if (resultFile.isSuccess()) {
					String[] retFileName = (String[])resultFile.getModel("fileNames");
					if (retFileName.length==1) {
						newRightsDO.setVoucherPic1(retFileName[0]);
					}else if(retFileName.length==2){
						newRightsDO.setVoucherPic1(retFileName[0]);
						newRightsDO.setVoucherPic2(retFileName[1]);
					}else if(retFileName.length==3){
						newRightsDO.setVoucherPic1(retFileName[0]);
						newRightsDO.setVoucherPic2(retFileName[1]);
						newRightsDO.setVoucherPic3(retFileName[2]);
					}
				}
			}
			Result resultRightsDO = rightsAO.updateRightsRecord(newRightsDO);
			if (resultRightsDO.isSuccess()) {
				errorMsg = Message.getMessage(MessageName.OPERATE_UPDATE_SUCCESS);
			} else {
				errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			}
			showModifySellerRightsPage();
		} catch (Exception e) {
			errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			log.error("Event=[RightsDetailAction#modifySellerRights] 发起维权修改失败！",e);
		}
		return SUCCESS;
	}

	/**
	 * Created on 2011-7-6
	 * <p>Discription:[消保维权撤销处理.买家]</p>
	 * @param
	 * @return
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String buyerCancelRights() {
		try {
			rightsId = getLong("rightsId");
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = Message.getMessage(MessageName.MEMBER_LOGIN_FAILED);
				return ERROR;
			}
			RightsDO newRightsDO = new RightsDO();
			newRightsDO.setId(rightsId);
			newRightsDO.setState(RightsConstant.RIGHTS_CLOSE); // 维权撤销即维权状态改为：关闭
			newRightsDO.setStateUpdateTime(new Date());
			Result resultRightsDO = rightsAO.updateRightsRecord(newRightsDO);
			if (!resultRightsDO.isSuccess()){
				this.getRightsDetail();
				errorMsg = resultRightsDO.getResultCode();
			}
		} catch (Exception e) {
			errorMsg = "系统繁忙，请稍后再试";
			log.error("Event=[RightsDetailAction#rightsCancel] 发起维权撤销失败！",e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Created on 2011-7-6
	 * <p>Discription:[消保维权撤销处理.卖家] </p>
	 * @param
	 * @return
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String sellerRightsCancel() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
//				this.getSellerRightsDetail();
				return SUCCESS;
			}
			rightsId = getLong("rightsId");
			RightsDO rightsDO = rightsManager.getRightsDOById(rightsId);
			if(rightsDO!=null){
				if (loginInfo.getMemberId() != rightsDO.getSellerId()) {
					errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_IDENTITY);
					return SUCCESS;
				}
				RightsDO newRightsDO = new RightsDO();
				newRightsDO.setId(rightsId);
				newRightsDO.setState(RightsConstant.RIGHTS_CLOSE); // 维权撤销即维权状态改为：关闭
				Result resultRightsDO = rightsAO.updateRightsRecord(newRightsDO);
				if (resultRightsDO.isSuccess()){
					errorMsg = Message.getMessage(MessageName.OPERATE_SUCCESS);
				}else{
					errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
				}
			}
		} catch (Exception e) {
			errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
			log.error("Event=[RightsDetailAction#sellerRightsCancel] 发起维权撤销失败！",e);
		}
		return SUCCESS;
	}



	/**
	 * @Description: 消保维权(退款退货).买家同意退货
	 * @author 凌建涛
	 * @create 2011-7-7 下午06:28:10
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @version V1.0
	 */
	public String buyerAgreeRights() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
				return ERROR;
			}
			RightsDO rightsDO = new RightsDO();
			rightsDO.setId(rightsId);
			Result resultRightsDO = rightsAO.getRightsDO(rightsDO);
			if(!resultRightsDO.isSuccess()){
				errorMsg = "查询维权信息失败";
				return ERROR;
			}
			rightsDO = (RightsDO)resultRightsDO.getModel("rightsDO");
			if(rightsDO == null){
				errorMsg = "该维权信息不存在";
				return ERROR;
			}
			if (!rightsDO.getBuyerId().equals(Long.valueOf(loginInfo.getMemberId()))) {
				status = rightsDO.getState();
				isGoods = rightsDO.getBuyerRequire();
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_IDENTITY);
				return ERROR;
			}
			if (rightsDO.getBuyerRequire() == RightsConstant.REQUIRE_GOODS) {//买家要求退款退货
				//确认发货后记录物流信息
				RightsLogisticsDO rightsLogisticsDO = new RightsLogisticsDO();
				rightsLogisticsDO.setVoucherId(rightsId);
				rightsLogisticsDO = rightsManager.getRightsLogisticsDO(rightsLogisticsDO);
				String logisticsId = "";
				if (StringUtil.isNotEmpty(logisticsName)) {
					logisticsId = StringUtil.substringBefore(logisticsName, ":");
					logisticsName = StringUtil.substringAfter(logisticsName, ":");
				}
				if(getStringLength(conments) > 300){
					status = rightsDO.getState();
					isGoods = rightsDO.getBuyerRequire();
					sendMsg = "请勿超出150个汉字300个字符长度，请尽量描述简洁，以方便查看。";
					return ERROR;
				}
				if(rightsLogisticsDO == null){
					rightsLogisticsDO = new RightsLogisticsDO();
					rightsLogisticsDO.setVoucherId(rightsId);
					rightsLogisticsDO.setLogisticsId(Long.valueOf(logisticsId));
					rightsLogisticsDO.setLogisticsName(logisticsName);
					rightsLogisticsDO.setBillNo(billNo);
					rightsLogisticsDO.setBuyerId(rightsDO.getBuyerId());
					rightsLogisticsDO.setComments(conments);
					rightsLogisticsDO.setBuySendDate(new Date());
					rightsLogisticsDO.setSellerId(rightsDO.getSellerId());
					rightsLogisticsDO.setBuyerReturnState(RightsConstant.RETURN_GOODS_ALREADY);
					if(rightsDO.getState() == 1){//卖家同意时
						rightsManager.insertLogisticsRecord(rightsLogisticsDO);
						
						rightsDO.setState(RightsConstant.WAIT_SELLER_RECEIVED); // 买家同意发货：等待卖家确认收货
						rightsDO.setStateUpdateTime(new Date());
						Result resultUpdateRightsDO = rightsAO.updateRightsRecord(rightsDO);
						if(!resultUpdateRightsDO.isSuccess()){
							errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
						}
					}else if(rightsDO.getState() == 12){//客服同意时
						rightsManager.insertLogisticsRecord(rightsLogisticsDO);
						
						rightsDO.setState(RightsConstant.WAIT_SELLER_RECEIVED_BY_CUSTOMER);//买家同意货，等待客服确认收货
						rightsDO.setStateUpdateTime(new Date());
						Result resultUpdateRightsDO = rightsAO.updateRightsRecord(rightsDO);
						if(!resultUpdateRightsDO.isSuccess()){
							errorMsg = Message.getMessage(MessageName.OPERATE_FAILED);
						}
					}
				}else {
					status = rightsDO.getState();
					isGoods = rightsDO.getBuyerRequire();
					errorMsg = "该维权的物流信息已发出";
					return ERROR;
				}
			}
		} catch (Exception e) {
			errorMsg = "系统繁忙，请稍后再试";
			log.error("Event=[RightsDetailAction#buyerAgreeRights] 买家家同发货失败！",e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * Create on: 2011-8-23下午05:05:44
	 * <p>Discription:[买家申请客服介入处理]</p>
	 * @param: 
	 * @return: String 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String buyerApply(){
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();//获取登录用户信息
			if (!loginInfo.isLogin()) {
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_RELOGIN);
				return ERROR;
			}
			rightsId = getLong("rightsId");
			RightsDO rightsDO = rightsManager.getRightsDOById(rightsId);
			if(rightsDO == null){
				errorMsg = "查询维权信息失败";
				return ERROR;
			}
			if (!rightsDO.getBuyerId().equals(Long.valueOf(loginInfo.getMemberId()))) {
				status = rightsDO.getState();
				isGoods = rightsDO.getBuyerRequire();
				errorMsg = Message.getMessage(RightsMessageName.OPERATE_RIGHTS_IDENTITY);
				return ERROR;
			}
			//只有当维权状态为0-等待卖家处理时，或者2-卖家拒绝时才能申请客服处理
			if(rightsDO.getState() == RightsConstant.WAIT_SELLER_HANDLE || rightsDO.getState() == RightsConstant.SELLER_REFUSE){
				rightsDO.setState(RightsConstant.WAIT_CUSTOMER_HANDLE); // 维权撤销即维权状态改为：关闭
			}else {
				this.getRightsDetail();
				errorMsg = "该状态不能申请客服介入";
				return SUCCESS;
			}
			rightsDO.setStateUpdateTime(new Date());
			Result resultRightsDO = rightsAO.updateRightsRecord(rightsDO);
			if (resultRightsDO.isSuccess()){
				errorMsg = (Message.getMessage(MessageName.OPERATE_SUCCESS));
			}else{
				errorMsg = (Message.getMessage(MessageName.OPERATE_FAILED));
			}
			this.getRightsDetail();
		} catch (Exception e) {
			errorMsg = "系统繁忙，请稍后再试";
			log.error("Event=[RightsDetailAction#buyerApply] 买家申请客服介入处理失败！",e);
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
	
	/**
	 * Create on: 2011-10-28下午05:10:32
	 * <p>Discription:[判断一个字符是不是汉字]</p>
	 * @param: char
	 * @return: boolean 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private static boolean isChinese(char c) {
		return c > '\377';
	}	
	
	public RightsAO getRightsAO() {
		return rightsAO;
	}

	public void setRightsAO(RightsAO rightsAO) {
		this.rightsAO = rightsAO;
	}

	public RightsMsgQuery getQuery() {
		return query;
	}

	public void setQuery(RightsMsgQuery query) {
		this.query = query;
	}

	public FileUploadAO getFileUploadAO() {
		return fileUploadAO;
	}

	public void setFileUploadAO(FileUploadAO fileUploadAO) {
		this.fileUploadAO = fileUploadAO;
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

	public String getBuyerReason() {
		return buyerReason;
	}

	public void setBuyerReason(String buyerReason) {
		this.buyerReason = buyerReason;
	}

	public Long getBuyerApplyPrice() {
		return buyerApplyPrice;
	}

	public void setBuyerApplyPrice(Long buyerApplyPrice) {
		this.buyerApplyPrice = buyerApplyPrice;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setRightsId(Long rightsId) {
		this.rightsId = rightsId;
	}

	public Long getRightsId() {
		return rightsId;
	}

	public void setOrderItemAmountStr(String orderItemAmountStr) {
		this.orderItemAmountStr = orderItemAmountStr;
	}

	public String getOrderItemAmountStr() {
		return orderItemAmountStr;
	}

	public void setGlobalMoney(String globalMoney){
		this.globalMoney = globalMoney;
	}

	public String getGlobalMoney(){
		return globalMoney;
	}

	public void setRightsManager(RightsManager rightsManager){
		this.rightsManager = rightsManager;
	}

	public RightsManager getRightsManager(){
		return rightsManager;
	}

	public void setBuyerApplyPriceStr(String buyerApplyPriceStr){
		this.buyerApplyPriceStr = buyerApplyPriceStr;
	}

	public String getBuyerApplyPriceStr(){
		return buyerApplyPriceStr;
	}

	public void setTradePrice(String tradePrice){
		this.tradePrice = tradePrice;
	}

	public String getTradePrice(){
		return tradePrice;
	}

	public void setLogisticsName(String logisticsName){
		this.logisticsName = logisticsName;
	}

	public String getLogisticsName(){
		return logisticsName;
	}

	public void setUpdateMsg(String updateMsg){
		this.updateMsg = updateMsg;
	}

	public String getUpdateMsg(){
		return updateMsg;
	}

	public MarginManager getMarginManager(){
		return marginManager;
	}

	public void setOrderBusinessManager(OrderBusinessManager orderBusinessManager){
		this.orderBusinessManager = orderBusinessManager;
	}

	public void setYouhuifei(String youhuifei){
		this.youhuifei = youhuifei;
	}

	public String getYouhuifei(){
		return youhuifei;
	}

	public void setPayment(String payment){
		this.payment = payment;
	}

	public String getPayment(){
		return payment;
	}

	public void setFreight(String freight){
		this.freight = freight;
	}

	public String getFreight(){
		return freight;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public String getOutOfDate() {
		return outOfDate;
	}

	public void setOutOfDate(String outOfDate) {
		this.outOfDate = outOfDate;
	}

	public void setRefusedReason(String refusedReason){
		this.refusedReason = refusedReason;
	}

	public String getRefusedReason(){
		return refusedReason;
	}

	public void setItemAmount(String itemAmount){
		this.itemAmount = itemAmount;
	}

	public String getItemAmount(){
		return itemAmount;
	}
	
	public void setConments(String conments){
		this.conments = conments;
	}

	public String getConments(){
		return conments;
	}
	
	public List<LogisticsCorpDO> getLogisticsCorpDOs(){
		return logisticsCorpDOs;
	}

	public void setLogisticsCorpDOs(List<LogisticsCorpDO> logisticsCorpDOs){
		this.logisticsCorpDOs = logisticsCorpDOs;
	}

	public void setLogisticsCorpManager(LogisticsCorpManager logisticsCorpManager){
		this.logisticsCorpManager = logisticsCorpManager;
	}
	public RightsLogisticsDO getRightsLogisticsDO(){
		return rightsLogisticsDO;
	}

	public void setRightsLogisticsDO(RightsLogisticsDO rightsLogisticsDO){
		this.rightsLogisticsDO = rightsLogisticsDO;
	}

	public void setOrderItemAmount(String orderItemAmount){
		this.orderItemAmount = orderItemAmount;
	}

	public String getOrderItemAmount(){
		return orderItemAmount;
	}

	public void setSendMsg(String sendMsg){
		this.sendMsg = sendMsg;
	}

	public String getSendMsg(){
		return sendMsg;
	}
	public String getBillNo(){
		return billNo;
	}

	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	public void setRightsTime(Long rightsTime){
		this.rightsTime = rightsTime;
	}

	public Long getRightsTime(){
		return rightsTime;
	}

	public void setNowTime(Long nowTime){
		this.nowTime = nowTime;
	}

	public Long getNowTime(){
		return nowTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setIsGoods(Integer isGoods){
		this.isGoods = isGoods;
	}

	public Integer getIsGoods(){
		return isGoods;
	}

	public void setRightsDateTime(Long rightsDateTime){
		this.rightsDateTime = rightsDateTime;
	}

	public Long getRightsDateTime(){
		return rightsDateTime;
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
