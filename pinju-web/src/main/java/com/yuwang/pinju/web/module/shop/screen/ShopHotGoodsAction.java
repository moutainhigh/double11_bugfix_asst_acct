package com.yuwang.pinju.web.module.shop.screen;

import java.io.InputStream;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.activemq.util.ByteArrayInputStream;

import com.yuwang.pinju.Constant.ItemKeyConstants;
import com.yuwang.pinju.common.MessageDigestUtil;
import com.yuwang.pinju.core.bi.manager.BiShopManager;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.bi.QueryShopSalesRankDO;
import com.yuwang.pinju.domain.bi.ShopSalesRankDO;
import com.yuwang.pinju.domain.member.SellerQualityInfoDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

public class ShopHotGoodsAction extends BaseWithUserAction{
	private MemberAO memberAO;
	private InputStream jsonresult;
	private String memberId;
	private BiShopManager biShopManager;
	private String sign;
	@Override
	public String execute() throws Exception {
		try{
			if(sign==null|| sign.equals("")){
				jsonresult = new ByteArrayInputStream("signError".getBytes());
				return "success";
			}
			if(memberId==null) {
				jsonresult = new ByteArrayInputStream("memberIdError".getBytes());
				return "success";
			}
			String signcode = MessageDigestUtil.hexHashBySHA(memberId, ItemKeyConstants.OUT_URL_FROM_COMMUNITY);
			if(!sign.equals(signcode)){
				jsonresult = new ByteArrayInputStream("signError".getBytes());
				return "success";
			}
			String[] memberstr = memberId.split(",");
			JSONArray jsonarray = new JSONArray();
			for(int i=0;i<memberstr.length;i++){
				JSONObject json = new JSONObject();
				Result result = memberAO.getMemberShopQuality(Long.valueOf(memberstr[i]));
				if (result.isSuccess()) {
					   SellerQualityInfoDO sqi = result.getModel(SellerQualityInfoDO.class);
					   json.put("sqi", sqi);
				}else{
					continue;
				}
				QueryShopSalesRankDO queryShopSalesRankDO = new QueryShopSalesRankDO();
				queryShopSalesRankDO.setMemberId(Long.valueOf(memberstr[i]));
				queryShopSalesRankDO.setShopId(this.getUserShopId(Long.valueOf(memberstr[i])));
				List<ShopSalesRankDO> list = biShopManager.queryShopSalesRank(queryShopSalesRankDO);
				if(list==null) continue;
				json.put("list", list);
				jsonarray.add(json);
			}
			jsonresult = new ByteArrayInputStream(jsonarray.toString().getBytes("UTF-8"));
		}catch(Exception e){
			log.error("调用接口发生异常！",e);
			jsonresult = new ByteArrayInputStream("error".getBytes());
		}
		return "success";
	}
	public MemberAO getMemberAO() {
		return memberAO;
	}
	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}
	public InputStream getJsonresult() {
		return jsonresult;
	}
	public void setJsonresult(InputStream jsonresult) {
		this.jsonresult = jsonresult;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public BiShopManager getBiShopManager() {
		return biShopManager;
	}
	public void setBiShopManager(BiShopManager biShopManager) {
		this.biShopManager = biShopManager;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

}
