package com.yuwang.pinju.core.favorite.ao.impl;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.favorite.ao.FavoriteItemAO;
import com.yuwang.pinju.core.favorite.manager.FavoriteItemManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.favorite.FavoriteItemDO;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;


public class FavoriteItemAOImpl extends BaseAO implements FavoriteItemAO {
	
	private FavoriteItemManager favoriteItemManager;
	/****
	 * 保存收藏商品信息
	 * @param favoriteItem
	 * @return
	 */
	public Long saveFavoriteItem(FavoriteItemDO favoriteItem){
		try{
			return favoriteItemManager.saveFavoriteItem(favoriteItem);
		}catch(ManagerException ex){
			log.error("保存收藏商品信息出错"+ex);
		}
		return null;
	}
	
	/****
	 * 删除收藏商品信息
	 * @param favoriteItemList
	 *  id   编号id
	 *  ItemId  商品id
	 * @return
	 */
	public boolean deleteFavoriteItem(List<Long> idList,Long memberId){
		try{
			return favoriteItemManager.deleteFavoriteItem(idList,memberId);
		}catch(ManagerException ex){
			log.error(" 删除收藏商品信息出错"+ex);
		}
		return false;
	}
	
	
	/***
	 * 查找收藏商品信息并按字段排序
	 * @param favoriteItem
	 * @return
	 */
	public List<FavoriteItemDO> getFavoriteList(FavoriteItemDO favoriteItem){
		try{
			return favoriteItemManager.getFavoriteList(favoriteItem);
		}catch(ManagerException ex){
			log.error("查找收藏商品信息并按字段排序出错"+ex);
		}
		return null;
	}
	
	
	/******
	 * 收藏商品总条数
	 * @return
	 */
    public Integer getFavoriteListCount(FavoriteItemDO favoriteItem){
    	try{
			return favoriteItemManager.getFavoriteListCount(favoriteItem);
		}catch(ManagerException ex){
			log.error("收藏商品总条数出错"+ex);
		}
		return null;
    }
    
    /****
     * 根据日期查询当天收藏商品总数
     * @param date
     * @return
     */
    public Integer getFavoriteCountByDate(String date,Long userId){
    	try{
    		FavoriteItemDO favoriteItem=new FavoriteItemDO();
    		favoriteItem.setFavoriteDateStr(date);
    		favoriteItem.setMemberId(userId);
			return favoriteItemManager.getFavoriteListCount(favoriteItem);
		}catch(ManagerException ex){
			log.error("根据日期查询当天收藏商品总数出错"+ex);
		}
		return null;
    }
    
    /****
     * 根据用户id、商品id查询收藏商品
     * @param userId 用户id
     * @param ItemId 商品id
     * @return
     */
    public FavoriteItemDO getFavoriteItemByUserId(Long userId,Long ItemId){
    	try{
			return favoriteItemManager.getFavoriteItemByUserId(userId,ItemId);
		}catch(ManagerException ex){
			log.error("根据用户id、商品id查询收藏商品出错"+ex);
		}
		return null;
    }
    
	/*****
	 * 查找收藏商品分类id集合
	 * @param memberId
	 * @return
	 */
	public List<FavoriteItemDO> getFavoriteItemCategorys(Long memberId){
		try{
			return favoriteItemManager.getFavoriteItemCategorys(memberId);
		}catch(ManagerException ex){
			log.error("查找收藏商品分类id集合出错"+ex);
		}
		return null;
	}
	public FavoriteItemManager getFavoriteItemManager() {
		return favoriteItemManager;
	}

	public void setFavoriteItemManager(FavoriteItemManager favoriteItemManager) {
		this.favoriteItemManager = favoriteItemManager;
	}
}
