package com.yuwang.pinju.core.images.category;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.images.ImagesCategoryConstant;
import com.yuwang.pinju.core.image.ao.ImagesCategoryAO;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;

/**
 * 图片分类单元测试
 * @author 杨昭
 * @since 2011-9-26
 */
public class SearchImageCategoryTest extends BaseTestCase {
	@SpringBean("imagesCategoryManager")
	private ImagesCategoryAO imagesCategoryAO;
	
	
	/**
	 * 查询
	 * @author 杨昭
	 * @throws ManagerException 
	 * @since 2011-9-26
	 */
	public void testSearchImageCategory() throws ManagerException{
		List<ImagesCategoryDO> list = imagesCategoryAO.getImagesCategory(0l);
		for(int i=0;i<list.size();i++){
			ImagesCategoryDO image = list.get(i);
			System.out.println("一级分类ID"+image.getFirstCategoryId()+"一级分类名称"+image.getFirstCategoryName()+"\n");
			for(int j=0;j<image.getSecondCategoryList().size();j++){
				String[] subCategory = image.getSecondCategoryList().get(j);
				System.out.println("二级分类ID："+subCategory[0]+"二级分类名称："+subCategory[1]);
			}
			
		}
	}
	/**
	 * 添加
	 * @author 杨昭
	 * @throws ManagerException 
	 * @since 2011-9-26
	 */
	public void testAddCategory() throws ManagerException{
		ImagesCategoryDO insertImagesCategoryDO = new ImagesCategoryDO();
		insertImagesCategoryDO.setMemberId(88888888L);
		insertImagesCategoryDO.setMemberName("发发发发");
		insertImagesCategoryDO.setFirstCategory(
				imagesCategoryAO.getImageCategorySeqId()+"@!@"+ImagesCategoryConstant.MOREN_ONE+","
				+imagesCategoryAO.getImageCategorySeqId()+"@!@"+ImagesCategoryConstant.MOREN_TWO+","
				+imagesCategoryAO.getImageCategorySeqId()+"@!@"+ImagesCategoryConstant.MOREN_THREE);
		insertImagesCategoryDO.setSecondCategory("");
		insertImagesCategoryDO.setUserSize(0L);
		Boolean bool = imagesCategoryAO.insertImagesCategory(insertImagesCategoryDO);
		if(bool){
			System.out.println("添加成功！");
		}else{
			System.out.println("添加失败！");
		}
	}
	/**
	 * 修改
	 * @author 杨昭
	 * @throws ManagerException 
	 * @since 2011-9-26
	 */
	public void testUpdateCategory() throws ManagerException{
		ImagesCategoryDO imagesCategoryDO = new ImagesCategoryDO();
		Long number = imagesCategoryAO.updateImageCategory(imagesCategoryDO);
		if(number>0){
			System.out.println("修改成功！");
		}else{
			System.out.println("修改失败！");
		}
	}
	/**
	 * 计算空间大小
	 * @author 杨昭
	 * @throws ManagerException 
	 * @since 2011-9-26
	 */
	public void testUpdateUserSize() throws ManagerException{
		Long number = imagesCategoryAO.updateUserSize(88888888L, 10L, "0");
		if(number>0){
			System.out.println("修改成功！");
		}else{
			System.out.println("修改失败！");
		}
	}
}
