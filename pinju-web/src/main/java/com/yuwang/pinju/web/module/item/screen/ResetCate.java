/**
 * 
 */
package com.yuwang.pinju.web.module.item.screen;

import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.web.module.BaseAction;

/**  
 * @Project: pinju-web
 * @Title: ResetCate.java
 * @Package com.yuwang.pinju.web.module.item.screen
 * @Description: 重置本地类目信息(线上用不用的到,还得看情况)
 * @author liuboen liuboen@zba.com
 * @date 2011-7-21 上午11:04:40
 * @version V1.0  
 */

public class ResetCate extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5508450262734847970L;
	
	private ItemDetailAO itemDetailAO;

	public String execute(){
		//boolean isSucess=itemDetailAO.resetFullCategory();
		boolean isSucess=true;
		if (isSucess) {
			request.setAttribute("message","上线不开通该接口");
			return SUCCESS;
		}else {
			request.setAttribute("message","重置失败");
			return SUCCESS;
		}
	}
	/**
	 * @param itemDetailAO the itemDetailAO to set
	 */
	public void setItemDetailAO(ItemDetailAO itemDetailAO) {
		this.itemDetailAO = itemDetailAO;
	}
	
	
}
