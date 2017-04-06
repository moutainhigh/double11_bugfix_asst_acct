package com.yuwang.pinju.core.trade.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.trade.DirectOrderDO;
import com.yuwang.pinju.domain.trade.DirectPayDO;
import com.yuwang.pinju.domain.trade.DirectPayRevLogDO;
import com.yuwang.pinju.domain.trade.DirectPaySendLogDO;

/**  
 * @Project: pinju-biz
 * @Description: 即时到账交易Manager
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午04:12:11
 * @update 2011-8-12 下午04:12:11
 * @version V1.0  
 */
public interface DirectManager {
	
	/**
	 * Created on 2011-8-15 
	 * <p>Discription: [担保交易订单走即时到账接口]</p>
	 * @param 
	 * @return
	 * @author:[杜成]
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void insertDirectOrderRecord(DirectPayDO directPayDO,DirectPaySendLogDO directSendLogDO)throws ManagerException;
	/**
	 * Created on 2011-8-15 
	 * <p>Discription:[根据订单ID获取OrderDO]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public DirectOrderDO getDirectOrderDOById(Long orderId) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-9-9
	 * <p>Discription: [根据支付ID获取支付对象]</p>
	 * @param payOrderId
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public DirectPayDO getDirectPayDOById(Long payOrderId) throws ManagerException;
	
	
	/**
	 * Created on 2011-9-26
	 * <p>Discription: 根据 订单ID，支付状态获得支付信息</p>
	 * @param directOrderDO
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public DirectPayDO getDirectPayDO(DirectPayDO directPayDO) throws ManagerException;
	
	
	/**
	 * Created on 2011-8-15 
	 * <p>Discription:[获取订单ID]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long getOrderId() throws ManagerException;
	
	/**
	 * Created on 2011-8-15 
	 * <p>Discription:[获取支付订单号,和编号共用一个序列]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Long getPayOrderId() throws ManagerException;	
	
	/**
	 * Create on: 2011-8-12上午11:20:36
	 * <p>Discription:[插入即时到帐订单记录]</p>
	 * @param: DirectOrderDO
	 * @return: Long 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void insertDirectOrderRecord(DirectOrderDO directOrderDO,DirectPayDO directPayDO,DirectPaySendLogDO marginSendLogDO) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-10-11
	 * <p>Discription: 即时到账回传日志</p>
	 * @param directPayRevLogDO
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void insertDirectPayRevLog(DirectPayRevLogDO directPayRevLogDO)throws ManagerException;
	
	/**
	 * 
	 * @Project:pinju-biz
	 * <p>Discription:[盛付通回调后：插入接受日志，卖家账户，卖家流水，更新业务订单，支付订单，品聚账户，品聚流水]</p>
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-15
	 * @update:2011-8-15
	 * @param directPayReceiveParamDO返回参数 payState 支付状态 
	 * @throws ManagerException
	 */
	public boolean updateDirectReceiveOrderRecord(DirectOrderDO directOrderDO,DirectPayDO directPayDO,DirectPayRevLogDO directPayRevLogDO) throws ManagerException;

	
	
	/**
	 * 
	 * @Project:pinju-biz
	 * <p>Discription:[担保交易即时到帐盛付通回调后：插入接受日志，卖家账户，卖家流水，支付订单，品聚账户，品聚流水]</p>
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-15
	 * @update:2011-8-15
	 * @param directPayReceiveParamDO返回参数 payState 支付状态 
	 * @throws ManagerException
	 */
	public boolean updateDirectReceiveOrderRecord(OrderDO orderDO,DirectPayDO directPayDO,DirectPayRevLogDO directPayRevLogDO) throws ManagerException;
	
}
