package com.yuwang.pinju.core.favorite.manager.impl;


import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.favorite.dao.FavoriteItemDAO;
import com.yuwang.pinju.core.favorite.manager.FavoriteItemManager;
import com.yuwang.pinju.domain.favorite.FavoriteItemDO;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public class FavoriteItemManagerImpl implements FavoriteItemManager {
	
	private FavoriteItemDAO favoriteItemDAO;
	/****
	 * 保存收藏商品信息
	 * @param favoriteItem
	 * @return
	 * @throws ManagerException
	 */
	public Long saveFavoriteItem(FavoriteItemDO favoriteItem)throws ManagerException{
		try{
			return favoriteItemDAO.saveFavoriteItem(favoriteItem);
		}catch(DaoException ex){
		   throw new ManagerException("保存收藏商品信息出错"+ex);	
		}
	}
	
	/****
	 * 删除收藏商品信息
	 * @param favoriteItemList
	 *  id   编号id
	 *  ItemId  商品id
	 * @return
	 * @throws ManagerException
	 */
	public boolean deleteFavoriteItem(List<Long> idList,Long memberId)throws ManagerException{
		try{
		   favoriteItemDAO.deleteFavoriteItem(idList,memberId);
			return true;
		}catch(DaoException ex){
		   throw new ManagerException("删除收藏商品信息出错"+ex);	
		}
	}
	
	
	/***
	 * 查找收藏商品信息并按字段排序
	 * @param favoriteItem
	 * @return
	 * @throws ManagerException
	 */
	public List<FavoriteItemDO> getFavoriteList(FavoriteItemDO favoriteItem)throws ManagerException{
		try{
		     return favoriteItemDAO.getFavoriteList(favoriteItem);
		}catch(DaoException ex){
		   throw new ManagerException("查找收藏商品信息并按字段排序出错"+ex);	
		}
	}
	
	/*****
	 * 收藏商品总条数
	 * @param favoriteItem
	 * @return
	 * @throws ManagerException
	 */
    public Integer getFavoriteListCount(FavoriteItemDO favoriteItem)throws ManagerException{
    	try{
		     return favoriteItemDAO.getFavoriteListCount(favoriteItem);
		}catch(DaoException ex){
		   throw new ManagerException("收藏商品总条数出错"+ex);	
		}
    }
    
    /****
     * 根据日期查询当天收藏商品总数
     * @param date
     * @return
     * @throws ManagerException
     */
    public Integer getFavoriteCountByDate(Date date)throws ManagerException{
    	try{
    		 FavoriteItemDO favoriteItem=new FavoriteItemDO();
    		 favoriteItem.setFavoriteDate(date);
		     return favoriteItemDAO.getFavoriteListCount(favoriteItem);
		}catch(DaoException ex){
		   throw new ManagerException("根据日期查询当天收藏商品总数出错"+ex);	
		}
    }
    
    /****
     * 删除商品时给收藏的商品打标记
     * @param memberId
     * @param itemId
     * @param  status
     * @return
     * @throws ManagerException
     */
    public boolean updateFavorite(Long memberId,Long itemId,Long status)throws ManagerException{
    	try{
    		FavoriteItemDO favoriteItemDO=new FavoriteItemDO();
        	favoriteItemDO.setMemberId(memberId);
        	favoriteItemDO.setItemId(itemId);
        	favoriteItemDO.setIsDelete(status);
		    favoriteItemDAO.updateFavorite(favoriteItemDO);
		    return true;
		}catch(DaoException ex){
		   throw new ManagerException("删除商品时给收藏的商品打标记出错"+ex);	
		}
    }
    
    /****
     * 商品批量操作时给收藏的商品打标记
     * @param memberId
     * @param itemId
     * @param status
     * @return
     * @throws ManagerException
     */
    public Integer updateBathFavorite(Long memberId,List<Long> itemId,Long status)throws ManagerException{
    	Integer count=0;
    	try{
    		for(int i=0;i<itemId.size();i++){
    			FavoriteItemDO favoriteItemDO=new FavoriteItemDO();
            	favoriteItemDO.setMemberId(memberId);
            	favoriteItemDO.setItemId(itemId.get(i));
            	favoriteItemDO.setIsDelete(status);
    		    favoriteItemDAO.updateFavorite(favoriteItemDO);
    		    count++;
    		}
		}catch(DaoException ex){
		   throw new ManagerException("删除商品时给收藏的商品打标记出错"+ex);	
		}
		return count;
    }
    
    /****
     * 根据用户id、商品id查询收藏商品
     * @param userId 用户id
     * @param ItemId 商品id
     * @return
     */
    public FavoriteItemDO getFavoriteItemByUserId(Long userId,Long itemId) throws ManagerException{
    	try{
    		FavoriteItemDO favoriteItemDO=new FavoriteItemDO();
        	favoriteItemDO.setMemberId(userId);
        	favoriteItemDO.setItemId(itemId);
		    return getFavoriteItem(favoriteItemDO);
		}catch(ManagerException ex){
		   throw new ManagerException("根据用户id、商品id查询收藏商品出错"+ex);	
		}
    }
   
    /****
	 * 查找收藏商品信息
	 * @param favoriteShopDO
	 * @return
	 * @throws DaoException
	 */
	public FavoriteItemDO getFavoriteItem(FavoriteItemDO favoriteItemDO)throws ManagerException{
		try{
			return favoriteItemDAO.getFavoriteItem(favoriteItemDO);	
		}catch(DaoException e){
			throw new ManagerException("查找收藏商品信息出错"+e);		
		}
	}
	
	/*****
	 * 查找收藏商品分类id集合
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	public List<FavoriteItemDO> getFavoriteItemCategorys(Long memberId)throws ManagerException{
		try{
			return favoriteItemDAO.getFavoriteItemCategorys(memberId);	
		}catch(DaoException e){
			throw new ManagerException("查找收藏商品分类id集合出错"+e);		
		}
	}
	public FavoriteItemDAO getFavoriteItemDAO() {
		return favoriteItemDAO;
	}

	public void setFavoriteItemDAO(FavoriteItemDAO favoriteItemDAO) {
		this.favoriteItemDAO = favoriteItemDAO;
	}
}
