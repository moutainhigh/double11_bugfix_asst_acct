package com.yuwang.pinju.web.module.images.action;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yuwang.pinju.Constant.ImagesConstants;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.image.ao.ImagesCategoryAO;
import com.yuwang.pinju.core.image.ao.StorageFileInfoAO;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.domain.images.StorageFileInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;

public class StorageFileInfoAction implements PinjuAction {
	

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/********图片信息操作AO******/
	private StorageFileInfoAO storageFileInfoAO;
	
	/********图片分类操作AO******/
	private ImagesCategoryAO imagesCategoryAO;
	
	private ShopShowInfoAO shopShowInfoAO;
	
	/********图片分类实体类******/
	private ImagesCategoryDO imagesCategory;
	
	protected String errorMessage="错误";


	public ImagesCategoryDO getImagesCategory() {
		return imagesCategory;
	}


	public void setImagesCategory(ImagesCategoryDO imagesCategory) {
		this.imagesCategory = imagesCategory;
	}


	public ImagesCategoryAO getImagesCategoryAO() {
		return imagesCategoryAO;
	}


	public void setImagesCategoryAO(ImagesCategoryAO imagesCategoryAO) {
		this.imagesCategoryAO = imagesCategoryAO;
	}

    /*********图片名称********/
	private String fileName;
	
	

	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	 /*********图片 信息集合*******/
	private List<StorageFileInfoDO> resultList;
	
	 /*********图片 分页信息集合*******/
	private List<ImagesCategoryDO> imagesCategoryList;
	
	/**********排序集合************/
	private List<String[]> orderList;
	
	/**
	 * 子账户日志
	 */
	private MemberAsstLog memberAsstLog;
	
	public List<String[]> getOrderList() {
		return orderList;
	}


	public ShopShowInfoAO getShopShowInfoAO() {
		return shopShowInfoAO;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}


	public void setOrderList(List<String[]> orderList) {
		this.orderList = orderList;
	}


	public List<StorageFileInfoDO> getResultList() {
		return resultList;
	}


	public List<ImagesCategoryDO> getImagesCategoryList() {
		return imagesCategoryList;
	}


	public void setImagesCategoryList(List<ImagesCategoryDO> imagesCategoryList) {
		this.imagesCategoryList = imagesCategoryList;
	}


	public void setResultList(List<StorageFileInfoDO> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 当前页
	 */
	private Integer currentPage;
	
	private Paginator query;
	
	private String result;
	
	
	private Long id;
	
	private Long imageCategoryId;
	
	private String msg;
	
	private String searchMsg;
	
	private List<Long> idCheck;
	
	private ShopShowInfoManager shopShowInfoManager;
	
	//图片使用容量
	private double userSize;
	public double getUserSize() {
		return userSize;
	}
	public void setUserSize(double userSize) {
		this.userSize = userSize;
	}
	//使用图片张数
	private Integer imageCount;
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}

	public List<Long> getIdCheck() {
		return idCheck;
	}


	public void setIdCheck(List<Long> idCheck) {
		this.idCheck = idCheck;
	}
    
	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Long getImageCategoryId() {
		return imageCategoryId;
	}


