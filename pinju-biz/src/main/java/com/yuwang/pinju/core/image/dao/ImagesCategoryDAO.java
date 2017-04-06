package com.yuwang.pinju.core.image.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;

/**
 * 图片分类管理DAO
 * @author 杨昭
 * @since 2011-9-21
 */
public interface ImagesCategoryDAO {
	/**
	 * 添加图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO 
	 */
	Long insertImagesCategory(ImagesCategoryDO imagesCategoryDO)throws DaoException;
	/**
	 * 查询图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @return ImagesCategoryDO
	 */
	ImagesCategoryDO getImagesCategory(ImagesCategoryDO imagesCategoryDO)throws DaoException;
	/**
	 * 修改图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @throws DaoException 
	 */
	Long updateImageCategory(ImagesCategoryDO imagesCategoryDO)throws DaoException;
	/**
	 * 计算空间大小
	 * @author 杨昭
	 * @param memberId
	 * @param size
	 * @return 
	 */
	Long updateUserSize(ImagesCategoryDO imagesCategoryDO)throws DaoException;
}
