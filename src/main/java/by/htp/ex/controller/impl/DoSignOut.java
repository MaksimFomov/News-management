package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignOut implements Command {
	private static final String ROLE_PARAM = "role";
	private static final String ROLE_LOCAL_KEY = "guest";
	private static final String USER_ACTIVITY_PARAM = "userActivity";
	private static final String USER_ACTIVITY_NOT_ACTIVE_LOCAL_KEY = "not active";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.setAttribute(USER_ACTIVITY_PARAM, USER_ACTIVITY_NOT_ACTIVE_LOCAL_KEY);
		session.setAttribute(ROLE_PARAM, ROLE_LOCAL_KEY);
		
		response.sendRedirect("index.jsp");
	}
}
