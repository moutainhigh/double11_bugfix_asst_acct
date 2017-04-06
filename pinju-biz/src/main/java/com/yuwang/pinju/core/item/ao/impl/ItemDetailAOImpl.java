/**
 * 
 */
package com.yuwang.pinju.core.item.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.core.item.manager.CategoryProManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsCityIpManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.shop.ao.ShopCategoryAO;
import com.yuwang.pinju.core.shop.ao.ShopInfoMemcacheAO;
import com.yuwang.pinju.core.shop.manager.ShopUserPageManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemDetailResult;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.item.ItemTagMetaInfo;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.item.SpuDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * @Project: pinju-biz
 * @Title: ItemDetailAoImpl.java
 * @Package com.yuwang.pinju.core.item.ao.impl
 * @Description: 商品详情展示页
 * @author liuboen liuboen@zba.com
 * @date 2011-6-7 上午09:42:46
 * @version V1.0
 */

public class ItemDetailAOImpl extends BaseAO implements ItemDetailAO {

	public final static String NULL_STR = "";
	
	private ItemManager itemManager;
	private CategoryProManager categoryProManager;
	private CategoryCacheManager categoryCacheManager;
	private  SkuManager skuManager;
	private ShopUserPageManager shopUserPageManager;
	private LogisticsCityIpManager logisticsCityIpManager;
	private OrderQueryManager orderQueryManager;
	private CategoryManager categoryManager;
	private ShopCategoryAO shopCategoryAO;
	public ShopCategoryAO getShopCategoryAO() {
		return shopCategoryAO;
	}

	public void setShopCategoryAO(ShopCategoryAO shopCategoryAO) {
		this.shopCategoryAO = shopCategoryAO;
	}

