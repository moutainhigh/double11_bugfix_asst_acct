package com.yuwang.pinju.core.item.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.FeaturesUtil;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.core.httpclient.SolrService;
import com.yuwang.pinju.core.httpclient.UpdateSolrService;
import com.yuwang.pinju.core.item.dao.CustomProValueDAO;
import com.yuwang.pinju.core.item.dao.ItemDAO;
import com.yuwang.pinju.core.item.dao.ItemPicDAO;
import com.yuwang.pinju.core.item.dao.ItemSnapshotDAO;
import com.yuwang.pinju.core.item.dao.SkuDAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.item.manager.ItemSalesStatManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsCityIpManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.distribute.ItemInfo;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemAndOtherVO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemPicDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;
import com.yuwang.pinju.domain.item.ItemSnapshotDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.search.index.ItemIndexDO;

/**
 * 
 * @author
 * 
 */
public class ItemManagerImpl extends BaseManager implements ItemManager {

	private ItemDAO itemDAO;
	private ItemSnapshotDAO itemSnapshotDao;
	private SkuDAO skuDAO;
	private CustomProValueDAO customProValueDAO;
	private ItemPicDAO itemPicDAO;
	private CategoryCacheManager categoryCacheManager;
	private SkuManager skuManager;
//	private CategoryManager categoryManager;
	private UpdateSolrService updateSolrService;
	private SolrService solrService;
//	private ItemSalesStatAO itemSalesStatAO;
	private DistributorManager distributorManager;
	private ItemSalesStatManager itemSalesStatManager;
	private LogisticsCityIpManager logisticsCityIpManager;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#addShelfById(long)
	 */
	@Override
	public String addShelfById(long id, long type) throws ManagerException {
		ItemDO item =null;
		try {

			item = itemDAO.selectItemDObyId(id);
			List<ItemPicDO> itemPicList = new ArrayList<ItemPicDO>();
			itemPicList = itemPicDAO.getItemPicByItemId(id);
			if (itemPicList != null && itemPicList.size() > 0) {
				item.setItemPicList(itemPicList);
			}
			List<SkuDO> skuList = skuManager.getItemSkuByItemId(id);
			if (skuList != null && skuList.size() > 0) {
				item.setSkuList(skuList);
			}
			
			if (item == null) {
				return "商品不存在";
			}
			if (type != ItemConstant.STATUS_TYPE_0 && type != ItemConstant.STATUS_TYPE_1) {
				return "";
			}
			if (item.getCurStock() <= 0) {
				return "商品\"" + item.getTitle() + "\"上架失败，原因：库存不足！";
			}

			// 插入商品快照
			createItemSnapshot(item);

			item.setStatus(type);
			item.setStartTime(DateUtil.current());

			item.setGmtModified(DateUtil.current());

			itemDAO.updateItem(item);

			

		} catch (Exception e) {
			throw new ManagerException("Event=[ItemManager#addShelfById]根据ID上架商品", e);
		}
		if (item!=null) {
			try {
				//上架商品更新缓存
				categoryCacheManager.resetItemDOCache(item);
				//上架商品，同步搜索中该商品数据
				ItemIndexDO itemIndexDO = null;
				itemIndexDO = setItemIndexDO(item);
				updateSolrService.itemInput(itemIndexDO);
			} catch (Exception e) {
				//update by liuboen 2011-11-10 不能让搜索影响商品，建议：这块代码问题严重需要重构
				log.error("上架商品调用搜索/缓存失败", e);
			}
		}
		return "";
	}

	@Override
	public void createItemSku(List<SkuDO> skuList, long itemId, long sellId, boolean isVerify) throws ManagerException {

		if (isVerify) {
			// throw new ManagerException();
		}

		for (SkuDO skuDO : skuList) {
			skuDO.setItemId(itemId);
			skuDO.setSellerId(sellId);
			skuDO.setGmtCreate(DateUtil.current());
			skuDO.setGmtModified(DateUtil.current());
			//价格低于0.1或者库存为0的SKU不入库
			if(skuDO.getPrice()>9&&skuDO.getCurrentStock()>=0){
				createItemSku(skuDO);
			}
		}
	}

	@Override
	public void createItemCustomSku(List<CustomProValueDO> customSkuList, long itemId, long sellId, boolean isVerify) throws ManagerException {

		if (isVerify) {
			// throw new ManagerException();
		}

		for (CustomProValueDO customProValueDO : customSkuList) {
			customProValueDO.setItemId(itemId);
			customProValueDO.setMemberId(sellId);
			customProValueDO.setGmtCreate(DateUtil.current());
			customProValueDO.setGmtModified(DateUtil.current());
			createItemCustomSku(customProValueDO);
		}
	}
	
