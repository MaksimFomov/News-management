package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class GoToErrorPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String errorMessage = (String) session.getAttribute("error_msg");

        if (errorMessage == null) {
            session.setAttribute("error_msg", "no such command error");
        }
        
        request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
    }
}
