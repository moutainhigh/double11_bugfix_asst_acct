package com.yuwang.pinju.core.jms.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.rate.json.DsrOrderCommentDO;
import com.yuwang.pinju.domain.member.ConcernDO;
import com.yuwang.pinju.domain.member.PrivateMailDO;

public interface JmsManager {

	/**
	 * <p>发送 DSR 评价数据</p>
	 *
	 * @param message DSR 评价数据 JSON 内容，
	 * <a href="http://doc.zba.com/mediawiki/index.php/%E7%A4%BE%E5%8C%BA%E4%BA%A4%E4%BA%92%E6%8E%A5%E5%8F%A3#.E4.B9.B0.E5.AE.B6.E8.B4.AD.E4.B9.B0.E5.95.86.E5.93.81.E5.90.8E.E8.AF.84.E4.BB.B7">消息格式</a>
	 * 在这里
	 *
	 * @author gaobaowen
	 * @since 2011-10-11 16:59:41
	 */
	void sendDsrComment(DsrOrderCommentDO orderComment) throws ManagerException;

	/**
	 * 发送私信的MQ接口
	 * @param privateMail 发私信实体
	 */
	void sendPrivateMail(PrivateMailDO privateMail);

	/**
	 * 关注他的MQ接口
	 * @param concernDO 关注他实体
	 */
	void sendConcern(ConcernDO concernDO);
}
