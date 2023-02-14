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

    @SuppressWarnings("unchecked")
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
    	
        if (req.getMethod().equals(HttpMethod.GET)) {
            HttpSession session = req.getSession(false);
            Map<String, String[]> paramsMap = req.getParameterMap();
            StringBuilder lastRequest;
        	String lastRequestName;
        	Map<String, String[]> lastRequestParams;

            if (!paramsMap.isEmpty()) {
                lastRequestName = (String)session.getAttribute("current_request_name");
                lastRequestParams = (Map<String, String[]>)session.getAttribute("current_request_params");                          

                session.setAttribute("current_request_name", req.getRequestURL().toString());
                session.setAttribute("current_request_params", paramsMap);

                if (lastRequestName == null) {
                    lastRequestName = (String)session.getAttribute("current_request_name");
                    lastRequestParams = (Map<String, String[]>)session.getAttribute("current_request_params");  
                }
                
                lastRequest = new StringBuilder(lastRequestName);
                try {                    
                    lastRequest.append("?");
                    for (var paramArray : lastRequestParams.entrySet()) {
                        for(var param : paramArray.getValue()) {
                            lastRequest.append(paramArray.getKey()).append("=").append(param).append("&");
                        }
                    }
                    lastRequest.deleteCharAt(lastRequest.length() - 1);
                    
                    session.setAttribute("last_request", lastRequest.toString());
                } catch (Exception e) {
                    session.setAttribute("last_request", null);
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}