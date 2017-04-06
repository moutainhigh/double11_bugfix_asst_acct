package com.yuwang.pinju.core.util.filter;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.util.StringUtils;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
/**
 * 敏感词过滤服务类
 * @author zwm
 *
 */
public class WordFilterService {

	private static final String SPACE_HTML = "&NBSP;";
	
	private static final String EMPTY_STRING = "";
	
	private static ConcurrentMap<Integer, Node> sensitiveWordMap = new ConcurrentHashMap<Integer, Node>();
	private static ConcurrentMap<Integer, Integer> sensitiveWordSizeMap = new ConcurrentHashMap<Integer, Integer>();
	
	private static Boolean filterLatch = true;
	
	private static void insertWord(Node node,String word) {
	    for (int i = 0; i < word.length(); i++) {
	      node = node.addChar(word.charAt(i));
	    }
	    node.setEnd(true);
}
	/**
	 * 判断敏感词中间包含的字符
	 * @param c
	 * @return
	 */
	private static boolean isPunctuationChar(char c) {
	    return Character.isSpaceChar(c);
	}

	/**
	 * 过滤文本的标点
	 * @param originalString
	 * @return
	 */
	private static PunctuationOrHtmlFilteredResult filterPunctation(String originalString) {
	    StringBuffer filteredString = new StringBuffer();
	    ArrayList<Integer> charOffsets = new ArrayList<Integer>();
	    for (int i = 0; i < originalString.length(); i++) {
	      String c = String.valueOf(originalString.charAt(i)).toUpperCase();
	      if (!isPunctuationChar(originalString.charAt(i))) {
	        filteredString.append(c);
	        charOffsets.add(Integer.valueOf(i));
	      }
	    }
	    PunctuationOrHtmlFilteredResult result = new PunctuationOrHtmlFilteredResult();
	    result.setOriginalString(originalString);
	    result.setFilteredString(filteredString);
	    result.setCharOffsets(charOffsets);
	    return result;
	}
	
	private static PunctuationOrHtmlFilteredResult filterPunctationAndHtml(String originalString) {
		    StringBuffer filteredString = new StringBuffer();
		    ArrayList<Integer> charOffsets = new ArrayList<Integer>();
		    int i = 0; for (int k = 0; i < originalString.length(); i++) {
		      String c = String.valueOf(originalString.charAt(i)).toUpperCase();
		      if (originalString.charAt(i) == '<') {
		        for (k = i + 1; k < originalString.length(); k++) {
		          if (originalString.charAt(k) == '<') {
		            k = i;
		            break;
		          }
		          if (originalString.charAt(k) == '>') {
		            break;
		          }
		        }
		        i = k;
		      }
		      else if (!isPunctuationChar(originalString.charAt(i))) {
		        filteredString.append(c);
		        charOffsets.add(Integer.valueOf(i));
		      }
		    }
		
		    PunctuationOrHtmlFilteredResult result = new PunctuationOrHtmlFilteredResult();
		    result.setOriginalString(originalString);
		    result.setFilteredString(filteredString);
		    result.setCharOffsets(charOffsets);
		    return result;
	  }
	
	/**
	 * 判断内容是否包含敏感词
	 * @param pohResult
	 * @return
	 */
	 private static boolean isExistSensitiveWord(PunctuationOrHtmlFilteredResult pohResult, Node sensitiveNode) {
	      if (sensitiveNode == null) {
	    	  return false;
	      }
	      StringBuffer sentence = pohResult.getFilteredString();
	      Node node = null;
		  int start = 0; int end = 0;
		  for (int i = 0; i < sentence.length(); i++) {
		      start = i;
		      end = i;
		      node = sensitiveNode;
		      for (int j = i; j < sentence.length(); j++) {
		        node = node.findChar(sentence.charAt(j));
		        if (node == null)
		              break;
		        if (node.isEnd()) 
		             end = j;
		      }
		      if (end > start) {
		    	  return true;
		      }
		   }
		  return false;
	  }
	 
