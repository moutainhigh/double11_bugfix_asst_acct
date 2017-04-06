package com.yuwang.pinju.core.margin.service;
import java.rmi.RemoteException;

/**  
 * @Project: pinju-biz
 * @Description: 保证金服务层,主要调用盛付通WebService接口
 * @author 石兴 shixing@zba.com
 * @date 2011-8-2 上午10:36:42
 * @update 2011-8-2 上午10:36:42
 * @version V1.0  
 */
public interface MarginService {

	public String payMargin(String userNick,String email) throws RemoteException;
}
