package com.yuwang.pinju.core.item.dao;

import java.sql.SQLException;
import java.util.List;

import com.yuwang.pinju.domain.item.BasePropertyDO;





/**
 * 
 * @author limingf
 * 
 */
public interface BasePropertyDAO {

	/**
	 * 查询所有ItemBaseProperty
	 * 
	 * @return ItemBaseProperty对象集合
	 * @throws SQLException
	 */
	public List<BasePropertyDO> selectAllItemBaseProperty()
			throws SQLException;

	/**
	 * 根据主键查询ItemBaseProperty
	 * 
	 * @paramid 主键
	 * @return ItemBaseProperty对象
	 * @throws SQLException
	 */
	public BasePropertyDO selectItemBasePropertyById(long id)
			throws SQLException;

	/**
	 * 根据对象查询所有ItemBaseProperty
	 * 
	 * @param obj
	 *            对象
	 * @return ItemBaseProperty对象集合
	 * @throws SQLException
	 */
	public List<BasePropertyDO> selectItemBaseProperty(BasePropertyDO obj)
			throws SQLException;

	/**
	 * 插入ItemBaseProperty
	 * 
	 * @param obj
	 *            对象
	 * @throws SQLException
	 */
	public void insertItemBaseProperty(BasePropertyDO obj)
			throws SQLException;

	/**
	 * 删除所有ItemBaseProperty
	 * 
	 * @throws SQLException
	 */
	public void deleteAllItemBaseProperty() throws SQLException;

	/**
	 * 根据主键删除ItemBaseProperty
	 * 
	 * @param id
	 *            主键
	 * @throws SQLException
	 */
	public void deleteItemBasePropertyById(long id) throws SQLException;

	/**
	 * 根据对象删除ItemBaseProperty
	 * 
	 * @param obj
	 *            对象
	 * @throws SQLException
	 */
	public void deleteItemBaseProperty(BasePropertyDO obj)
			throws SQLException;

	/**
	 * 更新ItemBaseProperty
	 * 
	 * @param obj
	 *            对象
	 * @throws SQLException
	 */
	public void updateItemBaseProperty(BasePropertyDO obj)
			throws SQLException;

}