	 /**
	  * 判断内容是否包含敏感词
	  * @param pohResult
	  * @return
	  */
	 private static boolean isAccuracySensitiveWord(PunctuationOrHtmlFilteredResult pohResult, Node sensitiveNode) {
		 if (sensitiveNode == null) {
			 return false;
		 }
		 StringBuffer sentence = pohResult.getFilteredString();
		 Node node = null;
		 int end=sentence.length();
		 node = sensitiveNode;
		 for (int j = 0; j < end; j++) {
			 node = node.findChar(sentence.charAt(j));
			 if (node == null)
				 break;
			 if (BooleanUtils.isTrue(node.isEnd()) && (end == (j+1))) {
				 return true;
			 }
		 }
		 return false;
	 }
	 
	/**
	 * 过滤文件内容
	 * @param pohResult
	 * @param replacement
	 * @return
	 */
	  private static FilteredResult filter(PunctuationOrHtmlFilteredResult pohResult, char replacement, Node sensitiveNode) {
		    FilteredResult result = new FilteredResult();
		    if (sensitiveNode == null) {
		    	result.setBadWord(false);
		    	return result;
		    }
		    StringBuffer sentence = pohResult.getFilteredString();
		    ArrayList<Integer> charOffsets = pohResult.getCharOffsets();
		    StringBuffer resultString = new StringBuffer(pohResult.getOriginalString());
		    StringBuffer badWords = new StringBuffer();
		   
		    Node node = null;
		    int start = 0; int end = 0;
		    for (int i = 0; i < sentence.length(); i++) {
		      start = i;
		      end = i;
		      node = sensitiveNode;
		      for (int j = i; j < sentence.length(); j++) {
		        node = node.findChar(sentence.charAt(j));
		        if (node == null) {
		          break;
		        }
		        if (node.isEnd()) {
		          end = j;
		        }
		      }
		      if (end > start) {
		    	  if (replacement != ' ') {
		    		  for (int k = start; k <= end; k++) {
				          resultString.setCharAt((charOffsets.get(k)).intValue(), replacement);
				      }
		    	  }
		          if (badWords.length() > 0) badWords.append(",");
		          badWords.append(sentence.substring(start, end + 1));
		          i = end;
		       }
		    }
		    result.setOriginalContent(pohResult.getOriginalString());
		    result.setFilteredContent(resultString.toString());
		    result.setBadWords(badWords.toString());
		    return result;
	  }
		
	 private static FilteredResult blendFilteredResult(FilteredResult filteredResult, FilteredResult anotherFilteredResult){
	     	FilteredResult result = new FilteredResult(); 
	     	if (filteredResult.isBadWord() || anotherFilteredResult.isBadWord()) {
	     	    String commonBadWords = ((anotherFilteredResult.getBadWords()!=null&&StringUtils.hasText(anotherFilteredResult.getBadWords()))?((filteredResult.getBadWords()!=null&&StringUtils.hasText(filteredResult.getBadWords()))?",":"")+anotherFilteredResult.getBadWords():"");
	     	    result.setBadWords(filteredResult.getBadWords()+commonBadWords);
	     	    result.setFilteredContent((filteredResult.isBadWord()?(StringUtils.hasText(filteredResult.getFilteredContent())?filteredResult.getFilteredContent():filteredResult.getOriginalContent()):(StringUtils.hasText(anotherFilteredResult.getFilteredContent())?anotherFilteredResult.getFilteredContent():anotherFilteredResult.getOriginalContent())));
	     	}else {
	     	    result.setFilteredContent(filteredResult.getOriginalContent());
	     	    result.setBadWords("");
	     	}
	     	result.setOriginalContent(filteredResult.getOriginalContent());
	     	return result;
	 }
	  
	 /**
	  * 过滤纯文本敏感词
	  * @param originalString
	  * @param replacement 
	  * @return
	  */
	 protected static FilteredResult filterText(String originalString, char replacement, Integer type) {
		 if (StringUtil.isNotEmpty(originalString)) {
			 return filter(filterPunctation(originalString), replacement, getSensitiveNode(type));
		 }
		 return new FilteredResult(false);
	 }
	 
	 protected static FilteredResult filterTextWithCommonWords(String originalString, char replacement, Integer type){
	     	if (StringUtil.isNotEmpty(originalString)) {
			 FilteredResult resultByType = filter(filterPunctation(originalString), replacement, getSensitiveNode(type));
			 FilteredResult commonWordResult = filter(filterPunctation(originalString), replacement, getSensitiveNode(SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON));
			 return blendFilteredResult(resultByType, commonWordResult);
		 }
		 return new FilteredResult(false);
	 }
	 
