/**
 * 
 */
package com.yuwang.pinju.biz.activity;

import java.util.Date;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.active.dao.ActivityDiscountStatDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.active.ActivityDiscountStatDO;

/**  
 * @Project: pinju-biz
 * @Title: DiscountStatDAOTest.java
 * @Package com.yuwang.pinju.biz.activity
 * @Description: 限时折扣统计
 * @author liuboen liuboen@zba.com
 * @date 2011-7-28 下午04:17:48
 * @version V1.0  
 */

public class DiscountStatDAOTest extends BaseTestCase {
	@SpringBean("activityDiscountStatDAO")
	ActivityDiscountStatDAO activityDiscountStatDAO;
	
	public void ignortestInsetStat(){
		ActivityDiscountStatDO activityDiscountStatDO=new ActivityDiscountStatDO();
		activityDiscountStatDO.setGmtCreate(new Date());
		activityDiscountStatDO.setGmtModified(new Date());
		activityDiscountStatDO.setMemberId(123l);
		activityDiscountStatDO.setMemberNick("test");
		activityDiscountStatDO.setStatDate(201107l);
		activityDiscountStatDO.setUsedNum(20l);
		activityDiscountStatDO.setUsedTime(154l);
		activityDiscountStatDO.setVersion(0l);
		try {
			activityDiscountStatDAO.insertActivityDiscountStat(activityDiscountStatDO);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	public void ignoretestgetStatByid(){
		
		try {
			ActivityDiscountStatDO activityDiscountStatDO =activityDiscountStatDAO.selectActivityDiscountStatById(1l);
			System.out.println(activityDiscountStatDO.toString());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	public void ignoretestActivityDiscountStatByMemberId(){
		
		try {
			ActivityDiscountStatDO activityDiscountStatDO =activityDiscountStatDAO.selectActivityDiscountStatByMemberId(123l,201107l);
			System.out.println(activityDiscountStatDO.getMemberNick());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	public void testActivityDiscountStatByMemberId(){
		
		try {
			System.out.println(activityDiscountStatDAO.updateActivityDiscountStat(20l, 15l, 123l, 201107l, 0));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
}
