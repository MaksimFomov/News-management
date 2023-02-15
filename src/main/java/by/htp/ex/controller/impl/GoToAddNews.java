package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class GoToAddNews implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        try {
            News news = new News();
            request.setAttribute("news", news);
            request.setAttribute("presentation", "addNews");
            request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
        } catch (Exception e) { 
            HttpSession session = request.getSession(false);
            session.setAttribute("error_msg", "cannot get the list of news");
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}