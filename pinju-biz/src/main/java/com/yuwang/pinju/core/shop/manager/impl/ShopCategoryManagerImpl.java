package com.yuwang.pinju.core.shop.manager.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import com.yuwang.pinju.common.ArrayUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.shop.dao.ShopCategroyDAO;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.filter.PropFilter;

/**
 * 店铺商品分类管理实现
 * 
 * @author mike
 *
 * @since 2011-6-1
 */
public class ShopCategoryManagerImpl extends BaseManager implements ShopCategoryManager {

	/**
	 * 店铺商品分类数据接口
	 */
	private  ShopCategroyDAO  shopCategoryDAO;
	
	private ItemManager itemManager;
	
	@Override
	public void insertShopCategroy(ShopCategoryDO shopCategory)throws ManagerException {
		 try {
			 shopCategory = repairShopCategoryId(shopCategory);
			 shopCategoryDAO.insertShopCategroy(shopCategory);
		} catch (DaoException e) {
				throw new ManagerException("店铺商品分类插入错误",e);
		}
	}

	@Override
	public ShopCategoryDO queryShopCategory(Integer shopId,Long userId)throws ManagerException {
			try {
				return shopCategoryDAO.queryShopCategory(shopId,userId);
			} catch (DaoException e) {
				throw new ManagerException("查询店铺分类错误",e);
			}
	}

	/**
	 * 注入店铺商品分类数据接口
	 * 
	 * @param shopCategoryDAO
	 */
	public void setShopCategoryDAO(ShopCategroyDAO shopCategoryDAO) {
		this.shopCategoryDAO = shopCategoryDAO;
	}

	@Override
	public void updateShopCategroy(ShopCategoryDO shopCategory)throws ManagerException {
		try {
			shopCategory = repairShopCategoryId(shopCategory);
			repairRemoveShopCategoryId(shopCategory);
			shopCategoryDAO.updateShopCategroy(shopCategory);
		} catch (DaoException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		}
	}

