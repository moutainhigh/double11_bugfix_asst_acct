package com.yuwang.pinju.domain.member;

import java.util.regex.Matcher;

import javax.validation.constraints.Pattern;

/**
 * <p>电话号码封装类，格式：[(<区号>)]<电话>[-<分机>]</p>
 *
 * @author gaobaowen
 * 2011-6-9 下午04:00:26
 */
public class PhoneDO {

	private final static java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(?:\\(([0-9]+)\\))?([0-9]+)(?:\\*([0-9]+))?");

	private String area;
	private String tel;
	private String ext;

	public PhoneDO() {
	}

	/**
	 * <p></p>
	 *
	 * @param phone
	 *
	 * @author gaobaowen
	 */
	public PhoneDO(String phone) {
		Matcher matcher = pattern.matcher(phone);
		if(matcher.matches()) {
			this.area = matcher.group(1);
			this.tel = matcher.group(2);
			this.ext = matcher.group(3);
		}
	}

	@Pattern(regexp = "(?:0[1-9][0-9][0-9]?)?", message = "{phone.area.pattern}")
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

	@Pattern(regexp = "(?:[1-9][0-9]{5,7})?", message = "{phone.tel.pattern}")
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	@Pattern(regexp = "(?:[0-9]{1,8})?", message = "{phone.ext.pattern}")
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}

	public String combine() {
		StringBuilder builder = new StringBuilder(30);
		if(area != null && area.trim().length() > 0) {
			builder.append("(").append(area).append(")");
		}
		if(tel != null && tel.trim().length() > 0) {
			builder.append(tel);
		}
		if(ext != null && ext.trim().length() > 0) {
			builder.append("*").append(ext);
		}
		if(builder.length() == 0) {
			return null;
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return "PhoneDO [" + combine() + "]";
	}

	public static void main(String[] args) {
		PhoneDO phone = new PhoneDO("(021)12345678*321");
		System.out.println(phone);
	}
}
