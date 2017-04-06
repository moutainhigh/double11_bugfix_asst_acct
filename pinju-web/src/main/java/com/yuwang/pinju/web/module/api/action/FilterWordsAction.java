/**
 * 
 */
package com.yuwang.pinju.web.module.api.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
import com.yuwang.pinju.core.util.filter.FilteredResult;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;

/**
 * @author zba
 *
 */
public class FilterWordsAction extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 392263044601918716L;

    private String content;
    
    private Integer type;
    
    private Boolean withCommonWords;
    
    private String replace;
    
    /**
     * 返回结果
     */
    private Map<String, Object> result = new HashMap<String, Object>();;
	
    public String filter(){
	FilteredResult result = null;
	if (StringUtils.hasText(this.content)) {
	    result = WordFilterFacade.filter(this.content, (this.type != null?this.type:SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON), this.replace);
	}
	populateResult(result);
	return SUCCESS;
    }

    private void populateResult(FilteredResult filteredResult) {
	if (filteredResult != null) {
	    this.result.put("sensitiveWords", filteredResult.getBadWords());
	    this.result.put("filtered", filteredResult.getFilteredContent());
	    this.result.put("content", filteredResult.getOriginalContent());
	    this.result.put("hadWords", filteredResult.isBadWord());
	}
    }
    
    /**
     * @param content the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
	this.type = type;
    }

    /**
     * @param withCommonWords the withCommonWords to set
     */
    public void setWithCommonWords(Boolean withCommonWords) {
	this.withCommonWords = withCommonWords;
    }

    /**
     * @return the result
     */
    public Map<String, Object> getResult() {
	return result;
    }

    /**
     * @param replace the replace to set
     */
    public void setReplace(String replace) {
	this.replace = replace;
    }

}