	@Override
	public long createItemCustomSku(CustomProValueDO customProValueDO) throws ManagerException {
		try {
			return customProValueDAO.insertItemCustomProValue(customProValueDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#createItemCustomSku]创建商品自定义 SKU", e);
		}
	}
	
	@Override
	public long createItemSku(SkuDO skuDO) throws ManagerException {
		try {
			return skuDAO.createItemSku(skuDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#createItemSku]创建商品 SKU", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#createItemSnapshot(com
	 * .yuwang.pinju.domain.item.ItemSnapshotDO)
	 */
	@Override
	public long createItemSnapshot(ItemDO itemDO) throws ManagerException {
		try {

			log.debug("插入商品快照");

			ItemSnapshotDO itemSnapshotDO = new ItemSnapshotDO();
			itemSnapshotDO.setItemId(itemDO.getId());
			itemSnapshotDO.setCatetoryId(itemDO.getCatetoryId());
			itemSnapshotDO.setSpuId(itemDO.getSpuId());
			itemSnapshotDO.setTitle(itemDO.getTitle());
			itemSnapshotDO.setStoreCategories(itemDO.getStoreCategories());
			itemSnapshotDO.setDescription(itemDO.getDescription());
			itemSnapshotDO.setPropertyValuePair(itemDO.getPropertyValuePair());
			itemSnapshotDO.setItemType(itemDO.getItemType().intValue());
			itemSnapshotDO.setSellerId(itemDO.getSellerId());
			itemSnapshotDO.setPicUrl(itemDO.getPicUrl());
			if (itemDO.getPicColor() != null) {
				itemSnapshotDO.setPicColor(itemDO.getPicColor().intValue());
			}
			itemSnapshotDO.setPrice(itemDO.getPrice());
			itemSnapshotDO.setProvinces(itemDO.getProvinces());
			itemSnapshotDO.setCity(itemDO.getCity());
			itemSnapshotDO.setDeliveryCosts(itemDO.getDeliveryCosts());

			itemSnapshotDO.setMailCosts(itemDO.getMailCosts());
			itemSnapshotDO.setEmsCosts(itemDO.getEmsCosts());

			if (itemDO.getFreeTemplateId() != null) {
				itemSnapshotDO.setFreeTemplateId(itemDO.getFreeTemplateId());
			}

			itemSnapshotDO.setStartTime(itemDO.getStartTime());
			itemSnapshotDO.setEndTime(itemDO.getEndTime());

			itemSnapshotDO.setItemDegree(itemDO.getItemDegree().intValue());
			if (itemDO.getRecommendedStatus() != null) {
				itemSnapshotDO.setRecommendedStatus(itemDO.getRecommendedStatus().intValue());
			}
			itemSnapshotDO.setCollectionNumber(itemDO.getCollectionNumber());
			itemSnapshotDO.setFeatures(itemDO.getFeatures());
			itemSnapshotDO.setExpiredDate(itemDO.getExpiredDate());
			itemSnapshotDO.setLastModified(itemDO.getLastModified());
			itemSnapshotDO.setGmtCreate(itemDO.getGmtCreate());
			itemSnapshotDO.setGmtModified(itemDO.getGmtModified());

			itemSnapshotDO.setCode(itemDO.getCode());
			itemSnapshotDO.setSellerNick(itemDO.getSellerNick());

			return itemSnapshotDao.insertItemItemSnapshot(itemSnapshotDO);

		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#createItemSnapshot]创建商品快照", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#deleteItemStoreCategories (java.lang.Long, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteItemStoreCategories(Long sellerId, List<String> ids) throws ManagerException {

		List<ItemDO> ls = getItemList(sellerId);
		final List<ItemDO> addList = new ArrayList<ItemDO>();
		if (ls != null && ls.size() > 0 && ids != null && ids.size() > 0) {
			for (ItemDO itemDO : ls) {
				String sc = itemDO.getStoreCategories();
				if (!EmptyUtil.isBlank(sc)) {
					boolean isUpdate = false;
					for (String id : ids) {
						if (sc.toLowerCase().indexOf(id.toLowerCase().trim()) != -1) {
							isUpdate = true;
							sc = sc.replaceAll(id, "");
						}
					}
					if (isUpdate) {
						String scs[] = StringUtil.split(sc, ",");
						StringBuffer s = new StringBuffer(" ");
						for (int i = 0; i < scs.length; i++) {
							String string = scs[i];
							if (!EmptyUtil.isBlank(string.trim())) {
								s.append(string);
								if (i < scs.length - 1)
									s.append(",");
							}
						}

//						if (!EmptyUtil.isBlank(s.toString())) {
							ItemDO i = new ItemDO();
							i.setId(itemDO.getId());
							i.setStoreCategories(s.toString());
							addList.add(i);
//						}
					}
				}
			}
		}

		int result = 0;

		if (addList.size() > 0) {
			int r = (Integer) getZizuTransactionTemplate().execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus status) {
					int result = 0;
					try {
						for (ItemDO itemDO : addList) {
							result += updateItem(itemDO);
						}
					} catch (Exception e) {
						status.setRollbackOnly();
						throw new RuntimeException(e);
					}
					return result;
				}
			});
			result = r;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#delItemByUser(long)
	 */
	@Override
	public int delItemById(long id, long type) throws ManagerException {
		try {
			ItemDO item = itemDAO.selectItemDObyId(id);
			if (type != ItemConstant.STATUS_TYPE_3 && type != ItemConstant.STATUS_TYPE_5) {
				return 0;
			}

			item.setId(id);
			item.setStatus(ItemConstant.STATUS_TYPE_3);
			item.setEndTime(DateUtil.current());
			item.setGmtModified(DateUtil.current());

			//删除商品更新缓存
			categoryCacheManager.resetItemDOCache(item);
			//删除商品，删除搜索中该商品数据
			updateSolrService.deleteItemById(item.getId());
			//调用停止分销接口
			ItemInfo itemInfo = new ItemInfo();
			itemInfo.setItemId(item.getId());
			distributorManager.discardDistributorGoods(itemInfo);
			return itemDAO.updateItem(item);

		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#delItemById]根据ID删除商品", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#delShelfById(long)
	 */
	@Override
	public int delShelfById(long id, long type) throws ManagerException {
		ItemDO item =null;
		int delShelfCount=-1;
		try {
			item = itemDAO.selectItemDObyId(id);
			List<ItemPicDO> itemPicList = new ArrayList<ItemPicDO>();
			itemPicList = itemPicDAO.getItemPicByItemId(id);
			if (itemPicList != null && itemPicList.size() > 0) {
				item.setItemPicList(itemPicList);
			}
			List<SkuDO> skuList = skuManager.getItemSkuByItemId(id);
			if (skuList != null && skuList.size() > 0) {
				item.setSkuList(skuList);
			}
			
			if (type != ItemConstant.STATUS_TYPE_2 && type != ItemConstant.STATUS_TYPE_4) {
				return 0;
			}

			item.setId(id);
			item.setStatus(type);
			item.setEndTime(DateUtil.current());
			item.setGmtModified(DateUtil.current());
			delShelfCount=itemDAO.updateItem(item);
			
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#delShelfById]根据ID下架商品", e);
		}
		
		try {
			//下架商品更新缓存
			categoryCacheManager.resetItemDOCache(item);
			//下架商品，删除搜索中该商品数据
			updateSolrService.deleteItemById(item.getId());
			//调用停止分销接口
			ItemInfo itemInfo = new ItemInfo();
			itemInfo.setItemId(item.getId());
			distributorManager.discardDistributorGoods(itemInfo);
		} catch (Exception e) {
			//update by liuboen 2011-11-10 不能让搜索影响商品，建议：这块代码问题严重需要重构
			log.error("下架商品调用搜索/停止分销/缓存接口失败", e);
		}
		
		return delShelfCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemDOById(long)
	 */
	@Override
	public ItemDO getItemDOById(long id) throws ManagerException {
		try {
			return itemDAO.selectItemDObyId(id);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemDOById]获取单条商品信息错误", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemList(java.util.Map, int)
	 */
	@Override
	public List<ItemDO> getItemList(ItemQuery itemQuery) throws ManagerException {
		try {
			return itemDAO.getItemList(itemQuery);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemList]获得商品列表错误", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemList(long, long)
	 */
	@Override
	public List<ItemDO> getItemList(long sellerId) throws ManagerException {
		try {
			return itemDAO.selectItemList(sellerId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemList]获得商品列表错误", e);
		}
	}

	@Override
	public List<ItemDO> getItemListByIds(List<Long> ids) throws ManagerException {
		if (ids.size() > 20) {
			// 不允许超过二十条
			throw new ManagerException("查询列表不允许超过二十条");
		}
		List<ItemDO> itemList = new ArrayList<ItemDO>();
		try {
			for (Long id : ids) {
				ItemDO itemDO = itemDAO.selectItemDObyId(id);
				if (itemDO != null) {
					itemList.add(itemDO);
				}
			}
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemListByIds],通过id list 获取商品列表失败", e);
		}

		return itemList;
	}
	@Override
	public List<ItemDO> getReadSimpleItemListByIds(String ids,long sellerId) throws ManagerException {
		List<Long> itemIDList = new ArrayList<Long>();
		try {
			String[] idsStrs = ids.split(",");
			//int i = 0;
			for (String string : idsStrs) {
				//i++;
				long id = Long.parseLong(string);
				itemIDList.add(id);
				/*if (i >= 4) {
					break;
				}*/
			}
			return itemDAO.selectReadSimpleItemDOListByIds(itemIDList,sellerId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemListByIds],通过string id list 获取商品列表失败,ids="+ids, e);
		}catch (NumberFormatException e) {
			throw new ManagerException("Event=[ItemManager#getItemListByIds],id类型转换错误,传入商品ID有误,ids="+ids,e);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemList(java.util.Map, int)
	 */
	@Override
	public int getItemListCount(ItemQuery itemQuery) throws ManagerException {
		try {
			return itemDAO.getItemListCount(itemQuery);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemListCount]获得商品数量错误", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemSnapshotIdByItemId (java.lang.Long)
	 */
	@Override
	public long getItemSnapshotIdByItemId(Long itemId) throws ManagerException {
		try {
			ItemSnapshotDO itemSnapshotDO = itemSnapshotDao.getItemSnapshotByItemId(itemId);
			if (itemSnapshotDO != null) {
				return itemSnapshotDO.getId();
			}
			return 0;
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemSnapshotIdByItemId]", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#itemReleased(com.yuwang .pinju.domain.item.ItemItemDO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public long itemReleased(final ItemDO itemDO, final List<SkuDO> skuList, final List<ItemPicDO> itemPicList, final List<CustomProValueDO> customSkuList) throws ManagerException {
		try {
			//存在SKU时，商品总数量=sku数量之和
			if (skuList != null && skuList.size() > 0) {
				long all = 0;
				for(int i=0;i<skuList.size();i++){
					SkuDO sdo = (SkuDO)skuList.get(i);
					all = all + sdo.getCurrentStock();
				}
				itemDO.setOriStock(all);
				itemDO.setCurStock(all);	
			}
			long itemId = itemDAO.getNewItemId();
			itemDO.setId(itemId);
			// 上架商品 插入商品快照
			long result = 0;
			log.debug("插入快照");
			if (itemDO.getStatus() == ItemConstant.STATUS_TYPE_0 || itemDO.getStatus() == ItemConstant.STATUS_TYPE_1) {
				result = createItemSnapshot(itemDO);
			}
			log.debug("插入快照结果：" + result);

			getZizuTransactionTemplate().execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus status) {
					try {
						itemDAO.insertItemItem(itemDO);
						if (itemPicList != null && itemPicList.size() > 0) {
							createItemPic(itemPicList, itemDO.getId());
							itemDO.setItemPicList(itemPicList);
						}
						if (skuList != null && skuList.size() > 0) {
							createItemSku(skuList, itemDO.getId(), itemDO.getSellerId(), true);
							itemDO.setSkuList(skuList);
						}
						if(customSkuList != null && customSkuList.size() > 0){
							createItemCustomSku(customSkuList, itemDO.getId(), itemDO.getSellerId(), true);
							itemDO.setCustomSkuList(customSkuList);
						}
					} catch (Exception e) {
						status.setRollbackOnly();
						throw new RuntimeException(e);
					}
					return null;
				}
			});
			
//			categoryCacheManager.resetItemDOCache(itemDO);
		} catch (Exception e) {
			throw new ManagerException("Event=[ItemManager#itemReleased]发布商品错误", e);
		}
		
		try {
			//发布商品当商品为立刻上架时，同步搜索
			if(itemDO.getStatus()!=null&&(itemDO.getStatus()==0||itemDO.getStatus()==1)){
				ItemIndexDO item = null;
				item = setItemIndexDO(itemDO);
				updateSolrService.itemInput(item);
			}
		} catch (Exception e) {
			//搜索更新不影响发布,update by liuboen @2011-11-5
			log.error("synchronized search interface error",e);
		}
		
		return 0;
	}

	public void createItemPic(List<ItemPicDO> itemPicList, long itemId) throws ManagerException {
		for (ItemPicDO itemPicDO : itemPicList) {
			itemPicDO.setItemId(itemId);
			createItemPic(itemPicDO);
		}
	}
	
	public long createItemPic(ItemPicDO itemPicDO) throws ManagerException {
		try {
			return itemPicDAO.insertItemPic(itemPicDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#createItemPic]创建商品副图", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#itemUpdate(com.yuwang. pinju.domain.item.ItemDO,
	 * java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void itemUpdate(final ItemDO itemDO, final List<SkuDO> skuList, final List<ItemPicDO> itemPicList, final List<CustomProValueDO> customSkuList) throws ManagerException {
		try {
			//存在SKU时，商品总数量=sku数量之和
			if (skuList != null && skuList.size() > 0) {
				long all = 0;
				for(int i=0;i<skuList.size();i++){
					SkuDO sdo = (SkuDO)skuList.get(i);
					all = all + sdo.getCurrentStock();
				}
				itemDO.setOriStock(all);
				itemDO.setCurStock(all);	
			}
			long itemId = (Long) getZizuTransactionTemplate().execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus status) {
					long id = 0;
					try {
						id = itemDAO.updateItem(itemDO);
						ItemPicDO itemPicDO = new ItemPicDO();
						itemPicDO.setItemId(itemDO.getId());
						itemPicDAO.deleteItemPic(itemPicDO);
						if (itemPicList != null && itemPicList.size() > 0) {
							createItemPic(itemPicList, itemDO.getId());
							itemDO.setItemPicList(itemPicList);
						}
						skuDAO.deleteItemSku(itemDO.getId());
						if (skuList != null && skuList.size() > 0) {
							createItemSku(skuList, itemDO.getId(), itemDO.getSellerId(), true);
							itemDO.setSkuList(skuList);
						}
						customProValueDAO.deleteCustomSku(itemDO.getId());
						if(customSkuList != null && customSkuList.size() > 0){
							createItemCustomSku(customSkuList, itemDO.getId(), itemDO.getSellerId(), true);
							itemDO.setCustomSkuList(customSkuList);
						}
						ItemDO i = itemDAO.selectItemDObyId(itemDO.getId());

						// 上架商品 插入商品快照
						if (i.getStatus() == ItemConstant.STATUS_TYPE_0 || i.getStatus() == ItemConstant.STATUS_TYPE_1) {
							createItemSnapshot(i);
						}

					} catch (Exception e) {
						status.setRollbackOnly();
						throw new RuntimeException(e);
					}
					return id;
				}
			});
			
		} catch (Exception e) {
			throw new ManagerException("Event=[ItemManager#itemUpdate]更新商品错误", e);
		}
		
		try {
			categoryCacheManager.resetItemDOCache(itemDO);
			//编辑商品当商品为立刻上架时，同步搜索中该商品数据;当商品为放入仓库时，删除搜索中该商品数据
			if(itemDO.getStatus()!=null&&(itemDO.getStatus()==0||itemDO.getStatus()==1)){
				ItemIndexDO item = null;
				item = setItemIndexDO(itemDO);
				updateSolrService.itemInput(item);
			}else{
				//调用停止分销接口
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setItemId(itemDO.getId());
				distributorManager.discardDistributorGoods(itemInfo);
				updateSolrService.deleteItemById(itemDO.getId());
			}
		} catch (Exception e) {
			//搜索更新不影响发布,update by liuboen @2011-11-5
			log.error("synchronized search interface or stop Distribution interface error",e);
		}
	}

	@Override
	public List<ItemDO> queryItemListEx(ItemQueryEx itemQuery) throws ManagerException {
		try {
			List<ItemDO> list = itemDAO.queryItemListEx(itemQuery);
			List<ItemDO> result = new ArrayList<ItemDO>();
			
			for(ItemDO itemdo:list){
				String[] categoryids = StringUtil.split(itemdo.getStoreCategories(), ",");
				if(categoryids!=null&&itemQuery.getCategory()!=null){
					for(String categoryid:categoryids){
						if(categoryid.equalsIgnoreCase(itemQuery.getCategory().toString())){
							result.add(itemdo);
						}
					}
				}else if(itemQuery.getCategory()==null){
					result.add(itemdo);
				}
				
			}
			return result;
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#queryItemListEx]获得商品列表错误", e);
		}
	}
	@Override
	public List<ItemAndOtherVO> queryItemListExAndOtherInfo(ItemQueryEx itemQuery) throws ManagerException {
		try {
			List<ItemDO> itemList= itemDAO.queryItemListEx(itemQuery);
			List<ItemAndOtherVO> list =new ArrayList<ItemAndOtherVO>();
			for (ItemDO itemDO : itemList) {
				/*ItemAndOtherVO itemAndOtherDO=(ItemAndOtherVO)itemDO;
				if (itemAndOtherDO!=null) {
					//折扣价
					itemAndOtherDO.setDiscountPrice(setActivityInfo(itemAndOtherDO));
					//购买数
					itemAndOtherDO.setBuyCount(setBuyCount(itemAndOtherDO.getId()));
					
					list.add(itemAndOtherDO);
				}*/
			}
			
			return list;
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#queryItemListEx]获得商品列表错误", e);
		}
	}
	@Override
	public List<Map<String, Object>> getSimpleItemMaps(List<ItemDO> itemList)
			throws ManagerException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (ItemDO itemDO : itemList) {
			if (itemDO != null) {
				// 折扣价
				String discountPrice = setActivityInfo(itemDO);
				// 购买数
				Long buyCount = setBuyCount(itemDO.getId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", itemDO.getId());
				map.put("price", itemDO.getPriceByYuan());
				map.put("discountPrice", discountPrice);
				map.put("title", itemDO.getTitle());
				map.put("picUrl", itemDO.getPicUrl());
				map.put("sellerId", itemDO.getSellerId());
				map.put("sellerNick", itemDO.getSellerNick());
				map.put("buyCount", buyCount);
				list.add(map);
			}
		}
		return list;
	}
	/**
	 * 设置活动信息
	 * 
	 * @param limitTimeDiscountID
	 * @throws ManagerException
	 */
	private String setActivityInfo(ItemDO itemAndOtherDO) throws ManagerException {
		String limitTimeDiscountID = itemAndOtherDO.getFeatureByEnum(ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT);
		if (limitTimeDiscountID == null) {
			return null;
		}
		long discountID = Long.parseLong(limitTimeDiscountID);
		ActivityDiscountDO activityDiscountDO = categoryCacheManager.getActivityDiscountDOId(discountID);
		long dateNow = new Date().getTime();
		// 验证是否活动到期以及是否活动中
		if (activityDiscountDO != null && activityDiscountDO.getStartTime().getTime() <= dateNow && activityDiscountDO.getEndTime().getTime() > dateNow) {
			String[] ids = activityDiscountDO.getItemList().split(",");
			for (int i = 0; i < ids.length; i++) {
				long id = Long.parseLong(ids[i]);
				if (id == itemAndOtherDO.getId()) {
					// 获取折扣
					String[] actDiscounts = activityDiscountDO.getDiscountList().split(",");
					long activityDiscount = Long.parseLong(actDiscounts[i]);
					Double actDiscount = Double.valueOf(activityDiscount) / 1000;
					//计算折后价
					Money money = new Money(itemAndOtherDO.getPrice());
					Money moneyDiscount=money.multiply(actDiscount);
					return moneyDiscount.toString();
				}
			}
		}
		return null;
	}
	
	private long setBuyCount(long itemId) {
		try {
			ItemSalesStatDO itemSalesStatDO = itemSalesStatManager.getItemSalesStatDO(itemId);
			if (itemSalesStatDO != null) {
				return itemSalesStatDO.getBuyNum();
			}
		} catch (ManagerException e) {
			log.error("查找商品销量异常:itemId=" + itemId);
			log.error(e);
		}
		return 0l;
	}
	
	@SuppressWarnings("unused")
	private ItemAndOtherVO setFreight(ItemAndOtherVO itemAndOtherVO,String cityIp) throws ManagerException{
		long freight=0;
		if (itemAndOtherVO.getFreeTemplateId()<=0) {
			if (itemAndOtherVO.getEmsCosts()!=null&&itemAndOtherVO.getEmsCosts()!=0) {
				freight=itemAndOtherVO.getEmsCosts();
			}
			if (itemAndOtherVO.getDeliveryCosts()!=null&&itemAndOtherVO.getDeliveryCosts()!=0&&freight>itemAndOtherVO.getDeliveryCosts()) {
				freight=itemAndOtherVO.getDeliveryCosts();
			}
			if (itemAndOtherVO.getMailCosts()!=null&&itemAndOtherVO.getMailCosts()!=0&&freight>itemAndOtherVO.getMailCosts()) {
				freight=itemAndOtherVO.getMailCosts();
			}
			itemAndOtherVO.setFreightStr(MoneyUtil.getCentToDollar(freight));
			return itemAndOtherVO;
		}
		Map<String,String> logisticsMap=logisticsCityIpManager.getDefaultRegionCarriage(itemAndOtherVO.getFreeTemplateId(), cityIp);
		if (logisticsMap!=null) {
			itemAndOtherVO.setCity(logisticsMap.get("cityName"));
			for (Entry<String, String> entry: logisticsMap.entrySet()) {
				String innerTemp="";
				if (!entry.getKey().equals("cityName")&&entry.getValue()!=null&&!entry.getValue().equals("0")) {
					long tmp=Long.parseLong(entry.getValue());
					if (tmp<freight||freight==0) {
						freight=tmp;
					}
				}
			}
			itemAndOtherVO.setFreightStr(MoneyUtil.getCentToDollar(freight));
		}
		return itemAndOtherVO;
	}
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public void setItemSnapshotDao(ItemSnapshotDAO itemSnapshotDao) {
		this.itemSnapshotDao = itemSnapshotDao;
	}

	public void setSkuDAO(SkuDAO skuDAO) {
		this.skuDAO = skuDAO;
	}
	
	public void setCustomProValueDAO(CustomProValueDAO customProValueDAO) {
		this.customProValueDAO = customProValueDAO;
	}
	
	public void setItemPicDAO(ItemPicDAO itemPicDAO) {
		this.itemPicDAO = itemPicDAO;
	}
	
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}
	
//	public void setCategoryManager(CategoryManager categoryManager) {
//		this.categoryManager = categoryManager;
//	}
	
	public void setUpdateSolrService(UpdateSolrService updateSolrService) {
		this.updateSolrService = updateSolrService;
	}
	
	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}
	
	public void setDistributorManager(DistributorManager distributorManager) {
		this.distributorManager = distributorManager;
	}
	
//	public void setItemSalesStatAO(ItemSalesStatAO itemSalesStatAO) {
//		this.itemSalesStatAO = itemSalesStatAO;
//	}
	
	public SkuManager getSkuManager() {
		return skuManager;
	}

	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#updateItem(com.yuwang. pinju.domain.item.vo.ItemVO)
	 */
	@Override
	public int updateItem(ItemDO itemDO) throws ManagerException {
		try {
			return itemDAO.updateItem(itemDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemListByIds],通过id list 获取商品列表失败", e);
		}
	}

	@Override
	public void updateItemSku(List<SkuDO> skuList) throws ManagerException {

		try {
			for (SkuDO skuDO : skuList) {
				skuDO.setGmtModified(DateUtil.current());
				updateItemSku(skuDO);
			}
		} catch (ManagerException e) {
			throw new ManagerException("Event=[ItemManager#getItemListByIds],更新商品SKU", e);
		}
	}

	@Override
	public long updateItemSku(SkuDO skuDO) throws ManagerException {
		try {
			return skuDAO.updateItemSku(skuDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#updateItemSku]更新商品 SKU", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#updateItemStoreCategories (java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateItemStoreCategories(final Map<Long, String> temps) throws ManagerException {
		try {

			int count = (Integer) getZizuTransactionTemplate().execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus status) {
					int result = 0;
					try {
						for (Long itemId : (Set<Long>) temps.keySet()) {
							String temp = temps.get(itemId);
							ItemDO i = new ItemDO();
							i = itemDAO.selectItemDObyId(itemId);
							i.setId(itemId);
							i.setStoreCategories(temp);
							result += updateItem(i);
							ItemIndexDO itemIndexDO = null;
							itemIndexDO = setItemIndexDO(i);
							updateSolrService.itemInput(itemIndexDO);
						}
					} catch (Exception e) {
						status.setRollbackOnly();
						throw new RuntimeException(e);
					}
					return result;
				}
			});
			return count;
		} catch (Exception e) {
			throw new ManagerException("Event=[ItemManager#updateItemFreeTemplates],更新商品店铺分类", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#shelvesItemByFreight(long)
	 */
	@Override
	public int shelvesItemByFreight(long freightId) throws ManagerException {
		try {
			return itemDAO.updateItemStatusByFreight(freightId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#setFeatures(long, com.yuwang.pinju.Constant.ItemFeaturesEnum,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Result setFeatures(final long itemId, final ItemFeaturesEnum featureEnum, final String toValue)
			throws ManagerException {
		final Result result = new ResultSupport();
		result.setSuccess(false);
		// **------------------- 华丽的验证线 -----------------------------------**//
		if (itemId <= 0) {
			result.getModels().put("errorMessage", "商品不存在");
			return result;
		}
		if (featureEnum == null || !(featureEnum.getFeatureType() > 0)) {
			result.getModels().put("errorMessage", "枚举类型为空");
			return result;
		}
		// 事务处理
		return (Result) zizuTransactionTemplate.execute(new TransactionCallback() {
			boolean isSucess = false;

			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					// 先获取features字段
					ItemDO itemDO = itemDAO.selectItemDObyId(itemId);
					if (itemDO == null) {
						result.getModels().put("errorMessage", "商品不存在");
						return result;
					}
					String features = itemDO.getFeatures();
					// 获取features map
					Map<String, String> featuresMap = FeaturesUtil.getFeaturesMapByFeatures(features);
					if (toValue == null || toValue.equals("")) {
						// 将某个置空
						featuresMap.remove(featureEnum.getFeatureName());
					} else {
						// **------------------- 华丽的验证线
						// -------------------------------------**//
						if (toValue.indexOf(";") != -1 || toValue.indexOf(",") != -1 || toValue.indexOf(":") != -1) {
							result.getModels().put("errorMessage", "value不能存在特殊字符(; , :)");
							return result;
						}
						// 添加某个Feature 到features字段
						featuresMap.put(featureEnum.getFeatureName(), toValue);
					}
					// 通过新的map生成features
					String feature = FeaturesUtil.getFeaturesByMap(featuresMap);
					// 获取更新版本号
					Long version = itemDO.getVersion();
					int flag = itemDAO.updateItemFeatures(itemId, feature, version.longValue());
					if (flag > 0) {
						isSucess = true;
						result.setSuccess(true);
						return result;
					} else {
						result.getModels().put("errorMessage", "没有更新");
						return result;
					}
				} catch (DaoException e) {
					result.getModels().put("errorMessage", "DAOException");
				} finally {
					if (!isSucess) {
						status.setRollbackOnly();
					}
				}
				return result;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#hasStoreCategories(long, java.lang.String)
	 */
	@Override
	public boolean hasStoreCategories(long sellerId, String storeCategoriesId, List<Long> status)
			throws ManagerException {
		try {

			List<ItemDO> ls = itemDAO.selectItemList(sellerId);

			if (ls == null) {
				return false;
			}
			
			for (ItemDO d : ls) {
				if (status != null && !status.contains(d.getStatus())) {
					continue;
				}
				if (d.getStoreCategories() != null) {
					String s[] = StringUtil.split(d.getStoreCategories(), ",");
					if (s != null && s.length > 0) {
						for (String t : s) {
							if (t.equalsIgnoreCase(storeCategoriesId)) {
								return true;
							}
						}
					}
				}
			}
			return false;

		} catch (Exception e) {
			throw new ManagerException("Event=[ItemManager#hasStoreCategories],是否存在店铺类型", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#setInventory(long, java.lang.Long, int)
	 */
	@Override
	public Result cutStock(final long itemId,final  Long skuId,final  long num)
			throws ManagerException {
		final Result result=new ResultSupport();
		result.setSuccess(false);
		return (Result)zizuTransactionTemplate.execute(new TransactionCallback<Result>() {
			@Override
			public Result doInTransaction(TransactionStatus status) {
				try {
					ItemDO itemDO=itemDAO.selectItemDObyId(itemId);
					long currentStock=itemDO.getCurStock()-num;
					if (itemDO==null) {
						//商品不存在
						result.getModels().put("errorMessage","ITEM_IS_NULL");
						return result;
					}
					if (currentStock<0) {
						//item库存少了
						result.getModels().put("errorMessage","ITEM_CUR_STOCK_LESS");
						return result;
					}
					if(skuId!=null&&skuId>0){
						SkuDO skuDO=skuDAO.getItemSkuById(skuId);
						if (skuDO==null) {
							//商品不存在
							result.getModels().put("errorMessage","SKU_IS_NULL");
							return result;
						}
						long skuCurrentStock=skuDO.getCurrentStock()-num;
						if (skuCurrentStock<0) {
							//sku库存少了
							result.getModels().put("errorMessage","SKU_CUR_STOCK_LESS");
							return result;
						}
						skuDO.setCurrentStock(skuCurrentStock);
						int flag=skuDAO.updateItemSku(skuDO);
						if (flag<=0) {
							result.getModels().put("errorMessage","UPDATE_SKU_CUURENT_STOCK_ERROR");
							return result;
						}
					}
					Long currentStatus=null;
					if (currentStock==0) {
						currentStatus=ItemConstant.STATUS_TYPE_7;
					}
					int flag=itemDAO.updateItemCurrentStock(itemId, currentStock, itemDO.getVersion(),currentStatus);
					if (flag<=0) {
						result.getModels().put("errorMessage","UPDATE_ITEM_CUURENT_STOCK_ERROR");
						return result;
					}else {
						result.setSuccess(true);
						boolean isDeleteCache= categoryCacheManager.deleteItemDOCache(itemId);
						if (!isDeleteCache) {
							log.error("event=[ItemManagerImpl#cutStock] delete itemDO cache error");
						}
					}
					/*if (itemDO.getStatus()!=1&&itemDO.getStatus()!=2) {
						result.getModels().put("errorMessage","商品不是出售中");
						return result;
					}*/
				} catch (DaoException e) {
					status.setRollbackOnly();
					result.getModels().put("errorMessage","DAO_EXCEPTION");
				}catch (ManagerException e) {
					log.error("event=[ItemManagerImpl#cutStock] delete itemDO cache error");
				}finally{
					//if (!result.isSuccess()) {
					//	status.setRollbackOnly();
					//}
				}
				return result;
			}
		});
		
	}
	
	@Override
	public boolean getItemCountByfreeTemplateId(long freeTemplateId)throws ManagerException {
		try {
			ItemQuery itemQuery = new ItemQuery();
			itemQuery.setFreeTemplateId(freeTemplateId);
			List<Object> statusList = new ArrayList();
			//0,1,-1,-3,-5,-6,-7
			statusList.add(ItemConstant.STATUS_TYPE_0);
			statusList.add(ItemConstant.STATUS_TYPE_1);
			statusList.add(ItemConstant.STATUS_TYPE_2);
			statusList.add(ItemConstant.STATUS_TYPE_4);
			statusList.add(ItemConstant.STATUS_TYPE_6);
			statusList.add(ItemConstant.STATUS_TYPE_7);
			statusList.add(ItemConstant.STATUS_TYPE_8);
			statusList.add(ItemConstant.STATUS_TYPE_9);
			itemQuery.setStatus(statusList);
			int items = 0;
			items = itemDAO.getItemCountByfreeTemplateId(itemQuery);
			if (items >0) {
				return true;
			}
			return false;

		} catch (Exception e) {
			throw new ManagerException("Event=[ItemManager#getItemCountByfreeTemplateId],该运费模版下是否存在商品", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemDOById(long)
	 */
	@Override
	public ItemDO getReadItemDOById(long id) throws ManagerException {
		try {
			return itemDAO.selectReadItemDObyId(id);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#getItemDOById]获取单条商品信息错误,read database", e);
		}

	}
	
	/**
	 * 设置搜索商品对象
	 * 
	 * @param itemDO
	 * @return
	 * @throws ManagerException
	 */
	private ItemIndexDO setItemIndexDO(ItemDO itemDO) throws Exception {
		ItemIndexDO item = null;
		try {
			if(itemDO.getId()!=null){
				item = solrService.getUpdateItem(itemDO.getId());
				if(item==null)item = new ItemIndexDO();
				item.setId(itemDO.getId());
//				ItemSalesStatDO itemSalesStatDO = itemSalesStatAO.getItemSalesStatById(item.getId());
//				if (itemSalesStatDO != null) {
//					item.setSalesNum(itemSalesStatDO.getBuyNum());
//				} else {
//					item.setSalesNum(0L);
//				}
			
//				if(itemDO.getCatetoryId()!=null){
//					item.setCatetoryId(itemDO.getCatetoryId());
//					CategoryDO categoryDO = categoryManager.getItemCategory(itemDO.getCatetoryId());
//					if(categoryDO!=null){
//						item.setCatetoryName(categoryDO.getName());
//					}
//				}
				if(itemDO.getSpuId()!=null){
					item.setSpuId(itemDO.getSpuId());
				}
				if(itemDO.getTitle()!=null){
					item.setTitle(itemDO.getTitle());
				}
				if(itemDO.getStoreCategories()!=null){
					item.setStoreCategories(itemDO.getStoreCategories());
				}
				if(itemDO.getSellerId()!=null){
					item.setSellerId(itemDO.getSellerId());
				}
				if(itemDO.getSellerNick()!=null){
					item.setSellerNick(itemDO.getSellerNick());
				}
				if(itemDO.getPropertyValuePair()!=null){
					item.setPropertyValuePair(itemDO.getPropertyValuePair());
				}
				if(itemDO.getPicUrl()!=null){
					item.setPicUrl(itemDO.getPicUrl());
				}
				if(itemDO.getPrice()!=null){
					item.setPrice(itemDO.getPrice());
				}
				if(itemDO.getProvinces()!=null){
					item.setProvinces(itemDO.getProvinces());
				}
				if(itemDO.getCity()!=null){
					item.setCity(itemDO.getCity());
				}
				if(itemDO.getDeliveryCosts()!=null){
					item.setDeliveryCosts(itemDO.getDeliveryCosts());
				}
				if(itemDO.getMailCosts()!=null){
					item.setMailCosts(itemDO.getMailCosts());
				}
				if(itemDO.getEmsCosts()!=null){
					item.setEmsCosts(itemDO.getEmsCosts());
				}
				if(itemDO.getStartTime()!=null){
					item.setStartTime(itemDO.getStartTime());
				}
				if(itemDO.getEndTime()!=null){
					item.setEndTime(itemDO.getEndTime());
				}
				if(itemDO.getItemDegree()!=null){
					item.setItemDegree(itemDO.getItemDegree());
				}
				if(itemDO.getFeatures()!=null){
					item.setFeatures(itemDO.getFeatures());
				}
				if(itemDO.getStatus()!=null){
					item.setStatus(itemDO.getStatus());
				}
				if(itemDO.getCurStock()!=null){
					item.setCurStock(itemDO.getCurStock());
				}
				if(itemDO.getGmtCreate()!=null){
					item.setGmtCreate(itemDO.getGmtCreate());
				}
				if(itemDO.getCode()!=null){
					item.setCode(itemDO.getCode());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return item;
	}

	@Override
	public String getItemTitles(ItemQueryEx itemQuery) throws ManagerException {
		StringBuffer itemTitles = new StringBuffer();
		try {
			List<ItemDO> list = itemDAO.getItemTitles(itemQuery);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					ItemDO itemDO =(ItemDO)list.get(i);
					if(itemDO!=null&&itemDO.getTitle()!=null){
						if(itemTitles!=null&&itemTitles.toString().length()>0){
							itemTitles.append("  ;  ");
							itemTitles.append(itemDO.getTitle());
						}else{
							itemTitles.append(itemDO.getTitle());
						}	
					}
				}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemTitles.toString();
	}
	
	/**
	 * 通过ID取得单条商品信息
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public ItemDO selectItemDObyId(long id) throws ManagerException {
		try {
			return itemDAO.selectItemDObyId(id);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#selectItemDObyId],read database", e);
		}
	}

	public void setItemSalesStatManager(ItemSalesStatManager itemSalesStatManager) {
		this.itemSalesStatManager = itemSalesStatManager;
	}

	public void setLogisticsCityIpManager(
			LogisticsCityIpManager logisticsCityIpManager) {
		this.logisticsCityIpManager = logisticsCityIpManager;
	}
	
}
