package com.yuwang.pinju.core.member.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.member.manager.TransactionService;

public class TransactionMemberManager {

	private final static Log log = LogFactory.getLog(TransactionMemberManager.class);

	private TransactionService mysqlTransactionService;

	public void setMysqlTransactionService(TransactionService mysqlTransactionService) {
		this.mysqlTransactionService = mysqlTransactionService;
	}

	protected <T> T executeInTransaction(TransactionExecutor<T> executor, String method) throws ManagerException {
		return executeInTransaction(executor, method, null);
	}

	protected <T> T executeInTransaction(TransactionExecutor<T> executor, String method, Object obj) throws ManagerException {
		if (log.isDebugEnabled()) {
			log.debug("start transaction member manager, method: " + method);
		}
		TransactionStatus status = null;
		T result = null;
		try {
			status = mysqlTransactionService.getRequiredTransactionStatus();
			if (log.isDebugEnabled()) {
				log.debug("prepare execute transaction, method: " + method);
			}
			result = executor.execute();
			if (log.isDebugEnabled()) {
				log.debug("execute transaction finished, method: " + method);
			}
			mysqlTransactionService.commit(status);
			if (log.isDebugEnabled()) {
				log.debug("execute transaction commit finished, method: " + method);
			}
		} catch (Exception e) {
			mysqlTransactionService.rollback(status);
			throw new ManagerException("member transaction failed, method: " + (obj == null ? method : method + ", parameter: " + obj), e);
		}
		return result;
	}

	protected static interface TransactionExecutor<T> {
		public T execute() throws DaoException;
	}
}
