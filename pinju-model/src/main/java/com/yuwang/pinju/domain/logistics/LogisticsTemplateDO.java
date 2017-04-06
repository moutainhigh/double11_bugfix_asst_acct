package com.yuwang.pinju.domain.logistics;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * <p>物流模板</p>
 *
 * @author shihongbo
 * @since   2011-07-09
 */

public class LogisticsTemplateDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    private Long id;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板说明
     */
    private String memo;

    /**
     * 模板状态
     */
    private Integer state;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;

    /**
     * 记录修改时间
     */
    private Date gmtModified;


    public Long getId(){
        return id;
    }

    public Long getSellerId(){
        return sellerId;
    }

    @NotNull(message = "{logistics.template.tempname.notnull}")
	@Length(min = 1, max = 25, message = "{logistics.template.tempname.length}")
    public String getTemplateName(){
        return templateName;
    }

    public String getMemo(){
        return memo;
    }

    public Integer getState(){
        return state;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setSellerId(Long sellerId){
        this.sellerId = sellerId;
    }

    public void setTemplateName(String templateName){
        this.templateName = templateName;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }

    public void setState(Integer state){
        this.state = state;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

}


