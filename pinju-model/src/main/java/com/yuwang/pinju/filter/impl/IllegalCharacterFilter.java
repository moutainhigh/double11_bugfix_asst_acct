package com.yuwang.pinju.filter.impl;

import java.util.Arrays;

import com.yuwang.pinju.filter.StringFilter;

/**
 * <p>无效字符过滤</p>
 *
 * @author gaobaowen
 * @since 2011-9-26 13:37:27
 */
public abstract class IllegalCharacterFilter implements StringFilter {

	/**
	 * 需要过滤的字符
	 */
    private final char[] filters;

    /**
     * 是否以黑名单方式过滤，还是以白名单方式过滤
     */
    private final boolean black;
    private int max = Integer.MAX_VALUE;

    private IllegalCharacterFilter(char[] filters, boolean black) {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("filter chars is null or empty");
        }
        this.filters = filters.clone();
        Arrays.sort(this.filters);
        this.black = black;
    }

    public static IllegalCharacterFilter black(char[] filters) {
        return new BlackFilter(filters);
    }

    public static IllegalCharacterFilter white(char[] filters) {
        return new WhiteFilter(filters);
    }

    @Override
    public String doFilter(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (str.length() > max) {
            return null;
        }
        char[] chs = str.toCharArray();
        int scan = 0;
        for (int i = 0; i < chs.length; i++) {
            if (isKey(chs[i]) ^ black) {
                chs[scan++] = chs[i];
            }
        }
        return new String(chs, 0, scan);
    }

    public int getMax() {
        return max;
    }

    public IllegalCharacterFilter max(int max) {
        this.max = max;
        return this;
    }

    /**
     * <p>是否</p>
     *
     * @param c
     * @return
     *
     * @author gaobaowen
     * @since 2011-9-26 13:39:37
     */
    private boolean isKey(char c) {
        return (Arrays.binarySearch(filters, c) >= 0);
    }

    /**
     * <p>白名单字符过滤器</p>
     *
     * @author gaobaowen
     * @since 2011-9-26 13:38:49
     */
    private static class WhiteFilter extends IllegalCharacterFilter {
        protected WhiteFilter(char[] filters) {
            super(filters, false);
        }
    }

    /**
     * <p>黑名单字符过滤器</p>
     *
     * @author gaobaowen
     * @since 2011-9-26 13:39:09
     */
    private static class BlackFilter extends IllegalCharacterFilter {
        protected BlackFilter(char[] filters) {
            super(filters, true);
        }
    }
}
