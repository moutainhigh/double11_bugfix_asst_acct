/**
 * 品聚生活，创造奇迹
 */
package com.yuwang.pinju.domain.member;

/**
 * 发私信的实体
 * @author liyouguo
 *
 * @since 2011-7-27
 */
public class PrivateMailDO extends ConcernDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8858165007260919762L;
	
	/**
	 * 发送的内容
	 */
	private String message;

	public PrivateMailDO() {
	    super();
	}

	public PrivateMailDO(int level, Long sender, Long receive_user_id,
		String message) {
	    super(level, sender, receive_user_id);
	    this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
