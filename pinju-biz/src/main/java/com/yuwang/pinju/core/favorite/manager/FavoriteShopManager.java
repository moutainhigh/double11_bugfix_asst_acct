package com.yuwang.pinju.core.favorite.manager;


import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.favorite.FavoriteShopDO;

public interface FavoriteShopManager {
	/****
	 * 保存收藏店铺信息
	 * @param favoriteShop
	 * @return
	 */
	public Long saveFavoriteShop(FavoriteShopDO favoriteShop)throws ManagerException;
	
	/****
	 * 删除收藏店铺信息
	 * @param id
	 * @param shopId
	 * @return
	 */
	public boolean deleteFavoriteShop(List<Long> idList,Long memberId)throws ManagerException;
	
	 /****
	  * 查找收藏店铺信息并按字段排序
	  * @param favoriteShop
	  * @return
	  * @throws ManagerException
	  */
	public List<FavoriteShopDO> getFavoriteList(FavoriteShopDO favoriteShop)throws ManagerException;
	
	/****
	 * 收藏店铺总条数
	 * @param favoriteShop
	 * @return
	 * @throws ManagerException
	 */
    public Integer getFavoriteListCount(FavoriteShopDO favoriteShop)throws ManagerException;
    
    /****
     * 根据日期查询当天收藏店铺总数
     * @param date
     * @param userId
     * @throws ManagerException
     */
    public Integer getFavoriteCountByDate(String date,Long userId)throws ManagerException;
    
    /****
     * 根据会员id、店铺id查找收藏店铺信息
     * @param memberId
     * @param shopId
     * @return
     * @throws ManagerException
     */
	public FavoriteShopDO getFavoriteShopByuserId(Long memberId,Long shopId) throws ManagerException;
	
	/****
	 * 查找收藏店铺信息
	 * @param favoriteShop
	 * @return
	 * @throws ManagerException
	 */
	public FavoriteShopDO getFavoriteShop(FavoriteShopDO favoriteShop) throws ManagerException;
	
	/*****
	 * 查找收藏店铺分类id集合
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	public List<FavoriteShopDO> getFavoriteShopCategorys(Long memberId)throws ManagerException;
}
