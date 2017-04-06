package com.yuwang.pinju.core.rights.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderQuery;

/**
 * @Project: 品聚
 * @Description: <p>财务处理工单管理类</p>
 * @author 石兴 shixing@zba.com
 * @date 2011-10-11 上午10:49:55
 * @update 石兴 2011-10-11 上午10:49:55
 * @version V1.0
 */
public interface RightsWorkOrderManager {
	
	/**
	 * 根据sellerNick ,buyerNick二者或其中任何一个查询所有记录的详细信息
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-14
	 * @update:2011-9-14
	 * @param rightsWorkOrderQuery
	 * @return
	 * @throws ManagerException
	 */
	public List<FinanceWorkOrderDO> getRightsWorkOrderDOs(FinanceWorkOrderQuery rightsWorkOrderQuery) throws ManagerException;
	
	
	/**
	 * 添加一条财务处理工单
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-14
	 * @update:2011-9-14
	 * @param rightsWorkOrderDO
	 * @throws ManagerException
	 */
	public void insertRightsWorkOrderDO(FinanceWorkOrderDO rightsWorkOrderDO) throws ManagerException;
	
	/**
	 * 更新财务处理工单状态
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-14
	 * @update:2011-9-14
	 * @param rightsWorkOrderDO
	 * @return
	 * @throws ManagerException
	 */
	public boolean updateRightsWorkOrderDO(FinanceWorkOrderDO rightsWorkOrderDO) throws ManagerException;

	/**
	 * Created on 2011-10-8 
	 * @desc <p>Discription:[根据业务ID和业务类型查询RightsWorkOrderDO]</p>
	 * @param rightsWorkOrderQuery
	 * @return RightsWorkOrderDO
	 * @author:[石兴]
	 * @update:[2011-10-8] [更改人姓名]
	 */
	FinanceWorkOrderDO getRightsWorkOrderDOByBizType(FinanceWorkOrderQuery rightsWorkOrderQuery)throws ManagerException;
	
	/**
	 * Created on 2011-10-25 
	 * @desc <p>Discription:[根据订单ID和业务类型查询FinanceWorkOrderDO]</p>
	 * @param 
	 * @return FinanceWorkOrderDO
	 * @author:[石兴]
	 * @update:[2011-10-25] [更改人姓名]
	 */
	FinanceWorkOrderDO getFinanceWorkOrderDOByOrderId(FinanceWorkOrderQuery rightsWorkOrderQuery) throws ManagerException;

	/**
	 * 查询未处理的退款工单
	 * @Project:pinju-biz
	 * @author: caoxiao
	 * @date:2011-10-11
	 * @update:2011-10-11
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public List<FinanceWorkOrderDO> queryRightsWorkOrderDOByFail(Map<String, Object> map) throws ManagerException;
	
	
}
