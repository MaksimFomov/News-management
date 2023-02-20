package by.htp.ex.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.dao.connection_pool.ConnectionPool;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;

public class NewsDAO implements INewsDAO {		
	private static final String SQL_QUERY_FOR_GET_NEWS_LIST = "SELECT * FROM news";
	private static final String SQL_QUERY_FOR_ADD_NEWS = "INSERT INTO news(title, brief, content, date, user_id) VALUES (?, ?, ?, ?, 7)"; 
	private static final String SQL_QUERY_FOR_UPDATE_NEWS = "UPDATE news SET title = ?, brief = ?, content = ?, date = ?, user_id = 7 WHERE id = ?"; 
	private static final String SQL_QUERY_FOR_DELETE_NEWS = "DELETE FROM news WHERE id = ?";
	
	private static final String DB_FIELD_ID = "id";
	private static final String DB_FIELD_TITLE = "title";
	private static final String DB_FIELD_BRIEF = "brief";
	private static final String DB_FIELD_CONTENT = "content";
	private static final String DB_FIELD_DATE= "date";
	
	@Override
	public List<News> getLatestNewsList(int count) throws NewsDAOException {
		List<News> latestNews = getNewsList();
		Collections.sort(latestNews, (emp1, emp2) -> emp2.getDate().compareTo(emp1.getDate()));
		
		if(latestNews.size() > count) {
			return latestNews.stream().limit(count).toList();
		}

		return latestNews;
	}

	@Override
	public List<News> getNewsList() throws NewsDAOException {
		List<News> newsList = new ArrayList<>();
		ResultSet resultSet = null;
		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_GET_NEWS_LIST)) {	
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				newsList.add(new News(
						resultSet.getInt(DB_FIELD_ID),
						resultSet.getString(DB_FIELD_TITLE),
						resultSet.getString(DB_FIELD_BRIEF),
						resultSet.getString(DB_FIELD_CONTENT),
						resultSet.getString(DB_FIELD_DATE)));
			} 
		} catch (SQLException e) {
			throw new NewsDAOException("sql error", e);
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		}
		
		return newsList;
	}

	@Override
	public News fetchById(int id) throws NewsDAOException {
		return getNewsList().stream().filter(o -> o.getId().equals(id)).findAny().orElse(null);
	}

	@Override
	public void addNews(News news) throws NewsDAOException {		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_ADD_NEWS)) {			    
			statement.setString(1, news.getTitle());
			statement.setString(2, news.getBrief());
			statement.setString(3, news.getContent());
			statement.setString(4, news.getDate());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new NewsDAOException("sql error", e);
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} 
	}

	@Override
	public void updateNews(News news) throws NewsDAOException {		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_UPDATE_NEWS)) {		
			statement.setString(1, news.getTitle());
			statement.setString(2, news.getBrief());
			statement.setString(3, news.getContent());
			statement.setString(4, news.getDate());
			statement.setInt(5, news.getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new NewsDAOException("sql error", e);
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} 
	}

	@Override
	public void deleteNews(String[] idNews) throws NewsDAOException {		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_DELETE_NEWS)) {	
			for(var id: idNews) {
				statement.setString(1, id);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new NewsDAOException("sql error", e);
		}  catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} 
	}
}
