package com.yuwang.pinju.core.order.dao;

import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;

/**
 * Created on 2011-7-30
 * 
 * @see <p>
 *      Discription: 订单更新相关
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderUpDdateDAO {

	/**
	 * 
	 * Created on 2011-7-14
	 * <p>
	 * Discription: 更新主订单
	 * </p>
	 * 
	 * @param map
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateOrder(OrderDO orderDO) throws DaoException;

	/**
	 * Created on 2011-7-14
	 * <p>
	 * 更新子订单
	 * </p>
	 * 
	 * @param orderItemId
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int upOrderItem(OrderItemDO orderItemDO) throws DaoException;
	
	/**
	 * 
	 * <p>Description: 更新所有子订单状态</p>
	 * @param orderItemDO
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-26
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int upAllOrderItemState(OrderItemDO orderItemDO) throws DaoException;
	
	/**
	 * 
	 * Created on 2011-7-14
	 * 
	 * @see <p>
	 *      Discription:更新订单物流记录
	 *      </p>
	 * @param orderLogisticsDO
	 * @return 返回更新的记录数
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int upOrderLogistics(OrderLogisticsDO orderLogisticsDO)
			throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-13
	 * <p>Discription: 更新买家评价状态</p>
	 * @param orderDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateOrderBuyerRate(OrderDO orderDO)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-13
	 * <p>Discription: 支付成功后根据支付的子订单换算最新的分销金额,更新分销表</p>
	 * @param orderItemId
	 * @param channelAmount
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int upChannelAmount(OrderChannelDO orderChannelDO)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-13
	 * <p>Discription: 更新子订单分账金额</p>
	 * @param orderItemDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateOrderItemDealAmount(OrderItemDO orderItemDO)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-19
	 * <p>Discription: 更新订单收货确认时间</p>
	 * @param orderDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateOrderConfirmTime(OrderDO orderDO)throws DaoException;
	
	/**
	 * 
	 * <p>Description: 更新订单退款状态,订单状态;价格</p>
	 * @param orderItemDO
	 * @return
	 * @throws DaoException
	 *@author:[杜成]
	 * @version 1.0
	 * @create:2011-10-26
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int upRefundOrderItem(OrderItemDO orderItemDO) throws DaoException;
	
	/**
	 * 
	 * Created on 2011-11-4
	 * <p>Discription: 更新主订单标记字段</p>
	 * @param map
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateOrderAttribute(Map<String,Long[]> map) throws DaoException;
}
