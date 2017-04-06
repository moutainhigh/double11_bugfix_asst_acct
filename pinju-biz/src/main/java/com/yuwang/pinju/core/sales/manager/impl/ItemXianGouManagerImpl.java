package com.yuwang.pinju.core.sales.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.sales.dao.ItemXianGouDAO;
import com.yuwang.pinju.core.sales.dao.ItemXianGouUseDAO;
import com.yuwang.pinju.core.sales.manager.ItemXianGouManager;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;
import com.yuwang.pinju.domain.sales.query.ItemXianGouQuery;

public class ItemXianGouManagerImpl extends BaseManager implements
		ItemXianGouManager {

	private ItemXianGouDAO itemXianGouDAO;
	
	private ItemXianGouUseDAO itemXianGouUseDAO;
	
	
	public void setItemXianGouUseDAO(ItemXianGouUseDAO itemXianGouUseDAO) {
		this.itemXianGouUseDAO = itemXianGouUseDAO;
	}

	public void setItemXianGouDAO(ItemXianGouDAO itemXianGouDAO) {
		this.itemXianGouDAO = itemXianGouDAO;
	}

	@Override
	public void addItemXianGouDO(ItemXianGouDO itemXianGouDO)
			throws ManagerException {
		log.debug("execute addItemXianGouDO");
		try {
			itemXianGouDAO.insertItemXianGouDO(itemXianGouDO);
		} catch (DaoException e) {
			throw new ManagerException("addItemXianGouDO,error:" ,e);
		}
		
	}

	@Override
	public ItemXianGouDO getItemXianGouDOById(Long id) throws ManagerException {
		log.debug("execute getItemXianGouDOById");
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		try {
			itemXianGouDO = itemXianGouDAO.getItemXianGouDOById(id);
		} catch (DaoException e) {
			throw new ManagerException("getItemXianGouDOById,error:" ,e);
		}
		return itemXianGouDO;
	}

	@Override
	public ItemXianGouDO getItemXianGouDOByItemId(Long itemId)
			throws ManagerException {
		log.debug("execute getItemXianGouDOByItemId");
		ItemXianGouDO itemXianGouDO = new ItemXianGouDO();
		try {
			itemXianGouDO = itemXianGouDAO.getItemXianGouDOByItemId(itemId);
		} catch (DaoException e) {
			throw new ManagerException("getItemXianGouDOByItemId,error:" ,e);
		}
		return itemXianGouDO;
	}

	@Override
	public boolean updateItemXianGouDO(ItemXianGouDO itemXianGouDO)
			throws ManagerException {
		log.debug("execute updateItemXianGouDO");
		boolean flag = false;
		try {
			flag = itemXianGouDAO.updateItemXianGouDO(itemXianGouDO);
		} catch (DaoException e) {
			throw new ManagerException("updateItemXianGouDO,error:" ,e);
		}
		return flag;
		
	}

	@Override
	public Long getItemXianGouUseCount(ItemXianGouUseDO itemXianGouUseDO)
			throws ManagerException {
		log.debug("execute getItemXianGouUseCount");
		Long count=0L;
		try {
			count= itemXianGouUseDAO.getItemXianGouUseDOCount(itemXianGouUseDO);
		} catch (DaoException e) {
			throw new ManagerException("getItemXianGouUseCount,error:" ,e);
		}
		return count;
		
	}

	@Override
	public void addItemXianGouUseDO(ItemXianGouUseDO itemXianGouUseDO)
			throws ManagerException {
		log.debug("execute addItemXianGouUseDO");
		try {
			itemXianGouUseDAO.insertItemXianGouUseRecord(itemXianGouUseDO);
		} catch (DaoException e) {
			throw new ManagerException("addItemXianGouUseDO,error:" ,e);
		}
	}

	@Override
	public int updateItemXianGouUse(ItemXianGouUseDO itemXianGouUseDO) throws ManagerException{
		log.debug("execute updateItemXianGouUse");
		try {
			return itemXianGouUseDAO.updateItemXianGouUseRecord(itemXianGouUseDO);
		} catch (DaoException e) {
			throw new ManagerException("修改限购码领用记录失败：",e);
		}
	}

	@Override
	public ItemXianGouUseDO getItemXianGouUseDOByCode(Long code) throws ManagerException{
		log.debug("execute getItemXianGouUseDOByCode");
		try {
			return itemXianGouUseDAO.getItemXianGouUseDOByCode(code);
		} catch (DaoException e) {
			throw new ManagerException("ItemXianGouManagerImpl.getItemXianGouUseDOByCode error：",e);
		}
	}

    @Override
    public List<ItemXianGouDO> getItemXianGouDOs(
            ItemXianGouQuery itemXianGouQuery) throws ManagerException
    {
        log.debug("execute getItemXianGouUseDOByCode");
        try
        {
            return itemXianGouDAO.getItemXianGouDOs(itemXianGouQuery);
        }
        catch (DaoException e)
        {
            throw new ManagerException("ItemXianGouManagerImpl.getItemXianGouDOs error：",e);
        }
        
    }	
	
}
