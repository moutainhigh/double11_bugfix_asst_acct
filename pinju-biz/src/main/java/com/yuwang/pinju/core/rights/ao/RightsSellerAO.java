package com.yuwang.pinju.core.rights.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.rights.RightsDO;

public interface RightsSellerAO {
	
	
	
	/**
	 * 根据订单Id查询物流号
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-19
	 * @update:2011-9-19
	 * @param orderId
	 * @return
	 */
	Result queryOrderLogisticsByOrderId(Long orderId) ;
	
	/**
	 * 根据维权Id查询DO
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-21
	 * @update:2011-9-21
	 * @param id
	 * @return
	 */
	Result getRightsDOById(Long id);
	
	/**
	 * 根据:订单Id查询OrderDO,VouchPayDO;订子订单Id查询子订单DO
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-21
	 * @update:2011-9-21
	 * @param orderId
	 * @return
	 */
	Result getOrderDOAndOrderItemDOAndVouchPayDOById(Long orderId,Long orderItemId);
	
	/**
	 * 根据退货运单Id和buyerId查询该退货单
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-21
	 * @update:2011-9-21
	 * @param id
	 * @param buyerId
	 * @return
	 */
	Result getRightsLogisticsDO(Long id,Long buyerId);
	
	/**
	 * 生成一条财务处理工单
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-22
	 * @update:2011-9-22
	 * @param rightsWorkOrderDO
	 * @return
	 */
	Result addRightsWorkOrderDO(RightsDO rightsDO);
	
	/**
	 * 更新维权成功后，添加财务处理工单，做事务处理
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-26
	 * @update:2011-9-26
	 * @param rightsDO
	 * @return
	 */
	Result updateRightsRecordAndAddRightsWorkOrder(RightsDO rightsDO);
	/**
	 * 首先查询rightsDO,插入rightsMessageDO ,更新维权状态为卖家拒绝该维权
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-27
	 * @update:2011-9-27
	 * @param rightsId
	 * @param refuseReason
	 * @return
	 */
	Result sellerRefuseRights(Long rightsId, String refuseReason);
	
	/**
	 * 更新物流表确认收货时间
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-10-24
	 * @update:2011-10-24
	 * @param rightsDO
	 * @return
	 */
	Result updateRightsLogisticsRecord(RightsDO rightsDO);
}
