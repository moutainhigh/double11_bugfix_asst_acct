package com.yuwang.pinju.core.image.dao.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.image.dao.StorageFileInfoDAO;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;

public class StorageFileInfoDAOImpl extends BaseDAO implements StorageFileInfoDAO {

	/**
	 * 根据条件查询图片信息
	 * @param StorageFileInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StorageFileInfoDO> getStorageFileInfoListByCon(StorageFileInfoDO storageFileInfo) throws DaoException {
		// TODO Auto-generated method stub
		return super.executeQueryForList("storageFileInfo.getStorageFileList", storageFileInfo);
	}

	/**
	 * 批量插入图片信息
	 * @param fileInfoList
	 * @return
	 */
	public void insertStorageFileInfo(List<StorageFileInfoDO> fileInfoList) throws DaoException {
		// TODO Auto-generated method stub
		 super.executeBatchInsert("storageFileInfo.insertStorageFileInfo", fileInfoList,10);
	}
    
	/*****
	 * 根据图片编号批量修改图片分类
	 * @param imageCategoryId 图片分类id
	 * @param idList   图片编号集合
	 * @return
	 * @throws DaoException
	 */
	public void updateStorageFileInfo(Long imageCategoryId,Long userId, List<Long> idList) throws DaoException {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("imageCategoryId", imageCategoryId);
		map.put("memberId", userId);
		map.put("idList", idList);
		super.executeUpdate("storageFileInfo.updateImageCategoryIdById",map);
	}
	/***
	 * 更新图片信息
	 * @param storageFileInfo
	 * @return
	 */
	public void updateStorageFileInfo(StorageFileInfoDO storageFileInfo) throws DaoException {
		// TODO Auto-generated method stub
		super.executeUpdate("storageFileInfo.updateStorageFileInfo", storageFileInfo);
	}
	
	/****
	 * 根据Id查询图片详情
	 * @param id
	 * @return
	 */
	public StorageFileInfoDO queryStorageFileInfo(Long id,Long userId) throws DaoException {
		// TODO Auto-generated method stub
		Map<Object,Object> map=new Hashtable<Object,Object>();
		map.put("id", id);
		map.put("memberId", userId);
		return (StorageFileInfoDO) super.executeQueryForObject("storageFileInfo.queryStorageFileInfo", map);
	}
    
	/****
	 * 批量删除分类下的所有图片
	 * @param imageCategoryIds 图片分类集合
	 * @return
	 */
	public void deleteFileByCategoryId(List<Long> imageCategoryIds,Long userId)throws DaoException{
		Map<Object,Object> map=new Hashtable<Object,Object>();
		map.put("imageCategoryIds", imageCategoryIds);
		map.put("memberId", userId);
		super.executeUpdate("storageFileInfo.deleteFileByCategId", map);
	}
	
	 /******
     * 根据条件查询出总条数
     * @param storageFileInfo
     * @return  总条数
     * @throws DaoException
     */
 	public Integer queryCount(StorageFileInfoDO storageFileInfo)throws DaoException{
 		return (Integer) super.executeQueryForObject("storageFileInfo.queryCount", storageFileInfo);
 	}
 	
 	/*****
 	 * 修改用户的图片分类
 	 * @param imageCategoryId  旧的图片分类分类
 	 * @param imageCategoryIdMod 新的图片分类分类
 	 * @param memberId  用户id
 	 * @return
 	 * @throws ManagerException
 	 */
	@SuppressWarnings("unchecked")
	public void updateStorageFileInfo(Long imageCategoryId,Long imageCategoryIdMod,Long memberId) throws DaoException {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imageCategoryId", imageCategoryId);
		map.put("imageCategoryIdMod", imageCategoryIdMod);
		map.put("memberId", memberId);
		super.executeUpdate("storageFileInfo.updateImageCategoryId",map);
	}
	
	/*****
 	 * 讲某一组分类下的所有图片移动到某分类下
 	 * @param tocategoryId  分类id
 	 * @param fromCategoryIdList 分类idList
 	 * @param memberId  用户id
 	 * @return
 	 * @throws ManagerException
 	 */
	@SuppressWarnings("unchecked")
	public void updateStorageFileInfo(Long tocategoryId,List<Long> fromCategoryIdList,Long memberId) throws DaoException {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("tocategoryId", tocategoryId);
		map.put("fromCategoryIdList", fromCategoryIdList);
		map.put("memberId", memberId);
		super.executeUpdate("storageFileInfo.updateCateorgFileByCateorgList",map);
	}
	
	/***
	 * 统计id集合的总容量
	 * @param idList 图片编号集合
	 * @return 返回用户总图片的容量
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<StorageFileInfoDO>  queryStorageFileSizeByIds(List<Long> idList,Long userId) throws DaoException{
		Map<Object,Object> map=new Hashtable<Object,Object>();
		map.put("idList", idList);
		map.put("memberId", userId);
		return   (List<StorageFileInfoDO>) super.executeQueryForList("storageFileInfo.querySizeById",map);
	}
	
	/***
	 * 根据Id删除图片信息
	 * @param idList 图片编号集合
	 * @return
	 * @throws DaoException
	 */
	public void deleteStorageFileByIds(List<Long> idList,Long userId) throws DaoException{
		Map<Object,Object> map=new Hashtable<Object,Object>();
		map.put("idList", idList);
		map.put("memberId", userId);
		super.executeUpdate("storageFileInfo.deleteFileByIds",map);
	}
	
	/***
	 * 统计分类下的总容量
	 * @param imageCategoryList  图片分类集合
	 * @return
	 * @throws DaoException
	 */
	public Long queryStorageFileSize(List<Long> imageCategoryList,Long userId) throws DaoException{
		Map<Object,Object> map=new Hashtable<Object,Object>();
		map.put("imageCategoryList", imageCategoryList);
		map.put("memberId", userId);
		return (Long) super.executeQueryForObject("storageFileInfo.querySizeByCateoryIds",map);
	}
	
	/****
	 * 根据分类查询分类下的图片信息
	 * @param imageCategoryList 图片分类集合
	 * @return  图片信息集合
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	public List<StorageFileInfoDO> queryStorageFileByCategoryIds(List<Long> imageCategoryList,Long userId) throws DaoException{
		Map<Object,Object> map=new Hashtable<Object,Object>();
		map.put("imageCategoryList", imageCategoryList);
		map.put("memberId", userId);
		return super.executeQueryForList("storageFileInfo.queryStorageFileByCategoryIds", map);
	}
	
	/*****
	 * 根据编号id查询图片信息
	 * @param idList 图片编号集合
	 * @return  图片信息集合
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	public List<StorageFileInfoDO> queryStorageFileByIds(List<Long> idList,Long userId) throws DaoException{
		Map<Object,Object> map=new Hashtable<Object,Object>();
		map.put("idList", idList);
		map.put("memberId", userId);
		return super.executeQueryForList("storageFileInfo.queryStorageFileByIds", map);
	}
	
	/******
	 * 根据图片编号查询图片名称
	 * @param fileName 图片名称
	 * @param id  图片编号
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public void updateStorageFileInfoByFileName(String fileName,Long id,Long userId)throws DaoException{
		Map<Object,Object> map=new Hashtable<Object,Object>();
		map.put("fileName", fileName);
		map.put("id", id);
		map.put("memberId", userId);
		super.executeUpdate("storageFileInfo.updateStorageFileInfoByFileName",map);
	}
}
