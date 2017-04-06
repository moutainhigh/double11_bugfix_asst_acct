/**
 * 
 */
package com.yuwang.pinju.core.item.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.common.StringUtilBYTE;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.dao.BaseValueDAO;
import com.yuwang.pinju.core.item.dao.CategoryPropertyDAO;
import com.yuwang.pinju.core.item.dao.CategoryPropertyValueDAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.CategoryProManager;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;

/**  
 * @Project: pinju-biz
 * @Title: CategoryProManagerImpl.java
 * @Package com.yuwang.pinju.core.item.manager.impl
 * @Description: 商品属性和属性值impl
 * @author liuboen liuboen@zba.com
 * @date 2011-6-8 下午04:07:43
 * @version V1.0  
 */

public class CategoryProManagerImpl implements CategoryProManager {
	private final static int MAX_CATE_PRO_VARCHAR=29;
	CategoryPropertyDAO categoryPropertyDAO;
	CategoryPropertyValueDAO categoryPropertyValueDAO;
	BaseValueDAO baseValueDAO;
	private CategoryCacheManager categoryCacheManager;
	/**
	 * @param baseValueDAO the baseValueDAO to set
	 */
	public void setBaseValueDAO(BaseValueDAO baseValueDAO) {
		this.baseValueDAO = baseValueDAO;
	}
	/**
	 * @param categoryPropertyDAO the categoryPropertyDAO to set
	 */
	public void setCategoryPropertyDAO(CategoryPropertyDAO categoryPropertyDAO) {
		this.categoryPropertyDAO = categoryPropertyDAO;
	}
	/**
	 * @param categoryPropertyValueDAO the categoryPropertyValueDAO to set
	 */
	public void setCategoryPropertyValueDAO(
			CategoryPropertyValueDAO categoryPropertyValueDAO) {
		this.categoryPropertyValueDAO = categoryPropertyValueDAO;
	}
	/**
	 * @param categoryCacheManager the categoryCacheManager to set
	 */
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryProManager#getItemCateProAndValueByIdMap(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> getItemCateProAndValueByIdMap(
			String cateProMap) throws ManagerException {
		if (cateProMap==null||cateProMap.equalsIgnoreCase("")) {
			return null;
		}
		String []cateProStrs=cateProMap.split(";");
		List <Map<String, Object>>cateProList=new ArrayList<Map<String, Object>>();
		try{
		for (String cateProStr : cateProStrs) {
			Map<String, Object>  tempMap=new HashMap<String, Object>();
			//获得属性
			Long cateProId=Long.parseLong(StringUtil.substringBefore(cateProStr, ":"));
			CategoryPropertyDO cateProDO =categoryCacheManager.getItemCategoryPropertyById(cateProId);
			//校验该类目属性是否被删除
			if (cateProDO!=null&&cateProDO.getStatus().longValue()==1l) {
				
				//获得属性值
				String cateProValueMap=StringUtil.substringAfter(cateProStr, ":");
					String cateProValueStrs[]=cateProValueMap.split(",");
					StringBuilder cateProValueBuilder=new StringBuilder();
					int num=0;
					for (String cateProValueStr : cateProValueStrs) {
						Long cateProValueId=Long.parseLong(cateProValueStr);
						BaseValueDO baseValueDO= categoryCacheManager.getBaseValueById(cateProValueId);
						if (baseValueDO!=null) {
							cateProValueBuilder.append(baseValueDO.getValue());
						}
						if (num<cateProValueStrs.length-1) {
							cateProValueBuilder.append(",");
						}
						num++;
					}
					String thisProValue = cateProValueBuilder.toString();
					String thisProName=cateProDO.getName();
					//截取长度默认一个中问算两个字符
					String thisCateProStr=thisProName+":"+cateProValueBuilder.toString();
					int thisCateProStrLenth=StringUtilBYTE.getLengh(thisCateProStr);
					if (thisCateProStrLenth>MAX_CATE_PRO_VARCHAR) {
						int subLenth=MAX_CATE_PRO_VARCHAR-StringUtilBYTE.getLengh(thisProName)-StringUtilBYTE.getLengh(":");
						if (subLenth>0) {
							thisProValue=StringUtilBYTE.substring(cateProValueBuilder.toString(), 0, subLenth)+"...";
						}else {
							thisProValue="...";
							thisProName=StringUtilBYTE.substring(thisProName, 0, MAX_CATE_PRO_VARCHAR)+"...";
						}
					}
					
					tempMap.put("cateProName",thisProName);
					tempMap.put("cateProValue", thisProValue);
					cateProList.add(tempMap);
			}
		}
		} catch (ManagerException e) {
			throw new ManagerException("Event=[CategoryProManager.getItemCateProAndValueByIdMap] 通过商品属性键值对获取属性和属性值错误",e);
		}
		return cateProList;
	}
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryProManager#getCategoryPropertyByCateProId(long)
	 */
	@Override
	public CategoryPropertyDO getCategoryPropertyByCateProId(long cateProId)
			throws ManagerException {
		try {
			CategoryPropertyDO cateProDO =categoryPropertyDAO.selectCategoryPropertyByCateProId(cateProId);
			if (cateProDO.getStatus().longValue()==1l) {
				return cateProDO;
			}else {
				return null;
			}
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CategoryProManager#getBaseValueDOByBaseValueId(long)
	 */
	@Override
	public BaseValueDO getBaseValueDOByBaseValueId(long baseValueId)
			throws ManagerException {
		try {
			return baseValueDAO.selectItemBaseValueById(baseValueId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

}
