package com.yuwang.pinju.web.cookie.convert;

import java.util.ArrayList;
import java.util.List;

import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.cookie.CookieShoppingCart;

/**
 * <p>
 * 购物车数据转换器
 * </p>
 *
 * @author gaobaowen
 * @since 2011-7-14 10:17:56
 */
public class ShoppingCarConvert extends BasicConvert<CookieShoppingCart[]> {

	/**
	 * 购物车中商品分隔符
	 */
	private final static String SHOPPING_CAR_ITEM_SEPARATOR = "~";

	/**
	 * 每个商品中各项的分隔符
	 */
	private final static String SHOPPING_CAR_ITEM_DETAIL_SEPARATOR = ",";

	@Override
	public String encode(CookieShoppingCart[] obj) {
		if (obj == null || obj.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0, k = 0; i < obj.length; i++) {
			if (obj[i].getItemId() == null) {
				continue;
			}
			if (k++ > 0) {
				builder.append(SHOPPING_CAR_ITEM_SEPARATOR);
			}
			builder.append(obj[i].getItemId()).append(SHOPPING_CAR_ITEM_DETAIL_SEPARATOR);
			builder.append(obj[i].getItemCount()).append(SHOPPING_CAR_ITEM_DETAIL_SEPARATOR);
			builder.append(obj[i].getItemPrice()).append(SHOPPING_CAR_ITEM_DETAIL_SEPARATOR);
			builder.append(obj[i].getSkuid()).append(SHOPPING_CAR_ITEM_DETAIL_SEPARATOR);
			builder.append(obj[i].getSkuDesc()).append(SHOPPING_CAR_ITEM_DETAIL_SEPARATOR);
			builder.append(obj[i].getChannelId()).append(SHOPPING_CAR_ITEM_DETAIL_SEPARATOR);
			builder.append(obj[i].getAd()).append(SHOPPING_CAR_ITEM_DETAIL_SEPARATOR);
			builder.append(obj[i].getTime());
		}
		return builder.toString();
	}

	@Override
	public CookieShoppingCart[] decode(String value) {
		if (EmptyUtil.isBlank(value)) {
			log.warn("decode shopping car data, value is empty, cannot decode");
			return null;
		}
		String[] items = value.split(SHOPPING_CAR_ITEM_SEPARATOR, -1);
		if (items == null || items.length == 0) {
			log.warn("decode shopping car data, split item info, info is null, value: " + value);
			return null;
		}

		List<CookieShoppingCart> cars = new ArrayList<CookieShoppingCart>(items.length);
		for (int i = 0; i < items.length; i++) {
			String[] item = items[i].split(SHOPPING_CAR_ITEM_DETAIL_SEPARATOR, -1);
			if (item == null || item.length != 8) {
				log.warn("decode shopping car item, data is empty or count is not 8 after item splitting, skip this, " +
						"item data: " + items + ", item length: " + (item == null ? null : item.length));
				continue;
			}
			CookieShoppingCart car = new CookieShoppingCart(item[0], item[1], item[2], item[3], item[4], item[5], item[6], item[7]);
			cars.add(car);
		}
		return cars.toArray(new CookieShoppingCart[cars.size()]);
	}
	
	public static void main(String[] args) {
		String[] str = "1,2,3,4,5,".split(",", -1);
		System.out.println(str.length);
	}
}
