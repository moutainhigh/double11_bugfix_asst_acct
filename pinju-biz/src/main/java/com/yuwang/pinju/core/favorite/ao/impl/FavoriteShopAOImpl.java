package com.yuwang.pinju.core.favorite.ao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.favorite.ao.FavoriteShopAO;
import com.yuwang.pinju.core.favorite.manager.FavoriteShopManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;


public class FavoriteShopAOImpl extends BaseAO implements FavoriteShopAO {
	
	private FavoriteShopManager favoriteShopManager;

	/****
	 * 保存收藏店铺信息
	 * @param favoriteShop
	 * @return
	 */
	public Long saveFavoriteShop(FavoriteShopDO favoriteShop){
		  try{
			 return favoriteShopManager.saveFavoriteShop(favoriteShop);
		  }catch(ManagerException ex){
			  log.error("保存收藏店铺信息出错"+ex);
		  }
		return null;
	}
	
	/*****
	 * 删除收藏店铺信息
	 * @param favoriteShopList
	 * @return
	 */
	public boolean deleteFavoriteShop(List<Long> idList,Long memberId){
		  try{
			 return favoriteShopManager.deleteFavoriteShop(idList,memberId);
		  }catch(ManagerException ex){
			  log.error("删除收藏店铺信息出错"+ex);
			  return false;
		  }
	}
	
	
	/***
	 * 查找收藏店铺信息并按字段排序
	 * @param favoriteShop
	 * @return
	 */
	public List<FavoriteShopDO> getFavoriteList(FavoriteShopDO favoriteShop){
		 try{
			 return favoriteShopManager.getFavoriteList(favoriteShop);
		  }catch(ManagerException ex){
			  log.error("查找收藏店铺信息并按字段排序出错"+ex);
		  }
		  return null;
	}
	
	
	/*****
	 * 收藏店铺总条数
	 * @param favoriteShop
	 * @return
	 */
    public Integer getFavoriteListCount(FavoriteShopDO favoriteShop){
    	try{
			 return favoriteShopManager.getFavoriteListCount(favoriteShop);
		  }catch(ManagerException ex){
			  log.error("收藏店铺总条数出错"+ex);
		  }
		  return null;
    }
    
    /****
     * 根据日期查询当天收藏店铺总数
     * @param date
     * @param userId
     * @return
     */
    public Integer getFavoriteCountByDate(String date,Long userId){
    	try{
			 return favoriteShopManager.getFavoriteCountByDate(date,userId);
		  }catch(ManagerException ex){
			  log.error("根据日期查询当天收藏店铺总数出错"+ex);
		  }
		  return null;
    }

    /****
     * 根据会员id、店铺id查找收藏店铺信息
     * @param memberId
     * @param shopId
     * @return
     */
	public FavoriteShopDO getFavoriteShopByuserId(Long memberId,Long shopId){
		try{
			 return favoriteShopManager.getFavoriteShopByuserId(memberId,shopId);
		  }catch(ManagerException ex){
			  log.error("根据会员id、店铺id查找收藏店铺信息出错"+ex);
		  }
		  return null;
	}
	
	/***
	 * 查找收藏店铺信息
	 * @param favoriteShop
	 * @return
	 */
	public FavoriteShopDO getFavoriteShop(FavoriteShopDO favoriteShop){
		try{
			 return favoriteShopManager.getFavoriteShop(favoriteShop);
		  }catch(ManagerException ex){
			  log.error("查找收藏店铺信息出错"+ex);
		  }
		  return null;
	}
	
	/*****
	 * 查找收藏店铺分类id集合
	 * @param memberId
	 * @return
	 */
	public List<FavoriteShopDO> getFavoriteShopCategorys(Long memberId){
		try{
			 return favoriteShopManager.getFavoriteShopCategorys(memberId);
		  }catch(ManagerException ex){
			  log.error("查找收藏店铺信息出错"+ex);
		  }
		  return null;
	}
    
	public FavoriteShopManager getFavoriteShopManager() {
		return favoriteShopManager;
	}

	public void setFavoriteShopManager(FavoriteShopManager favoriteShopManager) {
		this.favoriteShopManager = favoriteShopManager;
	}
}
