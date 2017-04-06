package com.yuwang.pinju.core.item.ao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.item.ao.ItemListAO;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.HtmlRegexpUtil;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;

/**
 * 
 * @author liming
 * 
 */
public class ItemListAOImpl extends BaseAO implements ItemListAO {

	private ItemManager itemManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemListAO#getItemList(com.yuwang.pinju .domain.item.ItemQuery)
	 */
	@Override
	public List<ItemDO> getItemList(ItemQuery itemQuery) {

		List<ItemDO> ls = null;
		try {

			// 商品状态
			List<Long> status = new ArrayList<Long>();
			if (itemQuery.getType() == 0) {
				status.add(ItemConstant.STATUS_TYPE_0);
				status.add(ItemConstant.STATUS_TYPE_1);
				itemQuery.setRecommendedStatus(null);
//			} else if (itemQuery.getType() == 1) {
//				itemQuery.setRecommendedStatus(1l);
//				status.add(ItemConstant.STATUS_TYPE_0);
//				status.add(ItemConstant.STATUS_TYPE_1);
			} else if (itemQuery.getType() == 2) {
				if(itemQuery.getFilterType() == 2){
					status.add(ItemConstant.STATUS_TYPE_10);
				}else{
					status.add(ItemConstant.STATUS_TYPE_2);
					status.add(ItemConstant.STATUS_TYPE_4);
					status.add(ItemConstant.STATUS_TYPE_6);
					status.add(ItemConstant.STATUS_TYPE_7);
					status.add(ItemConstant.STATUS_TYPE_8);
					status.add(ItemConstant.STATUS_TYPE_9);
				}
				itemQuery.setRecommendedStatus(null);
			} else {
				itemQuery.setType(0);
				status.add(ItemConstant.STATUS_TYPE_0);
				status.add(ItemConstant.STATUS_TYPE_1);
				itemQuery.setRecommendedStatus(null);
			}
			itemQuery.setStatus(status);

//			if (!EmptyUtil.isBlank(itemQuery.getTitle())) {
//				itemQuery.setTitle(HtmlRegexpUtil.replaceTag(itemQuery.getTitle().trim()));
//			}

			if (!EmptyUtil.isBlank(itemQuery.getCode())) {
				itemQuery.setCode(HtmlRegexpUtil.replaceTag(itemQuery.getCode().trim()));
			}

			// 类目
			if (itemQuery.getCategory() != null && itemQuery.getCategory() == 0) {
				itemQuery.setCategory(null);
			}

			// 单价
			if (!EmptyUtil.isBlank(itemQuery.getMinPriceInput()) && NumberUtil.isDouble(itemQuery.getMinPriceInput())) {
				Money m = new Money(itemQuery.getMinPriceInput());
				itemQuery.setMinPrice(m.getCent());
			} else {
				itemQuery.setMinPriceInput("");
			}

			if (!EmptyUtil.isBlank(itemQuery.getMaxPriceInput()) && NumberUtil.isDouble(itemQuery.getMaxPriceInput())) {
				Money m = new Money(itemQuery.getMaxPriceInput());
				itemQuery.setMaxPrice(m.getCent());
			} else {
				itemQuery.setMaxPriceInput("");
			}

			// 销量
			if (!EmptyUtil.isBlank(itemQuery.getMinSalesInput()) && NumberUtil.isDouble(itemQuery.getMinSalesInput())) {
				if(Long.parseLong(itemQuery.getMinSalesInput())>=0){
					itemQuery.setMinSales(Long.parseLong(itemQuery.getMinSalesInput()));
				}else{
					itemQuery.setMinSalesInput("");
				}			
			} else {
				itemQuery.setMinSalesInput("");
			}

			if (!EmptyUtil.isBlank(itemQuery.getMaxSalesInput()) && NumberUtil.isDouble(itemQuery.getMaxSalesInput())) {
				if(Long.parseLong(itemQuery.getMaxSalesInput())>=0){
					itemQuery.setMaxSales(Long.parseLong(itemQuery.getMaxSalesInput()));
				}else{
					itemQuery.setMaxSalesInput("");
				}
			} else {
				itemQuery.setMaxSalesInput("");
			}

			int page = itemQuery.getPage();

			int items = itemManager.getItemListCount(itemQuery);

			itemQuery.setItems(items);
			itemQuery.setItemsPerPage(10);
			itemQuery.setPage(page);
			itemQuery.setStartRow((page-1)*10);
			itemQuery.setEndRow(page*10);
			ls = itemManager.getItemList(itemQuery);

		} catch (ManagerException e) {
			log.error("获得商品列表错误", e);
		}
		return ls;
	}

	
	public List<ItemDO> getItemListForAPI(ItemQuery itemQuery) {

		List<ItemDO> ls = null;
		try {

			// 商品状态
			List<Long> status = new ArrayList<Long>();
			if (itemQuery.getType() == 0) {
				status.add(ItemConstant.STATUS_TYPE_0);
				status.add(ItemConstant.STATUS_TYPE_1);
				itemQuery.setRecommendedStatus(null);
//			} else if (itemQuery.getType() == 1) {
//				itemQuery.setRecommendedStatus(1l);
//				status.add(ItemConstant.STATUS_TYPE_0);
//				status.add(ItemConstant.STATUS_TYPE_1);
			} else if (itemQuery.getType() == 2) {
				status.add(ItemConstant.STATUS_TYPE_2);
				status.add(ItemConstant.STATUS_TYPE_4);
				status.add(ItemConstant.STATUS_TYPE_6);
				status.add(ItemConstant.STATUS_TYPE_7);
				status.add(ItemConstant.STATUS_TYPE_8);
				status.add(ItemConstant.STATUS_TYPE_9);
				status.add(ItemConstant.STATUS_TYPE_10);
				itemQuery.setRecommendedStatus(null);
			} else {
				itemQuery.setType(0);
				status.add(ItemConstant.STATUS_TYPE_0);
				status.add(ItemConstant.STATUS_TYPE_1);
				itemQuery.setRecommendedStatus(null);
			}
			itemQuery.setStatus(status);

//			if (!EmptyUtil.isBlank(itemQuery.getTitle())) {
//				itemQuery.setTitle(HtmlRegexpUtil.replaceTag(itemQuery.getTitle().trim()));
//			}

			if (!EmptyUtil.isBlank(itemQuery.getCode())) {
				itemQuery.setCode(HtmlRegexpUtil.replaceTag(itemQuery.getCode().trim()));
			}

			// 类目
			if (itemQuery.getCategory() != null && itemQuery.getCategory() == 0) {
				itemQuery.setCategory(null);
			}

			// 单价
			if (!EmptyUtil.isBlank(itemQuery.getMinPriceInput()) && NumberUtil.isDouble(itemQuery.getMinPriceInput())) {
				Money m = new Money(itemQuery.getMinPriceInput());
				itemQuery.setMinPrice(m.getCent());
			} else {
				itemQuery.setMinPriceInput("");
			}

			if (!EmptyUtil.isBlank(itemQuery.getMaxPriceInput()) && NumberUtil.isDouble(itemQuery.getMaxPriceInput())) {
				Money m = new Money(itemQuery.getMaxPriceInput());
				itemQuery.setMaxPrice(m.getCent());
			} else {
				itemQuery.setMaxPriceInput("");
			}

			// 销量
			if (!EmptyUtil.isBlank(itemQuery.getMinSalesInput()) && NumberUtil.isDouble(itemQuery.getMinSalesInput())) {
				if(Long.parseLong(itemQuery.getMinSalesInput())>=0){
					itemQuery.setMinSales(Long.parseLong(itemQuery.getMinSalesInput()));
				}else{
					itemQuery.setMinSalesInput("");
				}			
			} else {
				itemQuery.setMinSalesInput("");
			}

			if (!EmptyUtil.isBlank(itemQuery.getMaxSalesInput()) && NumberUtil.isDouble(itemQuery.getMaxSalesInput())) {
				if(Long.parseLong(itemQuery.getMaxSalesInput())>=0){
					itemQuery.setMaxSales(Long.parseLong(itemQuery.getMaxSalesInput()));
				}else{
					itemQuery.setMaxSalesInput("");
				}
			} else {
				itemQuery.setMaxSalesInput("");
			}

			int page = itemQuery.getPage();

			int items = itemManager.getItemListCount(itemQuery);

			itemQuery.setItems(items);
			if(itemQuery.getItemsPerPage()>0){
				itemQuery.setPage(page);
				itemQuery.setStartRow((page-1)*itemQuery.getItemsPerPage());
				itemQuery.setEndRow(page*itemQuery.getItemsPerPage());
			}else{
				itemQuery.setItemsPerPage(10);
				itemQuery.setPage(page);
				itemQuery.setStartRow((page-1)*10);
				itemQuery.setEndRow(page*10);
			}		
			ls = itemManager.getItemList(itemQuery);

		} catch (ManagerException e) {
			log.error("获得商品列表错误", e);
		}
		return ls;
	}
	
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemListAO#userAddShelfItem(java.lang.String )
	 */
	@Override
	public List<String> userAddShelfItem(Long ids[]) {
		List<String> resultList = new ArrayList<String>();
		try {
			if (ids.length > 20) {
				// 不允许超过二十条
				throw new ManagerException("用户上架商品超过二十条");
			}
			for (int i = 0; i < ids.length; i++) {
				String r = itemManager.addShelfById(ids[i], ItemConstant.STATUS_TYPE_0);
				if (!EmptyUtil.isBlank(r)) {
					resultList.add(r);
				}
			}

		} catch (ManagerException e) {
			log.error("用户上架商品错误", e);
		}
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemListAO#userDelItemByIds(java.lang.String )
	 */
	@Override
	public int userDelItem(Long ids[]) {
		int result = 0;
		try {
			if (ids.length > 20) {
				// 不允许超过二十条
				throw new ManagerException("用户商品删除超过二十条");
			}
			for (int i = 0; i < ids.length; i++) {
				result += itemManager.delItemById(ids[i], ItemConstant.STATUS_TYPE_3);
			}
		} catch (ManagerException e) {
			log.error("用户商品删除错误", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemListAO#userDelShelfItem(java.lang.String )
	 */
	@Override
	public int userDelShelfItem(Long ids[]) {
		int result = 0;
		try {
			if (ids.length > 20) {
				// 不允许超过二十条
				throw new ManagerException("用户下架商品超过二十条");
			}
			for (int i = 0; i < ids.length; i++) {
				result += itemManager.delShelfById(ids[i], ItemConstant.STATUS_TYPE_2);
			}

		} catch (ManagerException e) {
			log.error("用户下架商品错误", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemListAO#userAddShelfItem(java.lang.String )
	 */
	@Override
	public Map<Long, String> itemStatusValidatorForAPI(Long ids[],String operation,Long sellerId) {
		Map<Long, String> resultMap = new HashMap<Long, String>();	
		try {
			if (ids.length > 20) {
				// 不允许超过二十条
				throw new ManagerException("商品超过二十条");
			}
			for (int i = 0; i < ids.length; i++) {
				ItemDO item =null;
				if(operation!=null&&!operation.equals("")){
					item = itemManager.selectItemDObyId(ids[i]);
					if(null == item){
						resultMap.put(ids[i], "传入参数格式不正确");
						continue;
					}
					if(!item.getSellerId().equals(sellerId)){
						resultMap.put(ids[i], "非该用户商品无法操作");
						continue;
					}
					if(item!=null&&item.getStatus()!=null){
						if(operation.equals("itemUpdateByOut")){
							if(item.getStatus()==ItemConstant.STATUS_TYPE_3||item.getStatus()==ItemConstant.STATUS_TYPE_5
									||item.getStatus()==ItemConstant.STATUS_TYPE_9||item.getStatus()==ItemConstant.STATUS_TYPE_10){
								resultMap.put(ids[i], "商品状态不允许更新操作");
							}else{
								resultMap.put(ids[i], "ok");
							}
						}else if(operation.equals("userAddShelfItem")){
							if(item.getStatus()==ItemConstant.STATUS_TYPE_3||item.getStatus()==ItemConstant.STATUS_TYPE_5
									||item.getStatus()==ItemConstant.STATUS_TYPE_9||item.getStatus()==ItemConstant.STATUS_TYPE_4||item.getStatus()==ItemConstant.STATUS_TYPE_10){
								resultMap.put(ids[i], "商品状态不允许上架操作");
							}else{
								resultMap.put(ids[i], "ok");
							}
						}else if(operation.equals("userDelShelfItem")){
							if(item.getStatus()==ItemConstant.STATUS_TYPE_3||item.getStatus()==ItemConstant.STATUS_TYPE_5
									||item.getStatus()==ItemConstant.STATUS_TYPE_9||item.getStatus()==ItemConstant.STATUS_TYPE_4||item.getStatus()==ItemConstant.STATUS_TYPE_10){
								resultMap.put(ids[i], "商品状态不允许下架操作");
							}else{
								resultMap.put(ids[i], "ok");
							}
						}else if(operation.equals("userDelItem")){
							if(item.getStatus()==ItemConstant.STATUS_TYPE_3||item.getStatus()==ItemConstant.STATUS_TYPE_5||item.getStatus()==ItemConstant.STATUS_TYPE_9){
								resultMap.put(ids[i], "商品状态不允许删除操作");
							}else{
								resultMap.put(ids[i], "ok");
							}
						}else{
							resultMap.put(ids[i], "无效操作:"+operation);
						}
					}else{
						resultMap.put(ids[i], "商品不存在");
					}
				}else{
					resultMap.put(ids[i], "无效操作");
				}		
			}
		} catch (ManagerException e) {
			log.error("用户对商品操作错误", e);
		}
		return resultMap;
	}


	@Override
	public String getItemTitles(Long[] ids) {
		String itemTitles = "";
		List<Long> itemIds = new ArrayList<Long>();
		try {
			if(ids!=null&&ids.length>0){
				ItemQueryEx itemQuery = new ItemQueryEx();
				for(int i=0;i<ids.length;i++){
					itemIds.add(ids[i]);
				}
				itemQuery.setItemIdList(itemIds);
				itemTitles = itemManager.getItemTitles(itemQuery);
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemTitles;
	}
}
