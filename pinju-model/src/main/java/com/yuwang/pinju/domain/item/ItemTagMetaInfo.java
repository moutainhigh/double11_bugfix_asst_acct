/**
 * 
 */
package com.yuwang.pinju.domain.item;

import java.util.List;

import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**  
 * @Project com.yuwang.pinju.domain.item.pinju-model
 * @Description: 店铺标签及
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-12-13 上午10:32:53
 * @version V1.0  
 */

public class ItemTagMetaInfo {
		//商品类目名称  杨昭
		private String itemCategory;
		//店铺信息DO  杨昭
		private ShopInfoDO shopInfoDO;
		//店铺类型 杨昭
		private String shopType;
		//商品名称 杨昭
		private String itemTitle;
		public String getItemTitle() {
			return itemTitle;
		}

		public void setItemTitle(String itemTitle) {
			this.itemTitle = itemTitle;
		}

		public String getShopLabel() {
			return shopLabel;
		}

		public void setShopLabel(String shopLabel) {
			this.shopLabel = shopLabel;
		}

		//店铺类目 杨昭
		private String shopCategoryStr;
		//店铺标签
		private String shopLabel;
		//店铺标签List用于左侧显示
		private List<String> shopLabelList;
		
		public List<String> getShopLabelList() {
			return shopLabelList;
		}

		public void setShopLabelList(List<String> shopLabelList) {
			this.shopLabelList = shopLabelList;
		}

		public String getShopType() {
			return shopType;
		}

		public String getShopCategoryStr() {
			return shopCategoryStr;
		}

		public void setShopCategoryStr(String shopCategoryStr) {
			this.shopCategoryStr = shopCategoryStr;
		}

		public void setShopType(String shopType) {
			this.shopType = shopType;
		}
		public ShopInfoDO getShopInfoDO() {
			return shopInfoDO;
		}

		public void setShopInfoDO(ShopInfoDO shopInfoDO) {
			this.shopInfoDO = shopInfoDO;
		}

		public String getItemCategory() {
			return itemCategory;
		}

		public void setItemCategory(String itemCategory) {
			this.itemCategory = itemCategory;
		}
}
