package com.yuwang.pinju.core.favorite.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public interface FavoriteShopDAO {

	/****
	 * 保存收藏店铺信息
	 * @param favoriteShop
	 * @return
	 */
	public Long saveFavoriteShop(FavoriteShopDO favoriteShop)throws DaoException;
	
	/****
	 * 删除收藏店铺信息
	 * @param id
	 * @param shopId
	 * @return
	 */
	public void deleteFavoriteShop(List<Long> idList,Long memberId)throws DaoException;
	
	
	/***
	 * 查找收藏店铺信息并按字段排序
	 * @param orderby
	 * @return
	 */
	public List<FavoriteShopDO> getFavoriteList(FavoriteShopDO favoriteShopDO)throws DaoException;
	
	
	/******
	 * 搜索店铺总条数
	 * @return
	 */
    public Integer getFavoriteListCount(FavoriteShopDO favoriteShopDO)throws DaoException;
	
	/****
	 * 查找收藏店铺信息
	 * @param favoriteShop
	 * @return
	 * @throws DaoException
	 */
	public FavoriteShopDO getFavoriteShop(FavoriteShopDO favoriteShop) throws DaoException;
	
	/*****
	 * 查找收藏店铺分类id集合
	 * @param memberId
	 * @return
	 * @throws DaoException
	 */
	public List<FavoriteShopDO>getFavoriteShopCategorys(Long memberId)throws DaoException;
    
}
