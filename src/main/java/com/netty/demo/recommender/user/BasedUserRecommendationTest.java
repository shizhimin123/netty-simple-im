package com.netty.demo.recommender.user;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: BasedUserRecommendationTest
 * 不考虑用户、物品的属性（特征）信息，它主要是根据用户对物品的偏好（Preference）信息，
 * 发掘不同用户之间口味（Taste）的相似性，使用这种用户相似性来进行个性化推荐。
 * 基于用户的协同过滤推荐，它是以用户为中心，观察与该用户兴趣相似的一个用户的群体，
 * 将这个兴趣相似的用户群体所感兴趣的其他物品，推荐给该用户。
 */
public class BasedUserRecommendationTest {

    public static void main(String[] args) {
        //用户-物品-评分数据文件
        String filePath = "E:/alice.txt";
        //数据模型
        DataModel dataModel = null;
        try {
            //文件数据转换成数据模型
            dataModel = new FileDataModel(new File(filePath));
            /**
             * 用户相似度定义
             */
            //余弦相似度
//          UserSimilarity userSimilarity= new UncenteredCosineSimilarity(dataModel);
            //欧几里得相似度
//          UserSimilarity userSimilarity= new EuclideanDistanceSimilarity(dataModel);
            //皮尔森相似度
            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);

            //定义用户的2-最近邻
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
            //定义推荐引擎
            Recommender recommender = new GenericUserBasedRecommender(dataModel,userNeighborhood, userSimilarity);
            //从数据模型中获取所有用户ID迭代器
            LongPrimitiveIterator usersIterator = dataModel.getUserIDs();
            //通过迭代器遍历所有用户ID
            while (usersIterator.hasNext()) {
                System.out.println("================================================");
                //用户ID
                long userID = usersIterator.nextLong();
                //用户ID
                LongPrimitiveIterator otherusersIterator = dataModel.getUserIDs();
                //遍历用户ID，计算任何两个用户的相似度
                while (otherusersIterator.hasNext()) {
                    long otherUserID = otherusersIterator.nextLong();
                    System.out.println("用户 " + userID + " 与用户 " + otherUserID + " 的相似度为："
                            + userSimilarity.userSimilarity(userID, otherUserID));
                }
                //userID的N-最近邻
                long[] userN = userNeighborhood.getUserNeighborhood(userID);
                //用户userID的推荐物品，最多推荐两个
                List<RecommendedItem> recommendedItems = recommender.recommend(userID, 2);
                System.out.println("用户 "+userID + " 的2-最近邻是 "+ Arrays.toString(userN));
                if (recommendedItems.size() > 0) {
                    for (RecommendedItem item : recommendedItems) {
                        System.out.println("推荐的物品"+ item.getItemID()+"预测评分是 "+ item.getValue());
                    }
                } else {
                    System.out.println("无任何物品推荐");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
