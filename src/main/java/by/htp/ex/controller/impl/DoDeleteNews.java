package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DoDeleteNews implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	
	private static final String JSP_ID_PARAM = "id";
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String[] newsIds = request.getParameterValues(JSP_ID_PARAM);
        
        if (newsIds != null) {
            try {
                newsService.delete(newsIds);
                
                session.setAttribute("delete_success", "suc");
                response.sendRedirect("controller?command=go_to_news_list");
            } catch (ServiceException e) {
                session.setAttribute("error_msg", "delete error");
                response.sendRedirect("controller?command=go_to_error_page");
            }
        } 
        else {
            session.setAttribute("error_msg", "no news to delete selected");
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }    
}
