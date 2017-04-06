package com.yuwang.pinju.domain.item;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;


/**
 * @Project com.yuwang.pinju.domain.item.pinju-model
 * @Description: 商品自定义销售属性值DO
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a>
 * @date 2011-11-17 下午5:57:13
 * @version V1.0
 */

public class CustomProValueDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1001683114882970195L;
	
	public final static Integer VALUE_TYPE_COMMON=1;
	public final static Integer VALUE_TYPE_IMG=2;
	
	public final static Integer STATUS_NORMAL = 1;
	public final static Integer STATUS_DELETE = 0;
	
	private Long id;
	/**
	 * 商品ID
	 */
	private Long itemId;
	/**
	 * 用户ID
	 */
	private Long memberId;
	/**
	 * 类目属性ID
	 */
	private Long categoryPropertyId;
	/**
	 * 类目属性值ID
	 */
	private Long cateProValueId;
	/**
	 * 自定义值
	 */
	private String value;
	/**
	 * 自定义图片路径
	 */
	private String imgUrl;
	/**
	 * 自定义类型 1 普通 2 图片
	 */
	private Integer valueType;

	private Date gmtCreate;

	private Date gmtModified;
	
	/**
	 * 状态：0：删除，1：正常
	 */
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getCategoryPropertyId() {
		return categoryPropertyId;
	}

	public void setCategoryPropertyId(Long categoryPropertyId) {
		this.categoryPropertyId = categoryPropertyId;
	}

	public Long getCateProValueId() {
		return cateProValueId;
	}

	public void setCateProValueId(Long cateProValueId) {
		this.cateProValueId = cateProValueId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
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

	@Override
	public String toString() {
		return "CustomProValueDO [id=" + id + ", itemId=" + itemId
				+ ", memberId=" + memberId + ", categoryPropertyId="
				+ categoryPropertyId + ", cateProValueId=" + cateProValueId
				+ ", value=" + value + ", imgUrl=" + imgUrl + ", valueType="
				+ valueType + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + "]";
	}

	
}
