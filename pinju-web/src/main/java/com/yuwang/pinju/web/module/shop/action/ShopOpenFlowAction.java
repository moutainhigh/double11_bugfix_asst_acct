package com.yuwang.pinju.web.module.shop.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.CategoryMarginManager;
import com.yuwang.pinju.core.margin.ao.MarginAO;
import com.yuwang.pinju.core.margin.manager.MarginManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.shop.ao.ShopOpenAO;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.ObjectUtil;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.margin.MarginSellerDO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopFlowInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.interceptor.MemberAuthInterceptor;
import com.yuwang.pinju.web.interceptor.LoginInterceptor.MemberCheckerAware;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.shop.screen.ShopOpenBaseAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * 开店流程action
 * 
 * @author xueqi
 * 
 * @since 2011-7-4
 */
public class ShopOpenFlowAction extends ShopOpenBaseAction implements MemberCheckerAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String test = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * 以何种方式保存品牌信息 0:普通保存 1:保存并新建 2:不保存但新建   2.0新 
	 */
	private Integer brandSaveType;
	
	/**
	 * 类目manager 2.0新
	 */
	private CategoryCacheManager categoryCacheManager;
	
	/**
	 * 当前品牌 2.0新
	 */
	private Integer messageType;
	
	/**
	 * 当前品牌 2.0新
	 */
	private Integer currentBrand;
	
	/**
	 * 填写页面的类型(基础信息/上传信息/品牌信息) --2.0新
	 */
	private Integer fillType;
	
	/**
	 * 表示第几个品牌信息  2.0新
	 */
	private Integer brandSeq;
	
	/**
	 * 是否是新添加品牌信息;   2.0新
	 */
	private Integer isNewBrand;
	
	private MarginManager marginManager;
	
	private ShopOpenAO shopOpenAO;

	private ShopOpenFlowDO shopOpenFlowDO;

	private ShopCustomerInfoDO shopCustomerInfoDO;

	private ShopBusinessInfoDO shopBusinessInfoDO;
	
	private ShopIshopInfoDO shopIshopInfoDO;
	
	private ShopFlowInfoDO shopFlowInfoDO;

	private String businessLicenseEndDate;
	
	private File []myFile;
	
	private String myFileFileName;
	
	private FileStorageManager fileStorageManager;
	
	private String shopName;
	
	private String result;
	
	private ShopOpenManager shopOpenManager;
	
	private MarginAO marginAO;
	
	/**
	 * 类目id
	 */
	private Long categoryId;
	
	/**
	 * 类目名称
	 */
	private String selectedCategory;
	
	/**
	 * 是否有在其他网站开过店
	 */
	private int isHaveOuterShop=2;
	
	private MemberManager memberManager;

	/**
	 * 保证金
	 */
	private int exchangeMargin;
	
	/**
	 * 保证金金额
	 */
	private String exchangePrice;

	/**
	 * 基本信息填写步骤
	 */
	private int fillStep;

	private String errorMessage;
	
	public Integer getBrandSaveType() {
		return brandSaveType;
	}


	public void setBrandSaveType(Integer brandSaveType) {
		this.brandSaveType = brandSaveType;
	}

	private ShopShowInfoManager shopShowInfoManager;
	
	private CategoryMarginManager categoryMarginManager;
	
	public Integer getMessageType() {
		return messageType;
	}


	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}


	public CategoryCacheManager getCategoryCacheManager() {
		return categoryCacheManager;
	}


	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	/**
	 * 店铺分类list
	 */
	private Map<Long, String> shopCategoryList;
	
	public Integer getCurrentBrand() {
		return currentBrand;
	}


	public void setCurrentBrand(Integer currentBrand) {
		this.currentBrand = currentBrand;
	}

	private ShopShowInfoAO shopShowInfoAO;
	

	/**
	 * 同意服务条款
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String shopOpenAgreement() {
		String returnString = "";
		// 判断是C店还是B店,返回不同页面
		long userId = queryUserId();
		List shopInfoList = null;
		if (sellerType == ShopConstant.SELLER_TYPE_C.intValue()) {
			shopInfoList = shopOpenAO.queryShopCustomerInfo(userId);
			if (shopInfoList != null && shopInfoList.size() > 0) {
				shopCustomerInfoDO = (ShopCustomerInfoDO) shopInfoList.get(0);
			} else {
				shopCustomerInfoDO = null;
			}
			initOutShopInfoParam();
//			setShopCustomerCookie();
			returnString = "successC";

		} else {
			shopInfoList = shopOpenAO.queryShopBusinessInfo(userId);
			if (shopInfoList != null && shopInfoList.size() > 0) {
				shopBusinessInfoDO = (ShopBusinessInfoDO) shopInfoList.get(0);
			} else {
				shopBusinessInfoDO = null;
			}
//			setShopBusinessCookie(ShopConstant.IS_FILL_SHOP_INFO_NO);
			returnString = "FILL_SHOP_INFO_B_SETP1";
		}
		// 返回页面所需信息
		shopCategoryList = shopShowInfoAO.initShopCategoryList();
		initBaseParam();
		return returnString;
	}
	

	/**
	 * 验证C店铺基本信息
	 * 
	 * @param shopCustomerInfoDO
	 * @return
	 */
	private String validateSaveCustomerShopInfo(ShopCustomerInfoDO shopCustomerInfoDO, Long userId) {

		StringBuffer message = new StringBuffer();
		message.append(validateMember());
		if (message != null && message.length() > 0) {
			return message.toString();
		}
		if (shopCustomerInfoDO != null) {
			if (shopCustomerInfoDO.getName() == null || "".equals(shopCustomerInfoDO.getName())
					|| shopCustomerInfoDO.getName().trim().length() == 0) {
				message.append("店铺名称不能为空<br>");
			} else {
				if (null != shopCustomerInfoDO.getName() && shopCustomerInfoDO.getName().length() > 20) {
					message.append("店铺名称不能超过20个字符<br>");
				} else if (shopOpenAO.queryShopInfosByName(shopCustomerInfoDO.getName(), userId)) {
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
//			if (shopCustomerInfoDO.getGoodsSource() == null) {
//				message.append("主要货源不能为空<br>");
//			}
//			if (shopCustomerInfoDO.getGoodsSource() != null
//					&& !ObjectUtil.isIntType(String.valueOf(shopCustomerInfoDO.getGoodsSource()))) {
//				message.append("请选择主要货源<br>");
//			}
			if (shopCustomerInfoDO.getDescription() == null || "".equals(shopCustomerInfoDO.getDescription())
					|| shopCustomerInfoDO.getDescription().trim().length() == 0) {
				//message.append("店铺介绍不能为空<br>");
			} else {
				if (shopCustomerInfoDO.getDescription() != null && (shopCustomerInfoDO.getDescription().length() > 500)) {
					message.append("店铺介绍不能超过500个字<br>");
				}
			}
			if (shopCustomerInfoDO.getProvince() == null) {
				message.append("企业所在省不能为空<br>");
			}
			if (shopCustomerInfoDO.getCity() == null) {
				message.append("企业所在城市不能为空<br>");
			}
			if (isHaveOuterShop == 0) {
				if (shopCustomerInfoDO.getOuterShopAddressUrl() == null
						|| "".equals(shopCustomerInfoDO.getOuterShopAddressUrl())
						|| shopCustomerInfoDO.getOuterShopAddressUrl().trim().length() == 0) {
					message.append("店铺地址Url不能为空<br>");
				} else {
					if (shopCustomerInfoDO.getOuterShopAddressUrl() != null
							&& !ObjectUtil.isUrlPatten(String.valueOf(shopCustomerInfoDO.getOuterShopAddressUrl()))) {
						message.append("请输入正确的店铺地址Url<br>");
					}
				}
//				if (shopCustomerInfoDO.getOuterShopLevel() == null) {
//					message.append("请选择店铺等级<br>");
//				}
//				if (shopCustomerInfoDO.getOuterShopSaleScope() == null) {
//					message.append("请选择销售规模<br>");
//				}
//				if (shopCustomerInfoDO.getIsEnterB2c() == null) {
//					message.append("请选择是否入住过B2C<br>");
//				}
			}

			String[] alertStrC = { "请上传店标", "请上传企业营业执照", "请上传组织机构代码证", "请上传税务登记证" };
			String[] alertStrC2 = { "店标", "企业营业执照", "组织机构代码证", "税务登记证" };
			if (myFile == null || myFileFileName.length() <= 0) {
				for (int i = 0; i < 4; i++) {
					message.append(alertStrC[i] + "<br>");
				}
			} else {

				String fileNames[] = myFileFileName.split(",");
				for (int i = 0; i < 3; i++) {
					if (i + 1 <= fileNames.length) {
						if (null == fileNames[i] || "".equals(fileNames[i])) {
							message.append(alertStrC[i] + "<br>");
						}
					} else {
						message.append(alertStrC[i] + "<br>");
					}
					if (i == 0) {
//						if (myFile[i].length() / PinjuConstant.FILE_SIZE_K > 80) {
//							message.append(alertStrC2[i] + "文件过大<br>");
//							return message.toString();
//						}
					} else {
						if (myFile[i].length() / PinjuConstant.FILE_SIZE_K > 500) {
							message.append(alertStrC2[i] + "文件过大<br>");
							return message.toString();
						}
					}
					if (!FileSecurityUtils.isImageValid(myFile[i])) {
						message.append(alertStrC2[i] + "格式不正确<br>");
						return message.toString();
					}

				}
			}
		} else {
			message.append("请正确填写信息");
		}
		initOutShopInfoParam();
		return message.toString();
	}
	public Integer getBrandSeq() {
		return brandSeq;
	}


	public void setBrandSeq(Integer brandSeq) {
		this.brandSeq = brandSeq;
	}


	/**
	 * 验证B店第一步骤信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	private String validateSaveBusinessShopInfo1(
			ShopBusinessInfoDO shopBusinessInfoDO,Long userId) {
		StringBuffer message=new StringBuffer();
		
		message.append(validateMember());
		if(message != null && message.length() > 0){
			return message.toString();
		}
		if(shopBusinessInfoDO!=null){
			if(shopBusinessInfoDO.getName()==null || "".equals(shopBusinessInfoDO.getName()) || shopBusinessInfoDO.getName().trim().length()==0){
				message.append("店铺名称不能为空<br>");
			}else{
				if(null!=shopBusinessInfoDO.getName() 
						&& shopBusinessInfoDO.getName().length()>20){
					message.append("店铺名称不能超过20个字符<br>");
				}else if(shopOpenAO.queryShopInfosByName(shopBusinessInfoDO.getName(),userId)){
					message.append("店铺名已经有人使用，换一个吧！<br>");
				}
			}
			if (shopBusinessInfoDO.getShopCategory()==null) {
				message.append("店铺类目不能为空<br>");
			}else{
				if (shopBusinessInfoDO.getShopCategory() != null
						&& !ObjectUtil.isIntType(shopBusinessInfoDO.getShopCategory())) {
					message.append("请选择店铺类目<br>");
				}
			}
//			if (shopBusinessInfoDO.getShopNature()==null) {
//				message.append("买家类型不能为空<br>");
//			}else{
//				if (shopBusinessInfoDO.getShopNature() != null
//						&& !ObjectUtil.isIntType(String.valueOf(shopBusinessInfoDO
//								.getShopNature()))) {
//					message.append("请选择买家类型<br>");
//				}
//			}
		    if (shopBusinessInfoDO.getGoodsSource()==null) {
				message.append("主要货源不能为空<br>");
			}else{
				if (shopBusinessInfoDO.getGoodsSource() != null
						&& !ObjectUtil.isIntType(String.valueOf(shopBusinessInfoDO
								.getGoodsSource()))) {
					message.append("请选择主要货源<br>");
				}
			}
		    if (shopBusinessInfoDO.getDescription()==null || "".equals(shopBusinessInfoDO.getDescription()) || shopBusinessInfoDO.getDescription().trim().length()==0) {
				//message.append("店铺介绍不能为空<br>");
			}else{
				if (shopBusinessInfoDO.getDescription() != null && (shopBusinessInfoDO.getDescription().length()>500)) {
					message.append("店铺介绍不能超过500个字<br>");
				}
			}
		    if (myFile== null || myFileFileName.length()<=0) {
				//message.append("请上传店标<br>");
			} else {
				if(myFile[0].length()/PinjuConstant.FILE_SIZE_K > 80){
					message.append("店标文件过大" + "<br>");
					return message.toString();
				}
				if (!FileSecurityUtils.isImageValid(myFile[0])) {
					message.append("店标格式不正确" + "<br>");
					return message.toString();
				}
			}
			
		}else{
			message.append("请正确填写信息");
		}
		return message.toString();
	}
	
	/**
	 * 验证B店第二步骤信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	private String validateSaveBusinessShopInfo2(
			ShopBusinessInfoDO shopBusinessInfoDO) {
		StringBuffer message=new StringBuffer();
		message.append(validateMember());
		if(message != null && message.length() > 0){
			return message.toString();
		}
		if (shopBusinessInfoDO!=null) {
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
			if(null==businessLicenseEndDate || "".equals(businessLicenseEndDate)){
				message.append("营业执照有效期限不能为空!<br> ");
			}else{
				DateFormat format1 = null;
				if (null != businessLicenseEndDate && !ObjectUtil.isDate(businessLicenseEndDate)) {
					message.append("日期格式输入不正确!<br> ");
				} else {
					String str[] = businessLicenseEndDate.split("-");
					if (str.length == 3) {
						businessLicenseEndDate = str[0] + "-" + str[1] + "-"+ str[2];
						format1 = new SimpleDateFormat("yyyy-MM-dd");
						try {
						  if (null != businessLicenseEndDate && format1.parse(businessLicenseEndDate).getTime() < new Date().getTime()) {
								message.append("营业执照有效期限必须大于当前日期！<br> ");
							}
						} catch (ParseException e) {
							log.error(e.getMessage());
						}
					} else {
						message.append("日期格式输入不正确!<br> ");
					}
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
			if (shopBusinessInfoDO.getProvince()==null || shopBusinessInfoDO.getProvince().trim().length()==0) {
				message.append("企业所在省不能为空<br>");
			}if (shopBusinessInfoDO.getCity()==null || shopBusinessInfoDO.getCity().trim().length()==0) {
				message.append("企业所在城市不能为空<br>");
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
				/*if (shopBusinessInfoDO.getOuterShopLevel()==null) {
					message.append("请选择店铺等级<br>");
				}if (shopBusinessInfoDO.getOuterShopSaleScope()==null) {
					message.append("请选择销售规模<br>");
				}if (shopBusinessInfoDO.getIsEnterB2c()==null) {
					message.append("请选择是否入住过B2C<br>");
				}*/
			}
//			String [] alertStr = {"请上传企业营业执照","请上传组织机构代码证","请上传品牌授权书或品牌商标注册证书","请上传商品质量检验证书","请上传税务登记证","请上传生产许可证","请上传经营许可证","请上传税务登记证"};
//			String [] alertStr2 = {"企业营业执照","组织机构代码证","品牌授权书/品牌商标注册证书","商品质量检验证书","税务登记证","生产许可证","经营许可证","税务登记证"};
			String [] alertStr = {"请上传企业营业执照","请上传组织机构代码证","请上传税务登记证"};
			String [] alertStr2 = {"企业营业执照","组织机构代码证","税务登记证"};
			//String [] alertStr3 = {"企业营业执照","组织机构代码证","税务登记证"};
			if (myFile== null ||  myFileFileName.length()<=0) {
				//for(int i=0;i<7;i++){
				for(int i=0;i<3;i++){
					 message.append(alertStr[i]+"<br>");
				}
			}else{
				String fileNames[] = myFileFileName.split(",");
				//for(int i=0;i<7;i++){
				for(int i=0;i<myFile.length;i++){
					//if(i+1<=fileNames.length){
						if(null==fileNames[i] ||  "".equals(fileNames[i])){
						    message.append(alertStr[i]+"<br>");
						    return message.toString();
					     }
					//}else{
					//	 message.append(alertStr[i]+"<br>");
					//}
					if(myFile[i].length()/PinjuConstant.FILE_SIZE_K > 500){
						message.append(alertStr2[i]+ "文件过大<br>");
						return message.toString();
					}
					if (!FileSecurityUtils.isImageValid(myFile[i])) {
						message.append( alertStr2[i]+ "格式不正确<br>");
						return message.toString();
					}
				}
			}
		}else{
			message.append("请填写正确信息 ");
		}
		return message.toString();
	}
	public Integer getFillType() {
		return fillType;
	}


	public void setFillType(Integer fillType) {
		this.fillType = fillType;
	}


	/**
	 * 验证B店第三步骤的信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	private String  validateSaveBusinessShopInfo3(
			ShopBusinessInfoDO shopBusinessInfoDO){
		StringBuffer message=new StringBuffer();
		message.append(validateMember());
		if(message != null && message.length() > 0){
			return message.toString();
		}
		if (myFile != null && myFile.length > 0) {
			String brandNames = shopBusinessInfoDO.getBrandName();
			String brandName[] = brandNames.split(";");
			//String fileNames[] = myFileFileName.split(",");
			String alertString[] = {"品牌logo图片","品牌授权书/品牌商标注册证书","商品质量检验证书"};
			if(myFile.length % 3 != 0){
				message.append("上传图片数量不正确,请重新上传！");
				return message.toString();
			}
			for (int i = 0; i < myFile.length; i++) {
				int k = 0;
				if(i % 3 == 0){
					k = 80;
				}else{
					k = 500;
				}
				if (myFile[i].length() / PinjuConstant.FILE_SIZE_K > k) {
					message.append("品牌: \""+brandName[i] + "\" 的"+alertString[i%3]+"文件过大<br>");
					return message.toString();
				}
				if (!FileSecurityUtils.isImageValid(myFile[i])) {
					message.append("品牌: \""+brandName[i] + "\" 的+alertString[i%3]+文件格式不正确<br>");
					return message.toString();
				}
			}
		}
		return message.toString();
	}
	
	/**
	 * 验证B店第四步骤的信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	private String validateSaveBusinessShopInfo4(
			ShopBusinessInfoDO shopBusinessInfoDO) {
		StringBuffer message=new StringBuffer();
		message.append(validateMember());
		if(message != null && message.length() > 0){
			return message.toString();
		}
		if(shopBusinessInfoDO!=null)
		{
			/*if (null==shopBusinessInfoDO.getContactName() || "".equals(shopBusinessInfoDO.getContactName()) || shopBusinessInfoDO.getContactName().trim().length()==0) {
				message.append("联系人姓名不能为空<br>");
			}else{
				if (null!=shopBusinessInfoDO.getContactName() 
						&& shopBusinessInfoDO.getContactName().length()>20) {
					message.append("联系人姓名不能超过20个字符<br>");
				}else if(!ObjectUtil.isZEng(shopBusinessInfoDO.getContactName().trim())){
					message.append("联系人姓名只能为中文、英文字符<br>");
				}
			}
			if (null==shopBusinessInfoDO.getContactPhone()) {
				message.append("联系人电话号码不能为空<br>");
			}*/
