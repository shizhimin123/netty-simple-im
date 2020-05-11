package com.netty.demo.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Description: Village
 * @Author: shizhimin
 * @Date: 2020/4/15
 * @Version: 1.0
 */
@Data
@Document("gaode_poi_copy")
public class Village {
    @Field("id")
    private String id;
    @Field("name")
    private String name;
    @Field("type")
    private String type;
    @Field("address")
    private String address;
    @Field("location")
    private String location;
    @Field("adcode")
    private String adcode;
    @Field("adname")
    private String adname;
}
