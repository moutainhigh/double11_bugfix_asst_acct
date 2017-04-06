package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.CpValueRelationDO;

public interface CpValueRelationDAO {

	/**
	 * 根据父子编号 获得关系对象
	 * 
	 * @param cpId
	 * @return
	 * @throws DaoException
	 */
	public List<CpValueRelationDO> getItemCpValueRelation(long cpId, long sonCpId) throws DaoException;

	/**
	 * 根据类书属性编号 获得管理集合
	 * 
	 * @param cpId
	 * @return
	 * @throws DaoException
	 */
	public List<CpValueRelationDO> getItemCpValueRelationByCpId(long cpId) throws DaoException;

	/**
	 * 根据类目属性及属性值编号 获得關係集合
	 * 
	 * @param cpId
	 * @param cpvId
	 * @return
	 * @throws DaoException
	 */
	public CpValueRelationDO getItemCpValueRelationByCpIdAndCpvId(long cpId, long cpvId) throws DaoException;

}
