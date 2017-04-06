/**
 * 
 */
package com.yuwang.pinju.web.module.distribute.screen;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.distribute.ao.DistributeGoodsManagerAO;
import com.yuwang.pinju.core.distribute.ao.DistributeSupplierAO;
import com.yuwang.pinju.core.distribute.ao.DistributorAO;
import com.yuwang.pinju.core.shop.ao.ShopOpenAO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author xiazhenyu
 * 
 */
public class SupplierApplyAction extends BaseWithUserAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8539910380096984065L;

	protected ShopOpenAO shopOpenAO;

	protected DistributeSupplierAO distributeSupplierAO;

	protected DistributeSupplierDO distributeSupplierDO;

	protected DistributeGoodsManagerAO distributeGoodsManagerAO;

	@Autowired
	protected DistributorAO distributorAO;
	
	/**
	 * struts 返回字符串 没有申请供应商
	 */
	protected final String NOT_APPLY = "NOT_APPLY";

	/**
	 * struts 返回字符串 没有分销商品
	 */
	protected final String NO_ITEMS = "NO_ITEMS";

	/**
	 * struts 返回字符串 没有发布招募书
	 */
	protected final String NOT_RELEASE = "NOT_RELEASE";

	/**
	 * 页面中AJAX请求JSON返回值 true:申请成功 false:申请失败
	 */
	private Boolean status = false;

	/**
	 * 用户昵称
	 */
	private String userName;
	
	private String successMessage;

	/**
	 * 申请供应商
	 * 
	 * @return
	 */
	public String applySupplier() {
		if (checkIsSupplier(getUserId())) {
			return SUCCESS;
		} else {
			errorMessage = null;
			return NOT_APPLY;
		}
	}

	/**
	 * 申请供应商 AJAX请求
	 * 
	 * @return
	 */
	public String apply() {
		if (checkIsSupplier()) {
			setStatus(true);
		}
		return "json";
	}

	/**
	 * 申请成功跳转请求
	 * 
	 * @return
	 */
	public String welcome() {
		return SUCCESS;
	}

	/**
	 * 检查是否已经申请成为供应商
	 * 
	 * @return true:已申请 false:未申请
	 */
	@SuppressWarnings("unchecked")
	protected boolean checkIsSupplier() {
		List shopInfoList = (ArrayList) shopOpenAO.queryShopBusinessInfo(getUserId());
		errorMessage = null;
		if (0 < shopInfoList.size()) {
			DistributeSupplierParamDO distributeSupplierDO = new DistributeSupplierParamDO();
			ShopBusinessInfoDO shopBusinessInfoDO = (ShopBusinessInfoDO) shopInfoList.get(0);
			if (null != shopBusinessInfoDO.getShopId() 
					&& shopBusinessInfoDO.getApproveStatus() != null 
					&& shopBusinessInfoDO.getApproveStatus().intValue() != ShopConstant.APPROVE_STATUS_CLOSE) {
				distributeSupplierDO.setShopId(shopBusinessInfoDO.getShopId());
				CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
				distributeSupplierDO.setMemberId(login.getMemberId());
				distributeSupplierDO.setNickName(login.getNickname());
				setDistributeSupplierDO(distributeSupplierAO.applySupplier(distributeSupplierDO));
			}else{
				errorMessage = "您的品牌店申请流程还未结束。";
				return false;
			}
			distributeSupplierDO.setShopId(shopBusinessInfoDO.getShopId());
			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			distributeSupplierDO.setMemberId(login.getMemberId());
			distributeSupplierDO.setNickName(login.getNickname());
			setDistributeSupplierDO(distributeSupplierAO.applySupplier(distributeSupplierDO));
			// insert error
			if (null == getDistributeSupplierDO()) {
				errorMessage = "系统繁忙，请稍后再试。";
				return false;
			}
			return true;
		} else {
			errorMessage = "您还没有拥有品牌店。";
			return false;
		}
	}

	/**
	 * 根据userID 检查是否是供应商
	 * 
	 * @param userId
	 * @return true:是供应商 false:不是
	 */
	protected boolean checkIsSupplier(long userId) {
		distributeSupplierDO = distributeSupplierAO.getDistributeSupplier(userId);
		if (null == distributeSupplierDO) {
			return false;
		}
		return true;
	}

	/**
	 * 检查是否已经分销商品
	 * 
	 * @return true:已分销 false:未分销
	 */
	protected boolean checkHasItem() {
		if (0 == distributeGoodsManagerAO.getDistributeGoodsCount(distributeSupplierDO.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * 检查是否已经发布招募书
	 * 
	 * @return true:已发布 false:未发布
	 */
	protected boolean checkIsRelease() {
		if (null == distributeSupplierDO.getApplyTitle() || null == distributeSupplierDO.getApplyContent()) {
			return false;
		}
		return true;
	}

	public void setShopOpenAO(ShopOpenAO shopOpenAO) {
		this.shopOpenAO = shopOpenAO;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setDistributeSupplierAO(DistributeSupplierAO distributeSupplierAO) {
		this.distributeSupplierAO = distributeSupplierAO;
	}

	public String getUserName() {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		userName = login.getNickname();
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public DistributeSupplierDO getDistributeSupplierDO() {
		return distributeSupplierDO;
	}

	public void setDistributeSupplierDO(DistributeSupplierDO distributeSupplierDO) {
		this.distributeSupplierDO = distributeSupplierDO;
	}

	public void setDistributeGoodsManagerAO(DistributeGoodsManagerAO distributeGoodsManagerAO) {
		this.distributeGoodsManagerAO = distributeGoodsManagerAO;
	}

	public String getSuccessMessage() {
	    return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
	    this.successMessage = successMessage;
	}

	public Boolean getStatus() {
	    return status;
	}
	
	public String getErrorMessage() {
		return super.errorMessage;
	}
}
