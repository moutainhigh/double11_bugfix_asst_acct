package com.yuwang.pinju.core.util;

import java.net.InetAddress;
import java.util.Properties;

/**
 * To generate a UUID using JDK version 1.4 or lower. There is a convenient method to generate a 32 length UUID.
 * See <code>com.pinju.common.util.UUIDGenerationUtil.java</code> for UUID generation using Tiger (Java 5)
 * 
 * @author James
 *
 */
public class UUIDGeneration14Util {

    public UUIDGeneration14Util() {
    }

    private static final int IP;

    private String sep = "";

    static {
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    private static short counter = (short) 0;

    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

    /**
     * Unique across JVMs on this machine (unless they load this class in the
     * same quater second - very unlikely)
     */
    private int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there are >
     * Short.MAX_VALUE instances created in a millisecond)
     */
    private short getCount() {
        synchronized (UUIDGeneration14Util.class) {
            if (counter < 0)
                counter = 0;
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    private int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    private short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    private int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    private static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    /**
     * To configure seperator, which defaults to empty string if not set.
     * @param params The <code>java.util.Properties</code> with <em>seperator</em> info
     */
    public void configure(Properties params) {
        sep = params.getProperty("seperator");
    }

    protected String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    /**
     * Generate a UUID using IP, JVM, currentTimeMillis, and counter data. 
     * It is recommended to set <em>seperator</em> before invoking this function.
     * @return
     */
    public String generate() {
        return new StringBuffer(36).append(format(getIP())).append(sep).append(
                format(getJVM())).append(sep).append(format(getHiTime()))
                .append(sep).append(format(getLoTime())).append(sep).append(
                        format(getCount())).toString();
    }

    /**
     * Convenient method for generating a UUID using <code>generate()</code> public method
     * defined in this class. 
     * <p>
     * This method is equivalent to:
     * 
     * <pre>
     *     UUIDGeneration14Util uh = new UUIDGeneration14Util();
     *     uh.configure(props);
     * </pre>
     * 
     * @return A UUID string with length 32
     */
    public static String getUUIDHex() {
        Properties props = new Properties();
        props.setProperty("seperator", "");
        UUIDGeneration14Util uh = new UUIDGeneration14Util();
        uh.configure(props);
        return uh.generate();
    }

    public void main(String[] args){
        String s = UUIDGeneration14Util.getUUIDHex();
        System.out.println(s);
    }

}
