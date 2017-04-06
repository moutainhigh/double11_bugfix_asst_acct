/**
 *
 */
package com.yuwang.pinju.core.constant.system;
import java.security.Key;
import java.util.regex.Pattern;

import javax.crypto.spec.SecretKeySpec;

/**
 * @author yejingfeng 品聚系统配置常量
 *
 */
public class PinjuConstant {

	private PinjuConstant() {
	}

	/**
	 * 当前环境是否处于开发模式
	 */
	public final static boolean isDevelopment = ConfigHelper.getDefault().getBoolean("development");

	/**
	 * 1M的文件大小1024*1024=726016
	 */
	public final static long FILE_SIZE_M = 726016;

	/**
	 * 1K的文件大小1024
	 */
	public final static long FILE_SIZE_K = 1024;

	/**
	 * 盛付通.品聚在盛付通的商户号 Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_MERCHANTNO = ConfigHelper.getDefault().getString("shengpay.directpay.merchantno");

	/**
	 * 盛付通.盛付通即时到账网关地址 Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_PAYURL = ConfigHelper.getDefault().getString("shengpay.directpay.payurl");

	/**
	 * 盛付通.客户端回调地址,支付成功后,将附带回调数据跳转到此页面 Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_POSTBACKURL = ConfigHelper.getDefault().getString("shengpay.directpay.postbackurl");

	/**
	 * 盛付通.服务端通知地址,支付成功后,盛付通发支付成功到此地址 Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_NOTIFYURL = ConfigHelper.getDefault().getString("shengpay.directpay.notifyurl");

	/**
	 * 盛付通.货币类型，仅支持人民币，必须传入RMB Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_CURRENCYTYPE = ConfigHelper.getDefault().getString("shengpay.directpay.currencytype");

	/**
	 * 盛付通.发货通知方式tcp，http，https等。一般为http Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_NOTIFYURLTYPE = ConfigHelper.getDefault().getString("shengpay.directpay.notifyurltype");

	/**
	 * 盛付通.盛付通当前版本号,目前为3.0 Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_VERSION = ConfigHelper.getDefault().getString("shengpay.directpay.version");

	/**
	 * 盛付通.MD5加密串 Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_MD5KEY = ConfigHelper.getDefault().getString("shengpay.directpay.md5key");

	/**
	 * 盛付通.签名方式  Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_SIGNTYPE = ConfigHelper.getDefault().getString("shengpay.directpay.signtype");

	/**
	 * 盛付通.盛付通提示成功后回传消息  Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_SUCCESSMSG = ConfigHelper.getDefault().getString("shengpay.directpay.successmsg");

	/**
	 * 盛付通.支付渠道  Add By ShiXing@2011.08.02
	 */
	public final static String SHENGPAY_DIRECTPAY_PAYCHANNEL = ConfigHelper.getDefault().getString("shengpay.directpay.paychannel");

	/**
	 * 盛付通.品聚sellerID  Add By ShiXing@2011.08.15
	 */
	public final static String SHENGPAY_DIRECTPAY_PINJU_SELLERID = ConfigHelper.getDefault().getString("shengpay.directpay.pinju.sellerid");

	/**
	 * 盛付通.品聚sellerNick  Add By ShiXing@2011.08.15
	 */
	public final static String SHENGPAY_DIRECTPAY_PINJU_SELLERNICK = ConfigHelper.getDefault().getString("shengpay.directpay.pinju.sellernick");

	/**
	 * 盛付通.品聚支付账号  Add By ShiXing@2011.08.15
	 */
	public final static String SHENGPAY_DIRECTPAY_PINJU_PAYACCOUNT = ConfigHelper.getDefault().getString("shengpay.directpay.pinju.payaccount");

	/**
	 * 盛付通.请求盛付通发送时间格式 Add By ShiXing@2011.08.02
	 */
	public final static String DATE_FORMAT = "yyyyMMddHHmmss";


	/**

	 * 财付通.委托关系查询网关地址 Add By ShiXing@2011.09.23
	 */
	public final static String TENPAY_INQUIRE_RELATION_GATEURL = ConfigHelper.getDefault().getString("tenpay.inquire.relation.gateurl");

	/**
	 * 财付通.证书存放地址 Add By ShiXing@2011.09.23
	 */
	public final static String TENPAY_CERTIFICATE_PATH = ConfigHelper.getDefault().getString("tenpay.certificate.path");

