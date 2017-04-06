/**
 * 
 */
package com.yuwang.pinju.domain.item;

import java.util.List;
import java.util.Map;

/**
 * @Project: pinju-model
 * @Title: SearchCategoryQuery.java
 * @Package com.yuwang.pinju.domain.item
 * @Description: 搜索类目查询结果集
 * @author liuboen liuboen@zba.com
 * @date 2011-8-31 下午02:05:13
 * @version V1.0
 */

public class SearchCategoryResult {

	private long oneLevelCateId;

	private long twoLevelCateId;

	private long threeLevelCateId;

	private long fourLevleCateId;

	private String oneLevelCateName;

	private String twoLevelCateName;

	private String threeLevelCateName;

	private String fourLevleCateName;

	private Map<Long, List<CategoryDO>> cateMap;

	/**
	 * 搜索列表list
	 */
	private List<CategoryDO> cateList;

	/**
	 * 当前来源 1、 首页过来 2、搜索列表list
	 */
	private int currentFrom;

	/**
	 * @return the cateMap
	 */
	public Map<Long, List<CategoryDO>> getCateMap() {
		return cateMap;
	}

	/**
	 * @param cateMap
	 *            the cateMap to set
	 */
	public void setCateMap(Map<Long, List<CategoryDO>> cateMap) {
		this.cateMap = cateMap;
	}

	/**
	 * @return the currentFrom
	 */
	public int getCurrentFrom() {
		return currentFrom;
	}

	/**
	 * @param currentFrom
	 *            the currentFrom to set
	 */
	public void setCurrentFrom(int currentFrom) {
		this.currentFrom = currentFrom;
	}

	/**
	 * @return the cateList
	 */
	public List<CategoryDO> getCateList() {
		return cateList;
	}

	/**
	 * @param cateList
	 *            the cateList to set
	 */
	public void setCateList(List<CategoryDO> cateList) {
		this.cateList = cateList;
	}

	/**
	 * @return the oneLevelCateId
	 */
	public long getOneLevelCateId() {
		return oneLevelCateId;
	}

	/**
	 * @param oneLevelCateId
	 *            the oneLevelCateId to set
	 */
	public void setOneLevelCateId(long oneLevelCateId) {
		this.oneLevelCateId = oneLevelCateId;
	}

	/**
	 * @return the twoLevelCateId
	 */
	public long getTwoLevelCateId() {
		return twoLevelCateId;
	}

	/**
	 * @param twoLevelCateId
	 *            the twoLevelCateId to set
	 */
	public void setTwoLevelCateId(long twoLevelCateId) {
		this.twoLevelCateId = twoLevelCateId;
	}

	/**
	 * @return the threeLevelCateId
	 */
	public long getThreeLevelCateId() {
		return threeLevelCateId;
	}

	/**
	 * @param threeLevelCateId
	 *            the threeLevelCateId to set
	 */
	public void setThreeLevelCateId(long threeLevelCateId) {
		this.threeLevelCateId = threeLevelCateId;
	}

	/**
	 * @return the fourLevleCateId
	 */
	public long getFourLevleCateId() {
		return fourLevleCateId;
	}

	/**
	 * @param fourLevleCateId
	 *            the fourLevleCateId to set
	 */
	public void setFourLevleCateId(long fourLevleCateId) {
		this.fourLevleCateId = fourLevleCateId;
	}

	public String getOneLevelCateName() {
		return oneLevelCateName;
	}

	public void setOneLevelCateName(String oneLevelCateName) {
		this.oneLevelCateName = oneLevelCateName;
	}

	public String getTwoLevelCateName() {
		return twoLevelCateName;
	}

	public void setTwoLevelCateName(String twoLevelCateName) {
		this.twoLevelCateName = twoLevelCateName;
	}

	public String getThreeLevelCateName() {
		return threeLevelCateName;
	}

	public void setThreeLevelCateName(String threeLevelCateName) {
		this.threeLevelCateName = threeLevelCateName;
	}

	public String getFourLevleCateName() {
		return fourLevleCateName;
	}

	public void setFourLevleCateName(String thisCateName) {
		this.fourLevleCateName = thisCateName;
	}
	
	

}
