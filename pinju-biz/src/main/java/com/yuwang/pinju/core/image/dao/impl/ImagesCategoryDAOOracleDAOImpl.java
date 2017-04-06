package com.yuwang.pinju.core.image.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.image.dao.ImagesCategoryDAOOracleDAO;
/**
 * 图片分类在Oracle中获取Sequences
 * @author 杨昭
 * @since 2011-9-21
 */
public class ImagesCategoryDAOOracleDAOImpl extends BaseDAO implements ImagesCategoryDAOOracleDAO {
	/**
	 *获取Sequences 
	 * @author 杨昭
	 * @return 
	 */
	@Override
	public Long getImageCategorySeqId() throws DaoException{
		Object ob = super.executeQueryForObject("imagesCategory.getImagesCategorySeqId",null);
		return (Long)ob;
	}

}
