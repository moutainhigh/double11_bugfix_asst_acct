package com.yuwang.pinju.core.refund.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.RefundDO;

/** <p>Discription:退款查询， 用于超时使用  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-17
 */
public interface RefundQueryManager {

	/**
	 * <p> Discription: 获取所有拒绝退款和超过5天的退款</p>
	 * @param map
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> queryRefundByBuyerNoReply(Map<String, Object> map) throws ManagerException;
	
	/**
	 * <p> Discription: 获取所有买家申请，卖家没有响应，5天超时的退款</p>
	 * @return
	 * @throws ManagerException
	 * @author:[shihongbo]
	 * @version 1.0
	 * @create:2011-10-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> querySellerNoReplyRefund() throws ManagerException;
	
	/**
	 * <p> Discription: 获取卖家不确认收货 -- 平邮，30天超时的退款</p>
	 * @return
	 * @throws ManagerException
	 * @author:[shihongbo]
	 * @version 1.0
	 * @create:2011-10-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> sellerNotConfirmPost() throws ManagerException;
	
	/**
	 * <p> Discription: 获取卖家不确认收货 -- 快递，10天超时的退款</p>
	 * @return
	 * @throws ManagerException
	 * @author:[shihongbo]
	 * @version 1.0
	 * @create:2011-10-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> sellerNotConfirmExpress() throws ManagerException;
}


