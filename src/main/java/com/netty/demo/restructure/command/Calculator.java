package com.netty.demo.restructure.command;

import com.netty.demo.restructure.factory.FillCountService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Calculator
 * @Author: shizhimin
 * @Date: 2020/5/9
 * @Version: 1.0
 */
public class Calculator {

    private static Map<String, Command> calculatorHashMap = new HashMap<>();

    private static int x,y;

    static {
        calculatorHashMap.put("ADD", new AddCommand());
        calculatorHashMap.put("SUB", new SubCommand());

    }

    public int calculate(String type,int x,int y) {
        return calculatorHashMap.get(type).execute(x,y);
    }
}
