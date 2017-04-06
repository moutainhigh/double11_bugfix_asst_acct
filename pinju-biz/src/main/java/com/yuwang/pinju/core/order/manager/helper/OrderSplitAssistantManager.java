package com.yuwang.pinju.core.order.manager.helper;



import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.trade.TenSubAccountDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * Created on 2011-9-19
 * @see
 * <p>Discription: 订单分账实现</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderSplitAssistantManager {


	 
	 /**
	  * 
	  * Created on 2011-9-20
	  * <p>Discription:[分账处理] </p>
	  * @param payOrderId 内部支付订单编号
	  * @param list
	  * @return
	  * @author:[杜成]
	  * @version 1.0
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	void orderSplitProcess(String payOrderId) throws ManagerException;
	 
	/**
	 * 
	 * Created on 2011-9-21
	 * <p>Discription: 获取订单分账对象</p>
	 * @param orderId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	TenSubAccountDO getTenSubAccountDO(Long orderId)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-9-29
	 * <p>Discription: [生成支付表对象,订单价格不排除被修改这里不包含分账]</p>
	 * @param orderDO
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public VouchPayDO creationVouchPayDO(OrderDO orderDO) throws ManagerException;
}

