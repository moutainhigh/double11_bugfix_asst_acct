/**
 * 
 */
package com.yuwang.pinju.core.item.service;

import com.yuwang.pinju.domain.item.ForestDO;


/**  
 * @Project: pinju-biz
 * @Title: CategoryCacheServer.java
 * @Package com.yuwang.pinju.core.item.manager
 * @Description: 缓存服务
 * @author liuboen liuboen@zba.com
 * @date 2011-6-24 上午11:13:44
 * @version V1.0  
 */

public interface CategoryCacheServer {
	/**
	 * 加载所有类目
	 * @return
	 */
	public boolean loadFullCategory();
	
	/**
	 * 数据库加载所有类目
	 * @return
	 */
	public boolean loadFullCategoryDB();
	/**
	 * 重置内存和本地的类目库
	 * @return
	 */
	public boolean resetFullCategory();
	
	/**
	 * 获取本地类目树
	 * @return
	 */
	public ForestDO getForestDO(); 
	
	/**
	 * 增量更新
	 * @return
	 */
	public boolean incrLoadCategory();
}
