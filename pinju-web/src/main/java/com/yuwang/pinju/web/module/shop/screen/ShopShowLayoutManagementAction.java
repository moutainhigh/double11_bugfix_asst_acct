package com.yuwang.pinju.web.module.shop.screen;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.shop.ao.ShopModulePrototypeAO;
import com.yuwang.pinju.core.shop.ao.ShopPageLayoutAO;
import com.yuwang.pinju.core.shop.ao.ShopPagePrototypeAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;
import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

public class ShopShowLayoutManagementAction extends BaseWithUserAction{

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private ShopModulePrototypeAO shopModulePrototypeAO;

	private ShopPagePrototypeAO shopPagePrototypeAO;

	private ShopPageLayoutAO shopPageLayoutAO;

	private List<ShopPagePrototypeDO> shopPagePrototypeList;

	private List<ShopModulePrototypeDO> shopModulePrototypeList;

	private List<ShopPageLayoutDO> shopPageLayoutList;

	private String result;

	private List<List<String>> editPageList;

	private Integer pageId;

	private Integer type;

	private String xml;

	private Long userPageId;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	private Integer shopId;
	
	
	public Integer getShopId() {
		return shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	private String shopName;

	public Long getUserPageId() {
		return userPageId;
	}

	public void setUserPageId(Long userPageId) {
		this.userPageId = userPageId;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public List<ShopPageLayoutDO> getShopPageLayoutList() {
		return shopPageLayoutList;
	}

	public void setShopPageLayoutAO(ShopPageLayoutAO shopPageLayoutAO) {
		this.shopPageLayoutAO = shopPageLayoutAO;
	}

	public String getResult() {
		return result;
	}

	public List<ShopModulePrototypeDO> getShopModulePrototypeList() {
		return shopModulePrototypeList;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<List<String>> getEditPageList() {
		return editPageList;
	}

	public void setShopPagePrototypeAO(ShopPagePrototypeAO shopPagePrototypeAO) {
		this.shopPagePrototypeAO = shopPagePrototypeAO;
	}

	public void setShopModulePrototypeAO(
			ShopModulePrototypeAO shopModulePrototypeAO) {
		this.shopModulePrototypeAO = shopModulePrototypeAO;
	}

	/**
	 * 获得正在配置下拉菜单的值
	 * 
	 * @return
	 */
	private List<List<String>> queryEditPageList() {
		shopPagePrototypeList = shopPagePrototypeAO.getAllDesignerPage();
		editPageList = new ArrayList<List<String>>();

		for (int i = 0; i < shopPagePrototypeList.size(); i++) {
			ShopPagePrototypeDO shopPagePrototypeDO = (ShopPagePrototypeDO) shopPagePrototypeList
					.get(i);
			List<String> pageList = new ArrayList<String>();
			pageList.add(shopPagePrototypeDO.getName());
			pageList.add(shopPagePrototypeDO.getConfig("action"));
			pageList.add(shopPagePrototypeDO.getPageId().toString());
			editPageList.add(pageList);
		}
		return editPageList;
	}


	/**
	 * 获得添加模块
	 * 
	 * @return
	 */
	public String queryModulePrototype() {
		shopModulePrototypeList = shopModulePrototypeAO
				.getDesignerModuleByPage(pageId, type);
		JSONObject jo = new JSONObject();
		jo.put("root", shopModulePrototypeList);
		log.info(jo.toString());
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 显示布局管理首页
	 * 
	 * @return
	 */
	public String showIndexLayoutManagement() {
		this.queryEditPageList();
		if (pageId == null) {
			pageId = ShopConstants.SHOP_FIRST_PAGE;
		}
		Integer shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		ShopInfoDO shopInfoDO = null;
		if(shopId != null){
			try {
				shopInfoDO = shopShowInfoManager.queryShopInfoByShopId(shopId, ShopConstant.APPROVE_STATUS_YES);
				shopName = shopInfoDO.getName();
				shopId = shopInfoDO.getShopId();
			} catch (ManagerException e) {
				// TODO Auto-generated catch block
				log.info(e.getMessage());
			}
		}
		shopPageLayoutList = shopPageLayoutAO.queryPageLayout(userPageId,
				getUserId(), shopId, pageId, false);
		log.info("shopPageLayoutList is ================== "
				+ shopPageLayoutList);
		for (int i = 0; i < shopPageLayoutList.size(); i++) {
			log.info(((ShopPageLayoutDO) shopPageLayoutList.get(i))
					.getIsCustomCode());
		}

		return "success";
	}

	public String savePage() {
		Integer shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		shopPageLayoutAO.savePageLayout(getUserId(), shopId, pageId, xml);
		shopPageLayoutList = shopPageLayoutAO.queryPageLayout(userPageId,
				getUserId(), shopId, pageId, false);
		queryEditPageList();
		log.info(xml);
		return "success";
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
