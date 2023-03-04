package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.User;
import by.htp.ex.controller.Command;
import by.htp.ex.service.IUserService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoRegistration implements Command {
	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";
	
	private static final String INFO_MESSAGE_PARAM = "register_success";
	private static final String INFO_MESSAGE_LOCAL_KEY = "suc";
	private static final String ERROR_MESSAGE_PARAM = "register_error";
	private static final String INVALID_VALUES_PARAM = "invalid_values_for_register";
	private static final String ERROR_MESSAGE_LOCAL_KEY = "err";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		try {
			User newUser = new User();
			newUser.setLogin(request.getParameter(JSP_LOGIN_PARAM));
			newUser.setPassword(request.getParameter(JSP_PASSWORD_PARAM));
			
			if (!service.register(newUser)) {
				session.setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE_LOCAL_KEY);
				response.sendRedirect("controller?command=go_to_registration_page");
			} 
			else {
				session.setAttribute(INFO_MESSAGE_PARAM, INFO_MESSAGE_LOCAL_KEY);
				response.sendRedirect("controller?command=go_to_news_list");
			}
		} catch (ServiceException e) {
			session.setAttribute(INVALID_VALUES_PARAM, ERROR_MESSAGE_LOCAL_KEY);
			response.sendRedirect("controller?command=go_to_registration_page");
		}
	}
}
