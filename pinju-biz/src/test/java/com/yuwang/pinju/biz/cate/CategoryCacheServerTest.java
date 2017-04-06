/**
 * 
 */
package com.yuwang.pinju.biz.cate;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.item.service.CategoryCacheServer;

/**  
 * @Project: pinju-biz
 * @Title: CategoryCacheServerTest.java
 * @Package com.yuwang.pinju.biz.cate
 * @Description: 类目缓存
 * @author liuboen liuboen@zba.com
 * @date 2011-6-24 下午02:06:39
 * @version V1.0  
 */

public class CategoryCacheServerTest  extends BaseTestCase {

	@SpringBean("categoryCacheServer")
	private CategoryCacheServer categoryCacheServer;
	
	public void testDOCache(){
		categoryCacheServer.resetFullCategory();
	}
}
