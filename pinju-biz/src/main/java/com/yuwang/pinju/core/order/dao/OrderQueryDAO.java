package com.yuwang.pinju.core.order.dao;


import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.QueryDistributeOrder;
import com.yuwang.pinju.domain.order.query.QueryOrder;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.order.query.QueryVO;

/**
 * Created on 2011-7-30
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderQueryDAO {
	

	
	/**
     * Created on 2011-6-11
	 * <p>查询符合条件记录总数</p>
	 * @param queryOrder
	 * @return 符合条件的记录总数
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long queryOrderNum(QueryOrder queryOrder) throws DaoException;
	
	
	
	/**
	 * Created on 2011-6-11
	 * <p>查询符合条件订单分页记录</p>
	 * @param queryOrder
	 * @return List<OrderDO>
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> queryOrder(QueryOrder queryOrder)throws DaoException;
	
	/**
	 * Created on 2011-6-11
	 * <p>查询符合条件的主订单号对应实体</p>
	 * @param orderId
	 * @return OrderDO
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public OrderDO queryOrder(Long orderId) throws DaoException;
	
	/**
	 * 
	 * Created on 2011-8-1
	 * <p>Discription:查询主订单对应的所有子订单 </p>
	 * @param orderId 
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderItemDO> queryOrderItemList(Long orderId) throws DaoException;
	
	/**
	 * Created on 2011-7-30
	 * <p>查询符合条件的子订单</p>
	 * @param orderItemId
	 * @return
	 * @throws DaoException
	 * @author:
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public OrderItemDO queryOrderItemDO(Long orderItemId)throws DaoException;

	/**
	 * 
	 * Created on 2011-7-26
	 * <p>Discription: 子订单分页查询</p>
	 * @param queryOrderItemDO
	 * @return 
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderItemDO> queryOrderItemList(QueryOrderItem queryOrderItem)throws DaoException;
	/**
	 * 
	 * Created on 2011-7-27
	 * <p>Discription: 查询符合条件的订单总数</p>
	 * @param queryOrderItemDO
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public long queryOrderItemNum(QueryOrderItem queryOrderItem)throws DaoException;
	
	/**
	 * 根据订单ID查询物流信息
	 * @return OrderLogisticsDO
	 * @author lixin
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public OrderLogisticsDO queryOrderLogisticsByOrderId(Long orderId) throws DaoException;
	
	/**
	 * 
	 * Created on 2011-8-1
	 * <p>Discription: 分销分页查询</p>
	 * @param queryDistributeOrder
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderChannelDO> queryOrderChannelList(QueryDistributeOrder queryDistributeOrder)throws DaoException ;

	/**
	 * 
	 * Created on 2011-8-1
	 * <p>Discription:分销记录总数 </p>
	 * @param queryOrderItem
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public long queryOrderChannelListNum(QueryDistributeOrder queryDistributeOrder)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>Discription:提供商家查询主订单，查询时间精确到时分秒. </p>
	 * @param map
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> queryOrderListBuyBussiness(Map<String,Object> map) throws DaoException;
	
	public int queryOrderListBuyBussinessCount(Map<String,Object> map) throws DaoException;

	
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>Discription: 返回编号对应的子订单集合</p>
	 * @param orderItemIdList
	 * @return 
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderItemDO> queryOrderItemDOList(List<Long> orderItemIdList)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-8-11
	 * <p>Discription: 根据商品名称查询子订单记录</p>
	 * @param map
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> queryOrderItemListByItemTitle(Map<String, Object> map)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-8-17
	 * <p>Discription:根据商品名称查询子订单记录总数 </p>
	 * @param map
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public long queryOrderItemByItemTitleNum(Map<String, Object> map)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-10
	 * <p>Discription: 统计符合条件的商品购买数量</p>
	 * @param queryOrderItem
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public long queryOrderItemBuyNum(QueryOrderItem queryOrderItem)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-14
	 * <p>Discription: 主订单下符合评价条件的子订单集合</p>
	 * @param orderId
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderItemDO> queryRateOrderItemDOList(Long orderId)throws DaoException;
	
	public OrderChannelDO queryOrderChannelByOrderItemId(long orderItemId) throws DaoException;
	
	/**
	 * @Description: 查询未付款或者是卖家已发货的订单(我是买家主页用，用于显示前5条记录，时间降序)
	 * @author [贺泳]
	 * @date 2011-10-15 上午10:54:48
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param map
	 * @return
	 */
	public List<OrderDO> queryOrderItemListByState(Map<String, Object> map) throws DaoException;
	
	
	/**
	 * 
	 * Created on 2011-10-18
	 * <p>Discription:查询订单列表(无分页,查询时间精确到时分秒.) </p>
	 * @param map
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> queryOrderList(Map<String,Object> map) throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-27
	 * <p>Discription:买家查询订单 </p>
	 * @param QueryVO
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> queryOrderByBuyer(QueryVO queryVO) throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-27
	 * <p>Discription:买家获取订单数量 </p>
	 * @param QueryVO
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long queryOrderNumByBuyer(QueryVO queryVO) throws DaoException;
	
	/**
	 * 
	 * Created on 2011-10-31
	 * <p>Discription: 自动确认收货</p>
	 * @param map
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int queryOrderConfirmNum(Map<String, Object> map) throws DaoException;

	/**
	 * 
	 * Created on 2011-10-31
	 * <p>Discription: 自动确认收货</p>
	 * @param map
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<Long> queryOrderConfirm(Map<String, Object> map) throws DaoException;


	/**
	 * 查出最近三个月的分销量
	 * 
	 * @param queryDistributeOrder
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	abstract long queryOrderChannelListNumDuringMonth(QueryDistributeOrder queryDistributeOrder)
			throws DaoException;
	
	/**
	 * @Description:根据号订单号数组查询订单多个订单信息(用于正式发送第三方收银台前在主订单打上标记)
	 * @author [贺泳]
	 * @date 2011-11-4 上午11:05:37
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public List<OrderDO> queryOrderListByOrderId(Map<String,Long[]> map) throws DaoException;
	
	/**
	 * 
	 * Created on 2011-11-8
	 * <p>Discription: 
	 * 生成订单后调用支付ACTION查询当前订单与用户关系时,使用读写分离会有延迟现象。把该查询回复到查询写库
	 * </p>
	 * @param orderId
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public OrderDO queryWriteOrder(Long orderId) throws DaoException;
	
	/**
	 * Created on 2011-11-14
	 * <p>Discription: 
	 *  查询订单的总金额
	 * </p>
	 * @param orderId
	 * @return
	 * @throws DaoException
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int queryOrderPriceAmount(Map<String,Object> map) throws DaoException;
}

