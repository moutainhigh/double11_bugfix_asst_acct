package com.yuwang.pinju.core.item.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.yuwang.pinju.core.item.dao.BasePropertyDAO;
import com.yuwang.pinju.domain.item.BasePropertyDO;

/**
 * 
 * @author liming
 * 
 */
public class BasePropertyDAOImpl implements BasePropertyDAO {

	private SqlMapClient sqlMapClient;

	public List<BasePropertyDO> selectAllItemBaseProperty() throws SQLException {
		return sqlMapClient.queryForList("item_base_property.selectAllItemBaseProperty");
	}

	public BasePropertyDO selectItemBasePropertyById(long id) throws SQLException {
		return (BasePropertyDO) sqlMapClient.queryForObject("item_base_property.selectItemBasePropertyById", id);
	}

	public List<BasePropertyDO> selectItemBaseProperty(BasePropertyDO obj) throws SQLException {
		return sqlMapClient.queryForList("item_base_property.selectItemBaseProperty", obj);
	}

	public void insertItemBaseProperty(BasePropertyDO obj) throws SQLException {
		sqlMapClient.insert("item_base_property.insertItemBaseProperty", obj);
	}

	public void deleteAllItemBaseProperty() throws SQLException {
		sqlMapClient.delete("item_base_property.deleteAllItemBaseProperty");
	}

	public void deleteItemBasePropertyById(long id) throws SQLException {
		sqlMapClient.delete("item_base_property.deleteItemBasePropertyById", id);
	}

	public void deleteItemBaseProperty(BasePropertyDO obj) throws SQLException {
		sqlMapClient.delete("item_base_property.deleteItemBaseProperty", obj);
	}

	public void updateItemBaseProperty(BasePropertyDO obj) throws SQLException {
		sqlMapClient.update("item_base_property.updateItemBaseProperty", obj);
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

}
