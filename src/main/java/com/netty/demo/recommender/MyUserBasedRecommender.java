package com.netty.demo.recommender;

import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;

import java.util.List;

public class MyUserBasedRecommender {

	public List<RecommendedItem> userBasedRecommender(long userID,int size) {
		// step:1 构建模型 2 计算相似度 3 查找k紧邻 4 构造推荐引擎
		List<RecommendedItem> recommendations = null;
		try {
			//构造数据模型
			DataModel model = MyDataModel.myDataModel();
			//用PearsonCorrelation 算法计算用户相似度
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			//计算用户的“邻居”，这里将与该用户最近距离为 3 的用户设置为该用户的“邻居”。
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
			//采用 CachingRecommender 为 RecommendationItem 进行缓存
			Recommender recommender = new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, similarity));
			//得到推荐的结果，size是推荐结果的数目
			recommendations = recommender.recommend(userID, size);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return recommendations;
	}


}