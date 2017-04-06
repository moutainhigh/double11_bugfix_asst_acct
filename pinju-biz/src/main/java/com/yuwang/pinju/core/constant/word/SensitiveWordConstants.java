/**
 * 
 */
package com.yuwang.pinju.core.constant.word;

import java.util.HashMap;
import java.util.Map;

/**
 * 关键词过滤
 * 
 * @author caiwei
 *
 */
public class SensitiveWordConstants {
    
    /** 关键词类型 [商品]*/
    public static final Integer SENSITIVE_WORD_TYPE_GOODS = 0;
    /** 关键词类型 [会员]*/
    public static final Integer SENSITIVE_WORD_TYPE_MEMBER = 1;
    /** 关键词类型 [店铺]*/
    public static final Integer SENSITIVE_WORD_TYPE_SHOP = 2;
    /** 关键词类型 [求关注]*/
    public static final Integer SENSITIVE_WORD_TYPE_ATTENTION = 3;
    /** 关键词类型 [微博]*/
    public static final Integer SENSITIVE_WORD_TYPE_MICROBLOG = 4;
    /** 关键词类型 [问答]*/
    public static final Integer SENSITIVE_WORD_TYPE_REPLY = 5;
    /** 关键词类型 [公用]*/
    public static final Integer SENSITIVE_WORD_TYPE_COMMON = -1;
    
    /** 关键词说明 [商品]*/
    public static final String SENSITIVE_WORD_DESCRIPTION_GOODS = "商品";
    /** 关键词说明[会员]*/
    public static final String SENSITIVE_WORD_DESCRIPTION_MEMBER = "会员";
    /** 关键词说明 [店铺]*/
    public static final String SENSITIVE_WORD_DESCRIPTION_SHOP = "店铺";
    /** 关键词说明 [求关注]*/
    public static final String SENSITIVE_WORD_DESCRIPTION_ATTENTION = "求关注";
    /** 关键词说明 [微博]*/
    public static final String SENSITIVE_WORD_DESCRIPTION_MICROBLOG = "微博";
    /** 关键词说明 [问答]*/
    public static final String SENSITIVE_WORD_DESCRIPTION_REPLY = "问答";
    /** 关键词说明 [公用]*/
    public static final String SENSITIVE_WORD_DESCRIPTION_COMMON = "公用";
    
    /** 关键词过滤 [周期]*/
    public static final Integer SENSITIVE_WORD_DAYS = 7;
    /** 关键词过滤 [每次条数]*/
    public final static int FILTER_PAGE_SIZE = 1000;
    
    private static Map<Integer, String> sensitiveWordMap = new HashMap<Integer, String>();
    
    static{
	sensitiveWordMap.put(SENSITIVE_WORD_TYPE_ATTENTION, SENSITIVE_WORD_DESCRIPTION_ATTENTION);
	sensitiveWordMap.put(SENSITIVE_WORD_TYPE_GOODS, SENSITIVE_WORD_DESCRIPTION_GOODS);
	sensitiveWordMap.put(SENSITIVE_WORD_TYPE_MEMBER, SENSITIVE_WORD_DESCRIPTION_MEMBER);
	sensitiveWordMap.put(SENSITIVE_WORD_TYPE_MICROBLOG, SENSITIVE_WORD_DESCRIPTION_MICROBLOG);
	sensitiveWordMap.put(SENSITIVE_WORD_TYPE_REPLY, SENSITIVE_WORD_DESCRIPTION_REPLY);
	sensitiveWordMap.put(SENSITIVE_WORD_TYPE_SHOP, SENSITIVE_WORD_DESCRIPTION_SHOP);
	sensitiveWordMap.put(SENSITIVE_WORD_TYPE_COMMON, SENSITIVE_WORD_DESCRIPTION_COMMON);
    }
    
    public static Map<Integer, String> getSensitiveWordMap(){
	return sensitiveWordMap;
    } 
}
