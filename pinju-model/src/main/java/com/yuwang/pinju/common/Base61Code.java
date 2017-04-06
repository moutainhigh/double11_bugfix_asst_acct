package com.yuwang.pinju.common;

/**
 * <p>Cookie 二进制编码</p>
 *
 * @author gaobaowen
 * @since 2011-9-17 16:43:45
 */
public class Base61Code {

	public final static char[][] BASE41 = {{'N','8'},{'c','C'},{'a','H'},{'l','3'},{'L','e'},{'E','J'},{'M','2'},{'p','W'},{'K','Z'},{'o','Y'},{'O','S'},{'j','I'},{'q','n'},{'X','s'},{'g','y'},{'U','x'},{'m','6'},{'d','G'},{'B','A'},{'b','9'},{'Q','X'},{'v','K'},{'V','f'},{'t','u'},{'4','w'},{'P','P'},{'R','z'},{'k','F'},{'D','V'},{'5','c'},{'r','g'},{'f','j'},{'T','4'},{'1','h'},{'u','L'},{'7','m'},{'z','5'},{'i','1'},{'h','i'},{'F','a'},{'w','D'}};
	public final static int[][] BASE61_MAPPING = {{-1,18},{18,-1},{-1,1},{28,40},{5,-1},{39,27},{-1,17},{-1,2},{-1,11},{-1,5},{8,21},{4,34},{6,-1},{0,-1},{10,-1},{25,25},{20,-1},{26,-1},{-1,10},{32,-1},{15,-1},{22,28},{-1,7},{13,20},{-1,9},{-1,8},{2,39},{19,-1},{1,29},{17,-1},{-1,4},{31,22},{14,30},{38,33},{37,38},{11,31},{27,-1},{3,-1},{16,35},{-1,12},{9,-1},{7,-1},{12,-1},{30,-1},{-1,13},{23,-1},{34,23},{21,-1},{40,24},{-1,15},{-1,14},{36,26},{33,37},{-1,6},{-1,3},{24,32},{29,36},{-1,16},{35,-1},{-1,0},{-1,19}};

    /**
     * <p>使用双段 Base41 编码二进制数据</p>
     *
     * @param bys
     * @return
     *
     * @author gaobaowen
     * @since 2011-9-17 16:44:06
     */
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
}
