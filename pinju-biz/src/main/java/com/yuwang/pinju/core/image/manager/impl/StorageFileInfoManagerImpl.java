package com.yuwang.pinju.core.image.manager.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import magick.ImageInfo;
import magick.MagickImage;
import net.sf.json.JSONObject;

import com.yuwang.pinju.Constant.ImagesConstants;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.images.ImagesCategoryConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.image.dao.ImagesCategoryDAO;
import com.yuwang.pinju.core.image.dao.StorageFileInfoDAO;
import com.yuwang.pinju.core.image.manager.ImagesCategoryManager;
import com.yuwang.pinju.core.image.manager.StorageFileInfoManager;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.domain.images.ImagesQueryDO;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;

public class StorageFileInfoManagerImpl implements StorageFileInfoManager {
	
	static {
		if (System.getProperty("jmagick.systemclassloader") == null) {
			System.setProperty("jmagick.systemclassloader", "no");
		}
		try {
			MagickImage.class.getClass();
		} catch (Error e) {
			System.err.println("Make sure JMagick libraries are available in java.library.path. Current value: ");
			System.err.println("java.library.path=" + System.getProperty("java.library.path"));
			throw e;
		}
	}

	private StorageFileInfoDAO storageFileInfoDAO;
	private FileStorageManager fileStorageManager;
	private ImagesCategoryDAO imagesCategoryDAO;
	private ImagesCategoryManager imagesCategoryManager;
	
	/**
	 * 子账户日志
	 */
	private MemberAsstLog memberAsstLog;
	
