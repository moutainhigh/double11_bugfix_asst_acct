/**
 * 
 */
package com.yuwang.pinju.core.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.yuwang.pinju.domain.BaseDO;

/**
 * @Project: 品聚读写分离之.读
 * @Description: <p>业务描述</p>
 * @author 石兴 shixing@zba.com
 * @date 2011-10-20 上午9:09:42
 * @update 石兴 2011-10-20 上午9:09:42
 * @version V1.0
 */
public class ReadBaseDAO {

	protected static final int PAGE_SIZE = 4;
	protected static final String ALL_DEFAULT_DB_NAME = "all";
	protected static final Object[] NO_ARGUMENTS = new Object[0];
	private static Integer DEFAULT_MAX_RESULT_SET_SIZE = new Integer(10000); // 能够返回的记录集合最大值的缺省值
	protected Log log = LogFactory.getLog(this.getClass());
	private Integer maxResultSetSize = DEFAULT_MAX_RESULT_SET_SIZE; // 能够返回的记录集合最大值
	private SqlMapClient readSqlMapClient;

	/**
	 * @return Returns the maxResultSetSize.
	 */
	public Integer getMaxResultSetSize() {
		return maxResultSetSize;
	}

	/**
	 * @param maxResultSetSize
	 *            The maxResultSetSize to set.
	 */
	public void setMaxResultSetSize(Integer maxResultSetSize) {
		this.maxResultSetSize = maxResultSetSize;
	}

	/**
	 * 用于调试SQL语句的执行时间.
	 */
	private void logRunTime(String statementName, long runTime) {
		if (log.isDebugEnabled()) {
			log.debug("Sql " + statementName + " executed, Run time estimated: " + runTime + "ms");
		}
	}

	public Object executeQueryForObject(String statementName, Object parameterObject) throws DaoException {
		return executeQueryForObject(statementName, parameterObject, true);
	}

	/**
	 * 查找单个对象，可以统计记录的个数
	 *
	 * @param statementName
	 * @param parameterObject
	 * @param qualification
	 * @param isExistsExit  如果为真，则不在多库之间遍历，有值即返回。
	 * @return
	 * @throws DAOException
	 */
	public Object executeQueryForObject(String statementName, Object parameterObject, boolean isExistsExit) throws DaoException {
		int iTotalCount = 0;
		boolean isInteger = false;
		long startTime = System.currentTimeMillis();
		Object returnObject;
		try {
			returnObject = this.readSqlMapClient.queryForObject(statementName, parameterObject);
			long endTime = System.currentTimeMillis();
			logRunTime(statementName, endTime - startTime);
			// 如果有值被返回
			if (returnObject != null) {
				// 如果查到了DO的对象
				if (BaseDO.class.isAssignableFrom(returnObject.getClass())) {
					return returnObject;
				}
				// 如果查到记录的个数
				if (returnObject instanceof Integer) {
					iTotalCount += ((Integer) returnObject).intValue();
					isInteger = true;
				} else {
					return returnObject;
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return (isInteger ? new Integer(iTotalCount) : null);
	}

	/**
	 * 返回查询列表，如果不能唯一决定一个数据库，将在多库之间分别查询
	 *
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List executeQueryForList(String statementName, Object parameterObject) throws DaoException {
		long startTime = System.currentTimeMillis();
		List list = new ArrayList();
		try {
			list = this.readSqlMapClient.queryForList(statementName, parameterObject);

			long endTime = System.currentTimeMillis();
			logRunTime(statementName, endTime - startTime);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return list;
	}

	/**
	 * 返回一个MAP结果集，KEY是返回DO中的一个字段名称
	 *
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 * @param key
	 *
	 * @return
	 *
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public Map executeQueryForMap(String statementName, Object parameterObject, String key) throws DaoException {
		long startTime = System.currentTimeMillis();
		Map resultMap = new HashMap();
		try {
			resultMap = this.readSqlMapClient.queryForMap(statementName, parameterObject, key);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		long endTime = System.currentTimeMillis();
		logRunTime(statementName, endTime - startTime);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	public List executeQueryForMergeSortList(String statementName, Object parameterObject, int startResult, int maxResults, String orderByMethodString) throws DaoException {

		if (parameterObject instanceof Map) {
			Map param = (Map) parameterObject;
			param.put("startRow", new Integer(1));
			param.put("endRow", new Integer((maxResults + startResult) - 1));
		}
		long startTime = System.currentTimeMillis();
		List list = new ArrayList();
		try {
			list = this.readSqlMapClient.queryForList(statementName, parameterObject);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		long endTime = System.currentTimeMillis();
		logRunTime(statementName, endTime - startTime);
		return list;

	}

	protected boolean isMviewEnabled() {
		return false;
	}

	class QueryNumber {
		public String xid;
		public int startNum;
		public int endNum;

		public QueryNumber(String xid, int startNum, int endNum) {
			this.xid = xid;
			this.startNum = startNum;
			this.endNum = endNum;
		}
	}

	public void setReadSqlMapClient(SqlMapClient readSqlMapClient) {
		this.readSqlMapClient = readSqlMapClient;
	}

}
