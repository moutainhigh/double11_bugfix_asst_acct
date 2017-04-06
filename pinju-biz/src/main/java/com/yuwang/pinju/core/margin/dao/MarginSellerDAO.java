package com.yuwang.pinju.core.margin.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.margin.MarginSellerDO;

/**  
 * @Project: pinju-biz
 * @Discription: [卖家保证金DAO接口]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-1 下午04:41:56
 * @update 2011-8-1 下午04:41:56
 * @version V1.0  
 */
public interface MarginSellerDAO{
	
	/**
	 * Create on 2011-8-1
	 * <p>@Discription:[插入卖家保证金记录]</p>
	 * @param: SellerMarginDO
	 * @return: void 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertMarginSellerDORecord(MarginSellerDO marginSellerDO) throws DaoException;
	
	/**
	 * Create on 2011-8-1
	 * <p>@Discription:[通过ID查询卖家保证金]</p>
	 * @param: Long
	 * @return: SellerMarginDO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	MarginSellerDO getMarginSellerDOBySellerId(Long SellerId) throws DaoException;
	
	
	/**
	 * Create on 2011-8-1
	 * <p>@Discription:[更新卖家保证金记录]</p>
	 * @param: 
	 * @return: void 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	int updateMarginSellerDORecord(MarginSellerDO marginSellerDO) throws DaoException;
	
	/**
	 * 临时 
	 * @return
	 * @throws DaoException
	 */
	Long queryIndexVisitCount() throws DaoException;

}
