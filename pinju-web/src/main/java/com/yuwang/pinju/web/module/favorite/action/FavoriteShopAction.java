package com.yuwang.pinju.web.module.favorite.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.favorite.FavoriteConstant;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.favorite.ao.FavoriteShopAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;

public class FavoriteShopAction implements PinjuAction {
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private Long shopId;
	
	private String shopName;
	
	private Long categoryId;
	
	private JSONObject result;
	
	private Integer goodsCount;
	
	
	private FavoriteShopAO favoriteShopAO;
	
	private CategoryCacheManager categoryCacheManager;
	
	/********批量选择删除收藏店铺id************/
	
	private List<Long> idCheck;
	
	private Long id;
	
	private List<FavoriteShopDO> categoryList;
	
	private ShopShowInfoManager shopShowInfoManager;
	
//	private ShopInfoDO shopInfoDO;
	/**
	 * 当前页
	 */
	private Integer currentPage;
	
	private Paginator query;
	
	private ItemManager itemManager;
	
	 /*********收藏店铺信息集合*******/
	private List<FavoriteShopDO> favoriteShopList;
	
	
	/***********收藏店铺************/
	public String addFavoriteShop(){
		result = this.saveFavoriteShop();
		return "success";
	}
    public JSONObject saveFavoriteShop(){
    	Long userId=getUserId();
    	JSONObject json = new JSONObject();
    	if(null==userId || userId.intValue()==0){
    		json.put("isInsert", "-100");
    	}else{
	    	try {
	    		FavoriteShopDO favoriteShop=new FavoriteShopDO();
	    		favoriteShop.setMemberId(userId);
	    		favoriteShop.setShopId(shopId);
	    		favoriteShop.setShopName(shopName);
	    		favoriteShop.setCreateDate(new Date());
	    		favoriteShop.setIsDelete(ItemConstant.STATUS_TYPE_1);
	//    		long rootId=0;
	//  			String rootName="";
	    		try{
	   			 CategoryDO category=categoryCacheManager.getCategoryDOById(categoryId);
	//   			 if(category.getParentId()!=0){
	//   				 CategoryDO parentCateDO = category.getParentCategoryDO();
	//       			 while(parentCateDO.getParentId()!=0){
	//       				 parentCateDO = parentCateDO.getParentCategoryDO();
	//       			 }
	//       			 rootId = parentCateDO.getId();
	//   				 rootName = parentCateDO.getName();
	//   			 }else{
	//   				 rootId = category.getId();
	//   				 rootName = category.getName();
	//   			 }
	   			favoriteShop.setCategoryId(category.getId());
	   			favoriteShop.setCategoryName(category.getName());
	   			}catch(Exception e){
	   				log.error("查询店铺类型的名称出错"+e);
	   			}
	    		favoriteShop.setFavoriteDate(new Date());
	    		FavoriteShopDO shop=favoriteShopAO.getFavoriteShopByuserId(userId,shopId);
	            if(null==shop){
	            	FavoriteShopDO favoriteShopDO=new FavoriteShopDO();
					favoriteShopDO.setMemberId(userId);
					Integer count=favoriteShopAO.getFavoriteListCount(favoriteShopDO);
	                if(null!=count && count.intValue()<FavoriteConstant.FAVORITE_SHOP_TOTALCOUNT){
	                	Integer dayCount=favoriteShopAO.getFavoriteCountByDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()),userId);
						if(null!=dayCount && dayCount<FavoriteConstant.FAVORITE_SHOP_COUNTDAY){
							Long  id=favoriteShopAO.saveFavoriteShop(favoriteShop);
							if(null!=id){
								json.put("isInsert", "1");
								json.put("favoriteCategoryName",favoriteShop.getCategoryName());
								json.put("favoriteShopCount",count+1);
							}else{
								json.put("isInsert", "-1");
								json.put("message", "插入失败");
							}
						}else{
							json.put("isInsert", "-2");
							json.put("message", "当天收藏店铺数超过"+FavoriteConstant.FAVORITE_SHOP_COUNTDAY+",不能再添加");
						}
	               }else{
	            	   json.put("isInsert", "-3");
						json.put("message", "收藏店铺总数超过"+FavoriteConstant.FAVORITE_SHOP_TOTALCOUNT+",不能再添加");
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
    
    @SuppressWarnings("unchecked")
	public String queryFavoriteShopList(){
    	currentPage=this.currentPage!=null?this.currentPage:1;
    	Long userId=getUserId();
    	FavoriteShopDO favoriteShop=new FavoriteShopDO();
    	favoriteShop.setMemberId(userId);
    	if(null!=categoryId){
    		favoriteShop.setCategoryId(categoryId);
    	}
    	favoriteShop.setPageCount(FavoriteConstant.PAGE_ITEM);
    	favoriteShop.setStartRow((currentPage-1)*FavoriteConstant.PAGE_ITEM);
    	categoryList=favoriteShopAO.getFavoriteShopCategorys(userId);    
    	Integer count=favoriteShopAO.getFavoriteListCount(favoriteShop);
    	List<FavoriteShopDO> resultList=favoriteShopAO.getFavoriteList(favoriteShop);
    	if(null!=resultList && resultList.size()>0){
    		favoriteShopList=new ArrayList<FavoriteShopDO>();
        		for(int i=0;i<resultList.size();i++){
        			FavoriteShopDO favDO=resultList.get(i);
        			ItemQuery itemQuery=new ItemQuery();
            		List status=new ArrayList();
            		status.add(ItemConstant.STATUS_TYPE_0);
            		ShopInfoDO	shopInfoDO=null;
            		try{
            			shopInfoDO=shopShowInfoManager.queryShopInfoByShopId(favDO.getShopId().intValue(),ShopConstant.APPROVE_STATUS_YES);
            		}catch(ManagerException ex){
            			log.error("查询店铺信息出错"+ex);
            		}
            		 if(null!=shopInfoDO){
            			 favDO.setShopInfoDO(shopInfoDO);
            			 itemQuery.setSellerId(shopInfoDO.getUserId());
                 		itemQuery.setStatus(status);
                 		try{
                 	       goodsCount=itemManager.getItemListCount(itemQuery);
                 	       favDO.setGoodsCount(goodsCount);
                 		}catch(Exception ex){
                 			log.error("查询店铺商品数量出错"+ex);
                 		}
                 		favoriteShopList.add(favDO);
            		 }
        		}
    		query=new Paginator();
    		query.setItems(count);
    		query.setItemsPerPage(FavoriteConstant.PAGE_ITEM);
    		query.setPage(currentPage);
    		query.setAction("/favorite/queryFavoriteShopListAction.htm");
    	    return "favoriteList";
		}else{
			return "noResult";
		}
    	
    }
    
    /**********删除收藏店铺***********/
    public String deleteFavoriteShop(){
    	Long userId=getUserId();
    	if(idCheck==null){
			idCheck=new ArrayList<Long>();
			idCheck.add(id);
		}
		boolean  isScu=favoriteShopAO.deleteFavoriteShop(idCheck,userId);
		if(isScu){
			return SUCCESS;
		}else{
			return ERROR;
		}
    }
    
    @SuppressWarnings("unchecked")
	public String queryFavoryShopByCategoy(){
    	currentPage=this.currentPage!=null?this.currentPage:1;
    	Long userId=getUserId();
    	FavoriteShopDO favoriteShop=new FavoriteShopDO();
    	favoriteShop.setMemberId(userId);
    	favoriteShop.setPageCount(FavoriteConstant.PAGE_ITEM);
    	favoriteShop.setStartRow((currentPage-1)*FavoriteConstant.PAGE_ITEM);
    	if(null!=categoryId){
    		favoriteShop.setCategoryId(categoryId);
    	}
    	/****获得当前用户所收藏的店铺类目信息****/
    	categoryList=favoriteShopAO.getFavoriteShopCategorys(userId);
    	Integer count=favoriteShopAO.getFavoriteListCount(favoriteShop);
    	List<FavoriteShopDO> resultList=favoriteShopAO.getFavoriteList(favoriteShop);
    	if(null!=resultList && resultList.size()>0){
    		favoriteShopList=new ArrayList<FavoriteShopDO>();
    		for(int i=0;i<resultList.size();i++){
    			FavoriteShopDO favDO=resultList.get(i);
    			ItemQuery itemQuery=new ItemQuery();
        		List status=new ArrayList();
        		status.add(ItemConstant.STATUS_TYPE_0);
        		ShopInfoDO	shopInfoDO=null;
        		try{
           		   shopInfoDO=shopShowInfoManager.queryShopInfoByShopId(favDO.getShopId().intValue(),ShopConstant.APPROVE_STATUS_YES);
           		 
           		}catch(ManagerException ex){
           			log.error("查询店铺信息出错"+ex);
           		}
           		if(null!=shopInfoDO){
          			favDO.setShopInfoDO(shopInfoDO);
          			itemQuery.setSellerId(shopInfoDO.getUserId());
            		itemQuery.setStatus(status);
            		try{
            	       goodsCount=itemManager.getItemListCount(itemQuery);
            	       favDO.setGoodsCount(goodsCount);
            		}catch(Exception ex){
            			log.error("查询店铺商品数量出错"+ex);
            		}
            		favoriteShopList.add(favDO);
          		 }
    		}
    		query=new Paginator();
    		query.setItems(count);
    		query.setItemsPerPage(FavoriteConstant.PAGE_ITEM);
    		query.setPage(currentPage);
    		query.setAction("/favorite/queryFavoryShopByCategoyAction.htm");
    		return "favoriteList";
		}else{
			return "noResult";
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
	public JSONObject getResult() {
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public Paginator getQuery() {
		return query;
	}
	public List<FavoriteShopDO> getFavoriteShopList() {
		return favoriteShopList;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public void setQuery(Paginator query) {
		this.query = query;
	}
	public void setFavoriteShopList(List<FavoriteShopDO> favoriteShopList) {
		this.favoriteShopList = favoriteShopList;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public CategoryCacheManager getCategoryCacheManager() {
		return categoryCacheManager;
	}
	public FavoriteShopAO getFavoriteShopAO() {
		return favoriteShopAO;
	}
	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}
	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
//	public ShopInfoDO getShopInfoDO() {
//		return shopInfoDO;
//	}
//	public void setShopInfoDO(ShopInfoDO shopInfoDO) {
//		this.shopInfoDO = shopInfoDO;
//	}
	public List<Long> getIdCheck() {
		return idCheck;
	}
	public void setIdCheck(List<Long> idCheck) {
		this.idCheck = idCheck;
	}
	public List<FavoriteShopDO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<FavoriteShopDO> categoryList) {
		this.categoryList = categoryList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setFavoriteShopAO(FavoriteShopAO favoriteShopAO) {
		this.favoriteShopAO = favoriteShopAO;
	}
	public ItemManager getItemManager() {
		return itemManager;
	}
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}
	
}
