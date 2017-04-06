package com.yuwang.pinju.web.module.refund.action;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.refund.ao.ItemPropertyAO;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundBaseAO;
import com.yuwang.pinju.core.util.DateUtil;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

public class BuyerRefundListAction implements Action{

	private final Log log =LogFactory.getLog(BuyerRefundListAction.class);
	
	private RefundApplyAO refundApplyAO;
	private ItemPropertyAO itemPropertyAO;

	private List<RefundDO> refundList;
	private Map<Long, ItemDO> itemMap;
	private Map<Long, OrderItemDO> orderItemMap;
	//商品属性
	private Map<String, List<ItemPropertyVO>> itemPropertyMap;
	
	private String refundId;
	private String orderId;
	private String seltState;
	
	private String beginDate;
	private String endDate;

	private Paginator paginator;

	private Map<Long,Date> timesOut = new HashMap<Long,Date>();
	private RefundBaseAO refundBaseAO;
	
	/**
	 * 统计收到的退款总数
	 */
	private Long sellerRefundCount;
	public Long getSellerRefundCount() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sellerId", CookieLoginInfo.getCookieLoginInfo().getMemberId()); //用户ID
		map.put("beginDate", DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMMdd 00:00:00"));
		map.put("endDate", DateFormatUtils.format(new Date(), "yyyyMMdd HH:mm:ss"));
		
		sellerRefundCount = (long) refundApplyAO.loadRefundApplyInfoCount(map);
		return sellerRefundCount == null ? 0L : sellerRefundCount;
	}
	
