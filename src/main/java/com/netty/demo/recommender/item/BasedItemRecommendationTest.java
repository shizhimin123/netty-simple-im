package com.netty.demo.recommender.item;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.util.List;

/**
 * @Description: BasedItemRecommendationTest
 * @Author: shizhimin
 * @Date: 2020/3/25
 * @Version: 1.0
 */
public class BasedItemRecommendationTest {

    public static void main(String[] args) {
        //用户-物品-评分数据文件
        String filePath = "E:/alice.txt";
        //数据模型
        DataModel dataModel = null;
        try {
            //文件数据转换成数据模型
            dataModel = new FileDataModel(new File(filePath));
            /**
             * 物品相似度定义
             */
            //余弦相似度
            ItemSimilarity itemSimilarity = new UncenteredCosineSimilarity(dataModel);
            //欧几里得相似度
//            ItemSimilarity itemSimilarity= new EuclideanDistanceSimilarity(dataModel);
//            //皮尔森相似度
//            ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
            //定义推荐引擎
            Recommender recommender =new GenericItemBasedRecommender(dataModel, itemSimilarity);
            //获取物品迭代器
            LongPrimitiveIterator itemIDIterator = dataModel.getItemIDs();
            //遍历所有物品
            while(itemIDIterator.hasNext()){
                System.out.println("==================================================");
                Long itermID=itemIDIterator.next();
                LongPrimitiveIterator otherItemIDIterator=dataModel.getItemIDs();
                //打印物品相似度
                while (otherItemIDIterator.hasNext()){
                    Long otherItermID=otherItemIDIterator.next();
                    System.out.println("物品 "+itermID+" 与物品 "+otherItermID+" 的相似度为： "+itemSimilarity.itemSimilarity(itermID,otherItermID));
                }
            }
            //获取用户迭代器
            LongPrimitiveIterator userIDIterator =dataModel.getUserIDs();
            //遍历用户
            while(userIDIterator.hasNext()){
                //获取用户
                Long userID=userIDIterator.next();
                //获取用户userID的推荐列表
                List<RecommendedItem> itemList= recommender.recommend(userID,2);
                if(itemList.size()>0){
                    for(RecommendedItem item:itemList){
                        System.out.println("用户 "+userID+" 推荐物品 "+item.getItemID()+",物品评分 "+item.getValue());
                    }
                }else {
                    System.out.println("用户 "+userID+" 无任何物品推荐");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
