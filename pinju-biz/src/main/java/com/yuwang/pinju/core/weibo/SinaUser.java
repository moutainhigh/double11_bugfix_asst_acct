package com.yuwang.pinju.core.weibo;

import java.io.Serializable;

import com.yuwang.pinju.core.weibo.json.JSONException;
import com.yuwang.pinju.core.weibo.json.JSONObject;

public class SinaUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
     * 新浪微博账户性别 -- 女（0）
     */
    public final static String SINA_GENDER_FEMALE = "m";
    
    /**
     * 新浪微博账户性别 -- 男（1）
     */
    public final static String SINA_GENDER_MALE = "f";
    
    /**
     * 新浪微博账户性别 -- 未知（9）
     */
    public final static String SINA_GENDER_UNKONWN = "n"; 

	/**
	 * 用户UID
	 */
	private String uid;
	/**
	 * 微博昵称
	 */
	private String screenName;
	/**
	 * 省份编码
	 */
	private int province;
	/**
	 * 城市编码
	 */
	private int city;
	/**
	 * 通信地址
	 */
	private String location;
	
	/**
	 * 个人描述
	 */
	private String description;
	
	/**
	 * 用户博客地址
	 */
	private String url;
	
	/**
	 * 自定义图像
	 */
	private String profileImageUrl;
	
	/**
	 * 用户个性化URL
	 */
	private String userDomain;
	
	/**
	 * 用户性别
	 */
	private String gender;
	
	/**
	 * 大头像地址
	 */
	private String avatarLarge;
	
	public SinaUser(){}
	
	public SinaUser(JSONObject json) throws WeiboException {
		if(json!=null){
			try {
				this.uid = json.getString("id");
				this.screenName = json.getString("screen_name");
				this.province = json.getInt("province");
				this.city = json.getInt("city");
				this.location = json.getString("location");
				this.description = json.getString("description");
				this.url = json.getString("url");
				this.profileImageUrl = json.getString("profile_image_url");
				this.userDomain = json.getString("domain");
				this.gender = json.getString("gender");
				this.avatarLarge = json.getString("avatarLarge");
			} catch (JSONException jsone) {
				throw new WeiboException(jsone.getMessage() + ":" + json.toString(), jsone);
			}
		}
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvatarLarge() {
		return avatarLarge;
	}

	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	} 
}
