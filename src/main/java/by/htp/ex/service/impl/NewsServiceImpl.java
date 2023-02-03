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
	private final INewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();

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
		try {
			String newsDate = news.getNewsDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
			
			try {
				LocalDate date = LocalDate.parse(newsDate, formatter);
			} catch (DateTimeParseException e){
				throw new ServiceException(e);
			}
			
			newsDAO.addNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(News news) throws ServiceException{
		try {
			String newsDate = news.getNewsDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
			
			try {
				LocalDate date = LocalDate.parse(newsDate, formatter);
			} catch (DateTimeParseException e){
				throw new ServiceException(e);
			}
			
			newsDAO.updateNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> latestList(int count) throws ServiceException {
		try {
			List<News> latestNews = newsDAO.getLatestList(count);

			latestNews.sort((o1, o2) -> {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate date1 = LocalDate.parse(o1.getNewsDate(), formatter);
				LocalDate date2 = LocalDate.parse(o2.getNewsDate(), formatter);
				return date2.compareTo(date1);
			});
			
			return latestNews;
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> list() throws ServiceException {
		try {
			return newsDAO.getList();
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
