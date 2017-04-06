/**
 * 
 */
package com.yuwang.pinju.core.util.filter;

import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;

/**
 * 敏感词
 * 
 * @author caiwei
 * @since 2011-09-27
 * 
 */
public class WordFilterFacade extends WordFilterService {

    /**
     * 过滤文本中的敏感词
     * 
     * @param content
     * 		内容文本
     * @param type
     * 		过滤类型[com.yuwang.crm.constant.word.SensitiveWordConstants]
     * @return
     */
    public static FilteredResult filter(String content, Integer type){
	return filter(content, type, "");
    }
    
    /**
     * 过滤文本中的敏感词，并用设置的符号替换
     * 
     * @param content
     * 		内容文本
     * @param type
     * 		过滤类型[com.yuwang.crm.constant.word.SensitiveWordConstants]
     * @param replacement
     * 		敏感词替换符号[' '为不替换]
     * @return
     */
    public static FilteredResult filter(String content, Integer type, String replacement){
	if (ObjectUtils.nullSafeEquals(type, SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON)) {
	    return filterText(content, (StringUtils.hasText(replacement))?replacement.charAt(0):' ', type);
	}else {
	    return filterTextWithCommonWords(content, (StringUtils.hasText(replacement))?replacement.charAt(0):' ', type);
	}
    }
    
    /**
     * 过滤文本中的敏感词，可以处理文本中包含的HTML标签
     * 
     * @param content
     * 		内容文本
     * @param type
     * 		过滤类型[com.yuwang.crm.constant.word.SensitiveWordConstants]
     * @param containedHTML
     * 		是否处理文本中包含的HTML标签[true:处理、false:不处理]
     * @return
     */
    public static FilteredResult filter(String content, Integer type, Boolean containedHTML) {
	return filter(content, type, null, containedHTML);
    }
    
    /**
     * 过滤文本中的敏感词，可以处理文本中包含的HTML标签，并用设置的符号替换
     * 
     * @param content
     * 		内容文本
     * @param type
     * 		过滤类型[com.yuwang.crm.constant.word.SensitiveWordConstants]
     * @param replacement
     * 		敏感词替换符号[' '为不替换]
     * @param containedHTML
     * 		是否处理文本中包含的HTML标签[true:处理、false:不处理]
     * @return
     */
    public static FilteredResult filter(String content, Integer type, String replacement, Boolean containedHTML){
	if (BooleanUtils.isTrue(containedHTML)) {
	    if (ObjectUtils.nullSafeEquals(type, SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON)) {
		return filterHtml(content, (StringUtils.hasText(replacement))?replacement.charAt(0):' ', type);
	    }else {
		return filterHtmlWithCommonWords(content, (StringUtils.hasText(replacement))?replacement.charAt(0):' ', type);
	    }
	}else {
	    return filter(content, type, replacement);
	}
    }
    
    /**
     * 敏感词检测
     * 
     * @param content
     * 		内容文本
     * @param type
     * 		敏感词类型[com.yuwang.crm.constant.word.SensitiveWordConstants]
     * @return
     * 		[true:包含敏感词、false:不包含敏感词]
     */
    public static Boolean scan(String content, Integer type){
	return isTextSensitiveWordWithCommonWords(content, type);
    }
    
    /**
     * 敏感词检测，并处理文本中包含的HTML标签
     * 
     * @param content
     * 		内容文本
     * @param type
     * 		敏感词类型[com.yuwang.crm.constant.word.SensitiveWordConstants]
     * @param containedHTML
     * 		是否处理文本中包含的HTML标签[true:处理、false:不处理]
     * @return
     * 		[true:包含敏感词、false:不包含敏感词]
     */
    public static Boolean scan(String content, Integer type, Boolean containedHTML){
		if (BooleanUtils.isTrue(containedHTML)) {
		    return isHtmlSensitiveWordWithCommonWords(content, type);
		}else {
		    return scan(content, type);
		}
    }
    
    /**
     * 敏感词检测(精确匹配)
     * 
     * @param content
     * 		内容文本
     * @param type
     * 		敏感词类型[com.yuwang.crm.constant.word.SensitiveWordConstants]
     * @param containedHTML
     * 		是否处理文本中包含的HTML标签[true:处理、false:不处理]
     * @param accuracy
     * 		是否精确匹配敏感词数据[true:处理、false:不处理]
     * @return
     * 		[true:包含敏感词、false:不包含敏感词]
     */
    public static Boolean accurateScan(String content, Integer type, Boolean containedHTML, Boolean accuracy){
    	if (BooleanUtils.isTrue(accuracy)) {
    		if (BooleanUtils.isTrue(containedHTML)) {
    		    return isAccuracyHtmlSensitiveWordWithCommonWords(content, type);
    		}else {
    		    return isAccuracyTextSensitiveWordWithCommonWords(content, type);
    		}
		}else {
			return scan(content, type, containedHTML);
		}
    }
    
    /**
     * 获取各类型敏感词数量
     * 
     * @param type
     * 		敏感词类型[com.yuwang.crm.constant.word.SensitiveWordConstants]
     * @return
     */
    public static Integer capacity(Integer type){
	if (ObjectUtils.nullSafeEquals(type, SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON)) {
	    return getSensitiveWordCountByType(type);
	}else {
	    return getSensitiveWordAndCommonWordCountByType(type);
	}
    }
    
    /**
     * 初始化敏感词库
     * 
     * @param wordSet
     * 		敏感词列表
     * @param type
     * 		敏感词所属类型
     */
    public static void init(Set<String> wordSet, Integer type){
	initSensitiveWord(wordSet, type);
    }
}
