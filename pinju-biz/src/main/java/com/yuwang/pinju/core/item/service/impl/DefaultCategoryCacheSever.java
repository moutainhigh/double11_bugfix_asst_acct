/**
 * 
 */
package com.yuwang.pinju.core.item.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.ObjectIoHandler;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.CategoryDAO;
import com.yuwang.pinju.core.item.service.CategoryCacheServer;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.ForestDO;

/**  
 * @Project: pinju-biz
 * @Title: DefaultCategoryCacheSever.java
 * @Package com.yuwang.pinju.core.item.service.impl
 * @Description: 缓存服务类
 * @author liuboen liuboen@zba.com
 * @date 2011-6-24 上午11:19:29
 * @version V1.0  
 */

public class DefaultCategoryCacheSever  implements CategoryCacheServer {
	protected  final   Log log =LogFactory.getLog("cache-category");
	private CategoryDAO categoryDAO;
	/**缓存本地文件夹*/
	private String storedir;
	
	/**缓存文件名称前缀*/
	public final static String FOREST_FILE_NAME="category";
	
	private final static long ROOT_PARENT_ID=0l;
	
	/**所有类目*/
	private Map<Long, CategoryDO> fullCategories =new ConcurrentHashMap<Long, CategoryDO>(16);
	
	/**根类目list*/
	private List<CategoryDO> rootCatList = new ArrayList<CategoryDO>();
	
	private ForestDO forestDO=new ForestDO();
		
