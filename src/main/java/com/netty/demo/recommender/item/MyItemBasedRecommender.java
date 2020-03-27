package com.netty.demo.recommender.item;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.util.List;

public class MyItemBasedRecommender {
	
	public List<RecommendedItem> myItemBasedRecommender(long userID,int size){
		List<RecommendedItem> recommendations = null;
		try {
			//构造数据模型 读取文件数据
			DataModel model = new FileDataModel(new File("E:/movie_preferences.txt"));
			//计算内容相似度
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
			//构造推荐引擎
			Recommender recommender = new GenericItemBasedRecommender(model, similarity);
			//得到推荐结果
			recommendations = recommender.recommend(userID, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recommendations;
	}

}
