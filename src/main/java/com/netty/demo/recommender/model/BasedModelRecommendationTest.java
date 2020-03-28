package com.netty.demo.recommender.model;

/**
 * @Description: BasedItemRecommendationTest
 * 基于模型的协同过滤推荐，是采用机器学习的方法，通过离线计算实现推荐的，
 * 通常它会首先根据历史数据，将数据集分成训练集和测试集两个数据集，
 * 使用训练集进行训练生成推荐模型，然后将推荐模型应用到测试集上，评估模型的优劣，
 * 如果模型到达实际所需要的精度，最后可以使用训练得到的推荐模型进行推荐（预测）。
 * 可见，这种方法使用离线的历史数据，进行模型训练和评估，需要耗费较长的时间，依赖于实际的数据集规模、机器学习算法计算复杂度。
 */
public class BasedModelRecommendationTest {

    public static void main(String[] args) {

    }
}
