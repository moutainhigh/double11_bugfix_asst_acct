package com.yuwang.pinju.web.module.item.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.images.ImagesCategoryConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
import com.yuwang.pinju.core.image.manager.ImagesCategoryManager;
import com.yuwang.pinju.core.item.ao.ItemAO;
import com.yuwang.pinju.core.item.dao.ItemPicDAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsTemplateManager;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.shop.manager.ShopShieldManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.HtmlRegexpUtil;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryQuery;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemInput;
import com.yuwang.pinju.domain.item.ItemPicDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * 
 * 发布商品action
 * 
 * @author liming
 * 
 */
public class ItemReleasedAction extends ActionSupport {

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private String categoryId;

	private String categoryPath;
	
	private long categoryParentId = 0;

	private CategoryQuery categoryQuery;

	private String categoryTitle;

	private long cid = 0;

	private String errorMessage;

	private ItemAO itemAO;

	private ItemInput itemInput;

	private ItemDO itemItemDO;

	private Map<String, ShopCategoryListDO> shopCategoryList;

	private ShopCategoryManager shopCategoryManager;
	
	private ShopShowInfoManager shopShowInfoManager;

	private ShopShieldManager shopShieldManager;
	
	private ImagesCategoryManager imagesCategoryManager;
	
	private CategoryCacheManager categoryCacheManager;
	
	private ItemManager itemManager;
	
	private LogisticsTemplateManager logisticsTemplateManager;
	
	private ItemPicDAO itemPicDAO;
	/**
	 * 返回路径
	 */
	private String returnUrl;
	
	private MemberAsstLog memberAsstLog;
	
	public MemberAsstLog getMemberAsstLog() {
		return memberAsstLog;
	}

	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public CategoryCacheManager getCategoryCacheManager() {
		return categoryCacheManager;
	}

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public void setImagesCategoryManager(ImagesCategoryManager imagesCategoryManager) {
		this.imagesCategoryManager = imagesCategoryManager;
	}
	
	public void setShopShieldManager(ShopShieldManager shopShieldManager) {
		this.shopShieldManager = shopShieldManager;
	}
	
	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
	
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setLogisticsTemplateManager(LogisticsTemplateManager logisticsTemplateManager) {
		this.logisticsTemplateManager = logisticsTemplateManager;
	}
	
	public void setItemPicDAO(ItemPicDAO itemPicDAO) {
		this.itemPicDAO = itemPicDAO;
	}
	
	private long vid = 0;

	public String getCategoryId() {
		return categoryId;
	}

	public long getCategoryParentId() {
		return categoryParentId;
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	
	public CategoryQuery getCategoryQuery() {
		return categoryQuery;
	}

	public String getCategoryTitle() {
		categoryTitle = categoryTitle.replaceAll(",", "&nbsp;&gt;&nbsp");
		return categoryTitle;
	}

	public long getCid() {
		return cid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 获得类目
	 * 
	 * @return
	 */
	public String getItemCategory() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String s = itemAO.getItemCategoryList(categoryParentId);
			out.print(s);
		} catch (ManagerException e) {
			log.error("获得类目", e);
		} catch (IOException e) {
			log.error("获得类目", e);
		}
		return null;
	}
	
