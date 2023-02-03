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

public class GoToAddNews implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id;
        
        try {
            id = newsService.list().size() + 1;
            News news = new News(id, "", "", "", "");
            
            request.setAttribute("news", news);
            request.setAttribute("presentation", "addNews");
            
            request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
        } catch (ServiceException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("error_msg", "cannot get the list of news");
            
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}
