package com.yuwang.pinju.core.order.manager.cache.impl;

import java.util.Map;

import com.yuwang.pinju.core.common.BaseManager;

/**
 * Created on 2011-10-8
 * 
 * @see <p>
 *      Discription: [订单相关缓存基础管理]
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public abstract class OrderCacheBaseManager extends BaseManager {

	/**
	 * 
	 * Created on 2011-10-9
	 * <p>
	 * Discription: 获取缓存对应的key
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected String queryKey(String id) {
		return getPrefix().concat(id);
	}

	
	/**
	 * 
	 * Created on 2011-10-9
	 * <p>
	 * Discription: 获取缓存key前缀
	 * </p>
	 * 
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected abstract String getPrefix();

	/**
	 * 
	 * Created on 2011-10-9
	 * <p>
	 * Discription: [增量更新,非并发环境下使用]
	 * </p>
	 * 
	 * @param cacheKey
	 * @param map
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected boolean incrLoad(String cacheKey, Map<Long, Long> map) {
		return false;
	}

	/**
	 * 
	 * Created on 2011-10-9
	 * <p>
	 * Discription: [全部更新,数据库]
	 * </p>
	 * 
	 * @param cacheKey
	 * @param map
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected boolean loadFullDB(String cacheKey, Map<Long, Long> map) {
		return false;
	}
}
