package com.lx.pk.core.spring.context.properties;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

@SuppressWarnings("all")
public class ResourceBundleAdapter extends Properties {
    private static final long serialVersionUID = -3060271066454942469L;
    private ResourceBundle rb = null;

    public ResourceBundleAdapter(ResourceBundle rb) {
        assert (rb instanceof PropertyResourceBundle);
        this.rb = rb;

        Enumeration e = rb.getKeys();
        while (e.hasMoreElements()) {
            Object o = e.nextElement();
            put(o, rb.getObject((String) o));
        }
    }

    public ResourceBundle getBundle(String baseName) {
        return ResourceBundle.getBundle(baseName);
    }

    public ResourceBundle getBundle(String baseName, Locale locale) {
        return ResourceBundle.getBundle(baseName, locale);
    }

    public ResourceBundle getBundle(String baseName, Locale locale, ClassLoader loader) {
        return ResourceBundle.getBundle(baseName, locale, loader);
    }

    public Enumeration getKeys() {
        return this.rb.getKeys();
    }

    public Locale getLocale() {
        return this.rb.getLocale();
    }

    public Object getObject(String key) {
        return this.rb.getObject(key);
    }

    public String getString(String key) {
        return this.rb.getString(key);
    }

    public String[] getStringArray(String key) {
        return this.rb.getStringArray(key);
    }

    protected Object handleGetObject(String key) {
        return ((PropertyResourceBundle) this.rb).handleGetObject(key);
    }
}