	 /**
	  * 过滤纯文本敏感词
	  * @param originalString
	  * @param replacement
	  * @return
	  */
	 protected static FilteredResult filterText(String originalString, Integer type) {
		    return filterText(originalString, ' ', type);
	 }
	 
	 /**
	  * 过滤包含HTML文本敏感词
	  * @param originalString
	  * @param replacement
	  * @return
	  */
	 protected static FilteredResult filterHtml(String originalString, char replacement, Integer type) {
		 if (StringUtil.isNotEmpty(originalString)) {
			 return filter(filterPunctationAndHtml(originalString), replacement, getSensitiveNode(type));
		 }
		 return new FilteredResult(false);
	 }
	 
	 protected static FilteredResult filterHtmlWithCommonWords(String originalString, char replacement, Integer type) {
	     if (StringUtil.isNotEmpty(originalString)) {
		 FilteredResult resultByType = filter(filterPunctationAndHtml(originalString), replacement, getSensitiveNode(type));
		 FilteredResult commonWordResult = filter(filterPunctationAndHtml(originalString), replacement, getSensitiveNode(SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON));
		 return blendFilteredResult(resultByType, commonWordResult);
	     }
	     return new FilteredResult(false);
	 }
	 
	 protected static FilteredResult filterHtml(String originalString, Integer type) {
		    return filterHtml(originalString, ' ', type);
	 }
	 
	 /**
	  * 判断纯文本内容是否包含敏感词
	  * @param originalString
	  * @return
	  */
	 protected static boolean isTextSensitiveWord(String originalString, Integer type) {
		 if (StringUtil.isNotEmpty(originalString)) {
			 return isExistSensitiveWord(filterPunctation(originalString), getSensitiveNode(type));
		 }
		 return false;
	 }
	 protected static boolean isTextSensitiveWordWithCommonWords(String originalString, Integer type) {
	     if (StringUtil.isNotEmpty(originalString)) {
		 return (isExistSensitiveWord(filterPunctation(originalString), getSensitiveNode(type)) || isExistSensitiveWord(filterPunctation(originalString.toUpperCase()), getSensitiveNode(SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON)));
	     }
	     return false;
	 }
	 
	 /**
	  * 判断有HTML标签的文本内容是否包含敏感词
	  * @param originalString
	  * @return
	  */
	 protected static boolean isHtmlSensitiveWord(String originalString, Integer type) {
		 if (StringUtil.isNotEmpty(originalString)) {
			 String originalUpperString = originalString.toUpperCase();
			 if (originalUpperString.indexOf(SPACE_HTML) != -1) {
				 originalUpperString = originalUpperString.replaceAll(SPACE_HTML, EMPTY_STRING);
			 }
			 return isExistSensitiveWord(filterPunctationAndHtml(originalUpperString), getSensitiveNode(type));
		 }
		 return false;
	 }
	 
	 protected static boolean isHtmlSensitiveWordWithCommonWords(String originalString, Integer type) {
	     if (StringUtil.isNotEmpty(originalString)) {
		 String originalUpperString = originalString.toUpperCase();
		 if (originalUpperString.indexOf(SPACE_HTML) != -1) {
		     originalUpperString = originalUpperString.replaceAll(SPACE_HTML, EMPTY_STRING);
		 }
		 return (isExistSensitiveWord(filterPunctationAndHtml(originalUpperString), getSensitiveNode(type)) || isExistSensitiveWord(filterPunctationAndHtml(originalUpperString), getSensitiveNode(SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON)));
	     }
	     return false;
	 }
	 
	 /**
	  * 判断纯文本内容是否包含敏感词
	  * @param originalString
	  * @return
	  */
	 protected static boolean isAccuracyTextSensitiveWord(String originalString, Integer type) {
		 if (StringUtil.isNotEmpty(originalString)) {
			 return isAccuracySensitiveWord(filterPunctation(originalString), getSensitiveNode(type));
		 }
		 return false;
	 }
	 protected static boolean isAccuracyTextSensitiveWordWithCommonWords(String originalString, Integer type) {
		 if (StringUtil.isNotEmpty(originalString)) {
			 return (isAccuracySensitiveWord(filterPunctation(originalString), getSensitiveNode(type)) || isAccuracySensitiveWord(filterPunctation(originalString.toUpperCase()), getSensitiveNode(SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON)));
		 }
		 return false;
	 }
	 
