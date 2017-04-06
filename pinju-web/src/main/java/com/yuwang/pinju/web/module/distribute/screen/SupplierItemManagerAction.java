/**
 * 
 */
package com.yuwang.pinju.web.module.distribute.screen;

import java.util.ArrayList;
import java.util.List;

import com.yuwang.pinju.core.distribute.ao.DistributeGoodsManagerAO;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;

/**
 * @author xiazhenyu
 * 
 */
public class SupplierItemManagerAction extends SupplierApplyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8207961711720237800L;

	private DistributeGoodsManagerAO distributeGoodsManagerAO;
	/**
	 * 商品列表
	 */
	private List<DistrbuteSupplierItemDO> pageList;
	/**
	 * 总条数（商品列表）
	 */
	private Integer allCount;

	/**
	 * 已分销商品总数
	 */
	private Integer distributedItemCount;
	/**
	 * 分页的当前页码
	 */
	private Integer currentPage;

	/**
	 * 页面TAB FLAG 0:全部商品tab 1:未分销商品tab
	 */
	private int pageType = 0;

	/**
	 * 分页信息
	 */
	private Paginator query;

	/**
	 * 分销商信息查询参数
	 */
	private ItemQueryEx itemQuery;

	/**
	 * 取得全部商品action
	 * 
	 * @return
	 */
	public String getAllItem() {
		if (!checkIsSupplier(getUserId())) {
			return NOT_APPLY;
		}
		// tab flag 设为全部商品tab
		pageType = 0;
		initCount();
		initAllItemQueryParam();
		pageList = distributeGoodsManagerAO.getAllGoods(distributeSupplierDO.getId(), itemQuery);
		setQuery(itemQuery);
		return SUCCESS;
	}

	/**
	 * 保存商品
	 * 
	 * @return
	 */
	public String saveItems() {
		distributeGoodsManagerAO.saveDistributeGoods(pageList);
		initCount();
		initAllItemQueryParam();
		pageList = distributeGoodsManagerAO.getAllGoods(distributeSupplierDO.getId(), itemQuery);
		setQuery(itemQuery);
		pageType = 0;
		return SUCCESS;
	}

	/**
	 * 初始化全部商品查询条件
	 */
	private void initAllItemQueryParam() {
		if (null == this.itemQuery) {
			this.itemQuery = new ItemQueryEx();
		}
		this.itemQuery.setSellerId(getUserId());
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(0);
		statusList.add(1);
		this.itemQuery.setStatus(statusList);
		this.itemQuery.setItems(allCount);
		this.itemQuery.setItemsPerPage(10);
		this.itemQuery.setAction("/supplier/getAllItem.htm");
		this.itemQuery.setPage(currentPage == null ? 1 : currentPage);
	}

	/**
	 * 取得已分销商品action
	 * 
	 * @return
	 */
	public String getDistributeItem() {
		if (!checkIsSupplier(getUserId())) {
			return NOT_APPLY;
		}
		pageType = 1;
		initCount();
		initDistributeItemQueryParam();
		pageList = distributeGoodsManagerAO.getDistributeGoods(distributeSupplierDO.getId(), itemQuery);
		setQuery(itemQuery);
		return SUCCESS;
	}

	/**
	 * 初始化已分销商品查询条件
	 */
	private void initDistributeItemQueryParam() {
		if (null == this.itemQuery) {
			this.itemQuery = new ItemQueryEx();
		}
		this.itemQuery.setSellerId(getUserId());
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(0);
		statusList.add(1);
		this.itemQuery.setStatus(statusList);
		this.itemQuery.setItems(distributedItemCount);
		this.itemQuery.setItemsPerPage(10);
		this.itemQuery.setAction("/supplier/getDistributeItem.htm");
		this.itemQuery.setPage(currentPage == null ? 1 : currentPage);
	}

	private void initCount() {
		allCount = distributeGoodsManagerAO.getAllGoodsCount(getUserId());
		distributedItemCount = distributeGoodsManagerAO.getDistributeGoodsCount(distributeSupplierDO.getId());
	}

	public int getPageType() {
		return pageType;
	}

	public void setPageType(int pageType) {
		this.pageType = pageType;
	}

	public DistributeGoodsManagerAO getDistributeGoodsManagerAO() {
		return distributeGoodsManagerAO;
	}

	public void setDistributeGoodsManagerAO(DistributeGoodsManagerAO distributeGoodsManagerAO) {
		this.distributeGoodsManagerAO = distributeGoodsManagerAO;
	}

	public List<DistrbuteSupplierItemDO> getPageList() {
		return pageList;
	}

	public void setPageList(List<DistrbuteSupplierItemDO> pageList) {
		this.pageList = pageList;
	}

	public Integer getAllCount() {
		return allCount;
	}

	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}

	public Paginator getQuery() {
		return query;
	}

	public <T extends Paginator> void setQuery(T t) {
		this.query = t;
	}

	public ItemQueryEx getItemQuery() {
		return itemQuery;
	}

	public void setItemQuery(ItemQueryEx itemQuery) {
		this.itemQuery = itemQuery;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getDistributedItemCount() {
		return distributedItemCount;
	}

	public void setDistributedItemCount(Integer distributedItemCount) {
		this.distributedItemCount = distributedItemCount;
	}

}
