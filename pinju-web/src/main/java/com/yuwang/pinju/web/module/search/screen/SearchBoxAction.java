package com.yuwang.pinju.web.module.search.screen;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.yuwang.pinju.core.httpclient.SolrService;
import com.yuwang.pinju.web.module.BaseAction;

@SuppressWarnings("serial")
public class SearchBoxAction extends BaseAction {

	private String q;
	private SolrService solrService;
	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}

	public String itemSearchBox() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getWriter().print("jsoncallback(" +solrService.itemSearchBox(q)+")");
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	public String shopSearchBox() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getWriter().print("jsoncallback(" +solrService.shopSearchBox(q)+")");
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}


}
