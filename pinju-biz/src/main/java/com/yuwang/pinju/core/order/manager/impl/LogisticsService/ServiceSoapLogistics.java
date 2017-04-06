/**
 * ServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yuwang.pinju.core.order.manager.impl.LogisticsService;

public interface ServiceSoapLogistics extends java.rmi.Remote {
    public String getTrackBybillcode(java.lang.String billcode, java.lang.String express, java.lang.String key, int type) throws java.rmi.RemoteException;
    public <R> R xmlToBean(String xml,Class<R> beanClass);
}
