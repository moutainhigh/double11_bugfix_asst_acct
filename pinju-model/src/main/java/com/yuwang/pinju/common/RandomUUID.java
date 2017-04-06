package com.yuwang.pinju.common;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class RandomUUID {

    public final static char[][] BASE41 = {{'w','D'},{'T','k'},{'u','F'},{'z','m'},{'H','W'},{'x','J'},{'g','N'},{'v','r'},{'O','9'},{'R','Z'},{'M','o'},{'c','j'},{'n','l'},{'Q','A'},{'8','E'},{'K','1'},{'a','V'},{'S','s'},{'q','5'},{'3','b'},{'P','p'},{'G','B'},{'B','P'},{'7','8'},{'4','f'},{'t','y'},{'6','6'},{'C','i'},{'X','4'},{'d','t'},{'h','g'},{'L','T'},{'Y','Y'},{'I','R'},{'i','S'},{'U','K'},{'f','q'},{'p','L'},{'2','x'},{'y','e'},{'e','v'}};
    public final static int[][] BASE61_MAPPING = {{-1,13},{22,21},{27,-1},{-1,0},{-1,14},{-1,2},{21,-1},{4,-1},{33,-1},{-1,5},{15,35},{31,37},{10,-1},{-1,6},{8,-1},{20,22},{13,-1},{9,33},{17,34},{1,31},{35,-1},{-1,16},{-1,4},{28,-1},{32,32},{-1,9},{16,-1},{-1,19},{11,-1},{29,-1},{40,39},{36,24},{6,30},{30,-1},{34,27},{-1,11},{-1,1},{-1,12},{-1,3},{12,-1},{-1,10},{37,20},{18,36},{-1,7},{-1,17},{25,29},{2,-1},{7,40},{0,-1},{5,38},{39,25},{3,-1},{-1,15},{38,-1},{19,-1},{24,28},{-1,18},{26,26},{23,-1},{14,23},{-1,8}};

    private final static Random ran = new SecureRandom();

    public static boolean isRandomUUID(String uuid, int len) {
    	if (StringUtils.isBlank(uuid)) {
    		return false;
    	}
    	if (uuid.length() != len) {
    		return false;
    	}
    	char[] chs = uuid.toCharArray();
    	for (int i = 0; i < chs.length; i++) {
    		if (!isValidChar(chs[i])) {
    			return false;
    		}
    	}
    	return true;
    }

    public static String get() {
    	return get(30);
    }

    public static String get(int len) {
    	byte[] bys = new byte[len / 3 * 2];
    	ran.nextBytes(bys);
    	return encode(bys);
    }

    public static String encode(byte[] bys) {
        return new String(arrayEncode(bys));
    }

    private static char[] arrayEncode(byte[] bys) {
        char[] chs = new char[((bys.length + 1) >> 1) * 3];
        int offset = 0;
        int index = 0;
        int odd = 0;
        while(offset < bys.length) {
            int num = (bys[offset++] & 0xff) << 8;
            if(offset < bys.length) {
                num |= (bys[offset++] & 0xff);
            }
            for(int i = 0; i < 3; i++) {
                chs[index++] = BASE41[num % 41][odd & 1];
                num /= 41;
            }
            odd++;
        }
        return chs;
    }

    private static boolean isValidChar(char c) {
    	if (c >= '1' && c <= '9') {
    		return true;
    	}
    	if (c >= 'a' && c <= 'z') {
    		return true;
    	}
    	if (c >= 'A' && c <= 'Z') {
    		return true;
    	}
    	return false;
    }

    public static void main(String[] args) {
    	System.out.println(RandomUUID.get(36));
    }
}
