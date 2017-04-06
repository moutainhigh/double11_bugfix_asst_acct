package com.yuwang.pinju.web.module.logistics.action;

import java.util.List;
import java.util.Map;
import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.logistics.ao.LogisticsAreaCarriageAO;
import com.yuwang.pinju.core.logistics.ao.LogisticsCityIpAO;
import com.yuwang.pinju.core.logistics.ao.LogisticsTemplateAO;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateQuery;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * Created on 2011-7-22
 * <p>
 *    Discription: 商品发布页面异步显示运费模板列表
 * </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class LogisticsTemplateListAjaxAction extends BaseAction implements Action {
	private static final long serialVersionUID = 2039449576884413350L;

	private LogisticsCityIpAO logisticsCityIpAO;

	public void setLogisticsCityIpAO(LogisticsCityIpAO logisticsCityIpAO) {
		this.logisticsCityIpAO = logisticsCityIpAO;
	}

	/**
	 * execute 使用
	 */
	private LogisticsTemplateAO logisticsTemplateAO;

	/**
	 * execute 使用
	 */
	private LogisticsTemplateQuery query;
	/**
	 * execute 使用
	 */
	private List<LogisticsTemplateDO> templateList;

	/**
	 * execute 使用
	 */
	private int page;

	/**
	 * itemInfoLogistics 使用
	 */
	private List<LogisticsAreaCarriageDO> logisticsAreaCarriageList;

	/**
	 * itemInfoLogistics 使用
	 */
	private Long templateId;

	/**
	 * queryAreaLogisticsInfo 使用
	 */
	private LogisticsAreaCarriageAO logisticsAreaCarriageAO;
	/**
	 * queryAreaLogisticsInfo 使用
	 */
	private String areaName;
	/**
	 * queryAreaLogisticsInfo 使用
	 */
	private Integer areaId;

	private Long logisticsTemplateId;
	
	private String cityName;
	
	private Map<String, String> regionCarriage = null;
	
	/**
	 * queryLogisticsCarriage 使用
	 */
	List<LogisticsCarriageDO> carriageList;
	
	public List<LogisticsCarriageDO> getCarriageList() {
		return carriageList;
	}
	
	public void setCarriageList(List<LogisticsCarriageDO> carriageList) {
		this.carriageList = carriageList;
	}

	public Long getLogisticsTemplateId() {
		return logisticsTemplateId;
	}

	public void setLogisticsTemplateId(Long logisticsTemplateId) {
		this.logisticsTemplateId = logisticsTemplateId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Map<String, String> getRegionCarriage() {
		return regionCarriage;
	}

	public void setRegionCarriage(Map<String, String> regionCarriage) {
		this.regionCarriage = regionCarriage;
	}

	@AssistantPermission(target = "trade", action = "logistics")
	public String execute() {
		
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		query.setSellerId(login.getMasterMemberId());
		
		if (this.getPage() == 0) {
			page = 1;
		}
		if (query == null)
			query = new LogisticsTemplateQuery();
		query.setAction("/logistics/listTemplateAjax.htm");
		query.setItemsPerPage(10);

		int templateCount = logisticsTemplateAO
				.getLogisTicsTemplateCount(query);
		query.setItems(templateCount);
		templateList = logisticsTemplateAO.getLogisTicsTemplateList(query);
		return SUCCESS;
	}

	/**
	 * @Description: 根据模板ID查询地区物流运费信息列表
	 * @author [杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	public String itemInfoLogistics() {
		logisticsAreaCarriageList = logisticsTemplateAO.loadAreaCarriageByTemplate(templateId);
		return SUCCESS;
	}

	/**
	 * @Description: 根据地区ID或地区名称、物流模板ID加载加载物流运费信息
	 * @author [杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	public String queryAreaLogisticsInfo() {
		Integer addressId = Integer.parseInt(areaId.toString().substring(0, 2));
		logisticsAreaCarriageList = logisticsAreaCarriageAO.queryLACarriageDO(templateId, addressId, areaName);
		return SUCCESS;
	}
	
	/**
	 * @Description: 根据模板ID加载物流的默认运费信息
	 * @author [heyong]
	 * @date 2011-8-9 上午08:58:25
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	public String queryLogisticsCarriage(){
		carriageList = logisticsTemplateAO.loadLogisticsCarriage(templateId);
		return SUCCESS;
	}


	public String queryAreaCarriage() {
		this.regionCarriage = logisticsCityIpAO.getRegionCarriage(this.logisticsTemplateId, this.cityName);
		return SUCCESS;
	}

	public LogisticsTemplateQuery getQuery() {
		return query;
	}

	public void setQuery(LogisticsTemplateQuery query) {
		this.query = query;
	}

	public void setLogisticsTemplateAO(LogisticsTemplateAO logisticsTemplateAO) {
		this.logisticsTemplateAO = logisticsTemplateAO;
	}

	public List<LogisticsTemplateDO> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<LogisticsTemplateDO> templateList) {
		this.templateList = templateList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public List<LogisticsAreaCarriageDO> getLogisticsAreaCarriageList() {
		return logisticsAreaCarriageList;
	}

	public void setLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> logisticsAreaCarriageList) {
		this.logisticsAreaCarriageList = logisticsAreaCarriageList;
	}

	public void setLogisticsAreaCarriageAO(LogisticsAreaCarriageAO logisticsAreaCarriageAO) {
		this.logisticsAreaCarriageAO = logisticsAreaCarriageAO;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
}