	/**
	 * 财付通.证书名称 Add By ShiXing@2011.10.11
	 */
	public final static String TENPAY_CACERT_NAME = ConfigHelper.getDefault().getString("tenpay.cacert.name");

	/**
	 * 财付通.MD5加密串 Add By DuCheng@2011.08.31
	 */
	public final static String TENPAY_DIRECTPAY_MD5KEY = ConfigHelper.getDefault().getString("tenpay.directpay.md5key");
	/**
	 * 财付通商户号
	 */
	public final static String TENPAY_DIRECTPAY_PARTNER = ConfigHelper.getDefault().getString("tenpay.directpay.partner");
	/**
	 * 财付通即时到账网关
	 */
	public final static String TENPAY_DIRECTPAY_PAYURL = ConfigHelper.getDefault().getString("tenpay.directpay.payurl");

	/**
	 * 财付通回调页面地址
	 */
	public final static String TENPAY_DIRECTPAY_RETURN_URL = ConfigHelper.getDefault().getString("tenpay.directpay.returnurl");

	/**
	 * 财付通回调后台地址
	 */
	public final static String TENPAY_DIRECTPAY_NOTIFY_URL = ConfigHelper.getDefault().getString("tenpay.directpay.notifyurl");

	/**
	 * 财付通支付货币类型
	 */
	public final static String TENPAY_DIRECTPAY_FEE_TYPE = ConfigHelper.getDefault().getString("tenpay.directpay.feetype");

	/**
	 * 财付通签名类型
	 */
	public final static String TENPAY_DIRECTPAY_SIGN_TYPE = ConfigHelper.getDefault().getString("tenpay.directpay.signtype");

	/**
	 * 财付通编码类型
	 */
	public final static String TENPAY_DIRECTPAY_INPUT_CHARSET = ConfigHelper.getDefault().getString("tenpay.directpay.inputcharset");

	/**
	 * 财付通编码类型 gbk(分账和退款使用此编码类型)
	 */
	public final static String TENPAY_PAY_INPUT_CHARSET_GBK = ConfigHelper.getDefault().getString("tenpay.pay.input.charset.gbk");
	/**
	 * 财富通支付渠道
	 */
	public final static String TENPAY_DIRECTPAY_BANK_TYPE = ConfigHelper.getDefault().getString("tenpay.directpay.banktype");

	/**
	 * 财富通交易类型
	 */
	public final static String TENPAY_DIRECTPAY_TRADE_MODE = ConfigHelper.getDefault().getString("tenpay.directpay.trademode");

	/**
	 * 财富通支付结果成功
	 */
	public final static String TENPAY_DIRECTPAY_TRADE_STATE = ConfigHelper.getDefault().getString("tenpay.directpay.tradestate");

	/**
	 * 财富通通知成功后回传消息处理成功
	 */
	public final static String TENPAY_DIRECTPAY_SUCCESS = ConfigHelper.getDefault().getString("tenpay.directpay.success");

	/**
	 * 财富通通知成功后回传消息处理失败,进行补单
	 */
	public final static String TENPAY_DIRECTPAY_FAIL = ConfigHelper.getDefault().getString("tenpay.directpay.fail");

	/**
	 * 财富通.品聚sellerNick  Add By ShiXing@2011.08.15
	 */
	public final static String TENPAY_DIRECTPAY_PINJU_SELLERNICK = ConfigHelper.getDefault().getString("tenpay.directpay.pinju.sellernick");

	/**
	 * 财富通.品聚支付账号  Add By ShiXing@2011.08.15
	 */
	public final static String TENPAY_DIRECTPAY_PINJU_PAYACCOUNT = ConfigHelper.getDefault().getString("tenpay.directpay.pinju.payaccount");

	/**
	 * 财富通.请求盛付通发送时间格式 Add By ShiXing@2011.08.02
	 */
	public final static String TENPAY_DATE_FORMAT = "yyyyMMddHHmmss";


	/**
	 * 财付通支付账网关(单笔支付，合并支付相同) Add By Shihongbo
	 */
	public final static String TENPAY_PAY_URL = ConfigHelper.getDefault().getString("tenpay.pay.payurl");

	/**
	 * 财富通单笔支付--版本号  Add By Shihongbo
	 */
	public final static String TENPAY_SINGLE_PAY_VER = ConfigHelper.getDefault().getString("tenpay.pay.ver");

