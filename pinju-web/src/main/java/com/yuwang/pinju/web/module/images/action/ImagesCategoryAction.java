package com.yuwang.pinju.web.module.images.action;

import java.util.List;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.image.ao.ImagesCategoryAO;
import com.yuwang.pinju.core.image.ao.StorageFileInfoAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * 图片分类Action
 * @author 杨昭
 * @since 2011-9-21
 */
public class ImagesCategoryAction extends BaseWithUserAction{
	private ImagesCategoryAO imagesCategoryAO;
	private StorageFileInfoAO storageFileInfoAO;
	private ShopShowInfoManager shopShowInfoManager;
	protected String errorMessage="错误";
	public StorageFileInfoAO getStorageFileInfoAO() {
		return storageFileInfoAO;
	}
	public void setStorageFileInfoAO(StorageFileInfoAO storageFileInfoAO) {
		this.storageFileInfoAO = storageFileInfoAO;
	}
	public ImagesCategoryAO getImagesCategoryAO() {
		return imagesCategoryAO;
	}
	public void setImagesCategoryAO(ImagesCategoryAO imagesCategoryAO) {
		this.imagesCategoryAO = imagesCategoryAO;
	}
	//状态
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	//图片使用容量
	private double userSize;
	//使用图片张数
	private Integer imageCount;
	//页面传递的一级分类字符串
	private String firstCategory;
	//页面传递的二级分类字符串
	private String categoryList;
	public double getUserSize() {
		return userSize;
	}
	public void setUserSize(double userSize) {
		this.userSize = userSize;
	}
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
	public String getFirstCategory() {
		return firstCategory;
	}
	public void setFirstCategory(String firstCategory) {
		this.firstCategory = firstCategory;
	}
	public String getCategoryList() {
		return categoryList;
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
	public void setCategoryList(String categoryList) {
		this.categoryList = categoryList;
	}
	//页面显示用的
	private List<ImagesCategoryDO> showList;
	public List<ImagesCategoryDO> getShowList() {
		return showList;
	}
	public void setShowList(List<ImagesCategoryDO> showList) {
		this.showList = showList;
	}
	/**
	 * 跳转到图片空间页面
	 * @author 杨昭
	 * @return 
	 */
	public String toImageCategory(){
		Integer shopId=getUserShopId();
		ShopInfoDO shopInfoDO = null;
		if(shopId==null){
			   return "NOT_OPEN"; 
		}else{
			 try{
					shopInfoDO = shopShowInfoManager.queryShopInfoByShopId(shopId,ShopConstant.APPROVE_STATUS_HEGUI);
					if(shopInfoDO != null){
						errorMessage = "店铺已被关闭";
						return "IS_CLOSE";
					}
					shopInfoDO = shopShowInfoManager.queryShopInfoByShopId(shopId,ShopConstant.APPROVE_STATUS_YES);
					if(shopInfoDO==null) {
						errorMessage = "店铺不存在";
						return "NOT_EXIST";
					}
			  }catch(Exception e){
				  log.error("店铺查询报错",e);
	          }
			showList = imagesCategoryAO.getImagesCategory(getUserId());
			StorageFileInfoDO storageFileInfo = new StorageFileInfoDO();
			storageFileInfo.setMemberId(getUserId());
			imageCount = storageFileInfoAO.queryCount(storageFileInfo);
			ImagesCategoryDO imagesCategoryDO = imagesCategoryAO.getImagesCategoryObject(getUserId());
			userSize = imagesCategoryDO.getUserSize();
			//修改状态
			status = "";
		    return SUCCESS;
		}
	}
	
	public String updateImagesCategory(){
		
		if(firstCategory!=null&&categoryList!=null){
			int checkLength = checkStringLength(categoryList);
			if(checkLength > 4000){
				status = "M";
			}else{
				ImagesCategoryDO imagesCategoryDO = new ImagesCategoryDO();
				imagesCategoryDO.setMemberId(getUserId());
				imagesCategoryDO.setFirstCategory(firstCategory);
				imagesCategoryDO.setSecondCategory(categoryList);
				Long number = imagesCategoryAO.updateImageCategory(imagesCategoryDO);
				//修改执行失败
				if(number==null){
					status = "N";
				}else{
					status = "Y";
				}
			}
		}
		showList = imagesCategoryAO.getImagesCategory(getUserId());
		StorageFileInfoDO storageFileInfo = new StorageFileInfoDO();
		storageFileInfo.setMemberId(getUserId());
		imageCount = storageFileInfoAO.queryCount(storageFileInfo);
		ImagesCategoryDO imagesCategoryDO = imagesCategoryAO.getImagesCategoryObject(getUserId());
		userSize = imagesCategoryDO.getUserSize();
		return SUCCESS;
	}
	
	//判断字符串中文为3长度其余为1长度的总长度
	private int checkStringLength(String strs){
		int valueLength = 0;
        //String chinese = "[\u0391-\uFFE5]";
		 String chinese = "[^\\x00-\\xff]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < strs.length(); i++) {
            /* 获取一个字符 */
            String temp = strs.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 3;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
	}

	
}
