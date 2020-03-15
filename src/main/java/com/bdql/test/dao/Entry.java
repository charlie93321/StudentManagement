package com.bdql.test.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR: 小于
 * @DATE: [2020/3/15  14:29]
 * @DESC:
 */
public class Entry {
    private Map<Field, Method> setMethodMap;
    private Class clazz;

    public Map<Field, Method> getSetMethodMap() {
        return setMethodMap;
    }

    public void setSetMethodMap(Map<Field, Method> setMethodMap) {
        this.setMethodMap = setMethodMap;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
