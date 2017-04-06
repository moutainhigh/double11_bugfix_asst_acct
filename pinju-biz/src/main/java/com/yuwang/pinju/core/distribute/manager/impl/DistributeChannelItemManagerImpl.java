/**
 * 
 */
package com.yuwang.pinju.core.distribute.manager.impl;

import static com.yuwang.pinju.core.constant.distribute.DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_DISFAVOR;
import static com.yuwang.pinju.core.constant.distribute.DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_FAVOR;
import static com.yuwang.pinju.core.constant.distribute.DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_SELLING;
import static com.yuwang.pinju.core.constant.distribute.DistributeConstant.DISTRIBUTE_CHANNEL_ITEM_STOP_SELLING;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.distribute.dao.DistributeChannelItemDAO;
import com.yuwang.pinju.core.distribute.dao.DistributorDAO;
import com.yuwang.pinju.core.distribute.manager.DistributeChannelItemManager;
import com.yuwang.pinju.core.util.DES3Encrypt;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemDO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;

/**
 * 分销商商品Manager
 * 
 * @author caiwei
 *
 */
public class DistributeChannelItemManagerImpl extends BaseManager implements DistributeChannelItemManager {

	@Autowired
	private DistributeChannelItemDAO distributeChannelItemDAO;
	
	@Autowired
	private DistributorDAO distributorDAO;
	
	/**
	 * 分销商商品上架
	 * 
	 * @param param
	 * 			分销商商品
	 * @return
	 */
	public Boolean addDistributeChannelItem(DistributeChannelItemDO param) {
		try {
			//查找此分销商是否分销过此商品
			Integer count = distributeChannelItemDAO.getCount(new DistributeChannelItemParamDO(param.getItemId(), param.getChannelId()));
			//存在分销商品记录则做分销商品重新分销操作
			if (ObjectUtils.nullSafeEquals(count, new Integer(1))) {
				param.setOriginalStatus(DISTRIBUTE_CHANNEL_ITEM_STOP_SELLING);
				param.setStatus(DISTRIBUTE_CHANNEL_ITEM_SELLING);
				distributeChannelItemDAO.update(param);
			//不存在分销商品记录则做分销商品插入操作
			}else if(ObjectUtils.nullSafeEquals(count, new Integer(0))){
				param.setStatus(DISTRIBUTE_CHANNEL_ITEM_SELLING);
				distributeChannelItemDAO.save(param);
			//多条记录则抛出异常
			}else {
				throw new RuntimeException("某一分销商存在多条商品记录");
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error("Event=[DistributeChannelItemManager#save] 分销商商品上架操作失败", e);
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 分销商商品下架
	 * 
	 * @param param
	 * 			分销商品
	 * @return
	 */
	public Boolean discardDistributeChannelItem(DistributeChannelItemDO param){
		param.setOriginalStatus(DISTRIBUTE_CHANNEL_ITEM_SELLING);
		param.setStatus(DISTRIBUTE_CHANNEL_ITEM_STOP_SELLING);
		try {
			distributeChannelItemDAO.update(param);
			return Boolean.TRUE;
		} catch (DaoException e) {
			log.error("Event=[DistributeChannelItemManager#discardDistributeChannelItem] 分销商商品下架操作失败", e);
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 查询分销商品列表
	 * 
	 * @param param
	 * 			查询参数
	 * @return
	 */
	public List<DistributeChannelItemDO> findDistributeChannelItemByCondition(DistributeChannelItemParamDO param) {
		//上架状态
		param.setStatus(DISTRIBUTE_CHANNEL_ITEM_SELLING);
		try {
			return distributeChannelItemDAO.findDistributorChannelItemDOByCondition(param);
		} catch (DaoException e) {
			log.error("Event=[DistributeChannelItemManager#findDistributeChannelItemByCondition] 查询分销商商品列表失败", e);
			e.printStackTrace();
		}
		return new ArrayList<DistributeChannelItemDO>();
	}
	
	/**
	 * 查询分销商品总数
	 * 
	 * @param param
	 * 			查询参数
	 * @return
	 */
	public Integer getCount(DistributeChannelItemParamDO param){
		//上架状态
		param.setStatus(DISTRIBUTE_CHANNEL_ITEM_SELLING);
		try {
			return distributeChannelItemDAO.getCount(param);
		} catch (DaoException e) {
			log.error("Event=[DistributeChannelItemManager#getCount] 查询分销商商品总数失败", e);
			e.printStackTrace();
		}
		return new Integer(0);
	}
	
	/**
	 * 商品支持、反对信息更新
	 * 
	 * @param channelId
	 * 			分销商ID[加密]
	 * @param itemId
	 * 			商品ID
	 * @param type
	 * 			[1：支持、2：反对]
	 * @return
	 */
	@Override
	public Boolean channelItemReputionUpdate(String memberId, Long itemId, Integer type){
		try {
		    if (StringUtils.hasText(memberId) && !ObjectUtils.nullSafeEquals(memberId, "0") && type != null){
		    	//解密分销商ID
		    	Long memberIdL = Long.valueOf(DES3Encrypt.getInstance().decrypt(memberId));
		    	DistributeDistributorDO distributorDO = distributorDAO.findDistributorByMemberId(memberIdL);
		    	if (distributorDO.getId() != null) {
		    		DistributeChannelItemDO param = new DistributeChannelItemDO(itemId, distributorDO.getId());
		    		if (ObjectUtils.nullSafeEquals(type, DISTRIBUTE_CHANNEL_ITEM_FAVOR)) {
		    			param.setAgreeCount(1);
		    			param.setOpposeCount(0);
		    		}else if(ObjectUtils.nullSafeEquals(type, DISTRIBUTE_CHANNEL_ITEM_DISFAVOR)){
		    			param.setOpposeCount(1);
		    			param.setAgreeCount(0);
		    		}else {
		    			return Boolean.FALSE;
		    		}
		    		distributeChannelItemDAO.update(param);
		    		return Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Event=[DistributeChannelItemManager#updateChannelItemByCondition] 分销商商品更新操作失败", e);
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}
}
