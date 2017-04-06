package com.yuwang.pinju.core.member.service;

import com.yuwang.pinju.domain.api.SndaRegisterNotifyDO;

/**
 * <p>盛大通行证会员服务</p>
 *
 * @author gaobaowen
 * @since 2011-7-11 下午05:28:18
 */
public interface SndaMemberService {

	/**
	 * 昵称检查 -- 成功（0）
	 */
	String CHECK_NICKNAME_SUCCESS = "0";

	/**
	 * 昵称检查 -- 昵称已被使用（1）
	 */
	String CHECK_NICKNAME_USED = "1";

	/**
	 * 昵称检查 -- 昵称格式不正确（2）
	 */
	String CHECK_NICKNAME_FORMAT_ERROR = "2";

	/**
	 * 昵称检查 -- 昵称长度不正确（3）
	 */
	String CHECK_NICKNAME_LENGTH_ERROR = "3";

	String SNDA_REGISTER_NOTIFY_SUCCESS = "SUCCESS";
	String SNDA_REGISTER_NOTIFY_PRAM_ERROR = "PARAM_ERROR";
	String SNDA_REGISTER_NOTIFY_SDID_EXISTS = "SDID_EXISTS";
	String SNDA_REGISTER_NOTIFY_SIGN_ERROR = "SIGN_ERROR";
	String SNDA_REGISTER_NOTIFY_ERROR = "ERROR";

	/**
	 * <p>检查会员昵称是否有效</p>
	 *
	 * @param nickname 昵称
	 * @return 返回结果，即给调用方的响应：
	 * <ul>
	 *   <li>0: 昵称有效且可用</li>
	 *   <li>1: 昵称已被使用</li>
	 *   <li>2: 昵称只允许数字、大小写英文字母、下划线或者汉字</li>
	 *   <li>3: 昵称长度应在[3-20]个字符</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 下午05:28:49
	 */
	String checkNickname(String nickname);

	/**
	 * <p>接收盛大通行证用户注册后，保存用户的昵称等信息</p>
	 *
	 * @param data 通知接口传输的数据
	 * @return 返回结果，即给调用方的响应：
	 * <ul>
	 *   <li>SUCCESS: 数据接收成功</li>
	 *   <li>PRAM_ERROR: 请求参数错误</li>
	 *   <li>SDID_EXISTS: 盛大数字账号用户已存在</li>
	 *   <li>NICK_ERROR: 昵称格式错误</li>
	 *   <li>3: 昵称长度应在[3-20]个字符</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 下午06:30:01
	 */
	String sndaRegisterNotify(SndaRegisterNotifyDO data);
}
