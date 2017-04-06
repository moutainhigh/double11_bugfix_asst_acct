package com.yuwang.pinju.core.util.filter;

import java.io.Serializable;

/**
 * 敏感词过滤返回结果 
 * @author zwm
 *
 */
public class FilteredResult implements Serializable {

	/**
	 * 过滤后返回的文本内容
	 */
	private String filteredContent;
	/**
	 * 敏感词多个以逗号分隔
	 */
	private String badWords;
	/**
	 * 原始文本内容
	 */
	private String originalContent;
	/**
	 * 文本内容是否包含敏感词
	 * 	[true:包含敏感词、false:不包含敏感词]
	 */
	private boolean badWord;

	public FilteredResult() {

	}
	
	public FilteredResult(boolean badWords) {
         this.badWord = badWords;
	}

	public FilteredResult(String originalContent, String filteredContent,
			String badWords) {
		this.originalContent = originalContent;
		this.filteredContent = filteredContent;
		this.badWords = badWords;
	}
	
	/**
	 * 判断是否有敏感词
	 * @return
	 * 	[true:包含敏感词、false:不包含敏感词]
	 */
	public boolean isBadWord() {
		return this.badWord;
	}

	public void setBadWord(boolean badWord) {
		this.badWord = badWord;
	}

	public String getBadWords() {
		return this.badWords;
	}

	public void setBadWords(String badWords) {
		if (badWords != null && !"".equals(badWords)) {
			setBadWord(true);
		} else {
			setBadWord(false);
		}
		this.badWords = badWords;
	}

	public String getFilteredContent() {
		return this.filteredContent;
	}

	public void setFilteredContent(String filteredContent) {
		this.filteredContent = filteredContent;
	}

	public String getOriginalContent() {
		return this.originalContent;
	}

	public void setOriginalContent(String originalContent) {
		this.originalContent = originalContent;
	}
}
