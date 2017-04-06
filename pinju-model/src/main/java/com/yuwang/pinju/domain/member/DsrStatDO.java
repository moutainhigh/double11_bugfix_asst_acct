package com.yuwang.pinju.domain.member;

import java.util.Date;

import com.yuwang.pinju.common.RelationEntity;
import com.yuwang.pinju.domain.BaseDO;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;

/**
 * <p>卖家 DSR 信息统计</p>
 *
 * @author gaobaowen
 * 2011-6-17 下午03:00:01
 */
public class DsrStatDO extends BaseDO implements RelationEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 状态 -- 无效（0）
	 */
	public final static Integer STATUS_INVALID = DsrDimensionDO.STATUS_INVALID;

	/**
	 * 状态 -- 有效（1）
	 */
	public final static Integer STATUS_VALID = DsrDimensionDO.STATUS_VALID;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 会员编号
	 */
	private Long memberId;

	/**
	 * 维度编号
	 */
	private Integer dimensionId;

	/**
	 * 维度名称
	 */
	private String dimensionName;

	/**
	 * 1 分人数
	 */
	private int number1;

	/**
	 * 2 分人数
	 */
	private int number2;

	/**
	 * 3 分人数
	 */
	private int number3;

	/**
	 * 4 分人数
	 */
	private int number4;

	/**
	 * 5 分人数
	 */
	private int number5;

	/**
	 * 总分
	 */
	private long totalScore;

	/**
	 * 总人数
	 */
	private long totalNumber;

	/**
	 * 维度平均分（TOTAL_SCORE*100/TOTAL_NUMBER）
	 */
	private int dimensionAvg;

	/**
	 * 1 分人数占比
	 */
	private int percent1;

	/**
	 * 2 分人数占比
	 */
	private int percent2;

	/**
	 * 3 分人数占比
	 */
	private int percent3;

	/**
	 * 4 分人数占比
	 */
	private int percent4;

	/**
	 * 5 分人数占比
	 */
	private int percent5;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/**
	 * 数据项是否为默认的统计数据（非 SQL 字段映射）
	 */
	private boolean empty;

	public DsrStatDO() {
	}

	/**
	 * <p>根据</p>
	 *
	 * @param memberId
	 * @param dimension
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-10-19 上午10:41:30
	 */
	public static DsrStatDO createEmpty(long memberId, DsrDimensionDO dimension) {
		DsrStatDO empty = new DsrStatDO();
		empty.memberId = memberId;
		empty.dimensionId = dimension.getId();
		empty.dimensionName = dimension.getName();
		empty.empty = true;
		return empty;
	}

	@Override
	public boolean isNew() {
		return (id == null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(Integer dimensionId) {
		this.dimensionId = dimensionId;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	public int getNumber3() {
		return number3;
	}

	public void setNumber3(int number3) {
		this.number3 = number3;
	}

	public int getNumber4() {
		return number4;
	}

	public void setNumber4(int number4) {
		this.number4 = number4;
	}

	public int getNumber5() {
		return number5;
	}

	public void setNumber5(int number5) {
		this.number5 = number5;
	}

	public long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	public long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getDimensionAvg() {
		return dimensionAvg;
	}

	public void setDimensionAvg(int dimensionAvg) {
		this.dimensionAvg = dimensionAvg;
	}

	public int getPercent1() {
		return percent1;
	}

	public void setPercent1(int percent1) {
		this.percent1 = percent1;
	}

	public int getPercent2() {
		return percent2;
	}

	public void setPercent2(int percent2) {
		this.percent2 = percent2;
	}

	public int getPercent3() {
		return percent3;
	}

	public void setPercent3(int percent3) {
		this.percent3 = percent3;
	}

	public int getPercent4() {
		return percent4;
	}

	public void setPercent4(int percent4) {
		this.percent4 = percent4;
	}

	public int getPercent5() {
		return percent5;
	}

	public void setPercent5(int percent5) {
		this.percent5 = percent5;
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

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + dimensionAvg;
		result = prime * result + ((dimensionId == null) ? 0 : dimensionId.hashCode());
		result = prime * result + ((dimensionName == null) ? 0 : dimensionName.hashCode());
		result = prime * result + (empty ? 1231 : 1237);
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + number1;
		result = prime * result + number2;
		result = prime * result + number3;
		result = prime * result + number4;
		result = prime * result + number5;
		result = prime * result + percent1;
		result = prime * result + percent2;
		result = prime * result + percent3;
		result = prime * result + percent4;
		result = prime * result + percent5;
		result = prime * result + (int) (totalNumber ^ (totalNumber >>> 32));
		result = prime * result + (int) (totalScore ^ (totalScore >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DsrStatDO other = (DsrStatDO) obj;
		if (dimensionAvg != other.dimensionAvg)
			return false;
		if (dimensionId == null) {
			if (other.dimensionId != null)
				return false;
		} else if (!dimensionId.equals(other.dimensionId))
			return false;
		if (dimensionName == null) {
			if (other.dimensionName != null)
				return false;
		} else if (!dimensionName.equals(other.dimensionName))
			return false;
		if (empty != other.empty)
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
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (number1 != other.number1)
			return false;
		if (number2 != other.number2)
			return false;
		if (number3 != other.number3)
			return false;
		if (number4 != other.number4)
			return false;
		if (number5 != other.number5)
			return false;
		if (percent1 != other.percent1)
			return false;
		if (percent2 != other.percent2)
			return false;
		if (percent3 != other.percent3)
			return false;
		if (percent4 != other.percent4)
			return false;
		if (percent5 != other.percent5)
			return false;
		if (totalNumber != other.totalNumber)
			return false;
		if (totalScore != other.totalScore)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DsrStatDO [id=" + id + ", memberId=" + memberId + ", dimensionId=" + dimensionId + ", dimensionName="
				+ dimensionName + ", number1=" + number1 + ", number2=" + number2 + ", number3=" + number3
				+ ", number4=" + number4 + ", number5=" + number5 + ", totalScore=" + totalScore + ", totalNumber="
				+ totalNumber + ", dimensionAvg=" + dimensionAvg + ", percent1=" + percent1 + ", percent2=" + percent2
				+ ", percent3=" + percent3 + ", percent4=" + percent4 + ", percent5=" + percent5 + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + ", empty=" + empty + "]";
	}
}
