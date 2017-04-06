package com.yuwang.pinju.web.module.my.screen;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.FileSecurityUtils;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.cookie.PinjuCookieManager.LoginRelationCookie;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.MemberCheckAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

public class MySnsInfoAction extends MemberCheckAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(MySnsInfoAction.class);

	private final static String UPDATE = "update";
	
	private final static String ADD = "add";
	
	private MemberAO memberAO;
	private MemberSnsInfoDO ms = new MemberSnsInfoDO();
	private File avatar;
	private String avatarContentType;
	private String avatarFileName;
	private String returnUrl;
	private String type;

	public String execute() throws Exception {

		// 获取登录数据
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();

		// 未登录时跳至登录页面
		if(!login.isLogin()) {
			log.warn("current member not logged, member id: " + login);
			return LOGIN;
		}

		Result result = memberAO.getMemberSnsInfoByMemberId(login.getMemberId());
		if(MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("cannot find member information, member id: " + login.getMemberId());
			return LOGIN;
		}

		if(result.isSuccess()) {
			// 执行结果成功，获取数据对象
			ms = result.getModel(MemberKeyConstant.KEY_MEMBER_SNS_INFO, MemberSnsInfoDO.class);
			if(log.isDebugEnabled()) {
				log.debug("execute result success, find " + ms);
			}
		} else {
			// 执行结果错误，设置页面提示信息
			String message = ActionInvokeResult.setInvokeMessage(result.getResultCode());
			log.warn("execute result unsuccess, result code: " + result.getResultCode() + ", message on page is: " + message);
		}

		// 若还是为空，表示会员还没有社区信息
		if(ms == null) {
			ms = new MemberSnsInfoDO();
			setType(ADD);
			log.info("member sns info not exists, build an empty object, member id: " + login.getMemberId());
		} else {
			setType(UPDATE);
		}
        ms.setVersion(login.getInfoVersion());
		return SUCCESS;
	}

	public String update() throws Exception {

		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if(!login.isLogin()) {
			log.warn("current member not logged, member id: " + login);
			ServletUtil.loginReturnUrl(PinjuConstant.PINJU_SERVER + "/my/sns.htm");
			return LOGIN;
		}
		
		String method = ServletActionContext.getRequest().getMethod();
		if (REQUEST_METHOD_GET.equals(method)) {
			return INPUT;
		}
		
		if (!super.isSameMember(login)) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_MEMBER_NOT_MATCH, login.getNickname());
			return INPUT;
		}

		ActionInvokeResult air = new ActionInvokeResult(ms);
		if (!air.validate()) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBSER_SNS_INFO_BIOGRAPHY_LENGTH, 300);
			return INPUT;
		}

		ms.setMemberId(login.getMemberId());
        ms.setNickName(login.getNickname());
        
		if(avatar != null) {
			// 文件不能大于1M
			if (avatar.length() > 1024 * 1024) {
				ActionInvokeResult.setInvokeMessageKey(MessageName.FILE_ONE_M_LARGE);
				log.warn("image is more 1M, filename: " + avatarFileName + ", content-type: " + avatarContentType + ", file size: " + avatar.length());
				return INPUT;
			}
			
			if (!FileSecurityUtils.isImageValid(avatar)) {
				ActionInvokeResult.setInvokeMessageKey(MessageName.FILE_TYPE_INVALID);
				log.warn("image is invalid, filename: " + avatarFileName + ", content-type: " + avatarContentType + ", file size: " + avatar.length());
				return INPUT;
			}
			
			Result result = memberAO.saveAvatars(avatar, avatarFileName, login.getMemberId(), login.getNickname());
			String[] avatars = (String[])result.getModel("avatars");
			if(result.isSuccess() && (avatars != null) && (avatars.length > 0)) {
				log.info("fileupload original file: " + avatarFileName + ", upload to server filename: " + avatars[0]);
				ms.setAvatarsBasePath(avatars[0]);
			} else {
				ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_SNS_AVATOR_UPLOAD_ERROR);
				return INPUT;
			}
		} else {
			if (ADD.equals(type)) {
				air.addMessage("avatar", Message.getMessage(MessageName.MEMBER_SNS_AVATOR_UPLOAD_NULL));
				return INPUT;
			}
		}

		Result result = memberAO.saveOrUpdateMemberSnsInfo(ms);
		if(MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("cannot find member informationg, member id: " + login.getMemberId());
			ServletUtil.loginReturnUrl(PinjuConstant.PINJU_SERVER + "/my/sns.htm");
			return LOGIN;
		}

		processResult(login, result);

		if(!EmptyUtil.isBlank(returnUrl)) {
			log.info("need redirect to returnUrl after update success, return: [" + returnUrl + "]");
			return RETURN_URL;
		}

		return SUCCESS;
	}

	/**
	 * <p>处理更新、新增调用结果</p>
	 *
	 * @param result
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 下午05:16:16
	 */
	private void processResult(CookieLoginInfo login, Result result) {

		// 处理不成功
		if(!result.isSuccess()) {
			String message = ActionInvokeResult.setInvokeMessage(MessageName.OPERATE_FAILED);
			log.warn("execute result unsuccess, result code: " + result.getResultCode() + ", message on page is: " + message);
			return;
		}

		// 成功时获取调用结果
		ms = result.getModel(MemberKeyConstant.KEY_MEMBER_SNS_INFO, MemberSnsInfoDO.class);

		// 获取更新所影响的行数
		int updateCount = result.getModel(MemberKeyConstant.KEY_MEMBER_UPDATE_COUNT, int.class);
		log.info("execute result success, object is old data, update it, update count: " + updateCount);

		// 更新行数小于 1 时表示更新没有成功
		if(updateCount < 1) {
			ActionInvokeResult.setInvokeMessage(MessageName.OPERATE_FAILED);
			log.warn("execute update count error, result code: " + result.getResultCode() + ", update count: " + updateCount);
			return;
		}

		// 更新成功了
		ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_SUCCESS);

		Long infoVersion = result.getModel(MemberKeyConstant.KEY_MEMBER_INFO_VERSION, Long.class);

		if (infoVersion == null) {
			infoVersion = 0L;
		}

		ms.setVersion(infoVersion);
		LoginRelationCookie relation = new LoginRelationCookie(login);
		relation.setInfoVersion(infoVersion);
		PinjuCookieManager.writeLogin(relation);
	}

	public File getAvatar() {
		return avatar;
	}

	public void setAvatar(File avatar) {
		this.avatar = avatar;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public MemberSnsInfoDO getMs() {
		return ms;
	}

	public void setMs(MemberSnsInfoDO ms) {
		this.ms = ms;
	}

	public String getAvatarContentType() {
		return avatarContentType;
	}

	public void setAvatarContentType(String avatarContentType) {
		this.avatarContentType = avatarContentType;
	}

	public String getAvatarFileName() {
		return avatarFileName;
	}

	public void setAvatarFileName(String avatarFileName) {
		this.avatarFileName = avatarFileName;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
