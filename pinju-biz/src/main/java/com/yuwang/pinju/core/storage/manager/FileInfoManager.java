package com.yuwang.pinju.core.storage.manager;

import java.io.File;
import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.storage.FileInfoDO;

public interface FileInfoManager {
	/**
	 * 通过会员编号 获得文件列表
	 * 
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	public List<FileInfoDO> getFileInfoByMemberId(Long memberId)
			throws ManagerException;

	/**
	 * 保存会员文件上传信息
	 * 
	 * @param memberId
	 * @param fileName
	 * @param path
	 * @param length
	 * @throws ManagerException
	 */
	public void addFileInfo(long memberId, String fileName, String path,
			long length) throws ManagerException;

	/**
	 * 批量保存会员文件上传信息
	 * 
	 * @param memberId
	 * @param retFileNames图片服务器返回的带路径的全名
	 * @param files
	 * @throws ManagerException
	 */
	public void addFileInfos(long memberId, String[] retFileNames, File[] files)
			throws ManagerException;

	/**
	 * 删除会员文件信息
	 * 
	 * @param memberId
	 * @param fileName
	 * @throws ManagerException
	 */
	public void deleteFileInfo(long memberId, String fileName)
			throws ManagerException;
}
