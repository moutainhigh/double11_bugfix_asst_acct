package com.yuwang.pinju.core.favorite.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.favorite.FavoriteItemDO;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public interface FavoriteItemDAO {
	/****
	 * 保存收藏商品信息
	 * @param favoriteShop
	 * @return
	 * @throws DaoException
	 */
	public Long saveFavoriteItem(FavoriteItemDO favoriteItem)throws DaoException;
	/*****
	 * 删除收藏商品信息
	 * @param favoriteShopList
	 * id 编号
	 * userId 卖家编号
	 * @return
	 * @throws DaoException
	 */
	public void deleteFavoriteItem(List<Long> idList,Long memberId)throws DaoException;
	
	
	/****
	 * 查找收藏商品信息并按字段排序
	 * @param favoriteShopDO
	 * @return
	 * @throws DaoException
	 */
	public List<FavoriteItemDO> getFavoriteList(FavoriteItemDO favoriteItemDO)throws DaoException;
	
	
	/*****
	 *  搜索商品总条数
	 * @param favoriteShopDO
	 * @return
	 * @throws DaoException
	 */
    public Integer getFavoriteListCount(FavoriteItemDO favoriteItemDO)throws DaoException;
    
    
    /****
     * 删除商品时给收藏的商品打标记
     * @param memberId
     * @param itemId
     * @return
     * @throws DaoException
     */
    public void updateFavorite(FavoriteItemDO favoriteItemDO)throws DaoException;
    
    /****
	 * 查找收藏商品信息
	 * @param favoriteShopDO
	 * @return
	 * @throws DaoException
	 */
	public FavoriteItemDO getFavoriteItem(FavoriteItemDO favoriteItemDO)throws DaoException;
	
	/*****
	 * 查找收藏商品分类id集合
	 * @param memberId
	 * @return
	 * @throws DaoException
	 */
	public List<FavoriteItemDO> getFavoriteItemCategorys(Long memberId)throws DaoException;
}
