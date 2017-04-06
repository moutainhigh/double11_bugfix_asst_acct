package com.yuwang.pinju.core.image.manager;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;

public interface StorageFileInfoManager {
	/**
	 * 根据条件查询图片信息
	 * @param StorageFileInfo
	 * @return
	 * @throws ManagerException
	 */
	public List<StorageFileInfoDO> getStorageFileInfoListByCon(StorageFileInfoDO storageFileInfo) throws ManagerException;
	
	/**
	 * 批量插入图片信息 一个失败全部失败
	 * @param fileInfoList
	 * @return
     * @throws ManagerException
	 */
	public String[] insertStorageFileInfo(File[] files,String[] fileNames,Long memberId,String memberName,Long categoryId) throws ManagerException;
	
	/**
	 * 批量插入图片信息 一个失败不影响其他
	 * @param fileInfoList
	 * @return
     * @throws ManagerException
	 */
	public String[] insertStorageFileInfoExt(File[] files,String[] fileNames,Long memberId,String memberName,Long categoryId) throws ManagerException;
	
	/**
	 * 批量插入商品或装修分类下图片信息  一个失败全部失败
	 * @param files
	 * @param fileNames
	 * @param memberId
	 * @param memberName
	 * @param type 1商品 2装修 3默认
	 * @throws ManagerException
	 */
	public String[] insertStorageFileInfo(File[] files,String[] fileNames,Long memberId,String memberName,int type) throws ManagerException;
	
	/**
	 * 批量插入商品或装修分类下图片信息  一个失败不影响其他
	 * @param files
	 * @param fileNames
	 * @param memberId
	 * @param memberName
	 * @param type 1商品 2装修 3默认
	 * @throws ManagerException
	 */
	public String[] insertStorageFileInfoExt(File[] files,String[] fileNames,Long memberId,String memberName,int type) throws ManagerException;
	
	
	/*****
	 * 将某一组图片id集体移动到另一个图片分类下
	 * @param imageCategoryId  图片分类id
	 * @param idList  图片编号集合
	 * @return
	 * @throws DaoException
	 */
	public boolean updateStorageFileInfo(Long imageCategoryId,Long userId,List<Long> idList) throws ManagerException;
	
	/***
	 * 更新图片信息
	 * @param StorageFileInfo
	 * @return
	 * @throws ManagerException
	 */
	public boolean updateStorageFileInfo(StorageFileInfoDO storageFileInfo)throws ManagerException;
	
	
	/****
	 * 根据Id查询图片详情
	 * @param id 图片编号
	 * @return
	 * @throws ManagerException
	 */
	public StorageFileInfoDO queryStorageFileInfo(Long id,Long userId) throws ManagerException;
	
	/****
	 * 批量删除分类下的所有图片
	 * @param imageCategoryIds 图片分类id集合
	 * @return
	 * @throws ManagerException
	 */
	public boolean deleteFileByCategoryId(List<Long> imageCategoryIds,Long userId)throws ManagerException;
	
	
	/******
     * 根据条件查询出总条数
     * @param storageFileInfo
     * @return  总条数
     * @throws ManagerException
     */
 	public Integer queryCount(StorageFileInfoDO storageFileInfo)throws ManagerException;
 	
 	/*****
 	 * 修改用户的图片分类
 	 * @param imageCategoryId  旧的图片分类分类
 	 * @param imageCategoryIdMod 新的图片分类分类
 	 * @param memberId  用户id
 	 * @return
 	 * @throws ManagerException
 	 */
	public boolean updateStorageFileInfo(Long imageCategoryId,Long imageCategoryIdMod,Long memberId) throws ManagerException;
	
	/***
	 * 统计id集合的总容量
	 * @param idList 图片编号集合
	 * @return
	 * @throws ManagerException
	 */
	public Map<Long,Long> queryStorageFileSizeByIds(List<Long> idList,Long userId) throws ManagerException;
	
	/***
	 * 根据Id删除图片信息
	 * @param idList 图片编号集合
	 * @return
	 * @throws ManagerException
	 */
	public boolean deleteStorageFileByIds(List<Long> idList,Long userId) throws ManagerException;
	
	/***
	 * 统计分类集合下的总容量
	 * @param imageCategoryId 图片分类id集合 
	 * @return
	 * @throws ManagerException
	 */
	public Long queryStorageFileSize(List<Long> imageCategoryId,Long userId) throws ManagerException;
	
	/****
	 * 根据分类查询分类下的图片信息
	 * @param imageCategoryList
	 * @return
	 * @throws ManagerException
	 */
	public List<StorageFileInfoDO> queryStorageFileByCategoryIds(List<Long> imageCategoryList,Long userId) throws ManagerException;
	
	/*****
	 * 根据编号id批量查询图片信息
	 * @param idList 图片编号集合
	 * @return 图片信息集合
	 * @throws ManagerException
	 */
	public List<StorageFileInfoDO> queryStorageFileByIds(List<Long> idList,Long userId) throws ManagerException;
	
	/**
	 * 根据条件查询图片信息 并 转成JSON String
	 * @param StorageFileInfo 图片空间DO
	 * @return JSON String
	 */
	public String getStorageFileInfoListByConToJson(StorageFileInfoDO storageFileInfo) throws ManagerException;
	
	/*****
 	 * 讲某一组分类下的所有图片移动到某分类下
 	 * @param tocategoryId  分类id
 	 * @param fromCategoryIdList 分类idList
 	 * @param memberId  用户id
 	 * @return
 	 * @throws ManagerException
 	 */
	public boolean updateStorageFileInfo(Long tocategoryId,List<Long> fromCategoryIdList,Long memberId) throws ManagerException;
	
	/**********
	 * 根据图片编号修改图片名称
	 * @param fileName  图片名称
	 * @param id  图片编号
	 * @return
	 * @throws ManagerException
	 */
	public boolean updateStorageFileInfoByFileName(String fileName,Long id,Long userId)throws ManagerException;
}


