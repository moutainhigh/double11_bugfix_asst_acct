/**
 * 
 */
package com.yuwang.pinju.core.storage.ao;

import java.io.File;

import com.yuwang.pinju.core.common.ResultSupport;

/**
 * @author yejingfe 文件上传业务处理
 * 
 */
public interface FileUploadAO {

	/**
	 * 保存商品图片
	 * 
	 * @param files
	 * @return
	 */
	public ResultSupport saveItemPics(File[] files, String[] fileFileName,
			Long memberId, String nickName);

	/**
	 * 保存店铺图片
	 * 
	 * @param files
	 * @return
	 */
	public ResultSupport saveShopPics(File[] files);

	/**
	 * 保存维权图片
	 * 
	 * @param files
	 * @return
	 */
	public ResultSupport saveUserPics(File[] files, String[] fileNames,
			Long memberId, String nickName);

}
