package com.yuwang.pinju.core.order.service;

import java.util.List;
import java.util.Map;

import com.yuwang.api.ApiException;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OpenOrderQuery;

/**
 * Created on 2011-9-27
 * 
 * @see <p>
 *      Discription: 订单对外查询管理
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OpenQueryOrderService {
	/**
	 * 
	 * Created on 2011-9-27
	 * <p>
	 * Discription: [OpenAPI卖家订单查询]
	 * </p>
	 * 
	 * @param openOrderQuery
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	List<OrderDO> queryOrder(OpenOrderQuery openOrderQuery);

	/**
	 * 
	 * Created on 2011-9-27
	 * <p>
	 * Discription: [OpenAPI卖家某个时间范围的订单数量]
	 * </p>
	 * 
	 * @param sellerId
	 *            卖家编号
	 * @param startTime
	 *            查询开始时间
	 * @param endTime
	 *            查询结束时间
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Long sellerOrderSize(OpenOrderQuery openOrderQuery);

	/**
	 * 
	 * Created on 2011-9-27
	 * <p>
	 * Discription: [OpenAPI获取订单详情] 添加用户与订单关系效验
	 * </p>
	 * 
	 * @param orderId
	 * @param sellerId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	OrderDO queryOrderInfo(Long orderId, Long sellerId);

	/**
	 * 
	 * Created on 2011-9-27
	 * <p>
	 * Discription: [OpenAPI卖家发货] 添加订单与卖家关系效验 添加logisticsCode编码有效性效验
	 * </p>
	 * 
	 * @param orderId
	 *            内部订单编号
	 * @param outLogisticsId
	 *            外部物流编号
	 * @param logisticsCode
	 *            物流公司编码
	 * @param sellerId
	 *            卖家编号
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	boolean sellerDelivery(Long orderId, String outLogisticsId,
			String logisticsCode, Long sellerId,String sendAddress,String sendName,String sendPost,String sendMobilePhone,String sendPhone)throws ApiException;

	/**
	 * 
	 * Created on 2011-10-8
	 * <p>
	 * Discription: 查询某个子订单上的优惠价格
	 * </p>
	 * 
	 * @param orderItemId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	String queryOrderItemDiscountPrice(Long orderItemId);
	
	/**
	 * 
	 * Created on 2011-10-8
	 * <p>
	 * Discription: 查询某个子订单上的优惠价格
	 * </p>
	 * 
	 * @param orderItemId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	String queryOrderItemDiscountPrice(OrderItemDO orderItemDO);
	
	/**
	 * 
	 * Created on 2011-10-8
	 * <p>
	 * Discription: 查询订单优惠信息描述
	 * </p>
	 * 
	 * @param orderId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Map<Long,String> queryOrderItemDiscountDesc(Long orderId);
	
	/**
	 * @Description: 判断物流公司是否存在
	 * @author [贺泳]
	 * @date 2011-10-19 下午06:32:06
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param corpCode ：物流公司代码
	 * @return
	 */
	boolean checkTradeLogisticsCorp(String corpCode);
}
