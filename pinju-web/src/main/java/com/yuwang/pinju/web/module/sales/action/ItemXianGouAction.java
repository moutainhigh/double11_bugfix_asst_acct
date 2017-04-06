package com.yuwang.pinju.web.module.sales.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.Constant.ItemXianGouConstant;
import com.yuwang.pinju.common.ItemXianGouUtil;
import com.yuwang.pinju.core.captcha.CaptchaManager;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.resultcode.XianGouResultCode;
import com.yuwang.pinju.core.order.ao.OrderCreationAO;
import com.yuwang.pinju.core.sales.ao.ItemXianGouAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.member.MemberLoginDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.module.member.screen.MemberLoginAction;

public class ItemXianGouAction extends BaseAction{
	
	private String errMsg="OK";
	private ItemXianGouAO itemXianGouAO;
	private ItemXianGouDO itemXianGouDO;
	private String code;
	private MemberLoginDO login;
	private CaptchaManager captchaManager;
	private int resFlag=0;
	private ItemDO itemDO;
	private List<ItemDO> itemlist ;
	private OrderCreationAO orderCreationAO;
    private ItemDO itemDO1;
    private ItemDO itemDO2;
    private ItemDO itemDO3;
    private ItemDO itemDO4;
    private ItemDO itemDO5;
	
	public ItemXianGouAction() {
		login = new MemberLoginDO();
		itemlist = new ArrayList<ItemDO>();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 显示页面
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-5
	 * @update:2011-9-5
	 * @return
	 */
	public String showPage() {
		login.setSid(PinjuCookieManager.getHashSessionId(MemberLoginAction.class));
		return SUCCESS;
	}

	/**
	 * Created on 2011-9-7 
	 * <p>Discription:[异步校验限购码，供立即购买按钮使用]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String validateCode() {
		JSONObject jsonObject = new JSONObject();
		try {
			Long[] itemid = getLongs("itemid");
			String[] xianGouCode = getStrings("xianGouCode");
			ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
			OrderCreationVO orderCreationVO = new OrderCreationVO();
			orderCreationVO.setItemId(itemid);
			orderCreationVO.setDiscountCode(xianGouCode);
			Result result = orderCreationAO.activityCheck(orderCreationVO);
			if (!result.isSuccess()) {
				String itemId  = String.valueOf((Long)result.getModel("itemid"));
				if (StringUtils.isNotEmpty(itemId)) {
					String itemDetailURl = "http://www.pinju.com/detail/".concat(itemId).concat(".htm");
					jsonObject.put(ERROR, ResultCodeMsg.getResultMessage(result.getResultCode(),itemDetailURl));
				}else {
					jsonObject.put(ERROR, ResultCodeMsg.getResultMessage(result.getResultCode()));
				}
			}else {
				jsonObject.put(SUCCESS, SUCCESS);
			}
		} catch (Exception e) {
			jsonObject.put(ERROR, Message.getMessage(MessageName.OPERATE_FAILED));
			log.error("异步校验限购码，供立即购买按钮使用", e);
		} finally{
			try {
				ServletActionContext.getResponse().getWriter().println(jsonObject);
				ServletActionContext.getResponse().getWriter().flush();
				ServletActionContext.getResponse().getWriter().close();
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	/**
	 * 校验验证码，领取限购码
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-5
	 * @update:2011-9-5
	 * @return
	 */
	public String getXianGouCode() {
		JSONObject jsonObject = new JSONObject();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			if (!captchaManager.validate(getString("sid"), getString("captcha"))) {	
				   errMsg = ResultCodeMsg.getResultMessage(XianGouResultCode.XIANGOU_CHECKCODE_FAIL);
				   jsonObject.put("resFlag", 7);
				   jsonObject.put("flag", "error");
				   jsonObject.put("msg", errMsg);
				   tryC(jsonObject);
				   return null;
			}
			if(!checkXianGou()){
				jsonObject.put("resFlag", resFlag);
				jsonObject.put("flag","error");
				jsonObject.put("msg", errMsg);
				tryC(jsonObject);
				return null;
			}
			
			//生成限购码，插入记录，返回显示
			Result result = itemXianGouAO.getItemXianGouCode(itemXianGouDO);
			if (!result.isSuccess()) {
				errMsg =ResultCodeMsg.getResultMessage(XianGouResultCode.XIANGOU_OPERATE_FAILED);
				jsonObject.put("resFlag", 8);
				jsonObject.put("flag", "error");
				jsonObject.put("msg", errMsg);
				tryC(jsonObject);
				return null;			
			}
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			if (!loginInfo.isLogin()) {
				jsonObject.put("login", "false");
			}else {
				jsonObject.put("login", "true");			
			}
			jsonObject.put("size", itemlist.size());
			jsonObject.put("itemDO1", itemDO1);
			jsonObject.put("itemDO2", itemDO2);
			jsonObject.put("itemDO3", itemDO3);
			jsonObject.put("itemDO4", itemDO4);
			jsonObject.put("itemDO5", itemDO5);
			jsonObject.put("flag", "success");
			jsonObject.put("resFlag", 9);
			jsonObject.put("itemDO", itemDO);
			Long codeLong = (Long) result.getModel("code");
			code = ItemXianGouUtil.encode(codeLong);
			jsonObject.put("code", code);
			jsonObject.put("errMsg", errMsg);
			tryC(jsonObject);
		} catch (Exception e) {
			jsonObject.put("flag", "error");
			log.error("ItemXianGouAction#getXianGouCode itemDO="+itemDO);
			jsonObject.put(ERROR, Message.getMessage(MessageName.OPERATE_FAILED));
			tryC(jsonObject);
			log.error("ItemXianGouAction#getXianGouCode fail",e);
		}
		return null;
	}
	
