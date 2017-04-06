package com.yuwang.pinju.core.distribute.manager.impl;

import static com.yuwang.pinju.core.constant.distribute.DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_SELLING;
import static com.yuwang.pinju.core.constant.distribute.DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_STOP_SELLING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.distribute.DistributeConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.distribute.dao.DistributeChannelDao;
import com.yuwang.pinju.core.distribute.dao.DistributeChannelItemDAO;
import com.yuwang.pinju.core.distribute.dao.DistributeSupplierDao;
import com.yuwang.pinju.core.distribute.dao.DistributeSupplierItemDAO;
import com.yuwang.pinju.core.distribute.dao.DistributorDAO;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.util.DES3Encrypt;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemParamDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemDO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;
import com.yuwang.pinju.domain.distribute.DistributorItemQuery;
import com.yuwang.pinju.domain.distribute.GridDO;
import com.yuwang.pinju.domain.distribute.ItemInfo;
import com.yuwang.pinju.domain.distribute.ShareDesignDO;
import com.yuwang.pinju.domain.distribute.ShowCaseQueryDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopQuery;

/**
 * @author caiwei
 * 
 */
public class DistributorManagerImpl extends BaseManager implements DistributorManager {

	@Autowired
	private DistributorDAO distributorDAO;

	@Autowired
	private DistributeSupplierDao distributeSupplierDAO;

	@Autowired
	private DistributeChannelDao distributeChannelDAO;

	@Autowired
	private DistributeSupplierItemDAO distributeSupplierItemDAO;

	@Autowired
	private ItemManager itemManager;
	
	@Autowired
	private DistributeChannelItemDAO distributeChannelItemDAO;
	
