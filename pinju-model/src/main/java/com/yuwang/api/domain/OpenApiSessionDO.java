package com.yuwang.api.domain;

import java.util.Date;

import com.yuwang.api.common.BaseDO;

public class OpenApiSessionDO extends BaseDO {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3291953466967220458L;

	/**
	 * 编号
	 */
	private Long id;

	/**
     * 会员编号
     */
    private Long sellerId;
    
    /**
     * 会员昵称
     */
    private String nickName;

	/**
     * 应用编号
     */
    private Long appid;

    /**
     * 临时SESSIONKEY
     */
    private String sessionKey;

    /**
     * 生成时间
     */
    private String gmtCreated;

    /**
     * 调用时间
     */
    private Date invokeDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    public Long getSellerId(){
        return sellerId;
    }

    public Long getAppid(){
        return appid;
    }

    public String getSessionKey(){
        return sessionKey;
    }

    public String getGmtCreated(){
        return gmtCreated;
    }

    public Date getInvokeDate(){
        return invokeDate;
    }

    public void setSellerId(Long sellerId){
        this.sellerId = sellerId;
    }

    public void setAppid(Long appid){
        this.appid = appid;
    }

    public void setSessionKey(String sessionKey){
        this.sessionKey = sessionKey;
    }

    public void setGmtCreated(String gmtCreated){
        this.gmtCreated = gmtCreated;
    }

    public void setInvokeDate(Date invokeDate){
        this.invokeDate = invokeDate;
    }

    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}

