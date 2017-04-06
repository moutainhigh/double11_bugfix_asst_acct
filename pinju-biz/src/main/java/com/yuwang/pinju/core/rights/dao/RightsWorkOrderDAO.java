package com.yuwang.pinju.core.rights.dao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderQuery;

public interface RightsWorkOrderDAO {

	/**
	 * 生成一条财务处理工单
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-14
	 * @update:2011-9-14
	 * @param rightsWorkOrderDO
	 * @throws DaoException
	 */
	public void insertRightsWorkOrderDO(FinanceWorkOrderDO rightsWorkOrderDO)throws DaoException;
	
	/**
	 * 更新财务处理工单
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-14
	 * @update:2011-9-14
	 * @param rightsWorkOrderDO
	 * @return
	 * @throws DaoException
	 */
	public Integer updateRightsWorkOrderDO(FinanceWorkOrderDO rightsWorkOrderDO)throws DaoException;
	
	/**
	 * 根据sellerNick ,buyerNick二者或其中一个查询总个数
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-14
	 * @update:2011-9-14
	 * @param rightsWorkOrderDO
	 * @return
	 * @throws DaoException
	 */
	public Integer getRightsWorkOrderDOsCount(FinanceWorkOrderQuery rightsWorkOrderQuery)throws DaoException;
	
	/**
	 * 根据sellerNick ,buyerNick二者或其中一个查询所有记录详细信息
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-14
	 * @update:2011-9-14
	 * @param rightsWorkOrderDO
	 * @return
	 * @throws DaoException
	 */
	public List<FinanceWorkOrderDO> getRightsWorkOrderDOs(FinanceWorkOrderQuery rightsWorkOrderQuery) throws DaoException;

	/**
	 * Created on 2011-10-8 
	 * @desc <p>Discription:根据业务ID和业务类型查询FinanceWorkOrderDO]</p>
	 * @param 
	 * @return rightsWorkOrderQuery
	 * @author:[石兴]
	 * @update:[2011-10-8] [更改人姓名]
	 */
	public FinanceWorkOrderDO getRightsWorkOrderDOByBizType(FinanceWorkOrderQuery rightsWorkOrderQuery) throws DaoException;
	
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
	public List<FinanceWorkOrderDO> queryRightsWorkOrderDOByFail(Map<String, Object> map) throws DaoException;

	/**
	 * Created on 2011-10-25 
	 * @desc <p>Discription:[根据订单ID和业务类型查询FinanceWorkOrderDO]</p>
	 * @param 
	 * @return FinanceWorkOrderDO
	 * @author:[石兴]
	 * @update:[2011-10-25] [更改人姓名]
	 */
	public FinanceWorkOrderDO getFinanceWorkOrderDOByOrderId(FinanceWorkOrderQuery rightsWorkOrderQuery) throws DaoException;
	
}
