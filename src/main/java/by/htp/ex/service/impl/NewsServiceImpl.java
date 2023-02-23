package by.htp.ex.service.impl;

import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.util.validation.NewsDataValidation;
import by.htp.ex.util.validation.ValidationProvider;

public class NewsServiceImpl implements INewsService {
	private final NewsDataValidation newsDataValidation = ValidationProvider.getInstance().getNewsDataValidation();
	private final INewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();
	
	private static final String ERROR_MESSAGE_FOR_INVALID_NEWS_DATA = "fill in all the fields";

	@Override
	public void delete(String[] newsIds) throws ServiceException {
		try {
			newsDAO.deleteNews(newsIds);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void add(News news) throws ServiceException {
		if(!newsDataValidation.checkNewsData(news.getTitle(), news.getBrief(), news.getContent(), news.getDate())) {
			throw new ServiceException(ERROR_MESSAGE_FOR_INVALID_NEWS_DATA);
		}
		
		try {			
			newsDAO.addNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(News news) throws ServiceException {
		if(!newsDataValidation.checkNewsData(news.getTitle(), news.getBrief(), news.getContent(), news.getDate())) {
			throw new ServiceException(ERROR_MESSAGE_FOR_INVALID_NEWS_DATA);
		}
		
		try {			
			newsDAO.updateNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> latestList(int count) throws ServiceException {
		try {
			List<News> latestNews = newsDAO.getLatestNewsList(count);
			
			return latestNews;
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> list() throws ServiceException {
		try {
			return newsDAO.getNewsList();
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public News findById(int id) throws ServiceException {
		try {
			return newsDAO.fetchById(id);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}
}
