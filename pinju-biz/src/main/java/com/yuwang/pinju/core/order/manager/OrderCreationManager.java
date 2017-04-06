package com.yuwang.pinju.core.order.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.OrderTradeAdDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * Created on 2011-7-30
 * @see
 * <p>Discription: 订单创建</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderCreationManager {

	/**
	 * Created on 2011-6-4
	 * <p>
	 * 	生成父子订单(1:1*N)
	 * </p>
	 * @param buyerId
	 *            买家编号
	 * @param sellerId
	 *            卖家编号
	 * @param buyerNick
	 *            买家昵称
	 * @param sellerNick
	 *            卖家昵称
	 * @param orderItemList 子订单参数集合
	 * @param buyIp
	 *            买家ip
	 * @param buyerMeMo
	 *            买家备注
	 * @return key 主订单编号,value (子订单编号:商品编号)
	 * 		   失败返回null
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Map<Long,List<String>> creationOrder(final OrderDO orderDO, final List<OrderItemDO> orderItemList,
			final OrderLogisticsDO orderLogisticsDO,final VouchPayDO vouchPayDO) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-8-1
	 * <p>Discription: 向分销商中间表插入数据 </p>
	 * @param orderChannelDO
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean insertOrderChannelDO(OrderChannelDO orderChannelDO)throws ManagerException;
	
	  
	/**
	 * @Description: 向订单广告表中插入数据
	 * @author [贺泳]
	 * @date 2011-9-8 上午09:01:31
	 * @version 1.0
	 * @param orderTradeAdDO: 订单广告表对象
	 * @return
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean insertTradeAd(OrderTradeAdDO orderTradeAdDO)throws ManagerException;
}

