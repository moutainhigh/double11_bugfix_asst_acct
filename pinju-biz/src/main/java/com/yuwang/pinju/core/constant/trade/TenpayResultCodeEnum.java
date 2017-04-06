package com.yuwang.pinju.core.constant.trade;

/**
 * <p>Discription:  财付通返回结果码</p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-22 
 */
public enum TenpayResultCodeEnum {
	CODE("23421229", "系统繁忙	处理中,请一分钟后再查询"),

	CODE1("03019001", "CGI调用后台服务,socket操作超时"),

	CODE2("9999", "系统繁忙,网络问题,请通知管理员"),

	CODE3("03020040", "系统繁忙	通信协议错误"),

	CODE5("03020005", "系统繁忙	解析通用查询结果失败"),

	CODE6("03020009", "访问频次过高	访问过于频繁");

	private String resultCode;

	private String desc;

	TenpayResultCodeEnum(String resultCode, String desc) {
		this.resultCode = resultCode;
		this.desc = desc;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static TenpayResultCodeEnum getType(String resultCode) {
		for (TenpayResultCodeEnum type : TenpayResultCodeEnum.values()) {
			if (type.resultCode.equals(resultCode)) {
				return type;
			}
		}
		return CODE;
	}
	/**
	 * <p>Discription: 判断返回码是否存在于指定的集合中</p>
	 * @param resultCode
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static boolean verifyResultCode(String resultCode){
		for (TenpayResultCodeEnum type : TenpayResultCodeEnum.values()) {
			if (type.resultCode.compareTo(resultCode)==0) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String args[]){
		System.out.println(TenpayResultCodeEnum.verifyResultCode("99991111"));
	}
}
