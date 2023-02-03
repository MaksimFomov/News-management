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
	List<News> newsStorage = new ArrayList<>();
	{
		newsStorage.add(new News(1, "title1", "brief1brief1brief1brief1brief1brief1brief1", "content1", "07/11/22"));
		newsStorage.add(new News(2, "title2", "brief2brief2brief2brief2brief2brief2brief2", "content2", "06/11/22"));
		newsStorage.add(new News(3, "title3", "brief3brief3brief3brief3brief3brief3brief3", "content3", "11/11/22"));
		newsStorage.add(new News(4, "title4", "brief4brief4brief4brief4brief4brief4brief4", "content4", "08/11/22"));
		newsStorage.add(new News(5, "title5", "brief5brief5brief5brief5brief5brief5brief5", "content5", "11/11/22"));
	}
	
	@Override
	public List<News> getLatestList(int count) throws NewsDAOException {
		if (newsStorage.size() < count) {
			return newsStorage;
		} 
		else {
			List<News> allNews = new ArrayList<>(newsStorage);
			
			allNews.sort((o1, o2) -> {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate date1 = LocalDate.parse(o1.getNewsDate(), formatter);
				LocalDate date2 = LocalDate.parse(o2.getNewsDate(), formatter);
				return date2.compareTo(date1);
			});
			
			return allNews.subList(0, count);
		}
	}

	@Override
	public List<News> getList() throws NewsDAOException {
		return newsStorage;
	}

	@Override
	public News fetchById(int id) throws NewsDAOException {
		return newsStorage.stream().filter(o -> o.getIdNews().equals(id)).findAny().orElse(null);
	}

	@Override
	public int addNews(News news) throws NewsDAOException {
		newsStorage.add(news);
		return 0;
	}

	@Override
	public void updateNews(News news) throws NewsDAOException {
		newsStorage.removeIf(o -> o.getIdNews().equals(news.getIdNews()));
		newsStorage.add(news);
	}

	@Override
	public void deleteNews(String[] idNews) throws NewsDAOException {
		var idToDelete = new ArrayList<>(Arrays.asList(idNews));
		
		new ArrayList<>(newsStorage).stream().forEach(o -> {
			String id = o.getIdNews().toString();
			
			if(idToDelete.contains(id)) {
				newsStorage.remove(o);
		 		idToDelete.remove(id);
			}
		});
	}
}
