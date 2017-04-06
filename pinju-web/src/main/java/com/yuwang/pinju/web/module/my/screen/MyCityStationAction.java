package com.yuwang.pinju.web.module.my.screen;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.citystation.ao.CityStationAO;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.domain.citystation.CityOrderDO;
import com.yuwang.pinju.domain.citystation.CityOrderQuery;
import com.yuwang.pinju.domain.citystation.CityStationDO;
import com.yuwang.pinju.web.struts2.LoginChecker;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.struts2.LoginChecker.LoginCheckerResult;

public class MyCityStationAction implements PinjuAction {
	private static Log log = LogFactory.getLog(MyCityStationAction.class);

	private CityStationAO cityStationAO;

	private MemberManager memberManager;

	/**
	 * 查询开始日期
	 */
	private Date startDate;

	/**
	 * 查询结束日期
	 */
	private Date endDate;

	/**
	 * 错误提示信息
	 */
	private String errorMessage;

	/**
	 * 城市分站名称
	 */
	private String cityName;

	/**
	 * 订单总量
	 */
	private Long totalBuyCount = 0L;

	/**
	 * 订单总金额
	 */
	private Long totalBuyPrice = 0L;

	/**
	 * 付款总量
	 */
	private Long totalPayCount = 0L;

	/**
	 * 付款总金额
	 */
	private Long totalPayPrice = 0L;

	/**
	 * 交易成功总量
	 */
	private Long totalSucCount = 0L;

	/**
	 * 交易成功总金额
	 */
	private Long totalSucPrice = 0L;

	private List<CityOrderDO> cityOrderList;

	@Override
	public String execute() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}

		Long memberId = loginChecker.getMemberId();
		if (log.isDebugEnabled()) {
			log.debug("Member Id: " + memberId);
		}

		// 判断是否是城市分站主
		if (!memberManager.findBuyerExt(memberId)) {
			log.debug("该会员非城市分站主：" + memberId);
			return INPUT;
		}

		CityStationDO cityStation = cityStationAO.getCityStationInfo(memberId);
		if (cityStation == null) {
			log.debug("查询该会员所辖城市分站异常：" + memberId);
			errorMessage = "查询您所辖城市分站信息失败！";
			return INPUT;
		}

		cityName = cityStation.getCityName();

		String adCode = cityStation.getAdCode();
		if (adCode == null || "".equals(adCode)) {
			log.debug("查询城市分站广告代码异常：" + memberId);
			errorMessage = "查询您所辖城市分站信息失败！";
			return INPUT;
		}
		log.debug("City Station CODE: " + adCode);

		CityOrderQuery cityOrderQuery = new CityOrderQuery();
		// 只查询15天有效期内的订单
		// cityOrderQuery.setAdCode(adCode.concat("-1"));
		cityOrderQuery.setAdCode(adCode);
		if (startDate == null || endDate == null) {
			initQueryDate();
		}
		cityOrderQuery.setStartDate(startDate);
		cityOrderQuery.setEndDate(endDate);

		log.debug("Query From Start:" + startDate + " To End:" + endDate);
		cityOrderList = cityStationAO.getAllCityOrders(cityOrderQuery);
		log.debug("分站数据记录数：" + cityOrderList.size());
		// 合计
		calculateTotal();

		return SUCCESS;
	}

	private void calculateTotal() {
		if (cityOrderList != null && cityOrderList.size() > 0) {
			for (CityOrderDO cityOrder : cityOrderList) {
				totalBuyCount = totalBuyCount
						+ ((cityOrder.getOrderBuyCount() != null) ? cityOrder
								.getOrderBuyCount() : 0);
				totalBuyPrice = totalBuyPrice
						+ ((cityOrder.getOrderBuyPrice() != null) ? cityOrder
								.getOrderBuyPrice() : 0);
				totalPayCount = totalPayCount
						+ ((cityOrder.getOrderPayCount() != null) ? cityOrder
								.getOrderPayCount() : 0);
				totalPayPrice = totalPayPrice
						+ ((cityOrder.getOrderPayPrice() != null) ? cityOrder
								.getOrderPayPrice() : 0);
				totalSucCount = totalSucCount
						+ ((cityOrder.getOrderSucCount() != null) ? cityOrder
								.getOrderSucCount() : 0);
				totalSucPrice = totalSucPrice
						+ ((cityOrder.getOrderSucPrice() != null) ? cityOrder
								.getOrderSucPrice() : 0);
			}
		}
	}

	/**
	 * 首次进入页面，默认查询本月订单汇总信息
	 */
	private void initQueryDate() {
		// 上一月今日
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		resetTime(calendar);
		startDate = calendar.getTime();
		// 当前日前天
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		resetTime(calendar);
		endDate = calendar.getTime();
	}

	/**
	 * 时间清零
	 * 
	 * @param calendar
	 */
	private void resetTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setCityStationAO(CityStationAO cityStationAO) {
		this.cityStationAO = cityStationAO;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
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

	public String getCityName() {
		return cityName;
	}

	public List<CityOrderDO> getCityOrderList() {
		return cityOrderList;
	}

	public Long getTotalBuyPrice() {
		return totalBuyPrice;
	}

	public Long getTotalPayCount() {
		return totalPayCount;
	}

	public Long getTotalPayPrice() {
		return totalPayPrice;
	}

	public Long getTotalSucCount() {
		return totalSucCount;
	}

	public Long getTotalSucPrice() {
		return totalSucPrice;
	}

	public Long getTotalBuyCount() {
		return totalBuyCount;
	}

}
