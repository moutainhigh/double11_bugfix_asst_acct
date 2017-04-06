package com.yuwang.pinju.domain.member;

import java.io.Serializable;
import java.util.List;

/**
 * <p>店铺资质信息对象</p>
 *
 * @author gaobaowen
 * @since 2011-9-13 16:07:28
 */
public class SellerQualityInfoDO implements Serializable {

	private static final long serialVersionUID = 1L;

	private SellerQualityDO sellerQuality;
	private List<DsrStatDO> dsrStats;

	public SellerQualityInfoDO() {
	}

	public SellerQualityInfoDO(SellerQualityDO sellerQuality, List<DsrStatDO> dsrStats) {
		this.sellerQuality = sellerQuality;
		this.dsrStats = dsrStats;
	}

	public SellerQualityDO getSellerQuality() {
		return sellerQuality;
	}
	public void setSellerQuality(SellerQualityDO sellerQuality) {
		this.sellerQuality = sellerQuality;
	}
	public List<DsrStatDO> getDsrStats() {
		return dsrStats;
	}
	public void setDsrStats(List<DsrStatDO> dsrStats) {
		this.dsrStats = dsrStats;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dsrStats == null) ? 0 : dsrStats.hashCode());
		result = prime * result + ((sellerQuality == null) ? 0 : sellerQuality.hashCode());
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
		SellerQualityInfoDO other = (SellerQualityInfoDO) obj;
		if (dsrStats == null) {
			if (other.dsrStats != null)
				return false;
		} else if (!dsrStats.equals(other.dsrStats))
			return false;
		if (sellerQuality == null) {
			if (other.sellerQuality != null)
				return false;
		} else if (!sellerQuality.equals(other.sellerQuality))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "SellerQualityInfoDO [sellerQuality=" + sellerQuality + ", dsrStats="
				+ (dsrStats != null ? dsrStats.subList(0, Math.min(dsrStats.size(), maxLen)) : null) + "]";
	}
}