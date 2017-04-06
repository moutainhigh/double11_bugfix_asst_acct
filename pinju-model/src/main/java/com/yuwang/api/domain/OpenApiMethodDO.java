package com.yuwang.api.domain;

import com.yuwang.api.common.ConfigurableSupport;

public class OpenApiMethodDO extends ConfigurableSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6541580512727419705L;

	/**
	 * 编号
	 */
	private Integer id;

	/**
	 * 外部接口名称
	 */
	private String methodName;

	/**
	 * 内部接口类
	 */
	private String interfaceClass;

	/**
	 * 内部接口方法
	 */
	private String interMethod;

    /**
     * 请求转换实体类
     */
    private String domainClass;

	/**
	 * 创建时间
	 */
	private String gmtCreate;

	/**
	 * 是否需要用户授权后才能调用的方法（0：需要授权，1：无需授权）
	 */
	private Integer needSession;

	/**
	 * 当前状态（0：正常，其它：失效）
	 */
	private Integer status;

	public Integer getId() {
		return id;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getInterfaceClass() {
		return interfaceClass;
	}

	public String getInterMethod() {
		return interMethod;
	}

	public String getDomainClass() {
		return domainClass;
	}

	public void setDomainClass(String domainClass) {
		this.domainClass = domainClass;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public Integer getNeedSession() {
		return needSession;
	}

	public Integer getStatus() {
		return status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setInterfaceClass(String interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public void setInterMethod(String interMethod) {
		this.interMethod = interMethod;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setNeedSession(Integer needSession) {
		this.needSession = needSession;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
