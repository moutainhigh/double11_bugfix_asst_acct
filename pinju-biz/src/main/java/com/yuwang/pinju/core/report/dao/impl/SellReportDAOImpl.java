package com.yuwang.pinju.core.report.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.report.dao.SellReportDAO;
import com.yuwang.pinju.domain.report.ReportQueryDO;
import com.yuwang.pinju.domain.report.SellCategoryReportDO;
import com.yuwang.pinju.domain.report.SellItemsReportDO;
import com.yuwang.pinju.domain.report.SellReportQuery;

public class SellReportDAOImpl implements SellReportDAO {

	private final static String NAMESPACE = "T_ORDER_ITEM_STAT.";
	private final String T_ORDER_ITEM_STAT="T_ORDER_ITEM_STAT.";
	
	private ReadBaseDAO readBaseDAOForOracle ;
	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportQueryDO> querySellReportByDate(Map<String, Object> map) throws DaoException {
		return (List<ReportQueryDO>)readBaseDAOForOracle.executeQueryForList(NAMESPACE + "selectSimpleSellReport", map);
	}

	@Override
	public int querySellReportByDateCount(Map<String, Object> map) throws DaoException {
		return (Integer) readBaseDAOForOracle.executeQueryForObject(NAMESPACE + "selectSimpleSellReportCount", map);
	}

	@Override
	public ReportQueryDO querySellReportByDateSum(Map<String, Object> map) throws DaoException {
		return (ReportQueryDO) readBaseDAOForOracle.executeQueryForObject(NAMESPACE + "selectSimpleSellReportCountSum", map);
	}
	
	@Override
	public List<SellItemsReportDO> querySellItemsReportByDate(SellReportQuery sellReportQuery) throws DaoException {
		return (List<SellItemsReportDO>)readBaseDAOForOracle.executeQueryForList(T_ORDER_ITEM_STAT+"selectHotSellItemsReportsList" ,sellReportQuery);
	}

	/**
	 *获取根据商品统计的总记录数
	 */
	@Override
	public Integer querySellItemsReportCount(SellReportQuery sellReportQuery)throws DaoException {
		// TODO Auto-generated method stub
		return (Integer) readBaseDAOForOracle.executeQueryForObject(T_ORDER_ITEM_STAT+"selectHotSellItemsReportsListCount",sellReportQuery);
	}

	@Override
	public List<SellCategoryReportDO> querySellCategoryReportByDate(
			SellReportQuery sellReportQuery) throws DaoException {
		// TODO Auto-generated method stub
		return (List<SellCategoryReportDO>)readBaseDAOForOracle.executeQueryForList(T_ORDER_ITEM_STAT+"selectHotSellCategoryReportsList",sellReportQuery);
	}

	@Override
	public Integer querySellCategoryReportByDateCount(
			SellReportQuery sellReportQuery) throws DaoException {
		// TODO Auto-generated method stub
		return (Integer) readBaseDAOForOracle.executeQueryForObject(T_ORDER_ITEM_STAT+"selectHotSellCategoryReportsListCount",sellReportQuery);
	}

	@Override
	public SellCategoryReportDO sellstatisticsItemAndPriceSum(
			SellReportQuery sellReportQuery) throws DaoException {
		// TODO Auto-generated method stub
		return (SellCategoryReportDO) readBaseDAOForOracle.executeQueryForObject(T_ORDER_ITEM_STAT+"sellstatisticsItemAndPriceSum",sellReportQuery);
	}
}
