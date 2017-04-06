package com.yuwang.pinju.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺后台所有的常量
 * 
 * @author mike
 *
 * @since 2011-6-10
 */
public class ShopConstant {
	
	/*** ----------------店铺类型--------------------------- **/
	/**
	 * 店铺类型：品牌店
	 */
	public static  final Integer SELLER_TYPE_B=1;
	
	/**
	 * 店铺类型：普通店
	 */
	public static  final Integer SELLER_TYPE_C=0;
	
	/**
	 * 店铺类型 旗舰店
	 */
	public static final Integer SELLER_TYPE_B2 = 2;
	
	/**
	 * 店铺类型 i小铺--2.0新
	 */
	public static final Integer SELLER_TYPE_IShop = 3;
	
	
	
	/*** ----------------店铺流程审核状态--------------------------- **/
	
	/**
	 * 未审核
	 */
	public static  final Integer AUDIT_STATUS_WAIT=0;
	
	/**
	 * 已经审核通过
	 */
	public static  final Integer AUDIT_STATUS_PASS=1;
	
	/**
	 * 审核未通过
	 */
	public static  final Integer AUDIT_STATUS_NO=-1;
	
	/**
	 * 未提交申请
	 */
	public static  final Integer AUDIT_STATUS_NOT_APPLY= -100;
	
	/**
	 * 开店成功
	 */
	public static  final Integer AUDIT_STATUS_OPEN_END=2;
	
	
	/*** ----------------店铺是否已经拉黑名单-------------------------- **/
	
	/**
	 * 是黑名单
	 */
	public static  final Integer BLACK_TRUE=1;
	
	/**
	 * 不是黑名单
	 */
	public static  final Integer BLACK_FALSE=0;
	
	/*** ----------------店铺是否已经确认了服务协议-------------------------- **/
	
	/**
	 * 已确认服务协议
	 */
	public static final Integer IS_AGREEMENT_TRUE = 1;
	
	/**
	 * 未确认服务协议
	 */
	public static final Integer IS_AGREEMENT_FALSE = 0;
	
	/**
	 * 未填写店铺信息
	 */
	public static final Integer IS_FILL_SHOP_INFO_NO = 0;
	
	/**
	 * 已经填写店铺信息步骤1
	 */
	public static final Integer IS_FILL_SHOP_INFO_STEP1 = 1;
	
	/**
	 * 已经填写店铺信息步骤2
	 */
	public static final Integer IS_FILL_SHOP_INFO_STEP2 = 2;
	
	/**
	 * 已经填写店铺信息步骤3
	 */
	public static final Integer IS_FILL_SHOP_INFO_STEP3 = 3;
	
	/**
	 * 已经填写店铺信息步骤4
	 */
	public static final Integer IS_FILL_SHOP_INFO_STEP4 = 4;
	
	/**
	 * 已经填写店铺信息
	 */
	public static final Integer IS_FILL_SHOP_INFO_STEP5 = 5;
	
	public static final String []PREV_STEP = {"","FILL_SHOP_INFO_B_SETP1","FILL_SHOP_INFO_B_SETP2","FILL_SHOP_INFO_B_SETP3","FILL_SHOP_INFO_B_SETP4"};
	
	/**
	 * ka
	 */
	public static final Integer IS_KA_NO = 0;
	
	
	/**
	 * 不是ka
	 */
	public static final Integer IS_KA_YES = 1;
	
	/**
	 * 未开店
	 */
	
	public static final int APPROVE_STATUS_NO = 0;
	
	/**
	 * 已开店
	 */
	public static final int APPROVE_STATUS_YES = 1;
	
	/**
	 * 闭店
	 */
	public static final int APPROVE_STATUS_CLOSE = -1;
	
	/**
	 * 合规屏蔽
	 */
	public static final int APPROVE_STATUS_HEGUI = -2;
	
	/**
	 * shopid的起始值
	 */
	public static int SHOP_ID_BEGIN_NUMBER = 10000;
	
	public final static String SHOP_NEWLINE = "\r\n";
	
	public final static String SHOP_DEFAULT_CATEGORYNAME="未分类";
	
	public final static String SHOP_CATEGORY_SPLIT = "@!@";
	
