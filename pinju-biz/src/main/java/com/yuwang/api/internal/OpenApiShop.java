/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuwang.api.ApiException;
import com.yuwang.api.ApiRequest;
import com.yuwang.api.ApiResponse;
import com.yuwang.api.common.Constants;
import com.yuwang.api.domain.OpenShopCategoryDO;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * 平台店铺类数据接口
 * 
 * @author liyouguo
 * 
 * @since 2011-9-2
 */
public class OpenApiShop extends BaseApi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6627442126951519738L;

	private static ShopCategoryManager shopCategoryManager;
	private static ShopShowInfoManager shopShowInfoManager;

	public OpenApiShop() {
		shopCategoryManager = (ShopCategoryManager) getBean("shopCategoryManager");
		shopShowInfoManager = (ShopShowInfoManager) getBean("shopShowInfoManager");
	}

	/**
	 * 插入自定义分类（即将新建一个店铺的所有自定义分类）<br/>
	 * 一级分类格式 0@!@name1,0@!@name2,0@!@name3...<br/>
	 * 二级分类格式
	 * 0@!@name1=0@!@second1,0@!@second2\n0@!@name2=0@!@second3,0@!@second4\n<br/>
	 * 
	 * @param request
	 * @param response
	 * @return errorCode
	 */
	public int addCategory(ApiRequest request, ApiResponse response) {
		try {
			// 取得类别DO
			ShopCategoryDO sc = (ShopCategoryDO) request.createDomain();
			sc.setUserId(request.getMemberId());
			ShopInfoDO si = (ShopInfoDO) shopShowInfoManager
					.queryShopBaseInfoByUser(sc.getUserId(), null);
			if (null == si) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_SHOPINFO_NULL);
				response.setMsg(Constants.SUBMIT_APIMETHOD_SHOPINFO_NULL_MSG);
				return response.getErrorCode();
			}
			sc.setShopId(si.getShopId());
			sc.setGmtCreate(new Date());
			sc.setGmtModified(new Date());
			String tag = "0" + ShopConstants.SHOP_CATEGORY_SPLIT;
			// 填补标记tag:0@!@
			String second = sc.getSecondCategory().replaceAll(";", ";" + tag);
			second = second.replaceAll("=", "=" + tag);
			second = second.replaceAll(",", "," + tag);
			second = second.replaceAll(";", "\r\n");
			sc.setSecondCategory(tag + second);
			sc.setFirstCategory(tag
					+ sc.getFirstCategory().replaceAll(",", "," + tag));
			if (sc.getFirstCategory().getBytes("utf-8").length > 4000) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg("OPENAPI插入自定义分类格式错误，一级分类字节数大于4000。");
				return response.getErrorCode();
			}
			if (sc.getSecondCategory().getBytes("utf-8").length > 4000) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg("OPENAPI插入自定义分类格式错误，二级分类字节数大于4000。");
				return response.getErrorCode();
			}
			// 是否已经存在店铺类目
			ShopCategoryDO temp = shopCategoryManager.queryShopCategory(sc
					.getShopId(), sc.getUserId());
			if (null == temp) {
				shopCategoryManager.insertShopCategroy(sc);
			} else {
				sc.setId(temp.getId());
				shopCategoryManager.updateShopCategroy(sc);
			}
			return response.getErrorCode();
		} catch (ArrayIndexOutOfBoundsException e) {
			log.error("OPENAPI插入自定义分类格式错误，格式请参照文档", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg("OPENAPI插入自定义分类格式错误，格式请参照文档");
			return response.getErrorCode();
		} catch (Exception e) {
			log.error("OPENAPI插入自定义分类出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return response.getErrorCode();
		}
	}

	/**
	 * 插入自定义分类（即将新建一个店铺的所有自定义分类）<br/>
	 * 一级分类格式 0@!@name1,0@!@name2,0@!@name3...<br/>
	 * 二级分类格式
	 * 0@!@name1=0@!@second1,0@!@second2\n0@!@name2=0@!@second3,0@!@second4\n<br/>
	 * 
	 * @param request
	 * @param response
	 * @return errorCode
	 */
	public int saveCategory(ApiRequest request, ApiResponse response) {
		try {
			// 取得类别DO
			ShopCategoryDO sc = (ShopCategoryDO) request.createDomain();
			if(StringUtil.isEmpty(sc.getFirstCategory()) && StringUtil.isEmpty(sc.getSecondCategory())){
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_PARAM_INVALID);
				response.setMsg(Constants.SUBMIT_APIMETHOD_PARAM_INVALID_MSG);
				return response.getErrorCode();
			}
			sc.setUserId(request.getMemberId());
			ShopInfoDO si = (ShopInfoDO) shopShowInfoManager
					.queryShopBaseInfoByUser(sc.getUserId(), null);
			if (null == si) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_SHOPINFO_NULL);
				response.setMsg(Constants.SUBMIT_APIMETHOD_SHOPINFO_NULL_MSG);
				return response.getErrorCode();
			}
			sc.setShopId(si.getShopId());
			sc.setGmtModified(new Date());
			// 是否已经存在店铺类目
			ShopCategoryDO temp = shopCategoryManager.queryShopCategory(sc
					.getShopId(), sc.getUserId());
			if (null == temp) {
				response.setMsg("请调用接口：yuwang.category.add。");
				return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
			}
			
			/***********************************一级分类处理线束***********************************/
			String first = sc.getFirstCategory();
			String tag = ShopConstants.SHOP_CATEGORY_SPLIT;
			String firstCategory = temp.getFirstCategory();
			String secondCategory = temp.getSecondCategory();
			if(!StringUtil.isEmpty(first)) {
				try {
					String[] firstArray = first.split(";");//分隔成多行记录
					for (String str : firstArray) {
						String[] tmp = str.split(",", -1);//分隔一行记录【flag,cateId,cateName,afterCateId】
						if(StringUtil.isEmpty(tmp[0]))
							throw new ApiException();
						int flag = Integer.parseInt(tmp[0]);//操作类型【0：增加，1：修改，2：删除】
						String id = tmp[1] + tag;
						switch(flag) {
						case 0://add
							if(!StringUtil.isEmpty(tmp[2])) {//增加时名称不能为空
								if (firstCategory.contains(tmp[2])) {
									response.setMsg("重复的类目:" + tmp[2]);
									return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
								}
								if (StringUtil.isEmpty(tmp[3]) || firstCategory.indexOf(tmp[3] + tag) == -1)//没有填排序或ID传错的情况，默认将放到最后
									firstCategory += ",0"
											+ ShopConstants.SHOP_CATEGORY_SPLIT
											+ tmp[2];
								else//否则将插入到afterCateId之前
									firstCategory = firstCategory.replaceFirst(
											tmp[3] + tag, 0 + tag + tmp[2]
													+ "," + tmp[3] + tag);
							}else{
								// 增加时名称为空
								throw new ApiException();
							}
							break;
						case 1://modify
							if (!StringUtil.isEmpty(tmp[1])
									&& firstCategory.indexOf(id) != -1) {// 待修改的分类编号不能为空
								String orginal = firstCategory
										.substring(firstCategory.indexOf(id));// 获取原分类id@!@name开头的串
								if (orginal.indexOf(",") != -1)// 如果包含有其它分类
									orginal = orginal.substring(0, orginal
											.indexOf(","));// 获取原分类id@!@name
								if (!StringUtil.isEmpty(tmp[2])) {// 分类名称不为空，则替换原来的
									if (firstCategory.contains(tmp[2])) {
										response.setMsg("重复的类目:" + tmp[2]);
										return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
									}
									firstCategory = firstCategory.replaceFirst(
											orginal, id + tmp[2]);
									// 替换二级分类中的一级分类名称
									secondCategory = secondCategory.replaceFirst(orginal, id + tmp[2]);
									// 为排序准备
									orginal = id + tmp[2];
								}
								if (!StringUtil.isEmpty(tmp[3])
										&& firstCategory.indexOf(tmp[3] + tag) != -1) {// 分类排序不为空时
									firstCategory = firstCategory.replaceFirst(
											orginal, "");
									if ("-1".equals(tmp[3]))
										firstCategory += "," + orginal;
									else
										firstCategory = firstCategory
												.replaceFirst(tmp[3] + tag,
														orginal + "," + tmp[3]
																+ tag);
								}
							}else{
								// 待修改的分类编号为空
								throw new ApiException();
							}
							break;
						case 2://delete
							if (!StringUtil.isEmpty(tmp[1])
									&& firstCategory.indexOf(id) != -1) {// 待删除的分类编号不能为空
								try {
									String sec = secondCategory.split(id, -1)[1];
									sec = sec.split("=", -1)[1];//截取二级分类，从而判断此一级分类下是否包含有相应的二级分类
									if (!(StringUtil.isEmpty(sec) || sec
											.startsWith("\r\n"))) {
										response.setMsg("包含二级分类的一级分类不能删除。");
										return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
									}
								} catch (Exception e) {
									response.setMsg("系统数据有误，请从卖家后台处理。");
									return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
								}
								String orginal = firstCategory
										.substring(firstCategory.indexOf(id));
								if (orginal.indexOf(",") != -1)
									orginal = orginal.substring(0, orginal
											.indexOf(","));// 获取原分类id@!@name
								firstCategory = firstCategory.replaceFirst(
										orginal, "");
							}else{
								// 待删除的分类编号为空
								throw new ApiException();
							}
							break;
						default:
							// operType异常
							throw new ApiException();
						}
						firstCategory = firstCategory.replaceFirst(",,", ",");
						if (firstCategory.startsWith(","))
							firstCategory = firstCategory.substring(1,
									firstCategory.length());
						if (firstCategory.endsWith(","))
							firstCategory = firstCategory.substring(0,
									firstCategory.length() - 1);
					}
				} catch(Exception e) {
					response.setMsg("传参有误【firstCategory】，请参考文档。");
					return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
				}
				if (sc.getFirstCategory().getBytes("utf-8").length > 4000) {
					response.setMsg("OPENAPI插入自定义分类格式错误，一级分类字节数大于4000。");
					return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
				}
			}
			sc.setFirstCategory(firstCategory);
			/***********************************一级分类处理结束***********************************/
			
			/***********************************处理二级分类***********************************/
			String second = sc.getSecondCategory();
			if(!StringUtil.isEmpty(second)) {
				try {
					String[] secondArray = second.split(";");//分隔成多行记录
					for (String str : secondArray) {
						String[] tmp = str.split(",", -1);//分隔一行记录【flag,cateId,cateName,afterCateId】
						if(StringUtil.isEmpty(tmp[0]))
							throw new ApiException();
						int flag = Integer.parseInt(tmp[0]);//操作类型【0：增加，1：修改，2：删除】
						switch(flag) {
						case 0://add
							if(!StringUtil.isEmpty(tmp[1]) && !StringUtil.isEmpty(tmp[2])) {//增加时名称不能为空，所属的一级分类编号不能为空
								int i = 0;
								String id = tmp[1] + tag;//一级分类编号id@!@
								String replaceStr = null;
								StringBuilder sbd = new StringBuilder();
								boolean findFirstCategory = false;
								String[] orginalArray = secondCategory.split("\r\n");//将所有一级分类下的二级分类通过“回车换行”分隔开
								for(String orginal : orginalArray) {
									if(i++ > 0)
										sbd.append("\r\n");
									if(findFirstCategory || orginal.split("=")[0].indexOf(id) == -1) {
										sbd.append(orginal);// 已经替换完成 或者 非该二级分类的父分类
										continue;
									}
									findFirstCategory = true;
									replaceStr = orginal;//找到待增加的某个一级分类下的二级分类
									if (!orginal.endsWith("=") && -1 != replaceStr.split("=")[1].indexOf(tmp[2])) {
										response.setMsg("新增的二级分类" + tmp[2] + "已存在。");
										return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
									}
									if (StringUtil.isEmpty(tmp[3])
											&& replaceStr.indexOf(tmp[3] + tag) != -1) {// 排序为空时，增加到最后
										if(orginal.endsWith("="))//原来没有二级分类
											replaceStr += "0"
													+ ShopConstants.SHOP_CATEGORY_SPLIT
													+ tmp[2];
										else//原来有二级分类
											replaceStr += ",0"
												+ ShopConstants.SHOP_CATEGORY_SPLIT
												+ tmp[2];
									} else
										replaceStr = replaceStr.replaceFirst(
												tmp[3] + tag, 0 + tag + tmp[2]
														+ "," + tmp[3] + tag);
									sbd.append(replaceStr);
								}
								if(!findFirstCategory){
									response.setMsg("传入的一级分类ID有误：" + tmp[1]);
									return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
								}
								secondCategory = sbd.toString();
							}else{
								// 编号为空
								throw new ApiException();
							}
							break;
						case 1://modify
							if (!StringUtil.isEmpty(tmp[1])) {//待修改的分类编号不能为空
								String id = tmp[1] + tag;
								int i = 0;
								String replaceStr = null;
								StringBuilder sbd = new StringBuilder();
								boolean findSecondCategory = false;
								String[] orginalArray = secondCategory.split("\r\n");//将所有一级分类下的二级分类通过“回车换行”分隔开
								for(String secondCate : orginalArray) {
									if(i++ > 0)
										sbd.append("\r\n");
									if(findSecondCategory || secondCate.split("=").length < 2 ||secondCate.split("=")[1].indexOf(id) == -1) {
										sbd.append(secondCate);// 已经替换完成 或者 非该二级分类的父分类
										continue;
									}
									findSecondCategory = true;
									String[] secArray = secondCate.split("=");
									replaceStr = secArray[1];
									String orginal = secArray[1].substring(secArray[1].indexOf(id));
									if (orginal.indexOf(",") != -1)
										orginal = orginal.substring(0, orginal
												.indexOf(","));
									if (orginal.indexOf("\r\n") != -1)
										orginal = orginal.substring(0, orginal
												.indexOf("\r\n"));// 获取原来的分类id@!@name
									if(!StringUtil.isEmpty(tmp[2])) {
										replaceStr = secArray[1].replaceFirst(orginal, id + tmp[2]);
										orginal = id + tmp[2];
									}
									if ("-1".equals(tmp[3])
											|| (!StringUtil.isEmpty(tmp[3]) && secondCategory
													.indexOf(tmp[3] + tag) != -1)) {// 处理二级分类排序
										replaceStr = replaceStr.replaceFirst(
												orginal, "");
										if ("-1".equals(tmp[3]))//排序值传-1时，将此分类放到该二级分类的最后
											replaceStr += "," + orginal;
										else
											replaceStr = replaceStr
													.replaceFirst(tmp[3] + tag, orginal + "," + tmp[3]
															+ tag);
										replaceStr = replaceStr.replaceFirst(
												",,", ",");
										if (replaceStr.endsWith(","))
											replaceStr = replaceStr
													.substring(0, replaceStr
															.length() - 1);
										if (replaceStr.startsWith(","))
											replaceStr = replaceStr
													.substring(1, replaceStr
															.length());
									}
									sbd.append(secArray[0]).append("=").append(replaceStr);
								}
								if(!findSecondCategory){
									response.setMsg("传入的二级分类ID有误：" + tmp[1]);
									return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
								}
								secondCategory = sbd.toString();
							}else{
								// 编号为空
								throw new ApiException();
							}
							break;
						case 2://delete
							if (!StringUtil.isEmpty(tmp[1])) {//待删除的分类编号不能为空
								String id = tmp[1] + tag;
								StringBuilder sbd = new StringBuilder();
								int i = 0;
								String replaceStr = null;
								boolean findSecondCategory = false;
								String[] orginalArray = secondCategory.split("\r\n");//将所有一级分类下的二级分类通过“回车换行”分隔开
								for (String secondCate : orginalArray) {
									if (i++ > 0)
										sbd.append("\r\n");
									if (findSecondCategory || secondCate.split("=").length < 2
											|| secondCate.split("=")[1].indexOf(id) == -1) {
										sbd.append(secondCate);
										continue;
									}
									findSecondCategory = true;
									String[] secArray = secondCate.split("=");
									replaceStr = secArray[1];
									String orginal = replaceStr.substring(replaceStr.indexOf(id));
									if (orginal.indexOf(",") != -1)
										orginal = orginal.substring(0, orginal.indexOf(","));
									if (orginal.indexOf("\r\n") != -1)
										orginal = orginal.substring(0, orginal.indexOf("\r\n"));// 获取原来的分类id@!@name

									replaceStr = replaceStr.replaceFirst(orginal, "");
									replaceStr = replaceStr.replaceFirst(",,", ",");
									if (replaceStr.endsWith(","))
										replaceStr = replaceStr.substring(0, replaceStr.length() - 1);
									sbd.append(secArray[0]).append("=").append(replaceStr);
								}
								if(!findSecondCategory){
									response.setMsg("传入的二级分类ID有误：" + tmp[1]);
									return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
								}
								secondCategory = sbd.toString();
							}else{
								// 编号为空
								throw new ApiException();
							}
							break;
						default:
							// operType异常
							throw new ApiException();
						}
					}
				} catch(Exception e) {
					response.setMsg("传参有误【secondCategory】，请参考文档。");
					return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
				}
				if (sc.getSecondCategory().getBytes("utf-8").length > 4000) {
					response.setMsg("OPENAPI插入自定义分类格式错误，二级分类字节数大于4000。");
					return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
				}
			}
			/***********************************二级分类处理结束***********************************/
			sc.setId(temp.getId());
			sc.setSecondCategory(secondCategory);
			shopCategoryManager.updateShopCategroy(sc);
			
			return response.getErrorCode();
		} catch (ArrayIndexOutOfBoundsException e) {
			log.error("OPENAPI插入自定义分类格式错误，格式请参照文档", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg("OPENAPI插入自定义分类格式错误，格式请参照文档");
			return response.getErrorCode();
		} catch (Exception e) {
			log.error("OPENAPI插入自定义分类出错", e);
			response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return response.getErrorCode();
		}
	}
	
	public int getShopCategories(ApiRequest request, ApiResponse response) {
		try {
			Integer shopId = null;
			Long memberId = request.getMemberId();
			Map<String, ShopCategoryListDO> map = null;
			if (memberId == null) {
				try {
					shopId = new Integer(request.getTextParams().get("shopId"));
				} catch (Exception e) {
					// ignore
				}
				try {
					memberId = new Long(request.getTextParams().get("memberId"));
				} catch (Exception e) {
					// ignore
				}
				if (shopId == null && memberId == null) {
					log.error("调用接口【yuwang.categorys.get】未传入相应参数【userId或shopId】");
					response.setMsg(Constants.SUBMIT_APIMETHOD_PARAM_INVALID_MSG);
					return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
				}
				if (shopId != null)
					map = shopCategoryManager.queryShopCategoryList(shopId);
				else if (memberId != null)
					map = shopCategoryManager.queryShopCategoryByUser(memberId);
			} else
				map = shopCategoryManager.queryShopCategoryByUser(memberId);
			if (map == null) {
				log.error("调用接口【yuwang.categorys.get】未传入相应参数【userId="
						+ (memberId == null ? "" : memberId) + "或shopId="
						+ (shopId == null ? "" : shopId) + "】有误。");
				response.setMsg("传入参数有误。");
				return Constants.SUBMIT_APIMETHOD_PARAM_INVALID;
			}
			if (map.size() == 0) {
				response.setMsg(Constants.SUBMIT_APIMETHOD_NO_DATA_MSG);
				return Constants.SUBMIT_APIMETHOD_NO_DATA;
			}
			int sortId = 1;
			List<OpenShopCategoryDO> list = new ArrayList<OpenShopCategoryDO>();
			for (ShopCategoryListDO listDo : map.values()) {
				OpenShopCategoryDO oscDO = new OpenShopCategoryDO();
				oscDO.setId(listDo.getId());
				oscDO.setCateName(listDo.getCategoryName());
				oscDO.setSortId(sortId++);
				if (listDo.getSubCategoryList().size() > 0) {
					int childSort = 1;
					List<OpenShopCategoryDO> childList = new ArrayList<OpenShopCategoryDO>();
					for (Object subCate : listDo.getSubCategoryList()) {
						String[] subCategory = (String[]) subCate;
						OpenShopCategoryDO childDo = new OpenShopCategoryDO();
						childDo.setId(subCategory[0]);
						childDo.setSortId(childSort++);
						childDo.setCateName(subCategory[1]);
						childList.add(childDo);
					}
					oscDO.setChildCates(childList);
				}
				list.add(oscDO);
			}
			response.setRespData(list);
			return Constants.SUBMIT_APIMETHOD_INNER_SUCCESS;
		} catch (ManagerException e) {
			log.error("OPENAPI获取店铺分类出错", e);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		} catch (Exception e) {
			log.error("OPENAPI获取店铺分类出错", e);
			response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
			return Constants.SUBMIT_APIMETHOD_INNER_ERROR;
		}
	}
}
