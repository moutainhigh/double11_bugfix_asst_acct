package com.yuwang.pinju.core.image.ao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.images.ImagesCategoryConstant;
import com.yuwang.pinju.core.image.ao.ImagesCategoryAO;
import com.yuwang.pinju.core.image.manager.ImagesCategoryManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.JsonUtil;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.domain.member.MemberDO;
/**
 * 图片分类管理AO实现类
 * @author 杨昭
 * @since 2011-9-21
 */
public class ImagesCategoryAOImpl extends BaseAO implements ImagesCategoryAO {
	private ImagesCategoryManager imagesCategoryManager;
	private MemberManager memberManager;
	public MemberManager getMemberManager() {
		return memberManager;
	}
	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}
	public ImagesCategoryManager getImagesCategoryManager() {
		return imagesCategoryManager;
	}
	public void setImagesCategoryManager(ImagesCategoryManager imagesCategoryManager) {
		this.imagesCategoryManager = imagesCategoryManager;
	}
	/**
	 * 添加图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO 
	 * @return boolean
	 */
	@Override
	public boolean insertImagesCategory(ImagesCategoryDO insertImagesCategoryDO){
		boolean bool = false;
		try {
				bool = imagesCategoryManager.insertImagesCategory(insertImagesCategoryDO);
		} catch (ManagerException e) {
			log.error("添加图片分类:",e);
		}
		return bool;
	}
	/**
	 * 查询图片分类
	 * @author 杨昭
	 * @param memberId
	 * @return List<ImagesCategoryDO>
	 */
	@Override
	public List<ImagesCategoryDO> getImagesCategory(Long memberId) {
		try {
			MemberDO memberDO = memberManager.findMember(memberId);
			return imagesCategoryManager.getImagesCategory(memberId,memberDO.getNickname(),"0");
		} catch (ManagerException e) {
			log.error("查询图片分类:",e);
		}
		return null;
	}
	/**
	 *获取Sequences 
	 * @author 杨昭
	 * @return Long
	 */
	@Override
	public Long getImageCategorySeqId() {
		try {
			return imagesCategoryManager.getImageCategorySeqId();
		} catch (ManagerException e) {
			log.error("获取Sequences：",e);
		}
		return null;
	}
	/**
	 * 修改图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @return Long
	 */
	@Override
	public Long updateImageCategory(ImagesCategoryDO imagesCategoryDO){
		try {
			return imagesCategoryManager.updateImageCategory(imagesCategoryDO);
		} catch (ManagerException e) {
			log.error("图片分类修改：", e);
		}
		return null;
	}
	/**
	 * 计算空间大小
	 * @author 杨昭
	 * @param memberId
	 * @param size	计算需要的值 ，单位(字节)
	 * @param isType  1加，0减 
	 * @return 
	 */
	@Override
	public Long updateUserSize(Long memberId, Long size, String isType) {
		try {
			return imagesCategoryManager.updateUserSize(memberId,size,isType);
		} catch (Exception e) {
			log.error("加减空间大小", e);
		}
		return null;
	}
	/**
	 * @author 杨昭
	 * @param memberId
	 * @return ImagesCategoryDO
	 */
	@Override
	public ImagesCategoryDO getImagesCategoryObject(Long memberId) {
		try {
			MemberDO memberDO = memberManager.findMember(memberId);
			List<ImagesCategoryDO> list = imagesCategoryManager.getImagesCategory(memberId,memberDO.getNickname(),"1");
			return list.get(0);
		} catch (ManagerException e) {
			log.error("查询单个分类信息", e);
		}
		return null;
	}
	/**
	 * 查询图片分类 并转换成JSON String
	 * @author XueQi
	 * @param imagesCategoryDO 图片空间分类DO
	 * @return String JSON String
	 */
	@Override
	public String getImagesCategoryToJson(Long memberId){
		List<ImagesCategoryDO> iCategoryDOList = getImagesCategory(memberId);
		return JsonUtil.getJsonString4Collection(iCategoryDOList);
	}
	/**
	 * 根据会员ID获取"默认分类"ID
	 * @author 杨昭
	 * @param memberId
	 * @return Long
	 */
	@Override
	public Long getDefaultCategoryId(Long memberId) {
		MemberDO memberDO = null;
		Long defaultId = 0L;
		try {
			memberDO = memberManager.findMember(memberId);
			List<ImagesCategoryDO> list = imagesCategoryManager.getImagesCategory(memberId,memberDO.getNickname(),"0");
			for(int i=0;i<list.size();i++){
				if(list.get(i).getFirstCategoryName().equals(ImagesCategoryConstant.MOREN_ONE)){
					defaultId = Long.valueOf(list.get(i).getFirstCategoryId());
				}
			}
		} catch (ManagerException e) {
			log.error("根据会员ID获取默认分类ID", e);
		}
		return defaultId;
	}
}
