package com.yuwang.pinju.domain.logistics;

import java.util.Date;

public class LogisticsAreaCarriageDO implements java.io.Serializable,Comparable<LogisticsAreaCarriageDO> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 物流方式id
1：平邮
2：快递
3：EMS
     */
    private Integer logisticsTypeId;

    /**
     * 地区id
     */
    private Integer areaId;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 地区运费
     */
    private Long areaCarriage;

    /**
     * 增加运费
     */
    private Long areaCarriageIncrease;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;

    /**
     * 记录修改时间
     */
    private Date gmtModified;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 1选择，2显示
     */
    private Long type;

    public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getId(){
        return id;
    }

    public Integer getLogisticsTypeId(){
        return logisticsTypeId;
    }

    public Integer getAreaId(){
        return areaId;
    }

    public Long getTemplateId(){
        return templateId;
    }

    public Long getAreaCarriage(){
        return areaCarriage;
    }

    public Long getAreaCarriageIncrease(){
        return areaCarriageIncrease;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public String getAreaName(){
        return areaName;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setLogisticsTypeId(Integer logisticsTypeId){
        this.logisticsTypeId = logisticsTypeId;
    }

    public void setAreaId(Integer areaId){
        this.areaId = areaId;
    }

    public void setTemplateId(Long templateId){
        this.templateId = templateId;
    }

    public void setAreaCarriage(Long areaCarriage){
        this.areaCarriage = areaCarriage;
    }

    public void setAreaCarriageIncrease(Long areaCarriageIncrease){
        this.areaCarriageIncrease = areaCarriageIncrease;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

    public void setAreaName(String areaName){
        this.areaName = areaName;
    }

	@Override
	public int compareTo(LogisticsAreaCarriageDO o) {
		return this.getLogisticsTypeId().compareTo(o.getLogisticsTypeId());
	}
}

