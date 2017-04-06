package com.yuwang.pinju.core.distribute.ao;

import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * 所有需要的外部接口
 * 
 * @author liyouguo
 * @version 1.0
 * @updated 07-七月-2011 15:14:14
 */
public interface DistributeOutterAO {

	/**
	 * 分销商关注对应的供应商（社区接口）
	 * 
	 * @param supplierId
	 *            --供应商 会员编号
	 * @param memberId
	 *            --分销商 会员编号
	 */
	public void attentSupplier(Long supplierId, Long memberId) throws Exception;

	/**
	 * 判断该会员是否是分销商
	 * 
	 * @param memberId
	 *            --会员编号
	 */
	public boolean checkIsChannel(Long memberId) throws Exception;

	/**
	 * 获取商品详情列表（根据相关的查询条件，支持分页）
	 * 
	 * @param itemQuery
	 *            --商品查询参数，具体可参考ItemQueryEx
	 * 
	 */
	public java.util.List<ItemDO> getItemList(ItemQueryEx itemQuery)
			throws Exception;

	/**
	 * 获取分销商相关社区信息（社区接口）
	 * 
	 * @param memberId
	 */
	public MemberInfoDO getMemberSnsInfo(Long memberId) throws Exception;

	/**
	 * 根据供应商或者分销商查询生成的订单（支持分页）
	 * 
	 * @param distributor
	 *            --分销商 会员编号
	 * @param seller
	 *            --供应商 会员编号（即商品所有者）
	 */
	public java.util.List<OrderDO> getOrderInfo(Long distributor, Long seller)
			throws Exception;

	/**
	 * 根据店铺编号查询出店铺详情（店铺接口） 
	 * 
	 * @param shopId
	 *            --店铺编号
	 */
	public ShopInfoDO getShopInfo(Integer shopId) throws Exception;

	/**
	 * 分销商给供应商发送私信（社区接口）
	 * 
	 * @param content
	 *            --信息内容
	 * @param title
	 *            --私信标题
	 * @param receiver
	 *            --接收者（供应商-会员编号）
	 * @param sender
	 *            --发送者（分销商-会员编号）
	 */
	public void sendPrivateMail(String content, String title, Long receiver,
			Long sender) throws Exception;

	/**
	 * 会员申请成为分销商，需要判断该用户是否已绑定盛付通
	 * 
	 * @param memberId
	 *            --会员编号
	 */
	public boolean setMemberChannel(Long memberId) throws Exception;

}