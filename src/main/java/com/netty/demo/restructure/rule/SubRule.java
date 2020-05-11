package com.netty.demo.restructure.rule;

import sun.tools.jstat.Operator;

/**
 * @Description: AddRule
 * @Author: shizhimin
 * @Date: 2020/5/9
 * @Version: 1.0
 */
public class SubRule implements Rule {
    private Integer result;

    @Override
    public boolean evaluate(Expression expression) {
        boolean evalResult = false;
        if (expression.getOperator() == Operator.MINUS) {
            this.result = expression.getX() - expression.getY();
            evalResult = true;
        }
        return evalResult;
    }

    @Override
    public Result getResult() {
        Result result = new Result();
        result.setValue(this.result);
        return result;
    }
}
