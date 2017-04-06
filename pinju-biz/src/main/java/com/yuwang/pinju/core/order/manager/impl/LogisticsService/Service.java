
package com.yuwang.pinju.core.order.manager.impl.LogisticsService;

import com.yuwang.pinju.core.order.manager.impl.LogisticsService.ServiceSoapLogistics;


public interface Service extends javax.xml.rpc.Service {
    public java.lang.String getServiceSoapAddress();

    public ServiceSoapLogistics getServiceSoap() throws javax.xml.rpc.ServiceException;

    public ServiceSoapLogistics getServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getServiceSoap12Address();


}
