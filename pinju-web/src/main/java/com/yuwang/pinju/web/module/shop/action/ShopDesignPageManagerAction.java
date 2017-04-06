/**
 * 
 */
package com.yuwang.pinju.web.module.shop.action;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.shop.ao.ShopPagePrototypeAO;
import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author liyouguo
 * 
 */
public class ShopDesignPageManagerAction extends BaseWithUserAction {
	private String params;
	private String result = "保存成功。";
	private ShopUserPageAO shopUserPageAO;
	private ShopPagePrototypeAO shopPagePrototypeAO;
	

	

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ShopUserPageAO getShopUserPageAO() {
		return shopUserPageAO;
	}

	public void setShopUserPageAO(ShopUserPageAO shopUserPageAO) {
		this.shopUserPageAO = shopUserPageAO;
	}

	public ShopPagePrototypeAO getShopPagePrototypeAO() {
		return shopPagePrototypeAO;
	}

	public void setShopPagePrototypeAO(ShopPagePrototypeAO shopPagePrototypeAO) {
		this.shopPagePrototypeAO = shopPagePrototypeAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		try {
			Integer shopId = getUserShopId();
			if(shopId == null){
				errorMessage = "用户未开店";
				return "error";
			}
			
			String xmlStruct = null;
			ShopUserPageDO userPageDO = new ShopUserPageDO();
			long memberId = getUserId();
			userPageDO.setShopId(shopId);
			userPageDO.setUserId(memberId);
			List<ShopUserPageDO> tmp = new ArrayList<ShopUserPageDO>();
			if (parseParams(shopId, memberId, tmp)) {
				ShopPagePrototypeDO pagePrototype = shopPagePrototypeAO
						.queryPageProtoByPageid(ShopConstants.SHOP_CUSTOMER_PAGE);
				xmlStruct = pagePrototype.getPageStructure();
			}
			Object[] o = shopUserPageAO.saveUserCustomerPage(createSaveList(
					userPageDO, tmp, xmlStruct));
			if (o == null)
				result = "保存失败。";
		} catch (Exception e) {
			log.error(e);
			result = "保存失败。";
		}

		JSONObject json = new JSONObject();
		json.put("root", result);
		return SUCCESS;
	}

	/**
	 * 组装待保存的自定义页面
	 * 
	 * @param userPageDO
	 * @param tmp
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	private List<ShopUserPageDO> createSaveList(ShopUserPageDO userPageDO,
			List<ShopUserPageDO> tmp, String xmlStruct) throws Exception {
		List<ShopUserPageDO> userPageList = shopUserPageAO
				.selectShopUserCustomerPage(userPageDO, false);
		List<ShopUserPageDO> list = new ArrayList<ShopUserPageDO>();
		for (ShopUserPageDO shopUserPageDO : tmp) {
			if (shopUserPageDO.getId() == null || shopUserPageDO.getId() <= 0) {
				shopUserPageDO.setSaveStructure(xmlStruct);
				shopUserPageDO.setSaveType(1);// 新增
				list.add(shopUserPageDO);
				continue;
			}
			if (inList(userPageList, shopUserPageDO)) {
				shopUserPageDO.setSaveType(2);// 修改
				list.add(shopUserPageDO);
			}
		}
		for (ShopUserPageDO shopUserPageDO : userPageList) {
			if (!inList(list, shopUserPageDO)) {
				shopUserPageDO.setSaveType(3);// 删除
				list.add(shopUserPageDO);
			}
		}
		return list;
	}

	/**
	 * 判断对象是否在列表中
	 * 
	 * @param userPageList
	 * @param shopUserPage
	 * @return
	 */
	private boolean inList(List<ShopUserPageDO> userPageList,
			ShopUserPageDO shopUserPage) {
		for (ShopUserPageDO shopUserPageDO : userPageList) {
			if (shopUserPage.getId().longValue() == shopUserPageDO.getId()
					.longValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析前台返回的串 格式：一行用换行符区分，字段之间用“;”区分
	 * 
	 * @param shopId
	 * @param userId
	 * @param list
	 * @return
	 */
	private boolean parseParams(Integer shopId, long userId,
			List<ShopUserPageDO> list) throws Exception {
		Properties prop = new Properties();
		if (StringUtils.isNotEmpty(params)) {
			try {
				prop.load(new StringReader(params));
			} catch (Exception ignored) {
				return false;
			}
		} else
			return false;
		boolean flag = false;
		for (Object o : prop.keySet()) {
			String orderId = (String) o;
			String[] strArray = prop.getProperty(orderId).split(";");
			ShopUserPageDO entity = new ShopUserPageDO();
			try {
				entity.setId(new Long(strArray[0]));
			} catch (Exception e) {
			}
			if (!flag && (entity.getId() == null || entity.getId() <= 0))
				flag = true;
			entity.setPageId(3);
			entity.setShopId(shopId);
			entity.setUserId(userId);
			entity.setTitle(strArray[1]);
			entity.setOrderId(new Integer(orderId));
			entity.setConfiguration("displayNavigate=" + strArray[2]);
			list.add(entity);
		}
		return flag;
	}
}
