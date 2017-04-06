package com.yuwang.pinju.web.module.images.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.image.ao.ImagesCategoryAO;
import com.yuwang.pinju.core.image.manager.StorageFileInfoManager;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * JSON Action
 * @author XueQi
 *
 * @since 2011-9-23
 */
public class ImageJsonActon {
	private ImagesCategoryAO imagesCategoryAO;
	public ImagesCategoryAO getImagesCategoryAO() {
		return imagesCategoryAO;
	}

	public void setImagesCategoryAO(ImagesCategoryAO imagesCategoryAO) {
		this.imagesCategoryAO = imagesCategoryAO;
	}

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * 图片空间manager
	 */
	private StorageFileInfoManager storageFileInfoManager;
	
	/**
	 * 用户id
	 */
	private Long memberId;
	
	/**
	 * 图片空间DO
	 */
	private StorageFileInfoDO storageFileInfoDO;
	
	/**
	 * 返回的JSON Sting
	 */
	private String jsonString;
	
	public String getImagesCategoryJsonString(){
		try {
			if(memberId == null){
				memberId = getUserId();
			}
			jsonString = imagesCategoryAO.getImagesCategoryToJson(memberId);
		} catch (Exception e) {
			log.info("获取图片空间图片分类JSON出错",e);
		}
		return "success";
	}
	
	public String getStorageFileInfoJsonString(){
		try {
			if (storageFileInfoDO != null) {
				if (storageFileInfoDO.getMemberId() == null) {
					storageFileInfoDO.setMemberId(getUserId());
				}
				jsonString = storageFileInfoManager.getStorageFileInfoListByConToJson(storageFileInfoDO);
			} else {
				jsonString = "";
			}
			
		} catch (ManagerException e) {
			log.info("获取图片空间分页信息JSON出错",e);
		}
		return "success";
	}
	
	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		} 
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}


	public void setStorageFileInfoManager(StorageFileInfoManager storageFileInfoManager) {
		this.storageFileInfoManager = storageFileInfoManager;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public StorageFileInfoDO getStorageFileInfoDO() {
		return storageFileInfoDO;
	}

	public void setStorageFileInfoDO(StorageFileInfoDO storageFileInfoDO) {
		this.storageFileInfoDO = storageFileInfoDO;
	}
}
