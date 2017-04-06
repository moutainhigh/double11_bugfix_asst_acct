package com.yuwang.pinju.web.module.order.action;

import com.yuwang.pinju.core.item.ao.ItemSalesStatAO;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * @Discription: 向商品组提供商品隔夜购买数量Action
 * @Project: pinju-web
 * @Package: com.yuwang.pinju.web.module.order.action
 * @Title: QueryAjaxOrderAction.java
 * @author: [贺泳]
 * @date 2011-10-21 上午11:58:30
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class QueryAjaxOrderAction extends BaseAction{
	/**
	 * @Description: serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品Id
	 */
	private Long itemId;
	/**
	 * 销售量查询AO
	 */
	private ItemSalesStatAO itemSalesStatAO;
	
	/**
	 * 已卖数量
	 */
	private Long buyNum = 0L;

	public String queryAjaxItemBuyNum(){
		try {
			ItemSalesStatDO itemSalesStatDO = itemSalesStatAO.getItemSalesStatById(itemId);
			if(itemSalesStatDO != null){
				buyNum = itemSalesStatDO.getBuyNum();
			}
		} catch (Exception e) {
			log.error("Event=[QueryAjaxOrderAction#queryAjaxOvernightItemBuyNum]", e);
		}
		return SUCCESS;
	}
	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}

	public void setItemSalesStatAO(ItemSalesStatAO itemSalesStatAO) {
		this.itemSalesStatAO = itemSalesStatAO;
	}
	
}
