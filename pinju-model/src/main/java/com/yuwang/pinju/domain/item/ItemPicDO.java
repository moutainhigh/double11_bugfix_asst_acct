package com.yuwang.pinju.domain.item;

import java.util.Date;

public class ItemPicDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 图片存储地址
     */
    private String picUrl;

    /**
     * 商品ID
     */
    private Long itemId;

    /**
     * 图片长
     */
    private Long picLength;

    /**
     * 图片宽
     */
    private Long picWidth;

    /**
     * 占用空间大小，KB为单位
     */
    private Long picSize;

    /**
     * 排序值 为1-N 显示顺序
     */
    private Long sort;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 添加时间
     */
    private Date gmtCreate;


    public Long getId(){
        return id;
    }

    public String getPicUrl(){
        return picUrl;
    }

    public Long getItemId(){
        return itemId;
    }

    public Long getPicLength(){
        return picLength;
    }

    public Long getPicWidth(){
        return picWidth;
    }

    public Long getPicSize(){
        return picSize;
    }

    public Long getSort(){
        return sort;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setPicUrl(String picUrl){
        this.picUrl = picUrl;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }

    public void setPicLength(Long picLength){
        this.picLength = picLength;
    }

    public void setPicWidth(Long picWidth){
        this.picWidth = picWidth;
    }

    public void setPicSize(Long picSize){
        this.picSize = picSize;
    }

    public void setSort(Long sort){
        this.sort = sort;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

}

