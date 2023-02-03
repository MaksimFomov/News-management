package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class GoToRegistrationPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("presentation", "registration");
		
		request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
	}
}
