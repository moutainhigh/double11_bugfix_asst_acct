package com.yuwang.pinju.web.module.shop.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.shop.ao.ShopOpenAO;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.util.ObjectUtil;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopFlowInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.interceptor.LoginInterceptor.MemberCheckerAware;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.shop.screen.ShopOpenBaseAction;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * 开店流程action
 * 
 * @author xueqi
 * 
 * @since 2011-7-4
 */
public class ShopSaveShopInfoAction extends ShopOpenBaseAction implements MemberCheckerAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private ShopOpenAO shopOpenAO;

	private ShopOpenFlowDO shopOpenFlowDO;

	private ShopCustomerInfoDO shopCustomerInfoDO;

	private ShopBusinessInfoDO shopBusinessInfoDO=null;
	
	private ShopIshopInfoDO shopIshopInfoDO;
	
	private ShopShowInfoAO shopShowInfoAO;
	
	private FileStorageManager fileStorageManager;
	
	private ShopOpenManager shopOpenManager;
	
	/**
	 * 店铺分类list
	 */
	private Map<Long, String> shopCategoryList;
	
	/**
	 * 店铺logo
	 */
	private File []shopLogo;
	
	/**
	 * 店铺logo名称
	 */
	private String shopLogoFileName;
	
	/**
	 * 企业营业执照
	 */
	private File []businessLicense;
	
	/**
	 * 企业营业执照名称
	 */
	private String businessLicenseFileName;
	
	/**
	 * 组织机构代码
	 */
	private File []organizationCode;
	
	/**
	 * 组织机构代码名称
	 */
	private String organizationCodeFileName;
	
	/**
	 * 税务登记证
	 */
	private File []taxPass;
	
	/**
	 * 税务登记证名称
	 */
	private String taxPassFileName;
	
	
	/**
	 * 品牌logo
	 */
	private File []brandLogo;
	
	/**
	 * 品牌logo名称
	 */
	private String brandLogoFileName;

	/**
	 * 品牌授权书或品牌商标注册证书
	 */
	private File []brandCertificate;
	
	/**
	 * 品牌授权书或品牌商标注册证书名称
	 */
	private String brandCertificateFileName;
	
	/**
	 * 商品质量检验证书
	 */
	private File []qualityCertificate;
	
	/**
	 * 商品质量检验证书名称
	 */
	private String qualityCertificateFileName;
	
	/**
	 * 身份证
	 */
	private File []idCard;
	
	/**
	 * 身份证名称
	 */
	private String idCardFileName;
	
	/**
	 * 作品1
	 */
	private File []creation1;
	
	/**
	 * 作品1名称
	 */
	private String creation1FileName;
	
	/**
	 * 作品2
	 */
	private File []creation2;
	
	/**
	 * 作品2名称
	 */
	private String creation2FileName;
	
	/**
	 * 错误提示
	 */
	private String errorMessage;
	
	/**
	 * 标记是第几个品牌
	 */
	private Integer brandSeq;
	
	/**
	 * 是否有在其他网站开过店
	 */
	private int isHaveOuterShop=2;
	
	/**
	 * 以何种方式保存品牌信息 0:普通保存 1:保存并新建 2:不保存但新建
	 */
	private Integer brandSaveType;
	
	/**
	 * 当前品牌
	 */
	private Integer currentBrand;
	
	private String[] saveFile(File[] files, String myFileFileName) {
		String fileNames[] = myFileFileName.split("@!@");
		String picNames[] = null;
		String fileNameString[] = new String[files.length];
		try {
			for(int i=0;i<files.length;i++){
				fileNameString[i] = FileSecurityUtils.getImageFileName(files[i], fileNames[i]);
			}

			picNames = fileStorageManager.saveImage(files, fileNameString, queryUserId(), queryNickName(), true);
		} catch (ManagerException e) {
		}
		return picNames;
	}
	
	/**
	 * 验证普通店店铺base信息
	 * @param shopCustomerInfoDO
	 * @return 错误信息
	 */
	private String validateCustomerBaseInfo(ShopCustomerInfoDO shopCustomerInfoDO){
		StringBuffer message = new StringBuffer();
		message.append(validateMember());
		if(shopCustomerInfoDO != null){
			if (shopCustomerInfoDO.getName() == null || "".equals(shopCustomerInfoDO.getName())
					|| shopCustomerInfoDO.getName().trim().length() == 0) {
				message.append("店铺名称不能为空<br>");
			} else {
				if (null != shopCustomerInfoDO.getName() && shopCustomerInfoDO.getName().length() > 20) {
					message.append("店铺名称不能超过20个字符<br>");
				} else if (shopOpenAO.queryShopInfosByName(shopCustomerInfoDO.getName(), shopCustomerInfoDO.getUserId())) {
					log.warn("后台程序验证店铺名是否重复: 店铺名:"+shopCustomerInfoDO.getName()+" 用户id:"+shopCustomerInfoDO.getUserId());
					message.append("店铺名已经有人使用，换一个吧！<br>");
				}
			}
			if (shopCustomerInfoDO.getShopCategory() == null) {
				message.append("店铺类目不能为空<br>");
			} else {
				if (shopCustomerInfoDO.getShopCategory() != null
						&& !ObjectUtil.isIntType(shopCustomerInfoDO.getShopCategory())) {
					message.append("请选择店铺类目<br>");
				}
			}
			if (shopCustomerInfoDO.getShopNature() == null) {
				message.append("卖家类型不能为空<br>");
			} else {
				if (shopCustomerInfoDO.getShopNature() != null
						&& !ObjectUtil.isIntType(String.valueOf(shopCustomerInfoDO.getShopNature()))) {
					message.append("请选择卖家类型<br>");
				}
			}
			if (shopCustomerInfoDO.getDescription() == null || "".equals(shopCustomerInfoDO.getDescription())
					|| shopCustomerInfoDO.getDescription().trim().length() == 0) {
				//message.append("店铺介绍不能为空<br>");
			} else {
				if (shopCustomerInfoDO.getDescription() != null && (shopCustomerInfoDO.getDescription().length() > 500)) {
					message.append("店铺介绍不能超过500个字<br>");
				}
			}
			if (shopLogo != null && shopLogo.length > 0) {
				if (shopLogo[0].length() / PinjuConstant.FILE_SIZE_K > 80) {
					message.append("店标" + "文件过大<br>");
					return message.toString();
				}
				if (!FileSecurityUtils.isImageValid(shopLogo[0])) {
					message.append("店标" + "格式不正确<br>");
					return message.toString();
				}

			}
			if (shopCustomerInfoDO.getProvince() == null) {
				message.append("企业所在省不能为空<br>");
			}
			if (shopCustomerInfoDO.getCity() == null) {
				message.append("企业所在城市不能为空<br>");
			}
			if(null==shopCustomerInfoDO.getEnterpriseName() || "".equals(shopCustomerInfoDO.getEnterpriseName()) || shopCustomerInfoDO.getEnterpriseName().trim().length()==0){
				message.append("企业名称不能为空!<br> ");
			}else{
				if (null!=shopCustomerInfoDO.getEnterpriseName() 
						&& shopCustomerInfoDO.getEnterpriseName().length()>30) {
					message.append("企业名称不能超过30个字!<br> ");
				}
			}
			if(null==shopCustomerInfoDO.getBusinessLicenseNumber() || "".equals(shopCustomerInfoDO.getBusinessLicenseNumber()) || shopCustomerInfoDO.getBusinessLicenseNumber().trim().length()==0){
				message.append("工商营业执照注册号不能为空! <br>");
			}else{
				if (null!=shopCustomerInfoDO.getBusinessLicenseNumber() 
						&& shopCustomerInfoDO.getBusinessLicenseNumber().length()>18) {
					message.append("工商营业执照注册号不能超过18位!<br> ");
				}else if(!ObjectUtil.isNum(shopCustomerInfoDO.getBusinessLicenseNumber())){
					message.append("工商营业执照注册只能为数字! <br>");
				}
			}
			if(null==shopCustomerInfoDO.getOrganizationCodeNumber() || "".equals(shopCustomerInfoDO.getOrganizationCodeNumber()) || shopCustomerInfoDO.getOrganizationCodeNumber().trim().length()==0){
				message.append("组织机构代码证号不能为空! <br>");
			}else{
				if (null!=shopCustomerInfoDO.getOrganizationCodeNumber() 
						&& shopCustomerInfoDO.getOrganizationCodeNumber().length()>10) {
					message.append("请输10位的数字、字母和\"-\"组成的代码证号!<br>");
				}else if(!ObjectUtil.isNumZ2(shopCustomerInfoDO.getOrganizationCodeNumber())){
					message.append("请输10位的数字、字母和\"-\"组成的代码证号!<br>");
				}
			}
			if(null==shopCustomerInfoDO.getBusiness() || "".equals(shopCustomerInfoDO.getBusiness()) || shopCustomerInfoDO.getBusiness().trim().length()==0){
				message.append("经营范围不能为空!<br> ");
			}else{
				if (null!=shopCustomerInfoDO.getBusiness() 
						&& shopCustomerInfoDO.getBusiness().length()>50) {
					message.append("经营范围不能超过50个字符!<br> ");
				}
			}
			if(null==shopCustomerInfoDO.getBusinessLicenseEndDate()){
				message.append("营业执照有效期限不能为空!<br> ");
			}else{
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = format1.format(shopCustomerInfoDO.getBusinessLicenseEndDate());
				if (null != dateString && !ObjectUtil.isDate(dateString)) {
					message.append("日期格式输入不正确!<br> ");
				} 
			}
			if(null==shopCustomerInfoDO.getLegalName() || "".equals(shopCustomerInfoDO.getLegalName()) || shopCustomerInfoDO.getLegalName().trim().length()==0){
				message.append("法人代表姓名不能为空!<br> ");
			}else{
				if (null!=shopCustomerInfoDO.getLegalName() 
						&& shopCustomerInfoDO.getLegalName().length()>20) {
					message.append("法人代表姓名不能超过20个字! <br>");
				}else if(!ObjectUtil.isZEng(shopCustomerInfoDO.getLegalName().trim())){
					message.append("法人代表姓名只能是汉字或英文字符 <br>");
				}
			}
			if(null==shopCustomerInfoDO.getRegistAddress() || "".equals(shopCustomerInfoDO.getRegistAddress()) || shopCustomerInfoDO.getRegistAddress().trim().length()==0){
				message.append("企业注册地址不能为空!<br> ");
			}else{
				if (null!=shopCustomerInfoDO.getRegistAddress() 
						&& shopCustomerInfoDO.getRegistAddress().length()>50) {
					message.append("企业注册地址不能超过50个字符!<br> ");
				}
			}
			if(isHaveOuterShop==0){
				if (shopCustomerInfoDO.getOuterShopAddressUrl()==null || "".equals(shopCustomerInfoDO.getOuterShopAddressUrl()) || shopCustomerInfoDO.getOuterShopAddressUrl().trim().length()==0) {
					message.append("店铺地址Url不能为空<br>");
				}else{
					if (shopCustomerInfoDO.getOuterShopAddressUrl() != null
							&& !ObjectUtil.isUrlPatten(String.valueOf(shopCustomerInfoDO
									.getOuterShopAddressUrl()))) {
						message.append("请输入正确的店铺地址Url<br>");
					}
				}
			}
			if (null==shopCustomerInfoDO.getShopManagerName() || "".equals(shopCustomerInfoDO.getShopManagerName()) || shopCustomerInfoDO.getShopManagerName().trim().length()==0) {
				message.append("店铺负责人姓名不能为空<br>");
			}else{
				if(null!=shopCustomerInfoDO.getShopManagerName() && (shopCustomerInfoDO.getShopManagerName().length()>20)){
					message.append("店铺负责人姓名不能超过20个字符<br>");
				}else if(!ObjectUtil.isZEng(shopCustomerInfoDO.getShopManagerName().trim())){
					message.append("店铺负责人姓名只能为中文、英文字符<br>");
				}
			}
			if (null==shopCustomerInfoDO.getShopManagerTelephone()) {
				message.append("店铺负责人电话不能为空<br>");
			}
			if (null==shopCustomerInfoDO.getShopManagerMobile()) {
				message.append("店铺负责人手机号码不能为空<br>");
			}
			if (null==shopCustomerInfoDO.getShopManagerEmail()) {
				message.append("店铺负责人邮箱不能为空<br>");
			}else{
				if (null!=shopCustomerInfoDO.getShopManagerEmail() 
						&& (!ObjectUtil.isEmail(shopCustomerInfoDO.getShopManagerEmail()))) {
					message.append("请填写正确的店铺负责人邮箱<br>");
				}
			}
		}
		
		return message.toString();
	}
	
	
	/**
	 * 验证品牌旗舰店铺base信息
	 * @param shopBusinessInfoDO
	 * @return 错误信息
	 */
	private String validateBusinessBaseInfo(ShopBusinessInfoDO shopBusinessInfoDO){
		StringBuffer message = new StringBuffer();
		message.append(validateMember());
		if(shopBusinessInfoDO != null){
			if (shopBusinessInfoDO.getName() == null || "".equals(shopBusinessInfoDO.getName())
					|| shopBusinessInfoDO.getName().trim().length() == 0) {
				message.append("店铺名称不能为空<br>");
			} else {
				if (null != shopBusinessInfoDO.getName() && shopBusinessInfoDO.getName().length() > 20) {
					message.append("店铺名称不能超过20个字符<br>");
				} else if (shopOpenAO.queryShopInfosByName(shopBusinessInfoDO.getName(), shopBusinessInfoDO.getUserId())) {
					log.warn("后台程序验证店铺名是否重复: 店铺名:"+shopBusinessInfoDO.getName()+" 用户id:"+shopBusinessInfoDO.getUserId());
					message.append("店铺名已经有人使用，换一个吧！<br>");
				}
			}
			if (shopBusinessInfoDO.getShopCategory() == null) {
				message.append("店铺类目不能为空<br>");
			} else {
				if (shopBusinessInfoDO.getShopCategory() != null
						&& !ObjectUtil.isIntType(shopBusinessInfoDO.getShopCategory())) {
					message.append("请选择店铺类目<br>");
				}
			}
			if (shopBusinessInfoDO.getShopNature() == null) {
				message.append("卖家类型不能为空<br>");
			} else {
				if (shopBusinessInfoDO.getShopNature() != null
						&& !ObjectUtil.isIntType(String.valueOf(shopBusinessInfoDO.getShopNature()))) {
					message.append("请选择卖家类型<br>");
				}
			}
			if (shopBusinessInfoDO.getDescription() == null || "".equals(shopBusinessInfoDO.getDescription())
					|| shopBusinessInfoDO.getDescription().trim().length() == 0) {
				//message.append("店铺介绍不能为空<br>");
			} else {
				if (shopBusinessInfoDO.getDescription() != null && (shopBusinessInfoDO.getDescription().length() > 500)) {
					message.append("店铺介绍不能超过500个字<br>");
				}
			}
			if (shopLogo != null && shopLogo.length > 0) {
				if (shopLogo[0].length() / PinjuConstant.FILE_SIZE_K > 80) {
					message.append("店标" + "文件过大<br>");
					return message.toString();
				}
				if (!FileSecurityUtils.isImageValid(shopLogo[0])) {
					message.append("店标" + "格式不正确<br>");
					return message.toString();
				}

			}
			if (shopBusinessInfoDO.getProvince() == null) {
				message.append("企业所在省不能为空<br>");
			}
			if (shopBusinessInfoDO.getCity() == null) {
				message.append("企业所在城市不能为空<br>");
			}
			if(null==shopBusinessInfoDO.getEnterpriseName() || "".equals(shopBusinessInfoDO.getEnterpriseName()) || shopBusinessInfoDO.getEnterpriseName().trim().length()==0){
				message.append("企业名称不能为空!<br> ");
			}else{
				if (null!=shopBusinessInfoDO.getEnterpriseName() 
						&& shopBusinessInfoDO.getEnterpriseName().length()>30) {
					message.append("企业名称不能超过30个字!<br> ");
				}
			}
			if(null==shopBusinessInfoDO.getBusinessLicenseNumber() || "".equals(shopBusinessInfoDO.getBusinessLicenseNumber()) || shopBusinessInfoDO.getBusinessLicenseNumber().trim().length()==0){
				message.append("工商营业执照注册号不能为空! <br>");
			}else{
				if (null!=shopBusinessInfoDO.getBusinessLicenseNumber() 
						&& shopBusinessInfoDO.getBusinessLicenseNumber().length()>18) {
					message.append("工商营业执照注册号不能超过18位!<br> ");
				}else if(!ObjectUtil.isNum(shopBusinessInfoDO.getBusinessLicenseNumber())){
					message.append("工商营业执照注册只能为数字! <br>");
				}
			}
			if(null==shopBusinessInfoDO.getOrganizationCodeNumber() || "".equals(shopBusinessInfoDO.getOrganizationCodeNumber()) || shopBusinessInfoDO.getOrganizationCodeNumber().trim().length()==0){
				message.append("组织机构代码证号不能为空! <br>");
			}else{
				if (null!=shopBusinessInfoDO.getOrganizationCodeNumber() 
						&& shopBusinessInfoDO.getOrganizationCodeNumber().length()>10) {
					message.append("请输10位的数字、字母和\"-\"组成的代码证号!<br>");
				}else if(!ObjectUtil.isNumZ2(shopBusinessInfoDO.getOrganizationCodeNumber())){
					message.append("请输10位的数字、字母和\"-\"组成的代码证号!<br>");
				}
			}
			if(null==shopBusinessInfoDO.getBusiness() || "".equals(shopBusinessInfoDO.getBusiness()) || shopBusinessInfoDO.getBusiness().trim().length()==0){
				message.append("经营范围不能为空!<br> ");
			}else{
				if (null!=shopBusinessInfoDO.getBusiness() 
						&& shopBusinessInfoDO.getBusiness().length()>50) {
					message.append("经营范围不能超过50个字符!<br> ");
				}
			}
			if(null==shopBusinessInfoDO.getBusinessLicenseEndDate()){
				message.append("营业执照有效期限不能为空!<br> ");
			}else{
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = format1.format(shopBusinessInfoDO.getBusinessLicenseEndDate());
				if (null != dateString && !ObjectUtil.isDate(dateString)) {
//				if (null != shopBusinessInfoDO.getBusinessLicenseEndDate() && !ObjectUtil.isDate(shopBusinessInfoDO.getBusinessLicenseEndDate().toString())) {
					message.append("日期格式输入不正确!<br> ");
				} 
			}
			if(null==shopBusinessInfoDO.getLegalName() || "".equals(shopBusinessInfoDO.getLegalName()) || shopBusinessInfoDO.getLegalName().trim().length()==0){
				message.append("法人代表姓名不能为空!<br> ");
			}else{
				if (null!=shopBusinessInfoDO.getLegalName() 
						&& shopBusinessInfoDO.getLegalName().length()>20) {
					message.append("法人代表姓名不能超过20个字! <br>");
				}else if(!ObjectUtil.isZEng(shopBusinessInfoDO.getLegalName().trim())){
					message.append("法人代表姓名只能是汉字或英文字符 <br>");
				}
			}
			if(null==shopBusinessInfoDO.getRegistAddress() || "".equals(shopBusinessInfoDO.getRegistAddress()) || shopBusinessInfoDO.getRegistAddress().trim().length()==0){
				message.append("企业注册地址不能为空!<br> ");
			}else{
				if (null!=shopBusinessInfoDO.getRegistAddress() 
						&& shopBusinessInfoDO.getRegistAddress().length()>50) {
					message.append("企业注册地址不能超过50个字符!<br> ");
				}
			}
			if(isHaveOuterShop==0){
				if (shopBusinessInfoDO.getOuterShopAddressUrl()==null || "".equals(shopBusinessInfoDO.getOuterShopAddressUrl()) || shopBusinessInfoDO.getOuterShopAddressUrl().trim().length()==0) {
					message.append("店铺地址Url不能为空<br>");
				}else{
					if (shopBusinessInfoDO.getOuterShopAddressUrl() != null
							&& !ObjectUtil.isUrlPatten(String.valueOf(shopBusinessInfoDO
									.getOuterShopAddressUrl()))) {
						message.append("请输入正确的店铺地址Url<br>");
					}
				}
			}
			if (null==shopBusinessInfoDO.getShopManagerName() || "".equals(shopBusinessInfoDO.getShopManagerName()) || shopBusinessInfoDO.getShopManagerName().trim().length()==0) {
				message.append("店铺负责人姓名不能为空<br>");
			}else{
				if(null!=shopBusinessInfoDO.getShopManagerName() && (shopBusinessInfoDO.getShopManagerName().length()>20)){
					message.append("店铺负责人姓名不能超过20个字符<br>");
				}else if(!ObjectUtil.isZEng(shopBusinessInfoDO.getShopManagerName().trim())){
					message.append("店铺负责人姓名只能为中文、英文字符<br>");
				}
			}
			if (null==shopBusinessInfoDO.getShopManagerTelephone()) {
				message.append("店铺负责人电话不能为空<br>");
			}
			if (null==shopBusinessInfoDO.getShopManagerMobile()) {
				message.append("店铺负责人手机号码不能为空<br>");
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
	
	/**
	 * 验证i小铺base信息
	 * @param shopIshopInfoDO
	 * @return 错误信息
	 */
	private String validateIShopBaseInfo(ShopIshopInfoDO shopIshopInfoDO){
		StringBuffer message = new StringBuffer();
		message.append(validateMember());
		if(shopIshopInfoDO != null){
			if (shopIshopInfoDO.getName() == null || "".equals(shopIshopInfoDO.getName())
					|| shopIshopInfoDO.getName().trim().length() == 0) {
				message.append("店铺名称不能为空<br>");
			} else {
				if (null != shopIshopInfoDO.getName() && shopIshopInfoDO.getName().length() > 20) {
					message.append("店铺名称不能超过20个字符<br>");
				} else if (shopOpenAO.queryShopInfosByName(shopIshopInfoDO.getName(), shopIshopInfoDO.getUserId())) {
					log.warn("后台程序验证店铺名是否重复: 店铺名:"+shopIshopInfoDO.getName()+" 用户id:"+shopIshopInfoDO.getUserId());
					message.append("店铺名已经有人使用，换一个吧！<br>");
				}
			}
			if (shopIshopInfoDO.getShopCategory() == null) {
				message.append("店铺类目不能为空<br>");
			} else {
				if (shopIshopInfoDO.getShopCategory() != null
						&& !ObjectUtil.isIntType(shopIshopInfoDO.getShopCategory())) {
					message.append("请选择店铺类目<br>");
				}
			}
			if (shopIshopInfoDO.getShopNature() == null) {
				message.append("卖家类型不能为空<br>");
			} else {
				if (shopIshopInfoDO.getShopNature() != null
						&& !ObjectUtil.isIntType(String.valueOf(shopIshopInfoDO.getShopNature()))) {
					message.append("请选择卖家类型<br>");
				}
			}
			if (shopIshopInfoDO.getDescription() == null || "".equals(shopIshopInfoDO.getDescription())
					|| shopIshopInfoDO.getDescription().trim().length() == 0) {
				//message.append("店铺介绍不能为空<br>");
			} else {
				if (shopIshopInfoDO.getDescription() != null && (shopIshopInfoDO.getDescription().length() > 500)) {
					message.append("店铺介绍不能超过500个字<br>");
				}
			}
			if (shopLogo != null && shopLogo.length > 0) {
				if (shopLogo[0].length() / PinjuConstant.FILE_SIZE_K > 80) {
					message.append("店标" + "文件过大<br>");
					return message.toString();
				}
				if (!FileSecurityUtils.isImageValid(shopLogo[0])) {
					message.append("店标" + "格式不正确<br>");
					return message.toString();
				}

			}
			if (null==shopIshopInfoDO.getShopManagerName() || "".equals(shopIshopInfoDO.getShopManagerName()) || shopIshopInfoDO.getShopManagerName().trim().length()==0) {
				message.append("店铺负责人姓名不能为空<br>");
			}else{
				if(null!=shopIshopInfoDO.getShopManagerName() && (shopIshopInfoDO.getShopManagerName().length()>20)){
					message.append("店铺负责人姓名不能超过20个字符<br>");
				}else if(!ObjectUtil.isZEng(shopIshopInfoDO.getShopManagerName().trim())){
					message.append("店铺负责人姓名只能为中文、英文字符<br>");
				}
			}
			if (null==shopIshopInfoDO.getShopManagerTelephone()) {
				message.append("店铺负责人电话不能为空<br>");
			}
			if (null==shopIshopInfoDO.getShopManagerMobile()) {
				message.append("店铺负责人手机号码不能为空<br>");
			}
			if (null==shopIshopInfoDO.getShopManagerEmail()) {
				message.append("店铺负责人邮箱不能为空<br>");
			}else{
				if (null!=shopIshopInfoDO.getShopManagerEmail() 
						&& (!ObjectUtil.isEmail(shopIshopInfoDO.getShopManagerEmail()))) {
					message.append("请填写正确的店铺负责人邮箱<br>");
				}
			}
			
		}
		return message.toString();
	}
	
	
	/**
	 * 验证普通店品牌旗舰店上传信息
	 * @return 错误信息
	 */
	private String validateCustomerAndBusiniessUpload(){
		StringBuffer message = new StringBuffer();
		message.append(validateMember());
		if(businessLicense == null){
			message.append("请上传企业营业执照<br>");
		}
		if(businessLicense[0].length()/PinjuConstant.FILE_SIZE_K > 500){
			message.append("企业营业执照"+ "文件过大<br>");
			return message.toString();
		}
		if (!FileSecurityUtils.isImageValid(businessLicense[0])) {
			message.append( "企业营业执照"+ "格式不正确<br>");
			return message.toString();
		}
		
		if(organizationCode == null){
			message.append("请上传组织机构代码证<br>");
		}
		if(organizationCode[0].length()/PinjuConstant.FILE_SIZE_K > 500){
			message.append("组织机构代码证"+ "文件过大<br>");
			return message.toString();
		}
		if (!FileSecurityUtils.isImageValid(organizationCode[0])) {
			message.append( "组织机构代码证"+ "格式不正确<br>");
			return message.toString();
		}
		
		if(taxPass == null){
			message.append("请上传税务登记证<br>");
		}
		if(taxPass[0].length()/PinjuConstant.FILE_SIZE_K > 500){
			message.append("税务登记证"+ "文件过大<br>");
			return message.toString();
		}
		if (!FileSecurityUtils.isImageValid(taxPass[0])) {
			message.append( "税务登记证"+ "格式不正确<br>");
			return message.toString();
		}
		return message.toString();
	}
	
	/**
	 * 验证i小铺上传信息
	 * @return 错误信息
	 */
	private String validateIShopUpload(){
		StringBuffer message = new StringBuffer();
		message.append(validateMember());
		if(idCard == null){
			message.append("请上传身份证复印件<br>");
			return message.toString();
		}
		if(idCard[0].length()/PinjuConstant.FILE_SIZE_K > 500){
			message.append("身份证复印件"+ "文件过大<br>");
			return message.toString();
		}
		if (!FileSecurityUtils.isImageValid(idCard[0])) {
			message.append( "身份证复印件"+ "格式不正确<br>");
			return message.toString();
		}
		
		if(creation1 != null && creation1[0].length()/PinjuConstant.FILE_SIZE_K > 500){
			message.append("个人作品"+ "文件过大<br>");
			return message.toString();
		}
		if (creation1 != null && !FileSecurityUtils.isImageValid(creation1[0])) {
			message.append( "个人作品"+ "格式不正确<br>");
			return message.toString();
		}
		
		if(creation2 != null && creation2[0].length()/PinjuConstant.FILE_SIZE_K > 500){
			message.append("个人作品"+ "文件过大<br>");
			return message.toString();
		}
		if (creation2 != null && !FileSecurityUtils.isImageValid(creation2[0])) {
			message.append( "个人作品"+ "格式不正确<br>");
			return message.toString();
		}
		return message.toString();
	}
	
	/**
	 * 验证品牌旗舰店品牌信息
	 * @param objectDO
	 * @return 错误信息
	 */
	private String validateBrandInfo(ShopBusinessInfoDO shopBusinessInfoDO){
		StringBuffer message = new StringBuffer();
		message.append(validateMember());
		if(shopBusinessInfoDO != null){
			String brandNames = shopBusinessInfoDO.getBrandName();
			String brandName[] = brandNames.split(";");
			//String fileNames[] = myFileFileName.split(",");
			String alertString[] = {"品牌logo图片","品牌授权书/品牌商标注册证书","商品质量检验证书"};
			for (int i = 0; i < brandLogo.length; i++) {
				if (brandLogo[i].length() / PinjuConstant.FILE_SIZE_K > 80) {
					message.append("品牌: \""+brandName[i] + "\" 的"+alertString[0]+"文件过大<br>");
					return message.toString();
				}
				if (!FileSecurityUtils.isImageValid(brandLogo[i])) {
					message.append("品牌: \""+brandName[i] + "\" 的+alertString[0]+文件格式不正确<br>");
					return message.toString();
				}
			}
			
			for (int i = 0; i < brandCertificate.length; i++) {
				if (brandCertificate[i].length() / PinjuConstant.FILE_SIZE_K > 500) {
					message.append("品牌: \""+brandName[i] + "\" 的"+alertString[1]+"文件过大<br>");
					return message.toString();
				}
				if (!FileSecurityUtils.isImageValid(brandCertificate[i])) {
					message.append("品牌: \""+brandName[i] + "\" 的+alertString[1]+文件格式不正确<br>");
					return message.toString();
				}
			}
			
			for (int i = 0; i < qualityCertificate.length; i++) {
				if (qualityCertificate[i].length() / PinjuConstant.FILE_SIZE_K > 500) {
					message.append("品牌: \""+brandName[i] + "\" 的"+alertString[2]+"文件过大<br>");
					return message.toString();
				}
				if (!FileSecurityUtils.isImageValid(qualityCertificate[i])) {
					message.append("品牌: \""+brandName[i] + "\" 的+alertString[2]+文件格式不正确<br>");
					return message.toString();
				}
			}
		}
		return message.toString();
	}
	
	/**
	 * 保存普通店基本信息
	 * @return statusError: iWillOpenShopAction.htm
	 * @return baseInfoError: shopOpenFillCustomerBaseInfo.ftl
	 * @return success: choiceFillIndexAction.htm
	 * @return not-allowed: notAllowError.ftl
	 */
	public String saveCustomerBaseInfo() {
		try {
			long userId = queryUserId();
			ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
			if (shopFlowInfoDO != null
					&& shopFlowInfoDO.getAuditStatus() != null
					&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END) 
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_WAIT))
					) {
				return "statusError";
			}
			//验证C的信息
			shopCustomerInfoDO.setUserId(userId);
			ShopCustomerInfoDO shopC = new ShopCustomerInfoDO();
			errorMessage=validateCustomerBaseInfo(shopCustomerInfoDO);
			//返回错误信息
			if (null!=errorMessage && !"".equals(errorMessage)) {
				//初始化数据
				initCustomerAndBusinessParam();
				return "baseInfoError";
			}
			//获取店铺logo并保存
//			String picNames[] = null;
//			if(shopLogo != null && shopLogo.length > 0){
//				picNames = saveFile(shopLogo, shopLogoFileName);
//				if(picNames != null && picNames.length > 0){
//					shopCustomerInfoDO.setShopLogo(picNames[0]);
//				}
//			}
			//更新普通店店铺信息
			shopC.setName(shopCustomerInfoDO.getName());
			shopC.setShopCategory(shopCustomerInfoDO.getShopCategory());
			shopC.setShopNature(shopCustomerInfoDO.getShopNature());
			shopC.setDescription(shopCustomerInfoDO.getDescription());
			shopC.setGoodsSource(shopCustomerInfoDO.getGoodsSource());
			shopC.setProvince(shopCustomerInfoDO.getProvince());
			shopC.setCity(shopCustomerInfoDO.getCity());
			shopC.setEnterpriseName(shopCustomerInfoDO.getEnterpriseName());
			shopC.setBusinessLicenseNumber(shopCustomerInfoDO.getBusinessLicenseNumber());
			shopC.setOrganizationCodeNumber(shopCustomerInfoDO.getOrganizationCodeNumber());
			shopC.setBusiness(shopCustomerInfoDO.getBusiness());
			shopC.setBusinessLicenseEndDate(shopCustomerInfoDO.getBusinessLicenseEndDate());
			shopC.setLegalName(shopCustomerInfoDO.getLegalName());
			shopC.setRegistAddress(shopCustomerInfoDO.getRegistAddress());
			shopC.setOuterShopAddressUrl(shopCustomerInfoDO.getOuterShopAddressUrl());
			shopC.setOuterShopLevel(shopCustomerInfoDO.getOuterShopLevel());
			shopC.setOuterShopSaleScope(shopCustomerInfoDO.getOuterShopSaleScope());
			shopC.setIsEnterB2c(shopCustomerInfoDO.getIsEnterB2c());
			shopC.setShopManagerName(shopCustomerInfoDO.getShopManagerName());
			shopC.setShopManagerTelephone(shopCustomerInfoDO.getShopManagerTelephone());
			shopC.setShopManagerMobile(shopCustomerInfoDO.getShopManagerMobile());
			shopC.setShopManagerEmail(shopCustomerInfoDO.getShopManagerEmail());
			shopC.setContactAddress(shopCustomerInfoDO.getContactAddress());
			shopC.setShopManagerFax(shopCustomerInfoDO.getShopManagerFax());
			shopC.setQq(shopCustomerInfoDO.getQq());
			shopC.setMsn(shopCustomerInfoDO.getMsn());
			shopC.setUserId(userId);
			shopOpenManager.updateShopCustomerInfo(shopC);
			
			//更新开店流程信息
			List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
			if(list != null && list.size() > 0 ){
				ShopOpenFlowDO shopOpenFlowDO = list.get(0);
				shopOpenFlowDO.setUserId(userId);
				shopOpenFlowDO.setGmtModified(new Date());
				Integer isFillInfo = shopOpenFlowDO.getIsFillInfo();
				if(isFillInfo == null){
					isFillInfo = ShopConstant.IS_FILL_SHOP_INFO_STEP1;
					shopOpenFlowDO.setIsFillInfo(isFillInfo);
				}else{
					if(isFillInfo.toString().indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP1)) == -1){
						shopOpenFlowDO.setIsFillInfo(Integer.parseInt(isFillInfo.toString()+ShopConstant.IS_FILL_SHOP_INFO_STEP1.toString()));
					}
				}
				shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
			}
			//删除cookie
			PinjuCookieManager.clearShop1();
		} catch (ManagerException e) {
			log.error(e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 保存普通店上传信息
	 * @return statusError: iWillOpenShopAction.htm
	 * @return baseInfoError: shopOpenFillCustomerBaseInfo.ftl
	 * @return success: choiceFillIndexAction.htm
	 * @return not-allowed: notAllowError.ftl
	 */
	public String saveCustomerUploadInfo() {
		try {
			long userId = queryUserId();
			ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
			if (shopFlowInfoDO != null
					&& shopFlowInfoDO.getAuditStatus() != null
					&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END) 
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_WAIT))
					) {
				return "statusError";
			}
			//验证C的信息
			errorMessage=validateCustomerAndBusiniessUpload();
			//返回错误信息
			if (null!=errorMessage && !"".equals(errorMessage)) {
				return "uploadError";
			}
			//获取店铺营业执照,组织机构代码证,税务登记证并保存
			String businessLicensePicNames[] = null;
			String organizationCodePicNames[] = null;
			String taxPassPicNames[] = null;
			ShopCustomerInfoDO shopCustomerInfoDO = new ShopCustomerInfoDO();
			if(businessLicense != null && businessLicense.length > 0){
				businessLicensePicNames = saveFile(businessLicense, businessLicenseFileName);
				if(businessLicensePicNames != null && businessLicensePicNames.length > 0){
					shopCustomerInfoDO.setBusinessLicense(businessLicensePicNames[0]);
				}
			}
			if(organizationCode != null && organizationCode.length > 0){
				organizationCodePicNames = saveFile(organizationCode, organizationCodeFileName);
				if(organizationCodePicNames != null && organizationCodePicNames.length > 0){
					shopCustomerInfoDO.setOrganizationCode(organizationCodePicNames[0]);
				}
			}
			if(taxPass != null && taxPass.length > 0){
				taxPassPicNames = saveFile(taxPass, taxPassFileName);
				if(taxPassPicNames != null && taxPassPicNames.length > 0){
					shopCustomerInfoDO.setTaxPass(taxPassPicNames[0]);
				}
			}
			
			//更新普通店店铺信息
			shopCustomerInfoDO.setUserId(userId);
			shopOpenManager.updateShopCustomerInfo(shopCustomerInfoDO);
			
			//更新开店流程信息
			List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
			if(list != null && list.size() > 0 ){
				ShopOpenFlowDO shopOpenFlowDO = list.get(0);
				shopOpenFlowDO.setUserId(userId);
				shopOpenFlowDO.setGmtModified(new Date());
				Integer isFillInfo = shopOpenFlowDO.getIsFillInfo();
				if(isFillInfo == null){
					isFillInfo = ShopConstant.IS_FILL_SHOP_INFO_STEP2;
					shopOpenFlowDO.setIsFillInfo(isFillInfo);
				}else{
					if(isFillInfo.toString().indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP2)) == -1){
						shopOpenFlowDO.setIsFillInfo(Integer.parseInt(isFillInfo.toString()+ShopConstant.IS_FILL_SHOP_INFO_STEP2.toString()));
					}
				}
				shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
			}
		} catch (ManagerException e) {
			log.error(e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 保存i小铺基本信息
	 * @return statusError: iWillOpenShopAction.htm
	 * @return baseInfoError: shopOpenFillCustomerBaseInfo.ftl
	 * @return success: choiceFillIndexAction.htm
	 * @return not-allowed: notAllowError.ftl
	 */
	public String saveIShopBaseInfo() {
		try {
			long userId = queryUserId();
			ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
			if (shopFlowInfoDO != null
					&& shopFlowInfoDO.getAuditStatus() != null
					&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END) 
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_WAIT))
					) {
				return "statusError";
			}
			//验证C的信息
			errorMessage=validateIShopBaseInfo(shopIshopInfoDO);
			ShopIshopInfoDO ishop = new ShopIshopInfoDO();
			//返回错误信息
			if (null!=errorMessage && !"".equals(errorMessage)) {
				initIShopParam();
				return "baseInfoError";
			}
			//获取店铺logo并保存
//			String picNames[] = null;
//			if(shopLogo != null && shopLogo.length > 0){
//				picNames = saveFile(shopLogo, shopLogoFileName);
//				if(picNames != null && picNames.length > 0){
//					shopIshopInfoDO.setShopLogo(picNames[0]);
//				}
//			}
			//更新普通店店铺信息
			ishop.setName(shopIshopInfoDO.getName());
			ishop.setShopCategory(shopIshopInfoDO.getShopCategory());
			ishop.setShopNature(shopIshopInfoDO.getShopNature());
			ishop.setDescription(shopIshopInfoDO.getDescription());
			ishop.setGoodsSource(shopIshopInfoDO.getGoodsSource());
			ishop.setProvince(shopIshopInfoDO.getProvince());
			ishop.setCity(shopIshopInfoDO.getCity());
			ishop.setOuterShopAddressUrl(shopIshopInfoDO.getOuterShopAddressUrl());
			ishop.setOuterShopLevel(shopIshopInfoDO.getOuterShopLevel());
			ishop.setOuterShopSaleScope(shopIshopInfoDO.getOuterShopSaleScope());
			ishop.setIsEnterB2c(shopIshopInfoDO.getIsEnterB2c());
			ishop.setShopManagerName(shopIshopInfoDO.getShopManagerName());
			ishop.setShopManagerTelephone(shopIshopInfoDO.getShopManagerTelephone());
			ishop.setShopManagerMobile(shopIshopInfoDO.getShopManagerMobile());
			ishop.setShopManagerEmail(shopIshopInfoDO.getShopManagerEmail());
			ishop.setContactAddress(shopIshopInfoDO.getContactAddress());
			ishop.setShopManagerFax(shopIshopInfoDO.getShopManagerFax());
			ishop.setQq(shopIshopInfoDO.getQq());
			ishop.setMsn(shopIshopInfoDO.getMsn());
			ishop.setUserId(userId);
			shopOpenManager.updateIShopInfo(ishop);
			
			//更新开店流程信息
			List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
			if(list != null && list.size() > 0 ){
				ShopOpenFlowDO shopOpenFlowDO = list.get(0);
				shopOpenFlowDO.setUserId(userId);
				shopOpenFlowDO.setGmtModified(new Date());
				Integer isFillInfo = shopOpenFlowDO.getIsFillInfo();
				if(isFillInfo == null){
					isFillInfo = ShopConstant.IS_FILL_SHOP_INFO_STEP1;
					shopOpenFlowDO.setIsFillInfo(isFillInfo);
				}else{
					if(isFillInfo.toString().indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP1)) == -1){
						shopOpenFlowDO.setIsFillInfo(Integer.parseInt(isFillInfo.toString()+ShopConstant.IS_FILL_SHOP_INFO_STEP1.toString()));
					}
				}
				shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
			}
			//删除cookie
			PinjuCookieManager.clearShop1();
		} catch (ManagerException e) {
			log.error(e.getMessage());
		}
		return "success";
	}
	
	
	/**
	 * 保存i小铺上传信息
	 * @return statusError: iWillOpenShopAction.htm
	 * @return baseInfoError: shopOpenFillCustomerBaseInfo.ftl
	 * @return success: choiceFillIndexAction.htm
	 * @return not-allowed: notAllowError.ftl
	 */
	public String saveIShopUploadInfo() {
		try {
			long userId = queryUserId();
			ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
			if (shopFlowInfoDO != null
					&& shopFlowInfoDO.getAuditStatus() != null
					&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END) 
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_WAIT))
					) {
				return "statusError";
			}
			//验证C的信息
			errorMessage=validateIShopUpload();
			//返回错误信息
			if (null!=errorMessage && !"".equals(errorMessage)) {
				return "uploadError";
			}
			//获取店铺营业执照,组织机构代码证,税务登记证并保存
			String idCardPicNames[] = null;
			String creation1PicNames[] = null;
			String creation2PicNames[] = null;
			ShopIshopInfoDO shopIshopInfoDO = new ShopIshopInfoDO();
			if(idCard != null && idCard.length > 0){
				idCardPicNames = saveFile(idCard, idCardFileName);
				if(idCardPicNames != null && idCardPicNames.length > 0){
					shopIshopInfoDO.setIdCard(idCardPicNames[0]);
				}
			}
			if(creation1 != null && creation1.length > 0){
				creation1PicNames = saveFile(creation1, creation1FileName);
				if(creation1PicNames != null && creation1PicNames.length > 0){
					shopIshopInfoDO.setCreation1(creation1PicNames[0]);
				}
			}
			if(creation2 != null && creation2.length > 0){
				creation2PicNames = saveFile(creation2, creation2FileName);
				if(creation2PicNames != null && creation2PicNames.length > 0){
					shopIshopInfoDO.setCreation2(creation2PicNames[0]);
				}
			}
			
			//更新i小铺信息
			shopIshopInfoDO.setUserId(userId);
			shopOpenManager.updateIShopInfo(shopIshopInfoDO);
			
			//更新开店流程信息
			List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
			if(list != null && list.size() > 0 ){
				ShopOpenFlowDO shopOpenFlowDO = list.get(0);
				shopOpenFlowDO.setUserId(userId);
				shopOpenFlowDO.setGmtModified(new Date());
				Integer isFillInfo = shopOpenFlowDO.getIsFillInfo();
				if(isFillInfo == null){
					isFillInfo = ShopConstant.IS_FILL_SHOP_INFO_STEP2;
					shopOpenFlowDO.setIsFillInfo(isFillInfo);
				}else{
					if(isFillInfo.toString().indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP2)) == -1){
						shopOpenFlowDO.setIsFillInfo(Integer.parseInt(isFillInfo.toString()+ShopConstant.IS_FILL_SHOP_INFO_STEP2.toString()));
					}
				}
				shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
			}
		} catch (ManagerException e) {
			log.error(e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 保存品牌旗舰店基本信息
	 * @return statusError: iWillOpenShopAction.htm
	 * @return baseInfoError: shopOpenFillCustomerBaseInfo.ftl
	 * @return success: choiceFillIndexAction.htm
	 * @return not-allowed: notAllowError.ftl
	 */
	public String saveBusinessBaseInfo() {
		try {
			long userId = queryUserId();
			ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
			if (shopFlowInfoDO != null
					&& shopFlowInfoDO.getAuditStatus() != null
					&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END) 
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_WAIT))
					) {
				return "statusError";
			}
			//验证C的信息
			errorMessage=validateBusinessBaseInfo(shopBusinessInfoDO);
			ShopBusinessInfoDO shopB = new ShopBusinessInfoDO();
			//返回错误信息
			if (null!=errorMessage && !"".equals(errorMessage)) {
				initCustomerAndBusinessParam();
				return "baseInfoError";
			}
//			String picNames[] = null;
//			if(shopLogo != null && shopLogo.length > 0){
//				picNames = saveFile(shopLogo, shopLogoFileName);
//				if(picNames != null && picNames.length > 0){
//					shopB.setShopLogo(picNames[0]);
//				}
//			}
			
			shopB.setName(shopBusinessInfoDO.getName());
			shopB.setShopCategory(shopBusinessInfoDO.getShopCategory());
			shopB.setShopNature(shopBusinessInfoDO.getShopNature());
			shopB.setDescription(shopBusinessInfoDO.getDescription());
			shopB.setGoodsSource(shopBusinessInfoDO.getGoodsSource());
			shopB.setProvince(shopBusinessInfoDO.getProvince());
			shopB.setCity(shopBusinessInfoDO.getCity());
			shopB.setEnterpriseName(shopBusinessInfoDO.getEnterpriseName());
			shopB.setBusinessLicenseNumber(shopBusinessInfoDO.getBusinessLicenseNumber());
			shopB.setOrganizationCodeNumber(shopBusinessInfoDO.getOrganizationCodeNumber());
			shopB.setBusiness(shopBusinessInfoDO.getBusiness());
			shopB.setBusinessLicenseEndDate(shopBusinessInfoDO.getBusinessLicenseEndDate());
			shopB.setLegalName(shopBusinessInfoDO.getLegalName());
			shopB.setRegistAddress(shopBusinessInfoDO.getRegistAddress());
			shopB.setOuterShopAddressUrl(shopBusinessInfoDO.getOuterShopAddressUrl());
			shopB.setOuterShopLevel(shopBusinessInfoDO.getOuterShopLevel());
			shopB.setOuterShopSaleScope(shopBusinessInfoDO.getOuterShopSaleScope());
			shopB.setIsEnterB2c(shopBusinessInfoDO.getIsEnterB2c());
			shopB.setShopManagerName(shopBusinessInfoDO.getShopManagerName());
			shopB.setShopManagerTelephone(shopBusinessInfoDO.getShopManagerTelephone());
			shopB.setShopManagerMobile(shopBusinessInfoDO.getShopManagerMobile());
			shopB.setShopManagerEmail(shopBusinessInfoDO.getShopManagerEmail());
			shopB.setContactAddress(shopBusinessInfoDO.getContactAddress());
			shopB.setShopManagerFax(shopBusinessInfoDO.getShopManagerFax());
			shopB.setQq(shopBusinessInfoDO.getQq());
			shopB.setMsn(shopBusinessInfoDO.getMsn());
			shopB.setUserId(userId);
			//更新普通店店铺信息
			shopOpenManager.updateShopBusinessInfo(shopB);
			
			//更新开店流程信息
			List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
			if(list != null && list.size() > 0 ){
				ShopOpenFlowDO shopOpenFlowDO = list.get(0);
				shopOpenFlowDO.setUserId(userId);
				shopOpenFlowDO.setGmtModified(new Date());
				Integer isFillInfo = shopOpenFlowDO.getIsFillInfo();
				if(isFillInfo == null){
					isFillInfo = ShopConstant.IS_FILL_SHOP_INFO_STEP1;
					shopOpenFlowDO.setIsFillInfo(isFillInfo);
				}else{
					if(isFillInfo.toString().indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP1)) == -1){
						shopOpenFlowDO.setIsFillInfo(Integer.parseInt(isFillInfo.toString()+ShopConstant.IS_FILL_SHOP_INFO_STEP1.toString()));
					}
				}
				shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
			}
			//删除cookie
			PinjuCookieManager.clearShop1();
		} catch (ManagerException e) {
			log.error(e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 保存品牌旗舰店上传信息
	 * @return statusError: iWillOpenShopAction.htm
	 * @return baseInfoError: shopOpenFillCustomerBaseInfo.ftl
	 * @return success: choiceFillIndexAction.htm
	 * @return not-allowed: notAllowError.ftl
	 */
	public String saveBusinessUploadInfo() {
		try {
			long userId = queryUserId();
			ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
			if (shopFlowInfoDO != null
					&& shopFlowInfoDO.getAuditStatus() != null
					&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END) 
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
							|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_WAIT))
					) {
				return "statusError";
			}
			//验证C的信息
			errorMessage=validateCustomerAndBusiniessUpload();
			//返回错误信息
			if (null!=errorMessage && !"".equals(errorMessage)) {
				return "uploadError";
			}
			//获取店铺营业执照,组织机构代码证,税务登记证并保存
			String businessLicensePicNames[] = null;
			String organizationCodePicNames[] = null;
			String taxPassPicNames[] = null;
			ShopBusinessInfoDO shopBusinessInfoDO = new ShopBusinessInfoDO();
			if(businessLicense != null && businessLicense.length > 0){
				businessLicensePicNames = saveFile(businessLicense, businessLicenseFileName);
				if(businessLicensePicNames != null && businessLicensePicNames.length > 0){
					shopBusinessInfoDO.setBusinessLicense(businessLicensePicNames[0]);
				}
			}
			if(organizationCode != null && organizationCode.length > 0){
				organizationCodePicNames = saveFile(organizationCode, organizationCodeFileName);
				if(organizationCodePicNames != null && organizationCodePicNames.length > 0){
					shopBusinessInfoDO.setOrganizationCode(organizationCodePicNames[0]);
				}
			}
			if(taxPass != null && taxPass.length > 0){
				taxPassPicNames = saveFile(taxPass, taxPassFileName);
				if(taxPassPicNames != null && taxPassPicNames.length > 0){
					shopBusinessInfoDO.setTaxPass(taxPassPicNames[0]);
				}
			}
			
			//更新普通店店铺信息
			shopBusinessInfoDO.setUserId(userId);
			shopOpenManager.updateShopBusinessInfo(shopBusinessInfoDO);
			
			//更新开店流程信息
			List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
			if(list != null && list.size() > 0 ){
				ShopOpenFlowDO shopOpenFlowDO = list.get(0);
				shopOpenFlowDO.setUserId(userId);
				shopOpenFlowDO.setGmtModified(new Date());
				Integer isFillInfo = shopOpenFlowDO.getIsFillInfo();
				if(isFillInfo == null){
					isFillInfo = ShopConstant.IS_FILL_SHOP_INFO_STEP2;
					shopOpenFlowDO.setIsFillInfo(isFillInfo);
				}else{
					if(isFillInfo.toString().indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP2)) == -1){
						shopOpenFlowDO.setIsFillInfo(Integer.parseInt(isFillInfo.toString()+ShopConstant.IS_FILL_SHOP_INFO_STEP2.toString()));
					}
				}
				shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
			}
		} catch (ManagerException e) {
			log.error(e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 保存品牌旗舰店品牌信息
	 * @return statusError: iWillOpenShopAction.htm
	 * @return baseInfoError: shopOpenFillCustomerBaseInfo.ftl
	 * @return success: queryShopInfoAction.htm
	 * @return not-allowed: notAllowError.ftl
	 */
	public String saveBusinessBrandInfo() {
		long userId = queryUserId();
		ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
		if (shopFlowInfoDO != null
				&& shopFlowInfoDO.getAuditStatus() != null
				&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END) 
						|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
						|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_WAIT))
				) {
			return "statusError";
		}
		List<ShopBusinessInfoDO> shopInfoList = shopOpenAO.queryShopBusinessInfo(userId);
		ShopBusinessInfoDO shopBInfo = null;
		if(shopInfoList != null && shopInfoList.size() > 0){
			shopBInfo = shopInfoList.get(0);
		}
		ShopBusinessInfoDO shopB = new ShopBusinessInfoDO();
		if(brandSaveType == 0 || brandSaveType == 1){
			try {
				//验证C的信息
				errorMessage=validateBrandInfo(shopBusinessInfoDO);
				//返回错误信息
				if (null!=errorMessage && !"".equals(errorMessage)) {
					return "brandInfoError";
				}
				//获取店铺营业执照,组织机构代码证,税务登记证并保存
				String brandLogoPicNames[] = null;
				String brandCertificatePicNames[] = null;
				String qualityCertificatePicNames[] = null;
				
				if (shopBInfo != null) {
					if (brandLogo != null && brandLogo.length > 0) {
						brandLogoPicNames = saveFile(brandLogo, brandLogoFileName);
						if (brandLogoPicNames != null && brandLogoPicNames.length > 0) {
							// StringBuffer brandLogoPicNameBuffer = new
							// StringBuffer();
							// for(String brandLogoPicName : brandLogoPicNames){
							// brandLogoPicNameBuffer.append(brandLogoPicName).append(",");
							// }
							// String brandLogoPicNameStr =
							// brandLogoPicNameBuffer.substring(0,
							// brandLogoPicNameBuffer.length() - 1);
							String brandLogoPicNameStr = brandLogoPicNames[0];
							String brandLogoDBStr = shopBInfo.getBrandLogo();
							if (brandLogoDBStr != null && brandLogoDBStr.length() > 0) {
								String[] brandLogoArr = brandLogoDBStr.split("@!@");
								ArrayList<String> arrayList = new ArrayList<String>(brandLogoArr.length + 1);
								for (String str : brandLogoArr) {
									arrayList.add(str);
								}
								arrayList.add(currentBrand, brandLogoPicNameStr);
								if(arrayList.size() > currentBrand + 1){
									arrayList.remove(currentBrand + 1);
								}
								
								brandLogoPicNameStr = "";
								for (String str : arrayList) {
									brandLogoPicNameStr += str + "@!@";
								}
//								brandLogoPicNameStr = brandLogoPicNameStr.substring(0, brandLogoPicNameStr.length() - 1);
							}
							shopB.setBrandLogo(brandLogoPicNameStr);
						}
					}
					if (brandCertificate != null && brandCertificate.length > 0) {
						brandCertificatePicNames = saveFile(brandCertificate, brandCertificateFileName);
						if (brandCertificatePicNames != null && brandCertificatePicNames.length > 0) {
							// StringBuffer brandCertificatePicNameBuffer = new
							// StringBuffer();
							// for(String brandCertificate :
							// brandCertificatePicNames){
							// brandCertificatePicNameBuffer.append(brandCertificate).append(",");
							// }
							// String brandCertificatePicNameStr =
							// brandCertificatePicNameBuffer.substring(0,
							// brandCertificatePicNameBuffer.length() - 1);
							// shopBusinessInfoDO.setBrandCertificate(brandCertificatePicNameStr);
							String brandCertificatePicNameStr = brandCertificatePicNames[0];
							String brandCertificateDBStr = shopBInfo.getBrandCertificate();
							if (brandCertificateDBStr != null && brandCertificateDBStr.length() > 0) {
								String[] brandCertificateArr = brandCertificateDBStr.split("@!@");
								ArrayList<String> arrayList = new ArrayList<String>(brandCertificateArr.length + 1);
								for (String str : brandCertificateArr) {
									arrayList.add(str);
								}
								arrayList.add(currentBrand, brandCertificatePicNameStr);
								if(arrayList.size() > currentBrand + 1){
									arrayList.remove(currentBrand + 1);
								}
								brandCertificatePicNameStr = "";
								for (String str : arrayList) {
									brandCertificatePicNameStr += str + "@!@";
								}
//								brandCertificatePicNameStr = brandCertificatePicNameStr.substring(0,
//										brandCertificatePicNameStr.length() - 1);
							}
							shopB.setBrandCertificate(brandCertificatePicNameStr);
						}
					}
					if (qualityCertificate != null && qualityCertificate.length > 0) {
						qualityCertificatePicNames = saveFile(qualityCertificate, qualityCertificateFileName);
						if (qualityCertificatePicNames != null && qualityCertificatePicNames.length > 0) {
							// StringBuffer qualityCertificatePicNameBuffer = new
							// StringBuffer();
							// for(String qualityCertificate :
							// qualityCertificatePicNames){
							// qualityCertificatePicNameBuffer.append(qualityCertificate).append(",");
							// }
							// String qualityCertificatePicNameStr =
							// qualityCertificatePicNameBuffer.substring(0,
							// qualityCertificatePicNameBuffer.length() - 1);
							// shopBusinessInfoDO.setQualityCertificate(qualityCertificatePicNameStr);
							String qualityCertificatePicNameStr = qualityCertificatePicNames[0];
							String qualityCertificateDBStr = shopBInfo.getQualityCertificate();
							if (qualityCertificateDBStr != null && qualityCertificateDBStr.length() > 0) {
								String[] qualityCertificateArr = qualityCertificateDBStr.split("@!@");
								ArrayList<String> arrayList = new ArrayList<String>(qualityCertificateArr.length + 1);
								for (String str : qualityCertificateArr) {
									arrayList.add(str);
								}
								arrayList.add(currentBrand, qualityCertificatePicNameStr);
								if(arrayList.size() > currentBrand + 1){
									arrayList.remove(currentBrand + 1);
								}
								qualityCertificatePicNameStr = "";
								for (String str : arrayList) {
									qualityCertificatePicNameStr += str + "@!@";
								}
//								qualityCertificatePicNameStr = qualityCertificatePicNameStr.substring(0,
//										qualityCertificatePicNameStr.length() - 1);
							}
							shopB.setQualityCertificate(qualityCertificatePicNameStr);
						}
					}
					if (shopBusinessInfoDO.getBrandName() != null && shopBusinessInfoDO.getBrandName().length() > 0) {
						ArrayList<String> arrayList = null;
						if(shopBInfo.getBrandName() != null && shopBInfo.getBrandName().length() > 0){
							String nameString = shopBInfo.getBrandName();
							String []brandNameArr = nameString.split("@!@");
							arrayList = new ArrayList<String>(brandNameArr.length + 1);
							for (String str : brandNameArr) {
								arrayList.add(str);
							}
							arrayList.add(currentBrand, shopBusinessInfoDO.getBrandName());
							if(arrayList.size() > currentBrand + 1){
								arrayList.remove(currentBrand + 1);
							}
						}else{
							arrayList = new ArrayList<String>(1);
							arrayList.add(currentBrand, shopBusinessInfoDO.getBrandName());
						}
						
						
						String brandNameStr = "";
						for (String str : arrayList) {
							brandNameStr += str + "@!@";
						}
//						brandNameStr = brandNameStr.substring(0, brandNameStr.length() - 1);
						shopB.setBrandName(brandNameStr);
					}
					
					String[] brandEnglishNameArr = null;
					ArrayList<String> list = null;
					if(shopBInfo.getBrandEnglishName() != null && shopBInfo.getBrandEnglishName().length() > 0){
						brandEnglishNameArr = shopBInfo.getBrandEnglishName().split("@!@");
						list = new ArrayList<String>(brandEnglishNameArr.length + 1);
						for (String str : brandEnglishNameArr) {
							list.add(str);
						}
					}else{
						list = new ArrayList<String>(1);
					}
					
					if (shopBusinessInfoDO.getBrandEnglishName() != null
							&& shopBusinessInfoDO.getBrandEnglishName().length() > 0) {
						list.add(currentBrand, shopBusinessInfoDO.getBrandEnglishName());
					} else {
						list.add(currentBrand, " ");
					}
					if(list.size() > 1 && list.size() > currentBrand + 1){
						list.remove(currentBrand + 1);
					}

					String brandEnglishNameStr = "";
					for (String str : list) {
						brandEnglishNameStr += str + "@!@";
					}
					//brandEnglishNameStr = brandEnglishNameStr.substring(0, brandEnglishNameStr.length() - 1);
					shopB.setBrandEnglishName(brandEnglishNameStr);
					
					if (shopBusinessInfoDO.getBrandStory() != null && shopBusinessInfoDO.getBrandStory().length() > 0) {
						
						String[] brandStoryArr = null;
						ArrayList<String> arrayList = null;
						if(shopBInfo.getBrandStory() != null && shopBInfo.getBrandStory().length() > 0){
							brandStoryArr = shopBInfo.getBrandStory().split("@!@");
							arrayList = new ArrayList<String>(brandStoryArr.length + 1);
							for (String str : brandStoryArr) {
								arrayList.add(str);
							}
							arrayList.add(currentBrand, shopBusinessInfoDO.getBrandStory());
							if(arrayList.size() > currentBrand + 1){
								arrayList.remove(currentBrand + 1);
							}
						}else{
							arrayList = new ArrayList<String>(1);
							arrayList.add(currentBrand, shopBusinessInfoDO.getBrandStory());
						}
						String brandStoryStr = "";
						for (String str : arrayList) {
							brandStoryStr += str + "@!@";
						}
//						brandStoryStr = brandStoryStr.substring(0, brandStoryStr.length() - 1);
						shopB.setBrandStory(brandStoryStr);
					}
					
					
					if (shopBusinessInfoDO.getTrademarkNumber() != null && shopBusinessInfoDO.getTrademarkNumber().length() > 0) {
						
						String[] trademarkNumberArr = null;
						ArrayList<String> arrayList = null;
						if(shopBInfo.getTrademarkNumber() != null && shopBInfo.getTrademarkNumber().length() > 0){
							trademarkNumberArr = shopBInfo.getTrademarkNumber().split("@!@");
							arrayList = new ArrayList<String>(trademarkNumberArr.length + 1);
							for (String str : trademarkNumberArr) {
								arrayList.add(str);
							}
							arrayList.add(currentBrand, shopBusinessInfoDO.getTrademarkNumber());
							if(arrayList.size() > currentBrand + 1){
								arrayList.remove(currentBrand + 1);
							}
						}else{
							arrayList = new ArrayList<String>(1);
							arrayList.add(currentBrand, shopBusinessInfoDO.getTrademarkNumber());
						}
						
						
						String trademarkNumberStr = "";
						for (String str : arrayList) {
							trademarkNumberStr += str + "@!@";
						}
//						trademarkNumberStr = trademarkNumberStr.substring(0, trademarkNumberStr.length() - 1);
						shopB.setTrademarkNumber(trademarkNumberStr);
					}
				}
				
				
				//更新普通店店铺信息
				shopB.setUserId(userId);
				shopOpenManager.updateShopBusinessInfo(shopB);
				
				//更新开店流程信息
				List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
				if(list != null && list.size() > 0 ){
					ShopOpenFlowDO shopOpenFlowDO = list.get(0);
					shopOpenFlowDO.setUserId(userId);
					shopOpenFlowDO.setGmtModified(new Date());
					Integer isFillInfo = shopOpenFlowDO.getIsFillInfo();
					if(isFillInfo == null){
						isFillInfo = ShopConstant.IS_FILL_SHOP_INFO_STEP3;
						shopOpenFlowDO.setIsFillInfo(isFillInfo);
					}else{
						if(isFillInfo.toString().indexOf(String.valueOf(ShopConstant.IS_FILL_SHOP_INFO_STEP3)) == -1){
							shopOpenFlowDO.setIsFillInfo(Integer.parseInt(isFillInfo.toString()+ShopConstant.IS_FILL_SHOP_INFO_STEP3.toString()));
						}
					}
					shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
				}
				 
				//删除cookie
				PinjuCookieManager.clearShop2();
			} catch (ManagerException e) {
				log.error(e.getMessage());
			}
		}
		String returnString = "";
		int newBrandSeq = 0;
		if(shopBInfo != null){
			String brandNameString = shopBInfo.getBrandName();
			if(brandNameString != null && brandNameString.length() > 0){
				newBrandSeq = brandNameString.split("@!@").length;
			}
		}
		if(brandSaveType == 2){
			returnString = "notSaveAndNew";
			shopBusinessInfoDO = new ShopBusinessInfoDO();
			if(currentBrand + 1 <= newBrandSeq){
				brandSeq = currentBrand + 1;
				currentBrand =  currentBrand + 1;
			}
			
		}else if(brandSaveType == 1){
			returnString = "saveAndNew";
			shopBusinessInfoDO = new ShopBusinessInfoDO();
			if(currentBrand <= newBrandSeq){
				brandSeq = currentBrand + 1;
				currentBrand =  currentBrand + 1;
			}
		}else if(brandSaveType == 0){
			returnString = "success";
		}
		return returnString;
	}
	
	/**
	 * 删除品牌信息
	 * @return statusError: iWillOpenShopAction.htm
	 * @return success: queryShopInfoAction.htm
	 * @return not-allowed: notAllowError.ftl
	 */
	public String deleteBrandInfo(){
		try {
		Long userId = queryUserId();
		List<ShopBusinessInfoDO> shopInfoList = shopOpenAO.queryShopBusinessInfo(userId);
		ShopBusinessInfoDO shopBInfoDO = null;
		ShopBusinessInfoDO shopBusinessInfoDOSave = new ShopBusinessInfoDO();
			if (shopInfoList != null && shopInfoList.size() > 0) {
				shopBInfoDO = shopInfoList.get(0);
				if (currentBrand != null) {
					// 品牌名
					String brandName = shopBInfoDO.getBrandName();
					String[] brandNameArr = brandName.split("@!@");
					String brandNameSave = "";
					for (int i = 0; i < brandNameArr.length; i++) {
						if (i == currentBrand.intValue()) {
							continue;
						}
						brandNameSave += brandNameArr[i] + "@!@";
					}
					if(brandNameSave.length() > 0){
//						brandNameSave = brandNameSave.substring(0, brandNameSave.length() - 1);
					}
					shopBusinessInfoDOSave.setBrandName(brandNameSave);

					// 品牌英文名
					String brandEnglishName = shopBInfoDO.getBrandEnglishName();
					String[] brandEnglishNameArr = brandEnglishName.split("@!@");
					String brandEnglishNameSave = "";
					for (int i = 0; i < brandEnglishNameArr.length; i++) {
						if (i == currentBrand.intValue()) {
							continue;
						}
						brandEnglishNameSave += brandEnglishNameArr[i] + "@!@";
					}
					if(brandEnglishNameSave.length() > 0){
//						brandEnglishNameSave = brandEnglishNameSave.substring(0, brandEnglishNameSave.length() - 1);
					}
					shopBusinessInfoDOSave.setBrandEnglishName(brandEnglishNameSave);

					// 品牌故事
					String brandStory = shopBInfoDO.getBrandStory();
					String[] brandStoryArr = brandStory.split("@!@");
					String brandStorySave = "";
					for (int i = 0; i < brandStoryArr.length; i++) {
						if (i == currentBrand.intValue()) {
							continue;
						}
						brandStorySave += brandStoryArr[i] + "@!@";
					}
					if(brandStorySave.length() > 0){
//						brandStorySave = brandStorySave.substring(0, brandStorySave.length() - 1);
					}
					
					shopBusinessInfoDOSave.setBrandStory(brandStorySave);

					// 品牌logo
					String brandLogo = shopBInfoDO.getBrandLogo();
					String[] brandLogoArr = brandLogo.split("@!@");
					String brandLogoSave = "";
					for (int i = 0; i < brandLogoArr.length; i++) {
						if (i == currentBrand.intValue()) {
							continue;
						}
						brandLogoSave += brandLogoArr[i] + "@!@";
					}
					if(brandLogoSave.length() > 0){
//						brandLogoSave = brandLogoSave.substring(0, brandLogoSave.length() - 1);
					}
					shopBusinessInfoDOSave.setBrandLogo(brandLogoSave);

					// 品牌授权书
					String brandCertificate = shopBInfoDO.getBrandCertificate();
					String[] brandCertificateArr = brandCertificate.split("@!@");
					String brandCertificateSave = "";
					for (int i = 0; i < brandCertificateArr.length; i++) {
						if (i == currentBrand.intValue()) {
							continue;
						}
						brandCertificateSave += brandCertificateArr[i] + "@!@";
					}
					if(brandCertificateSave.length() > 0){
//						brandCertificateSave = brandCertificateSave.substring(0, brandCertificateSave.length() - 1);
					}
					shopBusinessInfoDOSave.setBrandCertificate(brandCertificateSave);

					// 质量检验证书
					String qualityCertificate = shopBInfoDO.getQualityCertificate();
					String[] qualityCertificateArr = qualityCertificate.split("@!@");
					String qualityCertificateSave = "";
					for (int i = 0; i < qualityCertificateArr.length; i++) {
						if (i == currentBrand.intValue()) {
							continue;
						}
						qualityCertificateSave += qualityCertificateArr[i] + "@!@";
					}
					if(qualityCertificateSave.length() > 0){
//						qualityCertificateSave = qualityCertificateSave.substring(0, qualityCertificateSave.length() - 1);
					}
					shopBusinessInfoDOSave.setQualityCertificate(qualityCertificateSave);

					// 商标注册证号
					String trademarkNumber = shopBInfoDO.getTrademarkNumber();
					String[] trademarkNumberArr = trademarkNumber.split("@!@");
					String trademarkNumberSave = "";
					for (int i = 0; i < trademarkNumberArr.length; i++) {
						if (i == currentBrand.intValue()) {
							continue;
						}
						trademarkNumberSave += trademarkNumberArr[i] + "@!@";
					}
					if(trademarkNumberSave.length() > 0){
//						trademarkNumberSave = trademarkNumberSave.substring(0, trademarkNumberSave.length() - 1);
					}
					shopBusinessInfoDOSave.setTrademarkNumber(trademarkNumberSave);

					shopBusinessInfoDOSave.setUserId(userId);
					shopOpenManager.updateShopBusinessInfo(shopBusinessInfoDOSave);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 店铺提交开店申请--2.0新
	 * @return success: iWillOpenShopAction.htm
	 * @return statusError: iWillOpenShopAction.htm
	 */
	public String submitShopOpenAsk(){
		Long userId = queryUserId();
		
		ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
		
		if (shopFlowInfoDO != null
				&& shopFlowInfoDO.getAuditStatus() != null
				&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_NOT_APPLY) 
						|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_NO))
				&& shopFlowInfoDO.getIsFillComplete().equals(ShopConstant.IS_FILL_COMPLETE_COMPLETE)) {

			ShopOpenFlowDO shopOpenFlowDO2 = new ShopOpenFlowDO();
			if (shopOpenFlowDO.getAuditCount() == null) {
				shopOpenFlowDO2.setAuditCount(1);
			} else {
				shopOpenFlowDO2.setAuditCount(shopOpenFlowDO.getAuditCount() + 1);
			}
			shopOpenFlowDO2.setUserId(userId);
			shopOpenFlowDO2.setAuditStatus(ShopConstant.AUDIT_STATUS_WAIT);
			shopOpenFlowDO2.setIsOnlineAuditEnd(ShopConstant.IS_ONLINE_AUDIT_END_0);
			shopOpenFlowDO2.setIsPostalAuditEnd(ShopConstant.IS_POSTAL_AUDIT_END_0);
			shopOpenFlowDO2.setAuditProgress(" ");
			shopOpenFlowDO2.setGmtModified(new Date());
			try {
				shopOpenManager.updateShopOpenFlow(shopOpenFlowDO2);
			} catch (ManagerException e) {
				log.error("店铺提交开店申请出错", e);
			}
		} else {
			return "statusError";
		}
		return "success";
	}
	
	private String validateMember(){
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!isSameMember(login)) {
		    log.warn("current member has been is not same member before form submited, before info: " + getPj0() + ", current login info: " + login);
		    ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_MEMBER_NOT_MATCH, login.getNickname());
		    //return INPUT;
		    errorMessage = MessageName.OPERATE_MEMBER_NOT_MATCH;
		    return errorMessage;
		}
		return "";
	}
	
	private void initCustomerAndBusinessParam(){
		initOutShopInfoParam();
		initBaseParam();
		shopCategoryList = shopShowInfoAO.initShopCategoryList();
	}
	
	private void initIShopParam(){
		initBaseParam();
		shopCategoryList = shopShowInfoAO.initShopCategoryList();
	}
	
	public ShopCustomerInfoDO getShopCustomerInfoDO() {
		return shopCustomerInfoDO;
	}

	public void setShopCustomerInfoDO(ShopCustomerInfoDO shopCustomerInfoDO) {
		this.shopCustomerInfoDO = shopCustomerInfoDO;
	}

	public ShopBusinessInfoDO getShopBusinessInfoDO() {
		return shopBusinessInfoDO;
	}

	public ShopIshopInfoDO getShopIshopInfoDO() {
		return shopIshopInfoDO;
	}

	public void setShopIshopInfoDO(ShopIshopInfoDO shopIshopInfoDO) {
		this.shopIshopInfoDO = shopIshopInfoDO;
	}

	public void setShopBusinessInfoDO(ShopBusinessInfoDO shopBusinessInfoDO) {
		this.shopBusinessInfoDO = shopBusinessInfoDO;
	}

	public void setShopOpenAO(ShopOpenAO shopOpenAO) {
		this.shopOpenAO = shopOpenAO;
	}

	public void setShopOpenFlowDO(ShopOpenFlowDO shopOpenFlowDO) {
		this.shopOpenFlowDO = shopOpenFlowDO;
	}

	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

	public Integer getCurrentBrand() {
		return currentBrand;
	}

	public void setCurrentBrand(Integer currentBrand) {
		this.currentBrand = currentBrand;
	}

	public Integer getBrandSaveType() {
		return brandSaveType;
	}

	public void setBrandSaveType(Integer brandSaveType) {
		this.brandSaveType = brandSaveType;
	}

	public Integer getBrandSeq() {
		return brandSeq;
	}

	public void setBrandSeq(Integer brandSeq) {
		this.brandSeq = brandSeq;
	}

	public void setShopOpenManager(ShopOpenManager shopOpenManager) {
		this.shopOpenManager = shopOpenManager;
	}

	public File[] getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(File[] shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getShopLogoFileName() {
		return shopLogoFileName;
	}

	public void setShopLogoFileName(String shopLogoFileName) {
		this.shopLogoFileName = shopLogoFileName;
	}

	public File[] getIdCard() {
		return idCard;
	}

	public void setIdCard(File[] idCard) {
		this.idCard = idCard;
	}

	public String getIdCardFileName() {
		return idCardFileName;
	}

	public void setIdCardFileName(String idCardFileName) {
		this.idCardFileName = idCardFileName;
	}

	public File[] getCreation1() {
		return creation1;
	}

	public void setCreation1(File[] creation1) {
		this.creation1 = creation1;
	}

	public String getCreation1FileName() {
		return creation1FileName;
	}

	public void setCreation1FileName(String creation1FileName) {
		this.creation1FileName = creation1FileName;
	}

	public File[] getCreation2() {
		return creation2;
	}

	public void setCreation2(File[] creation2) {
		this.creation2 = creation2;
	}

	public String getCreation2FileName() {
		return creation2FileName;
	}

	public void setCreation2FileName(String creation2FileName) {
		this.creation2FileName = creation2FileName;
	}

	public int getIsHaveOuterShop() {
		return isHaveOuterShop;
	}

	public void setIsHaveOuterShop(int isHaveOuterShop) {
		this.isHaveOuterShop = isHaveOuterShop;
	}

	public File[] getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(File[] businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getBusinessLicenseFileName() {
		return businessLicenseFileName;
	}

	public void setBusinessLicenseFileName(String businessLicenseFileName) {
		this.businessLicenseFileName = businessLicenseFileName;
	}

	public File[] getBrandLogo() {
		return brandLogo;
	}

	public void setBrandLogo(File[] brandLogo) {
		this.brandLogo = brandLogo;
	}

	public String getBrandLogoFileName() {
		return brandLogoFileName;
	}

	public void setBrandLogoFileName(String brandLogoFileName) {
		this.brandLogoFileName = brandLogoFileName;
	}

	public File[] getBrandCertificate() {
		return brandCertificate;
	}

	public void setBrandCertificate(File[] brandCertificate) {
		this.brandCertificate = brandCertificate;
	}

	public String getBrandCertificateFileName() {
		return brandCertificateFileName;
	}

	public void setBrandCertificateFileName(String brandCertificateFileName) {
		this.brandCertificateFileName = brandCertificateFileName;
	}

	public File[] getQualityCertificate() {
		return qualityCertificate;
	}

	public void setQualityCertificate(File[] qualityCertificate) {
		this.qualityCertificate = qualityCertificate;
	}

	public String getQualityCertificateFileName() {
		return qualityCertificateFileName;
	}

	public void setQualityCertificateFileName(String qualityCertificateFileName) {
		this.qualityCertificateFileName = qualityCertificateFileName;
	}

	public File[] getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(File[] organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationCodeFileName() {
		return organizationCodeFileName;
	}
	
	public void setOrganizationCodeFileName(String organizationCodeFileName) {
		this.organizationCodeFileName = organizationCodeFileName;
	}

	public File[] getTaxPass() {
		return taxPass;
	}

	public void setTaxPass(File[] taxPass) {
		this.taxPass = taxPass;
	}

	public String getTaxPassFileName() {
		return taxPassFileName;
	}

	public void setTaxPassFileName(String taxPassFileName) {
		this.taxPassFileName = taxPassFileName;
	}
	
	public Map<Long, String> getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(Map<Long, String> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}
	
}