	/**
	 * 财付通.业务代码  查询接口填 96 Add By LiXin@2011.09.09
	 */
	public final static String TENPAY_SEARCHORDER_CMDNO = ConfigHelper.getDefault().getString("tenpay.searchorder.cmdno");

	/**
	 * 财付通.后台回调地址 Add By LiXin@2011.09.09
	 */
	public final static String TENPAY_SEARCHORDER_RETURNURL = ConfigHelper.getDefault().getString("tenpay.searchorder.returnurl");

	/**
	 * 财付通.发送查询订单请求时间格式Add By LiXin@2011.09.09
	 */
	public final static String TENPAY_SEARCHORDER_DATE_FORMAT = "yyyyMMdd";

	/**
	 * 财付通.版本号 Add By LiXin@2011.09.09
	 */
	public final static String TENPAY_SEARCHORDER_VERSION = ConfigHelper.getDefault().getString("tenpay.searchorder.version");

	/**
	 * 财付通.查询订单请求地址Add By LiXin@2011.09.13
	 */
	public final static String TENPAY_SEARCHORDER_SEARCHURL = ConfigHelper.getDefault().getString("tenpay.searchorder.searchurl");



	/**
	 * 财富通单笔支付--业务代码  Add By Shihongbo
	 */
	public final static String TENPAY_SINGLE_PAY_CMDNO = ConfigHelper.getDefault().getString("tenpay.pay.cmdno");

	/**
	 * 财富通单笔支付--银行类型  Add By Shihongbo
	 */
	public final static String TENPAY_SINGLE_PAY_BANKTYPE = ConfigHelper.getDefault().getString("tenpay.pay.banktype");

	/**
	 * 财富通单笔支付--现金币种  Add By Shihongbo
	 */
	//public final static String TENPAY_SINGLE_PAY_FEETYPE = ConfigHelper.getDefault().getString("tenpay.pay.feetype");

	/**
	 * 财富通单笔支付--desc字符编码名称  Add By Shihongbo
	 */
	//public final static String TENPAY_SINGLE_PAY_CS = ConfigHelper.getDefault().getString("tenpay.pay.cs");

	/**
	 * 财富通合并支付--返回地址  Add By Shihongbo
	 */
	public final static String TENPAY_MERGE_PAY_RETURNURL = ConfigHelper.getDefault().getString("tenpay.pay.returnurl");

	/**
	 * 财富通合并支付--后台返回地址  Add By DuCheng@2011.11.11
	 */
	public final static String TENPAY_MERGE_PAY_NOTIFYURL = ConfigHelper.getDefault().getString("tenpay.pay.notifyurl");


	/**********************************************************担保交易*************************************************/
	/**
	 * 财富通品聚担保交易分账账户
	 */
	public final static String TENPAY_PAY_PINJU_ACCOUNT = ConfigHelper.getDefault().getString("tenpay.pay.pinju.account");


	/**
	 * 财付通担保交易商户号(商户网站编号) Add By DuCheng@2011.09.22
	 */
	public final static String TENPAY_PAY_PARTNER = ConfigHelper.getDefault().getString("tenpay.pay.partner");

	/**
	 * 财付通担保交易商户号(商户网站编号) Add By DuCheng@2011.09.22
	 */
	public final static String TENPAY_PAY_MD5KEY = ConfigHelper.getDefault().getString("tenpay.pay.md5key");


	/************************************平台退款常量配置开始***************************************************************************/
	/**
	 * 财付通平台退款网关地址 Add By MaYuanChao @2011-09-13
	 */
	public final static String TENPAY_PLATFORM_REFUND_REFUNDURL=ConfigHelper.getDefault().getString("tenpay.platformrefund.refundgatewayurl");

	/**
	 * 财付通平台退款返回地址 Add By MaYuanChao @2011-09-13
	 */
	public final static String TENPAY_PLATFORM_REFUND_RETURNURL=ConfigHelper.getDefault().getString("tenpay.platformrefund.returnurl");

	/**
	 * 财付通平台退款版本号 Add By MaYuanChao @2011-09-13
	 */
	public final static String TENPAY_PLATFORM_REFUND_VERSION=ConfigHelper.getDefault().getString("tenpay.platformrefund.version");

