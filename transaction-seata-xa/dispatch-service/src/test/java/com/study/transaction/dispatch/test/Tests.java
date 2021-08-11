package com.study.transaction.dispatch.test;

import java.util.concurrent.ConcurrentHashMap;

public class Tests {
    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(2);
        concurrentHashMap.put("1","");
        concurrentHashMap.put("2","");
        concurrentHashMap.put("9","");
        concurrentHashMap.put("4","");
        concurrentHashMap.put("5","");
        concurrentHashMap.put("6","");
        concurrentHashMap.put("7","");
        concurrentHashMap.put("8","");
        concurrentHashMap.put("9","");
    }
}
