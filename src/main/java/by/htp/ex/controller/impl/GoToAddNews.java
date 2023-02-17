package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class GoToAddNews implements Command {
	private static final String NEWS_PARAM = "news";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_LOCAL_KEY = "addNews";
	private static final String ERROR_MESSAGE_PARAM = "error_msg";
	private static final String ERROR_MESSAGE_LOCAL_KEY = "cannot get the list of news";
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        try {
            request.setAttribute(NEWS_PARAM, new News());
            request.setAttribute(PRESENTATION_PARAM, PRESENTATION_LOCAL_KEY);
            request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
        } catch (Exception e) { 
            HttpSession session = request.getSession(false);
            session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}