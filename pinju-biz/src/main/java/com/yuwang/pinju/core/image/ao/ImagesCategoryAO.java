package com.yuwang.pinju.core.image.ao;

import java.util.List;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
/**
 * 图片分类管理AO
 * @author 杨昭
 * @since 2011-9-21
 */
public interface ImagesCategoryAO {
	/**
	 * 添加图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO 
	 * @return boolean
	 */
	boolean insertImagesCategory(ImagesCategoryDO insertImagesCategoryDO);
	/**
	 * 查询图片分类
	 * @author 杨昭
	 * @param memberId
	 * @return List<ImagesCategoryDO>
	 */
	List<ImagesCategoryDO> getImagesCategory(Long memberId);
	/**
	 * @author 杨昭
	 * @param memberId
	 * @return ImagesCategoryDO
	 */
	ImagesCategoryDO getImagesCategoryObject(Long memberId);
	/**
	 *获取Sequences 
	 * @author 杨昭
	 * @return Long
	 */
	Long getImageCategorySeqId();
	/**
	 * 修改图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 */
	Long updateImageCategory(ImagesCategoryDO imagesCategoryDO);
	/**
	 * 计算空间大小
	 * @author 杨昭
	 * @param memberId
	 * @param size	计算需要的值 ，单位(字节)
	 * @param isType  1加，0减 
	 * @return Long
	 */
	Long updateUserSize(Long memberId,Long size,String isType);
	
	/**
	 * 查询图片分类 并转换成JSON String
	 * @author XueQi
	 * @param imagesCategoryDO 图片空间分类DO
	 * @return String JSON String
	 */
	String getImagesCategoryToJson(Long memberId);
	/**
	 * 根据会员ID获取"默认分类"ID
	 * @author 杨昭
	 * @param memberId
	 * @return Long
	 */
	Long getDefaultCategoryId(Long memberId);
}
