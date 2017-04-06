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
 * @Project: zizu-biz
 * @Title: BaseDAO.java
 * @Package com.yuwang.zizu.core.dao
 * @Description: 数据库操作基础类
 * @author liuboen liuboen@zba.com
 * @date 2011-5-26 下午05:23:56
 * @version V1.0  
 */

public class BaseDAO {

	protected static final int PAGE_SIZE = 4;
	protected static final String ALL_DEFAULT_DB_NAME = "all";
	protected static final Object[] NO_ARGUMENTS = new Object[0];
	private static Integer DEFAULT_MAX_RESULT_SET_SIZE = new Integer(10000); // 能够返回的记录集合最大值的缺省值
	protected Log log = LogFactory.getLog(this.getClass());
	private Integer maxResultSetSize = DEFAULT_MAX_RESULT_SET_SIZE; // 能够返回的记录集合最大值
	private SqlMapClient sqlMapClient;

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

	protected Object executeQueryForObject(String statementName, Object parameterObject) throws DaoException {
		return executeQueryForObject(statementName, parameterObject, true);
	}

	/**
	 * 查找单个对象，可以统计记录的个数
	 *
	 * @param statementName
	 * @param parameterObject
	 * @param qualification
	 * @param isExistsExit
	 *            如果为真，则不在多库之间遍历，有值即返回。
	 *
	 * @return
	 *
	 * @throws DAOException
	 */
	protected Object executeQueryForObject(String statementName, Object parameterObject, boolean isExistsExit) throws DaoException {
		int iTotalCount = 0;
		boolean isInteger = false;

		long startTime = System.currentTimeMillis();
		Object returnObject;
		try {
			returnObject = this.sqlMapClient.queryForObject(statementName, parameterObject);

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
	 *
	 * @return
	 *
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	protected List executeQueryForList(String statementName, Object parameterObject) throws DaoException {
		long startTime = System.currentTimeMillis();
		List list = new ArrayList();
		try {
			list = this.sqlMapClient.queryForList(statementName, parameterObject);

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
	protected Map executeQueryForMap(String statementName, Object parameterObject, String key) throws DaoException {

		long startTime = System.currentTimeMillis();
		Map resultMap = new HashMap();
		try {
			resultMap = this.sqlMapClient.queryForMap(statementName, parameterObject, key);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		long endTime = System.currentTimeMillis();
		logRunTime(statementName, endTime - startTime);
		return resultMap;
	}

	/**
	 * 更新数据库，可以插入一条记录，也可以删除一条记录 返回受影响的条数
	 *
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 *
	 * @return 被更新的记录数
	 *
	 * @throws DAOException
	 */
	protected int executeUpdate(String statementName, Object parameterObject) throws DaoException {
		int updateRows = 0;
		long startTime = System.currentTimeMillis();
		try {
			updateRows = this.sqlMapClient.update(statementName, parameterObject);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		long endTime = System.currentTimeMillis();
		logRunTime(statementName, endTime - startTime);
		return updateRows;
	}

	/**
	 * 插入一条记录
	 *
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 *
	 * @return 新增加的记录主键
	 *
	 * @throws DAOException
	 */
	protected Object executeInsert(String statementName, Object parameterObject) throws DaoException {
		Object back = null;

		long startTime = System.currentTimeMillis();

		// 调用Spring template
		try {
			back = this.sqlMapClient.insert(statementName, parameterObject);
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		long endTime = System.currentTimeMillis();

		logRunTime(statementName, endTime - startTime);

		return back;
	}

	/**
	 *批量插入记录
	 * @param statementName			sqlMap操作的id
	 * @param parameterList			需要插入的记录数
	 * @param num  				多少次插入一次
	 * @return				插入的主键列表，理论上size和parameterList是相等
	 * @throws DAOException
	 */
	protected void executeBatchInsert(String statementName, List<?> parameterList,int num) throws DaoException {
	    try {
		if (parameterList == null || parameterList.isEmpty()) {
		    log.warn("event=[BaseDAO#executeBatchInsert] parameterList is null or empty, statementName = ["+statementName+"]");
		    return;
		}

		long startTime = System.currentTimeMillis();

		sqlMapClient.startBatch();
		for (int i = 0; i <  parameterList.size(); i++) {
		    int count = i+1;

		    this.sqlMapClient.insert(statementName, parameterList.get(i));

		    if (count % num == 0 || count == parameterList.size()) {
			this.sqlMapClient.executeBatch();
                        if (count < parameterList.size()) {
                            sqlMapClient.startBatch();
                        }
		    }
		}


		long endTime = System.currentTimeMillis();

		logRunTime(statementName, endTime - startTime);
	    } catch (Exception e) {
		throw new DaoException(e);
	    }

	}

	/**
	 *批量更新记录
	 * @param statementName			sqlMap操作的id
	 * @param parameterList			需要更新的记录
	 * @param num  				多少次更新一次
	 * @return				插入的主键列表，理论上size和parameterList是相等
	 * @throws DAOException
	 */
	protected int executeBatchUpdate(String statementName, List<?> parameterList,int num) throws DaoException {
	    try {
		if (parameterList == null || parameterList.isEmpty()) {
		    log.warn("event=[BaseDAO#executeBatchUpdate] parameterList is null or empty, statementName = ["+statementName+"]");
		    return 0;
		}

		long startTime = System.currentTimeMillis();

		sqlMapClient.startBatch();
		int total = 0;
		for (int i = 0; i <  parameterList.size(); i++) {
			total = i;
		    int count = i+1;

		    this.sqlMapClient.update(statementName, parameterList.get(i));

		    if (count % num == 0 || count == parameterList.size()) {
		    	this.sqlMapClient.executeBatch();
                if (count < parameterList.size()) {
                    sqlMapClient.startBatch();
                }
		    }
		}

		long endTime = System.currentTimeMillis();
		logRunTime(statementName, endTime - startTime);
			return total;
	    } catch (Exception e) {
	    	throw new DaoException(e);
	    }
	  
	}
	
	
	@SuppressWarnings("unchecked")
	protected List executeQueryForMergeSortList(String statementName, Object parameterObject, int startResult, int maxResults, String orderByMethodString) throws DaoException {

		if (parameterObject instanceof Map) {
			Map param = (Map) parameterObject;
			param.put("startRow", new Integer(1));
			param.put("endRow", new Integer((maxResults + startResult) - 1));
		}

		long startTime = System.currentTimeMillis();

		List list = new ArrayList();
		try {
			list = this.sqlMapClient.queryForList(statementName, parameterObject);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		long endTime = System.currentTimeMillis();

		logRunTime(statementName, endTime - startTime);

		return list;

	}

	/**
	 *
	 * @param statementName
	 * @param parameterObject
	 * @param qualification
	 * @param expectedUpdateNumber
	 * @return
	 * @throws DAOException
	 */
	protected int executeUpdateList(String statementName, Object parameterObject, int expectedUpdateNumber) throws DaoException {
		int updateNumber = 0;

		long startTime = System.currentTimeMillis();
		try {
			updateNumber += this.sqlMapClient.update(statementName, parameterObject);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		long endTime = System.currentTimeMillis();

		logRunTime(statementName, endTime - startTime);

		if ((expectedUpdateNumber > 0) && (updateNumber >= expectedUpdateNumber)) {
			return updateNumber;
		}

		return updateNumber;
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

	/**
	 * 插入或更新一条记录
	 *
	 * @param countStatementName
	 * @param insertStatementName
	 * @param updateStatementName
	 * @param parameterObject
	 * @param dr
	 * @return Object
	 * @throws DAOException
	 */
	protected Object executeInsertOrUpdate(String countStatementName, String insertStatementName, String updateStatementName, Object parameterObject) throws DaoException {
		Integer count = (Integer) this.executeQueryForObject(countStatementName, parameterObject);
		if (null != count && count.intValue() > 0) {
			int u = this.executeUpdate(updateStatementName, parameterObject);
			return new Integer(u);
		}
		return this.executeInsert(insertStatementName, parameterObject);
	}


	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}


}
