package com.yuwang.pinju.web.module.shop.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.shop.ao.ShopOpenAO;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.util.ObjectUtil;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.module.shop.screen.ShopOpenBaseAction;

/**
 * 店铺基本展示action
 * @author xueqi
 *
 * @since 2011-7-4
 */
public class ShopShowInfoAction extends ShopOpenBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ShopInfoDO shopInfoDO;

	private ShopShowInfoAO shopShowInfoAO;
	
	private ShopOpenAO shopOpenAO;

	private File[] shopLogoFile;
	
	private String shopLogoFileFileName;
	
	private String logoStr;
	
	private String successMessage;
	
	/**********旗舰店联系人信息【2.0新增】****************/
	private ShopBusinessInfoDO shopBusinessInfoDO;

	/**********i小铺联系人信息【2.0新增】****************/
	private ShopIshopInfoDO shopIshopInfoDO;
	
	/**********普通店联系人信息【2.0新增】****************/
	private ShopCustomerInfoDO shopCustomerInfoDO;
	
	/**********是否入驻过B2C【2.0新增】****************/
	private List<String> isEnterB2cList;
	
	/**********年销售规模信息【2.0新增】****************/
	private List<String> saleScopeList;
	
	/**********店铺等级信息【2.0新增】****************/
	private List<String> shopLevelList;
	
	private List<String>  isOpenOuterShopList;
 	
	private String sellType;
	
	/**
	 * 是否有在其他网站开过店
	 */
	private int isHaveOuterShop=2;

	/**
	 * 店铺类型
	 */
	private List<String> sellTypeList;

	/**
	 * 店铺分类list
	 */
	@SuppressWarnings("unchecked")
	private Map shopCategoryList;

	/**
	 * 商品来源
	 */
	private List<String> goodsSourceList;

	private FileStorageManager fileStorageManager;

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	private String errorMessage;
	
	/**
	 * 子账户日志
	 */
	private MemberAsstLog memberAsstLog;

	/**
	 * 保存图片
	 * @param files
	 * @return
	 */
	private String[] saveFile(File[] files) {
		String fileNames[] = shopLogoFileFileName.split("@!@");
		
		String picNames[] = null;
		try {
			picNames = fileStorageManager.saveImage(files, fileNames, queryUserId(), queryNickName(),true);
		} catch (ManagerException e) {
		}
			
		return picNames;

	
	}
	/**
	 * 初始化返回页面的信息(外部店铺信息)
	 * @return
	 */
	protected void initOutShopInfoParam(){
		isOpenOuterShopList= ShopConstant.ISOPEN_OUTER_SHOP_LIST;
		shopLevelList = ShopConstant.SHOP_LEVEL_LIST;
		saleScopeList = ShopConstant.SALE_SCOPE_LIST;
		isEnterB2cList = ShopConstant.ISENTER_B2C_LIST;
	}
	
	/**
	 * 保存店铺基本信息页店铺基本信息
	 * @return
	 */
	@AssistantPermission(target = "shop", action = "set")
	public String execute() {
		//验证提交的信息
		errorMessage=validateSave();
		//保存图片
		String picNames[] = null;
		if(shopLogoFile != null && shopLogoFile.length>0){
			if (null!=errorMessage && !"".equals(errorMessage)) {
				shopInfoDO.setShopLogo(logoStr);
			}else{
				picNames = saveFile(shopLogoFile);
				if(picNames!=null && picNames.length>0){
					shopInfoDO.setShopLogo(picNames[0]);
				}
			}
		}else{
			shopInfoDO.setShopLogo(logoStr);
		}
		//校验错误后,初始化信息并且返回原页面
		if (null != errorMessage && !"".equals(errorMessage)) {
				goodsSourceList = ShopConstant.GOODS_SOURCE_LIST;

				if (shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_C))) {
					sellTypeList = ShopConstant.SHOP_NATURE_LIST_C;
					shopShowInfoAO.updateCustomerInfo(shopCustomerInfoDO);
				}else if(shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_IShop))){
					sellTypeList = ShopConstant.SHOP_NATURE_LIST_IShop;
					shopShowInfoAO.updateIshopInfo(shopIshopInfoDO);
				}else {
					sellTypeList = ShopConstant.SHOP_NATURE_LIST_B;
					shopShowInfoAO.updateBusinessInfo(shopBusinessInfoDO);
				}
				shopCategoryList = shopShowInfoAO.initShopCategoryList();
				initOutShopInfoParam();
			return "VALIDATEERROR";
		}else{
			long userId = queryUserId();
			shopInfoDO.setUserId(userId);
			if(isHaveOuterShop==1){
				shopInfoDO.setOuterShopAddressUrl("");
				shopInfoDO.setOuterShopLevel(null);
				shopInfoDO.setOuterShopSaleScope(null);
				shopInfoDO.setIsEnterB2c(null);
			}
			shopShowInfoAO.saveShopBaseInfo(shopInfoDO);
			if (shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_C))) {
				shopCustomerInfoDO.setUserId(userId);
				shopShowInfoAO.updateCustomerInfo(shopCustomerInfoDO);
			}else if(shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_IShop))){
				shopIshopInfoDO.setUserId(userId);
				shopShowInfoAO.updateIshopInfo(shopIshopInfoDO);
			}else {
				shopBusinessInfoDO.setUserId(userId);
				shopShowInfoAO.updateBusinessInfo(shopBusinessInfoDO);
			}
			successMessage = "操作成功!";
			//子账户日志
			memberAsstLog.log("修改店铺基本设置");
			return "success";
		}
		
	}
	
	/**
	 * 验证表单信息
	 * @return
	 */
	private String validateSave() {
		StringBuffer message = new StringBuffer();
		if (shopLogoFile != null && shopLogoFile.length > 0 && !FileSecurityUtils.isImageValid(shopLogoFile[0])) {
			message.append("店标文件格式不正确<br>");
		}
		if (shopLogoFile != null && shopLogoFile.length > 0 && shopLogoFile[0].length() / PinjuConstant.FILE_SIZE_K > 80) {
			message.append("店标文件过大,请上传不大于80K的文件<br>");
		}
		if (shopInfoDO.getGoodsSource() == null) {
			message.append("主要货源不能为空<br>");
		} else {
			if (shopInfoDO.getGoodsSource() != null && !ObjectUtil.isIntType(String.valueOf(shopInfoDO.getGoodsSource()))) {
				message.append("请选择主要货源<br>");
			}
		}
		if (shopInfoDO.getTitle() == null || "".equals(shopInfoDO.getTitle()) || shopInfoDO.getTitle().trim().length() == 0) {
			message.append("店铺简介不能为空<br>");
		} else {
			if (shopInfoDO.getTitle() != null && (shopInfoDO.getTitle().length() > 300)) {
				message.append("店铺简介不能超过300个字<br>");
			}
		}
		if (shopInfoDO.getDescription() == null || "".equals(shopInfoDO.getDescription()) || shopInfoDO.getDescription().trim().length() == 0) {
			message.append("店铺介绍不能为空<br>");
		} else {
			if (shopInfoDO.getDescription() != null && (shopInfoDO.getDescription().length() > 500)) {
				message.append("店铺介绍不能超过500个字<br>");
			}
		}
		if (isHaveOuterShop == 0) {
			if (shopInfoDO.getOuterShopAddressUrl() == null
					|| "".equals(shopInfoDO.getOuterShopAddressUrl())
					|| shopInfoDO.getOuterShopAddressUrl().trim().length() == 0) {
				message.append("店铺地址Url不能为空<br>");
			} else {
				if (shopInfoDO.getOuterShopAddressUrl() != null
						&& !ObjectUtil.isUrlPatten(String.valueOf(shopInfoDO.getOuterShopAddressUrl()))) {
					message.append("请输入正确的店铺地址Url<br>");
				}
			}
		}
		if (shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_C))) {
			if (null==shopCustomerInfoDO.getShopManagerTelephone() || "".equals(shopCustomerInfoDO.getShopManagerTelephone())) {
				message.append("店铺负责人电话不能为空<br>");
			}
			if (null==shopCustomerInfoDO.getShopManagerMobile() || "".equals(shopCustomerInfoDO.getShopManagerMobile())) {
				message.append("店铺负责人手机号码不能为空<br>");
			}
			if (null==shopCustomerInfoDO.getShopManagerName() || "".equals(shopCustomerInfoDO.getShopManagerName())) {
				message.append("店铺负责人姓名不能为空<br>");
			}
			if (null==shopCustomerInfoDO.getContactAddress() || "".equals(shopCustomerInfoDO.getContactAddress())) {
				message.append("店铺联系人地址不能为空<br>");
			}
			if (null==shopCustomerInfoDO.getShopManagerEmail()) {
				message.append("店铺负责人邮箱不能为空<br>");
			}else{
				if (null!=shopCustomerInfoDO.getShopManagerEmail() 
						&& (!ObjectUtil.isEmail(shopCustomerInfoDO.getShopManagerEmail()))) {
					message.append("请填写正确的店铺负责人邮箱<br>");
				}
			}
		}else if(shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_IShop))){
			if (null==shopIshopInfoDO.getShopManagerTelephone()) {
				message.append("店铺负责人电话不能为空<br>");
			}
			if (null==shopIshopInfoDO.getShopManagerMobile()) {
				message.append("店铺负责人手机号码不能为空<br>");
			}
			if (null==shopIshopInfoDO.getShopManagerName() || "".equals(shopIshopInfoDO.getShopManagerName())) {
				message.append("店铺负责人姓名不能为空<br>");
			}
			if (null==shopIshopInfoDO.getContactAddress() || "".equals(shopIshopInfoDO.getContactAddress())) {
				message.append("店铺联系人地址不能为空<br>");
			}
			if (null==shopIshopInfoDO.getShopManagerEmail()) {
				message.append("店铺负责人邮箱不能为空<br>");
			}else{
				if (null!=shopIshopInfoDO.getShopManagerEmail() 
						&& (!ObjectUtil.isEmail(shopIshopInfoDO.getShopManagerEmail()))) {
					message.append("请填写正确的店铺负责人邮箱<br>");
				}
			}
		}else {
			if (null==shopBusinessInfoDO.getShopManagerTelephone()) {
				message.append("店铺负责人电话不能为空<br>");
			}
			if (null==shopBusinessInfoDO.getShopManagerMobile()) {
				message.append("店铺负责人手机号码不能为空<br>");
			}
			if (null==shopBusinessInfoDO.getShopManagerName() || "".equals(shopBusinessInfoDO.getShopManagerName())) {
				message.append("店铺负责人姓名不能为空<br>");
			}
			if (null==shopBusinessInfoDO.getContactAddress() || "".equals(shopBusinessInfoDO.getContactAddress())) {
				message.append("店铺联系人地址不能为空<br>");
			}
			if (null==shopBusinessInfoDO.getShopManagerEmail()) {
				message.append("店铺负责人邮箱不能为空<br>");
			}else{
				if (null!=shopBusinessInfoDO.getShopManagerEmail() 
						&& (!ObjectUtil.isEmail(shopBusinessInfoDO.getShopManagerEmail()))) {
					message.append("请填写正确的店铺负责人邮箱<br>");
				}
			}
		}
		return message.toString();
	}
	
	@SuppressWarnings("unchecked")
	public Map getShopCategoryList() {
		return shopCategoryList;
	}

	@SuppressWarnings("unchecked")
	public void setShopCategoryList(Map shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}
	
	public ShopOpenAO getShopOpenAO() {
		return shopOpenAO;
	}

	public void setShopOpenAO(ShopOpenAO shopOpenAO) {
		this.shopOpenAO = shopOpenAO;
	}

	public List<String> getSellTypeList() {
		return sellTypeList;
	}

	public void setSellTypeList(List<String> sellTypeList) {
		this.sellTypeList = sellTypeList;
	}
	
	

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	public ShopInfoDO getShopInfoDO() {
		return shopInfoDO;
	}


	public int getIsHaveOuterShop() {
		return isHaveOuterShop;
	}
	public void setIsHaveOuterShop(int isHaveOuterShop) {
		this.isHaveOuterShop = isHaveOuterShop;
	}
	public void setShopInfoDO(ShopInfoDO shopInfoDO) {
		this.shopInfoDO = shopInfoDO;
	}

	public File[] getShopLogoFile() {
		return shopLogoFile;
	}

	public void setShopLogoFile(File[] shopLogoFile) {
		this.shopLogoFile = shopLogoFile;
	}

	public String getShopLogoFileFileName() {
		return shopLogoFileFileName;
	}

	public void setShopLogoFileFileName(String shopLogoFileFileName) {
		this.shopLogoFileFileName = shopLogoFileFileName;
	}

	public List<String> getIsOpenOuterShopList() {
		return isOpenOuterShopList;
	}
	public void setIsOpenOuterShopList(List<String> isOpenOuterShopList) {
		this.isOpenOuterShopList = isOpenOuterShopList;
	}
	public FileStorageManager getFileStorageManager() {
		return fileStorageManager;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}
	


	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}
	
	
	public List<String> getGoodsSourceList() {
		return goodsSourceList;
	}

	public void setGoodsSourceList(List<String> goodsSourceList) {
		this.goodsSourceList = goodsSourceList;
	}

	
	public ShopBusinessInfoDO getShopBusinessInfoDO() {
		return shopBusinessInfoDO;
	}
	public void setShopBusinessInfoDO(ShopBusinessInfoDO shopBusinessInfoDO) {
		this.shopBusinessInfoDO = shopBusinessInfoDO;
	}
	public ShopIshopInfoDO getShopIshopInfoDO() {
		return shopIshopInfoDO;
	}
	public void setShopIshopInfoDO(ShopIshopInfoDO shopIshopInfoDO) {
		this.shopIshopInfoDO = shopIshopInfoDO;
	}
	public ShopCustomerInfoDO getShopCustomerInfoDO() {
		return shopCustomerInfoDO;
	}
	public void setShopCustomerInfoDO(ShopCustomerInfoDO shopCustomerInfoDO) {
		this.shopCustomerInfoDO = shopCustomerInfoDO;
	}
	public List<String> getIsEnterB2cList() {
		return isEnterB2cList;
	}
	public void setIsEnterB2cList(List<String> isEnterB2cList) {
		this.isEnterB2cList = isEnterB2cList;
	}
	public List<String> getSaleScopeList() {
		return saleScopeList;
	}
	public void setSaleScopeList(List<String> saleScopeList) {
		this.saleScopeList = saleScopeList;
	}
	public List<String> getShopLevelList() {
		return shopLevelList;
	}
	public void setShopLevelList(List<String> shopLevelList) {
		this.shopLevelList = shopLevelList;
	}
	public String getSellType() {
		return sellType;
	}
	public void setSellType(String sellType) {
		this.sellType = sellType;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getLogoStr() {
		return logoStr;
	}

	public void setLogoStr(String logoStr) {
		this.logoStr = logoStr;
	}
	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
}
