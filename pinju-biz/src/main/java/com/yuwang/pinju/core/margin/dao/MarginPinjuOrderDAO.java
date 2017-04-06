package com.yuwang.pinju.core.margin.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.margin.MarginPinjuOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginPinJuOrderQuery;

/**  
 * @Project: pinju-biz
 * @Discription: [品聚保证金交易流水DAO]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-9 下午05:03:34
 * @update 2011-8-9 下午05:03:34
 * @version V1.0  
 */
public interface MarginPinjuOrderDAO{
	
	/**
	 * <p>@Discription:[插入品聚保证金交易记录]</p>
	 * @author:[凌建涛]
	 * @param: MarginPinjuOrderDO
	 * @return: void 
	 * @create: 2011-8-9下午05:06:03
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertMarginPinjuOrderRecord(MarginPinjuOrderDO marginPinjuOrderDO) throws DaoException;
	
	/**
	 * <p>@Discription:[更新品聚保证金交易记录]</p>
	 * @author:[凌建涛]
	 * @param: MarginPinjuOrderDO
	 * @return: int 
	 * @create: 2011-8-9下午05:07:20
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	int updateMarginPinjuOrderRecord(MarginPinjuOrderDO marginPinjuOrderDO) throws DaoException;
	
	/**
	 * <p>@Discription:[通过会员ID查询品聚保证金交易流水]</p>
	 * @author:[凌建涛]
	 * @param: Long
	 * @return: MarginPinjuOrderDO 
	 * @create: 2011-8-9下午05:09:27
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	MarginPinjuOrderDO getMarginPinjuOrderDOById(Long memberId) throws DaoException;
	
	/**
	 * <p>@Discription:[通过MarginQuery获取品聚保证金交易流水记录数]</p>
	 * @author:[凌建涛]
	 * @param: MarginQuery
	 * @return: int 
	 * @create: 2011-8-9下午05:13:39
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	int getMarginPinjuOrderDOsCount(MarginPinJuOrderQuery marginPinJuOrderQuery) throws DaoException;
	
	/**
	 * <p>@Discription:[通过MarginQuery获取品聚保证金交易流水]</p>
	 * @author:[凌建涛]
	 * @param: MarginQuery
	 * @return: List<MarginPinjuOrderDO> 
	 * @create: 2011-8-9下午05:15:35
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	List<MarginPinjuOrderDO> getPinjuOrderDOs(MarginPinJuOrderQuery marginPinJuOrderQuery) throws DaoException;
	
}
