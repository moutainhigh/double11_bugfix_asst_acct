package com.yuwang.pinju.core.order.ao.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.ao.HessianOrderAO;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.helper.OrderSplitAssistantManager;
import com.yuwang.pinju.core.trade.ao.TenSubAccountAO;
import com.yuwang.pinju.domain.trade.TenSubAccountDO;



/**
 * Created on 2011-7-22
 * @see
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class HessianOrderAOImpl implements HessianOrderAO {

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private OrderSplitAssistantManager orderSplitAssistantManager;
	
	private TenSubAccountAO tenSubAccountAO;
	
	private OrderQueryManager orderQueryManager;
	
	@Override
	public void confirmReceipt() {

		List<TenSubAccountDO> list = new ArrayList<TenSubAccountDO>();
		List<Long> idlist = null;
		try {
			int[] flag = { 0, 0 };
			flag[0] = orderQueryManager.queryOrderConfirmNum();
			log.debug("orderConfirm 开始执行,等待更新记录总数:" + flag[0]);
			if (flag[0] == 0){
				return ;
			}
			idlist = orderQueryManager.queryOrderConfirm(0, NUM);
			for(Long id : idlist){
				TenSubAccountDO tenSubAccountDO = orderSplitAssistantManager.getTenSubAccountDO(id);
				list.add(tenSubAccountDO);
			}
			tenSubAccountAO.createSubAccountParam(list);
			
		}catch (ManagerException e) {
			log.error("Event=[HessianOrderAOImpl#confirmReceipt] 定时任务确认收货出错:" + e);
		}catch (Exception e) {
			log.error("Event=[HessianOrderAOImpl#confirmReceipt] 定时任务确认收货出错:" + e);
		}
		list = null;
	}
	/**
	 * Created on 2011-6-20
	 * @param sum 总数
	 * @return 获取批次数
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected int getBatch(int sum){
		int temp = sum / this.getNUM();
		if(sum % this.getNUM() != 0)
			temp ++;
		return temp;
	}
	
	/**
	 * 每次更新,查询记录数
	 */
	protected final int NUM = 100;
	
	public void setOrderSplitAssistantManager(
			OrderSplitAssistantManager orderSplitAssistantManager) {
		this.orderSplitAssistantManager = orderSplitAssistantManager;
	}

	public void setTenSubAccountAO(TenSubAccountAO tenSubAccountAO) {
		this.tenSubAccountAO = tenSubAccountAO;
	}
	public int getNUM() {
		return NUM;
	}
	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

}

