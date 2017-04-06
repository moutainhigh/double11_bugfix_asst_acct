package com.yuwang.pinju.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unitils.UnitilsJUnit3;
import org.unitils.spring.annotation.SpringApplicationContext;



/**
 * base test case
 *
 * @author mike
 */

//@SpringApplicationContext({"classpath:/applicationContext-mysql.xml"})
@SpringApplicationContext({"classpath:/applicationContext.xml"})
public class BaseTestCase extends UnitilsJUnit3 {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public void test(){	}
}

