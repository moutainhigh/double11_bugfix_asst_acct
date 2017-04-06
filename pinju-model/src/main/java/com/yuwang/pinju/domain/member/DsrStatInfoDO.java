package com.yuwang.pinju.domain.member;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.domain.rate.DsrDimensionDO;

public class DsrStatInfoDO {

	private Integer dimensionId;
	private String dimensionName;
	private int number1 = 0;
	private int number2 = 0;
	private int number3 = 0;
	private int number4 = 0;
	private int number5 = 0;

	private DsrStatInfoDO(Integer dimensionId, String dimensionName) {
		this.dimensionId = dimensionId;
		this.dimensionName = dimensionName;
	}

	public static Map<Integer, DsrStatInfoDO> createDsrStatInfos(List<DsrDimensionDO> dimensions) {
		if(dimensions == null) {
			return null;
		}
		if(dimensions.size() == 0) {
			return new HashMap<Integer, DsrStatInfoDO>(0);
		}
		Map<Integer, DsrStatInfoDO> infos = new LinkedHashMap<Integer, DsrStatInfoDO>(dimensions.size());
		for(DsrDimensionDO dimension : dimensions) {
			DsrStatInfoDO info = new DsrStatInfoDO(dimension.getId(), dimension.getName());
			infos.put(info.getDimensionId(), info);
		}
		return infos;
	}

	public void setDsrStat(DsrStatDO dsrStat) {		
	}

	public Integer getDimensionId() {
		return dimensionId;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public int getNumber1() {
		return number1;
	}

	public int getNumber2() {
		return number2;
	}

	public int getNumber3() {
		return number3;
	}

	public int getNumber4() {
		return number4;
	}

	public int getNumber5() {
		return number5;
	}

	public int getTotal() {
		return number1 + number2 + number3 + number4 + number5;
	}

	public double getAverage() {
		return getTotal() / 5.0;
	}

	public double getNumber1Percent() {
		int total = getTotal();
		return (total == 0) ? 0D : number1 / (double)total;
	}

	public double getNumber2Percent() {
		int total = getTotal();
		return (total == 0) ? 0D : number2 / (double)total;
	}

	public double getNumber3Percent() {
		int total = getTotal();
		return (total == 0) ? 0D : number3 / (double)total;
	}

	public double getNumber4Percent() {
		int total = getTotal();
		return (total == 0) ? 0D : number4 / (double)total;
	}

	public double getNumber5Percent() {
		int total = getTotal();
		return (total == 0) ? 0D : number5 / (double)total;
	}
}
