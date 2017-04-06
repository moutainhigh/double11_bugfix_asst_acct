package com.yuwang.pinju.web.module.item.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.JsoupUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.core.item.ao.ItemSnapshotAO;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.domain.item.ItemSnapshotDO;
import com.yuwang.pinju.domain.item.SpuDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.module.PinjuControl;
import com.yuwang.pinju.web.module.member.control.MemberSellerQualityControl;

/**
 * 
 * @author gongjiayun
 * 
 */
public class ItemSnapshotDetail extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemSnapshotAO itemSnapshotAO;
	private ItemDetailAO itemDetailAO;
//	private OrderQueryAO orderQueryAO;
	private List<Map<String, Object>> cateProlist;
	private List<Map<String, Object>> spuProList;

	private ItemSnapshotDO itemSnapshotDO;
	private String errorMessage;
	private List<String> skulList;

	/**
	 * 快照ID
	 */
	private String arg1;

	/**
	 * 订单ID
	 */
	//private String arg2;

	public String execute() {
		if (StringUtil.isBlank(arg1)) {
			log.warn("参数快照ID:arg1为空");
			errorMessage = "没有找到商品快照";
			return ERROR;
		}

		if (!StringUtil.isNumeric(arg1)) {
			log.warn("参数快照ID:arg1不全为数字");
			errorMessage = "没有找到商品快照";
			return ERROR;
		}

		itemSnapshotDO = itemSnapshotAO.getItemSnapshotDoById(Long.parseLong(arg1));
		if (itemSnapshotDO == null) {
			log.warn("查找商品快照失败,快照ID为" + arg1);
			errorMessage = "没有找到商品快照";
			return ERROR;
		}

		// 对商品描述进行过滤
		if (!StringUtil.isBlank(itemSnapshotDO.getDescription())) {
			String des = JsoupUtil.getDetailDescription(itemSnapshotDO.getDescription());
			itemSnapshotDO.setDescription(des);
		}

		// 查找类目属性
		cateProlist = itemDetailAO.getItemCategoryPro(itemSnapshotDO.getPropertyValuePair());

		// 查找SPU
		SpuDO spuDO = itemDetailAO.getItemCategorySpubyId(itemSnapshotDO.getSpuId());
		if (spuDO != null) {
			if (log.isDebugEnabled()) {
				log.debug(spuDO.toString());
			}
			// 组装SPU
			spuProList = itemDetailAO.getItemCategoryPro(spuDO.getPropertyValuePair());
		}

		// 查找卖家资质
		PinjuControl pc = new MemberSellerQualityControl(itemSnapshotDO.getSellerId());
		boolean flag = pc.doControl();
		if (!flag) {
			log.warn("查找专家资质信息失败,会员ID为:"+itemSnapshotDO.getSellerId());
			errorMessage = "没有找到商品快照";
			return ERROR;
		}

		// 查找SKU信息
		//OrderItemDO orderItemDO = orderQueryAO.queryOrderItem(Long.parseLong(arg2));

//		if (orderItemDO != null) {
//			String sku = orderItemDO.getItemSkuAttributes();
//			if(!StringUtil.isBlank(sku)){
//				skulList = new ArrayList<String>();
//				String skus[] = sku.split(";");
//				for (int i = 0; i < skus.length; i++) {
//					skulList.add(skus[i]);
//				}
//			}
//		}
		
		return SUCCESS;
	}

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public ItemSnapshotDO getItemSnapshotDO() {
		return itemSnapshotDO;
	}

	public void setItemSnapshotDO(ItemSnapshotDO itemSnapshotDO) {
		this.itemSnapshotDO = itemSnapshotDO;
	}

	public void setItemSnapshotAO(ItemSnapshotAO itemSnapshotAO) {
		this.itemSnapshotAO = itemSnapshotAO;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<Map<String, Object>> getSpuProList() {
		return spuProList;
	}

	public void setSpuProList(List<Map<String, Object>> spuProList) {
		this.spuProList = spuProList;
	}

	public List<Map<String, Object>> getCateProlist() {
		return cateProlist;
	}

	public void setCateProlist(List<Map<String, Object>> cateProlist) {
		this.cateProlist = cateProlist;
	}

	public void setItemDetailAO(ItemDetailAO itemDetailAO) {
		this.itemDetailAO = itemDetailAO;
	}

//	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
//		this.orderQueryAO = orderQueryAO;
//	}

//	public String getArg2() {
//		return arg2;
//	}
//
//	public void setArg2(String arg2) {
//		this.arg2 = arg2;
//	}

	public List<String> getSkulList() {
		return skulList;
	}

	public void setSkulList(List<String> skulList) {
		this.skulList = skulList;
	}
	
}
