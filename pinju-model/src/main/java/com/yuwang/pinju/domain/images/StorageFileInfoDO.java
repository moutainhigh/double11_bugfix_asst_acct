package com.yuwang.pinju.domain.images;

import java.io.File;
import java.util.Date;


/**
 * 会员文件信息表
 * @author 杨昭
 * @since 2011-9-21
 */
public class StorageFileInfoDO implements java.io.Serializable{
	private static final long serialVersionUID = -3782157086364484039L;
	//文件编号
	private Long id;
	//会员编号
	private Long memberId;
	//文件名称
	private String name;
	//文件路径
	private String path;
	//文件类别
	private Integer type;
	//会员名称
	private String memberName;
	//建立时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModified;
	//文件大小
	private Long size;
	//文件自定义名称
	private String fileName;
	//文件尺寸
	private String dimension;
	//图片分类编号
	private Long imageCategoryId;
	
	private String imageCategoryName;
	//图片状态
	private Integer status;
	
    private String startDate;
	
	private String endDate;
	
	/**
	 * 查询开始数
	 */
	private int startRow;
	/**
	 * 每页显示数
	 */
	private int pageCount;
	
	/**
	 * 排序条件
	 */
	private String orderBy;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public String getImageCategoryName() {
		return imageCategoryName;
	}
	public void setImageCategoryName(String imageCategoryName) {
		this.imageCategoryName = imageCategoryName;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public Long getImageCategoryId() {
		return imageCategoryId;
	}
	public void setImageCategoryId(Long imageCategoryId) {
		this.imageCategoryId = imageCategoryId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	
	public void setStartDate(String startDate) {
		if(startDate!=null && !startDate.isEmpty()){
		  this.startDate = startDate+" 00:00:00";
		}else{
		   this.startDate = startDate;
		}
	}
	public void setEndDate(String endDate) {
		if(endDate!=null && !endDate.isEmpty()){
		  this.endDate = endDate+" 23:59:59";
		}else{
		   this.endDate = endDate;
		}
	}
}
