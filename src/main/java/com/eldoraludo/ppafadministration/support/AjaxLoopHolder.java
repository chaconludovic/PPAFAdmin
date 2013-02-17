package com.eldoraludo.ppafadministration.support;

import org.apache.tapestry5.ValueEncoder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Long.parseLong;
import static java.util.Collections.sort;

public class AjaxLoopHolder<T extends Comparable> {

    private AtomicLong increment = new AtomicLong();

    private Map<Long, T> valueMap = newHashMap();

    public List<T> getValues() {
        // Already sorted since it is a valueMap
        List<T> values = newArrayList(valueMap.values());
        sort(values);
        return values;
    }

    public void add(T newValue) {
        valueMap.put(increment.incrementAndGet(), newValue);
    }

    public void remove(T value) {
        // values are backed by map, therefore it will also remove entry from map when you remove from values collection
        valueMap.values().remove(value);
    }

    public ValueEncoder getEncoder() {
        return new ValueEncoder<T>() {

            public String toClient(T value) {
                for (Map.Entry entry : valueMap.entrySet()) {
                    if (entry.getValue().equals(value)) {
                        return entry.getKey().toString();
                    }
                }

                return null;
            }

            public T toValue(String keyAsString) {
                return valueMap.get(parseLong(keyAsString));
            }
        };
    }
}