//			else{
//				if (null!=shopBusinessInfoDO.getContactPhone() 
//						&& (!(ObjectUtil.isMobile(shopBusinessInfoDO.getContactPhone()) || ObjectUtil.isTel(shopBusinessInfoDO.getContactPhone()) ))) {
//					message.append("请输入正确的联系人电话号码<br>");
//				}
//			}
			/*if (null==shopBusinessInfoDO.getContactAddress() || "".equals(shopBusinessInfoDO.getContactAddress()) || shopBusinessInfoDO.getContactAddress().trim().length()==0) {
				message.append("联系人地址不能为空<br>");
			}else{
				if (null!=shopBusinessInfoDO.getContactAddress() 
						&& shopBusinessInfoDO.getContactAddress().length()>500) {
					if (null!=shopBusinessInfoDO.getContactAddress() 
							&& shopBusinessInfoDO.getContactAddress().length()>500) {
						message.append("联系人地址不能超过500个字符<br>");
					}
				}
			}
			if (null==shopBusinessInfoDO.getContactPostalCode()) {
				message.append("联系人邮政编码不能为空<br>");
			}else{
				if (null!=shopBusinessInfoDO.getOrganizationCodeNumber() && (!ObjectUtil.isPostal(shopBusinessInfoDO.getContactPostalCode()))) {
					message.append("请填写正确的联系人邮政编码<br>");
				}
			}*/
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
//			else{
//				if (null!=shopBusinessInfoDO.getShopManagerTelephone() 
//						&& (!ObjectUtil.isTel(shopBusinessInfoDO.getShopManagerTelephone()))) {
//					message.append("请填写正确的店铺负责人电话<br>");
//				}
//			}
			if (null==shopBusinessInfoDO.getShopManagerMobile()) {
				message.append("店铺负责人手机号码不能为空<br>");
			}
//			else{
//				if (null!=shopBusinessInfoDO.getShopManagerMobile() 
//						&& (!ObjectUtil.isMobile(shopBusinessInfoDO.getShopManagerMobile()))) {
//					message.append("请填写正确的店铺负责人手机号码<br>");
//				}
//			}
			if (null==shopBusinessInfoDO.getShopManagerEmail()) {
				message.append("店铺负责人邮箱不能为空<br>");
			}else{
				if (null!=shopBusinessInfoDO.getShopManagerEmail() 
						&& (!ObjectUtil.isEmail(shopBusinessInfoDO.getShopManagerEmail()))) {
					message.append("请填写正确的店铺负责人邮箱<br>");
				}
			}
//			if (null==shopBusinessInfoDO.getShopManagerFax()) {
//				message.append("店铺负责人传真不能为空<br>");
//			}
//			else{
//				if (null!=shopBusinessInfoDO.getShopManagerFax() 
//						&& !shopBusinessInfoDO.getShopManagerFax().equals("")
//						&& (!ObjectUtil.isTel(shopBusinessInfoDO.getShopManagerFax()))) {
//					message.append("请填写正确的店铺负责人传真<br>");
//				}
//			}
			/*if (null==shopBusinessInfoDO.getEmergencyContactName() || "".equals(shopBusinessInfoDO.getEmergencyContactName()) || shopBusinessInfoDO.getEmergencyContactName().trim().length()==0) {
				message.append("紧急联系人姓名不能为空<br>");
			}else{
				if (null!=shopBusinessInfoDO.getEmergencyContactName() 
						&& shopBusinessInfoDO.getEmergencyContactName().length()>20) {
					message.append("紧急联系人姓名不能超过20个字符<br>");
				}else if(!ObjectUtil.isZEng(shopBusinessInfoDO.getEmergencyContactName().trim())){
					message.append("紧急联系人姓名只能为中文、英文字符<br>");
				}
			}
			if (null==shopBusinessInfoDO.getEmergencyContactPhone()) {
				message.append("紧急联系人电话号码不能为空<br>");
			}*/
