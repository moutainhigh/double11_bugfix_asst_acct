package com.yuwang.pinju.web.module.logistics.action;

/**
 * <p>物流运费模板显示对象</p>
 * 
 * @author shihongbo
 * @since 2011-07-15
 */
public class LogisticsTemplateVO implements Comparable<LogisticsTemplateVO>{
	/**
     * 物流方式id
     */
    private Integer logisticsTypeId;
    
	/**
     * 物流方式名称
     */
    private String logisticsTypeName;
    
    /**
     * 默认运费
     */
    private String defaultCarriage;
    
    /**
     * 增加商品运费
     */
    private String carriageIncrease;
    
    /**
     * 地区名称
     */
    private String areaName;
    

	/**
     * 地区id
     */
    private Integer areaId;

    
    public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

    public Integer getLogisticsTypeId() {
		return logisticsTypeId;
	}

	public void setLogisticsTypeId(Integer logisticsTypeId) {
		this.logisticsTypeId = logisticsTypeId;
	}
	
	public String getLogisticsTypeName() {
		return logisticsTypeName;
	}

	public void setLogisticsTypeName(String logisticsTypeName) {
		this.logisticsTypeName = logisticsTypeName;
	}

	public String getDefaultCarriage() {
		return defaultCarriage;
	}

	public void setDefaultCarriage(String defaultCarriage) {
		this.defaultCarriage = defaultCarriage;
	}

	public String getCarriageIncrease() {
		return carriageIncrease;
	}

	public void setCarriageIncrease(String carriageIncrease) {
		this.carriageIncrease = carriageIncrease;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((areaName == null) ? 0 : areaName.hashCode());
		result = prime * result
				+ ((logisticsTypeId == null) ? 0 : logisticsTypeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		LogisticsTemplateVO other = (LogisticsTemplateVO) obj;
		if (areaName == null) {
			if (other.areaName != null)
				return false;
		} else if (!areaName.equals(other.areaName))
			return false;
		
		if (logisticsTypeId == null) {
			if (other.logisticsTypeId != null)
				return false;
		//} else if (!logisticsTypeId.equals(other.logisticsTypeId))
		} else if (logisticsTypeId.compareTo(other.logisticsTypeId) != 0)
			return false;
		return true;
	}

	public int compareTo(LogisticsTemplateVO logisticsTemplateVO){
		if(this.equals(logisticsTemplateVO))
			return 0;

		if(this.logisticsTypeId < logisticsTemplateVO.getLogisticsTypeId())
			return -1;
		else if(this.logisticsTypeId > logisticsTemplateVO.getLogisticsTypeId())
			return 1;
		
		if(this.areaId == 0)
			return 1;
		else if(logisticsTemplateVO.getAreaId() == 0)
			return -1;
		
		return this.areaId.compareTo(logisticsTemplateVO.getAreaId());
	}
}
