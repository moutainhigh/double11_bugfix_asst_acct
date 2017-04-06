/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yuwang.api.ApiException;
import com.yuwang.api.ApiRequest;
import com.yuwang.api.ApiResponse;
import com.yuwang.api.common.Constants;
import com.yuwang.api.domain.OperationItemDO;
import com.yuwang.api.domain.UploadImageDO;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.item.ao.ItemAO;
import com.yuwang.pinju.core.item.ao.ItemListAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemInput;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.ItemQueryEx;

/**
 * 平台商品类数据接口
 * 
 * @author liyouguo
 * 
 * @since 2011-9-2
 */
public class OpenApiItem extends BaseApi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1509060527077270318L;

	/**
	 * 添加多张图片至商品图片库
	 * 
	 * @param request
	 * @param response
	 * @return 商品图片库的URL
	 */
	public int uploadItemImgFile(ApiRequest request, ApiResponse response) {
		try {
			if (request.getUploadParams() == null || request.getUploadParams().size() == 0) {
				response.setMsg("未选择要上传的图片，请至少选择一张图片上传。");
				return Constants.SUBMIT_UPLOAD_PARAM_INVALID;
			}
			Iterator<String> it = request.getUploadParams().keySet().iterator();
			String[] fileNames = new String[request.getUploadParams().keySet().size()];
			File[] files = new File[request.getUploadParams().keySet().size()];
			int index = 0;
			while (it.hasNext()) {
				String key = it.next();
				fileNames[index] = key;
				files[index] = request.getUploadParams().get(key);
				index++;
			}
			String[] urls = null;
			try {
				urls = getItemAO().uploadFileReturnFullPath(files, fileNames, request.getMemberId(),
						request.getNickName());
			} catch (Exception e) {
				log.warn(e);
				try {
					response.setMsg(e.getMessage().replace(
							"com.yuwang.pinju.core.common.ManagerException: StorageFileInfoManagerImpl", ""));
				} catch (Exception e1) {
					response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
				}
				return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
			}
			if (null != urls) {
				List<UploadImageDO> result = new ArrayList<UploadImageDO>();
				for (int i = 0; i < index; i++) {
					UploadImageDO imageDo = new UploadImageDO();
					imageDo.setFileName(fileNames[i]);
					imageDo.setImageUrl(urls[i]);
					result.add(imageDo);
				}
				response.setRespData(result);
			} else {
				log.error("OPENAPI-event=[ItemAOImpl#uploadFileReturnFullPath]upload image file error:return null");
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			}
		} catch (Exception e) {
			log.error("OPENAPI添加多张图片至商品图片库出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
		} finally {
			request.clearTempFile();
		}
		return response.getErrorCode();
	}

	/**
	 * 增加单个商品接口
	 * 
	 * @param request
	 * @param response
	 * @return errorCode
	 */
	public int addItem(ApiRequest request, ApiResponse response) {
		try {
			ItemInput itemInput = (ItemInput) request.createDomain();
			itemInput.setSellerId(request.getMemberId());
			itemInput.setNickName(request.getNickName());
			if (request.getUploadParams() != null) {// 判断是否上传了主图片
				Iterator<String> it = request.getUploadParams().keySet().iterator();
				String[] fileNames = new String[request.getUploadParams().keySet().size()];
				File[] files = new File[request.getUploadParams().keySet().size()];
				int index = 0;
				while (it.hasNext()) {
					String key = it.next();
					fileNames[index] = key;
					files[index] = request.getUploadParams().get(key);
					index++;
				}
				itemInput.setPicFileFileName(fileNames);
				if (0 < fileNames.length) {
					itemInput.setPicFile(files);
				} else {
					itemInput.setPicFile(null);
				}
			}
			// 添加商品默认为3:仓库
			itemInput.setReleasedType(ItemConstant.RELEASED_TYPE_3);
			// 运费类型 卖家承担
			itemInput.setFreightType(ItemConstant.FREIGHT_TYPE_1);
			List<String> list = getItemAO().itemReleasedByOut(itemInput);
			if (null == list) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			} else {
				if (0 < list.size()) {
					StringBuffer sb = new StringBuffer();
					for (String msg : list) {
						sb.append(msg + "。");
					}
					response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
					response.setMsg(sb.toString());
				}
			}
			return response.getErrorCode();
		} catch (ApiException e) {
			log.error("用户上传商品出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return response.getErrorCode();
		} finally {
			logger.fatal("@@@@@@@@@@@@@@@@@@@@@@@@@@@login User:" + request.getNickName()
					+ "; upload item returnCode: " + response.getErrorCode());
			request.clearTempFile();
		}
	}

	/**
	 * 修改商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int updateItem(ApiRequest request, ApiResponse response) {
		try {
			ItemInput itemInput = (ItemInput) request.createDomain();
			itemInput.setSellerId(request.getMemberId());
			itemInput.setNickName(request.getNickName());
			// 设置图片
			if (null == itemInput.getItemEditPicUrl() || 0 == itemInput.getItemEditPicUrl().length) {
				throw new ApiException("参数itemEditPicUrl未填写。");
			}
			Map<Long, String> result = getItemListAO().itemStatusValidatorForAPI(new Long[] { itemInput.getId() },
					"itemUpdateByOut", request.getMemberId());
			if (!"ok".equals(result.get(itemInput.getId()))) {
				throw new ApiException(result.get(itemInput.getId()));
			}
//			ItemDO ItemDO = getCategoryCacheManager().getItemDOById(Long.valueOf(itemInput.getId()));

			if (null != request.getUploadParams()) {
				Iterator<String> it = request.getUploadParams().keySet().iterator();
				for (int i = 0; i < itemInput.getItemEditPicUrl().length; i++) {
					if ("true".equals(itemInput.getItemEditPicUrl()[i])) {
						if (it.hasNext()) {
							String key = it.next();
							File tmp = request.getUploadParams().get(key);
							switch (i + 1) {
							case 1:
								itemInput.setThisPicFile1(tmp);
								itemInput.setThisPicFile1FileName(key);
								break;
							case 2:
								itemInput.setThisPicFile2(tmp);
								itemInput.setThisPicFile2FileName(key);
								break;
							case 3:
								itemInput.setThisPicFile3(tmp);
								itemInput.setThisPicFile3FileName(key);
								break;
							case 4:
								itemInput.setThisPicFile4(tmp);
								itemInput.setThisPicFile4FileName(key);
								break;
							case 5:
								itemInput.setThisPicFile5(tmp);
								itemInput.setThisPicFile5FileName(key);
								break;
							default:
								break;
							}
						} else {
							// 修改了主图但没有上传图片
							if (i == 0) {
								throw new ApiException("请上传主图图片。");
							}
						}
					}
				}
			}
			List<String> list = getItemAO().itemUpdateByOut(itemInput);
			if (null == list) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			} else {
				if (0 < list.size()) {
					StringBuffer sb = new StringBuffer();
					for (String msg : list) {
						sb.append(msg + "。");
					}
					response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
					response.setMsg(sb.toString());
				}
			}
			return response.getErrorCode();
		} catch (ApiException e) {
			log.error("用户修改商品出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(e.getMessage());
			return response.getErrorCode();
		} catch (Exception e) {
			log.error("用户修改商品出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return response.getErrorCode();
		} finally {
			logger.fatal("@@@@@@@@@@@@@@@@@@@@@@@@@@@login User:" + request.getNickName()
					+ "; upload item returnCode: " + response.getErrorCode());
			request.clearTempFile();
		}
	}

	/********
	 * 用户上架商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int userAddShelfItem(ApiRequest request, ApiResponse response) {
		List<OperationItemDO> result = new ArrayList<OperationItemDO>();
		try {
			ItemQuery itemQuery = (ItemQuery) request.createDomain();
			if (null != itemQuery && null != itemQuery.getIds() && 0 < itemQuery.getIds().length) {
				Map<Long, String> status = getItemListAO().itemStatusValidatorForAPI(itemQuery.getIds(),
						"userAddShelfItem", request.getMemberId());
				for (Long id : itemQuery.getIds()) {
					OperationItemDO addShelfitem = new OperationItemDO();
					addShelfitem.setId(id);
					if (id == 0) {
						addShelfitem.setResultMsg("商品参数传入格式错误。");
					} else if (!"ok".equals(status.get(id))) {
						addShelfitem.setResultMsg(status.get(id));
					} else {
						List<String> errorMsgList = getItemListAO().userAddShelfItem(new Long[] { id });
						int index = errorMsgList.size();
						if (index > 0) {
							addShelfitem.setResultMsg(errorMsgList.get(0));
						} else {
							addShelfitem.setResultMsg("上架商品成功");
						}
					}
					result.add(addShelfitem);
				}
				response.setRespData(result);
				return response.getErrorCode();
			} else {
				log.error("OPENAPI用户上架商品插入参数为空");
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg("用户上架商品插入参数为空。");
				return response.getErrorCode();
			}
		} catch (ApiException e) {
			log.error("OPENAPI用户上架商品出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return response.getErrorCode();
		}
	}

	/**************
	 * 用户删除商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int userDelItem(ApiRequest request, ApiResponse response) {
		List<OperationItemDO> resultList = new ArrayList<OperationItemDO>();
		try {
			ItemQuery itemQuery = (ItemQuery) request.createDomain();
			if (null != itemQuery && null != itemQuery.getIds() && 0 < itemQuery.getIds().length) {
				Map<Long, String> status = getItemListAO().itemStatusValidatorForAPI(itemQuery.getIds(), "userDelItem",
						request.getMemberId());
				for (Long id : itemQuery.getIds()) {
					OperationItemDO addShelfitem = new OperationItemDO();
					addShelfitem.setId(id);
					if (id == 0) {
						addShelfitem.setResultMsg("商品参数传入格式错误。");
					} else if (!"ok".equals(status.get(id))) {
						addShelfitem.setResultMsg(status.get(id));
					} else {
						int result = getItemListAO().userDelItem(new Long[] { id });
						if (result == 0) {
							addShelfitem.setResultMsg("删除商品失败");
						} else {
							addShelfitem.setResultMsg("删除商品成功");
						}
					}
					resultList.add(addShelfitem);
				}
				response.setRespData(resultList);
			} else {
				log.error("OPENAPI用户删除商品传入参数为空");
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			}
			return response.getErrorCode();
		} catch (ApiException e) {
			log.error("OPENAPI用户删除商品出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return response.getErrorCode();
		}
	}

	/***********
	 * 用户下架商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int userDelShelfItem(ApiRequest request, ApiResponse response) {
		List<OperationItemDO> resultList = new ArrayList<OperationItemDO>();
		try {
			ItemQuery itemQuery = (ItemQuery) request.createDomain();
			if (null != itemQuery && null != itemQuery.getIds() && 0 < itemQuery.getIds().length) {
				Map<Long, String> status = getItemListAO().itemStatusValidatorForAPI(itemQuery.getIds(),
						"userDelShelfItem", request.getMemberId());
				for (Long id : itemQuery.getIds()) {
					OperationItemDO addShelfitem = new OperationItemDO();
					addShelfitem.setId(id);
					if (id == 0) {
						addShelfitem.setResultMsg("商品参数传入格式错误。");
					} else if (!"ok".equals(status.get(id))) {
						addShelfitem.setResultMsg(status.get(id));
					} else {
						int result = getItemListAO().userDelShelfItem(new Long[] { id });
						if (result == 0) {
							addShelfitem.setResultMsg("用户下架商品失败");
						} else {
							addShelfitem.setResultMsg("用户下架商品成功");
						}
					}
					resultList.add(addShelfitem);
				}
				response.setRespData(resultList);
			} else {
				log.error("OPENAPI用户下架商品传入参数为空");
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			}
			return response.getErrorCode();
		} catch (ApiException e) {
			log.error("OPENAPI用户下架商品出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return response.getErrorCode();
		}
	}

	/**
	 * 商品查询（暂不支持关键字搜索，仅支持通过卖家ID获得商品列表）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int getItemList(ApiRequest request, ApiResponse response) {
		try {
			ItemQuery itemQuery = new ItemQuery();
			itemQuery.setSellerId(request.getMemberId());
			setItemsPerPage(itemQuery, request.getTextParams().get("itemsPerPage"));
			setPage(itemQuery, request.getTextParams().get("page"));
			// 商品类型 0:上架 2:下架
			String type = request.getTextParams().get("type");
			// 默认查询所有上架商品
			if (!StringUtil.isEmpty(type)) {
				itemQuery.setType(Integer.parseInt(type));
			}
			List<ItemDO> result = getItemListAO().getItemListForAPI(itemQuery);
			if (result != null) {
				response.setTotalResults(itemQuery.getItems());
				response.setRespData(result);
			} else {
				response.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_NO_DATA);
			}
		} catch (NumberFormatException nfe) {
			log.error("OpenAPI-getItemList error", nfe);
			response.setMsg("传入参数类型不正确，请传入数字类型参数。");
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		} catch (ApiException e) {
			log.error("OpenAPI-getItemList error", e);
			response.setMsg(e.getMessage());
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		}
		return response.getErrorCode();
	}

	/**
	 * 获取缓存的单个商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public int getItemById(ApiRequest request, ApiResponse response) {
		try {
			String itemId = request.getTextParams().get("itemId");
			if (StringUtil.isEmpty(itemId)) {
				throw new ApiException("传入参数出错，必须传入数字型参数itemId");
			}
			ItemDO result = getCategoryCacheManager().getItemDOById(Long.valueOf(itemId));
			if (result != null) {
				response.setRespData(result);
			} else {
				response.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_NO_DATA);
			}
		} catch (NumberFormatException e) {
			log.error("OPENAPI-获取缓存的单个商品出错", e);
			response.setMsg("传入参数出错，必须传入数字型参数itemId");
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		} catch (ManagerException e) {
			log.error("OPENAPI-获取缓存的单个商品出错", e);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		} catch (ApiException e) {
			log.error("OPENAPI-获取缓存的单个商品出错", e);
			response.setMsg(e.getMessage());
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
		}
		return response.getErrorCode();
	}

//	/**
//	 * 验证是否为该卖家商品及是否超过20个
//	 * 
//	 * @param sellerId
//	 * @param ids
//	 * @return 存在的商品id
//	 * @throws ApiException
//	 */
//	private List<Long> checkIsOwnItem(Long sellerId, Long[] ids) throws ApiException {
//		if (ids.length > Constants.OPEN_API_ITEM_OPERATE_NUM) {
//			throw new ApiException("商品数超过" + Constants.OPEN_API_ITEM_OPERATE_NUM + "个。");
//		}
//		ItemQueryEx iqe = new ItemQueryEx();
//		iqe.setSellerId(sellerId);
//		iqe.setItemIdList(Arrays.asList(ids));
//		iqe.setItemsPerPage(Constants.OPEN_API_ITEM_OPERATE_NUM);
//		iqe.setItems(Constants.OPEN_API_ITEM_OPERATE_NUM);
//		List<Long> rtnList = new ArrayList<Long>();
//		try {
//			List<ItemDO> list = getItemManager().queryItemListEx(iqe);
//			for (ItemDO itemDO : list) {
//				rtnList.add(itemDO.getId());
//			}
//			return rtnList;
//		} catch (ManagerException e) {
//			throw new ApiException("验证商品失败。", e);
//		}
//	}

	public ItemAO getItemAO() {
		return (ItemAO) SpringBeanFactory.getBean("itemAO");
	}

	public ItemListAO getItemListAO() {
		return (ItemListAO) SpringBeanFactory.getBean("itemListAO");
	}

	public CategoryCacheManager getCategoryCacheManager() {
		return (CategoryCacheManager) SpringBeanFactory.getBean("categoryCacheManager");
	}

	public ItemManager getItemManager() {
		return (ItemManager) SpringBeanFactory.getBean("itemManager");
	}
}