	public final static String SHOP_VALUE_SPLIT = ",";
	/**
	 * 店铺类目
	 */
//	public static  List<String> SHOP_CATEGORY_LIST = new ArrayList<String>();
//	static{
//		SHOP_CATEGORY_LIST.add("服装类");
//		SHOP_CATEGORY_LIST.add("配饰类");
//		SHOP_CATEGORY_LIST.add("美容化妆类");
//		SHOP_CATEGORY_LIST.add("3C数码类");
//		SHOP_CATEGORY_LIST.add("家电类");
//		SHOP_CATEGORY_LIST.add("家居产品类");
//		SHOP_CATEGORY_LIST.add("母婴用品类");
//		SHOP_CATEGORY_LIST.add("文体用品类");
//		SHOP_CATEGORY_LIST.add("食品、保健类");
//		SHOP_CATEGORY_LIST.add("虚拟产品类");
//		SHOP_CATEGORY_LIST.add("服务产品类");
//	}
	/**
	 * C店性质 个人全职、个人兼职、有实体店铺、公司开店
	 */
	public static  List<String> SHOP_NATURE_LIST_C = new ArrayList<String>();
	static{
		SHOP_NATURE_LIST_C.add("有实体店铺");
		SHOP_NATURE_LIST_C.add("无实体店铺");
	}
	
	/**
	 * B店性质  品牌旗舰店 品牌专营店 品牌经销店
	 */
	public static  List<String> SHOP_NATURE_LIST_B = new ArrayList<String>();
	static{
		SHOP_NATURE_LIST_B.add("代理品牌");
		SHOP_NATURE_LIST_B.add("自有品牌");
	}
	
	public static String SELLER_NATURE__C = "普通店";
	
	public static String SELLER_ISHOP = "i小铺";
	
	
	public static List<String> SHOP_NATURE_LIST_IShop= new ArrayList<String>();
	static{
		SHOP_NATURE_LIST_IShop.add("i小铺");
	};
	
	/**
	 * B店性质  品牌旗舰店 品牌专营店 品牌经销店
	 */
	public static  List<String> SELLER_NATURE_LIST_B = new ArrayList<String>();
	static{
		SELLER_NATURE_LIST_B.add("品牌店");
		SELLER_NATURE_LIST_B.add("旗舰店");
//		SELLER_NATURE_LIST_B.add("品牌专营店");
//		SELLER_NATURE_LIST_B.add("品牌经销店");
	}
	
	/**
	 * 主要货源
	 */
	public static  List<String> GOODS_SOURCE_LIST = new ArrayList<String>();
	static{
		GOODS_SOURCE_LIST.add("线下批发市场");
		GOODS_SOURCE_LIST.add("自己生产");
		GOODS_SOURCE_LIST.add("货源还未确定");
		GOODS_SOURCE_LIST.add("代工生产");
		GOODS_SOURCE_LIST.add("分销/代销");
		GOODS_SOURCE_LIST.add("实体店铺拿货");
		GOODS_SOURCE_LIST.add("自身公司渠道");
		GOODS_SOURCE_LIST.add("其他");
	}
	
	/**
	 * 店铺等级
	 */
	public static  List<String> SHOP_LEVEL_LIST = new ArrayList<String>();
	static{
		SHOP_LEVEL_LIST.add("商城");
		SHOP_LEVEL_LIST.add("皇冠以上");
		SHOP_LEVEL_LIST.add("皇冠以下");
		SHOP_LEVEL_LIST.add("无");
	}
	
	/**
	 * 销售规模
	 */
	public static  List<String> SALE_SCOPE_LIST = new ArrayList<String>();
	static{
		SALE_SCOPE_LIST.add("十万");
		SALE_SCOPE_LIST.add("百万");
		SALE_SCOPE_LIST.add("千万");
		SALE_SCOPE_LIST.add("亿");
	}
	
	/**
	 * 是否入住过B2C
	 */
	public static  List<String> ISENTER_B2C_LIST = new ArrayList<String>();
	static{
		ISENTER_B2C_LIST.add("是");
		ISENTER_B2C_LIST.add("否");
	}
	
	/**
	 * 是否开过外部店铺
	 */
	public static  List<String> ISOPEN_OUTER_SHOP_LIST = new ArrayList<String>();
	static{
		ISOPEN_OUTER_SHOP_LIST.add("有");
		ISOPEN_OUTER_SHOP_LIST.add("没有");
	}
	
	/**
	 * B店保证金金额(分)
	 */
	public static int B_MARGIN_PRICE = 1000000;
	
	//public static int B_MARGIN_PRICE = 1;
	
	/**
	 * 提供给商品那里的店铺类型 1:普通店 2:品牌店 3:旗舰店 4:i小铺
	 */
	public static List<String> SHOP_TYPE = new ArrayList<String>();
	static{
		SHOP_TYPE.add("1");
		SHOP_TYPE.add("2");
		SHOP_TYPE.add("3");
		SHOP_TYPE.add("4");
	}
	
	/**
	 * 跳转至财付通绑定和签约页面
	 * 
	 */
	public static final String CHECK_TENPAY = "CHECK_TENPAY";

