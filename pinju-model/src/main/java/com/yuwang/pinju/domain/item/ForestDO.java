/**
 * 
 */
package com.yuwang.pinju.domain.item;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**  
 * @Project: pinju-model
 * @Title: ForestDO.java
 * @Package com.yuwang.pinju.domain.item
 * @Description: 缓存类目DO
 * @author liuboen liuboen@zba.com
 * @date 2011-6-24 上午11:32:00
 * @version V1.0  
 */

public class ForestDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 918008915403903261L;

	private Map<Long, CategoryDO> fullCategories =null;

	/**根类目列表*/
	private List<CategoryDO> rootCategories = null;
	
	private Date lastIncrUpdate=null;
	/**
	 * @return the fullCategories
	 */
	public Map<Long, CategoryDO> getFullCategories() {
		return fullCategories;
	}

	/**
	 * @param fullCategories the fullCategories to set
	 */
	public void setFullCategories(Map<Long, CategoryDO> fullCategories) {
		this.fullCategories = fullCategories;
	}

	/**
	 * @return the rootCategories
	 */
	public List<CategoryDO> getRootCategories() {
		return rootCategories;
	}

	/**
	 * @param rootCategories the rootCategories to set
	 */
	public void setRootCategories(List<CategoryDO> rootCategories) {
		this.rootCategories = rootCategories;
	}

	/**
	 * @return the lastIncrUpdate
	 */
	public Date getLastIncrUpdate() {
		return lastIncrUpdate;
	}

	/**
	 * @param lastIncrUpdate the lastIncrUpdate to set
	 */
	public void setLastIncrUpdate(Date lastIncrUpdate) {
		this.lastIncrUpdate = lastIncrUpdate;
	}


	
}
