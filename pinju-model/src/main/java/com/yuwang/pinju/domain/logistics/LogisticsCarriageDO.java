package com.yuwang.pinju.domain.logistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogisticsCarriageDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物流方式--平邮
     */
    public static final Integer LOGISTICS_TYPE_POST = 1;
    
    /**
     * 物流方式--快递
     */
    public static final Integer LOGISTICS_TYPE_COURIER = 2;
    
    /**
     * 物流方式--EMS
     */
    public static final Integer LOGISTICS_TYPE_EMS = 3;
    
    /**
     * id
     */
    private Long id;

    /**
     * 物流方式id
1：平邮
2：快递
3：EMS
     */
    private Integer logisticsTypeId;

    /**
     * 物流方式名称
     */
    private String logisticsTypeName;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 默认运费
     */
    private Long defaultCarriage;

    /**
     * 增加商品运费
     */
    private Long carriageIncrease;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;

    /**
     * 记录修改时间
     */
    private Date gmtModified;


    public Long getId(){
        return id;
    }

    public Integer getLogisticsTypeId(){
        return logisticsTypeId;
    }

    public String getLogisticsTypeName(){
        return logisticsTypeName;
    }

    public Long getTemplateId(){
        return templateId;
    }

    public Long getDefaultCarriage(){
        return defaultCarriage;
    }

    public Long getCarriageIncrease(){
        return carriageIncrease;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setLogisticsTypeId(Integer logisticsTypeId){
        this.logisticsTypeId = logisticsTypeId;
    }

    public void setLogisticsTypeName(String logisticsTypeName){
        this.logisticsTypeName = logisticsTypeName;
    }

    public void setTemplateId(Long templateId){
        this.templateId = templateId;
    }

    public void setDefaultCarriage(Long defaultCarriage){
        this.defaultCarriage = defaultCarriage;
    }

    public void setCarriageIncrease(Long carriageIncrease){
        this.carriageIncrease = carriageIncrease;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }
    
    private static Map<Integer, String> logisticsTypeMap = new HashMap<Integer, String>();
	
	static{
		logisticsTypeMap.put(LOGISTICS_TYPE_POST,    "平邮");
		logisticsTypeMap.put(LOGISTICS_TYPE_COURIER, "快递");
		logisticsTypeMap.put(LOGISTICS_TYPE_EMS,     "EMS");
	}
	
	/**
	 * <p>取得物流方式显示文字信息</p>
	 * 
	 * @return 物流方式显示文字信息
	 *
	 * @author shihongbo
	 */
	public String getLogisticsTypeDisplay(){
		return logisticsTypeMap.get(logisticsTypeId);
	}
	
	/**
	 * <p>取得物流方式显示文字信息</p>
	 * 
	 * @return 物流方式显示文字信息
	 *
	 * @author shihongbo
	 */
	public static String getLogisticsTypeDisplay(Integer typeid){
		return logisticsTypeMap.get(typeid);
	}
}

