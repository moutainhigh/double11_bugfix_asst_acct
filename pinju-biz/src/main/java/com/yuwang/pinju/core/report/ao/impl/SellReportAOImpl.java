package com.yuwang.pinju.core.report.ao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.report.ao.SellReportAO;
import com.yuwang.pinju.core.report.manager.SellReportManager;
import com.yuwang.pinju.domain.report.ReportQueryDO;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.report.ItemCategoryDO;
import com.yuwang.pinju.domain.report.SellCategoryReportDO;
import com.yuwang.pinju.domain.report.SellItemsReportDO;
import com.yuwang.pinju.domain.report.SellReportQuery;

public class SellReportAOImpl extends BaseAO implements SellReportAO {
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private SellReportManager sellReportManager;

	public SellReportManager getSellReportManager() {
		return sellReportManager;
	}

	public void setSellReportManager(SellReportManager sellReportManager) {
		this.sellReportManager = sellReportManager;
	}

	@Override
	public Result querySellReportByDate(Map<String, Object> map) {
		Result result = new ResultSupport();
		try {
			List<ReportQueryDO> list = sellReportManager.querySellReportByDate(map);
			List<ReportQueryDO> nList = new ArrayList<ReportQueryDO>();
			for (ReportQueryDO report : list) {
				report.setOrderItemPriceCount(MoneyUtil.getCentToDollar(Long.parseLong(report.getOrderItemPriceCount())));
				nList.add(report);
			}
			result.setModel("simpleList",nList);
			result.setSuccess(true);
		} catch (ManagerException e) {
			log.error("Event=[SellReportAOImpl#querySellReportByDate] 销售报表查询错误:" , e);
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public int querySellReportByDateCount(Map<String, Object> map) {
		int i=0;
		try {
			i = sellReportManager.querySellReportByDateCount(map);
		} catch (ManagerException e) {
			log.error("Event=[SellReportAOImpl#querySellReportByDate] 销售报表查询错误:" , e);
		}
		return i;
	}

	@Override
	public ReportQueryDO querySellReportByDateSum(Map<String, Object> map) {
		ReportQueryDO report = new ReportQueryDO();
		try {
			report = sellReportManager.querySellReportByDateSum(map);
		} catch (ManagerException e) {
			log.error("Event=[SellReportAOImpl#querySellReportByDate] 销售总额查询错误:" , e);
		}
		return report;
	}
	
	@Override
	public List<SellItemsReportDO> querySellItemsReportByDate(SellReportQuery sellReportQuery) {
		// TODO Auto-generated method stub
		if(log.isInfoEnabled()){
			log.info("Entry : SellReportAOImpl.querySellItemsReportByDate(SellReportQuery sellReportQuery) start");
		}
		List<SellItemsReportDO> sellList=new ArrayList<SellItemsReportDO>();
		try {
			//int page = sellReportQuery.getPage();
			sellReportQuery.setBegDate(sellReportQuery.getDateCode());
			
			if (!"3".equals(sellReportQuery.getStatus())) {
				int items = sellReportManager.querySellItemsReportCount(sellReportQuery);
				sellReportQuery.setItems(items);
				sellReportQuery.setItemsPerPage(20);
				//sellReportQuery.setPage(page);
			}else{
				sellReportQuery.setItemsPerPage(Integer.MAX_VALUE);
			}
			sellList =sellReportManager.querySellItemsReportByDate(sellReportQuery);
			System.out.println(sellReportQuery.getStartRow());
			System.out.println(sellReportQuery.getEndRow());
			if (sellList == null|| sellList.size() == 0) {
				sellReportQuery.setIsHasData(false);
			} else {
				sellReportQuery.setIsHasData(true);
			}
		} catch (ManagerException e) {
		  log.error("Load Seller Report error:", e);
		}
		return sellList;
	}

	@Override
	public List<SellCategoryReportDO> querySellCategoryReportByDate(
			SellReportQuery sellReportQuery) {
		// TODO Auto-generated method stub
		if(log.isInfoEnabled()){
			log.info("Entry : SellReportAOImpl.querySellCategoryReportByDate(SellReportQuery sellReportQuery) start");
		}
		List<SellCategoryReportDO> categoryList=new ArrayList<SellCategoryReportDO>();
		try{
			//int page = sellReportQuery.getPage();
			if (!"2".equals(sellReportQuery.getStatus())) {
				int items = sellReportManager.querySellCategoryReportByDateCount(sellReportQuery);
				sellReportQuery.setItems(items);
				sellReportQuery.setItemsPerPage(20);
			}else{
				sellReportQuery.setItemsPerPage(Integer.MAX_VALUE);
			}
			//sellReportQuery.setPage(page);
			categoryList=sellReportManager.querySellCategoryReportByDate(sellReportQuery);
			if (categoryList == null|| categoryList.size() == 0) {
				sellReportQuery.setIsHasData(false);
			} else {
				sellReportQuery.setIsHasData(true);
				for(SellCategoryReportDO sellCate: categoryList){
					StringBuffer buf=new StringBuffer();
					ItemCategoryDO itemCategoryDO=sellCate.getItemCategoryDO();
					if(itemCategoryDO !=null){
						//if(StringUtils.isNotBlank(sellCate.getItemCategoryDO().getName())){
						//	buf.append(sellCate.getItemCategoryDO().getName());
					 //   }
						if(StringUtils.isNotBlank(sellCate.getItemCategoryDO().getLevel1Name())){
							buf.append(sellCate.getItemCategoryDO().getLevel1Name());
						}
						if(StringUtils.isNotBlank(sellCate.getItemCategoryDO().getLevel2Name())){
							buf.append(">"+sellCate.getItemCategoryDO().getLevel2Name());
						}
						if(StringUtils.isNotBlank(sellCate.getItemCategoryDO().getLevel3Name())){
							buf.append(">"+sellCate.getItemCategoryDO().getLevel3Name());
						}
						if(StringUtils.isNotBlank(sellCate.getItemCategoryDO().getLevel4Name())){
							buf.append(">"+sellCate.getItemCategoryDO().getLevel4Name());
						}
					}else{
						buf.append("其它分类");
					}
					sellCate.setDisplayName(buf.toString());
				}
			}
		}catch (ManagerException e) {
			// TODO: handle exception
			log.error("Entry : SellReportAOImpl.querySellCategoryReportByDate(SellReportQuery sellReportQuery) error:", e);
		}
		return categoryList;
	}

	@Override
	public SellCategoryReportDO sellstatisticsItemAndPriceSum(
			SellReportQuery sellReportQuery) {
		if(log.isInfoEnabled()){
			log.info("Entry : SellReportAOImpl.sellstatisticsItemAndPriceSum(SellReportQuery sellReportQuery) start");
		}
		SellCategoryReportDO sellCategoryReportDO=null;
		try {
			// TODO Auto-generated method stub
			sellCategoryReportDO= sellReportManager.sellstatisticsItemAndPriceSum(sellReportQuery);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Entry : SellReportAOImpl.sellstatisticsItemAndPriceSum(SellReportQuery sellReportQuery) error:", e);
		}
		return sellCategoryReportDO;
	}
}
