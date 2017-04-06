package com.yuwang.pinju.web.module.api.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.util.ByteArrayInputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yuwang.pinju.Constant.ItemKeyConstants;
import com.yuwang.pinju.common.MessageDigestUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.core.item.ao.ItemSalesStatAO;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;
import com.yuwang.pinju.domain.member.SellerQualityInfoDO;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * 
 * @author gongjiayun
 * 
 */

public class ItemAjaxAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemDetailAO itemDetailAO;
	private MemberAO memberAO;
	private ItemSalesStatAO itemSalesStatAO;
	private InputStream inputStream;

	/*
	 * 商品id
	 */
	private String itemId;
	/*
	 * 卖家资质,0表示不取,1表示取
	 */
	private String qual;
	/*
	 * 加密key
	 */
	private String key;

	/**
	 * <p>
	 * 品聚社区获取商品信息接口,返回0表示加密key不正确,返回1表示商品ID串全为空,返回2表示商品ID串中有不全为数字的ID,
	 * 返回3表示只传一个商品ID时不全为数字,返回4表示没有找到对应商品ID的商品信息,返回5表示查找卖家资质失败,返回6表示
	 * 所传卖家资质参数qual不全为数字,返回7表示所传参数中有为空的
	 * </p>
	 */
	public String execute() {
		JSONArray array = new JSONArray();
		List<ItemDO> itemList = new ArrayList<ItemDO>();

		if (!StringUtil.isBlank(itemId) && !StringUtil.isBlank(qual) && !StringUtil.isBlank(key)) {

			String keys = MessageDigestUtil.hexHashBySHA(itemId, ItemKeyConstants.OUT_URL_FROM_COMMUNITY);

			// 匹配加密字段
			if (!keys.equals(key)) {
				log.warn("加密key匹配不正确: keys:" + keys + ",key:" + key);
				return errorResponse("0");
			}

			if (!itemId.contains(",") && !StringUtil.isNumeric(itemId)) {
				log.warn("itemId不全是数字:" + itemId);
				return errorResponse("3");
			}

			// 查找商品信息
			String itemIds[] = itemId.split(",");
			if (itemIds.length == 0) {
				log.warn("itemIds[]长度为: " + itemIds.length);
				return errorResponse("1");
			}

			for (int i = 0; i < itemIds.length; i++) {
				if (!StringUtil.isNumeric(itemIds[i])) {
					log.warn("itemIds[]的第" + i + "项为空或不全是数字:" + itemIds[i]);
					return errorResponse("2");
				}
				Long id = Long.parseLong(itemIds[i]);
				ItemDO itemDO = itemDetailAO.getItemDOById(id);
				if (itemDO != null) {
					itemList.add(itemDO);
				}
			}

			if (itemList.isEmpty()) {
				log.warn("没有找到对应商品id的商品:" + itemId);
				return errorResponse("4");
			}

			for (ItemDO itemDO : itemList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("itemId", itemDO.getId());
				jsonObject.put("itemImgUrl", itemDO.getPicUrl());
				jsonObject.put("itemTitle", itemDO.getTitle());
				List<SkuDO> skuList = itemDO.getSkuList();

				if (!skuList.isEmpty()) {
					Long upperPrice = 0l;
					Long lowerPrice = 0l;
					for (SkuDO skuDO : skuList) {
						if (upperPrice == 0 || upperPrice < skuDO.getPrice().longValue()) {
							upperPrice = skuDO.getPrice().longValue();
						}
						if (lowerPrice == 0 || lowerPrice > skuDO.getPrice().longValue()) {
							lowerPrice = skuDO.getPrice().longValue();
						}
					}
					jsonObject.put("itemPriceMin", lowerPrice);
					jsonObject.put("itemPriceMax", upperPrice);
				} else {
					jsonObject.put("itemPriceMin", itemDO.getPrice());
					jsonObject.put("itemPriceMax", itemDO.getPrice());
				}

				jsonObject.put("memberID", itemDO.getSellerId());
				jsonObject.put("sellerNick", itemDO.getSellerNick());
				// 商品销量
				ItemSalesStatDO itemSalesStatDO = itemSalesStatAO.getItemSalesStatById(itemDO.getId());
				if (itemSalesStatDO != null) {
					jsonObject.put("itemSold", itemSalesStatDO.getBuyNum());
				} else {
					jsonObject.put("itemSold", 0);
				}

				// 是否要卖家资质信息
				if (StringUtil.isNumeric(qual)) {
					Long qualification = Long.parseLong(qual);
					if (qualification == 1l) {
						Result result = memberAO.getMemberShopQuality(itemDO.getSellerId());

						if (!result.isSuccess()) {
							log.warn("doControl execute result is incorrect, member id: [" + itemDO.getSellerId()
									+ "], result code: [" + result.getResultCode() + "]");
							return errorResponse("5");
						}
						SellerQualityInfoDO sqi = result.getModel(SellerQualityInfoDO.class);
						SellerQualityDO sellerQualityDO = sqi.getSellerQuality();
						jsonObject.put("shopType", sellerQualityDO.getSellerType());
						jsonObject.put("shopLevel", sellerQualityDO.getLevel());
					}
				} else {
					log.warn("卖家资质参数不全为数字:" + qual);
					return errorResponse("6");
				}
				array.add(jsonObject);
			}

		} else {
			log.warn("所传参数中有为空的,itemId:" + itemId + ",qual:" + qual + ",key:" + key);
			return errorResponse("7");
		}

		String json = array.toString();
		return response(json);
	}

	private String errorResponse(String eStr) {
		try {
			inputStream = new ByteArrayInputStream(eStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return SUCCESS;
	}

	private String response(String json) {
		try {
			inputStream = new ByteArrayInputStream(json.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return SUCCESS;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setItemDetailAO(ItemDetailAO itemDetailAO) {
		this.itemDetailAO = itemDetailAO;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getQual() {
		return qual;
	}

	public void setQual(String qual) {
		this.qual = qual;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setItemSalesStatAO(ItemSalesStatAO itemSalesStatAO) {
		this.itemSalesStatAO = itemSalesStatAO;
	}

}
