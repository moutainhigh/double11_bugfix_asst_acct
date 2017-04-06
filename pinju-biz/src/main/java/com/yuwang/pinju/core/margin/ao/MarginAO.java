package com.yuwang.pinju.core.margin.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.margin.query.MarginSellerOrderQuery;
import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;

/**  
 * @Project: pinju-biz
 * @Description: 保证金AO
 * @author 石兴 shixing@zba.com
 * @date 2011-8-2 上午09:42:30
 * @update 2011-8-2 上午09:42:30
 * @version V1.0  
 */
public interface MarginAO {

	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[查询卖家保证金信息]</p>
	 * @param sellerId 卖家ID，即会员ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result queryMarginRecorde(Long sellerId);
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[查询卖家保证金余额]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result queryMarginBalance(Long sellerId);
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[缴纳保证金]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result payMargin(Long userId) ;
	
	/**
	 * Created on 2011-8-19 
	 * <p>Discription:[从后台缴纳保证金]</p>
	 * @param memberId price
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result backPayMargin(long memberId,long price);
	
	/**
	 * Created on 2011-8-12 
	 * <p>Discription:[盛付通回调接口]</p>
	 * @param payState支付状态
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result receiveMargin(DirectPayReceiveParamDO payReceiveParamDO,boolean payState);
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[解冻保证金]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result unfreezeMargin(Long sellerId) ;
	
	/**
	 * Create on: 2011-8-13下午01:32:29
	 * <p>Discription:[查询卖家保证金交易流水]</p>
	 * @param: MarginSellerOrderQuery
	 * @return: Result 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result queryMarginSellerOrderDOs(MarginSellerOrderQuery marginSellerOrderQuery);

	/**
	 * Created on 2011-9-20 
	 * @desc <p>Discription:[通过外部交易号获取卖家ID]</p>
	 * @param 
	 * @return Result
	 * @author:[石兴]
	 * @update:[2011-9-20] [更改人姓名]
	 */
	public Result getMarginBackUrlByPayOrderId(Long payOrderId);
	
	/**
	 * Created on 2011-10-17 
	 * @desc <p>Discription:[通知店铺更新店铺状态为开店成功,异常直接Catch以保证店铺状态的更新不会影响保证金业务的处理]</p>
	 * @param 
	 * @return void
	 * @author:[石兴]
	 * @update:[2011-10-17] [更改人姓名]
	 */
	public void notifyShopUpdate(Long userId);

}