	private void tryC(JSONObject jsonObject){
		try {
			ServletActionContext.getResponse().getWriter().println(jsonObject);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			jsonObject.put("flag","error");
			log.error("Event=[ItemXianGouAction#getXianGouCode] 获取限购码失败！！！");
		}
	}
	
	/**
	 * 限购码的前置条件校验
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-1
	 * @update:2011-9-1
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public boolean checkXianGou(){
	    //验证码不再绑定商品
//		itemId = getLong("itemId");
//		if (itemId==0) {
//			errMsg = "该地址不存在，请检查该请求地址是否拼写正确 ！！！";
//			resFlag =0;
//			return false;
//		}
	    
	    /*
	     * 1，首先查询Ids总数/5 得到总页面，随机取其中一页即可;
	     * 2，根据得到的其中一条记录，判断活动开始与否，有效期结束与否；
	     * 3，将这个5个Ids显示到页面
	     */
	    Result listResult = itemXianGouAO.getItemXianGouDOs();
	    List<ItemXianGouDO> list = (List<ItemXianGouDO>) listResult.getModel("list");
	    itemXianGouDO = list.get(0);
	    //判断该商品是否为限购状态
	    if (itemXianGouDO==null) {
            errMsg = "系统繁忙，请重试！！！";
            resFlag =11;
            return false;
        }
        
        if (itemXianGouDO.getStatus()==ItemXianGouConstant.XIANGOU_ACTIVE_END || itemXianGouDO.getExpiryEnd().getTime()<new Date().getTime()) {
            errMsg = ResultCodeMsg.getResultMessage(XianGouResultCode.XIANGOU_ACTIVE_CLOSED);
            resFlag =4;
            return false;
        }
        ItemDO itemDO = null;
	    for (int i = 0; i < list.size(); i++)
        {
	        Long itemId = list.get(i).getItemId();
	        Result result = itemXianGouAO.getItemDOById(itemId);
	        if(!result.isSuccess()){
	            errMsg = result.getResultCode();
	            resFlag =1;
	            return false;
	        }
	        //判断该商品状态是否为用户上架或运营上架
	        itemDO = (ItemDO) result.getModel("itemDO");
	        if (i==0)
            {
	            itemDO1 = itemDO;
            }else if (i==1) {
                itemDO2 = itemDO;
            }else if (i==2) {
                itemDO3 = itemDO;
            }else if (i==3) {
                itemDO4 = itemDO;
            }else if (i==4) {
                itemDO5 = itemDO;
            }
	        if (itemDO!=null)
            {
                itemlist.add(itemDO);
            }
        }
		
