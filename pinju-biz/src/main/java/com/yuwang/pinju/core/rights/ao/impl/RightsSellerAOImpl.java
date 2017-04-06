package com.yuwang.pinju.core.rights.ao.impl;

import java.util.Date;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.rights.ao.RightsSellerAO;
import com.yuwang.pinju.core.rights.manager.RightsManager;
import com.yuwang.pinju.core.rights.manager.RightsWorkOrderManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsLogisticsDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
public class RightsSellerAOImpl extends BaseAO implements RightsSellerAO  {
	
	
	private RightsManager rightsManager;
	
	private OrderBusinessManager orderBusinessManager;
	
	private OrderQueryManager orderQueryManager;
	
	private RightsWorkOrderManager rightsWorkOrderManager;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	private DataSourceTransactionManager zizuTransactionManager;
	
	private VouchQueryManager vouchQueryManager;
	
	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}

	public void setZizuTransactionManager(
			DataSourceTransactionManager zizuTransactionManager) {
		this.zizuTransactionManager = zizuTransactionManager;
	}
	
	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public void setRightsWorkOrderManager(
			RightsWorkOrderManager rightsWorkOrderManager) {
		this.rightsWorkOrderManager = rightsWorkOrderManager;
	}

	public void setRightsManager(RightsManager rightsManager) {
		this.rightsManager = rightsManager;
	}

	public void setOrderBusinessManager(OrderBusinessManager orderBusinessManager) {
		this.orderBusinessManager = orderBusinessManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	@Override
	public Result queryOrderLogisticsByOrderId(Long orderId) {
		Result result = new ResultSupport();
		try {
			OrderLogisticsDO orderLogisticsDO = orderBusinessManager.queryOrderLogisticsByOrderId(orderId);
			result.setModel("orderLogisticsDO",orderLogisticsDO);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("RightsSellerAOImpl#queryOrderLogisticsByOrderId:" + e);
		}
		return result;
	}

	@Override
	public Result getRightsDOById(Long id) {
		Result result = new ResultSupport();
		try {
			RightsDO rightsDO= rightsManager.getRightsDOById(id);
			result.setModel("rightsDO",rightsDO);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("RightsSellerAOImpl#getRightsDOById:" + e);
		}
		return result;
	}

	@Override
	public Result getOrderDOAndOrderItemDOAndVouchPayDOById(Long orderId,Long orderItemId) {
		Result result = new ResultSupport();
		try {
			//查询OrderDO
			OrderDO orderDO = orderQueryManager.getOrderDOById(orderId);
			result.setModel("orderDO", orderDO);
			//查询实付款VouchPayDO
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderId(orderId);
			result.setModel("vouchPayDO", vouchPayDO);
			//查询子订单
			OrderItemDO orderItemDO= orderQueryManager.getOrderItemDOById(orderItemId);
			result.setModel("orderItemDO",orderItemDO);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("RightsSellerAOImpl#getOrderDOAndOrderItemDOAndVouchPayDOById:fail" + e);
		}
		return result;
	}

	@Override
	public Result getRightsLogisticsDO(Long id, Long buyerId) {
		Result result = new ResultSupport();
		try {
			RightsLogisticsDO rightsLogisticsDO = new RightsLogisticsDO();
			rightsLogisticsDO.setVoucherId(id);
			rightsLogisticsDO.setBuyerId(buyerId);
			rightsLogisticsDO= rightsManager.getRightsLogisticsDO(rightsLogisticsDO);
			result.setModel("rightsLogisticsDO",rightsLogisticsDO);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("RightsSellerAOImpl#getRightsLogisticsDO:" + e);
		}
		return result;
	}

	@Override
	public Result addRightsWorkOrderDO(RightsDO rightsDO) {
		Result result = new ResultSupport();
		FinanceWorkOrderDO financeWorkOrderDO = new FinanceWorkOrderDO();
		financeWorkOrderDO.setBizId(rightsDO.getId());
		financeWorkOrderDO.setBuyerId(rightsDO.getBuyerId());
		financeWorkOrderDO.setBuyerNick(rightsDO.getBuyerNick());
		financeWorkOrderDO.setDeMargin(rightsDO.getBuyerApplyPrice());
		financeWorkOrderDO.setOperType(RightsConstant.OPER_TYPE_RIGHTS);
		financeWorkOrderDO.setRightsReason(rightsDO.getBuyerReason());
		financeWorkOrderDO.setOrderId(rightsDO.getOrderId());
		financeWorkOrderDO.setSellerId(rightsDO.getSellerId());
 		financeWorkOrderDO.setSellerNick(rightsDO.getSellerNick());
		financeWorkOrderDO.setStatus(RightsConstant.STATUS_HANDLE_NOT);
		financeWorkOrderDO.setBuyerBankAccount(rightsDO.getBuyerBankAccountName());
		financeWorkOrderDO.setBuyerBankCode(rightsDO.getBuyerBankAccountNo());
		financeWorkOrderDO.setBuyerBankOpen(rightsDO.getBuyerOpenBankName());
		financeWorkOrderDO.setBizType(RightsConstant.BIZ_TYPE_RIGHTS);//业务类型,维权
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(rightsDO.getSellerId(), null);
			if (shopInfoDO==null) {
				log.error("RightsSellerAOImpl#addRightsWorkOrderDO,生成财务处理工单时查询店铺信息出错！");
			}else {
				financeWorkOrderDO.setShopId(shopInfoDO.getId().longValue());
				financeWorkOrderDO.setShopName(shopInfoDO.getName());				
			}
			rightsWorkOrderManager.insertRightsWorkOrderDO(financeWorkOrderDO);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("RightsSellerAOImpl#addRightsWorkOrderDO:" + e);
		}
		return result;
	}
	
	@Override
	public Result updateRightsRecordAndAddRightsWorkOrder(RightsDO rightsDO) {
		Result result = new ResultSupport();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			rightsDO.setStateUpdateTime(new Date());
			rightsManager.updateRightsRecord(rightsDO);
			Result res = addRightsWorkOrderDO(rightsDO);
			if (!res.isSuccess()) {
				zizuTransactionManager.rollback(status);
				result.setSuccess(false);
				log.error("RightsSellerAOImpl#updateRightsRecordAndAddRightsWorkOrder fail!");
			}else {
				zizuTransactionManager.commit(status);				
			}
		} catch (ManagerException e) {
			zizuTransactionManager.rollback(status);
			result.setSuccess(false);
			log.error("RightsSellerAOImpl#updateRightsRecordAndAddRightsWorkOrder:" + e);
		}
		return result;
	}

	@Override
	public Result sellerRefuseRights(Long rightsId,String refuseReason) {
		Result result = new ResultSupport();
		try {
			RightsDO rightsDO = rightsManager.getRightsDOById(rightsId);
			if (rightsDO==null) {
				result.setSuccess(false);
				return result;
			}
			if (rightsDO.getState() ==RightsConstant.WAIT_SELLER_HANDLE) {
				rightsDO.setSellerProcDate(new Date());
				rightsDO.setSellerRefuseReason(refuseReason);
				rightsDO.setState(RightsConstant.SELLER_REFUSE);
				rightsDO.setStateUpdateTime(new Date());
				rightsManager.updateRightsRecord(rightsDO);
			}else {
				result.setModel("status", rightsDO.getState());
				result.setModel("isGoods", rightsDO.getBuyerRequire());
				result.setSuccess(false);
			}
			
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("RightsSellerAOImpl#sellerRefuseRights:" + e);
		}
		return result;
	}

	@Override
	public Result updateRightsLogisticsRecord(RightsDO rightsDO) {
		Result result = new ResultSupport();
		try {
			RightsLogisticsDO rightsLogisticsDO = new RightsLogisticsDO();
			rightsLogisticsDO.setVoucherId(rightsDO.getId());
			rightsLogisticsDO.setSellerId(rightsDO.getSellerId());
			//select
			rightsLogisticsDO = rightsManager.getRightsLogisticsDO(rightsLogisticsDO);
			//update
			rightsLogisticsDO.setSellerConfirmDate(new Date());
			int flag = rightsManager.updateRightsLogisticsRecord(rightsLogisticsDO);
			if (flag != 1) {
				result.setSuccess(false);
				log.error("RightsSellerAOImpl#updateRightsLogisticsRecord:flag:" + flag);
			}
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("RightsSellerAOImpl#updateRightsLogisticsRecord:" + e);
		}
		return result;
	}

}
