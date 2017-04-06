package com.yuwang.pinju.domain.rate;

import java.util.Date;

import com.yuwang.pinju.common.RelationEntity;
import com.yuwang.pinju.domain.BaseDO;

/**
 * <p>DSR维度</p>
 *
 * @author gaobaowen
 * 2011-6-15 下午01:05:06
 */
public class DsrDimensionDO extends BaseDO implements RelationEntity<Integer> {

	public final static String MEMCACHED_KEY = DsrDimensionDO.class.getName();

	/**
	 * 状态 -- 无效（0）
	 */
	public final static Integer STATUS_INVALID = 0;

	/**
	 * 状态 -- 有效（1）
	 */
	public final static Integer STATUS_VALID = 1;

	/**
	 * DSR 类型 -- 商品（1）
	 */
	public final static Integer DSR_TYPE_ITEM = 1;

	/**
	 * DSR 类型 -- 动态评分（2）
	 */
	public final static Integer DSR_TYPE_RATE = 2;

	/**
	 * 评分最小分数（1）
	 */
	public final static int RATE_MIN = 1;

	/**
	 * 评分最大分数（5）
	 */
	public final static int RATE_MAX = 5;

	private static final long serialVersionUID = 1L;

	/**
	 * 主键 ID
	 */
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 状态（0：无效；1：有效）
	 */
	private Integer status;

	/**
	 * 一分描述
	 */
	private String memo1;

	/**
	 * 二分描述
	 */
	private String memo2;

	/**
	 * 三分描述
	 */
	private String memo3;

	/**
	 * 四分描述
	 */
	private String memo4;

	/**
	 * 五分描述
	 */
	private String memo5;

	/**
	 * DSR类型（1：商品DSR；2：动态DSR）
	 */
	private Integer dsrType;

	/**
	 * 排序值（序值越小越靠前）
	 */
	private Integer sortOrder;

	/**
	 * 排序类型
	 */
	private Integer sortType;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public DsrDimensionDO() {
	}

	@Override
	public boolean isNew() {
		return (id == null);
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	public String getMemo3() {
		return memo3;
	}
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}

	public String getMemo4() {
		return memo4;
	}
	public void setMemo4(String memo4) {
		this.memo4 = memo4;
	}

	public String getMemo5() {
		return memo5;
	}
	public void setMemo5(String memo5) {
		this.memo5 = memo5;
	}

	public Integer getDsrType() {
		return dsrType;
	}
	public void setDsrType(Integer dsrType) {
		this.dsrType = dsrType;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getSortType() {
		return sortType;
	}
	public void setSortType(Integer sortType) {
		this.sortType = sortType;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((dsrType == null) ? 0 : dsrType.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((memo1 == null) ? 0 : memo1.hashCode());
		result = prime * result + ((memo2 == null) ? 0 : memo2.hashCode());
		result = prime * result + ((memo3 == null) ? 0 : memo3.hashCode());
		result = prime * result + ((memo4 == null) ? 0 : memo4.hashCode());
		result = prime * result + ((memo5 == null) ? 0 : memo5.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sortOrder == null) ? 0 : sortOrder.hashCode());
		result = prime * result + ((sortType == null) ? 0 : sortType.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		DsrDimensionDO other = (DsrDimensionDO) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (dsrType == null) {
			if (other.dsrType != null)
				return false;
		} else if (!dsrType.equals(other.dsrType))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (memo1 == null) {
			if (other.memo1 != null)
				return false;
		} else if (!memo1.equals(other.memo1))
			return false;
		if (memo2 == null) {
			if (other.memo2 != null)
				return false;
		} else if (!memo2.equals(other.memo2))
			return false;
		if (memo3 == null) {
			if (other.memo3 != null)
				return false;
		} else if (!memo3.equals(other.memo3))
			return false;
		if (memo4 == null) {
			if (other.memo4 != null)
				return false;
		} else if (!memo4.equals(other.memo4))
			return false;
		if (memo5 == null) {
			if (other.memo5 != null)
				return false;
		} else if (!memo5.equals(other.memo5))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sortOrder == null) {
			if (other.sortOrder != null)
				return false;
		} else if (!sortOrder.equals(other.sortOrder))
			return false;
		if (sortType == null) {
			if (other.sortType != null)
				return false;
		} else if (!sortType.equals(other.sortType))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DsrDimensionDO [id=" + id + ", name=" + name + ", alias=" + alias + ", status=" + status + ", memo1="
				+ memo1 + ", memo2=" + memo2 + ", memo3=" + memo3 + ", memo4=" + memo4 + ", memo5=" + memo5
				+ ", dsrType=" + dsrType + ", sortOrder=" + sortOrder + ", sortType=" + sortType + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
