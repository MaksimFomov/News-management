package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.controller.impl.utilities.Requests;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

public class DoChangeLanguage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setAttribute("local", request.getParameter("local"));

        try {
            String lastRequestName = (String) session.getAttribute("last_request_name");
            Map<String, String[]> paramsMap = (Map<String, String[]>) session.getAttribute("last_request_params");
            String lastRequest = Requests.getRequest(lastRequestName, paramsMap);
            
            if(lastRequest == null) {
                session.setAttribute("error_msg", "error getting last request");
                response.sendRedirect("controller?command=go_to_error_page");
            } 
            else {
                response.sendRedirect(lastRequest);
            }
        } catch (Exception e) {
        	session.setAttribute("error_msg", "error getting last request");
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}
