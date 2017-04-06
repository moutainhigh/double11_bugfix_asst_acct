package com.yuwang.pinju.core.margin.service.impl;


import java.rmi.RemoteException;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.margin.service.MarginService;
import com.yuwang.pinju.core.margin.service.MarginWebService;

public class MarginServiceImpl extends MarginWebService implements MarginService {

	Log log = LogFactory.getLog(this.getClass().getName());

	@Override
	public String payMargin(String userNick, String email) throws RemoteException{
		Object[] reqParams = new Object[]{"shixing","shixing@zba.com"};
		this.call.setOperationName(new QName(namespaces, "queryMargin"));
		String result = (String) call.invoke( reqParams );
		return result;
	}

}
