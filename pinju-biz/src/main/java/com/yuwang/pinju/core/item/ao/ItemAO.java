package com.yuwang.pinju.core.item.ao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yuwang.api.ApiException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.CategoryQuery;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemInput;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.SkuDO;

/**
 * @author liming
 */
public interface ItemAO {

	/**
	 * 查询商品列表
	 * 
	 * @param itemQuery
	 * @return
	 */
	public List<ItemDO> getAllItem(ItemQuery itemQuery);

	/**
	 * 获得类目json
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public Map getItemCategory(long id) throws ManagerException;

	/**
	 * 获得商品类目
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public CategoryQuery getItemCategory(String categoryId);

	/**
	 * 获得商品类目 用于更新
	 * 
	 * @param categoryId
	 * @return
	 */
	public CategoryQuery getItemCategoryForUpdate(ItemDO itemDO);

	/**
	 * 
	 * 获得商品类目
	 * 
	 * @param parentId
	 * @return
	 * @throws ManagerException
	 */
	public String getItemCategoryList(long parentId) throws ManagerException;

	/**
	 * 
	 * 获得选中商品类目
	 * 
	 * @param parentId
	 * @return
	 * @throws ManagerException
	 */
	public String getItemCategoryListByPath(String categoryPath) throws ManagerException;
	
	/**
	 * 获得商品SPU
	 */
	public Map getItemSpuByKey(Long keyPropertyId, Long keyPropertyValue);

	/**
	 * 发布商品
	 * 
	 * @param itemItemDO
	 *            用户填写的商品信息
	 * @return
	 * @throws SQLException
	 */
	public List<String> itemReleased(ItemInput itemInput, List<String> errorSKUMsg, List<SkuDO> skuList, List<CustomProValueDO> customSkuList);
	/**
	 * 发布商品
	 * 
	 * @param itemItemDO
	 *            用户填写的商品信息
	 * @return
	 * @throws ApiException 
	 * @throws SQLException
	 */
	public List<String> itemReleasedByOut(ItemInput itemInput) throws ApiException;

	/**
	 * 更新商品
	 * 
	 * @param itemItemDO
	 *            用户填写的商品信息
	 * @return
	 * @throws ApiException 
	 * @throws SQLException
	 */
	public List<String> itemUpdateByOut(ItemInput itemInput) throws ApiException;
	
	/**
	 * 更新商品
	 * 
	 * @param itemInput
	 * @return
	 */
	public List<String> itemUpdate(ItemInput itemInput, List<String> errorSKUMsg, List<SkuDO> skuList, List<CustomProValueDO> customSkuList);

	/**
	 * 新增图片,并返回图片http总路径
	 * @author liuboen
	 * @param imgFile
	 * @param imgFileFileName
	 * @param memberId
	 * @return
	 */
	String[] uploadFileReturnFullPath(File[] imgFile, String[] imgFileFileName,
			long memberId, String nickName) throws Exception;

}
