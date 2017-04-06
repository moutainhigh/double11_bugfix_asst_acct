package com.yuwang.pinju.core.image.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;

/**
 * 图片分类管理Manager
 * @author 杨昭
 * @since 2011-9-21
 */
public interface ImagesCategoryManager {
	/**
	 * 添加图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO 
	 */
	boolean insertImagesCategory(ImagesCategoryDO imagesCategoryDO)throws ManagerException;
	/**
	 * 查询图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @param memberName
	 * @param isType 0返回List<ImagesCategoryDO>(只限一级分类和二级分类) 1返回List<ImagesCategoryDO> get(0)获取单个ImagesCategoryDO对象
	 * @return List<ImagesCategoryDO>
	 */
	List<ImagesCategoryDO> getImagesCategory(Long memberId,String memberName,String isType)throws ManagerException;
	/**
	 * @author 杨昭
	 * @param memberId
	 * @return ImagesCategoryDO
	 */
	ImagesCategoryDO getImagesCategoryObject(Long memberId)throws ManagerException;
	/**
	 *获取Sequences 
	 * @author 杨昭
	 * @return Long
	 */
	Long getImageCategorySeqId()throws ManagerException;
	/**
	 * 修改图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @return Long
	 */
	Long updateImageCategory(ImagesCategoryDO imagesCategoryDO)throws ManagerException;
	/**
	 * 计算空间大小
	 * @author 杨昭
	 * @param memberId
	 * @param size 需要计算的大小，单位(字节)
	 * @param isType 0减，1加
	 * @return Long
	 */
	Long updateUserSize(Long memberId, Long size, String isType)throws ManagerException;
}
