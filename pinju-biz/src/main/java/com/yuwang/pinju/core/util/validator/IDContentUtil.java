/**
 * create on 2006-09-09
 */
package com.yuwang.pinju.core.util.validator;

import com.yuwang.pinju.core.util.RandomUtil;

/**
 * 邮箱或手机号验证 *
 * 
 * @author peng jiong
 * 
 */
public class IDContentUtil {

    /**
     * 根据注册类型、登录帐号验证输入是否有效
     * 
     * @param idContent
     * @param iType
     *            1:Email,2:Mobile,3:Smartphone
     * @return
     */
    public static boolean validationIDContent(String idContent, int iType) {

        boolean result = false;

        switch (iType) {
        case 1:
            result = EmailValidator.isValid(idContent);
            break;
        case 2:
            result = PhoneValidator.isValidMobile(idContent);
            break;
        case 3:
            // TODO
            result = false;
            break;
        }

        return result;
    }

    /**
     * 生成验证字符串
     * 
     * @return
     */
    public static String generatValidateCode() {

        return RandomUtil.random(8);
    }

    /**
     * 获取标识类别
     * 
     * @param idContent
     * @return 1:Email;2:Mobile:0:Error...
     */
    public static int getIDContentType(String idContent) {
        int idType = 0;
        if (EmailValidator.isValid(idContent)) {
            idType = 1;
        }
        if (PhoneValidator.isValidMobile(idContent)) {
            idType = 2;
        }
        return idType;
    }
}
