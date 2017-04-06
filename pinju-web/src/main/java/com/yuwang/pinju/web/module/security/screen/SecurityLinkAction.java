package com.yuwang.pinju.web.module.security.screen;

import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_SECURITY_LINK_NOT_FOUND;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_PARAMETERS_ERROR;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO;
import com.yuwang.pinju.domain.member.security.SecurityLinkDO;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>Email 安全链接</p>
 *
 * @author gaobaowen
 * @since 2011-9-5 09:38:23
 */
public class SecurityLinkAction implements PinjuAction, ModelDriven<SecurityLinkDO> {

	/**
	 * 安全链接获取 MemberSecurityEmailLink 对象 chain Struts 2 result type 参数名
	 */
	public final static String EMAIL_LINK_OBJECT_PARAM_NAME = "link";

	private final static Log log = LogFactory.getLog(SecurityLinkAction.class);

	private MemberSecurityAO memberSecurityAO;

	private SecurityLinkDO link;

	public SecurityLinkAction() {
		link = new SecurityLinkDO();
	}

	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	@Override
	public String execute() throws Exception {
		log.debug("execute...");
		link.setIp(ServletUtil.getRemoteIp());
		Result result = memberSecurityAO.confirmLink(link);
		if (log.isInfoEnabled()) {
			log.info("execute action, link: " + link.getParam() + ", ao invoke result: " + result.getResultCode());
		}

		ServletUtil.forbidBrowserCache();

		if (result.isSuccess()) {
			MemberSecurityEmailLinkDO ek = result.getModel(MemberSecurityEmailLinkDO.class);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(EMAIL_LINK_OBJECT_PARAM_NAME, ek);
			ActionContext.getContext().setParameters(parameters);

			if (log.isDebugEnabled()) {
				log.debug("execute finished, return to SUCCESS_" + ek.getLinkType());
			}

			return SUCCESS + "_" + ek.getLinkType();
		}

		// 参数错误
		if (RESULT_PARAMETERS_ERROR.equals(result.getResultCode())) {
			return ERROR;
		}

		// 链接不存在，或者已经过期，或者已确认过
		if (RESULT_MEMBER_SECURITY_LINK_NOT_FOUND.equals(result.getResultCode())) {
			return ERROR;
		}

		return ERROR;
	}

	@Override
	public SecurityLinkDO getModel() {
		return link;
	}
}
