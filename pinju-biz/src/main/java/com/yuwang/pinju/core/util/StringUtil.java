package com.yuwang.pinju.core.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * String Utilities.
 */
public class StringUtil {

    private StringUtil() {
        // util class, prevent from new instance
    }

    public static boolean isNull(Object object) {
        if (object instanceof String) {
            return StringUtil.isEmpty(object.toString());
        }
        return object == null;
    }
    /**
     * Checks if string is null or empty.
     * 
     * @param value
     *            The string to be checked
     * @return True if string is null or empty, otherwise false.
     */
    public static boolean isEmpty(final String value) {
        return value == null || value.trim().length() == 0;
    }

    /**
     * Converts <code>null</code> to empty string, otherwise returns it
     * directly.
     * 
     * @param string
     *            The nullable string
     * @return empty string if passed in string is null, or original string
     *         without any change
     */
    public static String null2String(String string) {
        return string == null ? "" : string;
    }

    /**
     * Converts string from GB2312 encoding ISO8859-1 (Latin-1) encoding.
     * 
     * @param gbString
     *            The string of GB1212 encoding
     * @return New string of ISO8859-1 encoding
     */
    public static String iso2Gb(String gbString) {
        if (gbString == null)
            return null;
        String outString = "";
        try {
            byte[] temp = null;
            temp = gbString.getBytes("ISO8859-1");
            outString = new String(temp, "GB2312");
        } catch (java.io.UnsupportedEncodingException e) {
            // ignore it as no way to convert between these two encodings
        }
        return outString;
    }

