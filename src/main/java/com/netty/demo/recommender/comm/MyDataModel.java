package com.netty.demo.recommender.comm;

import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.springframework.boot.jdbc.DataSourceBuilder;


public class MyDataModel {

	public static JDBCDataModel myDataModel() {
        JDBCDataModel dataModel = null;
        try {
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
            dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/shopping?characterEncoding=UTF-8&serverTimezone=UTC");
            dataSourceBuilder.username("root");
            dataSourceBuilder.password("root");
            dataModel = new MySQLJDBCDataModel(dataSourceBuilder.build(),"movie_preferences", "userID", "movieID","preference",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataModel;
    } 

}
