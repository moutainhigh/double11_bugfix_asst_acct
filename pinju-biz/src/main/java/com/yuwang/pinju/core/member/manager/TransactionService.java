package com.yuwang.pinju.core.member.manager;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionService {

	private PlatformTransactionManager mysqlTransactionManager;

	public TransactionStatus getRequiredTransactionStatus() {
		return mysqlTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
	}

	public TransactionStatus getTransactionStatus(TransactionDefinition definition) {
		return mysqlTransactionManager.getTransaction(definition);
	}

	public void commit(TransactionStatus status) {
		if(status != null) {
			mysqlTransactionManager.commit(status);
		}
	}

	public void rollback(TransactionStatus status) {
		if(status != null) {
			mysqlTransactionManager.rollback(status);
		}
	}

	public void setMysqlTransactionManager(PlatformTransactionManager mysqlTransactionManager) {
		this.mysqlTransactionManager = mysqlTransactionManager;
	}
}
