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
	
	private static final String INFO_MESSAGE_PARAM = "delete_success";
	private static final String INFO_MESSAGE_LOCAL_KEY = "suc";
	private static final String ERROR_MESSAGE_PARAM = "error_msg";
	private static final String ERROR_MESSAGE_LOCAL_KEY = "delete error";
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String[] newsIds = request.getParameterValues(JSP_ID_PARAM);
        
        if (newsIds != null) {
            try {
                newsService.delete(newsIds);
                
                session.setAttribute(INFO_MESSAGE_PARAM, INFO_MESSAGE_LOCAL_KEY);
                response.sendRedirect("controller?command=go_to_news_list");
            } catch (ServiceException e) {
                session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
                response.sendRedirect("controller?command=go_to_error_page");
            }
        } 
        else {
            session.setAttribute("error_msg", "no news to delete selected");
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }    
}
