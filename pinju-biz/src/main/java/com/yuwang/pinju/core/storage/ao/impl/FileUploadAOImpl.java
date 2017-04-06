/**
 * 
 */
package com.yuwang.pinju.core.storage.ao.impl;

import java.io.File;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.storage.ao.FileUploadAO;
import com.yuwang.pinju.core.storage.manager.FileInfoManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.user.ao.BaseAO;

/**
 * @author yejingfeng
 * 
 */
public class FileUploadAOImpl extends BaseAO implements FileUploadAO {
	private FileStorageManager fileStorageManager;
	private FileInfoManager fileInfoManager;

	@Override
	public ResultSupport saveItemPics(File[] files, String[] fileNames,
			Long memberId, String nickName) {
		ResultSupport result = new ResultSupport();
		try {
			// 保存图片至图片服务器，生成缩略图
			String[] retFileNames = fileStorageManager.saveImage(files,
					fileNames, memberId, nickName, true);
			// 记录会员与图片的关系
			fileInfoManager.addFileInfos(memberId, retFileNames, files);
			result.setSuccess(true);
			result.getModels().put("fileNames", retFileNames);
		} catch (ManagerException e) {
			log.error(e, e);
			result.setSuccess(false);
			result.setResultCode("文件保存出错");
		}
		return result;
	}

	@Override
	public ResultSupport saveShopPics(File[] files) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSupport saveUserPics(File[] files, String[] fileNames,
			Long memberId, String nickName) {
		ResultSupport result = new ResultSupport();
		try {
			String[] retFileNames = fileStorageManager.saveImage(files,
					fileNames, memberId, nickName);
			fileInfoManager.addFileInfos(memberId, retFileNames, files);
			result.setSuccess(true);
			result.getModels().put("fileNames", retFileNames);
		} catch (ManagerException e) {
			log.error(e, e);
			result.setSuccess(false);
			result.setResultCode("文件保存出错");
		}
		return result;
	}

	public FileStorageManager getFileStorageManager() {
		return fileStorageManager;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

	public FileInfoManager getFileInfoManager() {
		return fileInfoManager;
	}

	public void setFileInfoManager(FileInfoManager fileInfoManager) {
		this.fileInfoManager = fileInfoManager;
	}
}
