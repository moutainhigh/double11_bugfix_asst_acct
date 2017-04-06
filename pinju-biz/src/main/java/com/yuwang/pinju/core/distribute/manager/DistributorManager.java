package com.yuwang.pinju.core.distribute.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemParamDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;
import com.yuwang.pinju.domain.distribute.DistributorItemQuery;
import com.yuwang.pinju.domain.distribute.ItemInfo;
import com.yuwang.pinju.domain.distribute.ShareDesignDO;
import com.yuwang.pinju.domain.distribute.ShowCaseQueryDO;
import com.yuwang.pinju.domain.shop.ShopQuery;

/**
 * 分销商Manager
 * 
 * @author caiwei
 * 
 */
public interface DistributorManager {

	/**
	 * 申请成功,保存一个分销商信息
	 * 
	 * @param memberId
	 *            用户ID
	 * @param nickName
	 *            用户昵称
	 */
	void saveDistributor(Long memberId, String nickName);
	
	/**
	 * 分享购页面设置
	 * @param shareDesign
	 * @throws ManagerException
	 */
	void setShareDesign(ShareDesignDO shareDesign) throws ManagerException;

	/**
	 * 根据MemberId查询分销商信息
	 * 
	 * @param memberId
	 *            用户ID
	 * @return
	 */
	DistributeDistributorDO findDistributorByMemberId(Long memberId);

	/**
	 * 跟据条件查找指定的分销商关系信息
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	DistribureChannelDO findDistribureChannelByCondition(DistribureChannelParamDO param);

	/**
	 * 查询供应商信息列表
	 * 
	 * @param param
	 *            查询参数[分页]
	 * @return
	 */
	List<DistributeSupplierDO> findAllDistributeSupplierInfo(ShopQuery param);

	/**
	 * 根据条件查询供应商列表
	 * 
	 * @param param
	 *            查询条件
	 * @return
	 */
	List<DistributeSupplierDO> findDistributeSuppliersByCondition(DistributeSupplierParamDO param);

	/**
	 * 查询供应商数量
	 * 
	 * @param param
	 *            参数
	 * @return
	 */
	Integer getDistributeSupplierCount(DistributeSupplierParamDO param);

	/**
	 * 申请供应商的分销商
	 * 
	 * @param distribureChannelDO
	 */
	Boolean saveDistribureChannel(DistribureChannelDO distribureChannelDO);

	/**
	 * 查询供应商商品数量
	 * 
	 * @param param
	 *            参数
	 * @return
	 */
	Integer getSupplierItemsCount(DistrbuteSupplierItemParamDO param);

	/**
	 * 查询供应商的商品信息
	 * 
	 * @param param
	 *            参数
	 */
	List<DistrbuteSupplierItemDO> findAllSupplierItems(DistrbuteSupplierItemParamDO param);

	/**
	 * 跟据条件查找指定的供应商
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	DistributeSupplierDO findDistributeSupplierByCondition(DistributeSupplierParamDO param);

	/**
	 * 根据分销商信息查找该分销商所有的供应商[有分页]
	 * 
	 * @param param
	 *            分销商信息
	 * @return
	 */
	List<DistributeSupplierDO> findDistributeSuppliersByChannel(DistribureChannelParamDO param);

	/**
	 * 根据分销商信息统记分销商所有的供应商数量
	 * 
	 * @param param
	 *            分销商信息
	 * @return
	 */
	Integer findDistributeSuppliersCountByChannel(DistribureChannelParamDO param);

	/**
	 * 更新分销商与供应商关系信息
	 * 
	 * @param paramList
	 * @return
	 */
	Boolean updateDistribureChannels(List<DistribureChannelDO> paramList);

	/**
	 * 分销商分销商品的分销与停止分销商品操作
	 * 
	 * @param paramList
	 * @return
	 */
	Boolean updateDistribureChannelAddGoods(List<DistribureChannelDO> paramList);

	/**
	 * 查找指定分销商的所有已经分销商品列表
	 * 
	 * @param param
	 * @author caiwei
	 */
	DistributorItemQuery findAllowedSoldItems(DistributorItemQuery param);

	/**
	 * 查找指定分销商的所有未分销商品列表
	 * 
	 * @param param
	 * @return
	 * @author caiwei
	 */
	DistributorItemQuery findAllowedUnsoldItems(DistributorItemQuery param);

	/**
	 * 查询分销商已分销的商品数量（支持分页）
	 * 
	 * @param query
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	Integer findAllowedSoldItemsCount(DistributorItemQuery param);

	/**
	 * 查询分销商已分销商品数量
	 * 
	 * @param param
	 * @return
	 * @author caiwei
	 */
	Integer findAllowedUnsoldItemsCount(DistributorItemQuery param);

	/**
	 * 分销商所有商品查询[格子铺]
	 * 
	 * @param param
	 * @return
	 */
	ShowCaseQueryDO findAllowedSoldItems(final ShowCaseQueryDO param);

	/**
	 * 查找指定用户的商品信息
	 * 
	 * @param memberId
	 *            用户ID(加密)
	 * @param itemId
	 *            商品ID
	 * @return
	 */
	ItemInfo findItemInfoByCondition(String memberId, Long itemId);

	/**
	 * 以逗号分隔字符串并返回结果集
	 * 
	 * @param idString
	 * @return
	 */
	List<Long> getItemIdList(String param);

	/**
	 * 商品停止分销
	 * 
	 * @param itemInfo
	 * 		参数[ItemId:必填]
	 * @return
	 */
	abstract Boolean discardDistributorGoods(List<ItemInfo> itemInfoList);

	/**
	 * 商品停止分销
	 * 
	 * @param itemInfoList
	 * 		参数[ItemId:必填]
	 * @return
	 */
	abstract Boolean discardDistributorGoods(ItemInfo itemInfo);

	/**
	 * 查询供应商信息列表
	 * 
	 * @param ids
	 * 			会员ID
	 * @return
	 */
	abstract List<DistributeSupplierDO> selectSupplierShopIdByIds(List<Long> memberIds);

	/**
	 * 根据分销商信息查找该分销商所有的供应商[无分页]
	 * 
	 * @param param
	 *            分销商信息
	 * @return
	 */
	abstract List<DistributeSupplierDO> findDistributeSuppliersByCondition(DistribureChannelParamDO param);

}
