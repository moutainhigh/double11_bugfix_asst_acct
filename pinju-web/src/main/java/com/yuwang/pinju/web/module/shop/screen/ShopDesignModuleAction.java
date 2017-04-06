/**
 * 
 */
package com.yuwang.pinju.web.module.shop.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.shop.ao.ShopUserModuleAO;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author liyouguo
 * 
 */
public class ShopDesignModuleAction extends BaseWithUserAction {
	private String result;
	private Long userPageId;
	private Long moduleId;
	private String editFltKey;
	private String p;
	private ShopUserModuleAO shopUserModuleAO;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	public ShopUserModuleAO getShopUserModuleAO() {
		return shopUserModuleAO;
	}

	public void setShopUserModuleAO(ShopUserModuleAO shopUserModuleAO) {
		this.shopUserModuleAO = shopUserModuleAO;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getUserPageId() {
		return userPageId;
	}

	public void setUserPageId(Long userPageId) {
		this.userPageId = userPageId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getEditFltKey() {
		return editFltKey;
	}

	public void setEditFltKey(String editFltKey) {
		this.editFltKey = editFltKey;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	/**
	 * 用户点击“编辑”按钮时调用的action。 返回一个json串，即生成层的html
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		Integer shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
		shopUserModuleParamDO.setUserPageId(userPageId);
		shopUserModuleParamDO.setRealUserPageId(userPageId);
		shopUserModuleParamDO.setModuleId(moduleId);
		shopUserModuleParamDO.setUserId(getUserId());
		shopUserModuleParamDO.setShopId(shopId);
		shopUserModuleParamDO.setCustomPage(p);
		if (editFltKey == null || editFltKey.trim().length() == 0)
			editFltKey = "E" + moduleId;
		result = shopUserModuleAO.getModuleHtml(shopUserModuleParamDO, false,
				editFltKey, false);
		if (result == null) {
			log.error("生成模块HTML代码有误。");
			throw new Exception("生成模块HTML代码有误。");
		}
		return SUCCESS;
	}

	/**
	 * 查看各模块生成的html页面。 返回页面的html串。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String displayModulePage() throws Exception {
		Integer shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
		shopUserModuleParamDO.setUserPageId(userPageId);
		shopUserModuleParamDO.setModuleId(moduleId);
		shopUserModuleParamDO.setUserId(getUserId());
		shopUserModuleParamDO.setShopId(shopId);
		shopUserModuleParamDO.setCustomPage(p);
		if (editFltKey == null || editFltKey.trim().length() == 0)
			editFltKey = "D" + moduleId;
		result = shopUserModuleAO.getModuleHtml(shopUserModuleParamDO, false,
				editFltKey, true);
		if (result == null) {
			log.error("生成模块HTML代码有误。");
			throw new Exception("生成模块HTML代码有误。");
		}
		return SUCCESS;
	}
}
