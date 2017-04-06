package com.yuwang.pinju.core.member.manager.sequence.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.yuwang.pinju.core.member.manager.sequence.MemberIdGenerator;
import com.yuwang.pinju.core.member.manager.sequence.SequenceGenerator;
import com.yuwang.pinju.core.member.manager.ticket.MemberOrigin;

public class MemberIdGeneratorImpl implements MemberIdGenerator {

	private SequenceGenerator sequenceGenerator;

	private static final String MEMBER_ID_SEQUENCE_NAME = "S_MEMBER_ID";

	/**
	 * 用于分区平均分配的递增序号
	 */
	private AtomicInteger increment = new AtomicInteger(0);

	/**
	 * 数据库分区号集合
	 */
	private int[] partitions = { 0 };

	/**
	 * 获取分区号
	 */
	private int partition() {
		return partitions[(increment.incrementAndGet() & 0x7fffffff) % partitions.length];
	}

	/**
	 * 获取下一个会员编号
	 */
	public long nextMemberId(MemberOrigin memberOrign) {
		return sequenceGenerator.next(MEMBER_ID_SEQUENCE_NAME) * 100000L + memberOrign.getOrigin() * 1000L + partition();
	}

	public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}
}
