package com.netty.demo.lock;

/**
 * @Description: SynchronizedDemo
 * @Author: shizhimin
 * @Date: 2020/5/11
 * @Version: 1.0
 */
public class SynchronizedDemo {
    Object object = new Object();
    public void method1() {
        synchronized (object) {

        }
    }
}