	 /**
	  * 判断有HTML标签的文本内容是否包含敏感词
	  * @param originalString
	  * @return
	  */
	 protected static boolean isAccuracyHtmlSensitiveWord(String originalString, Integer type) {
		 if (StringUtil.isNotEmpty(originalString)) {
			 String originalUpperString = originalString.toUpperCase();
			 if (originalUpperString.indexOf(SPACE_HTML) != -1) {
				 originalUpperString = originalUpperString.replaceAll(SPACE_HTML, EMPTY_STRING);
			 }
			 return isAccuracySensitiveWord(filterPunctationAndHtml(originalUpperString), getSensitiveNode(type));
		 }
		 return false;
	 }
	 
	 protected static boolean isAccuracyHtmlSensitiveWordWithCommonWords(String originalString, Integer type) {
		 if (StringUtil.isNotEmpty(originalString)) {
			 String originalUpperString = originalString.toUpperCase();
			 if (originalUpperString.indexOf(SPACE_HTML) != -1) {
				 originalUpperString = originalUpperString.replaceAll(SPACE_HTML, EMPTY_STRING);
			 }
			 return (isAccuracySensitiveWord(filterPunctationAndHtml(originalUpperString), getSensitiveNode(type)) || isAccuracySensitiveWord(filterPunctationAndHtml(originalUpperString), getSensitiveNode(SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON)));
		 }
		 return false;
	 }
	 
	 private static class PunctuationOrHtmlFilteredResult {
	    private String originalString;
	    private StringBuffer filteredString;
	    private ArrayList<Integer> charOffsets;
	
	    public String getOriginalString() {
	      return this.originalString;
	    }
	    public void setOriginalString(String originalString) {
	      this.originalString = originalString;
	    }
	    public StringBuffer getFilteredString() {
	      return this.filteredString;
	    }
	    public void setFilteredString(StringBuffer filteredString) {
	      this.filteredString = filteredString;
	    }
	    public ArrayList<Integer> getCharOffsets() {
	      return this.charOffsets;
	    }
	    public void setCharOffsets(ArrayList<Integer> charOffsets) {
	      this.charOffsets = charOffsets;
	    }
	}
	 
	 private static Node initSensitiveWordNode(Set<String> sensitiveWordSet) {
		 Node node = new Node();
	      for (String sensitiveWord : sensitiveWordSet) {
	        insertWord(node, sensitiveWord);
	      }
	      return node;
	}

	/**
	 * 初始化敏感词
	 * @param sensitiveWordSet
	 */
	protected static void initSensitiveWord(Set<String> sensitiveWordSet,Integer type) {
		Node node = initSensitiveWordNode(sensitiveWordSet);
		if (node != null) {

			    sensitiveWordMap.put(type, node);
			    sensitiveWordSizeMap.put(type, sensitiveWordSet.size());

		}
	}
	
	protected static Node getSensitiveNode(Integer type) {

    	    Node node = sensitiveWordMap.get(type);
    	    if (node != null) {
    		return node;
		    }else {
			return new Node();
		    }

    }

	/**
	 * 获取指定类型敏感词总数
	 * 
	 * @param type
	 * 	敏感词类型
	 * @return
	 */
    protected static Integer getSensitiveWordCountByType(Integer type) {
		return sensitiveWordSizeMap.get(type)!=null?sensitiveWordSizeMap.get(type):0;
	}
	
	/**
	 * 获取指定敏感词总数与公用敏感词总数之和
	 * 
	 * @param type
	 * 	敏感词类型
	 * @return
	 */
	protected static Integer getSensitiveWordAndCommonWordCountByType(Integer type){
	    return getSensitiveWordCountByType(type) + getSensitiveWordCountByType(SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON);
	}
	
	/**
	 * 清除敏感词
	 */
    protected static void clearSensitiveWord() {
        if (!sensitiveWordMap.isEmpty()) {
            sensitiveWordMap.clear();
        }
    }

	/**
	 * @return the filterLatch
	 */
	public static synchronized Boolean getFilterLatch() {
	    return filterLatch;
	}

	/**
	 * 获取同步锁
	 */
	public static synchronized void await(){
	    WordFilterService.filterLatch = false;
	}
	
	/**
	 * 释放同步锁
	 */
	public static synchronized void recycle(){
	    WordFilterService.filterLatch = true;
	}
	
}
