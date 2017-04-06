package com.yuwang.pinju.core.shop.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.image.ao.ImagesCategoryAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.shop.dao.ShopOpenDAO;
import com.yuwang.pinju.core.shop.dao.ShopPageLayoutDAO;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;
import com.yuwang.pinju.domain.shop.ShopQuery;
import com.yuwang.points.mina.client.PointsClient;

public class ShopOpenManagerImpl extends BaseManager implements ShopOpenManager {
	private ShopOpenDAO shopOpenDAO;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	private MemberManager memberManager;
	
	private CategoryCacheManager categoryCacheManager;
	
	private ShopCategoryManager shopCategoryManager;
	
	private ShopPageLayoutDAO shopPageLayoutDAO;
	
	private ImagesCategoryAO  imagesCategoryAO;
	




	/**
	 * 同意协议
	 * 
	 * @param shopOpenFlowDO
	 * @return
	 */
	@Override
	public Object agreement(ShopOpenFlowDO shopOpenFlowDO) throws ManagerException {
		try {
			return shopOpenDAO.agreement(shopOpenFlowDO);
		} catch (DaoException e) {
			throw new ManagerException("同意协议出错",e);
		}
	}


	/**
	 * 保存店铺信息
	 * 
	 * @param sellerType
	 * @param shopInfo
	 * @return
	 */
	@Override
	public Object saveShopInfo(Integer sellerType, Object shopInfo) throws ManagerException {
		try{
			return shopOpenDAO.saveShopInfo(sellerType, shopInfo);
		}catch(DaoException e){
			e.printStackTrace();
			throw new ManagerException("保存店铺信息出错",e);
		}
	}


