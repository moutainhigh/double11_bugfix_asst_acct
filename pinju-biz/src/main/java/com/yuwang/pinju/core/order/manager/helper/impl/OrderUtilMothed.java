package com.yuwang.pinju.core.order.manager.helper.impl;

import java.util.Date;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.order.OrderItemDO;

/**
 * Created on 2011-9-19
 * 
 * @see <p>
 *      Discription:
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderUtilMothed {
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * Discription: 是否限时折扣
	 * </p>
	 * 
	 * @param bussinessType
	 * @return 是返回true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static boolean isDicountRate(Integer bussinessType) {
		return bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_3) == 0;
	}

	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * Discription: 是否团购或限时折扣
	 * </p>
	 * 
	 * @param bussinessType
	 * @return 是返回true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static boolean isGroupOrDicountRate(Integer bussinessType) {
		return bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_4) == 0
				|| bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_3) == 0;
	}

	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * Discription: 是否团购
	 * </p>
	 * 
	 * @param bussinessType
	 * @return 是返回true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static boolean isGroup(Integer bussinessType) {
		return bussinessType.compareTo(OrderItemDO.ORDER_ITEM_TYPE_4) == 0;
	}

	/**
	 * 
	 * Created on 2011-09-02
	 * <p>
	 * Discription: 比较当前时间是否在活动时间范围内
	 * </p>
	 * 
	 * @param startDate
	 *            ：活动开始时间 endDate：活动结束时间
	 * @return
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static boolean compareDate(Date startDate, Date endDate) {
		// 当前系统时间
		Date date = new Date();
		boolean flag = false;
		if (date.getTime() >= startDate.getTime()
				&& date.getTime() <= endDate.getTime()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * Created on 2011-8-31
	 * <p>
	 * Discription: 子订单打标记专用
	 * </p>
	 * 
	 * @param orderItemAttributes
	 * @param key
	 * @param value
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static String markingAttributes(String attributesString, String key,
			String value) {
		if (StringUtil.isNotEmpty(attributesString)&&StringUtil.lastIndexOf(attributesString, OrderConstant.SPLITKEY)!= (attributesString.length()-1)) {
			attributesString = attributesString
					.concat(OrderConstant.SPLITKEY);
		}
		return attributesString.concat(key).concat(VouchPayConstant.SPLITATTRIBUTES).concat(value).concat(VouchPayConstant.SPLITKEY);
	}

	/**
	 * 
	 * Created on 2011-8-23
	 * <p>
	 * Discription: 封装返回信息
	 * </p>
	 * 
	 * @param flag
	 * @param result
	 * @param resultCode
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static Result setResult(boolean flag, Result result, String resultCode) {
		result.setSuccess(flag);
		result.setResultCode(resultCode);
		return result;
	}
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription:</p>
	 * @param value
	 * @return 为null或0返回true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static boolean longIsNull(Long value) {
		if (value != null && value.compareTo(0L) > 0)
			return false;
		return true;
	}
	
	
}