	/**
	 * 财付通平台退款业务代号 Add By MaYuanChao @2011-09-13
	 */
	public final static String TENPAY_PLATFORM_REFUND_CMDNO=ConfigHelper.getDefault().getString("tenpay.platformrefund.cmdno");

	/**
	 *退款Id生成规则的起始codes[109] 生成规则:109＋spid+YYYYMMDD+7位流水号  Add By MaYuanChao @2011-09-13
	 */
	public final static String TENPAY_PLATFORM_REFUN_CODES=ConfigHelper.getDefault().getString("tenpay.platformrefund.codes");

	/******************************平台退款常量配置结束*****************************************************************************/


	/************************************财付通分账回退常量配置开始***************************************************************************/
	/**
	 * 回调地址
	 */
	public final static String TENPAY_SPLITREFUND_URL=ConfigHelper.getDefault().getString("tenpay.splitrefund.returnurl");

	/**
	 * 请求地址
	 */
	public final static String TENPAY_SPLITREFUND_REFUNDGATEWAYURL=ConfigHelper.getDefault().getString("tenpay.splitrefund.refundgatewayurl");

	/**
	 * 业务代码, 财付通分账回退款接口填  95
	 */
	public final static String TENPAY_SPLITREFUND_CMDNO=ConfigHelper.getDefault().getString("tenpay.splitrefund.cmdno");

	/**
	 * 版本号
	 */
	public final static String TENPAY_SPLITREFUND_VERSION=ConfigHelper.getDefault().getString("tenpay.splitrefund.version");

	/**
	 * 业务类型,代表分账回退处理规则与业务参数编码规则，暂固定为97
	 */
	public final static String TENPAY_SPLITREFUND_BUSTYPE=ConfigHelper.getDefault().getString("tenpay.splitrefund.bustype");

	/**
	 * 退款单ID前缀
	 */
	public final static String TENPAY_SPLITREFUND_REFUNDID=ConfigHelper.getDefault().getString("tenpay.splitrefund.refundid");


	/************************************分账回退常量配置结束***************************************************************************/

	/**
	 * 保证金WebService服务地址 Add By ShiXing@2011.08.02
	 */
	public final static String PINJU_MARGIN_SERVICE_URL = ConfigHelper.getDefault().getString("pinju.margin.service.url");

	/**
	 * 保证金WebService命名空间 Add By ShiXing@2011.08.02
	 */
	public final static String PINJU_MARGIN_SERVICE_NAMESPACE = ConfigHelper.getDefault().getString("pinju.margin.service.namespace");

	/**
	 * 分账--财付通分账业务代码  Add By heyong@2011.09.09
	 */
	public final static String TENPAY_SPLIT_CMDNO = ConfigHelper.getDefault().getString("tenpay.split.cmdno");

	/**
	 * 分账--财付通分账回调页面地址  Add By heyong@2011.09.09
	 */
	public final static String TENPAY_SPLIT_RETURNURL = ConfigHelper.getDefault().getString("tenpay.split.returnurl");

	/**
	 * 分账--财付通分账业务类型  Add By heyong@2011.09.09
	 */
	public final static String TENPAY_SPLIT_BUSTYPE = ConfigHelper.getDefault().getString("tenpay.split.bustype");

	/**
	 * 分账--财付通分账版本号 Add By heyong@2011.09.09
	 */
	public final static String TENPAY_SPLIT_VERSION = ConfigHelper.getDefault().getString("tenpay.split.version");

	/**
	 * 分账--财付通分账请求网关 Add By heyong@2011.09.09
	 */
	public final static String TENPAY_SPLIT_PATMENTGATEWAYURL = ConfigHelper.getDefault().getString("tenpay.split.patmentgatewayurl");

	/**
	 * 平台默认字符编码
	 */
	public final static String DEFAULT_CHARSET = ConfigHelper.getDefault().getString("default.charset");

	/**
	 * 文件服务器地址
	 */
	public static final String FILE_SERVER = ConfigHelper.getDefault().getString("file.server");

	/**
	 * 图片服务器地址
	 */
	public static final String IMAGE_SERVER = ConfigHelper.getDefault().getString("image.server");
	/**
	 * 文件上传临时目录
	 */
	public static final String FILE_TEMP_PATH = ConfigHelper.getDefault().getString("file.temp.path");
	/**
	 * 读取图片服务器地址
	 */
	public static final String VIEW_IMAGE_SERVER = ConfigHelper.getDefault().getString("view.image.server");

