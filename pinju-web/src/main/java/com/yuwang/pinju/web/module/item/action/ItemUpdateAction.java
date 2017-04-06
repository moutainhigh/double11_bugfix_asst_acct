package com.yuwang.pinju.web.module.item.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.images.ImagesCategoryConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.image.manager.ImagesCategoryManager;
import com.yuwang.pinju.core.item.ao.ItemAO;
import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.core.item.dao.ItemPicDAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsTemplateManager;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.shop.manager.ShopShieldManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.HtmlRegexpUtil;
import com.yuwang.pinju.core.util.filter.WordFilterService;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryQuery;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemInput;
import com.yuwang.pinju.domain.item.ItemPicDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * 
 * @author liming
 * 
 */
public class ItemUpdateAction extends ActionSupport {

	/**
	 * 类目显示对象
	 */
	private CategoryQuery categoryQuery;

	private ItemAO itemAO;

	private ItemDetailAO itemDetailAO;

	private ItemPicDAO itemPicDAO;
	/**
	 * 商品输入对象
	 */
	private ItemInput itemInput;

	/**
	 * 商品 查询对象
	 */
	private ItemQuery itemQuery;
	
	/**
	 * 返回路径
	 */
	private String returnUrl;
	
	private ItemManager itemManager;
	
	private ImagesCategoryManager imagesCategoryManager;
	
	private CategoryCacheManager categoryCacheManager;
	
	private MemberAsstLog memberAsstLog;
	
