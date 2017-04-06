package com.yuwang.pinju.core.report.dao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.report.ReportQueryDO;
import com.yuwang.pinju.domain.report.SellCategoryReportDO;
import com.yuwang.pinju.domain.report.SellItemsReportDO;
import com.yuwang.pinju.domain.report.SellReportQuery;

public interface SellReportDAO {
	
	/**
	 * 根据时间统计销售总量和销售额
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public List<ReportQueryDO> querySellReportByDate(Map<String, Object> map)  throws DaoException;
	
	/**
	 * 根据时间统计销售量和销售额记录总数
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public int querySellReportByDateCount(Map<String, Object> map)  throws DaoException;
	
	/**
	 * 根据时间统计销售总量和销售总额
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public ReportQueryDO querySellReportByDateSum(Map<String, Object> map)  throws DaoException;
	
	/**
	 * @see
	 * <p>Discription: 
	 * 	 根据时间统计商品的销售量和销售额
	 * </p>
	 * @param  map
	 * @return List<SellItemsReportDO>
	 * @throws
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<SellItemsReportDO> querySellItemsReportByDate(SellReportQuery sellReportQuery)throws DaoException;;
	/**
	 * @see
	 * <p>Discription: 
	 * 	 获取统计商品的总记录数
	 * </p>
	 * @param sellReportQuery
	 * @return Integer
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-27
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Integer querySellItemsReportCount(SellReportQuery sellReportQuery)throws DaoException;
	
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
	 public List<SellCategoryReportDO> querySellCategoryReportByDate(SellReportQuery sellReportQuery)throws DaoException;
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
	 public Integer querySellCategoryReportByDateCount(SellReportQuery sellReportQuery)throws DaoException;
	 /**
	  * 
	  * @see com.yuwang.pinju.core.report.dao.SellReportDAO.java
	  * <p>Discription: 
	  * 	 卖家统计总的销售额和销售量
	  * </p>
	  * @param sellReportQuery
	  * @return   SellCategoryReportDO
	  * @throws DaoException
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-8-29
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	 public SellCategoryReportDO sellstatisticsItemAndPriceSum(SellReportQuery sellReportQuery)throws DaoException;

}