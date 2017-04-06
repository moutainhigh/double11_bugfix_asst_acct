package com.yuwang.pinju.core.favorite.ao;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public interface FavoriteShopAO {
	
	/****
	 * 保存收藏店铺信息
	 * @param favoriteShop
	 * @return
	 */
	public Long saveFavoriteShop(FavoriteShopDO favoriteShop);
	
	/****
	 * 删除收藏店铺信息
	 * @param favoriteShopList
	 * id  编号
	 * shopId   店铺编号
	 * @return
	 */
	public boolean deleteFavoriteShop(List<Long> idList,Long memberId);
	
	
	/***
	 * 查找收藏店铺信息并按字段排序
	 * @param favoriteShop
	 * @return
	 */
	public List<FavoriteShopDO> getFavoriteList(FavoriteShopDO favoriteShop);
	
	
	/******
	 * 收藏店铺总条数
	 * @return
	 */
    public Integer getFavoriteListCount(FavoriteShopDO favoriteShop);
    
    /****
     * 根据日期查询当天收藏店铺总数
     * @param date
     * @param userId
     * @return
     */
    public Integer getFavoriteCountByDate(String date,Long userId);
  
    /****
     * 根据会员id、店铺id查找收藏店铺信息
     * @param memberId
     * @param shopId
     * @return
     */
	public FavoriteShopDO getFavoriteShopByuserId(Long memberId,Long shopId);
	
	/***
	 * 查找收藏店铺信息
	 * @param favoriteShop
	 * @return
	 */
	public FavoriteShopDO getFavoriteShop(FavoriteShopDO favoriteShop);
	
	/*****
	 * 查找收藏店铺分类id集合
	 * @param memberId
	 * @return
	 */
	public List<FavoriteShopDO> getFavoriteShopCategorys(Long memberId);

}
