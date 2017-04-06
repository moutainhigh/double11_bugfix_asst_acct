package com.yuwang.pinju.core.distribute.ao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.member.ConcernDO;
import com.yuwang.pinju.domain.member.PrivateMailDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopQuery;

/**
 * 分销商&供应商外部功能调用
 * 
 * @author caiwei
 *
 */
public interface DistributorAO {
	/**
	 * 申请成为分销商
	 * @param userId
	 * @return
	 */
	boolean applyDistributor(Long userId);
	
	/**
	 * 校验用户是否有支付权限（是否开通盛付通）
	 * 
	 * @param userId
	 * @return [true|false]
	 * 			true:开通盛付通
	 * 		   false:未开通盛付通
	 */
	boolean checkPaymentAuthority(Long userId);
	
	/**
	 * 所有分销商可以申请的供应商列表
	 * 
	 * @param shopQuery
	 * 			分页及过滤参数
	 * @return
	 */
	ShopQuery querySupplierList(ShopQuery shopQuery);
	
	/**
	 * 指定的供应商信息列表
	 * 
	 * @param distributeSupplierDO
	 * 			id参数
	 * @return
	 */
	List<DistributeSupplierDO> querySupplierList(List<DistributeSupplierDO> param);
	
	/**
	 * 所有分销商对应条件供应商列表
	 * @return
	 */
	List<DistributeSupplierDO> querySupplierList(DistribureChannelDO distribureChannelDO);
	
	/**
	 * 跟据条件查询单个供应商信息
	 * 
	 * @param param
	 * @return
	 */
	DistributeSupplierDO querySupplier(DistributeSupplierDO param);
	
	/**
	 * 查询某一个供应商参与的分销中的商品
	 * 
	 * @param paramList
	 * @return
	 */
	List<DistrbuteSupplierItemDO> querySupplierItems(List<DistrbuteSupplierItemDO> paramList);
	
	/**
	 * 查询某一个分销商对应的分销商品
	 * @param distribureChannelDO
	 * @return
	 */
	List<DistrbuteSupplierItemDO> querySupplierItems(DistribureChannelDO distribureChannelDO);
	
	/**
	 * 查询某个分销商的分销交易
	 * @param distributorId
	 * @return
	 */
	List<Object> queryDistributeTrade(Object ob);
	
	/**
	 * 发送关注某供应商信息
	 * 
	 * @param concernDO
	 * @return
	 */
	Boolean sendConcernDO(ConcernDO concernDO);
	
	/**
	 * 发送与某供应商的私信
	 * 
	 * @param privateMailDO
	 * @return
	 */
	Boolean sendMail(PrivateMailDO privateMailDO);

	/**
	 * 用户签约
	 * 
	 * @param memberId
	 * 		会员编号
	 */
	void signArgeement(Long memberId);

	/**
	 * 用户绑定财付通帐号
	 * 
	 * @param memberId
	 * 		会员编号
	 */
	void bindAccount(Long memberId);

	/**
	 * 判断用户是否签约
	 * 
	 * @param memberId
	 * 		会员编号
	 * @return
	 */
	boolean checkAgreement(Long memberId);

	/**
	 * 判断用户是否绑定财付通帐号
	 * 
	 * @param memberId
	 * 		会员编号
	 * @return
	 */
	boolean checkAccount(Long memberId);

	/**
	 * 指定的供应商信息列表
	 * 
	 * @param ids
	 * 			供应商店铺ID
	 * @return
	 */
	abstract Map<Integer, ShopInfoDO> querySupplierListById(List<Integer> ids);
}
