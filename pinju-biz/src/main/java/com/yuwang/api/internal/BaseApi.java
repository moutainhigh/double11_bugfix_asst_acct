/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.api.ApiException;
import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.Paginator;

/**
 * @author liyouguo
 * 
 * @since 2011-9-5
 */
public abstract class BaseApi implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1495439989149135880L;

	protected Log logger = LogFactory.getLog("open-api");
	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 根据spring 中beanId 返回对应的BEAN对象
	 * 
	 * @param beanId
	 *            --spring配置中的id
	 * @return 对应的bean对象
	 */
	protected Object getBean(String beanId) {
		return SpringBeanFactory.getBean(beanId);
	}
	
	/**
	 * 每页显示数默认值设定及验证<br/>
	 * 必须大于0小于100
	 * 
	 * @param ooq
	 * @param itemsPerPage
	 * @throws ApiException
	 */
	protected void setItemsPerPage(Paginator tagert, String itemsPerPage) throws ApiException {
		if (!StringUtil.isEmpty(itemsPerPage)) {
			try {
				int perpage = Integer.parseInt(itemsPerPage);
				if (perpage > 100 || perpage < 0) {
					throw new ApiException("传入参数出错，数字型参数itemsPerPage必须大于0小于100");
				}
				tagert.setItemsPerPage(perpage);
			} catch (NumberFormatException e) {
				throw new ApiException("传入参数itemsPerPage:" + itemsPerPage + "出错，必须传入数字型参数itemsPerPage");
			}
		} else {
			tagert.setItemsPerPage(40);
		}
	}

	/**
	 * 页数默认值设定及验证<br/>
	 * 必须大于0
	 * 
	 * @param ooq
	 * @param page
	 * @throws ApiException
	 */
	protected void setPage(Paginator tagert, String page) throws ApiException {
		if (!StringUtil.isEmpty(page)) {
			try {
				if (0 > Integer.parseInt(page)) {
					throw new ApiException("传入参数出错，数字型参数page必须大于0");
				}
				tagert.setPage(Integer.parseInt(page));
			} catch (NumberFormatException e) {
				throw new ApiException("传入参数page:" + page + "出错，必须传入数字型参数page");
			}
		} else {
			tagert.setPage(0);
		}
	}
}
