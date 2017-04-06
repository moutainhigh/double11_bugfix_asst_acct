/**
 * 
 */
package com.yuwang.pinju.web.module.distribute.screen;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.yuwang.pinju.core.distribute.ao.DistributeChannelAO;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;

/**
 * @author xiazhenyu
 * 
 */
public class SupplierDistributeManagerAction extends SupplierApplyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5840665135265589044L;

	private DistributeChannelAO distributeChannelAO;

	/**
	 * 申请数
	 */
	private Integer applierNum;

	/**
	 * 合作数
	 */
	private Integer cooperatorNum;

	/**
	 * 过期数
	 */
	private Integer expiredNum;

	/**
	 * 显示List
	 */
	private List<DistribureChannelDO> pageList;

	/**
	 * 分页信息
	 */
	private Paginator query;

	/**
	 * 分页的当前页码
	 */
	private Integer currentPage;

	/**
	 * 参数
	 */
	private DistribureChannelParamDO queryParam;

	/**
	 * 页面TAB FLAG 0:待审核tab页 1:合作中tab页 2:终止合作tab页
	 */
	private Integer pageType = 0;

	/**
	 * 页面单选序号
	 */
	private Integer index;

	/**
	 * 页面单选分销商昵称
	 */
	private String nickName;

	private static final String APPLY_ACTION_URL = "/supplier/getApplier.htm";
	private static final String COOP_ACTION_URL = "/supplier/getCooperator.htm";
	private static final String EXPIRED_ACTION_URL = "/supplier/getExpire.htm";

	@Autowired
	private DistributorManager distributorManager;
	
	/**
	 * 取得申请中分销商action
	 * 
	 * @return
	 */
	public String getApplier() {
		if (!checkIsSupplier(getUserId())) {
			return NOT_APPLY;
		}
		pageType = 0;
		getAllCount();
		initQuaryParam(applierNum, APPLY_ACTION_URL);
		pageList = distributeChannelAO.queryApplyDistribute(this.queryParam);
		return SUCCESS;
	}

	/**
	 * 取得合作中分销商action
	 * 
	 * @return
	 */
	public String getCooperator() {
		if (!checkIsSupplier(getUserId())) {
			return NOT_APPLY;
		}
		pageType = 1;
		getAllCount();
		initQuaryParam(cooperatorNum, COOP_ACTION_URL);
		pageList = distributeChannelAO.queryDistributeList(this.queryParam);
		return SUCCESS;
	}

	/**
	 * 取得合同终止分销商
	 * 
	 * @return
	 */
	public String getExpire() {
		if (!checkIsSupplier(getUserId())) {
			return NOT_APPLY;
		}
		pageType = 2;
		getAllCount();
		initQuaryParam(expiredNum, EXPIRED_ACTION_URL);
		pageList = distributeChannelAO.queryExpiredDistribute(this.queryParam);
		return SUCCESS;
	}

	private void initQuaryParam(int count, String actionUrl) {
		if (null == this.queryParam) {
			this.queryParam = new DistribureChannelParamDO();
		}
		this.queryParam.setSupplierId(distributeSupplierDO.getId());
		this.queryParam.setItems(count);
		this.queryParam.setAction(actionUrl);
		this.queryParam.setPage(currentPage == null ? 1 : currentPage);
		this.query = this.queryParam;
	}

	private void getAllCount() {
		applierNum = distributeChannelAO.getApplyDistributeCount(distributeSupplierDO.getId());
		cooperatorNum = distributeChannelAO.getDistributeCount(distributeSupplierDO.getId());
		expiredNum = distributeChannelAO.getExpiredDistributeCount(distributeSupplierDO.getId());
	}

	/**
	 * 同意分销商申请Action
	 * 
	 * @return
	 */
	public String pass() {
		if (null == index) {
			distributeChannelAO.setDistributePass(pageList);
		} else {
			distributeChannelAO.setDistributePass(pageList.get(index));
		}
		getApplier();
		return SUCCESS;
	}

	/**
	 * 拒绝分销商Action
	 * 
	 * @return
	 */
	public String refuse() {
		if (null == index) {
			distributeChannelAO.setDistributePass(pageList);
		} else {
			distributeChannelAO.setDistributePass(pageList.get(index));
		}
		getApplier();
		return SUCCESS;
	}

	/**
	 * 显示分销商授权页Action
	 * 
	 * @return
	 */
	public String popup() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null != request.getParameter("index")) {
			try {
				index = Integer.valueOf(request.getParameter("index"));
				Long memberId = Long.valueOf(request.getParameter("name"));
				DistributeDistributorDO distributeDistributorDO = distributorManager.findDistributorByMemberId(memberId);
				if (distributeDistributorDO != null && StringUtils.hasText(distributeDistributorDO.getNickName())) {
					this.nickName = distributeDistributorDO.getNickName();
				}
			} catch (NumberFormatException e) {
				return ERROR;
			}
		}
		return SUCCESS;
	}

	public void setDistributeChannelAO(DistributeChannelAO distributeChannelAO) {
		this.distributeChannelAO = distributeChannelAO;
	}

	public Integer getApplierNum() {
		return applierNum;
	}

	public void setApplierNum(Integer applierNum) {
		this.applierNum = applierNum;
	}

	public Integer getCooperatorNum() {
		return cooperatorNum;
	}

	public void setCooperatorNum(Integer cooperatorNum) {
		this.cooperatorNum = cooperatorNum;
	}

	public Integer getExpiredNum() {
		return expiredNum;
	}

	public void setExpiredNum(Integer expiredNum) {
		this.expiredNum = expiredNum;
	}

	public Paginator getQuery() {
		return query;
	}

	public void setQuery(Paginator query) {
		this.query = query;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public DistribureChannelParamDO getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(DistribureChannelParamDO queryParam) {
		this.queryParam = queryParam;
	}

	public DistributeChannelAO getDistributeChannelAO() {
		return distributeChannelAO;
	}

	public Integer getPageType() {
		return pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}

	public List<DistribureChannelDO> getPageList() {
		return pageList;
	}

	public void setPageList(List<DistribureChannelDO> pageList) {
		this.pageList = pageList;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
