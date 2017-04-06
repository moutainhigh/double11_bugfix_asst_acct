package com.yuwang.pinju.core.item.ao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.item.ao.CategoryAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.BrandDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CpValueRelationDO;
import com.yuwang.pinju.domain.item.SearchCategoryResult;
import com.yuwang.pinju.domain.item.SkuDO;

public class CategoryAOImpl extends BaseAO implements CategoryAO {

	private CategoryCacheManager categoryCacheManager;

	private SkuManager skuManager;

	@Override
	public Map getItemSku(String itemId) {
		JSONObject jsonObject = new JSONObject();
		try {
			List<SkuDO> skuList = skuManager.getItemSkuByItemId(Long.valueOf(itemId));
			if (skuList == null || skuList.size() < 1) {
				return null;
			}
			jsonObject.put("itemSku", skuList);
		} catch (ManagerException e) {
			log.error("获得商品SKU", e);
		}
		return jsonObject;
	}

	@Override
	public Map getSonPro(String cpId, String cpvId) {
		

		JSONObject jsonObject = new JSONObject();
		try {

			// 类目关系
			CpValueRelationDO cpValueRelationDO = categoryCacheManager.getItemCpValueRelationByCpIdAndCpvId(Long
					.valueOf(cpId), Long.valueOf(cpvId));
			
			if (cpValueRelationDO == null) {
				return null;
			}
			
			List<String> ids = Arrays.asList(cpValueRelationDO.getChildCpValueIds().split(","));

			// 子类目属性
			CategoryPropertyDO sonCategoryPropertyDO = categoryCacheManager
					.getItemCategoryPropertyById(cpValueRelationDO.getChildCpId());

			jsonObject.put("preCpId", cpId);
			jsonObject.put("sonCpId", sonCategoryPropertyDO.getId());
			jsonObject.put("sonCpName", sonCategoryPropertyDO.getName());

			// 子属性值列表
			List<CategoryPropertyValueDO> sonPropertys = categoryCacheManager
					.getItemCategoryPropertyValue(sonCategoryPropertyDO.getId());
			Map<String, String> names = new HashMap<String, String>();
			
			//对子属性进行排序
//			if(sonPropertys!=null&&!sonPropertys.equals("")){
//				Collections.sort(sonPropertys, new Comparator<CategoryPropertyValueDO>() {
//				public int compare(CategoryPropertyValueDO cpvd1, CategoryPropertyValueDO cpvd2) {
//					return (int) (cpvd1.getSortOrder() - cpvd2.getSortOrder());
//		            }
//		        });
//			}

			for (CategoryPropertyValueDO sonCategoryPropertyValueDO : sonPropertys) {

				if (!ids.contains(String.valueOf(sonCategoryPropertyValueDO.getValueId()))) {
					continue;
				}
				if (sonCategoryPropertyDO.getIsBrandProperty() == null
						|| sonCategoryPropertyDO.getIsBrandProperty() == CategoryPropertyDO.IS_NO) {
					// 取类目基本属性值
					BaseValueDO baseValueDO = categoryCacheManager.getBaseValueById(sonCategoryPropertyValueDO
							.getValueId());
					names.put(String.valueOf(sonCategoryPropertyValueDO.getValueId()), baseValueDO.getValue());
					log.debug("baseValueDO.getValue()================>"+baseValueDO.getValue());
				} else {
					// 取品牌属性值
					BrandDO brandDO = categoryCacheManager.getItemBrandById(sonCategoryPropertyValueDO.getValueId());
					names.put(String.valueOf(sonCategoryPropertyValueDO.getValueId()), brandDO.getName());
					log.debug("brandDO.getName()================>"+brandDO.getName());
				}
			}
			
			//对子属性进行排序
			LinkedHashMap<String, String> nameExt = new LinkedHashMap<String, String>();
			if(ids!=null&&ids.size()>0){
				for(int i =0; i<ids.size(); i++){
					if(names.get(ids.get(i))!=null){
						nameExt.put(ids.get(i), names.get(ids.get(i)));
					}
				}
			}
//			System.out.println(names);
//			System.out.println(ids);
//			System.out.println(nameExt);
			jsonObject.put("sonCpValueIds", nameExt);
		} catch (ManagerException e) {
			log.error("获得子类目属性", e);
		}

		return jsonObject;

	}

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	/**
	 * copy 对象
	 * @param complexCategoryDOs
	 * @return
	 */
	private List<CategoryDO> getSimpleCategoryDOs(List<CategoryDO> complexCategoryDOs){
		ArrayList<CategoryDO> newCatList = new ArrayList<CategoryDO>();
		for (CategoryDO categoryDO : complexCategoryDOs) {
			newCatList.add(categoryDO.simpleClone());
		}
		return newCatList;
	}
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.CategoryAO#getCategoryLevelByCateId(long)
	 */
	@Override
	public SearchCategoryResult getCategoryLevelByCateId(long cateId) {
		try {
			CategoryDO  categoryDO=categoryCacheManager.getForestCategoryDOById(cateId);
			if (categoryDO==null) {
				return null;
			}
			SearchCategoryResult query=new SearchCategoryResult();
			Map<Long,List<CategoryDO>> cateMap=new HashMap<Long, List<CategoryDO>>();
			int outInt=1;
			while (categoryDO.getCategoryLevel()>0&&outInt<=4) {
				long thisCateId=categoryDO.getId();
				String thisCateName = categoryDO.getName();
				switch (categoryDO.getCategoryLevel().intValue()) {
				case 4:
					query.setFourLevleCateId(thisCateId);
					query.setFourLevleCateName(thisCateName);
					break;
				case 3:
					query.setThreeLevelCateId(thisCateId);
					query.setThreeLevelCateName(thisCateName);
					//cateMap.put(thisCateId,getSimpleCategoryDOs(categoryDO.getChildCateList()));
					break;
				case 2:
					query.setTwoLevelCateId(thisCateId);
					query.setTwoLevelCateName(thisCateName);
					//cateMap.put(thisCateId,getSimpleCategoryDOs(categoryDO.getChildCateList()));
					break;
				case 1:
					query.setOneLevelCateId(thisCateId);
					query.setOneLevelCateName(thisCateName);
					//cateMap.put(thisCateId,getSimpleCategoryDOs(categoryDO.getChildCateList()));
					break;
				default:
					break;
				}
				if (categoryDO.getCategoryLevel()!=1) {
					categoryDO=categoryDO.getParentCategoryDO();
				}
				outInt++;
			}
		/*	boolean flag=true;
			int outInt=1;
			//获取父节点集合
			while (flag&&outInt<=4) {
					switch (categoryDO.getCategoryLevel().intValue()) {
					case 4:
						query.setFourLevleCateId(categoryDO.getId());
						break;
					case 3:
						query.setThreeLevelCateId(categoryDO.getId());
						break;
					case 2:
						query.setTwoLevelCateId(categoryDO.getId());
						break;
					case 1:
						query.setOneLevelCateId(categoryDO.getId());
						flag=false;
						break;
					default:
						break;
					}
					if (categoryDO.getCategoryLevel()!=1) {
						categoryDO=categoryCacheManager.getCategoryDOById(categoryDO.getParentId());
					}
				outInt++;
			}
			Map<Long,List<CategoryDO>> cateMap=new HashMap<Long, List<CategoryDO>>();*/
			List <CategoryDO> twoLevelList=categoryCacheManager.getItemCategoryListByParentId(query.getOneLevelCateId());
			cateMap.put(query.getOneLevelCateId(),twoLevelList);
			for (CategoryDO categoryDO2 : twoLevelList) {
				if (categoryDO2.getIsLeaf()!=1) {
					List <CategoryDO> threeLevelList=categoryCacheManager.getItemCategoryListByParentId(categoryDO2.getId());
					cateMap.put(categoryDO2.getId(), threeLevelList);
					for (CategoryDO categoryDO3 : threeLevelList) {
						if (categoryDO3.getIsLeaf()!=1) {
							List <CategoryDO> fourLevelList=categoryCacheManager.getItemCategoryListByParentId(categoryDO3.getId());
							cateMap.put(categoryDO3.getId(), fourLevelList);
						}
					}
				}
			}
			query.setCateMap(cateMap);
			query.setCurrentFrom(1);
			return query;
		} catch (ManagerException e) {
			log.error("获取类目信息失败",e);
		}
		return null;
	}

