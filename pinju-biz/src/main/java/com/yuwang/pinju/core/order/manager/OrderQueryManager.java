package com.yuwang.pinju.core.order.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.QueryDistributeOrder;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.order.query.QueryVO;

/**
 * Created on 2011-7-26
 * 
 * @see <p>
 *      Discription: 订单相关查询接口
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderQueryManager {
	/**
	 * 
	 * Created on 2011-7-26
	 * <p>
	 * Discription: 子订单分页查询
	 * </p>
	 * 
	 * @param queryOrderItemDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	List<OrderItemDO> queryOrderItemDOList(QueryOrderItem queryOrderItemDO)
			throws ManagerException;

	/**
	 * 
	 * Created on 2011-7-27
	 * <p>
	 * Discription: 查询符合条件的商品购买数量
	 * </p>
	 * 
	 * @param queryOrderItemDO
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	long queryOrderItemBuyNum(QueryOrderItem queryOrderItem)
			throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-7-27
	 * <p>
	 * Discription: 符合条件总数
	 * </p>
	 * 
	 * @param queryOrderItemDO
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	long queryOrderItemDONum(QueryOrderItem queryOrderItem)
			throws ManagerException;

	/**
	 * 
	 * Created on 2011-7-28
	 * <p>
	 * Discription: 分销分页查询
	 * </p>
	 * 
	 * @param queryDistributeOrder
	 * @return key: orderChannelList value:List<OrderChannelDO> key: num
	 *         value:总记录数
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result queryDistributeOrderList(
			QueryDistributeOrder queryDistributeOrder) throws ManagerException;

	/**
	 * Created on 2011-6-4
	 * <p>
	 * 得到查询订单记录总数
	 * </p>
	 * 
	 * @param sellerNick
	 *            卖家昵称
	 * @param orderState
	 *            订单状态
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param isBuyerRate
	 *            买家是否已评价
	 * @param isSellerRate
	 *            卖家是否已评价
	 * @param buyerId
	 *            买家编号
	 * @param page
	 *            当前页
	 * @param pageNum
	 *            每页显示数
	 * @return Long
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long queryOrderNum(Long sellerId,String sellerNick, Integer[] orderState,
			Date beginDate, Date endDate, Integer isBuyerRate,
			Integer isSellerRate, Long buyerId) throws ManagerException;

	/**
	 * Created on 2011-6-4
	 * <p>
	 * 得到查询父订单集合
	 * </p>
	 * 
	 * @param sellerNick
	 *            卖家昵称
	 * @param orderState
	 *            订单状态
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param isBuyerRate
	 *            买家是否已评价
	 * @param isSellerRate
	 *            卖家是否已评价
	 * @param buyerId
	 *            买家编号
	 * @param currentPage
	 *            当前页
	 * @param rowPerPage
	 *            每页显示数
	 * @return List<OrderDO>
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> queryOrder(Long sellerId,String sellerNick, Integer[] orderState,
			Date beginDate, Date endDate, Integer isBuyerRate,
			Integer isSellerRate, Long buyerId, int currentPage, int rowPerPage)
			throws ManagerException;

	/**
	 * Created on 2011-6-4
	 * <p>
	 * 得到某个主订单下的子订单集合
	 * </p>
	 * 
	 * @param orderId
	 *            主订单编号
	 * @return List<OrderItemDO>
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderItemDO> queryOrderItemList(Long orderId)
			throws ManagerException;

	
	/**
	 * Created on 2011-8-10
	 * <p>
	 * 得到对应子订单编号的集合
	 * </p>
	 * 
	 * @param orderId
	 *            主订单编号
	 * @return List<OrderItemDO>
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderItemDO> queryOrderItemList(List<Long> orderItemIdList)
			throws ManagerException;
	
	/**
	 * Created on 2011-6-4
	 * <p>
	 * 得到父子订单集合
	 * </p>
	 * 
	 * @param orderId
	 *            主订单编号
	 * @return Map<OrderDO,List<OrderItemDO>>
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Map<OrderDO, List<OrderItemDO>> queryOrderList(Long orderId)
			throws ManagerException;

	/**
	 * Created on 2011-6-4
	 * <p>得到主定单详情</p>
	 * @param orderId
	 * @return 没有查到返回null
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public OrderDO getOrderDOById(long orderId)throws ManagerException;
	
	/**
	 * Created on 2011-6-4 
	 * <p>得到子定单详情</p>
	 * @param orderItemId
	 * @return 没有查到返回null
	 * @throws ManagerException
	 */
	public OrderItemDO getOrderItemDOById(long orderItemId)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>Discription: 查询主订单列表，查询时间精确到时分秒</p>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> getOrderListInTime(Map<String, Object> map) throws ManagerException;
	
	public int getOrderListInTimeCount(Map<String, Object> map) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-8-11
	 * <p>Discription: 买家根据商品名称查询订单记录</p>
	 * @param map
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> getOrderItemListByItemTitle(Map<String, Object> map)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-8-17
	 * <p>Discription:买家根据商品名称查询订单记录总数 </p>
	 * @param map
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public long getOrderItemListByItemTitleNum(Map<String, Object> map) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-10-14
	 * <p>Discription: </p>
	 * @param orderId
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Map<OrderDO, List<OrderItemDO>> queryRateOrderList(Long orderId)throws ManagerException;
	
	
	public OrderChannelDO queryOrderChannelByOrderItemId(long orderItemId) throws ManagerException;
	
	/**
	 * @Description: 查询未付款或者是卖家已发货的订单(我是买家主页用，用于显示前5条记录，时间降序)
	 * @author [贺泳]
	 * @date 2011-10-15 上午11:00:40
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param map
	 * @return
	 * @throws ManagerException
	 */
	public List<OrderDO> queryOrderItemListByState(Map<String, Object> map)throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-10-18
	 * <p>Discription: 查询订单列表，查询时间精确到时分秒（无分页）</p>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> getOrderList(Map<String, Object> map) throws ManagerException;
	
	
	/**
	 * 
	 * Created on 2011-10-27
	 * <p>Discription:买家查询订单 </p>
	 * @param QueryVO
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<OrderDO> queryOrderByBuyer(QueryVO queryVO) throws ManagerException;
	
	
	/**
	 * 
	 * Created on 2011-10-27
	 * <p>Discription:买家获取订单数量 </p>
	 * @param QueryVO
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long queryOrderNumByBuyer(QueryVO queryVO) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-11-2
	 * <p>Discription: 查询符合定时确认收货订单ID集合(0-100)</p>
	 * @param map
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<Long> queryOrderConfirm(int startNum,int endNum )throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-10-31
	 * <p>Discription: 自动确认收货符合条件的订单总数</p>
	 * @param map
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int queryOrderConfirmNum()throws ManagerException;
	
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
	public List<OrderDO> queryOrderListByOrderId(Long[] orderId) throws ManagerException;
	
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
	public int queryOrderPriceAmount(Map<String,Object> map) throws ManagerException;
}
