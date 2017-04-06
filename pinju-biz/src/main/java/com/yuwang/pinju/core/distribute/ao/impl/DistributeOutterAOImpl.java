/**
 * 
 */
package com.yuwang.pinju.core.distribute.ao.impl;

import java.util.List;

import com.yuwang.pinju.core.distribute.ao.DistributeOutterAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * @author zba
 *
 */
public class DistributeOutterAOImpl implements DistributeOutterAO {

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.distribute.ao.DistributeOutterAO#attentSupplier(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void attentSupplier(Long supplierId, Long memberId) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.distribute.ao.DistributeOutterAO#checkIsChannel(java.lang.Long)
	 */
	@Override
	public boolean checkIsChannel(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.distribute.ao.DistributeOutterAO#getItemList(com.yuwang.pinju.domain.item.ItemQueryEx)
	 */
	@Override
	public List<ItemDO> getItemList(ItemQueryEx itemQuery) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.distribute.ao.DistributeOutterAO#getMemberSnsInfo(java.lang.Long)
	 */
	@Override
	public MemberInfoDO getMemberSnsInfo(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.distribute.ao.DistributeOutterAO#getOrderInfo(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<OrderDO> getOrderInfo(Long distributor, Long seller)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.distribute.ao.DistributeOutterAO#getShopInfo(java.lang.Integer)
	 */
	@Override
	public ShopInfoDO getShopInfo(Integer shopId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.distribute.ao.DistributeOutterAO#sendPrivateMail(java.lang.String, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void sendPrivateMail(String content, String title, Long receiver,
			Long sender) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.distribute.ao.DistributeOutterAO#setMemberChannel(java.lang.Long)
	 */
	@Override
	public boolean setMemberChannel(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