	public MemberAsstLog getMemberAsstLog() {
		return memberAsstLog;
	}

	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}

	public CategoryCacheManager getCategoryCacheManager() {
		return categoryCacheManager;
	}

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	public void setImagesCategoryManager(ImagesCategoryManager imagesCategoryManager) {
		this.imagesCategoryManager = imagesCategoryManager;
	}
	
	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	/**
	 * 店铺 分类 列表
	 */
	private Map<String, ShopCategoryListDO> shopCategoryList;

	public CategoryQuery getCategoryQuery() {
		return categoryQuery;
	}

	public ItemInput getItemInput() {
		return itemInput;
	}

	public ItemQuery getItemQuery() {
		return itemQuery;
	}

	public Map<String, ShopCategoryListDO> getShopCategoryList() {
		return shopCategoryList;
	}

	private ShopCategoryManager shopCategoryManager;

	private LogisticsTemplateManager logisticsTemplateManager;
	
	public void setShopCategoryManager(ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}
	
	public void setLogisticsTemplateManager(LogisticsTemplateManager logisticsTemplateManager) {
		this.logisticsTemplateManager = logisticsTemplateManager;
	}
	
	private ShopShieldManager shopShieldManager;
	
	public void setShopShieldManager(ShopShieldManager shopShieldManager) {
		this.shopShieldManager = shopShieldManager;
	}
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * 获得商品更新界面
	 * 
	 * @return
	 */
	@AssistantPermission(target = "item", action = "edit")
	public String getUpdate() {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
//		ItemDO itemDO = itemDetailAO.getItemDOById(itemInput.getId());
		ItemDO itemDO = null;
		try {
			itemDO = itemManager.getItemDOById(itemInput.getId());
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.error("无效的商品" + e);
		}
		if (itemDO == null) {
			return ERROR;
		}
		if(!itemDO.getSellerId().equals(login.getMasterMemberId())){
			returnUrl = "/detail/"+itemInput.getId()+".htm";
			return "itemForward";
		}
		try {
			List<ItemPicDO> itemPicDOList = itemPicDAO.getItemPicByItemId(itemInput.getId());
			String[] itemPicUrl=new String[4];
			if(itemPicDOList!=null&&itemPicDOList.size()>0){
				for(int i =0;i<itemPicDOList.size();i++){
					itemPicUrl[i] = (itemPicDOList.get(i)).getPicUrl();
				}
				itemInput.setItemPicUrl(itemPicUrl);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			log.error("通过ID获取商品副图详情" + e);
		}
		String[] itemPicUrl=new String[5];
		itemPicUrl[0]="false";
		itemPicUrl[1]="false";
		itemPicUrl[2]="false";
		itemPicUrl[3]="false";
		itemPicUrl[4]="false";
		itemInput.setItemEditPicUrl(itemPicUrl);
		itemInput.setCatetoryId(itemDO.getCatetoryId());
		itemInput.setSellerId(itemDO.getSellerId());
		itemInput.setItemDegree(itemDO.getItemDegree().intValue());
		itemInput.setTitle(itemDO.getTitle());

		Money price = new Money(itemDO.getPrice());
		itemInput.setPrice(price.toString());

		if (itemDO.getCode() != null) {
			itemInput.setCode(itemDO.getCode());
		}

		itemInput.setNumber(String.valueOf(itemDO.getOriStock()));

		itemInput.setProvinces(itemDO.getProvinces());
		itemInput.setCity(itemDO.getCity());

		if (itemDO.getMailCosts() == 0 && itemDO.getDeliveryCosts() == 0 && itemDO.getEmsCosts() == 0) {
			if (itemDO.getFreeTemplateId() != null) {
				itemInput.setFreightType(2);
				itemInput.setFreeTemplates(String.valueOf(itemDO.getFreeTemplateId()));
				itemInput.setBuyFreightType(1);
			} else {
				itemInput.setFreightType(1);
				itemInput.setBuyFreightType(2);
			}
		} else {
			itemInput.setBuyFreightType(2);
			Money costs = new Money();
			itemInput.setFreightType(2);
			itemInput.setMailCosts("0");
			itemInput.setDeliveryCosts("0");
			itemInput.setEmsCosts("0");
			if (itemDO.getMailCosts() > 0) {
				costs.setCent(itemDO.getMailCosts());
				itemInput.setMailCosts(costs.toString());
			}
			if (itemDO.getDeliveryCosts() > 0) {
				costs.setCent(itemDO.getDeliveryCosts());
				itemInput.setDeliveryCosts(costs.toString());
			}
			if (itemDO.getEmsCosts() > 0) {
				costs.setCent(itemDO.getEmsCosts());
				itemInput.setEmsCosts(costs.toString());
			}
		}

		itemInput.setPicUrl(itemDO.getPicUrl());

		// 描述 这里暂时是字段保存
		itemInput.setDescription(itemDO.getDescription());
		if (itemDO.getExpiredDate() != null) {
			itemInput.setExpiryDate(itemDO.getExpiredDate().intValue());
		}

		try {
			shopCategoryList = shopCategoryManager.queryShopCategoryByUser(login.getMemberId());
			if (shopCategoryList.size() < 1) {
				shopCategoryList = null;
			}
		} catch (ManagerException e) {
			log.error("显示商品发布页页面，店铺类目错误" + e);
		}
		
		//店铺类目
		if(shopCategoryList!=null&&shopCategoryList.size()>0){
			if(itemDO.getStoreCategories()!=null&&!itemDO.getStoreCategories().equals("")){
				Iterator it = shopCategoryList.entrySet().iterator();
				String[] sc = itemDO.getStoreCategories().split(",");
				if(sc!=null&&sc.length>0){
					while (it.hasNext()) {
						Map.Entry shopCategory = (Map.Entry) it.next();
						ShopCategoryListDO scDo = (ShopCategoryListDO) shopCategory.getValue();
						List subCategoryList = scDo.getSubCategoryList();
						if(subCategoryList!=null&&!subCategoryList.equals("")&&subCategoryList.size()>0){
							String checkValue = "";
							for(int g =0;g < subCategoryList.size();g++){
								String[] subCategory = (String[]) subCategoryList.get(g);
								for(int j =0;j<sc.length;j++){
									if(subCategory[0].equals(sc[j])){
										if(checkValue!=null&&!checkValue.equals("")){
											checkValue = checkValue+","+subCategory[0];
										}else{
											checkValue = subCategory[0];
										}
									}
								}
							}
							scDo.setCategoryImage(checkValue);
							shopCategory.setValue(scDo);
						}else{
							for(int j =0;j<sc.length;j++){
								if(shopCategory.getKey().equals(sc[j])){
									scDo.setCategoryImage("checked");
									shopCategory.setValue(scDo);
								}
							}
						}
					}
				}
			}
		}
		
		if(itemDO.getFreeTemplateId()!=null){
			try {
				LogisticsTemplateDO ltDo = logisticsTemplateManager.loadLogisticsTemplate(itemDO.getFreeTemplateId());
				if(ltDo.getTemplateName()!=null&&!ltDo.getTemplateName().equals("")){
					itemInput.setFreeTemplatesName(ltDo.getTemplateName());
				}
			} catch (ManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//freeTemplatesName
		
		// 类目获得
		categoryQuery = itemAO.getItemCategoryForUpdate(itemDO);
//		if (categoryQuery == null) {
//			return ERROR;
//		}
		String catTitle = "";
		if (categoryQuery != null) {
			try {
				CategoryDO categoryDO = categoryCacheManager.getCategoryDOById(categoryQuery.getCategoryDO().getId());
				catTitle = categoryDO.getName();
				for(int i =0;i<5;i++){
					if(categoryDO!=null&&!categoryDO.equals("")){
						if(categoryDO.getParentId()>0){
							categoryDO = categoryCacheManager.getCategoryDOById(categoryDO.getParentId());
							catTitle = categoryDO.getName()+">>"+catTitle;
						}else{
							break;
						}
					}else{
						break;
					}
				}
			} catch (ManagerException e) {
				// TODO Auto-generated catch block
				log.error(e);
				return ERROR;
			}
			categoryQuery.setCategoryTitle(catTitle);
		}else{
			categoryQuery = new CategoryQuery();
		}
		return SUCCESS;
	}

	public ItemInput updatePicFile(ItemInput itemInput){
		int picFileLength = 0;
		if(itemInput.getThisPicFile1()!=null){
			picFileLength = picFileLength+1;
		}
		if(itemInput.getThisPicFile2()!=null){
			picFileLength = picFileLength+1;
		}
		if(itemInput.getThisPicFile3()!=null){
			picFileLength = picFileLength+1;
		}
		if(itemInput.getThisPicFile4()!=null){
			picFileLength = picFileLength+1;
		}
		if(itemInput.getThisPicFile5()!=null){
			picFileLength = picFileLength+1;
		}
		File[] picFile = new File[picFileLength];
		String[] picFileFileName = new String[picFileLength];
		String[] picFileContentType = new String[picFileLength];
		Boolean picFileFlag1 = true;
		Boolean picFileFlag2 = true;
		Boolean picFileFlag3 = true;
		Boolean picFileFlag4 = true;
		Boolean picFileFlag5 = true;
		for(int i=0; i<picFile.length; i++){
			if(picFileFlag1){
				if(itemInput.getThisPicFile1()!=null){
					picFile[i] = itemInput.getThisPicFile1();
					picFileFileName[i] = itemInput.getThisPicFile1FileName();
					picFileContentType[i] = itemInput.getThisPicFile1ContentType();
					picFileFlag1 = false;
					continue;
				}
			}
			if(picFileFlag2){
				if(itemInput.getThisPicFile2()!=null){
					picFile[i] = itemInput.getThisPicFile2();
					picFileFileName[i] = itemInput.getThisPicFile2FileName();
					picFileContentType[i] = itemInput.getThisPicFile2ContentType();
					picFileFlag2 = false;
					continue;
				}
			}
			if(picFileFlag3){
				if(itemInput.getThisPicFile3()!=null){
					picFile[i] = itemInput.getThisPicFile3();
					picFileFileName[i] = itemInput.getThisPicFile3FileName();
					picFileContentType[i] = itemInput.getThisPicFile3ContentType();
					picFileFlag3 = false;
					continue;
				}
			}
			if(picFileFlag4){
				if(itemInput.getThisPicFile4()!=null){
					picFile[i] = itemInput.getThisPicFile4();
					picFileFileName[i] = itemInput.getThisPicFile4FileName();
					picFileContentType[i] = itemInput.getThisPicFile4ContentType();
					picFileFlag4 = false;
					continue;
				}
			}
			if(picFileFlag5){
				if(itemInput.getThisPicFile5()!=null){
					picFile[i] = itemInput.getThisPicFile5();
					picFileFileName[i] = itemInput.getThisPicFile5FileName();
					picFileContentType[i] = itemInput.getThisPicFile5ContentType();
					picFileFlag5 = false;
					continue;
				}
			}	
		}
		
		itemInput.setPicFile(picFile);
		itemInput.setPicFileFileName(picFileFileName);
		itemInput.setPicFileContentType(picFileContentType);
		
		return itemInput;
	}
	
	private long getMasterUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		}
		return userId;
	}
	
	/**
	 * 商品更新
	 * 
	 * @return
	 */
	@AssistantPermission(target = "item", action = "edit")
	public String itemUpdate() {
		long sellerId = this.getMasterUserId();
//		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		ActionInvokeResult air = new ActionInvokeResult(itemInput);
		if (!air.validate()) {
			return "validateError";
		}
		
		itemInput = updatePicFile(itemInput);	
		long size = ImagesCategoryConstant.MAXIMAGE_SIZE;
		//判断图片空间大小
		try {
			ImagesCategoryDO icDo = imagesCategoryManager.getImagesCategoryObject(sellerId);
			if(icDo!=null&&!icDo.equals("")){
				size = ImagesCategoryConstant.MAXIMAGE_SIZE - icDo.getUserSize();
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (itemInput.getPicFile() != null) {
			for (File f : itemInput.getPicFile()) {
				// 大小检验
				if (f.length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
					air.addMessage("image_size", Message.getMessage(MessageName.FILE_SIZE_TO_LARGE));
					return "validateError";
				}
				size = size - f.length();
				if(size<0){
					air.addMessage("image_size","图片空间不足！");
					return "validateError";
				}
				// 类型检验
				if (!FileSecurityUtils.isImageValid(f)) {
					air.addMessage("image_invalid", Message.getMessage(MessageName.FILE_TYPE_INVALID));
					return "validateError";
				}
			}
		}

		itemInput.setSellerId(sellerId);
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		itemInput.setNickName(login.getMasterMemberName());

		Boolean falg = shopShieldManager.getShieldInfoByUserId(sellerId);
		if(falg&&itemInput.getReleasedType()==1){
			air.addMessage("image_invalid","店铺屏蔽中暂不能做上架商品的操作");
			return "validateError";
		}
		
		List<SkuDO> skuList = new ArrayList<SkuDO>();
		long totalCapacity = 0;
		Money minPrice = null;
		List<String> errorSKUMsg = new ArrayList<String>();
		if (itemInput.getSkuIds() != null && itemInput.getSkuIds().length > 0) {
			// 获得SKU输入
			log.debug("SKU数量" + itemInput.getSkuIds().length);
			for (String s : itemInput.getSkuIds()) {
				log.debug("获取SKU输入ID:" + s);
				String price = ServletActionContext.getRequest().getParameter("price-" + s);
				price = price.trim();
				String capacity = ServletActionContext.getRequest().getParameter("capacity-" + s);
				capacity = capacity.trim();
				String sellerCode = ServletActionContext.getRequest().getParameter("sellerCode-" + s);
				sellerCode = sellerCode.trim();
				log.debug("获取SKU输入price:" + price);
				log.debug("获取SKU输入capacity:" + capacity);
				log.debug("获取SKU输入sellerCode:" + sellerCode);
				log.debug("解析skuAttrID:" + s);
				// 52:46-50:24-103:38-100:39
				String skusStr[] = s.split("-");
				List<String> attrs = new ArrayList<String>();
				for (String string : skusStr) {
					attrs.add(string);
				}
				SkuDO skuDO = new SkuDO();

				skuDO.setSalePv1(attrs.get(0));
				if (attrs.size() > 1) {
					skuDO.setSalePv2(attrs.get(1));
				}
				if (attrs.size() > 2) {
					skuDO.setSalePv3(attrs.get(2));
				}
				if (attrs.size() > 3) {
					skuDO.setSalePv4(attrs.get(3));
				}
				log.debug("解析结果:" + attrs);
				if (EmptyUtil.isBlank(price)) {
					skuDO.setPrice(new Money(0).getCent());
				} else {
					if (minPrice == null || minPrice.compareTo(new Money(price)) == 1) {
						minPrice = new Money(price);
					}
					skuDO.setPrice(new Money(price).getCent());
				}
				if (EmptyUtil.isBlank(capacity)) {
					skuDO.setOriStock(0l);
					skuDO.setCurrentStock(0l);
				} else {
					totalCapacity += Long.valueOf(capacity);
					skuDO.setOriStock(Long.valueOf(capacity));
					skuDO.setCurrentStock(Long.valueOf(capacity));
				}
				// 商家编码过滤
				if (!EmptyUtil.isBlank(sellerCode)) {
					if(sellerCode.length()>30){
						errorSKUMsg.add("SKU验证失败：输入的"+sellerCode+"长度不符");
					}else{
						skuDO.setSellerCode(HtmlRegexpUtil.replaceTag(sellerCode));
					}
				}
				skuDO.setGmtCreate(DateUtil.current());
				skuDO.setGmtModified(DateUtil.current());
				skuDO.setStatus(1l);
				skuList.add(skuDO);

			}

			if (minPrice == null || new Money(itemInput.getPrice()).compareTo(minPrice) == -1) {
				errorSKUMsg.add("SKU验证失败：输入价格小于最小销售屬性价格");
			}
			if (totalCapacity == 0 || Long.parseLong(itemInput.getNumber()) < totalCapacity) {
				errorSKUMsg.add("SKU验证失败：输入数量小于销售屬性数量之和");
			}
		}
		
		//自定义SKU
		List<CustomProValueDO> customSkuList = new ArrayList<CustomProValueDO>();
		//防止重复
		Map customSkuMap = new HashMap();
		if (itemInput.getSkuCustomIds() != null && itemInput.getSkuCustomIds().length > 0) {
			// 获得SKU输入
			log.debug("自定义SKU数量" + itemInput.getSkuCustomIds().length);
			for (String s : itemInput.getSkuCustomIds()) {
				if (EmptyUtil.isBlank(s)) {
					continue;
				}
				CustomProValueDO customProValueDO = new CustomProValueDO();
				String customName = ServletActionContext.getRequest().getParameter("customName-" + s);
				if(customName!=null){
					customName = customName.trim();
				}
				
				String customFile = ServletActionContext.getRequest().getParameter("customFile-" + s);
				if(customFile!=null){
					customFile = customFile.trim();
				}
				if((customName == null||customName.equals(""))&&( customFile == null||customFile.equals(""))){
//					errorSKUMsg.add("自定义SKU验证失败：输入的名称与图片均为空");
					continue;
				}else{
					if(customName!=null&&customName.length()>15){
						errorSKUMsg.add("自定义SKU验证失败：输入的自定义名称长度不符");
					}
				}
				if(customName!=null&&!customName.equals("")){
					customProValueDO.setValue(customName);
					customProValueDO.setValueType(CustomProValueDO.VALUE_TYPE_COMMON);
				}
				if(customFile!=null&&!customFile.equals("")){
					customProValueDO.setImgUrl(customFile.replace(PinjuConstant.VIEW_IMAGE_SERVER,""));
					customProValueDO.setValueType(CustomProValueDO.VALUE_TYPE_IMG);
				}
				String skusStr[] = s.split(":");
				List<String> attrs = new ArrayList<String>();
				for (String string : skusStr) {
					attrs.add(string);
				}
				customProValueDO.setCategoryPropertyId(Long.parseLong(attrs.get(0)));
				if (attrs.size() > 1) {
					customProValueDO.setCateProValueId(Long.parseLong(attrs.get(1)));
				}
				customProValueDO.setGmtCreate(DateUtil.current());
				customProValueDO.setGmtModified(DateUtil.current());
				customProValueDO.setMemberId(sellerId);
				customProValueDO.setStatus(CustomProValueDO.STATUS_NORMAL);
				String key = customProValueDO.getCategoryPropertyId()+"||"+customProValueDO.getCateProValueId();
				if(customSkuMap.get(key)==null){
					customSkuList.add(customProValueDO);
					customSkuMap.put(key, customProValueDO);
				}
			}
		}
		
		List<String> errorMsg = itemAO.itemUpdate(itemInput, errorSKUMsg, skuList, customSkuList);
		if (errorMsg == null) {
			return ERROR;
		}
		if (errorMsg.size() > 0) {
			for (String string : errorMsg) {
				air.addMessage(string, string);
			}
			return "validateError";
		} else {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("修改商品线程休眠"+e);
			}
			memberAsstLog.log("item", "edit", "编辑商品："+itemInput.getTitle());
			if(itemInput.getReleasedType()!=1){
				return "successStorage";
			}else{
				return SUCCESS;
			}
		}
	}

	public void setCategoryQuery(CategoryQuery categoryQuery) {
		this.categoryQuery = categoryQuery;
	}

	public void setItemAO(ItemAO itemAO) {
		this.itemAO = itemAO;
	}

	public void setItemDetailAO(ItemDetailAO itemDetailAO) {
		this.itemDetailAO = itemDetailAO;
	}
	
	public void setItemPicDAO(ItemPicDAO itemPicDAO) {
		this.itemPicDAO = itemPicDAO;
	}
	
	public void setItemInput(ItemInput itemInput) {
		this.itemInput = itemInput;
	}

	public void setItemQuery(ItemQuery itemQuery) {
		this.itemQuery = itemQuery;
	}

	public void setShopCategoryList(Map<String, ShopCategoryListDO> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}
}
