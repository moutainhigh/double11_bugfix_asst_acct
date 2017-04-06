/**
 * 
 */
package com.yuwang.pinju.biz.config;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.config.manager.ConfigManagerManager;
import com.yuwang.pinju.domain.config.ConfigManagerDO;

/**  
 * @Project: pinju-biz
 * @Package com.yuwang.pinju.biz.config
 * @Description: TODO
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-10-27 下午5:35:19
 * @version V1.0  
 */

public class ConfigManagerManagerTest extends BaseTestCase {

	@SpringBean("configManagerManager")
	ConfigManagerManager configManagerManager;
	
	public  void testQueryConfig() {
		try {
			List<ConfigManagerDO> list=configManagerManager.selectConfigManagerListByProjectType(1);
			for (ConfigManagerDO configManagerDO : list) {
				System.out.println(configManagerDO.getName());
			}
		} catch (ManagerException e) {
			System.out.println(e);
		}

	}
}
