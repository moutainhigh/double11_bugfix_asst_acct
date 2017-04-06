package com.yuwang.pinju.core.favorite.ao;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.favorite.FavoriteItemDO;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public interface FavoriteItemAO {
	/****
	 * 保存收藏商品信息
	 * @param favoriteItem
	 * @return
	 */
	public Long saveFavoriteItem(FavoriteItemDO favoriteItem);
	
	/****
	 * 删除收藏商品信息
	 * @param favoriteItemList
	 *  id   编号id
	 *  ItemId  商品id
	 * @return
	 */
	public boolean deleteFavoriteItem(List<Long> idList,Long memberId);
	
	
	/***
	 * 查找收藏商品信息并按字段排序
	 * @param favoriteItem
	 * @return
	 */
	public List<FavoriteItemDO> getFavoriteList(FavoriteItemDO favoriteItem);
	
	
	/******
	 * 收藏商品总条数
	 * @return
	 */
    public Integer getFavoriteListCount(FavoriteItemDO favoriteItem);
    
    /****
     * 根据日期查询当天收藏商品总数
     * @param date
     * @return
     */
    public Integer getFavoriteCountByDate(String date,Long userId);
    
    /****
     * 根据用户id、商品id查询收藏商品
     * @param userId 用户id
     * @param ItemId 商品id
     * @return
     */
    public FavoriteItemDO getFavoriteItemByUserId(Long userId,Long ItemId);
    
	/*****
	 * 查找收藏商品分类id集合
	 * @param memberId
	 * @return
	 */
	public List<FavoriteItemDO> getFavoriteItemCategorys(Long memberId);
}
