package com.yuwang.pinju.core.image.ao;

import java.io.File;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;

public interface StorageFileInfoAO {
	/**
	 * 根据条件查询图片信息
	 * @param StorageFileInfo
	 * @return
	 */
	public List<StorageFileInfoDO> getStorageFileInfoListByCon(StorageFileInfoDO storageFileInfo);
	
	/**
	 * 批量插入图片信息
	 * @param fileInfoList
	 * @return
	 */
	public String[] insertStorageFileInfo(File[] files,String[] fileNames,Long memberId,String memberName,Long categoryId);
	
	
	/*****
	 * 批量修改图片分类
	 * @param imageCategoryId 图片分类id
	 * @param idList 图片编号集合
	 * @return
	 */
	public boolean updateStorageFileInfo(Long imageCategoryId,Long memberId,List<Long> idList);
	
	/***
	 * 更新图片信息
	 * @param StorageFileInfo
	 * @return 是否更新成功
	 */
	public boolean updateStorageFileInfo(StorageFileInfoDO storageFileInfo);
	
	
	/****
	 * 根据图片编号查询图片详情
	 * @param id
	 * @return
	 */
	public StorageFileInfoDO queryStorageFileInfo(Long id,Long userId);
	
	
	/****
	 * 批量删除分类下的所有图片
	 * @param imageCategoryIds 图片分类id集合
	 * @return
	 */
	public boolean deleteFileByCategoryId(List<Long> imageCategoryIds,Long userId);
	
	/******
     * 根据条件查询出总条数
     * @param storageFileInfo
     * @return  总条数
     */
 	public int queryCount(StorageFileInfoDO storageFileInfo);
 	
 	/*****
 	 * 修改用户的图片分类
 	 * @param imageCategoryId  旧的图片分类分类
 	 * @param imageCategoryIdMod 新的图片分类分类
 	 * @param memberId  用户id
 	 * @return 是否更新成功
 	 */
	public boolean updateStorageFileInfo(Long imageCategoryId,Long imageCategoryIdMod,Long memberId);
	
	
	/***
	 * 统计图片id集合的总容量
	 * @param idList  图片编号集合
	 * @return
	 */
	public Map<Long,Long> queryStorageFileSizeByIds(List<Long> idList,Long userId);
	
	/***
	 * 根据图片编号批量删除图片信息
	 * @param idList 图片编号集合
	 * @return
	 */
	public boolean deleteStorageFileByIds(List<Long> idList,Long userId);
	
	/***
	 * 统计某分类下的总容量
	 * @param imageCategoryId 图片分类id集合
	 * @return
	 */
	public Long queryStorageFileSize(List<Long> imageCategoryId,Long userId);
	
	/****
	 * 根据分类查询分类下的图片信息
	 * @param imageCategoryList 图片分类id集合
	 * @return
	 */
	public List<StorageFileInfoDO> queryStorageFileByCategoryIds(List<Long> imageCategoryList,Long userId);
	
	/*****
	 * 根据编号id查询图片信息
	 * @param idList 图片id集合
	 * @return
	 */
	public List<StorageFileInfoDO> queryStorageFileByIds(List<Long> idList,Long userId);
	
	
	/*****
 	 * 将某一组分类下的所有图片移动到某分类下
 	 * @param tocategoryId  分类id
 	 * @param fromCategoryIdList 分类idList
 	 * @param memberId  用户id
 	 * @return
 	 */
	public boolean updateStorageFileInfo(Long tocategoryId,List<Long> fromCategoryIdList,Long memberId);
	
	/********
	 * 根据图片编号修改图片名称
	 * @param fileName  图片名称
	 * @param id   图片编号
	 * @return
	 */
	public boolean updateStorageFileInfoByFileName(String fileName,Long id,Long userId);
	
	
}
