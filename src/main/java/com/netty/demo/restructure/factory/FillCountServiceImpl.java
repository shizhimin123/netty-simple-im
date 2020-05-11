package com.netty.demo.restructure.factory;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: FillCountServiceImpl
 * @Author: shizhimin
 * @Date: 2020/5/9
 * @Version: 1.0
 */
@Service
public class FillCountServiceImpl {

    public CountRecoder getCountRecoder(List<CountEntries> countEntries) {
        CountRecoder countRecoder = new CountRecoder();
        countEntries.stream().forEach(countEntry ->
                FillCountServieFactory.getFillCountStrategy(countEntry.getCode())
                        .fillCount(countRecoder, countEntry.getCount()));
        return countRecoder;
    }
}
