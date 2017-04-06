package com.yuwang.pinju.web.module.points;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.points.constants.PointsConstants;
import com.yuwang.points.jms.manager.PointsJmsManager;
import com.yuwang.points.message.RecevieMessage;
import com.yuwang.points.mina.client.PointsClient;
import com.yuwang.points.model.PointsAccountDO;
import com.yuwang.points.model.PointsDictionaryDO;
import com.yuwang.points.model.PointsTradeOrderDO;
import com.yuwang.points.util.PointsDictionaryUtil;


public class AddActionPointsAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3351635675571534610L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private PointsJmsManager pointsJmsManager;
	
	public PointsJmsManager getPointsJmsManager() {
		return pointsJmsManager;
	}


	public void setPointsJmsManager(PointsJmsManager pointsJmsManager) {
		this.pointsJmsManager = pointsJmsManager;
	}

	/*************查询条件**************/
	private Long userId;

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}

	private Double baseValue;
	public Double getBaseValue() {
		return baseValue;
	}

	private Integer tradeChannel;

	public Integer getTradeChannel() {
		return tradeChannel;
	}


	public void setTradeChannel(Integer tradeChannel) {
		this.tradeChannel = tradeChannel;
	}


	private Integer isAdd;
	
	public Integer getIsAdd() {
		return isAdd;
	}


	public void setIsAdd(Integer isAdd) {
		this.isAdd = isAdd;
	}


	public void setBaseValue(Double baseValue) {
		this.baseValue = baseValue;
	}
    
	private String resultMsg;
	
	public String getResultMsg() {
		return resultMsg;
	}


	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	private PointsDictionaryUtil  dictionaryUtil=PointsDictionaryUtil.getInstance();
	public PointsDictionaryUtil getDictionaryUtil() {
		return dictionaryUtil;
	}


	public void setDictionaryUtil(PointsDictionaryUtil dictionaryUtil) {
		this.dictionaryUtil = dictionaryUtil;
	}

	private Long balance;

	public Long getBalance() {
		return balance;
	}


	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public List<PointsDictionaryDO> channelList;

	public List<PointsDictionaryDO> getChannelList() {
		return channelList;
	}


	public void setChannelList(List<PointsDictionaryDO> channelList) {
		this.channelList = channelList;
	}
	
	public String add() {
		try {
			if(null==userId || null==tradeChannel || null==isAdd || null==baseValue)
			{
				resultMsg="请输入的参数不全，必须输入用户id、交易渠道、增减类型、增加金额";
			}else{
				RecevieMessage<PointsTradeOrderDO,String> recevieMessage=PointsClient.updatePointsAccount(userId, 1, tradeChannel,isAdd,baseValue);
				this.channelList=dictionaryUtil.getPointsDictionaryList(com.yuwang.points.constants.PointsConstants.SELLERPOINTS_STAT_CHANNEL);
				resultMsg=recevieMessage.getResult();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage(),e);
		}
		return "success";
	}
	
	public String addByMQ() {
		try {
			if(null==userId || null==tradeChannel || null==isAdd || null==baseValue)
			{
				resultMsg="请输入的参数不全，必须输入用户id、交易渠道、增减类型、增加金额";
			}else{
				boolean isOperate=pointsJmsManager.sendPointsAccount(userId, 1, tradeChannel,isAdd,baseValue);
				this.channelList=dictionaryUtil.getPointsDictionaryList(com.yuwang.points.constants.PointsConstants.SELLERPOINTS_STAT_CHANNEL);
				if(isOperate)
				{
					resultMsg="success";
					
				}else{
					resultMsg="fail";
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage(),e);
		}
		return "success";
	}
}