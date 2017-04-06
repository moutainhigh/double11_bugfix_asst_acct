package com.yuwang.pinju.domain.logistics;

/**
 * 省份IP段
 * 
 * @author heyong
 * @since 2011-07-21
 * 
 */
public class LogisticsCityIpDO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID编号
	 */
	private Long id;
	/**
	 * 转换后的IP起始段
	 */
	private Long startIp;
	/**
	 * 转换后的IP结束段
	 */
	private Long endIp;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 服务提供者
	 */
	private String isp;
	/**
	 * IP起始段
	 */
	private String startIp1;
	/**
	 * IP结束段
	 */
	private String endIp1;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStartIp() {
		return startIp;
	}
	public void setStartIp(Long startIp) {
		this.startIp = startIp;
	}
	public Long getEndIp() {
		return endIp;
	}
	public void setEndIp(Long endIp) {
		this.endIp = endIp;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getStartIp1() {
		return startIp1;
	}
	public void setStartIp1(String startIp1) {
		this.startIp1 = startIp1;
	}
	public String getEndIp1() {
		return endIp1;
	}
	public void setEndIp1(String endIp1) {
		this.endIp1 = endIp1;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
