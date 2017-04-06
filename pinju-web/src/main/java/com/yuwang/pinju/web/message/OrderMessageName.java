package com.yuwang.pinju.web.message;

/**
 * Created on 2011-7-20
 * <p>
 * Discription:
 * </p>
 * 
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderMessageName {
	/**
	 * 错误页面默认的消息
	 */
	String ERROR_DEFAULT_MESSAGE = "error.default.message";
	/**
	 * 无效的操作
	 */
	String OPERATE_INVALID = "operate.invalid";
	/**
	 * 操作失败
	 */
	String OPERATE_FAILED = "operate.failed";
	/**
	 * 操作成功
	 */
	String OPERATE_SUCCESS = "operate.success";
	/**
	 * 更新成功
	 */
	String OPERATE_UPDATE_SUCCESS = "operate.update.success";
	/**
	 * 新增成功
	 */
	String OPERATE_INSERT_SUCCESS = "operate.insert.success";

	/**
	 * 删除成功
	 */
	String OPERATE_REMOVE_SUCCESS = "operate.delete.success";
	/**
	 * 订单昵称出错
	 */
	String ORDER_CHECK_NICKNAME_ERROR = "order.check.nickName.error";
	/**
	 * 订单商品所属店铺错误
	 */
	String ORDER_CHECK_SHOP_ERROR = "order.check.shop.error";
	/**
	 * 买家账户错误
	 */
	String ORDER_CHECK_BUYERMEMBER_ERROR = "order.check.buyerMember.error";
	/**
	 * 卖家账户错误
	 */
	String ORDER_CHECK_SELLERMEMBER_ERROR = "order.check.sellerMember.error";
	/**
	 * 商品库存与购买数量不符
	 */
	String ORDER_CHECK_ITEMNUM_ERROR = "order.check.itemNum.error";
	/**
	 * 商品有效性错误
	 */
	String ORDER_CHECK_ITEM_ERROR = "order.check.item.error";
	/**
	 * 商品状态错误
	 */
	String ORDER_CHECK_ITEMSTATE_ERROR = "order.check.itemState.error";
	/**
	 * sku错误
	 */
	String ORDER_CHECK_SKU_ERROR = "order.check.sku.error";
	/**
	 * sku与对应的商品对象关系错误
	 */
	String ORDER_CHECK_SKUTOITEM_ERROR = "order.check.skuToItem.error";

}
