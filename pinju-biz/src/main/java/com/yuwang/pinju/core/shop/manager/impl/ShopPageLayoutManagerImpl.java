package com.yuwang.pinju.core.shop.manager.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.shop.dao.ShopPageLayoutDAO;
import com.yuwang.pinju.core.shop.manager.ShopPageLayoutManager;
import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;

/**
 * @author Kevin
 *
 * @since 2011-7-4
 */
public class ShopPageLayoutManagerImpl extends BaseManager implements ShopPageLayoutManager {
	private ShopPageLayoutDAO shopPageLayoutDAO;
	/**
	 * 分布式缓存
	 */
	private MemcachedClient shopMemcachedClient;
	
	public ShopPageLayoutDAO getShopPageLayoutDAO() {
		return shopPageLayoutDAO;
	}

	public void setShopPageLayoutDAO(ShopPageLayoutDAO shopPageLayoutDAO) {
		this.shopPageLayoutDAO = shopPageLayoutDAO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopPageLayoutDO> queryPageLayout(Long userPageId,Long userId,
			Integer shopId, Integer pageId) throws ManagerException{
		try{
			List result =(List)shopPageLayoutDAO.queryUserPageLayout(userPageId,userId, shopId, pageId);
			
			if(result==null||result.size()==0){
				if(pageId.equals(ShopConstants.SHOP_FIRST_PAGE)){
					result =(List)shopPageLayoutDAO.queryPageLayout(pageId);
					shopPageLayoutDAO.insertUserPageLayout(userId, shopId, pageId, (String)((Map)result.get(0)).get("PAGE_STRUCTURE"));
					result =(List)shopPageLayoutDAO.queryUserPageLayout(userPageId,userId, shopId, pageId);
					if(result==null||result.size()==0){
						throw new ManagerException("没有找到页面的初始布局");
					}
				}
			}
			List result2 =(List)shopPageLayoutDAO.queryUserPageLayout(null,userId, shopId, pageId);
			((Map)result.get(0)).put("firstUserPageId", ((Map)result2.get(0)).get("ID"));
			return readXml((Map)result.get(0));
		}catch (DaoException e){
			throw new ManagerException("获取用户布局出错",e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ShopPageLayoutDO> queryPageLayoutCache(Long userPageId, Long userId,
			Integer shopId, Integer pageId, boolean isRelease) throws ManagerException{
		try {
			if (!isRelease)
				return queryPageLayout(userPageId,
						userId, shopId, pageId);
			String key = ShopConstants.createDesignPageCacheKey(userPageId,
					pageId, shopId);
			Map<String, String> map = (Map<String, String>) shopMemcachedClient
					.get(ShopConstants.SHOP_MODULE_CACHE_KEY + shopId);
			if (map == null)
				map = new HashMap<String, String>();
			List<ShopPageLayoutDO> pageLayoutList = null;
			if (!map.containsKey(key)) {
				pageLayoutList = queryPageLayout(
						userPageId, userId, shopId, pageId);
				shopMemcachedClient.set(key, 0, pageLayoutList);
				map.put(key, key);
				shopMemcachedClient.set(ShopConstants.SHOP_MODULE_CACHE_KEY
						+ shopId, 0, map);
				return pageLayoutList;
			}
			pageLayoutList = shopMemcachedClient.get(key);
			if (pageLayoutList != null) {
				log.debug("#################page structure get out from cache; KEY="
								+ key);
				return pageLayoutList;
			}
		} catch (TimeoutException e) {
			log.error("操作分布式缓存超时", e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("操作分布式缓存出错", e);
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			log.error("操作分布式缓存出错", e);
		} catch (ManagerException e) {
			log.error("获取页面布局结构出错", e);
		}
		return new Vector();
	}
	
	@Override
	public boolean savePageLayout(Long userId, Integer shopId, Integer pageId,String layoutXml) throws ManagerException{
		try{
			Object id = shopPageLayoutDAO.saveUserPageLayout(userId, shopId, pageId, layoutXml);
			if(id==null){
				id = shopPageLayoutDAO.insertUserPageLayout(userId, shopId, pageId, layoutXml);
				if(id!=null) return true;
			}
		}catch (DaoException e){
			throw new ManagerException("保存用户布局出错",e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<ShopPageLayoutDO> readXml(Map map){
		String layoutXml = (String)map.get("SAVE_STRUCTURE");
		Long userPageId = ((BigDecimal)map.get("ID")).longValue();
		Long firstUserPageId = ((BigDecimal)map.get("firstUserPageId")).longValue();
		
		List<ShopPageLayoutDO> list = new Vector<ShopPageLayoutDO>();
		
		SAXBuilder sax = new SAXBuilder();
		try {
			Document doc = sax.build(new ByteArrayInputStream(layoutXml.getBytes("UTF-8")));
			Element rootEle = doc.getRootElement();
			String layoutsType = rootEle.getAttributeValue("type");
			List<Element> layoutlist = rootEle.getChildren("layout");
			for(Iterator<Element> layoutIt = layoutlist.iterator(); layoutIt.hasNext();){
				Element layout = layoutIt.next();
				String type = layout.getAttributeValue("type");
				List<Element> slotlist = layout.getChildren("slot");
				int i=0;
				for (Iterator<Element> slotIt = slotlist.iterator(); slotIt.hasNext();) {
					if(type.equals("left-right")||type.equals("left")){
						if(i==0){
							type = "left";
						}else{
							type = "right";
						}
					}
					Element slot = slotIt.next();
					List<Element> modulelist = slot.getChildren("module");
					for (Iterator<Element> moduleIt = modulelist.iterator(); moduleIt.hasNext();) {
						Element module = moduleIt.next();
						String name = module.getAttributeValue("name");
						String id = module.getAttributeValue("id");
						String title = module.getAttributeValue("title");
						String isCustomCode = module.getAttributeValue("is-custom-code");
						String description = module.getAttributeValue("description");
						ShopPageLayoutDO shopPageLayoutDO = new ShopPageLayoutDO();
						shopPageLayoutDO.setLayoutsType(layoutsType);
						shopPageLayoutDO.setUserPageId(userPageId);
						shopPageLayoutDO.setFirstUserPageId(firstUserPageId);
						shopPageLayoutDO.setId(id);
						shopPageLayoutDO.setTitle(title);
						shopPageLayoutDO.setName(name);
						shopPageLayoutDO.setDescription(description);
						shopPageLayoutDO.setIsCustomCode(isCustomCode);
						shopPageLayoutDO.setType(type);
						list.add(shopPageLayoutDO);
					}
					i++;
				}
			}
			
		} catch (JDOMException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}   


		
		return list;
	}
	
	public MemcachedClient getShopMemcachedClient() {
		return shopMemcachedClient;
	}

	public void setShopMemcachedClient(MemcachedClient shopMemcachedClient) {
		this.shopMemcachedClient = shopMemcachedClient;
	}
}
