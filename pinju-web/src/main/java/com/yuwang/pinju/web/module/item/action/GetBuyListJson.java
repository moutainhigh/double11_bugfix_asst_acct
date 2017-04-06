/**
 * 
 */
package com.yuwang.pinju.web.module.item.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.web.module.BaseAction;

/**  
 * @Project: pinju-web
 * @Title: GetLogisticJson.java
 * @Package com.yuwang.pinju.web.module.item.action
 * @Description: 异步获取购买记录
 * @author liuboen liuboen@zba.com
 * @date 2011-8-16 下午06:42:31
 * @version V1.0  
 */

public class GetBuyListJson extends BaseAction {
	private ItemDetailAO itemDetailAO;
	Boolean sucess;
	Integer count;
	Long allItem;
	List<Map<String,Object>> list;
	private static final String STR_AND="***";
	;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3961750864502418813L;

	public String execute(){
		sucess=true;
		int page=getInteger("page");
		int size=getInteger("size");
		long itemId=getLong("itemId");
		QueryOrderItem queryOrderItemDO= new QueryOrderItem();
		queryOrderItemDO.setItemId(itemId);
		queryOrderItemDO.setPage(page);
		queryOrderItemDO.setItemsPerPage(size);
		int status[]={OrderItemDO.ORDER_ITEM_STATE_2,OrderItemDO.ORDER_ITEM_STATE_3,OrderItemDO.ORDER_ITEM_STATE_5};
		queryOrderItemDO.setOrderItemState(status);
		List<OrderItemDO> orderItemDOlist=itemDetailAO.getBuyOrderItemDOListById(queryOrderItemDO);
		
		list = new ArrayList<Map<String,Object>>();
		for (OrderItemDO orderItemDO : orderItemDOlist) {
			Map<String,Object> map=new HashMap<String, Object>();
			String nick=orderItemDO.getBuyerNick();
			if (orderItemDO.getAnonymousBuy()==null||orderItemDO.getAnonymousBuy()!=1l) {
				StringBuilder nickBuilder=new StringBuilder();
				nickBuilder.append(StringUtil.substring(nick, 0, 1)).append(STR_AND).append(StringUtil.substring(nick, nick.length()-1,nick.length()));
				nick=nickBuilder.toString();
			}
			map.put("buyerNick",nick);
			if (orderItemDO.isCoupon()==1) {
				//普通
				map.put("price", MoneyUtil.getCentToDollar(orderItemDO.getOriginalPrice()));
			}else if (orderItemDO.isCoupon()==2) {
				//促销时
				map.put("price", MoneyUtil.getCentToDollar(orderItemDO.getOrderItemPrice()));
			}else {
				map.put("price", MoneyUtil.getCentToDollar(orderItemDO.getOriginalPrice()));
			}
			map.put("gmtCreate", DateUtil.formatDatetime(orderItemDO.getGmtCreate()));
			map.put("buycount", orderItemDO.getBuyNum());
			map.put("isCoupon", orderItemDO.isCoupon());
			String skuAttr=orderItemDO.getItemSkuAttributes();
			if (skuAttr!=null&&!skuAttr.equals("")) {
				map.put("skuAttr",skuAttr);
			}else {
				map.put("skuAttr","默认款式");
			}
			list.add(map);
		}
		count=queryOrderItemDO.getItems();
		allItem=queryOrderItemDO.getAllItem();
		return SUCCESS;
	}

	/**
	 * @return the sucess
	 */
	public Boolean getSucess() {
		return sucess;
	}

	/**
	 * @param sucess the sucess to set
	 */
	public void setSucess(Boolean sucess) {
		this.sucess = sucess;
	}

	/**
	 * @param itemDetailAO the itemDetailAO to set
	 */
	public void setItemDetailAO(ItemDetailAO itemDetailAO) {
		this.itemDetailAO = itemDetailAO;
	}


	/**
	 * @return the list
	 */
	public List<Map<String, Object>> getList() {
		return list;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	public Long getAllItem() {
		return allItem;
	}



}
