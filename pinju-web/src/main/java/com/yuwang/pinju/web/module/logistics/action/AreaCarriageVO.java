package com.yuwang.pinju.web.module.logistics.action;

/**
 * <p>物流运费模板显示对象</p>
 * 
 * @author shihongbo
 * @since 2011-07-15
 */
public class AreaCarriageVO  implements Comparable<AreaCarriageVO>{
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
    private Long defaultCarriage;
    
    /**
     * 增加商品运费
     */
    private Long carriageIncrease;
    
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

	public Long getDefaultCarriage() {
		return defaultCarriage;
	}

	public void setDefaultCarriage(Long defaultCarriage) {
		this.defaultCarriage = defaultCarriage;
	}

	public Long getCarriageIncrease() {
		return carriageIncrease;
	}

	public void setCarriageIncrease(Long carriageIncrease) {
		this.carriageIncrease = carriageIncrease;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
    
	public String getLogisticsTypeName() {
		return logisticsTypeName;
	}

	public void setLogisticsTypeName(String logisticsTypeName) {
		this.logisticsTypeName = logisticsTypeName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((carriageIncrease == null) ? 0 : carriageIncrease.hashCode());
		result = prime * result
				+ ((defaultCarriage == null) ? 0 : defaultCarriage.hashCode());
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
		AreaCarriageVO other = (AreaCarriageVO) obj;
		if (carriageIncrease == null) {
			if (other.carriageIncrease != null)
				return false;
		} else if (carriageIncrease.compareTo(other.carriageIncrease) != 0)
			return false;
		if (defaultCarriage == null) {
			if (other.defaultCarriage != null)
				return false;
		} else if (defaultCarriage.compareTo(other.defaultCarriage) != 0)
			return false;
		if (logisticsTypeId == null) {
			if (other.logisticsTypeId != null)
				return false;
			
		} else if (logisticsTypeId.compareTo(other.logisticsTypeId) != 0)
			return false;
		return true;
	}

	public int compareTo(AreaCarriageVO areaCarriageVO){
		if(this.equals(areaCarriageVO))
			return 0;
		
		if(this.logisticsTypeId.compareTo(areaCarriageVO.getLogisticsTypeId()) != 0)
			return this.logisticsTypeId.compareTo(areaCarriageVO.getLogisticsTypeId());
		
		if(this.defaultCarriage.compareTo(areaCarriageVO.getDefaultCarriage()) != 0)
			return this.defaultCarriage.compareTo(areaCarriageVO.getDefaultCarriage());
		
		if(this.carriageIncrease.compareTo(areaCarriageVO.getCarriageIncrease()) != 0)
			return this.carriageIncrease.compareTo(areaCarriageVO.getCarriageIncrease());
		
		return 0;
	}
	
}
