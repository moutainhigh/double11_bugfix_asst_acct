/**
 * 
 */
package com.yuwang.pinju.biz.cate;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.domain.item.BaseValueDO;

/**  
 * @Project: pinju-biz
 * @Title: CategoryCacheTest.java
 * @Package com.yuwang.pinju.biz.cate
 * @Description: 类目\类目属性\类目属性值,缓存单元测试
 * @author liuboen liuboen@zba.com
 * @date 2011-6-29 下午04:23:15
 * @version V1.0  
 */

public class CategoryCacheTest extends BaseTestCase {
	@SpringBean("categoryCacheManager")
	private CategoryCacheManager categoryCacheManager;
	
	public void testGetBaseValue() {
		try {
			BaseValueDO  basevaluedo=categoryCacheManager.getBaseValueById(23l);
			System.out.println(basevaluedo.getValue());
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
}
