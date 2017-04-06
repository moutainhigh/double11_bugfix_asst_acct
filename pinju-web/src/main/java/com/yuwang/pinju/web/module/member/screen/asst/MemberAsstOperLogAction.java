package com.yuwang.pinju.web.module.member.screen.asst;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionTreeVO;
import com.yuwang.pinju.web.annotatioin.MasterAccount;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

public class MemberAsstOperLogAction extends BaseAction implements PinjuAction {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(MemberAsstOperLogAction.class);

	private MemberAsstOperLogQuery query;
	private List<MemberAsstOperLogDO> memberAsstOperLogList;
	private List<MemberAsstPermissionTreeVO> treePerVoList;
	private String first;

	private MemberAsstAO memberAsstAO;

	public void setMemberAsstAO(MemberAsstAO memberAsstAO) {
		this.memberAsstAO = memberAsstAO;
	}

	public MemberAsstOperLogAction() {
		query = new MemberAsstOperLogQuery(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	@MasterAccount
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			return INPUT;
		}

		long masterMemberId = login.getMemberId();
		long asstMemberId = MemberIdPuzzle.decode(query.getInputAsstMemberId());
		if (!query.validateParam(asstMemberId, first)) {
			return PAGE_500;
		}

        query.setAsstMemberId(asstMemberId);
		query.setMasterMemberId(masterMemberId);
		query.setItemsPerPage(10);
		Result result = memberAsstAO.findMemberAsstOperLog(query);
		if (!result.isSuccess()) {
			return PAGE_500;
		}
		memberAsstOperLogList = result.getModel(ArrayList.class);
		treePerVoList = (List<MemberAsstPermissionTreeVO>)result.getModel("TREE_PER_LIST");
		return SUCCESS;
	}

	public MemberAsstOperLogQuery getQuery() {
		return query;
	}

	public void setQuery(MemberAsstOperLogQuery query) {
		this.query = query;
	}

	public List<MemberAsstOperLogDO> getMemberAsstOperLogList() {
		return memberAsstOperLogList;
	}

	public void setMemberAsstOperLogList(
			List<MemberAsstOperLogDO> memberAsstOperLogList) {
		this.memberAsstOperLogList = memberAsstOperLogList;
	}

	public List<MemberAsstPermissionTreeVO> getTreePerVoList() {
		return treePerVoList;
	}

	public void setTreePerVoList(List<MemberAsstPermissionTreeVO> treePerVoList) {
		this.treePerVoList = treePerVoList;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}
}
