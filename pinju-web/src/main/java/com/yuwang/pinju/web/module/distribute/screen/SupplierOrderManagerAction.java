/**
 * 
 */
package com.yuwang.pinju.web.module.distribute.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.ObjectUtils;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.distribute.ao.DistributeChannelAO;
import com.yuwang.pinju.core.distribute.ao.DistributeGoodsManagerAO;
import com.yuwang.pinju.core.distribute.ao.DistributeOrdersManagerAO;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.query.QueryDistributeOrder;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * @author xiazhenyu
 * 
 */
public class SupplierOrderManagerAction extends SupplierApplyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6076084688771747193L;

	/**
	 * 分销商AO
	 */
	private DistributeChannelAO distributeChannelAO;

	/**
	 * 订单AO
	 */
	private DistributeOrdersManagerAO distributeOrdersManagerAO;

	/**
	 * 分销商品AO
	 */
	private DistributeGoodsManagerAO distributeGoodsManagerAO;

	/**
	 * 商品总数
	 */
	private Integer itemCount;

	/**
	 * 分销/供应商总数
	 */
	private Integer distributeCount;
	
	private Long selleredCount;

	/**
	 * 分销/供应商条件选择List
	 */
	private List selectList;

	/**
	 * 分销商/供应商页面区分 0:分销商 1:供应商
	 */
	private Integer type;

	private Map<Integer, ShopInfoDO> shopInfoMap;
	
	/**
	 * 查询条件
	 */
	private QueryDistributeOrder queryParam;

	/**
	 * 分页条件
	 */
	private Paginator query;

	/**
	 * 订单List
	 */
	private List pageList;

	/**
	 * 分页的当前页码
	 */
	private Integer currentPage;

	/**
	 * 
	 * @return
	 */
	public String getOrders() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == type) {
		    if (request.getParameter("type") != null) {
			type = Integer.parseInt(request.getParameter("type"));
		    }else {
			type = 0;
		    }
		}
		// 供应商
		if (ObjectUtils.nullSafeEquals(type, new Integer(1))) {
			if (!checkIsSupplier(getUserId())) {
				return NOT_APPLY;
			}
			itemCount = distributeGoodsManagerAO.getDistributeGoodsCount(distributeSupplierDO.getId());
			distributeCount = distributeChannelAO.getDistributeCount(distributeSupplierDO.getId());
			selectList = distributeChannelAO.findDistributeList(new DistribureChannelParamDO(distributeSupplierDO
					.getId()));
			prepareQuery(distributeSupplierDO.getMemberId());
		}
		// 分销商
		else {
			// check is a distributor or not
			DistributeDistributorDO dddo = distributeOrdersManagerAO.getDistributorId(getUserId());
			if (null == dddo) {
				return ERROR;
			}
			itemCount = distributeOrdersManagerAO.getDistributeItemCount(dddo.getId());
			DistribureChannelParamDO param = new DistribureChannelParamDO(dddo.getId(), (short) 1);
			distributeCount = distributeOrdersManagerAO.getDistributeSupplierCount(param);
			selectList = distributeOrdersManagerAO.getDistributeSuppliers(param);
			List<Integer> ids = new ArrayList<Integer>(selectList.size());
			for (DistributeSupplierDO distributeSupplierDO : (List<DistributeSupplierDO>)selectList) {
				ids.add(distributeSupplierDO.getShopId());
			}
			shopInfoMap = distributorAO.querySupplierListById((List<Integer>)ids);
			prepareQuery(dddo.getMemberId());
		}
		getOrderList();
		return SUCCESS;
	}

	private void prepareQuery(Long id) {
		if (null == this.queryParam) {
			this.queryParam = new QueryDistributeOrder();
		}
		this.queryParam.setPage(currentPage == null ? 1 : currentPage);
		if (ObjectUtils.nullSafeEquals(type, new Integer(1))) {
			this.queryParam.setAction("/supplier/getOrders.htm");
			this.queryParam.setSupplierId(id);
		} else {
			this.queryParam.setAction("/distributor/getOrders.htm");
			this.queryParam.setChannelIds(id);
		}
	}

	@SuppressWarnings("unchecked")
	private void getOrderList() {
		// order query
		this.queryParam.setType(type);
		Result result = distributeOrdersManagerAO.queryDistributeOrderList(this.queryParam);
		if (result != null) {
			Object object = result.getModel("orderChannelList");
			if (object != null) {
				pageList = (List<OrderChannelDO>) object;
			}else {
				pageList = new ArrayList<OrderChannelDO>(0);
			}
//			object = result.getModel("shopIds");
//			if (object != null) {
//				shopInfoMap = distributorAO.querySupplierListById((List<Integer>)object);
//			}
			this.selleredCount = result.getModel("selleredCount")!=null?(Long)result.getModel("selleredCount"):0;
		    if (result.getModel("num") != null) {
		    	this.queryParam.setItems(((Long) result.getModel("num")).intValue());
		    }
		}
		setQuery(this.queryParam);
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Integer getDistributeCount() {
		return distributeCount;
	}

	public void setDistributeCount(Integer distributeCount) {
		this.distributeCount = distributeCount;
	}

	public List getSelectList() {
		return selectList;
	}

	public void setSelectList(List selectList) {
		this.selectList = selectList;
	}

	public QueryDistributeOrder getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(QueryDistributeOrder queryParam) {
		this.queryParam = queryParam;
	}

	public Paginator getQuery() {
		return query;
	}

	public void setQuery(Paginator query) {
		this.query = query;
	}

	public String getUserName() {
		return CookieLoginInfo.getCookieLoginInfo().getNickname();
	}

	public void setDistributeChannelAO(DistributeChannelAO distributeChannelAO) {
		this.distributeChannelAO = distributeChannelAO;
	}

	public void setDistributeOrdersManagerAO(DistributeOrdersManagerAO distributeOrdersManagerAO) {
		this.distributeOrdersManagerAO = distributeOrdersManagerAO;
	}

	public void setDistributeGoodsManagerAO(DistributeGoodsManagerAO distributeGoodsManagerAO) {
		this.distributeGoodsManagerAO = distributeGoodsManagerAO;
	}

	public List getPageList() {
		return pageList;
	}

	public void setPageList(List pageList) {
		this.pageList = pageList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Map<Integer, ShopInfoDO> getShopInfoMap() {
		return shopInfoMap;
	}

	public Long getSelleredCount() {
		return selleredCount;
	}

}