	/**
	 * 保存之前将新增加的分类通Seqence生成ID
	 * @param shopCategoryDO
	 * @return
	 * @throws ManagerException
	 */
	public ShopCategoryDO repairShopCategoryId(ShopCategoryDO shopCategoryDO) throws ManagerException{
		try {
			String firstCategoryStr = shopCategoryDO.getFirstCategory();
			String secondCategoryStr = shopCategoryDO.getSecondCategory();
			secondCategoryStr = PropFilter.doFilter(secondCategoryStr);
			Properties prop = new Properties();
			prop.load(new StringReader(secondCategoryStr));
			StringBuffer firstsb = new StringBuffer();
			StringBuffer secondsb = new StringBuffer();
			if(firstCategoryStr!=null){
				String[] firstCategorys = firstCategoryStr.split(ShopConstants.SHOP_VALUE_SPLIT);
				for(String firstCategory:firstCategorys){
					if(firstCategory.equals("")) continue;
					String[] id_name = firstCategory.split(ShopConstants.SHOP_CATEGORY_SPLIT);
					if(id_name[0].equals("0")||id_name[0].equals("null")){
						id_name[0] = shopCategoryDAO.getShopCategorySeqId()+"";
					}
					firstsb.append(",").append(id_name[0]).append(ShopConstants.SHOP_CATEGORY_SPLIT).append(id_name[1]);
					secondsb.append(id_name[0]+ShopConstants.SHOP_CATEGORY_SPLIT+id_name[1]).append("=");
					StringBuffer secondsbtemp = new StringBuffer();
					String temp="";
					if(prop.getProperty(firstCategory)!=null&&!prop.getProperty(firstCategory).equals("")){
						String[] secondCategorys = prop.getProperty(firstCategory).split(ShopConstants.SHOP_VALUE_SPLIT);
						for(String secondCategory:secondCategorys){
							if(secondCategory.equals("")) continue;
							String[] id_name2 = secondCategory.split(ShopConstants.SHOP_CATEGORY_SPLIT);
							if(id_name2[0].equals("0")||id_name2[0].equals("null")){
								id_name2[0] = shopCategoryDAO.getShopCategorySeqId()+"";
							}
							secondsbtemp.append(",").append(id_name2[0]).append(ShopConstants.SHOP_CATEGORY_SPLIT).append(id_name2[1]);
						}
						if(secondsbtemp.toString().length()>0)
							temp = secondsbtemp.substring(1);
					}
					secondsb.append(temp);
					secondsb.append(ShopConstants.SHOP_NEWLINE);
				}
			}
			if(firstsb.toString().length()>0)
				shopCategoryDO.setFirstCategory(firstsb.toString().substring(1));
			if(secondsb.toString().length()>0)
				shopCategoryDO.setSecondCategory(secondsb.toString());
			return shopCategoryDO;
		} catch (DaoException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		} catch (IOException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		}
	}

	
	/**
	 * 获取已删除的分类ID，并发送给商品接口
	 * @param newshopCategoryDO
	 * @throws ManagerException
	 */
	public void repairRemoveShopCategoryId(ShopCategoryDO newshopCategoryDO) throws ManagerException{
		try {
			List<String> removieCategoryId = new Vector<String>();
			ShopCategoryDO oldShopCategoryDO = queryShopCategory(newshopCategoryDO.getShopId(),newshopCategoryDO.getUserId());
			String firstCategoryStr = oldShopCategoryDO.getFirstCategory();
			String secondCategoryStr = oldShopCategoryDO.getSecondCategory();
			secondCategoryStr = PropFilter.doFilter(secondCategoryStr);
			Properties prop = new Properties();
			if(secondCategoryStr!=null)
				prop.load(new StringReader(secondCategoryStr));
			if(firstCategoryStr!=null){
				String[] firstCategorys = firstCategoryStr.split(ShopConstants.SHOP_VALUE_SPLIT);
				for(String firstCategory:firstCategorys){
					String[] id_name = firstCategory.split(ShopConstants.SHOP_CATEGORY_SPLIT);
					if(id_name[0].equals("0")||id_name[0].equals("null")){
						id_name[0] = shopCategoryDAO.getShopCategorySeqId()+"";
					}
					if(newshopCategoryDO.getFirstCategory().indexOf(id_name[0]+ShopConstants.SHOP_CATEGORY_SPLIT)<0){
						removieCategoryId.add(id_name[0]);
					}
					if(prop.getProperty(firstCategory)!=null&&!prop.getProperty(firstCategory).equals("")){
						String[] secondCategorys = prop.getProperty(firstCategory).split(ShopConstants.SHOP_VALUE_SPLIT);
						for(String secondCategory:secondCategorys){
							String[] id_name2 = secondCategory.split(ShopConstants.SHOP_CATEGORY_SPLIT);
							if(id_name2[0].equals("0")||id_name2[0].equals("null")){
								id_name2[0] = shopCategoryDAO.getShopCategorySeqId()+"";
							}
							if(newshopCategoryDO.getSecondCategory().indexOf(id_name2[0]+ShopConstants.SHOP_CATEGORY_SPLIT)<0){
								removieCategoryId.add(id_name2[0]);
							}
						}
					}
				}
			}
			if(removieCategoryId.size()>0)
				itemManager.deleteItemStoreCategories(newshopCategoryDO.getUserId(), removieCategoryId);
		} catch (DaoException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		} catch (IOException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, ShopCategoryListDO> queryShopCategoryList(Integer shopId)
			throws ManagerException {
		Map<String, ShopCategoryListDO> shopCategoryList = new LinkedHashMap<String, ShopCategoryListDO>();
		ShopCategoryDO shopCategoryDO = queryShopCategory(shopId, null);
		if (shopCategoryDO == null) {
			return shopCategoryList;
		}
		String firstCategorys = shopCategoryDO.getFirstCategory();
		// 二级分类
		String secondCategorys = StringUtil.EMPTY_STRING;
		String[] firstCategoryList = StringUtil.split(firstCategorys, ',');

		// 最后封装好的分类
		if (ArrayUtil.isNotEmpty(firstCategoryList)) {
			for (String str : firstCategoryList) {
				// 店铺商品分类实体
				ShopCategoryListDO categorys = new ShopCategoryListDO();
				String[] strs = str.split(ShopConstants.SHOP_CATEGORY_SPLIT);
				categorys.setCategoryName(str.split(ShopConstants.SHOP_CATEGORY_SPLIT)[1]);
				categorys.setId(str.split(ShopConstants.SHOP_CATEGORY_SPLIT)[0]);
				categorys.setCategoryGoods(categorys.getGoodsList(
						shopCategoryDO.getFirstGoods(), str));
				secondCategorys = shopCategoryDO.getCategoryConfig(str);
				Map secondGoods = new HashMap();
				if (StringUtil.isNotEmpty(secondCategorys)) {
					String[] secondCategoryList = StringUtil.split(
							secondCategorys, ',');
					if (ArrayUtil.isNotEmpty(secondCategoryList)) {
						List temp = new ArrayList();
						Map<String, String> map = new HashMap<String, String>();
						for (String secondCategory : secondCategoryList) {
							temp.add(secondCategory
									.split(ShopConstants.SHOP_CATEGORY_SPLIT));
							String[] secondCateMap = secondCategory
									.split(ShopConstants.SHOP_CATEGORY_SPLIT);
							map.put(secondCateMap[0], secondCateMap[1]);
							secondGoods.put(str
									+ ShopConstants.SHOP_CATEGORY_SPLIT
									+ secondCategory, categorys.getGoodsList(
									shopCategoryDO.getSecondGoods(), str
											+ ShopConstants.SHOP_CATEGORY_SPLIT
											+ secondCategory));
						}
						categorys.setSubCategoryMap(map);
						categorys.setSubCategoryList(temp);
					}
				}
				categorys.setSecondeGoodsList(secondGoods);
				shopCategoryList.put(strs[0], categorys);
			}
		}
		return shopCategoryList;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, ShopCategoryListDO> queryShopCategory(Integer shopId)
			throws ManagerException {
		Map<String, ShopCategoryListDO> shopCategoryList = new LinkedHashMap<String, ShopCategoryListDO>();
		ShopCategoryDO shopCategoryDO = queryShopCategory(shopId, null);
		if (shopCategoryDO == null) {
			return shopCategoryList;
		}
		String firstCategorys = shopCategoryDO.getFirstCategory();
		// 二级分类
		String secondCategorys = StringUtil.EMPTY_STRING;
		String[] firstCategoryList = StringUtil.split(firstCategorys, ',');

		// 最后封装好的分类
		if (ArrayUtil.isNotEmpty(firstCategoryList)) {
			for (String str : firstCategoryList) {
				// 店铺商品分类实体
				ShopCategoryListDO categorys = new ShopCategoryListDO();
				categorys.setCategoryName(str);
				categorys.setCategoryGoods(categorys.getGoodsList(
						shopCategoryDO.getFirstGoods(), str));
				secondCategorys = shopCategoryDO.getCategoryConfig(str);
				Map secondGoods = new HashMap();
				if (StringUtil.isNotEmpty(secondCategorys)) {
					String[] secondCategoryList = StringUtil.split(
							secondCategorys, ',');
					if (ArrayUtil.isNotEmpty(secondCategoryList)) {
						List temp = new ArrayList();
						for (String secondCategory : secondCategoryList) {
							temp.add(secondCategory);
							secondGoods.put(str
									+ ShopConstants.SHOP_CATEGORY_SPLIT
									+ secondCategory, categorys.getGoodsList(
									shopCategoryDO.getSecondGoods(), str
											+ ShopConstants.SHOP_CATEGORY_SPLIT
											+ secondCategory));
						}
						categorys.setSubCategoryList(temp);
					}
				}
				categorys.setSecondeGoodsList(secondGoods);
				shopCategoryList.put(str, categorys);
			}
		}
		return shopCategoryList;
	}
	
	/**
	 * 添加到分类
	 * @param itemId
	 * @param shopId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean copyItemToCateGory(Long itemId[], String key, String fromKey[], ShopCategoryDO shopCategoryDO)
			throws Exception {
		ShopCategoryDO shopCategory = null;
		try {
			shopCategory = queryShopCategory(shopCategoryDO.getShopId(), shopCategoryDO.getUserId());
			if(shopCategory != null){
				
				String secondGoods = shopCategory.getConfig(shopCategory.getSecondGoods(), key);
				String secondGoodString = shopCategory.getSecondGoods();
				String secondCategoryString = shopCategory.getSecondCategory();
				
				if(secondCategoryString.indexOf(key.split(ShopConstants.SHOP_CATEGORY_SPLIT)[1]) != -1){		//有没有这个二级分类
					//如果未分类中有这个key ,从未分类中移除
					if(secondGoodString.indexOf(ShopConstants.SHOP_DEFAULT_CATEGORYNAME) != -1){
						String defaultCategory = shopCategory.getConfig(shopCategory.getSecondGoods(), ShopConstants.SHOP_DEFAULT_CATEGORYNAME);
						for(int i=0;i<itemId.length;i++){
							if(defaultCategory.indexOf(String.valueOf(itemId)) !=-1 ){
								int begin = secondGoodString.indexOf(defaultCategory);
								int end = 0;
								if(secondGoodString.substring(begin+defaultCategory.length()+1).equals(ShopConstants.SHOP_VALUE_SPLIT)){
									end = begin + defaultCategory.length()+1;
									defaultCategory = defaultCategory.replaceAll(String.valueOf(itemId[i])+ShopConstants.SHOP_VALUE_SPLIT, "");
								}else{
									end = begin + defaultCategory.length();
									defaultCategory = defaultCategory.replaceAll(String.valueOf(itemId[i]), "");
								}
								secondGoodString = secondGoodString.substring(0,begin) + defaultCategory +secondGoodString.substring(end, secondGoodString.length());
								log.info("secondGoodString--------------------------"+secondGoodString);
							}
						}
						
					}
					
					log.info("------------------------------------------------------------");
					//添加到SecondGoods字段的相应key
					for(int i=0;i<itemId.length;i++){
						if(secondGoodString.indexOf(key) != -1){		//SecondGoods字段中有没有
							int begin = secondGoodString.indexOf(secondGoods);
							int end = begin + secondGoodString.length();
							secondGoods = itemId[i] + ShopConstants.SHOP_VALUE_SPLIT + secondGoods;
							secondGoodString = secondGoodString.substring(0,begin) + secondGoods + secondGoodString.substring(end, secondGoodString.length());
							log.info("secondGoodString--------------------------"+secondGoodString);
						}else{
							secondGoodString += key + "=" + itemId[i] + "\n";
							log.info("secondGoodString--------------------------"+secondGoodString);
						}
					}
					log.info("------------------------------------------------------------");
				}
				
			}
		}catch(ManagerException e){
			log.error(e);
			log.info(e);
		}
		return false;
	}

	/**
	 * 移动到分类
	 * @param itemId
	 * @param shopId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean moveItemToCategory(Long itemId[], String key, String fromKey[], ShopCategoryDO shopCategoryDO)
			throws Exception {

		ShopCategoryDO shopCategory = null;
		try {
			shopCategory = queryShopCategory(shopCategoryDO.getShopId(), shopCategoryDO.getUserId());
			if(shopCategory != null){
				
				String secondGoods = shopCategory.getConfig(shopCategory.getSecondGoods(), key);
				String secondGoodString = shopCategory.getSecondGoods();
				String secondCategoryString = shopCategory.getSecondCategory();
				
				if(secondCategoryString.indexOf(key.split(ShopConstants.SHOP_CATEGORY_SPLIT)[1]) != -1){		//有没有这个二级分类
					//从分类中移除
					for(int i=0;i<fromKey.length;i++){
						String goods = shopCategory.getConfig(shopCategory.getSecondGoods(), fromKey[i]);
						int begin = secondGoodString.indexOf(goods);
						int end = 0;
						if(secondGoodString.substring(begin+goods.length()+1).equals(ShopConstants.SHOP_VALUE_SPLIT)){
							end = begin + goods.length()+1;
							goods = goods.replaceAll(String.valueOf(itemId[i])+ShopConstants.SHOP_VALUE_SPLIT, "");
						}else{
							end = begin + goods.length();
							goods = goods.replaceAll(String.valueOf(itemId[i]), "");
						}
						
						secondGoodString = secondGoodString.substring(0,begin) + goods +secondGoodString.substring(end, secondGoodString.length());
						log.info("secondGoodString--------------------------"+secondGoodString);
					}
					log.info("------------------------------------------------------------");
					//如果未分类中有这个key ,从未分类中移除
					if(secondGoodString.indexOf(ShopConstants.SHOP_DEFAULT_CATEGORYNAME) != -1){
						String defaultCategory = shopCategory.getConfig(shopCategory.getSecondGoods(), ShopConstants.SHOP_DEFAULT_CATEGORYNAME);
						for(int i=0;i<itemId.length;i++){
							if(defaultCategory.indexOf(String.valueOf(itemId)) !=-1 ){
								int begin = secondGoodString.indexOf(defaultCategory);
								int end = 0;
								if(secondGoodString.substring(begin+defaultCategory.length()+1).equals(ShopConstants.SHOP_VALUE_SPLIT)){
									end = begin + defaultCategory.length()+1;
									defaultCategory = defaultCategory.replaceAll(String.valueOf(itemId[i])+ShopConstants.SHOP_VALUE_SPLIT, "");
								}else{
									end = begin + defaultCategory.length();
									defaultCategory = defaultCategory.replaceAll(String.valueOf(itemId[i]), "");
								}
								secondGoodString = secondGoodString.substring(0,begin) + defaultCategory +secondGoodString.substring(end, secondGoodString.length());
								log.info("secondGoodString--------------------------"+secondGoodString);
							}
						}
						
					}
					
					log.info("------------------------------------------------------------");
					//添加到SecondGoods字段的相应key
					for(int i=0;i<itemId.length;i++){
						if(secondGoodString.indexOf(key) != -1){		//SecondGoods字段中有没有
							int begin = secondGoodString.indexOf(secondGoods);
							int end = begin + secondGoodString.length();
							secondGoods = itemId[i] + ShopConstants.SHOP_VALUE_SPLIT + secondGoods;
							secondGoodString = secondGoodString.substring(0,begin) + secondGoods + secondGoodString.substring(end, secondGoodString.length());
							log.info("secondGoodString--------------------------"+secondGoodString);
						}else{
							secondGoodString += key + "=" + itemId[i] + "\n";
							log.info("secondGoodString--------------------------"+secondGoodString);
						}
					}
					log.info("------------------------------------------------------------");
				}
				
			}
		}catch(ManagerException e){
			log.error(e);
			log.info(e);
		}
		return false;
	
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	@SuppressWarnings("unchecked")
	public Map<String, ShopCategoryListDO> queryShopCategoryByUser(Long memberId)
			throws ManagerException {
		// TODO Auto-generated method stub
		Map<String, ShopCategoryListDO> shopCategoryList = new LinkedHashMap<String, ShopCategoryListDO>();
		ShopCategoryDO shopCategoryDO = queryShopCategory(null, memberId);
		if (shopCategoryDO == null) {
			return shopCategoryList;
		}
		String firstCategorys = shopCategoryDO.getFirstCategory();
		// 二级分类
		String secondCategorys = StringUtil.EMPTY_STRING;
		String[] firstCategoryList = StringUtil.split(firstCategorys, ',');

		// 最后封装好的分类
		if (ArrayUtil.isNotEmpty(firstCategoryList)) {
			for (String str : firstCategoryList) {
				// 店铺商品分类实体
				ShopCategoryListDO categorys = new ShopCategoryListDO();
				String[] strs = str.split(ShopConstants.SHOP_CATEGORY_SPLIT);
				categorys.setCategoryName(str.split(ShopConstants.SHOP_CATEGORY_SPLIT)[1]);
				categorys.setId(str.split(ShopConstants.SHOP_CATEGORY_SPLIT)[0]);
				categorys.setCategoryGoods(categorys.getGoodsList(
						shopCategoryDO.getFirstGoods(), str));
				secondCategorys = shopCategoryDO.getCategoryConfig(str);
				Map secondGoods = new HashMap();
				if (StringUtil.isNotEmpty(secondCategorys)) {
					String[] secondCategoryList = StringUtil.split(
							secondCategorys, ',');
					if (ArrayUtil.isNotEmpty(secondCategoryList)) {
						List temp = new ArrayList();
						Map<String, String> map = new HashMap<String, String>();
						for (String secondCategory : secondCategoryList) {
							temp.add(secondCategory
									.split(ShopConstants.SHOP_CATEGORY_SPLIT));
							String[] secondCateMap = secondCategory
									.split(ShopConstants.SHOP_CATEGORY_SPLIT);
							map.put(secondCateMap[0], secondCateMap[1]);
							secondGoods.put(str
									+ ShopConstants.SHOP_CATEGORY_SPLIT
									+ secondCategory, categorys.getGoodsList(
									shopCategoryDO.getSecondGoods(), str
											+ ShopConstants.SHOP_CATEGORY_SPLIT
											+ secondCategory));
						}
						categorys.setSubCategoryMap(map);
						categorys.setSubCategoryList(temp);
					}
				}
				categorys.setSecondeGoodsList(secondGoods);
				shopCategoryList.put(strs[0], categorys);
			}
		}
		return shopCategoryList;
	}
}
