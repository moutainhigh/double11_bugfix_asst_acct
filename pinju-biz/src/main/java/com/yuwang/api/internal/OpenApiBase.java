/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.internal;

import java.util.List;
import java.util.Map;

import com.yuwang.api.ApiException;
import com.yuwang.api.ApiRequest;
import com.yuwang.api.ApiResponse;
import com.yuwang.api.common.Constants;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;

/**
 * 平台基础数据接口
 * 
 * @author liyouguo
 * 
 * @since 2011-9-5
 */
public class OpenApiBase extends BaseApi {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3634528092578427588L;

	private CategoryCacheManager categoryCacheManager;

	public OpenApiBase() {
		categoryCacheManager = (CategoryCacheManager) getBean("categoryCacheManager");
	}

	/**
	 * 获取所有类目
	 * 
	 * @param request
	 * @return
	 * @throws ManagerException
	 * @throws ApiException
	 */
	public int getAllCategory(ApiRequest request, ApiResponse apiResponse) {
		Map<String, String> map = request.getTextParams();
		try {
			Long parentId = Long.parseLong(map.get("parentId"));
			List<CategoryDO> categoryList = null;
			if (null != parentId && 0 == parentId) {
				categoryList = categoryCacheManager.getRootCategoryList();
			} else {
				categoryList = categoryCacheManager
						.getItemCategoryListByParentId(parentId);
			}
			if (categoryList.size() > 0) {
				apiResponse.setRespData(categoryList);
				return Constants.SUBMIT_APIMETHOD_INNER_SUCCESS;
			} else {
				log.error("传入参数parentId查询信息为空");
				apiResponse.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				return Constants.SUBMIT_APIMETHOD_NO_DATA;
			}
		} catch (NumberFormatException e) {
			log.error("传入参数出错了，必须传入参数parentId");
			apiResponse.setMsg("传入参数出错了，必须传入参数parentId");
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		} catch (ManagerException e) {
			log.error("查询类目出错", e);
			apiResponse.setMsg("查询类目出错");
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		} catch (Exception e) {
			log.error("获取类目出错", e);
			apiResponse.setMsg("获取类目出错");
			e.printStackTrace();
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		}
	}

	/**
	 * 获取所有类目属性
	 * 
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	public int getAllCategoryProperty(ApiRequest request,
			ApiResponse apiResponse) {
		Map<String, String> map = request.getTextParams();
		try {
			Long categoryId = Long.parseLong(map.get("categoryId"));
			List<CategoryPropertyDO> categoryPropertyList = categoryCacheManager
					.getItemCategoryProperty(categoryId);
			if (categoryPropertyList.size() > 0) {
				apiResponse.setRespData(categoryPropertyList);
				return Constants.SUBMIT_APIMETHOD_INNER_SUCCESS;
			} else {
				log.error("传入参数categoryId查询信息为空");
				apiResponse.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				return Constants.SUBMIT_APIMETHOD_NO_DATA;
			}
		} catch (NumberFormatException e) {
			log.error("传入参数出错了，必须传入参数categoryId");
			apiResponse.setMsg("传入参数出错了，必须传入参数categoryId");
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		} catch (ManagerException e) {
			log.error("查询类目属性出错", e);
			apiResponse.setMsg("查询类目属性出错");
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		} catch (Exception e) {
			log.error("取类目属性时出错", e);
			apiResponse.setMsg("取类目属性时出错");
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		}
	}

	/**
	 * 获取所有类目属性值
	 * 
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	public int getAllCategoryProVal(ApiRequest request, ApiResponse apiResponse) {
		Map<String, String> map = request.getTextParams();
		try {
			Long categoryPropertyId = Long.parseLong(map
					.get("categoryPropertyId"));
			List<CategoryPropertyValueDO> categoryPropertyValueList = categoryCacheManager
					.getItemCategoryPropertyValue(categoryPropertyId);
			if (categoryPropertyValueList == null
					|| categoryPropertyValueList.size() == 0) {
				log.error("传入参数categoryPropertyId查询信息为空");
				apiResponse.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				return Constants.SUBMIT_APIMETHOD_NO_DATA;
			}

			apiResponse.setRespData(categoryPropertyValueList);
			return Constants.SUBMIT_APIMETHOD_INNER_SUCCESS;
		} catch (NumberFormatException e) {
			log.error("传入参数出错了，必须传入参数categoryPropertyId");
			apiResponse.setMsg("传入参数出错了，必须传入参数categoryPropertyId");
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		} catch (ManagerException e) {
			log.error("查询类目属性值出错", e);
			apiResponse.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			apiResponse.setMsg("查询类目属性值出错");
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		} catch (Exception e) {
			log.error("取类目属性值时出错", e);
			apiResponse.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			apiResponse.setMsg("取类目属性值出错");
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		}
	}

	/**
	 * 增加一个心跳的空接口，需要用户授权调用。
	 * 
	 * @param request
	 * @param apiResponse
	 * @return
	 */
	public int heartBeat(ApiRequest request, ApiResponse apiResponse) {
		return Constants.SUBMIT_APIMETHOD_INNER_SUCCESS;
	}
}
