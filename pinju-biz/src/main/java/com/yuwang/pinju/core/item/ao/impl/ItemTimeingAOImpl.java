/**
 * 
 */
package com.yuwang.pinju.core.item.ao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.core.active.manager.ActivityDiscountManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.item.ao.ItemTimeingAO;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.user.ao.BaseAO;

/**  
 * @Project: pinju-biz
 * @Title: ItemTimeingAOImpl.java
 * @Package com.yuwang.pinju.core.item.ao.impl
 * @Description: 时间程序实现类
 * @author liuboen liuboen@zba.com
 * @date 2011-7-30 下午04:22:21
 * @version V1.0  
 */

public class ItemTimeingAOImpl extends BaseAO implements ItemTimeingAO {

	private ItemManager itemManager;
	private ActivityDiscountManager activityDiscountManager;
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemTimeingAO#emptyFeture(java.lang.Long, com.yuwang.pinju.Constant.ItemFeaturesEnum)
	 */
	@Override
	public Result emptyFeture(final String itemIds,final long actId, final ItemFeaturesEnum featuresEnum) {
		final Result result =new ResultSupport();
		result.setSuccess(true);
		
		zizuTransactionTemplate.execute(new TransactionCallback<Result>() {
			boolean issucess=true;
			@Override
			public Result doInTransaction(TransactionStatus status) {
				try {
					if (itemIds!=null&&!itemIds.equals("")) {
						String itemIdStrs[]=itemIds.split(",");
						for (String itemIdString : itemIdStrs) {
							if (itemIdString!=null&&!itemIdString.trim().equals("")) {
								Long itemId=Long.parseLong(itemIdString);
								//去掉商品中限时折扣标记
								Result itemResult=itemManager.setFeatures(itemId, featuresEnum, null);
								if (!itemResult.isSuccess()) {
									issucess=false;
									result.getModels().put("errorMessage",itemResult.getModels().get("errorMessage"));
									break;
								}
							}
						}
					}
					if (issucess && featuresEnum==ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT) {
						//将过期限时折扣活动的状态置为已结束
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("id",actId);
						map.put("status", 2);
						map.put("gmtModified",new Date());
						int flag=activityDiscountManager.updateActivityDiscountStatus(map);
						if (flag<=0) {
							issucess=false;
							result.getModels().put("errorMessage","更新折扣活动状态失败");
						}
					}
					
				} catch (ManagerException e) {
					issucess=false;
					if (e.getStackTrace().length>0) {
						result.getModels().put("errorMessage","managerException,in java line ="+e.getStackTrace()[0].toString());
					}
				}finally{
					if (!issucess) {
						status.setRollbackOnly();
					}
				}
				return null;
			}
		});
		return result;
	
	}
	/**
	 * @param itemManager the itemManager to set
	 */
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	/**
	 * @param activityDiscountManager the activityDiscountManager to set
	 */
	public void setActivityDiscountManager(
			ActivityDiscountManager activityDiscountManager) {
		this.activityDiscountManager = activityDiscountManager;
	}

	
}
