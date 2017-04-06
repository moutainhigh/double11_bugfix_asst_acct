package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.BaseValueDO;

/**
 * 
 * @author liming
 * 
 */
public interface BaseValueDAO {

	/**
	 * 根据主键查询ItemBaseValue
	 * 
	 * @author liuboen
	 * @paramid 主键
	 * @return ItemBaseValue对象
	 * @throws DaoException
	 */
	public BaseValueDO selectItemBaseValueById(long id) throws DaoException;

	/**
	 * 通过值获得基本值数量
	 * 
	 * @param value
	 * @return
	 * @throws DaoException
	 */
	public BaseValueDO getBaseValueByValue(String value) throws DaoException;

	/**
	 * 插入类目基本属性
	 * 
	 * @param baseValueDO
	 * @throws DaoException
	 */
	public Object insertItemBaseValue(BaseValueDO baseValueDO) throws DaoException;

	/**
	 * 获得基本属性名称
	 * 
	 * @param propertyValueList
	 * @return
	 * @throws DaoException
	 */
	public List<BaseValueDO> getBaseValueNameByIds(List propertyValueList) throws DaoException;

}
