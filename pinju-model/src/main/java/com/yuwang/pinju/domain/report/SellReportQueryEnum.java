package com.yuwang.pinju.domain.report;

/**
 * 
 * Created on 2011-8-24
 * @see <p>
 * Discription: 根据条件获取销售统计记录
 *</p>
 * @author:[MaYuanChao]
 * @version 1.0
 */

public enum SellReportQueryEnum {
	DEFAULT7DAY  		("default7day",-7, 0), 
	YESTERDAY    		("yesterday",-1, 1), 
	NEARLY3DYA   		("NearlythrDays",-3, 2),
    NEARLY1WEEK  		("NearlyOneWeek",-1, 3),
    NEARLY1MONTH 		("NearlyOneMonth",-1, 4),
    NEARLY3MONTH   		("NearlyThrMonth",-3, 5);
	
	private String name;
	private int amount;
	private long value;
	
	SellReportQueryEnum(String name, int amount,int value) {
		this.name=name;
		this.amount=amount;
		this.value=value;
	}

	  /**
     * Get the enum with the code.
     *
     * @param value
     * @return SellReportQueryEnum
     */
    public static SellReportQueryEnum getType(Long value) {
        switch (value.intValue()) {
        	case 0 :
        		return DEFAULT7DAY;
            case 1 :
                return YESTERDAY;
            case 2 :
                return NEARLY3DYA;
            case 3 :
            	return NEARLY1WEEK;
            case 4 :
            	return NEARLY1MONTH;
            case 5 :
            	return NEARLY3MONTH;
            default :
                return DEFAULT7DAY;
        }
    }
    
    /**
     * Get the enum by the name of the enum.
     *
     * @param name
     * @return SellReportQueryEnum
     */
    public static SellReportQueryEnum getType(String name) {
        for (SellReportQueryEnum type : SellReportQueryEnum.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return DEFAULT7DAY;
    }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}

 enum DateFormatEnum{
	FORMAT_1("yyyyMMdd",0),
	FORMAT_2("yyyy-MM-dd",1),
	FORMAT_3("yyyyMMdd hh:mm:ss",2),
	FORMAT_4("yyyy-MM-dd hh:mm:ss",3);
	
	private String pattern;
	private long vaule;
	DateFormatEnum(String pattern,long vaule){
		this.pattern=pattern;
		this.vaule=vaule;
	}
	
	  /**
     * Get the enum with the code.
     *
     * @param value
     * @return DateFormatEnum
     */
    public static DateFormatEnum getType(Long value) {
        switch (value.intValue()) {
        	case 0 :
        		return FORMAT_1;
            case 1 :
                return FORMAT_2;
            case 2 :
                return FORMAT_3;
            case 3 :
            	return FORMAT_4;
            default :
                return FORMAT_3;
        }
    }
    
    /**
     * Get the enum by the name of the enum.
     *
     * @param name
     * @return DateFormatEnum
     */
    public static DateFormatEnum getType(String name) {
        for (DateFormatEnum type : DateFormatEnum.values()) {
            if (type.pattern.equals(name)) {
                return type;
            }
        }
        return FORMAT_3;
    }
    
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public long getVaule() {
		return vaule;
	}
	public void setVaule(long vaule) {
		this.vaule = vaule;
	}
}
