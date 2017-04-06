package com.yuwang.pinju.core.distribute.ao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.distribute.ao.DistributorAO;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.jms.manager.JmsManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.member.ConcernDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.member.PrivateMailDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopQuery;

/**
 * 分销商&供应商外部功能调用
 * 
 * @author caiwei
 *
 */
public class DistributorAOImpl extends BaseAO implements DistributorAO{

	@Autowired
	private MemberManager memberManager;
	
	@Autowired
	private ShopOpenManager shopOpenManager;
	
	@Autowired
	private ItemManager itemManager;
	
	@Autowired
	private JmsManager jmsManager;
	
	/**
	 * 校验用户是否有支付权限（是否开通财富通）
	 * 
	 * @param userId
	 * @return [true|false]
	 * 			true:开通财富通&已签约
	 * 		   false:未开通财富通||未签约
	 */
	@Override
	public boolean checkPaymentAuthority(Long userId) {
            try {
                MemberPaymentDO memberPaymentDO = memberManager.findBoundMemberPayment(new MemberPaymentDO(userId, MemberPaymentDO.INSTITUTION_TENPAY));
                if(memberPaymentDO !=null && ObjectUtils.nullSafeEquals(memberPaymentDO.getBondStatus(), MemberPaymentDO.BOUND_STATUS_BOUND)){//&& ObjectUtils.nullSafeEquals(memberPaymentDO.getSignAgreement(), MemberPaymentDO.SIGN_AGREEMENT_YES)
                    return true;
                }
            } catch (ManagerException e) {
                log.error("Event=[DistributorAO#checkPaymentAuthority] 获取财付通绑定状态失败",e);
                e.printStackTrace();
            }
            return false;
	}
	
	/**
	 * 判断用户是否绑定财付通帐号
	 * 
	 * @param memberId
	 * 		会员编号
	 * @return
	 */
	@Override
	public boolean checkAccount(Long memberId){
            try {
                MemberPaymentDO memberPaymentDO = memberManager.findBoundMemberPayment(new MemberPaymentDO(memberId, MemberPaymentDO.INSTITUTION_TENPAY));
                if(memberPaymentDO !=null && ObjectUtils.nullSafeEquals(memberPaymentDO.getBondStatus(), MemberPaymentDO.BOUND_STATUS_BOUND)){
                    return true;
                }
            } catch (ManagerException e) {
                log.error("Event=[DistributorAO#checkAccount] 获取财付通绑定状态失败",e);
                e.printStackTrace();
            }
	    return false;
	}
	
	/**
	 * 判断用户是否签约
	 * 
	 * @param memberId
	 * 		会员编号
	 * @return
	 */
	@Override
	public boolean checkAgreement(Long memberId){
            try {
                MemberPaymentDO memberPaymentDO = memberManager.findBoundMemberPayment(new MemberPaymentDO(memberId, MemberPaymentDO.INSTITUTION_TENPAY));
                if(memberPaymentDO !=null && ObjectUtils.nullSafeEquals(memberPaymentDO.getSignAgreement(), MemberPaymentDO.SIGN_AGREEMENT_YES)){
                    return true;
                }
            } catch (ManagerException e) {
                log.error("Event=[DistributorAO#checkAgreement] 获取财付通签定状态失败",e);
                e.printStackTrace();
            }
	    return false;
	}
	
	/**
	 * 用户绑定财付通帐号
	 * 
	 * @param memberId
	 * 		会员编号
	 */
	@Override
	public void bindAccount(Long memberId){
	    
	}
	
	/**
	 * 用户签约
	 * 
	 * @param memberId
	 * 		会员编号
	 */
	@Override
	public void signArgeement(Long memberId){
	    
	}
	
	@Override
	public boolean applyDistributor(Long userId) {
		return false;
	}

	@Override
	public List<Object> queryDistributeTrade(Object ob) {
		return null;
	}

	/**
	 * 查询某一个供应商参与的分销中的商品
	 * 
	 * @param paramList
	 * @return
	 */
	@Override
	public List<DistrbuteSupplierItemDO> querySupplierItems(final List<DistrbuteSupplierItemDO> param) {
		List<Long> ids = new ArrayList<Long>();
		Map<Long, DistrbuteSupplierItemDO> supplierMap = new HashMap<Long, DistrbuteSupplierItemDO>();
		List<ItemDO> itemList = null;
		List<DistrbuteSupplierItemDO> result = new ArrayList<DistrbuteSupplierItemDO>();
		for (DistrbuteSupplierItemDO distrbuteSupplierItemDO : param) {
			ids.add(distrbuteSupplierItemDO.getItemId());
		}
		try {
			itemList = itemManager.queryItemListEx(new ItemQueryEx(ids));
		} catch (ManagerException e) {
			log.error("Event=[DistributorAO#querySupplierItems] 获取供应商商品信息失败",e);
			e.printStackTrace();
		}
		for (DistrbuteSupplierItemDO distrbuteSupplierItemDO : param) {
			supplierMap.put(distrbuteSupplierItemDO.getItemId(), distrbuteSupplierItemDO);
		}
		if (itemList != null) {
			for (ItemDO itemDO : itemList) {
			    DistrbuteSupplierItemDO distrbuteSupplierItemDO = supplierMap.get(itemDO.getId());
			    if (distrbuteSupplierItemDO != null) {
			    itemDO.setPicUrl(itemDO.getPicUrl()+"_160x160."+StringUtils.getFilenameExtension(itemDO.getPicUrl()));
				distrbuteSupplierItemDO.setItemDO(itemDO);
				result.add(distrbuteSupplierItemDO);
			    }
			}
		}
		return result;
	}


