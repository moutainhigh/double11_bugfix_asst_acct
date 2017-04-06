package com.yuwang.pinju.core.sales.ao;

import java.util.List;

import com.yuwang.pinju.Constant.ItemXianGouConstant;
import com.yuwang.pinju.common.ItemXianGouUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.XianGouResultCode;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.sales.manager.ItemXianGouManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;
import com.yuwang.pinju.domain.sales.query.ItemXianGouQuery;

public class ItemXianGouAO extends BaseAO {
	
	private ItemManager itemManager;
	private ItemXianGouManager itemXianGouManager;
	
	
	public void setItemXianGouManager(ItemXianGouManager itemXianGouManager) {
		this.itemXianGouManager = itemXianGouManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	/**
	 * 根据商品Id查询商品
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemId
	 * @return
	 */
	public Result getItemDOById(Long itemId){
		Result result = new ResultSupport();
		ItemDO itemDO = null;
		try {
			itemDO = itemManager.getItemDOById(itemId);
			result.setModel("itemDO",itemDO);
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode("通过ID获取商品详情失败");
			log.error("Event=[ItemXianGouAO#getItemDOById] 通过ID获取商品详情失败", e);
		}
		return result;
	}
	/**
	 * 根据商品Id查询该商品限购活动
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemId
	 * @return
	 */
	public Result getItemXianGouDOByItemId(Long itemId){
		Result result = new ResultSupport();
		ItemXianGouDO itemXianGouDO =null;
		try {
			itemXianGouDO = itemXianGouManager.getItemXianGouDOByItemId(itemId);
			result.setModel("itemXianGouDO",itemXianGouDO);
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(XianGouResultCode.XIANGOU_CHECKCODE_FAIL);
			log.error("Event=[ItemXianGouAO#getItemDOById] 通过商品ID获取商品限购详情失败", e);
		}
		return result;
	}
	
	/**
	 * 查询限购码领取状况
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemId
	 * @return
	 */
	public Result getItemXianGouDOCounts(ItemXianGouDO  itemXianGouDO){
		Result result = new ResultSupport();
		ItemXianGouUseDO itemXianGouUseDO = new ItemXianGouUseDO();
		itemXianGouUseDO.setItemId(itemXianGouDO.getItemId());
		itemXianGouUseDO.setXianGouId(itemXianGouDO.getId());
		itemXianGouUseDO.setVersion(new Long(itemXianGouDO.getVersion()));
		Long count = 0L,total = 0L,free = 0L;
		try {
			count = itemXianGouManager.getItemXianGouUseCount(itemXianGouUseDO);
			total = itemXianGouDO.getCount();
			free = total - count;
			result.setModel("free", free);
			return result;
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode(XianGouResultCode.XIANGOU_CHECKCODE_FAIL);
			log.error("Event=[ItemXianGouAO#getItemXianGouDOCounts] 获取商品限购码详情失败", e);
		}
		return result;
	}
	
	/**
	 * 生成限购码
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemXianGouDO
	 * @return
	 */
	public Result getItemXianGouCode(ItemXianGouDO itemXianGouDO) {
		Result result = new ResultSupport();
		Long code = ItemXianGouUtil.createCode(itemXianGouDO.getItemId());
		ItemXianGouUseDO itemXianGouUseDO = new ItemXianGouUseDO();
		itemXianGouUseDO.setCode(code);
		itemXianGouUseDO.setCodeSource(ItemXianGouUtil.encode(code));
		itemXianGouUseDO.setIsUse(ItemXianGouConstant.XIANGOU_CODE_NOT_USE);
//		itemXianGouUseDO.setItemId(itemXianGouDO.getItemId());
//		itemXianGouUseDO.setItemTitle(itemXianGouDO.getItemTitle());
//		itemXianGouUseDO.setSellerId(itemXianGouDO.getSellerId());
//		itemXianGouUseDO.setSellerNick(itemXianGouDO.getSellerNick());
		itemXianGouUseDO.setXianGouId(itemXianGouDO.getId());
		itemXianGouUseDO.setVersion(Long.valueOf(itemXianGouDO.getVersion()));
		itemXianGouUseDO.setBatchNum(itemXianGouDO.getBatchNum());
		try {
			itemXianGouManager.addItemXianGouUseDO(itemXianGouUseDO);
			result.setModel("code", code);
		} catch (ManagerException e) {
			result.setSuccess(false);
			result.setResultCode("生成商品限购码失败");
			log.error("Event=[ItemXianGouAO#getItemXianGouCode] 生成商品限购码失败", e);
		}
		return result;
	}
	
	/**
	 *  Created on 2011-12-2 
	 * <p>Discription:[随机查询5个参加限购活动的商品]</p>
	 * @author:[lixingquan]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return Result .
	 */
	public Result getItemXianGouDOs()
	{
	    Result result = new ResultSupport();
	    ItemXianGouQuery itemXianGouQuery = new ItemXianGouQuery(); 
	    itemXianGouQuery.setBatchNum(ItemXianGouConstant.BATCH_NUM);
	    try
        {
            List<ItemXianGouDO> list = itemXianGouManager.getItemXianGouDOs(itemXianGouQuery);
            result.setModel("list", list);
        }
        catch (ManagerException e)
        {
            result.setSuccess(false);
            result.setResultCode("查询活动商品记录失败");
            log.error("Event=[ItemXianGouAO#getItemXianGouDOs] 查询活动商品记录失败", e);
        }
	    return result;
	}
	
	
}
