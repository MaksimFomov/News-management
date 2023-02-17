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

public class GoToEditNews implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	
	private static final String ID_PARAM = "id";
	private static final String NEWS_PARAM = "news";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_LOCAL_KEY = "editNews";
	private static final String ERROR_MESSAGE_PARAM = "error_msg";
	private static final String ERROR_MESSAGE_LOCAL_KEY = "cannot find the news by id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        News news;
        String id = request.getParameter(ID_PARAM);

        try {
            news = newsService.findById(Integer.parseInt(id));
            request.setAttribute(NEWS_PARAM, news);
            request.setAttribute(PRESENTATION_PARAM, PRESENTATION_LOCAL_KEY);

            request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
        } catch (ServiceException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
            
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}