	public FileStorageManager getFileStorageManager() {
		return fileStorageManager;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

	public StorageFileInfoDAO getStorageFileInfoDAO() {
		return storageFileInfoDAO;
	}

	public void setStorageFileInfoDAO(StorageFileInfoDAO storageFileInfoDAO) {
		this.storageFileInfoDAO = storageFileInfoDAO;
	}

	public ImagesCategoryDAO getImagesCategoryDAO() {
		return imagesCategoryDAO;
	}

	public void setImagesCategoryDAO(ImagesCategoryDAO imagesCategoryDAO) {
		this.imagesCategoryDAO = imagesCategoryDAO;
	}

	public ImagesCategoryManager getImagesCategoryManager() {
		return imagesCategoryManager;
	}

	public void setImagesCategoryManager(ImagesCategoryManager imagesCategoryManager) {
		this.imagesCategoryManager = imagesCategoryManager;
	}

	/**
	 * 根据条件查询图片信息
	 * @param StorageFileInfo
	 * @return
	 * @throws ManagerException
	 */
	public List<StorageFileInfoDO> getStorageFileInfoListByCon(StorageFileInfoDO storageFileInfo) throws ManagerException {
		try {
			return storageFileInfoDAO.getStorageFileInfoListByCon(storageFileInfo);
		} catch (DaoException e) {
			throw new ManagerException("StorageFileInfoManagerImpl查询图片信息出错",e);
		}
	}

	/***
	 * 更新图片信息
	 * @param StorageFileInfo
	 * @return
	 * @throws ManagerException
	 */
	public boolean updateStorageFileInfo(StorageFileInfoDO storageFileInfo) throws ManagerException {
		try {
			 storageFileInfoDAO.updateStorageFileInfo(storageFileInfo);
			 return true;
		} catch (DaoException e) {
			throw new ManagerException("StorageFileInfoManagerImpl更新图片信息出错",e);
		}
	}
   
	
	/****
	 * 根据Id查询图片详情
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public StorageFileInfoDO queryStorageFileInfo(Long id,Long userId)throws ManagerException {
		try {
			return storageFileInfoDAO.queryStorageFileInfo(id,userId);
		} catch (DaoException e) {
			throw new ManagerException("StorageFileInfoManagerImpl查看图片详情出错",e);
		}
	}

	/***
	 * 批量插入图片信息,一个失败全部失败
	 * @param fileInfoList
	 * @throws ManagerException
	 */
	public String[] insertStorageFileInfo(File[] files,String[] fileNames,Long memberId,String memberName,Long categoryId) throws ManagerException {
		try {
			List<StorageFileInfoDO> fileInfoList = new Vector<StorageFileInfoDO>();
			long size = 0;
			int i = 0;
			for(String fileName:fileNames){
				fileNames[i] = FileSecurityUtils.getImageFileName(files[i], fileNames[i]);
				StorageFileInfoDO storageFileInfoDO = new StorageFileInfoDO();
				storageFileInfoDO.setImageCategoryId(categoryId);
				if(fileName!=null  && !fileName.isEmpty() && fileName.length()>20){
					storageFileInfoDO.setFileName(fileName.substring(0, 20));
				}else{
					storageFileInfoDO.setFileName(fileName);
				}
				storageFileInfoDO.setMemberId(memberId);
				storageFileInfoDO.setMemberName(memberName);
				storageFileInfoDO.setSize(files[i].length());
				storageFileInfoDO.setGmtModified(new Date());
				storageFileInfoDO.setGmtCreate(new Date());
				storageFileInfoDO.setType(1);
				storageFileInfoDO.setStatus(1);
				
				ImageInfo imgInfo = new ImageInfo(files[i].getAbsolutePath());
				MagickImage magic = new MagickImage(imgInfo);
	            int width = (int) magic.getDimension().getWidth();
	            int height = (int) magic.getDimension().getHeight();
	            storageFileInfoDO.setDimension(width+"X"+height);
	            
//				Image image = ImageIO.read(files[i]);
//				storageFileInfoDO.setDimension(image.getWidth(null)+"X"+image.getHeight(null));
				
				fileInfoList.add(storageFileInfoDO);
				i++;
			}
			
			ImagesCategoryDO imagesCategoryDO = imagesCategoryManager.getImagesCategoryObject(memberId);
			if((size + imagesCategoryDO.getUserSize())>ImagesConstants.userMaxSize){
				return null;
			}
			
			String[] names = fileStorageManager.saveImage(files, fileNames, memberId, memberName, true);
			i = 0;
			for(StorageFileInfoDO storageFileInfoDO:fileInfoList){
				if(names[i] == null) return null;
				int pos = names[i].lastIndexOf("/");
				String fullFileName = names[i]
						.substring(pos + 1);
				String path = names[i].substring(0, pos + 1);
				
				storageFileInfoDO.setName(fullFileName);
				storageFileInfoDO.setPath(path);
				size += files[i].length();
				i++;
			}
			
			storageFileInfoDAO.insertStorageFileInfo(fileInfoList);
			imagesCategoryManager.updateUserSize(memberId, size, "1");
			return names;
		} catch (Exception e) {
			throw new ManagerException("StorageFileInfoManagerImpl批量插入图片信息出错",e);
		}
	}
	
	/***
	 * 批量插入图片信息 一个失败不影响其他
	 * @param fileInfoList
	 * @throws ManagerException
	 */
	public String[] insertStorageFileInfoExt(File[] files,String[] fileNames,Long memberId,String memberName,Long categoryId) throws ManagerException {
		try {
			List<StorageFileInfoDO> fileInfoList = new Vector<StorageFileInfoDO>();
			List<StorageFileInfoDO> fileInfoListTemp = new Vector<StorageFileInfoDO>();
			long size = 0;
			int i = 0;
			for(String fileName:fileNames){
				fileNames[i] = FileSecurityUtils.getImageFileName(files[i], fileNames[i]);
				StorageFileInfoDO storageFileInfoDO = new StorageFileInfoDO();
				storageFileInfoDO.setImageCategoryId(categoryId);
				if(fileName!=null  && !fileName.isEmpty() && fileName.length()>20){
					storageFileInfoDO.setFileName(fileName.substring(0, 20));
				}else{
					storageFileInfoDO.setFileName(fileName);
				}
				storageFileInfoDO.setMemberId(memberId);
				storageFileInfoDO.setMemberName(memberName);
				storageFileInfoDO.setSize(files[i].length());
				storageFileInfoDO.setGmtModified(new Date());
				storageFileInfoDO.setGmtCreate(new Date());
				storageFileInfoDO.setType(1);
				storageFileInfoDO.setStatus(1);
				
				ImageInfo imgInfo = new ImageInfo(files[i].getAbsolutePath());
				MagickImage magic = new MagickImage(imgInfo);
	            int width = (int) magic.getDimension().getWidth();
	            int height = (int) magic.getDimension().getHeight();
	            storageFileInfoDO.setDimension(width+"X"+height);
	            
//				Image image = ImageIO.read(files[i]);
//				storageFileInfoDO.setDimension(image.getWidth(null)+"X"+image.getHeight(null));

				fileInfoListTemp.add(storageFileInfoDO);
			    
				i++;
				//子账户日志
				memberAsstLog.log("pic", "pic", "上传图片: "+ fileName);
			}
			
			ImagesCategoryDO imagesCategoryDO = imagesCategoryManager.getImagesCategoryObject(memberId);
			if((size + imagesCategoryDO.getUserSize())>ImagesConstants.userMaxSize){
				return null;
			}
			
			String[] names = fileStorageManager.saveImage(files, fileNames, memberId, memberName, true);
			
			i = 0;
			for(StorageFileInfoDO storageFileInfoDO:fileInfoListTemp){
				if(names[i] == null) continue;
				int pos = names[i].lastIndexOf("/");
				String fullFileName = names[i]
						.substring(pos + 1);
				String path = names[i].substring(0, pos + 1);
				storageFileInfoDO.setName(fullFileName);
				storageFileInfoDO.setPath(path);
				fileInfoList.add(storageFileInfoDO);
				size += files[i].length();
				i++;
			}
			
			storageFileInfoDAO.insertStorageFileInfo(fileInfoList);
			imagesCategoryManager.updateUserSize(memberId, size, "1");
			return names;
		} catch (Exception e) {
			throw new ManagerException("StorageFileInfoManagerImpl批量插入图片信息出错",e);
		}
	}
	
	/**
	 * 批量插入商品或装修分类下图片信息 一个失败全部失败
	 * @param files
	 * @param fileNames
	 * @param memberId
	 * @param memberName
	 * @param type 1商品 2装修 3默认
	 * @throws ManagerException
	 */
	public String[] insertStorageFileInfo(File[] files,String[] fileNames,Long memberId,String memberName,int type) throws ManagerException {
		try {
			Long categoryId = null;
			ImagesCategoryDO imagesCategoryDO = imagesCategoryManager.getImagesCategory(memberId, memberName, "1").get(0);
			if(type==1){
				categoryId = imagesCategoryDO.getCategoryIdByFirstName(ImagesCategoryConstant.MOREN_THREE);
			}else if(type==2){
				categoryId = imagesCategoryDO.getCategoryIdByFirstName(ImagesCategoryConstant.MOREN_TWO);
			}else if(type==3){
				categoryId = imagesCategoryDO.getCategoryIdByFirstName(ImagesCategoryConstant.MOREN_ONE);
			}
			if(categoryId==null) throw new ManagerException("未获得商品分类ID");
			return insertStorageFileInfo(files,fileNames,memberId,memberName,categoryId);
		} catch (Exception e) {
			throw new ManagerException("StorageFileInfoManagerImpl批量插入图片信息出错",e);
		}
	}
	
	/**
	 * 批量插入商品或装修分类下图片信息 一个失败不影响其他
	 * @param files
	 * @param fileNames
	 * @param memberId
	 * @param memberName
	 * @param type 1商品 2装修 3默认
	 * @throws ManagerException
	 */
	public String[] insertStorageFileInfoExt(File[] files,String[] fileNames,Long memberId,String memberName,int type) throws ManagerException {
		try {
			Long categoryId = null;
			ImagesCategoryDO imagesCategoryDO = imagesCategoryManager.getImagesCategory(memberId, memberName, "1").get(0);
			if(type==1){
				categoryId = imagesCategoryDO.getCategoryIdByFirstName(ImagesCategoryConstant.MOREN_THREE);
			}else if(type==2){
				categoryId = imagesCategoryDO.getCategoryIdByFirstName(ImagesCategoryConstant.MOREN_TWO);
			}else if(type==3){
				categoryId = imagesCategoryDO.getCategoryIdByFirstName(ImagesCategoryConstant.MOREN_ONE);
			}
			if(categoryId==null) throw new ManagerException("未获得商品分类ID");
			return insertStorageFileInfoExt(files,fileNames,memberId,memberName,categoryId);
		} catch (Exception e) {
			throw new ManagerException("StorageFileInfoManagerImpl批量插入图片信息出错",e);
		}
	}
	
	/*****
	 * 根据id修改图片分类
	 * @param imageCategoryId 修改成图片分类id
	 * @param idList 需要修改的图片编号集合
	 * @return
	 * @throws ManagerException
	 */
	public boolean updateStorageFileInfo(Long imageCategoryId,Long userId,List<Long> idList) throws ManagerException {
		try {
		   storageFileInfoDAO.updateStorageFileInfo(imageCategoryId,userId,idList);
		   return true;
		} catch (DaoException e) {
			throw new ManagerException("StorageFileInfoManagerImpl根据Id修改图片所属分类出错",e);
		}
	}
	
	/****
	 * 批量删除分类下的所有图片
	 * @param imageCategoryIds 图片分类id集合
	 * @return 是否删除成功
	 * @throws ManagerException
	 */
	public boolean deleteFileByCategoryId(List<Long> querySizeById,Long userId)throws ManagerException{
		try {
//			List<Long> idList = new Vector<Long>();
			//不做物理删除
//			List<StorageFileInfoDO> fileList = queryStorageFileByCategoryIds(imageCategoryIds);
//			for(StorageFileInfoDO storageFileInfoDO:fileList){
//				idList.add(storageFileInfoDO.getId());
//				String fullName = storageFileInfoDO.getPath()+storageFileInfoDO.getName();
//				fileStorageManager.deleteFileByName(fullName);
//			}
//			if(idList.size()==0) return true;
			Long size =queryStorageFileSize(querySizeById,userId);
//			Set<Long> set = map.keySet();
//			Iterator<Long> it = set.iterator();
//			while(it.hasNext()){
//				Long memberId = it.next();
				imagesCategoryManager.updateUserSize(userId, size, "0");
//			}
			storageFileInfoDAO.deleteFileByCategoryId(querySizeById,userId);
			return true;
		} catch (DaoException e) {
			throw new ManagerException("StorageFileInfoManagerImpl批量删除某个图片分类下的所有图片出错",e);
		}
	}
	
	/******
     * 根据条件查询出总条数
     * @param storageFileInfo
     * @return  总条数
     * @throws ManagerException
     */
 	public Integer queryCount(StorageFileInfoDO storageFileInfo)throws ManagerException{
 		try {
			 return storageFileInfoDAO.queryCount(storageFileInfo);
		} catch (DaoException e) {
			throw new ManagerException("查询图片总记录数出错",e);
		}	
 	}
 	
 	/*****
 	 * 修改用户的图片分类
 	 * @param imageCategoryId  旧的图片分类分类
 	 * @param imageCategoryIdMod 新的图片分类分类
 	 * @param memberId  用户id
 	 * @return
 	 * @throws ManagerException
 	 */
	public boolean updateStorageFileInfo(Long imageCategoryId,Long imageCategoryIdMod,Long memberId) throws ManagerException{
		try {
			  storageFileInfoDAO.updateStorageFileInfo(imageCategoryId, imageCategoryIdMod, memberId);
			  return true;
		} catch (DaoException e) {
			throw new ManagerException("修改用户的图片分类出错",e);
		}	
	}
	
	/***
	 * 统计id集合的总容量
	 * @param idList 图标编号集合
	 * @return
	 * @throws ManagerException
	 */
	public Map<Long,Long> queryStorageFileSizeByIds(List<Long> idList,Long userId) throws ManagerException{
		try {
			 Map<Long,Long> map=new HashMap<Long, Long>();
			 List<StorageFileInfoDO>  storageList=storageFileInfoDAO.queryStorageFileSizeByIds(idList,userId);
			 for(StorageFileInfoDO storageFileInfo:storageList){
				 map.put(storageFileInfo.getMemberId(),storageFileInfo.getSize());
			 }
			 return map;
		} catch (DaoException e) {
			throw new ManagerException("统计id集合的总容量出错",e);
		}
	}
	
	/***
	 * 根据id集合删除图片信息
	 * @param idList 图片编号集合
	 * @return
	 * @throws ManagerException
	 */
	public boolean deleteStorageFileByIds(List<Long> idList,Long userId) throws ManagerException{
		try {
			//不做物理删除
//			List<StorageFileInfoDO> storageFileInfoDOList = queryStorageFileByIds(idList);
//			for(StorageFileInfoDO storageFileInfoDO:storageFileInfoDOList){
//				String fullName = storageFileInfoDO.getPath()+storageFileInfoDO.getName();
//				fileStorageManager.deleteFileByName(fullName);
//			}
			Map<Long,Long> map = queryStorageFileSizeByIds(idList,userId);
			Set<Long> set = map.keySet();
			Iterator<Long> it = set.iterator();
			while(it.hasNext()){
				Long memberId = it.next();
				imagesCategoryManager.updateUserSize(memberId, map.get(memberId), "0");
			}
			 storageFileInfoDAO.deleteStorageFileByIds(idList,userId);
			 return true;
		} catch (DaoException e) {
			throw new ManagerException("根据id集合删除图片信息出错",e);
		}catch(Exception e){
			throw new ManagerException("根据id集合删除图片信息出错",e);
		}
	}
	
	/***
	 * 统计分类下的总容量
	 * @param imageCategoryId 图片分类集合
	 * @return  图片总容量
	 * @throws ManagerException
	 */
	public Long queryStorageFileSize(List<Long> imageCategoryId,Long userId) throws ManagerException{
		try {
			 return storageFileInfoDAO.queryStorageFileSize(imageCategoryId,userId);
		} catch (DaoException e) {
			throw new ManagerException("查询图片总记录数出错",e);
		}
	}
	
	/****
	 * 根据分类查询分类下的图片信息
	 * @param imageCategoryList 图片分类id集合
	 * @return   图片信息集合
	 * @throws ManagerException
	 */
	public List<StorageFileInfoDO> queryStorageFileByCategoryIds(List<Long> imageCategoryList,Long userId) throws ManagerException{
		try {
			return storageFileInfoDAO.queryStorageFileByCategoryIds(imageCategoryList,userId);
		} catch (DaoException e) {
			throw new ManagerException("StorageFileInfoManagerImpl查询图片信息出错",e);
		}
	}
	
	
	/*****
	 * 根据编号id查询图片信息
	 * @param idList
	 * @return
	 * @throws ManagerException
	 */
	public List<StorageFileInfoDO> queryStorageFileByIds(List<Long> idList,Long userId) throws ManagerException{
		try {
			return storageFileInfoDAO.queryStorageFileByIds(idList,userId);
		} catch (DaoException e) {
			throw new ManagerException("StorageFileInfoManagerImpl查询图片信息出错",e);
		}
	}
	
	/**
	 * 根据条件查询图片信息 并 转成JSON String
	 * @param StorageFileInfo 图片空间DO
	 * @return JSON String
	 */
	@Override
	public String getStorageFileInfoListByConToJson(StorageFileInfoDO storageFileInfo) throws ManagerException {
		List<StorageFileInfoDO> list = getStorageFileInfoListByCon(storageFileInfo);
		List<ImagesQueryDO> paramList = new ArrayList<ImagesQueryDO>();
		int count = 0;
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				ImagesQueryDO imagesQueryDO = new ImagesQueryDO();
				StorageFileInfoDO storageFileInfoDO = list.get(i);
				imagesQueryDO.setId(storageFileInfoDO.getId().toString());
				imagesQueryDO.setFilename(storageFileInfoDO.getFileName());
				imagesQueryDO.setFile_url(PinjuConstant.VIEW_IMAGE_SERVER+storageFileInfoDO.getPath()+storageFileInfoDO.getName());
				imagesQueryDO.setFilesize(storageFileInfoDO.getSize());
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				imagesQueryDO.setDatetime(format.format(storageFileInfoDO.getGmtCreate()));
				paramList.add(imagesQueryDO);
			}
			count = queryCount(storageFileInfo);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("file_list", paramList);
		jsonObject.put("total_count", count);
		return jsonObject.toString();
	}
	
	/*****
 	 * 将某一组分类下的所有图片移动到某分类下
 	 * @param tocategoryId  分类id
 	 * @param fromCategoryIdList 分类idList
 	 * @param memberId  用户id
 	 * @return
 	 * @throws ManagerException
 	 */
	public boolean updateStorageFileInfo(Long tocategoryId,List<Long> fromCategoryIdList,Long memberId) throws ManagerException{
		try {
		 storageFileInfoDAO.updateStorageFileInfo(tocategoryId,fromCategoryIdList,memberId);
			return true;
		} catch (DaoException e) {
			throw new ManagerException("将某一组分类下的所有图片移动到某分类下出错",e);
		}
	}
	
	/**********
	 * 根据图片编号修改图片名称
	 * @param fileName  图片名称
	 * @param id  图片编号
	 * @return
	 * @throws ManagerException
	 */
	public boolean updateStorageFileInfoByFileName(String fileName,Long id,Long userId)throws ManagerException{
		try {
			storageFileInfoDAO.updateStorageFileInfoByFileName(fileName,id,userId);
			return true;
		} catch (DaoException e) {
			throw new ManagerException("修改图片名称出错",e);
		}
	}

	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
}
