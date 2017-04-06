/**
 * @(#)TemporalProperty.java     06/07/25
 */
package com.yuwang.pinju.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ 跟据日期，查找价格策略<br>
 * @author lengweiping
 * @version 2.0
 */
public final class TemporalProperty {

    // ----------------------------------------------------------- 私有变量
    /**
     * @ 价格策略的Map
     */
    private Map contents = new HashMap();

    /**
     * @ 将map对象中key置入cache对象,内容倒序
     */
    private List milestoneCache;

    // ----------------------------------------------------------- 私有函数
    /**
     * @return 返回cache对象
     */
    private List milestones() {
        if (milestoneCache == null) {
            calculateMilestones();
        }
        return milestoneCache;
    }

    /**
     * @ 将map对象中key置入cache对象,内容倒序
     */
    private void calculateMilestones() {
        milestoneCache = new ArrayList(contents.size());
        milestoneCache.addAll(contents.keySet());
        Collections.sort(milestoneCache, Collections.reverseOrder());
    }

    /**
     * @ 释放cache对象
     */
    private void clearMilestoneCache() {
        milestoneCache = null;
    }

    // ----------------------------------------------------------- 公有函数
    /**
     * @ 验证有效日期，并跟据有效日期返回一个对象
     * @param when
     *            the when is MfDate instance
     * @return 返回指定日期的对象
     * @throws IllegalArgumentException
     */
    public Object get(final MfDate when) {
        Iterator it = milestones().iterator();
        while (it.hasNext()) {
            MfDate thisDate = (MfDate) it.next();
            if (thisDate.before(when) || thisDate.equals(when)) {
                return contents.get(when);
            }
        }
        throw new IllegalArgumentException("no records that early");
    }

    /**
     * @ 以MfDate对象为key,向HashMap中置一个对象
     * @param at
     *            the at is MfDate instance
     * @param item
     *            价格策略对象
     */
    public void put(final MfDate at, final Object item) {
        contents.put(at, item);
        clearMilestoneCache();
    }

    /**
     * @ 以当前系统日期的MfDate对象为key,向HashMap中置一个对象
     * @param item
     *            价格策略对象
     */
    public void put(final Object item) {
        contents.put(MfDate.today(), item);
        clearMilestoneCache();
    }
}
