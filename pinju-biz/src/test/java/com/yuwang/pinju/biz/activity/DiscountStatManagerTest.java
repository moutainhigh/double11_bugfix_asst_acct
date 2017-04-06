/**
 * 
 */
package com.yuwang.pinju.biz.activity;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.active.manager.ActivityDiscountStatManager;
import com.yuwang.pinju.core.common.ManagerException;

/**  
 * @Project: pinju-biz
 * @Title: DiscountStatManagerTest.java
 * @Package com.yuwang.pinju.biz.activity
 * @Description: manager测试类
 * @author liuboen liuboen@zba.com
 * @date 2011-7-29 下午12:53:14
 * @version V1.0  
 */

public class DiscountStatManagerTest extends BaseTestCase {
	
	@SpringBean("activityDiscountStatManager")
	ActivityDiscountStatManager activityDiscountStatManager;
	public void testStat(){
		try {
			activityDiscountStatManager.stat(123, "test",30, 1);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
	
}