	/**
	 * 跳转至签订服务协议页
	 */
	public static final String AGREEMENT = "AGREEMENT";

	/**
	 * 跳转至填写C店铺信息页
	 */
	public static final String FILL_SHOP_INFO_C = "FILL_SHOP_INFO_C";

	/**
	 * 跳转至填写B店铺步骤1信息页
	 */
	public static final String FILL_SHOP_INFO_B_SETP1 = "FILL_SHOP_INFO_B_SETP1";

	/**
	 * 跳转至填写B店铺步骤2信息页
	 */
	public static final String FILL_SHOP_INFO_B_SETP2 = "FILL_SHOP_INFO_B_SETP2";

	/**
	 * 跳转至填写B店铺步骤3信息页
	 */
	public static final String FILL_SHOP_INFO_B_SETP3 = "FILL_SHOP_INFO_B_SETP3";

	/**
	 * 跳转至填写B店铺步骤4信息页
	 */
	public static final String FILL_SHOP_INFO_B_SETP4 = "FILL_SHOP_INFO_B_SETP4";

	public static final String EXCHANGE_MARGIN = "EXCHANGE_MARGIN";

	public static final String SHOP_OPEN_BEGIN = "SHOP_OPEN_BEGIN";
	
	/**
	 * 域名设置操作--绑定 0
	 */
	public static final Integer SETTING_DOMAIN_BIND = 0;
	
	/**
	 * 域名设置操作--解绑 1
	 */
	public static final Integer SETTING_DOMAIN_UNBIND = 1;
	
	/**
	 * 域名设置操作--更改 2
	 */
	public static final Integer SETTING_DOMAIN_CHANGE = 2;
	
	public static final String PINJU_COM = ".pinju.com";
	
	public static final String SHOP_DEFAULT_LOGO = "http://static.pinju.com/img/shop_default_logo.png";
	
	//2.0
	/**
	 * 未绑定财付通
	 */
	public static final Integer TENPAYBIND_0 = 0;
	
	/**
	 * 绑定了财付通
	 */
	public static final Integer TENPAYBIND_1 = 1; 
	
	/**
	 * 未签约财付通
	 */
	public static final Integer TENPAYSING_0 = 0;
	
	/**
	 * 签约了财付通
	 */
	public static final Integer TENPAYSING_1 = 1; 	
	
	/**
	 * 不可以更改店铺类型
	 */
	public static final Integer CAN_CHANGE_SHOP_0 = 0;
	
	/**
	 * 可以更改店铺类型
	 */
	public static final Integer CAN_CHANGE_SHOP_1 = 1;
	
	/**
	 * 账户设定未完成
	 */
	public static final Integer IS_ACCOUNT_SET_NOT_COMPLETE = 0;
	
	/**
	 * 账户设定已完成
	 */
	public static final Integer IS_ACCOUNT_SET_COMPLETE = 1;
	
	/**
	 * 账户设定未开始
	 */
	public static final Integer IS_ACCOUNT_SET_NOT_BEGIN = -1;
	
	/**
	 * 填写信息未完成
	 */
	public static final Integer IS_FILL_COMPLETE_NOT_COMPLETE = 0;
	
	/**
	 * 填写信息已完成
	 */
	public static final Integer IS_FILL_COMPLETE_COMPLETE = 1;
	
	/**
	 * 填写信息未开始
	 */
	public static final Integer IS_FILL_COMPLETE_NOT_BEGIN = -1;
	
	/**
	 * 不可缴纳保证金
	 */
	public static final Integer CAN_PAY_MARGIN_0 = 0;
	
	/**
	 * 可以缴纳保证金
	 */
	public static final Integer CAN_PAY_MARGIN_1 = 1;
	
	/**
	 * 未填
	 */
	public static final Integer IS_FILL_SHOP_INFO_0 = 0;
	
	/**
	 * 已填
	 */
	public static final Integer IS_FILL_SHOP_INFO_1 = 1;
	
	/**
	 * 已填
	 */
	public static final Integer FILL_ALL_COMPLETE_VALUE = 123;
	
	/**
	 * 在线信息没通过
	 */
	public static final Integer IS_ONLINE_AUDIT_END_0 = 0;
	
	/**
	 * 在线信息通过
	 */
	public static final Integer IS_ONLINE_AUDIT_END_1 = 1;
	
	/**
	 * 邮寄信息没通过
	 */
	public static final Integer IS_POSTAL_AUDIT_END_0 = 0;
	
	/**
	 * 邮寄信息通过
	 */
	public static final Integer IS_POSTAL_AUDIT_END_1 = 1;
	
}
