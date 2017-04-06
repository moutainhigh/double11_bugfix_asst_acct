package com.yuwang.api.domain;

import com.yuwang.api.common.ConfigurableSupport;

public class OpenApiApplicationDO extends ConfigurableSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2529437989405405079L;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 标题
	 */
	private String title;

    /**
     * 应用生成密码
     */
    private String secret;

	/**
	 * 图标地址
	 */
	private String picUrl;

	/**
	 * 推广位置
	 */
	private String promPos;

	/**
	 * 发布位置
	 */
	private String releasePos;

	/**
	 * 流量大小
	 */
	private Integer flux;

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public String getPromPos() {
		return promPos;
	}

	public String getReleasePos() {
		return releasePos;
	}

	public Integer getFlux() {
		return flux;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setPromPos(String promPos) {
		this.promPos = promPos;
	}

	public void setReleasePos(String releasePos) {
		this.releasePos = releasePos;
	}

	public void setFlux(Integer flux) {
		this.flux = flux;
	}
}
