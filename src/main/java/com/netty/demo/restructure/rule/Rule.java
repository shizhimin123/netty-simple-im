package com.netty.demo.restructure.rule;

public interface Rule {
    boolean evaluate(Expression expression);
    Result getResult();
}
