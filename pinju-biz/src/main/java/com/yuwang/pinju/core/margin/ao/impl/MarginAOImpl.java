package com.yuwang.pinju.core.margin.ao.impl;

import java.util.Date;
import java.util.List;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.trimToEmpty;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.MarginResultCode;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.core.item.manager.CategoryMarginManager;
import com.yuwang.pinju.core.margin.ao.MarginAO;
import com.yuwang.pinju.core.margin.manager.MarginManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.trade.manager.DirectManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.margin.MarginPinJuDO;
import com.yuwang.pinju.domain.margin.MarginPinjuOrderDO;
import com.yuwang.pinju.domain.margin.MarginSellerDO;
import com.yuwang.pinju.domain.margin.MarginSellerOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginSellerOrderQuery;
import com.yuwang.pinju.domain.member.SellerQualityDO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.trade.DirectOrderDO;
import com.yuwang.pinju.domain.trade.DirectPayDO;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;

/**  
 * @Project: pinju-biz
 * @Description: 保证金管理类
 * @author 石兴 shixing@zba.com
 * @date 2011-8-2 上午10:43:52
 * @update 2011-8-2 上午10:43:52
 * @version V1.0  
 */
public class MarginAOImpl extends BaseAO implements MarginAO {

	private MarginManager marginManager;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	private DirectManager directManager;
	
	private ShopOpenManager shopOpenManager;
	
	private DataSourceTransactionManager zizuTransactionManager;
	
	private CategoryMarginManager categoryMarginManager;
	
	private MemberManager memberManager;
	
