package by.htp.ex.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SessionFilter implements Filter {
	private static final String ROLE_PARAM = "role";
	private static final String ROLE_GUEST_LOCAL_KEY = "guest";
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
        
        if (req.getSession() == null) {
            ((HttpServletRequest) request).getSession(true).setAttribute(ROLE_PARAM, ROLE_GUEST_LOCAL_KEY);
            resp.sendRedirect("controller?command=go_to_base_page");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}