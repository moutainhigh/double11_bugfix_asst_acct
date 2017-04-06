package com.yuwang.pinju.domain.sales.query;

import java.util.List;

import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;

/**  
 * @Project: crm-model
 * @Discription: [限购码领用记录Query]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-30 下午05:49:41
 * @update 2011-8-30 下午05:49:41
 * @version V1.0  
 */
public class ItemXianGouUseQuery extends Paginator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7024502416100163086L;
	
	private Long code;
	private String buyerNick;
	private String sellerNick;
	private Long orderId;
	private String itemTitle;
	private Integer isUse;
	
	private ItemXianGouUseDO itemXianGouUseDO;
	
	private List<ItemXianGouUseDO> itemXianGouUseDOs;

	public void setItemXianGouUseDO(ItemXianGouUseDO itemXianGouUseDO){
		this.itemXianGouUseDO = itemXianGouUseDO;
	}

	public ItemXianGouUseDO getItemXianGouUseDO(){
		return itemXianGouUseDO;
	}

	public void setItemXianGouUseDOs(List<ItemXianGouUseDO> itemXianGouUseDOs){
		this.itemXianGouUseDOs = itemXianGouUseDOs;
	}

	public List<ItemXianGouUseDO> getItemXianGouUseDOs(){
		return itemXianGouUseDOs;
	}

	public void setBuyerNick(String buyerNick){
		this.buyerNick = buyerNick;
	}

	public String getBuyerNick(){
		return buyerNick;
	}

	public Long getCode(){
		return code;
	}

	public void setCode(Long code){
		this.code = code;
	}

	public String getSellerNick(){
		return sellerNick;
	}

	public void setSellerNick(String sellerNick){
		this.sellerNick = sellerNick;
	}

	public Long getOrderId(){
		return orderId;
	}

	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}

	public Integer getIsUse(){
		return isUse;
	}

	public void setIsUse(Integer isUse){
		this.isUse = isUse;
	}

	public void setItemTitle(String itemTitle){
		this.itemTitle = itemTitle;
	}

	public String getItemTitle(){
		return itemTitle;
	}
	
}
