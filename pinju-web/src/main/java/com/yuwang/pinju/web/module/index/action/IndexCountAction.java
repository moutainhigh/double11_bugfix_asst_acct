package com.yuwang.pinju.web.module.index.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.core.index.ao.IndexCountAO;
import com.yuwang.pinju.web.module.BaseAction;

public class IndexCountAction extends BaseAction {

	private IndexCountAO indexCountAO;
	private Long visitCount = 0L;
	private String callback;
	protected HttpServletResponse response;

	public IndexCountAO getIndexCountAO() {
		return indexCountAO;
	}

	public void setIndexCountAO(IndexCountAO indexCountAO) {
		this.indexCountAO = indexCountAO;
	}

	public Long getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Long visitCount) {
		this.visitCount = visitCount;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * 获取httpServletResponse对象
	 * 
	 * @author cary
	 * @return HttpServertRequest
	 * @date 2011-6-8 上午9:20:05
	 */
	protected HttpServletResponse getResponse() {
		HttpServletResponse response = null;
		if (this.response != null) {
			response = this.response;
		}
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		return response;
	}

	public String getCount() {

		StringBuilder sb = new StringBuilder();
		visitCount = indexCountAO.queryIndexVisitCount();
		sb.append(this.getCallback() + "(");
		sb.append("{");
		sb.append("visit_count:");
		sb.append(visitCount);
		sb.append(",");
		sb.append("timestamp:");
		sb.append(new Date().getTime());
		sb.append("}");
		sb.append(")");
		try {
			outPutStream(getResponse(), sb.toString());
		} catch (IOException e) {
			log.error("IO异常", e);
		}
		return null;
	}

	protected void outPutStream(HttpServletResponse reps, String str) throws IOException {
		reps.getOutputStream().write(str.toString().getBytes("UTF-8"));
	}
}
