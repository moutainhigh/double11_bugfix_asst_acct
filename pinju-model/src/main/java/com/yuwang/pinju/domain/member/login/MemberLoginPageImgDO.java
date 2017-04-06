package com.yuwang.pinju.domain.member.login;

/**
 * <p>登录页面图片及链接</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 10:26:17
 */
public class MemberLoginPageImgDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态 -- 有效（1）
     */
    public final static Integer STATUS_VALID = 1;

    /**
     * 状态 -- 无效（0）
     */
    public final static Integer STATUS_INVALID = 0;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 图片位置（高x宽：3,4,5,6,7,8,9：95x95；1,2：95x195；10：195x195；11,12,13：195x95；14：195x195）
     */
    private Integer position;

    /**
     * 图片URL地址
     */
    private String imgUrl;

    /**
     * 图片链接地址
     */
    private String imgLink;

    /**
     * 图片TITLE
     */
    private String imgTitle;

    /**
     * 状态（0：无效；1：有效）
     */
    private Integer status;

    /**
     * 呈现图片DIV框的class名
     */
    private String divClass;

    /**
     * 呈现图片DIV的style位置
     */
    private String divStyle;

    /**
     * 位置空间宽度（px）
     */
    private Integer width;

    /**
     * 位置空间高度（px）
     */
    private Integer height;

    public MemberLoginPageImgDO() {
    }

	public MemberLoginPageImgDO(Integer id, Integer position, String imgUrl, String imgLink, String imgTitle,
			Integer status, String divClass, String divStyle, Integer width, Integer height) {
		this.id = id;
		this.position = position;
		this.imgUrl = imgUrl;
		this.imgLink = imgLink;
		this.imgTitle = imgTitle;
		this.status = status;
		this.divClass = divClass;
		this.divStyle = divStyle;
		this.width = width;
		this.height = height;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getImgTitle() {
		return imgTitle;
	}

	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDivClass() {
		return divClass;
	}

	public void setDivClass(String divClass) {
		this.divClass = divClass;
	}

	public String getDivStyle() {
		return divStyle;
	}

	public void setDivStyle(String divStyle) {
		this.divStyle = divStyle;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((divClass == null) ? 0 : divClass.hashCode());
		result = prime * result + ((divStyle == null) ? 0 : divStyle.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imgLink == null) ? 0 : imgLink.hashCode());
		result = prime * result + ((imgTitle == null) ? 0 : imgTitle.hashCode());
		result = prime * result + ((imgUrl == null) ? 0 : imgUrl.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
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
		MemberLoginPageImgDO other = (MemberLoginPageImgDO) obj;
		if (divClass == null) {
			if (other.divClass != null)
				return false;
		} else if (!divClass.equals(other.divClass))
			return false;
		if (divStyle == null) {
			if (other.divStyle != null)
				return false;
		} else if (!divStyle.equals(other.divStyle))
			return false;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imgLink == null) {
			if (other.imgLink != null)
				return false;
		} else if (!imgLink.equals(other.imgLink))
			return false;
		if (imgTitle == null) {
			if (other.imgTitle != null)
				return false;
		} else if (!imgTitle.equals(other.imgTitle))
			return false;
		if (imgUrl == null) {
			if (other.imgUrl != null)
				return false;
		} else if (!imgUrl.equals(other.imgUrl))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (width == null) {
			if (other.width != null)
				return false;
		} else if (!width.equals(other.width))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberLoginPageImgDO [id=" + id + ", position=" + position + ", imgUrl=" + imgUrl + ", imgLink="
				+ imgLink + ", imgTitle=" + imgTitle + ", status=" + status + ", divClass=" + divClass + ", divStyle="
				+ divStyle + ", width=" + width + ", height=" + height + "]";
	}
}