	/**
	 * 静态资源服务器地址
	 */
	public static final String STATIC_SERVER = ConfigHelper.getDefault().getString("static.server");

	/**
	 * 搜索服务器地址
	 */
	public static final String SEARCH_SERVER = ConfigHelper.getDefault().getString("search.server");

	/**
	 * 网站地址
	 */
	public final static String PINJU_SERVER =  ConfigHelper.getDefault().getString("pinju.server");

	/**
	 * SNDA 开放 API 应用 KEY
	 *
	 * @author gaobaowen
	 * @since 2011-07-11 11:07:00
	 */
	public final static String SNDA_API_APP_KEY = ConfigHelper.getDefault().getString("snda.api.app.key");

	/**
	 * SNDA 开放 API 应用安全 KEY
	 *
	 * @author gaobaowen
	 * @since 2011-07-11 11:07:00
	 */
	public final static String SNDA_API_APP_SECRET_KEY = ConfigHelper.getDefault().getString("snda.api.app.secret.key");

	/**
	 * SNDA 开放 API System Token 获取 URL
	 *
	 * @author gaobaowen
	 * @since 2011-07-11 11:07:00
	 */
	public final static String SNDA_API_SYSTEM_TOKEN_URL = ConfigHelper.getDefault().getString(
			"snda.api.system.token.url");

	/**
	 * 根据盛大通行证数据账号查询用户其他账号 SNDA 开放 API 调用的方法名
	 *
	 * @author gaobaowen
	 * @since 2011-07-11 11:13:00
	 */
	public final static String SNDA_API_QUERY_USER_INFO = ConfigHelper.getDefault().getString(
			"snda.api.query.user.info");

	/**
	 * SNDA 开放 API 调用 URL
	 *
	 * @author gaobaowen
	 * @since 2011-07-11 11:13:00
	 */
	public final static String SNDA_API_INVOKE_URL = ConfigHelper.getDefault().getString("snda.api.invoke.url");

	/**
	 * 查询用户账号的参数 key 值
	 *
	 * @author gaobaowen
	 * @since 2011-07-11 11:13:00
	 */
	public final static String SNDA_API_QUERY_USER_INFO_KEY = ConfigHelper.getDefault().getString(
			"snda.api.query.user.info.key");

	/**
	 * 盛大开放 API 调用时 connect 和 read 超时时间（毫秒）
	 *
	 * @author gaobaowen
	 * @since 2011-07-11 11:13:00
	 */
	public final static int SNDA_API_INVOKE_TIMEOUT = ConfigHelper.getDefault().getInt("snda.api.invoke.timeout", 3000);

	/**
	 * 盛大通行证定制注册后通知接口进行签名的 KEY
	 *
	 * @author gaobaowen
	 * @since 2011-07-12 10:47:00
	 */
	public final static String API_SNDA_REGISTER_NOTIFY_SIGN_KEY = ConfigHelper.getDefault().getString(
			"api.snda.register.notify.sign.key");

	/**
	 * 用于社区的 API 的数据签名 KEY
	 *
	 * @author gaobaowen
	 * @since 2011-07-19 16:05:00
	 */
	public final static String API_SNS_SIGN_KEY = ConfigHelper.getDefault().getString("api.sns.sign.key");

	/**
	 * 验证码缓存有效时间（秒）
	 *
	 * @author gaobaowen
	 * @since 2011-07-28 13:42:00
	 */
	public final static int CAPTCHA_MEMCACHED_TIMEOUT = ConfigHelper.getDefault().getInt("captcha.memcached.timeout");

	/**
	 * 登录密码错误超过多少次时出现验证码
	 *
	 * @author gaobaowen
	 * @since 2011-07-28 13:42:00
	 */
	public final static int PINJU_LOGIN_ERROR_COUNT_CAPTCHA = ConfigHelper.getDefault().getInt(
			"pinju.login.error.count.captcha");

	/**
	 * 登录密码错误超过多少次时出现验证码的超时时间（秒）
	 *
	 * @author gaobaowen
	 * @since 2011-07-28 13:42:00
	 */
	public final static int PINJU_LOGIN_ERROR_COUNT_TIMEOUT = ConfigHelper.getDefault().getInt(
			"pinju.login.error.count.timeout");

