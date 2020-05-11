package com.netty.demo.restructure.rule;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: RuleEngine
 * @Author: shizhimin
 * @Date: 2020/5/9
 * @Version: 1.0
 */
public class RuleEngine {

    private static List<Rule> rules = new ArrayList<>(2);
    static {
        rules.add(new AddRule());
        rules.add(new SubRule());
    }

    public Result process(Expression expression) {
        Rule rule = rules.stream()
                .filter(r -> r.evaluate(expression))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Expression does not matches any Rule"));
        return rule.getResult();
    }
}
