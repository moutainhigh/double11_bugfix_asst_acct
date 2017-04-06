/**
 * 
 */
package com.yuwang.pinju.web.module.distribute.screen;

import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
import com.yuwang.pinju.core.distribute.ao.DistributeGoodsManagerAO;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;

/**
 * @author xiazhenyu
 * 
 */
public class SupplierReleaseAction extends SupplierApplyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10599924120483921L;
	
	private DistributeGoodsManagerAO distributeGoodsManagerAO;

	/**
	 * 取得招募书
	 * 
	 * @return
	 */
	public String getRelease() {
		if (!checkIsSupplier(getUserId())) {
			return NOT_APPLY;
		}
		// 未设置分销商品不能发布招募书
		if (0 == distributeGoodsManagerAO.getDistributeGoodsCount(distributeSupplierDO.getId())) {
			setErrorMessage("请先设置分销商品。");
		}else {
		    setStatus(true);
		    setErrorMessage(null);
		}
		return SUCCESS;
	}

	/**
	 * 发布招募书
	 * 
	 * @return
	 */
	public String release() {
	    setStatus(true);
    	    if(distributeSupplierDO.getApplyContent().trim().length() < 4000 && !WordFilterFacade.scan(distributeSupplierDO.getApplyTitle(), SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON) && !WordFilterFacade.scan(distributeSupplierDO.getApplyContent(), SensitiveWordConstants.SENSITIVE_WORD_TYPE_COMMON, true)){
    		if (null == distributeSupplierAO.releaseSupplier(distributeSupplierDO)) {
    		    	setErrorMessage("招募书发布失败");
    			return ERROR;
    		}
    	    }else {
    	    	setErrorMessage("招募书最大不超过4000个字符并且标题和内容不能含有敏感字符");
    		return ERROR;
    	    }
	    setErrorMessage(null);
    	    setSuccessMessage("招募书发布成功");
    	    return SUCCESS;
	}

	public void setDistributeGoodsManagerAO(DistributeGoodsManagerAO distributeGoodsManagerAO) {
		this.distributeGoodsManagerAO = distributeGoodsManagerAO;
	}

}
