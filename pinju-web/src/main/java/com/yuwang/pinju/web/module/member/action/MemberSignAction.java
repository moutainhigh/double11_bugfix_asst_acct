package com.yuwang.pinju.web.module.member.action;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.tenpay.MD5Util;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * @Project: pinju-web
 * @Description: 会员绑定签约Action
 * @author 石兴 shixing@zba.com
 * @date 2011-9-13 上午13:56:14
 * @update 2011-9-13 上午13:56:14
 * @version V1.0
 */
public class MemberSignAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2495924312847613681L;
	
	/**
	 * 业务类型_绑定财付通.对应财付通通知接口的cmdno
	 */
	public final static String BIZ_TYPE_BIND = "91";
	
	/**
	 * 业务类型_签订代扣协议.对应财付通通知接口的cmdno
	 */
	public final static String BIZ_TYPE_SIGN = "2";
	
	private MemberAO memberAO;
	
	/**
	 * Created on 2011-9-13 
	 * @desc <p>Discription:[接收财付通回调消息]</p>
	 * @param 
	 * @return String
	 * @author:[石兴]
	 * @update:[2011-9-13] [更改人姓名]
	 */
	public String execute() {
		
		/**
		 * 因为回调只传回财付通账号,无法与memberID对应,会造成绑定和签约不是同一个支付账号问题,
		 * 故注掉回掉的逻辑处理,用主动查询接口代替 Add By ShiXing@2011.09.30
		 */
		/*String spid = getString("spid");
		if(!validateSpid(spid)){
			log.error("MemberSignAction execute 非法商户号");
		}
		String signType = getString("cmdno");
		if(BIZ_TYPE_SIGN.equals(signType)) { //签订委托退款协议
			if (!isTenpaySign()) {
				log.error("Event=[MemberSignAction#execute#isTenpaySign] 签订委托退款协议,签名校验失败,非品聚商户数据！");
			}
			String accountNo = getString("uin"); //被扣款方财付通账号
			MemberPaymentDO memberPaymentDO = memberAO.getPaymentDOByAccountNo(accountNo);
			Result result = memberAO.signPayAgreement(memberPaymentDO);
			if (!result.isSuccess()) {
				log.error("Event=[MemberSignAction#execute] 签订代扣协议失败！ResultCode:"+result.getResultCode());
			}
		}*/
		return null;
	}
	
	/**
	 * Created on 2011-9-15 
	 * @desc <p>Discription:[签委托退款关系协议回调验签]</p>
	 * @param 
	 * @return boolean
	 * @author:[石兴]
	 * @update:[2011-9-15] [更改人姓名]
	 */
	public boolean isTenpaySign(){
	    //获取参数
	    String cmdno = getString("cmdno");
        String spid = getString("spid");
        String uin = getString("uin");
        String status = getString("status");
        String tenpaySign = getString("cftsign");
	    //String key = PinjuConstant.TENPAY_PAY_MD5KEY;
	    String key = "pjincs753hdbca6zjse7g128ev4kmncj";
			
	    //组织签名串
	    StringBuffer sb = new StringBuffer();
	    sb.append("cmdno=" + cmdno + "&");
        sb.append("spid=" + spid + "&");
        sb.append("uin=" + uin + "&");
        sb.append("status=" + status);
	    sb.append(key);
		
	    //算出摘要
	    String enc = PinjuConstant.TENPAY_DIRECTPAY_INPUT_CHARSET;
	    String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
	    //debug信息
	    log.error("MemberSignAction#isTenpaySign"+sb.toString() + " => sign:" + sign + " tenpaySign:" + tenpaySign);
	    return tenpaySign.equals(sign);
	}

	/**
	 * Created on 2011-9-14 
	 * @desc <p>Discription:[校验商户号]</p>
	 * @param 
	 * @return boolean
	 * @author:[石兴]
	 * @update:[2011-9-14] [更改人姓名]
	 */
	private boolean validateSpid(String spid) {
		if (!PinjuConstant.TENPAY_PAY_PARTNER.equals(spid)) {
			return false;
		}
		return true;
	}
	
	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public String getMerchantno() {
		return PinjuConstant.TENPAY_PAY_PARTNER;
	}

}
