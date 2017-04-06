package com.yuwang.pinju.core.favorite.manager.impl;

import java.util.List;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.favorite.dao.FavoriteShopDAO;
import com.yuwang.pinju.core.favorite.manager.FavoriteShopManager;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public class FavoriteShopManagerImpl implements FavoriteShopManager {
	
	private FavoriteShopDAO favoriteShopDAO;
	
	/****
	 * 保存收藏店铺信息
	 * @param favoriteShop
	 * @return
	 */
	public Long saveFavoriteShop(FavoriteShopDO favoriteShop)throws ManagerException{
		try{
			return favoriteShopDAO.saveFavoriteShop(favoriteShop);
		}catch(DaoException ex){
		   throw new ManagerException("保存收藏店铺信息出错"+ex);	
		}
	}
	
	/****
	 * 删除收藏店铺信息
	 * @param favoriteShopList
	 * id   编号
	 * shopId  店铺编号
	 * @return
	 */
	public boolean deleteFavoriteShop(List<Long> idList,Long memberId)throws ManagerException{
		try{
		   favoriteShopDAO.deleteFavoriteShop(idList,memberId);
		   return true;
		}catch(DaoException ex){
		   throw new ManagerException("删除收藏店铺信息出错"+ex);	
		}
	}
	
	
	/****
	 * 查找收藏店铺信息并按字段排序
	 * @param favoriteShop
	 * @return
	 * @throws ManagerException
	 */
	public List<FavoriteShopDO> getFavoriteList(FavoriteShopDO favoriteShop)throws ManagerException{
		try{
			 return  favoriteShopDAO.getFavoriteList(favoriteShop);
		}catch(DaoException ex){
		   throw new ManagerException("查找收藏店铺信息并按字段排序出错"+ex);	
		}
	}
	
	
	/****
	 * 搜索店铺总条数
	 * @param favoriteShop
	 * @return
	 * @throws ManagerException
	 */
    public Integer getFavoriteListCount(FavoriteShopDO favoriteShop)throws ManagerException{
    	try{
        	return favoriteShopDAO.getFavoriteListCount(favoriteShop);
          }catch(DaoException ex){
        	  throw new ManagerException("搜索店铺总条数出错"+ex);
          }
    }
   
    /****
     * 根据日期查询当天收藏店铺总数
     * @param date
     * @param userId
     * @return
     * @throws ManagerException 
     */
    public Integer getFavoriteCountByDate(String date,Long userId) throws ManagerException{
      try{
    	FavoriteShopDO favoriteShopDO=new FavoriteShopDO();
    	favoriteShopDO.setFavoriteDateStr(date);
    	favoriteShopDO.setMemberId(userId);
    	return favoriteShopDAO.getFavoriteListCount(favoriteShopDO);
      }catch(DaoException ex){
    	  throw new ManagerException("根据日期查询当天收藏店铺总数出错"+ex);
      }
    }
    
    /****
     * 根据会员id、店铺id查找收藏店铺信息
     * @param memberId
     * @param shopId
     * @return
     * @throws ManagerException
     */
	public FavoriteShopDO getFavoriteShopByuserId(Long memberId,Long shopId) throws ManagerException{
		try{
	    	FavoriteShopDO favoriteShopDO=new FavoriteShopDO();
	    	favoriteShopDO.setMemberId(memberId);
	    	favoriteShopDO.setShopId(shopId);
	    	return getFavoriteShop(favoriteShopDO);
	      }catch(ManagerException ex){
	    	  throw new ManagerException("根据日期查询当天收藏店铺总数出错"+ex);
	      }
	}
	
	/****
	 * 查找收藏店铺信息
	 * @param favoriteShop
	 * @return
	 * @throws ManagerException
	 */
	public FavoriteShopDO getFavoriteShop(FavoriteShopDO favoriteShop) throws ManagerException{
		try{
	    	return favoriteShopDAO.getFavoriteShop(favoriteShop);
	      }catch(DaoException ex){
	    	  throw new ManagerException("查找收藏店铺信息出错"+ex);
	      }
	}
	
	/*****
	 * 查找收藏店铺分类id集合
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	public List<FavoriteShopDO> getFavoriteShopCategorys(Long memberId)throws ManagerException{
		try{
	    	return favoriteShopDAO.getFavoriteShopCategorys(memberId);
	      }catch(DaoException ex){
	    	  throw new ManagerException("查找收藏店铺分类id集合出错"+ex);
	      }
	}

	public FavoriteShopDAO getFavoriteShopDAO() {
		return favoriteShopDAO;
	}

	public void setFavoriteShopDAO(FavoriteShopDAO favoriteShopDAO) {
		this.favoriteShopDAO = favoriteShopDAO;
	}
}
