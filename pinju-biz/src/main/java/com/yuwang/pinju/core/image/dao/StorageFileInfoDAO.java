package com.yuwang.pinju.core.image.dao;

import java.util.List;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;

public interface StorageFileInfoDAO  {
	/**
	 * 根据条件查询图片信息
	 * @param StorageFileInfo
	 * @return
	 */
	public List<StorageFileInfoDO> getStorageFileInfoListByCon(StorageFileInfoDO storageFileInfo) throws DaoException;
	
	/**
	 * 批量插入图片信息
	 * @param fileInfoList
	 * @return
	 */
	public void insertStorageFileInfo(List<StorageFileInfoDO> fileInfoList) throws DaoException;
	
	/***
	 * 更新图片信息
	 * @param StorageFileInfo
	 * @return
	 */
	public void updateStorageFileInfo(StorageFileInfoDO storageFileInfo)throws DaoException;
	
	/****
	 * 根据Id查询图片详情
	 * @param id
	 * @return
	 */
	public StorageFileInfoDO queryStorageFileInfo(Long id,Long userId) throws DaoException;
	
	
	/*****
	 * 根据id批量修改图片分类
	 * @param imageCategoryId 图片分类id
	 * @param idList 图片编号集合
	 * @return
	 * @throws DaoException
	 */
	public void updateStorageFileInfo(Long imageCategoryId,Long userId, List<Long> idList) throws DaoException;
	
	
	/****
	 * 批量删除分类下的所有图片
	 * @param imageCategoryIds 图片分类集合
	 * @return
	 */
	public void deleteFileByCategoryId(List<Long> imageCategoryIds,Long userId)throws DaoException;
	
	
    /******
     * 根据条件查询出总条数
     * @param storageFileInfo
     * @return  总条数
     * @throws DaoException
     */
 	public Integer queryCount(StorageFileInfoDO storageFileInfo)throws DaoException;
 	
 	/*****
 	 * 修改用户的图片分类
 	 * @param imageCategoryId  旧的图片分类分类
 	 * @param imageCategoryIdMod 新的图片分类分类
 	 * @param memberId  用户id
 	 * @return
 	 * @throws ManagerException
 	 */
	public void updateStorageFileInfo(Long imageCategoryId,Long imageCategoryIdMod,Long memberId) throws DaoException;
	
	
	/***
	 * 统计id集合的总容量
	 * @param idList 图片编号集合
	 * @return
	 * @throws DaoException
	 */
	public List<StorageFileInfoDO> queryStorageFileSizeByIds(List<Long> idList,Long userId) throws DaoException;
	
	/***
	 * 根据Id批量删除图片信息
	 * @param idList 图片编号集合
	 * @return
	 * @throws DaoException
	 */
	public void deleteStorageFileByIds(List<Long> idList,Long userId) throws DaoException;
	
	/***
	 * 统计分类下的总容量
	 * @param imageCategoryIdList 图片分类集合
	 * @return
	 * @throws DaoException
	 */
	public Long queryStorageFileSize(List<Long> imageCategoryIdList,Long userId) throws DaoException;
	
	/****
	 * 根据分类查询分类下的图片信息
	 * @param imageCategoryList 图片分类集合
	 * @return
	 * @throws ManagerException
	 */
	public List<StorageFileInfoDO> queryStorageFileByCategoryIds(List<Long> imageCategoryList,Long userId) throws DaoException;
	
	/*****
	 * 根据编号id集合查询图片信息
	 * @param idList 图片编号集合
	 * @return
	 * @throws ManagerException
	 */
	public List<StorageFileInfoDO> queryStorageFileByIds(List<Long> idList,Long userId) throws DaoException;
	
	/*****
 	 * 讲某一组分类下的所有图片移动到某分类下
 	 * @param tocategoryId  分类id
 	 * @param fromCategoryIdList 分类idList
 	 * @param memberId  用户id
 	 * @return
 	 * @throws ManagerException
 	 */
	public void updateStorageFileInfo(Long tocategoryId,List<Long> fromCategoryIdList,Long memberId) throws DaoException;
	
	/******
	 * 根据图片编号查询图片名称
	 * @param fileName 图片名称
	 * @param id  图片编号
	 * @throws DaoException
	 */
	public void updateStorageFileInfoByFileName(String fileName,Long id,Long userId)throws DaoException;
}
