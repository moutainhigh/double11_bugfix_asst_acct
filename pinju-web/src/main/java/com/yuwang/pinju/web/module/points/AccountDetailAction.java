package com.yuwang.pinju.web.module.points;


import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;
import com.yuwang.points.constants.PointsConstants;
import com.yuwang.points.message.RecevieMessage;
import com.yuwang.points.mina.client.PointsClient;
import com.yuwang.points.model.PointsAccountDO;
import com.yuwang.points.model.PointsAccountDetailDO;
import com.yuwang.points.model.PointsDictionaryDO;
import com.yuwang.points.util.PointsDictionaryUtil;


public class AccountDetailAction extends BaseWithUserAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3351635675571534610L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	/*************查询条件**************/
	private String startDate;
	private String endDate;
	private Integer tradeChannel;
	private Integer accountType;
	private ShopShowInfoManager shopShowInfoManager;
	protected String errorMessage="错误";
	private PointsDictionaryUtil  dictionaryUtil=PointsDictionaryUtil.getInstance();
	public PointsDictionaryUtil getDictionaryUtil() {
		return dictionaryUtil;
	}


	public void setDictionaryUtil(PointsDictionaryUtil dictionaryUtil) {
		this.dictionaryUtil = dictionaryUtil;
	}

	private Long balance;

	public Long getBalance() {
		return balance;
	}


	public void setBalance(Long balance) {
		this.balance = balance;
	}


	public Integer getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
    
	public List<PointsDictionaryDO> channelList;

	public List<PointsDictionaryDO> getChannelList() {
		return channelList;
	}


	public void setChannelList(List<PointsDictionaryDO> channelList) {
		this.channelList = channelList;
	}

	/**
	 * 当前页
	 */
	private Integer currentPage;

    private List<PointsAccountDetailDO> resultList;
	public List<PointsAccountDetailDO> getResultList() {
		return resultList;
	}


	public void setResultList(List<PointsAccountDetailDO> resultList) {
		this.resultList = resultList;
	}
    
	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}


	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private Paginator query;

	public Paginator getQuery() {
		return query;
	}


	public void setQuery(Paginator query) {
		this.query = query;
	}
	
	/**
	 * 查询当前登录用户的积分余额
	 * @return
	 */
	public Long queryAccountBalance(){
        RecevieMessage<PointsAccountDO, String> recevieMessage=PointsClient.queryPointsAccount(getUserId(),1);
		if(recevieMessage.getResult().equals(PointsConstants.MESSAGE_RESULT_SUCCESS)){
			return recevieMessage.getResultObject()!=null?recevieMessage.getResultObject().getBalance():null;
		}else{
			log.error(recevieMessage.getReason());
			return null;
		}
	}
	/**
	 * 根据输入参数查询交易明细
	 * @return
	 */
	public String queryAccountDetail() throws Exception{
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
		PointsAccountDetailDO accountDetail=new PointsAccountDetailDO();
	     accountDetail.setMemberId(getUserId());
		if(null!=tradeChannel){
			accountDetail.setTradeChannel(tradeChannel);
		}
		if(null==accountType){
			accountType=PointsConstants.ACCOUNT_TYPE;
		}
	     accountDetail.setAccountType(accountType);
		if(null==startDate || startDate.isEmpty()){
			startDate=DateUtil.formatDate(new Date());
			endDate=DateUtil.formatDate(new Date());
		}
		accountDetail.setStartDate(startDate);
		accountDetail.setEndDate(endDate);
		try{
			RecevieMessage<String,PointsAccountDetailDO> recevieMessage=PointsClient.queryPointsAccountDetail(getUserId(), accountType, startDate, endDate, tradeChannel,20, currentPage);
			resultList=(List<PointsAccountDetailDO>) recevieMessage.getContent().getResultList();
			this.channelList=dictionaryUtil.getPointsDictionaryList(com.yuwang.points.constants.PointsConstants.SELLERPOINTS_STAT_CHANNEL);
			balance=queryAccountBalance();
			query=new Paginator();
			query.setItems(recevieMessage.getItems());
			query.setItemsPerPage(20);
			query.setPage(currentPage);
			query.setAction("/points/showAccountInfo.htm");
		}catch(Exception e){
			log.error("积分服务器报错",e);
			this.channelList=null;
			balance=0L;
		}
		return "success";
	}

	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Integer getTradeChannel() {
		return tradeChannel;
	}


	public void setTradeChannel(Integer tradeChannel) {
		this.tradeChannel = tradeChannel;
	}


	public Integer getAccountType() {
		return accountType;
	}


	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	
	
}