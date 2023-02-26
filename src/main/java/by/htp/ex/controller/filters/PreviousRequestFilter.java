package by.htp.ex.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.HttpMethod;
import java.io.IOException;
import java.util.Map;

public class PreviousRequestFilter implements Filter {
	private static final String CURRENT_REQUEST_NAME = "current_request_name";
	private static final String CURRENT_REQUEST_PARAMS = "current_request_params";
	private static final String LAST_REQUEST_PARAM = "last_request";
	
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
                lastRequestName = (String)session.getAttribute(CURRENT_REQUEST_NAME);
                lastRequestParams = (Map<String, String[]>)session.getAttribute(CURRENT_REQUEST_PARAMS);                          

                session.setAttribute(CURRENT_REQUEST_NAME, req.getRequestURL().toString());
                session.setAttribute(CURRENT_REQUEST_PARAMS, paramsMap);

                if (lastRequestName == null) {
                    lastRequestName = (String)session.getAttribute(CURRENT_REQUEST_NAME);
                    lastRequestParams = (Map<String, String[]>)session.getAttribute(CURRENT_REQUEST_PARAMS);  
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
                    
                    session.setAttribute(LAST_REQUEST_PARAM, lastRequest.toString());
                } catch (Exception e) {
                    session.setAttribute(LAST_REQUEST_PARAM, null);
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}