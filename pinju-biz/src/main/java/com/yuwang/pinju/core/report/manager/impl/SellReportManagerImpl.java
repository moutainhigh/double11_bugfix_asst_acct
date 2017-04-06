package com.yuwang.pinju.core.report.manager.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.report.dao.SellReportDAO;
import com.yuwang.pinju.core.report.manager.SellReportManager;
import com.yuwang.pinju.domain.report.ReportQueryDO;
import com.yuwang.pinju.domain.report.SellCategoryReportDO;
import com.yuwang.pinju.domain.report.SellItemsReportDO;
import com.yuwang.pinju.domain.report.SellReportQuery;

public class SellReportManagerImpl  extends BaseManager implements SellReportManager {

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private SellReportDAO sellReportDAO;
	
	@Override
	public List<ReportQueryDO> querySellReportByDate(Map<String, Object> map) throws ManagerException {
		try {
			return sellReportDAO.querySellReportByDate(map);
		} catch (DaoException e) {
			throw new ManagerException("销售报表查询错误:", e);
		}
	}
	
	@Override
	public int querySellReportByDateCount(Map<String, Object> map)
			throws ManagerException {
		try {
			return sellReportDAO.querySellReportByDateCount(map);
		} catch (DaoException e) {
			throw new ManagerException("销售报表查询分页总数错误:", e);
		}
	}

	@Override
	public ReportQueryDO querySellReportByDateSum(Map<String, Object> map) throws ManagerException {
		try {
			return sellReportDAO.querySellReportByDateSum(map);
		} catch (DaoException e) {
			throw new ManagerException("销售报表销售总额查询错误:", e);
		}
	}

	public SellReportDAO getSellReportDAO() {
		return sellReportDAO;
	}

	public void setSellReportDAO(SellReportDAO sellReportDAO) {
		this.sellReportDAO = sellReportDAO;
	}

	/**
	 * 根据条件获取一页的统计 记录数
	 */
	@Override
	public List<SellItemsReportDO> querySellItemsReportByDate(SellReportQuery sellReportQuery) throws ManagerException {
		try {
			// TODO Auto-generated method stub
			return sellReportDAO.querySellItemsReportByDate(sellReportQuery);
		} catch (DaoException e) {
			throw new ManagerException("Event=[SellReportManager#querySellItemsReportByDate]统计商品销售报表信息",  e);
			// TODO: handle exception
		}
	}
	
	/**
	 * 获取统计记录的总记录数
	 * 
	 */
	@Override
	public int querySellItemsReportCount(SellReportQuery sellReportQuery)
			throws ManagerException {
		try{
			Integer count=sellReportDAO.querySellItemsReportCount(sellReportQuery);
			if(count!=null) return count;
		}catch (DaoException e) {
			throw new ManagerException("Event=[SellReportManager#querySellItemsReportCount]统计商品销售报表信息",  e);
		}
		return 0;
	}

	/**
	 * 获取统计分类的记录数
	 * @author Colley Ma
	 */
	@Override
	public List<SellCategoryReportDO> querySellCategoryReportByDate(
			SellReportQuery sellReportQuery) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return sellReportDAO.querySellCategoryReportByDate(sellReportQuery);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("Event=[SellReportManager#querySellCategoryReportByDate]统计分类销售报表信息",  e);	
		}
	}

	/**
	 * 获取统计分类的总记录数
	 * @author Colley Ma
	 */
	@Override
	public int querySellCategoryReportByDateCount(
			SellReportQuery sellReportQuery) throws ManagerException {
		try{
			Integer count=sellReportDAO.querySellCategoryReportByDateCount(sellReportQuery);
			if(count !=null) return count.intValue();
		}catch (Exception e) {
			// TODO: handle exception
			throw new ManagerException("Event=[SellReportManager#querySellCategoryReportByDateCount]统计分类销售报表信息",  e);
		}
		return 0;
	}

	@Override
	public SellCategoryReportDO sellstatisticsItemAndPriceSum(
			SellReportQuery sellReportQuery) throws ManagerException {
		// TODO Auto-generated method stub
		try{
			return sellReportDAO.sellstatisticsItemAndPriceSum(sellReportQuery);
		}catch (Exception e) {
			// TODO: handle exception
			throw new ManagerException("Event=[SellReportManagerImpl#SellCategoryReportDO]卖家统计总的销售量和销售额信息",  e);
		}
	}
}
