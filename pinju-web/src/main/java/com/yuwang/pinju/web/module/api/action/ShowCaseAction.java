package com.yuwang.pinju.web.module.api.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.core.distribute.manager.DistributeChannelItemManager;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.domain.distribute.ShowCaseQueryDO;

/**
 * 格子铺
 * 
 * @author caiwei
 *
 */
public class ShowCaseAction extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = -4360853248522444868L;
	/**
	 * 用户编号
	 */
	private Long memberId;
	/**
	 * 返回结果
	 */
	private Map<String, Object> result;
	
	private Boolean status;
	
	private Integer perPage;
	
	private Integer curPage;
	
	private String channelId;
	
	private Long itemId;
	
	private Integer type;
	
	private String jsoncallback;
	
	@Autowired
	private DistributorManager distributorManager;
	
	@Autowired
	private DistributeChannelItemManager distributeChannelItemManager;
	
	public String show(){
	    ShowCaseQueryDO showCaseQueryDO = distributorManager.findAllowedSoldItems(new ShowCaseQueryDO(this.memberId, this.perPage!=null?this.perPage:10 ,(this.curPage!=null?this.curPage:0)));
	    populateResult(showCaseQueryDO);
	    return "json";
	}

	public String interact(){
		this.status = distributeChannelItemManager.channelItemReputionUpdate(this.channelId, this.itemId, this.type);
		return jsonResult();
	}
	
	public void populateResult(ShowCaseQueryDO showCaseQueryDO){
	    this.result = new HashMap<String, Object>();
	    this.result.put("curPage", showCaseQueryDO.getPage());
	    this.result.put("pages", showCaseQueryDO.getPages());
	    this.result.put("items", showCaseQueryDO.getGridList());
	    this.result.put("size", showCaseQueryDO.getItems());
	    this.result.put("startRow", showCaseQueryDO.getStartRow());
	    this.result.put("endRow", showCaseQueryDO.getEndRow());
	    this.result.put("firstPage", showCaseQueryDO.getFirstPage());
	    this.result.put("lastPage", showCaseQueryDO.getLastPage());
	    this.result.put("perPage", showCaseQueryDO.getItemsPerPage());
	    this.result.put("storeLOGO", showCaseQueryDO.getShopIndex());
	    this.result.put("topImg", showCaseQueryDO.getBannerImg());
	    this.result.put("pubImg", showCaseQueryDO.getAdImg());
	    this.result.put("perDes", showCaseQueryDO.getDescript());
	    this.result.put("channelId", showCaseQueryDO.getChannelIdSec());
	}
	
	/**
	 * 手动输出json字符流
	 * 
	 * @return
	 */
	public String jsonResult(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("status", this.status);
			out.print(this.jsoncallback + "(" + json.toString() + ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setMemberId(Long memberId) {
	    this.memberId = memberId;
	}

	public Map<String, Object> getResult() {
	    return result;
	}

	public void setPerPage(Integer perPage) {
	    this.perPage = perPage;
	}

	public void setCurPage(Integer curPage) {
	    this.curPage = curPage;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setJsoncallback(String jsoncallback) {
		this.jsoncallback = jsoncallback;
	}

}
