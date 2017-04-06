package com.yuwang.pinju.core.trade.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.trade.DirectOrderDO;
import com.yuwang.pinju.domain.trade.DirectPayDO;
import com.yuwang.pinju.domain.trade.DirectPayRevLogDO;
import com.yuwang.pinju.domain.trade.DirectPaySendLogDO;


/**  
 * @Project: pinju-biz
 * @Description: 即时到账DAO
 * @author 石兴 shixing@zba.com
 * @date 2011-8-15 上午11:27:53
 * @update 2011-8-15 上午11:27:53
 * @version V1.0  
 */
public interface DirectDAO {

	/**
	 * Create on 2011-8-1
	 * <p>@Discription:[插入保证金接受报文记录]</p>
	 * @param: MarginRevLogDO
	 * @return: void 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertdirectPayRevLogRecord(DirectPayRevLogDO directPayRevLogDO) throws DaoException;
	
	/**
	 * Create on 2011-8-1
	 * <p>@Discription:[通过ID查询保证金报文接受记录]</p>
	 * @param: MarginRevLogDO
	 * @return: MarginRevLogDO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	DirectPayRevLogDO getDirectPayRevLogDOById(Long id) throws DaoException;
	
	/**
	 * Create on: 2011-8-12上午09:31:37
	 * <p>Discription:[插入即时到账订单记录]</p>
	 * @param: DirectOrderDO
	 * @return: void 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertDirectOrderRecord(DirectOrderDO directOrderDO) throws DaoException;
	
	/**
	 * Create on: 2011-8-12下午05:16:39
	 * <p>Discription:[通过ID查询即时到帐订单信息]</p>
	 * @param: Long
	 * @return: DirectOrderDAO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	DirectOrderDO getDirectOrderDOById(Long id) throws DaoException;

	
	/**
	 * Create on: 2011-8-12下午05:16:39
	 * <p>Discription:[通过支付ID查询即时到帐支付信息]</p>
	 * @param: Long
	 * @return: DirectOrderDAO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	DirectPayDO getDirectPayDOById(Long payOrderId) throws DaoException;
	
	/**
	 * Created on 2011-9-26
	 * <p>Discription: 通过DirectPayDO对象，查询支付信息</p>
	 * @param directPayDO
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public DirectPayDO getDirectPayDO(DirectPayDO directPayDO) throws DaoException;
	
	/**
	 * Created on 2011-8-15 
	 * <p>Discription:[获取订单ID]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long getOrderId() throws DaoException;
	
	/**
	 * Create on: 2011-8-12下午05:09:55
	 * <p>Discription:[插入即时到帐支付订单记录]</p>
	 * @param: DirectPayDO
	 * @return: void 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertDirectPayRecord(DirectPayDO directPayDO) throws DaoException;

	/**
	 * Created on 2011-8-15 
	 * <p>Discription:[获取支付订单号,和编号共用一个序列]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Long getPayOrderId() throws DaoException;	
	
	/**
	 * Create on 2011-8-1
	 * <p>@Discription:[插入保证金报文发送记录]</p>
	 * @param: MarginSendLogDO
	 * @return: void 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertDirectPaySendLogRecord(DirectPaySendLogDO marginSendLogDO) throws DaoException;
	
	/**
	 * Create on 2011-8-1
	 * <p>@Discription:[通过ID查询保证金报文发送记录]</p>
	 * @param: Long
	 * @return: MarginSendLogDO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	DirectPaySendLogDO getDirectPaySendLogDOById(Long id) throws DaoException;
	
	/**
	 * 更新订单状态
	 * @Project:pinju-biz
	 * <p>Discription:[]</p>
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-15
	 * @update:2011-8-15
	 * @param directOrderDO
	 * @throws DaoException
	 */
	int updateDirectOrderDOStatus(DirectOrderDO directOrderDO)throws DaoException;
	
	int updateDirectPayDOStatus(DirectPayDO directPayDO) throws DaoException;

}
