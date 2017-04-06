/**
 * 
 */
package com.yuwang.pinju.core.storage.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.storage.FileInfoDO;

/**
 * @author yejingfeng
 * @Description 文件信息DAO
 */
public interface FileInfoDAO {
	/**
	 * 新增文件信息
	 * 
	 * @param fileInfoDO
	 * @return
	 * @throws DaoException
	 */
	public Long insertFileInfo(FileInfoDO fileInfoDO) throws DaoException;

	/**
	 * 修改文件信息
	 * 
	 * @param fileInfoDO
	 * @return
	 * @throws DaoException
	 */
	public Integer updateFileInfo(FileInfoDO fileInfoDO) throws DaoException;

	/**
	 * 删除文件信息
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public Integer deleteFileInfo(Long id) throws DaoException;

	/**
	 * 根据会员编号获取文件信息列表
	 * 
	 * @param memberId
	 * @return
	 * @throws DaoException
	 */
	public List<FileInfoDO> getFileInfoByMemberId(Long memberId) throws DaoException;

	/**
	 * 根据文件名获取文件信息
	 * 
	 * @param fileName
	 * @return
	 * @throws DaoException
	 */
	public FileInfoDO getFileInfoByName(Long fileName) throws DaoException;
}
