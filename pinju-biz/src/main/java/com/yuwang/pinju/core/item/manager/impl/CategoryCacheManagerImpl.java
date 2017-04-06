/**
 * 
 */
package com.yuwang.pinju.core.item.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.JsoupUtil;
import com.yuwang.pinju.core.active.manager.ActivityDiscountManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.BrandManager;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.core.item.manager.CustomCateProValueManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.item.manager.ItemPicManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.item.service.CategoryCacheServer;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.BrandDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CpValueRelationDO;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemPicDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.item.SpuDO;

/**
 * @Project: pinju-biz
 * @Title: CategoryCacheManagerImpl.java
 * @Package com.yuwang.pinju.core.item.service.impl
 * @Description: 类目缓存调用类
 * @author liuboen liuboen@zba.com
 * @date 2011-6-24 上午11:17:41
 * @version V1.0
 */

public class CategoryCacheManagerImpl extends BaseManager implements CategoryCacheManager {
	protected final Log log = LogFactory.getLog("cate-cache-manager");
	private CategoryManager categoryManager;

	private BrandManager brandManager;

	private CategoryCacheServer categoryCacheServer;

	private ItemManager itemManager;

	private  SkuManager skuManager;
	
	private ItemPicManager itemPicManager;
	
	private ActivityDiscountManager activityDiscountManager;
	
	private CustomCateProValueManager customProValueManager;
	
	private MemcachedClient itemMemcachedClient;

	/** 为缓存添加前缀防止重复 */
	private final static String CP_LIST_MEMCACHE_SUFFIX = "cplist";
	private final static String CP_MEMCACHE_SUFFIX = "cp";
	private final static String BASE_VALUE_MEMCACHE_SUFFIX = "bv";
	private final static String SPU_MEMCACHE_SUFFIX = "spu";
	private final static String ITEM_MEMCACHE_SUFFIX = "item";
	private final static String ACT_LIMIT_DISCOUNT_MEMCACHE_SUFFIX = "limitDis";
	private final static String ITEM_CUSTOM_PRO_VALUE_MEMCACHE_SUFFIX = "customProVal";

	/**
	 * 缓存时间
	 */
	private final static int CP_LIST_MEMCACHE_CACHETIME = 60*5;   
	private final static int CP_MEMCACHE_CACHETIME = 60*5;            
	private final static int BASE_VALUE_MEMCACHE_CACHETIME = 60 * 60 * 12;    
	private final static int SPU_MEMCACHE_CACHETIME = 60*5;          
	private final static int ITEM_MEMCACHE_CACHETIME = 60*5;          
	private final static int ACT_LIMIT_DISCOUNT_MEMCACHE_CACHETIME = 60*15;          
	private final static int ITEM_CUSTOM_PRO_VALUE_MEMCACHE_CACHETIME = 60*15;          
	
