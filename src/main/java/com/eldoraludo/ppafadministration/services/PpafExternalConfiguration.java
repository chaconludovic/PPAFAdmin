package com.eldoraludo.ppafadministration.services;

import org.apache.tapestry5.ioc.internal.services.MapSymbolProvider;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

public class PpafExternalConfiguration {

    private MapSymbolProvider mapSymbolProvider;
    private Map<String, String> values = new HashMap<String, String>();

    private Enumeration<String> properties = ResourceBundle.getBundle("ppaf").getKeys();

    public MapSymbolProvider createSymbolProvider() {
        while (properties.hasMoreElements()) {
            String property = properties.nextElement();
            String propertyValue = System.getProperty(property);
            if (propertyValue != null) {
                values.put(property, propertyValue);
            }
        }
        mapSymbolProvider = new MapSymbolProvider(values);
        printOverridenConfiguration();
        return mapSymbolProvider;
    }

    private void printOverridenConfiguration() {
        if (values.isEmpty()) {
            return;
        }
        System.err.println("-------------------------------------------------------");
        System.err.println(" Overriden configuration from JVM properties");
        System.err.println("-------------------------------------------------------");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            System.err.println(format("\t{0} -> {1}", entry.getKey(), entry.getValue()));
        }
        System.err.println("-------------------------------------------------------");
    }

}
