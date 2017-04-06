package com.yuwang.pinju.domain.citystation;

import java.io.Serializable;
import java.util.Date;

import com.yuwang.pinju.domain.BaseQuery;

/**
 * 分站订单详细查询对象
 * 
 * @author qiuhongming
 *
 */
public class CityOrderQuery extends BaseQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5287539056870407145L;

	/**
	 * 分站代码
	 */
	private String adCode;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	public CityOrderQuery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
