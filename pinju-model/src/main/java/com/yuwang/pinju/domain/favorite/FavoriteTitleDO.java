package com.yuwang.pinju.domain.favorite;

import java.util.Date;

public class FavoriteTitleDO implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7895320808447537382L;


	private Long id;


    private String configuration;


    private Long memberId;


    private Long titleId;


    private String titleName;


    private Long type;


    private Date modifiedDate;


    private Date createDate;


    public Long getId(){
        return id;
    }

    public String getConfiguration(){
        return configuration;
    }

    public Long getMemberId(){
        return memberId;
    }

    public Long getTitleId(){
        return titleId;
    }

    public String getTitleName(){
        return titleName;
    }

    public Long getType(){
        return type;
    }

    public Date getModifiedDate(){
        return modifiedDate;
    }

    public Date getCreateDate(){
        return createDate;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setConfiguration(String configuration){
        this.configuration = configuration;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public void setTitleId(Long titleId){
        this.titleId = titleId;
    }

    public void setTitleName(String titleName){
        this.titleName = titleName;
    }

    public void setType(Long type){
        this.type = type;
    }

    public void setModifiedDate(Date modifiedDate){
        this.modifiedDate = modifiedDate;
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

}

