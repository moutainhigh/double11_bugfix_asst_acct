package com.yuwang.pinju.domain.rights;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**  
* @Project: pinju-model
* @Description: 消保维权留言DO
* @author 石兴 shixing@zba.com
* @date 2011-6-28 下午04:16:15
* @update 2011-6-28 下午04:16:15
* @version V1.0  
*/
public class RightsMessageDO extends BaseDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6731273146464349668L;

	private Long id;  //留言ID

	private Long userId;  //留言用户ID
	
	private String userNick;  //留言用户NICK
	
	private String content;  //用户留言
	
	private String voucherPic1;  //留言凭证1
	
	private String voucherPic2;  //留言凭证2
	
	private String voucherPic3;  //留言凭证3

	private Long voucherId;   //维权记录id
	
	private Date gmtCreate;  //记录创建时间
	
	private Date gmtModified;  //记录修改时间


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getVoucherPic1() {
		return voucherPic1;
	}

	public void setVoucherPic1(String voucherPic1) {
		this.voucherPic1 = voucherPic1;
	}

	public String getVoucherPic2() {
		return voucherPic2;
	}

	public void setVoucherPic2(String voucherPic2) {
		this.voucherPic2 = voucherPic2;
	}

	public String getVoucherPic3() {
		return voucherPic3;
	}

	public void setVoucherPic3(String voucherPic3) {
		this.voucherPic3 = voucherPic3;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	
}
