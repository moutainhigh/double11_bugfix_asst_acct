package com.yuwang.pinju.core.logistics.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;

/**
 * 物流公司
 * @author caoxiao
 * @create on 2011-8-17
 */
public interface LogisticsCorpDAO {

	/**
	 * 获取状态正常的物流公司集合
	 * @return
	 * @throws DaoException
	 */
	public List<LogisticsCorpDO> getLogisticsCorpByStatus(LogisticsCorpDO corpDo) throws DaoException;
	
	/**
	 * 新增物流公司
	 * @param corpDO
	 * @throws DaoException
	 */
	public void addLogisticsCorp(LogisticsCorpDO corpDO) throws DaoException;
	
	/**
	 * 修改物流公司相关属性
	 * @param corpDO
	 * @throws DaoException
	 */
	public void updateLogisticsCorp(LogisticsCorpDO corpDO) throws DaoException;
	
	/**
	 * 根据id删除物流公司
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public int delLogisticsCorp(Long id) throws DaoException;
}
