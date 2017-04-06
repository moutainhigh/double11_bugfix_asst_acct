package com.yuwang.pinju.core.order.manager.helper.impl;

import java.util.List;

import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.order.manager.helper.OrderCategoryManager;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.SkuDO;

/**
 * Created on 2011-9-19
 * 
 * @see <p>
 *      Discription:
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderCategoryManagerImpl extends BaseManager implements
		OrderCategoryManager {

	private CategoryCacheManager categoryCacheManager;

	@Override
	public String getSkuDOAttributes(SkuDO skuDO) throws NumberFormatException,
			ManagerException {
		String skuAttributes = "";
		if (skuDO != null) {
			if (!StringUtil.isEmpty(skuDO.getSalePv1())) {
				skuAttributes = this.getSkuKeyAttributes(skuDO.getSalePv1(),
						skuDO.getItemId());
			}
			if (!StringUtil.isEmpty(skuDO.getSalePv2())) {
				skuAttributes = skuAttributes.concat(VouchPayConstant.SPLITKEY)
						.concat(
								this.getSkuKeyAttributes(skuDO.getSalePv2(),
										skuDO.getItemId()));
			}
			if (!StringUtil.isEmpty(skuDO.getSalePv3())) {
				skuAttributes = skuAttributes.concat(VouchPayConstant.SPLITKEY)
						.concat(
								this.getSkuKeyAttributes(skuDO.getSalePv3(),
										skuDO.getItemId()));
			}
			if (!StringUtil.isEmpty(skuDO.getSalePv4())) {
				skuAttributes = skuAttributes.concat(VouchPayConstant.SPLITKEY)
						.concat(
								this.getSkuKeyAttributes(skuDO.getSalePv4(),
										skuDO.getItemId()));
			}
		}
		return skuAttributes;

	}

	/**
	 * 
	 * Created on 2011-8-26
	 * <p>
	 * Discription: 字符串格式 (类目名称:值)
	 * </p>
	 * 
	 * @param skuAttributesValue
	 * @return
	 * @throws NumberFormatException
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private String getSkuKeyAttributes(String skuAttributesValue, long itemId)
			throws NumberFormatException, ManagerException {
		String skuAttributes = "";
		if (skuAttributesValue != null
				&& StringUtil.contains(skuAttributesValue,
						VouchPayConstant.SPLITATTRIBUTES)) {
			String categoryPropertyId = StringUtil.split(skuAttributesValue,
					VouchPayConstant.SPLITATTRIBUTES)[0];
			String baseValueById = StringUtil.split(skuAttributesValue,
					VouchPayConstant.SPLITATTRIBUTES)[1];
			long cateProId = Long.valueOf(categoryPropertyId);
			CategoryPropertyDO categoryPropertyDO = categoryCacheManager
					.getItemCategoryPropertyById(cateProId);
			BaseValueDO baseValueDO = categoryCacheManager
					.getBaseValueById(Long.valueOf(baseValueById));
			// 设置自定义SKU部分
			if (categoryPropertyDO.getIsSellCustom()!=null&&(categoryPropertyDO.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_TRUE || categoryPropertyDO.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_NO_IMG_TRUE)) {
				// 设置自定义SKU值
				CustomProValueDO customProValueDO = categoryCacheManager
						.getCustomProValueDO(itemId, cateProId, baseValueDO
								.getId());
				String[] values = baseValueDO.getValue().split("\\$\\@");
				if (customProValueDO != null) {
					// 查看是否自定义了
					if (customProValueDO.getValue() != null
							&& !customProValueDO.getValue().equals("")) {
						baseValueDO.setValue(customProValueDO.getValue());
					} else {
						
						
						if (2 <= values.length) {
							baseValueDO.setValue(values[1]);
						}
					}
				} else {
					if (2 <= values.length) {
						baseValueDO.setValue(values[1]);
					}

				}

			}else{
				List<CategoryPropertyValueDO> categoryPropertyValueList = categoryCacheManager.getItemCategoryPropertyValue(cateProId);
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

			if (categoryPropertyDO != null && baseValueDO != null) {
				skuAttributes = skuAttributes.concat(
						categoryPropertyDO.getName()).concat(
						VouchPayConstant.SPLITATTRIBUTES).concat(
						baseValueDO.getValue());
			}

		}
		return skuAttributes;
	}

	public void setCategoryCacheManager(
			CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

}
