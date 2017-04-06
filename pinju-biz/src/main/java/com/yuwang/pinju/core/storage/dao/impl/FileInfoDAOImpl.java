/**
 * 
 */
package com.yuwang.pinju.core.storage.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.storage.dao.FileInfoDAO;
import com.yuwang.pinju.domain.storage.FileInfoDO;

/**
 * @author yejingfeng
 * 
 */
public class FileInfoDAOImpl extends BaseDAO implements FileInfoDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.storage.dao.FileInfoDAO#deleteFileInfo(java.lang.Long)
	 */
	@Override
	public Integer deleteFileInfo(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.storage.dao.FileInfoDAO#getFileInfoByMemberId(java.lang.Long)
	 */
	@Override
	public List<FileInfoDO> getFileInfoByMemberId(Long memberId) throws DaoException {
		// TODO Auto-generated method stub
		return executeQueryForList("storage_file_info.getFileInfoByMemberId", memberId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.storage.dao.FileInfoDAO#getFileInfoByName(java.lang.Long)
	 */
	@Override
	public FileInfoDO getFileInfoByName(Long fileName) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.storage.dao.FileInfoDAO#insertFileInfo(com.yuwang.pinju.domain.storage.FileInfoDO)
	 */
	@Override
	public Long insertFileInfo(FileInfoDO fileInfoDO) throws DaoException {
		return (Long) executeInsert("storage_file_info.insertStorageFileInfo", fileInfoDO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.storage.dao.FileInfoDAO#updateFileInfo(com.yuwang.pinju.domain.storage.FileInfoDO)
	 */
	@Override
	public Integer updateFileInfo(FileInfoDO fileInfoDO) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
