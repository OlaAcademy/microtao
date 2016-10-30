package com.xpown.utils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ConfigUtil {

    private static PropertiesConfiguration config = null;
    
    static {
        try {
            config = new PropertiesConfiguration();
            config.setEncoding("UTF-8");
            config.load("app.properties");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getIntValue(final String key) {
        int reInt = 0;
        try {
            reInt = config.getInt(key);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return reInt;
    }

    public static long getLongValue(final String key) {
        long reLong = 0;
        try {
            reLong = config.getLong(key);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

        return reLong;
    }

    public static double getDoubleValue(final String key) {
        double reDouble = 0;
        try {
            reDouble = config.getDouble(key);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

        return reDouble;
    }

    public static String getStringValue(String key) {
        String str = "";
        try {
            str = config.getString(key);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return str;
    }

    public static boolean getBooleanValue(String key) {
        boolean flag = false;
        try {
            flag = config.getBoolean(key);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

        return flag;
    }

    public static void save(String key, Object o) {
        config.setProperty(key, o);

        try {
            config.save("app.properties");
            config = new PropertiesConfiguration();
            config.setEncoding("UTF-8");
            config.load("app.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
