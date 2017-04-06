package com.yuwang.pinju.domain.images;

import java.util.Date;

/**
 * 图片空间分类DO
 * @author 杨昭
 * @since 2011-9-21
 */
public class ImagesQueryDO implements java.io.Serializable{
	
	private static final long serialVersionUID = -3782157086364484039L;
	
	/**
	 * 文件编号
	 */
	private String id;
	/**
	 * 文件路径
	 */
	private String file_url;
	
	/**
	 * 文件名称
	 */
	private String filename;
	
	/**
	 * 文件大小
	 */
	private Long filesize;
	
	/**
	 * 建立时间
	 */
	private String datetime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String fileUrl) {
		file_url = fileUrl;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	
	
}