//			else{
//				if (null!=shopBusinessInfoDO.getEmergencyContactPhone() 
//						&& (!ObjectUtil.isMobile(shopBusinessInfoDO.getEmergencyContactPhone()))) {
//					message.append("请填写正确的紧急联系人电话号码<br>");
//				}	
//			}
		}else{
			message.append("请填写正确的信息");
		}
		
		return message.toString();
	}
	
	private String[] saveFile(File[] files) {
		String fileNames[] = myFileFileName.split(",");
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
	 * 检查店铺名称是否相同
	 * 
	 * @return
	 */
	public String queryAjaxShopName() {
		JSONObject json = this.queryAjaxSelShopName();
		result = json.toString();
		return "success";
	}
	
	private JSONObject queryAjaxSelShopName() {
		try {
			long userId = queryUserId();
			boolean isContain=shopOpenAO.queryShopInfosByName(shopName,userId);
			JSONObject json = new JSONObject();
			if(isContain){
				json.put("isRename", "1");
				json.put("message", "<font color='red'>店铺名已经有人使用，换一个吧！</font>");
			}else{
				json.put("isRename", "0");
				json.put("message", "<img src='http://static.pinju.com/img/icon-succeed.png'/>");
			}
			return json;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	
	public ShopIshopInfoDO getShopIshopInfoDO() {
		return shopIshopInfoDO;
	}


	public void setShopIshopInfoDO(ShopIshopInfoDO shopIshopInfoDO) {
		this.shopIshopInfoDO = shopIshopInfoDO;
	}


	/**
	 * 保存C开店店铺信息
	 * 
	 * @return
	 */
	public String saveCustomerShopInfo() {
		try {
			long userId = queryUserId();
			String nickname = queryNickName();
			//验证C的信息
			errorMessage=validateSaveCustomerShopInfo(shopCustomerInfoDO,userId);
			//返回错误信息
			if (null!=errorMessage && !"".equals(errorMessage)) {
				shopCategoryList = shopShowInfoAO.initShopCategoryList();
				initBaseParam();
				initOutShopInfoParam();
				return "successC";
			}
			String picNames[] = null;
			boolean flag = true;
			if(myFile != null && myFile.length > 0){
				//保存图片
				picNames = saveFile(myFile);
				//如果保存成功则记录图片路径到数据库
				if(picNames!=null && picNames.length>0){
					if(picNames.length == 4){
						shopCustomerInfoDO.setShopLogo(picNames[0]);
						shopCustomerInfoDO.setBusinessLicense(picNames[1]);
						shopCustomerInfoDO.setOrganizationCode(picNames[2]);
						shopCustomerInfoDO.setTaxPass(picNames[3]);
					}else{
						shopCustomerInfoDO.setShopLogo("");
						shopCustomerInfoDO.setBusinessLicense(picNames[0]);
						shopCustomerInfoDO.setOrganizationCode(picNames[1]);
						shopCustomerInfoDO.setTaxPass(picNames[2]);
					}
					
				}else{
					flag = false;
					shopCustomerInfoDO.setTaxPass("none");
				}
			}else{
				flag = false;
			}
			if(!flag){
				shopCustomerInfoDO.setShopLogo("");
				shopCustomerInfoDO.setBusinessLicense("none");
				shopCustomerInfoDO.setOrganizationCode("none");
				shopCustomerInfoDO.setTaxPass("none");
			}
			shopCustomerInfoDO.setUserId(userId);
			shopCustomerInfoDO.setNickname(nickname);
			shopCustomerInfoDO.setSellerType(String.valueOf(sellerType));
			shopCustomerInfoDO.setApproveStatus(ShopConstant.APPROVE_STATUS_NO);
			if(isHaveOuterShop==1){
				shopCustomerInfoDO.setOuterShopAddressUrl("");
				shopCustomerInfoDO.setOuterShopLevel(null);
				shopCustomerInfoDO.setOuterShopSaleScope(null);
				shopCustomerInfoDO.setIsEnterB2c(null);
			}
			
			//根据userid获取C店信息,如果有记录则更新记录,如果没有记录则插入一条新的记录
			List<ShopCustomerInfoDO> resultList = shopOpenAO.queryShopCustomerInfo(userId);
			if (resultList != null && resultList.size() > 0) {
				shopOpenManager.updateShopCustomerInfo(shopCustomerInfoDO);
			} else {
				shopCustomerInfoDO.setApproveStatus(ShopConstant.APPROVE_STATUS_NO);
				shopOpenManager.saveShopInfo(sellerType, shopCustomerInfoDO);
			}
			//根据userid,sellerytpe获取开店流程信息.如果有记录,则更新记录,如果没有记录则插入一条新记录
			List<ShopOpenFlowDO> shopOpenFlowList = shopOpenAO.queryShopOpenFlow(userId);
			if (shopOpenFlowList != null && shopOpenFlowList.size() > 0) {
				shopOpenFlowDO = (ShopOpenFlowDO) shopOpenFlowList.get(0);
				shopOpenFlowDO.setAuditCount(shopOpenFlowDO.getAuditCount() + 1);
				shopOpenFlowDO.setIsAgreement(ShopConstant.IS_AGREEMENT_TRUE);
				shopOpenFlowDO.setNoPassReason("");
				shopOpenFlowDO.setAuditStatus(ShopConstant.AUDIT_STATUS_WAIT);
				shopOpenFlowDO.setAuditProgress("");
				shopOpenFlowDO.setGmtModified(new Date());
				shopOpenFlowDO.setIsFillInfo(ShopConstant.IS_FILL_SHOP_INFO_STEP1);
				shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
			} else {
				shopOpenFlowDO = new ShopOpenFlowDO();
				shopOpenFlowDO.setUserId(userId);
				shopOpenFlowDO.setConfiguration("USER_NAME="+nickname);
				shopOpenFlowDO.setIsAgreement(ShopConstant.IS_AGREEMENT_TRUE);
				shopOpenFlowDO.setSellerType(sellerType);
				shopOpenFlowDO.setAuditCount(1);
				shopOpenFlowDO.setAuditProgress("");
				shopOpenFlowDO.setAuditStatus(ShopConstant.AUDIT_STATUS_WAIT);
				shopOpenFlowDO.setIsBlack(0);
				shopOpenFlowDO.setIsKa(ShopConstant.IS_KA_NO);
				shopOpenFlowDO.setIsOnlineAuditEnd(0);
				shopOpenFlowDO.setIsPostalAuditEnd(0);
				shopOpenFlowDO.setNoPassReason("");
				shopOpenFlowDO.setReviewer("");
				shopOpenFlowDO.setGmtCreate(new Date());
				shopOpenFlowDO.setGmtModified(new Date());
				shopOpenFlowDO.setIsFillInfo(ShopConstant.IS_FILL_SHOP_INFO_STEP1);
				shopOpenManager.agreement(shopOpenFlowDO);
			}
			List<ShopCustomerInfoDO> shopInfoList = shopOpenAO.queryShopCustomerInfo(userId);
			shopCustomerInfoDO = (ShopCustomerInfoDO) shopInfoList.get(0);
			//删除cookie
//			PinjuCookieManager.clearShopC();
		} catch (ManagerException e) {
			log.error(e.getMessage());
		}
		if (!shopOpenFlowDO.getAuditStatus().equals( ShopConstant.AUDIT_STATUS_PASS)) {
			return "SHOP_OPEN_BEGIN";
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
	private String validateSaveBusinessShopInfo(int fillStep, Long userId){
		
		if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP1.intValue()) {
			errorMessage=validateSaveBusinessShopInfo1(shopBusinessInfoDO,userId);
			if (null!=errorMessage && !"".equals(errorMessage)) {
					shopCategoryList = shopShowInfoAO.initShopCategoryList();
				initBaseParam();
				return "FILL_SHOP_INFO_B_SETP1";
			}
		}else if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP2.intValue()) {
			errorMessage=validateSaveBusinessShopInfo2(shopBusinessInfoDO);
			if (null!=errorMessage && !"".equals(errorMessage)) {
				initOutShopInfoParam();
				return "FILL_SHOP_INFO_B_SETP2";
			}
		}else if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP3
				.intValue()) {
			errorMessage=validateSaveBusinessShopInfo3(shopBusinessInfoDO);
			if (null!=errorMessage && !"".equals(errorMessage)) {
				return "FILL_SHOP_INFO_B_SETP3";
			}
		}else if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP4
				.intValue()) {
			errorMessage=validateSaveBusinessShopInfo4(shopBusinessInfoDO);
			if (null!=errorMessage && !"".equals(errorMessage)) {
				return "FILL_SHOP_INFO_B_SETP4";
			}
		}
		return "";
	}

	/**
	 * 保存B开店店铺信息
	 * 
	 * @return
	 */
	public String saveBusinessShopInfo() {
		try {
			long userId = queryUserId();
			String nickname = queryNickName();
			String errorRedirect = validateSaveBusinessShopInfo(fillStep, userId);
			if(errorRedirect!=null && errorRedirect.length()>0){
				return errorRedirect;
			}
			String picNames[] = null;
			//如果有图要保存
			if (myFile != null && myFile.length > 0) {
				picNames = saveFile(myFile);
				// 如果是第一步
				if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP1.intValue()) {
					if (picNames != null && picNames.length > 0) {
						shopBusinessInfoDO.setShopLogo(picNames[0]);
					}
				}
				// 如果是第二步
				else if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP2.intValue()) {
					if (picNames != null && picNames.length > 0) {
						shopBusinessInfoDO.setBusinessLicense(picNames[0]);
						shopBusinessInfoDO.setOrganizationCode(picNames[1]);
						shopBusinessInfoDO.setTaxPass(picNames[2]);
					}
				}
				// 如果是第三步
				else if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP3.intValue()) {
					StringBuffer logo = new StringBuffer();
					StringBuffer brandCertificate =  new StringBuffer();
					StringBuffer qualityCertificate =  new StringBuffer();
					if (picNames != null && picNames.length > 0) {
						for (int i = 0; i < picNames.length; i++) {
							if (i % 3 == 0) {
								logo.append(picNames[i]).append(",");
							} else if (i % 3 == 1) {
								brandCertificate.append(picNames[i]).append(",");
							} else if (i % 3 == 2) {
								qualityCertificate.append(picNames[i]).append(",");
							}

						}
						String logoStr = logo.substring(0, logo.length() - 1);
						String brandCertificateStr = brandCertificate.substring(0, brandCertificate.length() - 1);
						String qualityCertificateStr = qualityCertificate.substring(0, qualityCertificate.length() - 1);
						shopBusinessInfoDO.setBrandLogo(logoStr);
						shopBusinessInfoDO.setBrandCertificate(brandCertificateStr);
						shopBusinessInfoDO.setQualityCertificate(qualityCertificateStr);
					}

				}
			}else{
				if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP1.intValue()) {
					shopBusinessInfoDO.setShopLogo("");
				}
			}
			//处理非必填项--店铺介绍
			if(fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP1.intValue() && (shopBusinessInfoDO.getDescription() == null || shopBusinessInfoDO.getDescription().trim().equals(""))){
				shopBusinessInfoDO.setDescription(" ");
			}
			//处理非必填项--主要货源
			if(fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP1.intValue() && shopBusinessInfoDO.getGoodsSource() == null){
				shopBusinessInfoDO.setGoodsSource(-1);
			}
			//处理日期
			if(businessLicenseEndDate!=null && businessLicenseEndDate.length()>0){
				DateFormat format1 = null;
				if(businessLicenseEndDate.split("-").length>1){
					format1 = new SimpleDateFormat("yyyy-MM-dd"); 
				}else{
					String str[] = businessLicenseEndDate.split("/");
					businessLicenseEndDate = str[2]+"-"+str[0]+"-"+str[1];
					format1 = new SimpleDateFormat("yyyy-MM-dd"); 
				}
				shopBusinessInfoDO.setBusinessLicenseEndDate(format1.parse(businessLicenseEndDate));
			}
			shopBusinessInfoDO.setUserId(userId);
			shopBusinessInfoDO.setNickname(nickname);
			shopBusinessInfoDO.setSellerType(String.valueOf(sellerType));
			shopBusinessInfoDO.setApproveStatus(ShopConstant.APPROVE_STATUS_NO);
			//如果有信息,就做更新操作
			List<ShopBusinessInfoDO> resultList = shopOpenAO.queryShopBusinessInfo(userId);
			if (resultList != null && resultList.size() > 0) {
				if (isHaveOuterShop == 1) {
					shopBusinessInfoDO.setOuterShopAddressUrl("");
					shopBusinessInfoDO.setOuterShopLevel(null);
					shopBusinessInfoDO.setOuterShopSaleScope(null);
					shopBusinessInfoDO.setIsEnterB2c(null);
				} else if (2 == isHaveOuterShop) {
					shopBusinessInfoDO.setOuterShopAddressUrl(((ShopBusinessInfoDO) resultList.get(0))
							.getOuterShopAddressUrl());
					shopBusinessInfoDO.setOuterShopLevel(((ShopBusinessInfoDO) resultList.get(0)).getOuterShopLevel());
					shopBusinessInfoDO.setOuterShopSaleScope(((ShopBusinessInfoDO) resultList.get(0))
							.getOuterShopSaleScope());
					shopBusinessInfoDO.setIsEnterB2c(((ShopBusinessInfoDO) resultList.get(0)).getIsEnterB2c());
				}
				//更新
				shopOpenAO.updateShopBusinessInfo(userId, shopBusinessInfoDO);
			} else {
				//保存
				shopOpenAO.saveShopInfo(sellerType, shopBusinessInfoDO);
			}
			resultList = shopOpenAO.queryShopBusinessInfo(userId);
			shopBusinessInfoDO = (ShopBusinessInfoDO) resultList.get(0);
			
			//跳转到下个页面
			if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_NO.intValue()) {
//				String cookieString = PinjuCookieManager.getShop1();
//				log.error(cookieString);
//				setShopBusinessCookie(fillStep);
				return "FILL_SHOP_INFO_B_SETP1";
			} else if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP1
					.intValue()) {
				initOutShopInfoParam();
//				setShopBusinessCookie(fillStep);
//				PinjuCookieManager.clearShop1();
				return "FILL_SHOP_INFO_B_SETP2";
			} else if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP2
					.intValue()) {
//				setShopBusinessCookie(fillStep);
//				PinjuCookieManager.clearShop2();
				return "FILL_SHOP_INFO_B_SETP3";
			} else if (fillStep == ShopConstant.IS_FILL_SHOP_INFO_STEP3
					.intValue()) {
//				setShopBusinessCookie(fillStep);
//				PinjuCookieManager.clearShop3();
				return "FILL_SHOP_INFO_B_SETP4";
			}
			//更新或保存流程表信息
