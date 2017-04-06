package com.yuwang.pinju.web.module.report.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.report.ao.SellReportAO;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.report.ReportQueryDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;

public class SellReportAction extends BaseAction {

	private SellReportAO sellReportAO; 
	
	private List<ReportQueryDO> reportList;
	
	private Paginator paginator;
	
	private String flag;
	private String startDate;
	private String endDate;
	
	private long buyNumSum;
	private String orderItemPriceSum;
	
	private String timegp;
	private String year;
	private String month;
	
	private String orderField;
	private String orderStatus;
	
	public String importCSV() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/csv;charset=GBK");
		response.setContentType("application/x-download"); 
		
		//获取数据
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ReportQueryDO> list = new ArrayList<ReportQueryDO>();
		
		Long sellerId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
		paramMap.put("sellerId", sellerId);
		
		if (this.getOrderField() == null || "".equals(this.getOrderField())) {
			this.setOrderField("state_modify_time");
			this.setOrderStatus("desc");
		}
		paramMap.put("orderField", this.getOrderField());
		paramMap.put("orderStatus", this.getOrderStatus());
		Result result = null;
		if (flag != null && "day".equals(flag)) {
			paramMap.put("state_modify_time", "state_modify_time");
			paramMap.put("startDate", this.getStartDate());
			paramMap.put("endDate", this.getEndDate());
			
			paramMap.put("startNum", "1");
			paramMap.put("endNum",sellReportAO.querySellReportByDateCount(paramMap));

			result = sellReportAO.querySellReportByDate(paramMap);
			list = (List<ReportQueryDO>) result.getModel("simpleList");
			this.setReportList(list);
		} else if ("year".equals(flag)) {
			if (month!= null && !"".equals(month)) {
				paramMap.put("state_modify_time", "state_modify_time");
				Calendar cal = Calendar.getInstance();     
		        cal.set(Calendar.YEAR, Integer.parseInt(year));     
		        cal.set(Calendar.MONTH, Integer.parseInt(month));
		        //本月第一天
		        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE)); 
		        this.setStartDate(DateUtil.formatDate(cal.getTime()));
		        //本月最后一天
		        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		        this.setEndDate(DateUtil.formatDate(cal.getTime()));
			} else {
				paramMap.put("orderField", "to_char(state_modify_time,'yyyy-MM')");
				paramMap.put("state_modify_time", "to_char(state_modify_time,'yyyy-MM')");
				//本年第一天
				this.setStartDate(this.getYear()+"-01-01");
				//获取当年最后一天
				Calendar calendar = Calendar.getInstance();
		        calendar.clear();  
		        calendar.set(Calendar.YEAR, Integer.parseInt(year));  
		        calendar.roll(Calendar.DAY_OF_YEAR, -1); 
		        this.setEndDate(DateUtil.formatDate(calendar.getTime()));
			}
			paramMap.put("startDate", this.getStartDate());
			paramMap.put("endDate", this.getEndDate());
			
			paramMap.put("startNum", "1");
			paramMap.put("endNum",sellReportAO.querySellReportByDateCount(paramMap));

			result = sellReportAO.querySellReportByDate(paramMap);
			list = (List<ReportQueryDO>) result.getModel("simpleList");
			this.setReportList(list);
		}
		//======over
		
		StringBuffer bf = new StringBuffer(); 
		bf.append("日期").append(",").append("销售量").append(",").append("销售金额（元）").append("\n");
		
		for (ReportQueryDO report : list) {
			String date = "";
			if ((!"year".equals(flag)  && "".equals(month)) || ("year".equals(flag) && !"".equals(month))) {
				date = DateUtil.formatDate(DateUtil.parse("yyyy-MM-dd", report.getStateModifyTime()));
			}
			if ("year".equals(flag) && ("".equals(month) || month == null)) {
				date = DateUtil.formatDate("yyyy-MM",DateUtil.parse("yyyy-MM", report.getStateModifyTime()));
			}
			bf.append("".equals(date)?report.getStateModifyTime():" "+date).append(",").append(report.getBuyNumCount()).append(",").append(report.getOrderItemPriceCount()).append("\n");
		}
		
		try {
			PrintWriter pw = response.getWriter();
			
			try {
				String filenamedisplay = "销售简表"+this.getStartDate()+"至"+this.getEndDate()+".csv";
				
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
				log.error("SellReportAction#importCSV",e);
			} catch (IOException e) {
				log.error("SellReportAction#importCSV",e);
			} finally {
				pw.flush();
				pw.close();
			}
		} catch(IOException e) {
			log.error("SellReportAction#importCSV",e);
		}
		return null;
	}
	
	public String execute() {
		if (paginator == null) paginator = new Paginator();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if (flag == null || "".equals(flag)) {
			//根据当前日期向前推7天
			this.setStartDate(DateUtil.formatDate(DateUtil.decrementDay(new Date(), 7)));
			//根据当前日期向前推1天
			this.setEndDate(DateUtil.formatDate(DateUtil.decrementDay(new Date(), 1)));
			this.setTimegp("7");
			flag = "day";
		}
		
		//获取卖家id
		Long sellerId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
		paramMap.put("sellerId", sellerId);
		
		if (this.getOrderField() == null || "".equals(this.getOrderField())) {
			this.setOrderField("state_modify_time");
			this.setOrderStatus("desc");
		}
		paramMap.put("orderField", this.getOrderField());
		paramMap.put("orderStatus", this.getOrderStatus());
		
		Result result = new ResultSupport();
		if (flag != null && "day".equals(flag)) {
			paramMap.put("state_modify_time", "state_modify_time");
			paramMap.put("startDate", this.getStartDate());
			paramMap.put("endDate", this.getEndDate());
			
			paginator.setItems(sellReportAO.querySellReportByDateCount(paramMap));
			paginator.setItemsPerPage(20);
			paramMap.put("startNum", paginator.getStartRow());
			paramMap.put("endNum",paginator.getEndRow());

			ReportQueryDO report = sellReportAO.querySellReportByDateSum(paramMap);
			
			if (report.getBuyNumSum() != null) {
				this.setBuyNumSum(report.getBuyNumSum());
			} else {
				this.setBuyNumSum(0);
			}
			if (report.getOrderItemPriceSum() != null) {
				this.setOrderItemPriceSum(MoneyUtil.getCentToDollar(report.getOrderItemPriceSum()));
			} else {
				this.setOrderItemPriceSum("0");
			}
			
			result = sellReportAO.querySellReportByDate(paramMap);
			List<ReportQueryDO> list = (List<ReportQueryDO>) result.getModel("simpleList");
			this.setReportList(list);
			this.setMonth("");
		} else if ("year".equals(flag)) {
			if (month!= null && !"".equals(month)) {
				paramMap.put("state_modify_time", "state_modify_time");
				Calendar cal = Calendar.getInstance();     
		        cal.set(Calendar.YEAR, Integer.parseInt(year));     
		        cal.set(Calendar.MONTH, Integer.parseInt(month));
		        //本月第一天
		        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE)); 
		        this.setStartDate(DateUtil.formatDate(cal.getTime()));
		        //本月最后一天
		        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		        this.setEndDate(DateUtil.formatDate(cal.getTime()));
			} else {
				paramMap.put("orderField", "to_char(state_modify_time,'yyyy-MM')");
				paramMap.put("state_modify_time", "to_char(state_modify_time,'yyyy-MM')");
				//本年第一天
				this.setStartDate(this.getYear()+"-01-01");
				//获取当年最后一天
				Calendar calendar = Calendar.getInstance();
		        calendar.clear();  
		        calendar.set(Calendar.YEAR, Integer.parseInt(year));  
		        calendar.roll(Calendar.DAY_OF_YEAR, -1); 
		        this.setEndDate(DateUtil.formatDate(calendar.getTime()));
			}
			paramMap.put("startDate", this.getStartDate());
			paramMap.put("endDate", this.getEndDate());
			
			paginator.setItems(sellReportAO.querySellReportByDateCount(paramMap));
			paginator.setItemsPerPage(20);
			paramMap.put("startNum", paginator.getStartRow());
			paramMap.put("endNum",paginator.getEndRow());

			ReportQueryDO report = sellReportAO.querySellReportByDateSum(paramMap);
			
			if (report.getBuyNumSum() != null) {
				this.setBuyNumSum(report.getBuyNumSum());
			} else {
				this.setBuyNumSum(0);
			}
			if (report.getOrderItemPriceSum() != null) {
				this.setOrderItemPriceSum(MoneyUtil.getCentToDollar(report.getOrderItemPriceSum()));
			} else {
				this.setOrderItemPriceSum("0");
			}
			
			result = sellReportAO.querySellReportByDate(paramMap);
			List<ReportQueryDO> list = (List<ReportQueryDO>) result.getModel("simpleList");
			this.setReportList(list);
		}
		
		if (result.isSuccess()) {
			return Action.SUCCESS;
		} else {
			return Action.ERROR;
		}
	}
	
	
	
	public static void main(String[] args) {
//		System.out.println(DateUtil.formatDate(DateUtil.decrementDay(new Date(), 7)));
//		System.out.println(DateUtil.formatDate(new Date()));
		
//		Calendar cal = Calendar.getInstance();     
//        cal.set(Calendar.YEAR, 2011);     
//        cal.set(Calendar.MONTH, 0);
//        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
//        System.err.println(DateUtil.formatDate(cal.getTime()));
//        
//        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE)); 
//        System.err.println(DateUtil.formatDate(cal.getTime()));
        
		//获取当年最后一天
//		Calendar calendar = Calendar.getInstance();
//        calendar.clear();  
//        calendar.set(Calendar.YEAR, 2011);  
//        calendar.roll(Calendar.DAY_OF_YEAR, -1); 
//        System.err.println((DateUtil.formatDate(calendar.getTime())));
		System.out.println(DateUtil.formatDate(DateUtil.parse("yyyy-MM-dd", "2011-8-26  0:00:00")));
		
	}


	public SellReportAO getSellReportAO() {
		return sellReportAO;
	}

	public void setSellReportAO(SellReportAO sellReportAO) {
		this.sellReportAO = sellReportAO;
	}


	public List<ReportQueryDO> getReportList() {
		return reportList;
	}


	public void setReportList(List<ReportQueryDO> reportList) {
		this.reportList = reportList;
	}


	public Paginator getPaginator() {
		return paginator;
	}


	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getBuyNumSum() {
		return buyNumSum;
	}

	public void setBuyNumSum(long buyNumSum) {
		this.buyNumSum = buyNumSum;
	}

	public String getOrderItemPriceSum() {
		return orderItemPriceSum;
	}

	public void setOrderItemPriceSum(String orderItemPriceSum) {
		this.orderItemPriceSum = orderItemPriceSum;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTimegp() {
		return timegp;
	}

	public void setTimegp(String timegp) {
		this.timegp = timegp;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}



}
