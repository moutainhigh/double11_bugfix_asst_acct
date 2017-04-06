package com.yuwang.pinju.core.image.dao;

import com.yuwang.pinju.core.common.DaoException;

/**
 * 图片分类在Oracle中获取Sequences
 * @author 杨昭
 * @since 2011-9-21
 */
public interface ImagesCategoryDAOOracleDAO {
	/**
	 *获取Sequences 
	 * @author 杨昭
	 * @return 
	 */
	Long getImageCategorySeqId()throws DaoException;
}