	@Override
	public List<DistrbuteSupplierItemDO> querySupplierItems(
			DistribureChannelDO distribureChannelDO) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 所有分销商可以申请的供应商列表
	 * 
	 * @param shopQuery
	 * 			分页及过滤参数
	 * @return
	 */
	@Override
	public ShopQuery querySupplierList(ShopQuery shopQuery) {
		try {
			return shopOpenManager.queryListShopBusinessInfo(shopQuery);
		} catch (ManagerException e) {
			log.error("Event=[DistributorAO#querySupplierList] 获取供应商信息失败",e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 指定的供应商信息列表
	 * 
	 * @param shopQuery
	 * 			分页及参数
	 * @return
	 */
	@Override
	public List<DistributeSupplierDO> querySupplierList(List<DistributeSupplierDO> param) {
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer, ShopInfoDO> shopInfoMap = new HashMap<Integer, ShopInfoDO>();
		ShopQuery shopQuery = null;
		for (DistributeSupplierDO distributeSupplierDO : param) {
			ids.add(distributeSupplierDO.getShopId());
		}
		try {
			shopQuery = shopOpenManager.queryListShopBusinessInfo(new ShopQuery(ids));
			for (ShopInfoDO shopInfoAllDO : (shopQuery.getResultList()!=null?shopQuery.getResultList():(new ArrayList<ShopInfoDO>(0)))) {
				shopInfoMap.put(shopInfoAllDO.getShopId(), shopInfoAllDO);
			}
			for (DistributeSupplierDO distributeSupplierDO : param) {
				distributeSupplierDO.setShopInfoAllDO(shopInfoMap.get(distributeSupplierDO.getShopId()));
			}
		} catch (ManagerException e) {
			log.error("Event=[DistributorAO#querySupplierList] 获取供应商信息失败",e);
			e.printStackTrace();
		}
		return param;
	}
	
	/**
	 * 指定的供应商信息列表
	 * 
	 * @param ids
	 * 			供应商店铺ID
	 * @return
	 */
	@Override
	public Map<Integer, ShopInfoDO> querySupplierListById(List<Integer> shopIds) {
		Map<Integer, ShopInfoDO> shopInfoMap = new HashMap<Integer, ShopInfoDO>();
		ShopQuery shopQuery = null;
		try {
			shopQuery = shopOpenManager.queryListShopBusinessInfo(new ShopQuery(shopIds));
		} catch (ManagerException e) {
			log.error("Event=[DistributorAO#querySupplierList] 获取供应商信息失败",e);
			e.printStackTrace();
		}
		for (ShopInfoDO shopInfoAllDO : (shopQuery.getResultList()!=null?shopQuery.getResultList():(new ArrayList<ShopInfoDO>(0)))) {
			shopInfoMap.put(shopInfoAllDO.getShopId(), shopInfoAllDO);
		}
		return shopInfoMap;
	}
	
	/**
	 * 跟据条件查询单个供应商信息
	 * 
	 * @param param
	 * 			查询参数
	 * @return
	 */
	public DistributeSupplierDO querySupplier(DistributeSupplierDO param){
		try {
			if (param.getShopId() != null) {
				param.setShopBusinessInfoDO(shopOpenManager.queryShopInfoByShopId(param.getShopId()));
			}
		} catch (ManagerException e) {
			log.error("Event=[DistributorAO#querySupplier] 获取供应商信息失败",e);
			e.printStackTrace();
		}
		return param;
	}
	
	@Override
	public List<DistributeSupplierDO> querySupplierList(
			DistribureChannelDO distribureChannelDO) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 发送关注某供应商信息
	 * 
	 * @param concernDO
	 * @return
	 */
	public Boolean sendConcernDO(ConcernDO concernDO){
	    try {
		jmsManager.sendConcern(concernDO);
	    } catch (Exception e) {
		log.error("Event=[DistributorAO#sendConcernDO] 发送关注信息失败",e);
		e.printStackTrace();
		return false;
	    }
	    return true;
	}
	
	/**
	 * 发送与某供应商的私信
	 * 
	 * @param privateMailDO
	 * @return
	 */
	public Boolean sendMail(PrivateMailDO privateMailDO) {
		try {
			jmsManager.sendPrivateMail(privateMailDO);
		} catch (Exception e) {
			log.error("Event=[DistributorAO#sendMail] 发送私信失败", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
