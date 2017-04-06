package com.yuwang.pinju.domain.images;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.filter.PropFilter;

/**
 * 图片空间分类DO
 * @author 杨昭
 * @since 2011-9-21
 */
public class ImagesCategoryDO implements java.io.Serializable{
	private static final long serialVersionUID = -3782157086364484039L;
	//分类ID
	private Long id;
	//会员编号
	private Long memberId;
	//已经使用空间
	private Long userSize;
	//建立时间
	private Date gmtCreate;
	//修改时间
	private Date gmtMobified;
	//一级分类
	private String firstCategory;
	//一级分类名称
	private String firstCategoryName;
	//加减类型0减1加
	private String isType;
	//会员昵称
	private String memberName;
	//移除的一级分类ID
	private String removeFirstCategoryId;
	public String getRemoveFirstCategoryId() {
		return removeFirstCategoryId;
	}
	public void setRemoveFirstCategoryId(String removeFirstCategoryId) {
		this.removeFirstCategoryId = removeFirstCategoryId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getIsType() {
		return isType;
	}
	public void setIsType(String isType) {
		this.isType = isType;
	}
	public String getFirstCategoryName() {
		return firstCategoryName;
	}
	public void setFirstCategoryName(String firstCategoryName) {
		this.firstCategoryName = firstCategoryName;
	}
	public String getFirstCategoryId() {
		return firstCategoryId;
	}
	public void setFirstCategoryId(String firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}
	//一级分类ID
	private String firstCategoryId;
	//二级分类
	private String secondCategory;
	public List<String[]> getSecondCategoryList() {
		return secondCategoryList;
	}
	public void setSecondCategoryList(List<String[]> secondCategoryList) {
		this.secondCategoryList = secondCategoryList;
	}
	//存放二级分类数据
	private List<String[]> secondCategoryList;
	
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
	public Long getUserSize() {
		return userSize;
	}
	public void setUserSize(Long userSize) {
		this.userSize = userSize;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtMobified() {
		return gmtMobified;
	}
	public void setGmtMobified(Date gmtMobified) {
		this.gmtMobified = gmtMobified;
	}
	public String getFirstCategory() {
		return firstCategory;
	}
	public void setFirstCategory(String firstCategory) {
		this.firstCategory = firstCategory;
	}
	public String getSecondCategory() {
		return secondCategory;
	}
	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}
	 /**
     * 根据配置参数生成的配置项
     */
    private Properties categoryProperties;
	 /**
     * 获取二级分类列表
     *
     * @param key 一级分类名字:衣服
     * 
     * @return 二级分类列表采用,分隔符eg:短袖,长袖
     */
	public String getCategoryConfig(String key) {
		if (categoryProperties == null
				&& StringUtils.isNotEmpty(secondCategory)) {
			try {
				secondCategory = PropFilter.doFilter(secondCategory);
				categoryProperties = new Properties();
				categoryProperties.load(new StringReader(secondCategory));
			} catch (Exception ignored) {

			}
		}
		return categoryProperties != null ? categoryProperties.getProperty(key)
				: null;
	}
	/**
	 * 根据分类ID获得一级分类名称和二级分类名称
	 * @author 杨昭
	 * @param categoryId 
	 */
	public String[] getFirstNameOrSecondNameCategoryById(String categoryId){
		String[] returnNames = new String[2];
		boolean bool = false;
		//一级分类
		String[] firsts = firstCategory.split(",");
		for(int i=0;i<firsts.length;i++){
			String[] firstInfo = firsts[i].split("@!@");
			if(categoryId.equals(firstInfo[0])){
				returnNames[0]=firstInfo[1];
				returnNames[1]="";
				bool = true;
			}
		}
		//一级分类中找不到，进入二级分类中寻找
		if(bool==false){
			for(int j=0;j<firsts.length;j++){
				String seconds = getCategoryConfig(firsts[j]);
				if(seconds.indexOf(categoryId+"@!@")>=0){
					String[] secondInfo = seconds.split(",");
					for(int n=0;n<secondInfo.length;n++){
						String[] secondInfos = secondInfo[n].split("@!@");
						if(categoryId.equals(secondInfos[0])){
							returnNames[0] = firsts[j].split("@!@")[1];
							returnNames[1] = secondInfos[1];
							bool =true;
						}
					}
				}
			}
		}
		if(bool==false){
			return null;
		}else{
			return returnNames;
		}
	}
	
	public Long getCategoryIdByFirstName(String name){
		Long categoryId = null;
		if(firstCategory!=null&&firstCategory.length()>0){
			String[] firsts = firstCategory.split(",");
			for(int i=0;i<firsts.length;i++){
				String[] firstInfo = firsts[i].split("@!@");
				if(name.equals(firstInfo[1])){
					categoryId=Long.valueOf(firstInfo[0]);

				}
			}
		}
		return categoryId;
	}
}
