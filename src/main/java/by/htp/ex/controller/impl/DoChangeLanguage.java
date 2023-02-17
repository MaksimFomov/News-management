package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DoChangeLanguage implements Command {
	private static final String LOCAL_PARAM = "local";
	private static final String LAST_REQUEST_PARAM = "last_request";
	private static final String ERROR_MESSAGE_PARAM = "error_msg";
	private static final String ERROR_MESSAGE_LOCAL_KEY = "error getting last request";
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setAttribute(LOCAL_PARAM, request.getParameter(LOCAL_PARAM));
        
        try {
            String lastRequest = (String)session.getAttribute(LAST_REQUEST_PARAM);

            if(lastRequest != null) {
                response.sendRedirect(lastRequest);
            } 
            else {
                session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
                response.sendRedirect("controller?command=go_to_error_page");
            }
        } catch (Exception e) {
            session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}
