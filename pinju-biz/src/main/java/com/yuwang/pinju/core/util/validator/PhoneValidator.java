package com.yuwang.pinju.core.util.validator;

/**
 * Validate a phone number.
 */
public class PhoneValidator {
    
    private PhoneValidator(){
        //util class, prevent from new instance
    }
    
    public static int MOBILE = 1; //移动号码
    public static int PSTN = 2; //固话号码
    
    /**
     * 检验是否是电话号码，支持手机号码和固话号码
     * @param num
     * @return true if it is mobile number or PSTN number
     */
    public static boolean isValid(String num){
        if (isValidMobile(num))
            return true;
        if (isValidPstn(num))
            return true;
        return false;
    }
    
    /**
     * 检验是否是移动号码,13000000000-13999999999
     * @param num
     * @return true if it is mobile phone number
     */
    public static boolean isValidMobile(String num){
        if (GenericValidator.isBlankOrNull(num))
            return false;
        if (num.trim().length() != 11)
            return false;        
        if (!GenericValidator.isLong(num))
            return false;
        
        Long l = GenericTypeValidator.formatLong(num);
        long lNum = l.longValue();
        return GenericValidator.isInRange(lNum, 13000000000L, 13999999999L);
    }
    
    /**
     * 检验是否是固定电话号码,支持7位和8位号码
     * @param num
     * @return true if it is PSTN phone number
     */
    public static boolean isValidPstn(String num){
        if (GenericValidator.isBlankOrNull(num))
            return false;
        int length = num.trim().length();
        if (length != 7 && length != 8)
            return false;
        if (!GenericValidator.isLong(num))
            return false;
        
        Long l = GenericTypeValidator.formatLong(num);
        long lNum = l.longValue();
        return GenericValidator.isInRange(lNum, 1000000L, 99999999L);
    }
    
    public static void main(String[] args){
        System.out.println(PhoneValidator.isValidMobile("01377171519"));
    }

}
