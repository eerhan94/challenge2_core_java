package com.challenge2.core.java.config;

import com.challenge2.core.java.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    private Properties properties;
    private static AppProperties appProperties;
    private String invoiceLimitKey = "total.invoice.limit";

    public AppProperties() {
        initializeProperties();
    }

    public static AppProperties getInstance() {
        if (null == appProperties)
            appProperties = new AppProperties();
        return appProperties;
    }

    private void initializeProperties() {
        try {
            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("application.properties");
            if (properties == null) {
                properties = new Properties();
            }
            properties.load(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getInvoiceLimit() {
        return Integer.parseInt(properties.getProperty(invoiceLimitKey));
    }
}
