package com.yuwang.pinju.core.favorite.dao.impl;

import java.util.HashMap;
import java.util.List;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.favorite.dao.FavoriteShopDAO;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public class FavoriteShopDAOImpl extends BaseDAO implements FavoriteShopDAO{
	/****
	 * 保存收藏店铺信息
	 * @param favoriteShop
	 * @return
	 */
	public Long saveFavoriteShop(FavoriteShopDO favoriteShop)throws DaoException{
		return (Long) super.executeInsert("favorite_shop.insertFavoriteShop", favoriteShop);
	}
	
	/****
	 * 删除收藏店铺信息
	 * @param id
	 * @param shopId
	 * @return
	 */
	public void deleteFavoriteShop(List<Long> idList,Long memberId)throws DaoException{
			HashMap<Object,Object>  map=new HashMap<Object,Object>();
			map.put("idList", idList);
			map.put("memberId", memberId);
		    super.executeUpdate("favorite_shop.deleteFavoriteByList", map);
	}
	
	
	/***
	 * 查找收藏店铺信息并按字段排序
	 * @param orderby
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FavoriteShopDO> getFavoriteList(FavoriteShopDO favoriteShopDO)throws DaoException{
		return super.executeQueryForList("favorite_shop.selectFavoriteShop", favoriteShopDO);
	}
	
	
	/******
	 * 搜索店铺总条数
	 * @return
	 */
    public Integer getFavoriteListCount(FavoriteShopDO favoriteShopDO)throws DaoException{
    	return (Integer) super.executeQueryForObject("favorite_shop.queryCount",favoriteShopDO);
    }
    
    /****
	 * 查找收藏店铺信息
	 * @param favoriteShop
	 * @return
	 * @throws DaoException
	 */
	public FavoriteShopDO getFavoriteShop(FavoriteShopDO favoriteShop) throws DaoException{
		return (FavoriteShopDO) super.executeQueryForObject("favorite_shop.queryFavoriteShop",favoriteShop);
	}
	
	
	/*****
	 * 查找收藏店铺分类id集合
	 * @param memberId
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<FavoriteShopDO> getFavoriteShopCategorys(Long memberId)throws DaoException{
		return (List<FavoriteShopDO>) super.executeQueryForList("favorite_shop.queryFavoriteShopCategorys",memberId);
	}
	
	
}
