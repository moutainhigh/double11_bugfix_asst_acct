package com.yuwang.pinju.core.item.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.dao.BaseValueDAO;
import com.yuwang.pinju.core.item.dao.CategoryDAO;
import com.yuwang.pinju.core.item.dao.CategoryPropertyDAO;
import com.yuwang.pinju.core.item.dao.CategoryPropertyValueDAO;
import com.yuwang.pinju.core.item.dao.CpValueRelationDAO;
import com.yuwang.pinju.core.item.dao.SpuDAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;
import com.yuwang.pinju.domain.item.CpValueRelationDO;
import com.yuwang.pinju.domain.item.SpuDO;

/**
 * 
 * @author liming
 * 
 */
public class CategoryManagerImpl extends BaseManager implements CategoryManager {

	private CategoryDAO categoryDAO;

	private CategoryPropertyDAO categoryPropertyDAO;

	private CategoryPropertyValueDAO categoryPropertyValueDAO;

	private BaseValueDAO baseValueDAO;

	private CpValueRelationDAO cpValueRelationDAO;

	private SpuDAO spuDAO;

	private CategoryCacheManager categoryCacheManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CategoryManager#insertItemBaseValue
	 * (com.yuwang.pinju.domain.item.BaseValueDO)
	 */
	@Override
	public Long getItemBaseValueByValue(String value) throws ManagerException {
		try {
			BaseValueDO baseValueDO = baseValueDAO.getBaseValueByValue(value);
			if (baseValueDO == null) {
				Calendar c = Calendar.getInstance();
				baseValueDO = new BaseValueDO();
				baseValueDO.setValue(value);
				baseValueDO.setGmtCreate(c.getTime());
				baseValueDO.setGmtModified(c.getTime());
				baseValueDO.setHashcode(value.hashCode() + 0l);
				return (Long) baseValueDAO.insertItemBaseValue(baseValueDO);
			}
			return baseValueDO.getId();
		} catch (DaoException e) {
			throw new ManagerException("获得基本属性编号", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemCategory(long)
	 */
	@Override
	public CategoryDO getItemCategory(long id) throws ManagerException {
		try {
			return categoryDAO.selectItemCategoryById(id);
		} catch (DaoException e) {
			throw new ManagerException("获得类目对象错误", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#getItemCategoryNameList (long)
	 */
	@Override
	public List<CategoryDO> getItemCategoryList(long parentId) throws ManagerException {
		try {
			return categoryDAO.selectItemCategoryByParentId(parentId);
		} catch (DaoException e) {
			throw new ManagerException("获得类目名称列表错误", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#itemReleased(com.yuwang .pinju.domain.item.ItemItemDO)
	 */
	@Override
	public List<CategoryPropertyDO> getItemCategoryProperty(long categoryId) throws ManagerException {
		try {
			return categoryPropertyDAO.getCategoryPropertyByCategoryId(categoryId);
		} catch (DaoException e) {
			throw new ManagerException("获得类目属性错误", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.ItemManager#itemReleased(com.yuwang .pinju.domain.item.ItemItemDO)
	 */
	@Override
	public List<CategoryPropertyValueDO> getItemCategoryPropertyValue(long categoryPropertyId) throws ManagerException {
		try {
			return categoryPropertyValueDAO.getCategoryPropertyValue(categoryPropertyId);
		} catch (DaoException e) {
			throw new ManagerException("获得类目属性值错误", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CategoryManager#getItemSpuByKey(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Map getItemSpuByKey(Long keyPropertyId, Long keyPropertyValue) throws ManagerException {
		JSONObject m = new JSONObject();
		try {
			// 获得商品spu
			SpuDO spuDO = spuDAO.getItemSpuByKey(keyPropertyId, keyPropertyValue);
			if (spuDO == null) {
				return null;
			}

			List idsList = new ArrayList();
			String values[] = spuDO.getPropertyValuePair().split(";");
			for (int i = 0; i < values.length; i++) {
				String[] attr = values[i].split(":");
				String propertyId = attr[0];
				String propertyValues[] = attr[1].split(",");
				if (propertyValues.length == 0) {
					continue;
				}
				Map t = new HashMap();
				List<String> tls = new ArrayList<String> ();
				for (int j = 0; j < propertyValues.length; j++) {
					BaseValueDO baseValueDO = categoryCacheManager.getBaseValueById(Long.parseLong(propertyValues[j]));
					if (baseValueDO == null) {
						continue;
					}
					tls.add(baseValueDO.getValue());
				}
				if (tls.size() == 0) {
					continue;
				}
				t.put("pid", propertyId);
				t.put("vids", tls);

				CategoryPropertyDO categoryPropertyDO = categoryCacheManager.getItemCategoryPropertyById(Long
						.valueOf(propertyId));
				if (categoryPropertyDO == null) {
					continue;
				}
				if (categoryPropertyDO.getStatus() != 1) {
					continue;
				}
				t.put("name", categoryPropertyDO.getName());

				idsList.add(t);
			}

			m.put("spuId", spuDO.getId());
			m.put("name", spuDO.getName());
			m.put("marketPrice", new Money(spuDO.getMarketPrice()).toString());
			m.put("memo", spuDO.getMemo());
			m.put("idsList", idsList);

		} catch (DaoException e) {
			throw new ManagerException("获得商品SPU", e);
		}
		return m;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CategoryManager#getItemSpuById(java.lang.Long)
	 */
	@Override
	public SpuDO getItemSpuById(Long spuId) throws ManagerException {
		try {
			return spuDAO.getItemSpuById(spuId);
		} catch (DaoException e) {
			throw new ManagerException("根据编号获得商品SPU", e);
		}
	}

	public void setBaseValueDAO(BaseValueDAO baseValueDAO) {
		this.baseValueDAO = baseValueDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public void setCategoryPropertyDAO(CategoryPropertyDAO categoryPropertyDAO) {
		this.categoryPropertyDAO = categoryPropertyDAO;
	}

	public void setCategoryPropertyValueDAO(CategoryPropertyValueDAO categoryPropertyValueDAO) {
		this.categoryPropertyValueDAO = categoryPropertyValueDAO;
	}

	public void setSpuDAO(SpuDAO spuDAO) {
		this.spuDAO = spuDAO;
	}

	@Override
	public List getBaseValueNameByIds(List propertyValueList) throws ManagerException {
		try {
			return baseValueDAO.getBaseValueNameByIds(propertyValueList);
		} catch (DaoException e) {
			throw new ManagerException("根据值id列表获得基本值", e);
		}
	}

	@Override
	public Map<String, String> getBaseValueNameMapByIds(List propertyValueList) throws ManagerException {
		Map<String, String> m = new HashMap<String, String>();
		try {
			List<BaseValueDO> ls = baseValueDAO.getBaseValueNameByIds(propertyValueList);
			for (BaseValueDO baseValueDO : ls) {
				m.put(String.valueOf(baseValueDO.getId()), baseValueDO.getValue());
			}
			return m;
		} catch (DaoException e) {
			throw new ManagerException("根据值id列表获得基本值", e);
		}
	}

	@Override
	public BaseValueDO getBaseValueById(Long id) throws ManagerException {
		try {
			return baseValueDAO.selectItemBaseValueById(id);
		} catch (DaoException e) {
			throw new ManagerException("根据值id获得基本值", e);
		}
	}

	@Override
	public CategoryPropertyDO getItemCategoryPropertyById(long id) throws ManagerException {
		try {
			return categoryPropertyDAO.selectCategoryPropertyByCateProId(id);
		} catch (DaoException e) {
			throw new ManagerException("根据值id获得基本值", e);
		}
	}

	public void setCpValueRelationDAO(CpValueRelationDAO cpValueRelationDAO) {
		this.cpValueRelationDAO = cpValueRelationDAO;
	}

	@Override
	public List<CpValueRelationDO> getItemCpValueRelation(long cpId, long sonCpId) throws ManagerException {
		try {
			return cpValueRelationDAO.getItemCpValueRelation(cpId, sonCpId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<CpValueRelationDO> getItemCpValueRelationByCpId(long cpId) throws ManagerException {
		try {
			return cpValueRelationDAO.getItemCpValueRelationByCpId(cpId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public CpValueRelationDO getItemCpValueRelationByCpIdAndCpvId(long cpId, long cpvId) throws ManagerException {
		try {
			return cpValueRelationDAO.getItemCpValueRelationByCpIdAndCpvId(cpId, cpvId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

}
