package com.yuwang.pinju.core.item.manager.impl;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.item.dao.CategoryDAO;
import com.yuwang.pinju.core.item.manager.CategoryMarginManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.CategoryDO;

/**
 * 
 * @author gongjiayun
 * 
 */
public class CategoryMarginManagerImpl extends BaseAO implements CategoryMarginManager {

	private CategoryDAO categoryDAO;

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public int getItemMargin(Long categoryId, int sellerType) throws ManagerException {
		int margin = 0;
		CategoryDO categoryDO;
		String marginStr = "";
		if(sellerType == ShopConstant.SELLER_TYPE_B.intValue() || sellerType == ShopConstant.SELLER_TYPE_B2.intValue()){
			margin = PinjuConstant.B_MARGIN_PRICE;
			return margin;
			//return ShopConstant.B_MARGIN_PRICE;
		}
		try {
			categoryDO = categoryDAO.selectItemCategoryById(categoryId);
		} catch (DaoException e) {
			log.warn("查找类目保证金失败,categoryId:" + categoryId, e);
			throw new ManagerException("查找类目保证金失败", e);
		}

		String features = categoryDO.getFeatures();
		if (features != null && !"".equals(features)) {
			String featuresArray[] = features.split(";");
			for (int i = 0; i < featuresArray.length; i++) {
				if (featuresArray[i].contains("margin")) {
					marginStr = featuresArray[i].substring(featuresArray[i].indexOf(":") + 1,
							featuresArray[i].indexOf(","));
					margin = Integer.parseInt(marginStr);
					break;
				}
			}
		}
		// 如果返回结果是0表示没有找到
		return margin;
	}
}
