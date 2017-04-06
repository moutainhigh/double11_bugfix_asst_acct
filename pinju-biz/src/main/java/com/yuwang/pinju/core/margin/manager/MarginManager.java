package com.yuwang.pinju.core.margin.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.margin.MarginPinJuDO;
import com.yuwang.pinju.domain.margin.MarginPinjuOrderDO;
import com.yuwang.pinju.domain.margin.MarginSellerDO;
import com.yuwang.pinju.domain.margin.MarginSellerOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginSellerOrderQuery;


/**  
 * @Project: pinju-biz
 * @Description: 保证金Manager
 * @author 石兴 shixing@zba.com
 * @date 2011-8-2 上午10:10:02
 * @update 2011-8-2 上午10:10:02
 * @version V1.0  
 */
public interface MarginManager {
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[查询卖家保证金信息]</p>
	 * @param sellerId 卖家ID，即会员ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public MarginSellerDO getMarginSellerDOBySellerId(Long sellerId) throws ManagerException;

	/**
	 * Create on 2011-8-4
	 * <p>@Discription:[插入卖家保证金账户记录]</p>
	 * @param: MarginOrderDO
	 * @return: void 
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void insertMarginSellerRecord(MarginSellerDO marginSellerDO) throws ManagerException;
	
	/**
	 * Create on 2011-8-4
	 * <p>@Discription:[插入保证金交易记录]</p>
	 * @param: MarginOrderDO
	 * @return: void 
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void insertMarginSellerOrderRecord(MarginSellerOrderDO marginSellerOrderDO) throws ManagerException;
	
	/**
	 * Create on 2011-8-4
	 * <p>@Discription:[更新卖家保证金记录]</p>
	 * @param: SellerMarginDO
	 * @return: int 
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateMarginSellerRecord(MarginSellerDO marginSellerDO)throws ManagerException;
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[插入品聚保证金交易流水记录]</p>
	 * @param MarginPinjuOrderDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void insertMarginPinJuOrderRecord(MarginPinjuOrderDO marginPinjuOrderDO) throws ManagerException;
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[插入品聚保证新信息]</p>
	 * @param MarginPinJuDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void insertMarginPinJuRecord(MarginPinJuDO marginPinJuDO) throws ManagerException;
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[更新品聚保证金账户信息]</p>
	 * @param MarginPinJuDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int updateMarginPinJuRecord(MarginPinJuDO marginPinJuDO) throws ManagerException;
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[根据Id查询品聚保证金信息]</p>
	 * @param MarginPinjuOrderDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public MarginPinJuDO getMarginPinJuDOById(Long id) throws ManagerException;
	
	 /**
	  * @Project:pinju-biz
	  * <p>Discription:[查询品聚保证金信息，由于该表只有一条记录，请取第一条记录]</p>
	  * @author: lixingquan lixingquan@zba.com
	  * @date:2011-8-11
	  * @update:2011-8-11
	  * @return
	  * @throws ManagerException
	  */
	public List<MarginPinJuDO> getMarginPinJuDO() throws ManagerException;
	
	/**
	 * Create on: 2011-8-13下午01:37:03
	 * <p>Discription:[查询卖家保证金交易流水]</p>
	 * @param: MarginSellerOrderQuery
	 * @return: List<MarginSellerOrderDO> 
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<MarginSellerOrderDO> getMarginSellerOrderDOs(MarginSellerOrderQuery marginSellerOrderQuery) throws ManagerException;
	

}
