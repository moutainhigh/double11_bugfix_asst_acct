package com.yuwang.pinju.core.util.xml;

/**
 * 编码XML文档里的特殊字符
 */
public class XMLEncoder {

    /**
     * 编码XML文档里的特殊字符
     * <p>
     * XML文件有5个特殊字符，其中必须转换的是'&amp;'和'&lt;',这里会全部转换5个特殊字符:
     * <ul>
     * <li>&amp; - 与符号转换成&amp;amp;</li>
     * <li>&lt; - 小于号转换成&amp;lt;</li>
     * <li>&gt; - 大于号转换成&amp;gt;</li>
     * <li>" - 双引号转换成&amp;quot;</li>
     * <li>' - 单引号转换成&amp;apos</li>
     * </ul>
     * @param 需要编码的XML文档
     * @return 编码后的XML文档
     */
    public static String encode(String source) {
        if (source == null) return null;
        String result = null;
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '\"':
                sb.append("&quot;");
                break;
            case '\'':
                sb.append("&apos;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            default:
                sb.append(c);
            }
        }
        result = sb.toString();
        return result;
    }

}