/**
 *
 */
package com.yuwang.pinju.core.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Project: zizu-biz
 * @Title: BaseManager.java
 * @Package com.yuwang.zizu.core.manager
 * @Description: manager 基础类，提供基本manager支持
 * @author liuboen liuboen@zba.com
 * @date 2011-5-26 上午11:39:14
 * @version V1.0
 */

public class BaseManager extends ApplicationObjectSupport {

	protected  final   Log log =LogFactory.getLog(this.getClass().getName());
	protected TransactionTemplate zizuTransactionTemplate;

	/**
	 * 添加 mysql 的事务支持
	 * @author gaobaowen
	 * @since 2011-06-15 10:17:00
	 */
	private TransactionTemplate mysqlTransactionTemplate;

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
	protected <T> T executeMysqlTransaction(Class<T> returnType, TransactionCallback action) throws ManagerException {
		try {
			return (T)mysqlTransactionTemplate.execute(action);
		} catch (Error e) {
			log.error("executeMysqlTransaction failed", e);
			throw new ManagerException(e);
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
}
