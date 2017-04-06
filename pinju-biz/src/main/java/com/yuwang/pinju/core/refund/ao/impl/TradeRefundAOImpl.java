package com.yuwang.pinju.core.refund.ao.impl;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.refund.ao.TradeRefundAO;
import com.yuwang.pinju.core.refund.manager.TradeRefundManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;

public class TradeRefundAOImpl extends BaseAO implements TradeRefundAO{

	private final static Log log = LogFactory.getLog(TradeRefundAOImpl.class);
	private TradeRefundManager tradeRefundManager;
	private FileStorageManager fileStorageManager;


	/**
	 * 保存文件
	 * @param files 文件
	 * @param fileNames 文件名
	 * @param memberId  用户ID
	 * @param nickName  用户名
	 * @return result
	 */
	public Result saveFile(File[] files, String[] fileNames,Long memberId, String nickName) {
		ResultSupport result = new ResultSupport();
		try {
			String[] retFileNames = fileStorageManager.saveImage(files,fileNames, memberId, nickName);
			result.setSuccess(true);
			result.getModels().put("fileNames", retFileNames);
		} catch (ManagerException e) {
			log.error(e, e);
			result.setSuccess(false);
			result.setResultCode("文件保存出错");
		}
		return result;
	}

	/**
	 * 查询留言列表信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return Result 留言列表LIST
	 */
	public Result selectRefundLeavewordList(TradeRefundLeavewordDO tradeRefundLeavewordDO){
		Result result = new ResultSupport();
		try {
			result.setModel("list", tradeRefundManager.selectRefundLeavewordList(tradeRefundLeavewordDO));
			result.setSuccess(true);
		} catch (ManagerException e) {
			log.error("TradeRefundAOImpl 查询留言信息  error, " +  e);
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 查询留言列表信息
	 * @param refundId 退款id
	 * @return Result 留言列表LIST
	 */
	public Result selectRefundLeavewordList(Long refundId){
		Result result = new ResultSupport();
		try {
			result.setModel("list", tradeRefundManager.selectRefundLeavewordList(refundId));
			result.setSuccess(true);
		} catch (ManagerException e) {
			log.error("TradeRefundAOImpl 查询留言信息  error, " +  e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 插入留言信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言ID 0为错误
	 */
	public Long insertRefundLeaveword(TradeRefundLeavewordDO tradeRefundLeavewordDO){
		try {
			return tradeRefundManager.insertRefundLeaveword(tradeRefundLeavewordDO);
		} catch (ManagerException e) {
			log.error("TradeRefundAOImpl 插入留言信息  error, " +  e);
			return 0L;
		}
	}
	
	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}
	
	public void setTradeRefundManager(TradeRefundManager tradeRefundManager) {
		this.tradeRefundManager = tradeRefundManager;
	}
}