	@Override
	public Result getMarginBackUrlByPayOrderId(Long payOrderId) {
		Result result = new ResultSupport();
		try {
			DirectPayDO directPayDO =  directManager.getDirectPayDOById(payOrderId);
			if (directPayDO==null) {
				result.setSuccess(false);
				return result;
			}
			MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(directPayDO.getBuyerId());
			if (marginSellerDO==null) { //前台缴保证金
				result.setModel("resultActionName","fromOpenShop");
			}else { //后台缴保证金
				result.setModel("resultActionName","fromSellerBack");
			}
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * Created on 2011-8-13 
	 * <p>Discription:[判断卖家保证金是否正常]</p>
	 * @param 正常true 不正常(未缴,或余额不足)false
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean isMarginNormal(Long userId,ShopInfoDO shopInfoDO) {
		try {
			if (shopInfoDO == null) {
				return false;
			}
			//查询卖家账户，看是否有缴保证金
			MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(userId);
			if (marginSellerDO == null) {
				return false;
			}
			
			//需要缴纳的保证金金额
			Long marginPrice = Long.valueOf(shopInfoDO.getShopInfoConfig("MARGIN_PRICE")); 
			//用户当前保证金余额
			Long currentMargin = marginSellerDO.getCurrentMargin();
			if(marginPrice.longValue() == currentMargin.longValue()){  
				return true;
			}
		} catch (Exception e) {
			log.error("Event=[MarginAOImpl#isMarginNormal] 判断用户保证金状态异常",e);
		}
		return false;
	}
	
	/**
	 * Created on 2011-8-13 
	 * <p>Discription:[拼装支付参数]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void createDirectPayParamDO(ShopInfoDO shopInfoDO,Result result,Long price) {
		try {
			Long itemId = 0L;
			String itemTitle = EMPTY;
			String marginType = shopInfoDO.getExchangeMargin(); //保证金类型
			if (String.valueOf(DirectPayConstant.MARGIN_TYPE_BASE).equals(trimToEmpty(marginType))) { 
				itemId = DirectPayConstant.MARGIN_ITEM_ID_BASE;
				itemTitle = DirectPayConstant.MARGIN_TITLE_BASE;
			}else {
				itemId = DirectPayConstant.MARGIN_ITEM_ID_SENIOR;
				itemTitle = DirectPayConstant.MARGIN_TITLE_SENIOR;
			}
			DirectPayParamDO directPayParamDO = new DirectPayParamDO();
			directPayParamDO.setBuyerId(shopInfoDO.getUserId());
			directPayParamDO.setBuyerNick(shopInfoDO.getNickname());
			directPayParamDO.setSellerId(Long.valueOf(PinjuConstant.SHENGPAY_DIRECTPAY_PINJU_SELLERID));
			directPayParamDO.setSellerNick(PinjuConstant.SHENGPAY_DIRECTPAY_PINJU_SELLERNICK);
			directPayParamDO.setBizType(DirectPayConstant.BIZ_TYPE_MARGIN);
			directPayParamDO.setItemId(itemId);
			directPayParamDO.setItemTitle(itemTitle);
			directPayParamDO.setItemPrice(price); //需要缴的保证金金额
			directPayParamDO.setBuyAmount(1);//购买数量,消保默认1
			result.setModel("directPayParamDO",directPayParamDO);
		} catch (NumberFormatException e) {
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.NUMBERFORMAT_EXCEPTION_MARGIN);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.MARGIN_REQ_PARAM_FAIL);
		}
	}
	
	@Override
	public Result payMargin(Long userId) {
		Result result = new ResultSupport();
		try {
			//查询用户需要缴纳多少保证金信息
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, ShopConstant.APPROVE_STATUS_NO);
			if(shopInfoDO == null || isEmpty(shopInfoDO.getExchangeMargin())){ //保证金类型未知,未完成开店流程
				result.setSuccess(false);
				result.setResultCode(MarginResultCode.SELLER_MARGIN_NOT_EXIST);
				return result;
			}
			
			//如果用户保证金正常足额,则提示无需缴纳,
			//因业务调整为可多缴,故此操作先注释掉 By ShiXing@2011.09.09
			/*if (isMarginNormal(userId,shopInfoDO)) {
				result.setSuccess(false);
				result.setResultCode(MarginResultCode.MAGIN_SELLER_OVER);
				return result;
			}*/

			//拼装支付参数
			Long price = Long.valueOf(shopInfoDO.getShopInfoConfig("MARGIN_PRICE"));
			createDirectPayParamDO(shopInfoDO,result,price);
		}catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.PAYMARGIN_EXCEPTION);
			log.error("Event=[MarginAOImpl#payMargin] 缴纳保证金异常",e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.PAYMARGIN_FAIL);
			log.error("Event=[MarginAOImpl#payMargin] 缴纳保证金失败",e);
		}
		return result;
	}
	
	@Override
	public Result backPayMargin(long memberId,long price) {
		Result result = new ResultSupport();
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(memberId, null);
			if(shopInfoDO == null || shopInfoDO.getApproveStatus()==0){ //没有店铺,即不是品聚卖家,则提示开店
				result.setSuccess(false);
				result.setResultCode(MarginResultCode.SELLER_SHOP_NOT_EXIST);
				return result;
			}
			String marginPrice = shopInfoDO.getShopInfoConfig("MARGIN_PRICE");
			if (StringUtils.isEmpty(marginPrice)) { //兼容开店成功未缴保证金操作 Add By ShiXing@2011.09.21
				String pinjuMargin = String.valueOf(categoryMarginManager.getItemMargin(Long.valueOf(shopInfoDO.getShopCategory()),Integer.valueOf(shopInfoDO.getSellerType())));
				shopInfoDO.setExchangeMargin(String.valueOf(DirectPayConstant.MARGIN_TYPE_BASE));//基础消保
				shopInfoDO.setConfiguration("MARGIN_PRICE=".concat(pinjuMargin));
				shopShowInfoManager.updateShopBaseInfo(shopInfoDO); 
				SellerQualityDO sellerQuality = new SellerQualityDO();
				sellerQuality.setMargin(Long.valueOf(pinjuMargin));
				sellerQuality.setMemberId(memberId);
				sellerQuality.setCpType(DirectPayConstant.MARGIN_TYPE_BASE);
				memberManager.updateSellerQuality(memberId, sellerQuality);
			   repairSellerQuality(memberId, shopInfoDO);
			}
			//查询卖家账户，看是否有缴保证金
			//因业务调整为可多缴,故此操作先注释掉 By ShiXing@2011.09.09
			/*MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(memberId);
			if (marginSellerDO.getCurrentMargin() >= marginSellerDO.getPinjuMargin()) {
				result.setSuccess(false);
				result.setResultCode(MarginResultCode.MAGIN_SELLER_OVER);
				return result;
			}*/
			createDirectPayParamDO(shopInfoDO,result,price);
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.PAYMARGIN_EXCEPTION);
			log.error("Event=[MarginAOImpl#backPayMargin] 后台缴纳保证金异常",e);
		}catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.PAYMARGIN_FAIL);
			log.error("Event=[MarginAOImpl#backPayMargin] 后台缴纳保证金失败",e);
		}
		return result;
	}

	/**
	 * Created on 2011-9-30 
	 * @desc <p>Discription:[对已经开店成功和CP_TYPE IS NOT NULL约束引起的数据未插入问题补插]</p>
	 * @param 
	 * @return void
	 * @author:[石兴]
	 * @update:[2011-9-30] [更改人姓名]
	 */
	private void repairSellerQuality(long memberId, ShopInfoDO shopInfoDO)throws ManagerException {
		try {
			String pinjuMargin = String.valueOf(categoryMarginManager.getItemMargin(Long.valueOf(shopInfoDO.getShopCategory()),Integer.valueOf(shopInfoDO.getSellerType())));
			shopInfoDO.setExchangeMargin(String.valueOf(DirectPayConstant.MARGIN_TYPE_BASE));//基础消保
			shopInfoDO.setConfiguration("MARGIN_PRICE=".concat(pinjuMargin));
			shopShowInfoManager.updateShopBaseInfo(shopInfoDO); 
			SellerQualityDO sellerQualityDO = memberManager.getSellerQualityByMemberId(memberId);
			if (sellerQualityDO==null) { //兼容因cp_type is not null 约束造成的未插入记录问题,补插记录
				sellerQualityDO = new SellerQualityDO();
				if(!shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_C))){
					List<ShopBusinessInfoDO> bList = shopOpenManager.queryShopBusinessInfo(memberId);
					if(bList!=null && bList.size()>0){
						ShopBusinessInfoDO shopBusinessInfoDO = (ShopBusinessInfoDO)bList.get(0);
						sellerQualityDO.setCompanyName(shopBusinessInfoDO.getEnterpriseName());
					}
				}
				sellerQualityDO.setApproveStatus(shopInfoDO.getApproveStatus());
				//sellerQualityDO.setCategoryName();
				sellerQualityDO.setCategoryId(Long.valueOf(shopInfoDO.getShopCategory()));
				sellerQualityDO.setCpType(DirectPayConstant.MARGIN_TYPE_BASE);
				sellerQualityDO.setLevel(SellerQualityDO.LEVEL_D);
				//sellerQualityDO.setLocalId(localId);
				sellerQualityDO.setLocalName(shopInfoDO.getCity());
				sellerQualityDO.setMargin(Long.valueOf(pinjuMargin));
				sellerQualityDO.setMemberId(memberId);
				sellerQualityDO.setNickname(shopInfoDO.getNickname());
				//sellerQualityDO.setRefundRate(refundRate);
				//sellerQualityDO.setRefundSuccessRate(refundSuccessRate);
				//sellerQualityDO.setRightsRate(rightsRate);
				//sellerQualityDO.setRightsSuccessRate(rightsSuccessRate);
				sellerQualityDO.setSellerType(ShopConstant.SHOP_TYPE.get(Integer.valueOf(shopInfoDO.getSellerType())));//店铺类型需要加1
				sellerQualityDO.setShopId(shopInfoDO.getShopId());
				sellerQualityDO.setShopName(shopInfoDO.getName());
				sellerQualityDO.setGmtCreate(new Date());
				sellerQualityDO.setGmtModified(new Date());
				memberManager.initSellerQuality(sellerQualityDO);
			}else { //如果已经插入,则后补更新相关字段
				SellerQualityDO sellerQuality = new SellerQualityDO();
				sellerQuality.setMargin(Long.valueOf(pinjuMargin));
				sellerQuality.setCpType(DirectPayConstant.MARGIN_TYPE_BASE);
				memberManager.updateSellerQuality(memberId, sellerQuality);
			}
		} catch (Exception e) {
			log.error("Event=[MarginAOImpl#repairSellerQuality] 处理已开店成功后补插入或更新卖家资质及店铺信息时出错",e);
		}
	}
	
	@Override
	public Result unfreezeMargin(Long sellerId) {
		// TODO 调盛付通解冻接口,暂不实现
		return null;
	}
	
	@Override
	public Result receiveMargin(DirectPayReceiveParamDO directPayReceiveParamDO,boolean payState) {
		Result result = new ResultSupport();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			Long payOrderId = directPayReceiveParamDO.getOrderNo();
			DirectOrderDO directOrderDO = directManager.getDirectOrderDOById(payOrderId);
			if (directOrderDO == null) {//业务校验,订单是否存在
				result.setSuccess(false);
				result.setSubResultCode(MarginResultCode.DIRECTPAY_ORDER_NOT_EXITS);
				log.error("Event=[MarginAOImpl#receiveMargin] 订单不合法");
				return result;
			}
			/****** 处理卖家信息Begin ******/
			log.warn("begin seller info");
			MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(directOrderDO.getBuyerId());
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(directOrderDO.getBuyerId(), null);
			if (shopInfoDO==null) {
				log.warn("receiveMargin#shopInfoDO is null 无此店铺,非法数据！");
			}
			processSellerInfo(directPayReceiveParamDO,result,marginSellerDO,shopInfoDO);
			if (!result.isSuccess()) {
				log.warn("processSellerInfo result rollback");
				zizuTransactionManager.rollback(status);
				return result;
			}
			log.warn("end seller info");
			/****** 处理卖家信息End ******/
			
			/****** 处理品聚信息Begin ******/
			processPinjuInfo(directPayReceiveParamDO,shopInfoDO, result);
			if (!result.isSuccess()) {
				log.warn("processPinjuInfo result rollback");
				zizuTransactionManager.rollback(status);
				return result;
			}
			/****** 处理品聚信息End ******/
			zizuTransactionManager.commit(status);
		} catch (Exception e) {
			zizuTransactionManager.rollback(status);
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.DIRECTPAY_MARGIN_OPERATION_FAIL);
			log.error("Event=[MarginAOImpl#receiveMargin] 处理保证金后续信息失败",e);
		}
		return result;
	}

	/**
	 * Created on 2011-8-17 
	 * <p>Discription:[通知更新店铺状态]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void notifyShopUpdate(Long userId) {
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, null);
			if (shopInfoDO == null) {
				log.error("Event=[MarginAOImpl#notifyShopUpdate] userId="+ userId + "的店铺不存在!");
				return;
			}
			if (shopInfoDO.getApproveStatus()==ShopConstant.APPROVE_STATUS_YES){ //店铺状态正常,则不处理
				return;
			}
			//shopOpenManager.setShopIsOpenForMargin(userId);
		} catch (Exception e) {
			log.error("Event=[MarginAOImpl#notifyShopUpdate] 缴消保通知更新店铺状态失败", e);
		}
	}
	
	/**
	 * Created on 2011-8-17 
	 * <p>Discription:[处理品聚账户与账户明细]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @param marginSellerDO 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void processPinjuInfo(DirectPayReceiveParamDO directPayReceiveParamDO, ShopInfoDO shopInfoDO ,Result result){
		try {
			//更新品聚账户信息和插入品聚账户操作记录
			MarginPinJuDO marginPinJuDO = new MarginPinJuDO();
			List<MarginPinJuDO> list = marginManager.getMarginPinJuDO(); //返回LIST为防止将来有可能品聚有多账号问题
			if(list.size()==0){//第一次插品聚账户 
				marginPinJuDO.setCurBalAmount(directPayReceiveParamDO.getPayAmount());
				marginManager.insertMarginPinJuRecord(marginPinJuDO);
			}else{
				//非第一次则更新账户余额
				marginPinJuDO = list.get(0);
				marginPinJuDO.setCurBalAmount(marginPinJuDO.getCurBalAmount() + directPayReceiveParamDO.getPayAmount()); 
				int count = marginManager.updateMarginPinJuRecord(marginPinJuDO);//更新品聚账户,更新当前保证金金额
				if(count != 1){
					result.setSuccess(false);
					result.setSubResultCode(MarginResultCode.PAYMARGIN_UPDDATE_PINJU_FAIL);
					log.warn("Event=[MarginAOImpl#processPinjuInfo] 更新品聚账户信息失败");
					return;
				}
			}
			//插入品聚账户流水
			MarginPinjuOrderDO marginPinjuOrderDO = new MarginPinjuOrderDO();
			marginPinjuOrderDO.setPayOrderId(directPayReceiveParamDO.getOrderNo());
			marginPinjuOrderDO.setOperateType(DirectPayConstant.OPERATE_TYPE_ADD);
			marginPinjuOrderDO.setAmount(directPayReceiveParamDO.getPayAmount());
			marginPinjuOrderDO.setInvMemberId(shopInfoDO.getUserId());
			marginPinjuOrderDO.setInvMemberNick(shopInfoDO.getNickname());
			marginPinjuOrderDO.setInvMemberPayment("-"); //账户没有保存,无法获取,暂时不记
			marginManager.insertMarginPinJuOrderRecord(marginPinjuOrderDO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.MARGIIN_PROCESS_SELLER_INFO_FAIL);
			log.error("Event=[MarginAOImpl#processPinjuInfo] 处理品聚信息失败",e);
		}
	}

	/**
	 * Created on 2011-8-17 
	 * <p>Discription:[处理卖家账户及明细]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void processSellerInfo(DirectPayReceiveParamDO directPayReceiveParamDO,Result result,MarginSellerDO marginSellerDO,ShopInfoDO shopInfoDO){
		try {
			//判断卖家是否为第一次缴纳保证金
			if(marginSellerDO==null){ //没有缴纳记录,代表第一次缴
				//插入seller账户表
				marginSellerDO = new MarginSellerDO();
				marginSellerDO.setMemberId(shopInfoDO.getUserId());
				marginSellerDO.setMemberNick(shopInfoDO.getNickname());
				marginSellerDO.setCpType(DirectPayConstant.BIZ_TYPE_MARGIN);
				marginSellerDO.setCurrentMargin(directPayReceiveParamDO.getPayAmount()); 
				marginSellerDO.setCategoryId(Long.valueOf(shopInfoDO.getShopCategory()));
				marginSellerDO.setPinjuMargin(Long.valueOf(shopInfoDO.getShopInfoConfig("MARGIN_PRICE")));
				marginManager.insertMarginSellerRecord(marginSellerDO);
			}else{
				//非第一次缴纳，为补缴或多交
				Long curMargin = marginSellerDO.getCurrentMargin();
				curMargin += directPayReceiveParamDO.getPayAmount();
				//补缴保证金
				marginSellerDO.setCurrentMargin(curMargin);
				int count = marginManager.updateMarginSellerRecord(marginSellerDO);
				if(count != 1){
					result.setSuccess(false);
					result.setSubResultCode(MarginResultCode.PAYMARGIN_UPDDATE_SELLER_FAIL);
					log.warn("Event=[MarginAOImpl#processSellerInfo] 更新卖家账户信息失败");
					return;
				}
			}
			
			//插卖家流水表
			MarginSellerOrderDO marginSellerOrderDO = new MarginSellerOrderDO();
			marginSellerOrderDO.setMemberId(shopInfoDO.getUserId());
			marginSellerOrderDO.setMemberNick(shopInfoDO.getNickname());
			marginSellerOrderDO.setAmount(directPayReceiveParamDO.getPayAmount());
			marginSellerOrderDO.setOperateType(DirectPayConstant.OPERATE_TYPE_ADD);
			marginSellerOrderDO.setPayOrderId(directPayReceiveParamDO.getOrderNo());//orderNo实为PayOrderId
			marginSellerOrderDO.setInvMemberId(shopInfoDO.getUserId());
			marginSellerOrderDO.setInvMemberPayment("-");
			marginManager.insertMarginSellerOrderRecord(marginSellerOrderDO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(MarginResultCode.MARGIIN_PROCESS_PINJU_INFO_FAIL);
			log.error("Event=[MarginAOImpl#processSellerInfo] 处理卖家信息失败",e);
			
		}
	}

	@Override
	public Result queryMarginBalance(Long sellerId) {
		Result result = new ResultSupport();
		try {
			MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(sellerId);
			if(marginSellerDO == null){
				result.setSuccess(false);
				result.setResultCode(MarginResultCode.SELLER_MARGIN_NOT_EXIST);
				return result;
			}
			result.setModel("marginSellerDO",marginSellerDO);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[MarginAOImpl#queryMarginBalance] 查询保证金余额失败",e);
		}
		return result;
	}

	@Override
	public Result queryMarginRecorde(Long sellerId) {
		Result result = new ResultSupport();
		try {
			marginManager.getMarginSellerDOBySellerId(sellerId);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[MarginAOImpl#queryMarginRecorde] 查询保证金信息失败",e);
		}
		return result;
	}
	
	@Override
	public Result queryMarginSellerOrderDOs(MarginSellerOrderQuery marginSellerOrderQuery){
		Result result = new ResultSupport();
		try {
			List<MarginSellerOrderDO> marginSellerOrderDOs = marginManager.getMarginSellerOrderDOs(marginSellerOrderQuery);
			if(marginSellerOrderDOs == null || marginSellerOrderDOs.size() == 0){
				result.setSuccess(false);
				return result;
			}
			result.setModel("marginSellerOrderDOs",marginSellerOrderDOs);
		}catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[MarginAOImpl#queryMarginSellerOrderDOs] 查询卖家保证金交易流水异常",e);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[MarginAOImpl#queryMarginSellerOrderDOs] 查询卖家保证金交易流水失败",e);
		}
		return result;
	}
	
	public void setMarginManager(MarginManager marginManager) {
		this.marginManager = marginManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}


	public void setDirectManager(DirectManager directManager) {
		this.directManager = directManager;
	}

	public void setShopOpenManager(ShopOpenManager shopOpenManager) {
		this.shopOpenManager = shopOpenManager;
	}
	
	public void setZizuTransactionManager(
			DataSourceTransactionManager zizuTransactionManager) {
		this.zizuTransactionManager = zizuTransactionManager;
	}
	
	public void setCategoryMarginManager(CategoryMarginManager categoryMarginManager) {
		this.categoryMarginManager = categoryMarginManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

}
