package com.yuwang.pinju.web.module.active.screen;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.active.ao.ActiveAO;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.active.ActiveDescDO;
import com.yuwang.pinju.domain.active.ActiveInfoDO;
import com.yuwang.pinju.domain.active.ActiveInfoQuery;
import com.yuwang.pinju.domain.active.ActiveRegtDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.struts2.LoginChecker;
import com.yuwang.pinju.web.struts2.LoginChecker.LoginCheckerResult;
import com.yuwang.points.constants.PointsConstants;
import com.yuwang.points.message.RecevieMessage;
import com.yuwang.points.mina.client.PointsClient;
import com.yuwang.points.model.PointsAccountDO;
import com.yuwang.points.model.PointsTradeOrderDO;

/**
 * 活动管理Action
 * 
 * @author qiuhongming
 * 
 */
public class ActiveManagerAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 358145234438419548L;

	private final static Log log = LogFactory.getLog(ActiveManagerAction.class);

	private List<ActiveInfoDO> activeInfoList;
	private ActiveAO activeAO;
	private ActiveInfoDO activeInfo;
	private ActiveDescDO activeDesc;
	private ActiveRegtDO activeRegt;
	private ActiveInfoQuery query;
	private List<ActiveRegtDO> activeRegtList;

	private ShopShowInfoManager shopShowInfoManager;
	
	private String oriPrice;

	private String salePrice;

	/**
	 * 1K的文件大小1024
	 */
	public final static long FILE_SIZE_K = 1024;

	private static final String GOOD_REGIST = "goodRegist";
	private static final String SHOP_REGIST = "shopRegist";

	private static final String REGIST_INFO = "registInfo";
	private static final String ACTIVE_INFO = "activeInfo";

	private static final String REGIST_ACTIVE = "registActive";
	private static final String PASSED_ACTIVE = "passedActive";
	private static final String FAILURE = "failure";

	private static final int ITEM_PER_PAGE = 5;

	private File logo;
	private String logoFileName;
	private String logoContentType;

	/**
	 * 查询活动列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listActive() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		Long memberId = loginChecker.getMemberId();
		if (log.isDebugEnabled()) {
			log.debug("Query active list by member id: " + memberId);
		}

		// 验证卖家是否开店
		ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(memberId, null);
		if(shopInfoDO==null||shopInfoDO.getShopId()==null){
			return "NOT_OPEN";
		}else{
			if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()==ShopConstant.APPROVE_STATUS_HEGUI){
				return "IS_CLOSE";	
			}else if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()!=ShopConstant.APPROVE_STATUS_YES){
				return "NOT_EXIST";
			}
		}

		// 验证过滤，过滤信息封装在Query对象里
		query = activeAO.getActiveInfoQuery(memberId);

		if (query == null) {
			// 如果卖家资质信息错误，说明卖家尚未开店或者未缴纳保证金
			return "NOT_OPEN";
		}
		query.setMemberId(memberId);
		query.setNickName(loginChecker.getLogin().getNickname());

		int currentPage = getInteger("currentPage");
		if (currentPage == 0) {
			currentPage = 1;
		}
		query.setItemsPerPage(ITEM_PER_PAGE);
		query.setPage(currentPage);
		int count = activeAO.queryActiveCount(query);
		query.setItems(count);
		Result result = activeAO.queryActiveList(query);
		activeInfoList = (List<ActiveInfoDO>) result.getModel("activeInfoList");
		request.setAttribute("flags", result.getModel("flags"));
		query.setAction("/active/listActive.htm");

		return SUCCESS;
	}

	/**
	 * 查看活动详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String showActive() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		Long memberId = loginChecker.getMemberId();
		String type = getString("toWhere");
		if (activeInfoId == null || activeInfoId.intValue() == 0) {
			return FAILURE;
		}
		log.debug("Show Active Info by ID ：" + activeInfoId);
		activeInfo = activeAO.queryActiveInfo(activeInfoId);
		log.debug("Active Info --->>> " + activeInfo);
		// 查询卖家报名信息
		query = new ActiveInfoQuery();
		query.setMemberId(memberId);
		query.setNickName(loginChecker.getLogin().getNickname());
		query.setActivityId(activeInfo.getId());

		Result result = activeAO.queryActiveRegtList(query);
		if (!result.isSuccess()) {
			request.setAttribute("errorMessage", result
					.getModel("errorMessage"));
			return FAILURE;
		}
		activeRegtList = (List<ActiveRegtDO>) result.getModel("activeRegtList");
		request.setAttribute("isRegist", result.getModel("isRegist"));
		request.setAttribute("registed", result.getModel("registed"));
		request.setAttribute("passed", result.getModel("passed"));
		request.setAttribute("canRegist", result.getModel("canRegist"));

		log.debug("Active Regist Count : " + activeRegtList.size());

		if (REGIST_INFO.equals(type)) {
			// 报名信息页
			return REGIST_INFO;
		} else {
			// 活动详情页
			return ACTIVE_INFO;
		}
	}

	private Long activeInfoId;

	private Long activeRegtId;

	public Long getActiveInfoId() {
		return activeInfoId;
	}

	public void setActiveInfoId(Long activeInfoId) {
		this.activeInfoId = activeInfoId;
	}

	public Long getActiveRegtId() {
		return activeRegtId;
	}

	public void setActiveRegtId(Long activeRegtId) {
		this.activeRegtId = activeRegtId;
	}

	@SuppressWarnings("unchecked")
	public String listRegistInfo() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		Long memberId = loginChecker.getMemberId();

		if (activeInfoId == null || activeInfoId.intValue() == 0) {
			request.setAttribute("errorMessage", "查询活动报名信息失败，请重试！");
			return FAILURE;
		}

		activeInfo = activeAO.queryActiveInfo(activeInfoId);
		log.debug("Active Info --->>> " + activeInfo);
		// 查询卖家报名信息
		query = new ActiveInfoQuery();
		query.setMemberId(memberId);
		query.setNickName(loginChecker.getLogin().getNickname());
		query.setActivityId(activeInfo.getId());

		Result result = activeAO.queryActiveRegtList(query);
		if (!result.isSuccess()) {
			request.setAttribute("errorMessage", result
					.getModel("errorMessage"));
			return FAILURE;
		}
		activeRegtList = (List<ActiveRegtDO>) result.getModel("activeRegtList");
		request.setAttribute("isRegist", result.getModel("isRegist"));
		request.setAttribute("registed", result.getModel("registed"));
		request.setAttribute("passed", result.getModel("passed"));
		request.setAttribute("canRegist", result.getModel("canRegist"));

		log.debug("Active Regist Count : " + activeRegtList.size());

		return REGIST_INFO;
	}

	/**
	 * 活动报名准备
	 * 
	 * @return
	 * @throws Exception
	 */
	public String registActive() throws Exception {
		log.debug("Regist Active by ID ：" + activeInfo.getId()
				+ ", RegistType ：" + activeInfo.getRegistType());
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		Long memberId = loginChecker.getMemberId();
		Result result = activeAO.verifyActiveInfo(activeInfo, memberId);
		boolean flag = result.isSuccess();
		if (!flag) {
			log.debug("卖家无法报名此次活动！");
			request.setAttribute("errorMessage", result.getModel("errorMessage"));
			return FAILURE;
		} else {
			request.setAttribute("canRegist", result.getModel("canRegist"));
		}

		activeInfo = activeAO.queryActiveInfo(activeInfo.getId());
		int needPoints = activeInfo.getPoints();
		if(needPoints > 0){
			RecevieMessage<PointsAccountDO, String> recevieMessage = PointsClient
					.queryPointsAccount(memberId, 1);
			if (recevieMessage == null) {
				log.debug("查询卖家剩余积分失败！");
				request.setAttribute("errorMessage", "积分账户查询失败，请稍后再试！");
				return FAILURE;
			}
			if (recevieMessage.getResult().equals(
					PointsConstants.MESSAGE_RESULT_SUCCESS)) {
				long userPoints = recevieMessage.getResultObject() != null ? recevieMessage
						.getResultObject().getBalance().longValue()
						: 0;
				log.debug("卖家剩余积分：" + userPoints);
				if (needPoints > userPoints) {
					log.debug("卖家积分不足（" + userPoints + "）支付本次报名费用（" + needPoints
							+ "），无法报名此次活动！");
					request.setAttribute("errorMessage",
							"您的积分账户余额不足以支付本次报名费用，无法报名此次活动！");
					return FAILURE;
				}
			} else {
				log.debug(recevieMessage.getReason());
				request.setAttribute("errorMessage", "获取积分账户信息失败，请稍后再试！");
				return FAILURE;
			}
		}
		request.setAttribute("points", needPoints);

		activeDesc = activeAO.queryActiveDesc(activeInfo);
		log.debug("Active Regist Description : " + activeDesc);
		if (activeInfo.getRegistType().intValue() == 1) {
			return GOOD_REGIST;
		} else {
			return SHOP_REGIST;
		}
	}

	/**
	 * 活动报名
	 * 
	 * @return
	 * @throws Exception
	 */
	public String regist() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		Long memberId = loginChecker.getMemberId();

		if (!checkField(activeRegt)) {
			log.debug("活动报名信息有误");
			request.setAttribute("errorMessage", "活动报名信息不正确");
			return FAILURE;
		}

		activeRegt.setMemberId(memberId);
		activeRegt.setNickName(loginChecker.getLogin().getNickname());
		activeRegt.setCheckStatus(0);
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(memberId, null);
			if(shopInfoDO==null||shopInfoDO.getShopId()==null){
				return "NOT_OPEN";
			}else{
				if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()==ShopConstant.APPROVE_STATUS_HEGUI){
					return "IS_CLOSE";	
				}else if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()!=ShopConstant.APPROVE_STATUS_YES){
					return "NOT_EXIST";
				}
			}
			activeRegt.setCategoryId(Long.parseLong(shopInfoDO.getShopCategory()));
			activeRegt.setShopName(shopInfoDO.getName());
		} catch (Exception e) {
			// 
			log.error("获取店铺一级类目信息异常：", e);
			request.setAttribute("errorMessage", "系统繁忙请重试");
			return FAILURE;
		}
		
		// 价格处理
		try {
			if (activeRegt.getRegistType() != null
					&& activeRegt.getRegistType().intValue() == 1
					&& oriPrice != null && salePrice != null) {
				Money money = new Money(oriPrice);
				activeRegt.setOriPrice(money.getCent());
				money = new Money(salePrice);
				activeRegt.setSalePrice(money.getCent());
			}
		} catch (NumberFormatException e) {
			log.debug("价格格式不正确，报名失败！", e);
			request.setAttribute("errorMessage", "商品价格格式不正确");
			return FAILURE;
		}

		log.debug("Active Regist Info : " + activeRegt);
		log.debug("logoFileName : " + logoFileName + ", logoContentType : "
				+ logoContentType);
		if (logo != null) {
			if (!FileSecurityUtils.isImageValid(logo)) {
				// log.error("图片格式不正确，禁止上传！");
				request.setAttribute("errorMessage",
						"图片格式不正确");
				return FAILURE;
			}
			if (logo.length() / FILE_SIZE_K > 500) {
				// log.error("图片大小超限，禁止上传！");
				request.setAttribute("errorMessage", "图片太大，无法上传（最大500K）");
				return FAILURE;
			}
		}

		Result result = activeAO.addActiveRegistInfo(activeRegt, logo,
				logoFileName, logoContentType);

		activeInfoId = activeRegt.getActivityId();

		// 报名成功后，扣除卖家相应积分（允许积分扣除失败）
		if (result.isSuccess()) {
			activeInfo = activeAO.queryActiveInfo(activeInfoId);
			int needPoints = activeInfo.getPoints();
			if(needPoints > 0){
				RecevieMessage<PointsTradeOrderDO, String> recevieMessage = PointsClient
						.updatePointsAccount(memberId, 1, 4, 0, Double
								.valueOf(Integer.toString(needPoints)));
				if (recevieMessage == null) {
					log.debug("扣除卖家积分异常！");
				} else if (!PointsConstants.MESSAGE_RESULT_SUCCESS
						.equals(recevieMessage.getResult())) {
					log.debug("扣除卖家积分失败：" + recevieMessage.getReason());
				} else {
					log.debug("扣除积分成功：" + needPoints);
				}
			}
		}

		return SUCCESS;
	}

	/**
	 * 报名信息必填字段校验
	 * 
	 * @param active
	 * @return
	 */
	private boolean checkField(ActiveRegtDO active) {
		if (active == null) {
			log.debug("活动报名信息对象错误");
			return false;
		}
		if (active.getRegistType() == null) {
			log.debug("报名类型错误");
			return false;
		}
		if (logoFileName == null || logoFileName.length() > 255) {
			log.debug("图片错误");
			return false;
		}
		// 商品报名
		if (active.getRegistType().intValue() == 1) {
			if (oriPrice == null) {
				log.debug("商品原价错误");
				return false;
			}
			if (salePrice == null) {
				log.debug("商品现价错误");
				return false;
			}
			if (active.getAuctionTitle() == null
					|| active.getAuctionTitle().length() > 80) {
				log.debug("商品名称错误");
				return false;
			}
			if (active.getAuctionUrl() == null
					|| active.getAuctionUrl().length() > 255) {
				log.debug("商品URL错误");
				return false;
			}
			if (active.getAuctionNum() == null) {
				log.debug("商品数量错误");
				return false;
			}
			if (active.getIsIsbn() == null) {
				log.debug("是否包邮错误");
				return false;
			}
			if (active.getOtherInfo() == null
					|| active.getOtherInfo().length() > 500) {
				log.debug("其他信息错误");
				return false;
			}
		} else {
			if (active.getShopTitle() == null
					|| active.getShopTitle().length() > 80) {
				log.debug("店铺名称错误");
				return false;
			}
			if (active.getShopUrl() == null
					|| active.getShopUrl().length() > 255) {
				log.debug("店铺URL错误");
				return false;
			}
			if (active.getShopInfo() == null
					|| active.getShopInfo().length() > 500) {
				log.debug("店铺信息错误");
				return false;
			}
		}
		return true;
	}

	/**
	 * 查询卖家已报名和已通过的活动信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String query() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		Long memberId = loginChecker.getMemberId();
		if (log.isDebugEnabled()) {
			log.debug("Query active list by member id: " + memberId);
		}
		if (query == null) {
			query = new ActiveInfoQuery();
		}
		query.setMemberId(memberId);
		query.setNickName(loginChecker.getLogin().getNickname());

		log.debug("Active Info Query : " + query);
		int currentPage = getInteger("currentPage");
		if (currentPage == 0) {
			currentPage = 1;
		}
		
		int checkStatus = getInteger("checkStatus");
		if (checkStatus == 1) {
			query.setCheckStatus(1);
		}

		query.setItemsPerPage(ITEM_PER_PAGE);
		query.setPage(currentPage);
		int count = activeAO.queryActivePageCount(query);
		query.setItems(count);
		Result result = activeAO.queryActivePageList(query);
		if (!result.isSuccess()) {
			return FAILURE;
		}
		activeInfoList = (List<ActiveInfoDO>) result.getModel("activeInfoList");
		request.setAttribute("flags", result.getModel("flags"));
		query.setAction("/active/query.htm");
		if (query.getCheckStatus() != null
				&& query.getCheckStatus().intValue() == 1) {
			return PASSED_ACTIVE;
		}
		return REGIST_ACTIVE;
	}

	/**
	 * 撤销活动报名
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cancel() throws Exception {
		if (activeInfoId == null || activeRegtId == null
				|| activeInfoId.intValue() == 0 || activeRegtId.intValue() == 0) {
			request.setAttribute("errorMessage", "活动报名信息撤销失败，请重试！");
			return FAILURE;
		}
		if (activeRegt == null) {
			activeRegt = new ActiveRegtDO();
		}
		activeRegt.setId(activeRegtId);
		activeRegt.setActivityId(activeInfoId);
		activeAO.cancelActiveRegist(activeRegt);

		return SUCCESS;
	}

	public List<ActiveInfoDO> getActiveInfoList() {
		return activeInfoList;
	}

	public void setActiveInfoList(List<ActiveInfoDO> activeInfoList) {
		this.activeInfoList = activeInfoList;
	}

	public ActiveAO getActiveAO() {
		return activeAO;
	}

	public void setActiveAO(ActiveAO activeAO) {
		this.activeAO = activeAO;
	}

	public ActiveInfoDO getActiveInfo() {
		return activeInfo;
	}

	public void setActiveInfo(ActiveInfoDO activeInfo) {
		this.activeInfo = activeInfo;
	}

	public ActiveDescDO getActiveDesc() {
		return activeDesc;
	}

	public void setActiveDesc(ActiveDescDO activeDesc) {
		this.activeDesc = activeDesc;
	}

	public ActiveRegtDO getActiveRegt() {
		return activeRegt;
	}

	public void setActiveRegt(ActiveRegtDO activeRegt) {
		this.activeRegt = activeRegt;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getLogoContentType() {
		return logoContentType;
	}

	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}

	public ActiveInfoQuery getQuery() {
		return query;
	}

	public void setQuery(ActiveInfoQuery query) {
		this.query = query;
	}

	public List<ActiveRegtDO> getActiveRegtList() {
		return activeRegtList;
	}

	public void setActiveRegtList(List<ActiveRegtDO> activeRegtList) {
		this.activeRegtList = activeRegtList;
	}

	public String getOriPrice() {
		return oriPrice;
	}

	public void setOriPrice(String oriPrice) {
		this.oriPrice = oriPrice;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

}
