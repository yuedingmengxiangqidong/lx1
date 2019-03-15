package com.lx.pk.core.spring.context.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class JProperties {
    public static final int BY_PROPERTIES = 1;
    public static final int BY_RESOURCEBUNDLE = 2;
    public static final int BY_PROPERTYRESOURCEBUNDLE = 3;
    public static final int BY_CLASS = 4;
    public static final int BY_CLASSLOADER = 5;
    public static final int BY_SYSTEM_CLASSLOADER = 6;

    public static final Properties loadProperties(String name, int type)
            throws IOException {
        Properties p = new Properties();
        InputStream in = null;
        if (type == 1) {
            in = new BufferedInputStream(new FileInputStream(name));
            assert (in != null);
            p.load(in);
        } else {
            ResourceBundle rb;
            if (type == 2) {
                rb = ResourceBundle.getBundle(name, Locale.getDefault());
                assert (rb != null);
                p = new ResourceBundleAdapter(rb);
            } else if (type == 3) {
                in = new BufferedInputStream(new FileInputStream(name));
                assert (in != null);
                rb = new PropertyResourceBundle(in);
                p = new ResourceBundleAdapter(rb);
            } else if (type == 4) {
                assert (JProperties.class.equals(new JProperties().getClass()));
                in = JProperties.class.getResourceAsStream(name);
                assert (in != null);
                p.load(in);
            } else if (type == 5) {
                assert (JProperties.class.getClassLoader().equals(new JProperties().getClass().getClassLoader()));
                in = JProperties.class.getClassLoader().getResourceAsStream(name);
                assert (in != null);
                p.load(in);
            } else if (type == 6) {
                in = ClassLoader.getSystemResourceAsStream(name);
                assert (in != null);
                p.load(in);
            }
        }
        if (in != null) {
            in.close();
        }
        return p;
    }
}