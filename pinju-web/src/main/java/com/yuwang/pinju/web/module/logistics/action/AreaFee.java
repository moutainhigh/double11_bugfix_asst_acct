package com.yuwang.pinju.web.module.logistics.action;

public class AreaFee {
    /**
     * 物流方式id
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

	public Integer getLogisticsTypeId() {
		return logisticsTypeId;
	}

	public void setLogisticsTypeId(Integer logisticsTypeId) {
		this.logisticsTypeId = logisticsTypeId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result
				+ ((logisticsTypeId == null) ? 0 : logisticsTypeId.hashCode());
		result = prime * result
				+ ((templateId == null) ? 0 : templateId.hashCode());
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
		AreaFee other = (AreaFee) obj;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (logisticsTypeId == null) {
			if (other.logisticsTypeId != null)
				return false;
		} else if (!logisticsTypeId.equals(other.logisticsTypeId))
			return false;
		if (templateId == null) {
			if (other.templateId != null)
				return false;
		} else if (!templateId.equals(other.templateId))
			return false;
		return true;
	}

}
