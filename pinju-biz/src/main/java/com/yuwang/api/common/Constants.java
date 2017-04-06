/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.common;

/**
 * @author liyouguo
 * 
 * @since 2011-9-2
 */
public final class Constants {
	
	public static final Integer SUBMIT_APIMETHOD_PARAM_INVALID = 2;
	public static final String SUBMIT_APIMETHOD_PARAM_INVALID_MSG = "缺少相应必选参数。";

	public static final Integer SUBMIT_APIMETHOD_NO_DATA = 1;
	public static final String SUBMIT_APIMETHOD_NO_DATA_MSG = "没有查询到数据。";

	public static final Integer SUBMIT_APIMETHOD_INNER_SUCCESS= 0;
	public static final String SUBMIT_APIMETHOD_INNER_SUCCESS_MSG = "调用接口成功";
	
	public static final Integer SUMBIT_METHOD_FIDDEN = -2;
	public static final String SUMBIT_METHOD_FIDDEN_MSG = "开放API只允许POST方法提交。";

	public static final Integer SUMBIT_APIMETHOD_INVALID = -3;
	public static final String SUMBIT_APIMETHOD_INVALID_MSG = "提交的开放API方法未实现，带来不便请谅解。";

	public static final Integer SUBMIT_APIMETHOD_INNER_ERROR = -1;
	public static final String SUBMIT_APIMETHOD_INNER_ERROR_MSG = "调用接口失败，系统出错。";
	
	public static final Integer SUMBIT_APIAPPID_ISNULL_INVALID = -4;
	public static final String SUMBIT_APIAPPID_ISNULL_INVALID_MSG = "应用编号（appId）不能为空。";
	
	public static final Integer SUMBIT_APISESSIONKEY_INVALID = -5;
	public static final String SUMBIT_APISESSIONKEY_INVALID_MSG = "SessionKey不合法。";
	
	public static final Integer SUBMIT_UPLOAD_PARAM_INVALID = -6;
	public static final String SUBMIT_UPLOAD_PARAM_INVALID_MSG = "提交的上传图片参数无效，FORM表单须以multipart/form-data编码提交。";
	
	public static final Integer SUBMIT_UPLOAD_COUNT_SIZE_LIMIT_EXCEEDED = -10;
	public static final String SUBMIT_UPLOAD_COUNT_SIZE_LIMIT_EXCEEDED_MSG = "发出的请求超过服务器最大限制，目前最大请求数据为5M";
	
	public static final Integer SUBMIT_UPLOAD_SIZE_LIMIT_EXCEEDED = -8;
	public static final String SUBMIT_UPLOAD_SIZE_LIMIT_EXCEEDED_MSG = "上传的图片大小超出限制，目前最大支持上传500K。";
	
	public static final Integer SUBMIT_UPLOAD_FILE_WRITEDATA_ERROR = -7;
	public static final String SUBMIT_UPLOAD_FILE_WRITEDATA_ERROR_MSG = "获取上传图片的数据无效。";
	
	public static final Integer SUBMIT_SYSTEMDATA_SIGN_INVALID = -9;
	public static final String SUBMIT_SYSTEMDATA_SIGN_INVALID_MSG = "输入的数字签名无效。";
	
	public static final Integer SUBMIT_APIMETHOD_SHOPINFO_NULL = -11;
	public static final String SUBMIT_APIMETHOD_SHOPINFO_NULL_MSG = "请先成功开店。";

	public static final String YUWANG_OPENAPI_MEMCACHE_METHOD_KEY = "com.yuwang.api.methodmap.key";
	
	public static final String YUWANG_OPENAPI_MEMCACHE_APP_KEY = "com.yuwang.api.appmap.key";
	//缓存店铺信息key
	public static final String YUWANG_SHOPINFO_MEMCACHE_APP_KEY="com.yuwang.shopInfo.appmap.key";
	//缓存店铺类目信息key
	public static final String YUWANG_SHOPCATEGORY_MEMCACHE_APP_KEY="com.yuwang.shopCategory.appmap.key";
	//缓存商品类目信息key
	public static final String YUWANG_ITEMCATEGORY_MEMCACHE_APP_KEY="com.yuwang.itemCategory.appmap.key";
	public static final String OPEN_API_REQUEST_ENCODING = "UTF-8"; // 开放API编码

	public static final Integer OPEN_API_UPLOAD_FILE_COUNT_MAX_SIZE = 1024 * 1024 * 10; // 图片上传时请求最大大小10M

	public static final Integer OPEN_API_UPLOAD_FILE_MAX_SIZE = 1024 * 1024; // 图片上传时单张最大大小1M

	public static final String OPEN_API_UPLOAD_FILE_TEMP_PATH = System.getProperty("user.home") + "/apiUpload";// 上传图片存贮的临时路径

	public static final String OPEN_API_RESPONSE_PARSER_XML = "xml"; // 响应输出解析格式XML

	public static final String OPEN_API_RESPONSE_PARSER_JSON = "json";// 响应输出解析格式JSON
	
	/**
	 * 品聚开放API临时sessionKey
	 */
	public final static String PJ_OPEN_API_SESSION = "com.yuwang.api.session.key";
	
	public final static Integer OPEN_API_ITEM_OPERATE_NUM = 20;
}
