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

public class DoAddNews implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	int id = Integer.parseInt(request.getParameter("id"));
        
        News newNews = new News(
        		id,
        		request.getParameter("news_title"), 
        		request.getParameter("news_brief"), 
        		request.getParameter("news_content"), 
        		request.getParameter("news_date"));

        try {
            newsService.add(newNews);
            session.setAttribute("save_success", "suc");
            response.sendRedirect("controller?command=go_to_news_list");
        } catch (ServiceException e) {
            session.setAttribute("error_msg", "add error");
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}
