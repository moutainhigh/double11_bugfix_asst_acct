package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.shop.ao.ShopUserModuleAO;
import com.yuwang.pinju.core.shop.dao.ShopUserModuleParamDao;
import com.yuwang.pinju.core.shop.dao.ShopUserPageDAO;
import com.yuwang.pinju.core.shop.manager.ShopPageLayoutManager;
import com.yuwang.pinju.core.shop.manager.ShopUserPageManager;
import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * 装修页面管理
 * 
 * @author liyouguo
 * 
 * @since 2011-7-4
 */
public class ShopUserPageManagerImpl extends BaseManager implements
		ShopUserPageManager {

	/**
	 * 页面原型DAO
	 */
	private ShopUserModuleAO shopUserModuleAO;
	private ShopUserPageDAO shopUserPageDAO;
	private ShopPageLayoutManager shopPageLayoutManager;
	private ShopShowInfoAO shopShowInfoAO;
	/**
	 * 用户模块DAO
	 */
	private ShopUserModuleParamDao shopUserModuleParamDao;

	public ShopUserModuleParamDao getShopUserModuleParamDao() {
		return shopUserModuleParamDao;
	}

	public void setShopUserModuleParamDao(
			ShopUserModuleParamDao shopUserModuleParamDao) {
		this.shopUserModuleParamDao = shopUserModuleParamDao;
	}

	public ShopUserPageDAO getShopUserPageDAO() {
		return shopUserPageDAO;
	}

	public void setShopUserPageDAO(ShopUserPageDAO shopUserPageDAO) {
		this.shopUserPageDAO = shopUserPageDAO;
	}

	/**
	 * 删除指定页面（主要是根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object deleteShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserPageDAO.deleteShopUserPage(userPageDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("删除页面出错", e);
		}
	}

	/**
	 * 保存已增加的一条装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object insertShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserPageDAO.insertShopUserPage(userPageDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("插入页面出错", e);
		}
	}

	/**
	 * 获取指定店铺自定义页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopUserPageDO> selectShopUserCustomerPage(
			ShopUserPageDO userPageDO) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserPageDAO.selectShopUserCustomerPage(userPageDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("获取自定义页面列表出错", e);
		}
	}

	/**
	 * 获取相关参数获取所有可装修的页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopUserPageDO> selectShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserPageDAO.selectShopUserPage(userPageDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("获取页面列表出错", e);
		}
	}

	/**
	 * 修改页面结构（根据USERID,PAGEID,SHOPID）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object updateShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserPageDAO.updateShopUserPage(userPageDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("更新页面出错", e);
		}
	}

	/**
	 * 修改页面内容（根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object updateShopUserPageByKey(ShopUserPageDO userPageDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserPageDAO.updateShopUserPageByKey(userPageDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("更新指定页面出错", e);
		}
	}

	/**
	 * 保存用户自定义页面（可能包括新增、删除、修改），放到事务中进行统一管理
	 * 
	 * @param userPageList
	 * @return
	 * @throws ManagerException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Object[] saveUserCustomerPage(final List<ShopUserPageDO> userPageList)
			throws ManagerException {
		// TODO Auto-generated method stub
		if (userPageList == null || userPageList.size() == 0)
			return null;
		return (Object[]) zizuTransactionTemplate
				.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						try {
							Object[] o = new Object[userPageList.size()];
							int i = 0;
							for (ShopUserPageDO entity : userPageList) {
								switch (entity.getSaveType()) {
								case 1:// 新增
									o[i++] = insertShopUserPage(entity);
									break;
								case 2:// 修改
									o[i++] = updateShopUserPageByKey(entity);
									break;
								case 3:// 删除
									shopUserModuleParamDao
											.deleteShopUserModuleParamByPage(entity
													.getId());
									o[i++] = deleteShopUserPage(entity);
									break;
								}
							}
							return o;
						} catch (Exception e) {
							status.setRollbackOnly();
							log.error("保存用户自定义页面失败【saveUserCustomerPage】", e);
							throw new RuntimeException(e);
						}
					}
				});
	}

	/**
	 * 发布用户装修页面，包括页面和模块，因此放到事务中进行处理
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Object releaseShopUserPage(final ShopUserPageDO userPageDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		return zizuTransactionTemplate.execute(new TransactionCallback() {

			@Override
			public Object doInTransaction(TransactionStatus status) {
				// TODO Auto-generated method stub
				try {
					Object o = shopUserPageDAO.releaseShopUserPage(userPageDO);
					ShopUserModuleParamDO userModule = new ShopUserModuleParamDO();
					userModule.setUserId(userPageDO.getUserId());
					userModule.setShopId(userPageDO.getShopId());
					o = shopUserModuleParamDao
							.releaseShopUserModule(userModule);
					return o;
				} catch (Exception e) {
					status.setRollbackOnly();
					log.error("装修发布失败【releaseShopUserPage】", e);
					throw new RuntimeException(e);
				}
			}
		});
	}

	/**
	 * 还原用户装修页面，包括页面及模块，需要放到事务中统一处理。
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Object restoreDesignPage(final ShopUserPageDO userPageDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		return zizuTransactionTemplate.execute(new TransactionCallback() {

			@Override
			public Object doInTransaction(TransactionStatus status) {
				// TODO Auto-generated method stub
				try {
					ShopUserModuleParamDO userModule = new ShopUserModuleParamDO();
					userModule.setUserId(userPageDO.getUserId());
					userModule.setShopId(userPageDO.getShopId());
					Object o = shopUserModuleParamDao
							.restoreUserModule(userModule);
					o = shopUserPageDAO.restoreDesignUserPage(userPageDO);
					return o;
				} catch (Exception e) {
					status.setRollbackOnly();
					log.error("还原装修失败【clearDesignPage】", e);
					throw new RuntimeException(e);
				}
			}
		});
	}

	/**
	 * 获取店铺头部Html
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	public String getTopPageHtml(Long userId) throws ManagerException {
		Integer shopId = getUserShopId(userId);
		if (shopId == null) {
			throw new ManagerException("用户未开店");
		}
		List<ShopPageLayoutDO> list = shopPageLayoutManager
				.queryPageLayoutCache(null, userId, shopId,
						ShopConstants.SHOP_FIRST_PAGE, true);
		boolean isRelease = true;
		String headHtml = "";
		for (ShopPageLayoutDO shopPageLayoutDO : list) {
			ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
			shopUserModuleParamDO
					.setModuleId(new Long(shopPageLayoutDO.getId()));
			shopUserModuleParamDO.setPageId(ShopConstants.SHOP_FIRST_PAGE);
			shopUserModuleParamDO.setUserId(userId);
			shopUserModuleParamDO.setShopId(shopId);
			shopUserModuleParamDO
					.setModuleId(new Long(shopPageLayoutDO.getId()));
			shopUserModuleParamDO.setUserPageId(shopPageLayoutDO
					.getUserPageId());
			shopUserModuleParamDO.setRealUserPageId(shopPageLayoutDO
					.getUserPageId());
			shopUserModuleParamDO.setPreview("true");
			if (shopPageLayoutDO.getType().equals("head")) {
				if (shopUserModuleParamDO.getModuleId().equals(
						ShopConstants.SHOP_TOPSHOPSIGN_MODULEID)) {
					shopUserModuleParamDO.setUserPageId(shopPageLayoutDO
							.getFirstUserPageId());
					headHtml += shopUserModuleAO.getModuleHtml(
							shopUserModuleParamDO, isRelease, "D"
									+ shopPageLayoutDO.getId(), true);
				}
			}
		}
		return headHtml;
	}

	/**
	 * 获取店铺左侧HTML
	 * 
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	public String getLeftPageHtml(Long userId) throws ManagerException {
		Integer shopId = getUserShopId(userId);
		if (shopId == null) {
			throw new ManagerException("用户未开店");
		}
		List<ShopPageLayoutDO> list = shopPageLayoutManager
				.queryPageLayoutCache(null, userId, shopId,
						ShopConstants.SHOP_FIRST_PAGE, true);
		boolean isRelease = true;
		String leftHtml = "";
		for (ShopPageLayoutDO shopPageLayoutDO : list) {
			ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
			shopUserModuleParamDO
					.setModuleId(new Long(shopPageLayoutDO.getId()));
			shopUserModuleParamDO.setPageId(ShopConstants.SHOP_FIRST_PAGE);
			shopUserModuleParamDO.setUserId(userId);
			shopUserModuleParamDO.setShopId(shopId);
			shopUserModuleParamDO
					.setModuleId(new Long(shopPageLayoutDO.getId()));
			shopUserModuleParamDO.setUserPageId(shopPageLayoutDO
					.getUserPageId());
			shopUserModuleParamDO.setRealUserPageId(shopPageLayoutDO
					.getUserPageId());
			shopUserModuleParamDO.setPreview("true");
			if (shopPageLayoutDO.getType().equals("left")) {
				leftHtml += shopUserModuleAO.getModuleHtml(
						shopUserModuleParamDO, isRelease, "D"
								+ shopPageLayoutDO.getId(), true);
			}
		}

		return leftHtml;
	}

	protected Integer getUserShopId(Long userId) throws ManagerException {
		return shopShowInfoAO.queryShopIdByUserId(userId);
	}

	public ShopPageLayoutManager getShopPageLayoutManager() {
		return shopPageLayoutManager;
	}

	public void setShopPageLayoutManager(
			ShopPageLayoutManager shopPageLayoutManager) {
		this.shopPageLayoutManager = shopPageLayoutManager;
	}

	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}

	public ShopUserModuleAO getShopUserModuleAO() {
		return shopUserModuleAO;
	}

	public void setShopUserModuleAO(ShopUserModuleAO shopUserModuleAO) {
		this.shopUserModuleAO = shopUserModuleAO;
	}

	@Override
	public ShopUserPageDO selectShopUserPageById(Long userPageId)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			ShopUserPageDO userPageDO = new ShopUserPageDO();
			userPageDO.setId(userPageId);
			return shopUserPageDAO.selectShopUserPageById(userPageDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
}
