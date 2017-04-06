package com.yuwang.pinju.domain.shop;

import java.io.StringReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 用户模块：编辑时设置的参数
 * 
 * @author mike
 * @version 1.0
 * @created 17-六月-2011 10:39:56
 */
public class ShopUserModuleParamDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6415762578198543782L;
	/**
	 * 模块编辑：设置参数，key=value的形式
	 */
	private String save_config;
	private String release_config;

	private String editHtml;
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 模块原型id
	 */
	private Long moduleId;
	
	private Integer shopId;
	
	private Long userId;
	/**
	 * 用户页面id
	 */
	private Long userPageId;

	private Integer pageId;
	/**
	 * 自定义页面
	 */
	private String customPage;
	
	private String save_html;
	private String release_html;

	private String preview;
	
	private Long realUserPageId;
	
	private String ip;
	public String getSave_html() {
		return save_html;
	}

	public void setSave_html(String saveHtml) {
		save_html = saveHtml;
	}

	public String getRelease_html() {
		return release_html;
	}

	public void setRelease_html(String releaseHtml) {
		release_html = releaseHtml;
	}

	public String getCustomPage() {
		return customPage;
	}

	public void setCustomPage(String customPage) {
		this.customPage = customPage;
	}

	public ShopUserModuleParamDO() {

	}

	public void finalize() throws Throwable {

	}

	public String getSave_config() {
		return save_config;
	}

	public void setSave_config(String save_config) {
		this.save_config = save_config;
	}

	public String getRelease_config() {
		return release_config;
	}

	public void setRelease_config(String release_config) {
		this.release_config = release_config;
	}

	public String getEditHtml() {
		return editHtml;
	}

	public void setEditHtml(String editHtml) {
		this.editHtml = editHtml;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserPageId() {
		return userPageId;
	}

	public void setUserPageId(Long userPageId) {
		this.userPageId = userPageId;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	/**
	 * 获取配置参数值
	 * 
	 * @param key
	 *            key
	 * @return 配置参数值
	 */
	public String getConfig(String key, boolean isRelease) {
		String configuration = isRelease ? this.release_config
				: this.save_config;
		Properties properties = null;
		if (properties == null && StringUtils.isNotEmpty(configuration)) {
			try {
				properties = new Properties();
				properties.load(new StringReader(configuration));
			} catch (Exception ignored) {

			}
		}
		return properties != null ? properties.getProperty(key) : null;
	}

	public Properties getConfigs(boolean isRelease) {
		String configuration = isRelease ? this.release_config
				: this.save_config;
		Properties properties = new Properties();
		if (StringUtils.isNotEmpty(configuration)) {
			try {
				properties = new Properties();
				properties.load(new StringReader(configuration));
			} catch (Exception ignored) {

			}
		}
		return properties;
	}

	/**
	 * 设置配置参数值
	 * 
	 * @param key
	 *            key
	 * @return 配置参数值
	 */
	public Properties setConfig(String key, String value, Properties properties) {
		try {
			Object obj = properties.setProperty(key, value);
			if (obj == null) {
				properties.put(key, value);
			}
		} catch (Exception ignored) {

		}
		return properties;

	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public Long getRealUserPageId() {
		return realUserPageId;
	}

	public void setRealUserPageId(Long realUserPageId) {
		this.realUserPageId = realUserPageId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}