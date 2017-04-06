/**
 * 
 */
package com.yuwang.pinju.web.module.item.screen;

import java.util.List;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.item.ao.CategoryAO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.SearchCategoryResult;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * @Project: pinju-web
 * @Title: GetCategory.java
 * @Package com.yuwang.pinju.web.module.item.screen
 * @Description: 获取类目信息
 * @author liuboen liuboen@zba.com
 * @date 2011-9-1 下午05:12:26
 * @version V1.0
 */

public class GetCategory extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5692954570513292200L;
	private CategoryAO categoryAO;

	@SuppressWarnings("unchecked")
	public String getCategoryLevel() {
		if(true){
			//屏蔽入口
			return ERROR;
		}
		long cateId = getLong("cid");
		String cateIdStr = getString("cstr");
		if (cateId != 0l && StringUtil.isBlank(cateIdStr)) {
			SearchCategoryResult result = categoryAO.getCategoryLevelByCateId(cateId);
			request.setAttribute("cateResult", result);
		}

		if (cateId == 0l && !StringUtil.isBlank(cateIdStr)) {
			Result result = categoryAO.sercheCategoryDOInfo(cateIdStr);
			request.setAttribute("cateName", result.getModel("pCateName"));
			request.setAttribute("cateList", (List<CategoryDO>)result.getModel("cateList"));
			request.setAttribute("totalNum", result.getModel("totalNum"));
			request.setAttribute("cateResult", (SearchCategoryResult)result.getModel("currentFrom"));
		}

		return SUCCESS;
	}

	/**
	 * @param categoryAO
	 *            the categoryAO to set
	 */
	public void setCategoryAO(CategoryAO categoryAO) {
		this.categoryAO = categoryAO;
	}

}
