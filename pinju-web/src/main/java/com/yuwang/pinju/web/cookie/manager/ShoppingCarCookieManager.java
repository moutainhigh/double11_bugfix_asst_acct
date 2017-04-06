package com.yuwang.pinju.web.cookie.manager;

import static com.yuwang.pinju.web.cookie.PinjuCookieName.CG_SHOPPING_CAR;
import static com.yuwang.pinju.web.cookie.PinjuCookieName.CN_SHOPPING_CAR_SC;
import static com.yuwang.pinju.web.cookie.PinjuCookieName.CN_SHOPPING_CAR_SD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.cookie.CookieManager;
import com.yuwang.cookie.CookieMapping;
import com.yuwang.cookie.rw.CookieReader;
import com.yuwang.cookie.rw.CookieWriter;
import com.yuwang.pinju.web.cookie.CookieShoppingCart;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;

/**
 * <p>购物车 Cookie 管理类</p>
 *
 * @author gaobaowen
 * @since 2011-7-14 11:23:48
 */
public class ShoppingCarCookieManager {

	private final static Log log = LogFactory.getLog(ShoppingCarCookieManager.class);

	private ShoppingCarCookieManager() {
	}

	/**
	 * <p>将购物车数据写入用户浏览器 Cookie</p>
	 *
	 * @param cars
	 *
	 * @author gaobaowen
	 * @since 2011-7-14 11:14:14
	 */
	public static void writeShoppingCars(CookieShoppingCart[] cars) {
		CookieWriter writer = CookieManager.newCookieWriter(CG_SHOPPING_CAR);
		writer.addCookie(CN_SHOPPING_CAR_SC, cars);
		
		int len = 0;
		
		if(cars != null){
			len = cars.length;
			for(CookieShoppingCart car:cars){
				if(car.getItemId() == null){
					len--;
				}
			}
		}
		
		writer.addCookie(CN_SHOPPING_CAR_SD, len);
		
		writer.write(ServletActionContext.getResponse());
	}

	/**
	 * 清空购物车信息
	 * 
	 * @return
	 */
	public static void clearShoppingCart(){
		writeShoppingCars(null);
	}
	
	/**
	 * <p>删除购物车中的某条数据</p>
	 *
	 * @param itemList  需要删除的购物车商品 ID，删除购物车的商品id + ' ' + skuid, 如果skuid没有，则为0；如果有多组，用,分隔
	 *
	 * @author shihongbo
	 */
	public static void deleteItemFromShoppingCart(String itemList) {
		String[] itemIds = itemList.split(",");
		if(itemIds == null || itemIds.length == 0) {
			return;
		}
		
		CookieShoppingCart[] cars = getCookieShoppingCarts();
		if(cars == null || cars.length == 0) {
			return;
		}
		
		for(int i = 0; i < itemIds.length; i++) {
			int p = itemIds[i].indexOf(' ');
			String itemId = itemIds[i].substring(0, p);
			String delSkuid = itemIds[i].substring(p + 1, itemIds[i].length());
			
			for(int j = 0; j < cars.length; j++) {
				String id = cars[j].getItemId();
				String skuid = cars[j].getSkuid();
				if(id != null && id.equals(itemId) &&
						skuid != null && skuid.equals(delSkuid)) {
					cars[j].setItemId(null);
					cars[j].setSkuid(null);
				}
			}
		}
		
		writeShoppingCars(cars);
	}

	/**
	 * <p>获取用户的购物车数据 </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-14 11:01:34
	 */
	public static CookieShoppingCart[] getCookieShoppingCarts() {
		CookieReader reader = CookieManager.newCookieReader(CG_SHOPPING_CAR);
		CookieMapping mapping = PinjuCookieManager.getCookieMapping();
		if (!reader.readCookies(mapping, ServletActionContext.getResponse())) {
			log.debug("no shopping car data , cannot read shopping cookies value");
			return new CookieShoppingCart[0];
		}
		return (CookieShoppingCart[])reader.getOriginalValue(CN_SHOPPING_CAR_SC);
	}
	
	public static int getCookieShoppingCartsCount() {
		CookieReader reader = CookieManager.newCookieReader(CG_SHOPPING_CAR);
		CookieMapping mapping = PinjuCookieManager.getCookieMapping();
		if (!reader.readCookies(mapping, ServletActionContext.getResponse())) {
			log.debug("no shopping car data , cannot read shopping cookies value");
			return 0;
		}
		Integer count = (Integer)reader.getOriginalValue(CN_SHOPPING_CAR_SD);
		if (count == null) {
			return 0;
		}
		return count;
	}
}
