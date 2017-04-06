package com.yuwang.pinju.domain.sales.query;  

import com.yuwang.pinju.domain.Paginator;
  
public class ItemXianGouQuery extends Paginator
{

    /**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = 1L;
    
    private int batchNum;

    public int getBatchNum()
    {
        return batchNum;
    }

    public void setBatchNum(int batchNum)
    {
        this.batchNum = batchNum;
    }
    
    
}
