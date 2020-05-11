package com.netty.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import com.netty.demo.controller.mapper.AreaMapper;
import com.netty.demo.pojo.Area;
import com.netty.demo.pojo.AreaZone;
import com.netty.demo.pojo.Town;
import com.netty.demo.pojo.Village;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @Description: MongoDBController
 * @Author: shizhimin
 * @Date: 2020/4/11
 * @Version: 1.0
 */
@Controller
public class MongoDBController {


    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private AreaMapper areaMapper;

    @GetMapping(value = "/updateMongoData")
    public void updateMongoData(Integer currentPage) {
        if (null == currentPage){
            currentPage = 1;
        }
        int pageSize = 20;
        Query query = new Query();
        // 查询记录总数
        int totalCount = (int) mongoTemplate.count(query, Area.class);
        // 数据总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if (currentPage > 0){
            long apiNum = 0L;
            //31944   32148   35200
            for (int i = currentPage;i <= totalPage;i ++){
                if (apiNum < 100000){
                    query = new Query();
                    // 设置起始数
                    System.out.println("更新MongoDb数据到 : --> "+i);
                    query.skip((i - 1) * pageSize)
                            // 设置查询条数
                            .limit(pageSize);
                    // 查询当前页数据集合
                    List<Area> records = mongoTemplate.find(query, Area.class);
                    //经纬度坐标;最多支持20个坐标点;多个点之间用"|"分割。
                    String locStr = records.stream().map(Area::getLocation).collect(Collectors.joining("|", "", ""));
                    List<Town> towns = new ArrayList<>(20);
                    String url = "https://restapi.amap.com/v3/geocode/regeo?location="+ locStr +"&key=5d199b345218da0f64959b7287988088&radius=1000&extensions=base&batch=true";
                    try {
                        String json = loadJSON(url);
                        JSONObject obj = JSONObject.parseObject(json);
                        String status = obj.get("status").toString();
                        if ("1".equals(status)) {
                            //返回集合json解析
                            JSONArray regeocode = obj.getJSONArray("regeocodes");
                            for(int j = 0;j < regeocode.size(); j++) {
                                Town town = new Town();
                                JSONObject addressComponent = (JSONObject) regeocode.getJSONObject(j).get("addressComponent");
                                String township = addressComponent.getString("township");
                                String townCode = addressComponent.getString("towncode");
                                town.setTownship(township);
                                town.setTownCode(townCode);
                                towns.add(town);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //mongdb更新数据
                    if (records.size() > 0 && towns.size() > 0){
                        for (int t = 0;t < records.size();t ++){
                            Area area = records.get(t);
                            Town town = towns.get(t);
                            if (null != area && null != town){
                                query = new Query();
                                query.addCriteria(Criteria.where("id").is(area.getId()));
                                Update update = new Update();
                                update.set("adcode", town.getTownCode());
                                update.set("adname", town.getTownship());
                                UpdateResult result = mongoTemplate.upsert(query, update, "gaode_poi_copy_copy");
                                System.out.println(result.toString());
                            }
                        }
                    }
                }
                apiNum ++;
            }
        }
    }

    @GetMapping(value = "/integrationArea")
    public void integrationArea() {
        //1.查找1000条数据
        int pageSize = 1000;
        Query query = new Query();
        int totalCount = (int) mongoTemplate.count(query, Area.class);
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        for (int i = 0;i <= totalPage;i ++){
            query = new Query();
            query.skip((i - 1) * pageSize).limit(pageSize);
            List<Village> records = mongoTemplate.find(query, Village.class);
            if (records.size() > 0){
                for (Village record : records) {
                    //2.将查找出该条数据的归属地  整合数据  判断行政编码是否正确
                    QueryWrapper<AreaZone> queryWrapper = new QueryWrapper<AreaZone>();
                    queryWrapper.eq("level", 4).eq("id",record.getAdcode());
                    AreaZone areaZone = areaMapper.selectOne(queryWrapper);
                    if (null != areaZone){
                        queryWrapper = new QueryWrapper<AreaZone>();
                        queryWrapper.eq("level", 5).eq("pid",record.getAdcode());
                        List<AreaZone> areaZones = areaMapper.selectList(queryWrapper);
                        //3.同时查出该乡镇的所有行政村委 判断该村是否是自然村还是行政村
                        boolean match = false;
                        if (null != areaZones && areaZones.size() > 0){
                            match = areaZones.stream()
                                    .noneMatch((e) ->{
                                        Pattern p = Pattern.compile(e.getName());
                                        Matcher m = p.matcher(record.getName());
                                        return m.matches();
                                    });
                        }
                        if (match){
                            //3.更新数据 删除原表数据

                            return;

                        }
                        //4.行政编码生成策略

                    }
                    //5.插入到新表 删除原表数据
                    int result = areaMapper.insert(null);
                    if (result > 0){
                        query = new Query();
                        mongoTemplate.findAndRemove(query,Village.class);
                    }
                }
            }
        }
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}
