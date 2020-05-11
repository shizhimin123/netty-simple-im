package com.netty.demo.restructure.factory;

@FunctionalInterface
public interface FillCountService {
    void fillCount(CountRecoder countRecoder, int count);
}
