package com.yuwang.pinju.core.favorite.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.favorite.dao.FavoriteItemDAO;
import com.yuwang.pinju.domain.favorite.FavoriteItemDO;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public class FavoriteItemDAOImpl  extends BaseDAO implements FavoriteItemDAO{
	/****
	 * 保存收藏商品信息
	 * @param favoriteShop
	 * @return
	 * @throws DaoException
	 */
	public Long saveFavoriteItem(FavoriteItemDO favoriteItem)throws DaoException{
		return (Long) super.executeInsert("favorite_item.insertFavoriteItem", favoriteItem);
	}
	
	/*****
	 * 删除收藏商品信息
	 * @param favoriteShopList
	 * id 编号
	 * userId 卖家编号
	 * @return
	 * @throws DaoException
	 */
	public void deleteFavoriteItem(List<Long> idList,Long memberId)throws DaoException{
		HashMap<String,Object>  map=new HashMap<String,Object>();
		map.put("idList", idList);
		map.put("memberId", memberId);
		super.executeUpdate("favorite_item.deleteFavoriteItemByList", map);
	}
	
	
	/****
	 * 查找收藏商品信息并按字段排序
	 * @param favoriteShopDO
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<FavoriteItemDO> getFavoriteList(FavoriteItemDO favoriteItemDO)throws DaoException{
		return super.executeQueryForList("favorite_item.selectFavoriteItem", favoriteItemDO);
	}
	
	
	/*****
	 *  搜索商品总条数
	 * @param favoriteShopDO
	 * @return
	 * @throws DaoException
	 */
    public Integer getFavoriteListCount(FavoriteItemDO favoriteItemDO)throws DaoException{
    	return (Integer) super.executeQueryForObject("favorite_item.queryCount", favoriteItemDO);
    }
    
    /****
     * 删除商品时给收藏的商品打标记
     * @param memberId
     * @param itemId
     * @return
     * @throws DaoException
     */
    public void updateFavorite(FavoriteItemDO favoriteItemDO)throws DaoException{
    	 super.executeUpdate("favorite_item.updateFavoriteItem", favoriteItemDO);
    }
    
    
    /****
	 * 查找收藏商品信息
	 * @param favoriteShopDO
	 * @return
	 * @throws DaoException
	 */
	public FavoriteItemDO getFavoriteItem(FavoriteItemDO favoriteItemDO)throws DaoException{
		return (FavoriteItemDO) super.executeQueryForObject("favorite_item.queryFavoriteItem", favoriteItemDO);
	}
	
	/*****
	 * 查找收藏商品分类id集合
	 * @param memberId
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<FavoriteItemDO> getFavoriteItemCategorys(Long memberId)throws DaoException{
		return (List<FavoriteItemDO>) super.executeQueryForList("favorite_item.queryFavoriteItemCategorys",memberId);
	}
}
