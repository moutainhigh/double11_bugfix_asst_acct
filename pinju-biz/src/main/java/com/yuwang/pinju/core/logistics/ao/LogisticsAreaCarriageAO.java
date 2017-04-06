package com.yuwang.pinju.core.logistics.ao;

import java.util.List;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;

public interface LogisticsAreaCarriageAO {
	/**
	 * 查询地区物流运费信息列表
	 * @param logisticsCarriageDO
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public Result selectLogisticsAreaCarriageList(LogisticsAreaCarriageDO logisticsAreaCarriageDO);
	
	/**
	 * 删除地区物流运费信息
	 * @param logisticsAreaCarriageDO
	 * @return 受影响的记录数
	 */
	public int deleteLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO);
	
	/**
	 * 修改地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list);
	
	/**
	 * 修改地区物流运费
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO);


	/**
	 * 保存地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 */
	public boolean insertLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list);

	
	/**
	 * 保存地区物流运费
	 * @param logisticsCarriageDO
	 * @return ID
	 */
	public Long insertLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO);
	
	/**
	 * Created on 2011-7-12
	 * @see 对应对象集合
	 * @param templateId 模板编号 
	 * @param areaId 地区编号
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<LogisticsAreaCarriageDO> queryLACarriageDO(Long templateId,Integer areaId);
	
	/**
	 * 
	 * Created on 2011-7-29
	 * <p>Discription: 获取商品对应的地区运费</p>
	 * @param templateId
	 * @param areaId
	 * @param areaName
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<LogisticsAreaCarriageDO> queryLACarriageDO(Long templateId,Integer areaId,String areaName);
}
