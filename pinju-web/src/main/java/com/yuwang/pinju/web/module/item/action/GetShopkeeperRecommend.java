/**
 * 
 */
package com.yuwang.pinju.web.module.item.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.core.shop.ao.ShopUserModuleAO;

/**
 * @Project com.yuwang.pinju.web.module.item.action.pinju-web
 * @Description: 商品详情页获取掌柜推荐
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a>
 * @date 2011-12-7 上午9:03:24
 * @version V1.0
 */

public class GetShopkeeperRecommend extends ActionSupport {
	private static final long serialVersionUID = -5042673902076747561L;

	@Autowired
	private ShopUserModuleAO shopUserModuleAO;
	@Autowired
	private ItemDetailAO itemDetailAO;

	public String mid;
	public List<Map<String, Object>> itemDOList;

	public String errorInfo;
	public boolean isSucess;
	
	public String getShopKeeperRecommend() {
		long memberId = 0;
		try {
			memberId = Long.parseLong(mid);
		} catch (NumberFormatException e) {
			isSucess=Boolean.FALSE;
			errorInfo = "传入值有误";
			return SUCCESS;
		}

		String itemIds = shopUserModuleAO.getKeeperPromoteIds(memberId);
		itemDOList = itemDetailAO.getSimpleItemDOsByids(itemIds, memberId);
		isSucess=Boolean.TRUE;
		return SUCCESS;
	}

	public List<Map<String, Object>> getItemDOList() {
		return itemDOList;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public boolean isSucess() {
		return isSucess;
	}

}
