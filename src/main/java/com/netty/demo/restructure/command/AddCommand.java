package com.netty.demo.restructure.command;

/**
 * @Description: AddCommand
 * @Author: shizhimin
 * @Date: 2020/5/9
 * @Version: 1.0
 */
public class AddCommand implements Command {

    @Override
    public Integer execute(int a, int b) {
        return a + b;
    }
}
