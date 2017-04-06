/**
 * 
 */
package com.yuwang.pinju.domain.word;

import com.yuwang.pinju.domain.BaseDO;

/**
 * @author caiwei
 * 
 */
public abstract class SensitiveWordBaseDO extends BaseDO {

    /**
     * 
     */
    private static final long serialVersionUID = -5614004094723530053L;

    /**
     * 屏蔽类型
     */
    private Integer type;

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
	this.type = type;
    }

    /**
     * @return the type
     */
    public Integer getType() {
	return type;
    }
}
