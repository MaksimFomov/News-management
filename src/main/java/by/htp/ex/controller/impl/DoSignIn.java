package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.User;
import by.htp.ex.controller.Command;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.service.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignIn implements Command {
	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";
	
	private static final String ROLE_PARAM = "role";
	private static final String USER_ACTIVITY_PARAM = "userActivity";
	private static final String USER_ACTIVITY_ACTIVE_LOCAL_KEY = "active";
	private static final String USER_ACTIVITY_NOT_ACTIVE_LOCAL_KEY = "not active";
	private static final String AUTH_ERROR_MESSAGE_PARAM = "auth_error";
	private static final String AUTH_ERROR_MESSAGE_LOCAL_KEY = "wrong login or password";
	private static final String ERROR_MESSAGE_PARAM = "error_msg";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession(false);
		
		try {
			User user = new User();
			user.setLogin(request.getParameter(JSP_LOGIN_PARAM));
			user.setPassword(request.getParameter(JSP_PASSWORD_PARAM));
			
			String role = service.signIn(user);

			if (!"guest".equals(role)) {
				session.setAttribute(USER_ACTIVITY_PARAM, USER_ACTIVITY_ACTIVE_LOCAL_KEY);
				session.setAttribute(ROLE_PARAM, role);	
				
				response.sendRedirect("controller?command=go_to_news_list");
			} else {
				session.setAttribute(USER_ACTIVITY_PARAM, USER_ACTIVITY_NOT_ACTIVE_LOCAL_KEY);
				session.setAttribute(AUTH_ERROR_MESSAGE_PARAM, AUTH_ERROR_MESSAGE_LOCAL_KEY);
				
				response.sendRedirect("controller?command=go_to_base_page");
			}
		} catch (ServiceException e) {
			session.setAttribute(ERROR_MESSAGE_PARAM, e.getMessage());
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}
}
