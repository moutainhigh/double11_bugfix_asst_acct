package com.yuwang.pinju.common;

import java.io.Serializable;

/**
 * <p>数据库关系对象映射实体</p>
 *
 * @param <T> 主键类型
 *
 * @author gaobaowen
 * 2011-6-2 下午12:11:07
 */
public interface RelationEntity<T> extends Serializable {

	public boolean isNew();
}