    /**
     * Converts string from ISO8859-1 encoding to UTF-8 encoding.
     * 
     * @param isoString
     *            String of ISO8859-1 encoding
     * @return New converted string of UTF-8 encoding
     */
    public static String iso2Utf(String isoString) {
        if (isoString == null)
            return null;
        String outString = "";
        try {
            byte[] temp = null;
            temp = isoString.getBytes("ISO8859-1");
            outString = new String(temp, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {

        }
        return outString;
    }

    /**
     * Converts string from platform default encoding to GB2312.
     * 
     * @param inString
     *            String in platform default encoding
     * @return New string in GB2312 encoding
     */
    public static String str2Gb(String inString) {
        if (inString == null)
            return null;
        String outString = "";
        try {
            byte[] temp = null;
            temp = inString.getBytes();
            outString = new String(temp, "GB2312");
        } catch (java.io.UnsupportedEncodingException e) {

        }
        return outString;
    }

    /**
     * Insert "0" in front of <em>dealCode</em> if its length is less than 3.
     * 
     * @param dealCode
     *            The dealCode to be filled with "0" at the beginning
     * @return New string with "0" filled
     */
    public static String fillZero(String dealCode) {
        String zero = "";
        if (dealCode.length() < 3) {
            for (int i = 0; i < (3 - dealCode.length()); i++) {
                zero += "0";
            }
        }
        return (zero + dealCode);
    }

    public static String convertAmount(String amount) {
        String str = String.valueOf(Double.parseDouble(amount));
        int pos = str.indexOf(".");
        int len = str.length();
        if (len - pos < 3) {
            return str.substring(0, pos + 2) + "0";
        } else {
            return str.substring(0, pos + 3);
        }
    }

    /**
     * to 10Decrypt
     */
    public static String to10(String opStr) {
        String zm = "#123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int lenOfOp = opStr.length();
        long result = 0;
        String indexOfOp;
        int js;
        for (int i = 0; i < lenOfOp; i++) {
            indexOfOp = opStr.substring(i, i + 1);
            js = zm.indexOf(indexOfOp);
            result = result * 36 + js;
        }
        // 补充到12位
        String jg = String.valueOf(result);
        int bc = 12 - jg.length();
        String jgq = "";
        for (int j = 0; j < bc; j++) {
            jgq += "0";
        }
        return jgq + jg;
    }

    /**
     * to 36Encrypt
     */
    public static String to36(String originalStr) {

        long oVal = Long.parseLong(originalStr);
        long shang;
        int yushu;
        String result = "";
        String zm = "#123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 1; i < 9; i++) {
            shang = oVal / 36;
            yushu = (int) (oVal % 36);
            result = zm.substring(yushu, yushu + 1) + result;
            oVal = shang;
        }

        return result;

    }

    /**
     * Encrypt deal Id : 2 bits year,10 bits sequence
     */
    public static String encDealId(String dealid) {
        if (dealid.length() != 23)
            return "notval";
        String ny = dealid.substring(5, 7);
        String sq = dealid.substring(11, 21);

        return to36(ny + sq);
    }

    /**
     * Decrypt deal Id : 12 bits
     */
    public static String decDealId(String opStr) {
        return to10(opStr);
    }

    /**
     * 数字转换为大写字母
     * 
     * @param money
     *            format example: 100.00
     * @return
     */
    public static String[] numToWords(String money) {
        int j = money.length();
        String[] str = new String[j];
        for (int i = 0; i < j; i++) {
            switch (money.charAt(i)) {
            case '0':
                str[i] = "零";
                continue;
            case '1':
                str[i] = "壹";
                continue;
            case '2':
                str[i] = "贰";
                continue;
            case '3':
                str[i] = "叁";
                continue;
            case '4':
                str[i] = "肆";
                continue;
            case '5':
                str[i] = "伍";
                continue;
            case '6':
                str[i] = "陆";
                continue;
            case '7':
                str[i] = "柒";
                continue;
            case '8':
                str[i] = "捌";
                continue;
            case '9':
                str[i] = "玖";
                continue;
            case '.':
                str[i] = "点";
                continue;
            }
        }
        return str;
    }

    /**
     * 把人民币转换成大写的标准格式
     * 
     * @param str
     * @return
     */
    public static String money2BigFormat(String money) {
        String[] bigNumber = numToWords(money);
        int len = bigNumber.length;
        if (len > 11)
            return "数额过高";
        StringBuffer sb = new StringBuffer();
        if (len >= 7) {
            if (len == 11) {
                sb.append(bigNumber[0] + "仟");
                sb.append(bigNumber[1] + "佰" + bigNumber[2] + "拾"
                        + bigNumber[3] + "万");
            }
            if (len == 10) {
                sb.append(bigNumber[0] + "佰" + bigNumber[1] + "拾"
                        + bigNumber[2] + "万");
            }
            if (len == 9) {
                sb.append(bigNumber[0] + "拾" + bigNumber[1] + "万");
            }
            if (len == 8) {
                sb.append(bigNumber[0] + "万");
            }
            sb.append(bigNumber[len - 7] + "仟" + bigNumber[len - 6] + "佰"
                    + bigNumber[len - 5] + "拾");
        }
        if (len == 6) {
            sb.append(bigNumber[0] + "佰" + bigNumber[1] + "拾");
        }
        if (len == 5) {
            sb.append(bigNumber[0] + "拾");
        }
        sb.append(bigNumber[len - 4] + "元" + bigNumber[len - 2] + "角"
                + bigNumber[len - 1] + "分整");
        return sb.toString();
    }

    /**
     * 货币格式化
     * 
     * @param currency
     * @return
     */
    public static String formatCurrecy(String currency) {
        if ((null == currency) || "".equals(currency)
                || "null".equals(currency)) {
            return "";
        }

        NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);

        try {
            return usFormat.format(Double.parseDouble(currency));
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatCurrecy(String currency, String currencyCode) {
        try {
            if ((null == currency) || "".equals(currency)
                    || "null".equals(currency)) {
                return "";
            }

            if (currencyCode.equalsIgnoreCase("1")) {
                NumberFormat usFormat = NumberFormat
                        .getCurrencyInstance(Locale.CHINA);
                return usFormat.format(Double.parseDouble(currency));
            } else {
                return currency + "点";
            }
        } catch (Exception e) {
            return "";
        }
    }

    // Useful split and replace methods

    /**
     * Splits the provided text into a list, using whitespace as the separator.
     * The separator is not included in the returned String array.
     * 
     * @param str
     *            the string to parse
     * @return an array of parsed Strings
     */
    public static String[] split(String str) {
        return split(str, null, -1);
    }

    /**
     * @param text
     *            String
     * @param separator
     *            String
     * @return String[]
     */
    public static String[] split(String text, String separator) {
        return split(text, separator, -1);
    }

    /**
     * Splits the provided text into a list, based on a given separator. The
     * separator is not included in the returned String array. The maximum
     * number of splits to perfom can be controlled. A null separator will cause
     * parsing to be on whitespace.
     * <p>
     * <p>
     * This is useful for quickly splitting a string directly into an array of
     * tokens, instead of an enumeration of tokens (as
     * <code>StringTokenizer</code> does).
     * 
     * @param str
     *            The string to parse.
     * @param separator
     *            Characters used as the delimiters. If <code>null</code>,
     *            splits on whitespace.
     * @param max
     *            The maximum number of elements to include in the list. A zero
     *            or negative value implies no limit.
     * @return an array of parsed Strings
     */
    public static String[] split(String str, String separator, int max) {
        StringTokenizer tok = null;
        if (separator == null) {
            // Null separator means we're using StringTokenizer's default
            // delimiter, which comprises all whitespace characters.
            tok = new StringTokenizer(str);
        } else {
            tok = new StringTokenizer(str, separator);
        }

        int listSize = tok.countTokens();
        if (max > 0 && listSize > max) {
            listSize = max;
        }

        String[] list = new String[listSize];
        int i = 0;
        int lastTokenBegin = 0;
        int lastTokenEnd = 0;
        while (tok.hasMoreTokens()) {
            if (max > 0 && i == listSize - 1) {
                // In the situation where we hit the max yet have
                // tokens left over in our input, the last list
                // element gets all remaining text.
                String endToken = tok.nextToken();
                lastTokenBegin = str.indexOf(endToken, lastTokenEnd);
                list[i] = str.substring(lastTokenBegin);
                break;
            }
            list[i] = tok.nextToken();
            lastTokenBegin = str.indexOf(list[i], lastTokenEnd);
            lastTokenEnd = lastTokenBegin + list[i].length();
            i++;
        }
        return list;
    }

    /**
     * Replace all occurances of a string within another string.
     * 
     * @param text
     *            text to search and replace in
     * @param repl
     *            String to search for
     * @param with
     *            String to replace with
     * @return the text with any replacements processed
     * @see #replace(String text, String repl, String with, int max)
     */
    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    /**
     * Replace a string with another string inside a larger string,
     * for the first <code>max</code> values of the search string.  A
     * <code>null</code> reference is passed to this method is a
     * no-op.
     *
     * @param text text to search and replace in
     * @param repl String to search for
     * @param with String to replace with
     * @param max  maximum number of values to replace, or
     *             <code>-1</code> if no maximum
     * @return the text with any replacements processed
     * @throws NullPointerException if repl is null
     */
    private static String replace(String text, String repl, String with, int max) {
        if (text == null) {
            return null;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        int end = text.indexOf(repl, start);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
            end = text.indexOf(repl, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * 用Map中的变量名-变量值替换源字符串中的变量名.
     * @param src 字符串
     * @param value  变量名-变量值
     * @return String
     * <br>
     * <br>Example:
     * <br>String src = "Hello ${username}, this is ${target} speaking.";
     * <br>Map map = new HashMap();
     * <br>map.put("username", "petter");
     * <br>map.put("target", "tom");
     * <br>src = StringUtil.replaceVariable(str, map);
     * <br>#The src equals:
     * <br>     "Hello petter, this is tom speaking."
     */
    public static String replaceVariable(final String src, final Map value) {
        int len = src.length();
        StringBuffer buf = new StringBuffer(len);
        for (int i=0; i<len; i++) {
            char c = src.charAt(i);
            if (c == '$') {
                i++;
                StringBuffer key = new StringBuffer();
                char temp = src.charAt(i);
                while(temp != '}') {
                    if (temp != '{') {
                        key.append(temp);
                    }
                    i++;
                    temp = src.charAt(i);
                }
                String variable = (String)value.get(key.toString());
                if (null == variable) {
                    buf.append("");
                } else {
                    buf.append(variable);
                }
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }
    
    public static String array2String(String[] strs){
    	StringBuffer sb = new StringBuffer();
    	for(String ss:strs){
    		sb.append(",").append(ss);
    	}
    	return sb.toString().substring(1);
    }
    
    /**  
     * 获取字符串长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1  
     * @param str 字符 串
     * @return 字符串长度  
     */
    public static int getStringLength(String str)
    {
    	int stringLength = 0;
    	char[] chars = str.toCharArray();
    	for (int i = 0; i < chars.length; i++)
    	{
    		int specialCharLength = getSpecialCharLength(chars[i]);
    		stringLength += specialCharLength;
    	}
    	return stringLength;
    }   
    
    /**  
     * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1  
     * @param c 字符  
     * @return 字符长度  
     */   
    private static int getSpecialCharLength(char c) {   
        if (isLetter(c)) {   
            return 1;   
        } else {   
            return 2;   
        }   
    }   
   
    /**  
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）  
     *   
     * @param char c, 需要判断的字符  
     * @return boolean, 返回true,Ascill字符  
     */   
    private static boolean isLetter(char c) {   
        int k = 0x80;   
        return c / k == 0 ? true : false;   
    }  

    /** 空字符串。 */
    public static final String EMPTY_STRING = "";
    
    /**
     * 如果字符串是<code>null</code>，则返回空字符串<code>""</code>，否则返回字符串本身。
     * <pre>
     * StringUtil.defaultIfNull(null)  = ""
     * StringUtil.defaultIfNull("")    = ""
     * StringUtil.defaultIfNull("  ")  = "  "
     * StringUtil.defaultIfNull("bat") = "bat"
     * </pre>
     *
     * @param str 要转换的字符串
     *
     * @return 字符串本身或空字符串<code>""</code>
     */
    public static String defaultIfNull(String str) {
        return (str == null) ? EMPTY_STRING
                             : str;
    }
    
    /**
     * 如果字符串是<code>null</code>，则返回指定默认字符串，否则返回字符串本身。
     * <pre>
     * StringUtil.defaultIfNull(null, "default")  = "default"
     * StringUtil.defaultIfNull("", "default")    = ""
     * StringUtil.defaultIfNull("  ", "default")  = "  "
     * StringUtil.defaultIfNull("bat", "default") = "bat"
     * </pre>
     *
     * @param str 要转换的字符串
     * @param defaultStr 默认字符串
     *
     * @return 字符串本身或指定的默认字符串
     */
    public static String defaultIfNull(String str, String defaultStr) {
        return (str == null) ? defaultStr
                             : str;
    }
}