	/**
	 * 类目全量或增量加载
	 */
	private boolean isLoadCategoryLock=false;
	/**
	 * 初始化memcache配置
	 */
	public void init() {
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#loadFullCategory()
	 */
	@Override
	public boolean loadFullCategory() throws ManagerException {
		synchronized (categoryCacheServer) {
			if (!isLoadCategoryLock) {
				isLoadCategoryLock = Boolean.TRUE;
				Boolean loadTempBoolean = categoryCacheServer.loadFullCategory();
				isLoadCategoryLock = Boolean.FALSE;
				return loadTempBoolean;
			} else {
				return Boolean.FALSE;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#resetFullCategory()
	 */
	@Override
	public boolean resetFullCategory() throws ManagerException {
		synchronized (categoryCacheServer) {
			if (!isLoadCategoryLock) {
				isLoadCategoryLock = Boolean.TRUE;
				Boolean loadTempBoolean = categoryCacheServer.resetFullCategory();
				isLoadCategoryLock = Boolean.FALSE;
				return loadTempBoolean;
			} else {
				return Boolean.FALSE;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#incrLoadCategory()
	 */
	@Override
	public boolean incrLoadCategory() throws ManagerException {
		synchronized (categoryCacheServer) {
			if (!isLoadCategoryLock) {
				isLoadCategoryLock=Boolean.TRUE;
				Boolean loadTempBoolean=  categoryCacheServer.incrLoadCategory();
				isLoadCategoryLock=Boolean.FALSE;
				return loadTempBoolean;
			}else {
				return Boolean.FALSE;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#getItemCategoryListByParentId(long)
	 */
	@Override
	public List<CategoryDO> getItemCategoryListByParentId(long parentId) throws ManagerException {

		try {
			List<CategoryDO> catList = null;
			if (parentId>0) {
				Map<Long, CategoryDO> m = categoryCacheServer.getForestDO().getFullCategories();
				CategoryDO thisCategoryDO=m.get(parentId);
				if (thisCategoryDO == null) {
					return null;
				}
				catList = thisCategoryDO.getChildCateList();
			}else {
				catList = categoryCacheServer.getForestDO().getRootCategories();
			}
			return getSimpleCategoryDOs(catList);
		} catch (Exception e) {
			throw new ManagerException("通过父类目编号 获得类目列表", e);
		}
	}


	@Override
	public List<CategoryDO> getCategoryParentLevel(long parentId)
			throws ManagerException {
		try {
			List<CategoryDO> catList = new ArrayList<CategoryDO>();
			if (parentId>0) {
				Map<Long, CategoryDO> m = categoryCacheServer.getForestDO().getFullCategories();
				CategoryDO thisCategoryDO=m.get(parentId);
				if (thisCategoryDO==null) {
					return null;
				}
				catList.add(thisCategoryDO.simpleClone());
				while (thisCategoryDO.getCategoryLevel()>1) {
					thisCategoryDO=thisCategoryDO.getParentCategoryDO();
					catList.add(thisCategoryDO.simpleClone());
				}
				Collections.reverse(catList);
			}else {
				return null;
			}
			return catList;
		} catch (Exception e) {
			throw new ManagerException("获取类目层级错误", e);
		}
	}

	/**
	 * copy 对象
	 * @param complexCategoryDOs
	 * @return
	 */
	private List<CategoryDO> getSimpleCategoryDOs(List<CategoryDO> complexCategoryDOs){
		ArrayList<CategoryDO> newCatList = new ArrayList<CategoryDO>();
		for (CategoryDO categoryDO : complexCategoryDOs) {
			newCatList.add(categoryDO.simpleClone());
		}
		return newCatList;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#getItemRootCategoryList()
	 */
	@Override
	public List<CategoryDO> getRootCategoryList() throws ManagerException {
		List<CategoryDO> rootCategoryDOs=categoryCacheServer.getForestDO().getRootCategories();
		ArrayList<CategoryDO> newCatList = new ArrayList<CategoryDO>();
		for (CategoryDO categoryDO : rootCategoryDOs) {
			newCatList.add(categoryDO.simpleClone());
		}
		return newCatList;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#getForestCategoryDOById()
	 */
	@Override
	public CategoryDO getForestCategoryDOById(long id) throws ManagerException {
		try {
			CategoryDO categoryDO = categoryCacheServer.getForestDO().getFullCategories().get(id);
			return categoryDO;
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#getForestCategoryDOById()
	 */
	@Override
	public CategoryDO getCategoryDOById(long id) throws ManagerException {
		try {
			CategoryDO categoryDO = categoryCacheServer.getForestDO().getFullCategories().get(id);
			return categoryDO==null?null:categoryDO.simpleClone();
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryPropertyDO> getItemCategoryProperty(long categoryId) throws ManagerException {
		List<CategoryPropertyDO> cateProList = null;
		String categoryIdString = CP_LIST_MEMCACHE_SUFFIX + String.valueOf(categoryId);
		// 将获取和写入缓存分两步捕获异常,防止缓存服务器down机,导致前端应用使用失败。
		try {
			// 取memcache,没有值则走数据库

			cateProList = (List<CategoryPropertyDO>) itemMemcachedClient.get(categoryIdString);

		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]连接缓存服务器超时,[read]", e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]缓存服务器线程中断,[read]", e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]缓存服务器MemcachedException,[read]", e);
		}
		try {
			if (cateProList == null) {
				// 数据库再没的值就怪不得我了
				cateProList = categoryManager.getItemCategoryProperty(categoryId);
				if (cateProList != null && cateProList.size() > 0) {
					boolean isCache = itemMemcachedClient.set(categoryIdString, CP_LIST_MEMCACHE_CACHETIME, cateProList);
					if (!isCache) {
						log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]cache categoryProperty list error,categoryId="
										+ categoryId);
					}
				}
			}
		} catch (ManagerException e) {
			throw new ManagerException("数据库级别错误", e);
		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]连接缓存服务器超时,[write]", e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]缓存服务器线程中断,[write]", e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]缓存服务器MemcachedException,[write]", e);
		}

		return cateProList;
	}

	@Override
	public List<CategoryPropertyValueDO> getItemCategoryPropertyValue(long categoryPropertyId) throws ManagerException {
		try {
			return categoryManager.getItemCategoryPropertyValue(categoryPropertyId);
		} catch (ManagerException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public BaseValueDO getBaseValueById(long id) throws ManagerException {
		BaseValueDO baseValueDO = null;
		String baseIdAndPrefix = BASE_VALUE_MEMCACHE_SUFFIX + String.valueOf(id);
		try {

			baseValueDO = (BaseValueDO) itemMemcachedClient.get(baseIdAndPrefix);
		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getBaseValueById]连接缓存服务器超时,[read]", e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getBaseValueById]缓存服务器线程中断,[read]", e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getBaseValueById]缓存服务器MemcachedException,[read]", e);
		}
		try {
			if (baseValueDO == null) {
				baseValueDO = categoryManager.getBaseValueById(id);
				if (baseValueDO != null) {
					boolean isCache = itemMemcachedClient.set(baseIdAndPrefix, BASE_VALUE_MEMCACHE_CACHETIME, baseValueDO);
					if (!isCache) {
						log
								.error("event=[CategoryCacheManagerImpl#getBaseValueById]cache BaseValueDO  error,basevalueid="
										+ id);
					}
				}
			}
		} catch (ManagerException e) {
			throw new ManagerException(e);
		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getBaseValueById]连接缓存服务器超时,[write]", e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getBaseValueById]缓存服务器线程中断,[write]", e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getBaseValueById]缓存服务器MemcachedException,[write]", e);
		}

		return baseValueDO;
	}

	@Override
	public CategoryPropertyDO getItemCategoryPropertyById(long id) throws ManagerException {
		CategoryPropertyDO categoryPropertyDO = null;
		String cpIdAndPrefix = CP_MEMCACHE_SUFFIX + String.valueOf(id);
		try {

			categoryPropertyDO = (CategoryPropertyDO) itemMemcachedClient.get(cpIdAndPrefix);
		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryPropertyById]连接缓存服务器超时,[write]", e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryPropertyById]缓存服务器线程中断,[write]", e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryPropertyById]缓存服务器MemcachedException,[write]", e);
		}
		try {
			if (categoryPropertyDO == null) {
				categoryPropertyDO = categoryManager.getItemCategoryPropertyById(id);
				if (categoryPropertyDO != null) {
					boolean isCache = itemMemcachedClient.set(cpIdAndPrefix, CP_MEMCACHE_CACHETIME, categoryPropertyDO);
					if (!isCache) {
						log.error("event=[CategoryCacheManagerImpl#getItemCategoryPropertyById]cache CategoryPropertyDO  error,cateProid="
										+ id);
					}
				}
			}
		} catch (ManagerException e) {
			throw new ManagerException(e);
		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryPropertyById]连接缓存服务器超时,[write]", e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryPropertyById]缓存服务器线程中断,[write]", e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategoryPropertyById]缓存服务器MemcachedException,[write]", e);
		}

		return categoryPropertyDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#getItemCategorySpubyId(long)
	 */
	@Override
	public SpuDO getItemCategorySpubyId(long spuid) throws ManagerException {
		SpuDO spuDO = null;
		String id = SPU_MEMCACHE_SUFFIX + String.valueOf(spuid);
		// 将获取和写入缓存分两步,防止缓存服务器down机,导致前端应用使用失败。
		try {
			spuDO = (SpuDO) itemMemcachedClient.get(id);
		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategorySpubyId]连接缓存服务器超时,[read]", e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategorySpubyId]缓存服务器线程中断,[read]", e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemCategorySpubyId]缓存服务器MemcachedException,[read]", e);
		}
		// 当缓存为空时读取数据库
		if (spuDO == null) {
			try {
				spuDO = categoryManager.getItemSpuById(spuid);
				if (spuDO != null) {
					boolean isCache = itemMemcachedClient.set(id, SPU_MEMCACHE_CACHETIME, spuDO);
					if (!isCache) {
						log.error("event=[CategoryCacheManagerImpl#getItemCategorySpubyId]cache CategoryPropertyDO  error,cateProid="
										+ id);
					}
				}
			} catch (ManagerException e) {
				throw new ManagerException(e);
			} catch (TimeoutException e) {
				log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]连接缓存服务器超时,[write]", e);
			} catch (InterruptedException e) {
				log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]缓存服务器线程中断,[write]", e);
			} catch (MemcachedException e) {
				log.error("event=[CategoryCacheManagerImpl#getItemCategoryProperty]缓存服务器MemcachedException,[write]", e);
			}

		}
		return spuDO;
	}

	@Override
	public List<CpValueRelationDO> getItemCpValueRelation(long cpId, long sonCpId) throws ManagerException {
		return categoryManager.getItemCpValueRelation(cpId, sonCpId);
	}

	@Override
	public CpValueRelationDO getItemCpValueRelationByCpIdAndCpvId(long cpId, long cpvId) throws ManagerException {
		return categoryManager.getItemCpValueRelationByCpIdAndCpvId(cpId, cpvId);
	}

	@Override
	public List<CpValueRelationDO> getItemCpValueRelationByCpId(long cpId) throws ManagerException {
		return categoryManager.getItemCpValueRelationByCpId(cpId);
	}

	@Override
	public BrandDO getItemBrandById(long id) throws ManagerException {
		return brandManager.getItemBrandById(id);
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#getItemDOById(long)
	 */
	@Override
	public ItemDO getItemDOById(long itemId) throws ManagerException {
		ItemDO itemDO=null;
		String  id= ITEM_MEMCACHE_SUFFIX + String.valueOf(itemId);
		try {
			itemDO=(ItemDO)itemMemcachedClient.get(id);
		}catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemDOById]connect memcache server time out [read]",e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemDOById]memcache thread Interrupted[read]",e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getItemDOById]MemcachedException[read]",e);
		}
		if (itemDO==null) {
			try {
					itemDO=itemManager.getReadItemDOById(itemId);
					if (itemDO==null) {
						itemDO=new ItemDO();
						itemDO.setIsExist(Boolean.FALSE);
					}else {
						String description=JsoupUtil.getDetailDescription(itemDO.getDescription());
						itemDO.setDescription(description);
						itemDO.setIsExist(Boolean.TRUE);
						List<SkuDO> skuList=skuManager.getReadItemSkuByItemId(itemId);
						List<ItemPicDO> picList=itemPicManager.getReadItemPicByItemId(itemId);
						if (picList!=null) {
							itemDO.setItemPicList(picList);
						}
						if (skuList!=null) {
							itemDO.setSkuList(skuList);
						}
					}
					boolean isCache=itemMemcachedClient.set(id, ITEM_MEMCACHE_CACHETIME, itemDO);
					if (!isCache) {
						log.error("event=[CategoryCacheManagerImpl#getItemDOById]cache itemDO error ,item_id="+itemId);
					}
			}catch (ManagerException e) {
				throw new ManagerException(e);
			} catch (TimeoutException e) {
				log.error("event=[CategoryCacheManagerImpl#getItemDOById]connect memcache server time out[write]",e);
			} catch (InterruptedException e) {
				log.error("event=[CategoryCacheManagerImpl#getItemDOById]memcache thread Interrupted[write]",e);
			} catch (MemcachedException e) {
				log.error("event=[CategoryCacheManagerImpl#getItemDOById]MemcachedException[write]",e);
			}
		}
		if (itemDO.getIsExist() != null && !itemDO.getIsExist()) {
			return null;
		}
		return itemDO;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#resetItemDOCache(com.yuwang.pinju.domain.item.ItemDO)
	 */
	@Override
	public boolean resetItemDOCache(ItemDO itemDO) throws ManagerException {
		//为保证数据一致性,重置使用删除
		return this.deleteItemDOCache(itemDO.getId());
	}
	
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryCacheManager#getActivityDiscountDOId(long)
	 */
	@Override
	public ActivityDiscountDO getActivityDiscountDOId(long id)
			throws ManagerException {
		String  memcacheKey= ACT_LIMIT_DISCOUNT_MEMCACHE_SUFFIX + String.valueOf(id);
		ActivityDiscountDO activityDiscountDO=null;
		try {
			activityDiscountDO=(ActivityDiscountDO)itemMemcachedClient.get(memcacheKey);
		}catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#getActivityDiscountDOId]connect memcache server time out [read]",e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#getActivityDiscountDOId]memcache thread Interrupted[read]",e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#getActivityDiscountDOId]MemcachedException[read]",e);
		}
		if (activityDiscountDO==null) {
			activityDiscountDO= activityDiscountManager.queryActivityDiscountById(id);
			if (activityDiscountDO!=null) {
				try {
				
				boolean isCache=itemMemcachedClient.set(memcacheKey, ACT_LIMIT_DISCOUNT_MEMCACHE_CACHETIME, activityDiscountDO);
				if (!isCache) {
					log.error("CategoryCacheManagerImpl#getActivityDiscountDOId,can not set memecache obj");
				}
				}catch (TimeoutException e) {
					log.error("event=[CategoryCacheManagerImpl#getActivityDiscountDOId]connect memcache server time out [read]",e);
				} catch (InterruptedException e) {
					log.error("event=[CategoryCacheManagerImpl#getActivityDiscountDOId]memcache thread Interrupted[read]",e);
				} catch (MemcachedException e) {
					log.error("event=[CategoryCacheManagerImpl#getActivityDiscountDOId]MemcachedException[read]",e);
				}
			}
		}
		return activityDiscountDO;
	}
	@Override
	public boolean deleteItemDOCache(long id) throws ManagerException {
		String  keyid= ITEM_MEMCACHE_SUFFIX + String.valueOf(id);
		try {
			ItemDO cacheItemDO=(ItemDO)itemMemcachedClient.get(keyid);
			if (cacheItemDO!=null) {
				boolean isCache=itemMemcachedClient.delete(keyid);
				if (!isCache) {
					log.error("event=[CategoryCacheManagerImpl#deleteItemDOCache]delete itemDO error ,item_id="+id);
				}else {
					//同时删除自定义属性
					deleteCustomProValueDO(id);
					return Boolean.TRUE;
				}
			}
		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#deleteItemDOCache]connect memcache server time out",e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#deleteItemDOCache]memcache thread Interrupted",e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#deleteItemDOCache]MemcachedException",e);
		}
		return Boolean.FALSE;
	}
	@Override
	public boolean deleteActivityDiscountDOCache(long actId) throws ManagerException {
		String  id= ACT_LIMIT_DISCOUNT_MEMCACHE_SUFFIX + String.valueOf(actId);
		try {
			ActivityDiscountDO cacheActivityDiscountDO=(ActivityDiscountDO)itemMemcachedClient.get(id);
			if (cacheActivityDiscountDO!=null) {
				boolean isDelete=itemMemcachedClient.delete(id);
				if (!isDelete) {
					log.error("event=[CategoryCacheManagerImpl#resetActivityDiscountDOCache]delete ActivityDiscountDO error ,id="+actId);
				}else {
					return Boolean.TRUE;
				}
			}
		} catch (TimeoutException e) {
			log.error("event=[CategoryCacheManagerImpl#resetActivityDiscountDOCache]connect memcache server time out",e);
		} catch (InterruptedException e) {
			log.error("event=[CategoryCacheManagerImpl#resetActivityDiscountDOCache]memcache thread Interrupted",e);
		} catch (MemcachedException e) {
			log.error("event=[CategoryCacheManagerImpl#resetActivityDiscountDOCache]MemcachedException",e);
		}
		return Boolean.FALSE;
	}
	
	
	@Override
	public CustomProValueDO getCustomProValueDO(long itemId, long cateProID,long valueID) 
			throws ManagerException {
		String id = ITEM_CUSTOM_PRO_VALUE_MEMCACHE_SUFFIX + itemId;
		Map<String, CustomProValueDO> map = null;
		try {
			map = itemMemcachedClient.get(id);
			if (map == null) {
				List<CustomProValueDO> list = customProValueManager.selectItemCustomProValueList(itemId);
				map = new HashMap<String, CustomProValueDO>();
				for (CustomProValueDO customProValueDO : list) {
					String twoId = String.valueOf(customProValueDO.getCategoryPropertyId())
							+ ":" + String.valueOf(customProValueDO.getCateProValueId());
					map.put(twoId, customProValueDO);
				}
				itemMemcachedClient.set(id, ITEM_CUSTOM_PRO_VALUE_MEMCACHE_CACHETIME, map);
			}
		} catch (TimeoutException e) {
			log.error(
					"event=[CategoryCacheManagerImpl#getCustomProValueDO]connect memcache server time out,itemId="
							+ itemId, e);
		} catch (InterruptedException e) {
			log.error(
					"event=[CategoryCacheManagerImpl#getCustomProValueDO]memcache thread Interrupted,itemId="
							+ itemId, e);
		} catch (MemcachedException e) {
			log.error(
					"event=[CategoryCacheManagerImpl#getCustomProValueDO]MemcachedException,itemId="
							+ itemId, e);
		}
		String twoIdget = String.valueOf(cateProID) + ":" + String.valueOf(valueID);
		return map == null ? null : map.get(twoIdget);
	}

	@Override
	public boolean deleteCustomProValueDO(long itemId)
			throws ManagerException {
		String id = ITEM_CUSTOM_PRO_VALUE_MEMCACHE_SUFFIX + itemId;
		try {
			return itemMemcachedClient.delete(id);
		} catch (TimeoutException e) {
			log.error(
					"event=[CategoryCacheManagerImpl#deleteCustomProValueDO]connect memcache server time out,itemId="
							+ itemId, e);
		} catch (InterruptedException e) {
			log.error(
					"event=[CategoryCacheManagerImpl#deleteCustomProValueDO]memcache thread Interrupted,itemId="
							+ itemId, e);
		} catch (MemcachedException e) {
			log.error(
					"event=[CategoryCacheManagerImpl#deleteCustomProValueDO]MemcachedException,itemId="
							+ itemId, e);
		}
		return Boolean.FALSE;
	}
	
	public void destroy() {
		try {
			itemMemcachedClient.shutdown();
			if (log.isInfoEnabled()) {
				log.info("event=[CategoryCacheManagerImpl#destroy]shutdown MemcachedClient sucess");
			}
		} catch (IOException e) {
			log.error("destroy MemcachedClient  ERROR", e);
		}
	}

	/**
	 * @param itemManager the itemManager to set
	 */
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	/**
	 * @param skuManager the skuManager to set
	 */
	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	/**
	 * @param itemPicManager the itemPicManager to set
	 */
	public void setItemPicManager(ItemPicManager itemPicManager) {
		this.itemPicManager = itemPicManager;
	}

	/**
	 * @param itemMemcachedClient the itemMemcachedClient to set
	 */
	public void setItemMemcachedClient(MemcachedClient itemMemcachedClient) {
		this.itemMemcachedClient = itemMemcachedClient;
	}

	/**
	 * @param activityDiscountManager the activityDiscountManager to set
	 */
	public void setActivityDiscountManager(
			ActivityDiscountManager activityDiscountManager) {
		this.activityDiscountManager = activityDiscountManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

	/**
	 * @param categoryCacheServer
	 *            the categoryCacheServer to set
	 */
	public void setCategoryCacheServer(CategoryCacheServer categoryCacheServer) {
		this.categoryCacheServer = categoryCacheServer;
	}

	public void setCustomProValueManager(
			CustomCateProValueManager customProValueManager) {
		this.customProValueManager = customProValueManager;
	}


}
