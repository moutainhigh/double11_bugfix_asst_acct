package com.yuwang.pinju.core.favorite.manager;


import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.favorite.FavoriteItemDO;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public interface FavoriteItemManager {
	/****
	 * 保存收藏商品信息
	 * @param favoriteItem
	 * @return
	 * @throws ManagerException
	 */
	public Long saveFavoriteItem(FavoriteItemDO favoriteItem)throws ManagerException;
	
	/****
	 * 删除收藏商品信息
	 * @param favoriteItemList
	 *  id   编号id
	 *  ItemId  商品id
	 * @return
	 * @throws ManagerException
	 */
	public boolean deleteFavoriteItem(List<Long> idList,Long memberId)throws ManagerException;
	
	
	/***
	 * 查找收藏商品信息并按字段排序
	 * @param favoriteItem
	 * @return
	 * @throws ManagerException
	 */
	public List<FavoriteItemDO> getFavoriteList(FavoriteItemDO favoriteItem)throws ManagerException;
	
	
	/******
	 * 收藏商品总条数
	 * @return
	 */
    public Integer getFavoriteListCount(FavoriteItemDO favoriteItem)throws ManagerException;
    
    /****
     * 根据日期查询当天收藏商品总数
     * @param date
     * @return
     * @throws ManagerException
     */
    public Integer getFavoriteCountByDate(Date date)throws ManagerException;
    
    /****
     * 商品操作时给收藏的商品打标记
     * @param memberId
     * @param itemId
     * @param  status
     * @return
     * @throws ManagerException
     */
    public boolean updateFavorite(Long memberId,Long itemId,Long status)throws ManagerException;
    
    
    /****
     * 商品批量操作时给收藏的商品打标记
     * @param memberId
     * @param itemId
     * @param status
     * @return
     * @throws ManagerException
     */
    public Integer updateBathFavorite(Long memberId,List<Long>itemId,Long status)throws ManagerException;
    
    /****
     * 根据用户id、商品id查询收藏商品
     * @param userId 用户id
     * @param ItemId 商品id
     * @return
     */
    public FavoriteItemDO getFavoriteItemByUserId(Long userId,Long itemId) throws ManagerException;
    
    /****
	 * 查找收藏商品信息
	 * @param favoriteShopDO
	 * @return
	 * @throws DaoException
	 */
	public FavoriteItemDO getFavoriteItem(FavoriteItemDO favoriteItemDO)throws ManagerException;
	
	/*****
	 * 查找收藏商品分类id集合
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	public List<FavoriteItemDO> getFavoriteItemCategorys(Long memberId)throws ManagerException;
}
