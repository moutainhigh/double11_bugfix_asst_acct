/**
 * 
 */
package com.yuwang.pinju.core.storage.manager.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.storage.dao.FileInfoDAO;
import com.yuwang.pinju.core.storage.manager.FileInfoManager;
import com.yuwang.pinju.domain.storage.FileInfoDO;

/**
 * @author yejingfeng
 * 
 */
public class FileInfoManagerImpl extends BaseManager implements FileInfoManager {
	protected final Log log = LogFactory.getLog(getClass());
	private FileInfoDAO fileInfoDAO;

	public FileInfoDAO getFileInfoDAO() {
		return fileInfoDAO;
	}

	public void setFileInfoDAO(FileInfoDAO fileInfoDAO) {
		this.fileInfoDAO = fileInfoDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.storage.manager.FileInfoManager#addFileInfo(long,
	 * java.lang.String, java.lang.String, long)
	 */
	@Override
	public void addFileInfo(long memberId, String fileName, String path,
			long length) throws ManagerException {
		FileInfoDO fileInfoDO = new FileInfoDO();
		fileInfoDO.setMemberId(memberId);
		if (fileName.startsWith("/")) {
			fileName = fileName.substring(1, fileName.length());
		}
		fileInfoDO.setName(fileName);
		fileInfoDO.setPath(path);
		fileInfoDO.setType(1);
		fileInfoDO.setGmtCreate(DateUtil.current());
		fileInfoDO.setGmtModified(DateUtil.current());
		fileInfoDO.setSize(length);
		try {
			fileInfoDAO.insertFileInfo(fileInfoDO);
		} catch (DaoException e) {
			log.error(e, e);
			throw new ManagerException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.storage.manager.FileInfoManager#deleteFileInfo(
	 * long, java.lang.String)
	 */
	@Override
	public void deleteFileInfo(long memberId, String fileName)
			throws ManagerException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.storage.manager.FileInfoManager#getFileInfoByMemberId
	 * (java.lang.Long)
	 */
	@Override
	public List<FileInfoDO> getFileInfoByMemberId(Long memberId)
			throws ManagerException {
		try {
			return fileInfoDAO.getFileInfoByMemberId(memberId);
		} catch (DaoException e) {
			log.error(e, e);
			throw new ManagerException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.storage.manager.FileInfoManager#addFileInfos(long,
	 * java.lang.String[], java.io.File[])
	 */
	@Override
	public void addFileInfos(long memberId, String[] retFileNames, File[] files)
			throws ManagerException {
		try {
			for (int i = 0; i < retFileNames.length; i++) {
				try {
					if (null != retFileNames[i]) {
						int pos = retFileNames[i].lastIndexOf("/");
						String fullFileName = retFileNames[i]
								.substring(pos + 1);
						String path = retFileNames[i].substring(0, pos + 1);
						addFileInfo(memberId, fullFileName, path, files[i]
								.length());
					}
				} catch (Exception e) {
					log.error(e, e);
				}
			}
		} catch (Exception e) {
			log.error(e, e);
			throw new ManagerException(e);
		}

	}
}
