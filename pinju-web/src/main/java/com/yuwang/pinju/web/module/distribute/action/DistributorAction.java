/**
 * 
 */
package com.yuwang.pinju.web.module.distribute.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
import com.yuwang.pinju.core.distribute.ao.DistributeImageAO;
import com.yuwang.pinju.core.distribute.ao.DistributorAO;
import com.yuwang.pinju.core.distribute.manager.DistributeChannelItemManager;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;
import com.yuwang.pinju.domain.member.ConcernDO;
import com.yuwang.pinju.domain.member.PrivateMailDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * 分销商操作
 * 
 * @author caiwei
 *
 */
public class DistributorAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3453261391914982951L;

	/**
	 * 用户编号
	 */
	private Long memberId;
	/**
	 * 私信主体内容
	 */
	private String content;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 状态
	 */
	private Boolean status;
	/**
	 * 提示信息
	 */
	private String message;
	/**
	 * 分销商供应商关系IDs
	 */
	private List<Long> channelIdList;
	/**
	 * 分销商供应商关系参数
	 */
	private DistribureChannelParamDO queryparam;
	/**
	 * 分销商供应商关系参数
	 */
	private DistribureChannelDO param;
	/**
	 * 商品ID
	 */
	private Long itemId;
	/**
	 * 推荐理由
	 */
	private String recommendReason;
	/**
	 * 分销商品Id
	 */
	private Long channelItemId;
	/**
	 * 商品图片地址
	 */
	private String imageUrl;
	
	private Boolean needForward;
	
	private File picFile;
	
	private String picFileFileName;
	
	private String submitType;
	
	private static final String SUBMISSION_TYPE = "custom";
	
	private static final String[] FILE_TYPE_ARRAY = {"jpg","png","gif","bmp"};
	
	@Autowired
	private DistributorAO distributorAO;
	
	@Autowired
	private DistributorManager distributorManager;
	
