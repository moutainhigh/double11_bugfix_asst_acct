package com.yuwang.pinju.web.module.shop.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.domain.item.ItemInput;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;

public class ShopImageValidateAction {
	
	
	private ShopCustomerInfoDO shopCustomerInfoDO;
	
	private ShopBusinessInfoDO shopBusinessInfoDO;
	
	private int max;
	
	private String picName;
	
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
	 * 图片表单的id
	 */
	private String id;
	
	/**
	 * 图片大小验证
	 */
	public String validateUploadImage() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			
			out = response.getWriter();
			int imagFlag = 0;
			JSONObject m = new JSONObject();
			
			File [] myFile = null;
			String myFileFileName = null;
			if(id.equals("shopLogo")){
				myFile = getShopLogo();
				myFileFileName = getShopLogoFileName();
			}else if(id.equals("businessLicense")){
				myFile = getBusinessLicense();
				myFileFileName = getBusinessLicenseFileName();
			}else if(id.equals("organizationCode")){
				myFile = getOrganizationCode();
				myFileFileName = getOrganizationCodeFileName();
			}else if(id.equals("taxPass")){
				myFile = getTaxPass();
				myFileFileName = getTaxPassFileName();
			}else if(id.equals("idCard")){
				myFile = getIdCard();
				myFileFileName = getIdCardFileName();
			}else if(id.equals("creation1")){
				myFile = getCreation1();
				myFileFileName = getCreation1FileName();
			}else if(id.equals("creation2")){
				myFile = getCreation2();
				myFileFileName = getCreation2FileName();
			}else if(id.equals("brandLogo")){
				myFile = getBrandLogo();
				myFileFileName = getBrandLogoFileName();
			}else if(id.equals("brandCertificate")){
				myFile = getBrandCertificate();
				myFileFileName = getBrandCertificateFileName();
			}else if(id.equals("qualityCertificate")){
				myFile = getQualityCertificate();
				myFileFileName = getQualityCertificateFileName();
			}
			
			if (myFile==null || myFile.length == 0) {
				imagFlag = 1;
			} else {
				String names[] = myFileFileName.split(",");
				for (int i=0;i<myFile.length;i++) {
					String name = names[i].trim();
					
					String name2 = picName.substring(picName.lastIndexOf("\\")+1,picName.length()).trim();
					if(name.equals(name2)){
						
						// 大小检验
						if (myFile[i].length() / ItemInput.FILE_SIZE_K > max) {
							imagFlag = 3;
						}
						// 类型检验
						if (!FileSecurityUtils.isImageValid(myFile[i])) {
							imagFlag = 2;
						}
					}
				}
			}
			m.put("imagFlag", imagFlag);
			out.print(m.toString());
		} catch (IOException e) {
		}
		return null;
	}


	public ShopCustomerInfoDO getShopCustomerInfoDO() {
		return shopCustomerInfoDO;
	}

	public void setShopCustomerInfoDO(ShopCustomerInfoDO shopCustomerInfoDO) {
		this.shopCustomerInfoDO = shopCustomerInfoDO;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public ShopBusinessInfoDO getShopBusinessInfoDO() {
		return shopBusinessInfoDO;
	}

	public void setShopBusinessInfoDO(ShopBusinessInfoDO shopBusinessInfoDO) {
		this.shopBusinessInfoDO = shopBusinessInfoDO;
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

	public File[] getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(File[] businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getBusinessLicenseFileName() {
		return businessLicenseFileName;
	}

	public void setBusinessLicenseFileName(String businessLicenseFileName) {
		this.businessLicenseFileName = businessLicenseFileName;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
