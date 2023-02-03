package by.htp.ex.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;

public class NewsServiceImpl implements INewsService{

	@Override
	public void delete(String[] newsIds) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(News news) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void find() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(News news) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<News> latestList(int count) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<News> list() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public News findById(int id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
