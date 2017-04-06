/**
 *
 */
package com.yuwang.pinju.core.user.ao;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.yuwang.pinju.core.common.ManagerException;

/**
 * @Project: zizu-biz
 * @Title: BaseAO.java
 * @Package com.yuwang.zizu.core.ao
 * @Description: AO Base
 * @author liuboen liuboen@zba.com
 * @date 2011-5-26 10:42:49
 * @version V1.0
 */

public class BaseAO extends ApplicationObjectSupport {
	protected  final   Log log =LogFactory.getLog(this.getClass().getName());
	protected TransactionTemplate zizuTransactionTemplate;

	/**
	 * 添加 mysql 的事务支持
	 * @author gaobaowen
	 * @since 2011-06-15 10:17:00
	 */
	protected TransactionTemplate mysqlTransactionTemplate;

	/**
	 * <p>执行 mysql 事务</p>
	 *
	 * @param <T>        返回类型
	 * @param returnType
	 * @param action
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 */
	@SuppressWarnings("unchecked")
	protected <T> T executeMysqlTransaction(Class<T> returnType, TransactionCallback action) {
		try {
			return (T)mysqlTransactionTemplate.execute(action);
		} catch (Error e) {
			log.error("executeMysqlTransaction failed", e);
			return null;
		}
	}

	/**
	 * <p>执行默认数据源事务</p>
	 *
	 * @param <T>
	 * @param returnType
	 * @param action
	 * @return
	 *
	 * @author gaobaowen
	 */
	@SuppressWarnings("unchecked")
	protected <T> T executeTransactiono(Class<T> returnType, TransactionCallback action) {
		try {
			return (T)zizuTransactionTemplate.execute(action);
		} catch (Error e) {
			log.error("executeTransaction failed", e);
			return null;
		}
	}

	/**
	 * @return the zizuTransactionTemplate
	 */
	public TransactionTemplate getZizuTransactionTemplate() {
		return zizuTransactionTemplate;
	}
	/**
	 * @param zizuTransactionTemplate the zizuTransactionTemplate to set
	 */
	public void setZizuTransactionTemplate(TransactionTemplate zizuTransactionTemplate) {
		this.zizuTransactionTemplate = zizuTransactionTemplate;
	}

	public void setMysqlTransactionTemplate(TransactionTemplate mysqlTransactionTemplate) {
		this.mysqlTransactionTemplate = mysqlTransactionTemplate;
	}

	protected interface WithoutThrowsExceptionCallback<T> {
		public T doInTransaction(TransactionStatus transactionstatus) throws Exception;
	}
}
