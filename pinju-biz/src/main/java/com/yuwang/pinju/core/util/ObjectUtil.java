/**
 * Created on 2006-09-20
 */
package com.yuwang.pinju.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author James
 *
 */
public abstract class ObjectUtil {
    /**
     * Default constructor.
     *
     */
    private ObjectUtil() {
    }

    /**
     * 判断字符串是否为数字.
     * @param str String
     * @return boolean
     */
    public static boolean isIntType(final String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    /**
     * 判断字符串是否为数字、字母.
     * @param str String
     * @return boolean
     */
    public static boolean isNumZ(final String str) {
    	Pattern p = Pattern.compile("^[a-zA-Z0-9-]+$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    
    /**
     * 判断字符串是否为数字、字母.
     * @param str String
     * @return boolean
     */
    public static boolean isNumZ2(final String str) {
    	Pattern p = Pattern.compile("[0-9A-Z]{8}-[0-9A-Z]{1}"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    /**
     * 判断字符串是否为数字、字母、英文
     * @param str String
     * @return boolean
     */
    public static boolean isNumZEng(final String str) {
    	Pattern p = Pattern.compile("^[a-zA-Z0-9\u4e00-\u9fa5]+$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    
    /**
     * 判断字符串是否为数字
     * @param str String
     * @return boolean
     */
    public static boolean isNum(final String str) {
    	Pattern p = Pattern.compile("\\d+$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    /**
     * 判断字符串是否为英文、字母.
     * @param str String
     * @return boolean
     */
    public static boolean isZEng(final String str) {
    	Pattern p = Pattern.compile("^[a-z A-Z\u4e00-\u9fa5]+$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    /**
     * 判断字符串是否为英文
     * @param str String
     * @return boolean
     */
    public static boolean isEnglish(final String str) {
    	Pattern p = Pattern.compile("^[A-Za-z]+$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
        
    /**
     * 判断图片格式
     * @param str String
     * @return boolean
     */
    public static boolean isIcon(final String str) {
    	Pattern p = Pattern.compile("^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    
    
    /**
     * 判断字符串是否为手机号码
     * @param str String
     * @return boolean
     */
    public static boolean isMobile(final String str) {
    	//Pattern p = Pattern.compile("^(?:13\\d|15[89])-?\\d{5}(\\d{3}|\\*{3})$"); // 正则表达式
    	Pattern p = Pattern.compile("^((1(3[4-9]|5[012789]|8[278])\\d{8})|(18[09]\\d{8})|(1(3[0-2]|5[56]|8[56]|4[57])\\d{8})|(1[35]3\\d{8}))$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    
    /**
     * 判断字符串是否为电话号码
     * @param str String
     * @return boolean
     */
    public static boolean isTel(final String str) {
    	Pattern p = Pattern.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    /**
     * 判断字符串是否为电子邮箱
     * @param str String
     * @return boolean
     */
    public static boolean isEmail(final String str) {
    	Pattern p = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    /**
     * 判断字符串是否为传真号
     * @param str String
     * @return boolean
     */
    public static boolean isPostal(final String str) {
    	Pattern p = Pattern.compile("/^\\d{6}$/"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    /**
     * 判断字符串是否为日期(yyyy-mm-dd)
     * @param str String
     * @return boolean
     */
    public static boolean isDate(final String str) {
    	Pattern p = Pattern.compile("^((((19|20|21)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20|21)\\d{2})-(0?[13578]|1[02])-31)|(((19|20|21)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20|21)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();
    }
    
    /**
     * 判断Url地址是否输入正确
     * @param str String
     * @return boolean
     */
    public static boolean isUrlPatten(final String str){
        Pattern p = Pattern.compile("^https?:\\/\\/(([a-zA-Z0-9_-])+(\\.)?)*(:\\d+)?(\\/((\\.)?(\\?)?=?&?[a-zA-Z0-9_-](\\?)?)*)*$"); // 正则表达式
    	Matcher m = p.matcher(str); // 操作的字符串
    	return  m.matches();	
    }
    
    

}
