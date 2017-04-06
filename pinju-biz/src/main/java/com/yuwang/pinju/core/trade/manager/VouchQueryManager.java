package com.yuwang.pinju.core.trade.manager;

import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * Created on 2011-9-15
 * @see
 * <p>Discription:担保交易查询 </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface VouchQueryManager {

    
    /**
     * @Description: 根据内部 订单编号 查询支付记录
     * @author [贺泳]
     * @date 2011-9-15 下午04:09:47
     * @version 1.0
     * @param id
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public VouchPayDO selectOrderPayByOrderId(Long OrderId)throws ManagerException;
    
    /**
     * @Description: 查询支付表
     * @author [贺泳]
     * @date 2011-9-21 下午03:04:26
     * @version 1.0
     * @param orderPayDO：支付表对像
     * @return
     * @throws ManagerException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public VouchPayDO selectOrderPay(VouchPayDO orderPayDO) throws ManagerException;
	/**
     * @Description: 根据内部 支付编号 查询支付记录
     * @author [贺泳]
     * @date 2011-9-15 下午04:09:47
     * @version 1.0
     * @param id
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public VouchPayDO selectOrderPayByOrderPayId(String orderPayId) throws ManagerException;
    
    /**
     * @Description:查询订单支付记录
     * @author [lixin]
     * @date 2011-10-08 
     * @version 1.0
     * @param map
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public VouchPayDO selectOrderPay(Map map) throws ManagerException;
}