	public void setImageCategoryId(Long imageCategoryId) {
		this.imageCategoryId = imageCategoryId;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getResult() {
		return result;
	}


	public Integer getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
    

	public Paginator getQuery() {
		return query;
	}


	public void setQuery(Paginator query) {
		this.query = query;
	}
	
	public StorageFileInfoAO getStorageFileInfoAO() {
		return storageFileInfoAO;
	}

	public void setStorageFileInfoAO(StorageFileInfoAO storageFileInfoAO) {
		this.storageFileInfoAO = storageFileInfoAO;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}


	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	private StorageFileInfoDO storageFileInfo;

	public StorageFileInfoDO getStorageFileInfo() {
		return storageFileInfo;
	}


	public String getSearchMsg() {
		return searchMsg;
	}


	public void setSearchMsg(String searchMsg) {
		this.searchMsg = searchMsg;
	}


	public void setStorageFileInfo(StorageFileInfoDO storageFileInfo) {
		this.storageFileInfo = storageFileInfo;
	}
	//获得当前登录人的ID
	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		}if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}
	//获得当前登录人的NickName
	private String getNickName() {
		String nickName = "";
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			nickName = login.getMasterMemberName();
		}if (log.isDebugEnabled()) {
			log.debug("member Name: " + nickName);
		}
		return nickName;
	}
	
	/*************查询图片信息并分页*******************/
	@AssistantPermission(target = "pic", action = "pic")
	public String queryStorageFileList() throws Exception{
		/**
		 * 根据卖家会员编号查看店铺
		 */
		ShopInfoDO shopInfoDO = null;
		if (shopInfoDO == null && getUserId() != 0L) {
			shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(getUserId(), null);
			if(shopInfoDO != null){
				if(shopInfoDO.getApproveStatus()==null){
					return "NOT_OPEN";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
						|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
					return "IS_CLOSE";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
					return "NOT_OPEN";
				}
			}else{
				return "NOT_EXIST";
			}
			
		}
		currentPage=this.currentPage!=null?this.currentPage:1;
		if(storageFileInfo!=null){
			storageFileInfo.setStartRow(0+(currentPage-1)*20);
			storageFileInfo.setPageCount(20);
			storageFileInfo.setMemberId(getUserId());
		}else{
			storageFileInfo=new StorageFileInfoDO();
			storageFileInfo.setStartRow(0+(currentPage-1)*20);
			storageFileInfo.setPageCount(20);
			/****显示默认分类***/
//			storageFileInfo.setImageCategoryId(imagesCategoryAO.getDefaultCategoryId(getUserId()));
			storageFileInfo.setMemberId(getUserId());
		}
		ImagesCategoryDO imagesCategoryDO = imagesCategoryAO.getImagesCategoryObject(getUserId());
		userSize = imagesCategoryDO.getUserSize();
		orderList=ImagesConstants.STORAGE_FILE_ORDER;
		int count=storageFileInfoAO.queryCount(storageFileInfo);
		StorageFileInfoDO storage=new StorageFileInfoDO();
		storage.setMemberId(getUserId());
		imageCount = storageFileInfoAO.queryCount(storage);
		imagesCategoryList=imagesCategoryAO.getImagesCategory(getUserId());
		resultList=storageFileInfoAO.getStorageFileInfoListByCon(storageFileInfo);
		query=new Paginator();
		query.setItems(count);
		query.setItemsPerPage(20);
		query.setPage(currentPage);
		query.setAction("/images/queryStorageAction.htm");
	    if(resultList==null || resultList.size()==0){
	    	searchMsg="搜索无结果";
	    }
		return SUCCESS;
	}
	
	public void view(){
		storageFileInfo=new StorageFileInfoDO();
		storageFileInfo.setMemberId(getUserId());
		ImagesCategoryDO imagesCategoryDO = imagesCategoryAO.getImagesCategoryObject(getUserId());
		userSize = imagesCategoryDO.getUserSize();
		orderList=ImagesConstants.STORAGE_FILE_ORDER;
		StorageFileInfoDO storage=new StorageFileInfoDO();
		storage.setMemberId(getUserId());
		imageCount = storageFileInfoAO.queryCount(storage);
		imagesCategoryList=imagesCategoryAO.getImagesCategory(getUserId());
	}
	
	/*************查询图片详情*******************/
	public String queryStorageFileInfo() throws Exception{
		/**
		 * 根据卖家会员编号查看店铺
		 */
		ShopInfoDO shopInfoDO = null;
		if (shopInfoDO == null && getUserId() != 0L) {
			shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(getUserId(), null);
			if(shopInfoDO != null){
				if(shopInfoDO.getApproveStatus()==null){
					return "NOT_OPEN";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
						|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
					return "IS_CLOSE";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
					return "NOT_OPEN";
				}
			}else{
				return "NOT_EXIST";
			}
			
		}
		StorageFileInfoDO storage=new StorageFileInfoDO();
		storage.setMemberId(getUserId());
		imageCount = storageFileInfoAO.queryCount(storage);
		imagesCategory= imagesCategoryAO.getImagesCategoryObject(getUserId());
		userSize = imagesCategory.getUserSize();
		imagesCategoryList=imagesCategoryAO.getImagesCategory(getUserId());
		storageFileInfo=storageFileInfoAO.queryStorageFileInfo(id,getUserId());
		if(null!=storageFileInfo){
		   if((null!=storageFileInfo.getMemberId() )  && storageFileInfo.getMemberId().equals(getUserId())){
			   if(null!=storageFileInfo.getImageCategoryId()){ 
				    String[] str= imagesCategory.getFirstNameOrSecondNameCategoryById(storageFileInfo.getImageCategoryId().toString());
					if(null!=str){
					    if(str[1]!=null && !str[1].isEmpty()){
							storageFileInfo.setImageCategoryName(str[0]+"-->"+str[1]);
						}else{
							storageFileInfo.setImageCategoryName(str[0]);
						}
					    return SUCCESS;
					}else{
						 searchMsg="此图片所属图片分类有误";
						 view();
						 return "error";  
					}
		     }else{
		    	 searchMsg="此图片不属于图片空间";
		    	 view();
		    	 return "error";
		     }
		   }else{
				  searchMsg="您无权访问此图片";
				  view();
				  return "error";  
		   }  
		}else{
			searchMsg="此图片不存在";
			 view();
			return "error";
		}
	}

	
	/***********删除图片【包括批量】************/
	@AssistantPermission(target = "pic", action = "pic")
	public String deleteStorageFileInfo(){
		if(idCheck==null){
			idCheck=new ArrayList<Long>();
			idCheck.add(id);
		}
		Long userId = getUserId();
		List<StorageFileInfoDO> storageFileInfoDOs = storageFileInfoAO.queryStorageFileByIds(idCheck, userId);
		boolean  isScu=storageFileInfoAO.deleteStorageFileByIds(idCheck,userId);
		if(isScu){
			for(StorageFileInfoDO storageFileInfoDO : storageFileInfoDOs){
				//子账户日志
				memberAsstLog.log("删除图片: " + storageFileInfoDO.getFileName());
			}
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/***********移动图片【包括批量】************/
	@AssistantPermission(target = "pic", action = "pic")
	public String removeStorageFile(){
		if(idCheck==null){
			idCheck=new ArrayList<Long>();
			idCheck.add(id);
		}
		Long userId = getUserId();
		boolean  isScu=storageFileInfoAO.updateStorageFileInfo(imageCategoryId,userId,idCheck);
		if(isScu){
			List<StorageFileInfoDO> storageFileInfoDOs = storageFileInfoAO.queryStorageFileByIds(idCheck, userId);
			ImagesCategoryDO imagesCategoryDO = imagesCategoryAO.getImagesCategoryObject(userId);
			if(storageFileInfoDOs != null && imagesCategoryDO != null && storageFileInfoDOs.size() > 0){
				for (StorageFileInfoDO storageFileInfoDO : storageFileInfoDOs) {
					// 子账户日志
					Long imgCat = storageFileInfoDO.getImageCategoryId();
					String imgCatString = imgCat == null ? "" : String.valueOf(imgCat);
					String []imgto = imagesCategoryDO.getFirstNameOrSecondNameCategoryById(imgCatString);
					memberAsstLog.log("移动图片: " + storageFileInfoDO.getFileName() + " 移动到分类 -- " + (imgto == null ? "" : imgto[0]));
				}
			}
			msg="移动成功";
		}else{
			msg="移动失败";
		}
		return SUCCESS;
	}
	
	/***********仅根据图片分类查询图片信息************/
	public String queryStorageByCateorgId() throws Exception{
		/**
		 * 根据卖家会员编号查看店铺
		 */
		ShopInfoDO shopInfoDO = null;
		if (shopInfoDO == null && getUserId() != 0L) {
			shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(getUserId(), null);
			if(shopInfoDO != null){
				if(shopInfoDO.getApproveStatus()==null){
					return "NOT_OPEN";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
						|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
					return "IS_CLOSE";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
					return "NOT_OPEN";
				}
			}else{
				return "NOT_EXIST";
			}
			
		}
		StorageFileInfoDO storage=new StorageFileInfoDO();
		storage.setMemberId(getUserId());
		imageCount = storageFileInfoAO.queryCount(storage);
		ImagesCategoryDO imagesCategoryDO = imagesCategoryAO.getImagesCategoryObject(getUserId());
		userSize = imagesCategoryDO.getUserSize();
		storageFileInfo=new StorageFileInfoDO();
		currentPage=this.currentPage!=null?this.currentPage:1;
		storageFileInfo.setStartRow(0);
		storageFileInfo.setPageCount(20);
		/****显示默认分类***/
//		storageFileInfo.setImageCategoryId(imagesCategoryAO.getDefaultCategoryId(getUserId()));
		storageFileInfo.setMemberId(getUserId());
		storageFileInfo.setImageCategoryId(imageCategoryId);
		orderList=ImagesConstants.STORAGE_FILE_ORDER;
		int count=storageFileInfoAO.queryCount(storageFileInfo);
		imagesCategoryList=imagesCategoryAO.getImagesCategory(getUserId());
		resultList=storageFileInfoAO.getStorageFileInfoListByCon(storageFileInfo);
		query=new Paginator();
		query.setItems(count);
		query.setItemsPerPage(20);
		query.setPage(currentPage);
		query.setAction("/images/queryStorageAction.htm");
		if(resultList==null || resultList.size()==0){
			searchMsg="此分类下还没有图片";
	    }
		return SUCCESS;
	}
	

	/***********修改图片空间的图片名称************/
	public String updateStorageFileName(){
		JSONObject json = this.updateAjaxStorageFileName();
		result = json.toString();
		return "success";
	}
    public JSONObject updateAjaxStorageFileName(){
    	try {
			boolean isContain=storageFileInfoAO.updateStorageFileInfoByFileName(fileName, id,getUserId());
			JSONObject json = new JSONObject();
			if(isContain){
				json.put("isUpdate", "1");
			}else{
				json.put("isUpdate", "0");
			}
			return json;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
    }


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
}
