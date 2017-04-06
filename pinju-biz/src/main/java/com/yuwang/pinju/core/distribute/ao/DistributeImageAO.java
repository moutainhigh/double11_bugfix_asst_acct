/**
 * 分销系统二期需求，分享购
 */
package com.yuwang.pinju.core.distribute.ao;

import java.io.File;

import com.yuwang.pinju.core.common.Result;

/**
 * @author liyouguo
 * 
 * @since 2011-10-24
 */
public interface DistributeImageAO {
	public class DistributeImage {
		public final static int IMAGE_TYPE_JPG = 1;
		public final static int IMAGE_TYPE_GIF = 2;
		public final static int IMAGE_TYPE_PNG = 3;
		public final static int IMAGE_TYPE_BMP = 4;

		/**
		 * 图片类型
		 */
		private int type;
		/**
		 * 图片宽度
		 */
		private int width;
		/**
		 * 图片高度
		 */
		private int height;
		/**
		 * 图片大小
		 */
		private long size;

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public DistributeImage(int type, int width, int height) {
			this.type = type;
			this.width = width;
			this.height = height;
		}

		public DistributeImage(int width, int height) {
			// TODO Auto-generated constructor stub
			this.width = width;
			this.height = height;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}

	/**
	 * 根据上传的文件获取图片相关信息
	 * 
	 * @param file
	 *            --上传的文件
	 * @return
	 */
	public DistributeImage getImageInfo(File file);

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
			int maxHeight, int minHeight);

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
	public Result checkImageInfo(File file, int width, int height, int size);

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
			String nickName);
}
