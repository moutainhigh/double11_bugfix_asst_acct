package com.yuwang.pinju.core.order.ao;


import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.order.query.QueryVO;

/**
 * Created on 2011-7-27
 * @see
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderQueryAO {
	/**
	 * 
	 * Created on 2011-7-27
	 * <p>Discription:限时购买记录分页查询 </p>
	 * @param queryOrderItemDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result queryLastTimeBuyList(QueryOrderItem queryOrderItemDO);
	
	/**
	 * 查询主订单列表
	 * Created on 2011-8-10
	 * <p>Discription: </p>
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
    public Result queryOrderList(Map<String, Object> map);
    
    /**
	 * 查询主订单列表
	 * Created on 2011-8-10
	 * <p>Discription: </p>
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
    public int queryOrderListCount(Map<String, Object> map);
    
    /**
     * 
     * Created on 2011-8-11
     * <p>Discription: 买家根据商品名称查询订单记录</p>
     * @param buyerId
     * @param itemTitle
     * @return
     * @author:[李鑫]
     * @version 1.0
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public Result queryOrderListByItemTitle(long buyerId, String itemTitle,String gmtCreateStart,String gmtCreateEnd,int page,String orderItemState,Integer [] refundState,String isBuyerRate);
    
	
	/**
	 * Created on 2011-8-03
	 * @see  根据订单ID获得订单详情
	 * @param orderId 订单编号
	 * @throws ManagerException
	 * @author:[lixin]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名] 
	 */
	public Result getOrderDOById(long orderId,Long urderId,String tp);
	
	/**
	 * Created on 2011-8-3
	 * <p>得到某个主订单下的子订单集合</p>
	 * @param orderId 主订单编号
	 * @return List<OrderItemDO>
	 * @throws ManagerException
	 * @author:[lixin]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result queryOrderItemList(long orderId);
	
	/**
	 * 
	 * Created on 2011-8-26
	 * <p>Discription: 获取满足查询条件的订单数</p>
	 * @param buyerId
	 * @param itemTitle
	 * @param gmtCreateStart
	 * @param gmtCreateEnd
	 * @param orderItemState
	 * @param refundState
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public long queryOrderItemListNum(long buyerId, String itemTitle,String gmtCreateStart,String gmtCreateEnd,String orderItemState,Integer [] refundState);
	
	
	/**
	 * 
	 * Created on 2011-9-15
	 * <p>Discription:[获取要支付的订单信息]</p>
	 * @param orderId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result createOrderPay(Long[] orderId);
	
	/**
	 * @Description: 查询不同状态的订单数量 
	 * @author [贺泳]
	 * @date 2011-10-13 下午07:51:10
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param map
	 * @return
	 */
	public Result selectOrderListCount(Map<String, Object> map);
	
	/**
	 * @Description: 查询 未付款 和 等待买家确认收货 的订单前5条记录
	 * @author [贺泳]
	 * @date 2011-10-14 上午10:16:52
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param buyerId
	 * @param gmtCreateStart
	 * @param gmtCreateEnd
	 * @param page
	 * @param orderItemState
	 * @param refundState
	 * @return
	 */
	public Result queryOrderListByState(long buyerId,int page,int orderItemState[]);
	
	/**
	 * @Description:查询订单维权数量
	 * @author [贺泳]
	 * @date 2011-10-18 下午06:23:43
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param buyerId
	 * @return
	 */
	public Result getRightsManagerNum(long buyerId);
	
	/**
	 * 
	 * Created on 2011-10-17
	 * <p>Discription:[获取子订单信息]</p>
	 * @param orderItemId
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public OrderItemDO queryOrderItem(Long orderItemId);
	
	/**
	 * 查询订单列表（无分页）
	 * Created on 2011-10-18
	 * <p>Discription: </p>
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
    public Result queryOrderListNoPage(Map<String, Object> map);
    
    
    /**
	 * 
	 * Created on 2011-10-27
	 * <p>Discription:买家查询订单 </p>
	 * @param QueryVO
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
    public Result queryOrderByBuyer(QueryVO queryVO);
 
    /**
	 * 
	 * Created on 2011-10-27
	 * <p>Discription:买家获取订单数量 </p>
	 * @param QueryVO
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
    public Long queryOrderNumByBuyer(QueryVO queryVO);
    
    /**
	 * 
	 * Created on 2011-11-04
	 * <p>Discription:卖家修改订单价格--权限判断 </p>
	 * @param sellerId,orderId
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
    public Result checkModifyPrice(Long sellerId,Long orderId);
    
    /**
	 * Created on 2011-11-11
	 * @see  根据订单ID获得订单对象
	 * @param orderId 订单编号
	 * @throws ManagerException
	 * @author:[lixin]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名] 
	 */
    public OrderDO queryOrderDO(Long orderId);
    
    /**
	 * Created on 2011-11-14
	 * @see  查询订单的总价格
	 * @param orderId 订单编号
	 * @throws ManagerException
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名] 
	 */
    public Result queryOrderPriceAmount(Map<String, Object> map);
}

