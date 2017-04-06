package com.yuwang.pinju.domain.shop;

import com.yuwang.pinju.domain.BaseDO;

public class ShopModulePictureRotationDO extends BaseDO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3360989541443002042L;
	
	/**
	 * 模块标题
	 */
	private String moduleTitle;
	
	/**
	 * 是否显示模块标题 0:不显示  1:显示
	 */
	private String isShowTitle;
	
	/**
	 * 模块显示高度
	 */
	private String moduleheight;
	
	/**
	 * 显示效果
	 */
	private String effect;
	
	/**
	 * 图片地址
	 */
	private String picPath0;
	
	/**
	 * 图片地址
	 */
	private String picPath1;
	
	/**
	 * 图片地址
	 */
	private String picPath2;
	
	/**
	 * 图片地址
	 */
	private String picPath3;
	
	
	
	/**
	 * 图片http地址
	 */
	private String pictureHttp0;
	
	/**
	 * 图片http地址
	 */
	private String pictureHttp1;
	
	/**
	 * 图片http地址
	 */
	private String pictureHttp2;
	
	/**
	 * 图片http地址
	 */
	private String pictureHttp3;
	

	public String getModuleTitle() {
		return moduleTitle;
	}

	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}

	public String getModuleheight() {
		return moduleheight;
	}

	public void setModuleheight(String moduleheight) {
		this.moduleheight = moduleheight;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}


	public String getPicPath0() {
		return picPath0;
	}

	public void setPicPath0(String picPath0) {
		this.picPath0 = picPath0;
	}

	public String getPicPath1() {
		return picPath1;
	}

	public void setPicPath1(String picPath1) {
		this.picPath1 = picPath1;
	}

	public String getPicPath2() {
		return picPath2;
	}

	public void setPicPath2(String picPath2) {
		this.picPath2 = picPath2;
	}

	public String getPicPath3() {
		return picPath3;
	}

	public void setPicPath3(String picPath3) {
		this.picPath3 = picPath3;
	}

	public String getPictureHttp0() {
		return pictureHttp0;
	}

	public void setPictureHttp0(String pictureHttp0) {
		this.pictureHttp0 = pictureHttp0;
	}

	public String getPictureHttp1() {
		return pictureHttp1;
	}

	public void setPictureHttp1(String pictureHttp1) {
		this.pictureHttp1 = pictureHttp1;
	}

	public String getPictureHttp2() {
		return pictureHttp2;
	}

	public void setPictureHttp2(String pictureHttp2) {
		this.pictureHttp2 = pictureHttp2;
	}

	public String getPictureHttp3() {
		return pictureHttp3;
	}

	public void setPictureHttp3(String pictureHttp3) {
		this.pictureHttp3 = pictureHttp3;
	}

	public String getIsShowTitle() {
		return isShowTitle;
	}

	public void setIsShowTitle(String isShowTitle) {
		this.isShowTitle = isShowTitle;
	}


}
