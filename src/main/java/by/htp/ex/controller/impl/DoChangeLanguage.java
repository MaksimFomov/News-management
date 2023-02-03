package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DoChangeLanguage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setAttribute("local", request.getParameter("local"));

        response.sendRedirect("controller?command=go_to_news_list");
    }
}
