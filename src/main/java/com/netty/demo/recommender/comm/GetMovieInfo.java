/**
 * 获得电影的信息
 */
package com.netty.demo.recommender.comm;

import com.netty.demo.recommender.item.MyItemBasedRecommender;
import com.netty.demo.recommender.user.MyUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class GetMovieInfo {
	private DataSource dataSource;
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	//根据推荐的movie的ID，获得movie的详细信息
	public ArrayList<MovieInfo> getMovieByMovieId(int id){
		ArrayList<MovieInfo> movieList = new ArrayList<MovieInfo>();
		try {

			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
			dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
			dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/shopping?characterEncoding=UTF-8&serverTimezone=UTC");
			dataSourceBuilder.username("root");
			dataSourceBuilder.password("root");
			dataSource = dataSourceBuilder.build();
			conn = dataSource.getConnection();

			String sql = "select m.name,m.published_year,m.type from movies m where m.id="+id+"";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				MovieInfo movieInfo = new MovieInfo();
				movieInfo.setName(rs.getString(1));
				movieInfo.setPublishedYear(rs.getString(2));
				movieInfo.setType(rs.getString(3));
				movieInfo.setPreference(5.0);
				movieList.add(movieInfo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return movieList;
	}
	
	//根据推荐的movie的ID，获得movie的详细信息
	public ArrayList<MovieInfo> getMovieByMovieId(List<RecommendedItem> recommendations){
		ArrayList<MovieInfo> movieList = new ArrayList<MovieInfo>();
		try {
			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
			dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
			dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/shopping?characterEncoding=UTF-8&serverTimezone=UTC");
			dataSourceBuilder.username("root");
			dataSourceBuilder.password("root");
			dataSource = dataSourceBuilder.build();
			conn = dataSource.getConnection();

			String sql = "";
			for(int i=0;i<recommendations.size();i++){
				sql = "select m.name,m.published_year,m.type from movies m where m.id="+recommendations.get(i).getItemID()+"";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					MovieInfo movieInfo = new MovieInfo();
					movieInfo.setName(rs.getString(1));
					movieInfo.setPublishedYear(rs.getString(2));
					movieInfo.setType(rs.getString(3));
					movieInfo.setPreference(recommendations.get(i).getValue());
					movieList.add(movieInfo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return movieList;
	}
	
	//根据用户的id，获得用户的所有电影
	public ArrayList<MovieInfo> getMovieByUserId(long userID){
		ArrayList<MovieInfo> movieList = new ArrayList<MovieInfo>();
		try {
			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
			dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
			dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/shopping?characterEncoding=UTF-8&serverTimezone=UTC");
			dataSourceBuilder.username("root");
			dataSourceBuilder.password("root");
			dataSource = dataSourceBuilder.build();
			conn = dataSource.getConnection();

			String sql = "select m.name,m.published_year,m.type,mp.preference from movie_preferences mp,movies m where mp.movieID=m.id and mp.userID="+userID+" order by preference desc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				MovieInfo movieInfo = new MovieInfo();
				movieInfo.setName(rs.getString(1));
				movieInfo.setPublishedYear(rs.getString(2));
				movieInfo.setType(rs.getString(3));
				movieInfo.setPreference(rs.getInt(4));
				movieList.add(movieInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return movieList;
	}
	
	//关闭数据库
	public void closeAll(){
		try {
			if(rs !=null){
				rs.close();
				rs = null;
			}
			if(ps !=null){
				ps.close();
				ps = null;
			}
			if(conn !=null){
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		//接收用户id
		long userId = 1L;
		//接收size
		int size = 5;
		//接收推荐类型参数
		String recommendType = "userBased";
		GetMovieInfo getMovieInfo = new GetMovieInfo();
		//用户的所有电影
		ArrayList<MovieInfo> ownMovieInfo = getMovieInfo.getMovieByUserId(userId);
		//推荐电影的List
		List<RecommendedItem> recommendation = null;
		//基于用户
		if("userBased".equals(recommendType)){
			MyUserBasedRecommender mubr = new MyUserBasedRecommender();
			recommendation = mubr.userBasedRecommender(userId,size);
			//基于内容
		}else if("itemBased".equals(recommendType)){
			MyItemBasedRecommender mibr = new MyItemBasedRecommender();
			recommendation = mibr.myItemBasedRecommender(userId,size);
		}
		//拿到推荐的电影的详细信息
		ArrayList<MovieInfo> recommendMovieInfo = getMovieInfo.getMovieByMovieId(recommendation);
		//页面跳转
		System.out.println(ownMovieInfo.toString());
		System.out.println(recommendMovieInfo.toString());
	}
}
