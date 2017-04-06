package com.yuwang.pinju.core.margin.manager.impl;

import java.util.ArrayList;
import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.margin.dao.MarginPinJuDAO;
import com.yuwang.pinju.core.margin.dao.MarginPinjuOrderDAO;
import com.yuwang.pinju.core.margin.dao.MarginSellerDAO;
import com.yuwang.pinju.core.margin.dao.MarginSellerOrderDAO;
import com.yuwang.pinju.core.margin.manager.MarginManager;
import com.yuwang.pinju.domain.margin.MarginPinJuDO;
import com.yuwang.pinju.domain.margin.MarginPinjuOrderDO;
import com.yuwang.pinju.domain.margin.MarginSellerDO;
import com.yuwang.pinju.domain.margin.MarginSellerOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginSellerOrderQuery;

public class MarginManagerImpl extends BaseManager implements MarginManager {

	private MarginSellerDAO marginSellerDAO;
	private MarginSellerOrderDAO marginSellerOrderDAO;
	private MarginPinJuDAO marginPinJuDAO;
	private MarginPinjuOrderDAO marginPinjuOrderDAO;

	@Override
	public  void insertMarginSellerOrderRecord(MarginSellerOrderDO marginSellerOrderDO) throws ManagerException{
		log.debug("execute insertMarginOrderRecord");
		try {
			marginSellerOrderDAO.insertMarginSellerOrderRecord(marginSellerOrderDO);
		} catch (DaoException e) {
			throw new ManagerException("insertMarginOrderRecord,error:" ,e);
		}
	}

	@Override
	public int updateMarginSellerRecord(MarginSellerDO marginSellerDO) throws ManagerException{
		log.debug("execute updateSellerMarginRecord");
		try {
			return marginSellerDAO.updateMarginSellerDORecord(marginSellerDO);
		} catch (DaoException e) {
			throw new ManagerException("updateSellerMarginRecord,error:",e);
		}
	}

	@Override
	public void insertMarginPinJuRecord(MarginPinJuDO marginPinJuDO)
			throws ManagerException {
		log.debug("execute insertMarginPinJuRecord");
		try {
			marginPinJuDAO.addMarginPinJuDO(marginPinJuDO);
		} catch (DaoException e) {
			throw new ManagerException("insert PinJu Margin Info faild :",e);
		}
	}

	@Override
	public int updateMarginPinJuRecord(MarginPinJuDO marginPinJuDO)
			throws ManagerException {
		log.debug("execute updateMarginPinJuRecord");
		try {
			return marginPinJuDAO.updateMarginPinJuDO(marginPinJuDO);
		} catch (DaoException e) {
			throw new ManagerException("insert PinJu Margin Info faild :",e);
		}
	}

	@Override
	public List<MarginPinJuDO> getMarginPinJuDO() throws ManagerException {
		log.debug("execute selectAllMarginPinJuRecord");
		List<MarginPinJuDO> margins = new ArrayList<MarginPinJuDO>();
		try {
			margins=marginPinJuDAO.getMarginPinJuDO();
		} catch (DaoException e) {
			throw new ManagerException("select Margin Info faild:",e);
		}
		return margins;
	}

	@Override
	public MarginPinJuDO getMarginPinJuDOById(Long id) throws ManagerException {
		log.debug("execute selectMarginPinJuRecordById");
		MarginPinJuDO marginPinJuDO = new MarginPinJuDO();
		try {
			marginPinJuDO=marginPinJuDAO.getMarginPinJuDOById(id);
		} catch (DaoException e) {
			throw new ManagerException("execute selectMarginPinJuRecordById faild:",e);
		}
		return marginPinJuDO;
	}

	@Override
	public void insertMarginPinJuOrderRecord(MarginPinjuOrderDO marginPinjuOrderDO)
			throws ManagerException {
		log.debug("execute insertMarginPinJuOrderRecord");
		try {
			marginPinjuOrderDAO.insertMarginPinjuOrderRecord(marginPinjuOrderDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<MarginSellerOrderDO> getMarginSellerOrderDOs(MarginSellerOrderQuery marginSellerOrderQuery) throws ManagerException{
		log.debug("execute getMarginSellerOrderDOs");
		try {
			return marginSellerOrderDAO.getMarginSellerOrderDOs(marginSellerOrderQuery);
		} catch (DaoException e) {
			throw new ManagerException("execute getMarginSellerOrderDOs faild:",e);
		}
	}
	
	@Override
	public void insertMarginSellerRecord(MarginSellerDO marginSellerDO)
			throws ManagerException {
		try {
			marginSellerDAO.insertMarginSellerDORecord(marginSellerDO);
		} catch (DaoException e) {
			throw new ManagerException("execute insertMarginSellerRecord faild:",e);
		}		
	}
	
	@Override
	public MarginSellerDO getMarginSellerDOBySellerId(Long sellerId) throws ManagerException{
		try {
			return marginSellerDAO.getMarginSellerDOBySellerId(sellerId);
		} catch (DaoException e) {
			throw new ManagerException("execute getMarginSellerDOBysellerId faild:",e);
		}	
	}

	
	public void setMarginPinjuOrderDAO(MarginPinjuOrderDAO marginPinjuOrderDAO) {
		this.marginPinjuOrderDAO = marginPinjuOrderDAO;
	}

	public void setMarginPinJuDAO(MarginPinJuDAO marginPinJuDAO) {
		this.marginPinJuDAO = marginPinJuDAO;
	}
	
	public void setMarginSellerDAO(MarginSellerDAO marginSellerDAO) {
		this.marginSellerDAO = marginSellerDAO;
	}

	public void setMarginSellerOrderDAO(MarginSellerOrderDAO marginSellerOrderDAO) {
		this.marginSellerOrderDAO = marginSellerOrderDAO;
	}
	
	public void setSellerMarginDAO(MarginSellerDAO marginSellerDAO) {
		this.marginSellerDAO = marginSellerDAO;
	}


}
