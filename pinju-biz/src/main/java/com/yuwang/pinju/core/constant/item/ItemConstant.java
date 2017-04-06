package com.yuwang.pinju.core.constant.item;

/**
 * 
 * 商品相关常量
 * 
 * @author liming
 * 
 */
public class ItemConstant {

	/**
	 * 一口价商品
	 */
	public static final long ITEM_TYPE_1 = 1;

	/**
	 * 商品类型 全新
	 */
	
	public static final int DEGREE_TYPE_1 = 1;

	/**
	 * 商品类型 二手
	 */
	public static final int DEGREE_TYPE_2 = 2;
	
	/**
	 * 商品类型 个人闲置
	 */
	public static final int DEGREE_TYPE_3 = 3;

	/**
	 * 运费类型 卖家承担
	 */
	public static final int FREIGHT_TYPE_1 = 1;

	/**
	 * 运费类型 买家承担
	 */
	public static final int FREIGHT_TYPE_2 = 2;
	
	/**
	 * 运费类型 运费模板
	 */
	public static final int BUY_FREIGHT_TYPE_1 = 1;
	
	/**
	 * 运费类型 固定运费
	 */
	public static final int BUY_FREIGHT_TYPE_2 = 2;

	/**
	 * 默认的平邮价格
	 */
	public static final int DEFAULT_SURFACE_CHARGES = 0;

	/**
	 * 默认的快递价格
	 */
	public static final int DEFAULT_DELIVERY_COSTS = 0;

	/**
	 * 默认的EMS价格
	 */
	public static final int DEFAULT_EMS_COSTS = 0;

	/**
	 * 有效期1
	 */
	public static final int EXPIRY_TYPE_1 = 7;

	/**
	 * 有效期2
	 */
	public static final int EXPIRY_TYPE_2 = 14;

	/**
	 * 有效期3
	 */
	public static final int EXPIRY_TYPE_3 = 21;

	/**
	 * 发布类型 立即
	 */
	public static final int RELEASED_TYPE_1 = 1;

	/**
	 * 发布类型 定时
	 */
	public static final int RELEASED_TYPE_2 = 2;

	/**
	 * 发布类型 仓库
	 */
	public static final int RELEASED_TYPE_3 = 3;

	/**
	 * 商品状态 用户上架
	 */
	public static final long STATUS_TYPE_0 = 0;

	/**
	 * 商品状态 运营上架
	 */
	public static final long STATUS_TYPE_1 = 1;

	
	/**
	 * 商品状态 用户下架     
	 */
	public static final long STATUS_TYPE_2 = -1;

	/**
	 * 商品状态 用户删除
	 */
	public static final long STATUS_TYPE_3 = -2;

	/**
	 * 商品状态 运营下架
	 */
	public static final long STATUS_TYPE_4 = -3;

	/**
	 * 商品状态 运营删除
	 */
	public static final long STATUS_TYPE_5 = -4;

	/**
	 * 商品状态 从未上架
	 */
	public static final long STATUS_TYPE_6 = -5;
	
	/**
	 * 商品状态 售完下架
	 */
	public static final long STATUS_TYPE_7 = -6;
	
	/**
	 * 商品状态 到期下架
	 */
	public static final long STATUS_TYPE_8 = -7;
	
	/**
	 * 商品状态 冻结
	 */
	public static final long STATUS_TYPE_9 = -9;

	/**
	 * 商品状态 OPENAPI导入
	 */
	public static final long STATUS_TYPE_10 = 2;
	
	/**
	 * 是叶子节点
	 */
	public static final long IS_LEAF_1 = 1;

	/**
	 * 不是叶子节点
	 */
	public static final long IS_LEAF_0 = 0;
	
	/**
	 * 类目状态删除
	 */
	public static final long CATEGORY_STATUS_0 = 0;
	
	/**
	 * 类目状态正常
	 */
	public static final long CATEGORY_STATUS_1 = 1;
	
	/**
	 * 类目状态冻结
	 */
	public static final long CATEGORY_STATUS_2 = 2;
	
}
