package com.yuwang.pinju.domain.distribute;

import java.io.Serializable;

public class ItemInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -850815231156630584L;
    /**
     * 用户ID
     */
    public Long memberId;
    /**
     * 商品ID
     */
    public Long itemId;
    /**
     * 结果状态
     */
    public Boolean status;
    /**
     * 返点比率
     */
    public Long reward;
    /**
     * 分销商昵称
     */
    public String nickName;
    /**
     * 分销商ID
     */
    public Long channelId;

    /**
     * 店铺ID
     */
    public Integer shopId;
    
    public ItemInfo() {
	super();
	this.status = false;
    }

    public ItemInfo(Long memberId, Long itemId) {
	super();
	this.memberId = memberId;
	this.itemId = itemId;
	this.status = false;
    }

    public ItemInfo(Integer shopId, Long itemId) {
	super();
	this.itemId = itemId;
	this.shopId = shopId;
    }

    public Long getMemberId() {
	return memberId;
    }

    public void setMemberId(Long memberId) {
	this.memberId = memberId;
    }

    public Long getItemId() {
	return itemId;
    }

    public void setItemId(Long itemId) {
	this.itemId = itemId;
    }

    public Boolean getStatus() {
	return status;
    }

    public void setStatus(Boolean status) {
	this.status = status;
    }

	public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
	return "ItemInfo [channelId=" + channelId + ", itemId=" + itemId
		+ ", memberId=" + memberId + ", nickName=" + nickName
		+ ", reward=" + reward + ", status=" + status + "]";
    }

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

}
