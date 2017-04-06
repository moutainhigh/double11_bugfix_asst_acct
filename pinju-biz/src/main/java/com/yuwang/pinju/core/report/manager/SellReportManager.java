package com.yuwang.pinju.core.report.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;

import com.yuwang.pinju.domain.report.ReportQueryDO;

import com.yuwang.pinju.domain.report.SellCategoryReportDO;
import com.yuwang.pinju.domain.report.SellItemsReportDO;
import com.yuwang.pinju.domain.report.SellReportQuery;


public interface SellReportManager {
	
	/**
	 * 根据时间group by统计销售量和销售额
	 * @param map
	 * @return
	 * @throws ManagerException
	 */
	public List<ReportQueryDO> querySellReportByDate(Map<String, Object> map)  throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-8-24
	 * @see
	 * <p>Discription: 
	 * 	 	根据条件统计销售记录
	 * </p>
	 * @param sellReportQuery
	 * @return List<SellItemsReportDO>
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<SellItemsReportDO> querySellItemsReportByDate(SellReportQuery sellReportQuery) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-8-24
	 * @see
	 * <p>Discription: 
	 * 	 根据条件获取统计的总记录数
	 * </p>
	 * @param sellReportQuery
	 * @return 总记录数    min=0
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int querySellItemsReportCount(SellReportQuery sellReportQuery) throws ManagerException;
	
	/**
	 * @see
	 * <p>Discription: 
	 * 	 根据时间统计分类销售量和销售额
	 * </p>
	 * @param sellReportQuery
	 * @return List
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-27
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	 public List<SellCategoryReportDO> querySellCategoryReportByDate(SellReportQuery sellReportQuery)throws ManagerException;
	 /**
	  * @see
	  * <p>Discription: 
	  * 	按照时间统计分类销售的总记录 
	  * </p>
	  * @param sellReportQuery
	  * @return Integer
	  * @throws DaoException
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-8-27
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	 public int querySellCategoryReportByDateCount(SellReportQuery sellReportQuery)throws ManagerException;
	 /**
	  * @see com.yuwang.pinju.core.report.manager.SellReportManager.java
	  * <p>Discription: 
	  * 	卖家统计总销售量和总的销售额	 
	  * </p>
	  * @param sellReportQuery
	  * @return SellCategoryReportDO
	  * @throws ManagerException
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-8-29
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	 public SellCategoryReportDO sellstatisticsItemAndPriceSum(SellReportQuery sellReportQuery)throws ManagerException;
	/**
	 * 根据时间统计销售量和销售额数
	 * @param map
	 * @return
	 * @throws ManagerException
	 */
	public int querySellReportByDateCount(Map<String, Object> map)  throws ManagerException;
	
	
	/**
	 * 根据时间统计销售总量和销售总额
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public ReportQueryDO querySellReportByDateSum(Map<String, Object> map)  throws ManagerException;
}