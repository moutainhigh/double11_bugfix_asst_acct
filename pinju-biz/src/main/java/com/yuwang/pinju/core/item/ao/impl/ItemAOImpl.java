package com.yuwang.pinju.core.item.ao.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.yuwang.api.ApiException;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
import com.yuwang.pinju.core.image.manager.StorageFileInfoManager;
import com.yuwang.pinju.core.item.ao.ItemAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.core.item.manager.CustomCateProValueManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.item.manager.ItemPicManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.shop.manager.ShopShieldManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.storage.manager.FileInfoManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.HtmlRegexpUtil;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.core.util.PingYinUtil;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyQuery;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueQuery;
import com.yuwang.pinju.domain.item.CategoryQuery;
import com.yuwang.pinju.domain.item.CpValueRelationDO;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemInput;
import com.yuwang.pinju.domain.item.ItemPicDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.item.SkuInput;
import com.yuwang.pinju.domain.item.SpuDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * 
 * @author liming
 * 
 */
public class ItemAOImpl extends BaseAO implements ItemAO {

	static Logger logger = Logger.getLogger(ItemAOImpl.class.getName());

	public static final String ITEM_CATEGORY_MAP_KEY = "category";
	public static final String ITEM_CATEGORY_PROPERTY_KEY = "categoryProperty";
	public static final String ITEM_CATEGORY_PROPERTY_VALUE_KEY = "categoryPropertyValue";
	public static final String ITEM_CATEGORY_PROPERTYS_MAP_KEY = "categoryPropertys";

	private ItemManager itemManager;

	private CategoryManager categoryManager;

	private MemberManager memberManager;

	private SkuManager skuManager;

	private CategoryCacheManager categoryCacheManager;

	private FileStorageManager fileStorageManager;
	
	private FileInfoManager fileInfoManager;

	private CustomCateProValueManager customProValueManager;
	
	private ItemPicManager itemPicManager;
	
	private ShopShieldManager shopShieldManager;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	private StorageFileInfoManager storageFileInfoManager;
	
