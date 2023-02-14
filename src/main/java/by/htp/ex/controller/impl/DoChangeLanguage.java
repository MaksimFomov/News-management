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
        
        try {
            String lastRequest = (String)session.getAttribute("last_request");

            if(lastRequest != null) {
                response.sendRedirect(lastRequest);
            } 
            else {
                session.setAttribute("error_msg", "error getting last request");
                response.sendRedirect("controller?command=go_to_error_page");
            }
        } catch (Exception e) {
            session.setAttribute("error_msg", "error getting last request");
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}
