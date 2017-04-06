/**
 * 分销系统二期需求，分享购
 */
package com.yuwang.pinju.domain.distribute;

import com.yuwang.api.common.BaseDO;
import com.yuwang.pinju.common.StringUtil;

/**
 * @author liyouguo
 * 
 * @since 2011-10-24
 */
public class ShareDesignDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1627577080595321990L;

	/**
	 * 分销商编号
	 */
	private Long channelId;
	/**
	 * 店标图片地址
	 */
	private String shopIndex;
	/**
	 * 横幅图片地址
	 */
	private String bannerImg;
	/**
	 * 宣传图片地址
	 */
	private String adImg;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String descript;

	public String getShopIndex() {
		return shopIndex;
	}

	public void setShopIndex(String shopIndex) {
		this.shopIndex = shopIndex;
	}

	public String getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public String getAdImg() {
		return adImg;
	}

	public void setAdImg(String adImg) {
		this.adImg = adImg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String toString() {
		StringBuilder sbd = new StringBuilder();
		if (!StringUtil.isEmpty(shopIndex))
			sbd.append("shopIndex=").append(shopIndex);
		if (!StringUtil.isEmpty(bannerImg))
			sbd.append("\n").append("bannerImg=").append(bannerImg);
		if (!StringUtil.isEmpty(adImg))
			sbd.append("\n").append("adImg=").append(adImg);
		if (!StringUtil.isEmpty(title))
			sbd.append("\n").append("title=").append(title);
		if (!StringUtil.isEmpty(descript))
			sbd.append("\n").append("descript=").append(descript);
		return sbd.toString();
	}
}