	/**
	 * 获得选中类目
	 * 
	 * @return
	 */
	public String getItemCategoryByPath() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String s = itemAO.getItemCategoryListByPath(categoryPath);
			out.print(s);
		} catch (ManagerException e) {
			log.error("获得选中类目", e);
		} catch (IOException e) {
			log.error("获得选中类目", e);
		}
		return null;
	}

	public ItemInput getItemInput() {
		return itemInput;
	}

	public ItemDO getItemItemDO() {
		return itemItemDO;
	}

	public Map<String, ShopCategoryListDO> getShopCategoryList() {
		return shopCategoryList;
	}

	/**
	 * 获得类目
	 * 
	 * @return
	 */
	public String getSpu() {

		try {

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			log.debug("cid:" + cid);
			log.debug("vid:" + vid);

			Map m = itemAO.getItemSpuByKey(Long.valueOf(cid), Long.valueOf(vid));

			if (m == null) {
				return null;
			}
			log.info(m.toString());
			out.print(m.toString());

		} catch (Exception e) {
			log.error("获得类目", e);
		}
		return null;
	}

	public long getVid() {
		return vid;
	}

	/**
	 * 图片大小验证
	 */
	public String validateUploadImage() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String imagButtonType = (String) ServletActionContext.getRequest().getParameter("imagButtonType");
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		try {
			//判断图片空间大小
			ImagesCategoryDO icDo = imagesCategoryManager.getImagesCategoryObject(login.getMemberId());
			long size = ImagesCategoryConstant.MAXIMAGE_SIZE;
			if(icDo!=null){
				size = ImagesCategoryConstant.MAXIMAGE_SIZE - icDo.getUserSize();
			}
			
			out = response.getWriter();
			int imagFlag = 0;
			JSONObject m = new JSONObject();
			if(imagButtonType!=null&&!imagButtonType.equals("")){
				File f = null;
				if(imagButtonType.equals("1")){
					if(itemInput==null || itemInput.getThisPicFile1()==null){
						imagFlag = 1;
					}else{
						f = itemInput.getThisPicFile1();
					}
				}else if(imagButtonType.equals("2")){
					if(itemInput==null || itemInput.getThisPicFile2()==null){
						imagFlag = 1;
					}else{
						f = itemInput.getThisPicFile2();
					}
				}else if(imagButtonType.equals("3")){
					if(itemInput==null || itemInput.getThisPicFile3()==null){
						imagFlag = 1;
					}else{
						f = itemInput.getThisPicFile3();
					}
				}else if(imagButtonType.equals("4")){
					if(itemInput==null || itemInput.getThisPicFile4()==null){
						imagFlag = 1;
					}else{
						f = itemInput.getThisPicFile4();
					}
				}else if(imagButtonType.equals("5")){
					if(itemInput==null || itemInput.getThisPicFile5()==null){
						imagFlag = 1;
					}else{
						f = itemInput.getThisPicFile5();
					}
				}
				if(f!=null){
					// 大小检验
					if (f.length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
						imagFlag = 3;
					}
					if (f.length() > size) {
						imagFlag = 4;
					}
					// 类型检验
					if (!FileSecurityUtils.isImageValid(f)) {
						imagFlag = 2;
					}
				}
			}else{
				if (itemInput==null || itemInput.getPicFile() == null || itemInput.getPicFile().length == 0) {
					imagFlag = 1;
				} else {
					for (File f : itemInput.getPicFile()) {
						// 大小检验
						if (f.length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
							imagFlag = 3;
						}
						if (f.length() > size) {
							imagFlag = 4;
						}
						// 类型检验
						if (!FileSecurityUtils.isImageValid(f)) {
							imagFlag = 2;
						}
					}
				}
			}
			m.put("imagFlag", imagFlag);
			out.print(m.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	
	/**
	 * 发布商品
	 * 
	 * @return
	 */
	@AssistantPermission(target = "item", action = "pub")
	public String itemReleased() {
		long sellerId = this.getMasterUserId();
		ActionInvokeResult air = new ActionInvokeResult(itemInput);
		if (!air.validate()) {
			return "validateError";
		}
		
		itemInput = updatePicFile(itemInput);
		long size = ImagesCategoryConstant.MAXIMAGE_SIZE;
		//判断图片空间大小
		try {
			ImagesCategoryDO icDo = imagesCategoryManager.getImagesCategoryObject(sellerId);
			if(icDo!=null){
				size = ImagesCategoryConstant.MAXIMAGE_SIZE - icDo.getUserSize();
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (itemInput.getPicFile() == null || itemInput.getPicFile().length == 0) {
			if(itemInput.getItemEditPicUrl()!=null&&itemInput.getItemEditPicUrl().length>0){
				Boolean flag = true;
				for(int i=0;i<itemInput.getItemEditPicUrl().length;i++){
					if(!((itemInput.getItemEditPicUrl())[i]).equals("false")&&!(itemInput.getItemEditPicUrl())[i].equals("true")){
						flag = false;
					}
				}
				if(flag){
					air.addMessage("image_null","缺少商品图片！");
					return "validateError";
				}
			}else{
				air.addMessage("image_null","缺少商品图片！");
				return "validateError";
			}
		} else {
			for (File f : itemInput.getPicFile()) {
				// 大小检验
				if (f.length() / ItemInput.FILE_SIZE_K > ItemInput.MAX_IMAGE_SIZE) {
					air.addMessage("image_size",Message.getMessage(MessageName.FILE_SIZE_TO_LARGE));
					return "validateError";
				}
				size = size - f.length();
				if(size<0){
					air.addMessage("image_size","图片空间不足！");
					return "validateError";
				}
				// 类型检验
				if (!FileSecurityUtils.isImageValid(f)) {
					air.addMessage("image_invalid",Message.getMessage(MessageName.FILE_TYPE_INVALID));
					return "validateError";
				}
			}
		}

		itemInput.setSellerId(sellerId);
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		itemInput.setNickName(login.getMasterMemberName());
		
		if(itemInput.getDescription()!=null&&!itemInput.getDescription().equals("")){
			itemInput.setDescription(itemInput.getDescription().replaceAll("\r\n", "\n"));
			if(itemInput.getDescription().length()>40000){
				air.addMessage("image_invalid",Message.getMessage(MessageName.OPERATE_SUBMIT_PARAMETER_ERROR));
				return "validateError";
			}
		}

		Boolean falg = shopShieldManager.getShieldInfoByUserId(sellerId);
		if(falg&&itemInput.getReleasedType()==1){
			air.addMessage("image_invalid","店铺屏蔽中暂不能做上架商品的操作");
			return "validateError";
		}
		// 验证SKU
		List<SkuDO> skuList = new ArrayList<SkuDO>();
		long totalCapacity = 0;
		Money minPrice = null;
		List<String> errorSKUMsg = new ArrayList<String>();
		if (itemInput.getSkuIds() != null && itemInput.getSkuIds().length > 0) {
			// 获得SKU输入
			log.debug("SKU数量" + itemInput.getSkuIds().length);
			for (String s : itemInput.getSkuIds()) {
				if (EmptyUtil.isBlank(s)) {
					continue;
				}
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
					if(NumberUtil.isDouble(price)){
						if (minPrice == null || minPrice.compareTo(new Money(price)) == 1) {
							minPrice = new Money(price);
						}
						skuDO.setPrice(new Money(price).getCent());
					}else{
						errorSKUMsg.add("SKU验证失败：输入的"+price+"价格无效");
					}
				}
				if (EmptyUtil.isBlank(capacity)) {
					skuDO.setOriStock(0l);
					skuDO.setCurrentStock(0l);
				} else {
					if(NumberUtil.isDouble(capacity)){
						totalCapacity += Long.valueOf(capacity);
						skuDO.setOriStock(Long.valueOf(capacity));
						skuDO.setCurrentStock(Long.valueOf(capacity));
					}else{
						errorSKUMsg.add("SKU验证失败：输入的"+capacity+"数量无效");
					}
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

			log.debug("SKU对象数量" + skuList.size());
			log.debug("SKU数量和" + totalCapacity);
			log.debug("SKU最小价格" + minPrice);

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
		
		List<String> errorMsg = itemAO.itemReleased(itemInput, errorSKUMsg, skuList, customSkuList);
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
				log.error("发布商品线程休眠"+e);
			}
			memberAsstLog.log("item", "pub", "发布商品："+itemInput.getTitle());
			if(itemInput.getReleasedType()!=1){
				return "successStorage";
			}else{
				return SUCCESS;
			}
		}
	}

	//敏感词验证
	public String validateSensitiveWord() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			
			Boolean isBadTitleFlag = WordFilterFacade.scan(itemInput.getTitle(),SensitiveWordConstants.SENSITIVE_WORD_TYPE_GOODS);
			Boolean isBadUrlFlag = WordFilterFacade.scan(itemInput.getDescription(),SensitiveWordConstants.SENSITIVE_WORD_TYPE_GOODS, true);
			
			JSONObject m = new JSONObject();
			m.put("isBadTitleFlag", isBadTitleFlag);
			m.put("isBadUrlFlag", isBadUrlFlag);
			
			out.print(m.toString());
			
		} catch (Exception e) {
			log.error("敏感词验证", e);
		}
		return null;
	}
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	
	public void setCategoryParentId(long categoryParentId) {
		this.categoryParentId = categoryParentId;
	}

	public void setCategoryQuery(CategoryQuery categoryQuery) {
		this.categoryQuery = categoryQuery;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setItemAO(ItemAO itemAO) {
		this.itemAO = itemAO;
	}

	public void setItemInput(ItemInput itemInput) {
		this.itemInput = itemInput;
	}

	public void setItemItemDO(ItemDO itemItemDO) {
		this.itemItemDO = itemItemDO;
	}

	public void setShopCategoryList(Map<String, ShopCategoryListDO> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

	public void setShopCategoryManager(ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}

	public void setVid(long vid) {
		this.vid = vid;
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
	 * 显示类目页面
	 * 
	 * @return
	 */
	@AssistantPermission(target = "item", action = "pub")
	public String showCategoryList() {
		long sellerId = this.getMasterUserId();
		//判断用户是否开店
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(sellerId, null);
//			ActionInvokeResult air = new ActionInvokeResult(itemInput);
//			if (shopInfoDO != null
//					&& shopInfoDO.getShopId() != null 
//					&& shopInfoDO.getApproveStatus() != null 
//					&& (shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_YES || shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_HEGUI)) {
////				air.addMessage("image_invalid","用户已经开店，请先完成开店流程");
////				return "validateError";
//			}else{
//				air.addMessage("image_invalid","用户未开店，请先完成开店流程");
//				return "validateError";
//			}
			if(shopInfoDO==null||shopInfoDO.getShopId()==null){
				return "NOT_OPEN";
			}else{
				if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()==ShopConstant.APPROVE_STATUS_HEGUI){
					return "IS_CLOSE";	
				}else if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()!=ShopConstant.APPROVE_STATUS_YES){
					return "NOT_EXIST";
				}
			}
		} catch (ManagerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "success";
	}

	/**
	 * 显示发布商品页面
	 * 
	 * @return
	 */
	@AssistantPermission(target = "item", action = "pub")
	public String showItemReleased() {
		long sellerId = this.getMasterUserId();
		
		if (EmptyUtil.isBlank(sellerId)) {
			ServletUtil.loginReturnBaseUrl("/itemReleased/categoryList.htm");
			return "nickname";
		}
		
		if (EmptyUtil.isBlank(categoryId)) {
			return "categoryList";
		}
		
		if (itemInput != null && itemInput.getId()>0){
//			ItemDO itemDO = itemDetailAO.getItemDOById(itemInput.getId());
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
			if(itemDO.getSellerId()!=sellerId){
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
			itemDO.setCatetoryId(Long.valueOf(categoryId));
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
				shopCategoryList = shopCategoryManager.queryShopCategoryByUser(sellerId);
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
//			if (categoryQuery == null) {
//				return ERROR;
//			}
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
			return "successUpdate";
		}
		
		if (itemInput != null && itemInput.getCatetoryId()>0) {
			categoryId = String.valueOf(itemInput.getCatetoryId());
		} else {
			itemInput = new ItemInput();
		}

		itemInput.setCategoryPath(categoryPath);
		if(categoryPath!=null&&!categoryPath.equals("")){
			String catTitle = "";
			String[] strArray = categoryPath.split(":");
			try {
				for(int j = 0; j < strArray.length; j++) {
					CategoryDO categoryDO = categoryCacheManager.getCategoryDOById(Long.parseLong(strArray[j]));
					if(categoryDO.getName()!=null&&!categoryDO.getName().equals("")){
						if(catTitle==""){
							catTitle = categoryDO.getName();
						}else{
							catTitle = catTitle+">>"+categoryDO.getName();
						}	
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(catTitle!=null){
				setCategoryTitle(catTitle);
			}
		}

		itemInput.setCatetoryId(Long.valueOf(categoryId));
		categoryQuery = itemAO.getItemCategory(categoryId);

		//判断用户是否开店
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(sellerId, null);
//			ActionInvokeResult air = new ActionInvokeResult(itemInput);
//			if (shopInfoDO != null && shopInfoDO.getShopId() != null
//					&& shopInfoDO.getApproveStatus() != null 
//					&& (shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_YES || shopInfoDO.getApproveStatus().intValue()  == ShopConstant.APPROVE_STATUS_HEGUI)) {
////				air.addMessage("image_invalid","用户已经开店，请先完成开店流程");
////				return "validateError";
//			}else{
//				air.addMessage("image_invalid","用户未开店，请先完成开店流程");
//				return "validateError";
//			}
			if(shopInfoDO==null||shopInfoDO.getShopId()==null){
				return "NOT_OPEN";
			}else{
				if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()==ShopConstant.APPROVE_STATUS_HEGUI){
					return "IS_CLOSE";	
				}else if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()!=ShopConstant.APPROVE_STATUS_YES){
					return "NOT_EXIST";
				}
			}
		} catch (ManagerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			shopCategoryList = shopCategoryManager.queryShopCategoryByUser(sellerId);
			if (shopCategoryList.size() < 1) {
				shopCategoryList = null;
			}
		} catch (ManagerException e) {
			log.error("显示商品发布页页面，店铺类目错误" + e);
			return "categoryList";
		}

		if (categoryQuery == null) {
			return "categoryList";
		}
		return "success";
	}

}
