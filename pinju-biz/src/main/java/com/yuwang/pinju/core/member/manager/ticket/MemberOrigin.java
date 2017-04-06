package com.yuwang.pinju.core.member.manager.ticket;

import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.domain.member.MemberDO;

/**
 * <p>用户来源</p>
 *
 * @author gaobaowen
 * 2011-6-1 上午09:11:00
 */
public class MemberOrigin {

	private static Map<Integer, MemberOrigin> cache = new HashMap<Integer, MemberOrigin>();

	/**
	 * 会员来源，允许的值有
	 * {@link MemberDO#MEMBER_ORIGIN_SDO}、
	 * {@link MemberDO#MEMBER_ORIGIN_163}、
	 * {@link MemberDO#MEMBER_ORIGIN_RENREN}、
	 * {@link MemberDO#MEMBER_ORIGIN_SINA}、
	 * {@link MemberDO#MEMBER_ORIGIN_KAIXIN001}
	 * {@link MemberDO#MEMBER_ORIGIN_PINJU}
	 */
	private int origin;

	/**
	 * {@link TicketValidator} 验证接口实现类的 Spring bean id
	 */
	private String beanName;

	private MemberOrigin(int origin, String beanName) {
		this.origin = origin;
		this.beanName = beanName;
		register();
	}

	/**
	 * 盛大通行证
	 */
	public final static MemberOrigin SDO = new MemberOrigin(MemberDO.MEMBER_ORIGIN_SDO, "sdoTicketValidator");

	/**
	 * 品聚网
	 */
	public final static MemberOrigin PINJU = new MemberOrigin(MemberDO.MEMBER_ORIGIN_PINJU, null);

	/**
	 * 新浪
	 */
	public final static MemberOrigin SINA = new MemberOrigin(MemberDO.MEMBER_ORIGIN_SINA, "sinaTicketValidator");

	public static MemberOrigin valueOf(int origin) {
		MemberOrigin memberOrigin = cache.get(origin);
		if(memberOrigin == null) {
			return null;
		}
		return memberOrigin;
	}

	public int getOrigin() {
		return origin;
	}

	public TicketValidator getTicketValidator() {
		if (beanName == null) {
			return null;
		}
		return SpringBeanFactory.getBean(beanName, TicketValidator.class);
	}

	private void register() {
		cache.put(origin, this);
	}

	@Override
	public String toString() {
		return "MemberOrign [origin=" + origin + ", beanName=" + beanName + "]";
	}
}
