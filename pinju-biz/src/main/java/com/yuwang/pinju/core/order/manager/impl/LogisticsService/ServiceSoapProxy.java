package com.yuwang.pinju.core.order.manager.impl.LogisticsService;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class ServiceSoapProxy implements com.yuwang.pinju.core.order.manager.impl.LogisticsService.ServiceSoapLogistics {
  private String _endpoint = null;
  private com.yuwang.pinju.core.order.manager.impl.LogisticsService.ServiceSoapLogistics serviceSoap = null;
  
  private String key;
  private String webserviceURL;
  private final static int type = 0;
	
  public void setKey(String key) {
		this.key = key;
  }

  public void setWebserviceURL(String webserviceURL) {
		this.webserviceURL = webserviceURL;
  }
  
  public ServiceSoapProxy() {
    _initServiceSoapProxy();
  }
  
  public ServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceSoapProxy();
  }
  
  private void _initServiceSoapProxy() {
    try {
      serviceSoap = (new com.yuwang.pinju.core.order.manager.impl.LogisticsService.ServiceLocator(webserviceURL)).getServiceSoap();
      if (serviceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviceSoap != null)
      ((javax.xml.rpc.Stub)serviceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.yuwang.pinju.core.order.manager.impl.LogisticsService.ServiceSoapLogistics getServiceSoap() {
    if (serviceSoap == null)
      _initServiceSoapProxy();
    return serviceSoap;
  }
  
  public java.lang.String getTrackBybillcode(java.lang.String billcode, java.lang.String express,String keys,int types) throws java.rmi.RemoteException{
    if (serviceSoap == null)
      _initServiceSoapProxy();
    return serviceSoap.getTrackBybillcode(billcode, express, key, type);
  }
  
  public <R> R xmlToBean(String xml,Class<R> beanClass){
		try {
			String tagName;
			String tagValue;
			List<HashMap<String,String>> list = new ArrayList();
			
			StringReader read = new StringReader(xml);
			InputSource source = new InputSource(read);

			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(source);

			Element foo = doc.getRootElement();
			List<Element> allChildren = foo.getChildren();
			Element em=null;
			R beanObj = beanClass.newInstance();
			for(int i=0; i<allChildren.size(); i++) {
				em = (Element)allChildren.get(i);
				List<Element> children = em.getChildren();
				if(children.size()>0){
					HashMap<String,String> map = new HashMap<String,String>();
					for(int ii=0; ii<children.size(); ii++){
						Element m = (Element)children.get(ii);
						tagName = m.getName();
						tagValue = m.getText();
						map.put(tagName, tagValue);
					}
					list.add(map);
					continue;
				}
				tagName = em.getName();
				tagValue = em.getText();
				if (isExistField(tagName, beanClass)) {
					String methodName = toSetName(tagName);
					Method method = beanClass.getMethod(methodName, String.class);
					// 调用set方法放入数据
					method.invoke(beanObj, tagValue);
				}
			}
			if (isExistField("step", beanClass)) {
				String methodName = toSetName("step");
				Method method = beanClass.getMethod(methodName, List.class);
				// 调用set方法放入数据
				method.invoke(beanObj, list);
			}
			return beanObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断类中是否有该属性
	 */
	private boolean isExistField(String fieldName,Class c) {
		try {
			c.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			return false;
		} catch (NoSuchFieldException e) {
			return false;
		}
		return true;
	}
	
	private String toSetName(String fieldName) {
		fieldName = fieldName.replaceFirst("^" + fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
		return "set" + fieldName;
	}
	
	public void setBeanParam(String pater,String key,String value){
		
	}
  
  
}