package com.yuwang.pinju.core.shop.ao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.shop.ao.ShopUserModuleAO;
import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.shop.manager.ShopUserPageManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.config.PageLayoutsPathConfig;
import com.yuwang.pinju.core.util.freemarker.FreemarkerUtil;
import com.yuwang.pinju.domain.member.SellerQualityInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * 店铺页面
 * 
 * @author liyouguo
 * 
 * @since 2011-6-27
 */
public class ShopUserPageAOImpl extends BaseAO implements ShopUserPageAO {

	private ShopUserPageManager shopUserPageManager;
	private ShopUserModuleAO shopUserModuleAO;
	private MemcachedClient shopMemcachedClient;
	private ShopShowInfoManager shopShowInfoManager;

	private MemberAO memberAO;
	
	public MemberAO getMemberAO() {
		return memberAO;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public MemcachedClient getShopMemcachedClient() {
		return shopMemcachedClient;
	}

	public void setShopMemcachedClient(MemcachedClient shopMemcachedClient) {
		this.shopMemcachedClient = shopMemcachedClient;
	}

	public ShopUserPageManager getShopUserPageManager() {
		return shopUserPageManager;
	}

	public void setShopUserPageManager(ShopUserPageManager shopUserPageManager) {
		this.shopUserPageManager = shopUserPageManager;
	}

	public ShopUserModuleAO getShopUserModuleAO() {
		return shopUserModuleAO;
	}

	public void setShopUserModuleAO(ShopUserModuleAO shopUserModuleAO) {
		this.shopUserModuleAO = shopUserModuleAO;
	}

	@Override
	public Object deleteShopUserPage(ShopUserPageDO userPageDO)
			throws Exception {
		// TODO Auto-generated method stub
		return shopUserPageManager.deleteShopUserPage(userPageDO);
	}

	@Override
	public Object insertShopUserPage(ShopUserPageDO userPageDO)
			throws Exception {
		// TODO Auto-generated method stub
		return shopUserPageManager.insertShopUserPage(userPageDO);
	}

	@Override
	public List<ShopUserPageDO> selectShopUserCustomerPage(
			ShopUserPageDO userPageDO, boolean isRelease) throws Exception {
		// TODO Auto-generated method stub
		List<ShopUserPageDO> tmp = selectShopUserPage(userPageDO, isRelease);
		List<ShopUserPageDO> list = new ArrayList<ShopUserPageDO>();
		for (ShopUserPageDO userPage : tmp) {
			if (userPage.getPageId().equals(ShopConstants.SHOP_CUSTOMER_PAGE))
				list.add(userPage);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopUserPageDO> selectShopUserPage(
			ShopUserPageDO shopUserPageDO, boolean isRelease) throws Exception {
		// TODO Auto-generated method stub
		if (!isRelease)
			return shopUserPageManager.selectShopUserPage(shopUserPageDO);
		String key = "shop.design.allpage." + shopUserPageDO.getShopId();
		if (shopUserPageDO.getPageId() != null
				&& shopUserPageDO.getPageId().intValue() > 0)
			key += "_" + shopUserPageDO.getPageId();// 生成缓存的KEY
		try {
			Map<String, String> map = (Map<String, String>) shopMemcachedClient
					.get(ShopConstants.SHOP_MODULE_CACHE_KEY
							+ shopUserPageDO.getShopId());// 取一级缓存，即缓存店铺所有页面及模块的缓存KEY
			if (map == null)
				map = new HashMap<String, String>();
			List<ShopUserPageDO> list = null;
			if (!map.containsKey(key)) {// 未命中，从数据库中或者接口中取相应数据，同时保存到缓存中
				list = shopUserPageManager.selectShopUserPage(shopUserPageDO);
				shopMemcachedClient.set(key, 0, list);
				map.put(key, key);
				shopMemcachedClient.set(ShopConstants.SHOP_MODULE_CACHE_KEY
						+ shopUserPageDO.getShopId(), 0, map);
				return list;
			}
			list = shopMemcachedClient.get(key);// 直接从缓存中取数据
			if (list != null) {
				log.debug("#################get all page from cache; KEY="
						+ key);
				return list;
			}
		} catch (TimeoutException e) {
			log.error("操作分布式缓存超时", e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("操作分布式缓存出错", e);
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			log.error("操作分布式缓存出错", e);
		}
		return shopUserPageManager.selectShopUserPage(shopUserPageDO);
	}

	@Override
	public Object updateShopUserPage(ShopUserPageDO userPageDO)
			throws Exception {
		// TODO Auto-generated method stub
		return shopUserPageManager.updateShopUserPage(userPageDO);
	}

	@Override
	public Object updateShopUserPageByKey(ShopUserPageDO userPageDO)
			throws Exception {
		// TODO Auto-generated method stub
		return shopUserPageManager.updateShopUserPageByKey(userPageDO);
	}

	@Override
	public Object[] saveUserCustomerPage(List<ShopUserPageDO> userPageList)
			throws Exception {
		// TODO Auto-generated method stub
		return shopUserPageManager.saveUserCustomerPage(userPageList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getPageHtml(List<ShopPageLayoutDO> list, Integer pageId,
			Long userId, Integer shopId, String preview, String seller, boolean isRelease,String ip) {
		
		String sellerInfo = "";
		Result result = memberAO.getMemberShopQuality(userId);
		Map m = new Hashtable();
		SellerQualityInfoDO sqi = result.getModel(SellerQualityInfoDO.class);
		if (sqi != null) {
			m.put("sellerQuality", sqi.getSellerQuality());
			m.put("dsrStats", sqi.getDsrStats());
			m.put("mid", seller);
			try {
				ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopInfoByShopId(shopId, null);
				//店铺标签LIST
				List<String> strList = new ArrayList<String>();
				if(shopInfoDO!=null){
					if(shopInfoDO.getShopLabel()!=null){
						String[] strShopLabel = shopInfoDO.getShopLabel().split(" ");
					
						for(int j=0;j<strShopLabel.length;j++){
							strList.add(strShopLabel[j]);
						}
					}else{
						strList.add("品聚");
					}
				}
				m.put("strList",strList);
				sellerInfo = FreemarkerUtil.process(m,
						"/module/seller-quality-info.ftl");
			} catch (Exception e1) {
				log.error("获取卖家资质信息报错!", e1);
			}
		} else
			log.error("获取卖家【" + userId == null ? "" : userId + "】资质信息出错");
		String pageHtml = null;
		String headHtml = "";
		String leftHtml = sellerInfo;
		String rightHtml = "";
		String footHtml = "";
		for (ShopPageLayoutDO shopPageLayoutDO : list) {
			ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
			shopUserModuleParamDO
					.setModuleId(new Long(shopPageLayoutDO.getId()));
			shopUserModuleParamDO.setPageId(pageId);
			shopUserModuleParamDO.setUserId(userId);
			shopUserModuleParamDO.setShopId(shopId);
			shopUserModuleParamDO.setIp(ip);
			shopUserModuleParamDO
					.setModuleId(new Long(shopPageLayoutDO.getId()));
			shopUserModuleParamDO.setUserPageId(shopPageLayoutDO
					.getUserPageId());
			shopUserModuleParamDO.setRealUserPageId(shopPageLayoutDO
					.getUserPageId());
			shopUserModuleParamDO.setPreview(preview);
			if (shopPageLayoutDO.getType().equals("head")) {
				if (shopUserModuleParamDO.getModuleId().equals(
						ShopConstants.SHOP_TOPSHOPSIGN_MODULEID)) {
					shopUserModuleParamDO.setUserPageId(shopPageLayoutDO
							.getFirstUserPageId());
				}
				headHtml += shopUserModuleAO.getModuleHtml(
						shopUserModuleParamDO, isRelease, "D"
								+ shopPageLayoutDO.getId(), true);
			} else if (shopPageLayoutDO.getType().equals("left")) {
				leftHtml += shopUserModuleAO.getModuleHtml(
						shopUserModuleParamDO, isRelease, "D"
								+ shopPageLayoutDO.getId(), true);
			} else if (shopPageLayoutDO.getType().equals("right")) {
				rightHtml += shopUserModuleAO.getModuleHtml(
						shopUserModuleParamDO, isRelease, "D"
								+ shopPageLayoutDO.getId(), true);
			} else if (shopPageLayoutDO.getType().equals("footer")) {
				footHtml += shopUserModuleAO.getModuleHtml(
						shopUserModuleParamDO, isRelease, "D"
								+ shopPageLayoutDO.getId(), true);
			}
		}
		if (list.size() > 0) {
			ShopPageLayoutDO shopPageLayoutDO = list.get(0);
			Map map = new Hashtable();
			map.put("headHtml", headHtml);
			map.put("leftHtml", leftHtml);
			map.put("rightHtml", rightHtml);
			map.put("footHtml", footHtml);
			try {
				pageHtml = FreemarkerUtil.process(map, PageLayoutsPathConfig
						.getInstance().getProperty(
								shopPageLayoutDO.getLayoutsType()));
			} catch (Exception e) {
				log.error("获取模块Html报错",e);
			}
		}
		return pageHtml;
	}

	public String getSkinColor(Long userId, Integer shopId, boolean isRelease) {
		String templateColor = "";
		if (shopId == null) {
			shopId = getUserShopId(userId);
		}
		StringBuffer key = null;
		if (isRelease) {
			key = new StringBuffer("shop.skin");
			key.append(".SHOPID=").append(shopId);
			try {
				templateColor = (String) shopMemcachedClient
						.get(key.toString());
				if (templateColor != null && !"".equals(templateColor.trim())) {
					log.debug("#################get shop skin from cache, KEY="
							+ key.toString());
					return templateColor;
				}
			} catch (TimeoutException e) {
				log.error("操作分布式缓存超时", e);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("操作分布式缓存出错", e);
			} catch (MemcachedException e) {
				// TODO Auto-generated catch block
				log.error("操作分布式缓存出错", e);
			}
		}
		try {
			ShopUserPageDO shopUserPageDO = new ShopUserPageDO();
			shopUserPageDO.setUserId(userId);
			shopUserPageDO.setShopId(shopId);
			shopUserPageDO.setPageId(ShopConstants.SHOP_FIRST_PAGE);
			List<ShopUserPageDO> shopUserPageDOList = shopUserPageManager
					.selectShopUserPage(shopUserPageDO);
			if (shopUserPageDOList.size() > 0) {
				templateColor = shopUserPageDOList.get(0).getConfig(
						shopUserPageDOList.get(0).getSkinId(), "templateColor");
				if (isRelease && templateColor != null)
					shopMemcachedClient.add(key.toString(), 0, templateColor);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return templateColor;
	}

	public String getFristPageConfiguration(Long userId, Integer shopId, boolean isRelease) {
		String configuration = "";
		if (shopId == null) {
			shopId = getUserShopId(userId);
		}
		StringBuffer key = null;
		if (isRelease) {
			key = new StringBuffer("shop.fristpageconfiguration");
			key.append(".SHOPID=").append(shopId);
			try {
				configuration = (String) shopMemcachedClient
						.get(key.toString());
				if (configuration != null && !"".equals(configuration.trim())) {
					log.debug("#################get shop skin from cache, KEY="
							+ key.toString());
					return configuration;
				}
			} catch (TimeoutException e) {
				log.error("操作分布式缓存超时", e);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("操作分布式缓存出错", e);
			} catch (MemcachedException e) {
				// TODO Auto-generated catch block
				log.error("操作分布式缓存出错", e);
			}
		}
		try {
			ShopUserPageDO shopUserPageDO = new ShopUserPageDO();
			shopUserPageDO.setUserId(userId);
			shopUserPageDO.setShopId(shopId);
			shopUserPageDO.setPageId(ShopConstants.SHOP_FIRST_PAGE);
			List<ShopUserPageDO> shopUserPageDOList = shopUserPageManager
					.selectShopUserPage(shopUserPageDO);
			if (shopUserPageDOList.size() > 0) {
				configuration = shopUserPageDOList.get(0).getConfiguration();
				if (isRelease && configuration != null)
					shopMemcachedClient.add(key.toString(), 0, configuration);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return configuration;
	}
	
	public Integer getUserShopId(Long userId) {
		ShopInfoDO shopInfoDO;
		try {
			shopInfoDO = shopShowInfoManager
					.queryShopBaseInfoByUser(userId, ShopConstant.APPROVE_STATUS_YES);
			if (shopInfoDO != null && shopInfoDO.getUserId() != null
					&& shopInfoDO.getShopId() != null) {
				return shopInfoDO.getShopId();
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.info("",e);
		}
		
		return null;
	}

	@Override
	public Object releaseUserPage(ShopUserPageDO userPageDO) throws Exception {
		// TODO Auto-generated method stub
		return shopUserPageManager.releaseShopUserPage(userPageDO);
	}

	@Override
	public Object restoreDesignPage(ShopUserPageDO userPageDO) throws Exception {
		// TODO Auto-generated method stub
		return shopUserPageManager.restoreDesignPage(userPageDO);
	}


	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

}