	/**
	 * 申请成功,保存一个分销商信息
	 * 
	 * @param param
	 *            分销商信息
	 */
	@Override
	public void saveDistributor(Long memberId, String nickName) {
		try {
			distributorDAO.saveDistributor(new DistributeDistributorDO(memberId, nickName));
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#saveDistributor] 保存分销商信息失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 跟据条件查找指定的分销商关系信息
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	@Override
	public DistribureChannelDO findDistribureChannelByCondition(DistribureChannelParamDO param) {
		try {
			return distributeChannelDAO.findDistributeChannelByCondition(param);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findDistribureChannelByCondition] 查询单一分销商关系信息失败", e);
			e.printStackTrace();
			return new DistribureChannelDO(-1L);
		}
	}

	/**
	 * 根据MemberId查询分销商信息
	 * 
	 * @param memberId
	 *            用户ID
	 * @return
	 */
	@Override
	public DistributeDistributorDO findDistributorByMemberId(Long memberId) {
		try {
			return distributorDAO.findDistributorByMemberId(memberId);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findDistributorDOByMemberId] 查询分销商信息失败", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询供应商信息列表
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	@Override
	public List<DistributeSupplierDO> findAllDistributeSupplierInfo(ShopQuery param) {
		List<Integer> ids = new ArrayList<Integer>();
		List<DistributeSupplierDO> distributeSupplierInfoList = null;
		Map<Integer, DistributeSupplierDO> distributeSupplierInfoMap = new HashMap<Integer, DistributeSupplierDO>();
		List<DistributeSupplierDO> resultList = new ArrayList<DistributeSupplierDO>();
		for (ShopBusinessInfoDO shopInfoAllDO : param.getResultList()) {
			ids.add(shopInfoAllDO.getShopId());
		}
		try {
			if (!ids.isEmpty()) {
				distributeSupplierInfoList = distributeSupplierDAO.selectDistributeSupplierByIds(ids);
			}
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findAllDistributeSupplierInfo] 查询供应商信息列表失败", e);
			e.printStackTrace();
		}
		if (distributeSupplierInfoList != null) {
			for (DistributeSupplierDO distributeSupplierDO : distributeSupplierInfoList) {
				distributeSupplierInfoMap.put(distributeSupplierDO.getShopId(), distributeSupplierDO);
			}
		}
		for (ShopBusinessInfoDO shopInfoAllDO : param.getResultList()) {
			DistributeSupplierDO distributeSupplierDO = distributeSupplierInfoMap.get(shopInfoAllDO.getShopId());
			if (distributeSupplierDO != null) {
				if (StringUtils.hasText(shopInfoAllDO.getShopLogo())) {
					shopInfoAllDO.setShopLogo(shopInfoAllDO.getShopLogo()+"_80x80."+StringUtils.getFilenameExtension(shopInfoAllDO.getShopLogo()));
				}
				distributeSupplierDO.setShopInfoAllDO(shopInfoAllDO);
				resultList.add(distributeSupplierDO);
			}
		}
		return resultList;
	}

	/**
	 * 查询供应商信息列表
	 * 
	 * @param ids
	 * @return
	 */
	@Override
	public List<DistributeSupplierDO> selectSupplierShopIdByIds(List<Long> memberIds) {
		try {
			return distributeSupplierDAO.selectSupplierShopIdByIds(memberIds);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#selectSupplierShopIdByIds] 查询供应商信息列表失败", e);
			e.printStackTrace();
		}
		return new ArrayList<DistributeSupplierDO>(0);
	}
	
	/**
	 * 查询供应商数量
	 * 
	 * @param param
	 *            参数
	 * @return
	 */
	@Override
	public Integer getDistributeSupplierCount(DistributeSupplierParamDO param) {
		try {
			if (param == null) {
				return distributeSupplierDAO.getCount(new DistributeSupplierParamDO());
			} else {
				return distributeSupplierDAO.getCount(param);
			}
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#getDistributeSupplierCount] 查询供应商总数失败", e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 申请供应商的分销商
	 * 
	 * @param distribureChannelDO
	 */
	@Override
	public Boolean saveDistribureChannel(DistribureChannelDO distribureChannelDO) {
		try {
			distributeChannelDAO.saveDistribureChannel(distribureChannelDO);
			return true;
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#saveDistribureChannel] 保存分销商信息失败", e);
			e.printStackTrace();
		}
		return false;
	}

	public Boolean updateDistribureChannels(List<DistribureChannelDO> paramList) {
		try {
			for (DistribureChannelDO distribureChannelDO : paramList) {
				distributeChannelDAO.updateDistribute(distribureChannelDO);
			}
			return true;
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#updateDistribureChannels] 更新分销商列表信息失败", e);
		}
		return false;
	}

	public Boolean updateDistribureChannelAddGoods(List<DistribureChannelDO> paramList) {
		try {
			for (DistribureChannelDO distribureChannelDO : paramList) {
				distributeChannelDAO.updateDistributeAddGoods(distribureChannelDO);
			}
			return true;
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#updateDistribureChannelAddGoods] 更新分销商商品信息失败", e);
		}
		return false;
	}

	/**
	 * 查询供应商商品数量
	 * 
	 * @param param
	 *            参数
	 * @return
	 */
	@Override
	public Integer getSupplierItemsCount(DistrbuteSupplierItemParamDO param) {
		try {
			if (param == null) {
				return distributeSupplierItemDAO.getCount(new DistrbuteSupplierItemParamDO());
			} else {
				return distributeSupplierItemDAO.getCount(param);
			}
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#getSupplierItemsCount] 查询供应商分销商品总数失败", e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 查询供应商的商品信息
	 * 
	 * @param param
	 *            参数
	 */
	@Override
	public List<DistrbuteSupplierItemDO> findAllSupplierItems(DistrbuteSupplierItemParamDO param) {
		try {
			param.setStatus(new Short("0"));
			return distributeSupplierItemDAO.selectAllDistrbuteSupplierItem(param);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findAllSupplierItems] 查询供应商商品信息失败", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跟据条件查找指定的供应商
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 */
	@Override
	public DistributeSupplierDO findDistributeSupplierByCondition(DistributeSupplierParamDO param) {
		DistributeSupplierDO result = null;
		try {
			result = distributeSupplierDAO.getDistributeSupplier(param);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findDistributeSupplierByCondition] 查询单一供应商信息失败", e);
			e.printStackTrace();
		}
		if (result == null) {
			return new DistributeSupplierDO();
		} else {
			return result;
		}
	}

	/**
	 * 根据条件查询供应商列表
	 * 
	 * @param param
	 *            查询条件
	 * @return
	 */
	@Override
	public List<DistributeSupplierDO> findDistributeSuppliersByCondition(DistributeSupplierParamDO param) {
		try {
			return distributeSupplierDAO.findAllDistributeSupplierByCondition(param);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findDistributeSuppliersByCondition] 查询单一供应商失败", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据分销商信息查找该分销商所有的供应商[有分页]
	 * 
	 * @param param
	 *            分销商信息
	 * @return
	 */
	public List<DistributeSupplierDO> findDistributeSuppliersByChannel(DistribureChannelParamDO param) {
		try {
			List<DistributeSupplierDO> result = distributeChannelDAO.findAllDistribureSupplierByCondition(param);
			for (DistributeSupplierDO distributeSupplierDO : result) {
				if (distributeSupplierDO.getAgreeDate() != null && distributeSupplierDO.getCooperateMonth() != null
						&& !distributeSupplierDO.getCooperateMonth().equals(new Short("0"))) {
					distributeSupplierDO.setEndDate(DateUtils.addMonths(distributeSupplierDO.getAgreeDate(),
							distributeSupplierDO.getCooperateMonth()));
				}
			}
			return result;
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findDistributeSuppliersByCondition] 查询分销商的所有供应商信息失败", e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据分销商信息查找该分销商所有的供应商[无分页]
	 * 
	 * @param param
	 *            分销商信息
	 * @return
	 */
	@Override
	public List<DistributeSupplierDO> findDistributeSuppliersByCondition(DistribureChannelParamDO param) {
		try {
			List<DistributeSupplierDO> result = distributeChannelDAO.findDistribureSupplierByCondition(param);
			for (DistributeSupplierDO distributeSupplierDO : result) {
				if (distributeSupplierDO.getAgreeDate() != null && distributeSupplierDO.getCooperateMonth() != null
						&& !distributeSupplierDO.getCooperateMonth().equals(new Short("0"))) {
					distributeSupplierDO.setEndDate(DateUtils.addMonths(distributeSupplierDO.getAgreeDate(),
							distributeSupplierDO.getCooperateMonth()));
				}
			}
			return result;
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findDistributeSuppliersByCondition] 查询分销商的所有供应商信息失败", e);
			e.printStackTrace();
		}
		return new ArrayList<DistributeSupplierDO>(0);
	}

	/**
	 * 根据分销商信息统记分销商所有的供应商数量
	 * 
	 * @param param
	 *            分销商信息
	 * @return
	 */
	public Integer findDistributeSuppliersCountByChannel(DistribureChannelParamDO param) {
		try {
			return distributeChannelDAO.getDistributeCount(param);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findDistributeSuppliersByCondition] 查询分销商供应商数量失败", e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 查找指定分销商的所有已经分销商品列表
	 * 
	 * @param param
	 * @author caiwei
	 */
	public DistributorItemQuery findAllowedSoldItems(DistributorItemQuery param) {
		try {
			// 将ID放入查询参数中
			param.setItemIdList(getItemIdByCondition(param));
			// 分页参数[记录总数]
			param.setItems(findAllowedSoldItemsCount(param));
			// 查询商品详细信息
			param.setDistrbuteSupplierItemDOList(distributeSupplierItemDAO.findDistributorSoldItem(param));
			// 已分销商品数量
			param.setSoldItemCount(param.getItems());
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findAllowedItems] 查询分销商已分销商品列表失败", e);
			e.printStackTrace();
		}
		return param;
	}

	/**
	 * 分销商所有商品查询[格子铺]
	 * 
	 * @param param
	 * @return
	 */
	public ShowCaseQueryDO findAllowedSoldItems(final ShowCaseQueryDO param) {
		ShowCaseQueryDO result = param;
		String memberId = null;
		try {
			if (result.getMemberId() != null) {
				memberId = DES3Encrypt.getInstance().encrypt(result.getMemberId().toString());
				result.setChannelIdSec(memberId);
				// 获取分销商已分销商品
				result = getItemIdByCondition(result);
				Map<Long, DistributeChannelItemDO> channelItemMap = getChannelItemMapFromChannelItemList(findDistributeChannelItemDOList(param));
				List<GridDO> gridList = new ArrayList<GridDO>();
				//如果没有分销商品则格子铺商品没有任何内容
				if (!result.getIds().isEmpty()) {
					List<ItemDO> itemList = itemManager.queryItemListEx(new ItemQueryEx(result.getIds(), result.getItemsPerPage()));
					for (ItemDO itemDO : itemList) {
						GridDO gridDO = new GridDO();
						BeanUtils.copyProperties(itemDO, gridDO);
						DistributeChannelItemDO distributeChannelItemDO = channelItemMap.get(itemDO.getId());
						if (distributeChannelItemDO != null) {
							gridDO.setPicUrl(StringUtils.hasText(distributeChannelItemDO.getImageUrl())?distributeChannelItemDO.getImageUrl():gridDO.getPicUrl());
							gridDO.setAgreeCount(distributeChannelItemDO.getAgreeCount() != null?distributeChannelItemDO.getAgreeCount():0);
							gridDO.setOpposeCount(distributeChannelItemDO.getOpposeCount() != null?distributeChannelItemDO.getOpposeCount():0);
							gridDO.setRecommendReason(StringUtils.hasText(distributeChannelItemDO.getRecommendReason())?distributeChannelItemDO.getRecommendReason():"");
						}else {
							gridDO.setPicUrl(gridDO.getPicUrl());
							gridDO.setAgreeCount(0);
							gridDO.setOpposeCount(0);
							gridDO.setRecommendReason("");
						}
						if (result.getMemberId() != null) {
							gridDO.setUrl(PinjuConstant.PINJU_SERVER + "/detail/" + itemDO.getId() + ".htm?channelId=" + memberId);
						}
						gridList.add(gridDO);
					}
				}
				result.setGridList(gridList);
			}
		} catch (Exception e) {
			log.error("Event=[DistributorManager#findAllowedSoldItems] 查询格子铺商品列表失败", e);
		}
		return result;
	}

	/**
	 * 查找指定分销商的所有未分销商品列表
	 * 
	 * @param param
	 * @return
	 * @author caiwei
	 */
	public DistributorItemQuery findAllowedUnsoldItems(DistributorItemQuery param) {
		try {
			// 将ID放入查询参数中
			param.setItemIdList(getItemIdByCondition(param));
			// 分页参数[记录总数]
			param.setItems(findAllowedUnsoldItemsCount(param));
			// 查询商品详细信息
			param.setDistrbuteSupplierItemDOList(distributeSupplierItemDAO.findDistributorUnsoldItem(param));
			// 已分销商品数量
			param.setSoldItemCount(findAllowedSoldItemsCount(param));
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findAllowedUnsoldItems] 查询分销商未分销商品列表失败", e);
			e.printStackTrace();
		}
		return param;
	}

	/**
	 * 获取分销商已经分销的商品ID(分销页面展示)
	 * 
	 * @param param
	 * 	     参数:分销商ID
	 * @return
	 * @author caiwei
	 */
	List<Long> getItemIdByCondition(DistributorItemQuery param){
	    List<Long> ids = new ArrayList<Long>();
	    try {
			// 获取分销商信息
			List<DistribureChannelDO> distribureChannelDOList = distributeChannelDAO.selectAllowedDistributeChannelGoodList(param);
			// 判断分销商是否在合作期限内以及是否通过审核
			if (!CollectionUtils.isEmpty(distribureChannelDOList)) {
				for (DistribureChannelDO distribureChannelDO : distribureChannelDOList) {
					// 查询分销商分销的商品ID
					ids.addAll(getItemIdListByChannelId(distribureChannelDO.getDistributorId()));
				}
			}else {
				ids.add(-1L);
			}
	    } catch (Exception e) {
			log.error("Event=[DistributorManager#getItemIdByCondition] 获取分销商已经分销的商品ID(分销页面展示)失败", e);
			e.printStackTrace();
	    }
	    return ids;
	}
	
	/**
	 * 获取分销商社区商品首页信息(社区分享购页面展示)
	 * 
	 * @param param
	 * 	     参数:会员编号
	 * @return
	 * @author caiwei
	 */
	private ShowCaseQueryDO getItemIdByCondition(ShowCaseQueryDO param){
	    try {
			// 获取分销商信息
			List<DistribureChannelDO> distribureChannelDOList = distributeChannelDAO.findShowCaseByCondition(param);
			// 判断分销商是否在合作期限内以及是否通过审核
			if (!CollectionUtils.isEmpty(distribureChannelDOList) && distribureChannelDOList.size() == 1) {
				DistribureChannelDO distribureChannelDO = distribureChannelDOList.get(0);
				//读出配置的config信息[店标、横幅、宣传图、个性文案]
				param.setChannelId(distribureChannelDO.getDistributorId());
				param.setShopIndex(StringUtils.hasText(distribureChannelDO.getConfig("shopIndex"))?distribureChannelDO.getConfig("shopIndex"):"");
				param.setAdImg(StringUtils.hasText(distribureChannelDO.getConfig("adImg"))?distribureChannelDO.getConfig("adImg"):"");
				param.setBannerImg(StringUtils.hasText(distribureChannelDO.getConfig("bannerImg"))?distribureChannelDO.getConfig("bannerImg"):"");
				param.setDescript(StringUtils.hasText(distribureChannelDO.getConfig("descript"))?distribureChannelDO.getConfig("descript"):"");
			}
	    } catch (DaoException e) {
			log.error("Event=[DistributorManager#getItemIdByCondition] 获取分销商社区商品首页信息(社区分享购页面展示)失败", e);
			e.printStackTrace();
	    }
	    return param;
	}
	
	/**
	 * 分销商已分销商品的详细信息(社区分享购页面展示)
	 * 
	 * @param param
	 * 	     参数:会员编号
	 * @return
	 * @author caiwei
	 */
	private List<DistributeChannelItemDO> findDistributeChannelItemDOList(ShowCaseQueryDO param){
		List<Long> ids = new ArrayList<Long>();
		//取出分销商已分销商品总数
		List<DistributeChannelItemDO> result = new ArrayList<DistributeChannelItemDO>();
		try {
			Integer page = param.getPage();
			Integer perPage = param.getItemsPerPage();
			param.setItems(distributeChannelItemDAO.findShowCaseItemCountByCondition(param));
			param.setItemsPerPage(perPage);
			param.setPage(page);
			
			result.addAll(distributeChannelItemDAO.findShowCaseItemByCondition(param));
			//取出分销商已分销商品ID
			for (DistributeChannelItemDO distributeChannelItemDO : result) {
				ids.add(distributeChannelItemDO.getItemId());
			}
			param.setIds(ids);
		} catch (Exception e) {
			log.error("Event=[DistributorManager#findDistributeChannelItemDOList] 获取分销商已分销商品的详细信息(社区分享购页面展示)失败", e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询分销商分销的商品ID
	 * 
	 * @param channelId
	 * 	    分销商ID
	 * @return
	 * @throws DaoException 
	 */
	private List<Long> getItemIdListByChannelId(Long channelId) throws DaoException{
		List<Long> ids = distributeChannelItemDAO.findItemIdsByCondition(new DistributeChannelItemParamDO(channelId, DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_SELLING));
		if (CollectionUtils.isEmpty(ids)) {
			ids.add(-1L);
		}
	    return ids;
	}
	
	/**
	 * 查询分销商已分销商品数量
	 * 
	 * @param param
	 * @return
	 * @author caiwei
	 */
	public Integer findAllowedSoldItemsCount(DistributorItemQuery param) {
		try {
			return distributeSupplierItemDAO.findDistributorSoldItemCount(param);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findAllowedSoldItemsCount] 查询分销商已分销商品数量失败", e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 查询分销商未分销商品数量
	 * 
	 * @param param
	 * @return
	 * @author caiwei
	 */
	public Integer findAllowedUnsoldItemsCount(DistributorItemQuery param) {
		try {
			return distributeSupplierItemDAO.findDistributorUnsoldItemCount(param);
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#findAllowedSoldItemsCount] 查询分销商未分销商品数量失败", e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 以逗号分隔字符串并返回结果集
	 * 
	 * @param idString
	 * @return
	 */
	public List<Long> getItemIdList(String param) {
		List<Long> result = new ArrayList<Long>();
		// 存在已分销商品的ID
		if (StringUtils.hasText(param)) {
			String[] strs = StringUtils.trimArrayElements(StringUtils.delimitedListToStringArray(param, ","));
			for (String string : strs) {
				if (StringUtils.hasText(string)) {
					try {
						result.add(new Long(string));
					} catch (NumberFormatException e) {
						log.error("Event=[DistributorManager#getIds] 非法的商品ID值", e);
						e.printStackTrace();
					}
				}
			}
		}
		if (result.isEmpty()) {
			// 不存在分销商品的ID则写入一个不可能的值传入SQL进行匹配
			result.add(-1L);
		}
		return result;
	}

	/**
	 *  将分销商品list转换为分销商品map
	 *  
	 * @param param
	 * 			分销商品list
	 * @return
	 * 		Map<ItemId, DistributeChannelItemDO>
	 */
	private Map<Long, DistributeChannelItemDO> getChannelItemMapFromChannelItemList(List<DistributeChannelItemDO> param){
		Map<Long, DistributeChannelItemDO> result = new HashMap<Long, DistributeChannelItemDO>();
		for (DistributeChannelItemDO distributeChannelItemDO : param) {
			result.put(distributeChannelItemDO.getItemId(), distributeChannelItemDO);
		}
		return result;
	}
	
	/**
	 * 获取页面上显示的供应商详细信息
	 * 
	 * @param param
	 * @param channelMap
	 */
	public List<DistribureChannelDO> getDistribureChannelDOList(DistributorItemQuery param,
			Map<Integer, DistribureChannelDO> channelMap) {
		List<DistribureChannelDO> result = new ArrayList<DistribureChannelDO>();
		for (DistrbuteSupplierItemDO distrbuteSupplierItemDO : param.getDistrbuteSupplierItemDOList()) {
			DistribureChannelDO channelDO = channelMap.get(distrbuteSupplierItemDO.getSupplierId());
			if (!result.contains(channelDO)) {
				result.add(channelDO);
			}
		}
		return result;
	}
	
	/**
	 * 查找指定用户的商品信息
	 * 
	 * @param memberId
	 *            用户ID
	 * @param itemId
	 *            商品ID
	 * @return
	 */
	public ItemInfo findItemInfoByCondition(String memberId, Long itemId) {
	    //加密会员编号不能为零或为空
	    if (StringUtils.hasText(memberId) && !memberId.equals("0")) {
	    	//解密会员编号
		Long memberIdL = Long.valueOf(DES3Encrypt.getInstance().decrypt(memberId));
		//设置返回DO的会员编号和商品编号
		ItemInfo result = new ItemInfo(memberIdL, itemId);
		try {
		    //查询分销商商品信息
		    List<DistrbuteSupplierItemDO> distrbuteSupplierItemDOs = distributeSupplierItemDAO.selectDistributorSoldItemByCondition(new DistributorItemQuery(memberIdL, itemId));
		    //循环分销商所有分销商品
		    for (DistrbuteSupplierItemDO distrbuteSupplierItemDO : distrbuteSupplierItemDOs) {
				//判断分销商是否分销了指定商品
				if (distrbuteSupplierItemDO.getItemId() != null) {
				    //设置返回信息
				    result.setStatus(true);
				    //设置返点比率
				    result.setReward(distrbuteSupplierItemDO.getReward().longValue());
				    //设置分销商ID
				    result.setChannelId(distrbuteSupplierItemDO.getChannelId());
				    //设置分销商昵称
				    result.setNickName(distrbuteSupplierItemDO.getNickName());
				}
		    }
		} catch (Exception e) {
			log.error("Event=[DistributorManager#findItemInfoByCondition] 查询用户商品失败", e);
		}
		return result;
	    }
	    return new ItemInfo();
	}
	
	/**
	 * 商品停止分销
	 * 
	 * @param itemInfo
	 * 		参数[ItemId:必填、MemberId&ShopId:必填(二选一)]
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Boolean discardDistributorGoods(ItemInfo itemInfo){
	    ItemInfo[] itemInfoArray = {itemInfo};
	    return discardDistributorGoods(CollectionUtils.arrayToList(itemInfoArray));
	}
	
	/**
	 * 商品停止分销
	 * 
	 * @param itemInfoList
	 * 		参数[ItemId:必填]
	 * @return
	 */
	@Override
	public Boolean discardDistributorGoods(List<ItemInfo> itemInfoList){
	    final List<DistributeChannelItemDO> distribureChannelParamList = new ArrayList<DistributeChannelItemDO>();
	    final List<DistrbuteSupplierItemDO> distrbuteSupplierItemParamList = new ArrayList<DistrbuteSupplierItemDO>();
	    if (!CollectionUtils.isEmpty(itemInfoList)) {
			for (ItemInfo itemInfo : itemInfoList) {
			    if (itemInfo.getItemId() != null) {
					try {
					    DistributeSupplierDO distributeSupplierDO = distributeSupplierDAO.selectDistributeSupplierByItemId(itemInfo.getItemId());
					    if (distributeSupplierDO != null && distributeSupplierDO.getId() != null) {
							// 停止分销商品改为停止分销状态
							distribureChannelParamList.add(populateChannelItemDO(itemInfo));
							distrbuteSupplierItemParamList.add(new DistrbuteSupplierItemDO(distributeSupplierDO.getId(), itemInfo.getItemId()));
					    }
					} catch (Exception e) {
					    log.error("Event=[DistributorManager#discardDistributorGoods] 分享购商品查询失败", e);
					}
			    }
			}
	    }
	    return doTransaction(distribureChannelParamList, distrbuteSupplierItemParamList);
	}

	/**
	 * 组装分销商商品参数
	 * 
	 * @param itemInfo
	 * @return
	 */
	private DistributeChannelItemDO populateChannelItemDO(ItemInfo itemInfo) {
		DistributeChannelItemDO result = new DistributeChannelItemDO();
		result.setItemId(itemInfo.getItemId());
		result.setOriginalStatus(DISTRIBUTE_CHANNEL_ITEM_SELLING);
		result.setStatus(DISTRIBUTE_CHANNEL_ITEM_STOP_SELLING);
		return result;
	}
	
	/**
	 * 执行分销商品下架操作[事务]
	 * 
	 * @param distribureChannelParamList
	 * @param distrbuteSupplierItemParamList
	 * @return
	 */
	private Boolean doTransaction(final List<DistributeChannelItemDO> distribureChannelParamList, final List<DistrbuteSupplierItemDO> distrbuteSupplierItemParamList) {
		try {
		    return super.executeMysqlTransaction(Boolean.class, new TransactionCallback<Boolean>() {
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
				    try {
				    	distributeChannelItemDAO.update(distribureChannelParamList);
						distributeSupplierItemDAO.discardDistributorItemBySupplierIdAndItemId(distrbuteSupplierItemParamList);
				    } catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("error:[DistributorManager#discardDistributorGoods] Transaction roll back!");
				    }
				    return true;
				}
		    });
		} catch (Exception e) {
		    log.error("Event=[DistributorManager#discardDistributorGoods] 商品下架后停止分销失败", e);
		}
	    return false;
	}
	
	/**
	 * 分享购页面设置
	 * @param shareDesign
	 * @throws ManagerException
	 */
	public void setShareDesign(ShareDesignDO shareDesign)
			throws ManagerException {
		try {
			distributorDAO.setShareDesign(shareDesign.getChannelId(),
					shareDesign.toString());
		} catch (DaoException e) {
			log.error("Event=[DistributorManager#setShareDesign] 保存分享购页面信息失败",
					e);
			throw new ManagerException(e);
		}
	}
}
