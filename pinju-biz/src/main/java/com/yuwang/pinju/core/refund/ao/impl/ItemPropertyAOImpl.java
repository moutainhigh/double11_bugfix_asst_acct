package com.yuwang.pinju.core.refund.ao.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.refund.ao.ItemPropertyAO;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;

/**
 * 取得商品属性
 * @author shihongbo
 * @since   2011-8-11
 */
public class ItemPropertyAOImpl implements ItemPropertyAO{
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	private CategoryCacheManager categoryCacheManager;
	
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}
	
	/**
	 * 取得商品属性
	 * 
	 * @param sku
	 * @return
	 */
	public List<ItemPropertyVO> getItemPropertyBySku(SkuDO sku){
		List<ItemPropertyVO> itemPropertylist = new LinkedList<ItemPropertyVO>();

		String pv_1 = sku.getSalePv1();
		if (pv_1!=null&&!pv_1.equals("")) {
			ItemPropertyVO itemPropertyVO = getItemPropertyBySku(pv_1,sku.getItemId());
			itemPropertylist.add(itemPropertyVO);
		}

		String pv_2 = sku.getSalePv2();
		if (pv_2!=null&&!pv_2.equals("")) {
			ItemPropertyVO itemPropertyVO = getItemPropertyBySku(pv_2,sku.getItemId());
			itemPropertylist.add(itemPropertyVO);
		}

		String pv_3 = sku.getSalePv3();
		if (pv_3!=null&&!pv_3.equals("")) {
			ItemPropertyVO itemPropertyVO = getItemPropertyBySku(pv_3,sku.getItemId());
			itemPropertylist.add(itemPropertyVO);
		}

		String pv_4 = sku.getSalePv4();
		if (pv_4!=null&&!pv_4.equals("")) {
			ItemPropertyVO itemPropertyVO = getItemPropertyBySku(pv_4,sku.getItemId());
			itemPropertylist.add(itemPropertyVO);
		}
		
		return itemPropertylist;
	}
	
	/**
	 * 取得商品属性
	 * 
	 * @param pv
	 * @return
	 */
	private ItemPropertyVO getItemPropertyBySku(String pv,long itemId){
		ItemPropertyVO itemPropertyVO = new ItemPropertyVO();
		
		if (pv == null || pv.equals("")) 
			return itemPropertyVO;
		
		try{
			String indexPV = StringUtil.substringBefore(pv,";");
			String indexP = StringUtil.substringBefore(indexPV, ":");
			String indexV = StringUtil.substringAfter(indexPV, ":");
			long indexPLong = Long.parseLong(indexP);
			long indexVLong = Long.parseLong(indexV);
			
			//获取基础属性值
			BaseValueDO baseValueDO = categoryCacheManager.getBaseValueById(indexVLong);

			CategoryPropertyDO  cateProDO = categoryCacheManager.getItemCategoryPropertyById(indexPLong);

			//设置自定义SKU部分
			if(cateProDO.getIsSellCustom()==CategoryPropertyDO.SELL_CUSTOM_TRUE || cateProDO.getIsSellCustom()==CategoryPropertyDO.SELL_CUSTOM_NO_IMG_TRUE){
					// 设置自定义SKU值
					CustomProValueDO customProValueDO = categoryCacheManager.getCustomProValueDO(itemId, indexPLong, indexVLong);
					String[] values=baseValueDO.getValue().split("\\$\\@");
					if (customProValueDO!=null) {
						//查看是否自定义了
						if (customProValueDO.getValue()!=null&&!customProValueDO.getValue().equals("")) {
							baseValueDO.setValue(customProValueDO.getValue());	
						}else {
							if (2<=values.length){
								baseValueDO.setValue(values[1]);
							}
						}
					}else {
						if (2<=values.length) {
							baseValueDO.setValue(values[1]);
						}

					}
				
			}else{
				List<CategoryPropertyValueDO> categoryPropertyValueList = categoryCacheManager.getItemCategoryPropertyValue(indexPLong);
				if(categoryPropertyValueList!=null&&categoryPropertyValueList.size()>0){
					for(int i=0;i<categoryPropertyValueList.size();i++){
						CategoryPropertyValueDO cpDo = categoryPropertyValueList.get(i);
						if(cpDo.getValueId().equals(baseValueDO.getId())){
							if(cpDo.getValueType()!=null&&cpDo.getValueType()!=1){
								String[] ss = baseValueDO.getValue().split("\\$\\@");
								baseValueDO.setValue(ss[1]);
							}
							continue;
						}
					}
				}
			}

			itemPropertyVO.setName(cateProDO.getName());
			itemPropertyVO.setValue(baseValueDO.getValue());

			return itemPropertyVO;
		}catch (ManagerException e) {
			log.error("Event=[ItemPropertyAOImpl#getItemPropertyBySku] 取得商品属性失败", e);
		}
		return itemPropertyVO;
	}
}