	public CategoryManager getCategoryManager() {
		return categoryManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	@Autowired
	@Qualifier("shopInfoMemcacheAO")
	private ShopInfoMemcacheAO shopInfoMemcacheAO;

	/**
	 * @param categoryProManager the categoryProManager to set
	 */
	public void setCategoryProManager(CategoryProManager categoryProManager) {
		this.categoryProManager = categoryProManager;
	}

	/**
	 * @param itemManager
	 *            the itemManager to set
	 */
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	/**
	 * @param logisticsCityIpManager the logisticsCityIpManager to set
	 */
	public void setLogisticsCityIpManager(
			LogisticsCityIpManager logisticsCityIpManager) {
		this.logisticsCityIpManager = logisticsCityIpManager;
	}


	/**
	 * @param orderQueryManager the orderQueryManager to set
	 */
	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.ao.ItemDetailAo#getCategoryDOForestByCateId
	 * (java.lang.Long)
	 */
	@Override
	public CategoryDO getCategoryDOForestByCateId(Long cateId) {
		
		try {
			return categoryCacheManager.getCategoryDOById(cateId);
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getCategoryDOForestByCateId] 通过类目ID获取类目信息失败",e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.ao.ItemDetailAo#getItemDOById(java.lang.Long)
	 */
	@Override
	public ItemDO getItemDOById(Long id) {
		try {
			//return itemManager.getItemDOById(id);
			return categoryCacheManager.getItemDOById(id);
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getItemDOById] 通过ID获取商品详情失败", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemDetailAO#getItemCategoryPro(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> getItemCategoryPro(String cateProMap) {
		try {
		 return	categoryProManager.getItemCateProAndValueByIdMap(cateProMap);
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getItemCategoryPro] 通过cateProMap获取类目属性信息失败",e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemDetailAO#getItemCategorySpubyId(java.lang.String)
	 */
	@Override
	public SpuDO getItemCategorySpubyId(long spuid) {
		try {
			return categoryCacheManager.getItemCategorySpubyId(spuid);
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getItemCategorySpubyId]get spu info error", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemDetailAO#getSkuByItemId(long)
	 */
	@Override
	public ItemDetailResult getSkuByItemId(ItemDO itemDO,ItemDetailResult detailQuery) {
		try {
			List<SkuDO> skuList=itemDO.getSkuList();
			if (skuList==null) {
				return detailQuery;
			}
			List <Map<String,Object>>jsSkuInit=new ArrayList<Map<String,Object>>();
			List <Map<String,Object>> skuListPV =new ArrayList<Map<String,Object>>();
			CategoryPropertyDO skuCateProName_1=null;
			CategoryPropertyDO skuCateProName_2=null;
			CategoryPropertyDO skuCateProName_3=null;
			CategoryPropertyDO skuCateProName_4=null;
			List <BaseValueDO>skuValueLevel_1=new ArrayList<BaseValueDO>();
			List <BaseValueDO>skuValueLevel_2=new ArrayList<BaseValueDO>();
			List <BaseValueDO>skuValueLevel_3=new ArrayList<BaseValueDO>();
			List <BaseValueDO>skuValueLevel_4=new ArrayList<BaseValueDO>();
			int fristSort=0;
			long upperPrice=0;
            long lowerPrice=0;
            
            boolean isSellCustom_1=Boolean.FALSE;
            boolean isSellCustom_2=Boolean.FALSE;
            boolean isSellCustom_3=Boolean.FALSE;
            boolean isSellCustom_4=Boolean.FALSE;
			for (SkuDO skuDO : skuList) {
				
				//初始化的js设置-begin
				Map< String,Object> jsInitMap=new HashMap< String,Object>();
				StringBuilder builder=new StringBuilder();
				String pv_1=skuDO.getSalePv1();
				String pv_2=skuDO.getSalePv2();
				String pv_3=skuDO.getSalePv3();
				String pv_4=skuDO.getSalePv4();
				if (pv_1!=null&&!pv_1.equals("")) {
					String indexPV=StringUtil.substringBefore(pv_1,";");
					long indexPLong=Long.parseLong(StringUtil.substringBefore(indexPV, ":"));
					long indexVLong=Long.parseLong(StringUtil.substringAfter(indexPV, ":"));
					builder.append(indexPV);
					if (fristSort==0) {
						//获取类目属性值,只设置第一个,默认信任数据库数据
						skuCateProName_1=categoryCacheManager.getItemCategoryPropertyById(indexPLong);
						if (skuCateProName_1==null) {
							return detailQuery;
						}
						if (skuCateProName_1 != null && skuCateProName_1.getIsSellCustom()!=null &&
								(skuCateProName_1.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_TRUE || skuCateProName_1.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_NO_IMG_TRUE)) {
							//判断是否为可自定义类目属性
							isSellCustom_1=Boolean.TRUE;
						}
					}
					//获取基础属性值
					BaseValueDO baseValueDO=setCustomBaseValueDO(itemDO.getId(),indexPLong,indexVLong,isSellCustom_1);
					if (!skuValueLevel_1.contains(baseValueDO)) {
						skuValueLevel_1.add(baseValueDO);
					}
					
				}
				if (pv_2!=null&&!pv_2.equals("")) {
					String indexPV=StringUtil.substringBefore(pv_2,";");
					long indexPLong=Long.parseLong(StringUtil.substringBefore(indexPV, ":"));
					long indexVLong=Long.parseLong(StringUtil.substringAfter(indexPV, ":"));
					builder.append("-").append(indexPV);
					if (fristSort==0) {
						//获取类目属性值,只设置第一个,默认信任数据库数据
						skuCateProName_2=categoryCacheManager.getItemCategoryPropertyById(indexPLong);
						if (skuCateProName_2==null) {
							return detailQuery;
						}
						if (skuCateProName_2 != null && skuCateProName_2.getIsSellCustom()!=null &&
								(skuCateProName_2.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_TRUE || skuCateProName_2.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_NO_IMG_TRUE)) {
							//判断是否为可自定义类目属性
							isSellCustom_2=Boolean.TRUE;
						}
					}
					//获取基础属性值
					BaseValueDO baseValueDO=setCustomBaseValueDO(itemDO.getId(),indexPLong,indexVLong,isSellCustom_2);
					
					if (!skuValueLevel_2.contains(baseValueDO)) {
						skuValueLevel_2.add(baseValueDO);
					}
					
				}
				if (pv_3!=null&&!pv_3.equals("")) {
					String indexPV=StringUtil.substringBefore(pv_3,";");
					long indexPLong=Long.parseLong(StringUtil.substringBefore(indexPV, ":"));
					long indexVLong=Long.parseLong(StringUtil.substringAfter(indexPV, ":"));
					builder.append("-").append(indexPV);
					if (fristSort==0) {
						//获取类目属性值,只设置第一个,默认信任数据库数据
						skuCateProName_3=categoryCacheManager.getItemCategoryPropertyById(indexPLong);
						if (skuCateProName_3==null) {
							return detailQuery;
						}
						if (skuCateProName_3 != null && skuCateProName_3.getIsSellCustom()!=null &&
								(skuCateProName_3.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_TRUE || skuCateProName_3.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_NO_IMG_TRUE)) {
							//判断是否为可自定义类目属性
							isSellCustom_3=Boolean.TRUE;
						}
					}
					//获取基础属性值
					BaseValueDO baseValueDO=setCustomBaseValueDO(itemDO.getId(),indexPLong,indexVLong,isSellCustom_3);
					if (!skuValueLevel_3.contains(baseValueDO)) {
						skuValueLevel_3.add(baseValueDO);
					}
					
				}
				if (pv_4!=null&&!pv_4.equals("")) {
					String indexPV=StringUtil.substringBefore(pv_4,";");
					long indexPLong=Long.parseLong(StringUtil.substringBefore(indexPV, ":"));
					long indexVLong=Long.parseLong(StringUtil.substringAfter(indexPV, ":"));
					builder.append("-").append(indexPV);
					if (fristSort==0) {
						//获取类目属性值,只设置第一个,默认信任数据库数据
						skuCateProName_4=categoryCacheManager.getItemCategoryPropertyById(indexPLong);
						if (skuCateProName_4==null) {
							return detailQuery;
						}
						if (skuCateProName_4 != null && skuCateProName_4.getIsSellCustom()!=null &&
								(skuCateProName_4.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_TRUE || skuCateProName_4.getIsSellCustom() == CategoryPropertyDO.SELL_CUSTOM_NO_IMG_TRUE)) {
							//判断是否为可自定义类目属性
							isSellCustom_4=Boolean.TRUE;
						}
					}
					//获取基础属性值
					BaseValueDO baseValueDO=setCustomBaseValueDO(itemDO.getId(),indexPLong,indexVLong,isSellCustom_4);
					if (!skuValueLevel_4.contains(baseValueDO)) {
						skuValueLevel_4.add(baseValueDO);
					}
				
				}
				jsInitMap.put("JsSkuPv", builder.toString());
				jsInitMap.put("JsSkuPrice", MoneyUtil.getCentToDollar(skuDO.getPrice()));
				jsInitMap.put("JsSkuId", skuDO.getId());
				jsInitMap.put("JsSkuCurrentStock", skuDO.getCurrentStock());
				jsSkuInit.add(jsInitMap);
				//初始化的js设置-end
				
				if (upperPrice==0||upperPrice<skuDO.getPrice().longValue()) {
					upperPrice=skuDO.getPrice().longValue();
				}
				if (lowerPrice==0||lowerPrice>skuDO.getPrice().longValue()) {
					lowerPrice=skuDO.getPrice().longValue();
				}
				
				fristSort++;
			}
			//设置SKU catepro 和 value信息到SKU-PV
			if (skuCateProName_1 != null) {
				skuListPV = setSkuListPV(skuCateProName_1, skuValueLevel_1, skuListPV);
			}
			if (skuCateProName_2 != null) {
				skuListPV = setSkuListPV(skuCateProName_2, skuValueLevel_2, skuListPV);
			}
			if (skuCateProName_3 != null) {
				skuListPV = setSkuListPV(skuCateProName_3, skuValueLevel_3, skuListPV);
			}
			if (skuCateProName_4 != null) {
				skuListPV = setSkuListPV(skuCateProName_4, skuValueLevel_4, skuListPV);
			}
			if (skuListPV!=null&&skuListPV.size()>0) {
				detailQuery.setSkuListPV(skuListPV);
			}
			if (jsSkuInit!=null&&jsSkuInit.size()>0) {
				detailQuery.setJsSkuInit(jsSkuInit);
			}
			detailQuery.setUpperPrice(MoneyUtil.getCentToDollar(upperPrice));
			detailQuery.setLowerPrice(MoneyUtil.getCentToDollar(lowerPrice));
			return detailQuery;
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getSkuByItemId]get sku info error", e);
		}catch (NullPointerException e) {
			log.warn("Event=[ItemDetailAo#getSkuByItemId]get sku info error,可能该销售属性被后台删除", e);
		}catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	/**
	 * 设置信息
	 * @param categoryPropertyDO
	 * @param baseValueDOs
	 * @param skuListPV
	 * @return
	 */
	private List<Map<String, Object>> setSkuListPV(CategoryPropertyDO categoryPropertyDO, List<BaseValueDO> baseValueDOs, List<Map<String, Object>> skuListPV) {
		Map<String, Object> skuMapPV = new HashMap<String, Object>();
		skuMapPV.put("cateProlevel", categoryPropertyDO);
		if (baseValueDOs != null && baseValueDOs.size() > 0) {
			skuMapPV.put("levelList", baseValueDOs);
			skuListPV.add(skuMapPV);
		}
		return skuListPV;
	}
	/**
	 * 设置自定义属性值
	 * @param itemId
	 * @param indexPLong
	 * @param indexVLong
	 * @param isSellCustom
	 * @return
	 * @throws ManagerException
	 */
	private BaseValueDO setCustomBaseValueDO(long itemId,long indexPLong,long indexVLong,boolean isSellCustom) throws ManagerException{
		//获取基础属性值
		BaseValueDO baseValueDO=categoryCacheManager.getBaseValueById(indexVLong);
		if (isSellCustom) {
			//设置自定义SKU值
			CustomProValueDO customProValueDO=categoryCacheManager.getCustomProValueDO(itemId, indexPLong, indexVLong);
			String[] values=baseValueDO.getValue().split("\\$\\@");
			if (customProValueDO!=null) {
				//判断是否自定义了图片
				if (customProValueDO.getValueType()==CustomProValueDO.VALUE_TYPE_IMG.intValue()) {
					baseValueDO.setValueType(CustomProValueDO.VALUE_TYPE_IMG);
					baseValueDO.setImgUrl(customProValueDO.getImgUrl());
				}else {
					//没有自定义图片则设置basevalue为通用字符串类型
					baseValueDO.setValueType(CustomProValueDO.VALUE_TYPE_COMMON);
				}
				//查看是否自定义了
				if (customProValueDO.getValue()!=null&&!customProValueDO.getValue().equals("")) {
					baseValueDO.setValue(customProValueDO.getValue());	
				}else {
					if (2<=values.length) {
						baseValueDO.setValue(values[1]);
					}
				}
			}else {
				if (2<=values.length) {
					baseValueDO.setValue(values[1]);
					baseValueDO.setValueType(CustomProValueDO.VALUE_TYPE_COMMON);
				}else {
					baseValueDO.setValueType(1);
				}
			}
		}else {
			List<CategoryPropertyValueDO> categoryPropertyValueList = categoryCacheManager.getItemCategoryPropertyValue(indexPLong);
			if(categoryPropertyValueList!=null&&categoryPropertyValueList.size()>0){
				for(int i=0;i<categoryPropertyValueList.size();i++){
					CategoryPropertyValueDO cpDo = categoryPropertyValueList.get(i);
					if(cpDo.getValueId().equals(baseValueDO.getId())){
						if(cpDo.getValueType()!=null&&cpDo.getValueType()!=1){
							String[] ss = baseValueDO.getValue().split("\\$\\@");
							baseValueDO.setValue(ss[1]);
						}
						continue;
					}
				}
			}
			baseValueDO.setValueType(1);
		}
		return baseValueDO;
	}
	
	@Deprecated
	public ItemDetailResult getSkuByItemId2(long itemId,ItemDetailResult detailQuery) {
		try {
			List<SkuDO> skuList=skuManager.getItemSkuByItemId(itemId);
			List <Map<String,Object>>jsSkuInit=new ArrayList<Map<String,Object>>();
			List <Map<String,Object>> skuListPV =new ArrayList<Map<String,Object>>();
			/*CategoryPropertyDO skuCateProName_1=null;
			CategoryPropertyDO skuCateProName_2=null;
			CategoryPropertyDO skuCateProName_3=null;
			CategoryPropertyDO skuCateProName_4=null;*/
			List<CategoryPropertyDO> cateProDOList=new ArrayList<CategoryPropertyDO>();
			List <BaseValueDO>skuValueLevel_1=new ArrayList<BaseValueDO>();
			List <BaseValueDO>skuValueLevel_2=new ArrayList<BaseValueDO>();
			List <BaseValueDO>skuValueLevel_3=new ArrayList<BaseValueDO>();
			List <BaseValueDO>skuValueLevel_4=new ArrayList<BaseValueDO>();
			List<List <BaseValueDO>> skuValueLevelList=new ArrayList<List <BaseValueDO>>();
			skuValueLevelList.add(skuValueLevel_1);
			skuValueLevelList.add(skuValueLevel_2);
			skuValueLevelList.add(skuValueLevel_3);
			skuValueLevelList.add(skuValueLevel_4);
			int fristSort=0;
			long upperPrice=0;
			long lowerPrice=0;
			for (SkuDO skuDO : skuList) {
				
				//初始化的js设置-begin
				Map< String,Object> jsInitMap=new HashMap< String,Object>();
				StringBuilder builder=new StringBuilder();
				List<String> salePvList=new ArrayList<String>();
				salePvList.add(skuDO.getSalePv1());
				salePvList.add(skuDO.getSalePv2());
				salePvList.add(skuDO.getSalePv3());
				salePvList.add(skuDO.getSalePv4());
				for (String pv : salePvList) {
					int listSize=0;
					if (pv!=null&&!pv.equals("")) {
						String indexPV=StringUtil.substringBefore(pv,";");
						String indexP=StringUtil.substringBefore(indexPV, ":");
						String indexV=StringUtil.substringAfter(indexPV, ":");
						long indexPLong=Long.parseLong(indexP);
						long indexVLong=Long.parseLong(indexV);
						builder.append(indexPV);
						//获取基础属性值
						BaseValueDO baseValueDO=categoryCacheManager.getBaseValueById(indexVLong);
						if (!skuValueLevelList.get(listSize).contains(baseValueDO)) {
							skuValueLevelList.get(listSize).add(baseValueDO);
						}
						if (fristSort==0) {
							//获取类目属性值,只设置第一个,默认信任数据库数据
							CategoryPropertyDO  cateProDO=categoryCacheManager.getItemCategoryPropertyById(indexPLong);
							cateProDOList.add(cateProDO);
						}
					}
					listSize++;
				}
				
				jsInitMap.put("JsSkuPv",builder.toString());
				jsInitMap.put("JsSkuPrice",MoneyUtil.getCentToDollar(skuDO.getPrice()));
				jsInitMap.put("JsSkuId",skuDO.getId());
				jsInitMap.put("JsSkuCurrentStock", skuDO.getCurrentStock());
				jsSkuInit.add(jsInitMap);
				//初始化的js设置-end
				
				if (upperPrice==0||upperPrice<skuDO.getPrice().longValue()) {
					upperPrice=skuDO.getPrice().longValue();
					upperPrice=skuDO.getPrice().longValue();
				}
				if (lowerPrice==0||lowerPrice>skuDO.getPrice().longValue()) {
					lowerPrice=skuDO.getPrice().longValue();
					lowerPrice=skuDO.getPrice().longValue();
				}
				
				fristSort++;
			}
			//获取四级以上的循环
			for (CategoryPropertyDO cateProDO : cateProDOList) {
				int listSize=0;
				if (cateProDO!=null) {
					Map<String,Object> skuMapPV=new HashMap<String, Object>();
					skuMapPV.put("cateProlevel", cateProDO);
					if (skuValueLevelList.get(listSize)!=null&&skuValueLevelList.get(listSize).size()>0) {
						skuMapPV.put("levelList",skuValueLevelList.get(listSize));
						skuListPV.add(skuMapPV);
					}
				}
				listSize++;
			}
			
			if (skuListPV!=null&&skuListPV.size()>0) {
				detailQuery.setSkuListPV(skuListPV);
			}
			if (jsSkuInit!=null&&jsSkuInit.size()>0) {
				detailQuery.setJsSkuInit(jsSkuInit);
			}
			detailQuery.setUpperPrice(MoneyUtil.getCentToDollar(upperPrice));
			detailQuery.setLowerPrice(MoneyUtil.getCentToDollar(lowerPrice));
			return detailQuery;
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getSkuByItemId]get sku info error", e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemDetailAO#getShopInfoByUserId(long, com.yuwang.pinju.domain.item.ItemDetailQuery)
	 */
	@Override
	public ItemDetailResult getShopInfoByUserId(long userId,
			ItemDetailResult detailQuery) {
		try {
			String shopLeftHtml=shopUserPageManager.getLeftPageHtml(userId);
			String shopUpHtml=shopUserPageManager.getTopPageHtml(userId);
			detailQuery.setShopLeftHtml(shopLeftHtml);
			detailQuery.setShopUpHtml(shopUpHtml);
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getShopInfoByUserId]get shop info error", e);
		}
		return detailQuery;
	}
	
	/**
	 * @param categoryCacheManager the categoryCacheManager to set
	 */
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	/**
	 * @param skuManager the skuManager to set
	 */
	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	/**
	 * @param shopUserPageManager the shopUserPageManager to set
	 */
	public void setShopUserPageManager(ShopUserPageManager shopUserPageManager) {
		this.shopUserPageManager = shopUserPageManager;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemDetailAO#resetFullCategory()
	 */
	@Override
	public boolean resetFullCategory() {
		try {
			return categoryCacheManager.resetFullCategory();
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#resetFullCategory]reset full cache category error", e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemDetailAO#getLogisticsCityByIp(java.lang.String)
	 */
	@Override
	public Map<String, String>  getLogisticsCityByIp(Long logisticsTemplateId,String cityIp) {
		try {
			
			return logisticsCityIpManager.getDefaultRegionCarriage(logisticsTemplateId, cityIp);
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getLogisticsCityByIp]get LogisticsCity error", e);
		}catch (Exception e) {
			log.error("Event=[ItemDetailAo#getLogisticsCityByIp]get LogisticsCity ,exception error", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemDetailAO#getActivityDiscountDOById(java.lang.Long)
	 */
	@Override
	public ActivityDiscountDO getActivityDiscountDOById(String disid) {
		if (disid==null) {
			return null;
		}
		try {
			long discountID=Long.parseLong(disid);
			ActivityDiscountDO activityDiscountDO= categoryCacheManager.getActivityDiscountDOId(discountID);
			if (activityDiscountDO==null) {
				return null;
			}
			long dateNow=new Date().getTime();
			//验证是否活动到期以及是否活动中
			if (activityDiscountDO.getStartTime().getTime()<=dateNow&&activityDiscountDO.getEndTime().getTime()>dateNow) {
				return activityDiscountDO;
			}else {
				/*if (activityDiscountDO.getStatus().longValue()!=1) {
					//主动更新活动信息
					Result featureResult=itemTimeingAO.emptyFeture(activityDiscountDO.getItemList(), activityDiscountDO.getId(),ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT);
					if (!featureResult.isSuccess()) {
						log.error("Event=[ItemDetailAo#getActivityDiscountDOById]update act_discount or item features status error,act_discount id ="+activityDiscountDO.getId());
					}
				}*/
			}
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getLogisticsCityByIp]get LogisticsCity error", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.ao.ItemDetailAO#getActivityDiscountDOById(com.yuwang.pinju.domain.order.query.QueryOrderItem)
	 */
	@Override
	public List<OrderItemDO> getBuyOrderItemDOListById(
			QueryOrderItem queryOrderItemDO) {
		try {
			Long count =orderQueryManager.queryOrderItemDONum(queryOrderItemDO);
			queryOrderItemDO.setItems(count.intValue());
			List<OrderItemDO> list=orderQueryManager.queryOrderItemDOList(queryOrderItemDO);
			QueryOrderItem tempDO=new QueryOrderItem();
			int status[]={OrderItemDO.ORDER_ITEM_STATE_2,OrderItemDO.ORDER_ITEM_STATE_3,OrderItemDO.ORDER_ITEM_STATE_5};
			tempDO.setItemId(queryOrderItemDO.getItemId());
			tempDO.setOrderItemState(status);
			Long allItem=orderQueryManager.queryOrderItemBuyNum(tempDO);
			queryOrderItemDO.setAllItem(allItem);
			return list;
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getBuyOrderItemDOListById]get getActivityDiscountDOById error", e);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getSimpleItemDOsByids(String ids,
			long sellerId) {
		List<ItemDO> list = null;
		try {
			if (ids != null&&!ids.equals(NULL_STR)) {
				list = itemManager.getReadSimpleItemListByIds(ids,sellerId);
			} else {
				ItemQueryEx itemQuery = new ItemQueryEx();
				itemQuery.setSellerId(sellerId);
				itemQuery.setItemsPerPage(4);
				list = itemManager.queryItemListEx(itemQuery);

			}

			return itemManager.getSimpleItemMaps(list);
		} catch (ManagerException e) {
			log.error("Event=[ItemDetailAo#getSimpleItemDOsByids]", e);
		}
		return null;
	}

	@Override
	public ItemTagMetaInfo getItemTagMetaInfo(ItemDO itemDO) {
		//将标签和meta信息抽象为单独DO,update by liuboen
				ItemTagMetaInfo itemTagMetaInfo = new ItemTagMetaInfo();
				/*******************************start 杨昭***************************************/
				
				try{
					//找商品类目   
					CategoryDO itemCatetoryDO = categoryManager.getItemCategory(itemDO.getCatetoryId());
					itemTagMetaInfo.setItemCategory(itemCatetoryDO.getName());
					//获取商品名称
					itemTagMetaInfo.setItemTitle(itemDO.getTitle());
					//获取店铺信息   
					//调用缓存数据
					ShopInfoDO shopInfoDO = shopInfoMemcacheAO.getShopInfoDO(itemDO.getSellerId());
					if(shopInfoDO!=null){
						if(shopInfoDO.getSellerType()!=null){
							//店铺类型
							if(shopInfoDO.getSellerType().equals("0")){
								itemTagMetaInfo.setShopType("普通店");
							}
							if(shopInfoDO.getSellerType().equals("1")){
								itemTagMetaInfo.setShopType("品牌店");
							}
							if(shopInfoDO.getSellerType().equals("2")){
								itemTagMetaInfo.setShopType("旗舰店");
							}
							if(shopInfoDO.getSellerType().equals("3")){
								itemTagMetaInfo.setShopType("i小铺");
							}
						}
						//获取店铺类目
						CategoryDO shopCatetoryDO = categoryManager.getItemCategory(Long.parseLong(shopInfoDO.getShopCategory()));
						if(shopCatetoryDO!=null){
							itemTagMetaInfo.setShopCategoryStr(shopCatetoryDO.getName());
						}
						//获取店铺标签
						if(shopInfoDO.getShopLabel()!=null){
							itemTagMetaInfo.setShopLabel(shopInfoDO.getShopLabel());
							List <String>shopLabelList = new ArrayList<String>();
							String[] strShopLabel = shopInfoDO.getShopLabel().split(" ");
							if(strShopLabel!=null){
								for(int i=0;i<strShopLabel.length;i++){
									shopLabelList.add(strShopLabel[i]);
								}
								itemTagMetaInfo.setShopLabelList(shopLabelList);
							}
						}
					}
				}catch(Exception e){
					log.error("商品详情页获取店铺标签信息出现异常", e);
				}
				/*******************************end 杨昭***************************************/
				
				return itemTagMetaInfo;
	}
}
