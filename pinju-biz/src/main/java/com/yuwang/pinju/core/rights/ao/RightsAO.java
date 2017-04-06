package com.yuwang.pinju.core.rights.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsMsgQuery;
import com.yuwang.pinju.domain.rights.RightsQuery;

/**  
 * @Project: pinju-biz
 * @Description: 消保维权AO
 * @author 石兴 shixing@zba.com
 * @date 2011-6-29 上午10:36:52
 * @update 2011-6-29 上午10:36:52
 * @version V1.0  
 */
public interface RightsAO {
	/**
     * Created on 2011-6-29
     * <p>Discription:[插入维权记录]</p>
     * @param RightsDO
     * @return
     * @author:[石兴]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
	Result insertRightsRecord(RightsDO rightsDO);

	/**
	 * Created on 2011-7-4
	 * <p>Discription:[查询维权留言记录列表]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result getRightsMsgDOs(RightsMsgQuery rightsMsgQuery);
	
	/**
	 * Created on 2011-6-29
	 * <p>Discription:[查询维权记录列表]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result getRightsDOs(RightsQuery rightsQuery) ;
	
	/**
	 * Created on 2011-6-29
	 * <p>Discription:[查询维权记录]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result getRightsDO(RightsDO rightsDO) ;
	
	/**
	 * Created on 2011-6-29
	 * <p>Discription:[更新维权记录]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result updateRightsRecord(RightsDO rightsDO) ;
	
	/**
	 * Created on 2011-6-30
	 * <p>Discription:[Discription]</p>
	 * @param rightsMessageDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result sendMessage(RightsMessageDO rightsMessageDO);
	
	/**
	 * Create on: 2011-10-9下午05:43:20
	 * <p>Discription:[根据维权DO中的订单ID和子订单ID查询订单DO，子订单DO，支付订单DO和物流DO]</p>
	 * @param: RightsDO
	 * @return: Result 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result getOrderDOAndOrderItemDOAndVouchPayDOAndOrderLogisticsDOById(RightsDO rightsDO);
}