//			if (null != shopOpenFlowDO) {
//				List<ShopOpenFlowDO> shopOpenFlowList = shopOpenAO.queryShopOpenFlow(userId);
//				if (shopOpenFlowList != null && shopOpenFlowList.size() > 0) {
//					shopOpenFlowDO = (ShopOpenFlowDO) shopOpenFlowList.get(0);
//					shopOpenFlowDO
//							.setAuditCount(shopOpenFlowDO.getAuditCount() + 1);
//					shopOpenFlowDO
//							.setIsAgreement(ShopConstant.IS_AGREEMENT_TRUE);
//					shopOpenFlowDO.setNoPassReason("");
//					shopOpenFlowDO
//							.setAuditStatus(ShopConstant.AUDIT_STATUS_WAIT);
//					shopOpenFlowDO.setAuditProgress("");
//					shopOpenFlowDO.setGmtModified(new Date());
//					shopOpenFlowDO.setUserId(userId);
//					shopOpenFlowDO.setSellerType(sellerType);
//					shopOpenFlowDO
//							.setIsFillInfo(ShopConstant.IS_FILL_SHOP_INFO_STEP4);
//					shopOpenManager.updateShopOpenFlow(shopOpenFlowDO);
//				} else {
//					shopOpenFlowDO = new ShopOpenFlowDO();
//					shopOpenFlowDO.setUserId(userId);
//					shopOpenFlowDO.setConfiguration("USER_NAME="+nickname);
//					shopOpenFlowDO
//							.setIsAgreement(ShopConstant.IS_AGREEMENT_TRUE);
//					shopOpenFlowDO.setSellerType(sellerType);
//					shopOpenFlowDO.setAuditCount(1);
//					shopOpenFlowDO.setAuditProgress("");
//					shopOpenFlowDO.setAuditStatus(ShopConstant.AUDIT_STATUS_WAIT);
//					//shopOpenFlowDO.setConfiguration("");
//					shopOpenFlowDO.setIsBlack(0);
//					shopOpenFlowDO.setIsKa(ShopConstant.IS_KA_NO);
//					shopOpenFlowDO.setIsOnlineAuditEnd(0);
//					shopOpenFlowDO.setIsPostalAuditEnd(0);
//					shopOpenFlowDO.setNoPassReason("");
//					shopOpenFlowDO.setReviewer("");
//					shopOpenFlowDO.setGmtCreate(new Date());
//					shopOpenFlowDO.setGmtModified(new Date());
//					shopOpenFlowDO
//							.setIsFillInfo(ShopConstant.IS_FILL_SHOP_INFO_STEP4);
//					shopOpenManager.agreement(shopOpenFlowDO);
//				}
//				PinjuCookieManager.clearShop4();
//				return "SHOP_OPEN_BEGIN";
//			}
//		} catch (ManagerException e) {
//			log.error(e.getMessage());
		}catch (ParseException e) {
		}
		return "success";
	}
	
	/**
	 * 设置品牌旗舰店cookie
	 * @param step 步骤
	 */
