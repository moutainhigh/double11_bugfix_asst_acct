package com.yuwang.pinju.core.shop.ao.impl;

import java.util.List;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.shop.ao.ShopOpenAO;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopFlowInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;

/**
 * 开店
 * @author xueqi
 *
 * @since 2011-7-4
 */
public class ShopOpenAoImpl extends BaseAO implements ShopOpenAO {
	
	private ShopOpenManager shopOpenManager;
	
	private MemberManager memberManager;
	
	private MemberAO memberAO;

	public MemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public ShopOpenManager getShopOpenManager() {
		return shopOpenManager;
	}

	public void setShopOpenManager(ShopOpenManager shopOpenManager) {
		this.shopOpenManager = shopOpenManager;
	}
	
	/**
	 * 验证财付通
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public Result checkTenpay(Long userId) {
		Result result = memberAO.isSignPayAgreement(userId);
		return result;
	}
	
	
	/**
	 * 验证是否已经同意服务协议
	 * 
	 * @param userId
	 * @return ture:已经同意 false: 尚未同意
	 */
	@Override
	public boolean checkIsAgreement(ShopOpenFlowDO shopOpenFlowDO) {
		if(shopOpenFlowDO != null && shopOpenFlowDO.getIsAgreement().equals(ShopConstant.IS_AGREEMENT_TRUE)){
			return true;
		}
		return false;
	}
	
	/**
	 * 保存店铺信息
	 * 
	 * @param sellerType
	 * @param shopInfo
	 * @return 保存的返回结果
	 */
	@Override
	public Object saveShopInfo(Integer sellerType, Object shopInfo) {
		try {
			return shopOpenManager.saveShopInfo(sellerType, shopInfo);
		} catch (ManagerException e) {
			log.error("保存店铺信息出错",e);
		}
		return null;
	}
	