	public String execute() {
		if(log.isInfoEnabled()){
			log.info("Event=[BuyerRefundListAction.execute() start]"+ new Date().toString());
		}
		itemMap = new HashMap<Long, ItemDO>();
		orderItemMap = new HashMap<Long, OrderItemDO>();
		itemPropertyMap = new HashMap<String, List<ItemPropertyVO>>();
		// 查询操作
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("buyerId", CookieLoginInfo.getCookieLoginInfo().getMemberId()); //用户ID
		if(paginator==null){
			paginator = new Paginator();
			if(StringUtils.isBlank(beginDate)){
				Date _beginDate = DateUtils.addMonths(new Date(), -3);
				beginDate = DateFormatUtils.format(_beginDate, "yyyy-MM-dd 00:00:00");
				String datetime = DateFormatUtils.format(_beginDate, "yyyyMMdd 00:00:00");
				map.put("beginDate", datetime);
			}
			if(StringUtils.isBlank(endDate)){
				endDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
				String datetime = DateFormatUtils.format(new Date(), "yyyyMMdd HH:mm:ss");
				map.put("endDate", datetime);
			}
			this.handleRefundList(map);
			return SUCCESS;
		}
		
		// 查询操作
		if(StringUtils.isNotBlank(refundId)) map.put("id", refundId);
		if(StringUtils.isNotBlank(orderId)) map.put("orderId", orderId);

		//日期时间
		if(StringUtils.isNotBlank(beginDate)){
			String _beginDate=beginDate.replace("-", "");
			map.put("beginDate", _beginDate);
		}
		if(StringUtils.isNotBlank(endDate)) {
			String _endDate=endDate.replace("-", "");
			map.put("endDate", _endDate);
		}
		
		// 退款状态
		if(seltState.compareTo("all") !=0){
			if(seltState.compareTo("1")==0) map.put("refundState", RefundDO.STATUS_WAIT_SELLER_AGREE); //买家申请退款
			if(seltState.compareTo("2")==0) map.put("refundState", RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS); //卖家同意退款
			if(seltState.compareTo("3")==0) map.put("refundState", RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS); //买家已退货
			if(seltState.compareTo("4")==0) map.put("refundState", RefundDO.STATUS_CLOSED); //退款关闭
			if(seltState.compareTo("5")==0) map.put("refundState", RefundDO.STATUS_SUCCESS); //退款成功
			if(seltState.compareTo("6")==0) map.put("refundState", RefundDO.STATUS_SELLER_REFUSE_BUYER); //卖家拒绝退款
			if(seltState.compareTo("7")==0) map.put("refundState", RefundDO.STATUS_CS_PROCESS); //客服仲裁
			if(seltState.compareTo("8")==0) map.put("refundState", RefundDO.STATUS_REFUND_FAIL); //退款失败
			if(seltState.compareTo("9")==0) map.put("refundState", RefundDO.STATUS_REFUND_PROTOCAL_AGREE); //退款协议达成
		}
		
		this.handleRefundList(map);
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void handleRefundList(Map<String,Object> map){
		//分页
		paginator.setItems(refundApplyAO.loadRefundApplyInfoCount(map));
		map.put("startNum", paginator.getStartRow());
		map.put("endNum",paginator.getEndRow());
		Result refundResult = refundApplyAO.loadRefundApplyInfo(map);
		if(refundResult.isSuccess()){
			refundList = (List<RefundDO>)refundResult.getModel("obj");
		}
		if(refundList != null && refundList.size() !=0 ){
			List<Long> orderItemIdList = new LinkedList<Long>();
			for(RefundDO myrefund:refundList){
				orderItemIdList.add(myrefund.getOrderItemId());
				Date _outDate = refundBaseAO.getOvertimes(myrefund);
				Date outDate = _outDate== null ? myrefund.getGmtModified() : _outDate;
				timesOut.put(myrefund.getId(), outDate);
			}
			Result itemOrderResult = refundApplyAO.queryOrderItemList(orderItemIdList);
			if(itemOrderResult.isSuccess()){
				List<OrderItemDO> orderItemDOList = (List<OrderItemDO>) itemOrderResult.getModel("orderItemDOs");
				List<Long> itemIdList = new LinkedList<Long>();
				for(OrderItemDO orderItem:orderItemDOList){
					orderItemMap.put(orderItem.getOrderItemId(), orderItem);
					itemIdList.add(orderItem.getItemId());
				}
				Result itemResult = refundApplyAO.getItemListByIds(itemIdList);
				if(itemResult.isSuccess()){
					List<ItemDO> itemList = (List<ItemDO>) itemResult.getModel("itemDOs");
					for(ItemDO item:itemList){
						itemMap.put(item.getId(), item);
					}
				}
				for(OrderItemDO orderItemDO:orderItemDOList){
					Long skuid = orderItemDO.getItemSkuId();
					if(skuid == null)
						skuid = 0L;
				//如果有skuid, 设置商品sku属性
					if(skuid.compareTo(0L) != 0){
						try{
							SkuDO sku = refundApplyAO.getItemSkuById(skuid);
							List<ItemPropertyVO> itemPropertylist = itemPropertyAO.getItemPropertyBySku(sku);
							itemPropertyMap.put("key" + orderItemDO.getItemId() + skuid, itemPropertylist);
						}catch(Exception e){
							log.error(e);
						}
					
					}
				}
			}
		}
	}

	
	public Date getOvertime(Date date,int d){
		return DateUtil.skipDateTime(date, d);
	}
	
	public RefundApplyAO getRefundApplyAO() {
		return refundApplyAO;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public ItemPropertyAO getItemPropertyAO() {
		return itemPropertyAO;
	}

	public void setItemPropertyAO(ItemPropertyAO itemPropertyAO) {
		this.itemPropertyAO = itemPropertyAO;
	}

	public List<RefundDO> getRefundList() {
		return refundList;
	}

	public void setRefundList(List<RefundDO> refundList) {
		this.refundList = refundList;
	}

	public Map<Long, ItemDO> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<Long, ItemDO> itemMap) {
		this.itemMap = itemMap;
	}

	public Map<Long, OrderItemDO> getOrderItemMap() {
		return orderItemMap;
	}

	public void setOrderItemMap(Map<Long, OrderItemDO> orderItemMap) {
		this.orderItemMap = orderItemMap;
	}

	public Map<String, List<ItemPropertyVO>> getItemPropertyMap() {
		return itemPropertyMap;
	}

	public void setItemPropertyMap(Map<String, List<ItemPropertyVO>> itemPropertyMap) {
		this.itemPropertyMap = itemPropertyMap;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSeltState() {
		return seltState;
	}

	public void setSeltState(String seltState) {
		this.seltState = seltState;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public void setRefundBaseAO(RefundBaseAO refundBaseAO) {
		this.refundBaseAO = refundBaseAO;
	}

	public Map<Long, Date> getTimesOut() {
		return timesOut;
	}

	public void setTimesOut(Map<Long, Date> timesOut) {
		this.timesOut = timesOut;
	}
}
