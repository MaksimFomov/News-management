package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToNewsList implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	
	private static final String NEWS_PARAM = "news";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_LOCAL_KEY = "newsList";
	private static final String ERROR_MESSAGE_PARAM = "error_msg";
	private static final String ERROR_MESSAGE_LOCAL_KEY = "cannot get the list of news";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> newsList;
		
		try {
			newsList = newsService.list();
			
			if (newsList.size() > 0) {
				request.setAttribute(NEWS_PARAM, newsList);
			}
			
			request.setAttribute(PRESENTATION_PARAM, PRESENTATION_LOCAL_KEY);
			request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
		} catch (ServiceException e) {
			HttpSession session = request.getSession(false);
			session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
			
			response.sendRedirect("controller?command=go_to_error_page");
		}	
	}
}
