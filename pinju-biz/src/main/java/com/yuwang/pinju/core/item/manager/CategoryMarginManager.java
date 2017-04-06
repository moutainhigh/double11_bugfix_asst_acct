package com.yuwang.pinju.core.item.manager;

import com.yuwang.pinju.core.common.ManagerException;

/**
 * 类目保证金manager
 * 
 * @author gongjiayun
 * 
 */
public interface CategoryMarginManager {

	/**
	 * 根据类目ID查找类目保证金
	 * 
	 * @param categoryId
	 *            类目id
	 * @return
	 * @throws ManagerException
	 */
	//public int getItemMargin(Long categoryId) throws ManagerException;
	
	public int getItemMargin(Long categoryId, int sellerType) throws ManagerException;

}
