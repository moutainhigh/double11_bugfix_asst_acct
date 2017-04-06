package com.yuwang.pinju.web.module.images.action;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

import com.yuwang.pinju.Constant.ImagesConstants;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.core.constant.images.ImagesCategoryConstant;
import com.yuwang.pinju.core.image.ao.ImagesCategoryAO;
import com.yuwang.pinju.core.image.ao.StorageFileInfoAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;


public class ImagesUpLoadAction extends BaseWithUserAction{
	private File[] imageFiles;
	private String[] imageFilesFileName;
	private Long categoryId;
	private StorageFileInfoAO storageFileInfoAO;
	private ImagesCategoryAO imagesCategoryAO;
	private String errorMsg;
	private List<ImagesCategoryDO> imagesList;
	private ImagesCategoryDO imagesCategoryDO;
	private int imagesCount;
	private ShopShowInfoManager shopShowInfoManager;
	protected String errorMessage="错误";
	@Override
	@AssistantPermission(target = "pic", action = "pic")
	public String execute() throws Exception {
		JSONObject jsob = new JSONObject();
		ShopInfoDO shopInfoDO = null;
		if (shopInfoDO == null && getUserId() != 0L) {
			shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(getUserId(), null);
			if(shopInfoDO != null){
				if(shopInfoDO.getApproveStatus()==null){
					jsob.put("errorMsg", "店铺不未开店!");
					errorMessage = jsob.toString();
					return "json";
//					return "NOT_OPEN";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
						|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
					jsob.put("errorMsg", "店铺已关闭!");
					errorMessage = jsob.toString();
					return "json";
//					return "IS_CLOSE";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
					jsob.put("errorMsg", "店铺不未开店!");
					errorMessage = jsob.toString();
					return "json";
//					return "NOT_OPEN";
				}
			}else{
				jsob.put("errorMsg", "店铺不存在!");
				errorMessage = jsob.toString();
				return "json";
			}
			
		}
		
		if(imageFiles ==null||imageFiles.length==0){
			jsob.put("errorMsg", "请选择需要上传的文件!");
			errorMessage = jsob.toString();
//			view();
			return "json";
		}
		if(imageFiles.length>0){
			for(File file:imageFiles){
				if(file == null){
					jsob.put("errorMsg", "上传的文件不能为空!");
					errorMessage = jsob.toString();
//					view();
					return "json";
				}
			}
		}
		for(String filename:imageFilesFileName){
			if(filename.length()>50){
				jsob.put("errorMsg", "上传的文件名称长度不能超过50个中文字符!");
				errorMessage = jsob.toString();
//				view();
				return "json";
			}
		}
		if(categoryId==null){
			jsob.put("errorMsg", "请选择分类!");
			errorMessage = jsob.toString();
//			view();
			return "json";
		}
		ImagesCategoryDO imagesCategoryDO = imagesCategoryAO.getImagesCategoryObject(getUserId());
		int size = 0;
		int j = 0;
		for(File file:imageFiles){
			// 图片类型校验，支持jpg、jpeg、gif、png
			if (!FileSecurityUtils.isImageValid(file)) {
				jsob.put("errorMsg", imageFilesFileName[j] + ":图片类型不支持。");
				errorMessage = jsob.toString();
//				view();
				return "json";
			}

			if(file.length()>ImagesCategoryConstant.FILE_SIZE){
				jsob.put("errorMsg", "上传的文件不能超过1M!");
				errorMessage = jsob.toString();
//				view();
				return "json";
			}
			size += file.length();
			j++;
		}
		
		if((size + imagesCategoryDO.getUserSize())>ImagesConstants.userMaxSize){
			jsob.put("errorMsg", "您的图片空间不足，请删除一些图片后再上传!");
			errorMessage = jsob.toString();
//			view();
			return "json";
		}
		
		String[] names = storageFileInfoAO.insertStorageFileInfo(imageFiles, imageFilesFileName, getUserId(), getNickName(), categoryId);
		if(names == null){
			jsob.put("errorMsg", "您的图片无法读取，请更换图片！");
			errorMessage = jsob.toString();
//			view();
			return "json";
		}
		for(int i=0;i<names.length;i++){
			if(names[i]==null||"".equals(names[i].trim())){
				jsob.put("errorMsg", "文件上传失败。");
				errorMessage = jsob.toString();
			}else{
				jsob.put("fileName", imageFilesFileName[i]);
				errorMessage = jsob.toString();
			}
		}
//		view();
		return "json";
	}
	
	public String view() throws Exception{
		ShopInfoDO shopInfoDO = null;
		if (shopInfoDO == null && getUserId() != 0L) {
			shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(getUserId(), null);
			if(shopInfoDO != null){
				if(shopInfoDO.getApproveStatus()==null){
					return "NOT_OPEN";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
						|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
					return "IS_CLOSE";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
					return "NOT_OPEN";
				}
			}else{
				return "NOT_EXIST";
			}
			
		}
		imagesCategoryDO = imagesCategoryAO.getImagesCategoryObject(getUserId());
		StorageFileInfoDO storageFileInfoDO = new StorageFileInfoDO();
		storageFileInfoDO.setMemberId(getUserId());
		imagesCount = storageFileInfoAO.queryCount(storageFileInfoDO);
		imagesList = imagesCategoryAO.getImagesCategory(getUserId());
		return "success";

	}

	public File[] getImageFiles() {
		return imageFiles;
	}

	public void setImageFiles(File[] imageFiles) {
		this.imageFiles = imageFiles;
	}

	public String[] getImageFilesFileName() {
		return imageFilesFileName;
	}

	public void setImageFilesFileName(String[] imageFilesFileName) {
		this.imageFilesFileName = imageFilesFileName;
	}

	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public StorageFileInfoAO getStorageFileInfoAO() {
		return storageFileInfoAO;
	}
	public void setStorageFileInfoAO(StorageFileInfoAO storageFileInfoAO) {
		this.storageFileInfoAO = storageFileInfoAO;
	}
	public ImagesCategoryAO getImagesCategoryAO() {
		return imagesCategoryAO;
	}
	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setImagesCategoryAO(ImagesCategoryAO imagesCategoryAO) {
		this.imagesCategoryAO = imagesCategoryAO;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ImagesCategoryDO getImagesCategoryDO() {
		return imagesCategoryDO;
	}

	public void setImagesCategoryDO(ImagesCategoryDO imagesCategoryDO) {
		this.imagesCategoryDO = imagesCategoryDO;
	}

	public int getImagesCount() {
		return imagesCount;
	}

	public void setImagesCount(int imagesCount) {
		this.imagesCount = imagesCount;
	}

	public List<ImagesCategoryDO> getImagesList() {
		return imagesList;
	}

	public void setImagesList(List<ImagesCategoryDO> imagesList) {
		this.imagesList = imagesList;
	}
	
}
