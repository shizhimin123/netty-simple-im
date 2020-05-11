package com.netty.demo.restructure.rule;

import sun.tools.jstat.Operator;

/**
 * @Description: AddRule
 * @Author: shizhimin
 * @Date: 2020/5/9
 * @Version: 1.0
 */
public class AddRule implements Rule {
    private Integer result;

    @Override
    public boolean evaluate(Expression expression) {
        boolean evalResult = false;
        if (expression.getOperator() == Operator.PLUS) {
            this.result = expression.getX() + expression.getY();
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
