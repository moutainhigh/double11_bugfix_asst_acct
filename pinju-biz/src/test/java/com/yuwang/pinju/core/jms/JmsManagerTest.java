/**
 * 品聚生活，创造奇迹
 */
package com.yuwang.pinju.core.jms;

import java.util.Date;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.jms.manager.JmsManager;
import com.yuwang.pinju.domain.member.ConcernDO;
import com.yuwang.pinju.domain.member.PrivateMailDO;

/**
 * @author liyouguo
 * 
 * @since 2011-7-27
 */
public class JmsManagerTest extends BaseTestCase {

	@SpringBean("jmsManager")
	private JmsManager jmsManager;

	public void testSendPrivateMail() {
		PrivateMailDO privateMail = new PrivateMailDO();
		privateMail.setIpaddr("10.245.130.144");
		privateMail.setMessage("test data");
		privateMail.setReceive_user_id(100001015009000L);
		privateMail.setReceiveusername("member004");
		privateMail.setSend_user_id(100001015109000L);
		privateMail.setSendusername("member005");
		jmsManager.sendPrivateMail(privateMail);
	}

	public void ltestSendConcern() {
		ConcernDO concern = new ConcernDO();
		concern.setSendDate(DateUtil.formatDatetime(new Date()));
		jmsManager.sendConcern(concern);
	}
}
