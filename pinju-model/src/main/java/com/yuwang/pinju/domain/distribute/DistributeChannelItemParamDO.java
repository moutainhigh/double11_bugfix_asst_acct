package com.yuwang.pinju.domain.distribute;

import java.util.Date;

import com.yuwang.pinju.domain.Paginator;

public class DistributeChannelItemParamDO extends Paginator {

    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long itemId;

    /**
     * 分销商编号
     */
    private Long channelId;

    /**
     * 上传图片地址
     */
    private String imageUrl;

    /**
     * 推荐原因
     */
    private String recommendReason;

    /**
     * 当前状态（0：分销中，1：停止分销）
     */
    private Integer status;

    /**
     * 分销时间
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 同意数
     */
    private Integer agreeCount;

    /**
     * 反对数
     */
    private Integer opposeCount;

    /**
     * 扩展字段
     */
    private String configuration;


    public DistributeChannelItemParamDO() {
		super();
	}
    
	public DistributeChannelItemParamDO(Integer items, Integer currentPage) {
		super(10, items);
		setPage(currentPage);
	}
	
	public DistributeChannelItemParamDO(Long channelId, Integer status) {
		super();
		this.channelId = channelId;
		this.status = status;
	}

	public DistributeChannelItemParamDO(Long itemId, Long channelId) {
		super();
		this.itemId = itemId;
		this.channelId = channelId;
	}

	public DistributeChannelItemParamDO(Long itemId, Long channelId,Integer status) {
		super();
		this.itemId = itemId;
		this.channelId = channelId;
		this.status = status;
	}

	public Long getId(){
        return id;
    }

    public Long getItemId(){
        return itemId;
    }

    public Long getChannelId(){
        return channelId;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getRecommendReason(){
        return recommendReason;
    }

    public Integer getStatus(){
        return status;
    }

    public String getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModify(){
        return gmtModify;
    }

    public Integer getAgreeCount(){
        return agreeCount;
    }

    public Integer getOpposeCount(){
        return opposeCount;
    }

    public String getConfiguration(){
        return configuration;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }

    public void setChannelId(Long channelId){
        this.channelId = channelId;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public void setRecommendReason(String recommendReason){
        this.recommendReason = recommendReason;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public void setGmtCreate(String gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModify(Date gmtModify){
        this.gmtModify = gmtModify;
    }

    public void setAgreeCount(Integer agreeCount){
        this.agreeCount = agreeCount;
    }

    public void setOpposeCount(Integer opposeCount){
        this.opposeCount = opposeCount;
    }

    public void setConfiguration(String configuration){
        this.configuration = configuration;
    }

}