	/**
	 * 申请应用时分配的AppKey
	 */
	public final static String SINA_WEIBO_CLIENTID = ConfigHelper.getDefault().getString("sina.weibo.clientid");

	/**
	 * 申请应用时分配的AppSecret
	 */
	public final static String SINA_WEIBO_CLIENT_SERCRET = ConfigHelper.getDefault().getString("sina.weibo.client.sercret");

	/**
	 * weibo登录成功回调地址
	 */
	public final static String SINA_WEIBO_REDIRECT_URI = ConfigHelper.getDefault().getString("sina.weibo.redirect.uri");

	/**
	 * 查询新浪会员UID的url
	 */
	public final static String SINA_WEIBO_GETUID_URL = ConfigHelper.getDefault().getString("sina.weibo.getuid.url");

	/**
	 * 查询新浪会员信息的url
	 */
	public final static String SINA_WEIBO_GETUSER_URL = ConfigHelper.getDefault().getString("sina.weibo.getuser.url");

	/**
	 * 获取weibo的accessToken信息的URL
	 */
	public final static String SINA_WEIBO_ACCESSTOKEN_URL = ConfigHelper.getDefault().getString("sina.weibo.accesstoken.url");

	/**
	 * 获取weibo授权信息的URL
	 */
	public final static String SINA_WEIBO_AUTHORIZE_URL = ConfigHelper.getDefault().getString("sina.weibo.authorize.url");

	/**
	 * 链接超时为30s
	 */
	public final static int SINA_WEIBO_CONNECTTIMEOUT = ConfigHelper.getDefault().getInt("sina.weibo.connecttimeout");

	/**
	 * 读取超时时间为30s
	 */
	public final static int SINA_WEIBO_READTIMEOUT = ConfigHelper.getDefault().getInt("sina.weibo.readtimeout");

	/**
	 * 搜索连接超时时间
	 *
	 * @author zhouzhaohua
	 * @since 2011-07-21 17:25:00
	 */
	public final static int SEARCH_CONNECTION_TIMEOUT = ConfigHelper.getDefault().getInt(
			"search.httpclient.connectTimeout");

	/**
	 * 搜索数据等待超时时间
	 *
	 * @author zhouzhaohua
	 * @since 2011-07-21 17:28:00
	 */
	public final static int SEARCH_SOTIMEOUT = ConfigHelper.getDefault().getInt("search.httpclient.soTimeout");

	/**
	 * 搜索每个主机的最大并行链接数
	 *
	 * @author zhouzhaohua
	 * @since 2011-07-21 17:28:00
	 */
	public final static int SEARCH_MAX_CONNECTIONS_PER_HOST = ConfigHelper.getDefault().getInt(
			"search.httpclient.maxConnectionsPerHost");

	/**
	 * 搜索总并行链接最大数
	 *
	 * @author zhouzhaohua
	 * @since 2011-07-21 17:28:00
	 */
	public final static int SEARCH_MAX_TOTAL_CONNECTIONS = ConfigHelper.getDefault().getInt(
			"search.httpclient.maxTotalConnections");

	/**
	 * 搜索查询服务地址
	 *
	 * @author zhouzhaohua
	 * @since 2011-07-21 17:28:00
	 */
	public final static String SEARCH_SERVERURL = ConfigHelper.getDefault().getString("search.httpclient.serverUrl");

	/**
	 * 搜索索引更新地址
	 *
	 * @author zhouzhaohua
	 * @since 2011-10-17 13:45:00
	 */
	public final static String UPDATE_SEARCH_SERVERURL = ConfigHelper.getDefault().getString("search.httpclient.updateServerUrl");


	/**
	 * 财付通绑定账号网关地址 Add By ShiXing@2011.09.15
	 */
	public static final String TENPAY_BIND_GATEURL = "tenpay.bind.gateurl";

	/**
	 * 邮件链接有效时间（毫秒）
	 *
	 * @autho gaobaowen
	 * @since 2011-09-02 10:52:00
	 */
	public final static long EMAIL_LINK_EXPIRE_MILLIS = 24L * 3600L * 1000L * ConfigHelper.getDefault().getInt("email.link.expire.days", 1);

	/**
	 * 系统邮箱地址
	 */
	public final static String EMAIL_SYSTEM_ADDRESS = ConfigHelper.getDefault().getString("email.system.address");

