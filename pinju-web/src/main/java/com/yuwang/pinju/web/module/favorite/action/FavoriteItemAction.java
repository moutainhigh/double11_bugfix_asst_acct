package com.yuwang.pinju.web.module.favorite.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.favorite.FavoriteConstant;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.favorite.ao.FavoriteItemAO;
import com.yuwang.pinju.core.item.ao.ItemSalesStatAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.favorite.FavoriteItemDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;

public class FavoriteItemAction implements PinjuAction {
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private Long shopId;
	
	private String shopName;
	
	private Long itemId;
	
	private Long categoryId;
	
	private String result;
	
	private FavoriteItemAO favoriteItemAO;
	
	private ItemSalesStatAO itemSalesStatAO;
	
	private CategoryCacheManager categoryCacheManager;
	
	private ItemManager itemManager;
	
	/********批量选择删除收藏商品id************/
	
	private List<Long> idCheck;
	
	private Long id;
	
	/**
	 * 当前页
	 */
	private String currentPage;
	
	private Paginator query;
	
	 /*********收藏商品信息集合*******/
	private List<FavoriteItemDO> favoriteItemList;
	
	private  List<FavoriteItemDO> categoryList;
	
	
	/***********收藏商品************/
	public String addFavoriteItem(){
		JSONObject json = this.saveFavoriteItem();
		result = json.toString();
		return "success";
	}
    public JSONObject saveFavoriteItem(){
    	Long userId=getUserId();
    	JSONObject json = new JSONObject();
    	if(null==userId || userId.intValue()==0){
    		json.put("isInsert", "-100");
    	}else{
	    	try {
	    		FavoriteItemDO favoriteItem=new FavoriteItemDO();
	            favoriteItem.setMemberId(userId);
	            favoriteItem.setShopId(shopId);
	            favoriteItem.setShopName(shopName);
	            favoriteItem.setItemId(itemId);
	            favoriteItem.setCreateDate(new Date());
	            favoriteItem.setIsDelete(ItemConstant.STATUS_TYPE_1);
	            try{
	      			 CategoryDO category=categoryCacheManager.getForestCategoryDOById(categoryId);
	      			 long rootId=0;
	      			 String rootName="";
	      			 if(category.getParentId()!=0){
	      				 CategoryDO parentCateDO = category.getParentCategoryDO();
	          			 while(parentCateDO.getParentId()!=0){
	          				 parentCateDO = parentCateDO.getParentCategoryDO();
	          			 }
	          			 rootId = parentCateDO.getId();
	      				 rootName = parentCateDO.getName();
	      			 }else{
	      				 rootId = category.getId();
	      				 rootName = category.getName();
	      			 }
	      			favoriteItem.setCategoryId(rootId);
	      			favoriteItem.setCategoryName(rootName);
	      			}catch(Exception e){
	      				log.error("查询店铺类型的名称出错"+e);
	      			}
	            favoriteItem.setFavoriteDate(new Date());
	            FavoriteItemDO item=favoriteItemAO.getFavoriteItemByUserId(userId,itemId);
	            if(null==item){
					FavoriteItemDO favoriteItemDO=new FavoriteItemDO();
					favoriteItemDO.setMemberId(userId);
					Integer count=favoriteItemAO.getFavoriteListCount(favoriteItemDO);
					if(null!=count && count.intValue()<FavoriteConstant.FAVORITE_ITEM_TOTALCOUNT){
						Integer dayCount=favoriteItemAO.getFavoriteCountByDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()),userId);
						if(null!=dayCount && dayCount.intValue()<FavoriteConstant.FAVORITE_ITEM_COUNTDAY){
							Long  id=favoriteItemAO.saveFavoriteItem(favoriteItem);
							if(null!=id){
								json.put("isInsert", "1");
								json.put("categoryName", favoriteItem.getCategoryName());
								json.put("favoriteItemCount", count+1);
							}else{
								json.put("isInsert", "-1");
							}
						}else{
							json.put("isInsert", "-2");
						}
					}else{
						json.put("isInsert", "-3");
					}
	            }else{
	            	json.put("isInsert", "0");
	            }
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
			}
    	}
    	return json;
    }
    
    public String queryFavoriteItemList(){
    	currentPage=this.currentPage!=null?this.currentPage:"1";
    	Long userId=getUserId();
    	FavoriteItemDO favoriteItem=new FavoriteItemDO();
    	favoriteItem.setMemberId(userId);
    	if(null!=categoryId){
    		favoriteItem.setCategoryId(categoryId);
    	}
    	favoriteItem.setPageCount(FavoriteConstant.PAGE_ITEM);
    	favoriteItem.setStartRow((Integer.parseInt(currentPage)-1)*FavoriteConstant.PAGE_ITEM);
    	List<FavoriteItemDO> resultList=favoriteItemAO.getFavoriteList(favoriteItem);
    	/****获得当前用户所收藏的商品类目信息****/
    	categoryList=favoriteItemAO.getFavoriteItemCategorys(userId);
    	if(null!=resultList && resultList.size()>0){
    		List<Long> itemIds=new ArrayList<Long>();
    		for(int i=0;i<resultList.size();i++){
    			itemIds.add(resultList.get(i).getItemId());
    		}
    		List<ItemDO> itemList=null;
    		try{
    	       itemList=itemManager.getItemListByIds(itemIds);
    		}catch(ManagerException ex){
    			log.error("根据商品组查询商品报错"+ex);
    		}
    		favoriteItemList=new ArrayList<FavoriteItemDO>();
    		for(int i=0;i<resultList.size();i++){
    			FavoriteItemDO favoriteItmDO=resultList.get(i);
    			ItemDO itemDO=null;
    			if(null!=itemList && itemList.size()>0){
    				itemDO=itemList.get(i);
    				ItemSalesStatDO itemSalesStatDO = itemSalesStatAO.getItemSalesStatById(itemDO.getId());
    				if(itemSalesStatDO != null){
    					Long buyNum = itemSalesStatDO.getBuyNum();
    					itemDO.setSales(buyNum);
    				}else{
    					itemDO.setSales(0L);
    				}
    				favoriteItmDO.setItemDO(itemDO);
    			}
    			favoriteItemList.add(favoriteItmDO);
    		}
    		Integer count=favoriteItemAO.getFavoriteListCount(favoriteItem);
        	query=new Paginator();
    		query.setItems(count);
    		query.setItemsPerPage(FavoriteConstant.PAGE_ITEM);
    		query.setPage(Integer.parseInt(currentPage));
    		query.setAction("/favorite/queryFavoriteItemListAction.htm");
        	return "favoriteList";
    	}else{
    		return "noResult";
    	}
    	
    }
    
    
    public String queryFavoryItemByCategoy(){
    	currentPage=this.currentPage!=null?this.currentPage:"1";
    	Long userId=getUserId();
    	FavoriteItemDO favoriteItem=new FavoriteItemDO();
    	favoriteItem.setMemberId(userId);
    	favoriteItem.setPageCount(FavoriteConstant.PAGE_ITEM);
    	if(null!=categoryId){
    		favoriteItem.setCategoryId(categoryId);
    	}
    	favoriteItem.setStartRow((Integer.parseInt(currentPage)-1)*FavoriteConstant.PAGE_ITEM);
    	List<FavoriteItemDO> resultList=favoriteItemAO.getFavoriteList(favoriteItem);
    	categoryList=favoriteItemAO.getFavoriteItemCategorys(userId);
    	if(null!=resultList && resultList.size()>0){
    		List<Long> itemIds=new ArrayList<Long>();
    		for(int i=0;i<resultList.size();i++){
    			itemIds.add(resultList.get(i).getItemId());
    		}
    		List<ItemDO> itemList=null;
    		try{
    	       itemList=itemManager.getItemListByIds(itemIds);
    		}catch(ManagerException ex){
    			log.error("根据商品组查询商品报错"+ex);
    		}
    		favoriteItemList=new ArrayList<FavoriteItemDO>();
    		for(int i=0;i<resultList.size();i++){
    			FavoriteItemDO favoriteItmDO=resultList.get(i);
    			ItemDO itemDO=null;
    			if(null!=itemList && itemList.size()>0){
    				itemDO=itemList.get(i);
    				ItemSalesStatDO itemSalesStatDO = itemSalesStatAO.getItemSalesStatById(itemDO.getId());
    				if(itemSalesStatDO != null){
    					Long buyNum = itemSalesStatDO.getBuyNum();
    					itemDO.setSales(buyNum);
    				}else{
    					itemDO.setSales(0L);
    				}
    				favoriteItmDO.setItemDO(itemDO);
    			}
    			favoriteItemList.add(favoriteItmDO);
    		}
    		Integer count=favoriteItemAO.getFavoriteListCount(favoriteItem);
        	query=new Paginator();
    		query.setItems(count);
    		query.setItemsPerPage(FavoriteConstant.PAGE_ITEM);
    		query.setPage(Integer.parseInt(currentPage));
    		query.setAction("/favorite/queryFavoryItemByCategoyAction.htm");
        	return "favoriteList";
    	}else{
    		return "noResult";
    	}
    	
    }
    
    /*********删除收藏商品*********/
    public String deleteFavoriteItem(){
    	Long userId=getUserId();
    	if(idCheck==null){
			idCheck=new ArrayList<Long>();
			idCheck.add(id);
		}
		boolean  isScu=favoriteItemAO.deleteFavoriteItem(idCheck,userId);
		if(isScu){
			return SUCCESS;
		}else{
			return ERROR;
		}
    }
    
   //获得当前登录人的ID
	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMemberId();
		}if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}
	
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Long getItemId() {
		return itemId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public FavoriteItemAO getFavoriteItemAO() {
		return favoriteItemAO;
	}
	public void setFavoriteItemAO(FavoriteItemAO favoriteItemAO) {
		this.favoriteItemAO = favoriteItemAO;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public ItemSalesStatAO getItemSalesStatAO() {
		return itemSalesStatAO;
	}
	public void setItemSalesStatAO(ItemSalesStatAO itemSalesStatAO) {
		this.itemSalesStatAO = itemSalesStatAO;
	}
	public CategoryCacheManager getCategoryCacheManager() {
		return categoryCacheManager;
	}
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public Paginator getQuery() {
		return query;
	}
	public List<FavoriteItemDO> getFavoriteItemList() {
		return favoriteItemList;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public void setQuery(Paginator query) {
		this.query = query;
	}
	public List<Long> getIdCheck() {
		return idCheck;
	}
	public Long getId() {
		return id;
	}
	public void setIdCheck(List<Long> idCheck) {
		this.idCheck = idCheck;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setFavoriteItemList(List<FavoriteItemDO> favoriteItemList) {
		this.favoriteItemList = favoriteItemList;
	}
	public List<FavoriteItemDO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<FavoriteItemDO> categoryList) {
		this.categoryList = categoryList;
	}
	public ItemManager getItemManager() {
		return itemManager;
	}
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
}
