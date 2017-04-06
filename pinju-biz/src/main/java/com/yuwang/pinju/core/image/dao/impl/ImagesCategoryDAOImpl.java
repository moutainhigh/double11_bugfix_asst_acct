package com.yuwang.pinju.core.image.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.image.dao.ImagesCategoryDAO;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
/**
 * 图片分类管理DAO实现类
 * @author 杨昭
 * @since 2011-9-21
 */
public class ImagesCategoryDAOImpl extends BaseDAO implements ImagesCategoryDAO{
	/**
	 * 添加图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @throws DaoException 
	 * @since 2011-9-21
	 */
	@Override
	public Long insertImagesCategory(ImagesCategoryDO imagesCategoryDO) throws DaoException {
		 return (Long)this.executeInsert("imagesCategory.insertImagesCategory", imagesCategoryDO);
	}
	/**
	 * 查询图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @return ImagesCategoryDO
	 */
	@Override
	public ImagesCategoryDO getImagesCategory(ImagesCategoryDO imagesCategoryDO)throws DaoException {
		return (ImagesCategoryDO) this.executeQueryForObject("imagesCategory.getImagesCategory",imagesCategoryDO);
	}
	/**
	 * 修改图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @throws DaoException 
	 */
	@Override
	public Long updateImageCategory(ImagesCategoryDO imagesCategoryDO) throws DaoException {
		return Long.valueOf(this.executeUpdate("imagesCategory.updateImageCategory", imagesCategoryDO));
	}
	/**
	 * 计算空间大小
	 * @author 杨昭
	 * @param memberId
	 * @param size
	 * @return 
	 */
	@Override
	public Long updateUserSize(ImagesCategoryDO imagesCategoryDO) throws DaoException {
		return Long.valueOf(this.executeUpdate("imagesCategory.updateUserSize", imagesCategoryDO));
	}
}
