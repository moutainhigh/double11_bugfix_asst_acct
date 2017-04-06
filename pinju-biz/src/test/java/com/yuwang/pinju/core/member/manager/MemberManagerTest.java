package com.yuwang.pinju.core.member.manager;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class MemberManagerTest {

	@Autowired
	private MemberManager memberManager;

	@Test
	public void testSpringInject() {
		Assert.assertNotNull(memberManager);
	}

	@Test
	public void testCreateSellerQuality() {
		SellerQualityDO sq = new SellerQualityDO();
		sq.setMemberId(100000855009000L);
		sq.setNickname("登录");
		sq.setShopId(10);
		sq.setShopName("登录商店");
		sq.setSellerType("S");
		sq.setApproveStatus(1);
		sq.setCompanyName("我的登录商店有限公司");
		sq.setLocalId(31);
		sq.setLocalName("上海");
		sq.setLevel(SellerQualityDO.LEVEL_D);
		sq.setCpType(SellerQualityDO.CP_TYPE_BASIC);
		sq.setMargin(5000000L);
		sq.setCategoryId(10L);
		sq.setCategoryName("体育");
		sq.setRefundRate(0);
		sq.setRefundSuccessRate(0);
		sq.setRightsRate(0);
		sq.setRefundSuccessRate(0);
		sq.setDisputeRate(0);

		Exception exp = null;
		try {
			SellerQualityDO afterInsert1 = memberManager.initSellerQuality(sq);
			Assert.assertNotNull(afterInsert1);
			Assert.assertNotNull(afterInsert1.getId());
			memberManager.initSellerQuality(sq);
		} catch (ManagerException e) {
			exp = e;
			e.printStackTrace();
		}
		Assert.assertNotNull(exp);
		Assert.assertNotNull(exp.getMessage());
	}

	@Test
	public void testGetMemberDeliveryById() {
		try {
			MemberDeliveryDO delivery = memberManager.getMemberDeliveryById(1007L);
			System.out.println(delivery);
			Assert.assertNotNull(delivery);
			Assert.assertNotNull(delivery.getId());
			Assert.assertNotNull(delivery.getMemberId());
			Assert.assertNotNull(delivery.getReceiverName());
			Assert.assertNotNull(delivery.getProvince());
			Assert.assertNotNull(delivery.getCity());
			Assert.assertNotNull(delivery.getDistrict());
			Assert.assertNotNull(delivery.getPcdCode());
			Assert.assertNotNull(delivery.getAddress());
			Assert.assertNotNull(delivery.getZipCode());
			Assert.assertNotNull(delivery.getPhone());
			Assert.assertNotNull(delivery.getMobile());
			Assert.assertNotNull(delivery.getStatus());
			Assert.assertNotNull(delivery.getUpdateTime());
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
}