	    if (itemlist.size()!=0)
        {
            return true;
        }else {
            errMsg = "查询推荐商品失败，请重试！！！";
            return false;
        }
		
//		if (itemDO ==null) {
//			errMsg = "该商品不存在！！！";
//			resFlag =10;
//			return false;
//		}
//		
//		if (itemDO.getStatus()!=ItemConstant.STATUS_TYPE_0 && itemDO.getStatus()!=ItemConstant.STATUS_TYPE_1) {
//			errMsg = "该商品已下架或删除！！！"+(itemDO.getStatus());
//			resFlag =2;
//			return false;
//		}
	    
//		Result res = itemXianGouAO.getItemXianGouDOByItemId(itemId);
//		if(!res.isSuccess()){
//			errMsg = ResultCodeMsg.getResultMessage(res.getResultCode());
//			resFlag =3;
//			return false;
//		}
//		itemXianGouDO=(ItemXianGouDO) res.getModel("itemXianGouDO");
		
		//判断该商品限购码数量是否为0
//		Result rec = itemXianGouAO.getItemXianGouDOCounts(itemXianGouDO);
//		if(!rec.isSuccess()){
//			errMsg = ResultCodeMsg.getResultMessage(rec.getResultCode());
//			resFlag =5;
//			return false;
//		}
//		Long free = (Long) rec.getModel("free");
//		if(free<=0){
//			errMsg = ResultCodeMsg.getResultMessage(XianGouResultCode.XIANGOU_CODE_NOT_ENOUGH);
//			resFlag =6;
//			return false;
//		}else {
//			errMsg = ResultCodeMsg.getResultMessage(XianGouResultCode.XIANGOU_BEFORE_GETCODE);
//			return true;
//		}
	}
	
	
	/**
	 * 校验后，显示验证码
	 * @Project:pinju-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-5
	 * @update:2011-9-5
	 * @return
	 */
	public String getValCode() {
		JSONObject date = new JSONObject();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		if(!checkXianGou()){
			date.put("flag","error");
		}else {			
			date.put("flag", "success");
		}
		date.put("msg", errMsg);
		tryC(date);
		return null;
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public void setItemXianGouAO(ItemXianGouAO itemXianGouAO) {
		this.itemXianGouAO = itemXianGouAO;
	}

	public ItemXianGouDO getItemXianGouDO() {
		return itemXianGouDO;
	}

	public void setItemXianGouDO(ItemXianGouDO itemXianGouDO) {
		this.itemXianGouDO = itemXianGouDO;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MemberLoginDO getLogin() {
		return login;
	}

	public void setLogin(MemberLoginDO login) {
		this.login = login;
	}

	public CaptchaManager getCaptchaManager() {
		return captchaManager;
	}

	public void setCaptchaManager(CaptchaManager captchaManager) {
		this.captchaManager = captchaManager;
	}

	public int getResFlag() {
		return resFlag;
	}

	public void setResFlag(int resFlag) {
		this.resFlag = resFlag;
	}

	public ItemDO getItemDO() {
		return itemDO;
	}

	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
	}

	public OrderCreationAO getOrderCreationAO() {
		return orderCreationAO;
	}

	public void setOrderCreationAO(OrderCreationAO orderCreationAO) {
		this.orderCreationAO = orderCreationAO;
	}

    public List<ItemDO> getItemlist()
    {
        return itemlist;
    }

    public void setItemlist(List<ItemDO> itemlist)
    {
        this.itemlist = itemlist;
    }


    public ItemDO getItemDO1()
    {
        return itemDO1;
    }

    public void setItemDO1(ItemDO itemDO1)
    {
        this.itemDO1 = itemDO1;
    }

    public ItemDO getItemDO2()
    {
        return itemDO2;
    }

    public void setItemDO2(ItemDO itemDO2)
    {
        this.itemDO2 = itemDO2;
    }

    public ItemDO getItemDO3()
    {
        return itemDO3;
    }

    public void setItemDO3(ItemDO itemDO3)
    {
        this.itemDO3 = itemDO3;
    }

    public ItemDO getItemDO4()
    {
        return itemDO4;
    }

    public void setItemDO4(ItemDO itemDO4)
    {
        this.itemDO4 = itemDO4;
    }

    public ItemDO getItemDO5()
    {
        return itemDO5;
    }

    public void setItemDO5(ItemDO itemDO5)
    {
        this.itemDO5 = itemDO5;
    }
	
}