	/**
	 * 是否已经填写个人基本信息
	 * @param shopOpenFlowDO
	 * @return true:已填 false 未填
	 */
	@Override
	public boolean checkIsFillInfo(ShopOpenFlowDO shopOpenFlowDO) {
		int isFillInfo = shopOpenFlowDO.getIsFillInfo();
		/**
		 * C店判断是否为1
		 */
		if(shopOpenFlowDO.getSellerType().equals(ShopConstant.SELLER_TYPE_C)){
			if (isFillInfo == ShopConstant.IS_FILL_SHOP_INFO_STEP1.intValue()) {
				return true;
			}
		/**
		 * B店判断是否为4
		 */
		}else{
			if (isFillInfo == ShopConstant.IS_FILL_SHOP_INFO_STEP4.intValue()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 验证是否已经审核
	 * @param shopOpenFlowDO
	 * @return
	 */
	@Override
	public boolean checkAudit(ShopOpenFlowDO shopOpenFlowDO) {
		Integer auditStatus = shopOpenFlowDO.getAuditStatus();
		if (auditStatus.equals(ShopConstant.AUDIT_STATUS_PASS)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 保存开店流程信息
	 * @param shopOpenFlowDO
	 * @return 保存的返回结果
	 */
	@Override
	public Object saveShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO){
		try {
			return shopOpenManager.saveShopOpenFlow(shopOpenFlowDO);
		} catch (ManagerException e) {
			log.error("保存开店流程信息出错",e);
		}
		return null;
	}
	
	/**
	 * 更新开店流程信息
	 * @param shopOpenFlowDO
	 * @return 更新的返回结果
	 */
	@Override
	public Object updateShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO){
		try {
			return shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
		} catch (ManagerException e) {
			log.error("更新开店流程信息出错",e);
		}
		return null;
	}
	
	/**
	 * 开店
	 * @param shopOpenFlowDO
	 * @param tenPayError
	 * @return
	 */
	@Override
	public Result shopOpen(ShopOpenFlowDO shopOpenFlowDO, Long userId, Integer sellerType) {
		
		//财付通
		Result result = checkTenpay(userId);
		//result.setSuccess(true);
		if (!result.isSuccess()) {
			result.setSubResultCode(ShopConstant.CHECK_TENPAY);
			return result;
		}
		
		result = new ResultSupport();
		//协议
		if (!checkIsAgreement(shopOpenFlowDO)) {
			result.setSubResultCode(ShopConstant.AGREEMENT);
			return result;
		}
		
		//信息填写
		if (!checkIsFillInfo(shopOpenFlowDO)) {
			if (sellerType.equals(ShopConstant.SELLER_TYPE_C)) {
				result.setSubResultCode(ShopConstant.FILL_SHOP_INFO_C);
				return result;
			}else{
				if(shopOpenFlowDO.getIsFillInfo().equals(ShopConstant.IS_FILL_SHOP_INFO_NO)){
					result.setSubResultCode(ShopConstant.FILL_SHOP_INFO_B_SETP1);
					return result;
				}else if(shopOpenFlowDO.getIsFillInfo().equals(ShopConstant.IS_FILL_SHOP_INFO_STEP1)){
					result.setSubResultCode(ShopConstant.FILL_SHOP_INFO_B_SETP2);
					return result;
				}else if(shopOpenFlowDO.getIsFillInfo().equals(ShopConstant.IS_FILL_SHOP_INFO_STEP2)){
					result.setSubResultCode(ShopConstant.FILL_SHOP_INFO_B_SETP3);
					return result;
				}else if(shopOpenFlowDO.getIsFillInfo().equals(ShopConstant.IS_FILL_SHOP_INFO_STEP3)){
					result.setSubResultCode(ShopConstant.FILL_SHOP_INFO_B_SETP4);
					return result;
				}
				
			}
		}
		
		//审核是否通过
		if(!checkAudit(shopOpenFlowDO)){
			result.setSubResultCode(ShopConstant.SHOP_OPEN_BEGIN);
			return result;
		}
		
		//保证金
		result.setSubResultCode(ShopConstant.EXCHANGE_MARGIN);
		return result;
	}
	
	/**
	 * 根据用户获取B店信息
	 * @param userId
	 * @return
	 */
	@Override
	public List<ShopBusinessInfoDO> queryShopBusinessInfo(Long userId)  {
		try {
			return shopOpenManager.queryShopBusinessInfo(userId);
		} catch (ManagerException e) {
			log.error("根据用户获取B店信息",e);
		}
		return null;
	}
	
	/**
	 * 根据用户获取C店信息
	 * @param userId
	 * @return
	 */
	@Override
	public List<ShopCustomerInfoDO> queryShopCustomerInfo(Long userId) {
		try {
			return shopOpenManager.queryShopCustomerInfo(userId);
		} catch (ManagerException e) {
			log.error("根据用户获取C店信息",e);
		}
		return null;
	}
	
	/**
	 * 更新B店信息
	 * @param userId
	 * @param shopBusinessInfoDO
	 * @return
	 */
	@Override
	public Object updateShopBusinessInfo(Long userId,
			ShopBusinessInfoDO shopBusinessInfoDO)  {
		try {
			return shopOpenManager.updateShopBusinessInfo(shopBusinessInfoDO);
		} catch (ManagerException e) {
			log.error("更新B店信息",e);
		}
		return null;
	}
	
	/**
	 * 更新C店信息
	 * @param userId
	 * @param shopCustomerInfoDO
	 * @return
	 */
	@Override
	public Object updateShopCustomerInfo(Long userId,
			ShopCustomerInfoDO shopCustomerInfoDO)  {
		try {
			return shopOpenManager.updateShopCustomerInfo(shopCustomerInfoDO);
		} catch (ManagerException e) {
			log.error("更新C店信息",e);
		}
		return null;
	}
	
	/**
	 * 根据用户获取开店流程信息
	 * @param userId
	 * @return
	 */
	@Override
	public List<ShopOpenFlowDO> queryShopOpenFlow(Long userId) {
		try {
			return shopOpenManager.queryShopOpenFlow(userId);
		} catch (ManagerException e) {
			log.error("根据用户获取开店流程信息",e);
		}
		return null;
	}

	/**
	 * 查询店铺名称是否存在
	 * 
	 * @param name
	 * @return
	 * @throws ManagerException
	 */
	public boolean queryShopInfosByName(String name,Long userId){
		try {
			return shopOpenManager.queryShopInfosByName(name,userId);
		} catch (ManagerException e) {
			log.error("查询店铺名称是否存在",e);
		}
		return false;
	}
	
	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	/**
	 * 获取i小铺的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopIshopInfoDO> queryShopIShopnfo(Long userId){
		try {
			return shopOpenManager.queryShopIShopnfo(userId);
		} catch (ManagerException e) {
			log.error("获取B的店铺信息出错",e);
		}
		return null;
	}
	
	/**
	 * 签订同意服务协议【2.0版本新增】
	 * 
	 * @param userId
	 * @return
	 */
	public boolean agreement(Integer sellerType,ShopOpenFlowDO shopOpenFlowDO,ShopInfoDO shopInfoDO){
		try {
			 shopOpenManager.saveShopOpenFlow(shopOpenFlowDO);
		} catch (ManagerException e) {
			log.error("初始化店铺开店流程错误",e);
			return false;
		}
		try {
			 shopOpenManager.saveShopInfo(sellerType,shopInfoDO);
		} catch (ManagerException e) {
			log.error("初始化店铺开店流程错误",e);
			return false;
		}
		return true;
	}
	/**
	 * 获取流程DO -- 2.0新
	 * @param userId
	 * @return 流程DO
	 */
	@Override
	public ShopFlowInfoDO queryFlowInfo(Long userId) {
		ShopFlowInfoDO shopFlowInfoDO = new ShopFlowInfoDO();
		try {
			if(userId == null){
				return null;
			}
			//店铺流程表信息
			List<ShopOpenFlowDO> shopOpenFlowDOList  = shopOpenManager.queryShopOpenFlow(userId);
			//验证是否已绑定和已签约信息
			Result result = memberAO.isSignPayAgreement(userId);
			//拼装ShopFlowInfoDO
			if(shopOpenFlowDOList != null && shopOpenFlowDOList.size() > 0){
				ShopOpenFlowDO shopOpenFlowDO = shopOpenFlowDOList.get(0);
				//审核状态
				shopFlowInfoDO.setAuditStatus(shopOpenFlowDO.getAuditStatus());
				//开店协议确认状态
				shopFlowInfoDO.setIsAgreement(shopOpenFlowDO.getIsAgreement());
				//店铺类型
				shopFlowInfoDO.setSellerType(String.valueOf(shopOpenFlowDO.getSellerType()));
				shopFlowInfoDO.setUserId(userId);
				//资料填写情况
				String fillInfo = shopOpenFlowDO.getIsFillInfo().toString();
				
				//默认设置资料填写完成状态为完成
				shopFlowInfoDO.setIsFillComplete(ShopConstant.IS_FILL_COMPLETE_COMPLETE);
				
				//如果第一步已填写 则设置资料填写情况步骤1已填写 否则设置资料填写情况步骤1未写并且资料填写完成状态为未完成
				if(fillInfo.indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP1)) != -1){
					shopFlowInfoDO.setIsFillStep1(ShopConstant.IS_FILL_SHOP_INFO_1);
				}else{
					shopFlowInfoDO.setIsFillStep1(ShopConstant.IS_FILL_SHOP_INFO_0);
					shopFlowInfoDO.setIsFillComplete(ShopConstant.IS_FILL_COMPLETE_NOT_COMPLETE);
				}
				//如果第一步已填写 则设置资料填写情况步骤2已填写 否则设置资料填写情况步骤2未写并且资料填写完成状态为未完成
				if(fillInfo.indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP2)) != -1){
					shopFlowInfoDO.setIsFillStep2(ShopConstant.IS_FILL_SHOP_INFO_1);
				}else{
					shopFlowInfoDO.setIsFillStep2(ShopConstant.IS_FILL_SHOP_INFO_0);
					shopFlowInfoDO.setIsFillComplete(ShopConstant.IS_FILL_COMPLETE_NOT_COMPLETE);
				}
				
				//如果资料填写情况未0 则设置资料填写完成状态为未开始
				if(shopOpenFlowDO.getIsFillInfo().equals(ShopConstant.IS_FILL_SHOP_INFO_0)){
					shopFlowInfoDO.setIsFillComplete(ShopConstant.IS_ACCOUNT_SET_NOT_BEGIN);
				}
				
				//如果第一步已填写 则设置资料填写情况步骤2已填写 否则设置资料填写情况步骤2未写并且资料填写完成状态为未完成
				if(shopFlowInfoDO.getSellerType().equals(ShopConstant.SELLER_TYPE_B.toString()) || shopFlowInfoDO.getSellerType().equals(ShopConstant.SELLER_TYPE_B2.toString())){
					if(fillInfo.indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP3)) != -1){
						shopFlowInfoDO.setIsFillStep3(ShopConstant.IS_FILL_SHOP_INFO_1);
					}else{
						shopFlowInfoDO.setIsFillStep3(ShopConstant.IS_FILL_SHOP_INFO_0);
						shopFlowInfoDO.setIsFillComplete(ShopConstant.IS_FILL_COMPLETE_NOT_COMPLETE);
					}
				}
				//如果审核状态为通过 则设置可以缴纳保证金 否则设置不能缴纳
				if(shopOpenFlowDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)){
					shopFlowInfoDO.setCanPayMargin(ShopConstant.CAN_PAY_MARGIN_1);
				}else{
					shopFlowInfoDO.setCanPayMargin(ShopConstant.CAN_PAY_MARGIN_0);
				}
				
				//如果审核状态为未提交 则设置可以更改店铺类型 否则设置为不可更改店铺类型 
				if(shopOpenFlowDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_NOT_APPLY)){
					shopFlowInfoDO.setCanChangeShop(ShopConstant.CAN_CHANGE_SHOP_1);
				}else{
					shopFlowInfoDO.setCanChangeShop(ShopConstant.CAN_CHANGE_SHOP_0);
				}
			}
			
			//如果绑定签约结果集为成功 则设置绑定已完成 签约已完成 账户设定已完成
			if(result.isSuccess()){
				shopFlowInfoDO.setTenpayBind(ShopConstant.TENPAYBIND_1);
				shopFlowInfoDO.setTenpaySign(ShopConstant.TENPAYSING_1);
				shopFlowInfoDO.setIsAccountSet(ShopConstant.IS_ACCOUNT_SET_COMPLETE);
			}
			//如果绑定签约结果集为没有绑定签约财付通账号 则设置绑定未完成 签约未完成 账户设定未开始
			if(result.getResultCode() != null && result.getResultCode().equals(MemberResultConstant.RESULT_MEMBERID_FAIL)){
				shopFlowInfoDO.setTenpayBind(ShopConstant.TENPAYBIND_0);
				shopFlowInfoDO.setTenpaySign(ShopConstant.TENPAYSING_0);
				shopFlowInfoDO.setIsAccountSet(ShopConstant.IS_ACCOUNT_SET_NOT_BEGIN);
			}
			//如果绑定签约结果集为没有绑定财付通账号 则设置绑定未完成 签约已完成 账户设定未完成
			if(result.getResultCode() != null && result.getResultCode().equals(MemberResultConstant.RESULT_SIGN_PAY_BIND_NOT)){
				shopFlowInfoDO.setTenpayBind(ShopConstant.TENPAYBIND_0);
				shopFlowInfoDO.setTenpaySign(ShopConstant.TENPAYSING_1);
				shopFlowInfoDO.setIsAccountSet(ShopConstant.IS_ACCOUNT_SET_NOT_COMPLETE);
			}
			//如果绑定签约结果集为没有签约财付通账号 则设置绑定已完成 签约未完成 账户设定未完成
			if(result.getResultCode() != null && result.getResultCode().equals(MemberResultConstant.RESULT_SIGN_PAY_AGREEMENT_NOT)){
				shopFlowInfoDO.setTenpayBind(ShopConstant.TENPAYBIND_1);
				shopFlowInfoDO.setTenpaySign(ShopConstant.TENPAYSING_0);
				shopFlowInfoDO.setIsAccountSet(ShopConstant.IS_ACCOUNT_SET_NOT_COMPLETE);
			}
			return shopFlowInfoDO;
		} catch (ManagerException e) {
			log.error("获取B的店铺信息出错",e);
		}
		return shopFlowInfoDO;
	}
	
	
	/**
	 * 删除C店信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopCustomerInfo(Long userId)  {
		try {
			 shopOpenManager.deleteShopCustomerInfo(userId);
			 return true;
		} catch (ManagerException e) {
			log.error("删除C店信息",e);
			return false;
		}
	}
	
	/**
	 * 删除B店信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopBusinessInfo(Long userId)  {
		try {
		 shopOpenManager.deleteShopBusinessInfo(userId);
		 return true;
		} catch (ManagerException e) {
			log.error("删除B店信息",e);
			return false;
		}
	}
	
	/***
	 * 删除i小铺信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopIShopInfo(Long userId){
		try {
			 shopOpenManager.deleteShopIShopInfo(userId);
			 return true;
			} catch (ManagerException e) {
				log.error(" 删除i小铺信息",e);
				return false;
			}
	}
	
	/***
	 * 删除店铺基本信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopInfo(Long userId){
		try {
			 shopOpenManager.deleteShopInfo(userId);
			 return true;
			} catch (ManagerException e) {
				log.error("删除店铺基本信息",e);
				return false;
			}
	}
	
	/***
	 * 删除开店流程信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public boolean deleteShopOpenFlow(Long userId){
		try {
			 shopOpenManager.deleteShopOpenFlow(userId);
			 return true;
			} catch (ManagerException e) {
				log.error("删除开店流程信息",e);
				return false;
			}
		
	}

}
