package com.yuwang.pinju.domain.logistics;

import com.yuwang.pinju.domain.Paginator;

public class LogisticsTemplateQuery extends Paginator {
	private static final long serialVersionUID = -328403312189591296L;
	
    /**
     * 模板id
     */
    private Long id;
    
	/**
     * 卖家id
     */
    private Long sellerId;
	
    /**
     * 模板名称
     */
    private String templateName;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getSellerId(){
        return sellerId;
    }
    
    public void setSellerId(Long sellerId){
        this.sellerId = sellerId;
    }
    
    public String getTemplateName(){
        return templateName;
    }
    
    public void setTemplateName(String templateName){
        this.templateName = templateName;
    }
    
}
