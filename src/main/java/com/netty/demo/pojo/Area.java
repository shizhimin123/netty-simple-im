package com.netty.demo.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Description: Area
 * @Author: shizhimin
 * @Date: 2020/4/11
 * @Version: 1.0
 */
@Data
@Document("gaode_poi_copy_copy")
public class Area {
    @Field("id")
    private String id;
    @Field("location")
    private String location;
    @Field("adcode")
    private String adcode;
    @Field("adname")
    private String adname;
}
