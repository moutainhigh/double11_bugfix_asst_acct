package com.yuwang.pinju.web.module.report.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.report.ao.SellReportAO;
import com.yuwang.pinju.domain.report.ReportQueryDO;
import com.yuwang.pinju.domain.report.SellCategoryReportDO;
import com.yuwang.pinju.domain.report.SellItemsReportDO;
import com.yuwang.pinju.domain.report.SellReportQuery;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.TokenAction;

/**
 * @see
 * <p>Discription: 
 * 	 统计商品和分类报表
 * </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-8-29
 */
public class SellItemAndCategoryReportAction extends TokenAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5586868388725869253L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private SellReportAO sellReportAO;

	private List<SellItemsReportDO> sellItemsReportDOList;
	private List<SellCategoryReportDO> sellCategoryReportDOList;
	private SellReportQuery sellReportQuery;
	private SellCategoryReportDO statisticsItemAndPriceSum;
	
	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	/**
	 * @see <p>
	 *      Discription: 根据条件获取销售统计记录
	 *      </p>
	 * @param
	 * @return Action.SUCCESS
	 * @throws
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String hotSellItemsReportStatistics() {
		if(log.isInfoEnabled()){
			log.info("Entry: SellReportAction.hotSellItemsReportStatistics()");
		}
		try {
			if (sellReportQuery == null)sellReportQuery = new SellReportQuery();
			Long sellerId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
			//sellReportQuery.setBeginDate(");
			sellReportQuery.setSellerId(sellerId);
			sellItemsReportDOList = sellReportAO.querySellItemsReportByDate(sellReportQuery);
			//sellItemsReportDOList=null;
			//Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Entry: SellReportAction.hotSellItemsReportStatistics()## statistics hot seller items error",e);
		}
		return Action.SUCCESS;
	}
	/**
	 * 
	 * @see
	 * <p>Discription: 
	 * 	 平台分类统计
	 * </p>
	 * @return  Action.SUCCESS
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-26
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String hotSellerCategoryStatistics(){
		if(log.isInfoEnabled()){
			log.info("Entry: SellReportAction.hotSellerCategoryStatistics().....");
		}
		try{
			if (sellReportQuery == null)sellReportQuery = new SellReportQuery();
			Long sellerId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
			//sellReportQuery.setBeginDate(");
			sellReportQuery.setSellerId(sellerId);
			//sellReportQuery.setBeginDate("2011-08-01");
			//sellReportQuery.setEndDate("2011-08-29");
			sellCategoryReportDOList=sellReportAO.querySellCategoryReportByDate(sellReportQuery);
			
			//显示总销售额和总销售量  暂时prod中不用
			//statisticsItemAndPriceSum=sellReportAO.sellstatisticsItemAndPriceSum(sellReportQuery);
		}catch (Exception e) {
			// TODO: handle exception
			log.error("Entry: SellReportAction.hotSellerCategoryStatistics()## statistics hot seller category error",e);
		}
		return Action.SUCCESS;
	}
	
	public String importCsvStatistics() {
		if(log.isInfoEnabled()){
			log.info("Entry: SellReportAction.importCsvStatistics().....");
		}
		try{
			if (sellReportQuery == null)sellReportQuery = new SellReportQuery();
			Long sellerId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
			sellReportQuery.setSellerId(sellerId);
			if ("2".equals(sellReportQuery.getStatus())) {
				sellCategoryReportDOList=sellReportAO.querySellCategoryReportByDate(sellReportQuery);
			} else {
				sellItemsReportDOList = sellReportAO.querySellItemsReportByDate(sellReportQuery);
			}
			
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			response.setContentType("application/csv;charset=GBK");
			response.setContentType("application/x-download"); 
			StringBuffer bf = new StringBuffer(); 
			if ("2".equals(sellReportQuery.getStatus())) {
				bf.append("分类名称").append(",").append("销售量").append(",").append("销售金额（元）").append("\n");
				for (SellCategoryReportDO categoryReport : sellCategoryReportDOList) {
					bf.append(categoryReport.getDisplayName()).append(",").append(categoryReport.getItemSum()).append(",").append(categoryReport.getAmountByYuan()).append("\n");
				}
			} else {
				bf.append("商品名称").append(",").append("销售量").append(",").append("销售金额（元）").append("\n");
				for (SellItemsReportDO itemReport : sellItemsReportDOList) {
					bf.append(itemReport.getTitle()).append(",").append(itemReport.getItemSum()).append(",").append(itemReport.getAmountByYuan()).append("\n");
				}
			}
			
			PrintWriter pw = response.getWriter();
			
			try {
				String filenamedisplay = "";
				if ("2".equals(sellReportQuery.getStatus())) {
					filenamedisplay = "热卖分类统计"+sellReportQuery.getBeginDate()+"至"+sellReportQuery.getEndDate()+".csv";
				} else {
					filenamedisplay = "热卖商品统计"+sellReportQuery.getBeginDate()+"至"+sellReportQuery.getEndDate()+".csv";
				}
				
				String agent = request.getHeader("USER-AGENT");
				if (null != agent && -1 != agent.indexOf("MSIE")) {
					filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF8");
				} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
					filenamedisplay =  new String(filenamedisplay.getBytes("UTF-8"),"ISO-8859-1");
				} else {
					filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF8");
				}
				
				response.addHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);
				pw.write(bf.toString()); 
			} catch (UnsupportedEncodingException e) {
				log.error("SellItemAndCategoryReportAction#importCsvStatistics",e);
			} catch (IOException e) {
				log.error("SellItemAndCategoryReportAction#importCsvStatistics",e);
			} finally {
				pw.flush();
				pw.close();
			}
			
		} catch (Exception e) {
			log.error("Entry: SellReportAction.importCsvStatistics()## statistics hot seller category error",e);
		}
		return null;
	}
	
	public String importSellItems() {
		if(log.isInfoEnabled()){
			log.info("Entry: SellReportAction.importSellItems().....");
		}
		try{
			if (sellReportQuery == null)sellReportQuery = new SellReportQuery();
			
			Long sellerId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
			sellReportQuery.setSellerId(sellerId);
			sellItemsReportDOList = sellReportAO.querySellItemsReportByDate(sellReportQuery);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			response.setContentType("application/csv;charset=GBK");
			response.setContentType("application/x-download"); 
			StringBuffer bf = new StringBuffer(); 
			bf.append("商品名称").append(",").append("销售量").append(",").append("销售金额（元）").append("\n");
			for (SellCategoryReportDO categoryReport : sellCategoryReportDOList) {
				bf.append(categoryReport.getDisplayName()).append(",").append(categoryReport.getItemSum()).append(",").append(categoryReport.getAmountByYuan()).append("\n");
			}
			
			PrintWriter pw = response.getWriter();
			
			try {
				String filenamedisplay = "热卖商品统计"+sellReportQuery.getBeginDate()+"至"+sellReportQuery.getEndDate()+".csv";
				
				String agent = request.getHeader("USER-AGENT");
				if (null != agent && -1 != agent.indexOf("MSIE")) {
					filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF8");
				}else if (null != agent && -1 != agent.indexOf("Mozilla")) {
					filenamedisplay =  new String(filenamedisplay.getBytes("UTF-8"),"ISO-8859-1");
				}
				
				response.addHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);
				pw.write(bf.toString()); 
			} catch (UnsupportedEncodingException e) {
				log.error("SellItemAndCategoryReportAction#importSellItems",e);
			} catch (IOException e) {
				log.error("SellItemAndCategoryReportAction#importSellItems",e);
			} finally {
				pw.flush();
				pw.close();
			}
			
		}catch (Exception e) {
			log.error("Entry: SellReportAction.importSellItems()## statistics hot seller category error",e);
		}
		return null;
	}
	
	public SellReportAO getSellReportAO() {
		return sellReportAO;
	}

	public void setSellReportAO(SellReportAO sellReportAO) {
		this.sellReportAO = sellReportAO;
	}

	public List<SellItemsReportDO> getSellItemsReportDOList() {
		return sellItemsReportDOList;
	}

	public void setSellItemsReportDOList(
			List<SellItemsReportDO> sellItemsReportDOList) {
		this.sellItemsReportDOList = sellItemsReportDOList;
	}
	
	public SellReportQuery getSellReportQuery() {
		return sellReportQuery;
	}

	public void setSellReportQuery(SellReportQuery sellReportQuery) {
		this.sellReportQuery = sellReportQuery;
	}

	public List<SellCategoryReportDO> getSellCategoryReportDOList() {
		return sellCategoryReportDOList;
	}

	public void setSellCategoryReportDOList(
			List<SellCategoryReportDO> sellCategoryReportDOList) {
		this.sellCategoryReportDOList = sellCategoryReportDOList;
	}

	public SellCategoryReportDO getStatisticsItemAndPriceSum() {
		return statisticsItemAndPriceSum;
	}

	public void setStatisticsItemAndPriceSum(
			SellCategoryReportDO statisticsItemAndPriceSum) {
		this.statisticsItemAndPriceSum = statisticsItemAndPriceSum;
	}
}


