/**
 * 分销系统二期需求，分享购
 */
package com.yuwang.pinju.core.distribute.ao.impl;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.distribute.ao.DistributeImageAO;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.user.ao.BaseAO;

/**
 * @author liyouguo
 * 
 * @since 2011-10-24
 */
public class DistributeImageAOImpl extends BaseAO implements DistributeImageAO {
	FileStorageManager fileStorageManager;

	private final static long MAX_IMAGE_SIZE = 500 * 1024;

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

	/**
	 * 根据上传的文件获取图片相关信息
	 * 
	 * @param file
	 *            --上传的文件
	 * @return
	 */
	public DistributeImage getImageInfo(File file) {
		// TODO Auto-generated method stub
		BufferedImage src;
		try {
			src = ImageIO.read(file);
			int width = src.getWidth();
			int height = src.getHeight();
			DistributeImage image = new DistributeImage(width, height);
			return image;
		} catch (Exception e) {
			log.error("获取图片信息失败：", e);
		}
		return null;
	}

	/**
	 * 校验上传的图片是否符合要求
	 * 
	 * @param file
	 *            --上传的图片
	 * @param maxWidth
	 *            --最大支持的宽度（单位：px）
	 * @param minWidth
	 *            --最小支持的宽度（单位：px）
	 * @param maxHeight
	 *            --最大支持的高度（单位：px）
	 * @param minHeight
	 *            --最小支持的高度（单位：px）
	 * @return 返回对应的结果，如果失败，原因放到ResultCode
	 */
	public Result checkImageInfo(File file, int maxWidth, int minWidth,
			int maxHeight, int minHeight) {
		Result result = new ResultSupport();
		result.setSuccess(false);
		DistributeImage image = getImageInfo(file);
		if (image == null) {
			result.setResultCode("上传的图片无效。");
			return result;
		}

		if (!FileSecurityUtils.isImageValid(file)) {
			result.setResultCode("上传的图片类型错误（仅支持上传jpg、gif、png格式的图片）");
			return result;
		}
		
		if (file.length() > MAX_IMAGE_SIZE) {
			result.setResultCode("上传的图片太大，目前最大只限制上传：" + MAX_IMAGE_SIZE / 1024 + " KB");
			return result;
		}
		
		StringBuilder sbd = new StringBuilder();
		if (image.getWidth() > maxWidth)
			sbd.append("图片宽度超出上限(最大为:" + maxWidth + " px);");
		if (image.getWidth() < minWidth)
			sbd.append("图片宽度超出下限(最小为:" + minWidth + " px);");
		if (image.getHeight() > maxHeight)
			sbd.append("图片高度超出上限(最大为:" + maxHeight + " px);");
		if (image.getHeight() < minHeight)
			sbd.append("图片高度超出下限(最小为:" + minHeight + " px).");

		if (sbd.length() > 0) {
			result.setResultCode(sbd.toString());
		} else {
			result.setSuccess(true);
		}

		return result;
	}

	/**
	 * 校验上传的图片是否符合要求
	 * 
	 * @param file
	 *            --上传的图片
	 * @param width
	 *            --图片的宽度（单位：px）
	 * @param height
	 *            --图片的高度（单位：px）
	 * @param size
	 *            --图片的大小（单位：KB）
	 * @return 返回对应的结果，如果失败，原因放到ResultCode
	 */
	public Result checkImageInfo(File file, int width, int height, int size) {
		// TODO Auto-generated method stub
		Result result = new ResultSupport();
		result.setSuccess(false);
		DistributeImage image = getImageInfo(file);
		if (image == null) {
			result.setResultCode("上传的图片无效。");
			return result;
		}
		if (file.length() > size * 1024) {
			result.setResultCode("上传的图片太大，目前最大只限制上传：" + size / 1024 + " KB");
			return result;
		}
		if (!FileSecurityUtils.isImageValid(file)) {
			result.setResultCode("上传的图片类型错误（仅支持上传jpg、gif、png格式的图片）");
			return result;
		}
		if (image.getWidth() != width) {
			result.setResultCode("上传的图片宽度不符（仅允许宽度：" + width + " px）");
			return result;
		}
		if (image.getHeight() != height) {
			result.setResultCode("上传的图片高度不符（仅允许高度：" + height + " px）");
			return result;
		}
		result.setSuccess(true);
		return result;
	}

	/**
	 * 上传分销商分享购图片
	 * 
	 * @param file
	 *            --上传的图片
	 * @param fileName
	 *            --上传图片的文件名
	 * @param memberId
	 *            --分销商会员编号
	 * @param nickName
	 *            --分销商会员名称
	 * @return 返回图片上传后生成的文件路径
	 */
	public String uploadChannelImage(File file, String fileName, Long memberId,
			String nickName) {
		// TODO Auto-generated method stub
		try {
			String realName = FileSecurityUtils
					.getImageFileName(file, fileName);
			if (realName == null)
				return null;
			String[] imageUrls = fileStorageManager.saveImage(
					new File[] { file }, new String[] { realName }, memberId,
					nickName, false);
			if (imageUrls != null && imageUrls.length > 0)
				return imageUrls[0];
		} catch (ManagerException e) {
			log.error("上传分销商图片失败：", e);
		}
		return null;
	}
}
