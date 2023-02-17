package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class GoToErrorPage implements Command {
	private static final String ERROR_MESSAGE_PARAM = "error_msg";
	private static final String ERROR_MESSAGE_LOCAL_KEY = "no such command error";
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String errorMessage = (String) session.getAttribute(ERROR_MESSAGE_PARAM);

        if (errorMessage == null) {
            session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
        }
        
        request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
    }
}
