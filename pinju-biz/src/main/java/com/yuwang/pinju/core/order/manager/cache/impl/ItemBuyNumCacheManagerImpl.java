package com.yuwang.pinju.core.order.manager.cache.impl;


import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.core.order.manager.cache.ItemBuyNumCacheManager;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;

/**
 * Created on 2011-10-8
 * 
 * @see <p>
 *      Discription: [订单相关缓存管理]
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class ItemBuyNumCacheManagerImpl extends OrderCacheBaseManager implements
		ItemBuyNumCacheManager {

	private MemcachedClient itemBuyNumMemcachedClient;

	private OrderQueryDAO orderQueryDAO;
	
	private String CACHE_ITEM_BUY_NUM_KEY = "item_buy_num_ids.";
	//缓存时间
	private final static int ITEM_BUY_NUM_MEMCACHE_CACHETIME = 60*60*24*15;  
	
	
	
	
	@Override
	public Long queryCacheItemBuyNum(QueryOrderItem queryOrderItem) throws ManagerException {
		String buyNum = "0";
		if (queryOrderItem == null || queryOrderItem.getItemId().compareTo(0l)==0) {
			log.error("查询对象参数异常");
			return 0l;
		}
		try {
			//获取key名称
			String cacheKey = super.queryKey(String.valueOf(queryOrderItem
					.getItemId()));
			buyNum = itemBuyNumMemcachedClient.get(cacheKey);
			
			if(StringUtil.isEmpty(buyNum)||StringUtil.equalsIgnoreCase(buyNum, "0")){
				//查实时并放入缓存
				long num = getItemBuyNum(queryOrderItem);
				buyNum = String.valueOf(num);
				itemBuyNumMemcachedClient.incr(super.queryKey(String.valueOf(queryOrderItem.getItemId())) ,num,0,ITEM_BUY_NUM_MEMCACHE_CACHETIME);
			}
		} catch (TimeoutException e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存超时错误TimeoutException:" +  e);
		} catch (InterruptedException e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存错误InterruptedException:" +  e);
		} catch (MemcachedException e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存错误 MemcachedException:" +  e);
		}catch (DaoException e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存错误DaoException:" +  e);
		}
		catch (Exception e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存错误Exception:" +  e);
		}
		if(NumberUtil.isLong(buyNum))
			return Long.parseLong(buyNum);
		return 0l;
	}
	
	private Long getItemBuyNum(QueryOrderItem queryOrderItem) throws DaoException{
		Long buyNum = orderQueryDAO.queryOrderItemBuyNum(queryOrderItem);
		return buyNum;
	}
	
	@Override
	protected String getPrefix() {
		return CACHE_ITEM_BUY_NUM_KEY;
	}

	public void setItemBuyNumMemcachedClient(
			MemcachedClient itemBuyNumMemcachedClient) {
		this.itemBuyNumMemcachedClient = itemBuyNumMemcachedClient;
	}

	public void setOrderQueryDAO(OrderQueryDAO orderQueryDAO) {
		this.orderQueryDAO = orderQueryDAO;
	}

	@Override
	public boolean setCacheItemBuyNum(Long itemId, Long buyNum) {
		boolean flag = false;
		if(itemId==null||buyNum==null){
			return false;
		}
		try {
			String cacheKey = super.queryKey(String.valueOf(itemId));
			itemBuyNumMemcachedClient.incr(cacheKey,buyNum.longValue());
			flag = true;
		} catch (TimeoutException e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存超时错误TimeoutException:" +  e);
		} catch (InterruptedException e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存错误InterruptedException:" +  e);
		} catch (MemcachedException e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存错误 MemcachedException:" +  e);
		}
		catch (Exception e) {
			log.error("Event=[ItemBuyNumCacheManagerImpl#queryCacheItemBuyNum] 查询商品购买记录缓存错误Exception:" +  e);
		}
		return flag;
	}

	
}
