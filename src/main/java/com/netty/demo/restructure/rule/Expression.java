package com.netty.demo.restructure.rule;

import lombok.Data;
import sun.tools.jstat.Operator;

/**
 * @Description: Expression
 * @Author: shizhimin
 * @Date: 2020/5/9
 * @Version: 1.0
 */
@Data
public class Expression {
    private Integer x;
    private Integer y;
    private Operator operator;

    public Expression(Integer x, Integer y, Operator operator) {
        this.x = x;
        this.y = y;
        this.operator = operator;

    }
}
