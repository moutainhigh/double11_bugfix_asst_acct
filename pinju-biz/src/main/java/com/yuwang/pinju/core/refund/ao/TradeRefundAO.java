package com.yuwang.pinju.core.refund.ao;

import java.io.File;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;

public interface TradeRefundAO {

	/**
	 * 保存文件
	 * @param files 文件
	 * @param fileNames 文件名
	 * @param memberId  用户ID
	 * @param nickName  用户名
	 * @return result
	 */
	public Result saveFile(File[] files, String[] fileNames,Long memberId, String nickName);
	
	/**
	 * 查询留言列表信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return Result 留言列表LIST
	 */
	public Result selectRefundLeavewordList(TradeRefundLeavewordDO tradeRefundLeavewordDO);
	
	/**
	 * 查询留言列表信息
	 * @param refundId 退款id
	 * @return Result 留言列表LIST
	 */
	public Result selectRefundLeavewordList(Long refundId);
	
	/**
	 * 插入留言信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言ID
	 */
	public Long insertRefundLeaveword(TradeRefundLeavewordDO tradeRefundLeavewordDO);
}
