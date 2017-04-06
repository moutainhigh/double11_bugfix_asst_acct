package com.yuwang.api.domain;

import com.yuwang.api.common.BaseDO;

public class UploadImageDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1694095858596686303L;

	private String fileName;
	private String imageUrl;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
