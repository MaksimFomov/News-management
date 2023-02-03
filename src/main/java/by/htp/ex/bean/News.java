package by.htp.ex.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idNews = 0;
	private String title = "";
	private String briefNews = "";
	private String content = "";
	private String newsDate = "";
	
	public News(){}

	public News(int idNews, String title, String briefNews, String content, String newsDate) {
		super();
		this.idNews = idNews;
		this.title = title;
		this.briefNews = briefNews;
		this.content = content;
		this.newsDate = newsDate;
	}

	public Integer getIdNews() {
		return idNews;
	}

	public void setIdNews(Integer idNews) {
		this.idNews = idNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBriefNews() {
		return briefNews;
	}

	public void setBriefNews(String briefNews) {
		this.briefNews = briefNews;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) throws DateTimeParseException {
		this.newsDate = newsDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		News news = (News) o;
		return Objects.equals(idNews, news.idNews) && Objects.equals(title, news.title) && Objects.equals(briefNews, news.briefNews) && Objects.equals(content, news.content) && Objects.equals(newsDate, news.newsDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idNews, title, briefNews, content, newsDate);
	}
}
