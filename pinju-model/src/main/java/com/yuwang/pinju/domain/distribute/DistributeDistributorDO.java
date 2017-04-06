package com.yuwang.pinju.domain.distribute;

import com.yuwang.pinju.domain.ConfigurableSupport;

/**
 * 分销商实体类
 * 
 * @author liyouguo
 * @version 1.0
 * @updated 12-七月-2011 11:04:41
 */
public class DistributeDistributorDO extends ConfigurableSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -218010172776235525L;

	/**
	 * 自增编号
	 */
	private Long id;
	/**
	 * 会员编号
	 */
	private Long memberId;
	private String nickName;

	public DistributeDistributorDO() {
	}

	public DistributeDistributorDO(Long memberId) {
		super();
		this.memberId = memberId;
	}

	public DistributeDistributorDO(Long memberId, String nickName) {
		super();
		this.memberId = memberId;
		this.nickName = nickName;
	}

	public void finalize() throws Throwable {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	/**
	 * 根据返回的分销商信息生成相应的分享购管理实体
	 * 
	 * @return
	 */
	public ShareDesignDO getShareDesign() {
		ShareDesignDO shareDesign = new ShareDesignDO();
		shareDesign.setAdImg(getConfig("adImg"));
		shareDesign.setTitle(getConfig("title"));
		shareDesign.setDescript(getConfig("descript"));
		shareDesign.setShopIndex(getConfig("shopIndex"));
		shareDesign.setBannerImg(getConfig("bannerImg"));
		return shareDesign;
	}
}