	/**
	 * 系统发送者名称
	 */
	public final static String EMAIL_SYSTEM_FROM_NAME = ConfigHelper.getDefault().getString("email.system.from.name");

	/**
	 * 会员安全 -- 对称加密算法
	 */
	public final static String AUTH_ENCRYPT_ALGORITHM = ConfigHelper.getDefault().getString("auth.encrypt.algorithm");

	/**
	 * 会员安全 -- 加密算法模式
	 */
	public final static String AUTH_ENCRYPT_MODE = ConfigHelper.getDefault().getString("auth.encrypt.mode");

	/**
	 * 会员安全 -- 加密填充模式
	 */
	public final static String AUTH_ENCRYPT_PADDING = ConfigHelper.getDefault().getString("auth.encrypt.padding");

	/**
	 * 会员安全 -- 加密密钥
	 */
	public final static Key AUTH_ENCRYPT_KEY = new SecretKeySpec(ConfigHelper.getDefault().getBytesFromBase64("auth.encrypt.key"), AUTH_ENCRYPT_ALGORITHM);

	/**
	 * 会员安全 -- MAC 签名算法
	 */
	public final static String AUTH_SIGN_MAC_ALGORITHM = ConfigHelper.getDefault().getString("auth.sign.mac.algorithm");

	/**
	 * 会员安全 -- MAC 签名密钥
	 */
	public final static Key AUTH_SIGN_MAC_KEY = new SecretKeySpec(ConfigHelper.getDefault().getBytesFromBase64("auth.sign.mac.key"), AUTH_SIGN_MAC_ALGORITHM);

	/**
	 * 会员安全 -- RSA 私钥
	 */
	final static Key AUTH_RSA_PRIVATE_KEY = PinjuConstantUtil.privateKey( ConfigHelper.getDefault().getString("auth.rsa.private.key") );

	/**
	 * 短信验证码字符数
	 */
	public final static int MOBILE_CODE_LENGTH = ConfigHelper.getDefault().getInt("mobile.code.length", 6);

	/**
	 * 短信验证码有效时间（秒）
	 */
	public final static int MOBILE_CODE_EXPIRE_SECONDS = ConfigHelper.getDefault().getInt("mobile.code.expire.seconds", 300);

	/**
	 * 短信验证码每天的发送次数（按功能分）
	 */
	public final static int MOBILE_CODE_DAILY_MAX_COUNT = ConfigHelper.getDefault().getInt("mobile.code.daily.max.count", 5);

	/**
	 * 手机号码正则表达式，使用 MOBILE_NUMBER_PATTERN.matcher(input).matches(); 判断
	 */
	public final static Pattern MOBILE_NUMBER_PATTERN = Pattern.compile(ConfigHelper.getDefault().getString("mobile.number.pattern"));

	/**
	 * 品聚公司IP列表,用于公司内部访问白名单@liuboen
	 */
	public static final String[] PINJU_COMPANY_IPS = ConfigHelper.getDefault().getStringArray("pinju.company.ips");

	/**
	 * 商品评论默认每页数量
	 */
	public static final int COMMENTS_ITEM_PAGE_SIZE = ConfigHelper.getDefault().getInt("comments.item.default.page.size", 10);

	/**
	 * 商品评论缓存页数
	 */
	public static final int COMMENTS_ITEM_CACHE_PAGE = ConfigHelper.getDefault().getInt("comments.item.cache.page", 5);

	/**
	 * 商品评论页：选取多少天以内的评论
	 */
	public static final int COMMENTS_ITEM_DAYS = ConfigHelper.getDefault().getInt("comments.item.days", 30);

	/**
	 * 卖家评论默认每页数量
	 */
	public static final int COMMENTS_SELLER_PAGE_SIZE = ConfigHelper.getDefault().getInt("comments.seller.default.page.size", 10);

	/**
	 * 卖家评论缓存页数
	 */
	public static final int COMMENTS_SELLER_CACHE_PAGE = ConfigHelper.getDefault().getInt("comments.seller.cache.page", 5);

	/**
	 * 卖家资质评论页：选取多少天以内的评论
	 */
	public static final int COMMENTS_SELLER_DAYS = ConfigHelper.getDefault().getInt("comments.seller.days", 30);
	
	/**
	 * B店保证金金额(分)
	 */
	public static final int B_MARGIN_PRICE = ConfigHelper.getDefault().getInt("shop.business.margin",1000000);
}
