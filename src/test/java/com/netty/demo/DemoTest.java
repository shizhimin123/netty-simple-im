package com.netty.demo;

import com.netty.demo.hbase.HbaseDemo;
import com.netty.demo.restructure.command.Calculator;
import com.netty.demo.restructure.command.SubCommand;
import com.netty.demo.restructure.factory.CountEntries;
import com.netty.demo.restructure.factory.CountRecoder;
import com.netty.demo.restructure.factory.FillCountServiceImpl;
import com.netty.demo.restructure.rule.Expression;
import com.netty.demo.restructure.rule.Result;
import com.netty.demo.restructure.rule.RuleEngine;
import org.junit.Test;
import sun.tools.jstat.Operator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: DemoTest
 * @Author: shizhimin
 * @Date: 2020/4/1
 * @Version: 1.0
 */
public class DemoTest {

    @Test
    public void testCreateTable() {
        HbaseDemo.createTable("con", "log");
    }

    @Test
    public void testdelTable() {
        HbaseDemo.delTable("dr_ps");
    }

    @Test
    public void test001() {
        List<CountEntries> entriesList = new ArrayList<>();
        for (int i = 1;i < 7;i++){
            CountEntries entries = new CountEntries();
            entries.setCode(i);
            entries.setCount(i*10);
            entriesList.add(entries);
        }
        FillCountServiceImpl fillCountService = new FillCountServiceImpl();
        CountRecoder countRecoder = fillCountService.getCountRecoder(entriesList);
        System.out.println(countRecoder.toString());
    }

    @Test
    public void test002() {
        Calculator calculator = new Calculator();
        /*int result = calculator.calculate(new SubCommand(3, 7));*/
        int result = calculator.calculate("SUB", 3, 7);
        System.out.println(result);
    }

    @Test
    public void test003() {
        Expression expression = new Expression(5, 6, Operator.MINUS);
        RuleEngine engine = new RuleEngine();
        Result result = engine.process(expression);
        System.out.println(result.toString());
    }
}