	/**
	 * 插入开店流程信息
	 * @param shopOpenFlowDO
	 * @return
	 */
	@Override
	public Object saveShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO)
			throws ManagerException {
		try{
			return shopOpenDAO.saveShopOpenFlow(shopOpenFlowDO);
		}catch(DaoException e){
			throw new ManagerException("插入开店流程信息出错",e);
		}
	}

	/**
	 * 更新开店流程信息
	 * @param shopOpenFlowDO
	 * @return
	 * @throws ManagerException 
	 */
	@Override
	public Object updateShopOpenFlow(ShopOpenFlowDO shopOpenFlowDO)throws ManagerException  {
		try{
			return shopOpenDAO.updateShopOpenFlow(shopOpenFlowDO);
		}catch(DaoException e){
			throw new ManagerException("更新开店流程信息出错",e);
		}
	}

	/**
	 * 获取B的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public List<ShopBusinessInfoDO> queryShopBusinessInfo(Long userId) throws ManagerException {
		try{
			return shopOpenDAO.queryShopBusinessInfo(userId,null);
		}catch(DaoException e){
			throw new ManagerException("获取B的店铺信息出错",e);
		}
	}

	/**
	 * 获取C的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public List<ShopCustomerInfoDO> queryShopCustomerInfo(Long userId) throws ManagerException {
		try{
			return shopOpenDAO.queryShopCustomerInfo(userId,null);
		}catch(DaoException e){
			throw new ManagerException("获取C的店铺信息出错",e);
		}
	}

	/**
	 * 更新B的店铺信息
	 * @param userId  shopBusinessInfoDO
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public Object updateShopBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO) throws ManagerException {
		try{
			return shopOpenDAO.updateShopBusinessInfo(shopBusinessInfoDO);
		}catch(DaoException e){
			throw new ManagerException("更新B的店铺信息出错",e);
		}
	}

	/**
	 * 更新C的店铺信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public Object updateShopCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO) throws ManagerException {
		try{
			return shopOpenDAO.updateShopCustomerInfo(shopCustomerInfoDO);
		}catch(DaoException e){
			throw new ManagerException("更新C的店铺信息出错",e);
		}
	}

	/**
	 * 获取开店流程信息
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public List<ShopOpenFlowDO> queryShopOpenFlow(Long userId) throws ManagerException {
		try{
			return shopOpenDAO.queryShopOpenFlow(userId);
		}catch(DaoException e){
			throw new ManagerException("查询开店流程信息出错",e);
		}
	}

	/**
	 * 查询店铺名称是否存在
	 * @param name
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean queryShopInfosByName(String name,Long userId) throws ManagerException {
		try{
			List  resultList = shopOpenDAO.queryShopInfosByName(name,userId);
			if(null!=resultList && resultList.size()>0){
				ShopInfoDO shopinfoDo=(ShopInfoDO)resultList.get(0);
				if(null!=shopinfoDo && null!=shopinfoDo.getName()){
					return true;
				}
			}
		}catch(DaoException e){
			throw new ManagerException("查询开店流程信息出错",e);
		}
		return false;
	}
	
	/**
	 * 根据shopid获取店铺信息
	 */
	@Override
	public ShopBusinessInfoDO queryShopInfoByShopId(Integer shopId) throws ManagerException {
		try{
			List<ShopBusinessInfoDO> list = shopOpenDAO.queryShopInfoByShopId(shopId);
			if(list != null && list.size()>0){
				return list.get(0);
			}
			return null;
		}catch(DaoException e){
			throw new ManagerException("根据shopid获取店铺信息出错",e);
		}
	}
	
	
	/**
	 * 查询所有有shopid和开店状态为1的店铺信息
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public List<ShopInfoDO> queryShopInfoAllList() throws ManagerException {
		try{
			return shopOpenDAO.queryShopInfoAllList();
		}catch(DaoException e){
			throw new ManagerException("查询所有有shopid和开店状态为1的店铺信息出错",e);
		}
	}
	
	/**
	 * 获取分销所需信息(ShopQuery : shopIdList, province, exchangeType)
	 * @param shopQuery
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public ShopQuery queryListShopBusinessInfo(ShopQuery shopQuery) throws ManagerException {
		if(shopQuery == null){
			shopQuery = new ShopQuery();
		}
		try{
			int count = shopOpenDAO.queryListShopBusinessInfoCount(shopQuery);
			List<ShopBusinessInfoDO> list = shopOpenDAO.queryListShopBusinessInfo(shopQuery);
			shopQuery.setItems(count);
			shopQuery.setResultList(list);
			return shopQuery;
		}catch(DaoException e){
			throw new ManagerException("获取分销所需信息出错",e);
		}
	}
	
	/**
	 * 设置店铺为供应商
	 * @param shopId
	 * @param isSupplier
	 * @return
	 */
	public Object setSellerIsSupplier(Integer shopId, Integer isSupplier) throws ManagerException {
		try{
			return shopOpenDAO.setSellerIsSupplier(shopId, isSupplier);
		}catch(DaoException e){
			throw new ManagerException("获取分销所需信息出错",e);
		}
	}
	
	/**
	 * 保存保证金缴纳信息(开店并没有完成,只记录保证金类型和金额)
	 * @param shopOpenFlowDO
	 * @param exchangeMargin
	 * @param exchangePrice
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public Object saveExchangeInfo(Long userId, Integer exchangeMargin, String exchangePrice)
			throws ManagerException {
		Object object = null;
		try {
			//转成分
			long marginPrice = new Money(exchangePrice).getCent();
			ShopInfoDO shopInfoDO = new ShopInfoDO();
			shopInfoDO.setUserId(userId);
			shopInfoDO.setExchangeMargin(String.valueOf(exchangeMargin));
			shopInfoDO.setConfiguration("MARGIN_PRICE="+marginPrice);
			object = shopShowInfoManager.updateShopBaseInfo(shopInfoDO);
		} catch (ManagerException e) {
			log.error("保存保证金缴纳信息出错",e);
		}
		return object;
	}
	
	private void setSellerQuality(ShopInfoDO shopInfoDO, String enterpriseName) throws ManagerException{
		try {
			SellerQualityDO sellerQualityDO = new SellerQualityDO();
			sellerQualityDO.setMemberId(shopInfoDO.getUserId());
			sellerQualityDO.setNickname(shopInfoDO.getNickname());
			sellerQualityDO.setShopId(shopInfoDO.getShopId());
			sellerQualityDO.setCompanyName(enterpriseName);
			sellerQualityDO.setShopName(shopInfoDO.getName());
			sellerQualityDO.setSellerType(String.valueOf(Integer.parseInt(shopInfoDO.getSellerType()) + 1));

			String categoryId = shopInfoDO.getShopCategory();
			String categoryName = "";
			List<CategoryDO> categoryList = categoryCacheManager.getItemCategoryListByParentId(0);
			if (categoryList != null && categoryList.size() > 0) {
				for (int i = 0; i < categoryList.size(); i++) {
					if (categoryId != null && categoryId.equals(String.valueOf((categoryList.get(i)).getId()))) {
						categoryName = categoryList.get(i).getName();
					}
				}
			}
			sellerQualityDO.setCategoryId(Long.parseLong(categoryId));
			sellerQualityDO.setCategoryName(categoryName);
			sellerQualityDO.setApproveStatus(ShopConstant.APPROVE_STATUS_YES);
			sellerQualityDO.setLocalName(shopInfoDO.getCity());
			sellerQualityDO.setLevel(SellerQualityDO.LEVEL_D);

			sellerQualityDO.setCpType(Integer.parseInt(shopInfoDO.getExchangeMargin()));
			sellerQualityDO.setMargin(Long.valueOf(shopInfoDO.getShopInfoConfig("MARGIN_PRICE")));

			sellerQualityDO.setGmtCreate(new Date());

			memberManager.initSellerQuality(sellerQualityDO);
		} catch (ManagerException e) {
			log.error("插入卖家资质表出错!", e);
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 开店成功(给消保金调用)
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Result setShopIsOpenForMargin(final Long userId) throws ManagerException {
		log.warn("setShopIsOpenForMargin");
		final ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, ShopConstant.APPROVE_STATUS_NO);
		final Result result = new ResultSupport();
		result.setSuccess(false);
		String enterpriseName = "";
		int shopId = 0;
		if(shopInfoDO != null){
			shopId = shopInfoDO.getId();
			shopInfoDO.setUserId(userId);
			shopInfoDO.setShopId(shopId);
			shopInfoDO.setOpenDate(new Date());
			shopInfoDO.setApproveStatus(ShopConstant.APPROVE_STATUS_YES);
			if(!shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_IShop))){
				if(shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_C))){
					List<ShopCustomerInfoDO> cList = queryShopCustomerInfo(userId);
					if(cList!=null && cList.size()>0){
						ShopCustomerInfoDO shopCustomerInfoDO = (ShopCustomerInfoDO)cList.get(0);
						enterpriseName = shopCustomerInfoDO.getEnterpriseName();
					}
				}else if(shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_B)) || 
						shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_B2))){
					List<ShopBusinessInfoDO> bList = queryShopBusinessInfo(userId);
					if(bList!=null && bList.size()>0){
						ShopBusinessInfoDO shopBusinessInfoDO = (ShopBusinessInfoDO)bList.get(0);
						enterpriseName = shopBusinessInfoDO.getEnterpriseName();
					}
				}
			}
			log.warn("begin open shop");
			//卖家资质信息
			try{
				setSellerQuality(shopInfoDO, enterpriseName);
			}catch (ManagerException e){
				log.error("插入卖家资质表出错!", e);
			}
			zizuTransactionTemplate.execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus status) {
					Boolean success=true;
					try {
						
						List<ShopOpenFlowDO> list = queryShopOpenFlow(userId);
						if (list != null && list.size() > 0) {
							ShopOpenFlowDO shopOpenFlowDO = (ShopOpenFlowDO) list.get(0);
							shopOpenFlowDO.setAuditStatus(ShopConstant.AUDIT_STATUS_OPEN_END);
							shopOpenFlowDO.setShopId(shopInfoDO.getId());
							// 更新shop_base_info表
							shopShowInfoManager.updateShopBaseInfo(shopInfoDO);
							// 更新shop_open_flow表
							updateShopOpenFlow(shopOpenFlowDO);
							result.setSuccess(success);
						}

					} catch (ManagerException e) {
						log.error("开店成功(给消保金调用)出错!", e);
						success = false;
						result.setSuccess(success);
					}
					return result;
				}
			});
			try {
				PointsClient.queryInsertAccount(userId);
			} catch (Exception ex) {
				log.error("积分账户初始化出错", ex);
			}
			try {
				ShopCategoryDO categoryDO = new ShopCategoryDO();
				categoryDO.setUserId(userId);
				categoryDO.setGmtCreate(new Date());
				categoryDO.setGmtModified(new Date());
				categoryDO.setFirstCategory("");
				categoryDO.setSecondCategory("");
				categoryDO.setShopId(shopId);
				shopCategoryManager.insertShopCategroy(categoryDO);
			} catch (Exception ex) {
				log.error("店铺商品分类初始化出错", ex);
			}
			try {
				List result2 = (List) shopPageLayoutDAO.queryPageLayout(ShopConstants.SHOP_FIRST_PAGE);
				shopPageLayoutDAO.insertUserPageLayout(userId, shopId, ShopConstants.SHOP_FIRST_PAGE,
						(String) ((Map) result2.get(0)).get("PAGE_STRUCTURE"));
			} catch (Exception ex) {
				log.error("店铺装修初始化出错", ex);
			}
			try {
				imagesCategoryAO.getImagesCategoryObject(userId);
			} catch (Exception ex) {
				log.error("图片分类初始化出错", ex);
			}
		}
		result.setSuccess(true);
		return result;
		
	}
	
	/**
	 * 根据店铺id更新店铺状态
	 * @param shopId
	 * @return 数据库状态信息
	 * @throws ManagerException
	 */
	@Override
	public Object updateShopApproveStatusByShopId(Integer shopId, Integer approveStatus) throws ManagerException {
		if(shopId == null){
			return null;
		}
		try {
			Object object = shopOpenDAO.updateShopApproveStatusByShopId(shopId, approveStatus);
			return object;
		} catch (DaoException e) {
			log.error("根据店铺id更新店铺状态出错!", e);
			throw new ManagerException("根据店铺id更新店铺状态出错!", e);
		}
		
	}
	
	
	/**
	 * 更新i小铺信息 -- 2.0新
	 * @param shopIshopInfoDO
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public Object updateIShopInfo(
			ShopIshopInfoDO shopIshopInfoDO) throws ManagerException {
		try{
			return shopOpenDAO.updateIShopInfo(shopIshopInfoDO);
		}catch(DaoException e){
			throw new ManagerException("更新C的店铺信息出错",e);
		}
	}
	
	/**
	 * 获取i小铺信息--2.0新
	 */
	@Override
	public List<ShopIshopInfoDO> queryShopIShopnfo(long userId) throws ManagerException {
		try {
			return shopOpenDAO.queryShopIShopnfo(userId);
		} catch (DaoException e) {
			throw new ManagerException("获取i小铺的店铺信息错误",e);
		}
	}
	
	
	/**
	 * 删除C店信息【2.0新增版本】
	 * @param id
	 * @param userId
	 * @return
	 */
	public void deleteShopCustomerInfo(Long userId)throws ManagerException{
		try {
			 shopOpenDAO.deleteShopCustomerInfo(userId);
		} catch (DaoException e) {
			throw new ManagerException("删除C店信息错误",e);
		}
	}
	
	
	/***
	 * 删除B店信息【2.0新增版本】
	 * @param id
	 * @param userId
	 * @return
	 */
	public void deleteShopBusinessInfo(Long userId)throws ManagerException{
		try {
			 shopOpenDAO.deleteShopBusinessInfo(userId);
		} catch (DaoException e) {
			throw new ManagerException("删除B店信息错误",e);
		}	
	}
	
	/***
	 * 删除i小铺信息【2.0新增版本】
	 * @param id
	 * @param userId
	 * @return
	 */
	public void deleteShopIShopInfo(Long userId)throws ManagerException{
		try {
			 shopOpenDAO.deleteShopIShopInfo(userId);
		} catch (DaoException e) {
			throw new ManagerException("删除i小铺信息错误",e);
		}
	}
	
	/***
	 * 删除店铺基本信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopInfo(Long userId)throws ManagerException{
		try {
			 shopOpenDAO.deleteShopInfo(userId);
		} catch (DaoException e) {
			throw new ManagerException("删除店铺基本信息出错",e);
		}
	}
	
	/***
	 * 删除开店流程信息【2.0新增版本】
	 * @param userId
	 * @return
	 */
	public void deleteShopOpenFlow(Long userId)throws ManagerException{
		try {
			 shopOpenDAO.deleteShopOpenFlow(userId);
		} catch (DaoException e) {
			throw new ManagerException("删除开店流程信息错误",e);
		}
	}
	
	/**
     * 签订协议初始化开店流程店铺基本信息数据【2.0新增版本】
     * @param sellerType
     * @param shopInfo
     * @param shopOpenFlowDO
     * @return
     */
	@SuppressWarnings("unchecked")
	public Object signAgreementLoadData(final Integer sellerType,final Object shopInfo,final ShopOpenFlowDO shopOpenFlowDO){
		final Result result = new ResultSupport();
		result.setSuccess(false);	
		Result o =(Result)zizuTransactionTemplate.execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus status) {
					Boolean success=true;
					try {
						saveShopInfo(sellerType, shopInfo);
						saveShopOpenFlow(shopOpenFlowDO);
						result.setResultCode("SUCCESS");
					}catch(ManagerException ex){
						log.error("签订协议时初始化店铺开店流程数据失败");
						result.setResultCode("FAIL");
						success=false;
					}finally{
						if(!success){
							status.setRollbackOnly();
						}
						result.setSuccess(success);
					}
					return result;
				}
			});
		return o;
	}
	
	/****
	 * 删除店铺相关的店铺数据【2.0新增版本】
	 * @param sellerType
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object deleteShopDateByUpdateSellerType(final Integer sellerType,final Long userId){
		final Result result = new ResultSupport();
		result.setSuccess(false);	
		Result o = (Result)zizuTransactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				Boolean success=true;
				try {
					if(sellerType!=null && sellerType.equals(ShopConstant.SELLER_TYPE_C)){
						deleteShopCustomerInfo(userId);
					}else if(sellerType!=null &&  sellerType.equals(ShopConstant.SELLER_TYPE_IShop)){
						deleteShopIShopInfo(userId);
					}else{
						deleteShopBusinessInfo(userId);
					}
					deleteShopInfo(userId);
					deleteShopOpenFlow(userId);
					result.setResultCode("SUCCESS");
				}catch(ManagerException ex){
					log.error("删除店铺相关的店铺数据失败");
					success=false;
					result.setResultCode("FAIL");
				}catch(Exception e){
					log.error("删除店铺相关的店铺数据失败");
					success=false;
					result.setResultCode("FAIL");
				}finally{
					if(!success){
						status.setRollbackOnly();
					}
					result.setSuccess(success);
				}
				return result;
			}
		});
	   return o;
	}
	
	public ShopOpenDAO getShopOpenDAO() {
		return shopOpenDAO;
	}

	public void setShopOpenDAO(ShopOpenDAO shopOpenDAO) {
		this.shopOpenDAO = shopOpenDAO;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public void setShopCategoryManager(ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}


	public void setShopPageLayoutDAO(ShopPageLayoutDAO shopPageLayoutDAO) {
		this.shopPageLayoutDAO = shopPageLayoutDAO;
	}


	public void setImagesCategoryAO(ImagesCategoryAO imagesCategoryAO) {
		this.imagesCategoryAO = imagesCategoryAO;
	}

	

}
