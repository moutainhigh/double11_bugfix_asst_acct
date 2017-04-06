/**
 * 品聚生活，创造奇迹
 */
package com.yuwang.pinju.domain.member;

import java.math.BigDecimal;
import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.domain.BaseDO;

/**
 * 关注他的实体
 * 
 * @author liyouguo
 * 
 * @since 2011-7-27
 */
public class ConcernDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5305632936384922832L;

	/**
	 * 关注的紧急程度（备用）
	 */
	private int level;
	/**
	 * 关注者（发私信的发送者）
	 */
	private Long send_user_id;
	private String sendusername;
	/**
	 * 被关注者（发私信的接收者）
	 */
	private Long receive_user_id;	
	private String receiveusername;
	private String ipaddr;
	private Short messageType = 2;	

	/**
	 * 发送时间 （格式:YYYY-MM-DD HH24:MI:SS）
	 */
	private String sendDate = DateUtil.formatDatetime(new Date());	
	private long send_time = new BigDecimal(System.currentTimeMillis()/1000).setScale(0, BigDecimal.ROUND_DOWN).longValue();
	private long createTime = send_time;
	
	/**
	 * 消息类型[2:商城]
	 */
	private Integer message_status;
	public ConcernDO() {
	    super();
	}

	public ConcernDO(int level, Long send_user_id, Long receive_user_id) {
	    super();
	    this.level = level;
	    this.send_user_id = send_user_id;
	    this.receive_user_id = receive_user_id;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Long getSend_user_id() {
		return send_user_id;
	}

	public void setSend_user_id(Long sendUserId) {
		send_user_id = sendUserId;
	}

	public String getSendusername() {
		return sendusername;
	}

	public void setSendusername(String sendusername) {
		this.sendusername = sendusername;
	}

	public Long getReceive_user_id() {
		return receive_user_id;
	}

	public void setReceive_user_id(Long receiveUserId) {
		receive_user_id = receiveUserId;
	}

	public String getReceiveusername() {
		return receiveusername;
	}

	public void setReceiveusername(String receiveusername) {
		this.receiveusername = receiveusername;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public Short getMessageType() {
		return messageType;
	}

	public void setMessageType(Short messageType) {
		this.messageType = messageType;
	}

	public long getSend_time() {
		return send_time;
	}

	public void setSend_time(long sendTime) {
		send_time = sendTime;
	}
	
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Integer getMessage_status() {
	    return message_status;
	}

	public void setMessage_status(Integer messageStatus) {
	    message_status = messageStatus;
	}
}
