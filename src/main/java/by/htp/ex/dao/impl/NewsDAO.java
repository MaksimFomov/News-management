package by.htp.ex.dao.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;

public class NewsDAO implements INewsDAO {

	@Override
	public List<News> getList() throws NewsDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<News> getLatestList(int count) throws NewsDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public News fetchById(int id) throws NewsDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addNews(News news) throws NewsDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateNews(News news) throws NewsDAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNews(String[] idNewses) throws NewsDAOException {
		// TODO Auto-generated method stub
		
	}

	

}
