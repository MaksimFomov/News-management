package by.htp.ex.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.HttpMethod;
import java.io.IOException;
import java.util.Map;

public class PreviousRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
    	
        if (req.getMethod().equals(HttpMethod.GET)) {
            HttpSession session = req.getSession(false);
            Map<String, String[]> paramsMap = req.getParameterMap();

            if (!paramsMap.isEmpty()) {
                session.setAttribute("last_request_name", session.getAttribute("current_request_name"));
                session.setAttribute("last_request_params", session.getAttribute("current_request_params"));                          

                session.setAttribute("current_request_name", req.getRequestURL().toString());
                session.setAttribute("current_request_params", paramsMap);

                if (session.getAttribute("last_request_name") == null) {
                    session.setAttribute("last_request_name", session.getAttribute("current_request_name"));
                    session.setAttribute("last_request_params", session.getAttribute("current_request_params"));
                }
                
                String lastRequestName = (String)session.getAttribute("last_request_name");
                Map<String, String[]> lastRequestParams = (Map<String, String[]>)session.getAttribute("last_request_params");
                
                session.setAttribute("last_request", getLastRequest(lastRequestName, lastRequestParams));
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
    
    public static String getLastRequest(String requestName, Map<String, String[]> requestParams) {
        StringBuilder lastRequest = new StringBuilder(requestName);
        
        try {
            Map<String, String[]> paramsMap = requestParams;
            
            lastRequest.append("?");
            for (var paramArray : paramsMap.entrySet()) {
                for(var param : paramArray.getValue()) {
                    lastRequest.append(paramArray.getKey()).append("=").append(param).append("&");
                }
            }
            lastRequest.deleteCharAt(lastRequest.length() - 1);
            
            return lastRequest.toString();
        } catch (Exception e) {
            return null;
        }
    }
}