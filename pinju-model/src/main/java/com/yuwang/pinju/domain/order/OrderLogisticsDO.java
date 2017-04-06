package com.yuwang.pinju.domain.order;

import java.util.Date;

import com.yuwang.pinju.common.MoneyUtil;

/**
 * 物流实体
 * 
 * @author 杜成
 * @version 1.0
 * @since 2011-06-01
 */
public class OrderLogisticsDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8702135397357428430L;

	/**
	 * 物流状态　未发货
	 */
	public static final int LOGISTICS_STATE_1 = 1;
	/**
	 * 物流状态　已发货
	 */
	public static final int LOGISTICS_STATE_2 = 2;
	
	
	/**
	 * 物流发货方式  ：自定义物流
	 */
	
	public static final String CONSIGNMENT_MODE_SELF = "5";
	/**
	 * 物流发货方式  ：无需物流
	 */
	public static final String CONSIGNMENT_MODE_NO = "6";
	
	/**
	 * 物流发货方式  卖家承担运费
	 */
	
	public static final String CONSIGNMENT_MODE_FREE = "4";
	/**
	 * 物流发货方式 EMS
	 */
	public static final String CONSIGNMENT_MODE_EMS = "3";

	/**
	 * 物流发货方式 平邮
	 */
	public static final String CONSIGNMENT_MODE_MAIL = "1";
	
	/**
	 * 物流发货方式 快递
	 */
	public static final String CONSIGNMENT_MODE_EXPRESS = "2";

	/**
	 * ID 主键
	 */
	private Long orderLogisticsId;
	/**
	 * 订单ID
	 */
	private Long orderId;

	/**
	 * 物流价格
	 */
	private Long postPrice;
	
	/**
	 * 物流价格显示用
	 */
	private String postPriceByYuan;

	/**
	 * 收货地址
	 */
	private String address;

	/**
	 * 收货人姓名
	 */
	private String fullName;

	/**
	 * 邮编
	 */
	private String post;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 手机
	 */
	private String mobilePhone;

	/**
	 * 省
	 */
	private String prov;

	/**
	 * 市
	 */
	private String city;

	/**
	 * 区
	 */
	private String area;

	/**
	 * 物流状态 默认未发货
	 */
	private Integer logisticsState;

	/**
	 * 外部物流编号
	 */
	private String outLogisticsId;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 发货时间
	 */
	private Date consingTime;

	/**
	 * 物流单创建时间(生成订单时间)
	 */
	private Date gmtCreate;

	/**
	 * 最后一次修改时间
	 */
	private Date gmtModified;

	/**
	 * 物流类型 
	 */
	private String consignmentMode;

	/**
	 * 物流方式 1为 未选 2为 未知,其它根据第三方物流接口的物流公司编号
	 */
	private String logisticsType;
	
	/**
	 * 物流公司名称
	 */
	private String logisticsName;

	/**
	 * 送货地址,区市编码
	 */
	private String pcdCode;

	/**
	 * 发货人姓名
	 */
	private String sendName;

	/**
	 * 发货地址
	 */
	private String sendAddress;
	
	/**
	 * 发货人电话
	 */
	private String sendPhone;
	
	/**
	 * 发货人邮编
	 */
	private String sendPost;
	
	/**
	 * 发货人手机
	 */
	private String sendMobilePhone;
	
	public OrderLogisticsDO() {

	}


	/**
	 * 
	 * Created on 2011-8-22
	 * <p>Discription: 生成订单时添加收货地址 ，请插入订单ID</p>
	 * @param orderId
	 * @param postPrice
	 * @param address
	 * @param fullName
	 * @param post
	 * @param phone
	 * @param mobilePhone
	 * @param prov
	 * @param city
	 * @param area
	 * @param gmtCreate
	 * @param gmtModified
	 * @param consignmentMode
	 * @param pcdCode
	 * @param logisticsState
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static OrderLogisticsDO creationOrderLogisticsDO(Long postPrice, String address,
			String fullName, String post, String phone, String mobilePhone,
			String prov, String city, String area, Date gmtCreate,
			Date gmtModified, String consignmentMode, String pcdCode,
			Integer logisticsState){
		OrderLogisticsDO orderLogisticsDO = new OrderLogisticsDO();
		orderLogisticsDO.setAddress(address);
		orderLogisticsDO.setFullName(fullName);
		orderLogisticsDO.setPost(post);
		orderLogisticsDO.setPhone(phone);
		orderLogisticsDO.setMobilePhone(mobilePhone);
		orderLogisticsDO.setProv(prov);
		orderLogisticsDO.setCity(city);
		orderLogisticsDO.setArea(area);
		orderLogisticsDO.setLogisticsState(logisticsState);
		orderLogisticsDO.setGmtCreate(gmtCreate);
		orderLogisticsDO.setGmtModified(gmtModified);
		orderLogisticsDO.setPcdCode(pcdCode);
		orderLogisticsDO.setLogisticsState(logisticsState);
		orderLogisticsDO.setConsignmentMode(consignmentMode);
		orderLogisticsDO.setPostPrice(postPrice);
		return orderLogisticsDO;
	}
	
	
	/**
	 * 更新发货状态
	 */
	public OrderLogisticsDO(Long orderId, String outLogisticsId,
			String sendAddress, String sendName, String consignmentMode,
			String logisticsType, Date gmtModified, Date consingTime,
			String sendPhone,String sendPost,String sendMobilePhone, int logisticsState, String logisticsName) {
		this.orderId = orderId;
		this.outLogisticsId = outLogisticsId;
		this.sendAddress = sendAddress;
		this.sendName = sendName;
		this.consignmentMode = consignmentMode;
		this.logisticsType = logisticsType;
		this.gmtModified = gmtModified;
		this.consingTime = consingTime;
		this.sendPhone = sendPhone;
		this.sendPost = sendPost;
		this.sendMobilePhone = sendMobilePhone;
		this.logisticsState = logisticsState;
		this.logisticsName = logisticsName;
	}

	public Long getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(Long postPrice) {
		this.postPrice = postPrice;
		
		this.postPriceByYuan = MoneyUtil.getCentToDollar(this.postPrice);
	}

	public String getPcdCode() {
		return pcdCode;
	}

	public void setPcdCode(String pcdCode) {
		this.pcdCode = pcdCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getLogisticsState() {
		return logisticsState;
	}

	public void setLogisticsState(Integer logisticsState) {
		this.logisticsState = logisticsState;
	}

	public String getOutLogisticsId() {
		return outLogisticsId;
	}

	public void setOutLogisticsId(String outLogisticsId) {
		this.outLogisticsId = outLogisticsId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getConsingTime() {
		return consingTime;
	}

	public void setConsingTime(Date consingTime) {
		this.consingTime = consingTime;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getConsignmentMode() {
		return consignmentMode;
	}

	public void setConsignmentMode(String consignmentMode) {
		this.consignmentMode = consignmentMode;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public Long getOrderLogisticsId() {
		return orderLogisticsId;
	}

	public void setOrderLogisticsId(Long orderLogisticsId) {
		this.orderLogisticsId = orderLogisticsId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public String getPostPriceByYuan() {
		return postPriceByYuan;
	}


	public void setPostPriceByYuan(String postPriceByYuan) {
		this.postPriceByYuan = postPriceByYuan;
	}


	public String getSendPhone() {
		return sendPhone;
	}


	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}


	public String getSendPost() {
		return sendPost;
	}


	public void setSendPost(String sendPost) {
		this.sendPost = sendPost;
	}


	public String getSendMobilePhone() {
		return sendMobilePhone;
	}


	public void setSendMobilePhone(String sendMobilePhone) {
		this.sendMobilePhone = sendMobilePhone;
	}


	public String getLogisticsName() {
		return logisticsName;
	}


	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

}