	private boolean isInit=false;
	public void init(){
		if (!isInit) {
			isInit=loadFullCategory();
		}
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.service.CategoryCacheServer#loadFullCategory()
	 */
	@Override
	public boolean loadFullCategory() {
			//查看当天的文件缓存是否存在
			boolean isExist=loadLocalForestDO();
			if (!isExist) {
					/*loadFullCategoryDB();
					buildCatTreeStructure();
					persistenceForestDO();
					return loadLocalForestDO();*/
					return resetFullCategory();
			}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.service.CategoryCacheServer#loadFullCategoryDB()
	 */
	@Override
	public boolean loadFullCategoryDB() {
		boolean isLoadDB=Boolean.FALSE;
		long startTime=System.currentTimeMillis();
			try {
				List <CategoryDO>list=categoryDAO.selectFullItemCategory(1l);
				buildCategories(list);
				isLoadDB=Boolean.TRUE;
			} catch (DaoException e) {
				log.error("Event=[DefaultCategoryCacheSever#loadFullCategoryDB]can not get full category by DB", e);
			}
		if (isLoadDB) {
			forestDO.setFullCategories(fullCategories);
			forestDO.setRootCategories(rootCatList);
			forestDO.setLastIncrUpdate(new Date());
		}
		long endTime=System.currentTimeMillis()-startTime;
		if(log.isInfoEnabled()){
			log.info("event=[DefaultCategoryCacheSever#loadFullCategoryDB], category load success. consume time:["+endTime+"], load category count=["+forestDO.getFullCategories().size()+"]");
		}
		return isLoadDB;
	}
	
	/**
	 * 构建类目的map存储方式
	 */
	private void buildCategories(List<CategoryDO> catList) {
		Map<Long,CategoryDO> rootCats = new HashMap<Long, CategoryDO>();
		destroy();
		for (CategoryDO categoryDO : catList) {

			fullCategories.put(categoryDO.getId(), categoryDO);

			Long parentId = categoryDO.getParentId();

			//如果是根类目的话，就添加到根类目列表中
			if (parentId.longValue()==ROOT_PARENT_ID) {
				rootCats.put(categoryDO.getId(),categoryDO);
			}
		}

		rootCatList.addAll(toList(rootCats.values()));
	}
	/**
	 * 构建类目的map存储方式(增量)
	 */
	private void buildModifyCategories(List<CategoryDO> catList) {
		Map<Long,CategoryDO> rootCats = new HashMap<Long, CategoryDO>();
		for (CategoryDO categoryDO : rootCatList) {
			rootCats.put(categoryDO.getId(), categoryDO);
		}
		
		Map<Long,CategoryDO> fullCateMapTemp=new ConcurrentHashMap<Long, CategoryDO>(16);
		for (Entry<Long,CategoryDO> entry : fullCategories.entrySet()) {
			fullCateMapTemp.put(entry.getKey(),entry.getValue().simpleClone());
		}
		
		for (CategoryDO categoryDO : catList) {
			Long parentId = categoryDO.getParentId();
			if (categoryDO.getStatus()==1l) {
				fullCateMapTemp.put(categoryDO.getId(), categoryDO);
				if (parentId.longValue()==ROOT_PARENT_ID) {
					rootCats.put(categoryDO.getId(), categoryDO);
				}
			}else {
				fullCateMapTemp.remove(categoryDO.getId());
				if (parentId.longValue()==ROOT_PARENT_ID) {
					rootCats.remove(categoryDO.getId());
				}
			}
		}
		
		//重新构建类目数
		for (Map.Entry<Long, CategoryDO> catMap  : fullCateMapTemp.entrySet()) {
			CategoryDO childCatDO = catMap.getValue();
			Long parentId = childCatDO.getParentId();
			CategoryDO parentCatDO = fullCateMapTemp.get(parentId);
			if (parentCatDO != null) {
				parentCatDO.addChildCate(childCatDO);
				childCatDO.setParentCategoryDO(parentCatDO);
			}
		}
		destroy();
		fullCategories.putAll(fullCateMapTemp);
		rootCatList.addAll(toList(rootCats.values()));
	}
	
	private List<CategoryDO> toList(Collection<CategoryDO> categoryDOs) {
		List<CategoryDO> catList = new ArrayList<CategoryDO>();
		for (Iterator iterator = categoryDOs.iterator(); iterator.hasNext();) {
			CategoryDO categoryDO = (CategoryDO) iterator.next();
			catList.add(categoryDO);
		}
		return catList;
	}
	
	/**
	 *加载本地ForestDO
	 * @return
	 */
	private boolean loadLocalForestDO() {
		log.info("event=[DefaultCategoryCacheSever#loadLocalForestDO] start load local ForestDO!");

		String filePath = generateFilePath();
		ForestDO forestDO = (ForestDO) ObjectIoHandler.inputObject(filePath);

		if(forestDO == null) {
			log.info("event=[DefaultCategoryCacheSever#loadLocalForestDO] start load local ForestDO fail, " +
					"file not exist, filePath=["+filePath+"]");
			return false;
		}

		destroy();

		if (forestDO!=null) {
			this.forestDO = forestDO;
		}
		if (forestDO.getRootCategories()!=null) {
			this.rootCatList = forestDO.getRootCategories();
		}
		if (forestDO.getFullCategories()!=null) {
			this.fullCategories = forestDO.getFullCategories();
		}
		//增量更新该段时间
		boolean isSucess=incrLoadCategory();
		if (isSucess) {
			log.info("event=[DefaultCategoryCacheSever#loadLocalForestDO] load local incr ForestDO finish!");
		}else {
			log.error("event=[DefaultCategoryCacheSever#loadLocalForestDO] load local incr ForestDO error!");
		}
		log.info("event=[DefaultCategoryCacheSever#loadLocalForestDO] load local ForestDO finish!");
		return true;
	}

	/**
	 * 持久化ForestDO
	 */
	private void persistenceForestDO() {
		log.info("event=[DefaultCategoryCacheSever#persistenceForestDO] start persistence ForestDO.");
		File file = new File(storedir);
		if(!file.exists()) {
			if (log.isInfoEnabled()) {
			log.info("event=[DefaultCategoryCacheSever#persistenceForestDO] create dir=["+storedir+"]");
			}
			file.mkdirs();
		}
		File oldCategoryFiles[]=file.listFiles();
		for (File oldFile : oldCategoryFiles) {
			if (log.isInfoEnabled()) {
				log.info("event=[DefaultCategoryCacheSever#persistenceForestDO] delete old dir = "+oldFile.getName());
			}
			oldFile.delete();
		}
		String filePath = generateFilePath();
		ObjectIoHandler.outputObject(forestDO, filePath);
		log.info("event=[DefaultCategoryCacheSever#persistenceForestDO] end persistence ForestDO.");
	}


	/**
	 *生成文件路径
	 * @return
	 */
	private String generateFilePath() {
		String dateStr = DateUtil.formatDate(new Date());
		StringBuilder sb = new StringBuilder();
		sb.append(storedir);
		sb.append(FOREST_FILE_NAME).append(".");
		sb.append(dateStr);
		return sb.toString();
	}
	
	/**销毁缓存*/
	public void destroy(){
		log.info("event=[DefaultCategoryCacheSever#destroy], begin destroy old cache ..");

		destroy(rootCatList);
		destroy(fullCategories);
		log.info("event=[DefaultCategoryCacheSever#destroy], all category cache destroy success.");
	}

	private void destroy(Object obj){
		if (obj instanceof List) {
			List list = (List) obj;

			if (!list.isEmpty()) {
				list.clear();
			}
		} else if (obj instanceof Map) {
			Map map = (Map) obj;

			if (!map.isEmpty()) {
				map.clear();
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.service.CategoryCacheServer#loadTimeFullCategory()
	 */
	@Override
	public boolean resetFullCategory() {
		boolean isLoadDB=loadFullCategoryDB();
		if (isLoadDB) {
			buildCatTreeStructure();
			persistenceForestDO();
			return loadLocalForestDO();
		}else {
			return Boolean.FALSE;
		}
		
	}
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.service.CategoryCacheServer#incrLoadCategory()
	 */
	@Override
	public boolean incrLoadCategory() {
		Date lastTime=forestDO.getLastIncrUpdate();
		String lastTempDateStr="";
		if (lastTime==null) {
			lastTempDateStr=DateUtil.formatDate(new Date());
		}else {
			lastTempDateStr=DateUtil.formatDatetime(lastTime);
		}
		Date dateNow=new Date();
		//通过上次更新时间获取这个时间段有修改
		try {
			List <CategoryDO>list=categoryDAO.selectModifyItemCategory(lastTempDateStr,DateUtil.formatDatetime(dateNow));
			if (list!=null&&list.size()>0) {
				buildModifyCategories(list);
				persistenceForestDO();
				forestDO.setFullCategories(fullCategories);
				forestDO.setRootCategories(rootCatList);
				forestDO.setLastIncrUpdate(dateNow);
			}
			return true;
		} catch (DaoException e) {
			log.error("Event=[DefaultCategoryCacheSever#incrLoadCategory]can not load incr category by DB", e);
			return false;
		}
	}
	
	/**
	 *构建类目树结构
	 */
	private void buildCatTreeStructure() {
		if (fullCategories.isEmpty()) {
			return;
		}
		for (Map.Entry<Long, CategoryDO> catMap  : fullCategories.entrySet()) {
			CategoryDO childCatDO = catMap.getValue();
			Long parentId = childCatDO.getParentId();
			CategoryDO parentCatDO = fullCategories.get(parentId);
			if (parentCatDO != null) {
				parentCatDO.addChildCate(childCatDO);
				childCatDO.setParentCategoryDO(parentCatDO);
			}
		}
	}
	/**
	 * @return the forestDO
	 */
	@Override
	public ForestDO getForestDO() {
		if (forestDO==null) {
			if (!isInit) {
				this.loadFullCategory();
			}
		}
		return forestDO;
	}


	/**
	 * @param storedir the storedir to set
	 */
	public void setStoredir(String storedir) {
		this.storedir = storedir;
	}
	
	/**
	 * @param categoryDAO the categoryDAO to set
	 */
	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	/**
	 * @return the fullCategories
	 */
	public Map<Long, CategoryDO> getFullCategories() {
		return fullCategories;
	}

	/**
	 * @return the rootCatList
	 */
	public List<CategoryDO> getRootCatList() {
		return rootCatList;
	}

}
