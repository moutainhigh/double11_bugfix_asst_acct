package com.yuwang.pinju.web.module.ajax.main;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.Tools;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.system.SysConfig;

public class RefreshAction implements PinjuAction {
	private final static Log log = LogFactory.getLog(RefreshAction.class);

	private final static Pattern RETURN_URL_PATTERN = Pattern
			.compile("https?://[a-z0-9-]{1,63}"
					+ SysConfig.PINJU_ROOT_DOMAIN.replace(".", "\\.")
					+ "(?:[/?].*)?");

	private InputStream inputStream;

	private Map<String, Long> counts = new HashMap<String, Long>();

	private String idString;

	private String callBack;

	private OrderQueryManager orderQueryManager;

	/**
	 * 查询订单总数
	 * @return
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		String referer = ServletUtil.getHttpReferer();
		if (log.isDebugEnabled()) {
			log.debug("HTTP Referer : " + referer);
		}
		if (Tools.isBlank(referer)
				|| !RETURN_URL_PATTERN.matcher(referer.toLowerCase()).matches()) {
			return SUCCESS;
		}

		log.debug("团购商品ID列表：" + idString);
		log.debug("JSONP:" + callBack);
		if (idString != null && !"".equals(idString)) {
			String[] ids = idString.split("-");
			for (String id : ids) {
				log.debug("Item Id: " + id);
				if (id == null || "".equals(id)) {
					continue;
				}
				try {
					if(NumberUtil.isLong(id.trim())){
						Long itemId = Long.parseLong(id.trim());
						QueryOrderItem queryOrderItem = new QueryOrderItem();
						queryOrderItem.setItemId(itemId);
						queryOrderItem.setOrderItemState(new int[] {
								OrderItemDO.ORDER_ITEM_STATE_2,
								OrderItemDO.ORDER_ITEM_STATE_3,
								OrderItemDO.ORDER_ITEM_STATE_5 });
						Long count = orderQueryManager
								.queryOrderItemDONum(queryOrderItem);
						counts.put(id, count);
					}else{
						log.error("体验购页面传入商品ID错误：" + id);
						counts.put(id, 0L);
					}
				} catch (Exception e) {
					log.error("Item ID Parse Exception : ", e);
				}
			}
		}
		JSONArray array = JSONArray.fromObject(counts);
		String result = callBack + "(" + array.toString() + ")";
		return response(result);
	}
	
	/**
	 * 查询商品总数
	 * @return
	 * @throws Exception
	 */
	public String getItemCounts() throws Exception {
		String referer = ServletUtil.getHttpReferer();
		if (log.isDebugEnabled()) {
			log.debug("HTTP Referer : " + referer);
		}
		if (Tools.isBlank(referer)
				|| !RETURN_URL_PATTERN.matcher(referer.toLowerCase()).matches()) {
			return SUCCESS;
		}

		log.debug("团购商品ID列表：" + idString);
		log.debug("JSONP:" + callBack);
		if (idString != null && !"".equals(idString)) {
			String[] ids = idString.split("-");
			for (String id : ids) {
				log.debug("Item Id: " + id);
				if (id == null || "".equals(id)) {
					continue;
				}
				try {
					Long itemId = Long.parseLong(id.trim());
					QueryOrderItem queryOrderItem = new QueryOrderItem();
					queryOrderItem.setItemId(itemId);
					queryOrderItem.setOrderItemState(new int[] {
							OrderItemDO.ORDER_ITEM_STATE_2,
							OrderItemDO.ORDER_ITEM_STATE_3,
							OrderItemDO.ORDER_ITEM_STATE_5 });
					Long count = orderQueryManager
							.queryOrderItemBuyNum(queryOrderItem);
					counts.put(id, count);
				} catch (Exception e) {
					log.error("Item ID Parse Exception : ", e);
				}
				
			}
		}
		JSONArray array = JSONArray.fromObject(counts);
		String result = callBack + "(" + array.toString() + ")";
		return response(result);
	}

	private String response(String responseData) {
		try {
			inputStream = new ByteArrayInputStream(responseData
					.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return SUCCESS;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setIdString(String idString) {
		this.idString = idString;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

}
