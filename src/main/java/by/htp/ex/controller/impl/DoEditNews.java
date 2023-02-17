package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DoEditNews implements Command {
    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
    
	private static final String JSP_ID_PARAM = "id";
	private static final String JSP_NEWS_TITLE_PARAM = "news_title";
	private static final String JSP_NEWS_BRIEF_PARAM = "news_brief";
	private static final String JSP_NEWS_CONTENT_PARAM = "news_content";
	private static final String JSP_NEWS_DATE_PARAM = "news_date";
	
	private static final String INFO_MESSAGE_PARAM = "save_success";
	private static final String INFO_MESSAGE_LOCAL_KEY = "suc";
	private static final String ERROR_MESSAGE_PARAM = "error_msg";
	private static final String ERROR_MESSAGE_LOCAL_KEY = "update error";
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int id = Integer.parseInt(request.getParameter(JSP_ID_PARAM));
        
        News newNews = new News(
        		id,
        		request.getParameter(JSP_NEWS_TITLE_PARAM), 
        		request.getParameter(JSP_NEWS_BRIEF_PARAM), 
        		request.getParameter(JSP_NEWS_CONTENT_PARAM), 
        		request.getParameter(JSP_NEWS_DATE_PARAM));
        
        try {
            newsService.update(newNews);
            session.setAttribute(INFO_MESSAGE_PARAM, INFO_MESSAGE_LOCAL_KEY);
            response.sendRedirect("controller?command=go_to_news_list");
        } catch (ServiceException e) {
            session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}