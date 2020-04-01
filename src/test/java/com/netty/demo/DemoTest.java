package com.netty.demo;

import com.netty.demo.hbase.HbaseDemo;
import org.junit.Test;

/**
 * @Description: DemoTest
 * @Author: shizhimin
 * @Date: 2020/4/1
 * @Version: 1.0
 */
public class DemoTest {

    @Test
    public void testCreateTable() {
        HbaseDemo.createTable("dr_ps", "trademark", "dr_type", "service_id");
    }

    @Test
    public void testdelTable() {
        HbaseDemo.delTable("dr_ps");
    }
}
