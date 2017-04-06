/**
 * 
 */
package com.yuwang.pinju.web.module.distribute.screen;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.distribute.ao.DistributorAO;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemParamDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;
import com.yuwang.pinju.domain.distribute.DistributorItemQuery;
import com.yuwang.pinju.domain.shop.ShopQuery;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * 分销商显示
 * 
 * @author caiwei
 *
 */
public class DistributorAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4754920013078946471L;

	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 是否绑定财付通帐户
	 */
	private Boolean bindAccount;
	/**
	 * 是否签约
	 */
	private Boolean signArgeement;
	/**
	 * 供应商信息查询参数
	 */
	private DistributeSupplierParamDO supplierParam;
	/**
	 * 供应商信息返回结果
	 */
	private DistributeSupplierDO supplierResult;
	/**
	 * 供应商商品信息查询参数
	 */
	private DistrbuteSupplierItemParamDO supplierItemParam;
	/**
	 * 分销商信息查询参数
	 */
	private DistribureChannelParamDO channelParam;
	/**
	 * 分销商商品信息查询参数
	 */
	private DistributorItemQuery itemQuery;
	/**
	 * 省份信息
	 */
	private String province;
	/**
	 * 消保类型
	 */
	private String exchangeType;
	/**
	 * 当前页码[分页]
	 */
	private Integer currentPage;
	/**
	 * 分页信息
	 */
	private Paginator query;
	/**
	 * 供应商列表
	 */
	private List<DistributeSupplierDO> distributeSupplierDOList;
	/**
	 * 供应商商品列表
	 */
	private List<DistrbuteSupplierItemDO> distrbuteSupplierItemDOList;
	/**
	 * 分销商信息列表
	 */
	private List<DistribureChannelDO> distribureChannelDOList;
	
	private String merchantno = PinjuConstant.TENPAY_PAY_PARTNER;
	
	@Autowired
	private DistributorManager distributorManager;
	@Autowired
	private DistributorAO distributorAO;
	
	/**
	 * 用户欢迎页面
	 * 
	 * @return
	 */
	public String index(){
	    Long distributorId = getDistributorDO().getId();
	    if (distributorId == null) {
		return SUCCESS;
	    }else {
		return "welcome";
	    }
	}
	
	/**
	 * 绑定财付通接口
	 * 
	 * @return
	 */
	public String bindAccount(){
	    loadNickName();
	    this.bindAccount = distributorAO.checkAccount(getLoginInfo().getMemberId());
	    //this.signArgeement = distributorAO.checkAgreement(getLoginInfo().getMemberId());
	    return SUCCESS;
	}
	
	/**
	 * 分销商欢迎页面
	 * 
	 * @return
	 */
	public String welcome(){
		loadNickName();
		return SUCCESS;
	}
	
	/**
	 * 分销商申请页面
	 * 
	 * @return
	 */
	public String applyDistributor() {
		loadNickName();
		ShopQuery shopQuery = distributorAO.querySupplierList(new ShopQuery(getCurrentPage(), this.province, this.exchangeType));
		initApplyParam(shopQuery);
		this.distributeSupplierDOList = distributorManager.findAllDistributeSupplierInfo(shopQuery);
		return SUCCESS;
	}
	
	/**
	 * 供应商商品展示页面
	 * 
	 * @return
	 */
	public String supplierItemList(){
		initSupplierItemParam();
		if (this.supplierItemParam != null) {
			this.distrbuteSupplierItemDOList = distributorAO.querySupplierItems(distributorManager.findAllSupplierItems(this.supplierItemParam));
		}
		return SUCCESS;
	}
	
	/**
	 * 供应商招募书页面
	 * 
	 * @return
	 */
	public String supplierRelease(){
		if (this.supplierParam != null) {
			this.supplierResult = distributorAO.querySupplier(distributorManager.findDistributeSupplierByCondition(this.supplierParam));
		}
		return SUCCESS;
	}
	
	/**
	 * 分销管理[申请中供应商管理]
	 * 
	 * @return
	 */
	public String applyManager(){
		loadNickName();
		Long distributorId = getDistributorDO().getId();
		//判断用户是否具有分销商资质
		if (distributorId != null) {
			initSupplierManagerParam(distributorId,new Short("0"),"/distributor/applyManager.htm");//[0:审核中]
			if (this.channelParam != null) {
				this.distributeSupplierDOList = distributorAO.querySupplierList(distributorManager.findDistributeSuppliersByChannel(this.channelParam));
				populateCountInfo(distributorId,this.channelParam.getStatus());
			}
			return SUCCESS;
		}else {
			return ERROR;
		}
	}
	
	/**
	 * 分销管理[合作中供应商管理]
	 * @return
	 */
	public String alignmentManager(){
		loadNickName();
		Long distributorId = getDistributorDO().getId();
		//判断用户是否具有分销商资质
		if (distributorId != null) {
			initSupplierManagerParam(distributorId,new Short("1"),"/distributor/alignmentManager.htm");//[0:合作中]
			if (this.channelParam != null) {
				this.distributeSupplierDOList = distributorAO.querySupplierList(distributorManager.findDistributeSuppliersByChannel(this.channelParam));
				populateCountInfo(distributorId,this.channelParam.getStatus());
			}
			return SUCCESS;
		}else {
			return ERROR;
		}
	}
	
	/**
	 * 分销管理[历史供应商管理]
	 * 
	 * @return
	 */
	public String historyManager(){
		loadNickName();
		Long distributorId = getDistributorDO().getId();
		//判断用户是否具有分销商资质
		if (distributorId != null) {
			initSupplierManagerParam(distributorId,new Short("-1"),"/distributor/historyManager.htm");//[-1:历史合作]
			if (this.channelParam != null) {
				this.distributeSupplierDOList = distributorAO.querySupplierList(distributorManager.findDistributeSuppliersByChannel(this.channelParam));
				populateCountInfo(distributorId,this.channelParam.getStatus());
			}
			return SUCCESS;
		}else {
			return ERROR;
		}
	}
	
	/**
	 * 分销管理[已分销商品]
	 * 
	 * @return
	 */
	public String soldManager(){
	    loadNickName();
	    Long distributorId = getDistributorDO().getId();
	    //判断用户是否具有分销商资质
	    if (distributorId != null) {
			initSoldParam(distributorId,"/distributor/soldManager.htm");
			this.itemQuery = distributorManager.findAllowedSoldItems(this.itemQuery);
			initAfterSoldParam(distributorId);
			return SUCCESS;
	    }else {
	    	return ERROR;
	    }
	}
	
	/**
	 * 分销管理[未分销商品]
	 * 
	 * @return
	 */
	public String unsoldManager(){
	    loadNickName();
	    Long distributorId = getDistributorDO().getId();
	    //判断用户是否具有分销商资质
	    if (distributorId != null) {
			initSoldParam(distributorId,"/distributor/unsoldManager.htm");
			this.itemQuery = distributorManager.findAllowedUnsoldItems(this.itemQuery);
			initAfterSoldParam(distributorId);
			return SUCCESS;
	    }else {
	    	return ERROR;
	    }
	}
	
	/**
	 * 前置处理分销商品参数
	 * 
	 * @param distributorId
	 * @param actionUrl
	 */
	private void initSoldParam(Long distributorId, String actionUrl){
	    this.itemQuery = new DistributorItemQuery(distributorId);
	    this.itemQuery.setPage(getCurrentPage());
	    this.itemQuery.setAction(actionUrl);
	}
	
	/**
	 * 后置处理分销商品参数
	 * 
	 * @param distributorId
	 */
	private void initAfterSoldParam(Long distributorId){
	    this.distribureChannelDOList = this.itemQuery.getDistribureChannelDOList();
	    this.distrbuteSupplierItemDOList = distributorAO.querySupplierItems(this.itemQuery.getDistrbuteSupplierItemDOList());
	    this.itemQuery.setApplyStatusCount(distributorManager.findDistributeSuppliersCountByChannel(new DistribureChannelParamDO(distributorId, new Short("1"))));
	    setQuery(this.itemQuery);
	}
	
	/**
	 * 分销管理[统记信息]
	 * 
	 * @param status
	 */
	private void populateCountInfo(Long distributorId,Short status){
		this.supplierParam = new DistributeSupplierParamDO();
		DistribureChannelParamDO param = new DistribureChannelParamDO(distributorId,status);
		if (new Short("0").equals(status)) {
			this.supplierParam.setApplyCount(getQuery().getItems());
			param.setStatus(new Short("1"));
			this.supplierParam.setAlignmentCount(distributorManager.findDistributeSuppliersCountByChannel(param));
			param.setStatus(new Short("-1"));
			this.supplierParam.setHistoryCount(distributorManager.findDistributeSuppliersCountByChannel(param));
		}
		if (new Short("1").equals(status)) {
			this.supplierParam.setAlignmentCount(getQuery().getItems());
			param.setStatus(new Short("-1"));
			this.supplierParam.setHistoryCount(distributorManager.findDistributeSuppliersCountByChannel(param));
			param.setStatus(new Short("0"));
			this.supplierParam.setApplyCount(distributorManager.findDistributeSuppliersCountByChannel(param));
		}
		if (new Short("-1").equals(status)) {
			this.supplierParam.setHistoryCount(getQuery().getItems());
			param.setStatus(new Short("0"));
			this.supplierParam.setApplyCount(distributorManager.findDistributeSuppliersCountByChannel(param));
			param.setStatus(new Short("1"));
			this.supplierParam.setAlignmentCount(distributorManager.findDistributeSuppliersCountByChannel(param));
		}
	}
	
	/**
	 * 管理供应商初始化[分销模块]
	 * @param status
	 * 			分销商与供应商的状态
	 * @param actionUrl
	 * 			分页跳转的URL
	 */
	private void initSupplierManagerParam(Long distributorId,Short status,String actionUrl){
		if (this.channelParam == null) {
			this.channelParam = new DistribureChannelParamDO();
		}
		//状态[0:等待审核中][1:合作中][2:申请被拒绝][3:撤消申请]
		this.channelParam.setStatus(status);
		//分销商的ID
		this.channelParam.setDistributorId(distributorId);
		/** 分页参数 */
		this.channelParam.setItems(distributorManager.findDistributeSuppliersCountByChannel(this.channelParam));
		this.channelParam.setItemsPerPage(10);
		this.channelParam.setPage(getCurrentPage());
		this.channelParam.setAction(actionUrl);
		//设置分页
		setQuery(this.channelParam);
	}
	
	/**
	 * 初始化申请供应商查询参数
	 */
	private void initApplyParam(ShopQuery param){
		if (param == null) {
			param = new ShopQuery(getCurrentPage(), this.province, this.exchangeType);
		}
		param.setAction("/distributor/applyDistributor.htm");
		setQuery(param);
	}
	
	/**
	 * 初始化供应商商品展示参数
	 */
	private void initSupplierItemParam() {
		if (this.supplierItemParam != null) {
			//状态是分销中
			this.supplierItemParam.setStatus(new Short("0"));
			/** 分页参数 */
			this.supplierItemParam.setItems(distributorManager.getSupplierItemsCount(this.supplierItemParam));
			this.supplierItemParam.setPage(getCurrentPage());
			this.supplierItemParam.setItemsPerPage(10);
			this.supplierItemParam.setAction("/distributor/supplierItems.htm");
			//设置分页
			setQuery(this.supplierItemParam);
		}
	}
	
	/**
	 * 根据当前登录用户的ID获取用户的分销商信息
	 * @return
	 */
	@JSON(serialize=false)
	private DistributeDistributorDO getDistributorDO(){
		if (getLoginInfo() != null) {
		    	DistributeDistributorDO distributeDistributorDO = distributorManager.findDistributorByMemberId(getLoginInfo().getMemberId());
			if (distributeDistributorDO != null) {
			    return distributeDistributorDO;
			}
		}
		return new DistributeDistributorDO();
	}
	
	/**
	 * 读取登录信息
	 * 
	 * @return
	 */
	protected CookieLoginInfo getLoginInfo() {
		return CookieLoginInfo.getCookieLoginInfo();
	}
	
	/**
	 * 读取用户昵称
	 */
	private void loadNickName() {
		this.nickName = getLoginInfo().getNickname();
	}
	public String getNickName(){
		return this.nickName;
	}
	
	public Paginator getQuery(){
		return query;
	}
	
	public <T extends Paginator> void setQuery(T t){
		this.query = t;
	}
	
	public Integer getCurrentPage() {
		return currentPage!=null?currentPage:0;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public List<DistributeSupplierDO> getDistributeSupplierDOList() {
		return distributeSupplierDOList;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return province;
	}

	public List<DistrbuteSupplierItemDO> getDistrbuteSupplierItemDOList() {
		return distrbuteSupplierItemDOList;
	}

	public void setSupplierItemParam(DistrbuteSupplierItemParamDO supplierItemParam) {
		this.supplierItemParam = supplierItemParam;
	}

	public DistrbuteSupplierItemParamDO getSupplierItemParam() {
		return supplierItemParam;
	}

	public DistributeSupplierParamDO getSupplierParam() {
		return supplierParam;
	}

	public void setSupplierParam(DistributeSupplierParamDO supplierParam) {
		this.supplierParam = supplierParam;
	}

	public void setChannelParam(DistribureChannelParamDO channelParam) {
		this.channelParam = channelParam;
	}

	public DistributeSupplierDO getSupplierResult() {
		return supplierResult;
	}

	public DistributorItemQuery getItemQuery() {
	    return itemQuery;
	}

	public List<DistribureChannelDO> getDistribureChannelDOList() {
	    return distribureChannelDOList;
	}

	public String getExchangeType() {
	    return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
	    this.exchangeType = exchangeType;
	}

	public Boolean getBindAccount() {
	    return bindAccount;
	}

	public Boolean getSignArgeement() {
	    return signArgeement;
	}

	public String getMerchantno() {
		return merchantno;
	}

}
