package com.yuwang.pinju.core.member.manager.sequence.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSequenceDao;
import com.yuwang.pinju.core.member.manager.sequence.StepCacheSequenceGenerator;
import com.yuwang.pinju.domain.member.MemberSequenceDO;

/**
 * <p>
 * 采用数据表作为序号发生源作为序列号的产生器
 * </p>
 * 
 * @author gaobaowen
 * @since 2011-6-8 下午12:33:32
 */
public class TableSequenceGenerator extends StepCacheSequenceGenerator {

	private final static Log log = LogFactory.getLog(TableSequenceGenerator.class);

	private MemberSequenceDao memberSequenceDAO;

	public TableSequenceGenerator() {
	}

	@Override
	protected SequenceInfo retriveSequenceInfo(String name) {
		log.info("sequence " + name + " cache is exhausted, re-find it");
		int tryAgain = 1;
		while (tryAgain++ < 3) {
			try {
				MemberSequenceDO sequence = memberSequenceDAO.findMemberSequence(name);
				int updateCount = memberSequenceDAO.updateMemberSequence(sequence);
				if (updateCount > 0) {
					SequenceInfo sequenceInfo = new SequenceInfo(sequence.getName(), sequence.getNextSeq(),
							sequence.getSeqStep());
					if (log.isDebugEnabled()) {
						log.debug("get sequence " + name + ", detail: " + sequenceInfo);
					}
					return sequenceInfo;
				}
				log.warn(tryAgain + ". get sequence " + name + " concurrent update failed, try again");
			} catch (DaoException e) {
				log.error("update sequence info error, name: " + name, e);
			}
		}
		return null;
	}

	public void setMemberSequenceDAO(MemberSequenceDao memberSequenceDAO) {
		this.memberSequenceDAO = memberSequenceDAO;
	}
}