	@Override
	public Result sercheCategoryDOInfo(String cateIdStr) {
		Result result = new ResultSupport();
		List<CategoryDO> cateList = new ArrayList<CategoryDO>();
		int totalNum = 0;
		try {
			String cateCol[] = cateIdStr.split(";");
			for (int i = 0; i < cateCol.length; i++) {
				String cateColl[] = cateCol[i].split(",");
				CategoryDO categoryDO = categoryCacheManager.getCategoryDOById(Long.parseLong(cateColl[0]));
				categoryDO.setItemNum(Long.parseLong(cateColl[1]));
				cateList.add(categoryDO);
				totalNum += Integer.parseInt(cateColl[1]);
			}
			
			Long cateId = cateList.get(0).getParentId();
			CategoryDO category = categoryCacheManager.getForestCategoryDOById(cateId);
			String pCateName = category.getName();
			SearchCategoryResult query=new SearchCategoryResult();
			query.setCurrentFrom(2);
			
			result.setModel("totalNum", totalNum);
			result.setModel("cateList", cateList);
			result.setModel("pCateName", pCateName);
			result.setModel("currentFrom", query);
			
		} catch (ManagerException e) {
			log.debug("查找缓存类目异常",e);
		}
		
		return result;
	}
	
	public List<CategoryDO> getCategoryParentsByCateId(long cateId){
		List<CategoryDO> list=null;
		try{
			list = new ArrayList<CategoryDO>();
			CategoryDO category = categoryCacheManager.getCategoryDOById(cateId);
			list.add(category);
			while(category!=null &&category.getParentId()!=0L){
				category = categoryCacheManager.getCategoryDOById(category.getParentId());
				list.add(category);
			}
		}catch(ManagerException e){
			log.debug("查找缓存类目异常",e);
		}
		return list;
	}
}
