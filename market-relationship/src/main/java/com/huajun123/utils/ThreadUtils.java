package com.huajun123.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    //Use A Fixed ThreadPool to fasten the speed of response
    private static final ExecutorService es= Executors.newFixedThreadPool(10);
    public static void execute(Runnable runnable){
        es.submit(runnable);
    }
}