//	@Autowired
//	private DistributeSupplierManager distributeSupplierManager;
	
	@Autowired
	private DistributeChannelItemManager distributeChannelItemManager;
	
	@Autowired
	private DistributeImageAO distributeImageAO;
	
	private final static Integer maxInputSize = 200;
	/**
	 * 申请分销商
	 * 
	 * @return
	 */
	public String applyAuthority(){
		loadUserId();
		//查看用户是否已经是分销商
		DistributeDistributorDO distributeDistributorDO = distributorManager.findDistributorByMemberId(this.memberId);
//		DistributeSupplierDO distributeSupplierDO = null;
//		try {
//		    distributeSupplierDO = distributeSupplierManager.getDistributeSupplier(new DistributeSupplierParamDO(this.memberId));
//		} catch (ManagerException e) {
//		    e.printStackTrace();
//		}
//		if (distributeSupplierDO == null) {
		    if (distributeDistributorDO != null && distributeDistributorDO.getId() == null) {
			//查看用户是否绑了财付通
			if (distributorAO.checkPaymentAuthority(this.memberId)) {
			    //申请成功,保存分销商信息
			    distributorManager.saveDistributor(this.memberId, getLoginInfo().getNickname());
			    setJsonResult(Boolean.TRUE, Message.getMessage("distributor.apply.success"));
			}else {
			    this.needForward = Boolean.TRUE;
			    setJsonResult(Boolean.FALSE, Message.getMessage("distributor.apply.failure"));
			}
		    }else{
			setJsonResult(Boolean.FALSE, Message.getMessage("distributor.apply.again"));
		    }
//		}else {
//		    this.needForward = Boolean.FALSE;
//		    setJsonResult(Boolean.FALSE, Message.getMessage("distributor.apply.supplier"));
//		}
		return "json";
	}
	
	/**
	 * 申请某一供应商的分销商
	 * 
	 * @return
	 */
	public String apply(){
		Long distributorId = getDistributorDO().getId();
		//判断用户是否具有分销商资质
		if (distributorId != null) {
			if (populateDistribureChannelAndCheckStatus(distributorId,new Short("-2")) && distributorManager.saveDistribureChannel(this.param)) {//[-2:审核中或是合作中]
				setJsonResult(Boolean.TRUE, Message.getMessage("distributor.apply.distribureChannel.success"));
			}else {
				setJsonResult(Boolean.FALSE, Message.getMessage("distributor.apply.distribureChannel.failure"));
			}
		}else {
			setJsonResult(Boolean.FALSE, Message.getMessage("distributor.apply.distribureChannel.null"));
		}
		return "json";
	}
	
	/**
	 * 分销商撤消申请
	 * 
	 * @return
	 */
	public String cancelApply(){
		distributorManager.updateDistribureChannels(populateCancelApplyParamList());
		return SUCCESS;
	}
	
	/**
	 * 分销商品
	 * 
	 * @return
	 */
	public String sold(){
			Long distributorId = getDistributorDO().getId();
			//判断用户是否具有分销商资质
			if (distributorId != null && this.itemId != null) {
				if (StringUtils.hasText(this.recommendReason) && this.recommendReason.length() <= 100 && !WordFilterFacade.scan(this.recommendReason, SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON)) {
					if (distributeChannelItemManager.addDistributeChannelItem(populateDistributeChannelItemDO(distributorId))) {
						setJsonResult(Boolean.TRUE, Message.getMessage("distributor.manager.sold.success"));
					}else {
						setJsonResult(Boolean.FALSE, Message.getMessage("distributor.manager.sold.failure"));
					}
				}else {
					setJsonResult(Boolean.FALSE, Message.getMessage("distributor.manager.sold.error"));
				}
			}else {
				setJsonResult(Boolean.FALSE, Message.getMessage("distributor.manager.sold.failure"));
			}
		return jsonResult();
	}
	
	
	/**
	 * 分享商品购图片检测
	 * 
	 * @return
	 */
	public String checkPic(){
			Long distributorId = getDistributorDO().getId();
			//判断用户是否具有分销商资质
			if (distributorId != null) {
				//图片上传格式是否正确
				if (validatePicFileType()) {
					Result result = distributeImageAO.checkImageInfo(this.picFile, 194, 194, 292, 122);
					//图片大小、尺寸是否正确
					if (result != null) {
						if (result.isSuccess()) {
							setJsonResult(Boolean.TRUE, Message.getMessage("distributor.item.picture.success"));
						}else {
							setJsonResult(Boolean.FALSE, result.getResultCode());
						}
					}else {
						setJsonResult(Boolean.FALSE, Message.getMessage("distributor.item.picture.failure"));
					}
				}else {
					setJsonResult(Boolean.FALSE, Message.getMessage("distributor.manager.sold.picture.error"));
				}
			}else {
				setJsonResult(Boolean.FALSE, Message.getMessage("distributor.item.picture.failure"));
			}
		return jsonResult();
	}
	
	/**
	 * 停止分销商品
	 * 
	 * @return
	 */
	public String unsold(){
        Long distributorId = getDistributorDO().getId();
        //判断用户是否具有分销商资质
        if (distributorId != null && this.itemId != null) {
	    	if (distributeChannelItemManager.discardDistributeChannelItem(populateDistributeChannelItemDO(distributorId))) {
				setJsonResult(Boolean.TRUE, Message.getMessage("distributor.manager.unsold.success"));
			}else {
				setJsonResult(Boolean.FALSE, Message.getMessage("distributor.manager.unsold.failure"));
			}
        }else {
        	setJsonResult(Boolean.FALSE, Message.getMessage("distributor.manager.sold.failure"));
		}
            return "json";
	}
	
	/**
	 * 发送关注信息
	 * 
	 * @return
	 */
	public String sendConcern(){
	    Long supplierId = getUserId();
	    loadUserId();
	    if (distributorAO.sendConcernDO(new ConcernDO(0, this.memberId, supplierId))) {
		setJsonResult(Boolean.TRUE, Message.getMessage("distributor.apply.concern.success"));
	    }else {
		setJsonResult(Boolean.TRUE, Message.getMessage("distributor.apply.concern.failure"));
	    }
	    return "json";
	}
	
	/**
	 * 给供应商发送私信
	 * 
	 * @return
	 */
	public String sendMail(){
		Long memberIdCopy = getLoginInfo().getMemberId();
		if (memberIdCopy != null && this.memberId != null && !ObjectUtils.nullSafeEquals(memberIdCopy, this.memberId)) {
			if ( StringUtils.hasText(this.content) && this.content.length() <= maxInputSize) {
				if (distributorAO.sendMail(populatePrivateMail())) {
					setJsonResult(Boolean.TRUE, Message.getMessage("distributor.apply.mail.success"));
				}else {
					setJsonResult(Boolean.FALSE, Message.getMessage("distributor.apply.mail.failure"));
				}
			}else {
				setJsonResult(Boolean.FALSE, Message.getMessage("distributor.apply.mail.toolong"));
			}
		}else {
			setJsonResult(Boolean.FALSE, "私信接收方必需为其它有效供应商");
		}
	    return "json";
	}
	
	/**
	 * 组装私信内容
	 * 
	 * @return
	 */
	private PrivateMailDO populatePrivateMail(){
	    PrivateMailDO result = new PrivateMailDO();
	    result.setIpaddr(ipAddr());
	    result.setMessage(this.content);
	    result.setReceive_user_id(this.memberId);
	    result.setReceiveusername(this.nickName);
	    result.setSend_user_id(getLoginInfo().getMemberId());
	    result.setSendusername(getLoginInfo().getNickname());
	    result.setMessage_status(2);//[2:商城]
	    return result;
	}
	
	/**
	 * 组装分销（停止||开始）商品DO
	 * @param distributorId
	 * @param status
	 * @return
	 */
	private DistributeChannelItemDO populateDistributeChannelItemDO(Long distributorId){
	    DistributeChannelItemDO result = new DistributeChannelItemDO();
	    result.setChannelId(distributorId);
	    result.setItemId(this.itemId);
	    //判断是默认图片上传还是自定义图片上传
	    if (ObjectUtils.nullSafeEquals(this.submitType, SUBMISSION_TYPE) && StringUtils.hasText(this.picFileFileName)) {
	    	String picUrl = distributeImageAO.uploadChannelImage(this.picFile, this.picFileFileName, getLoginInfo().getMemberId(), getLoginInfo().getNickname());
	    	if (StringUtils.hasText(picUrl)) {
	    		result.setImageUrl(StringUtils.startsWithIgnoreCase(picUrl, "/")?picUrl.substring(1):picUrl);
			}
		}else {
			result.setImageUrl(StringUtils.startsWithIgnoreCase(this.imageUrl, "/")?this.imageUrl.substring(1):this.imageUrl);
		}
	    result.setRecommendReason(StringUtils.hasText(this.recommendReason)?this.recommendReason:"");
	    result.setId(this.channelItemId!=null?this.channelItemId:null);
	    return result;
	}
	
	/**
	 * 返回客户端的IP地址
	 * 
	 * @return
	 */
	public  String ipAddr() {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}
	
	/**
	 * 处理批量
	 * 
	 * @return
	 */
	public List<DistribureChannelDO> populateCancelApplyParamList(){
		List<DistribureChannelDO> result = new ArrayList<DistribureChannelDO>();
		for (Long id : this.channelIdList) {
			DistribureChannelDO channelDO = new DistribureChannelDO();
			//状态是撤消
			channelDO.setStatus(new Short("3"));//[3:撤消状态]
			//记录ID
			channelDO.setId(id);
			channelDO.setOldStatus(new Short("0"));//[0:申请状态]
			result.add(channelDO);
		}
		return result;
	}
	
	/**
	 * 查询供应商条件
	 * 
	 * @param status
	 * 			供应商状态
	 * @return
	 */
	private Boolean populateDistribureChannelAndCheckStatus(Long distributorId,Short status) {
		//申请的分销商ID
		this.queryparam.setDistributorId(distributorId);
		//能否申请状态检查
		if (new Short("-2").equals(status)) {
			//关系状态[审核中或是合作中]
			this.queryparam.setStatus(status);
			if (distributorManager.findDistribureChannelByCondition(this.queryparam).getId() == null) {
			    /** 写入参数组装 */
			    this.param = new DistribureChannelDO();
			    this.param.setSupplierId(this.queryparam.getSupplierId());
			    this.param.setDistributorId(this.queryparam.getDistributorId());
			    this.param.setStatus(new Short("0"));
			    return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
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
	 * 手动输出json字符流
	 * 
	 * @return
	 */
	public String jsonResult(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("status", this.status);
			json.put("message", this.message);
			out.print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 验证上传图片文件的格式是否正确
	 * 
	 * @return
	 */
	public Boolean validatePicFileType() {
		String fileType = StringUtils.getFilenameExtension(this.picFileFileName);
		for (String type : FILE_TYPE_ARRAY) {
			if (type.equalsIgnoreCase(fileType)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 设置json返回信息
	 * 
	 * @param status
	 * 			状态
	 * @param message
	 * 			信息
	 */
	private void setJsonResult(Boolean status,String message){
		this.status = status;
		this.message = message;
	}
	
	/**
	 * 读取用户编号
	 */
	private void loadUserId() {
		this.memberId = getLoginInfo().getMemberId();
	}
	
	public Long getUserId() {
		return this.memberId;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public DistribureChannelDO getParam() {
		return param;
	}

	public void setParam(DistribureChannelDO param) {
		this.param = param;
	}

	public void setChannelIdList(List<Long> channelIdList) {
		this.channelIdList = channelIdList;
	}

	public DistribureChannelParamDO getQueryparam() {
		return queryparam;
	}

	public void setQueryparam(DistribureChannelParamDO queryparam) {
		this.queryparam = queryparam;
	}

	public void setMemberId(Long memberId) {
	    this.memberId = memberId;
	}

	public void setContent(String content) {
	    this.content = content;
	}

	public Boolean getNeedForward() {
	    return needForward;
	}

	public void setNickName(String nickName) {
	    this.nickName = nickName;
	}

	public Long getChannelItemId() {
		return channelItemId;
	}

	public void setChannelItemId(Long channelItemId) {
		this.channelItemId = channelItemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

	public void setPicFile(File picFile) {
		this.picFile = picFile;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setPicFileFileName(String picFileFileName) {
		this.picFileFileName = picFileFileName;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

}
