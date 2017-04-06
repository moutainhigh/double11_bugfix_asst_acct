package com.yuwang.pinju.core.image.ao.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.image.ao.StorageFileInfoAO;
import com.yuwang.pinju.core.image.manager.StorageFileInfoManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;

public class StorageFileInfoAOImpl extends BaseAO implements StorageFileInfoAO {

	private StorageFileInfoManager storageFileInfoManager;
	public StorageFileInfoManager getStorageFileInfoManager() {
		return storageFileInfoManager;
	}

	public void setStorageFileInfoManager(
			StorageFileInfoManager storageFileInfoManager) {
		this.storageFileInfoManager = storageFileInfoManager;
	}

	/**
	 * 根据条件查询图片信息
	 * @param StorageFileInfo
	 * @return
	 */
	public List<StorageFileInfoDO> getStorageFileInfoListByCon(StorageFileInfoDO storageFileInfo) {
		// TODO Auto-generated method stub
		try {
			return storageFileInfoManager.getStorageFileInfoListByCon(storageFileInfo);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error("查询图片列表出错",e);
			return null;
		}
	}
	
	/***
	 * 更新图片信息
	 * @param StorageFileInfo
	 * @return
	 */
	public boolean updateStorageFileInfo(StorageFileInfoDO storageFileInfo) {
		// TODO Auto-generated method stub
		try {
			return storageFileInfoManager.updateStorageFileInfo(storageFileInfo);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error("更新图片信息出错",e);
			return false;
		}
	}
	
	/****
	 * 根据Id查询图片详情
	 * @param id
	 * @return
	 */
	public StorageFileInfoDO queryStorageFileInfo(Long id,Long userId) {
		try {
			return storageFileInfoManager.queryStorageFileInfo(id,userId);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error("查询图片详情出错",e);
			return null;
		}
	}

	/***
	 * 批量插入图片信息
	 * @param fileInfoList
	 */
	public String[] insertStorageFileInfo(File[] files,String[] fileNames,Long memberId,String memberName,Long categoryId) {
		// TODO Auto-generated method stub
		try {
			return storageFileInfoManager.insertStorageFileInfoExt(files,fileNames,memberId,memberName,categoryId);
		} catch (ManagerException e) {
			logger.error("批量插入图片信息出错",e);
			return null;
		}
	}

	/***
	 * 批量更新图片所属分类
	 * @param type  分类类型
	 * @param ids    图片id
	 * @return
	 */
	public boolean updateStorageFileInfo(Long imageCategoryId,Long userId,List<Long> idList) {
		try {
			  storageFileInfoManager.updateStorageFileInfo(imageCategoryId,userId,idList);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error("批量更新图片所属分类出错",e);
			return false;
		}
		return true;
	}

	/****
	 * 批量删除分类下的所有图片
	 * @param imageCategoryIds  分类id集合
	 * @return
	 */
	public boolean deleteFileByCategoryId(List<Long> imageCategoryIds,Long userId){
		try {
			return storageFileInfoManager.deleteFileByCategoryId(imageCategoryIds,userId);
		} catch (ManagerException e) {
			logger.error("批量删除某个分类下的所有图片出错",e);
			return false;
		}
	}
	
	/******
     * 根据条件查询出总条数
     * @param storageFileInfo
     * @return  总条数
     */
 	public int queryCount(StorageFileInfoDO storageFileInfo){
 		try {
 			Integer count=storageFileInfoManager.queryCount(storageFileInfo);
 			return count!=null?count.intValue():0;
		} catch (ManagerException e) {
			logger.error("根据条件查询出总条数出错",e);
			return 0;
		}
 	}
 	
 	/*****
 	 * 修改用户的图片分类
 	 * @param imageCategoryId  旧的图片分类分类
 	 * @param imageCategoryIdMod 新的图片分类分类
 	 * @param memberId  用户id
 	 * @return
 	 */
	public boolean updateStorageFileInfo(Long imageCategoryId,Long imageCategoryIdMod,Long memberId){
		try {
 			return storageFileInfoManager.updateStorageFileInfo(imageCategoryId, imageCategoryIdMod, memberId);
		} catch (ManagerException e) {
			logger.error("修改用户的图片分类出错",e);
			return false;
		}
	}
	
	/***
	 * 统计id集合的总容量
	 * @param idList
	 * @return
	 */
	public Map<Long,Long> queryStorageFileSizeByIds(List<Long> idList,Long userId){
		try {
 			return storageFileInfoManager.queryStorageFileSizeByIds(idList,userId);
 			
		} catch (ManagerException e) {
			logger.error("统计id集合的总容量出错",e);
			return null;
		}
	}
	
	/***
	 * 根据id批量删除图片信息
	 * @param idList  图片编号集合
	 * @return
	 */
	public boolean deleteStorageFileByIds(List<Long> idList,Long userId){
		try {
 			return storageFileInfoManager.deleteStorageFileByIds(idList,userId);
		} catch (ManagerException e) {
			logger.error("Id删除图片信息出错",e);
			return false;
		}
	}
	
	/***
	 * 统计分类下的总容量
	 * @param imageCategoryId 图片分类集合
	 * @return
	 */
	public Long queryStorageFileSize(List<Long> imageCategoryId,Long userId){
		try {
 			return storageFileInfoManager.queryStorageFileSize(imageCategoryId,userId);
		} catch (ManagerException e) {
			logger.error("统计分类下的总容量出错",e);
			return 0L;
		}
	}
	
	/****
	 * 根据分类查询分类下的图片信息
	 * @param imageCategoryList 图片分类id集合
	 * @return
	 */
	public List<StorageFileInfoDO> queryStorageFileByCategoryIds(List<Long> imageCategoryList,Long userId){
		try {
			return storageFileInfoManager.queryStorageFileByCategoryIds(imageCategoryList,userId);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error("根据分类查询分类下的图片信息出错",e);
			return null;
		}
	}
	
	/*****
	 * 根据编号id批量查询图片信息
	 * @param idList 图片编号集合
	 * @return
	 */
	public List<StorageFileInfoDO> queryStorageFileByIds(List<Long> idList,Long userId){
		try {
			return storageFileInfoManager.queryStorageFileByIds(idList,userId);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(" 根据编号id批量查询图片信息出错",e);
			return null;
		}
	}
	
	/*****
 	 * 将某一组分类下的所有图片移动到某分类下
 	 * @param tocategoryId  分类id
 	 * @param fromCategoryIdList 分类idList
 	 * @param memberId  用户id
 	 * @return
 	 */
	public boolean updateStorageFileInfo(Long tocategoryId,List<Long> fromCategoryIdList,Long memberId){
		try {
			return storageFileInfoManager.updateStorageFileInfo(tocategoryId,fromCategoryIdList,memberId);
		} catch (ManagerException e) {
			logger.error("将某一组分类下的所有图片移动到某分类下出错",e);
			return false;
		}
	}
	
	/********
	 * 根据图片编号修改图片名称
	 * @param fileName  图片名称
	 * @param id   图片编号
	 */
	public boolean updateStorageFileInfoByFileName(String fileName,Long id,Long memberId){
		try {
			return storageFileInfoManager.updateStorageFileInfoByFileName(fileName,id,memberId);
		} catch (ManagerException e) {
			logger.error("修改图片名称出错",e);
			return false;
		}
	}
}
