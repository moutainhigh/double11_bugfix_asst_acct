package com.yuwang.pinju.web.cookie;

public class PinjuCookieName {

	/**
	 * Session Group -- session
	 */
	public final static String CG_SESSION = "session";

	/**
	 * Session Group 会话 ID -- TOKEN
	 */
	public final static String CN_SESSION_ID = "TOKEN";

	/**
	 * Login Member Group -- member
	 */
	public final static String CG_LOGIN = "login";
	
	/**
	 * Shop Group -- shop1
	 */
	public final static String CG_SHOP1 = "shop-1";
	
	/**
	 * Shop Group -- shop2
	 */
	public final static String CG_SHOP2 = "shop-2";
	
	/**
	 * Login Member Group 会员昵称 -- N
	 */
	public final static String CN_LOGIN_NICKNAME = "N";

	/**
	 * Login member Group 会员登录信息 -- PJ0
	 */
	public final static String CN_LOGIN_PJ0 = "PJ0";

	/**
	 * Login Member 子 Cookie 会员版本 -- A
	 */
	public final static String CS_LOGIN_PJ0_INFO_VERSION = "A";

	/**
	 * Login Member 子 Cookie 会员登录时间 -- B
	 */
	public final static String CS_LOGIN_PJ0_LOGINTIME = "B";

	/**
	 * Login Member 子 Cookie 会员编号 -- C
	 */
	public final static String CS_LOGIN_PJ0_MEMBER_ID = "C";

	/**
	 * Login Member 子 Cookie 会员来源 -- D
	 */
	public final static String CS_LOGIN_PJ0_ORIGIN_AGREEMENT = "D";

	/**
	 * Login Member 子 Cookie 会员浏览器 USER-AGENT -- E
	 */
	public final static String CS_LOGIN_PJ0_USERAGENT = "E";

	/**
	 * Login Member 子 Cookie 会话ID(TOKEN)摘要 -- F
	 */
	public final static String CS_LOGIN_PJ0_TOKEN_HASH = "F";

	/**
	 * Login Member 子 Cookie 会话登录IP -- G
	 */
	public final static String CS_LOGIN_PJ0_IP = "G";

	/**
	 * 购物车 Group -- shopping-car
	 */
	public final static String CG_SHOPPING_CAR = "shopping-car";

	/**
	 * 购物车 Group 购物车数据 -- SC
	 */
	public final static String CN_SHOPPING_CAR_SC = "SC";

	/**
	 * 购物车 商品数-- SD
	 */
	public final static String CN_SHOPPING_CAR_SD = "SD";

	/**
	 * 防重复提交 TOKEN Group -- refresh-token
	 */
	public final static String CG_REFRESH_TOKEN = "refresh-token";

	/**
	 * 防重复提交 TOKEN -- _T_
	 */
	public final static String CN_REFRESH_T = "_T_";
	
	public final static String CN_SHOP1_PJ1 = "PJ1";
	public final static String CN_SHOP2_PJ2 = "PJ2";

	/**
	 * 品聚广告
	 */
	public final static String PJ_AD_POP= "advertise";

	/**
	 * 品聚广告推广商信息
	 */
	public final static String PJ_AD_POP_INFO = "P";

	/**
	 * 品聚广告推广商信息写入时间
	 */
	public final static String PJ_AD_POP_TIME = "PT";
}
