package com.yuwang.pinju.core.order.manager.impl.httpLogsisticsServce;

/** <p>Discription:物流接口  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-18
 */
public interface ServiceHttpLogistics {
	public static final String CHARSET = "UTF-8";
	 public String getTrackBybillcode(java.lang.String billcode, java.lang.String express, int resulttype) throws Exception;
	 public <R> R xmlToBean(String xml,Class<R> beanClass);
}


