package com.yuwang.pinju.domain.distribute;

import java.util.List;

import com.yuwang.pinju.domain.Paginator;

/**
 * 
 * 格子铺
 * 
 * @author caiwei
 * 
 */
public class ShowCaseQueryDO extends Paginator {

    /**
     * 
     */
    private static final long serialVersionUID = -2253129667797944050L;
    /**
     * 用户编号
     */
    private Long memberId;
    /**
     * 供应商编号
     */
    private Integer supplierId;
    /**
     * 分销商ID
     */
    private Long channelId;
    /**
     * 商品ID列表
     */
    private List<Long> ids;
    
    /**
     * 格子铺展示信息列表
     */
    private List<GridDO> gridList;
    
    /**
     * 店标LOGO
     */
    private String shopIndex;
    
    /**
     * 横幅广告
     */
    private String bannerImg;
    
    /**
     * 宣传大图
     */
    private String adImg;
    
    /**
     * 个性文案
     */
    private String descript;
    
    
    private String channelIdSec;
    
    public ShowCaseQueryDO() {
	super();
    }

    /**
     * 格子铺
     * 
     * @param memberId
     * 		用户ID
     * @param itemsPerPage
     * 		每页显示条数
     */
    public ShowCaseQueryDO(Long memberId, Integer itemsPerPage, Integer currPage) {
	super(itemsPerPage);
	setPage(currPage);
	this.memberId = memberId;
    }

    public Long getMemberId() {
	return memberId;
    }

    public void setMemberId(Long memberId) {
	this.memberId = memberId;
    }

    public Integer getSupplierId() {
	return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
	this.supplierId = supplierId;
    }

    public Long getChannelId() {
	return channelId;
    }

    public void setChannelId(Long channelId) {
	this.channelId = channelId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<GridDO> getGridList() {
        return gridList;
    }

    public void setGridList(List<GridDO> gridList) {
        this.gridList = gridList;
    }

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

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getChannelIdSec() {
		return channelIdSec;
	}

	public void setChannelIdSec(String channelIdSec) {
		this.channelIdSec = channelIdSec;
	}

}
