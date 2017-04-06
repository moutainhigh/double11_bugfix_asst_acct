package com.yuwang.pinju.core.rights.ao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.RightsResultCode;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.rights.ao.RightsAO;
import com.yuwang.pinju.core.rights.manager.RightsManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsMsgQuery;
import com.yuwang.pinju.domain.rights.RightsQuery;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**  
 * @Project: pinju-biz
 * @Description: 消保维权AO
 * @author 石兴 shixing@zba.com
 * @date 2011-6-29 上午10:43:23
 * @update 2011-6-29 上午10:43:23
 * @version V1.0  
 */
public class RightsAOImpl extends BaseAO implements RightsAO {

	private RightsManager rightsManager;
	
	private OrderQueryManager orderQueryManager;

	private OrderBusinessManager orderBusinessManager;
	
	private VouchQueryManager vouchQueryManager;

	@Override
	public Result getRightsDOs(RightsQuery rightsQuery) {
		Result result = new ResultSupport();
		try {
			List<RightsDO> rightsDOs = rightsManager.getApplyRightsDOs(rightsQuery);
			if (rightsDOs == null) {
				result.setSuccess(false);
			}
			result.setModel("rightsDO", rightsDOs);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RightsAOImpl#getApplyRightsDO] 获取维权记录失败",e);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[RightsAOImpl#getApplyRightsDO] 获取维权记录错误",e);
		}
		return result;
	}
	
	@Override
	public Result getRightsDO(RightsDO rightsDO) {
		Result result = new ResultSupport();
		try {
			rightsDO = rightsManager.getApplyRightsDO(rightsDO);
			if (rightsDO == null) {
				result.setSuccess(false);
				return result;
			}
			result.setModel("rightsDO", rightsDO);
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(RightsResultCode.OPERATE_FAILED);
			log.error("Event=[RightsAOImpl#getApplyRightsDO] 获取维权记录失败",e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(RightsResultCode.OPERATE_FAILED);
			log.error("Event=[RightsAOImpl#getApplyRightsDO] 获取维权记录错误",e);
		}
		return result;
	}

	@Override
	public Result insertRightsRecord(RightsDO rightsDO) {
		Result result = new ResultSupport();
		try {
			RightsDO rightsQueryDO = rightsManager.getApplyRightsDO(rightsDO);
			if (rightsQueryDO!=null) { // 即该订单已发起过维权
				result.setSuccess(false);
				result.setResultCode(RightsResultCode.OPERATE_RIGHTS_ALREADY);
				return result;
			}
			rightsManager.insertRightsRecord(rightsDO);
			result.setResultCode(RightsResultCode.OPERATE_SUCCESS);
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(RightsResultCode.OPERATE_FAILED);
			log.error("Event=[RightsAOImpl#insertRightsRecord] 生成维权记录失败",e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(RightsResultCode.OPERATE_FAILED);
			log.error("Event=[RightsAOImpl#insertRightsRecord] 生成维权记录错误",e);
		}
		return result;
	}

	@Override
	public Result updateRightsRecord(RightsDO rightsDO) {
		Result result = new ResultSupport();
		try {
			rightsManager.updateRightsRecord(rightsDO);
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(RightsResultCode.OPERATE_FAILED);
			log.error("Event=[RightsAOImpl#updateRightsRecord] 更新维权记录失败",e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(RightsResultCode.OPERATE_FAILED);
			log.error("Event=[RightsAOImpl#updateRightsRecord] 更新维权记录错误",e);
		}
		return result;
	}

	@Override
	public Result sendMessage(RightsMessageDO rightsMessageDO) {
		Result result = new ResultSupport();
		try {
			rightsManager.insertMessageRecord(rightsMessageDO);
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(RightsResultCode.OPERATE_FAILED);
			log.error("Event=[RightsAOImpl#sendMessage] 用户提交留言失败",e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(RightsResultCode.OPERATE_FAILED);
			log.error("Event=[RightsAOImpl#sendMessage] 用户提交留言错误",e);
		}
		return result;
	}

	@Override
	public Result getRightsMsgDOs(RightsMsgQuery rightsMsgQuery) {
		Result result = new ResultSupport();
		try {
			List<RightsMessageDO> rightsMsgDOs = rightsManager.getRightsMessageDOs(rightsMsgQuery);
			if (rightsMsgDOs == null) {
				result.setSuccess(false);
			}
			result.setModel("rightsMsgDOs", rightsMsgDOs);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RightsAOImpl#getRightsMsgDOs] 获取维权留言记录列表失败",e);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[RightsAOImpl#getRightsMsgDOs] 获取维权留言记录列表错误",e);
		}
		return result;
	}

	@Override
	public Result getOrderDOAndOrderItemDOAndVouchPayDOAndOrderLogisticsDOById(RightsDO rightsDO){
		Result result = new ResultSupport();
		try {
			//查询OrderLogisticsDO
			OrderLogisticsDO orderLogisticsDO = orderBusinessManager.queryOrderLogisticsByOrderId(rightsDO.getOrderId());
			result.setModel("orderLogisticsDO",orderLogisticsDO);
			//查询OrderDO
			OrderDO orderDO = orderQueryManager.getOrderDOById(rightsDO.getOrderId());
			if(orderDO == null){
				result.setSuccess(false);
				result.setResultCode("查询订单失败！");
				return result;
			}
			result.setModel("orderDO", orderDO);
			//查询实付款VouchPayDO
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderId(rightsDO.getOrderId());
			if(vouchPayDO == null){
				result.setSuccess(false);
				result.setResultCode("查询支付订单失败！");
				return result;
			}
			result.setModel("vouchPayDO", vouchPayDO);
			//查询子订单OrderItemDO
			OrderItemDO orderItemDO = orderQueryManager.getOrderItemDOById(rightsDO.getSubOrderId());
			if(orderItemDO == null){
				result.setSuccess(false);
				result.setResultCode("该维权子订单不存在！");
				return result;
			}
			result.setModel("orderItemDO",orderItemDO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode("系统繁忙，请稍候再试！");
			log.error("RightsSellerAOImpl#getOrderDOAndOrderItemDOAndVouchPayDOAndOrderLogisticsDOById:fail" + e);
		}
		return result;
	}
	
	public void setRightsManager(RightsManager rightsManager) {
		this.rightsManager = rightsManager;
	}
	
	public void setOrderQueryManager(OrderQueryManager orderQueryManager){
		this.orderQueryManager = orderQueryManager;
	}

	public void setOrderBusinessManager(OrderBusinessManager orderBusinessManager){
		this.orderBusinessManager = orderBusinessManager;
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager){
		this.vouchQueryManager = vouchQueryManager;
	}

}
