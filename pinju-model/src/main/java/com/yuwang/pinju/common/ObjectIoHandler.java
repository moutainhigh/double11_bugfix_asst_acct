/**
 * 
 */
package com.yuwang.pinju.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**  
 * @Project: pinju-model
 * @Title: ObjectIoHandler.java
 * @Package com.yuwang.pinju.common
 * @Description: 对象数据存储工具
 * @author liuboen liuboen@zba.com
 * @date 2011-6-23 下午02:12:04
 * @version V1.0  
 */

public class ObjectIoHandler {

	final static Log log = LogFactory.getLog(ObjectIoHandler.class.getName());

    /**
     * 输出一个对象
     * @param object
     * @param filePath
     */
    public static void outputObject(Object object,String filePath) {
        try {

        	File file = new File(filePath);

            ObjectOutput outputObj = new ObjectOutputStream(new FileOutputStream(file));
            try {
                outputObj.writeObject(object);
            } finally {
                outputObj.flush();
                outputObj.close();
            }
        } catch (Exception e) {
        	log.warn("event=[ObjectIOHandler#outputObject], filePath=["+filePath+"], exception message=["+e.getMessage()+"]");
        }
    }

    /**
     * 读入一个对象
     * @param filePath
     * @return
     */
    public static Object inputObject(String filePath) {
        try {
            ObjectInput inputObject = new ObjectInputStream(new FileInputStream(filePath));
            try {
                return inputObject.readObject();
            } finally {
                inputObject.close();
            }
        } catch (Exception e) {
        	log.warn("event=[ObjectIOHandler#inputObject] filePath=["+filePath+"] exception message=["+e.getMessage()+"]");
        }
        return null;
    }
}
