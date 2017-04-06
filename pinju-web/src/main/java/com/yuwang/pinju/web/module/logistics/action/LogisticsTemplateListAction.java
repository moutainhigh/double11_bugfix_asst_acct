package com.yuwang.pinju.web.module.logistics.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.logistics.ao.LogisticsTemplateAO;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateQuery;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * <p>列表显示物流运费模板</p>
 * 
 * @author shihongbo
 * @since 2011-07-11
 */
public class LogisticsTemplateListAction extends BaseAction implements Action{

	private static final long serialVersionUID = -5883215680587200908L;

	private LogisticsTemplateAO logisticsTemplateAO;
	
	private LogisticsTemplateQuery query;
	
	private List<LogisticsTemplateDO> templateList;
	
	private Map<String, List<LogisticsTemplateVO>> templateDetailMap;

	@AssistantPermission(target = "trade", action = "logistics")
	public String execute() {

		int currentPage = getInteger("currentPage");
		if (currentPage == 0) {
			currentPage = 1;
		}
		
		if(query == null)
			query = new LogisticsTemplateQuery();
		query.setAction("/logistics/listTemplate.htm");
		query.setItemsPerPage(10);
		query.setPage(currentPage);
		
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		query.setSellerId(login.getMasterMemberId());
		
		int templateCount = logisticsTemplateAO.getLogisTicsTemplateCount(query);
		query.setItems(templateCount);
		

		templateList = logisticsTemplateAO.getLogisTicsTemplateList(query);
		
		templateDetailMap = new HashMap<String, List<LogisticsTemplateVO>>();
		

		
		for(LogisticsTemplateDO template:templateList){
			List<LogisticsTemplateVO> list = new LinkedList<LogisticsTemplateVO>();
			
			Long tid = template.getId();
			
			//加载默认运费
			List<LogisticsCarriageDO> carriageList = logisticsTemplateAO.getLogisticsCarriageList(tid);
			
			//加载地区运费
			List<LogisticsAreaCarriageDO> areaCarriageList = logisticsTemplateAO.loadAreaCarriageByTemplate(tid);
			
			if(areaCarriageList != null){

				//合并相同地区运费
				Map<AreaCarriageVO, String> map = new TreeMap<AreaCarriageVO, String>();
				
				for(LogisticsAreaCarriageDO areaCarriage:areaCarriageList){
					AreaCarriageVO areaCarriageVO = new AreaCarriageVO();
					
					areaCarriageVO.setAreaName(areaCarriage.getAreaName());
					areaCarriageVO.setCarriageIncrease(areaCarriage.getAreaCarriageIncrease());
					areaCarriageVO.setDefaultCarriage(areaCarriage.getAreaCarriage());
					areaCarriageVO.setLogisticsTypeId(areaCarriage.getLogisticsTypeId());
					areaCarriageVO.setLogisticsTypeName(LogisticsCarriageDO.getLogisticsTypeDisplay(areaCarriage.getLogisticsTypeId()));
					areaCarriageVO.setAreaId(areaCarriage.getAreaId());

					String areaname = map.get(areaCarriageVO);
					if(areaname != null){
						areaname = areaname + "," + areaCarriageVO.getAreaName();
					}else{
						areaname = areaCarriageVO.getAreaName();
					}

					map.put(areaCarriageVO, areaname);

				}
				
				//地区运费 加入显示列表
				Iterator<Map.Entry<AreaCarriageVO,String>> iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<AreaCarriageVO, String> entry = (Map.Entry<AreaCarriageVO, String>)iterator.next();
					AreaCarriageVO key = (AreaCarriageVO)entry.getKey();
					String value = (String)entry.getValue();
					
					LogisticsTemplateVO item = new LogisticsTemplateVO();
					item.setAreaName(value);
					
					item.setCarriageIncrease(MoneyUtil.getCentToDollar(key.getCarriageIncrease()));
					item.setDefaultCarriage(MoneyUtil.getCentToDollar(key.getDefaultCarriage()));
					item.setLogisticsTypeName(key.getLogisticsTypeName());
					item.setLogisticsTypeId(key.getLogisticsTypeId());
					item.setAreaId(key.getAreaId());
					
					//System.out.println(value+ key.getLogisticsTypeName());
					
					list.add(item);
				}

			}
			
			//默认运费加入显示列表
			if(carriageList != null){
				for(LogisticsCarriageDO carriage:carriageList){
					LogisticsTemplateVO item = new LogisticsTemplateVO();
					
					item.setCarriageIncrease(MoneyUtil.getCentToDollar(carriage.getCarriageIncrease()));
					item.setDefaultCarriage(MoneyUtil.getCentToDollar(carriage.getDefaultCarriage()));
					item.setLogisticsTypeName(carriage.getLogisticsTypeName());
					item.setLogisticsTypeId(carriage.getLogisticsTypeId());
					
					//其他地区id设置为0,按照地区排序时用于比较
					item.setAreaId(0);
					
					int c = logisticsTemplateAO.getAreaCarriageCount(carriage.getTemplateId(), carriage.getLogisticsTypeId());
					if(c > 0)
						item.setAreaName("其他地区");
					else
						item.setAreaName("全国");
					
					
					list.add(item);
				}
			}
			
			Collections.sort(list);
			templateDetailMap.put("key" + tid, list);
		}

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

	public Map<String, List<LogisticsTemplateVO>> getTemplateDetailMap() {
		return templateDetailMap;
	}

}
