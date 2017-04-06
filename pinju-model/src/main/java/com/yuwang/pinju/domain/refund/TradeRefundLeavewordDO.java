package com.yuwang.pinju.domain.refund;

import java.util.Date;

import org.apache.commons.lang.xwork.StringUtils;

public class TradeRefundLeavewordDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录修改时间
     */
    private Date gmtModified;

    /**
     * 留言id
     */
    private Long id;

    /**
     * 退款id
     */
    private Long refundId;

    /**
     * 留言者id
     */
    private Long memberId;

    /**
     * 留言者昵称
     */
    private String memberNick;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 图片1
     */
    private String pic1;

    /**
     * 图片2
     */
    private String pic2;

    /**
     * 图片3
     */
    private String pic3;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;
    /**
     * 留言者类型
	 * 1-买家
	 * 2-卖家
	 * 3-客服
     */
    
    private Long userType;

    public Date getGmtModified(){
        return gmtModified;
    }

    public Long getId(){
        return id;
    }

    public Long getRefundId(){
        return refundId;
    }

    public Long getMemberId(){
        return memberId;
    }

    public String getMemberNick(){
        return memberNick;
    }

    public String getContent(){
        return content;
    }

    public String getPic1(){
        return pic1;
    }

    public String getPic2(){
        return pic2;
    }

    public String getPic3(){
        return pic3;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setRefundId(Long refundId){
        this.refundId = refundId;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public void setMemberNick(String memberNick){
        this.memberNick = memberNick;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setPic1(String pic1){
        this.pic1 = pic1;
    }

    public void setPic2(String pic2){
        this.pic2 = pic2;
    }

    public void setPic3(String pic3){
        this.pic3 = pic3;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    
	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result
				+ ((memberNick == null) ? 0 : memberNick.hashCode());
		result = prime * result + ((pic1 == null) ? 0 : pic1.hashCode());
		result = prime * result + ((pic2 == null) ? 0 : pic2.hashCode());
		result = prime * result + ((pic3 == null) ? 0 : pic3.hashCode());
		result = prime * result
				+ ((refundId == null) ? 0 : refundId.hashCode());
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
		TradeRefundLeavewordDO other = (TradeRefundLeavewordDO) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
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
		if (memberNick == null) {
			if (other.memberNick != null)
				return false;
		} else if (!memberNick.equals(other.memberNick))
			return false;
		if (pic1 == null) {
			if (other.pic1 != null)
				return false;
		} else if (!pic1.equals(other.pic1))
			return false;
		if (pic2 == null) {
			if (other.pic2 != null)
				return false;
		} else if (!pic2.equals(other.pic2))
			return false;
		if (pic3 == null) {
			if (other.pic3 != null)
				return false;
		} else if (!pic3.equals(other.pic3))
			return false;
		if (refundId == null) {
			if (other.refundId != null)
				return false;
		} else if (!refundId.equals(other.refundId))
			return false;
		return true;
	}

    
}

