
package com.yuwang.pinju.core.shop.ao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;



import junit.framework.Assert;

public class TestShopOpenAO extends Assert {
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	/**
	 * 保存店铺基本信息
	 * @throws DaoException
	 */
	@Test public void testSaveShopInfo() throws DaoException{
		FileSystemXmlApplicationContext factory = new FileSystemXmlApplicationContext("/src/test/resources/applicationContext.xml");
		ShopShowInfoManager shopShowInfoManager = (ShopShowInfoManager)factory.getBean("shopShowInfoManager");
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(100000055000000l);
		List reultList;
		try {
			reultList = shopShowInfoManager.queryShopInfoByUserIdList(userIdList);
			log.info(reultList);
			log.info(reultList.size());
//			log.info(((ShopInfoAllDO)reultList.get(0)).getName());
			
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		FileSystemXmlApplicationContext factory = new FileSystemXmlApplicationContext("/src/test/resources/applicationContext.xml");
//		ShopOpenAO shopOpenAO = (ShopOpenAO)factory.getBean("shopOpenAO");
//	
//		/**
//		 * 测试插入C的店铺信息
//		 */
//		ShopCustomerInfoDO shopCustomerInfoDO = new ShopCustomerInfoDO();
//		shopCustomerInfoDO.setAddress("shanghai");
//		shopCustomerInfoDO.setApproveStatus(0);
//		shopCustomerInfoDO.setBusinessLicense("c:/abc.jpg");
//		shopCustomerInfoDO.setCity("shanghai");
//		shopCustomerInfoDO.setDescription("testestsetsetsetset");
//		shopCustomerInfoDO.setGmtCreate(new Date());
//		shopCustomerInfoDO.setGoodsSource(1);
//		shopCustomerInfoDO.setName("小屋aaa");
//		shopCustomerInfoDO.setOpenDate(new Date());
//		shopCustomerInfoDO.setOrganizationCode("c:/bcd.jpg");
//		shopCustomerInfoDO.setProvince("shanghai");
//		shopCustomerInfoDO.setSellerType(0);
//		shopCustomerInfoDO.setShopCategory("1");
//		shopCustomerInfoDO.setShopMark("c:/cde.jpg");
//		shopCustomerInfoDO.setTitle("sdfsdf");
//		shopCustomerInfoDO.setUserId(123);
//		
////		try {
////			shopOpenAO.saveShopInfo(0, shopCustomerInfoDO);
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/**
//		 * 测试更新C的店铺信息
//		 */
////		try {
////			shopOpenAO.updateShopCustomerInfo(123, shopCustomerInfoDO);
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/**
//		 * 测试查询C的店铺信息
//		 */
////		try {
////			List cList = (ArrayList)shopOpenAO.queryShopCustomerInfo(123);
////			log.info("query shop C list is ===== "+cList);
////			log.info("shop name is ====== "+ ((ShopCustomerInfoDO)cList.get(0)).getName());
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/**
//		 * 测试插入B店铺信息
//		 */
//		ShopBusinessInfoDO shopBusinessInfoDO = new ShopBusinessInfoDO();
//		shopBusinessInfoDO.setAddress("shanghai");
//		shopBusinessInfoDO.setApproveStatus(0);
//		shopBusinessInfoDO.setBusinessLicense("c:/abc.jpg");
//		shopBusinessInfoDO.setCity("shanghai");
//		shopBusinessInfoDO.setDescription("testestsetsetsetset");
//		shopBusinessInfoDO.setGmtCreate(new Date());
//		shopBusinessInfoDO.setGoodsSource(1);
//		shopBusinessInfoDO.setName("小屋bbb");
//		shopBusinessInfoDO.setOpenDate(new Date());
//		shopBusinessInfoDO.setOrganizationCode("c:/bcd.jpg");
//		shopBusinessInfoDO.setProvince("shanghai");
//		shopBusinessInfoDO.setSellerType(0);
//		shopBusinessInfoDO.setShopCategory("1");
//		shopBusinessInfoDO.setShopMark("c:/cde.jpg");
//		shopBusinessInfoDO.setTitle("sdfsdf");
//		shopBusinessInfoDO.setUserId(222);
//		shopBusinessInfoDO.setBrandCertificate("c:/123.jpg");
//		shopBusinessInfoDO.setBrandEnglishName("aaa");
//		shopBusinessInfoDO.setBrandLogo("asdf");
//		shopBusinessInfoDO.setBrandName("名字");
//		shopBusinessInfoDO.setBusiness("asdf");
//		shopBusinessInfoDO.setBusinessCertificate("asdfasdf");
//		shopBusinessInfoDO.setBusinessLicense("c:/asdf.jpg");
//		shopBusinessInfoDO.setBusinessLicenseEndDate(new Date());
//		shopBusinessInfoDO.setBusinessLicenseNumber("1212121213333");
//		shopBusinessInfoDO.setContactAddress("nanjin");
//		shopBusinessInfoDO.setContactName("dfdfdfd");
//		shopBusinessInfoDO.setContactPhone("66677788");
//		shopBusinessInfoDO.setContactPostalCode("asfasdf");
//		shopBusinessInfoDO.setEmergencyContactName("lm");
//		shopBusinessInfoDO.setEmergencyContactPhone("77788899");
//		shopBusinessInfoDO.setEnterpriseName("jj");
//		shopBusinessInfoDO.setHygieneLicense("c:/asf.jpg");
//		shopBusinessInfoDO.setLegalName("dfsadfasd");
//		shopBusinessInfoDO.setOrganizationCodeNumber("12345");
//		shopBusinessInfoDO.setProductionLicense("c:/asfsafsdf.jpg");
//		shopBusinessInfoDO.setQualityCertificate("c:/asdf.jpg");
//		shopBusinessInfoDO.setRegistAddress("asdfasdfasdfasdf");
//		shopBusinessInfoDO.setShopManagerEmail("asdf");
//		shopBusinessInfoDO.setShopManagerFax("332452345");
//		shopBusinessInfoDO.setShopManagerMobile("1213343244");
//		shopBusinessInfoDO.setShopManagerName("asdf");
//		shopBusinessInfoDO.setShopManagerTelephone("14812984");
//		shopBusinessInfoDO.setShopNature("asfasdf");
//		shopBusinessInfoDO.setTrademarkNumber("1294128340");
////		try {
////			shopOpenAO.saveShopInfo(1, shopBusinessInfoDO);
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/**
//		 * 测试更新B店铺信息
//		 */
////		try {
////			shopOpenAO.updateShopBusinessInfo(222, shopBusinessInfoDO);
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/**
//		 * 测试查询B的店铺信息
//		 */
//		try {
//			List bList = (ArrayList)shopOpenAO.queryShopBusinessInfo(100000055000000l);
//			log.info("query shop B list is ===== "+bList);
//			log.info("shop name is ====== "+ ((ShopBusinessInfoDO)bList.get(0)).getName());
//		} catch (ManagerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		
//		/**
//		 * 测试插入流程信息
//		 */
////		ShopOpenFlowDO shopOpenFlowDO = new ShopOpenFlowDO();
////		shopOpenFlowDO.setAuditCount(1);
////		shopOpenFlowDO.setAuditDate(new Date());
////		shopOpenFlowDO.setAuditProgress("asfasdfasdfasdf");
////		shopOpenFlowDO.setAuditStatus(0);
////		shopOpenFlowDO.setIsAgreement(0);
////		shopOpenFlowDO.setIsBlack(0);
////		shopOpenFlowDO.setIsFillInfo(0);
////		shopOpenFlowDO.setIsKa(0);
////		shopOpenFlowDO.setSellerType(1);
////		shopOpenFlowDO.setUserId(12345);
////		shopOpenFlowDO.setIsOnlineAuditEnd(0);
////		shopOpenFlowDO.setIsPostalAuditEnd(0);
////		shopOpenFlowDO.setIsSampleAuditEnd(0);
////		shopOpenFlowDO.setNoPassReason("asdfasdfasdfasdf");
////		shopOpenFlowDO.setGmtCreate(new Date());
////		try {
////			shopOpenAO.saveShopOpenFlow(shopOpenFlowDO);
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/**
//		 * 测试更新流程信息
//		 */
////		ShopOpenFlowDO shopOpenFlowDO = new ShopOpenFlowDO();
////		shopOpenFlowDO.setAuditStatus(1);
////		shopOpenFlowDO.setIsOnlineAuditEnd(1);
////		shopOpenFlowDO.setUserId(555);
////		shopOpenFlowDO.setNoPassReason("不通过就是不通过");
////		try {
////			shopOpenAO.updateShopOpenFlow(shopOpenFlowDO);
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/**
//		 * 测试获取流程信息
//		 */
////		try {
////			List flowList = (ArrayList)shopOpenAO.queryShopOpenFlow(222);
////			log.info("audit status is == "+((ShopOpenFlowDO)flowList.get(0)).getAuditStatus());
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/**
//		 * 测试同意服务协议
//		 */
////		try {
////			Object back = shopOpenAO.agreement(222,1);
////			log.info(back+"====================");
////		} catch (ManagerException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		
//		
//		ShopOpenFlowDO shopOpenFlowDO = new ShopOpenFlowDO();
//		shopOpenFlowDO.setUserId((int)12345);
//		shopOpenFlowDO.setIsAgreement(ShopConstant.IS_AGREEMENT_TRUE);
//		shopOpenFlowDO.setSellerType(0);
//		shopOpenFlowDO.setGmtCreate(new Date());
//		shopOpenFlowDO.setAuditCount(1);
//		shopOpenFlowDO.setAuditDate(new Date());
//		shopOpenFlowDO.setAuditProgress("");
//		shopOpenFlowDO.setAuditStatus(0);
//		shopOpenFlowDO.setConfiguration("");
//		shopOpenFlowDO.setIsBlack(0);
//		shopOpenFlowDO.setIsFillInfo(0);
//		shopOpenFlowDO.setIsKa(0);
//		shopOpenFlowDO.setIsOnlineAuditEnd(0);
//		shopOpenFlowDO.setIsPostalAuditEnd(0);
//		shopOpenFlowDO.setNoPassReason("");
//		shopOpenFlowDO.setReviewer("");
//		try {
//			Object back = shopOpenAO.agreement(shopOpenFlowDO);
//			log.info(back);
//		} catch (ManagerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}

