package com.yuwang.pinju.domain.report;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.domain.Paginator;

public class SellReportQuery extends Paginator{

	private static final long serialVersionUID = 473274489347888567L;
	private static final String DEFAULT_BY_KEY="ITEM_SUM";
	private static final String FEFAULT_SORT="DESC";
	/**
	 *起始时间
	 */
	private String beginDate;
	private String endDate;
	private String dateCode;
	private String begDate;
	private String displayBeginDate;
	private String displayEndDate;

	/**
	 * 排序的关键字
	 */
	private String orderByKey=DEFAULT_BY_KEY;
	
	/**
	 * 排序的方式  降序--"desc" 升序  --"asc"
	 */
	private String sort=FEFAULT_SORT;	//- 
	 /**
     * 卖家编号 
     */
    private Long sellerId;
   

    /**
     * 买家编号
     */
    private Long buyerId;

    /**
     * 标记是否有数据
     */
    private Boolean isHasData=false;
    
    /**
     * 1表示查询  2表示导出热卖分类统计csv 3表示导出热卖商品统计csv
     */
    private String status;
    
    
    //初始化  默认是现在前七天的统计记录
	public SellReportQuery() {
		super(20);
		String pattern = DateFormatEnum.FORMAT_2.getPattern();
		if (StringUtils.isBlank(this.beginDate)) {
			int amount = SellReportQueryEnum.DEFAULT7DAY.getAmount();
			Date date = DateUtils.addDays(new Date(), amount);
			this.beginDate = DateFormatUtils.format(date, pattern);
		}
		if (StringUtils.isBlank(this.endDate)) {
			Date endDate = DateUtils.addDays(new Date(), -1);
			this.endDate = DateFormatUtils.format(endDate, pattern);
		}
		this.dateCode=SellReportQueryEnum.NEARLY1WEEK.getValue()+"";
	}
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOrderByKey() {
		return orderByKey;
	}

	public void setOrderByKey(String orderByKey) {
		if(StringUtils.isBlank(orderByKey)){
			orderByKey=DEFAULT_BY_KEY;
		}
		this.orderByKey = orderByKey;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		if(StringUtils.isBlank(sort)){
			sort=FEFAULT_SORT;
		}
		this.sort = sort;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Boolean getIsHasData() {
		return isHasData;
	}
	
	public void setIsHasData(Boolean isHasData) {
		this.isHasData = isHasData;
	}

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public String getDisplayBeginDate() {
		return displayBeginDate;
	}

	public String getDisplayEndDate() {
		return displayEndDate;
	}
	
	public void setDisplayBeginDate(String displayBeginDate) {
		this.displayBeginDate = displayBeginDate;
	}
	public void setDisplayEndDate(String displayEndDate) {
		this.displayEndDate = displayEndDate;
	}

	public String getBegDate() {
		return begDate;
	}

	public void setBegDate(String dateCode) {
		//获取时间的 封装
		String pattern=DateFormatEnum.FORMAT_2.getPattern();
		//String patternDisplay=DateFormatEnum.FORMAT_2.getPattern();
		if(StringUtils.isBlank(dateCode)){
			int amount=SellReportQueryEnum.DEFAULT7DAY.getAmount();
			Date date=DateUtils.addDays(new Date(), amount);
			this.begDate=DateFormatUtils.format(date, pattern);
			this.displayBeginDate=DateFormatUtils.format(date, pattern);
		}else{
			Long value=Long.parseLong(dateCode);
			if(value== null) value=0L;
			SellReportQueryEnum typeEnum=SellReportQueryEnum.getType(value);
			Date date=null;
			if(typeEnum.compareTo(SellReportQueryEnum.NEARLY1MONTH)==0){
				date=DateUtils.addMonths(new Date(), typeEnum.getAmount());
			}else if(typeEnum.compareTo(SellReportQueryEnum.NEARLY3DYA)==0){
				date=DateUtils.addDays(new Date(), typeEnum.getAmount());
			}else if(typeEnum.compareTo(SellReportQueryEnum.NEARLY1WEEK)==0){
				date=DateUtils.addWeeks(new Date(), typeEnum.getAmount());
			}else if(typeEnum.compareTo(SellReportQueryEnum.YESTERDAY)==0){
				date=DateUtils.addDays(new Date(), typeEnum.getAmount());
			}else if(typeEnum.compareTo(SellReportQueryEnum.NEARLY3MONTH)==0){
				date=DateUtils.addMonths(new Date(), typeEnum.getAmount());
			}
			if(date!=null){
				this.begDate=DateFormatUtils.format(date, pattern);
				this.displayBeginDate=DateFormatUtils.format(date, pattern);
			}else{
				int amount=SellReportQueryEnum.DEFAULT7DAY.getAmount();
				Date dateDefault=DateUtils.addDays(new Date(), amount);
				this.begDate=DateFormatUtils.format(dateDefault, pattern);
				this.displayBeginDate=DateFormatUtils.format(dateDefault, pattern);
		}
		//设置显示的结束时间
		Date endDate=DateUtils.addDays(new Date(), -1);
		this.displayEndDate=DateFormatUtils.format(endDate, pattern);
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
