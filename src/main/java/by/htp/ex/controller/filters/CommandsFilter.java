package by.htp.ex.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.ex.controller.CommandName;
import static by.htp.ex.controller.CommandName.*;

public class CommandsFilter implements Filter {
	private final static List<CommandName> adminCommands = List.of
            (DO_SIGN_OUT, DO_ADD_NEWS, DO_DELETE_NEWS, DO_EDIT_NEWS, DO_CHANGE_LANGUAGE,
                    GO_TO_ADD_NEWS,GO_TO_EDIT_NEWS, GO_TO_BASE_PAGE, GO_TO_VIEW_NEWS, GO_TO_NEWS_LIST, GO_TO_ERROR_PAGE);
    private final static List<CommandName> userCommands = List.of
            (DO_SIGN_OUT, DO_CHANGE_LANGUAGE, 
            		GO_TO_VIEW_NEWS, GO_TO_NEWS_LIST, GO_TO_BASE_PAGE, GO_TO_ERROR_PAGE);

    private final static List<CommandName> guestCommands = List.of
            (DO_SIGN_IN, DO_REGISTRATION, DO_CHANGE_LANGUAGE, 
            		GO_TO_REGISTRATION_PAGE, GO_TO_VIEW_NEWS, GO_TO_NEWS_LIST, GO_TO_BASE_PAGE, GO_TO_ERROR_PAGE);
    
	private final static Map<String, List<CommandName>> rolePermissions = new HashMap<>() {
		private static final long serialVersionUID = 1L;
	{
		put("admin", adminCommands);
		put("user", userCommands);
		put("guest", guestCommands);
	}};
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         HttpSession session = ((HttpServletRequest) request).getSession(false);
         String commandNameStr = request.getParameter("command");
         
         if (commandNameStr == null) {
             session.setAttribute("error_msg", "no command passed in request");
         } 
         else {
             try {
                 CommandName commandName = CommandName.valueOf(commandNameStr.toUpperCase());
                 String userRole = (String) session.getAttribute("role");
                 
                 if(userRole == null) {
                	 userRole = "guest";
                 }
                 
                 if(!rolePermissions.get(userRole).contains(commandName)) {
                	 session.setAttribute("error_msg", "cannot do this request for this role"); 
                 }
             } catch (IllegalArgumentException e) {
                 session.setAttribute("error_msg", "invalid command passed in request");
             }
             
        	 chain.doFilter(request, response);
         }
    }

    @Override
    public void destroy() {}
}