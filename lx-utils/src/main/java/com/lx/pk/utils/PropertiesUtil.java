package com.lx.pk.utils;



import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 * Properties配置文件读取类
 */
public class PropertiesUtil {


    private static Properties prop = new Properties();


    static {
        try {
            System.out.println("load properties... ");
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(".properties"));
            System.out.println("load properties success.");
        } catch (IOException e) {
            System.err.println("load properties failed."+e);
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static void setProperty(String key,String value) {
        prop.setProperty(key,value);
    }

    public static String getGBKPropertyValue(String key) {
        try {
            String value = prop.getProperty(key);
            value = new String(value.getBytes("ISO8859-1"), "UTF-8");
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getProperty(String key, String defaultValue) {
        return StringUtils.isEmpty(prop.getProperty(key)) ? defaultValue : prop.getProperty(key);
    }

    public static String getPropertyReplaced(String key, String[] replacements) {
        try {
            return String.format(prop.getProperty(key), replacements);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println();
        Iterator<Object> keySet = prop.keySet().iterator();
        while (keySet.hasNext()) {
            String key = (String) keySet.next();
            System.out.println( key+"==="+prop.get(key));
        }
    }
}
