package com.netty.demo.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HbaseDemo {

    private static Configuration conf = HBaseConfiguration.create();
    private static Admin admin;

    static {
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        // 设置Zookeeper,直接设置IP地址
        conf.set("hbase.zookeeper.quorum", "localhost:2181");
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建表，可以同时创建多个列簇
     */
    public static void createTable(String tableName, String... columnFamily) {
        TableName tableNameObj = TableName.valueOf(tableName);
        try {
            if (admin.tableExists(tableNameObj)) {
                System.out.println("Table : " + tableName + " already exists !");
            } else {
                HTableDescriptor td = new HTableDescriptor(tableNameObj);
                int len = columnFamily.length;
                for (int i = 0; i < len; i++) {
                    HColumnDescriptor family = new HColumnDescriptor(columnFamily[i]);
                    td.addFamily(family);
                }
                admin.createTable(td);
                System.out.println(tableName + " 表创建成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(tableName + " 表创建失败！");
        }
    }

    /**
     * 删除表
     */
    public static void delTable(String tableName) {
        TableName tableNameObj = TableName.valueOf(tableName);
        try {
            if (admin.tableExists(tableNameObj)) {
                admin.disableTable(tableNameObj);
                admin.deleteTable(tableNameObj);
                System.out.println(tableName + " 表删除成功！");
            } else {
                System.out.println(tableName + " 表不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(tableName + " 表删除失败！");
        }
    }


    /**
     *插入数据
     */
    public static void insertRecord(String tableName, String rowKey, String columnFamily, String qualifier, String value) {
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(rowKey.getBytes());
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            table.close();
            connection.close();
            System.out.println(tableName + " 表插入数据成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(tableName + " 表插入数据失败！");
        }
    }

    /**
     * 根据KEYROW删除表中的数据
     */
    public static void deleteRecord(String tableName, String rowKey) {
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table table = connection.getTable(TableName.valueOf(tableName));
            Delete del = new Delete(rowKey.getBytes());
            table.delete(del);
            System.out.println(tableName + " 表删除数据成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(tableName + " 表删除数据失败！");
        }
    }

    /**
     * 根据表名字获取所有数据
     */
    public static List<Result> getAll(String tableName) {
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            ResultScanner scanner = table.getScanner(scan);
            List<Result> list = new ArrayList<Result>();
            for (Result r : scanner) {
                list.add(r);
            }
            scanner.close();
            System.out.println(tableName + " 表获取所有记录成功！");
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除表
     */
    public static void deleteTable(String tableName) {
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Admin admin = connection.getAdmin();
            TableName table = TableName.valueOf(tableName);
            admin.disableTable(table);//使表失效
            admin.deleteTable(table);//删除表
            System.out.println("delete table " + tableName + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}