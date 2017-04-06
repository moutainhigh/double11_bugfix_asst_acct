package com.yuwang.pinju.core.trade.ao.impl;

import java.util.SortedMap;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.tenpay.MD5Util;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.user.ao.BaseAO;

/**
 * Created on 2011-9-13
 * @see
 * <p>Discription: 支付接口基础类</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public abstract class TenPayAbstractBaseAO extends BaseAO{

	/**
	 * 
	 * Created on 2011-9-1
	 * <p>Discription: 财付通签名 使用财付通MD5加密 Md5(原字符串&key=商户密钥).toLowerCase</p>
	 * @param parameters
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected SortedMap<String, String> createSign(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> map : parameters.entrySet()) {
			String k = map.getKey();
			String v = map.getValue();
			if (StringUtils.isNotEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k.concat("=").concat(v).concat("&"));
			}
		}
		sb.append("key=" + getMD5Key());
		String sign = MD5Util.MD5Encode(sb.toString(), CHARSET).toLowerCase();
		parameters.put("sign", sign);
		log.debug("createSign=>".concat(sb.toString()).concat(" => sign:").concat(sign));
		return parameters;
	}
	
	
	
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription: 验证签名</p>
	 * @param parameters
	 * @param signString
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean verifySign(SortedMap<String, String> parameters,String signString){
		createSign(parameters);
		String _signString = parameters.get("sign");
		return StringUtil.equalsIgnoreCase(_signString, signString);
	
	}

	
	/**
	 * 
	 * Created on 2011-9-1
	 * <p>Discription: 生成添加签名的财富通参数串</p>
	 * @param parameters
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected String parametersToString(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> map : parameters.entrySet()) {
			String k = map.getKey();
			String v = map.getValue();
			if (StringUtils.isNotEmpty(v)) {
				sb.append(k.concat("=").concat(v).concat("&"));
			}
		}
		//去除最后一个&
		String parameter = StringUtils.removeEnd(sb.toString(),"&");
		log.debug("parametersToString=>".concat(parameter));
		return parameter;
	}


	/**
	 * TODO Created on 2011-8-11
	 * <p>
	 * Discription: sdo.md5.key 后续在配置文件中提供加密串,使用前在这里进行解密
	 * </p>
	 * 
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected abstract String getMD5Key();
	
	/**
	 * Created on 2011-10-10
	 * <p>Discription: 验证分账和退款签名，使用GBK编码字符</p>
	 * @param parameters
	 * @param signString
	 * @return
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean verifySignByGbk(SortedMap<String, String> parameters,String signString){
		createSignByGbk(parameters);
		String _signString = parameters.get("sign");
		return StringUtil.equalsIgnoreCase(_signString, signString);
	
	}
	
	/**
	 * Created on 2011-10-10
	 * <p>Discription: 财付通分账和退款签名 使用 GBK 编码字符 财付通MD5加密 Md5(原字符串&key=商户密钥).toLowerCase</p>
	 * @param parameters
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected SortedMap<String, String> createSignByGbk(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> map : parameters.entrySet()) {
			String k = map.getKey();
			String v = map.getValue();
			if (StringUtils.isNotEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k.concat("=").concat(v).concat("&"));
			}
		}
		sb.append("key=" + getMD5Key());
		String sign = MD5Util.MD5Encode(sb.toString(), CHARSET_GBK).toLowerCase();
		parameters.put("sign", sign);
		log.debug("createSign=>".concat(sb.toString()).concat(" => sign:").concat(sign));
		return parameters;
	}
	
	/**  必填  */
	//财付通商户号
	protected final String MERCHANTNO = PinjuConstant.TENPAY_DIRECTPAY_PARTNER;
	//财付通支付货币类型
	protected final String CURRENCYTYPE = PinjuConstant.TENPAY_DIRECTPAY_FEE_TYPE;
	//财付通签名类型
	protected final String SIGNTYPE = PinjuConstant.TENPAY_DIRECTPAY_SIGN_TYPE;
	//财付通编码类型 utf-8(支付使用此编码类型)
	protected final String CHARSET = PinjuConstant.TENPAY_DIRECTPAY_INPUT_CHARSET;
	//财付通编码类型 gbk(分账和退款使用此编码类型)
	protected final String CHARSET_GBK = PinjuConstant.TENPAY_PAY_INPUT_CHARSET_GBK;
	//品聚财付通分账账户
	protected final String PLATFORMACCOUNT= PinjuConstant.TENPAY_PAY_PINJU_ACCOUNT;
}