	@Override
	public List<ItemDO> getAllItem(ItemQuery itemQuery) {
		try {
			return itemManager.getItemList(itemQuery);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemCategoryNameList (long)
	 */
	@Override
	public JSONObject getItemCategory(long id) throws ManagerException {
		try {

			JSONObject json = new JSONObject();
			CategoryDO categoryDO = categoryManager.getItemCategory(id);
			List<CategoryPropertyDO> categoryPropertyList = categoryManager.getItemCategoryProperty(id);

			List ls = new ArrayList();

			CategoryPropertyDO categoryProperty = null;
			for (int i = 0; categoryPropertyList != null && i < categoryPropertyList.size(); i++) {
				categoryProperty = categoryPropertyList.get(i);
				List<CategoryPropertyValueDO> categoryPropertyValueList = categoryManager
						.getItemCategoryPropertyValue(categoryProperty.getId());
				if (categoryPropertyValueList != null) {
					Map m = new HashMap();
					m.put(ITEM_CATEGORY_PROPERTY_KEY, categoryProperty);
					m.put(ITEM_CATEGORY_PROPERTY_VALUE_KEY, categoryPropertyValueList);
					ls.add(m);
				}

			}

			json.put(ITEM_CATEGORY_MAP_KEY, categoryDO);
			json.put(ITEM_CATEGORY_PROPERTYS_MAP_KEY, ls);

			return json;
		} catch (ManagerException e) {
			throw new ManagerException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemCategoryNameList (long)
	 */
	@Override
	public CategoryQuery getItemCategory(String categoryId) {

		long startTime = System.currentTimeMillis();
		CategoryQuery categoryQuery = new CategoryQuery();

		try {

			if (EmptyUtil.isBlank(categoryId)) {
				return null;
			}

			// 获得类目
			CategoryDO categoryDO = categoryCacheManager.getCategoryDOById(Long.valueOf(categoryId));
			if (categoryDO == null) {
				log.debug("无效类目：" + categoryId);
				return null;
			}

			// 一般属性集合
			List<CategoryPropertyQuery> categoryPropertyList = new ArrayList<CategoryPropertyQuery>();
			// 销售属性集合
			List<CategoryPropertyQuery> selleCategoryPropertyList = new ArrayList<CategoryPropertyQuery>();
			// 关键属性集合
			List<CategoryPropertyQuery> keyCategoryPropertyList = new ArrayList<CategoryPropertyQuery>();

			// 类目属性列表
			List<CategoryPropertyDO> ls = categoryCacheManager.getItemCategoryProperty(categoryDO.getId());

			// 处理关系属性

			// 处理其他属性
			for (CategoryPropertyDO categoryPropertyDO : ls) {

				log.debug("处理类目属性：" + categoryPropertyDO.getCategoryId());
				if (categoryPropertyDO.getStatus() != categoryPropertyDO.IS_VALID) {
					log.debug("无效类目属性：" + categoryPropertyDO.getCategoryId());
					continue;
				}
				// 子属性
				CategoryPropertyDO preCategoryProperty = null;
				if (categoryPropertyDO.getParentId() != null) {
					preCategoryProperty = categoryCacheManager.getItemCategoryPropertyById(categoryPropertyDO
							.getParentId());
				}

				if (preCategoryProperty != null) {
					continue;
				}

				List<CategoryPropertyQuery> childPropertyList = getChildPropertyList(categoryPropertyDO, null);
				if (childPropertyList != null && childPropertyList.size() > 0) {
					for (CategoryPropertyQuery childQuery : childPropertyList) {

						if (childQuery.getIsEnumerate() == CategoryPropertyDO.IS_YES
								&& childQuery.getIsMultipleChoice() != 1
								&& (childQuery.getCpvList() == null || childQuery.getCpvList().size() < 1)) {
							continue;
						}

						// 关键属性
						if (childQuery.getIsKeyProperty() == CategoryPropertyDO.IS_YES) {
							log.debug("关键属性");
							keyCategoryPropertyList.add(childQuery);
						} else {
							// 销售属性
							if (categoryPropertyDO.getIsSelleProperty() != null
									&& categoryPropertyDO.getIsSelleProperty() == CategoryPropertyDO.IS_YES) {
								if (selleCategoryPropertyList.size() < 4) {
									selleCategoryPropertyList.add(childQuery);
									log.debug("销售属性");
								}
							} else {
								log.debug("普通属性");
								categoryPropertyList.add(childQuery);
							}
						}
					}
				}

			}

			int propertySize = keyCategoryPropertyList.size() + categoryPropertyList.size();
			int sellPropertySize = selleCategoryPropertyList.size();
			categoryQuery.setPropertySize(propertySize);
			categoryQuery.setSellPropertySize(sellPropertySize);
			categoryQuery.setCategoryDO(categoryDO);
			categoryQuery.setKeyCategoryPropertyList(keyCategoryPropertyList);
			categoryQuery.setCategoryPropertyList(categoryPropertyList);
			categoryQuery.setSellCategoryPropertyList(selleCategoryPropertyList);

		} catch (ManagerException e) {
			log.error("获得商品类目错误", e);
		}

		long endTime = System.currentTimeMillis();
		log.debug("getItemCategory Run time:" + (endTime - startTime));

		return categoryQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemAO#getItemCategoryForUpdate(com.yuwang.pinju.domain.item.ItemDO)
	 */
	@Override
	public CategoryQuery getItemCategoryForUpdate(ItemDO itemDO) {

		long startTime = System.currentTimeMillis();
		CategoryQuery categoryQuery = new CategoryQuery();

		try {

			// 获得类目
			CategoryDO categoryDO = categoryCacheManager.getCategoryDOById(itemDO.getCatetoryId());
			if (categoryDO == null) {
				log.debug("无效类目：" + itemDO.getCatetoryId());
				return null;
			}

			Map<String, String> selPropertyMap = new HashMap<String, String>();
			if (!EmptyUtil.isBlank(itemDO.getPropertyValuePair())) {
				log.debug("处理商品类目属性字符串：" + itemDO.getPropertyValuePair());
				String pros[] = itemDO.getPropertyValuePair().split(";");
				for (String cpId : pros) {
					selPropertyMap.put(cpId.split(":")[0], cpId.split(":")[1]);
				}
			}

			// 一般属性集合
			List<CategoryPropertyQuery> categoryPropertyList = new ArrayList<CategoryPropertyQuery>();
			// 销售属性集合
			List<CategoryPropertyQuery> selleCategoryPropertyList = new ArrayList<CategoryPropertyQuery>();
			// 关键属性集合
			List<CategoryPropertyQuery> keyCategoryPropertyList = new ArrayList<CategoryPropertyQuery>();

			// 类目属性列表
			List<CategoryPropertyDO> ls = categoryCacheManager.getItemCategoryProperty(categoryDO.getId());

			// 处理 SKU
			List<SkuInput> skuList = null;
			skuList = new ArrayList<SkuInput>();
			List<SkuDO> sls = skuManager.getItemSkuByItemId(itemDO.getId());

			for (SkuDO skuDO : sls) {
				skuList.add(getSkuInput(skuDO));
			}

			Map<String, List<String>> selectSkuMap = getSkuSelectMap(sls);

			// 处理其他属性
			for (CategoryPropertyDO categoryPropertyDO : ls) {

				log.debug("处理类目属性：" + categoryPropertyDO.getId());
				if (categoryPropertyDO.getStatus() != categoryPropertyDO.IS_VALID) {
					log.debug("无效类目属性：" + categoryPropertyDO.getId());
					continue;
				}
				// 子属性
				CategoryPropertyDO preCategoryProperty = null;
				if (categoryPropertyDO.getParentId() != null) {
					preCategoryProperty = categoryCacheManager.getItemCategoryPropertyById(categoryPropertyDO
							.getParentId());
				}

				if (preCategoryProperty != null) {
					continue;
				}

				SpuDO spuDO = null;
				if (itemDO.getSpuId() != null) {
					spuDO = categoryManager.getItemSpuById(itemDO.getSpuId());
				}

				List<CategoryPropertyQuery> childPropertyList = getChildPropertyList(categoryPropertyDO, selPropertyMap);

				if (childPropertyList != null && childPropertyList.size() > 0) {
					for (CategoryPropertyQuery childQuery : childPropertyList) {

						if (childQuery.getIsEnumerate() == CategoryPropertyDO.IS_YES
								&& childQuery.getIsMultipleChoice() != 1
								&& (childQuery.getCpvList() == null || childQuery.getCpvList().size() < 1)) {
							continue;
						}

						// 关键属性
						if (childQuery.getIsKeyProperty() == CategoryPropertyDO.IS_YES) {
							log.debug("关键属性");
							keyCategoryPropertyList.add(childQuery);

							if (spuDO != null
									&& childQuery.getCpId().equalsIgnoreCase(String.valueOf(spuDO.getKeyPropertyId()))) {
								// SPU属性
								if (childQuery.getCpvList() != null) {
									for (CategoryPropertyValueQuery categoryPropertyValueQuery : childQuery
											.getCpvList()) {
										if (categoryPropertyValueQuery.getCpvId().equalsIgnoreCase(
												String.valueOf(spuDO.getKeyPropertyValue()))) {
											categoryPropertyValueQuery.isSelect = 1;
										}
									}
								}
							}

						} else {
							if (categoryPropertyDO.getIsSelleProperty() != null
									&& categoryPropertyDO.getIsSelleProperty() == CategoryPropertyDO.IS_YES) {
								if (selleCategoryPropertyList.size() < 4) {
									selleCategoryPropertyList.add(childQuery);
									if (selectSkuMap != null) {
										if (selectSkuMap.containsKey(String.valueOf(childQuery.getCpId()))) {
											List ssl = selectSkuMap.get(String.valueOf(childQuery.getCpId()));
											List<CustomProValueDO> customSkuList = new ArrayList<CustomProValueDO>();
											for (CategoryPropertyValueQuery categoryPropertyValueQuery : childQuery
													.getCpvList()) {
												if (ssl.contains(categoryPropertyValueQuery.getCpvId())) {
													log
															.debug("SKU属性" + categoryPropertyValueQuery.getCpvValue()
																	+ "选中");
													categoryPropertyValueQuery.isSelect = 1;
													CustomProValueDO customProValueDO = new CustomProValueDO();
													customProValueDO = customProValueManager.selectItemCustomProValue(itemDO.getId(), categoryPropertyDO.getId(), Long.parseLong(categoryPropertyValueQuery.getCpvId()));
													if(customProValueDO!=null&&customProValueDO.getId()!=null&&customProValueDO.getId()>0){
														categoryPropertyValueQuery.setId(customProValueDO.getId());
														if(customProValueDO.getValue()!=null){
															categoryPropertyValueQuery.setValue(customProValueDO.getValue());
														}
														if(customProValueDO.getImgUrl()!=null){
															categoryPropertyValueQuery.setImgUrl(customProValueDO.getImgUrl());
														}
													}
												}
											}
										}
									}
									log.debug("销售属性");
								}
							} else {
								log.debug("普通属性");
								categoryPropertyList.add(childQuery);
							}
						}
					}
				}

			}

			int propertySize = keyCategoryPropertyList.size() + categoryPropertyList.size();
			int sellPropertySize = selleCategoryPropertyList.size();
			categoryQuery.setPropertySize(propertySize);
			categoryQuery.setSellPropertySize(sellPropertySize);
			categoryQuery.setCategoryDO(categoryDO);
			categoryQuery.setKeyCategoryPropertyList(keyCategoryPropertyList);
			categoryQuery.setCategoryPropertyList(categoryPropertyList);
			categoryQuery.setSellCategoryPropertyList(selleCategoryPropertyList);
			categoryQuery.setSkuList(skuList);

		} catch (ManagerException e) {
			log.error("获得商品类目错误", e);
		} catch (Exception e) {
			log.error("获得商品类目错误", e);
		}

		long endTime = System.currentTimeMillis();
		log.debug("getItemCategory Run time:" + (endTime - startTime));

		return categoryQuery;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemCategoryNameList (long)
	 */
	@Override
	public String getItemCategoryListByPath(String categoryPath) throws ManagerException {
		try {
			JSONObject objCategory = new JSONObject();
			Map mAll = new HashMap();
			if(categoryPath!=null&&!categoryPath.equals("")){
				String[] strArray = categoryPath.split(":");	
				for(int j = 0; j < strArray.length; j++) {
//					System.out.println(strArray[j]);
					
					JSONObject obj = new JSONObject();
					if (Long.parseLong(strArray[j]) == 0) {
						obj.put("categoryLevel", 0);
					} else {
						CategoryDO categoryDO = categoryCacheManager.getCategoryDOById(Long.parseLong(strArray[j]));
						if (categoryDO.getCategoryLevel() == null || categoryDO.getCategoryLevel() < 0
								&& categoryDO.getCategoryLevel() > 4) {
							obj.put("size", 0);
							return obj.toString();
						}
						obj.put("categoryLevel", categoryDO.getCategoryLevel() + 1);
					}

					List<CategoryDO> ls = categoryCacheManager.getItemCategoryListByParentId(Long.parseLong(strArray[j]));
					List<Map<String, Object>> show = new ArrayList<Map<String, Object>>();
					if (ls != null) {
						if(ls.size()>0){
							Collections.sort(ls, new Comparator<CategoryDO>() {
							public int compare(CategoryDO cpvd1, CategoryDO cpvd2) {
								return (int) (cpvd1.getSortOrder() - cpvd2.getSortOrder());
					            }
					        });
						}
						for (int i = 0; i < ls.size(); i++) {
							CategoryDO s = ls.get(i);
							String d = "";
//							for (int j = 0; j < s.getName().length(); j++) {
//								d += PingYinUtil.convert(String.valueOf(s.getName().charAt(j)));
//							}
							d = PingYinUtil.getPinYinHeadChar(s.getName());
							s.setSpell(d);
							Map m = new HashMap();
							m.put("id", s.getId());
							m.put("title", s.getName());
							m.put("spell", d);
							m.put("isleaf", s.getIsLeaf());
							show.add(m);
						}
						obj.put("size", ls.size());
						obj.put("itemCategory", show);
					} else {
						obj.put("size", 0);
					}
					mAll.put(strArray[j], obj);
				}
				objCategory.put("categoryPathAll",mAll);
			}
			return objCategory.toString();

		} catch (ManagerException e) {
			throw new ManagerException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemCategoryNameList (long)
	 */
	@Override

	public String getItemCategoryList(long parentId) throws ManagerException {
		try {

			JSONObject obj = new JSONObject();
			if (parentId == 0) {
				obj.put("categoryLevel", 0);
			} else {
				CategoryDO categoryDO = categoryCacheManager.getCategoryDOById(parentId);
				if (categoryDO.getCategoryLevel() == null || categoryDO.getCategoryLevel() < 0
						&& categoryDO.getCategoryLevel() > 4) {
					obj.put("size", 0);
					return obj.toString();
				}
				obj.put("categoryLevel", categoryDO.getCategoryLevel() + 1);
			}

			List<CategoryDO> ls = categoryCacheManager.getItemCategoryListByParentId(parentId);

			List<Map<String, Object>> show = new ArrayList<Map<String, Object>>();
			if (ls != null) {
				if(ls.size()>0){
					Collections.sort(ls, new Comparator<CategoryDO>() {
					public int compare(CategoryDO cpvd1, CategoryDO cpvd2) {
						return (int) (cpvd1.getSortOrder() - cpvd2.getSortOrder());
			            }
			        });
				}
				for (int i = 0; i < ls.size(); i++) {
					CategoryDO s = ls.get(i);
					String d = "";
//					for (int j = 0; j < s.getName().length(); j++) {
//						d += PingYinUtil.convert(String.valueOf(s.getName().charAt(j)));
//					}
					d = PingYinUtil.getPinYinHeadChar(s.getName());
					s.setSpell(d);
					Map m = new HashMap();
					m.put("id", s.getId());
					m.put("title", s.getName());
					m.put("spell", d);
					m.put("isleaf", s.getIsLeaf());
					show.add(m);
				}
				obj.put("size", ls.size());
				obj.put("itemCategory", show);
			} else {
				obj.put("size", 0);
			}

			return obj.toString();

		} catch (ManagerException e) {
			throw new ManagerException(e);
		}
	}

//	public static void main(String[] arg){
//		String ss = PingYinUtil.convert("啫");
//		System.out.println("ss");
//		String ss = "#3423434$@红色$@3";
//		String[] ssss = ss.split(Pattern.quote("$@"));
//		for(int i=0;i<ssss.length;i++){
//			System.out.println(ssss[i]);
//		}
//	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemAO#getItemSpuByKey(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Map getItemSpuByKey(Long keyPropertyId, Long keyPropertyValue) {
		try {
			return categoryManager.getItemSpuByKey(keyPropertyId, keyPropertyValue);
		} catch (ManagerException e) {
			log.error("获得商品 SPU", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemReleasedAo#itemReleased(com.yuwang. pinju.domain.item.itemInput)
	 */
	@Override
	public List<String> itemReleased(ItemInput itemInput, List<String> errorSKUMsg, List<SkuDO> skuList, List<CustomProValueDO> customSkuList) {
		try {

			List<String> errorMsg = itemValidator(itemInput,false);
			if(errorSKUMsg!=null&&errorSKUMsg.size()>0){
				errorMsg.addAll(errorSKUMsg);
			}
//			log.debug("开始SKU处理");
			
			if (errorMsg.size() > 0) {
				return errorMsg;
			}

			ItemDO itemDO = setItemDO(itemInput, true, false);
			
			//图片管理中图片url
			Map imageUrl = new HashMap();
			if(itemInput.getItemEditPicUrl()!=null&&itemInput.getItemEditPicUrl().length>0){
				for(int i=0;i<itemInput.getItemEditPicUrl().length;i++){
					if(!((itemInput.getItemEditPicUrl())[i]).equals("false")&&!(itemInput.getItemEditPicUrl())[i].equals("true")){
						String picurl = (itemInput.getItemEditPicUrl())[i].replace(PinjuConstant.VIEW_IMAGE_SERVER,"");
						imageUrl.put(i, picurl);
					}
				}
			}

			// 图片上传
//			String[] fileName = fileStorageManager.saveImage(itemInput.getPicFile(), itemInput.getPicFileFileName(),
//					itemInput.getSellerId(), "", true);
			List<ItemPicDO> itemPicList = new ArrayList<ItemPicDO>();
			File[] imagFile = new File[1];
			String[] imagFileName = new String[1];
			if(itemInput.getThisPicFile1()!=null){
				imagFile[0] = itemInput.getThisPicFile1();
				imagFileName[0] = itemInput.getThisPicFile1FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(0,fileName[0]);
			}
			if(itemInput.getThisPicFile2()!=null){
				imagFile[0] = itemInput.getThisPicFile2();
				imagFileName[0] = itemInput.getThisPicFile2FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(1,fileName[0]);
			}
			if(itemInput.getThisPicFile3()!=null){
				imagFile[0] = itemInput.getThisPicFile3();
				imagFileName[0] = itemInput.getThisPicFile3FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(2,fileName[0]);
			}
			if(itemInput.getThisPicFile4()!=null){
				imagFile[0] = itemInput.getThisPicFile4();
				imagFileName[0] = itemInput.getThisPicFile4FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(3,fileName[0]);
			}
			if(itemInput.getThisPicFile5()!=null){
				imagFile[0] = itemInput.getThisPicFile5();
				imagFileName[0] = itemInput.getThisPicFile5FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(4,fileName[0]);
			}
			
//			if(itemInput.getPicFile() != null && itemInput.getPicFile().length > 0){
//				String[] fileName = storageFileInfoManager.insertStorageFileInfo(itemInput.getPicFile(), itemInput.getPicFileFileName(), itemInput.getSellerId(), itemInput.getNickName(), 1);
//				
//				if(fileName==null||fileName.length<0){
//					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
//					return errorMsg;
//				}
//				
//				// 记录上传文件信息
////				fileInfoManager.addFileInfos(itemInput.getSellerId(), fileName, itemInput.getPicFile());
//				if (fileName != null && fileName.length > 0) {
//					if(imageUrl!=null&&imageUrl.size()>0&&imageUrl.get(0)!=null){
//						itemDO.setPicUrl((String)imageUrl.get(0));
//						imageUrl.remove(0);
//						for(int i=0; i<fileName.length; i++){
//							ItemPicDO ipDo = new ItemPicDO();
//							ipDo.setGmtCreate(DateUtil.current());
//							ipDo.setGmtModified(DateUtil.current());
//							ipDo.setPicUrl(fileName[i]);
//							ipDo.setSort(new Long(i));
//							ipDo.setPicLength(new Long(0));
//							ipDo.setPicWidth(new Long(0));
//							ipDo.setPicSize(new Long(0));
//							itemPicList.add(ipDo);	
//						}
//					}else{
//						itemDO.setPicUrl(fileName[0]);
//						for(int i=0; i<fileName.length; i++){
//							if(i!=0){
//								ItemPicDO ipDo = new ItemPicDO();
//								ipDo.setGmtCreate(DateUtil.current());
//								ipDo.setGmtModified(DateUtil.current());
//								ipDo.setPicUrl(fileName[i]);
//								ipDo.setSort(new Long(i));
//								ipDo.setPicLength(new Long(0));
//								ipDo.setPicWidth(new Long(0));
//								ipDo.setPicSize(new Long(0));
//								itemPicList.add(ipDo);	
//							}
//						}
//					}
//				}
//			}
			
			if(imageUrl!=null&&imageUrl.size()>0){
				Boolean imageUrlFlag = true;
				if(imageUrl.get(0)!=null){
					itemDO.setPicUrl((String)imageUrl.get(0));
					imageUrl.remove(0);
				}else{
					if(imageUrl.get(1)!=null){
						itemDO.setPicUrl((String)imageUrl.get(1));
						imageUrl.remove(1);
					}else{
						if(imageUrl.get(2)!=null){
							itemDO.setPicUrl((String)imageUrl.get(2));
							imageUrl.remove(2);
						}else{
							if(imageUrl.get(3)!=null){
								itemDO.setPicUrl((String)imageUrl.get(3));
								imageUrl.remove(3);
							}else{
								if(imageUrl.get(4)!=null){
									itemDO.setPicUrl((String)imageUrl.get(4));
									imageUrl.remove(4);
									imageUrlFlag = false;
								}
							}	
						}
					}
				}
				if(imageUrlFlag){
					Iterator it = imageUrl.entrySet().iterator();    
					while (it.hasNext()){    
						Map.Entry pairs = (Map.Entry)it.next();
						ItemPicDO ipDo = new ItemPicDO();
						ipDo.setGmtCreate(DateUtil.current());
						ipDo.setGmtModified(DateUtil.current());
						ipDo.setPicUrl((String)pairs.getValue());
//						ipDo.setSort(new Long(sort));
						ipDo.setPicLength(new Long(0));
						ipDo.setPicWidth(new Long(0));
						ipDo.setPicSize(new Long(0));
						itemPicList.add(ipDo);
					 }
				}
			}
			
			List<ItemPicDO> addItemPicList = new ArrayList<ItemPicDO>();
			
			if(itemPicList!=null&&itemPicList.size()>0){
				for(int i =0;i<itemPicList.size();i++){
					ItemPicDO ipDo = itemPicList.get(i);
					ipDo.setSort(Long.valueOf(i+1));
					addItemPicList.add(ipDo);
				}
			}

			long itemResult = itemManager.itemReleased(itemDO, skuList, addItemPicList, customSkuList);

			return errorMsg;

		} catch (ManagerException e) {
			log.error("发布商品错误", e);
			return null;
		} catch (Exception e) {
			log.error("发布商品错误", e);
			return null;
		}

	}
	@Override
	public List<String> itemReleasedByOut(ItemInput itemInput) throws ApiException {
		try {
			
			List<String> errorMsg = itemValidator(itemInput,true);
			
			if(itemInput.getCode()!=null&&!itemInput.getCode().equals("")){
				if(itemInput.getCode().length()>30&&itemInput.getCode().length()<=100){
					errorMsg.add("商品输入验证错误：商家编码长度不符");
				}
			}
			
			if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {
				itemInput.setPropertyValuePair(itemInput.getPropertyValuePair().replaceAll("-", ","));
			}
			
			Boolean shopFalg = true;
			
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(itemInput.getSellerId(), null);
			if (shopInfoDO != null && shopInfoDO.getShopId() != null
					&& shopInfoDO.getApproveStatus() != null 
					&& (shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_YES || shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_HEGUI)) {
//				air.addMessage("image_invalid","用户已经开店，请先完成开店流程");
//				return "validateError";
			}else{
				shopFalg = false;
				errorMsg.add("商品输入验证错误：用户未开店，请先完成开店流程");
			}
			
			Boolean falg = shopShieldManager.getShieldInfoByUserId(itemInput.getSellerId());
			if(falg&&itemInput.getReleasedType()==1){
				errorMsg.add("商品输入验证错误：店铺屏蔽中暂不能做上架商品的操作");
			}
			
			List<SkuDO> skuList = new ArrayList<SkuDO>();
			if(shopFalg){
				//商品图片验证
				if (itemInput.getPicFile() == null || itemInput.getPicFile().length == 0) {
					errorMsg.add("商品输入验证错误：缺少商品图片");
				} else {
					for (File f : itemInput.getPicFile()) {
						// 大小检验
						if (f.length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
							errorMsg.add("商品输入验证错误：商品图片大小不能超过500K");
						}
						// 类型检验
						if (!FileSecurityUtils.isImageValid(f)) {
							errorMsg.add("商品输入验证错误：商品图片格式错误");
						}
					}
				}
				
				// 验证基本属性
				List<CategoryPropertyDO> categoryPropertyList;

				categoryPropertyList = categoryManager.getItemCategoryProperty(itemInput.getCatetoryId());
				if(categoryPropertyList!=null&&categoryPropertyList.size()>0){
					//类目ID
					List ls = new ArrayList();
					//值ID
					Map valueMap = new HashMap();
					//必填属性
					List requiredList = new ArrayList();
					Map<String, String> selPropertyMap = new HashMap<String, String>();
					if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {
						log.debug("处理商品类目属性字符串：" + itemInput.getPropertyValuePair());
						String pros[] = itemInput.getPropertyValuePair().split(";");
						for (String cpId : pros) {
							selPropertyMap.put(cpId.split(":")[0], cpId.split(":")[1]);
						}
					}
					
					for (CategoryPropertyDO categoryPropertyDO : categoryPropertyList) {
	//						if (categoryPropertyDO.getIsEnumerate() == CategoryPropertyDO.IS_NO) {
							ls.add(String.valueOf(categoryPropertyDO.getId()));
	//						}
						if(categoryPropertyDO.getRequired()==1){
							requiredList.add(String.valueOf(categoryPropertyDO.getId()));
						}
						//获取值系统ID
						List<CategoryPropertyQuery> childPropertyList = getChildPropertyList(categoryPropertyDO, selPropertyMap);
	
						if (childPropertyList != null && childPropertyList.size() > 0) {
							//类目值ID
							List valueLs = new ArrayList();
							for (CategoryPropertyQuery childQuery : childPropertyList) {
	
								if (childQuery.getIsEnumerate() == CategoryPropertyDO.IS_YES
										&& childQuery.getIsMultipleChoice() != 1
										&& (childQuery.getCpvList() == null || childQuery.getCpvList().size() < 1)) {
									continue;
								}
								for(CategoryPropertyValueQuery cpvQuery : childQuery.getCpvList()){
									valueLs.add(cpvQuery.getCpvId());
								}
							}
							valueMap.put(categoryPropertyDO.getId(),valueLs);
						}
					}
	
					if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {
	
						String propertyValue = itemInput.getPropertyValuePair();
	//					StringBuffer attr = new StringBuffer();
						//将输入属性分割成属性数组
						String[] pvs = propertyValue.split(";");
						if(pvs!=null&&pvs.length>0){
							for(int i =0 ; i<pvs.length;i ++){
								//将属性数组中的
								String[] cpIds = pvs[i].split(":");
								CategoryPropertyDO cpDO = new CategoryPropertyDO();
								if(cpIds!=null&&cpIds.length>0){
									boolean isFlag = true;
									StringBuffer cpValue = new StringBuffer();
									for(int j=0;j<cpIds.length;j++){
										//验证属性ID是否存在
										if (ls.contains(cpIds[0])) {
											if(isFlag){
												requiredList.remove(cpIds[0]);
												cpDO = categoryManager.getItemCategoryPropertyById(Long.parseLong(cpIds[0]));
	//											attr.append(cpIds[0]);
	//											attr.append(":");
											}
											isFlag = false;
											if(j!=0){
												String[] cpvIds = cpIds[j].split(",");
												for(int g=0;g<cpvIds.length;g++){
													//验证属性值ID是否存在
													if(NumberUtil.isDouble(cpvIds[g])){
														BaseValueDO bvDo = categoryManager.getBaseValueById(Long.parseLong(cpvIds[g]));
														if(valueMap.get(Long.parseLong(cpIds[0]))!=null){
															List valueList = (List) valueMap.get(Long.parseLong(cpIds[0]));
															if (valueList.contains(cpvIds[g])) {
																if(bvDo!=null&&bvDo.getId()>0){
																	if(cpValue.length()>0){
																		cpValue.append(",");
																	}
																	cpValue.append(String.valueOf(bvDo.getId()));
																}else{
																	errorMsg.add("属性值验证失败：输入的属性值ID-"+cpvIds[g]+"无效");
																}
															}else{
																errorMsg.add("属性值验证失败：输入的属性值ID-"+cpvIds[g]+"不是属性ID-"+cpIds[0]+"下的值");
															}
														}else{
															if(bvDo!=null&&bvDo.getId()>0){
																if(cpValue.length()>0){
																	cpValue.append(",");
																}
																cpValue.append(String.valueOf(bvDo.getId()));
															}else{
																errorMsg.add("属性值验证失败：输入的属性值ID-"+cpvIds[g]+"无效");
															}
														}
													}	
												}
											}
											
	//										attr.append(cpValue);
										}else{
											errorMsg.add("属性验证失败：输入的属性ID-"+cpIds[0]+"无效");
											break;
										}
	//									if(j==cpIds.length-1||isFlag){
	//										attr.append(";");
	//									}
									}
									if(cpDO.getRequired()!=null&&cpDO.getRequired()==1&&cpValue.length()<1){
//										errorMsg.add("属性值验证失败：输入的属性ID-"+cpIds[0]+"为必填属性");
										itemInput.setCatetoryId(-1L);
									}
								}
							}
						}
					}
					
					// 验证SKU
					long totalCapacity = 0;
					Money minPrice = null;
					log.debug("开始SKU处理");
					if (itemInput.getSkuList() != null && itemInput.getSkuList().length > 0) {
						// 获得SKU输入
						for (int i = 0; i < itemInput.getSkuList().length; i++ ) {
							SkuDO skuDO = itemInput.getSkuList()[i];
							if(skuDO.getSkuPrice()!=null){
								String price = skuDO.getSkuPrice();
								log.debug("获取SKU输入price:" + price);
								if (EmptyUtil.isBlank(price)) {
									skuDO.setPrice(new Money(0).getCent());
								} else {
									if(NumberUtil.isDouble(price)){
										NumberFormat formatter = new DecimalFormat("#0.00");
										price = formatter.format(Double.parseDouble(price));
										if (minPrice == null || minPrice.compareTo(new Money(price)) == 1) {
											minPrice = new Money(price);
										}
										if(Double.parseDouble(price)<0.1){
											errorMsg.add("SKU验证失败：输入的"+price+"价格不能小于0.1元");
										}
										skuDO.setPrice(new Money(price).getCent());
									}else{
										errorMsg.add("SKU验证失败：输入的"+price+"价格无效");
									}
								}
							}else{
								errorMsg.add("SKU验证失败：未输入SKU的价格");
							}
							if(skuDO.getOriStock()!=null){
								String capacity = skuDO.getOriStock().toString();
								log.debug("获取SKU输入capacity:" + capacity);
								if (EmptyUtil.isBlank(capacity)) {
									skuDO.setOriStock(0l);
									skuDO.setCurrentStock(0l);
								} else {
									if(NumberUtil.isLong(capacity)){
										totalCapacity += Long.valueOf(capacity);
										if(Long.valueOf(capacity)<0){
											errorMsg.add("SKU验证失败：输入的"+capacity+"价格不能小于0");
										}
										skuDO.setOriStock(Long.valueOf(capacity));
										skuDO.setCurrentStock(Long.valueOf(capacity));
									}else{
										errorMsg.add("SKU验证失败：输入的"+capacity+"数量无效");
									}
								}
							}else{
								errorMsg.add("SKU验证失败：未输入SKU的数量");
							}
							
							if(skuDO.getSalePv1()!=null&&!skuDO.getSalePv1().equals("")){
								String[] valueIds = skuDO.getSalePv1().split(":");
								if(valueIds!=null&&valueIds.length==2){
									try{
										Long cpDOId = Long.parseLong(valueIds[0]);
										Long bvDOId = Long.parseLong(valueIds[1]);
										CategoryPropertyDO cpDo = categoryManager.getItemCategoryPropertyById(cpDOId);
										BaseValueDO bvDo = categoryManager.getBaseValueById(bvDOId);
										if(cpDo!=null&&bvDo!=null&&cpDo.getId().equals(cpDOId)&&bvDo.getId().equals(bvDOId)){
											if(!cpDo.getCategoryId().equals(itemInput.getCatetoryId())){
												errorMsg.add("SKU验证失败：输入的SalePv1属性ID-"+cpDOId+"不是该类目下的SKU");
											}else{
												//属性ID传输正确
												requiredList.remove(String.valueOf(cpDOId));
												if(valueMap.get(cpDOId)!=null){
													List valueList = (List) valueMap.get(cpDOId);
													if (valueList.contains(bvDOId.toString())) {
														//存在，正常
													}else{
														errorMsg.add("SKU验证失败：输入的SalePv1属性值ID-"+bvDOId+"不是属性ID-"+cpDOId+"下的值");
													}
												}
											}
										}else{
											//属性ID传输不正确
											errorMsg.add("SKU验证失败：输入的SalePv1值无效");
										}
									}catch(NumberFormatException e){
										errorMsg.add("SKU验证失败：输入的SalePv1值无效");
									}
								}else{
									errorMsg.add("SKU验证失败：输入的SalePv1值无效");
								}
							}
							
							if(skuDO.getSalePv2()!=null&&!skuDO.getSalePv2().equals("")){
								String[] valueIds = skuDO.getSalePv2().split(":");
								if(valueIds!=null&&valueIds.length==2){
									try{
										Long cpDOId = Long.parseLong(valueIds[0]);
										Long bvDOId = Long.parseLong(valueIds[1]);
										CategoryPropertyDO cpDo = categoryManager.getItemCategoryPropertyById(cpDOId);
										BaseValueDO bvDo = categoryManager.getBaseValueById(bvDOId);
										if(cpDo!=null&&bvDo!=null&&cpDo.getId().equals(cpDOId)&&bvDo.getId().equals(bvDOId)){
											if(!cpDo.getCategoryId().equals(itemInput.getCatetoryId())){
												errorMsg.add("SKU验证失败：输入的SalePv2属性ID-"+cpDOId+"不是该类目下的SKU");
											}else{
												//属性ID传输正确
												requiredList.remove(String.valueOf(cpDOId));
												if(valueMap.get(cpDOId)!=null){
													List valueList = (List) valueMap.get(cpDOId);
													if (valueList.contains(bvDOId.toString())) {
														//存在，正常
													}else{
														errorMsg.add("SKU验证失败：输入的SalePv2属性值ID-"+bvDOId+"不是属性ID-"+cpDOId+"下的值");
													}
												}
											}
										}else{
											//属性ID传输不正确
											errorMsg.add("SKU验证失败：输入的SalePv2值无效");
										}
									}catch(NumberFormatException e){
										errorMsg.add("SKU验证失败：输入的SalePv2值无效");
									}
								}else{
									errorMsg.add("SKU验证失败：输入的SalePv2值无效");
								}
							}
							
							if(skuDO.getSalePv3()!=null&&!skuDO.getSalePv3().equals("")){
								String[] valueIds = skuDO.getSalePv3().split(":");
								if(valueIds!=null&&valueIds.length==2){
									try{
										Long cpDOId = Long.parseLong(valueIds[0]);
										Long bvDOId = Long.parseLong(valueIds[1]);
										CategoryPropertyDO cpDo = categoryManager.getItemCategoryPropertyById(cpDOId);
										BaseValueDO bvDo = categoryManager.getBaseValueById(bvDOId);
										if(cpDo!=null&&bvDo!=null&&cpDo.getId().equals(cpDOId)&&bvDo.getId().equals(bvDOId)){
											if(!cpDo.getCategoryId().equals(itemInput.getCatetoryId())){
												errorMsg.add("SKU验证失败：输入的SalePv3属性ID-"+cpDOId+"不是该类目下的SKU");
											}else{
												//属性ID传输正确
												requiredList.remove(String.valueOf(cpDOId));
												if(valueMap.get(cpDOId)!=null){
													List valueList = (List) valueMap.get(cpDOId);
													if (valueList.contains(bvDOId.toString())) {
														//存在，正常
													}else{
														errorMsg.add("SKU验证失败：输入的SalePv3属性值ID-"+bvDOId+"不是属性ID-"+cpDOId+"下的值");
													}
												}
											}
										}else{
											//属性ID传输不正确
											errorMsg.add("SKU验证失败：输入的SalePv3值无效");
										}
									}catch(NumberFormatException e){
										errorMsg.add("SKU验证失败：输入的SalePv3值无效");
									}
								}else{
									errorMsg.add("SKU验证失败：输入的SalePv3值无效");
								}
							}
							
							if(skuDO.getSalePv4()!=null&&!skuDO.getSalePv4().equals("")){
								String[] valueIds = skuDO.getSalePv4().split(":");
								if(valueIds!=null&&valueIds.length==2){
									try{
										Long cpDOId = Long.parseLong(valueIds[0]);
										Long bvDOId = Long.parseLong(valueIds[1]);
										CategoryPropertyDO cpDo = categoryManager.getItemCategoryPropertyById(cpDOId);
										BaseValueDO bvDo = categoryManager.getBaseValueById(bvDOId);
										if(cpDo!=null&&bvDo!=null&&cpDo.getId().equals(cpDOId)&&bvDo.getId().equals(bvDOId)){
											if(!cpDo.getCategoryId().equals(itemInput.getCatetoryId())){
												errorMsg.add("SKU验证失败：输入的SalePv4属性ID-"+cpDOId+"不是该类目下的SKU");
											}else{
												//属性ID传输正确
												requiredList.remove(String.valueOf(cpDOId));
												if(valueMap.get(cpDOId)!=null){
													List valueList = (List) valueMap.get(cpDOId);
													if (valueList.contains(bvDOId.toString())) {
														//存在，正常
													}else{
														errorMsg.add("SKU验证失败：输入的SalePv4属性值ID-"+bvDOId+"不是属性ID-"+cpDOId+"下的值");
													}
												}
											}
										}else{
											//属性ID传输不正确
											errorMsg.add("SKU验证失败：输入的SalePv4值无效");
										}
									}catch(NumberFormatException e){
										errorMsg.add("SKU验证失败：输入的SalePv4值无效");
									}
								}else{
									errorMsg.add("SKU验证失败：输入的SalePv4值无效");
								}
							}
							if(skuDO.getSellerCode()!=null&&!skuDO.getSellerCode().equals("")){
								if(skuDO.getSellerCode().length()>30){
									errorMsg.add("SKU验证失败：商家编码长度不符");
								}
							}
							skuDO.setGmtCreate(DateUtil.current());
							skuDO.setGmtModified(DateUtil.current());
							skuDO.setStatus(1l);
							skuList.add(skuDO);
						}
						log.debug("SKU对象数量" + skuList.size());
						log.debug("SKU数量和" + totalCapacity);
						log.debug("SKU最小价格" + minPrice);
	
						if (minPrice == null || new Money(itemInput.getPrice()).compareTo(minPrice) == -1) {
							errorMsg.add("SKU验证失败：输入价格小于最小销售屬性价格");
						}
						if (totalCapacity == 0 || Long.parseLong(itemInput.getNumber()) < totalCapacity) {
							errorMsg.add("SKU验证失败：输入数量小于销售屬性数量之和");
						}
					}
					
					if(requiredList!=null&&requiredList.size()>0){
						for(int i=0;i<requiredList.size();i++){
//							errorMsg.add("属性验证失败：属性ID-"+requiredList.get(i)+"为必填属性");
							itemInput.setCatetoryId(-1L);
						}
					}
				}
			}
			
			if (errorMsg.size() > 0) {
				return errorMsg;
			}
			
			ItemDO itemDO = setItemDO(itemInput, true, true);
			
			// 图片上传
//			String[] fileName = fileStorageManager.saveImage(itemInput.getPicFile(), itemInput.getPicFileFileName(),
//					itemInput.getSellerId(), "", true);
			String[] fileName = storageFileInfoManager.insertStorageFileInfo(itemInput.getPicFile(), itemInput.getPicFileFileName(), itemInput.getSellerId(), itemInput.getNickName(), 1);
			
			if(fileName==null||fileName.length<0){
				errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
				return errorMsg;
			}
			// 记录上传文件信息
//			fileInfoManager.addFileInfos(itemInput.getSellerId(), fileName, itemInput.getPicFile());
			List<ItemPicDO> itemPicList = new ArrayList<ItemPicDO>();
			if (fileName != null && fileName.length > 0) {
				itemDO.setPicUrl(fileName[0]);
				for(int i=0; i<fileName.length; i++){
					if(i!=0){
						ItemPicDO ipDo = new ItemPicDO();
						ipDo.setGmtCreate(DateUtil.current());
						ipDo.setGmtModified(DateUtil.current());
						ipDo.setPicUrl(fileName[i]);
						ipDo.setSort(new Long(i));
						ipDo.setPicLength(new Long(0));
						ipDo.setPicWidth(new Long(0));
						ipDo.setPicSize(new Long(0));
						itemPicList.add(ipDo);	
					}
				}
			}
			List<CustomProValueDO> customSkuList = new ArrayList<CustomProValueDO>();
			long itemResult = itemManager.itemReleased(itemDO, skuList, itemPicList, customSkuList);
			
			return errorMsg;
			
		} catch (ManagerException e) {
//			log.error("发布商品by out错误", e);
			throw new ApiException("图片上传失败",e);
		} catch (Exception e) {
//			log.error("发布商品by out错误", e);
			throw new ApiException("发布商品失败",e);
//			return null;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemAO#itemUpdateByOut(com.yuwang.pinju.domain .item.ItemInput)
	 */
	@Override
	public List<String> itemUpdateByOut(ItemInput itemInput) throws ApiException {
		try {
			List<String> errorMsg = new ArrayList<String>();

			ItemDO itemDO = itemManager.getItemDOById(itemInput.getId());
			if (itemDO == null) {
				errorMsg.add("商品更新：无效的商品。");
				return errorMsg;
			}
			if (!itemDO.getSellerId().equals(itemInput.getSellerId())) {
				errorMsg.add("商品更新：错误的卖家编号。");
				return errorMsg;
			}
			itemInput.setCatetoryId(itemDO.getCatetoryId());
			//暂不支持自定义SKU更新商品
			int customSku = 0;
			customSku = customProValueManager.selectItemCustomProValueCountList(itemInput.getId());
			if(customSku>0){
				errorMsg.add("商品更新：暂不支持存在自定义SKU的商品更新，请到网页修改。");
				return errorMsg;
			}
			errorMsg = itemValidator(itemInput,false);
			if(itemInput.getCode()!=null&&!itemInput.getCode().equals("")){
				if(itemInput.getCode().length()>30&&itemInput.getCode().length()<=100){
					errorMsg.add("商品输入验证错误：商家编码长度不符");
				}
			}
			
			if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {
				itemInput.setPropertyValuePair(itemInput.getPropertyValuePair().replaceAll("-", ","));
			}
			
			Boolean shopFalg = true;
			
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(itemInput.getSellerId(), null);
			if (shopInfoDO != null && shopInfoDO.getShopId() != null
					&& shopInfoDO.getApproveStatus() != null 
					&& (shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_YES || shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_HEGUI)) {
//				air.addMessage("image_invalid","用户已经开店，请先完成开店流程");
//				return "validateError";
			}else{
				shopFalg = false;
				errorMsg.add("商品输入验证错误：用户未开店，请先完成开店流程");
			}
			
			Boolean falg = shopShieldManager.getShieldInfoByUserId(itemInput.getSellerId());
			if(falg&&itemInput.getReleasedType()==1){
				errorMsg.add("商品输入验证错误：店铺屏蔽中暂不能做上架商品的操作");
			}
			List<SkuDO> skuList = new ArrayList<SkuDO>();
			if(shopFalg){
				// 验证基本属性
				List<CategoryPropertyDO> categoryPropertyList;

				categoryPropertyList = categoryManager.getItemCategoryProperty(itemInput.getCatetoryId());
				
				//类目ID
				List ls = new ArrayList();
				//值ID
				Map valueMap = new HashMap();
				//必填属性
				List requiredList = new ArrayList();
				Map<String, String> selPropertyMap = new HashMap<String, String>();
				if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {
					log.debug("处理商品类目属性字符串：" + itemInput.getPropertyValuePair());
					String pros[] = itemInput.getPropertyValuePair().split(";");
					for (String cpId : pros) {
						selPropertyMap.put(cpId.split(":")[0], cpId.split(":")[1]);
					}
				}
				if(categoryPropertyList!=null&&categoryPropertyList.size()>0){
					for (CategoryPropertyDO categoryPropertyDO : categoryPropertyList) {
//						if (categoryPropertyDO.getIsEnumerate() == CategoryPropertyDO.IS_NO) {
							ls.add(String.valueOf(categoryPropertyDO.getId()));
//						}
						if(categoryPropertyDO.getRequired()==1){
							requiredList.add(String.valueOf(categoryPropertyDO.getId()));
						}
						//获取值系统ID
						List<CategoryPropertyQuery> childPropertyList = getChildPropertyList(categoryPropertyDO, selPropertyMap);

						if (childPropertyList != null && childPropertyList.size() > 0) {
							//类目值ID
							List valueLs = new ArrayList();
							for (CategoryPropertyQuery childQuery : childPropertyList) {

								if (childQuery.getIsEnumerate() == CategoryPropertyDO.IS_YES
										&& childQuery.getIsMultipleChoice() != 1
										&& (childQuery.getCpvList() == null || childQuery.getCpvList().size() < 1)) {
									continue;
								}
								for(CategoryPropertyValueQuery cpvQuery : childQuery.getCpvList()){
									valueLs.add(cpvQuery.getCpvId());
								}
							}
							valueMap.put(categoryPropertyDO.getId(),valueLs);
						}
					}
				}else{
					errorMsg.add("商品输入验证错误：类目无效暂不能做更新商品的操作");
					return errorMsg;
				}

				if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {

					String propertyValue = itemInput.getPropertyValuePair();
//					StringBuffer attr = new StringBuffer();
					//将输入属性分割成属性数组
					String[] pvs = propertyValue.split(";");
					if(pvs!=null&&pvs.length>0){
						for(int i =0 ; i<pvs.length;i ++){
							//将属性数组中的
							String[] cpIds = pvs[i].split(":");
							CategoryPropertyDO cpDO = new CategoryPropertyDO();
							if(cpIds!=null&&cpIds.length>0){
								boolean isFlag = true;
								StringBuffer cpValue = new StringBuffer();
								for(int j=0;j<cpIds.length;j++){
									//验证属性ID是否存在
									if (ls.contains(cpIds[0])) {
										if(isFlag){
											requiredList.remove(cpIds[0]);
											cpDO = categoryManager.getItemCategoryPropertyById(Long.parseLong(cpIds[0]));
//											attr.append(cpIds[0]);
//											attr.append(":");
										}
										isFlag = false;
										if(j!=0){
											String[] cpvIds = cpIds[j].split(",");
											for(int g=0;g<cpvIds.length;g++){
												//验证属性值ID是否存在
												if(NumberUtil.isDouble(cpvIds[g])){
													BaseValueDO bvDo = categoryManager.getBaseValueById(Long.parseLong(cpvIds[g]));
													if(valueMap.get(Long.parseLong(cpIds[0]))!=null){
														List valueList = (List) valueMap.get(Long.parseLong(cpIds[0]));
														if (valueList.contains(cpvIds[g])) {
															if(bvDo!=null&&bvDo.getId()>0){
																if(cpValue.length()>0){
																	cpValue.append(",");
																}
																cpValue.append(String.valueOf(bvDo.getId()));
															}else{
																errorMsg.add("属性值验证失败：输入的属性值ID-"+cpvIds[g]+"无效");
															}
														}else{
															errorMsg.add("属性值验证失败：输入的属性值ID-"+cpvIds[g]+"不是属性ID-"+cpIds[0]+"下的值");
														}
													}else{
														if(bvDo!=null&&bvDo.getId()>0){
															if(cpValue.length()>0){
																cpValue.append(",");
															}
															cpValue.append(String.valueOf(bvDo.getId()));
														}else{
															errorMsg.add("属性值验证失败：输入的属性值ID-"+cpvIds[g]+"无效");
														}
													}
												}	
											}
										}
										
//										attr.append(cpValue);
									}else{
										errorMsg.add("属性验证失败：输入的属性ID-"+cpIds[0]+"无效");
										break;
									}
//									if(j==cpIds.length-1||isFlag){
//										attr.append(";");
//									}
								}
								if(cpDO.getRequired()!=null&&cpDO.getRequired()==1&&cpValue.length()<1){
									errorMsg.add("属性值验证失败：输入的属性ID-"+cpIds[0]+"为必填属性");
								}
							}
						}
					}
				}
				
				// 验证SKU
				long totalCapacity = 0;
				Money minPrice = null;
				log.debug("开始SKU处理");
				if (itemInput.getSkuList() != null && itemInput.getSkuList().length > 0) {
					// 获得SKU输入
					for (int i = 0; i < itemInput.getSkuList().length; i++ ) {
						SkuDO skuDO = itemInput.getSkuList()[i];
						if(skuDO.getSkuPrice()!=null){
							String price = skuDO.getSkuPrice();
							log.debug("获取SKU输入price:" + price);
							if (EmptyUtil.isBlank(price)) {
								skuDO.setPrice(new Money(0).getCent());
							} else {
								if(NumberUtil.isDouble(price)){
									NumberFormat formatter = new DecimalFormat("#0.00");
									price = formatter.format(Double.parseDouble(price));
									if (minPrice == null || minPrice.compareTo(new Money(price)) == 1) {
										minPrice = new Money(price);
									}
									if(Double.parseDouble(price)<0.1){
										errorMsg.add("SKU验证失败：输入的"+price+"价格不能小于0.1元");
									}
									skuDO.setPrice(new Money(price).getCent());
								}else{
									errorMsg.add("SKU验证失败：输入的"+price+"价格无效");
								}
							}
						}else{
							errorMsg.add("SKU验证失败：未输入SKU的价格");
						}
						if(skuDO.getOriStock()!=null){
							String capacity = skuDO.getOriStock().toString();
							log.debug("获取SKU输入capacity:" + capacity);
							if (EmptyUtil.isBlank(capacity)) {
								skuDO.setOriStock(0l);
								skuDO.setCurrentStock(0l);
							} else {
								if(NumberUtil.isLong(capacity)){
									totalCapacity += Long.valueOf(capacity);
									if(Long.valueOf(capacity)<0){
										errorMsg.add("SKU验证失败：输入的"+capacity+"价格不能小于0");
									}
									skuDO.setOriStock(Long.valueOf(capacity));
									skuDO.setCurrentStock(Long.valueOf(capacity));
								}else{
									errorMsg.add("SKU验证失败：输入的"+capacity+"数量无效");
								}
							}
						}else{
							errorMsg.add("SKU验证失败：未输入SKU的数量");
						}
						
						if(skuDO.getSalePv1()!=null&&!skuDO.getSalePv1().equals("")){
							String[] valueIds = skuDO.getSalePv1().split(":");
							if(valueIds!=null&&valueIds.length==2){
								try{
									Long cpDOId = Long.parseLong(valueIds[0]);
									Long bvDOId = Long.parseLong(valueIds[1]);
									CategoryPropertyDO cpDo = categoryManager.getItemCategoryPropertyById(cpDOId);
									BaseValueDO bvDo = categoryManager.getBaseValueById(bvDOId);
									if(cpDo!=null&&bvDo!=null&&cpDo.getId().equals(cpDOId)&&bvDo.getId().equals(bvDOId)){
										if(!cpDo.getCategoryId().equals(itemInput.getCatetoryId())){
											errorMsg.add("SKU验证失败：输入的SalePv1属性ID-"+cpDOId+"不是该类目下的SKU");
										}else{
											//属性ID传输正确
											requiredList.remove(String.valueOf(cpDOId));
											if(valueMap.get(cpDOId)!=null){
												List valueList = (List) valueMap.get(cpDOId);
												if (valueList.contains(bvDOId.toString())) {
													//存在，正常
												}else{
													errorMsg.add("SKU验证失败：输入的SalePv1属性值ID-"+bvDOId+"不是属性ID-"+cpDOId+"下的值");
												}
											}
										}
									}else{
										//属性ID传输不正确
										errorMsg.add("SKU验证失败：输入的SalePv1值无效");
									}
								}catch(NumberFormatException e){
									errorMsg.add("SKU验证失败：输入的SalePv1值无效");
								}
							}else{
								errorMsg.add("SKU验证失败：输入的SalePv1值无效");
							}
						}
						
						if(skuDO.getSalePv2()!=null&&!skuDO.getSalePv2().equals("")){
							String[] valueIds = skuDO.getSalePv2().split(":");
							if(valueIds!=null&&valueIds.length==2){
								try{
									Long cpDOId = Long.parseLong(valueIds[0]);
									Long bvDOId = Long.parseLong(valueIds[1]);
									CategoryPropertyDO cpDo = categoryManager.getItemCategoryPropertyById(cpDOId);
									BaseValueDO bvDo = categoryManager.getBaseValueById(bvDOId);
									if(cpDo!=null&&bvDo!=null&&cpDo.getId().equals(cpDOId)&&bvDo.getId().equals(bvDOId)){
										if(!cpDo.getCategoryId().equals(itemInput.getCatetoryId())){
											errorMsg.add("SKU验证失败：输入的SalePv2属性ID-"+cpDOId+"不是该类目下的SKU");
										}else{
											//属性ID传输正确
											requiredList.remove(String.valueOf(cpDOId));
											if(valueMap.get(cpDOId)!=null){
												List valueList = (List) valueMap.get(cpDOId);
												if (valueList.contains(bvDOId.toString())) {
													//存在，正常
												}else{
													errorMsg.add("SKU验证失败：输入的SalePv2属性值ID-"+bvDOId+"不是属性ID-"+cpDOId+"下的值");
												}
											}
										}
									}else{
										//属性ID传输不正确
										errorMsg.add("SKU验证失败：输入的SalePv2值无效");
									}
								}catch(NumberFormatException e){
									errorMsg.add("SKU验证失败：输入的SalePv2值无效");
								}
							}else{
								errorMsg.add("SKU验证失败：输入的SalePv2值无效");
							}
						}
						
						if(skuDO.getSalePv3()!=null&&!skuDO.getSalePv3().equals("")){
							String[] valueIds = skuDO.getSalePv3().split(":");
							if(valueIds!=null&&valueIds.length==2){
								try{
									Long cpDOId = Long.parseLong(valueIds[0]);
									Long bvDOId = Long.parseLong(valueIds[1]);
									CategoryPropertyDO cpDo = categoryManager.getItemCategoryPropertyById(cpDOId);
									BaseValueDO bvDo = categoryManager.getBaseValueById(bvDOId);
									if(cpDo!=null&&bvDo!=null&&cpDo.getId().equals(cpDOId)&&bvDo.getId().equals(bvDOId)){
										if(!cpDo.getCategoryId().equals(itemInput.getCatetoryId())){
											errorMsg.add("SKU验证失败：输入的SalePv3属性ID-"+cpDOId+"不是该类目下的SKU");
										}else{
											//属性ID传输正确
											requiredList.remove(String.valueOf(cpDOId));
											if(valueMap.get(cpDOId)!=null){
												List valueList = (List) valueMap.get(cpDOId);
												if (valueList.contains(bvDOId.toString())) {
													//存在，正常
												}else{
													errorMsg.add("SKU验证失败：输入的SalePv3属性值ID-"+bvDOId+"不是属性ID-"+cpDOId+"下的值");
												}
											}
										}
									}else{
										//属性ID传输不正确
										errorMsg.add("SKU验证失败：输入的SalePv3值无效");
									}
								}catch(NumberFormatException e){
									errorMsg.add("SKU验证失败：输入的SalePv3值无效");
								}
							}else{
								errorMsg.add("SKU验证失败：输入的SalePv3值无效");
							}
						}
						
						if(skuDO.getSalePv4()!=null&&!skuDO.getSalePv4().equals("")){
							String[] valueIds = skuDO.getSalePv4().split(":");
							if(valueIds!=null&&valueIds.length==2){
								try{
									Long cpDOId = Long.parseLong(valueIds[0]);
									Long bvDOId = Long.parseLong(valueIds[1]);
									CategoryPropertyDO cpDo = categoryManager.getItemCategoryPropertyById(cpDOId);
									BaseValueDO bvDo = categoryManager.getBaseValueById(bvDOId);
									if(cpDo!=null&&bvDo!=null&&cpDo.getId().equals(cpDOId)&&bvDo.getId().equals(bvDOId)){
										if(!cpDo.getCategoryId().equals(itemInput.getCatetoryId())){
											errorMsg.add("SKU验证失败：输入的SalePv4属性ID-"+cpDOId+"不是该类目下的SKU");
										}else{
											//属性ID传输正确
											requiredList.remove(String.valueOf(cpDOId));
											if(valueMap.get(cpDOId)!=null){
												List valueList = (List) valueMap.get(cpDOId);
												if (valueList.contains(bvDOId.toString())) {
													//存在，正常
												}else{
													errorMsg.add("SKU验证失败：输入的SalePv4属性值ID-"+bvDOId+"不是属性ID-"+cpDOId+"下的值");
												}
											}
										}
									}else{
										//属性ID传输不正确
										errorMsg.add("SKU验证失败：输入的SalePv4值无效");
									}
								}catch(NumberFormatException e){
									errorMsg.add("SKU验证失败：输入的SalePv4值无效");
								}
							}else{
								errorMsg.add("SKU验证失败：输入的SalePv4值无效");
							}
						}
						
						skuDO.setGmtCreate(DateUtil.current());
						skuDO.setGmtModified(DateUtil.current());
						skuDO.setStatus(1l);
						skuList.add(skuDO);
					}
					log.debug("SKU对象数量" + skuList.size());
					log.debug("SKU数量和" + totalCapacity);
					log.debug("SKU最小价格" + minPrice);

					if (minPrice == null || new Money(itemInput.getPrice()).compareTo(minPrice) == -1) {
						errorMsg.add("SKU验证失败：输入价格小于最小销售屬性价格");
					}
					if (totalCapacity == 0 || Long.parseLong(itemInput.getNumber()) < totalCapacity) {
						errorMsg.add("SKU验证失败：输入数量小于销售屬性数量之和");
					}
				}
				
				if(requiredList!=null&&requiredList.size()>0){
					for(int i=0;i<requiredList.size();i++){
						errorMsg.add("属性验证失败：属性ID-"+requiredList.get(i)+"为必填属性");
					}
				}
			}
			if (errorMsg.size() > 0) {
				return errorMsg;
			}
			
			itemDO = setItemUpdateDO(itemInput,itemDO,true);

			// 图片上传
			List<ItemPicDO> addItemPicList = new ArrayList<ItemPicDO>();
			//图片管理中图片url
			Map imageUrl = new HashMap();
			
			List<Long> status = new ArrayList<Long>();
			
			List<ItemPicDO> itemPicDOList = itemPicManager.getItemPicByItemId(itemInput.getId());

			if(itemInput.getItemEditPicUrl()!=null&&itemInput.getItemEditPicUrl().length>0){
				if(!((itemInput.getItemEditPicUrl())[0]).equals("false")){
//					picChangeFalg = true;
					if(!(itemInput.getItemEditPicUrl())[0].equals("true")){
						String picurl = (itemInput.getItemEditPicUrl())[0].replace(PinjuConstant.VIEW_IMAGE_SERVER,"");
						imageUrl.put(0, picurl);
					}
				}else{
					ItemDO ido = itemManager.getItemDOById(itemInput.getId());
					imageUrl.put(0, ido.getPicUrl());
				}
				for(int i=1;i<itemInput.getItemEditPicUrl().length;i++){
					if(((itemInput.getItemEditPicUrl())[i]).equals("false")){
						if(itemPicDOList!=null&&itemPicDOList.size()>0){
							for(int j =0; j<itemPicDOList.size(); j++){
								ItemPicDO ipdo = itemPicDOList.get(j);
								if(ipdo.getSort().equals((long)i)){
									imageUrl.put(i, ipdo.getPicUrl());
								}
							}
						}
					}else{
						if(!(itemInput.getItemEditPicUrl())[i].equals("true")){
							String picurl = (itemInput.getItemEditPicUrl())[i].replace(PinjuConstant.VIEW_IMAGE_SERVER,"");
							imageUrl.put(i, picurl);
						}
					}
				}
			}
			File[] imagFile = new File[1];
			String[] imagFileName = new String[1];
			if(itemInput.getThisPicFile1()!=null){
				imagFile[0] = itemInput.getThisPicFile1();
				imagFileName[0] = itemInput.getThisPicFile1FileName();
				// 大小检验
				if (imagFile[0].length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
					errorMsg.add("商品输入验证错误：商品图片1大小不能超过500K");
				}
				// 类型检验
				if (!FileSecurityUtils.isImageValid(imagFile[0])) {
					errorMsg.add("商品输入验证错误：商品图片1格式错误");
				}
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(0,fileName[0]);
			}
			if(itemInput.getThisPicFile2()!=null){
				imagFile[0] = itemInput.getThisPicFile2();
				imagFileName[0] = itemInput.getThisPicFile2FileName();
				// 大小检验
				if (imagFile[0].length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
					errorMsg.add("商品输入验证错误：商品图片2大小不能超过500K");
				}
				// 类型检验
				if (!FileSecurityUtils.isImageValid(imagFile[0])) {
					errorMsg.add("商品输入验证错误：商品图片2格式错误");
				}
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(1,fileName[0]);
			}
			if(itemInput.getThisPicFile3()!=null){
				imagFile[0] = itemInput.getThisPicFile3();
				imagFileName[0] = itemInput.getThisPicFile3FileName();
				// 大小检验
				if (imagFile[0].length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
					errorMsg.add("商品输入验证错误：商品图片3大小不能超过500K");
				}
				// 类型检验
				if (!FileSecurityUtils.isImageValid(imagFile[0])) {
					errorMsg.add("商品输入验证错误：商品图片3格式错误");
				}
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(2,fileName[0]);
			}
			if(itemInput.getThisPicFile4()!=null){
				imagFile[0] = itemInput.getThisPicFile4();
				imagFileName[0] = itemInput.getThisPicFile4FileName();
				// 大小检验
				if (imagFile[0].length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
					errorMsg.add("商品输入验证错误：商品图片4大小不能超过500K");
				}
				// 类型检验
				if (!FileSecurityUtils.isImageValid(imagFile[0])) {
					errorMsg.add("商品输入验证错误：商品图片4格式错误");
				}
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(3,fileName[0]);
			}
			if(itemInput.getThisPicFile5()!=null){
				imagFile[0] = itemInput.getThisPicFile5();
				imagFileName[0] = itemInput.getThisPicFile5FileName();
				// 大小检验
				if (imagFile[0].length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
					errorMsg.add("商品输入验证错误：商品图片5大小不能超过500K");
				}
				// 类型检验
				if (!FileSecurityUtils.isImageValid(imagFile[0])) {
					errorMsg.add("商品输入验证错误：商品图片5格式错误");
				}
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(4,fileName[0]);
			}
			if(imageUrl!=null&&imageUrl.size()>0){
				Boolean imageUrlFlag = true;
				if(imageUrl.get(0)!=null){
					itemDO.setPicUrl((String)imageUrl.get(0));
					imageUrl.remove(0);
				}else{
					if(imageUrl.get(1)!=null){
						itemDO.setPicUrl((String)imageUrl.get(1));
						imageUrl.remove(1);
					}else{
						if(imageUrl.get(2)!=null){
							itemDO.setPicUrl((String)imageUrl.get(2));
							imageUrl.remove(2);
						}else{
							if(imageUrl.get(3)!=null){
								itemDO.setPicUrl((String)imageUrl.get(3));
								imageUrl.remove(3);
							}else{
								if(imageUrl.get(4)!=null){
									itemDO.setPicUrl((String)imageUrl.get(4));
									imageUrl.remove(4);
									imageUrlFlag = false;
								}
							}	
						}
					}
				}
				if(imageUrlFlag){
					Iterator it = imageUrl.entrySet().iterator();    
					while (it.hasNext()){    
						Map.Entry pairs = (Map.Entry)it.next();
						ItemPicDO ipDo = new ItemPicDO();
						ipDo.setGmtCreate(DateUtil.current());
						ipDo.setGmtModified(DateUtil.current());
						ipDo.setPicUrl((String)pairs.getValue());
//						ipDo.setSort(new Long(sort));
						ipDo.setPicLength(new Long(0));
						ipDo.setPicWidth(new Long(0));
						ipDo.setPicSize(new Long(0));
						addItemPicList.add(ipDo);
					 }
				}		
			}
			List<ItemPicDO> addItemPicListExt = new ArrayList<ItemPicDO>();
			
			if(addItemPicList!=null&&addItemPicList.size()>0){
				for(int i =0;i<addItemPicList.size();i++){
					ItemPicDO ipDo = addItemPicList.get(i);
					ipDo.setSort(Long.valueOf(i+1));
					addItemPicListExt.add(ipDo);
				}
			}
			List<CustomProValueDO> customSkuList = new ArrayList<CustomProValueDO>();
			itemManager.itemUpdate(itemDO, skuList, addItemPicListExt, customSkuList);
			return errorMsg;

		} catch (ManagerException e) {
			log.error("更新商品", e);
			return null;
		} catch (Exception e){
			log.error("更新商品", e);
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.ao.ItemAO#itemUpdate(com.yuwang.pinju.domain .item.ItemInput)
	 */
	@Override
	public List<String> itemUpdate(ItemInput itemInput, List<String> errorSKUMsg, List<SkuDO> skuList, List<CustomProValueDO> customSkuList) {
		try {

			List<String> errorMsg = itemValidator(itemInput,false);
			if(errorSKUMsg!=null&&errorSKUMsg.size()>0){
				errorMsg.addAll(errorSKUMsg);
			}
			if (errorMsg.size() > 0) {
				return errorMsg;
			}
			ItemDO itemDO = itemManager.getItemDOById(itemInput.getId());
			if (itemDO == null) {
				errorMsg.add("商品更新：无效的商品。");
				return errorMsg;
			}

			if (!itemDO.getSellerId().equals(itemInput.getSellerId())) {
				errorMsg.add("商品更新：错误的卖家编号。");
				return errorMsg;
			}

//			log.debug("开始SKU处理");
			

			itemDO = setItemUpdateDO(itemInput,itemDO);

			// 图片上传
//			Boolean picChangeFalg = false;
			List<ItemPicDO> addItemPicList = new ArrayList<ItemPicDO>();
			//图片管理中图片url
			Map imageUrl = new HashMap();
			
			List<Long> status = new ArrayList<Long>();
			
			List<ItemPicDO> itemPicDOList = itemPicManager.getItemPicByItemId(itemInput.getId());

			if(itemInput.getItemEditPicUrl()!=null&&itemInput.getItemEditPicUrl().length>0){
				if(!((itemInput.getItemEditPicUrl())[0]).equals("false")){
//					picChangeFalg = true;
					if(!(itemInput.getItemEditPicUrl())[0].equals("true")){
						String picurl = (itemInput.getItemEditPicUrl())[0].replace(PinjuConstant.VIEW_IMAGE_SERVER,"");
						imageUrl.put(0, picurl);
					}
				}else{
					ItemDO ido = itemManager.getItemDOById(itemInput.getId());
					imageUrl.put(0, ido.getPicUrl());
				}
				for(int i=1;i<itemInput.getItemEditPicUrl().length;i++){
					if(((itemInput.getItemEditPicUrl())[i]).equals("false")){
						if(itemPicDOList!=null&&itemPicDOList.size()>0){
							for(int j =0; j<itemPicDOList.size(); j++){
								ItemPicDO ipdo = itemPicDOList.get(j);
								if(ipdo.getSort().equals((long)i)){
									imageUrl.put(i, ipdo.getPicUrl());
								}
							}
						}
					}else{
						if(!(itemInput.getItemEditPicUrl())[i].equals("true")){
							String picurl = (itemInput.getItemEditPicUrl())[i].replace(PinjuConstant.VIEW_IMAGE_SERVER,"");
							imageUrl.put(i, picurl);
						}
					}
				}
			}
//			List<ItemPicDO> itemPicDOList = itemPicManager.getItemPicByItemId(itemInput.getId());
//			int sort = 1;
//			if(itemPicDOList!=null&&itemPicDOList.size()>0){
//				for(int i =0; i<itemPicDOList.size(); i++){
//					Boolean addFlag = false;
//					ItemPicDO ipdo = itemPicDOList.get(i);
//					if(status!=null&&status.size()>0){
//						for(int j =0; j<status.size(); j++){
//							if(ipdo.getSort().equals(status.get(j))){
//								addFlag = true;
//								break;
//							}
//						}
//					}
//					if(addFlag){
//						ipdo.setGmtModified(DateUtil.current());
//						ipdo.setSort(new Long(sort));
//						addItemPicList.add(ipdo);
//						sort = sort+1;
//					}
//				}
//			}
			
			File[] imagFile = new File[1];
			String[] imagFileName = new String[1];
			if(itemInput.getThisPicFile1()!=null){
				imagFile[0] = itemInput.getThisPicFile1();
				imagFileName[0] = itemInput.getThisPicFile1FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(0,fileName[0]);
			}
			if(itemInput.getThisPicFile2()!=null){
				imagFile[0] = itemInput.getThisPicFile2();
				imagFileName[0] = itemInput.getThisPicFile2FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(1,fileName[0]);
			}
			if(itemInput.getThisPicFile3()!=null){
				imagFile[0] = itemInput.getThisPicFile3();
				imagFileName[0] = itemInput.getThisPicFile3FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(2,fileName[0]);
			}
			if(itemInput.getThisPicFile4()!=null){
				imagFile[0] = itemInput.getThisPicFile4();
				imagFileName[0] = itemInput.getThisPicFile4FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(3,fileName[0]);
			}
			if(itemInput.getThisPicFile5()!=null){
				imagFile[0] = itemInput.getThisPicFile5();
				imagFileName[0] = itemInput.getThisPicFile5FileName();
				String[] fileName = storageFileInfoManager.insertStorageFileInfo(imagFile, imagFileName, itemInput.getSellerId(), itemInput.getNickName(), 1);
				if(fileName==null||fileName.length<0){
					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
					return errorMsg;
				}
				imageUrl.put(4,fileName[0]);
			}
			
			
//			if (itemInput.getPicFile() != null && itemInput.getPicFile().length > 0) {
////				String[] fileName = fileStorageManager.saveImage(itemInput.getPicFile(),itemInput.getPicFileFileName(), itemInput.getSellerId(), "", true);
//				String[] fileName = storageFileInfoManager.insertStorageFileInfo(itemInput.getPicFile(), itemInput.getPicFileFileName(), itemInput.getSellerId(), itemInput.getNickName(), 1);
//				
//				if(fileName==null||fileName.length<0){
//					errorMsg.add("商品输入验证错误：图片上传失败或图片空间不足");
//					return errorMsg;
//				}
//				// 记录文件上传信息
////				fileInfoManager.addFileInfos(itemInput.getSellerId(), fileName, itemInput.getPicFile());
//				if (fileName != null && fileName.length > 0) {
//					if(picChangeFalg){
//						if(imageUrl!=null&&imageUrl.size()>0&&imageUrl.get(0)!=null){
//							itemDO.setPicUrl((String)imageUrl.get(0));
//							imageUrl.remove(0);
//							for(int i=0; i<fileName.length; i++){
//								ItemPicDO ipDo = new ItemPicDO();
//								ipDo.setGmtCreate(DateUtil.current());
//								ipDo.setGmtModified(DateUtil.current());
//								ipDo.setPicUrl(fileName[i]);
////								ipDo.setSort(new Long(sort));
//								ipDo.setPicLength(new Long(0));
//								ipDo.setPicWidth(new Long(0));
//								ipDo.setPicSize(new Long(0));
//								addItemPicList.add(ipDo);
////								sort = sort+1;
//							}
//						}else{
//							itemDO.setPicUrl(fileName[0]);
//							for(int i=0; i<fileName.length; i++){
//								if(i!=0){
//									ItemPicDO ipDo = new ItemPicDO();
//									ipDo.setGmtCreate(DateUtil.current());
//									ipDo.setGmtModified(DateUtil.current());
//									ipDo.setPicUrl(fileName[i]);
////									ipDo.setSort(new Long(sort));
//									ipDo.setPicLength(new Long(0));
//									ipDo.setPicWidth(new Long(0));
//									ipDo.setPicSize(new Long(0));
//									addItemPicList.add(ipDo);	
////									sort = sort+1;
//								}
//							}
//						}
//					}else{
//						for(int i=0; i<fileName.length; i++){
//							ItemPicDO ipDo = new ItemPicDO();
//							ipDo.setGmtCreate(DateUtil.current());
//							ipDo.setGmtModified(DateUtil.current());
//							ipDo.setPicUrl(fileName[i]);
////							ipDo.setSort(new Long(sort));
//							ipDo.setPicLength(new Long(0));
//							ipDo.setPicWidth(new Long(0));
//							ipDo.setPicSize(new Long(0));
//							addItemPicList.add(ipDo);
////							sort = sort+1;
//						}
//					}
//				}
//			}
			
			if(imageUrl!=null&&imageUrl.size()>0){
				Boolean imageUrlFlag = true;
				if(imageUrl.get(0)!=null){
					itemDO.setPicUrl((String)imageUrl.get(0));
					imageUrl.remove(0);
				}else{
					if(imageUrl.get(1)!=null){
						itemDO.setPicUrl((String)imageUrl.get(1));
						imageUrl.remove(1);
					}else{
						if(imageUrl.get(2)!=null){
							itemDO.setPicUrl((String)imageUrl.get(2));
							imageUrl.remove(2);
						}else{
							if(imageUrl.get(3)!=null){
								itemDO.setPicUrl((String)imageUrl.get(3));
								imageUrl.remove(3);
							}else{
								if(imageUrl.get(4)!=null){
									itemDO.setPicUrl((String)imageUrl.get(4));
									imageUrl.remove(4);
									imageUrlFlag = false;
								}
							}	
						}
					}
				}
				if(imageUrlFlag){
					Iterator it = imageUrl.entrySet().iterator();    
					while (it.hasNext()){    
						Map.Entry pairs = (Map.Entry)it.next();
						ItemPicDO ipDo = new ItemPicDO();
						ipDo.setGmtCreate(DateUtil.current());
						ipDo.setGmtModified(DateUtil.current());
						ipDo.setPicUrl((String)pairs.getValue());
//						ipDo.setSort(new Long(sort));
						ipDo.setPicLength(new Long(0));
						ipDo.setPicWidth(new Long(0));
						ipDo.setPicSize(new Long(0));
						addItemPicList.add(ipDo);
					 }
				}		
			}
			List<ItemPicDO> addItemPicListExt = new ArrayList<ItemPicDO>();
			
			if(addItemPicList!=null&&addItemPicList.size()>0){
				for(int i =0;i<addItemPicList.size();i++){
					ItemPicDO ipDo = addItemPicList.get(i);
					ipDo.setSort(Long.valueOf(i+1));
					addItemPicListExt.add(ipDo);
				}
			}

			itemManager.itemUpdate(itemDO, skuList, addItemPicListExt, customSkuList);

			return errorMsg;

		} catch (ManagerException e) {
			log.error("更新商品", e);
			return null;
		}

	}

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}
	
	public void setStorageFileInfoManager(StorageFileInfoManager storageFileInfoManager) {
		this.storageFileInfoManager = storageFileInfoManager;
	}
	
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setCustomProValueManager(CustomCateProValueManager customProValueManager) {
		this.customProValueManager = customProValueManager;
	}
	
	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	public void setFileInfoManager(FileInfoManager fileInfoManager) {
		this.fileInfoManager = fileInfoManager;
	}

	public void setItemPicManager(ItemPicManager itemPicManager) {
		this.itemPicManager = itemPicManager;
	}
	
	public void setShopShieldManager(ShopShieldManager shopShieldManager) {
		this.shopShieldManager = shopShieldManager;
	}
	
	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
	
	private void addSkuSelectMap(String salePv, Map<String, List<String>> m) {
		if (m == null) {
			return;
		}
		if (EmptyUtil.isBlank(salePv)) {
			return;
		}
		String t = StringUtil.substringBefore(salePv, ";");
		String cp = StringUtil.substringBefore(t, ":");
		String cpv = StringUtil.substringAfter(t, ":");
		List<String> ls = null;
		if (!m.containsKey(cp)) {
			ls = new ArrayList<String>();
		} else {
			ls = m.get(cp);
		}
		if (!ls.contains(cpv)) {
			ls.add(cpv);
			m.put(cp, ls);
		}
	}

	/**
	 * 获得 类目属性显示对象
	 * 
	 * @param categoryPropertyDO
	 * @param valueAttr
	 * @return
	 * @throws ManagerException
	 */
	private CategoryPropertyQuery getCategoryPropertyQuery(CategoryPropertyDO categoryPropertyDO, String valueAttr,
			String showIds) throws ManagerException {

		CategoryPropertyQuery categoryPropertyQuery = null;
		boolean hasValueAttr = !EmptyUtil.isBlank(valueAttr);

		// 属性值列表
		List<CategoryPropertyValueDO> categoryPropertyValueList;
		try {

			categoryPropertyQuery = new CategoryPropertyQuery();
			categoryPropertyQuery.setCpId(String.valueOf(categoryPropertyDO.getId()));

			categoryPropertyQuery.setCpName(categoryPropertyDO.getName());
			categoryPropertyQuery.setChildIds("");
			if(categoryPropertyDO.getIsSellCustom() !=null){
				categoryPropertyQuery.setIsSellCustom(categoryPropertyDO.getIsSellCustom().intValue());
			} else {
				categoryPropertyQuery.setIsSellCustom(0);
			}
			if (categoryPropertyDO.getIsAcceptsInput() != null) {
				categoryPropertyQuery.setIsAcceptsInput(categoryPropertyDO.getIsAcceptsInput().intValue());
			} else {
				categoryPropertyQuery.setIsAcceptsInput(2);
			}
			if (categoryPropertyDO.getIsEnumerate() != null) {
				categoryPropertyQuery.setIsEnumerate(categoryPropertyDO.getIsEnumerate().intValue());
			} else {
				categoryPropertyQuery.setIsEnumerate(1);
			}
			categoryPropertyQuery.setIsEnumerate(categoryPropertyDO.getIsEnumerate().intValue());
			if (categoryPropertyDO.getInputType() != null) {
				categoryPropertyQuery.setInputType(categoryPropertyDO.getInputType().intValue());
			} else {
				categoryPropertyQuery.setInputType(-1);
			}
			if (categoryPropertyDO.getLenLimit() != null) {
				categoryPropertyQuery.setLenLimit(categoryPropertyDO.getLenLimit().intValue());
			} else {
				categoryPropertyQuery.setLenLimit(-1);
			}
			if (categoryPropertyDO.getMaxValue() != null&&categoryPropertyDO.getMaxValue()>0) {
				if(categoryPropertyDO.getInputType()==1){
					categoryPropertyQuery.setMaxValue(String.valueOf((long)categoryPropertyDO.getMaxValue().doubleValue()/100));
				}else{
					if(categoryPropertyDO.getMaxValue()>999999999){
						String max = categoryPropertyDO.getMaxValue().toString();
						StringBuffer maxValue = new StringBuffer();
						maxValue.append(max.substring(0,max.length()-2));
						maxValue.append(".");
						maxValue.append(max.substring(max.length()-2, max.length()));
						categoryPropertyQuery.setMaxValue(maxValue.toString());
					}else{
						categoryPropertyQuery.setMaxValue(String.valueOf(categoryPropertyDO.getMaxValue().doubleValue()/100));
					}
					
				}
			} else {
				categoryPropertyQuery.setMaxValue("-1");
			}
			if (categoryPropertyDO.getMinValue() != null&&categoryPropertyDO.getMaxValue()>0) {
				if(categoryPropertyDO.getInputType()==1){
					categoryPropertyQuery.setMinValue(String.valueOf((long)categoryPropertyDO.getMinValue().doubleValue()/100));
				}else{
					if(categoryPropertyDO.getMinValue()>999999999){
						String min = categoryPropertyDO.getMinValue().toString();
						StringBuffer minValue = new StringBuffer();
						minValue.append(min.substring(0,min.length()-2));
						minValue.append(".");
						minValue.append(min.substring(min.length()-2, min.length()));
						categoryPropertyQuery.setMinValue(minValue.toString());
					}else{
						categoryPropertyQuery.setMinValue(String.valueOf(categoryPropertyDO.getMinValue().doubleValue()/100));
					}
				}
			} else {
				categoryPropertyQuery.setMinValue("-1");
			}
			if (categoryPropertyDO.getInputType() != null) {
				categoryPropertyQuery.setInputType(categoryPropertyDO.getInputType().intValue());
			} else {
				categoryPropertyQuery.setInputType(-1);
			}
			if (categoryPropertyDO.getRequired() != null) {
				categoryPropertyQuery.setRequired(categoryPropertyDO.getRequired().intValue());
			} else {
				categoryPropertyQuery.setRequired(2);
			}
			if (categoryPropertyDO.getIsKeyProperty() != null) {
				categoryPropertyQuery.setIsKeyProperty(categoryPropertyDO.getIsKeyProperty().intValue());
			} else {
				categoryPropertyQuery.setIsKeyProperty(2);
			}
			if (categoryPropertyDO.getIsSelleProperty() != null) {
				categoryPropertyQuery.setIsSelleProperty(categoryPropertyDO.getIsSelleProperty().intValue());
			} else {
				categoryPropertyQuery.setIsSelleProperty(2);
			}
			if (categoryPropertyDO.getIsSpuKey() != null) {
				categoryPropertyQuery.setIsSpuKey(categoryPropertyDO.getIsSpuKey().intValue());
			} else {
				categoryPropertyQuery.setIsSpuKey(2);
			}
			if (categoryPropertyDO.getIsMultipleChoice() != null) {
				categoryPropertyQuery.setIsMultipleChoice(categoryPropertyDO.getIsMultipleChoice().intValue());
			} else {
				categoryPropertyQuery.setIsMultipleChoice(0);
			}

			categoryPropertyQuery.setPropertyValue(valueAttr);

			if (showIds == null) {
				return categoryPropertyQuery;
			}

			if (categoryPropertyDO.getIsEnumerate() == CategoryPropertyDO.IS_YES) {
				// 枚举类
				categoryPropertyValueList = categoryCacheManager.getItemCategoryPropertyValue(categoryPropertyDO
						.getId());
				if (categoryPropertyValueList == null || categoryPropertyValueList.size() == 0) {
					log.debug("无值类目属性：" + categoryPropertyDO.getCategoryId());
					return null;
				}
				//对商品属性进行排序
				if(categoryPropertyValueList!=null&&categoryPropertyValueList.size()>0){
					Collections.sort(categoryPropertyValueList, new Comparator<CategoryPropertyValueDO>() {
					public int compare(CategoryPropertyValueDO cpvd1, CategoryPropertyValueDO cpvd2) {
						return (int) (cpvd1.getSortOrder() - cpvd2.getSortOrder());
			            }
			        });
				}
				
				List<String> bvids = null;
				if (hasValueAttr) {
					bvids = Arrays.asList(valueAttr.split(","));
				}

				List<String> showIdList = Arrays.asList(showIds.split(","));

				log.debug("showIds:" + showIds);

				for (CategoryPropertyValueDO categoryPropertyValueDO : categoryPropertyValueList) {

					/*
					 * // 获得基本属性值对象 if (categoryPropertyDO.getIsBrandProperty() != null &&
					 * categoryPropertyDO.getIsBrandProperty() == CategoryPropertyDO.IS_YES) { BrandDO brandDO =
					 * brandManager.getItemBrandById(categoryPropertyValueDO.getValueId()); if (brandDO == null) {
					 * log.debug("无效的品牌属性值：" + categoryPropertyDO.getCategoryId()); continue; } int sel = 2; if
					 * (hasValueAttr && bvids != null &&
					 * bvids.contains(String.valueOf(categoryPropertyValueDO.getValueId()))) { sel = 1; }
					 * categoryPropertyQuery.addCategoryPropertyValue(String.valueOf(brandDO.getId()), brandDO
					 * .getName(), sel); } else {
					 */

					if (showIds != "" && !showIdList.contains(String.valueOf(categoryPropertyValueDO.getValueId()))) {
						continue;
					}

					BaseValueDO baseValueDO = categoryCacheManager.getBaseValueById(categoryPropertyValueDO
							.getValueId());
					if (baseValueDO == null) {
						log.debug("无值类目属性基础值：" + categoryPropertyDO.getCategoryId());
						continue;
					}

					int sel = 2;
					if (hasValueAttr && bvids != null
							&& bvids.contains(String.valueOf(categoryPropertyValueDO.getValueId()))) {
						sel = 1;
					}
					String showValue = "";
					if(categoryPropertyValueDO.getValueType()!=null&&categoryPropertyValueDO.getValueType()!=1){
						String[] ss = baseValueDO.getValue().split("\\$\\@");
						baseValueDO.setValue(ss[0]);
						if(ss.length>1){
							showValue = ss[1];
						}
					}else{
						categoryPropertyValueDO.setValueType(1L);
					}
					categoryPropertyQuery.addCategoryPropertyValue(String.valueOf(baseValueDO.getId()), baseValueDO
							.getValue(), sel, categoryPropertyValueDO.getSortOrder(), categoryPropertyValueDO.getValueType(),showValue);
				}

			} else {
				// 非枚举属性 无属性值
				if (hasValueAttr) {
					BaseValueDO baseValueDO = categoryCacheManager.getBaseValueById(Long.valueOf(valueAttr));
					categoryPropertyQuery.addCategoryPropertyValue(String.valueOf(baseValueDO.getId()), baseValueDO
							.getValue(), 2, 0, 1, "");
				} else {
					categoryPropertyQuery.addCategoryPropertyValue("0", "", 1, 0, 1, "");
				}
			}

			// 属性值排序
			/*
			 * if (categoryPropertyQuery.getCpvList() != null) { Collections.sort(categoryPropertyQuery.getCpvList(),
			 * new Comparator() {
			 * 
			 * @Override public int compare(Object o1, Object o2) { CategoryPropertyValueQuery c1 =
			 * (CategoryPropertyValueQuery) o1; CategoryPropertyValueQuery c2 = (CategoryPropertyValueQuery) o2; if
			 * (c1.getSort() > c2.getSort()) { return 1; } else if (c1.getSort() == c2.getSort()) { return 0; } else {
			 * return -1; } } }); }
			 */

			return categoryPropertyQuery;

		} catch (ManagerException e) {
			throw e;
		}
	}

	/**
	 * 获得所有子类目属性列表
	 * 
	 * @param categoryPropertyDO
	 * @return
	 * @throws ManagerException
	 */
	private List<CategoryPropertyQuery> getChildPropertyList(CategoryPropertyDO topCP, Map selPropertyMap)
			throws ManagerException {

		List<CategoryPropertyQuery> ls = new ArrayList<CategoryPropertyQuery>();
		try {

			CategoryPropertyQuery topCPQ = null;
			CategoryPropertyQuery childCPQ1 = null;
			CategoryPropertyQuery childCPQ2 = null;

			String topCPSelect = null;
			String childCPSelect = null;
			String topPro = "";
			String childPro1 = "";
			String childPro2 = "";

			// 空值处理
			if (topCP.getIsEnumerate() == null) {
				topCP.setIsEnumerate(1l);
			}
			if (topCP.getIsMultipleChoice() == null) {
				topCP.setIsMultipleChoice(1l);
			}
			if (selPropertyMap != null && selPropertyMap.containsKey(String.valueOf(topCP.getId()))) {
				topPro = selPropertyMap.get(String.valueOf(topCP.getId())).toString();
			}
			// 创建类目显示对象
			topCPQ = getCategoryPropertyQuery(topCP, topPro, "");
			// 无值对象
			if (topCPQ == null || topCPQ.getCpvList() == null || topCPQ.getCpvList().size() < 1) {
				return null;
			}

			if (topCP.getIsEnumerate() == CategoryPropertyDO.IS_YES && topCP.getIsMultipleChoice() == 1) {
				// 子属性操作(仅非枚举 下拉框)
				CpValueRelationDO cpValueRelationDO = null;
				if (EmptyUtil.isBlank(topPro)) {
					// 无选中操作
					List<CpValueRelationDO> cvrList = categoryCacheManager.getItemCpValueRelationByCpId(topCP.getId());
					if (cvrList != null && cvrList.size() > 0) {
						cpValueRelationDO = cvrList.get(0);
					}
				} else {
					// 有选中操作
					if(NumberUtil.isLong(topPro)){
						cpValueRelationDO = categoryCacheManager.getItemCpValueRelationByCpIdAndCpvId(topCP.getId(), Long
								.valueOf(topPro));
						if (cpValueRelationDO != null) {
							topCPSelect = cpValueRelationDO.getChildCpValueIds();
						}
					}
				}

				if (cpValueRelationDO != null) {

					CategoryPropertyDO childCp = categoryCacheManager.getItemCategoryPropertyById(cpValueRelationDO
							.getChildCpId());
					// 空值处理
					if (childCp.getIsEnumerate() == null) {
						childCp.setIsEnumerate(1l);
					}
					if (childCp.getIsMultipleChoice() == null) {
						childCp.setIsMultipleChoice(1l);
					}

					if (childCp != null && childCp.getIsEnumerate() == CategoryPropertyDO.IS_YES
							&& childCp.getIsMultipleChoice() == 1) {
						// 第一级子类目属性
						cpValueRelationDO = null;
						if (selPropertyMap != null && selPropertyMap.containsKey(String.valueOf(childCp.getId()))) {
							childPro1 = selPropertyMap.get(String.valueOf(childCp.getId())).toString();
						}
						if (EmptyUtil.isBlank(childPro1)) {
							List<CpValueRelationDO> cvrList = categoryCacheManager.getItemCpValueRelationByCpId(childCp
									.getId());
							if (cvrList != null && cvrList.size() > 0) {
								cpValueRelationDO = cvrList.get(0);
							}
						} else {
							cpValueRelationDO = categoryCacheManager.getItemCpValueRelationByCpIdAndCpvId(childCp
									.getId(), Long.valueOf(childPro1));
							if (cpValueRelationDO != null) {
								childCPSelect = cpValueRelationDO.getChildCpValueIds();
							}
						}
						childCPQ1 = getCategoryPropertyQuery(childCp, childPro1, topCPSelect);

						if (childCPQ1 != null && cpValueRelationDO != null) {
							CategoryPropertyDO childCp2 = categoryCacheManager
									.getItemCategoryPropertyById(cpValueRelationDO.getChildCpId());
							// 空值处理
							if (childCp2.getIsEnumerate() == null) {
								childCp2.setIsEnumerate(1l);
							}
							if (childCp2.getIsMultipleChoice() == null) {
								childCp2.setIsMultipleChoice(1l);
							}
							if (childCp2 != null && childCp2.getIsEnumerate() == CategoryPropertyDO.IS_YES
									&& childCp2.getIsMultipleChoice() == 1) {
								// 第二级子属性
								cpValueRelationDO = null;
								if (selPropertyMap != null
										&& selPropertyMap.containsKey(String.valueOf(childCp2.getId()))) {
									childPro2 = selPropertyMap.get(String.valueOf(childCp2.getId())).toString();
								}
								childCPQ1.setChildIds(String.valueOf(childCp2.getId()));
								childCPQ2 = getCategoryPropertyQuery(childCp2, childPro2, childCPSelect);
							}
						}

					}
				}
			}

			if (topCPQ != null) {
				String childIds = "";
				if (childCPQ1 != null) {
					childIds = childCPQ1.getCpId();
					if (childCPQ2 != null) {
						childIds += "," + childCPQ2.getCpId();
					}
				}
				topCPQ.setChildIds(childIds);
				ls.add(topCPQ);
			}

			if (childCPQ1 != null) {
				ls.add(childCPQ1);
			}
			if (childCPQ2 != null) {
				ls.add(childCPQ2);
			}

			return ls;
		} catch (ManagerException e) {
			throw e;
		}
	}

	/**
	 * 组建SKU显示对象
	 * 
	 * @param skuDO
	 * @return
	 * @throws ManagerException
	 */
	private SkuInput getSkuInput(SkuDO skuDO) throws Exception {
		SkuInput skuInput = null;
		try {

			skuInput = new SkuInput();
			skuInput.setId(skuDO.getId());
			StringBuffer sb = new StringBuffer();
			if (!EmptyUtil.isBlank(skuDO.getSalePv1())) {
				sb.append(StringUtil.substringBefore(skuDO.getSalePv1(), ";"));
			}
			if (!EmptyUtil.isBlank(skuDO.getSalePv2())) {
				sb.append("-");
				sb.append(StringUtil.substringBefore(skuDO.getSalePv2(), ";"));
			}
			if (!EmptyUtil.isBlank(skuDO.getSalePv3())) {
				sb.append("-");
				sb.append(StringUtil.substringBefore(skuDO.getSalePv3(), ";"));
			}
			if (!EmptyUtil.isBlank(skuDO.getSalePv4())) {
				sb.append("-");
				sb.append(StringUtil.substringBefore(skuDO.getSalePv4(), ";"));
			}

			skuInput.setValues(sb.toString());
			skuInput.setSellerCode(skuDO.getSellerCode());
			skuInput.setPrice(new Money(skuDO.getPrice()).toString());
			skuInput.setCurrentStock(String.valueOf(skuDO.getCurrentStock()));

			return skuInput;
		} catch (Exception e) {
			log.error("组建SKU显示对象", e);
			throw e;
		}
	}

	private Map getSkuSelectMap(List<SkuDO> skuList) throws Exception {
		Map<String, List<String>> m = new HashMap<String, List<String>>();
		try {

			for (SkuDO sku : skuList) {
				// 99:100;
				addSkuSelectMap(sku.getSalePv1(), m);
				addSkuSelectMap(sku.getSalePv2(), m);
				addSkuSelectMap(sku.getSalePv3(), m);
				addSkuSelectMap(sku.getSalePv4(), m);
			}

		} catch (Exception e) {
			log.error("获得所有选中SKU属性MAP", e);
			throw e;
		}
		return m;
	}

	private List<String> itemValidator(ItemInput itemInput, Boolean falg) throws ManagerException {

		List<String> ls = new ArrayList<String>();
		try {
//			itemInput.setTitle(HtmlRegexpUtil.replaceTag(itemInput.getTitle().trim()));
			if(itemInput.getTitle()!=null){
				itemInput.setTitle(itemInput.getTitle().trim());
				if(!itemInput.getTitle().equals("")){
					int length = itemInput.getTitle().length();
					char[] charArray = itemInput.getTitle().toCharArray(); 
					for(int i=0;i<charArray.length;i++){ 
						//是中文的长度加1
						if ((charArray[i] >= 0x4e00)&&(charArray[i] <= 0x9fbb)){ 
							length = length+1;
						} 
					}
					if (length > 60) {
						ls.add("商品输入验证错误：商品标题长度不符");
					}
					Boolean isBadTitleFlag = WordFilterFacade.scan(itemInput.getTitle(), SensitiveWordConstants.SENSITIVE_WORD_TYPE_GOODS);
					if(isBadTitleFlag){
						ls.add("商品输入验证错误：商品标题存在违规字符");
					}
				}else{
					ls.add("商品输入验证错误：商品标题不能为空");
				}
			}else{
				ls.add("商品输入验证错误：商品标题不能为空");
			}
			//商品描述
			if(itemInput.getDescription()!=null&&!itemInput.getDescription().equals("")){
				itemInput.setDescription(itemInput.getDescription().replaceAll("\r\n", "\n"));
				if(itemInput.getDescription().length()>40000){
					ls.add("商品输入验证错误：商品描述长度不符");
				}
				Boolean isBadUrlFlag = WordFilterFacade.scan(itemInput.getDescription(),SensitiveWordConstants.SENSITIVE_WORD_TYPE_GOODS, true);
				if(isBadUrlFlag){
					ls.add("商品输入验证错误：商品描述存在违规字符");
				}
			}else{
				ls.add("商品输入验证错误：商品描述不能为空");
			}
			
			// 卖家编号
			if (EmptyUtil.isBlank(itemInput.getSellerId())) {
				ls.add("商品输入验证错误：无效的卖家编号");
			}
			MemberDO memberDO = memberManager.findMember(itemInput.getSellerId());
			if (memberDO == null) {
				ls.add("商品输入验证错误：无效的卖家编号");
			}

			// 类目(当通过openapi导入商品时，类目不匹配兼容)
			if(!falg){
				if (itemInput.getCatetoryId() == 0) {
					ls.add("商品输入验证错误：无效类目");
				}
				CategoryDO categoryDO = categoryManager.getItemCategory(itemInput.getCatetoryId());
				if (categoryDO == null) {
					ls.add("商品输入验证错误：无效类目");
				}else{
					if(categoryDO.getStatus()!=ItemConstant.CATEGORY_STATUS_1){
						ls.add("商品输入验证错误：类目非正常状态");
					}else{
						if(categoryDO.getIsLeaf()!=ItemConstant.IS_LEAF_1){
							ls.add("商品输入验证错误：类目非叶子节点");
						}
					}
				}
			}else{
				CategoryDO categoryDO = categoryManager.getItemCategory(itemInput.getCatetoryId());
				if (categoryDO == null) {
//					ls.add("商品输入验证错误：无效类目");
				}else{
					if(categoryDO.getStatus()!=ItemConstant.CATEGORY_STATUS_1){
						ls.add("商品输入验证错误：类目非正常状态");
					}else{
						if(categoryDO.getIsLeaf()!=ItemConstant.IS_LEAF_1){
							ls.add("商品输入验证错误：类目非叶子节点");
						}
					}
				}
			}
			
			// 商品类型（新旧程度）
			if (itemInput.getItemDegree() != ItemConstant.DEGREE_TYPE_1
					&& itemInput.getItemDegree() != ItemConstant.DEGREE_TYPE_2
					&& itemInput.getItemDegree() != ItemConstant.DEGREE_TYPE_3) {
				ls.add("商品输入验证错误：无效新旧程度");
			}

			// 价格
			if (EmptyUtil.isBlank(itemInput.getPrice())||!NumberUtil.isDouble(itemInput.getPrice())) {
				ls.add("商品输入验证错误：无效价格");
			}else{
				if(Double.parseDouble(itemInput.getPrice())<0.1){
					ls.add("商品输入验证错误：价格不能小于0.1元");
				}
			}

			// 数量
			if (EmptyUtil.isBlank(itemInput.getNumber())||!NumberUtil.isLong(itemInput.getNumber())) {
				ls.add("商品输入验证错误：无效数量");
			}else{
				if(Long.parseLong(itemInput.getNumber())<=0){
					ls.add("商品输入验证错误：无效数量");
				}
			}

			// 运费类型
			if (itemInput.getFreightType() != ItemConstant.FREIGHT_TYPE_1
					&& itemInput.getFreightType() != ItemConstant.FREIGHT_TYPE_2) {
				ls.add("商品输入验证错误：无效运费类型");
			}

			// 有效期
//			if (itemInput.getExpiryType() != ItemConstant.EXPIRY_TYPE_1
//					&& itemInput.getExpiryType() != ItemConstant.EXPIRY_TYPE_2
//					&& itemInput.getExpiryType() != ItemConstant.EXPIRY_TYPE_3) {
//				ls.add("商品输入验证错误：无效有效期");
//			}

			// 发布类型
			if (itemInput.getReleasedType() != ItemConstant.RELEASED_TYPE_1
					&& itemInput.getReleasedType() != ItemConstant.RELEASED_TYPE_2
					&& itemInput.getReleasedType() != ItemConstant.RELEASED_TYPE_3) {
				ls.add("商品输入验证错误：无效发布类型");
			}

			// 运费
			if (itemInput.getFreightType() == ItemConstant.FREIGHT_TYPE_2) {
				if (EmptyUtil.isBlank(itemInput.getMailCosts()) && EmptyUtil.isBlank(itemInput.getDeliveryCosts())
						&& EmptyUtil.isBlank(itemInput.getEmsCosts())) {
					ls.add("商品输入验证错误：无效运费");
				}else{
					if(!EmptyUtil.isBlank(itemInput.getMailCosts())&&!NumberUtil.isDouble(itemInput.getMailCosts())){
						ls.add("商品输入验证错误：无效平邮运费");
					}else if(!EmptyUtil.isBlank(itemInput.getDeliveryCosts())&&!NumberUtil.isDouble(itemInput.getDeliveryCosts())){
						ls.add("商品输入验证错误：无效快递运费");
					}else if(!EmptyUtil.isBlank(itemInput.getEmsCosts())&&!NumberUtil.isDouble(itemInput.getEmsCosts())){
						ls.add("商品输入验证错误：无效EMS运费");
					}
				}
			}
			// 商家编码过滤
			if (!EmptyUtil.isBlank(itemInput.getCode())) {
				itemInput.setCode(HtmlRegexpUtil.replaceTag(itemInput.getCode().trim()));
			}
			
			if(itemInput.getCode()!=null&&!itemInput.getCode().equals("")){
				if(itemInput.getCode().length()>100){
					ls.add("商品输入验证错误：商家编码长度不符");
				}
			}
			
			//所在地省份
			if(itemInput.getProvinces()!=null&&!itemInput.getProvinces().equals("")){
				if(itemInput.getProvinces().length()>80){
					ls.add("商品输入验证错误：所在地省份长度不符");
				}
			}else{
				ls.add("商品输入验证错误：所在地省份不能为空");
			}
			//所在地城市
			if(itemInput.getCity()!=null&&!itemInput.getCity().equals("")){
				if(itemInput.getCity().length()>80){
					ls.add("商品输入验证错误：所在地城市长度不符");
				}
			}else{
				ls.add("商品输入验证错误：所在地城市不能为空");
			}
			
		} catch (ManagerException e) {
			throw e;
		}

		return ls;
	}

//	public static void main(String[] arg){
////		String ss = "8375:40000;8489:40008,40009;8490:40012;8491:40014;8492:1765123;8500:40022;";
//		String propertyValue = "19283:12001992;19284:12007973;19288:12009429";
//	//	StringBuffer attr = new StringBuffer();
//		//将输入属性分割成属性数组
//		String[] pvs = propertyValue.split(";");
//		if(pvs!=null&&pvs.length>0){
//			for(int i =0 ; i<pvs.length;i ++){
//				//将属性数组中的
//				String[] cpIds = pvs[i].split(":");
//				CategoryPropertyDO cpDO = new CategoryPropertyDO();
//				if(cpIds!=null&&cpIds.length>0){
//					for(int j=0;j<cpIds.length;j++){
//						//验证属性ID是否存在
//						 System.out.println("cpIds===="+j+"====>"+cpIds[j]);
//							if(j!=0){
//								String[] cpvIds = cpIds[j].split(",");
//								for(int g=0;g<cpvIds.length;g++){
//									System.out.println("cpvIds===="+g+"====>"+cpvIds[g]);
//								}
//							}
//							
//
//					}
//				}
//			}
//		}
//		String chineseStr = "中华2人3民%公a社"; 
//		char[] charArray = chineseStr.toCharArray(); 
//		for(int i=0;i<charArray.length;i++){ 
//			if ((charArray[i] >= 0x4e00)&&(charArray[i] <= 0x9fbb)){ 
//				System.out.println(charArray[i]); 
//			} 
//		}
//	}
	/**
	 * 设置商品输入对象
	 * 
	 * @param itemInput
	 * @param isCreate
	 * @param isOpenApi 
	 * @return
	 * @throws ManagerException
	 */
	private ItemDO setItemDO(ItemInput itemInput, boolean isCreate, boolean isOpenApi) throws ManagerException {

		ItemDO itemDO = null;
		try {

			itemDO = new ItemDO();

			Calendar c = Calendar.getInstance();

			if (!isCreate) {
				itemDO.setId(itemInput.getId());
			} else {
				itemDO.setGmtCreate(c.getTime());
			}

			// itemDO.setExpiredDate(c.getTime());
			itemDO.setGmtModified(c.getTime());
			itemDO.setLastModified(c.getTime());
			itemDO.setStartTime(c.getTime());
			itemDO.setCatetoryId(itemInput.getCatetoryId());
			itemDO.setSellerId(itemInput.getSellerId());
			itemDO.setSellerNick(itemInput.getNickName());
			itemDO.setItemType(ItemConstant.ITEM_TYPE_1);
			itemDO.setStatus(ItemConstant.STATUS_TYPE_0);
			itemDO.setItemDegree((long) itemInput.getItemDegree());

			itemDO.setCollectionNumber(0L);
			itemDO.setRecommendedStatus(0L);

			itemDO.setSpuId(itemInput.getSpuId());

			// 有效期
			itemDO.setExpiredDate(itemInput.getExpiryType() + 0l);
			
			// 发布类型
			if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_1) {

				// 结束时间
				long endTimeLong = c.getTime().getTime();
				endTimeLong += itemDO.getExpiredDate() * 24 * 60 * 60 * 1000;
				itemDO.setEndTime(new Date(endTimeLong));

			} else if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_2) {

				if (itemInput.getReleasedYear() != null) {
					String d[] = itemInput.getReleasedYear().split("-");
					c.set(Calendar.YEAR, Integer.parseInt(d[0]));
					c.set(Calendar.MONTH, Integer.parseInt(d[1]) - 1);
					c.set(Calendar.DATE, Integer.parseInt(d[2]));
					c.set(Calendar.MINUTE, Integer.parseInt(itemInput.getReleasedMinute()));
					c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(itemInput.getReleasedHour()));
					c.set(Calendar.SECOND, 0);
				}
				itemDO.setStartTime(c.getTime());
				itemDO.setStatus(ItemConstant.STATUS_TYPE_6);

			} else if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_3) {
				itemDO.setStatus(ItemConstant.STATUS_TYPE_6);
			}
			
			if (itemInput.getPropertyValuePair() != null) {
				if(isOpenApi){
					// 插入基本属性
					List<CategoryPropertyDO> categoryPropertyList;

					categoryPropertyList = categoryManager.getItemCategoryProperty(itemInput.getCatetoryId());
					if(categoryPropertyList!=null&&categoryPropertyList.size()>0){
						List ls = new ArrayList();
						for (CategoryPropertyDO categoryPropertyDO : categoryPropertyList) {
	//						if (categoryPropertyDO.getIsEnumerate() == CategoryPropertyDO.IS_NO) {
								ls.add(String.valueOf(categoryPropertyDO.getId()));
	//						}
						}
	
						log.debug("all Enumerate CategoryProperty:" + ls);
	
						if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {
	
							String propertyValue = itemInput.getPropertyValuePair();
							StringBuffer attr = new StringBuffer();
							//将输入属性分割成属性数组
							String[] pvs = propertyValue.split(";");
							if(pvs!=null&&pvs.length>0){
								for(int i =0 ; i<pvs.length;i ++){
									//将属性数组中的
									String[] cpIds = pvs[i].split(":");
									if(cpIds!=null&&cpIds.length>0){
										boolean isFlag = true;
										for(int j=0;j<cpIds.length;j++){
											//验证属性ID是否存在
											if (ls.contains(cpIds[0])) {
												if(isFlag){
													attr.append(cpIds[0]);
													attr.append(":");
												}
												isFlag = false;
												StringBuffer cpValue = new StringBuffer();
												if(j!=0){
													String[] cpvIds = cpIds[j].split(",");
													for(int g=0;g<cpvIds.length;g++){
														//验证属性值ID是否存在
														if(NumberUtil.isDouble(cpvIds[g])){
															BaseValueDO bvDo = categoryManager.getBaseValueById(Long.parseLong(cpvIds[g]));
															if(bvDo!=null&&bvDo.getId()>0){
																if(cpValue.length()>0){
																	cpValue.append(",");
																}
																cpValue.append(String.valueOf(bvDo.getId()));
															}											
														}	
													}
												}
												attr.append(cpValue);
											}else{
												break;
											}
											if(j==cpIds.length-1||isFlag){
												attr.append(";");
											}
										}
									}
								}
							}
							log.debug("attr:" + attr.toString());
							itemDO.setPropertyValuePair(attr.toString());
						}
					}else{
						itemDO.setCatetoryId(-1L);
						itemDO.setStatus(ItemConstant.STATUS_TYPE_10);
					}
				}else{
					// 插入基本属性
					List<CategoryPropertyDO> categoryPropertyList;

					categoryPropertyList = categoryManager.getItemCategoryProperty(itemInput.getCatetoryId());

					List ls = new ArrayList();
					for (CategoryPropertyDO categoryPropertyDO : categoryPropertyList) {
						if (categoryPropertyDO.getIsEnumerate() == CategoryPropertyDO.IS_NO) {
							ls.add(String.valueOf(categoryPropertyDO.getId()));
						}
					}

					log.debug("all Enumerate CategoryProperty:" + ls);

					if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {

						JSONObject cp = JSONObject.fromObject(itemInput.getPropertyValuePair());

						StringBuffer attr = new StringBuffer();
//						for (String cpId : (Set<String>) cp.keySet()) {
//							JSONObject cpv = JSONObject.fromObject(cp.getString(cpId));
//							String cpValue = "";
//							log.debug("cpId:" + cpId);
//							for (String i : (Set<String>) cpv.keySet()) {
//								if (cpValue != "") {
//									cpValue += ",";
//								}
//								if (ls.contains(cpId)) {
//									log.debug("insert baseValue by:" + cpId);
//									cpValue += String.valueOf(categoryManager.getItemBaseValueByValue(cpv.getString(i)));
//									log.debug("insert baseValue result:" + cpv.getString(i));
//								} else {
//									cpValue += cpv.getString(i);
//								}
//							}
//							attr.append(cpId);
//							attr.append(":");
//							attr.append(cpValue);
//							attr.append(";");
//						}
						for (String cpId : (Set<String>) cp.keySet()) {
							JSONObject cpv = JSONObject.fromObject(cp.getString(cpId));
							StringBuffer cpValue = new StringBuffer();
							log.debug("cpId:" + cpId);
							for (String i : (Set<String>) cpv.keySet()) {
								if (ls.contains(cpId)) {
									log.debug("insert baseValue by:" + cpId);
									if(cpValue.length()>0){
										cpValue.append(",");
									}
									cpValue.append(String.valueOf(categoryManager.getItemBaseValueByValue(cpv.getString(i))));
									log.debug("insert baseValue result:" + cpv.getString(i));
								} else {
									if(cpValue.length()>0){
										cpValue.append(",");
									}
									cpValue.append(cpv.getString(i));
								}
							}
							attr.append(cpId);
							attr.append(":");
							attr.append(cpValue);
							attr.append(";");
						}
						log.debug("attr:" + attr.toString());
						itemDO.setPropertyValuePair(attr.toString());
					}
				}
			}

			// 标题
			itemDO.setTitle(itemInput.getTitle());

			// 一口价
			Money m = new Money(itemInput.getPrice());
			itemDO.setPrice(m.getCent());

			// 商家编码
			if (itemInput.getCode() != null) {
				itemDO.setCode(itemInput.getCode());
			}

			itemDO.setMailCosts(0l);
			itemDO.setDeliveryCosts(0l);
			itemDO.setEmsCosts(0l);
			if (itemInput.getFreightType() == ItemConstant.FREIGHT_TYPE_2) {
				// 买家承担运费
				if (itemInput.getBuyFreightType() == ItemConstant.BUY_FREIGHT_TYPE_2) {
					// 普通
					if (!EmptyUtil.isBlank(itemInput.getMailCosts())) {
						itemDO.setMailCosts(new Money(itemInput.getMailCosts()).getCent());
					}
					if (!EmptyUtil.isBlank(itemInput.getDeliveryCosts())) {
						itemDO.setDeliveryCosts(new Money(itemInput.getDeliveryCosts()).getCent());
					}
					if (!EmptyUtil.isBlank(itemInput.getEmsCosts())) {
						itemDO.setEmsCosts(new Money(itemInput.getEmsCosts()).getCent());
					}
				} else {
					// 运费模板
					if (!EmptyUtil.isBlank(itemInput.getFreeTemplates())) {
						itemDO.setFreeTemplateId(Long.parseLong(itemInput.getFreeTemplates()));
					}
				}
			}

			// 数量
			itemDO.setOriStock(Long.valueOf(itemInput.getNumber()));
			itemDO.setCurStock(Long.valueOf(itemInput.getNumber()));

			// 描述
			itemDO.setDescription(itemInput.getDescription());

			// 地方 城市
			itemDO.setProvinces(itemInput.getProvinces());
			itemDO.setCity(itemInput.getCity());

			// 店铺 分类
			if (!EmptyUtil.isBlank(itemInput.getStoreCategories())) {
				itemDO.setStoreCategories(itemInput.getStoreCategories().replaceAll(" ", ""));
			}

			// 暂不用字段
			// 图片颜色
			itemDO.setPicColor(0l);

		} catch (ManagerException e) {
			throw e;
		}

		return itemDO;
	}

	/**
	 * 设置商品输入对象
	 * 
	 * @param itemInput
	 * @param isCreate
	 * @return
	 * @throws ManagerException
	 */
	private ItemDO setItemUpdateDO(ItemInput itemInput, ItemDO itemDO) throws ManagerException {

//		ItemDO itemDO = null;
		try {

//			itemDO = new ItemDO();

			Calendar c = Calendar.getInstance();

			itemDO.setId(itemInput.getId());
			itemDO.setSellerId(itemInput.getSellerId());
			itemDO.setGmtModified(c.getTime());
			itemDO.setLastModified(c.getTime());

			itemDO.setItemType(ItemConstant.ITEM_TYPE_1);
			itemDO.setStatus(ItemConstant.STATUS_TYPE_0);
			itemDO.setItemDegree((long) itemInput.getItemDegree());

			itemDO.setSpuId(itemInput.getSpuId());
			if(itemDO.getCatetoryId()==-1){
				if(itemInput.getCatetoryId()>0){
					itemDO.setCatetoryId(itemInput.getCatetoryId());
				}
			}
			
			if (itemInput.getPropertyValuePair() != null) {

				// 插入基本属性
				List<CategoryPropertyDO> categoryPropertyList;

				categoryPropertyList = categoryManager.getItemCategoryProperty(itemInput.getCatetoryId());

				List ls = new ArrayList();
				for (CategoryPropertyDO categoryPropertyDO : categoryPropertyList) {
					if (categoryPropertyDO.getIsEnumerate() == CategoryPropertyDO.IS_NO) {
						ls.add(String.valueOf(categoryPropertyDO.getId()));
					}
				}

				log.debug("all Enumerate CategoryProperty:" + ls);

				if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {

					JSONObject cp = JSONObject.fromObject(itemInput.getPropertyValuePair());

					StringBuffer attr = new StringBuffer();
//					for (String cpId : (Set<String>) cp.keySet()) {
//						JSONObject cpv = JSONObject.fromObject(cp.getString(cpId));
//						String cpValue = "";
//						log.debug("cpId:" + cpId);
//						for (String i : (Set<String>) cpv.keySet()) {
//							if (cpValue != "") {
//								cpValue += ",";
//							}
//							if (ls.contains(cpId)) {
//								log.debug("insert baseValue by:" + cpId);
//								cpValue += String.valueOf(categoryManager.getItemBaseValueByValue(cpv.getString(i)));
//								log.debug("insert baseValue result:" + cpv.getString(i));
//							} else {
//								cpValue += cpv.getString(i);
//							}
//						}
//						attr.append(cpId);
//						attr.append(":");
//						attr.append(cpValue);
//						attr.append(";");
//					}
					for (String cpId : (Set<String>) cp.keySet()) {
						JSONObject cpv = JSONObject.fromObject(cp.getString(cpId));
						StringBuffer cpValue = new StringBuffer();
						log.debug("cpId:" + cpId);
						for (String i : (Set<String>) cpv.keySet()) {
							if (ls.contains(cpId)) {
								log.debug("insert baseValue by:" + cpId);
								if(cpValue.length()>0){
									cpValue.append(",");
								}
								cpValue.append(String.valueOf(categoryManager.getItemBaseValueByValue(cpv.getString(i))));
								log.debug("insert baseValue result:" + cpv.getString(i));
							} else {
								if(cpValue.length()>0){
									cpValue.append(",");
								}
								cpValue.append(cpv.getString(i));
							}
						}
						attr.append(cpId);
						attr.append(":");
						attr.append(cpValue);
						attr.append(";");
					}
					

					log.debug("attr:" + attr.toString());
					itemDO.setPropertyValuePair(attr.toString());
				}

			}

			// 有效期
			itemDO.setExpiredDate(itemInput.getExpiryType() + 0l);

			if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_1) {
				// 结束时间
				long endTimeLong = c.getTime().getTime();
				endTimeLong += itemDO.getExpiredDate() * 24 * 60 * 60 * 1000;
				itemDO.setEndTime(new Date(endTimeLong));
			} else if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_2) {
				if (itemInput.getReleasedYear() != null) {
					String d[] = itemInput.getReleasedYear().split("-");
					c.set(Calendar.YEAR, Integer.parseInt(d[0]));
					c.set(Calendar.MONTH, Integer.parseInt(d[1]) - 1);
					c.set(Calendar.DATE, Integer.parseInt(d[2]));
					c.set(Calendar.MINUTE, Integer.parseInt(itemInput.getReleasedMinute()));
					c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(itemInput.getReleasedHour()));
					c.set(Calendar.SECOND, 0);
				}
				itemDO.setStartTime(c.getTime());
				itemDO.setStatus(ItemConstant.STATUS_TYPE_6);

			} else if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_3) {
				itemDO.setStatus(ItemConstant.STATUS_TYPE_6);
			}

			itemDO.setTitle(itemInput.getTitle());
			Money m = new Money(itemInput.getPrice());
			itemDO.setPrice(m.getCent());
			if (itemInput.getCode() != null) {
				itemDO.setCode(itemInput.getCode());
			}

			itemDO.setMailCosts(0l);
			itemDO.setDeliveryCosts(0l);
			itemDO.setEmsCosts(0l);
			if (itemInput.getFreightType() == ItemConstant.FREIGHT_TYPE_2) {
				// 买家承担运费
				if (itemInput.getBuyFreightType() == ItemConstant.BUY_FREIGHT_TYPE_2) {
					// 普通
					if (!EmptyUtil.isBlank(itemInput.getMailCosts())) {
						itemDO.setMailCosts(new Money(itemInput.getMailCosts()).getCent());
					}
					if (!EmptyUtil.isBlank(itemInput.getDeliveryCosts())) {
						itemDO.setDeliveryCosts(new Money(itemInput.getDeliveryCosts()).getCent());
					}
					if (!EmptyUtil.isBlank(itemInput.getEmsCosts())) {
						itemDO.setEmsCosts(new Money(itemInput.getEmsCosts()).getCent());
					}
					itemDO.setFreeTemplateId(null);
				} else {
					// 运费模板
					if (!EmptyUtil.isBlank(itemInput.getFreeTemplates())) {
						itemDO.setFreeTemplateId(Long.parseLong(itemInput.getFreeTemplates()));
					}
				}
			}else{
				itemDO.setFreeTemplateId(null);
			}

			// 数量
			itemDO.setOriStock(Long.valueOf(itemInput.getNumber()));
			itemDO.setCurStock(Long.valueOf(itemInput.getNumber()));

			// 描述
			if (!itemDO.getDescription().equals(itemInput.getDescription())) {
				itemDO.setDescription(itemInput.getDescription());
			}

			// 地区 城市
			if (!itemInput.getProvinces().equalsIgnoreCase(itemDO.getProvinces())) {
				itemDO.setProvinces(itemInput.getProvinces());
			}
			if (!itemInput.getCity().equalsIgnoreCase(itemDO.getCity())) {
				itemDO.setCity(itemInput.getCity());
			}

			// 店铺 分类
			if (!EmptyUtil.isBlank(itemInput.getStoreCategories())) {
				itemDO.setStoreCategories(itemInput.getStoreCategories().replaceAll(" ", ""));
			}

			// 暂不用字段
			itemDO.setPicColor(0l);

		} catch (ManagerException e) {
			throw e;
		}

		return itemDO;
	}

	/**
	 * 设置商品输入对象
	 * 
	 * @param itemInput
	 * @param isCreate
	 * @return
	 * @throws ManagerException
	 */
	private ItemDO setItemUpdateDO(ItemInput itemInput, ItemDO itemDO, boolean isOpenApi) throws ManagerException {

//		ItemDO itemDO = null;
		try {

//			itemDO = new ItemDO();

			Calendar c = Calendar.getInstance();

			itemDO.setId(itemInput.getId());
			itemDO.setSellerId(itemInput.getSellerId());
			itemDO.setGmtModified(c.getTime());
			itemDO.setLastModified(c.getTime());

			itemDO.setItemType(ItemConstant.ITEM_TYPE_1);
			itemDO.setStatus(ItemConstant.STATUS_TYPE_0);
			itemDO.setItemDegree((long) itemInput.getItemDegree());

			itemDO.setSpuId(itemInput.getSpuId());

			if (itemInput.getPropertyValuePair() != null) {
				if(isOpenApi){
					// 插入基本属性
					List<CategoryPropertyDO> categoryPropertyList;

					categoryPropertyList = categoryManager.getItemCategoryProperty(itemInput.getCatetoryId());

					List ls = new ArrayList();
					for (CategoryPropertyDO categoryPropertyDO : categoryPropertyList) {
//						if (categoryPropertyDO.getIsEnumerate() == CategoryPropertyDO.IS_NO) {
							ls.add(String.valueOf(categoryPropertyDO.getId()));
//						}
					}

					log.debug("all Enumerate CategoryProperty:" + ls);

					if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {

						String propertyValue = itemInput.getPropertyValuePair();
						StringBuffer attr = new StringBuffer();
						//将输入属性分割成属性数组
						String[] pvs = propertyValue.split(";");
						if(pvs!=null&&pvs.length>0){
							for(int i =0 ; i<pvs.length;i ++){
								//将属性数组中的
								String[] cpIds = pvs[i].split(":");
								if(cpIds!=null&&cpIds.length>0){
									boolean isFlag = true;
									for(int j=0;j<cpIds.length;j++){
										//验证属性ID是否存在
										if (ls.contains(cpIds[0])) {
											if(isFlag){
												attr.append(cpIds[0]);
												attr.append(":");
											}
											isFlag = false;
											StringBuffer cpValue = new StringBuffer();
											if(j!=0){
												String[] cpvIds = cpIds[j].split(",");
												for(int g=0;g<cpvIds.length;g++){
													//验证属性值ID是否存在
													if(NumberUtil.isDouble(cpvIds[g])){
														BaseValueDO bvDo = categoryManager.getBaseValueById(Long.parseLong(cpvIds[g]));
														if(bvDo!=null&&bvDo.getId()>0){
															if(cpValue.length()>0){
																cpValue.append(",");
															}
															cpValue.append(String.valueOf(bvDo.getId()));
														}											
													}	
												}
											}
											attr.append(cpValue);
										}else{
											break;
										}
										if(j==cpIds.length-1||isFlag){
											attr.append(";");
										}
									}
								}
							}
						}
						log.debug("attr:" + attr.toString());
						itemDO.setPropertyValuePair(attr.toString());
					}
				
				}else{
					// 插入基本属性
					List<CategoryPropertyDO> categoryPropertyList;

					categoryPropertyList = categoryManager.getItemCategoryProperty(itemInput.getCatetoryId());

					List ls = new ArrayList();
					for (CategoryPropertyDO categoryPropertyDO : categoryPropertyList) {
						if (categoryPropertyDO.getIsEnumerate() == CategoryPropertyDO.IS_NO) {
							ls.add(String.valueOf(categoryPropertyDO.getId()));
						}
					}

					log.debug("all Enumerate CategoryProperty:" + ls);

					if (!EmptyUtil.isBlank(itemInput.getPropertyValuePair())) {

						JSONObject cp = JSONObject.fromObject(itemInput.getPropertyValuePair());

						StringBuffer attr = new StringBuffer();
//						for (String cpId : (Set<String>) cp.keySet()) {
//							JSONObject cpv = JSONObject.fromObject(cp.getString(cpId));
//							String cpValue = "";
//							log.debug("cpId:" + cpId);
//							for (String i : (Set<String>) cpv.keySet()) {
//								if (cpValue != "") {
//									cpValue += ",";
//								}
//								if (ls.contains(cpId)) {
//									log.debug("insert baseValue by:" + cpId);
//									cpValue += String.valueOf(categoryManager.getItemBaseValueByValue(cpv.getString(i)));
//									log.debug("insert baseValue result:" + cpv.getString(i));
//								} else {
//									cpValue += cpv.getString(i);
//								}
//							}
//							attr.append(cpId);
//							attr.append(":");
//							attr.append(cpValue);
//							attr.append(";");
//						}
						for (String cpId : (Set<String>) cp.keySet()) {
							JSONObject cpv = JSONObject.fromObject(cp.getString(cpId));
							StringBuffer cpValue = new StringBuffer();
							log.debug("cpId:" + cpId);
							for (String i : (Set<String>) cpv.keySet()) {
								if (ls.contains(cpId)) {
									log.debug("insert baseValue by:" + cpId);
									if(cpValue.length()>0){
										cpValue.append(",");
									}
									cpValue.append(String.valueOf(categoryManager.getItemBaseValueByValue(cpv.getString(i))));
									log.debug("insert baseValue result:" + cpv.getString(i));
								} else {
									if(cpValue.length()>0){
										cpValue.append(",");
									}
									cpValue.append(cpv.getString(i));
								}
							}
							attr.append(cpId);
							attr.append(":");
							attr.append(cpValue);
							attr.append(";");
						}
						

						log.debug("attr:" + attr.toString());
						itemDO.setPropertyValuePair(attr.toString());
					}
				}
			}

			// 有效期
			itemDO.setExpiredDate(itemInput.getExpiryType() + 0l);

			if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_1) {
				// 结束时间
				long endTimeLong = c.getTime().getTime();
				endTimeLong += itemDO.getExpiredDate() * 24 * 60 * 60 * 1000;
				itemDO.setEndTime(new Date(endTimeLong));
			} else if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_2) {
				if (itemInput.getReleasedYear() != null) {
					String d[] = itemInput.getReleasedYear().split("-");
					c.set(Calendar.YEAR, Integer.parseInt(d[0]));
					c.set(Calendar.MONTH, Integer.parseInt(d[1]) - 1);
					c.set(Calendar.DATE, Integer.parseInt(d[2]));
					c.set(Calendar.MINUTE, Integer.parseInt(itemInput.getReleasedMinute()));
					c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(itemInput.getReleasedHour()));
					c.set(Calendar.SECOND, 0);
				}
				itemDO.setStartTime(c.getTime());
				itemDO.setStatus(ItemConstant.STATUS_TYPE_6);

			} else if (itemInput.getReleasedType() == ItemConstant.RELEASED_TYPE_3) {
				itemDO.setStatus(ItemConstant.STATUS_TYPE_6);
			}

			itemDO.setTitle(itemInput.getTitle());
			Money m = new Money(itemInput.getPrice());
			itemDO.setPrice(m.getCent());
			if (itemInput.getCode() != null) {
				itemDO.setCode(itemInput.getCode());
			}

			itemDO.setMailCosts(0l);
			itemDO.setDeliveryCosts(0l);
			itemDO.setEmsCosts(0l);
			if (itemInput.getFreightType() == ItemConstant.FREIGHT_TYPE_2) {
				// 买家承担运费
				if (itemInput.getBuyFreightType() == ItemConstant.BUY_FREIGHT_TYPE_2) {
					// 普通
					if (!EmptyUtil.isBlank(itemInput.getMailCosts())) {
						itemDO.setMailCosts(new Money(itemInput.getMailCosts()).getCent());
					}
					if (!EmptyUtil.isBlank(itemInput.getDeliveryCosts())) {
						itemDO.setDeliveryCosts(new Money(itemInput.getDeliveryCosts()).getCent());
					}
					if (!EmptyUtil.isBlank(itemInput.getEmsCosts())) {
						itemDO.setEmsCosts(new Money(itemInput.getEmsCosts()).getCent());
					}
					itemDO.setFreeTemplateId(null);
				} else {
					// 运费模板
					if (!EmptyUtil.isBlank(itemInput.getFreeTemplates())) {
						itemDO.setFreeTemplateId(Long.parseLong(itemInput.getFreeTemplates()));
					}
				}
			}else{
				itemDO.setFreeTemplateId(null);
			}

			// 数量
			itemDO.setOriStock(Long.valueOf(itemInput.getNumber()));
			itemDO.setCurStock(Long.valueOf(itemInput.getNumber()));

			// 描述
			if (!itemDO.getDescription().equals(itemInput.getDescription())) {
				itemDO.setDescription(itemInput.getDescription());
			}

			// 地区 城市
			if (!itemInput.getProvinces().equalsIgnoreCase(itemDO.getProvinces())) {
				itemDO.setProvinces(itemInput.getProvinces());
			}
			if (!itemInput.getCity().equalsIgnoreCase(itemDO.getCity())) {
				itemDO.setCity(itemInput.getCity());
			}

			// 店铺 分类
			if (!EmptyUtil.isBlank(itemInput.getStoreCategories())) {
				itemDO.setStoreCategories(itemInput.getStoreCategories().replaceAll(" ", ""));
			}

			// 暂不用字段
			itemDO.setPicColor(0l);

		} catch (ManagerException e) {
			throw e;
		}

		return itemDO;
	}
	
	public static int lengths(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	@Override
	public String[] uploadFileReturnFullPath(File[] imgFile,String[] imgFileFileName,long memberId,String nickName) throws Exception {
		String[] fileName;
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(memberId, null);
			if (shopInfoDO != null && shopInfoDO.getShopId() != null
					&& shopInfoDO.getApproveStatus() != null 
					&& (shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_YES || shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_HEGUI)) {
//				air.addMessage("image_invalid","用户已经开店，请先完成开店流程");
//				return "validateError";
			}else{
				throw new Exception("用户未开店，请先完成开店流程");
			}
//			fileName = fileStorageManager.saveImage(imgFile, imgFileFileName, memberId, "", true);
			fileName = storageFileInfoManager.insertStorageFileInfoExt(imgFile, imgFileFileName, memberId, nickName, 3);
			
			if(fileName==null||fileName.length<0){
				throw new Exception("图片空间不足。");
			}
//			fileInfoManager.addFileInfos(memberId, fileName, imgFile);
			if (fileName != null && fileName.length > 0) {
				String[] fileUrls=new String[fileName.length];
				for (int i = 0; i < fileName.length; i++) {
					if(fileName[i] == null)
						fileUrls[i] = "图片上传失败。";
					else
						fileUrls[i]=PinjuConstant.VIEW_IMAGE_SERVER + fileName[i];
				}
				return fileUrls;
			}
		} catch (ManagerException e) {
			log.error("event=[ItemAOImpl#uploadFileReturnFullPath]upload image file error,for External interface[open api]", e);
			throw new Exception(e);
		}
		return null;
	}
}
