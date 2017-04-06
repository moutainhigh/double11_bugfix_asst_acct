package com.yuwang.pinju.core.util;

import java.util.Properties;

/**
 * Convenient helper methods to read <code>java.util.Properties</code>
 * 
 * @see java.util.Properties
 * @author James
 *
 */
public class TextUtil {

    /**
     * Returns integer value for <code>key</code>
     * @param props The <code>java.util.Properties</code> to look up
     * @param key The <code>key</code>
     * @param defVal Default value if not found
     * @return integer value for the key
     */
    public static int getIntegerProperty(Properties props, String key, int defVal) {
        String val = props.getProperty(key);

        return parseIntParameter(val, defVal);
    }

    /**
     * Returns boolean value for <code>key</code>
     * @param props The <code>java.util.Properties</code> to look up
     * @param key The <code>key</code>
     * @param defval Default value if not found
     * @return boolean value for the key, or defval if not found
     */
    public static boolean getBooleanProperty(Properties props, String key, boolean defval) {
        String val = props.getProperty(key);

        if (val == null) return defval;

        return isPositive(val);
    }

    /**
     * Returns string value for <code>key</code>
     * @param props The <code>java.util.Properties</code> to look up
     * @param key The <code>key</code>
     * @param defval Default value if not found
     * @return string value for the key, or defval if not found
     */
    public static String getStringProperty(Properties props, String key, String defval) {
        String val = props.getProperty(key);

        if (val == null) return defval;

        return val.trim();
    }

    /**
     * Checks if <code>val</code> is "true", "on" or "yes" 
     * @param val The value to be inspected
     * @return true if value is "true", "on" or "yes" (case insensitive), or false if not
     */
    public static boolean isPositive(String val) {
        if (val == null) return false;

        return (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("on") || val.equalsIgnoreCase("yes"));
    }

    /**
     * Parse value to integer
     * @param value The value to be evaluated
     * @param defvalue The default value
     * @return integer value parsed
     */
    public static int parseIntParameter(String value, int defvalue) {
        int val = defvalue;

        try {
            val = Integer.parseInt(value);
        }
        catch (Exception e) {
        }

        return val;
    }
    
}