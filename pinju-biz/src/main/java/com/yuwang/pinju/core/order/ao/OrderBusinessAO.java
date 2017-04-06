package com.yuwang.pinju.core.order.ao;


import java.util.Map;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.order.query.SendDeliveryVO;

/**
 * Created on 2011-6-3
 * <p>订单管理接口定义</p>
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 * 
 */
public interface OrderBusinessAO {


	/**
	 * 根据订单ID查询物流信息
	 * @return OrderLogisticsDO
	 * @author lixin
	 */
	public Result queryOrderLogisticsByOrderId(Long orderId);
	
	/**
	 * 更新订单
	 * @return 成功 true 失败 false
	 * @author huazhishu
	 */
	public Result sellCloseOrder(Map<String,Object> map);
	
	/**
	 * Created on 2011-12-13
	 * <p>Description: 卖家取消订单异步校验</p>
	 * @param orderId
	 * @param userId
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result sellerCloseOrderCheck(Long orderId,Long userId);

	
	/**
	 * 延长发贷时间
	 * @return 成功 true 失败 false
	 * @author huazhishu
	 */
	public Result updateRecevingDate(Long orderID, Integer day , long userId);


	
	/**
	 * Created on 2011-7-13
	 * @see 
	 * <p>Discription:进入卖家发货页面</p>
	 * @param orderId
	 * @param sellerId
	 * @return Result
	 * 	1. 主订单明细
	 * 	2. 卖家发货地址列表
	 * @author 杜成
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result inSellerDelivery(long orderId,long sellerId);
	
	/**
	 * 
	 * Created on 2011-7-14
	 * @see
	 * <p>Discription:
	 * 卖家发货 
	 * 	  1.更新发货订单发货记录
	 * 	  2.更新订单状态
	 * </p>
	 * @param orderId 
	 * @param outLogisticsId
	 * @param logisticsType
	 * @return 
	 * 		1.返回确认是否成功
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result confirmDelivery(SendDeliveryVO sendDelivery);
	
	/**
	 * 
	 * Created on 2011-7-14
	 * @see
	 * <p>Discription: 修改默认发货地址</p>
	 * @param memberId
	 * @param memberDeliveryId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result setDufDelivery(long memberId,long memberDeliveryId);
	
	
	/**
	 * Created on 2011-6-10
	 * @see
	 * <p>保存会员的收货地址。</p>
	 * @param memberDelivery     需要保存的会员收货地址信息
	 * @return Result
	 * key:memberDelivery  value:memberDelivery  阵对新增:id
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result saveMemberDelivery(MemberDeliveryDO memberDelivery);
	
	/**
	 * Created on 2011-10-25
	 * 查询主订单id下退款记录
	 * @param orderId
	 * @return
	 */
	public Result isOrderRefund(Long orderId) ;
	
	/**
	 * Created on 2011-11-16
	 * @see
	 * <p>延时买家收货时间验证</p>
	 * @return Result
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result prolongOrderCheck(Long orderId,Long userId);
}
