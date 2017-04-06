package com.yuwang.pinju.web.module.shop.action;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import com.yuwang.pinju.core.shop.ao.ShopLabelAO;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;
/**
 * 店铺标签Action
 * @author 杨昭
 * @since 2011-12-7
 */
public class ShopLabelAction extends BaseWithUserAction{
	private ShopLabelAO shopLabelAO;
	private ShopInfoDO shopInfoDO;
	/**
	 * 分布式缓存
	 */
	private MemcachedClient shopMemcachedClient;
	public MemcachedClient getShopMemcachedClient() {
		return shopMemcachedClient;
	}
	public void setShopMemcachedClient(MemcachedClient shopMemcachedClient) {
		this.shopMemcachedClient = shopMemcachedClient;
	}
	private String wordData = " ";
	private String str = "品聚";
	//修改状态
	private String message;
	//店铺标签
	private List<String> shopLabelList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWordData() {
		return wordData;
	}
	public void setWordData(String wordData) {
		this.wordData = wordData;
	}
	public List<String> getShopLabelList() {
		return shopLabelList;
	}
	public void setShopLabelList(List<String> shopLabelList) {
		this.shopLabelList = shopLabelList;
	}
	public ShopInfoDO getShopInfoDO() {
		return shopInfoDO;
	}
	public void setShopInfoDO(ShopInfoDO shopInfoDO) {
		this.shopInfoDO = shopInfoDO;
	}
	public ShopLabelAO getShopLabelAO() {
		return shopLabelAO;
	}
	public void setShopLabelAO(ShopLabelAO shopLabelAO) {
		this.shopLabelAO = shopLabelAO;
	}
	/**
	 * 进入店铺标签设置页
	 * @author 杨昭
	 * @since 2011-12-7
	 */
	@AssistantPermission(action="shop",target="label")
	public String toShopLabel(){
		ShopInfoDO shopInfo = new ShopInfoDO();
		shopInfo.setUserId(super.getUserId());
		try{
			shopInfoDO = shopLabelAO.getShopLabelByShopId(shopInfo);
			if(shopInfoDO.getShopLabel()==null){
				wordData = str+" "+wordData;
				shopInfoDO.setShopLabel(wordData);
			}
		}catch(Exception e){
			log.error("进入店铺标签设置页", e);
		}
		return SUCCESS;
	}
	/**
	 * 修改店铺标签
	 * @author 杨昭
	 * @since 2011-12-7
	 */
	public String updateShopLabel(){
		try {
			ShopInfoDO shopInfo = new ShopInfoDO();
			shopInfo.setUserId(super.getUserId());
			if(wordData.indexOf("品聚")==-1){
				wordData = str+" "+wordData;
			}
			shopInfo .setShopLabel(wordData);
			
			//敏感词
			if(WordFilterFacade.scan(shopInfo.getShopLabel(),9)){
				message = "M";
			}else{
				Integer number = shopLabelAO.updateShopLabel(shopInfo);
				if(number>0){
					message = "Y";
					shopMemcachedClient.delete(shopInfo.getUserId().toString());
				}else{
					message ="N";
				}
			}
			shopInfoDO = shopLabelAO.getShopLabelByShopId(shopInfo);
		} catch (Exception e) {
			log.error("修改店铺标签",e);
		}
		return SUCCESS;
	}
}
