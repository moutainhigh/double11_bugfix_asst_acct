package com.yuwang.pinju.core.report.ao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.domain.report.SellCategoryReportDO;
import com.yuwang.pinju.domain.report.SellItemsReportDO;
import com.yuwang.pinju.domain.report.SellReportQuery;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.report.ReportQueryDO;

public interface SellReportAO {
	
	/**
	 * 根据时间group by统计销售量和销售额
	 * @param map
	 * @return
	 */
	public Result querySellReportByDate(Map<String, Object> map);
	
	/**
	 * 根据时间统计销售量和销售额记录总数
	 * @param map
	 * @return
	 */
	public int querySellReportByDateCount(Map<String, Object> map) ;
	
	
	/**
	 * 根据时间统计销售总量和销售总额
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public ReportQueryDO querySellReportByDateSum(Map<String, Object> map) ;

	/**
	 * 
	 * Created on 2011-8-24
	 * @see
	 * <p>Discription: 
	 * 	 根据时间统计商品的销售量和销售额
	 * </p>
	 * @param  sellReportQuery
	 * @return List
	 * @throws
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<SellItemsReportDO> querySellItemsReportByDate(SellReportQuery sellReportQuery);
	
	/**
	 * 
	 * @see com.yuwang.pinju.core.report.ao.SellReportAO.java
	 * <p>Discription: 
	 * 	  根据时间统计分类的销售量和销售额
	 * </p>
	 * @param sellReportQuery
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-27
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	 public List<SellCategoryReportDO> querySellCategoryReportByDate(SellReportQuery sellReportQuery);
	 
	 /**
	  * 
	  * @see com.yuwang.pinju.core.report.ao.SellReportAO.java
	  * <p>Discription: 
	  * 	 卖家统计总的销售额和总的销售量
	  * </p>
	  * @param sellReportQuery
	  * @return
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-8-29
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	 public SellCategoryReportDO sellstatisticsItemAndPriceSum(SellReportQuery sellReportQuery);
}