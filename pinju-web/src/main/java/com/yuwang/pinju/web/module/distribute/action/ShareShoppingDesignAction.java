/**
 * 分销系统二期需求，分享购
 */
package com.yuwang.pinju.web.module.distribute.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.distribute.ao.DistributeImageAO;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.distribute.ShareDesignDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * @author liyouguo
 * 
 * @since 2011-10-24
 */
public class ShareShoppingDesignAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5363907063540558963L;

	/**
	 * 用户编号
	 */
	private Long memberId;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 店标图片
	 */
	private File shopIndex;
	/**
	 * 店标图片文件名
	 */
	private String shopIndexFileName;
	/**
	 * 横幅图片
	 */
	private File bannersImg;
	/**
	 * 横幅图片文件名
	 */
	private String bannersImgFileName;
	/**
	 * 宣传图片
	 */
	private File adImg;
	/**
	 * 宣传图片文件名
	 */
	private String adImgFileName;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String descript;
	/**
	 * 校验图片类型
	 */
	private String uploadType;

	private ShareDesignDO shareDesigner;
	private DistributeImageAO distributeImageAO;
	private DistributorManager distributorManager;

	/**
	 * 获取当前页面设置内容
	 * 
	 * @return
	 */
	public String getShareDesign() {
		DistributeDistributorDO channel = getDistributorDO();
		if (channel == null)
			return ERROR;
		shareDesigner = channel.getShareDesign();
		shareDesigner
				.setShopIndex(shareDesigner.getShopIndex() != null ? PinjuConstant.VIEW_IMAGE_SERVER
						+ shareDesigner.getShopIndex()
						: null);
		shareDesigner
				.setBannerImg(shareDesigner.getBannerImg() != null ? PinjuConstant.VIEW_IMAGE_SERVER
						+ shareDesigner.getBannerImg()
						: null);
		shareDesigner
				.setAdImg(shareDesigner.getAdImg() != null ? PinjuConstant.VIEW_IMAGE_SERVER
						+ shareDesigner.getAdImg()
						: null);
		return SUCCESS;
	}

	/**
	 * 校验图片
	 * 
	 * @return
	 */
	public String validateUploadImage() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONObject m = new JSONObject();
			if ("Logo".equals(uploadType)) {
				Result result = distributeImageAO.checkImageInfo(shopIndex,
						161, 77, 100);
				if (!result.isSuccess()) {
					m.put("status", false);
					m.put("message", "店标图片不符合要求：" + result.getResultCode());
					throw new Exception(m.toString());
				}
			}
			if ("Banner".equals(uploadType)) {
				Result result = distributeImageAO.checkImageInfo(bannersImg,
						489, 77, 100);
				if (!result.isSuccess()) {
					m.put("status", false);
					m.put("message", "横幅图片不符合要求：" + result.getResultCode());
					throw new Exception(m.toString());
				}
			}
			if ("Bigger".equals(uploadType)) {
				Result result = distributeImageAO.checkImageInfo(adImg, 489,
						367, 300);
				if (!result.isSuccess()) {
					m.put("status", false);
					m.put("message", "宣传图片不符合要求：" + result.getResultCode());
					throw new Exception(m.toString());
				}
			}
			m.put("status", true);
			out.print(m.toString());
		} catch (Exception e) {
			out.print(e.getMessage());
		}
		return null;
	}

	/**
	 * 保存用户页面设置
	 * 
	 * @return
	 * 
	 */
	public String execute() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONObject m = new JSONObject();
			m.put("status", 1);
			DistributeDistributorDO channel = getDistributorDO();
			if (channel == null) {
				m.put("status", 2);
				m.put("message", "请先申请成为分销商。");
				throw new Exception(m.toString());
			}
			StringBuilder sbd = new StringBuilder();
			if (title != null && title.getBytes().length > 30)
				sbd.append("标题不能超过10字节（5个汉字）。");
			if (descript != null && descript.getBytes().length > 60) {
				sbd.append("\n").append("个性文案最多可以输入20个汉字。");
				m.put("message", sbd.toString());
				throw new Exception(m.toString());
			}
			String shopIdxUrl = null;
			if (shopIndex != null) {
				shopIdxUrl = distributeImageAO.uploadChannelImage(shopIndex,
						shopIndexFileName, channel.getMemberId(), channel
								.getNickName());
				if (shopIdxUrl == null) {
					m.put("message", "上传店标失败");
					throw new Exception(m.toString());
				}
			}
			String bannerImgUrl = null;
			if (bannersImg != null) {
				bannerImgUrl = distributeImageAO.uploadChannelImage(bannersImg,
						bannersImgFileName, channel.getMemberId(), channel
								.getNickName());
				if (bannerImgUrl == null) {
					m.put("message", "上传横幅失败");
					throw new Exception(m.toString());
				}
			}
			String adImgUrl = null;
			if (adImg != null) {
				adImgUrl = distributeImageAO.uploadChannelImage(adImg,
						adImgFileName, channel.getMemberId(), channel
								.getNickName());
				if (adImgUrl == null) {
					m.put("message", "上传宣传失败");
					throw new Exception(m.toString());
				}
			}
			try {
				channel = getDistributorDO();
				shareDesigner = channel.getShareDesign();
				shareDesigner.setChannelId(channel.getId());
				shareDesigner.setTitle(title);
				shareDesigner.setDescript(descript);
				if (shopIdxUrl != null)
					shareDesigner.setShopIndex(shopIdxUrl);
				if (bannerImgUrl != null)
					shareDesigner.setBannerImg(bannerImgUrl);
				if (adImgUrl != null)
					shareDesigner.setAdImg(adImgUrl);
				distributorManager.setShareDesign(shareDesigner);
			} catch (ManagerException e) {
				log.error("设置分享购页面失败：", e);
				m.put("message", "设置分享购页面失败。");
				throw new Exception(m.toString());
			}
			m.put("status", 0);
			m.put("message", "设置分享购页面成功，您可以到社区查看分享购页面。");
			out.print(m.toString());
		} catch (Exception e) {
			out.print(e.getMessage());
		}
		return null;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public DistributorManager getDistributorManager() {
		return distributorManager;
	}

	public void setDistributorManager(DistributorManager distributorManager) {
		this.distributorManager = distributorManager;
	}

	public void setShareDesigner(ShareDesignDO shareDesigner) {
		this.shareDesigner = shareDesigner;
	}

	public ShareDesignDO getShareDesigner() {
		return this.shareDesigner;
	}

	public File getShopIndex() {
		return shopIndex;
	}

	public void setShopIndex(File shopIndex) {
		this.shopIndex = shopIndex;
	}

	public File getBannersImg() {
		return bannersImg;
	}

	public void setBannersImg(File bannersImg) {
		this.bannersImg = bannersImg;
	}

	public File getAdImg() {
		return adImg;
	}

	public void setAdImg(File adImg) {
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

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public void setDistributeImageAO(DistributeImageAO distributeImageAO) {
		this.distributeImageAO = distributeImageAO;
	}

	public String getShopIndexFileName() {
		return shopIndexFileName;
	}

	public void setShopIndexFileName(String shopIndexFileName) {
		this.shopIndexFileName = shopIndexFileName;
	}

	public String getBannersImgFileName() {
		return bannersImgFileName;
	}

	public void setBannersImgFileName(String bannersImgFileName) {
		this.bannersImgFileName = bannersImgFileName;
	}

	public String getAdImgFileName() {
		return adImgFileName;
	}

	public void setAdImgFileName(String adImgFileName) {
		this.adImgFileName = adImgFileName;
	}

	private DistributeDistributorDO getDistributorDO() {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		DistributeDistributorDO distributeDistributorDO = distributorManager
				.findDistributorByMemberId(login.getMemberId());
		if (distributeDistributorDO.getMemberId() != null)
			return distributeDistributorDO;
		return null;
	}
}