//	private void setShopBusinessCookie(Integer step){
//		try {
//			//品牌旗舰店第一步
//			if (step.equals(ShopConstant.IS_FILL_SHOP_INFO_NO)) {
//				//获取cookie
//				String cookieString = PinjuCookieManager.getShop1();
//				log.error(cookieString);
//				//如果有cookie
//				if (cookieString != null && cookieString.length() > 0) {
//					//如果shopBusinessInfoDO为空则初始化
//					if (shopBusinessInfoDO == null) {
//						shopBusinessInfoDO = new ShopBusinessInfoDO();
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getName())) {
//						String name = getCookieParamValue(cookieString, "name");
//						if (!EmptyUtil.isBlank(name)) {
//							shopBusinessInfoDO.setName(URLDecoder.decode(name, "UTF-8"));
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopNature())) {
//						shopBusinessInfoDO.setShopNature(getCookieParamValue(cookieString, "shopNature"));
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopCategory())) {
//						shopBusinessInfoDO.setShopCategory(getCookieParamValue(cookieString, "shopCategory"));
//					}
//				}
//
//			}
//			//品牌旗舰店第二步
//			else if (step.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP1)) {
//				String cookieString = PinjuCookieManager.getShop2();
//				log.error(cookieString);
//				if (cookieString != null && cookieString.length() > 0) {
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getProvince())) {
//						String province = getCookieParamValue(cookieString, "province");
//						if (!EmptyUtil.isBlank(province)) {
//							shopBusinessInfoDO.setProvince(URLDecoder.decode(province, "UTF-8"));
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getCity())) {
//						String city = getCookieParamValue(cookieString, "city");
//						if (!EmptyUtil.isBlank(city)) {
//							shopBusinessInfoDO.setCity(URLDecoder.decode(city, "UTF-8"));
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getEnterpriseName())) {
//						String enterpriseName = getCookieParamValue(cookieString, "enterpriseName");
//						if (!EmptyUtil.isBlank(enterpriseName)) {
//							shopBusinessInfoDO.setEnterpriseName(URLDecoder.decode(enterpriseName, "UTF-8"));
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getBusinessLicenseNumber())) {
//						shopBusinessInfoDO.setBusinessLicenseNumber(getCookieParamValue(cookieString,
//								"businessLicenseNumber"));
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getOrganizationCodeNumber())) {
//						shopBusinessInfoDO.setOrganizationCodeNumber(getCookieParamValue(cookieString,
//								"organizationCodeNumber"));
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getBusiness())) {
//						String business = getCookieParamValue(cookieString, "business");
//						if (!EmptyUtil.isBlank(business)) {
//							shopBusinessInfoDO.setBusiness(URLDecoder.decode(business, "UTF-8"));
//						}
//					}
//					if (shopBusinessInfoDO.getBusinessLicenseEndDate() == null) {
//						String dateString = getCookieParamValue(cookieString, "businessLicenseEndDate");
//						if (!EmptyUtil.isBlank(dateString)) {
//							DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//							try {
//								shopBusinessInfoDO.setBusinessLicenseEndDate(format1.parse(dateString));
//							} catch (ParseException e) {
//								log.error(e);
//							}
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getLegalName())) {
//						String legalNameString = getCookieParamValue(cookieString, "legalName");
//						if (!EmptyUtil.isBlank(legalNameString)) {
//							shopBusinessInfoDO.setLegalName(URLDecoder.decode(legalNameString, "UTF-8"));
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getRegistAddress())) {
//						String registAddress = getCookieParamValue(cookieString, "registAddress");
//						if (!EmptyUtil.isBlank(registAddress)) {
//							shopBusinessInfoDO.setRegistAddress(URLDecoder.decode(registAddress, "UTF-8"));
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getOuterShopAddressUrl())) {
//						String shopAddressUrl = getCookieParamValue(cookieString, "shopAddressUrl");
//						if (!EmptyUtil.isBlank(shopAddressUrl)) {
//							shopBusinessInfoDO.setOuterShopAddressUrl(URLDecoder.decode(shopAddressUrl, "UTF-8"));
//						}
//					}
//				}
//			} 
//			//品牌旗舰店第三步
//			else if (step.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP2)) {
//				String cookieString = PinjuCookieManager.getShop3();
//				log.error(cookieString);
//				if (cookieString != null && cookieString.length() > 0) {
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getTrademarkNumber())) {
//						String trademarkNumber = getCookieParamValue(cookieString, "trademarkNumber");
//						if (!EmptyUtil.isBlank(trademarkNumber) && !trademarkNumber.equals("")) {
//							shopBusinessInfoDO.setTrademarkNumber(URLDecoder.decode(trademarkNumber, "UTF-8"));
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getBrandName())) {
//						String brandName = getCookieParamValue(cookieString, "brandName");
//						if (!EmptyUtil.isBlank(brandName) && !brandName.equals("")) {
//							shopBusinessInfoDO.setBrandName(URLDecoder.decode(brandName, "UTF-8"));
//						}
//					}
//				}
//			} 
//			//品牌旗舰店第四步
//			else if (step.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP3)) {
//				String cookieString = PinjuCookieManager.getShop4();
//				log.error(cookieString);
//				if (cookieString != null && cookieString.length() > 0) {
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerName())) {
//						String shopManagerName = getCookieParamValue(cookieString, "shopManagerName");
//						if (!EmptyUtil.isBlank(shopManagerName)) {
//							shopBusinessInfoDO.setShopManagerName(URLDecoder.decode(shopManagerName, "UTF-8"));
//						}
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerTelephone())) {
//						shopBusinessInfoDO.setShopManagerTelephone(getCookieParamValue(cookieString,
//								"shopManagerTelephone"));
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerMobile())) {
//						shopBusinessInfoDO.setShopManagerMobile(getCookieParamValue(cookieString, "shopManagerMobile"));
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerEmail())) {
//						shopBusinessInfoDO.setShopManagerEmail(getCookieParamValue(cookieString, "shopManagerEmail"));
//					}
//					if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerFax())) {
//						shopBusinessInfoDO.setShopManagerFax(getCookieParamValue(cookieString, "shopManagerFax"));
//					}
//				}
//
//			}
//		} catch (UnsupportedEncodingException e) {
//			log.error(e);
//		}
//	}
	
	/**
	 * 设置普通店cookie
	 */
//	private void setShopCustomerCookie(){
//		try {
//			String cookieString = PinjuCookieManager.getShopC();
//			log.error(cookieString);
//			if (cookieString != null && cookieString.length() > 0) {
//				if (shopCustomerInfoDO == null) {
//					shopCustomerInfoDO = new ShopCustomerInfoDO();
//				}
//				if (EmptyUtil.isBlank(shopCustomerInfoDO.getName())) {
//					String name = getCookieParamValue(cookieString, "name");
//					if (!EmptyUtil.isBlank(name)) {
//						shopCustomerInfoDO.setName(URLDecoder.decode(name, "UTF-8"));
//					}
//				}
//				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopNature())) {
//					shopCustomerInfoDO.setShopNature(getCookieParamValue(cookieString, "shopNature"));
//				}
//				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopCategory())) {
//					shopCustomerInfoDO.setShopCategory(getCookieParamValue(cookieString, "shopCategory"));
//				}
//				if(EmptyUtil.isBlank(shopCustomerInfoDO.getOuterShopAddressUrl())){
//					String shopAddressUrl = getCookieParamValue(cookieString, "shopAddressUrl");
//					if(!EmptyUtil.isBlank(shopAddressUrl)){
//						shopCustomerInfoDO.setOuterShopAddressUrl(URLDecoder.decode(shopAddressUrl, "UTF-8"));
//					}
//				}
//				if(EmptyUtil.isBlank(shopCustomerInfoDO.getProvince())){
//					String province = getCookieParamValue(cookieString, "province");
//					if(!EmptyUtil.isBlank(province)){
//						shopCustomerInfoDO.setProvince(URLDecoder.decode(province, "UTF-8"));
//					}
//				}
//				if(EmptyUtil.isBlank(shopCustomerInfoDO.getCity())){
//					String city = getCookieParamValue(cookieString, "city");
//					if(!EmptyUtil.isBlank(city)){
//						shopCustomerInfoDO.setCity(URLDecoder.decode(city, "UTF-8"));
//					}
//				}
//			}
//		} catch (UnsupportedEncodingException e) {
//			log.error(e);
//		}
//	}
	
	private String getCookieParamValue(String cookieString, String key){
		String splitCookie[] = cookieString.split(",");
		Map<String, String> map = new HashMap<String, String>(splitCookie.length);
		for(int i=0;i<splitCookie.length;i++){
			String keyValue[] = splitCookie[i].split("=");
			map.put(keyValue[0], keyValue.length > 1 ? keyValue[1] : "");
		}
		log.error(map);
		return map.get(key);
	}
	
	/**
	 * 去交保证金(跳到收银台)
	 * @return error: shopOpenCash.ftl
	 * @return success: /orderPay tenDirectSendOrder
	 */
	public String payMargin(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		try {
			//保存保证金信息
			long userId = queryUserId();
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, null);
			categoryId = Long.parseLong(shopInfoDO.getShopCategory());
			Integer exchangePriceInt = categoryMarginManager.getItemMargin(categoryId, Integer.parseInt(shopInfoDO.getSellerType()));
			String exchangePriceString= new Money(Long.valueOf(exchangePriceInt.toString())).getAmount().toString();
			Object object = shopOpenManager.saveExchangeInfo(userId, exchangeMargin, exchangePriceString);
			if(object == null){
				errorMessage = "缴纳保证金出错";
				return "error";
			}
			/****** 缴纳保证金接口 Add By ShiXing@2011.08.18 ******/
			//exchangePrice = new Money(Long.valueOf(exchangePriceInt.toString())).getAmount().toString();
			Result result = marginAO.payMargin(userId);
			if (!result.isSuccess()) {
				errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode());
				return "error";
			}
			DirectPayParamDO directPayParamDO = (DirectPayParamDO)result.getModel("directPayParamDO");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("directPayParamDO", directPayParamDO);
			ActionContext.getContext().setParameters(paramMap);
			/****** ShiXing End! ******/
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 获取店铺信息填写页 (3个页面都调用此方法)  --- 2.0 新
	 * @return statusError: iWillOpenShopAction.htm
	 * @return fillCustomerBaseInfo: shopOpenFillCustomerBaseInfo.ftl
	 * @return fillCustomerUploadInfo: shopOpenFillCustomerUploadInfo.ftl
	 * @return fillBusinessBaseInfo: shopOpenFillBusinessBaseInfo.ftl
	 * @return fillBusinessUploadInfo: shopOpenFillBusinessUploadInfo.ftl
	 * @return fillBusinessBrandInfo: shopOpenFillBusinessBrandInfo.ftl
	 * @return fillIShopBaseInfo: shopOpenFillIShopBaseInfo.ftl
	 * @return fillIShopUploadInfo: shopOpenFillIShopUploadInfo.ftl
	 */
	public String queryShopInfo(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		Long userId = queryUserId();
		shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
		if (shopFlowInfoDO != null
				&& shopFlowInfoDO.getAuditStatus() != null
				&& (shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_OPEN_END) 
						|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
						|| shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_WAIT))
				) {
			return "statusError";
		}
		//初始化值
		if(fillType == null){
			fillType = 3;
		}
		if(fillType.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP1)){
			initOutShopInfoParam();
			initBaseParam();
			shopCategoryList = shopShowInfoAO.initShopCategoryList();
		}
		//获取店铺数据
		sellerType = Integer.parseInt(shopFlowInfoDO.getSellerType());
		//普通店数据
		if(sellerType.equals(ShopConstant.SELLER_TYPE_C)){
			List<ShopCustomerInfoDO> shopInfoList = shopOpenAO.queryShopCustomerInfo(userId);
			if(shopInfoList != null && shopInfoList.size() > 0){
				shopCustomerInfoDO = shopInfoList.get(0);
			}
			//cookie
			setShopCustomerBaseCookie();
			if (fillType.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP1)) {
				return "fillCustomerBaseInfo";
			} else if (fillType.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP2)) {
				return "fillCustomerUploadInfo";
			} 
		//品牌旗舰店数据
		}else if(sellerType.equals(ShopConstant.SELLER_TYPE_B) || sellerType.equals(ShopConstant.SELLER_TYPE_B2)){
			List<ShopBusinessInfoDO> shopInfoList = shopOpenAO.queryShopBusinessInfo(userId);
			if(shopInfoList != null && shopInfoList.size() > 0){
				shopBusinessInfoDO = shopInfoList.get(0);
			}
			setShopBusinessBaseCookie();
			setShopBrandCookie();
			if (fillType.equals(ShopConstant
					.IS_FILL_SHOP_INFO_STEP1)) {
				return "fillBusinessBaseInfo";
			} else if (fillType.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP2)) {
				return "fillBusinessUploadInfo";
			} else if (fillType.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP3)) {
				//如果是新添加品牌信息则品牌相关数据设置为空字符串
				if(isNewBrand != null && isNewBrand.intValue() == 1){
					shopBusinessInfoDO.setBrandName("");
					shopBusinessInfoDO.setBrandEnglishName("");
					shopBusinessInfoDO.setBrandStory("");
					shopBusinessInfoDO.setTrademarkNumber("");
				}
				int newBrandSeq = 0;
				String brandNameString = shopBusinessInfoDO.getBrandName();
				if(brandNameString != null && brandNameString.length() > 0){
					newBrandSeq = brandNameString.split("@!@").length - 1;
				}
				//品牌个数
				brandSeq = newBrandSeq;
				//当前品牌
				if(currentBrand == null){
					currentBrand = 0;
				}
				if(brandSeq != null){
					String brandName = shopBusinessInfoDO.getBrandName();
					String brandEnglishName = shopBusinessInfoDO.getBrandEnglishName();
					String brandStory = shopBusinessInfoDO.getBrandStory();
					String trademarkNumber = shopBusinessInfoDO.getTrademarkNumber();
					if(brandName != null && brandName.length() > 0){
						shopBusinessInfoDO.setBrandName(brandName.split("@!@")[currentBrand]);
					}
					if(brandEnglishName != null && brandEnglishName.length() > 0){
						String []ename = brandEnglishName.split("@!@");
						if(ename.length <= currentBrand){
							shopBusinessInfoDO.setBrandEnglishName("");
						}else{
							shopBusinessInfoDO.setBrandEnglishName(brandEnglishName.split("@!@")[currentBrand].trim());
						}
						
					}
					if(brandStory != null && brandStory.length() > 0){
						shopBusinessInfoDO.setBrandStory(brandStory.split("@!@")[currentBrand]);
					}
					if(trademarkNumber != null && trademarkNumber.length() > 0){
						shopBusinessInfoDO.setTrademarkNumber(trademarkNumber.split("@!@")[currentBrand]);
					}
				}else{
					brandSeq = 0;
				}
				return "fillBusinessBrandInfo";
			}
		//i小铺信息
		}else if(sellerType.equals(ShopConstant.SELLER_TYPE_IShop)){
			List<ShopIshopInfoDO> shopInfoList = shopOpenAO.queryShopIShopnfo(userId);
			if(shopInfoList != null && shopInfoList.size() > 0){
				shopIshopInfoDO = shopInfoList.get(0);
			}
			setShopIShopBaseCookie();
			if (fillType.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP1)) {
				return "fillIShopBaseInfo";
			} else if (fillType.equals(ShopConstant.IS_FILL_SHOP_INFO_STEP2)) {
				return "fillIShopUploadInfo";
			} 
		}
		return "error";
	}
	
	/**
	 * 设置品牌旗舰店base cookie
	 */
	private void setShopBusinessBaseCookie(){
		try {
			// 获取基本信息cookie
			String cookieString = PinjuCookieManager.getShop1();
			log.info(cookieString);
			// 如果有cookie
			if (cookieString != null && cookieString.length() > 0) {
				//店铺信息
				// 如果shopBusinessInfoDO为空则初始化
				if (shopBusinessInfoDO == null) {
					shopBusinessInfoDO = new ShopBusinessInfoDO();
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getName())) {
					String name = getCookieParamValue(cookieString, "name");
					if (!EmptyUtil.isBlank(name)) {
						shopBusinessInfoDO.setName(URLDecoder.decode(name, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopNature())) {
					shopBusinessInfoDO.setShopNature(getCookieParamValue(cookieString, "shopNature"));
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopCategory())) {
					shopBusinessInfoDO.setShopCategory(getCookieParamValue(cookieString, "shopCategory"));
				}
				//企业信息

				if (EmptyUtil.isBlank(shopBusinessInfoDO.getProvince())) {
					String province = getCookieParamValue(cookieString, "province");
					if (!EmptyUtil.isBlank(province)) {
						shopBusinessInfoDO.setProvince(URLDecoder.decode(province, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getCity())) {
					String city = getCookieParamValue(cookieString, "city");
					if (!EmptyUtil.isBlank(city)) {
						shopBusinessInfoDO.setCity(URLDecoder.decode(city, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getEnterpriseName())) {
					String enterpriseName = getCookieParamValue(cookieString, "enterpriseName");
					if (!EmptyUtil.isBlank(enterpriseName)) {
						shopBusinessInfoDO.setEnterpriseName(URLDecoder.decode(enterpriseName, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getBusinessLicenseNumber())) {
					shopBusinessInfoDO.setBusinessLicenseNumber(getCookieParamValue(cookieString,
							"businessLicenseNumber"));
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getOrganizationCodeNumber())) {
					shopBusinessInfoDO.setOrganizationCodeNumber(getCookieParamValue(cookieString,
							"organizationCodeNumber"));
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getBusiness())) {
					String business = getCookieParamValue(cookieString, "business");
					if (!EmptyUtil.isBlank(business)) {
						shopBusinessInfoDO.setBusiness(URLDecoder.decode(business, "UTF-8"));
					}
				}
				if (shopBusinessInfoDO.getBusinessLicenseEndDate() == null) {
					String dateString = getCookieParamValue(cookieString, "businessLicenseEndDate");
					if (!EmptyUtil.isBlank(dateString)) {
						DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
						try {
							shopBusinessInfoDO.setBusinessLicenseEndDate(format1.parse(dateString));
						} catch (ParseException e) {
							log.error(e);
						}
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getLegalName())) {
					String legalNameString = getCookieParamValue(cookieString, "legalName");
					if (!EmptyUtil.isBlank(legalNameString)) {
						shopBusinessInfoDO.setLegalName(URLDecoder.decode(legalNameString, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getRegistAddress())) {
					String registAddress = getCookieParamValue(cookieString, "registAddress");
					if (!EmptyUtil.isBlank(registAddress)) {
						shopBusinessInfoDO.setRegistAddress(URLDecoder.decode(registAddress, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getOuterShopAddressUrl())) {
					String shopAddressUrl = getCookieParamValue(cookieString, "shopAddressUrl");
					if (!EmptyUtil.isBlank(shopAddressUrl)) {
						shopBusinessInfoDO.setOuterShopAddressUrl(URLDecoder.decode(shopAddressUrl, "UTF-8"));
					}
				}
				//联系人信息
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerName())) {
					String shopManagerName = getCookieParamValue(cookieString, "shopManagerName");
					if (!EmptyUtil.isBlank(shopManagerName)) {
						shopBusinessInfoDO.setShopManagerName(URLDecoder.decode(shopManagerName, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerTelephone())) {
					shopBusinessInfoDO
							.setShopManagerTelephone(getCookieParamValue(cookieString, "shopManagerTelephone"));
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerMobile())) {
					shopBusinessInfoDO.setShopManagerMobile(getCookieParamValue(cookieString, "shopManagerMobile"));
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerEmail())) {
					shopBusinessInfoDO.setShopManagerEmail(getCookieParamValue(cookieString, "shopManagerEmail"));
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getShopManagerFax())) {
					shopBusinessInfoDO.setShopManagerFax(getCookieParamValue(cookieString, "shopManagerFax"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
	}
	
	/**
	 * 设置品牌旗舰店品牌cookie
	 */
	private void setShopBrandCookie(){
		try {
			String cookieString = PinjuCookieManager.getShop2();
			log.info(cookieString);
			if (cookieString != null && cookieString.length() > 0) {
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getTrademarkNumber())) {
					String trademarkNumber = getCookieParamValue(cookieString, "trademarkNumber");
					if (!EmptyUtil.isBlank(trademarkNumber) && !trademarkNumber.equals("")) {
						shopBusinessInfoDO.setTrademarkNumber(URLDecoder.decode(trademarkNumber, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopBusinessInfoDO.getBrandName())) {
					String brandName = getCookieParamValue(cookieString, "brandName");
					if (!EmptyUtil.isBlank(brandName) && !brandName.equals("")) {
						shopBusinessInfoDO.setBrandName(URLDecoder.decode(brandName, "UTF-8"));
					}
				}
			}
		
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
	}
	
	/**
	 * 设置普通店cookie --2.0新
	 */
	private void setShopCustomerBaseCookie(){
		try {
			// 获取基本信息cookie
			String cookieString = PinjuCookieManager.getShop1();
			log.info(cookieString);
			// 如果有cookie
			if (cookieString != null && cookieString.length() > 0) {
				//店铺信息
				// 如果shopCustomerInfoDO为空则初始化
				if (shopCustomerInfoDO == null) {
					shopCustomerInfoDO = new ShopCustomerInfoDO();
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getName())) {
					String name = getCookieParamValue(cookieString, "name");
					if (!EmptyUtil.isBlank(name)) {
						shopCustomerInfoDO.setName(URLDecoder.decode(name, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopNature())) {
					shopCustomerInfoDO.setShopNature(getCookieParamValue(cookieString, "shopNature"));
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopCategory())) {
					shopCustomerInfoDO.setShopCategory(getCookieParamValue(cookieString, "shopCategory"));
				}
				//企业信息

				if (EmptyUtil.isBlank(shopCustomerInfoDO.getProvince())) {
					String province = getCookieParamValue(cookieString, "province");
					if (!EmptyUtil.isBlank(province)) {
						shopCustomerInfoDO.setProvince(URLDecoder.decode(province, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getCity())) {
					String city = getCookieParamValue(cookieString, "city");
					if (!EmptyUtil.isBlank(city)) {
						shopCustomerInfoDO.setCity(URLDecoder.decode(city, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getEnterpriseName())) {
					String enterpriseName = getCookieParamValue(cookieString, "enterpriseName");
					if (!EmptyUtil.isBlank(enterpriseName)) {
						shopCustomerInfoDO.setEnterpriseName(URLDecoder.decode(enterpriseName, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getBusinessLicenseNumber())) {
					shopCustomerInfoDO.setBusinessLicenseNumber(getCookieParamValue(cookieString,
							"businessLicenseNumber"));
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getOrganizationCodeNumber())) {
					shopCustomerInfoDO.setOrganizationCodeNumber(getCookieParamValue(cookieString,
							"organizationCodeNumber"));
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getBusiness())) {
					String business = getCookieParamValue(cookieString, "business");
					if (!EmptyUtil.isBlank(business)) {
						shopCustomerInfoDO.setBusiness(URLDecoder.decode(business, "UTF-8"));
					}
				}
				if (shopCustomerInfoDO.getBusinessLicenseEndDate() == null) {
					String dateString = getCookieParamValue(cookieString, "businessLicenseEndDate");
					if (!EmptyUtil.isBlank(dateString)) {
						DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
						try {
							shopCustomerInfoDO.setBusinessLicenseEndDate(format1.parse(dateString));
						} catch (ParseException e) {
							log.error(e);
						}
					}
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getLegalName())) {
					String legalNameString = getCookieParamValue(cookieString, "legalName");
					if (!EmptyUtil.isBlank(legalNameString)) {
						shopCustomerInfoDO.setLegalName(URLDecoder.decode(legalNameString, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getRegistAddress())) {
					String registAddress = getCookieParamValue(cookieString, "registAddress");
					if (!EmptyUtil.isBlank(registAddress)) {
						shopCustomerInfoDO.setRegistAddress(URLDecoder.decode(registAddress, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getOuterShopAddressUrl())) {
					String shopAddressUrl = getCookieParamValue(cookieString, "shopAddressUrl");
					if (!EmptyUtil.isBlank(shopAddressUrl)) {
						shopCustomerInfoDO.setOuterShopAddressUrl(URLDecoder.decode(shopAddressUrl, "UTF-8"));
					}
				}
				//联系人信息
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopManagerName())) {
					String shopManagerName = getCookieParamValue(cookieString, "shopManagerName");
					if (!EmptyUtil.isBlank(shopManagerName)) {
						shopCustomerInfoDO.setShopManagerName(URLDecoder.decode(shopManagerName, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopManagerTelephone())) {
					shopCustomerInfoDO
							.setShopManagerTelephone(getCookieParamValue(cookieString, "shopManagerTelephone"));
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopManagerMobile())) {
					shopCustomerInfoDO.setShopManagerMobile(getCookieParamValue(cookieString, "shopManagerMobile"));
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopManagerEmail())) {
					shopCustomerInfoDO.setShopManagerEmail(getCookieParamValue(cookieString, "shopManagerEmail"));
				}
				if (EmptyUtil.isBlank(shopCustomerInfoDO.getShopManagerFax())) {
					shopCustomerInfoDO.setShopManagerFax(getCookieParamValue(cookieString, "shopManagerFax"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
	
	}



	/**
	 * 设置i小铺cookie --2.0新
	 */
	private void setShopIShopBaseCookie(){
		try {
			// 获取基本信息cookie
			String cookieString = PinjuCookieManager.getShop1();
			log.info(cookieString);
			// 如果有cookie
			if (cookieString != null && cookieString.length() > 0) {
				//店铺信息
				// 如果shopBusinessInfoDO为空则初始化
				if (shopIshopInfoDO == null) {
					shopIshopInfoDO = new ShopIshopInfoDO();
				}
				if (EmptyUtil.isBlank(shopIshopInfoDO.getName())) {
					String name = getCookieParamValue(cookieString, "name");
					if (!EmptyUtil.isBlank(name)) {
						shopIshopInfoDO.setName(URLDecoder.decode(name, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopIshopInfoDO.getShopNature())) {
					shopIshopInfoDO.setShopNature(getCookieParamValue(cookieString, "shopNature"));
				}
				if (EmptyUtil.isBlank(shopIshopInfoDO.getShopCategory())) {
					shopIshopInfoDO.setShopCategory(getCookieParamValue(cookieString, "shopCategory"));
				}
				//联系人信息
				if (EmptyUtil.isBlank(shopIshopInfoDO.getShopManagerName())) {
					String shopManagerName = getCookieParamValue(cookieString, "shopManagerName");
					if (!EmptyUtil.isBlank(shopManagerName)) {
						shopIshopInfoDO.setShopManagerName(URLDecoder.decode(shopManagerName, "UTF-8"));
					}
				}
				if (EmptyUtil.isBlank(shopIshopInfoDO.getShopManagerTelephone())) {
					shopIshopInfoDO
							.setShopManagerTelephone(getCookieParamValue(cookieString, "shopManagerTelephone"));
				}
				if (EmptyUtil.isBlank(shopIshopInfoDO.getShopManagerMobile())) {
					shopIshopInfoDO.setShopManagerMobile(getCookieParamValue(cookieString, "shopManagerMobile"));
				}
				if (EmptyUtil.isBlank(shopIshopInfoDO.getShopManagerEmail())) {
					shopIshopInfoDO.setShopManagerEmail(getCookieParamValue(cookieString, "shopManagerEmail"));
				}
				if (EmptyUtil.isBlank(shopIshopInfoDO.getShopManagerFax())) {
					shopIshopInfoDO.setShopManagerFax(getCookieParamValue(cookieString, "shopManagerFax"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
	
	}
	
	/**
	 * 验证是否缴纳保证金成功.成功则跳转到卖家首页,否则停留在本页面 --2.0新
	 * @return
	 */
	public String checkIsPayMarginSuccess(){
		//MarginManager  getMarginSellerDOBySellerId 
		Long sellerId = queryUserId();
		try {
			//调用石兴的接口验证
			MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(sellerId);
			//marginSellerDO不为空
			if(marginSellerDO != null){
				return "redirectSellerIndex";
			}
			return "currentPage";
		} catch (ManagerException e) {
			log.error("验证是否缴纳保证金成功",e);
		}
		return "currentPage";
	}
	
	/**
	 * 显示开店流程首页 --2.0新
	 * @return shopOpenEnd: /my/sell.htm
	 * @return openShopIndex1: shopOpentep2.ftl
	 * @return openShopIndex2: shopOpentep3.ftl
	 * @return openShopIndex3: shopOpentep4.ftl
	 * @return openShopIndex4: shopOpentep5.ftl
	 * @return openShopIndex:  shopOpentep1.ftl
	 **/
	public String iWillOpenShop(){
		Long userId =  queryUserId();
		//验证是否缴纳了保证金
		try {
			//补老数据初始化信息
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, null);
			if(shopInfoDO != null){
				Long sellerType = Long.parseLong(shopInfoDO.getSellerType());
				boolean booleanC = true;
				boolean booleanB = true;
				boolean booleanIShop = true;
				booleanC = shopShowInfoManager.checkHasCustomerInfo(userId);
				booleanIShop = shopShowInfoManager.checkHasIShopInfo(userId);
				booleanB = shopShowInfoManager.checkHasBusinessInfo(userId);
				boolean b2 = true;
				if(!booleanC && !booleanB && !booleanIShop){
					if (sellerType.equals(ShopConstant.SELLER_TYPE_C)) {
						ShopCustomerInfoDO shopCustomerInfo = new ShopCustomerInfoDO();
						shopCustomerInfo.setUserId(userId);
						shopCustomerInfo.setSellerType(String.valueOf(sellerType));
						b2 = shopShowInfoManager.insertCustomerInfo(shopCustomerInfo);
						if (!b2) {
							errorMessage = "显示开店流程首页出错!";
							return "error";
						}
					} else if (sellerType.equals(ShopConstant.SELLER_TYPE_IShop)) {
						ShopIshopInfoDO shopIshopInfo = new ShopIshopInfoDO();
						shopIshopInfo.setUserId(userId);
						shopIshopInfo.setSellerType(String.valueOf(sellerType));
						b2 = shopShowInfoManager.insertIShopInfo(shopIshopInfo);
						if (!b2) {
							errorMessage = "显示开店流程首页出错!";
							return "error";
						}
					} else {
						ShopBusinessInfoDO shopBusinessInfo = new ShopBusinessInfoDO();
						shopBusinessInfo.setUserId(userId);
						shopBusinessInfo.setSellerType(String.valueOf(sellerType));
						b2 = shopShowInfoManager.insertBusinessInfo(shopBusinessInfo);
						if (!b2) {
							errorMessage = "显示开店流程首页出错!";
							return "error";
						}
					}
				}
				
				List<ShopOpenFlowDO> shopOpenFlowDOList  = shopOpenManager.queryShopOpenFlow(userId);
				if(shopOpenFlowDOList == null || shopOpenFlowDOList.size() == 0){
					shopOpenFlowDO=new ShopOpenFlowDO();
					shopOpenFlowDO.setUserId(userId);
					shopOpenFlowDO.setIsAgreement(ShopConstant.IS_AGREEMENT_TRUE);
					shopOpenFlowDO.setAuditStatus(ShopConstant.AUDIT_STATUS_NOT_APPLY);
					shopOpenFlowDO.setGmtCreate(new Date());
					shopOpenFlowDO.setIsFillInfo(ShopConstant.IS_FILL_SHOP_INFO_NO);
					shopOpenFlowDO.setSellerType(Integer.parseInt(sellerType.toString()));
					shopOpenFlowDO.setConfiguration("USER_NAME="+queryNickName());
					shopOpenFlowDO.setAuditCount(0);
					shopOpenFlowDO.setIsKa(ShopConstant.IS_KA_NO);
					shopOpenFlowDO.setIsBlack(ShopConstant.BLACK_FALSE);
					shopOpenManager.saveShopOpenFlow(shopOpenFlowDO);
				}
			}
			
			
			//获取相关主要流程进展情况
			shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
			if(shopFlowInfoDO != null){
				if(shopFlowInfoDO.getIsAccountSet() != null && !shopFlowInfoDO.getIsAccountSet().equals(ShopConstant.IS_ACCOUNT_SET_COMPLETE)){
					
				}else{
					MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(userId);
					// 判断保证金,如果为null不成功
					//marginSellerDO = new MarginSellerDO();
					if (marginSellerDO != null) {
						return "shopOpenEnd";
					}
					
					if(shopInfoDO != null && shopInfoDO.getExchangeMargin() != null && shopInfoDO.getShopInfoConfig("MARGIN_PRICE") != null){
						log.warn("保证金验证没有通过,进入开店流程最后一步....");
						return "shopOpenLast";
					}
				}
			}
			
			//return "shopOpenEnd";
		} catch (ManagerException e) {
			log.error("显示开店流程首页,[marginManager.getMarginSellerDOBySellerId]出错!",e);
		}
			
		
		//获取error信息
		if(ActionContext.getContext().getParameters() != null){
			Map<String, Object> map = ActionContext.getContext().getParameters();
			if(map.get("errorMessage") != null && map.get("errorMessage").toString().length() > 0){
				setErrorMessage(map.get("errorMessage").toString());
				map.remove("errorMessage");
				ActionContext.getContext().setParameters(map);
			}
		}
		if(null!=shopFlowInfoDO){
			if (null != shopFlowInfoDO.getSellerType() && !"".equals(shopFlowInfoDO.getSellerType())) {
				// ((账户设定不为null && (账户设定未开始 || 账户设定未完成))
				// && (填写信息不为null && (填写信息未开始 || 写信息未完成)))
				if ((null != shopFlowInfoDO.getIsAccountSet() && (ShopConstant.IS_ACCOUNT_SET_NOT_BEGIN
						.equals(shopFlowInfoDO.getIsAccountSet()) || ShopConstant.IS_ACCOUNT_SET_NOT_COMPLETE
						.equals(shopFlowInfoDO.getIsAccountSet())))
						&& (null != shopFlowInfoDO.getIsFillComplete() && (ShopConstant.IS_FILL_COMPLETE_NOT_BEGIN
								.equals(shopFlowInfoDO.getIsFillComplete()) || ShopConstant.IS_FILL_COMPLETE_NOT_COMPLETE
								.equals(shopFlowInfoDO.getIsFillComplete().intValue())))) {
					return "openShopIndex1";
				}
				// ((账户设定不为null && 账户设定已完成)
				// && (填写信息不为null && (填写信息未开始 || 写信息未完成)))
				else if ((null != shopFlowInfoDO.getIsAccountSet() && ShopConstant.IS_ACCOUNT_SET_COMPLETE
						.equals(shopFlowInfoDO.getIsAccountSet().intValue()))
						&& (null != shopFlowInfoDO.getIsFillComplete() && (ShopConstant.IS_FILL_COMPLETE_NOT_BEGIN
								.equals(shopFlowInfoDO.getIsFillComplete().intValue()) || ShopConstant.IS_FILL_COMPLETE_NOT_COMPLETE
								.equals(shopFlowInfoDO.getIsFillComplete().intValue())))) {
					return "openShopIndex2";
				} 
				//((填写信息不为null && 填写信息已完成) && (账户设定不为null && (账户设定未完成 || 账户设定未开始)))
				else if ((null != shopFlowInfoDO.getIsFillComplete() && ShopConstant.IS_FILL_COMPLETE_COMPLETE
						.equals(shopFlowInfoDO.getIsFillComplete().intValue()))
						&& (null != shopFlowInfoDO.getIsAccountSet() && (ShopConstant.IS_ACCOUNT_SET_NOT_COMPLETE
								.equals(shopFlowInfoDO.getIsAccountSet().intValue()) || ShopConstant.IS_ACCOUNT_SET_NOT_BEGIN
								.equals(shopFlowInfoDO.getIsAccountSet().intValue())))) {
					return "openShopIndex4";
				} 
				//((账户设定不为null && 账户设定已完成) && (填写信息不为null && 填写信息已完成))
				else if ((null != shopFlowInfoDO.getIsAccountSet() && ShopConstant.IS_ACCOUNT_SET_COMPLETE
						.equals(shopFlowInfoDO.getIsAccountSet().intValue()))
						&& (null != shopFlowInfoDO.getIsFillComplete() && ShopConstant.IS_FILL_COMPLETE_COMPLETE
								.equals(shopFlowInfoDO.getIsFillComplete().intValue()))) {
					return "openShopIndex3";
				}
			}
		}
		return "openShopIndex";
	}
	
	/**
	 * 显示审核状态页面 --2.0新
	 * @return auditStatusPage: shopOpenAuditStatus.ftl
	 */
	public String showAuditStatusPage(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		Long userId =  queryUserId();
		List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
		if(list != null && list.size() > 0){
			shopOpenFlowDO = list.get(0);
			if(shopOpenFlowDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_NO)){
				String noPassReason = shopOpenFlowDO.getNoPassReason();
				if(noPassReason != null && noPassReason.length() > 0){
					String noPassReasons[] = noPassReason.split("@!@");
					shopOpenFlowDO.setNoPassReason(noPassReasons[noPassReasons.length-1]);
				}
			}
		}
		return "auditStatusPage";
	}
	
	
	
	/**
	 * 签订协议[2.0版本新增]
	 * @return openShopIndex1: iWillOpenShopAction.htm
	 * @return statusError: shopOpentep1.ftl
	 **/
	public String shopOpenSignAgreement() {
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		Long userId = queryUserId();
		if(null!=userId){
			List<ShopOpenFlowDO> list = shopOpenAO.queryShopOpenFlow(userId);
			if(null!=list && list.size()>0){
				return "openShopIndex1";
			}
			//初始化流程信息
			shopOpenFlowDO=new ShopOpenFlowDO();
			shopOpenFlowDO.setUserId(userId);
			shopOpenFlowDO.setIsAgreement(ShopConstant.IS_AGREEMENT_TRUE);
			shopOpenFlowDO.setAuditStatus(ShopConstant.AUDIT_STATUS_NOT_APPLY);
			shopOpenFlowDO.setGmtCreate(new Date());
			shopOpenFlowDO.setIsFillInfo(ShopConstant.IS_FILL_SHOP_INFO_NO);
			shopOpenFlowDO.setSellerType(sellerType);
			shopOpenFlowDO.setConfiguration("USER_NAME="+queryNickName());
			shopOpenFlowDO.setAuditCount(0);
			shopOpenFlowDO.setIsKa(ShopConstant.IS_KA_NO);
			shopOpenFlowDO.setIsBlack(ShopConstant.BLACK_FALSE);
			if(sellerType.equals(ShopConstant.SELLER_TYPE_C)){
				ShopCustomerInfoDO shopCustomerInfo=new ShopCustomerInfoDO();
				shopCustomerInfo.setUserId(userId);
				shopCustomerInfo.setNickname(queryNickName());
				shopCustomerInfo.setIsSupplier(0);
				shopCustomerInfo.setGmtCreate(new Date());
				shopCustomerInfo.setApproveStatus(ShopConstant.APPROVE_STATUS_NO);
				shopCustomerInfo.setSellerType(String.valueOf(sellerType));
				Result result=(Result)shopOpenManager.signAgreementLoadData(sellerType,shopCustomerInfo,shopOpenFlowDO);
				if(null!=result && !result.isSuccess()){
					errorMessage="签订协议初始化数据失败";
					return "statusError";	
				}
			}else if(sellerType.equals(ShopConstant.SELLER_TYPE_IShop)){
				ShopIshopInfoDO shopIshopInfo=new ShopIshopInfoDO();
				shopIshopInfo.setUserId(userId);
				shopIshopInfo.setNickname(queryNickName());
				shopIshopInfo.setIsSupplier(0);
				shopIshopInfo.setGmtCreate(new Date());
				shopIshopInfo.setApproveStatus(ShopConstant.APPROVE_STATUS_NO);
				shopIshopInfo.setSellerType(String.valueOf(sellerType));
			    Result result=(Result)shopOpenManager.signAgreementLoadData(sellerType,shopIshopInfo,shopOpenFlowDO);
			    if(null!=result && !result.isSuccess()){
					errorMessage="签订协议初始化数据失败";
					return "statusError";	
				}
			}else{
				ShopBusinessInfoDO shopBusinessInfo=new ShopBusinessInfoDO();
				shopBusinessInfo.setUserId(userId);
				shopBusinessInfo.setNickname(queryNickName());
				shopBusinessInfo.setIsSupplier(0);
				shopBusinessInfo.setGmtCreate(new Date());
				shopBusinessInfo.setApproveStatus(ShopConstant.APPROVE_STATUS_NO);
				shopBusinessInfo.setSellerType(String.valueOf(sellerType));
				Result result=(Result) shopOpenManager.signAgreementLoadData(sellerType,shopBusinessInfo,shopOpenFlowDO);
				if(null!=result && !result.isSuccess()){
					errorMessage="签订协议初始化数据失败";
					return "statusError";	
				}
			}
		}
		return "openShopIndex1";
	}
	
	/**
	 * 显示选择提交信息页 --2.0 新
	 * @return choiceFillIndex: shopOpenChoiceFillIndex.ftl
	 */
	public String choiceFillIndex(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		Long userId = queryUserId();
		//获取相关主要流程进展情况
		shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
		return "choiceFillIndex";
	}
	
	/**
	 * 重新开店 --2.0 新
	 * @return reopenShop: iWillOpenShopAction.htm
	 * @return statusError: iWillOpenShopAction.htm
	 */
	public String reopenShop(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		Long userId = queryUserId();
		ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
		if(null!=shopFlowInfoDO && !shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_NO)){
			return "statusError";
		}
		ShopOpenFlowDO shopOpenFlowDO = new ShopOpenFlowDO();
		shopOpenFlowDO.setUserId(userId);
		shopOpenFlowDO.setAuditStatus(ShopConstant.AUDIT_STATUS_NOT_APPLY);
		shopOpenFlowDO.setIsFillInfo(ShopConstant.IS_FILL_SHOP_INFO_NO);
		shopOpenFlowDO.setGmtModified(new Date());
		shopOpenAO.updateShopOpenFlow(shopOpenFlowDO);
		return "reopenShop";
	}
	
	/**
	 * 更改店铺类型【2.0新增】
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String changeSellType(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		Long userId = queryUserId();
		List list=shopOpenAO.queryShopOpenFlow(userId);
		if(null!=list && list.size()>0){
			ShopOpenFlowDO shopOpenFlowDO=(ShopOpenFlowDO) list.get(0);
			if(null!=shopOpenFlowDO && shopOpenFlowDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_NOT_APPLY)){
				Result result=(Result)shopOpenManager.deleteShopDateByUpdateSellerType(shopOpenFlowDO.getSellerType(),userId);
				if(null!=result && !result.isSuccess()){
					Map<String, Object> parameters = new HashMap<String, Object>();
					errorMessage="更改店铺类型失败";
					parameters.put("errorMessage", errorMessage);
					ActionContext.getContext().setParameters(parameters);
					return "statusError";
				}
		  }
		}
		return "changeSellType";
	}
	
	/**
	 * 最后一步,进入卖家首页-- 2.0新
	 * @return shopOpenEnd: /my/sell.htm
	 * @return error: shopOpenLast.ftl
	 */
	public String shopOpenEnd(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		Long userId = queryUserId();
		//验证是否缴纳了保证金
		try {
			MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(userId);
			//调用开店成功方法
			//判断保证金,如果为null不成功 
			//marginSellerDO = new MarginSellerDO();
				if(marginSellerDO == null){
					errorMessage = "<font color=\"red\">您的保证金尚未缴纳成功！</font><font color=\"gray\"><a href=\"/shop/showPayMarginPageAction.htm\">重新缴纳保证金</a></font><br>（如有疑问请联系客服4008-211-588）";
					return "error";
				}else{
					Result result = shopOpenManager.setShopIsOpenForMargin(userId);
					if(!result.isSuccess()){
						errorMessage = "<font color=\"red\">进入卖家首页出错！</font><br>（如有疑问请联系客服4008-211-588）";
						return "error";
					}
				}
			
		} catch (ManagerException e) {
			log.error(e);
		}
		
		return "shopOpenEnd";
	}
	
	public String showShopOpenEnd(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		log.warn("in showShopOpenEnd");
		return "showShopOpenEnd";
	}
	
	/**
	 * 进入缴纳保证金页面【2.0新】 
	 * @return showPayMarginPage: shopOpenCash.ftl
	 * @return error: error.ftl
	 * @return statusError: iWillOpenShopAction.htm
	 */
	public String showPayMarginPage(){
		if(CookieLoginInfo.getCookieLoginInfo().isAssistantAccount()){
			return MemberAuthInterceptor.accessDenied();
		}
		Long userId = queryUserId();
		ShopFlowInfoDO shopFlowInfoDO = shopOpenAO.queryFlowInfo(userId);
		if(shopFlowInfoDO == null){
			errorMessage = "进入缴纳保证金页面出错!";
			return "error";
		}
		//验证流程
		if(shopFlowInfoDO.getAuditStatus() != null 
				&& shopFlowInfoDO.getAuditStatus().equals(ShopConstant.AUDIT_STATUS_PASS)
				&& shopFlowInfoDO.getIsAccountSet() != null 
				&& shopFlowInfoDO.getIsAccountSet().equals(ShopConstant.IS_ACCOUNT_SET_COMPLETE)) {

			try {
				ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, null);
				if (shopInfoDO == null) {
					errorMessage = "进入缴纳保证金页面出错!";
					return "error";
				}
				if (shopInfoDO != null) {
					String category = shopInfoDO.getShopCategory();
					if (category == null) {
						errorMessage = "进入缴纳保证金页面出错!";
						return "error";
					}
					String sellerType = shopInfoDO.getSellerType();
					if (sellerType == null || sellerType.length() == 0) {
						sellerType = shopFlowInfoDO.getSellerType();
					}
					categoryId = Long.parseLong(category);
					int price = categoryMarginManager.getItemMargin(Long.parseLong(category), Integer
							.parseInt(shopInfoDO.getSellerType()));
					// 获取已选择的类目名称
					List<CategoryDO> categoryList = categoryCacheManager.getRootCategoryList();
					if (categoryList != null && categoryList.size() > 0) {
						shopCategoryList = new HashMap<Long, String>();
						for (int i = 0; i < categoryList.size(); i++) {
							shopCategoryList.put((categoryList.get(i)).getId(), (categoryList.get(i).getName()));
							if (categoryId != null && categoryId.equals(categoryList.get(i).getId())) {
								selectedCategory = categoryList.get(i).getName();
							}
						}
					}
					if (selectedCategory == null) {
						errorMessage = "进入缴纳保证金页面出错!";
						return "error";
					}
					// 需要缴纳的保证金
					exchangePrice = new Money(Long.valueOf(price)).getAmount().toString();
				}
			} catch (ManagerException e) {
				log.error("进入缴纳保证金页面", e);
			}
		}else{
			return "statusError";
		}
		return "showPayMarginPage";
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public MemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public int getExchangeMargin() {
		return exchangeMargin;
	}

	public void setExchangeMargin(int exchangeMargin) {
		this.exchangeMargin = exchangeMargin;
	}

	public int getFillStep() {
		return fillStep;
	}

	public void setFillStep(int fillStep) {
		this.fillStep = fillStep;
	}

	public ShopCustomerInfoDO getShopCustomerInfoDO() {
		return shopCustomerInfoDO;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}


	public void setCategoryMarginManager(CategoryMarginManager categoryMarginManager) {
		this.categoryMarginManager = categoryMarginManager;
	}


	public void setShopCustomerInfoDO(ShopCustomerInfoDO shopCustomerInfoDO) {
		this.shopCustomerInfoDO = shopCustomerInfoDO;
	}

	public ShopBusinessInfoDO getShopBusinessInfoDO() {
		return shopBusinessInfoDO;
	}

//	public void setShopBusinessInfoDO(ShopBusinessInfoDO shopBusinessInfoDO) {
//		this.shopBusinessInfoDO = shopBusinessInfoDO;
//	}

	private Integer sellerType;

	public ShopOpenFlowDO getShopOpenFlowDO() {
		return shopOpenFlowDO;
	}

	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}

	public Map<Long, String> getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(Map<Long, String> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}


	public void setShopOpenFlowDO(ShopOpenFlowDO shopOpenFlowDO) {
		this.shopOpenFlowDO = shopOpenFlowDO;
	}

	public ShopOpenAO getShopOpenAO() {
		return shopOpenAO;
	}

	public void setShopOpenAO(ShopOpenAO shopOpenAO) {
		this.shopOpenAO = shopOpenAO;
	}

	public int getSellerType() {
		return sellerType;
	}

	public void setSellerType(Integer sellerType) {
		this.sellerType = sellerType;
	}

	public String getExchangePrice() {
		return exchangePrice;
	}

	public void setExchangePrice(String exchangePrice) {
		this.exchangePrice = exchangePrice;
	}

	public int getIsHaveOuterShop() {
		return isHaveOuterShop;
	}

	public void setIsHaveOuterShop(int isHaveOuterShop) {
		this.isHaveOuterShop = isHaveOuterShop;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public FileStorageManager getFileStorageManager() {
		return fileStorageManager;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

	public File[] getMyFile() {
		return myFile;
	}

	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public String getBusinessLicenseEndDate() {
		return businessLicenseEndDate;
	}

	public void setBusinessLicenseEndDate(String businessLicenseEndDate) {
		this.businessLicenseEndDate = businessLicenseEndDate;
	}
	
	public ShopOpenManager getShopOpenManager() {
		return shopOpenManager;
	}

	public void setShopOpenManager(ShopOpenManager shopOpenManager) {
		this.shopOpenManager = shopOpenManager;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public void setMarginAO(MarginAO marginAO) {
		this.marginAO = marginAO;
	}

	public MarginAO getMarginAO() {
		return marginAO;
	}


	public void setMarginManager(MarginManager marginManager) {
		this.marginManager = marginManager;
	}


	public ShopFlowInfoDO getShopFlowInfoDO() {
		return shopFlowInfoDO;
	}


	public void setShopFlowInfoDO(ShopFlowInfoDO shopFlowInfoDO) {
		this.shopFlowInfoDO = shopFlowInfoDO;
	}
}
